package xyz.przemyk.simpleplanes.upgrades;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Hand;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

public abstract class Upgrade {

    private final UpgradeType type;
    protected final PlaneEntity planeEntity;

    public PlaneEntity getPlaneEntity() {
        return planeEntity;
    }

    public Upgrade(UpgradeType type, PlaneEntity planeEntity) {
        this.type = type;
        this.planeEntity = planeEntity;
    }

    public final UpgradeType getType() {
        return type;
    }

    public DefaultedList<ItemStack> getDrops() {
        return DefaultedList.copyOf(getDrop());
    }

    public ItemStack getDrop() {
        return type.getDrops();
    }


    /**
     * Called when passenger right clicks with item.
     *
     *
     * @param player
     * @param world
     * @param hand
     * @param itemStack
     * @return Should this upgrade be removed after this event is called?
     */
    public boolean onItemRightClick(PlayerEntity player, World world, Hand hand, ItemStack itemStack) {
        return false;
    }

    /**
     * Called every tick by plane entity.
     *
     * @return Should this upgrade be removed after this event is called?
     */
    public boolean tick() {
        return false;
    }

    /**
     * is taking the engine place
     */
    public boolean isEngine() {
        return false;
    }

    /**
     * Called to render upgrade model. Loading model outside of this method may crash server.
     *
     * @param matrixStack matrix stack. Don't modify it.
     * @param buffer      Render type buffer
     * @param packedLight packed light
     */
    public abstract void render(MatrixStack matrixStack, VertexConsumerProvider buffer, int packedLight, float partialticks);

    public CompoundTag serializeNBT() {
        return new CompoundTag();
    }

    public void deserializeNBT(CompoundTag nbt) {
    }

    public CompoundTag serializeNBTData() {
        return new CompoundTag();
    }

    public void deserializeNBTData(CompoundTag nbt) {
    }

    public void onApply(ItemStack itemStack, PlayerEntity playerEntity) {
    }

    public void onRemove() {
    }

    public int getSeats() {
        return this.getType().occupyBackSeat ? 1 : 0;
    }
}
