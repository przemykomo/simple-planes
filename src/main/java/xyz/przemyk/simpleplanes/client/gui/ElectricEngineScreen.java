package xyz.przemyk.simpleplanes.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.container.ElectricEngineContainer;
import xyz.przemyk.simpleplanes.upgrades.engines.electric.ElectricEngineUpgrade;

public class ElectricEngineScreen extends ContainerScreen<ElectricEngineContainer> {

    private static final ResourceLocation GUI = new ResourceLocation(SimplePlanesMod.MODID, "textures/gui/electric_engine.png");

    public ElectricEngineScreen(ElectricEngineContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        if (isHovering(80, 7, 16, 72, mouseX, mouseY)) {
            renderTooltip(matrixStack, new TranslationTextComponent(SimplePlanesMod.MODID + ".gui.energy", menu.energyReference.get()), mouseX, mouseY);
        } else {
            renderTooltip(matrixStack, mouseX, mouseY);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void renderBg(MatrixStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bind(GUI);
        int i = this.leftPos;
        int j = this.topPos;
        this.blit(matrixStack, i, j, 0, 0, this.imageHeight, this.imageHeight);

        int energy = menu.energyReference.get();
        if (energy > 0) {
            int k = energy * 71 / ElectricEngineUpgrade.CAPACITY;
            this.blit(matrixStack, i + 80, j + 78 - k, 176, 85 - k, 16, k + 1);
        }
    }
}
