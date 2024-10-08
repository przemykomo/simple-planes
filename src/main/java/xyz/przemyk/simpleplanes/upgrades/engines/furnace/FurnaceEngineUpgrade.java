package xyz.przemyk.simpleplanes.upgrades.engines.furnace;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.capabilities.BaseCapability;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.ItemStackHandler;
import xyz.przemyk.simpleplanes.client.ModBusClientEventHandler;
import xyz.przemyk.simpleplanes.client.gui.PlaneInventoryScreen;
import xyz.przemyk.simpleplanes.container.slots.FuelSlot;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesItems;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.engines.EngineUpgrade;

import java.util.function.Function;

public class FurnaceEngineUpgrade extends EngineUpgrade {

    public final ItemStackHandler itemStackHandler = new ItemStackHandler();
    public int burnTime;
    public int burnTimeTotal;

    public FurnaceEngineUpgrade(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.FURNACE_ENGINE.get(), planeEntity);
    }

    @Override
    public void tick() {
        if (burnTime > 0) {
            burnTime -= planeEntity.getFuelCost();
            updateClient();
        } else if (planeEntity.getThrottle() > 0) {
            ItemStack itemStack = itemStackHandler.getStackInSlot(0);
            int itemBurnTime = itemStack.getBurnTime(RecipeType.SMELTING);
            if (itemBurnTime > 0) {
                burnTimeTotal = itemBurnTime;
                burnTime = itemBurnTime;
                if (itemStack.hasCraftingRemainingItem()) {
                    itemStackHandler.setStackInSlot(0, itemStack.getCraftingRemainingItem());
                } else {
                    itemStackHandler.extractItem(0, 1, false);
                }
                updateClient();
            }
        }
    }

    @Override
    public boolean isPowered() {
        return burnTime > 0;
    }

    @Override
    public Tag serializeNBT() {
        CompoundTag compound = new CompoundTag();
        compound.put("item", itemStackHandler.serializeNBT(planeEntity.registryAccess()));
        compound.putInt("burnTime", burnTime);
        compound.putInt("burnTimeTotal", burnTimeTotal);
        return compound;
    }

    @Override
    public void deserializeNBT(CompoundTag compound) {
        itemStackHandler.deserializeNBT(planeEntity.registryAccess(), compound.getCompound("item"));
        burnTime = compound.getInt("burnTime");
        burnTimeTotal = compound.getInt("burnTimeTotal");
    }

    @Override
    public void writePacket(RegistryFriendlyByteBuf buffer) {
        ItemStack.OPTIONAL_STREAM_CODEC.encode(buffer, itemStackHandler.getStackInSlot(0));
        buffer.writeVarInt(burnTime);
        buffer.writeVarInt(burnTimeTotal);
    }

    @Override
    public void readPacket(RegistryFriendlyByteBuf buffer) {
        itemStackHandler.setStackInSlot(0, ItemStack.OPTIONAL_STREAM_CODEC.decode(buffer));
        burnTime = buffer.readVarInt();
        burnTimeTotal = buffer.readVarInt();
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
        return SimplePlanesItems.FURNACE_ENGINE.get().getDefaultInstance();
    }

    @Override
    public void renderPowerHUD(GuiGraphics guiGraphics, HumanoidArm side, int scaledWidth, int scaledHeight, float partialTicks) {
        int i = scaledWidth / 2;
        Minecraft mc = Minecraft.getInstance();
        if (side == HumanoidArm.LEFT) {
            guiGraphics.blit(ModBusClientEventHandler.HUD_TEXTURE, i - 91 - 29, scaledHeight - 40, 0, 44, 22, 40);
        } else {
            guiGraphics.blit(ModBusClientEventHandler.HUD_TEXTURE, i + 91, scaledHeight - 40, 0, 44, 22, 40);
        }

        if (burnTime > 0) {
            int burnTimeTotal2 = burnTimeTotal == 0 ? 200 : burnTimeTotal;
            int burnLeftScaled = burnTime * 13 / burnTimeTotal2;
            if (side == HumanoidArm.LEFT) {
                // render on left side
                guiGraphics.blit(ModBusClientEventHandler.HUD_TEXTURE, i - 91 - 29 + 4, scaledHeight - 40 + 16 - burnLeftScaled, 22, 56 - burnLeftScaled, 14, burnLeftScaled + 1);
            } else {
                // render on right side
                guiGraphics.blit(ModBusClientEventHandler.HUD_TEXTURE, i + 91 + 4, scaledHeight - 40 + 16 - burnLeftScaled, 22, 56 - burnLeftScaled, 14, burnLeftScaled + 1);
            }
        }

        ItemStack fuelStack = itemStackHandler.getStackInSlot(0);
        if (!fuelStack.isEmpty()) {
            int i2 = scaledHeight - 16 - 3;
            if (side == HumanoidArm.LEFT) {
                guiGraphics.renderItem(fuelStack, i - 91 - 26, i2);
                guiGraphics.renderItemDecorations(mc.font, fuelStack, i - 91 - 26, i2);
            } else {
                guiGraphics.renderItem(fuelStack, i + 91 + 3, i2);
                guiGraphics.renderItemDecorations(mc.font, fuelStack, i + 91 + 3, i2);
            }
        }
    }

    @Override
    public void addContainerData(Function<Slot, Slot> addSlot, Function<DataSlot, DataSlot> addDataSlot) {
        addSlot.apply(new FuelSlot(itemStackHandler, 0, 152, 62));
    }

    @Override
    public void renderScreenBg(GuiGraphics guiGraphics, int x, int y, float partialTicks, PlaneInventoryScreen screen) {
        guiGraphics.blit(PlaneInventoryScreen.GUI, screen.getGuiLeft() + 151, screen.getGuiTop() + 44, 208, 0, 18, 35);

        if (burnTime > 0) {
            int burnLeftScaled = burnTime * 13 / (burnTimeTotal == 0 ? 200 : burnTimeTotal);
            guiGraphics.blit(PlaneInventoryScreen.GUI, screen.getGuiLeft() + 152, screen.getGuiTop() + 57 - burnLeftScaled, 208, 47 - burnLeftScaled, 14, burnLeftScaled + 1);
        }
    }
}
