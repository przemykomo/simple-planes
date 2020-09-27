package xyz.przemyk.simpleplanes.setup;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.integration.energy.ChargerBlock;
import xyz.przemyk.simpleplanes.integration.energy.ChargerTileEntity;
import xyz.przemyk.simpleplanes.integration.liquid.LiquidFuelingBlock;
import xyz.przemyk.simpleplanes.integration.liquid.LiquidFuelingTileEntity;
import xyz.przemyk.simpleplanes.upgrades.cloud.CloudBlock;

//@ObjectHolder(SimplePlanesMod.MODID)
@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = SimplePlanesMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SimplePlanesBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, SimplePlanesMod.MODID);
    private static final DeferredRegister<TileEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, SimplePlanesMod.MODID);


    public static void init() {

        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        TILES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }


    public static final RegistryObject<Block> CHARGER_BLOCK = BLOCKS.register("charger_block", ChargerBlock::new);
    public static final RegistryObject<Block> FUELING_BLOCK = BLOCKS.register("fueling_block", LiquidFuelingBlock::new);
    public static final RegistryObject<Block> CLOUD = BLOCKS.register("cloud", CloudBlock::new);
    public static final RegistryObject<TileEntityType<ChargerTileEntity>> CHARGER_TILE = TILES.register("charger_block_tile", () -> TileEntityType.Builder.create(ChargerTileEntity::new, CHARGER_BLOCK.get()).build(null));
    public static final RegistryObject<TileEntityType<LiquidFuelingTileEntity>> FUELING_TILE = TILES.register("fueling_block_tile", () -> TileEntityType.Builder.create(LiquidFuelingTileEntity::new, FUELING_BLOCK.get()).build(null));
}
