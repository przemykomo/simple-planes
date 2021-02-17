package xyz.przemyk.simpleplanes.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import xyz.przemyk.simpleplanes.container.StorageContainer;

public class StorageScreen extends ContainerScreen<StorageContainer> {

    private static final ResourceLocation TEXTURE = new ResourceLocation("textures/gui/container/generic_54.png");

    public static final int inventoryRows = 3;

    public StorageScreen(StorageContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        ySize = 114 + inventoryRows * 18;
        playerInventoryTitleY = ySize - 94;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        minecraft.getTextureManager().bindTexture(TEXTURE);
        int i = (width - xSize) / 2;
        int j = (height - ySize) / 2;
        blit(matrixStack, i, j, 0, 0, xSize, inventoryRows * 18 + 17);
        blit(matrixStack, i, j + inventoryRows * 18 + 17, 0, 126, xSize, 96);
    }
}
