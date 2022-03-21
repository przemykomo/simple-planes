package xyz.przemyk.simpleplanes.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import xyz.przemyk.simpleplanes.compat.ironchest.IronChestsCompat;
import xyz.przemyk.simpleplanes.container.StorageContainer;

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
        if (leftPos < 0) {
            leftPos = 0;
        }
        if (topPos < 0) {
            topPos = 0;
        }
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(PoseStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, texture);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        blit(matrixStack, x, y, 0.0F, 0.0F, imageWidth, imageHeight, 256, textureYSize);
    }
}
