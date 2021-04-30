package xyz.przemyk.simpleplanes.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.container.FurnaceEngineContainer;

public class FurnaceEngineScreen extends ContainerScreen<FurnaceEngineContainer> {

    private static final ResourceLocation GUI = new ResourceLocation(SimplePlanesMod.MODID, "textures/gui/furnace_engine.png");

    public FurnaceEngineScreen(FurnaceEngineContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        renderTooltip(matrixStack, mouseX, mouseY);
    }

    @SuppressWarnings({"ConstantConditions", "deprecation"})
    @Override
    protected void renderBg(MatrixStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bind(GUI);
        int i = this.leftPos;
        int j = this.topPos;
        this.blit(matrixStack, i, j, 0, 0, this.imageWidth, this.imageHeight);
        if (menu.engineData.get(0) > 0) {
            int k = getBurnLeftScaled();
            this.blit(matrixStack, i + 80, j + 57 - k, 176, 12 - k, 14, k + 1);
        }
    }

    private int getBurnLeftScaled() {
        int burnTimeTotal = menu.engineData.get(1);
        if (burnTimeTotal == 0) {
            burnTimeTotal = 200;
        }

        return menu.engineData.get(0) * 13 / burnTimeTotal;
    }
}
