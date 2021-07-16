package xyz.przemyk.simpleplanes.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.container.ElectricEngineContainer;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
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
        if (isHovering(152, 7, 16, 72, mouseX, mouseY)) {
            renderTooltip(matrixStack, new TranslationTextComponent(SimplePlanesMod.MODID + ".gui.energy", getEnergy()), mouseX, mouseY);
        } else {
            renderTooltip(matrixStack, mouseX, mouseY);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void renderBg(MatrixStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bind(GUI);
        this.blit(matrixStack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);

        int energy = getEnergy();
        if (energy > 0) {
            int k = energy * 71 / ElectricEngineUpgrade.CAPACITY;
            this.blit(matrixStack, this.leftPos + 152, this.topPos + 78 - k, 176, 85 - k, 16, k + 1);
        }
    }

    private int getEnergy() {
        Entity entity = minecraft.level.getEntity(menu.planeID);
        if (entity instanceof PlaneEntity) {
            if (((PlaneEntity) entity).engineUpgrade instanceof ElectricEngineUpgrade) {
                return ((ElectricEngineUpgrade) ((PlaneEntity) entity).engineUpgrade).energyStorage.getEnergyStored();
            }
        }

        return 0;
    }
}
