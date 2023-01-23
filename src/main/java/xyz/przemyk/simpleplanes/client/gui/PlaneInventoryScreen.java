package xyz.przemyk.simpleplanes.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.container.PlaneInventoryContainer;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

public class PlaneInventoryScreen extends AbstractContainerScreen<PlaneInventoryContainer> {

    public static final ResourceLocation GUI = new ResourceLocation(SimplePlanesMod.MODID, "textures/gui/plane_inventory.png");

    public PlaneInventoryScreen(PlaneInventoryContainer screenContainer, Inventory inventory, Component title) {
        super(screenContainer, inventory, title);
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, partialTicks);
        if (menu.planeEntity != null) {
            for (Upgrade upgrade : menu.planeEntity.upgrades.values()) {
                upgrade.renderScreen(poseStack, mouseX, mouseY, partialTicks, this);
            }
        }
        renderTooltip(poseStack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(PoseStack poseStack, float partialTicks, int x, int y) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI);
        blit(poseStack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);

        if (menu.planeEntity != null) {
            for (Upgrade upgrade : menu.planeEntity.upgrades.values()) {
                upgrade.renderScreenBg(poseStack, x, y, partialTicks, this);
            }
        }
    }

    public int getGuiLeft() {
        return leftPos;
    }

    public int getGuiTop() {
        return topPos;
    }
}
