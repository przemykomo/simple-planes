package xyz.przemyk.simpleplanes.upgrades.sprayer;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.Fertilizable;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.potion.PotionUtil;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.HelicopterEntity;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

import java.util.List;

public class SprayerUpgrade extends Upgrade {
    public static final Identifier TEXTURE = new Identifier(SimplePlanesMod.MODID, "textures/plane_upgrades/sprayer.png");
    public static final Box AFFECT_ENTITIES = new Box(-3, -3, -3, 3, 0, 3);

    public SprayerUpgrade(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.SPRAYER, planeEntity);
    }

    private int fluid = 0;
    private StatusEffect effect = null;

    @SuppressWarnings("ConstantConditions")
    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compoundNBT = new CompoundTag();
        compoundNBT.putInt("fluid", fluid);
        compoundNBT.putString("effect", effect == null ? "empty" : Registry.STATUS_EFFECT.getId(effect).toString());
        return compoundNBT;
    }

    @Override
    public void deserializeNBT(CompoundTag compoundNBT) {
        fluid = compoundNBT.getInt("fluid");
        String effectName = compoundNBT.getString("effect");
        if (effectName.equals("empty")) {
            effect = null;
        } else {
            effect = Registry.STATUS_EFFECT.get(new Identifier(effectName));
        }
    }

    @Override
    public CompoundTag serializeNBTData() {
        return serializeNBT();
    }

    @Override
    public void deserializeNBTData(CompoundTag nbt) {
        deserializeNBT(nbt);
    }

    @Override
    public boolean tick() {
        if (fluid > 0) {
            --fluid;
            if (planeEntity.world.isClient && effect != null) {
                int l1 = this.effect.getColor();
                int i2 = l1 >> 16 & 255;
                int j2 = l1 >> 8 & 255;
                int j1 = l1 & 255;
                planeEntity.world.addImportantParticle(ParticleTypes.ENTITY_EFFECT,
                    planeEntity.getX() - MathHelper.sin((planeEntity.yaw - 50) * ((float) Math.PI / 180F)),
                    planeEntity.getY() + 0.5,
                    planeEntity.getZ() + MathHelper.cos((planeEntity.yaw - 50) * ((float) Math.PI / 180F)),
                    ((float) i2 / 255.0F), ((float) j2 / 255.0F), (float) j1 / 255.0F);
                planeEntity.world.addImportantParticle(ParticleTypes.ENTITY_EFFECT,
                    planeEntity.getX() - MathHelper.sin((planeEntity.yaw + 50) * ((float) Math.PI / 180F)),
                    planeEntity.getY() + 0.5,
                    planeEntity.getZ() + MathHelper.cos((planeEntity.yaw + 50) * ((float) Math.PI / 180F)),
                    ((float) i2 / 255.0F), ((float) j2 / 255.0F), (float) j1 / 255.0F);
            }

            if (!planeEntity.world.isClient()) {
                BlockPos.Mutable blockPos = new BlockPos.Mutable();
                double range = fluid % 4;
                fire_fight(blockPos, range);

                if (planeEntity.age % 5 == 0) {
                    ((ServerWorld) planeEntity.world).spawnParticles(ParticleTypes.CLOUD,
                        planeEntity.getX() - MathHelper.sin((planeEntity.yaw - 50) * ((float) Math.PI / 180F)),
                        planeEntity.getY() + 0.5,
                        planeEntity.getZ() + MathHelper.cos((planeEntity.yaw - 50) * ((float) Math.PI / 180F)),
                        0, 0, 0, 0, 0.0);

                    ((ServerWorld) planeEntity.world).spawnParticles(ParticleTypes.CLOUD,
                        planeEntity.getX() - MathHelper.sin((planeEntity.yaw + 50) * ((float) Math.PI / 180F)),
                        planeEntity.getY() + 0.5,
                        planeEntity.getZ() + MathHelper.cos((planeEntity.yaw + 50) * ((float) Math.PI / 180F)),
                        0, 0, 0, 0, 0.0);

                    ((ServerWorld) planeEntity.world).spawnParticles(ParticleTypes.CLOUD,
                        planeEntity.getX() - 2 * MathHelper.sin((planeEntity.yaw - 80) * ((float) Math.PI / 180F)),
                        planeEntity.getY() + 0.5,
                        planeEntity.getZ() + 2 * MathHelper.cos((planeEntity.yaw - 80) * ((float) Math.PI / 180F)),
                        0, 0, 0, 0, 0.0);

                    ((ServerWorld) planeEntity.world).spawnParticles(ParticleTypes.CLOUD,
                        planeEntity.getX() - 2 * MathHelper.sin((planeEntity.yaw + 80) * ((float) Math.PI / 180F)),
                        planeEntity.getY() + 0.5,
                        planeEntity.getZ() + 2 * MathHelper.cos((planeEntity.yaw + 80) * ((float) Math.PI / 180F)),
                        0, 0, 0, 0, 0.0);

                    blockPos.set(planeEntity.getParticleX(3.0), Math.min(255, planeEntity.getY() + 2), planeEntity.getParticleZ(3.0));
                    for (int j = 0; j < 6; ++j) {
                        BlockState blockState = planeEntity.world.getBlockState(blockPos);
                        extinguishFires(blockPos);
                        Block block = blockState.getBlock();
                        if (block instanceof Fertilizable) {
                            ((Fertilizable) block).grow((ServerWorld) planeEntity.world, planeEntity.world.random, blockPos, blockState);
                            break;
                        }
                        blockPos.move(Direction.DOWN);
                    }

                    if (effect != null) {
                        for (LivingEntity entity : planeEntity.world
                            .getNonSpectatingEntities(LivingEntity.class, AFFECT_ENTITIES.offset(planeEntity.getPos()))) {
                            entity.addStatusEffect(new StatusEffectInstance(effect, 100));
                        }
                    }
                }
            }
        }

        return false;
    }

    private void fire_fight(BlockPos.Mutable blockPos, double range) {
        int i1 = this.effect == StatusEffects.FIRE_RESISTANCE ? 10 : 3;
        for (int i = 0; i < i1; ++i) {
            blockPos.set(planeEntity.getParticleX(range), Math.min(255, planeEntity.getY() + 2), planeEntity.getParticleZ(range));
            for (int j = 0; j < 6; ++j) {
                BlockState blockState = planeEntity.world.getBlockState(blockPos);
                extinguishFires(blockPos);
                if (blockState.isOpaque()) {
                    break;
                }
                blockPos.move(Direction.DOWN);
            }
        }
    }

    @Override
    public boolean onItemRightClick(PlayerEntity player, World world, Hand hand, ItemStack itemStack) {
        if (itemStack.getItem() == Items.POTION && fluid < 20) {
            planeEntity.upgradeChanged();
            fluid = 60;
            List<StatusEffectInstance> effectInstances = PotionUtil.getPotionEffects(itemStack);
            if (effectInstances.size() == 0) {
                effect = null;
            } else {
                effect = effectInstances.get(0).getEffectType();
            }
            if (!player.isCreative()) {
                player.setStackInHand(hand, new ItemStack(Items.GLASS_BOTTLE));
            }
        }

        return false;
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider buffer, int packedLight, float partialticks) {
        VertexConsumer ivertexbuilder = buffer.getBuffer(SprayerModel.INSTANCE.getLayer(TEXTURE));
        if (!planeEntity.isLarge() || planeEntity instanceof HelicopterEntity) {
            SprayerModel.INSTANCE.render(matrixStack, ivertexbuilder, packedLight, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
        } else {
            LargeSprayerModel.INSTANCE.render(matrixStack, ivertexbuilder, packedLight, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    private void extinguishFires(BlockPos pos) {
        BlockState blockstate = planeEntity.world.getBlockState(pos);
        if (blockstate.isIn(BlockTags.FIRE)) {
            planeEntity.world.removeBlock(pos, false);
        } else if (CampfireBlock.isLitCampfire(blockstate)) {
            planeEntity.world.syncWorldEvent((planeEntity.getPlayer()), 1009, pos, 0);
            CampfireBlock.extinguish(planeEntity.world, pos, blockstate);
            planeEntity.world.setBlockState(pos, blockstate.with(CampfireBlock.LIT, Boolean.FALSE));
        }

    }

}
