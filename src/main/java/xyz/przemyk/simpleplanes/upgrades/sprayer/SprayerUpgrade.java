package xyz.przemyk.simpleplanes.upgrades.sprayer;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
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
        super(SimplePlanesUpgrades.SPRAYER, planeEntity);
    }

    private int fluid = 0;
    private Potion effect = null;

    @SuppressWarnings("ConstantConditions")
    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound compoundNBT = new NBTTagCompound();
        compoundNBT.setInteger("fluid", fluid);
        String value = (effect == null) ? "empty" : effect.getRegistryName().toString();
        compoundNBT.setString("effect", value);
        return compoundNBT;
    }

    @Override
    public void deserializeNBT(NBTTagCompound compoundNBT) {
        fluid = compoundNBT.getInteger("fluid");
        String effectName = compoundNBT.getString("effect");
        if (effectName.equals("empty")) {
            effect = null;
        } else {
            effect = ForgeRegistries.POTIONS.getValue(new ResourceLocation(effectName));
        }
    }

    @Override
    public NBTTagCompound serializeNBTData() {
        return serializeNBT();
    }

    @Override
    public void deserializeNBTData(NBTTagCompound nbt) {
        deserializeNBT(nbt);
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
                planeEntity.world.spawnParticle(EnumParticleTypes.SPELL,
                    planeEntity.getPosX() - MathHelper.sin((planeEntity.rotationYaw - 50) * ((float) Math.PI / 180F)),
                    planeEntity.getPosY() + 0.5,
                    planeEntity.getPosZ() + MathHelper.cos((planeEntity.rotationYaw - 50) * ((float) Math.PI / 180F)),
                    ((float) i2 / 255.0F), ((float) j2 / 255.0F), (float) j1 / 255.0F);
                planeEntity.world.spawnParticle(EnumParticleTypes.SPELL,
                    planeEntity.getPosX() - MathHelper.sin((planeEntity.rotationYaw + 50) * ((float) Math.PI / 180F)),
                    planeEntity.getPosY() + 0.5,
                    planeEntity.getPosZ() + MathHelper.cos((planeEntity.rotationYaw + 50) * ((float) Math.PI / 180F)),
                    ((float) i2 / 255.0F), ((float) j2 / 255.0F), (float) j1 / 255.0F);
            }

            if (!planeEntity.world.isRemote) {
                BlockPos.MutableBlockPos blockPos = new BlockPos.MutableBlockPos();
                double range = fluid % 4;
                fire_fight(blockPos, range);

                if (planeEntity.ticksExisted % 5 == 0) {
                    (planeEntity.world).spawnParticle(EnumParticleTypes.CLOUD,
                        planeEntity.getPosX() - MathHelper.sin((planeEntity.rotationYaw - 50) * ((float) Math.PI / 180F)),
                        planeEntity.getPosY() + 0.5,
                        planeEntity.getPosZ() + MathHelper.cos((planeEntity.rotationYaw - 50) * ((float) Math.PI / 180F)),
                        0, 0, 0.0f);

                    (planeEntity.world).spawnParticle(EnumParticleTypes.CLOUD,
                        planeEntity.getPosX() - MathHelper.sin((planeEntity.rotationYaw + 50) * ((float) Math.PI / 180F)),
                        planeEntity.getPosY() + 0.5,
                        planeEntity.getPosZ() + MathHelper.cos((planeEntity.rotationYaw + 50) * ((float) Math.PI / 180F)),
                        0, 0, 0.0f);

                    (planeEntity.world).spawnParticle(EnumParticleTypes.CLOUD,
                        planeEntity.getPosX() - 2 * MathHelper.sin((planeEntity.rotationYaw - 80) * ((float) Math.PI / 180F)),
                        planeEntity.getPosY() + 0.5,
                        planeEntity.getPosZ() + 2 * MathHelper.cos((planeEntity.rotationYaw - 80) * ((float) Math.PI / 180F)),
                        0, 0, 0.0f);

                    (planeEntity.world).spawnParticle(EnumParticleTypes.CLOUD,
                        planeEntity.getPosX() - 2 * MathHelper.sin((planeEntity.rotationYaw + 80) * ((float) Math.PI / 180F)),
                        planeEntity.getPosY() + 0.5,
                        planeEntity.getPosZ() + 2 * MathHelper.cos((planeEntity.rotationYaw + 80) * ((float) Math.PI / 180F)),
                        0, 0, 0.0f);

                    blockPos.setPos(planeEntity.getPosX(), Math.min(255, planeEntity.getPosY() + 2), planeEntity.getPosZ());
                    blockPos.add(Math.random() * 3, 0, Math.random() * 3);
                    for (int j = 0; j < 6; ++j) {
                        IBlockState blockState = planeEntity.world.getBlockState(blockPos);
                        extinguishFires(blockPos);
                        Block block = blockState.getBlock();
                        if (block instanceof IGrowable) {
                            ((IGrowable) block).grow(planeEntity.world, planeEntity.world.rand, blockPos, blockState);
                            break;
                        }
                        blockPos.move(EnumFacing.DOWN);
                    }

                    if (effect != null) {
                        for (EntityLiving entity : planeEntity.world
                            .getEntitiesWithinAABB(EntityLiving.class, AFFECT_ENTITIES.offset(planeEntity.getPositionVector()))) {
                            entity.addPotionEffect(new PotionEffect(effect, 100));
                        }
                    }
                }
            }
        }

        return false;
    }

    private void fire_fight(BlockPos.MutableBlockPos blockPos, double range) {
        int i1 = this.effect == MobEffects.FIRE_RESISTANCE ? 10 : 3;
        for (int i = 0; i < i1; ++i) {
            blockPos.setPos(planeEntity.getPosX(), Math.min(255, planeEntity.getPosY() + 2), planeEntity.getPosZ());
            blockPos.add(Math.random() * 3, 0, Math.random() * 3);
            for (int j = 0; j < 6; ++j) {
                IBlockState blockState = planeEntity.world.getBlockState(blockPos);
                extinguishFires(blockPos);
                if (!blockState.isTranslucent()) {
                    break;
                }
                blockPos.move(EnumFacing.DOWN);
            }
        }
    }

    @Override
    public boolean onItemRightClick(EntityPlayer player, World world, EnumHand hand, ItemStack itemStack) {
        if (itemStack.getItem() == Items.POTIONITEM && fluid < 20) {
            planeEntity.upgradeChanged();
            fluid = 60;
            List<PotionEffect> effectInstances = PotionUtils.getEffectsFromStack(itemStack);
            if (effectInstances.size() == 0) {
                effect = null;
            } else {
                effect = effectInstances.get(0).getPotion();
            }
            if (!player.isCreative()) {
                player.setHeldItem(hand, new ItemStack(Items.GLASS_BOTTLE));
            }
        }

        return false;
    }

    @Override
    public void render(float partialticks, float scale) {
        if (!planeEntity.isLarge() || planeEntity instanceof HelicopterEntity) {
            SprayerModel.INSTANCE.render(planeEntity,1,1,1,1,1,scale);
        } else {
            LargeSprayerModel.INSTANCE.render(planeEntity,1,1,1,1,1,scale);
        }
    }

    private void extinguishFires(BlockPos pos) {
        IBlockState blockstate = planeEntity.world.getBlockState(pos);
        if (blockstate.getBlock() == Blocks.FIRE) {
            planeEntity.world.setBlockToAir(pos);
        }
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }
}
