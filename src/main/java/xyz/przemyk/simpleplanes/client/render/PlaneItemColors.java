package xyz.przemyk.simpleplanes.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import java.util.HashMap;

public class PlaneItemColors {
    public static final HashMap<Block, Integer> cachedColors = new HashMap<>();
    public static final int DEFAULT_COLOR = 0xB28F55;

    public static void clearCache() {
        cachedColors.clear();
    }

    public static int getColor(ItemStack itemStack, int tintIndex) {
        CompoundTag entityTag = itemStack.getTagElement("EntityTag");
        if (tintIndex != 0) {
            return -1;
        }

        if (entityTag != null && entityTag.contains("material")) {
            Block block = Registry.BLOCK.get(new ResourceLocation(entityTag.getString("material")));
            if (block != null) {
                if (cachedColors.containsKey(block)) {
                    return cachedColors.get(block);
                }

                try {
                    TextureAtlasSprite sprite = Minecraft.getInstance().getModelManager().getModel(new ModelResourceLocation(Registry.BLOCK.getKey(block), "inventory")).getQuads(null, Direction.SOUTH, RandomSource.create()).get(0).getSprite();
                    int frameX = 0;
                    int frameY = 0;
                    if (sprite.animatedTexture != null) {
                        frameX = sprite.animatedTexture.getFrameX(0) * sprite.getWidth();
                        frameY = sprite.animatedTexture.getFrameY(0) * sprite.getHeight();
                    }
                    int g = 0;
                    int b = 0;
                    int a = 0;

                    for (int x = 0; x < 16; x++) {
                        for (int y = 0; y < 16; y++) {
                            int pixelRGBA = sprite.mainImage[0].getPixelRGBA(x + frameY, y + frameY);

                            g += (pixelRGBA & 0x00ff0000) >>> 16;
                            b += (pixelRGBA & 0x0000ff00) >>> 8;
                            a += (pixelRGBA & 0x000000ff);
                        }
                    }

                    g /= 256;
                    b /= 256;
                    a /= 256;
                    g = (int) Math.min(255, g * 1.1);
                    b = (int) Math.min(255, b * 1.1);
                    a = (int) Math.min(255, a * 1.1);

                    int redo = g | b << 8 | a << 16;

                    if (redo < 0) {
                        redo = 0xffffff - redo;
                    }

                    cachedColors.put(block, redo);
                    return redo;

                } catch (IndexOutOfBoundsException | IllegalArgumentException exception) {
                    cachedColors.put(block, DEFAULT_COLOR);
                    return DEFAULT_COLOR;
                }

            }
        }
        return DEFAULT_COLOR;
    }
}
