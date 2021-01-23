package xyz.przemyk.simpleplanes;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

public class RemoveUpgradesScreen extends ContainerScreen<RemoveUpgradesContainer> {

    private UpgradeButtonsList buttonsList;

    public RemoveUpgradesScreen(RemoveUpgradesContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }

    @Override
    protected void init() {
        super.init();
        Entity entity = minecraft.world.getEntityByID(container.planeID);
        if (entity instanceof PlaneEntity) {
            buttonsList = new UpgradeButtonsList(minecraft, 160, height, 32, height - 61, 20, (PlaneEntity) entity);
            children.add(buttonsList);
        } else {
            closeScreen();
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {}
    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int x, int y) {}

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        buttonsList.render(matrixStack, mouseX, mouseY, partialTicks);
    }
}
