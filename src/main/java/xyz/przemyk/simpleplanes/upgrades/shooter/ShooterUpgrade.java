package xyz.przemyk.simpleplanes.upgrades.shooter;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.entity.projectile.EntitySpectralArrow;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.math.Vector3f;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

import java.util.Random;

import static net.minecraft.init.Items.FIREWORKS;
import static net.minecraft.init.Items.FIRE_CHARGE;


public class ShooterUpgrade extends Upgrade {
    public static final ResourceLocation TEXTURE = new ResourceLocation(SimplePlanesMod.MODID, "textures/plane_upgrades/shooter.png");

    private boolean shootSide = false;

    public ShooterUpgrade(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.SHOOTER, planeEntity);
    }

    @Override
    public boolean onItemRightClick(EntityPlayer player, World world, EnumHand hand, ItemStack itemStack) {
        Vector3f motion1 = planeEntity.transformPos(new Vector3f(0, 0, (float) (1 + planeEntity.getMotion().length())));
        Vec3d motion = new Vec3d(motion1.x, motion1.y, motion1.z);
        Random random = world.rand;

        Vector3f pos = planeEntity.transformPos(new Vector3f(shootSide ? 0.8f : -0.8f, 0.8f, 0.8f));
        shootSide = !shootSide;

        double x = pos.getX() + planeEntity.getPosX();
        double y = pos.getY() + planeEntity.getPosY();
        double z = pos.getZ() + planeEntity.getPosZ();

        Item item = itemStack.getItem();

        if (item == FIREWORKS) {
            EntityFireworkRocket fireworkrocketentity = new EntityFireworkRocket(world, x, y, z, itemStack);

            setMotion(fireworkrocketentity, motion.scale(-1));

            world.spawnEntity(fireworkrocketentity);
            if (!player.isCreative()) {
                itemStack.shrink(1);
            }
        } else if (item == FIRE_CHARGE) {
            double d3 = random.nextGaussian() * 0.05D + 2 * motion.x;
            double d4 = random.nextGaussian() * 0.05D;
            double d5 = random.nextGaussian() * 0.05D + 2 * motion.z;
            EntitySmallFireball fireball = new EntitySmallFireball(world, x, y, z, d3, d4, d5);
            fireball.shootingEntity = player;
            world.spawnEntity(fireball);
            setMotion(fireball,motion);
            if (!player.isCreative()) {
                itemStack.shrink(1);
            }
        } else if (item == Items.ARROW) {
            EntityTippedArrow arrowEntity = new EntityTippedArrow(world, x, y, z);
            Vec3d m = motion.scale(Math.max(motion.length() * 1.5, 3) / motion.length());
            arrowEntity.shootingEntity = planeEntity;

            setMotion(arrowEntity, m);
            if (!player.isCreative()) {
                itemStack.shrink(1);
                arrowEntity.pickupStatus = EntityArrow.PickupStatus.ALLOWED;
            }
            world.spawnEntity(arrowEntity);
        } else if (item == Items.TIPPED_ARROW) {
            EntityTippedArrow arrowEntity = new EntityTippedArrow(world, x, y, z);
            Vec3d m = motion.scale(Math.max(motion.length() * 1.5, 3) / motion.length());
            arrowEntity.shootingEntity = planeEntity;
            arrowEntity.setPotionEffect(itemStack);

            setMotion(arrowEntity, m);
            if (!player.isCreative()) {
                itemStack.shrink(1);
                arrowEntity.pickupStatus = EntityArrow.PickupStatus.ALLOWED;
            }
            world.spawnEntity(arrowEntity);

        } else if (item == Items.SPECTRAL_ARROW) {
            EntitySpectralArrow arrowEntity = new EntitySpectralArrow(world, x, y, z);
            Vec3d m = motion.scale(Math.max(motion.length() * 1.5, 3) / motion.length());
            arrowEntity.shootingEntity = planeEntity;
            setMotion(arrowEntity, m);
            if (!player.isCreative()) {
                itemStack.shrink(1);
                arrowEntity.pickupStatus = EntityArrow.PickupStatus.ALLOWED;
            }
            world.spawnEntity(arrowEntity);
        }
        return false;
    }

    private void setMotion(Entity arrowEntity, Vec3d m) {
        arrowEntity.motionX = m.x;
        arrowEntity.motionY = m.y;
        arrowEntity.motionZ = m.z;
    }

    @Override
    public void render(float partialticks, float scale) {
        ShooterModel.INSTANCE.render(planeEntity, 1F, 1.0F, 1.0F, 1.0F, 1.0F, scale);
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }
}
