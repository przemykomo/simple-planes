package xyz.przemyk.simpleplanes.compat;

import com.mrcrayfish.guns.Config;
import com.mrcrayfish.guns.common.Gun;
import com.mrcrayfish.guns.common.ProjectileManager;
import com.mrcrayfish.guns.entity.ProjectileEntity;
import com.mrcrayfish.guns.event.GunProjectileHitEvent;
import com.mrcrayfish.guns.init.ModItems;
import com.mrcrayfish.guns.interfaces.IProjectileFactory;
import com.mrcrayfish.guns.item.GunItem;
import com.mrcrayfish.guns.item.IAmmo;
import com.mrcrayfish.guns.network.PacketHandler;
import com.mrcrayfish.guns.network.message.S2CMessageGunSound;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

public class MrCrayfishGunCompat {
    @SubscribeEvent
    public static void onGunProjectileHit(GunProjectileHitEvent event) {
        HitResult result = event.getRayTrace();
        if (result instanceof EntityHitResult entityHitResult) {
            Entity entity = entityHitResult.getEntity();
            if (entity instanceof PlaneEntity planeEntity) {
                if (planeEntity.getPassengers().contains(event.getProjectile().getShooter())) {
                    event.setCanceled(true);
                }
            }
        }
    }

    public static final ItemStack minigunDummy = ModItems.MINI_GUN.get().getDefaultInstance();
    public static final ItemStack shotgunDummy = ModItems.SHOTGUN.get().getDefaultInstance();
    public static final ItemStack heavyRifleDummy = ModItems.HEAVY_RIFLE.get().getDefaultInstance();
    public static final ItemStack grenadeLauncherDummy = ModItems.GRENADE_LAUNCHER.get().getDefaultInstance();
    public static final ItemStack bazookaDummy = ModItems.BAZOOKA.get().getDefaultInstance();

    public static void shooterBehaviour(Item ammoItem, ItemStackHandler itemStackHandler, Level level, Player player, Vec3 motion, double x, double y, double z) {
        if (ammoItem instanceof IAmmo) {
            ItemStack gunDummy;
            if (ModItems.BASIC_BULLET.get() == ammoItem) {
                gunDummy = minigunDummy;
            } else if (ModItems.SHELL.get() == ammoItem) {
                gunDummy = shotgunDummy;
            } else if (ModItems.ADVANCED_AMMO.get() == ammoItem) {
                gunDummy = heavyRifleDummy;
            } else if (ModItems.GRENADE.get() == ammoItem) {
                gunDummy = grenadeLauncherDummy;
            } else if (ModItems.MISSILE.get() == ammoItem) {
                gunDummy = bazookaDummy;
            } else {
                return;
            }

            GunItem gunItem = (GunItem) gunDummy.getItem();
            Gun gun = gunItem.getGun();
            IProjectileFactory projectileFactory = ProjectileManager.getInstance().getFactory(ForgeRegistries.ITEMS.getKey(ammoItem));
            ProjectileEntity projectile = projectileFactory.create(level, player, gunDummy, gunItem, gun);
            projectile.setWeapon(gunDummy);
            projectile.setPos(x, y, z);
            double speed = gun.getProjectile().getSpeed();
            projectile.setDeltaMovement(motion.x * speed, motion.y * speed, motion.z * speed);
            level.addFreshEntity(projectile);

            if (!level.isClientSide) {
                ResourceLocation fireSound = gun.getSounds().getFire();
                if (fireSound != null) {
                    double posX = player.getX();
                    double posY = player.getY();
                    double posZ = player.getZ();
                    float volume = 1.0f;
                    float pitch = 0.9F + level.random.nextFloat() * 0.2F;
                    double radius = Config.SERVER.gunShotMaxDistance.get();
                    boolean muzzle = gun.getDisplay().getFlash() != null;
                    S2CMessageGunSound messageSound = new S2CMessageGunSound(fireSound, SoundSource.PLAYERS, (float) posX, (float) posY, (float) posZ, volume, pitch, player.getId(), muzzle, false);
                    PacketDistributor.TargetPoint targetPoint = new PacketDistributor.TargetPoint(posX, posY, posZ, radius, player.level.dimension());
                    PacketHandler.getPlayChannel().send(PacketDistributor.NEAR.with(() -> targetPoint), messageSound);
                }
            }

            if (!player.isCreative()) {
                itemStackHandler.extractItem(0, 1, false);
            }
        }
    }
}
