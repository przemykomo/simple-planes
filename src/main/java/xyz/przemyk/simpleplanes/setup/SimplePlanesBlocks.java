package xyz.przemyk.simpleplanes.setup;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
//import xyz.przemyk.simpleplanes.integration.energy.ChargerBlock;
//import xyz.przemyk.simpleplanes.integration.energy.ChargerTileEntity;
//import xyz.przemyk.simpleplanes.integration.liquid.LiquidFuelingBlock;
//import xyz.przemyk.simpleplanes.integration.liquid.LiquidFuelingTileEntity;
import xyz.przemyk.simpleplanes.upgrades.cloud.CloudBlock;

import static xyz.przemyk.simpleplanes.SimplePlanesMod.MODID;

//@ObjectHolder(SimplePlanesMod.MODID)
@SuppressWarnings("unused")
public class SimplePlanesBlocks {


    public static void init() {

    }


//    public static final Block CHARGER_BLOCK = register("charger_block", new ChargerBlock());
//    public static final Block FUELING_BLOCK = register("fueling_block", new LiquidFuelingBlock());
    public static final Block CLOUD = register("cloud",new CloudBlock());
//    public static final BlockEntityType<ChargerTileEntity> CHARGER_TILE = register("charger_block_tile", BlockEntityType.Builder.create(ChargerTileEntity::new, CHARGER_BLOCK).build(null));
//    public static final BlockEntityType<LiquidFuelingTileEntity> FUELING_TILE = register("fueling_block_tile", BlockEntityType.Builder.create(LiquidFuelingTileEntity::new, FUELING_BLOCK).build(null));

    private static Block register(String id, Block block) {
        return Registry.register(Registry.BLOCK, new Identifier(MODID, id), block);
    }

    private static BlockEntityType register(String id, BlockEntityType block) {
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(MODID, id), block);
    }
}
