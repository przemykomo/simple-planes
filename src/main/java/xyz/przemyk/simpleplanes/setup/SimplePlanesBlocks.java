package xyz.przemyk.simpleplanes.setup;

import com.google.common.collect.ImmutableSet;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.blocks.ChargingStationBlock;
import xyz.przemyk.simpleplanes.blocks.ChargingStationTile;
import xyz.przemyk.simpleplanes.blocks.PlaneWorkbenchBlock;
import xyz.przemyk.simpleplanes.blocks.PlaneWorkbenchBlockEntity;

@SuppressWarnings("unused")
public class SimplePlanesBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, SimplePlanesMod.MODID);
    public static final DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, SimplePlanesMod.MODID);

    public static void init() {
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        TILES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final RegistryObject<PlaneWorkbenchBlock> PLANE_WORKBENCH_BLOCK = BLOCKS.register("plane_workbench", () -> new PlaneWorkbenchBlock(BlockBehaviour.Properties.copy(Blocks.CRAFTING_TABLE)));
    public static final RegistryObject<ChargingStationBlock> CHARGING_STATION_BLOCK = BLOCKS.register("charging_station", () -> new ChargingStationBlock(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE)));

    public static final RegistryObject<BlockEntityType<PlaneWorkbenchBlockEntity>> PLANE_WORKBENCH_TILE = TILES.register("plane_workbench", () -> new BlockEntityType<>(PlaneWorkbenchBlockEntity::new, ImmutableSet.of(PLANE_WORKBENCH_BLOCK.get()), null));
    public static final RegistryObject<BlockEntityType<ChargingStationTile>> CHARGING_STATION_TILE = TILES.register("charging_station", () -> new BlockEntityType<>(ChargingStationTile::new, ImmutableSet.of(CHARGING_STATION_BLOCK.get()), null));
}
