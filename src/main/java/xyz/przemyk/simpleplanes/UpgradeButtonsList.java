package xyz.przemyk.simpleplanes;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.list.AbstractList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.network.PlaneNetworking;
import xyz.przemyk.simpleplanes.network.CRemoveUpgradePacket;

public class UpgradeButtonsList extends AbstractList<UpgradeButtonsList.ButtonEntry> {

    public UpgradeButtonsList(Minecraft mcIn, int widthIn, int heightIn, int topIn, int bottomIn, int itemHeightIn, PlaneEntity planeEntity) {
        super(mcIn, widthIn, heightIn, topIn, bottomIn, itemHeightIn);

        for (ResourceLocation resourceLocation : planeEntity.upgrades.keySet()) {
            addEntry(new ButtonEntry(x0 + widthIn / 2 - widthIn / 2, y0, widthIn, itemHeightIn - 4, new TranslationTextComponent(resourceLocation.toString()), planeEntity, resourceLocation, this));
        }
    }

    @Override
    protected void renderDecorations(MatrixStack matrixStack, int mouseX, int mouseY) {
        minecraft.fontRenderer.func_243248_b(matrixStack, new StringTextComponent("Remove upgrade"), 4, 4, 0xFFFFFF);
    }

    public static class ButtonEntry extends AbstractList.AbstractListEntry<ButtonEntry> {

        private final Button button;

        public ButtonEntry(int x, int y, int width, int height, ITextComponent title, PlaneEntity planeEntity, ResourceLocation resourceLocation, UpgradeButtonsList list) {
            this.button = new Button(x + 4, y, width - 8, height, title, b -> {
                planeEntity.removeUpgrade(resourceLocation);
                PlaneNetworking.INSTANCE.sendToServer(new CRemoveUpgradePacket(resourceLocation));
                list.removeEntry(this);
            });
        }

        @Override
        public void render(MatrixStack matrixStack, int entryIdx, int top, int left, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean p_194999_5_, float partialTicks) {
            button.y = top;
            button.render(matrixStack, mouseX, mouseY, partialTicks);
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            if (button == 0) {
                this.button.onClick(mouseX, mouseY);
                this.button.playDownSound(Minecraft.getInstance().getSoundHandler());
                return true;
            } else {
                return false;
            }
        }
    }
}
