package xyz.przemyk.simpleplanes.client.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.container.PlaneWorkbenchContainer;
import xyz.przemyk.simpleplanes.network.CycleItemsPacket;
import xyz.przemyk.simpleplanes.network.SimplePlanesNetworking;

public class PlaneWorkbenchScreen extends AbstractContainerScreen<PlaneWorkbenchContainer> {
    public static final ResourceLocation GUI = new ResourceLocation(SimplePlanesMod.MODID, "textures/gui/plane_workbench.png");

    public PlaneWorkbenchScreen(PlaneWorkbenchContainer screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
    }

    @Override
    protected void init() {
        super.init();
        addRenderableWidget(new ImageButton(leftPos + 122, topPos + 47, 10, 15, 176, 0, 15, GUI,
                button -> SimplePlanesNetworking.INSTANCE.sendToServer(new CycleItemsPacket(CycleItemsPacket.Type.CRAFTING_LEFT))));

        addRenderableWidget(new ImageButton(leftPos + 152, topPos + 47, 10, 15, 186, 0, 15, GUI,
                button -> SimplePlanesNetworking.INSTANCE.sendToServer(new CycleItemsPacket(CycleItemsPacket.Type.CRAFTING_RIGHT))));
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int x, int y) {
        int i = this.leftPos;
        int j = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(GUI, i, j, 0, 0, this.imageWidth, this.imageHeight);
    }
}
