package xyz.przemyk.simpleplanes.upgrades;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

public abstract class Upgrade implements INBTSerializable<NBTTagCompound> {

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

    public NonNullList<ItemStack> getDrops() {
        return NonNullList.from(ItemStack.EMPTY,getDrop());
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
    public boolean onItemRightClick(EntityPlayer player, World world, EnumHand hand, ItemStack itemStack) {
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
     */
    public abstract void render(float partialticks, float scale);
    public ResourceLocation getTexture(){
        return null;
    }
    @Override
    public NBTTagCompound serializeNBT() {
        return new NBTTagCompound();
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
    }
    public NBTTagCompound serializeNBTData() {
        return new NBTTagCompound();
    }

    public void deserializeNBTData(NBTTagCompound nbt) {
    }

    public void onApply(ItemStack itemStack, EntityPlayer playerEntity) {
    }

    public void onRemove() {
    }

    public int getSeats() {
        return this.getType().occupyBackSeat ? 1 : 0;
    }
}
