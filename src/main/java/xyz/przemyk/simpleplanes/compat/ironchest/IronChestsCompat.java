package xyz.przemyk.simpleplanes.compat.ironchest;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;

import java.util.function.Consumer;

public class IronChestsCompat {
    public static final String MODID = "ironchest";

    public static final String IRON_CHEST_NAME = MODID + ":iron_chest";
    public static final String GOLD_CHEST_NAME = MODID + ":gold_chest";
    public static final String DIAMOND_CHEST_NAME = MODID + ":diamond_chest";
    public static final String COPPER_CHEST_NAME = MODID + ":copper_chest";
    public static final String SILVER_CHEST_NAME = MODID + ":silver_chest";
    public static final String CRYSTAL_CHEST_NAME = MODID + ":crystal_chest";
    public static final String OBSIDIAN_CHEST_NAME = MODID + ":obsidian_chest";
    public static final String DIRT_CHEST_NAME = MODID + ":dirt_chest";

    public static final ResourceLocation IRON_CHEST_GUI = ResourceLocation.fromNamespaceAndPath(MODID, "textures/gui/iron_container.png");
    public static final ResourceLocation GOLD_CHEST_GUI = ResourceLocation.fromNamespaceAndPath(MODID, "textures/gui/gold_container.png");
    public static final ResourceLocation DIAMOND_CHEST_GUI = ResourceLocation.fromNamespaceAndPath(MODID, "textures/gui/diamond_container.png");
    public static final ResourceLocation COPPER_CHEST_GUI = ResourceLocation.fromNamespaceAndPath(MODID, "textures/gui/copper_container.png");
    public static final ResourceLocation SILVER_CHEST_GUI = ResourceLocation.fromNamespaceAndPath(MODID, "textures/gui/silver_container.png");
    public static final ResourceLocation DIRT_CHEST_GUI = ResourceLocation.fromNamespaceAndPath(MODID, "textures/gui/dirt_container.png");
    public static final ResourceLocation VANILLA_CHEST_GUI = ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "textures/gui/vanilla_chest.png");

    private static void registerChest(Item chestItem) {
        if (chestItem != Items.AIR) {
            SimplePlanesUpgrades.registerLargeUpgradeItem(chestItem, SimplePlanesUpgrades.CHEST.get());
        }
    }

    public static void registerUpgradeItems() {
        registerChest(BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("ironchest", "iron_chest")));
        registerChest(BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("ironchest", "gold_chest")));
        registerChest(BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("ironchest", "diamond_chest")));
        registerChest(BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("ironchest", "copper_chest")));
        registerChest(BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("ironchest", "silver_chest")));
        registerChest(BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("ironchest", "crystal_chest")));
        registerChest(BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("ironchest", "obsidian_chest")));
        registerChest(BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("ironchest", "dirt_chest")));
    }

    public static int getSize(String chestType) {
        return switch (chestType) {
            case IRON_CHEST_NAME -> 54;
            case GOLD_CHEST_NAME -> 81;
            case DIAMOND_CHEST_NAME, OBSIDIAN_CHEST_NAME, CRYSTAL_CHEST_NAME -> 108;
            case COPPER_CHEST_NAME -> 45;
            case SILVER_CHEST_NAME -> 72;
            case DIRT_CHEST_NAME -> 1;
            default -> 27;
        };
    }

    public static int getRowCount(String chestType) {
        return getSize(chestType) / getRowLength(chestType);
    }

    public static int getRowLength(String chestType) {
        return switch (chestType) {
            case DIAMOND_CHEST_NAME, CRYSTAL_CHEST_NAME, OBSIDIAN_CHEST_NAME -> 12;
            case DIRT_CHEST_NAME -> 1;
            default -> 9;
        };
    }

    public static void addSlots(String chestType, IItemHandler itemHandler, int rowCount, Inventory playerInventory, Consumer<Slot> addSlotFunction) {
        if (chestType.equals(DIRT_CHEST_NAME)) {
            addSlotFunction.accept(new DirtChestSlot(itemHandler, 0, 84, 44));
        } else {
            int rowLength = getRowLength(chestType);
            for (int row = 0; row < rowCount; ++row) {
                for (int column = 0; column < rowLength; ++column) {
                    addSlotFunction.accept(new SlotItemHandler(itemHandler, column + row * rowLength, 12 + column * 18, 18 + row * 18));
                }
            }
        }

        int xSize = getXSize(chestType);
        int ySize = getYSize(chestType);
        int leftCol = (xSize - 162) / 2 + 1;

        for (int row = 0; row < 3; ++row) {
            for(int column = 0; column < 9; ++column) {
                addSlotFunction.accept(new Slot(playerInventory, column + row * 9 + 9, leftCol + column * 18, ySize - (4 - row) * 18 - 10));
            }
        }

        for (int column = 0; column < 9; ++column) {
            addSlotFunction.accept(new Slot(playerInventory, column, leftCol + column * 18, ySize - 24));
        }
    }

    public static int getXSize(String chestType) {
        return switch (chestType) {
            case DIAMOND_CHEST_NAME, CRYSTAL_CHEST_NAME, OBSIDIAN_CHEST_NAME -> 238;
            default -> 184;
        };
    }

    public static int getYSize(String chestType) {
        return switch (chestType) {
            case IRON_CHEST_NAME -> 222;
            case GOLD_CHEST_NAME, DIAMOND_CHEST_NAME, CRYSTAL_CHEST_NAME, OBSIDIAN_CHEST_NAME -> 276;
            case COPPER_CHEST_NAME -> 204;
            case DIRT_CHEST_NAME -> 184;
            default -> 168;
        };
    }

    public static ResourceLocation getGuiTexture(String chestType) {
        return switch (chestType) {
            case IRON_CHEST_NAME -> IRON_CHEST_GUI;
            case GOLD_CHEST_NAME -> GOLD_CHEST_GUI;
            case DIAMOND_CHEST_NAME, CRYSTAL_CHEST_NAME, OBSIDIAN_CHEST_NAME -> DIAMOND_CHEST_GUI;
            case COPPER_CHEST_NAME -> COPPER_CHEST_GUI;
            case SILVER_CHEST_NAME -> SILVER_CHEST_GUI;
            case DIRT_CHEST_NAME -> DIRT_CHEST_GUI;
            default -> VANILLA_CHEST_GUI;
        };
    }

    public static int getTextureYSize(String chestType) {
        return switch (chestType) {
            case GOLD_CHEST_NAME, DIAMOND_CHEST_NAME, SILVER_CHEST_NAME, CRYSTAL_CHEST_NAME, OBSIDIAN_CHEST_NAME -> 276;
            default -> 256;
        };
    }
}
