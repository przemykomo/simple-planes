package xyz.przemyk.simpleplanes.client.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.network.PacketDistributor;
import xyz.przemyk.simpleplanes.compat.ironchest.IronChestsCompat;
import xyz.przemyk.simpleplanes.container.StorageContainer;
import xyz.przemyk.simpleplanes.network.CyclePlaneInventoryPacket;

public class StorageScreen extends AbstractContainerScreen<StorageContainer> {

    public final ResourceLocation texture;
    public final int textureYSize;

    public StorageScreen(StorageContainer screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
        imageHeight = IronChestsCompat.getYSize(screenContainer.chestType);
        imageWidth = IronChestsCompat.getXSize(screenContainer.chestType);
        inventoryLabelY = imageHeight - 94;
        texture = IronChestsCompat.getGuiTexture(screenContainer.chestType);
        textureYSize = IronChestsCompat.getTextureYSize(screenContainer.chestType);
    }

    @Override
    protected void init() {
        super.init();
        addRenderableWidget(new ImageButton(leftPos + 3, topPos + 54, 10, 15, PlaneInventoryScreen.LEFT_BUTTON_SPRITES,
                button -> PacketDistributor.sendToServer(new CyclePlaneInventoryPacket(CyclePlaneInventoryPacket.Direction.LEFT))));
        addRenderableWidget(new ImageButton(leftPos + imageWidth - 13, topPos + 54, 10, 15, PlaneInventoryScreen.RIGHT_BUTTON_SPRITES,
                button -> PacketDistributor.sendToServer(new CyclePlaneInventoryPacket(CyclePlaneInventoryPacket.Direction.RIGHT))));

    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        guiGraphics.blit(texture, x, y, 0.0F, 0.0F, imageWidth, imageHeight, 256, textureYSize);
    }
}
