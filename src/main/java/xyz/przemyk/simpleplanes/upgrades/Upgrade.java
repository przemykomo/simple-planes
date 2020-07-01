package xyz.przemyk.simpleplanes.upgrades;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import xyz.przemyk.simpleplanes.entities.furnacePlane.FurnacePlaneEntity;

public abstract class Upgrade implements INBTSerializable<CompoundNBT> {

    private final UpgradeType type;
    protected final FurnacePlaneEntity planeEntity;

    public Upgrade(UpgradeType type, FurnacePlaneEntity planeEntity) {
        this.type = type;
        this.planeEntity = planeEntity;
    }

    public final UpgradeType getType() {
        return type;
    }

    /**
     * Called when passenger right clicks with item.
     * @param event The right click event
     * @return Should this upgrade be removed after this event is called?
     */
    public abstract boolean onItemRightClick(PlayerInteractEvent.RightClickItem event);

    /**
     * Called every tick by plane entity.
     * @return Should this upgrade be removed after this event is called?
     */
    public boolean tick() {
        return false;
    }
    public abstract void render(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight);

    @Override
    public CompoundNBT serializeNBT() {
        return new CompoundNBT();
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {}
}
