package xyz.przemyk.simpleplanes.upgrades.furnace;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IIntArray;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.items.ItemStackHandler;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.EngineUpgrade;

public class FurnaceEngineUpgrade extends EngineUpgrade implements INamedContainerProvider {

    public final ItemStackHandler itemStackHandler = new ItemStackHandler();
    public int burnTime;
    public int burnTimeTotal;

    public FurnaceEngineUpgrade(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.FURNACE_ENGINE.get(), planeEntity);
    }

    @Override
    public void tick() {
        if (burnTime > 0) {
            --burnTime;
            updateClient();
        } else {
            int itemBurnTime = ForgeHooks.getBurnTime(itemStackHandler.getStackInSlot(0));
            if (itemBurnTime > 0) {
                burnTimeTotal = itemBurnTime;
                burnTime = itemBurnTime;
                itemStackHandler.extractItem(0, 1, false);
                updateClient();
            }
        }
    }

    @Override
    public boolean isPowered() {
        return burnTime > 0;
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight, float partialTicks) {

    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT compound = new CompoundNBT();
        compound.put("item", itemStackHandler.serializeNBT());
        compound.putInt("burnTime", burnTime);
        compound.putInt("burnTimeTotal", burnTimeTotal);
        return compound;
    }

    @Override
    public void deserializeNBT(CompoundNBT compound) {
        itemStackHandler.deserializeNBT(compound.getCompound("item"));
        burnTime = compound.getInt("burnTime");
        burnTimeTotal = compound.getInt("burnTimeTotal");
    }

    @Override
    public void writePacket(PacketBuffer buffer) {
        buffer.writeItemStack(itemStackHandler.getStackInSlot(0));
        buffer.writeVarInt(burnTime);
        buffer.writeVarInt(burnTimeTotal);
    }

    @Override
    public void readPacket(PacketBuffer buffer) {
        itemStackHandler.setStackInSlot(0, buffer.readItemStack());
        burnTime = buffer.readVarInt();
        burnTimeTotal = buffer.readVarInt();
    }

    @Override
    public boolean canOpenGui() {
        return true;
    }

    @Override
    public void openGui(PlayerEntity playerEntity) {
        playerEntity.openContainer(this);
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent(SimplePlanesMod.MODID + ".furnace_engine_container");
    }

    @Override
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new FurnaceEngineContainer(id, playerInventory, itemStackHandler, new IIntArray() {
            @Override
            public int get(int index) {
                if (index == 0) {
                    return burnTime;
                }
                return burnTimeTotal;
            }

            @Override
            public void set(int index, int value) {}

            @Override
            public int size() {
                return 2;
            }
        });
    }
}
