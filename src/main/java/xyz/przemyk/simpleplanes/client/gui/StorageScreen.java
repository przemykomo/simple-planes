package xyz.przemyk.simpleplanes.client.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.compat.ironchest.IronChestsCompat;
import xyz.przemyk.simpleplanes.container.StorageContainer;
import xyz.przemyk.simpleplanes.network.CyclePlaneInventoryPacket;
import xyz.przemyk.simpleplanes.network.SimplePlanesNetworking;

public class StorageScreen extends AbstractContainerScreen<StorageContainer> {

    public final ResourceLocation texture;
    public final int textureYSize;
    public static final ResourceLocation GUI = new ResourceLocation(SimplePlanesMod.MODID, "textures/gui/plane_inventory.png");

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
        if (leftPos < 0) {
            leftPos = 0;
        }
        if (topPos < 0) {
            topPos = 0;
        }
        addRenderableWidget(new ImageButton(leftPos + 3, topPos + 54, 10, 15, 8, 49, 15, GUI,
                button -> SimplePlanesNetworking.INSTANCE.sendToServer(new CyclePlaneInventoryPacket(CyclePlaneInventoryPacket.Type.LEFT))));
        addRenderableWidget(new ImageButton(leftPos + 171, topPos + 54, 10, 15, 218, 49, 15, GUI,
                button -> SimplePlanesNetworking.INSTANCE.sendToServer(new CyclePlaneInventoryPacket(CyclePlaneInventoryPacket.Type.RIGHT))));

    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        renderBackground(guiGraphics);
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
