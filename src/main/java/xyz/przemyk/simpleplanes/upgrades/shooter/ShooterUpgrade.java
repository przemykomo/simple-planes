package xyz.przemyk.simpleplanes.upgrades.shooter;

import net.minecraft.Util;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.StructureTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.capabilities.BaseCapability;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.joml.Vector3f;
import xyz.przemyk.simpleplanes.client.gui.PlaneInventoryScreen;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesItems;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

import java.util.function.Function;

public class ShooterUpgrade extends Upgrade {

    public final ItemStackHandler itemStackHandler = new ItemStackHandler();

    public ShooterUpgrade(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.SHOOTER.get(), planeEntity);
    }

    public void use(Player player) {
        Vector3f motion1 = planeEntity.transformPos(new Vector3f(0, -0.25f, (float) (1 + planeEntity.getDeltaMovement().length())));
        Vec3 motion = new Vec3(motion1);
        Level level = player.level();
        RandomSource random = level.random;

        Vector3f pos = planeEntity.transformPos(new Vector3f(0.0f, 1.8f, 2.0f));
        updateClient();

        double x = pos.x() + planeEntity.getX();
        double y = pos.y() + planeEntity.getY();
        double z = pos.z() + planeEntity.getZ();

        ItemStack itemStack = itemStackHandler.getStackInSlot(0);
        Item item = itemStack.getItem();

        if (item == Items.FIREWORK_ROCKET) {
            FireworkRocketEntity fireworkrocketentity = new FireworkRocketEntity(level, itemStack, x, y, z, true);
            fireworkrocketentity.shoot(-motion.x, -motion.y, -motion.z, -(float) Math.max(0.5F, motion.length() * 1.5), 1.0F);
            level.addFreshEntity(fireworkrocketentity);
            if (!player.isCreative()) {
                itemStackHandler.extractItem(0, 1, false);
            }
        } else if (item == Items.FIRE_CHARGE) {
            double d3 = random.nextGaussian() * 0.05D + 2 * motion.x;
            double d4 = random.nextGaussian() * 0.05D;
            double d5 = random.nextGaussian() * 0.05D + 2 * motion.z;
            Fireball fireBallEntity = Util
                .make(new SmallFireball(level, player, new Vec3(d3, d4, d5)), (p_229425_1_) -> p_229425_1_.setItem(itemStack));
            fireBallEntity.setPos(x, y, z);
            fireBallEntity.setDeltaMovement(motion.scale(2));
            level.addFreshEntity(fireBallEntity);
            if (!player.isCreative()) {
                itemStackHandler.extractItem(0, 1, false);
            }
        } else if (item instanceof ArrowItem arrowItem) {
            AbstractArrow arrowEntity = arrowItem.createArrow(level, itemStack, player, null);
//            Arrow arrowEntity = new Arrow(level, x, y, z);
//            arrowEntity.setOwner(player);
            arrowEntity.setDeltaMovement(motion.scale(Math.max(motion.length() * 1.5, 3) / motion.length()));
            if (player.isCreative()) {
                arrowEntity.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
            } else {
                itemStackHandler.extractItem(0, 1, false);
            }
            level.addFreshEntity(arrowEntity);
        } else if (item == Items.ENDER_EYE && level instanceof ServerLevel serverLevel) {
            BlockPos blockpos = serverLevel.findNearestMapStructure(StructureTags.EYE_OF_ENDER_LOCATED, new BlockPos((int) x, (int) y, (int) z), 100, false);
            if (blockpos != null) {
                EyeOfEnder eyeOfEnder = new EyeOfEnder(level, x, y, z);
                eyeOfEnder.setItem(itemStack);
                eyeOfEnder.signalTo(blockpos);
                level.addFreshEntity(eyeOfEnder);
                level.playSound(null, x, y, z, SoundEvents.ENDER_EYE_LAUNCH, SoundSource.NEUTRAL, 0.5f, 0.4f / random.nextFloat() * 0.4f + 0.8f);
                if (!player.isCreative()) {
                    itemStackHandler.extractItem(0, 1, false);
                }
            }
        } /*else {
            ModList.get().getModContainerById("cgm").ifPresent(cgm -> MrCrayfishGunCompat.shooterBehaviour(item, itemStackHandler, level, player, motion, x, y, z));
        }*/
    }

    @Override
    public Tag serializeNBT() {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.put("item", itemStackHandler.serializeNBT(planeEntity.registryAccess()));
        return compoundTag;
    }

    @Override
    public void deserializeNBT(CompoundTag compoundTag) {
        itemStackHandler.deserializeNBT(planeEntity.registryAccess(), compoundTag.getCompound("item"));
    }

    @Override
    public void writePacket(RegistryFriendlyByteBuf buffer) {
        ItemStack.OPTIONAL_STREAM_CODEC.encode(buffer, itemStackHandler.getStackInSlot(0));
    }

    @Override
    public void readPacket(RegistryFriendlyByteBuf buffer) {
        itemStackHandler.setStackInSlot(0, ItemStack.OPTIONAL_STREAM_CODEC.decode(buffer));
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getCap(BaseCapability<T, ?> cap) {
        if (cap == Capabilities.ItemHandler.ENTITY) {
            return (T) itemStackHandler;
        }

        return super.getCap(cap);
    }

    @Override
    public void onRemoved() {
        planeEntity.spawnAtLocation(itemStackHandler.getStackInSlot(0));
    }

    @Override
    public ItemStack getItemStack() {
        return SimplePlanesItems.SHOOTER.get().getDefaultInstance();
    }

    @Override
    public void addContainerData(Function<Slot, Slot> addSlot, Function<DataSlot, DataSlot> addDataSlot) {
        addSlot.apply(new SlotItemHandler(itemStackHandler, 0, 134, 62));
    }

    @Override
    public void renderScreenBg(GuiGraphics guiGraphics, int x, int y, float partialTicks, PlaneInventoryScreen screen) {
        guiGraphics.blit(PlaneInventoryScreen.GUI, screen.getGuiLeft() + 133, screen.getGuiTop() + 61, 226, 0, 18, 18);
    }
}
