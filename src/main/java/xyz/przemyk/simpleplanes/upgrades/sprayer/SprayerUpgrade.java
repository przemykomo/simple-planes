package xyz.przemyk.simpleplanes.upgrades.sprayer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.*;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.PotionUtils;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.HelicopterEntity;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

import java.util.List;

public class SprayerUpgrade extends Upgrade {
    public static final ResourceLocation TEXTURE = new ResourceLocation(SimplePlanesMod.MODID, "textures/plane_upgrades/sprayer.png");
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
            if (planeEntity.world.isRemote && effect != null) {
                int l1 = this.effect.getLiquidColor();
                int i2 = l1 >> 16 & 255;
                int j2 = l1 >> 8 & 255;
                int j1 = l1 & 255;
                planeEntity.world.addOptionalParticle(ParticleTypes.ENTITY_EFFECT,
                    planeEntity.getPosX() - MathHelper.sin((planeEntity.rotationYaw - 50) * ((float) Math.PI / 180F)),
                    planeEntity.getPosY() + 0.5,
                    planeEntity.getPosZ() + MathHelper.cos((planeEntity.rotationYaw - 50) * ((float) Math.PI / 180F)),
                    ((float) i2 / 255.0F), ((float) j2 / 255.0F), (float) j1 / 255.0F);
                planeEntity.world.addOptionalParticle(ParticleTypes.ENTITY_EFFECT,
                    planeEntity.getPosX() - MathHelper.sin((planeEntity.rotationYaw + 50) * ((float) Math.PI / 180F)),
                    planeEntity.getPosY() + 0.5,
                    planeEntity.getPosZ() + MathHelper.cos((planeEntity.rotationYaw + 50) * ((float) Math.PI / 180F)),
                    ((float) i2 / 255.0F), ((float) j2 / 255.0F), (float) j1 / 255.0F);
            }

            if (!planeEntity.world.isRemote()) {
                BlockPos.Mutable blockPos = new BlockPos.Mutable();
                double range = fluid % 4;
                fire_fight(blockPos, range);

                if (planeEntity.ticksExisted % 5 == 0) {
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

                    blockPos.setPos(planeEntity.getPosXRandom(3.0), Math.min(255, planeEntity.getPosY() + 2), planeEntity.getPosZRandom(3.0));
                    for (int j = 0; j < 6; ++j) {
                        BlockState blockState = planeEntity.world.getBlockState(blockPos);
                        extinguishFires(blockPos);
                        Block block = blockState.getBlock();
                        if (block instanceof IGrowable) {
                            ((IGrowable) block).grow((ServerWorld) planeEntity.world, planeEntity.world.rand, blockPos, blockState);
                            break;
                        }
                        blockPos.move(Direction.DOWN);
                    }

                    if (effect != null) {
                        for (LivingEntity entity : planeEntity.world
                            .getEntitiesWithinAABB(LivingEntity.class, AFFECT_ENTITIES.offset(planeEntity.getPositionVec()))) {
                            entity.addPotionEffect(new EffectInstance(effect, 100));
                        }
                    }
                }
            }
        }

        return false;
    }

    private void fire_fight(BlockPos.Mutable blockPos, double range) {
        int i1 = this.effect == Effects.FIRE_RESISTANCE ? 10 : 3;
        for (int i = 0; i < i1; ++i) {
            blockPos.setPos(planeEntity.getPosXRandom(range), Math.min(255, planeEntity.getPosY() + 2), planeEntity.getPosZRandom(range));
            for (int j = 0; j < 6; ++j) {
                BlockState blockState = planeEntity.world.getBlockState(blockPos);
                extinguishFires(blockPos);
                if (blockState.isSolid()) {
                    break;
                }
                blockPos.move(Direction.DOWN);
            }
        }
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
        if (!planeEntity.isLarge() || planeEntity instanceof HelicopterEntity) {
            SprayerModel.INSTANCE.render(matrixStack, ivertexbuilder, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        } else {
            LargeSprayerModel.INSTANCE.render(matrixStack, ivertexbuilder, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    private void extinguishFires(BlockPos pos) {
        BlockState blockstate = planeEntity.world.getBlockState(pos);
        Block block = blockstate.getBlock();
        if (block == Blocks.FIRE) {
            planeEntity.world.removeBlock(pos, false);
        } else if (block == Blocks.CAMPFIRE && blockstate.get(CampfireBlock.LIT)) {
            planeEntity.world.playEvent((PlayerEntity)null, 1009, pos, 0);
            planeEntity.world.setBlockState(pos, blockstate.with(CampfireBlock.LIT, Boolean.FALSE));
        }

    }

}
