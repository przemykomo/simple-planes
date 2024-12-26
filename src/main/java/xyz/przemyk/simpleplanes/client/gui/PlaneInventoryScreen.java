package xyz.przemyk.simpleplanes.client.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.network.PacketDistributor;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.container.PlaneInventoryContainer;
import xyz.przemyk.simpleplanes.network.CyclePlaneInventoryPacket;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

public class PlaneInventoryScreen extends AbstractContainerScreen<PlaneInventoryContainer> {

    public static final ResourceLocation GUI = ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "textures/gui/plane_inventory.png");
    public static final WidgetSprites LEFT_BUTTON_SPRITES = new WidgetSprites(
        ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "left"),
        ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "left_highlighted")
    );
    public static final WidgetSprites RIGHT_BUTTON_SPRITES = new WidgetSprites(
        ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "right"),
        ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "right_highlighted")
    );

    public PlaneInventoryScreen(PlaneInventoryContainer screenContainer, Inventory inventory, Component title) {
        super(screenContainer, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        addRenderableWidget(new ImageButton(leftPos + 8, topPos + 54, 10, 15, LEFT_BUTTON_SPRITES,
                button -> PacketDistributor.sendToServer(new CyclePlaneInventoryPacket(CyclePlaneInventoryPacket.Direction.LEFT))));
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        if (menu.planeEntity != null) {
            for (Upgrade upgrade : menu.planeEntity.upgrades.values()) {
                upgrade.renderScreen(guiGraphics, mouseX, mouseY, partialTicks, this);
            }
        }
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int x, int y) {
        guiGraphics.blit(GUI, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);

        if (menu.planeEntity != null) {
            for (Upgrade upgrade : menu.planeEntity.upgrades.values()) {
                upgrade.renderScreenBg(guiGraphics, x, y, partialTicks, this);
            }
        }
    }

    @Override
    public boolean isHovering(int p_97768_, int p_97769_, int p_97770_, int p_97771_, double p_97772_, double p_97773_) {
        return super.isHovering(p_97768_, p_97769_, p_97770_, p_97771_, p_97772_, p_97773_);
    }

    @Override
    public void renderTooltip(GuiGraphics p_283594_, int p_282171_, int p_281909_) {
        super.renderTooltip(p_283594_, p_282171_, p_281909_);
    }
}
