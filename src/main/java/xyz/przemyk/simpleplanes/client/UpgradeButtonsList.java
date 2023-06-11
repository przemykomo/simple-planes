package xyz.przemyk.simpleplanes.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSelectionList;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.network.CRemoveUpgradePacket;
import xyz.przemyk.simpleplanes.network.SimplePlanesNetworking;

public class UpgradeButtonsList extends AbstractSelectionList<UpgradeButtonsList.ButtonEntry> {

    public static final Component TITLE = Component.translatable(SimplePlanesMod.MODID + ".remove_upgrades");

    public UpgradeButtonsList(Minecraft mcIn, int widthIn, int heightIn, int topIn, int bottomIn, int itemHeightIn, PlaneEntity planeEntity) {
        super(mcIn, widthIn, heightIn, topIn, bottomIn, itemHeightIn);

        for (ResourceLocation resourceLocation : planeEntity.upgrades.keySet()) {
            addEntry(new ButtonEntry(x0 + widthIn / 2 - widthIn / 2, y0, widthIn, itemHeightIn - 4, Component.translatable(resourceLocation.toString()), planeEntity, resourceLocation, this));
        }
    }

    @Override
    protected void renderDecorations(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(minecraft.font, TITLE, 4, 4, 0xFFFFFF);
    }

    @Override
    public void updateNarration(NarrationElementOutput p_169152_) {}

    public static class ButtonEntry extends AbstractSelectionList.Entry<ButtonEntry> {

        private final Button button;

        public ButtonEntry(int x, int y, int width, int height, Component title, PlaneEntity planeEntity, ResourceLocation resourceLocation, UpgradeButtonsList list) {
            this.button = Button.builder(title, b -> {
                planeEntity.removeUpgrade(resourceLocation);
                SimplePlanesNetworking.INSTANCE.sendToServer(new CRemoveUpgradePacket(resourceLocation));
                list.removeEntry(this);
            }).pos(x + 4, y).size(width - 8, height).build();
        }

        @Override
        public void render(GuiGraphics guiGraphics, int entryIdx, int top, int left, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean p_194999_5_, float partialTicks) {
            button.setY(top);
            button.render(guiGraphics, mouseX, mouseY, partialTicks);
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            if (button == 0) {
                this.button.onClick(mouseX, mouseY);
                this.button.playDownSound(Minecraft.getInstance().getSoundManager());
                return true;
            } else {
                return false;
            }
        }
    }
}
