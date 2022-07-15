package xyz.przemyk.simpleplanes.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemStack;

public class ClientUtil {
    public static void renderHotbarItem(PoseStack matrixStack, int x, int y, float partialTicks, ItemStack stack, Minecraft mc) {
        ItemRenderer itemRenderer = mc.getItemRenderer();
        if (!stack.isEmpty()) {
            float f = (float) stack.getUseDuration() - partialTicks;
            if (f > 0.0F) {
                matrixStack.pushPose();
                float f1 = 1.0F + f / 5.0F;
                matrixStack.translate((float) (x + 8), (float) (y + 12), 0.0F);
                matrixStack.scale(1.0F / f1, (f1 + 1.0F) / 2.0F, 1.0F);
                matrixStack.translate((float) (-(x + 8)), (float) (-(y + 12)), 0.0F);
            }

            itemRenderer.renderAndDecorateItem(stack, x, y);
            if (f > 0.0F) {
                matrixStack.popPose();
            }

            itemRenderer.renderGuiItemDecorations(mc.font, stack, x, y);
        }
    }

    public static void blit(PoseStack matrixStack, int blitOffset, int x, int y, int uOffset, int vOffset, int uWidth, int vHeight) {
        GuiComponent.blit(matrixStack, x, y, blitOffset, (float) uOffset, (float) vOffset, uWidth, vHeight, 256, 256);
    }
}
