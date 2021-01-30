package xyz.przemyk.simpleplanes.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.container.PlaneWorkbenchContainer;
import xyz.przemyk.simpleplanes.network.CycleItemsPacket;
import xyz.przemyk.simpleplanes.network.PlaneNetworking;

public class PlaneWorkbenchScreen extends ContainerScreen<PlaneWorkbenchContainer> {
    public static final ResourceLocation GUI_TEXTURE = new ResourceLocation(SimplePlanesMod.MODID, "textures/gui/plane_workbench.png");

    public PlaneWorkbenchScreen(PlaneWorkbenchContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }

    @Override
    protected void init() {
        super.init();
        // left recipe output
        addButton(new ImageButton(guiLeft + 122, guiTop + 47, 10, 15, 176, 0, 15, GUI_TEXTURE,
                button -> PlaneNetworking.INSTANCE.sendToServer(new CycleItemsPacket(CycleItemsPacket.TYPE.CRAFTING_LEFT))));

        // right recipe output
        addButton(new ImageButton(guiLeft + 152, guiTop + 47, 10, 15, 186, 0, 15, GUI_TEXTURE,
                button -> PlaneNetworking.INSTANCE.sendToServer(new CycleItemsPacket(CycleItemsPacket.TYPE.CRAFTING_RIGHT))));
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
        this.minecraft.getTextureManager().bindTexture(GUI_TEXTURE);
        int i = this.guiLeft;
        int j = (this.height - this.ySize) / 2;
        this.blit(matrixStack, i, j, 0, 0, this.xSize, this.ySize);
    }
}
