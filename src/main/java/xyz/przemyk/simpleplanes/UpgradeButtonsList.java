package xyz.przemyk.simpleplanes;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.list.AbstractList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

public class UpgradeButtonsList extends AbstractList<UpgradeButtonsList.ButtonEntry> {

    public UpgradeButtonsList(Minecraft mcIn, int widthIn, int heightIn, int topIn, int bottomIn, int itemHeightIn, PlaneEntity entity) {
        super(mcIn, widthIn, heightIn, topIn, bottomIn, itemHeightIn);

        for (ResourceLocation resourceLocation : entity.upgrades.keySet()) {
            addEntry(new ButtonEntry(new Button(x0 + widthIn / 2 - widthIn / 2, y0, widthIn, itemHeightIn - 4, new StringTextComponent(resourceLocation.toString()), b -> {
                //TODO: send remove packet
                System.out.println(resourceLocation);
            })));
        }
    }

    public static class ButtonEntry extends AbstractList.AbstractListEntry<ButtonEntry> {

        private final Button button;

        public ButtonEntry(Button button) {
            this.button = button;
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
