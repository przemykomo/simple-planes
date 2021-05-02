package xyz.przemyk.simpleplanes.setup;

import com.google.common.collect.ImmutableSet;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.blocks.ChargingStationBlock;
import xyz.przemyk.simpleplanes.blocks.ChargingStationTile;
import xyz.przemyk.simpleplanes.blocks.PlaneWorkbenchBlock;

@SuppressWarnings("unused")
public class SimplePlanesBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, SimplePlanesMod.MODID);
    public static final DeferredRegister<TileEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, SimplePlanesMod.MODID);

    public static void init() {
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        TILES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final RegistryObject<PlaneWorkbenchBlock> PLANE_WORKBENCH_BLOCK = BLOCKS.register("plane_workbench", () -> new PlaneWorkbenchBlock(AbstractBlock.Properties.copy(Blocks.CRAFTING_TABLE)));
    public static final RegistryObject<ChargingStationBlock> CHARGING_STATION_BLOCK = BLOCKS.register("charging_station", () -> new ChargingStationBlock(AbstractBlock.Properties.copy(Blocks.COBBLESTONE)));

    public static final RegistryObject<TileEntityType<ChargingStationTile>> CHARGING_STATION_TILE = TILES.register("charging_station", () -> new TileEntityType<>(ChargingStationTile::new, ImmutableSet.of(CHARGING_STATION_BLOCK.get()), null));
}
