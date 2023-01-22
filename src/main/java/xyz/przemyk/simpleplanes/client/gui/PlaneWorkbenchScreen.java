package xyz.przemyk.simpleplanes.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.container.PlaneWorkbenchContainer;

public class PlaneWorkbenchScreen extends AbstractContainerScreen<PlaneWorkbenchContainer> {
    public static final ResourceLocation GUI = new ResourceLocation(SimplePlanesMod.MODID, "textures/gui/plane_workbench.png");

    public PlaneWorkbenchScreen(PlaneWorkbenchContainer screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
    }

    @Override
    protected void init() {
        super.init();
//        addRenderableWidget(new ImageButton(leftPos + 122, topPos + 47, 10, 15, 176, 0, 15, GUI,
//                button -> SimplePlanesNetworking.INSTANCE.sendToServer(new CycleItemsPacket(CycleItemsPacket.Type.CRAFTING_LEFT))));
//
//        addRenderableWidget(new ImageButton(leftPos + 152, topPos + 47, 10, 15, 186, 0, 15, GUI,
//                button -> SimplePlanesNetworking.INSTANCE.sendToServer(new CycleItemsPacket(CycleItemsPacket.Type.CRAFTING_RIGHT))));
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(PoseStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI);
        int i = this.leftPos;
        int j = (this.height - this.imageHeight) / 2;
        this.blit(matrixStack, i, j, 0, 0, this.imageWidth, this.imageHeight);
    }
}
