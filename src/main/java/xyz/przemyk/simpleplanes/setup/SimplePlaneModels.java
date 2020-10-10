package xyz.przemyk.simpleplanes.setup;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.ArrayList;
import java.util.List;

import static xyz.przemyk.simpleplanes.SimplePlanesMod.MODID;
import static xyz.przemyk.simpleplanes.setup.SimplePlanesItems.ITEMS;

@Mod.EventBusSubscriber(modid = MODID, value = Side.CLIENT)
public class SimplePlaneModels {
    private static final List<Item> REGISTERED_ITEM_MODELS = new ArrayList<>();

    @SubscribeEvent
    public static void registerItemModels(ModelRegistryEvent event) {
        for (Item item : ITEMS) {
            if (!REGISTERED_ITEM_MODELS.contains(item)) {
                registerItemModel(item);
            }
        }
    }
    private static void registerItemModel(Item item) {
        registerCustomItemModel(item, 0, item.getRegistryName().toString(), "inventory");
    }

    private static void registerCustomItemModel(Item item, int meta, String modelLocation, String variant) {

        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(modelLocation, variant));
        REGISTERED_ITEM_MODELS.add(item);
    }

}
