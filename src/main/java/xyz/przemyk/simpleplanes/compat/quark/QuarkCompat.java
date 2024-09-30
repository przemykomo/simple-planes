package xyz.przemyk.simpleplanes.compat.quark;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;

public class QuarkCompat {

    private static void registerChest(Item chestItem) {
        if (chestItem != Items.AIR) {
            SimplePlanesUpgrades.registerLargeUpgradeItem(chestItem, SimplePlanesUpgrades.CHEST.get());
        }
    }

    public static void registerUpgradeItems() {
        registerChest(BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("quark", "azalea_chest")));
        registerChest(BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("quark", "oak_chest")));
        registerChest(BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("quark", "spruce_chest")));
        registerChest(BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("quark", "birch_chest")));
        registerChest(BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("quark", "jungle_chest")));
        registerChest(BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("quark", "acacia_chest")));
        registerChest(BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("quark", "dark_oak_chest")));
        registerChest(BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("quark", "crimson_chest")));
        registerChest(BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("quark", "warped_chest")));
        registerChest(BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("quark", "mangrove_chest")));
        registerChest(BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("quark", "nether_brick_chest")));
        registerChest(BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("quark", "purpur_chest")));
        registerChest(BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("quark", "prismarine_chest")));
        registerChest(BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("quark", "blossom_chest")));
    }
}
