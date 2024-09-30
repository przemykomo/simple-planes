package xyz.przemyk.simpleplanes.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.inventory.InventoryMenu;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidStack;
import org.joml.Matrix4f;
import xyz.przemyk.simpleplanes.client.gui.PlaneInventoryScreen;

public class ClientUtil {

    public static void renderTiledTextureAtlas(GuiGraphics guiGraphics, AbstractContainerScreen<?> screen, TextureAtlasSprite sprite, int x, int y, int width, int height, int depth) {
        RenderSystem.setShaderTexture(0, sprite.atlasLocation());
        BufferBuilder builder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);

        float u1 = sprite.getU0();
        float v1 = sprite.getV0();
        int spriteHeight = sprite.contents().height();
        int spriteWidth = sprite.contents().width();
        int startX = x + screen.getGuiLeft();
        int startY = y + screen.getGuiTop();

        do {
            int renderHeight = Math.min(spriteHeight, height);
            height -= renderHeight;
            float v2 = sprite.getV((16f * renderHeight) / spriteHeight);

            // we need to draw the quads per width too
            int x2 = startX;
            int widthLeft = width;
            Matrix4f matrix = guiGraphics.pose().last().pose();
            // tile horizontally
            do {
                int renderWidth = Math.min(spriteWidth, widthLeft);
                widthLeft -= renderWidth;

                float u2 = sprite.getU((16f * renderWidth) / spriteWidth);
                buildSquare(matrix, builder, x2, x2 + renderWidth, startY, startY + renderHeight, depth, u1, u2, v1, v2);
                x2 += renderWidth;
            } while(widthLeft > 0);

            startY += renderHeight;
        } while (height > 0);

        // finish drawing sprites
//        builder.end();
//        BufferUploader.end(builder);
        MeshData meshData = builder.build();
        if (meshData != null) {
            BufferUploader.drawWithShader(meshData);
        }
    }

    private static void buildSquare(Matrix4f matrix, BufferBuilder builder, int x1, int x2, int y1, int y2, int z, float u1, float u2, float v1, float v2) {
        builder.addVertex(matrix, x1, y2, z).setUv(u1, v2);
        builder.addVertex(matrix, x2, y2, z).setUv(u2, v2);
        builder.addVertex(matrix, x2, y1, z).setUv(u2, v1);
        builder.addVertex(matrix, x1, y1, z).setUv(u1, v1);
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

    public static void renderLiquidEngineFluid(GuiGraphics guiGraphics, PlaneInventoryScreen screen, FluidStack fluidStack, int height, int width, int fluidHeight) {
        TextureAtlasSprite fluidSprite = screen.getMinecraft().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(IClientFluidTypeExtensions.of(fluidStack.getFluid()).getStillTexture(fluidStack));
//        setColorRGBA(IClientFluidTypeExtensions.of(fluidStack.getFluid()).getTintColor(fluidStack));
        renderTiledTextureAtlas(guiGraphics, screen, fluidSprite, 151 + 2, 7 + 18 + height - 2 - fluidHeight, width - 4, fluidHeight, 100);
//        RenderSystem.setShaderColor(1, 1, 1, 1);
//        RenderSystem.setShaderTexture(0, PlaneInventoryScreen.GUI);
    }
}
