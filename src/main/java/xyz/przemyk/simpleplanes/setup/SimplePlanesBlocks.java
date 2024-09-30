package xyz.przemyk.simpleplanes.setup;

import com.google.common.collect.ImmutableSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.blocks.ChargingStationBlock;
import xyz.przemyk.simpleplanes.blocks.ChargingStationBlockEntity;
import xyz.przemyk.simpleplanes.blocks.PlaneWorkbenchBlock;
import xyz.przemyk.simpleplanes.blocks.PlaneWorkbenchBlockEntity;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public class SimplePlanesBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(BuiltInRegistries.BLOCK, SimplePlanesMod.MODID);
    public static final DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, SimplePlanesMod.MODID);

    public static void init(IEventBus bus) {
        BLOCKS.register(bus);
        TILES.register(bus);
    }

    public static final Supplier<PlaneWorkbenchBlock> PLANE_WORKBENCH_BLOCK = BLOCKS.register("plane_workbench", () -> new PlaneWorkbenchBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CRAFTING_TABLE)));
    public static final Supplier<ChargingStationBlock> CHARGING_STATION_BLOCK = BLOCKS.register("charging_station", () -> new ChargingStationBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE)));

    public static final Supplier<BlockEntityType<PlaneWorkbenchBlockEntity>> PLANE_WORKBENCH_TILE = TILES.register("plane_workbench", () -> new BlockEntityType<>(PlaneWorkbenchBlockEntity::new, ImmutableSet.of(PLANE_WORKBENCH_BLOCK.get()), null));
    public static final Supplier<BlockEntityType<ChargingStationBlockEntity>> CHARGING_STATION_TILE = TILES.register("charging_station", () -> new BlockEntityType<>(ChargingStationBlockEntity::new, ImmutableSet.of(CHARGING_STATION_BLOCK.get()), null));
}
