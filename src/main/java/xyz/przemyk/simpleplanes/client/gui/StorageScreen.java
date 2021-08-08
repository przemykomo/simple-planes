package xyz.przemyk.simpleplanes.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import xyz.przemyk.simpleplanes.compat.ironchest.IronChestsCompat;
import xyz.przemyk.simpleplanes.container.StorageContainer;

public class StorageScreen extends ContainerScreen<StorageContainer> {

    public final ResourceLocation texture;
    public final int textureYSize;

    public StorageScreen(StorageContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        imageHeight = IronChestsCompat.getYSize(screenContainer.chestType);
        imageWidth = IronChestsCompat.getXSize(screenContainer.chestType);
        inventoryLabelY = imageHeight - 94;
        texture = IronChestsCompat.getGuiTexture(screenContainer.chestType);
        textureYSize = IronChestsCompat.getTextureYSize(screenContainer.chestType);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        renderTooltip(matrixStack, mouseX, mouseY);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void renderBg(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        minecraft.getTextureManager().bind(texture);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        blit(matrixStack, x, y, 0.0F, 0.0F, imageWidth, imageHeight, 256, textureYSize);
    }
}
