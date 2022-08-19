package xyz.przemyk.simpleplanes.upgrades.shooter;

import com.mojang.math.Vector3f;
import net.minecraft.Util;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

public class ShooterUpgrade extends Upgrade {

    private boolean shootSide = false;

    public ShooterUpgrade(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.SHOOTER.get(), planeEntity);
    }

    @Override
    public void onItemRightClick(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getEntity();
        ItemStack itemStack = player.getItemInHand(event.getHand());
        Vector3f motion1 = planeEntity.transformPos(new Vector3f(0, 0, (float) (1 + planeEntity.getDeltaMovement().length())));
        Vec3 motion = new Vec3(motion1);
        Level world = event.getLevel();
        RandomSource random = world.random;

        Vector3f pos = planeEntity.transformPos(new Vector3f(shootSide ? 0.8f : -0.8f, 0.8f, 0.8f));
        shootSide = !shootSide;
        updateClient();

        double x = pos.x() + planeEntity.getX();
        double y = pos.y() + planeEntity.getY();
        double z = pos.z() + planeEntity.getZ();

        Item item = itemStack.getItem();

        if (item == Items.FIREWORK_ROCKET) {
            FireworkRocketEntity fireworkrocketentity = new FireworkRocketEntity(world, itemStack, x, y, z, true);
            fireworkrocketentity.shoot(-motion.x, -motion.y, -motion.z, -(float) Math.max(0.5F, motion.length() * 1.5), 1.0F);
            world.addFreshEntity(fireworkrocketentity);
            if (!player.isCreative()) {
                itemStack.shrink(1);
            }
        } else if (item == Items.FIRE_CHARGE) {
            double d3 = random.nextGaussian() * 0.05D + 2 * motion.x;
            double d4 = random.nextGaussian() * 0.05D;
            double d5 = random.nextGaussian() * 0.05D + 2 * motion.z;
            Fireball fireBallEntity = Util
                .make(new SmallFireball(world, player, d3, d4, d5), (p_229425_1_) -> p_229425_1_.setItem(itemStack));
            fireBallEntity.setPos(x, y, z);
            fireBallEntity.setDeltaMovement(motion.scale(2));
            world.addFreshEntity(fireBallEntity);
            if (!player.isCreative()) {
                itemStack.shrink(1);
            }
        } else if (item == Items.ARROW) {
            Arrow arrowEntity = new Arrow(world, x, y, z);
            arrowEntity.setOwner(player);
            arrowEntity.setDeltaMovement(motion.scale(Math.max(motion.length() * 1.5, 3) / motion.length()));
            if (!player.isCreative()) {
                itemStack.shrink(1);
                arrowEntity.pickup = AbstractArrow.Pickup.ALLOWED;
            }
            world.addFreshEntity(arrowEntity);
        } else if (item == Items.TIPPED_ARROW) {
            Arrow arrowEntity = new Arrow(world, x, y, z);
            arrowEntity.setOwner(player);
            arrowEntity.setEffectsFromItem(itemStack);
            arrowEntity.setDeltaMovement(motion.scale(Math.max(motion.length() * 1.5, 3) / motion.length()));
            if (!player.isCreative()) {
                itemStack.shrink(1);
                arrowEntity.pickup = AbstractArrow.Pickup.ALLOWED;
            }
            world.addFreshEntity(arrowEntity);
        } else if (item == Items.SPECTRAL_ARROW) {
            SpectralArrow arrowEntity = new SpectralArrow(world, x, y, z);
            arrowEntity.setOwner(player);
            arrowEntity.setDeltaMovement(motion.scale(Math.max(motion.length() * 1.5, 3) / motion.length()));
            if (!player.isCreative()) {
                itemStack.shrink(1);
                arrowEntity.pickup = AbstractArrow.Pickup.ALLOWED;
            }
            world.addFreshEntity(arrowEntity);
        }
    }

    @Override
    public void writePacket(FriendlyByteBuf buffer) {
        buffer.writeBoolean(shootSide);
    }

    @Override
    public void readPacket(FriendlyByteBuf buffer) {
        shootSide = buffer.readBoolean();
    }

    @Override
    public void onRemoved() {
        planeEntity.spawnAtLocation(Items.DISPENSER);
    }
}
