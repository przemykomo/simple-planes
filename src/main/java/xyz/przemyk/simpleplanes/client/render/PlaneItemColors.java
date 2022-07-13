package xyz.przemyk.simpleplanes.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.ForgeModelBakery;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Random;

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
            Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(entityTag.getString("material")));
            if (block != null) {
                if (cachedColors.containsKey(block)) {
                    return cachedColors.get(block);
                }

                try {
                    TextureAtlasSprite sprite = Minecraft.getInstance().getModelManager().getModel(ForgeModelBakery.getInventoryVariant(block.getRegistryName().toString())).getQuads(null, Direction.SOUTH, new Random(42L), EmptyModelData.INSTANCE).get(0).getSprite();

                    int g = 0;
                    int b = 0;
                    int a = 0;

                    for (int x = 0; x < 16; x++) {
                        for (int y = 0; y < 16; y++) {
                            int pixelRGBA = sprite.getPixelRGBA(0, x, y);

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
//                        r_b
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
