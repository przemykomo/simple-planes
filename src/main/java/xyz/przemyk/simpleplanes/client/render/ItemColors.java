package xyz.przemyk.simpleplanes.client.render;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class ItemColors {
    public static final HashMap<Block, Integer> cachedColors = new HashMap<>();

    public void reloadColors() {
        //todo: use this somehow
        ItemColors.cachedColors.clear();
    }


    @OnlyIn(Dist.CLIENT)
    public static int getColor(ItemStack itemStack, int i) {
        CompoundNBT entityTag = itemStack.getTagElement("EntityTag");
        if (i != 0) {
            return -1;
        }
        int default_value = 0xB28F55;
        if (entityTag != null) {
            if (entityTag.contains("material")) {
                Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(entityTag.getString("material")));
                if (block != null) {
                    if (cachedColors.containsKey(block)) {
                        return cachedColors.get(block);
                    }

                    ResourceLocation texture;
                    try {
                        TextureAtlasSprite sprite = Minecraft.getInstance().getModelManager().getModel(ModelLoader.getInventoryVariant(block.getRegistryName().toString())).getQuads(null, Direction.SOUTH, new Random(42L), EmptyModelData.INSTANCE).get(0).getSprite();

                        int k = 0;
                        int g = 0;
                        int b = 0;
                        int a = 0;
//                        List<Integer> r1= new ArrayList<>();
                        List<Integer> g1 = new ArrayList<>();
                        List<Integer> b1 = new ArrayList<>();
                        List<Integer> a1 = new ArrayList<>();

                        for (int x = 0; x < 16; x++) {
                            for (int y = 0; y < 16; y++) {
                                int pixelRGBA = sprite.getPixelRGBA(0, x, y);

                                g += (pixelRGBA & 0x00ff0000) >>> 16;
                                b += (pixelRGBA & 0x0000ff00) >>> 8;
                                a += (pixelRGBA & 0x000000ff);
                                g1.add((pixelRGBA & 0x00ff0000) >>> 16);
                                b1.add((pixelRGBA & 0x0000ff00) >>> 8);
                                a1.add((pixelRGBA & 0x000000ff));

                                //                        int redo = r<<0|g<<8|b<<16;

//                                redo += getColor(sprite, x, y);
                                k++;
                            }
                        }

                        g /= 256;
                        b /= 256;
                        a /= 256;
                        g = (int) Math.min(255, g * 1.1);
                        b = (int) Math.min(255, b * 1.1);
                        a = (int) Math.min(255, a * 1.1);
                        g1.sort(Integer::compareTo);
                        b1.sort(Integer::compareTo);
                        a1.sort(Integer::compareTo);
//                        g=g1.get(127);
//                        b=b1.get(127);
//                        a=a1.get(127);

                        int redo = g << 0 | b << 8 | a << 16;

                        if (redo < 0) {
                            redo = 0xffffff - redo;
                        }
//                        r_b
                        cachedColors.put(block, (int) redo);
                        return redo;

//                        return (int) (redo*0.9+0xffffff*0.1);
//                        return pixelRGBA&0xffffff;
//                        return pixelRGBA>>>8;
                    } catch (IndexOutOfBoundsException exception) {
                        cachedColors.put(block, default_value);
                        return default_value;
                    }

                }
            }
        }
        return default_value;
    }
}
