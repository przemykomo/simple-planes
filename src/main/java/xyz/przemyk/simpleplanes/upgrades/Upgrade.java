package xyz.przemyk.simpleplanes.upgrades;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

public abstract class Upgrade implements INBTSerializable<CompoundNBT> {

    private final UpgradeType type;

    public PlaneEntity getPlaneEntity() {
        return planeEntity;
    }

    protected final PlaneEntity planeEntity;

    public Upgrade(UpgradeType type, PlaneEntity planeEntity) {
        this.type = type;
        this.planeEntity = planeEntity;
    }

    public final UpgradeType getType() {
        return type;
    }

    public ItemStack getItem() {
        return type.upgradeItem.getDefaultInstance();
    }


    /**
     * Called when passenger right clicks with item.
     * @param event The right click event
     * @return Should this upgrade be removed after this event is called?
     */
    public boolean onItemRightClick(PlayerInteractEvent.RightClickItem event) {
        return false;
    }

    /**
     * Called every tick by plane entity.
     * @return Should this upgrade be removed after this event is called?
     */
    public boolean tick() {
        return false;
    }

    /**
     * Called to render upgrade model. Loading model outside of this method may crash server.
     * @param matrixStack matrix stack. Don't modify it.
     * @param buffer Render type buffer
     * @param packedLight packed light
     */
    public abstract void render(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight, float partialticks);

    @Override
    public CompoundNBT serializeNBT() {
        return new CompoundNBT();
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {}

    public void onApply(ItemStack itemStack, PlayerEntity playerEntity){}
}
