package xyz.przemyk.simpleplanes.setup;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.HelicopterEntity;
import xyz.przemyk.simpleplanes.entities.LargePlaneEntity;
import xyz.przemyk.simpleplanes.entities.MegaPlaneEntity;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.items.InformationItem;
import xyz.przemyk.simpleplanes.items.PlaneItem;
import xyz.przemyk.simpleplanes.blocks.CloudBlock;
import xyz.przemyk.simpleplanes.upgrades.UpgradeItem;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = SimplePlanesMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SimplePlanesItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SimplePlanesMod.MODID);


    public static void init() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final ItemGroup ITEM_GROUP = new ItemGroup("simpleplanes") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(PLANE_ITEM.get());
        }
    };

    public static final RegistryObject<PlaneItem> PLANE_ITEM = ITEMS.register("plane", () -> new PlaneItem(new Item.Properties().group(ITEM_GROUP), world -> new PlaneEntity(SimplePlanesEntities.PLANE.get(), world)));
    public static final RegistryObject<PlaneItem> LARGE_PLANE_ITEM = ITEMS.register("large_plane", () -> new PlaneItem(new Item.Properties().group(ITEM_GROUP), world -> new LargePlaneEntity(SimplePlanesEntities.LARGE_PLANE.get(), world)));
    public static final RegistryObject<PlaneItem> HELICOPTER_ITEM = ITEMS.register("helicopter", () -> new PlaneItem(new Item.Properties().group(ITEM_GROUP), world -> new HelicopterEntity(SimplePlanesEntities.HELICOPTER.get(), world)));
    public static final RegistryObject<PlaneItem> MEGA_PLANE_ITEM = ITEMS.register("mega_plane", () -> new PlaneItem(new Item.Properties().group(ITEM_GROUP), world -> new MegaPlaneEntity(SimplePlanesEntities.MEGA_PLANE.get(), world)));

    public static final RegistryObject<Item> PROPELLER = ITEMS.register("propeller", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> FURNACE_ENGINE = ITEMS
        .register("furnace_engine", () -> new Item(new Item.Properties().group(ITEM_GROUP)));

    public static final RegistryObject<UpgradeItem> FLOATY_BEDDING = ITEMS.register("floaty_bedding", () -> new UpgradeItem(new Item.Properties().group(ITEM_GROUP), SimplePlanesUpgrades.FLOATING));
    public static final RegistryObject<UpgradeItem> BOOSTER = ITEMS.register("booster", () -> new UpgradeItem(new Item.Properties().group(ITEM_GROUP), SimplePlanesUpgrades.BOOSTER));

    //TODO: make all upgrades actually work
    public static final RegistryObject<InformationItem> SPRAYER = ITEMS.register("sprayer", () -> new InformationItem(new TranslationTextComponent("description.simpleplanes.sprayer")));
//    public static final RegistryObject<InformationItem> BOOSTER = ITEMS.register("booster", () -> new InformationItem(new TranslationTextComponent("description.simpleplanes.booster")));
    public static final RegistryObject<InformationItem> SHOOTER = ITEMS.register("shooter", () -> new InformationItem(new TranslationTextComponent("description.simpleplanes.shooter")));
    public static final RegistryObject<InformationItem> FOLDING = ITEMS.register("folding", () -> new InformationItem(new TranslationTextComponent("description.simpleplanes.folding")));
    public static final RegistryObject<InformationItem> HEALING = ITEMS.register("healing", () -> new InformationItem(new TranslationTextComponent("description.simpleplanes.healing")));
    public static final RegistryObject<InformationItem> CLOUD = ITEMS
        .register("cloud", () -> new InformationItem(new TranslationTextComponent("description.simpleplanes.cloud")) {
            @Override
            public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
                ItemStack itemstack = playerIn.getHeldItem(handIn);
                playerIn.setActiveHand(handIn);
                CloudBlock.placeCloud(playerIn.getPosition(), worldIn);
                return ActionResult.resultConsume(itemstack);
            }

            @Override
            public ActionResultType onItemUse(ItemUseContext context) {
                CloudBlock.placeCloud(context.getPos(), context.getWorld());
                return ActionResultType.CONSUME;
            }
        });

    public static final RegistryObject<BlockItem> PLANE_WORKBENCH = ITEMS.register("plane_workbench", () -> new BlockItem(SimplePlanesBlocks.PLANE_WORKBENCH_BLOCK.get(), new Item.Properties().group(ITEM_GROUP)));
}
