package xyz.przemyk.simpleplanes.upgrades.rocket;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import xyz.przemyk.simpleplanes.math.MathUtil;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.HelicopterEntity;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.math.Vector3f;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

import static net.minecraft.init.Items.GUNPOWDER;

public class RocketUpgrade extends Upgrade {
    public static final ResourceLocation TEXTURE = new ResourceLocation(SimplePlanesMod.MODID, "textures/plane_upgrades/rocket.png");
    public static int FUEL_PER_GUNPOWDER = 20;

    public int fuel = 0;

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound compoundNBT = new NBTTagCompound();
        compoundNBT.setInteger("fuel", fuel);
        return compoundNBT;
    }

    @Override
    public void deserializeNBT(NBTTagCompound compoundNBT) {
        fuel = compoundNBT.getInteger("fuel");
    }

    public RocketUpgrade(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.BOOSTER, planeEntity);
    }

    @Override
    public boolean tick() {
        push();
        return super.tick();
    }

    @Override
    public boolean onItemRightClick(EntityPlayer player, World world, EnumHand hand, ItemStack itemStack) {
        if (fuel <= 0) {
            if (itemStack.getItem().equals(GUNPOWDER)) {
                if (!player.isCreative()) {
                    itemStack.shrink(1);
                }
                fuel = FUEL_PER_GUNPOWDER;
                planeEntity.addFuelMaxed(FUEL_PER_GUNPOWDER * 2);
            }
        }
        push();
        return false;
    }

    private void push() {
        if (!planeEntity.world.isRemote) {
            planeEntity.spawnParticle(EnumParticleTypes.FLAME, new Vector3f(-0.6f, 0f, -1.3f), 5);
            planeEntity.spawnParticle(EnumParticleTypes.FLAME, new Vector3f(0.6f, 0f, -1.3f), 5);
        }
        if (fuel < 0) {
            return;
        }

        fuel -= 1;

        Vec3d m = planeEntity.getMotion();
        float pitch = 0;
        EntityPlayer player = planeEntity.getPlayer();
        if (player != null) {
            if (player.moveForward > 0.0F) {
                if (planeEntity.isSprinting()) {
                    pitch += 2;
                }
            } else if (player.moveForward < 0.0F) {
                pitch -= 2;
            }
        }
        if (planeEntity.world.rand.nextInt(50) == 0) {
            planeEntity.attackEntityFrom(DamageSource.ON_FIRE, 1);
        }
        if (planeEntity instanceof HelicopterEntity) {
            pitch = 0;
        }
        planeEntity.rotationPitch += pitch;
        Vec3d motion = MathUtil.rotationToVector(planeEntity.rotationYaw, planeEntity.rotationPitch, 0.05);

        planeEntity.setMotion(m.add(motion));
        if (!planeEntity.world.isRemote) {
            planeEntity.spawnParticle(EnumParticleTypes.FLAME, new Vector3f(-0.6f, 0f, -1.3f), 5);
            planeEntity.spawnParticle(EnumParticleTypes.FLAME, new Vector3f(0.6f, 0f, -1.3f), 5);

        }
    }

    @Override
    public void render(float partialticks, float scale) {
        RocketModel.INSTANCE.render(planeEntity, partialticks, 0, 0, 0, 0, scale);
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }
}
