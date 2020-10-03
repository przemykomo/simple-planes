package xyz.przemyk.simpleplanes.setup;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import xyz.przemyk.simpleplanes.entities.HelicopterEntity;
import xyz.przemyk.simpleplanes.entities.LargePlaneEntity;
import xyz.przemyk.simpleplanes.entities.MegaPlaneEntity;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.items.InformationItem;
import xyz.przemyk.simpleplanes.items.PlaneItem;
import xyz.przemyk.simpleplanes.upgrades.cloud.CloudBlock;

import static xyz.przemyk.simpleplanes.SimplePlanesMod.MODID;

//@ObjectHolder(SimplePlanesMod.MODID)
@SuppressWarnings("unused")
public class SimplePlanesItems {


    public static void init() {
        for (String name :
            SimplePlanesMaterials.MATERIALS) {
            register(name + "_plane", new PlaneItem(new Item.Settings().group(SIMPLE_PLANES_ITEM_GROUP), world -> new PlaneEntity(SimplePlanesEntities.PLANE, world, SimplePlanesMaterials.getMaterial(name))));
            register(name + "_large_plane", new PlaneItem(new Item.Settings().group(SIMPLE_PLANES_ITEM_GROUP), world -> new LargePlaneEntity(SimplePlanesEntities.LARGE_PLANE, world, SimplePlanesMaterials.getMaterial(name))));
            register(name + "_helicopter", new PlaneItem(new Item.Settings().group(SIMPLE_PLANES_ITEM_GROUP), world -> new HelicopterEntity(SimplePlanesEntities.HELICOPTER, world, SimplePlanesMaterials.getMaterial(name))));
            register(name + "_mega_plane", new PlaneItem(new Item.Settings().group(SIMPLE_PLANES_ITEM_GROUP), world -> new MegaPlaneEntity(SimplePlanesEntities.MEGA_PLANE, world, SimplePlanesMaterials.getMaterial(name))));
        }
    }

    public static final ItemGroup SIMPLE_PLANES_ITEM_GROUP = FabricItemGroupBuilder.build(
        new Identifier("tutorial", "general"),
        () -> new ItemStack(Registry.ITEM.get(new Identifier(MODID, "oak_plane"))));

    public static final Item PROPELLER = register("propeller", new Item(new Item.Settings().group(SIMPLE_PLANES_ITEM_GROUP)));
    public static final Item FURNACE_ENGINE = register("furnace_engine", new Item(new Item.Settings().group(SIMPLE_PLANES_ITEM_GROUP)));

    public static final Item SPRAYER = register("sprayer", new InformationItem(new TranslatableText("description.simpleplanes.sprayer")));
    public static final Item BOOSTER = register("booster", new InformationItem(new TranslatableText("description.simpleplanes.booster")));
    public static final Item FLOATY_BEDDING = register("floaty_bedding", new InformationItem(new TranslatableText("description.simpleplanes.floaty_bedding")));
    public static final Item SHOOTER = register("shooter", new InformationItem(new TranslatableText("description.simpleplanes.shooter")));
    public static final Item FOLDING = register("folding", new InformationItem(new TranslatableText("description.simpleplanes.folding")));
    public static final Item HEALING = register("healing", new InformationItem(new TranslatableText("description.simpleplanes.healing")));
    public static final Item CLOUD = register("cloud", new InformationItem(new TranslatableText("description.simpleplanes.cloud")) {
        @Override
        public TypedActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
            ItemStack itemstack = playerIn.getStackInHand(handIn);
            playerIn.setCurrentHand(handIn);
            CloudBlock.placeCloud(playerIn.getBlockPos(), worldIn);
            return TypedActionResult.consume(itemstack);
        }

        @Override
        public ActionResult useOnBlock(ItemUsageContext context) {
            CloudBlock.placeCloud(context.getBlockPos(), context.getWorld());
            return ActionResult.CONSUME;
        }
    });
//    public static final Item CHARGER_BLOCK = register("charger_block", new BlockItem(SimplePlanesBlocks.CHARGER_BLOCK, (new Item.Settings()).group(SIMPLE_PLANES_ITEM_GROUP)));
//    public static final Item FUELING_BLOCK = register("fueling_block", new BlockItem(SimplePlanesBlocks.FUELING_BLOCK, (new Item.Settings()).group(SIMPLE_PLANES_ITEM_GROUP)));

    private static Item register(String id, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(MODID, id), item);
    }

}
