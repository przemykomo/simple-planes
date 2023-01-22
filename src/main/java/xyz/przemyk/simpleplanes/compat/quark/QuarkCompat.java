//package xyz.przemyk.simpleplanes.compat.quark;
//
//import net.minecraft.world.item.Item;
//import net.minecraftforge.registries.ObjectHolder;
//import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
//
//public class QuarkCompat {
//
//    @ObjectHolder(registryName = "item", value = "quark:azalea_chest") public static final Item AZALEA_CHEST = null;
//    @ObjectHolder(registryName = "item", value = "quark:oak_chest") public static final Item OAK_CHEST = null;
//    @ObjectHolder(registryName = "item", value = "quark:spruce_chest") public static final Item SPRUCE_CHEST = null;
//    @ObjectHolder(registryName = "item", value = "quark:birch_chest") public static final Item BIRCH_CHEST = null;
//    @ObjectHolder(registryName = "item", value = "quark:jungle_chest") public static final Item JUNGLE_CHEST = null;
//    @ObjectHolder(registryName = "item", value = "quark:acacia_chest") public static final Item ACACIA_CHEST = null;
//    @ObjectHolder(registryName = "item", value = "quark:dark_oak_chest") public static final Item DARK_OAK_CHEST = null;
//    @ObjectHolder(registryName = "item", value = "quark:crimson_chest") public static final Item CRIMSON_CHEST = null;
//    @ObjectHolder(registryName = "item", value = "quark:warped_chest") public static final Item WARPED_CHEST = null;
//    @ObjectHolder(registryName = "item", value = "quark:mangrove_chest") public static final Item MANGROVE_CHEST = null;
//    @ObjectHolder(registryName = "item", value = "quark:nether_brick_chest") public static final Item NETHER_BRICK_CHEST = null;
//    @ObjectHolder(registryName = "item", value = "quark:purpur_chest") public static final Item PURPUR_CHEST = null;
//    @ObjectHolder(registryName = "item", value = "quark:prismarine_chest") public static final Item PRISMARINE_CHEST = null;
//    @ObjectHolder(registryName = "item", value = "quark:blossom_chest") public static final Item BLOSSOM_CHEST = null;
//
//    private static void registerChest(Item chestItem) {
//        if (chestItem != null) {
//            SimplePlanesUpgrades.registerLargeUpgradeItem(chestItem, SimplePlanesUpgrades.CHEST.get());
//        }
//    }
//
//    public static void registerUpgradeItems() {
//        registerChest(AZALEA_CHEST);
//        registerChest(OAK_CHEST);
//        registerChest(SPRUCE_CHEST);
//        registerChest(BIRCH_CHEST);
//        registerChest(JUNGLE_CHEST);
//        registerChest(ACACIA_CHEST);
//        registerChest(DARK_OAK_CHEST);
//        registerChest(CRIMSON_CHEST);
//        registerChest(WARPED_CHEST);
//        registerChest(MANGROVE_CHEST);
//        registerChest(NETHER_BRICK_CHEST);
//        registerChest(PURPUR_CHEST);
//        registerChest(PRISMARINE_CHEST);
//        registerChest(BLOSSOM_CHEST);
//    }
//}
