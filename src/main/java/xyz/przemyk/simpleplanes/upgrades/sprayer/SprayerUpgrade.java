package xyz.przemyk.simpleplanes.upgrades.sprayer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

import java.util.List;

public class SprayerUpgrade extends Upgrade {
    public static final ResourceLocation TEXTURE = new ResourceLocation("simpleplanes", "textures/plane_upgrades/sprayer.png");
    public static final AxisAlignedBB AFFECT_ENTITIES = new AxisAlignedBB(-3, -3, -3, 3, 0, 3);

    public SprayerUpgrade(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.SPRAYER.get(), planeEntity);
    }

    private int fluid = 0;
    private Effect effect = null;

    @SuppressWarnings("ConstantConditions")
    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.putInt("fluid", fluid);
        compoundNBT.putString("effect", effect == null ? "empty" : effect.getRegistryName().toString());
        return compoundNBT;
    }

    @Override
    public void deserializeNBT(CompoundNBT compoundNBT) {
        fluid = compoundNBT.getInt("fluid");
        String effectName = compoundNBT.getString("effect");
        if (effectName.equals("empty")) {
            effect = null;
        } else {
            effect = ForgeRegistries.POTIONS.getValue(new ResourceLocation(effectName));
        }
    }

    @Override
    public boolean tick() {
        if (fluid > 0) {
            --fluid;

            if (!planeEntity.world.isRemote() && planeEntity.ticksExisted % 5 == 0) {
                ((ServerWorld) planeEntity.world).spawnParticle(ParticleTypes.CLOUD,
                        planeEntity.getPosX() - MathHelper.sin((planeEntity.rotationYaw - 50) * ((float) Math.PI / 180F)),
                        planeEntity.getPosY() + 0.5,
                        planeEntity.getPosZ() + MathHelper.cos((planeEntity.rotationYaw - 50) * ((float) Math.PI / 180F)),
                        0, 0, 0, 0, 0.0);

                ((ServerWorld) planeEntity.world).spawnParticle(ParticleTypes.CLOUD,
                        planeEntity.getPosX() - MathHelper.sin((planeEntity.rotationYaw + 50) * ((float) Math.PI / 180F)),
                        planeEntity.getPosY() + 0.5,
                        planeEntity.getPosZ() + MathHelper.cos((planeEntity.rotationYaw + 50) * ((float) Math.PI / 180F)),
                        0, 0, 0, 0, 0.0);

                ((ServerWorld) planeEntity.world).spawnParticle(ParticleTypes.CLOUD,
                        planeEntity.getPosX() - 2 * MathHelper.sin((planeEntity.rotationYaw - 80) * ((float) Math.PI / 180F)),
                        planeEntity.getPosY() + 0.5,
                        planeEntity.getPosZ() + 2 * MathHelper.cos((planeEntity.rotationYaw - 80) * ((float) Math.PI / 180F)),
                        0, 0, 0, 0, 0.0);

                ((ServerWorld) planeEntity.world).spawnParticle(ParticleTypes.CLOUD,
                        planeEntity.getPosX() - 2 * MathHelper.sin((planeEntity.rotationYaw + 80) * ((float) Math.PI / 180F)),
                        planeEntity.getPosY() + 0.5,
                        planeEntity.getPosZ() + 2 * MathHelper.cos((planeEntity.rotationYaw + 80) * ((float) Math.PI / 180F)),
                        0, 0, 0, 0, 0.0);

                BlockPos.Mutable blockPos = new BlockPos.Mutable();
                blockPos.setPos(planeEntity.getPosXRandom(3.0), planeEntity.getPosY(), planeEntity.getPosZRandom(3.0));
                for (int j = 0; j < 3; ++j) {
                    BlockState blockState = planeEntity.world.getBlockState(blockPos);
                    Block block = blockState.getBlock();
                    if (block instanceof IGrowable) {
                        ((IGrowable) block).grow((ServerWorld) planeEntity.world, planeEntity.world.rand, blockPos, blockState);
                        break;
                    } else {
                        blockPos.move(Direction.DOWN);
                    }
                }

                if (effect != null) {
                    for (LivingEntity entity : planeEntity.world.getEntitiesWithinAABB(LivingEntity.class, AFFECT_ENTITIES.offset(planeEntity.getPositionVec()))) {
                        entity.addPotionEffect(new EffectInstance(effect, 100));
                    }
                }
            }
        }

        return false;
    }

    @Override
    public boolean onItemRightClick(PlayerInteractEvent.RightClickItem event) {
        ItemStack itemStack = event.getPlayer().getHeldItem(event.getHand());
        if (itemStack.getItem() == Items.POTION && fluid < 20) {
            planeEntity.upgradeChanged();
            fluid = 60;
            List<EffectInstance> effectInstances = PotionUtils.getEffectsFromStack(itemStack);
            if (effectInstances.size() == 0) {
                effect = null;
            } else {
                effect = effectInstances.get(0).getPotion();
            }
            if (!event.getPlayer().isCreative()) {
                event.getPlayer().setHeldItem(event.getHand(), new ItemStack(Items.GLASS_BOTTLE));
            }
        }

        return false;
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight, float partialticks) {
        IVertexBuilder ivertexbuilder = buffer.getBuffer(SprayerModel.INSTANCE.getRenderType(TEXTURE));
        if (planeEntity.isLarge()) {
            LargeSprayerModel.INSTANCE.render(matrixStack, ivertexbuilder, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        } else {
            SprayerModel.INSTANCE.render(matrixStack, ivertexbuilder, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}
