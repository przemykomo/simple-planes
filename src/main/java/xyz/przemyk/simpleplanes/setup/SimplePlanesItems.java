package xyz.przemyk.simpleplanes.setup;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.items.PlaneItem;
import xyz.przemyk.simpleplanes.upgrades.UpgradeItem;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = SimplePlanesMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SimplePlanesItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SimplePlanesMod.MODID);

    public static void init() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final ItemGroup ITEM_GROUP = new ItemGroup(SimplePlanesMod.MODID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(PLANE_ITEM.get());
        }
    };

    public static List<PlaneItem> getPlaneItems(){
        ArrayList<PlaneItem> planeItems = new ArrayList<>();
        planeItems.add(PLANE_ITEM.get());
        planeItems.add(LARGE_PLANE_ITEM.get());
        planeItems.add(HELICOPTER_ITEM.get());
        return planeItems;
    }

    public static final RegistryObject<PlaneItem> PLANE_ITEM = ITEMS.register("plane", () -> new PlaneItem(new Item.Properties().group(ITEM_GROUP), SimplePlanesEntities.PLANE));
    public static final RegistryObject<PlaneItem> LARGE_PLANE_ITEM = ITEMS.register("large_plane", () -> new PlaneItem(new Item.Properties().group(ITEM_GROUP), SimplePlanesEntities.LARGE_PLANE));
    public static final RegistryObject<PlaneItem> HELICOPTER_ITEM = ITEMS.register("helicopter", () -> new PlaneItem(new Item.Properties().group(ITEM_GROUP), SimplePlanesEntities.HELICOPTER));

    public static final RegistryObject<Item> PROPELLER = ITEMS.register("propeller", () -> new Item(new Item.Properties().group(ITEM_GROUP)));

    public static final RegistryObject<UpgradeItem> FLOATY_BEDDING = ITEMS.register("floaty_bedding", () -> new UpgradeItem(new Item.Properties().group(ITEM_GROUP), SimplePlanesUpgrades.FLOATY_BEDDING));
    public static final RegistryObject<UpgradeItem> BOOSTER = ITEMS.register("booster", () -> new UpgradeItem(new Item.Properties().group(ITEM_GROUP), SimplePlanesUpgrades.BOOSTER));
    public static final RegistryObject<UpgradeItem> SHOOTER = ITEMS.register("shooter", () -> new UpgradeItem(new Item.Properties().group(ITEM_GROUP), SimplePlanesUpgrades.SHOOTER));
    public static final RegistryObject<UpgradeItem> HEALING = ITEMS.register("healing", () -> new UpgradeItem(new Item.Properties().group(ITEM_GROUP), SimplePlanesUpgrades.HEALING));
    public static final RegistryObject<UpgradeItem> FURNACE_ENGINE = ITEMS.register("furnace_engine", () -> new UpgradeItem(new Item.Properties().group(ITEM_GROUP), SimplePlanesUpgrades.FURNACE_ENGINE));

//    public static final RegistryObject<InformationItem> SPRAYER = ITEMS.register("sprayer", () -> new InformationItem(new TranslationTextComponent("description.simpleplanes.sprayer")));
//    public static final RegistryObject<InformationItem> FOLDING = ITEMS.register("folding", () -> new InformationItem(new TranslationTextComponent("description.simpleplanes.folding")));
//    public static final RegistryObject<InformationItem> CLOUD = ITEMS
//        .register("cloud", () -> new InformationItem(new TranslationTextComponent("description.simpleplanes.cloud")) {
//            @Override
//            public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
//                ItemStack itemstack = playerIn.getHeldItem(handIn);
//                playerIn.setActiveHand(handIn);
//                CloudBlock.placeCloud(playerIn.getPosition(), worldIn);
//                return ActionResult.resultConsume(itemstack);
//            }
//
//            @Override
//            public ActionResultType onItemUse(ItemUseContext context) {
//                CloudBlock.placeCloud(context.getPos(), context.getWorld());
//                return ActionResultType.CONSUME;
//            }
//        });

    public static final RegistryObject<Item> WRENCH = ITEMS.register("wrench", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<BlockItem> PLANE_WORKBENCH = ITEMS.register("plane_workbench", () -> new BlockItem(SimplePlanesBlocks.PLANE_WORKBENCH_BLOCK.get(), new Item.Properties().group(ITEM_GROUP)));
}
