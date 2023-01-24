package xyz.przemyk.simpleplanes.upgrades.shooter;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.RandomSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import xyz.przemyk.simpleplanes.client.gui.PlaneInventoryScreen;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesItems;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

import java.util.function.Function;

public class ShooterUpgrade extends Upgrade {

    public final SimpleContainer itemStackHandler = new SimpleContainer(1);

    public ShooterUpgrade(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.SHOOTER, planeEntity);
    }

    public void use(Player player) {
        Vector3f motion1 = planeEntity.transformPos(new Vector3f(0, -0.25f, (float) (1 + planeEntity.getDeltaMovement().length())));
        Vec3 motion = new Vec3(motion1);
        Level level = player.level;
        RandomSource random = level.random;

        Vector3f pos = planeEntity.transformPos(new Vector3f(0.0f, 1.8f, 2.0f));
        updateClient();

        double x = pos.x() + planeEntity.getX();
        double y = pos.y() + planeEntity.getY();
        double z = pos.z() + planeEntity.getZ();

        ItemStack itemStack = itemStackHandler.getItem(0);
        Item item = itemStack.getItem();

        if (item == Items.FIREWORK_ROCKET) {
            FireworkRocketEntity fireworkrocketentity = new FireworkRocketEntity(level, itemStack, x, y, z, true);
            fireworkrocketentity.shoot(-motion.x, -motion.y, -motion.z, -(float) Math.max(0.5F, motion.length() * 1.5), 1.0F);
            level.addFreshEntity(fireworkrocketentity);
            if (!player.isCreative()) {
                itemStackHandler.removeItem(0, 1);
            }
        } else if (item == Items.FIRE_CHARGE) {
            double d3 = random.nextGaussian() * 0.05D + 2 * motion.x;
            double d4 = random.nextGaussian() * 0.05D;
            double d5 = random.nextGaussian() * 0.05D + 2 * motion.z;
            Fireball fireBallEntity = Util
                .make(new SmallFireball(level, player, d3, d4, d5), (p_229425_1_) -> p_229425_1_.setItem(itemStack));
            fireBallEntity.setPos(x, y, z);
            fireBallEntity.setDeltaMovement(motion.scale(2));
            level.addFreshEntity(fireBallEntity);
            if (!player.isCreative()) {
                itemStackHandler.removeItem(0, 1);
            }
        } else if (item == Items.ARROW) {
            Arrow arrowEntity = new Arrow(level, x, y, z);
            arrowEntity.setOwner(player);
            arrowEntity.setDeltaMovement(motion.scale(Math.max(motion.length() * 1.5, 3) / motion.length()));
            if (!player.isCreative()) {
                itemStackHandler.removeItem(0, 1);
                arrowEntity.pickup = AbstractArrow.Pickup.ALLOWED;
            }
            level.addFreshEntity(arrowEntity);
        } else if (item == Items.TIPPED_ARROW) {
            Arrow arrowEntity = new Arrow(level, x, y, z);
            arrowEntity.setOwner(player);
            arrowEntity.setEffectsFromItem(itemStack);
            arrowEntity.setDeltaMovement(motion.scale(Math.max(motion.length() * 1.5, 3) / motion.length()));
            if (!player.isCreative()) {
                itemStackHandler.removeItem(0, 1);
                arrowEntity.pickup = AbstractArrow.Pickup.ALLOWED;
            }
            level.addFreshEntity(arrowEntity);
        } else if (item == Items.SPECTRAL_ARROW) {
            SpectralArrow arrowEntity = new SpectralArrow(level, x, y, z);
            arrowEntity.setOwner(player);
            arrowEntity.setDeltaMovement(motion.scale(Math.max(motion.length() * 1.5, 3) / motion.length()));
            if (!player.isCreative()) {
                itemStackHandler.removeItem(0, 1);
                arrowEntity.pickup = AbstractArrow.Pickup.ALLOWED;
            }
            level.addFreshEntity(arrowEntity);
        }/* else {
            ModList.get().getModContainerById("cgm").ifPresent(cgm -> MrCrayfishGunCompat.shooterBehaviour(item, itemStackHandler, level, player, motion, x, y, z));
        }*/
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.put("item", itemStackHandler.getItem(0).save(new CompoundTag()));
        return compoundTag;
    }

    @Override
    public void deserializeNBT(CompoundTag compoundTag) {
        itemStackHandler.setItem(0, ItemStack.of(compoundTag.getCompound("item")));
    }

    @Override
    public void writePacket(FriendlyByteBuf buffer) {
        buffer.writeItem(itemStackHandler.getItem(0));
    }

    @Override
    public void readPacket(FriendlyByteBuf buffer) {
        itemStackHandler.setItem(0, buffer.readItem());
    }

    @Override
    public void onRemoved() {
        planeEntity.spawnAtLocation(SimplePlanesItems.SHOOTER);
        planeEntity.spawnAtLocation(itemStackHandler.getItem(0));
    }

    @Override
    public void addContainerData(Function<Slot, Slot> addSlot, Function<DataSlot, DataSlot> addDataSlot) {
        addSlot.apply(new Slot(itemStackHandler, 0, 134, 62));
    }

    @Override
    public void renderScreenBg(PoseStack poseStack, int x, int y, float partialTicks, PlaneInventoryScreen screen) {
        screen.blit(poseStack, screen.getGuiLeft() + 133, screen.getGuiTop() + 61, 226, 0, 18, 18);
    }
}
