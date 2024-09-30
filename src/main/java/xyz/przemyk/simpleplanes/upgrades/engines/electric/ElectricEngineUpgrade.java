package xyz.przemyk.simpleplanes.upgrades.engines.electric;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.BaseCapability;
import net.neoforged.neoforge.capabilities.Capabilities;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.client.ModBusClientEventHandler;
import xyz.przemyk.simpleplanes.client.gui.PlaneInventoryScreen;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.misc.EnergyStorageWithSet;
import xyz.przemyk.simpleplanes.setup.SimplePlanesItems;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.engines.EngineUpgrade;

public class ElectricEngineUpgrade extends EngineUpgrade {

    public static final int CAPACITY = 1_500_000;

    public final EnergyStorageWithSet energyStorage = new EnergyStorageWithSet(CAPACITY);

    public ElectricEngineUpgrade(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.ELECTRIC_ENGINE.get(), planeEntity);
    }

    @Override
    public void tick() {
        if (planeEntity.getThrottle() > 0) {
            if (energyStorage.extractEnergy(12 * planeEntity.getFuelCost(), false) > 0) {
                updateClient();
            }
        }
    }

    @Override
    public boolean isPowered() {
        return energyStorage.getEnergyStored() > 12 * planeEntity.getFuelCost();
    }

    @Override
    public void renderPowerHUD(GuiGraphics guiGraphics, HumanoidArm side, int scaledWidth, int scaledHeight, float partialTicks) {
        int i = scaledWidth / 2;
        if (side == HumanoidArm.LEFT) {
            guiGraphics.blit(ModBusClientEventHandler.HUD_TEXTURE, i - 91 - 29, scaledHeight - 22, 38, 44, 22, 21);
        } else {
            guiGraphics.blit(ModBusClientEventHandler.HUD_TEXTURE, i + 91, scaledHeight - 22, 38, 44, 22, 21);
        }

        int energy = energyStorage.getEnergyStored();

        if (energy > 0) {
            int energyScaled = energy * 15 / CAPACITY;
            if (side == HumanoidArm.LEFT) {
                guiGraphics.blit(ModBusClientEventHandler.HUD_TEXTURE, i - 91 - 29 + 3, scaledHeight - 22 + 16 - energyScaled, 60, 57 - energyScaled, 16, energyScaled + 2);
            } else {
                guiGraphics.blit(ModBusClientEventHandler.HUD_TEXTURE, i + 91 + 3, scaledHeight - 22 + 16 - energyScaled, 60, 57 - energyScaled, 16, energyScaled + 2);
            }
        }
    }

    @Override
    public Tag serializeNBT() {
        CompoundTag compoundNBT = new CompoundTag();
        compoundNBT.putInt("energy", energyStorage.getEnergyStored());
        return compoundNBT;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        int energy = nbt.getInt("energy");
        energyStorage.setEnergy(Math.min(energy, CAPACITY));
    }

    @Override
    public void writePacket(RegistryFriendlyByteBuf buffer) {
        buffer.writeVarInt(energyStorage.getEnergyStored());
    }

    @Override
    public void readPacket(RegistryFriendlyByteBuf buffer) {
        energyStorage.setEnergy(buffer.readVarInt());
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getCap(BaseCapability<T, ?> cap) {
        if (cap == Capabilities.EnergyStorage.ENTITY) {
            return (T) energyStorage;
        }

        return super.getCap(cap);
    }

    @Override
    public ItemStack getItemStack() {
        return SimplePlanesItems.ELECTRIC_ENGINE.get().getDefaultInstance();
    }

    @Override
    public void renderScreen(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks, PlaneInventoryScreen planeInventoryScreen) {
        if (planeInventoryScreen.isHovering(152, 7, 16, 72, mouseX, mouseY)) {
            guiGraphics.renderTooltip(planeInventoryScreen.getMinecraft().font, Component.translatable(SimplePlanesMod.MODID + ".gui.energy", energyStorage.getEnergyStored()), mouseX, mouseY);
        }
    }

    @Override
    public void renderScreenBg(GuiGraphics guiGraphics, int x, int y, float partialTicks, PlaneInventoryScreen screen) {
        guiGraphics.blit(PlaneInventoryScreen.GUI, screen.getGuiLeft() + 152, screen.getGuiTop() + 7, 176, 0, 16, 72);

        int energy = energyStorage.getEnergyStored();
        if (energy > 0) {
            int energyScaled = energy * 71 / CAPACITY;
            guiGraphics.blit(PlaneInventoryScreen.GUI, screen.getGuiLeft() + 152, screen.getGuiTop() + 78 - energyScaled, 192, 71 - energyScaled, 16, energyScaled + 1);
        }
    }
}
