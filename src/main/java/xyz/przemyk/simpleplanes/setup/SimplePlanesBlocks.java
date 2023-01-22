package xyz.przemyk.simpleplanes.setup;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.blocks.ChargingStationBlock;
import xyz.przemyk.simpleplanes.blocks.ChargingStationTile;
import xyz.przemyk.simpleplanes.blocks.PlaneWorkbenchBlock;
import xyz.przemyk.simpleplanes.blocks.PlaneWorkbenchBlockEntity;

@SuppressWarnings("unused")
public class SimplePlanesBlocks {

    public static void init() {

    }

    public static final PlaneWorkbenchBlock PLANE_WORKBENCH_BLOCK = Registry.register(Registry.BLOCK, new ResourceLocation(SimplePlanesMod.MODID, "plane_workbench"), new PlaneWorkbenchBlock(BlockBehaviour.Properties.copy(Blocks.CRAFTING_TABLE)));
    public static final ChargingStationBlock CHARGING_STATION_BLOCK = Registry.register(Registry.BLOCK, new ResourceLocation(SimplePlanesMod.MODID, "charging_station"), new ChargingStationBlock(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE)));

    public static final BlockEntityType<PlaneWorkbenchBlockEntity> PLANE_WORKBENCH_TILE = Registry.register(Registry.BLOCK_ENTITY_TYPE, new ResourceLocation(SimplePlanesMod.MODID, "plane_workbench"), FabricBlockEntityTypeBuilder.create(PlaneWorkbenchBlockEntity::new, PLANE_WORKBENCH_BLOCK).build());
    public static final BlockEntityType<ChargingStationTile> CHARGING_STATION_TILE = Registry.register(Registry.BLOCK_ENTITY_TYPE, new ResourceLocation(SimplePlanesMod.MODID, "charging_station"), FabricBlockEntityTypeBuilder.create(ChargingStationTile::new, CHARGING_STATION_BLOCK).build());
}
