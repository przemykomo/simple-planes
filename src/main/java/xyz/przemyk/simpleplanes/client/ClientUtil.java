package xyz.przemyk.simpleplanes.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
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

//    public static void renderTiledTextureAtlas(PoseStack poseStack, AbstractContainerScreen<?> screen, TextureAtlasSprite sprite, int x, int y, int width, int height, int depth) {
//        RenderSystem.setShaderTexture(0, sprite.atlas().location());
//        BufferBuilder builder = Tesselator.getInstance().getBuilder();
//        builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
//
//        float u1 = sprite.getU0();
//        float v1 = sprite.getV0();
//        int spriteHeight = sprite.getHeight();
//        int spriteWidth = sprite.getWidth();
//        int startX = x + screen.getGuiLeft();
//        int startY = y + screen.getGuiTop();
//
//        do {
//            int renderHeight = Math.min(spriteHeight, height);
//            height -= renderHeight;
//            float v2 = sprite.getV((16f * renderHeight) / spriteHeight);
//
//            // we need to draw the quads per width too
//            int x2 = startX;
//            int widthLeft = width;
//            Matrix4f matrix = poseStack.last().pose();
//            // tile horizontally
//            do {
//                int renderWidth = Math.min(spriteWidth, widthLeft);
//                widthLeft -= renderWidth;
//
//                float u2 = sprite.getU((16f * renderWidth) / spriteWidth);
//                buildSquare(matrix, builder, x2, x2 + renderWidth, startY, startY + renderHeight, depth, u1, u2, v1, v2);
//                x2 += renderWidth;
//            } while(widthLeft > 0);
//
//            startY += renderHeight;
//        } while(height > 0);
//
//        // finish drawing sprites
////        builder.end();
////        BufferUploader.end(builder);
//        Tesselator.getInstance().end();
//    }

    private static void buildSquare(Matrix4f matrix, BufferBuilder builder, int x1, int x2, int y1, int y2, int z, float u1, float u2, float v1, float v2) {
        builder.vertex(matrix, x1, y2, z).uv(u1, v2).endVertex();
        builder.vertex(matrix, x2, y2, z).uv(u2, v2).endVertex();
        builder.vertex(matrix, x2, y1, z).uv(u2, v1).endVertex();
        builder.vertex(matrix, x1, y1, z).uv(u1, v1).endVertex();
    }

    public static int alpha(int c) {
        return (c >> 24) & 0xFF;
    }

    public static int red(int c) {
        return (c >> 16) & 0xFF;
    }

    public static int green(int c) {
        return (c >> 8) & 0xFF;
    }

    public static int blue(int c) {
        return (c) & 0xFF;
    }

    public static void setColorRGBA(int color) {
        float a = alpha(color) / 255.0F;
        float r = red(color) / 255.0F;
        float g = green(color) / 255.0F;
        float b = blue(color) / 255.0F;

        RenderSystem.setShaderColor(r, g, b, a);
    }

//    public static void renderLiquidEngineFluid(PoseStack poseStack, PlaneInventoryScreen screen, FluidStack fluidStack, int height, int width, int fluidHeight) {
//        TextureAtlasSprite fluidSprite = screen.getMinecraft().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(IClientFluidTypeExtensions.of(fluidStack.getFluid()).getStillTexture(fluidStack));
//        setColorRGBA(IClientFluidTypeExtensions.of(fluidStack.getFluid()).getTintColor(fluidStack));
//        renderTiledTextureAtlas(poseStack, screen, fluidSprite, 151 + 2, 7 + 18 + height - 2 - fluidHeight, width - 4, fluidHeight, 100);
//        RenderSystem.setShaderColor(1, 1, 1, 1);
//        RenderSystem.setShaderTexture(0, PlaneInventoryScreen.GUI);
//    }
}
