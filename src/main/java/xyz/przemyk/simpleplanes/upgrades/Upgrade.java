package xyz.przemyk.simpleplanes.upgrades;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.capabilities.CapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

import javax.annotation.Nullable;

public abstract class Upgrade extends CapabilityProvider<Upgrade> implements INBTSerializable<CompoundNBT> {

    private final UpgradeType type;
    protected final PlaneEntity planeEntity;
    public boolean updateClient = false;
    public boolean removed = false;

    public PlaneEntity getPlaneEntity() {
        return planeEntity;
    }

    public Upgrade(UpgradeType type, PlaneEntity planeEntity) {
        super(Upgrade.class);
        this.type = type;
        this.planeEntity = planeEntity;
    }

    /**
     * Call it when data is changed and it needs to be synced to the client.
     * If called on a server, results in calling writePacket method on a server and readPacket on a client
     */
    protected void updateClient() {
        updateClient = true;
    }

    /**
     * Call it to remove this upgrade from the plane.
     */
    protected void remove() {
        removed = true;
        invalidateCaps();
    }

    public final UpgradeType getType() {
        return type;
    }

    /**
     * Called when passenger right clicks with item.
     *
     * @param event The right click event
     */
    public void onItemRightClick(PlayerInteractEvent.RightClickItem event) {}

    /**
     * Called every tick by plane entity.
     */
    public void tick() {}

    /**
     * Called to render upgrade model. Loading model outside of this method may crash server.
     *
     * @param matrixStack matrix stack.
     * @param buffer Render type buffer
     * @param packedLight packed light
     */
    public abstract void render(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight, float partialTicks);

    @Override
    public CompoundNBT serializeNBT() {
        return new CompoundNBT();
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {}

    public void onApply(ItemStack itemStack, PlayerEntity playerEntity) {}

    /**
     * Called on the server.
     */
    public abstract void writePacket(PacketBuffer buffer);

    /**
     * Called on the client.
     */
    public abstract void readPacket(PacketBuffer buffer);

    public ItemStack toItemStack() {
        Item item = getItem();
        if (item == null) {
            return ItemStack.EMPTY;
        }

        ItemStack itemStack = item.getDefaultInstance();
        itemStack.setTagInfo("upgradeTag", serializeNBT());
        return itemStack;
    }

    public void fromItemStack(ItemStack itemStack) {
        CompoundNBT compound = itemStack.getChildTag("upgradeTag");
        if (compound != null) {
            deserializeNBT(compound);
        }
    }

    @Nullable
    protected Item getItem() { return null; }
}
