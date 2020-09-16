package xyz.przemyk.simpleplanes.BlockShip;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.serialization.Lifecycle;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.world.Dimension;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeMagnifier;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.FlatChunkGenerator;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;

import java.util.Optional;
import java.util.OptionalLong;
import java.util.function.Supplier;

public class BlockShipDim extends DimensionType {
    public static final RegistryKey<Dimension> alternate5Option = RegistryKey.func_240903_a_(
        Registry.DIMENSION_KEY,
        new ResourceLocation("simpleplanes:blockshipdim"));
    public static DimensionType surfaceTypeObject = new BlockShipDim();
    public static RegistryKey<World> dimensionRegistryKey;

    public BlockShipDim() {
        super(OptionalLong.empty(), true, false, false, true, 1.0D, false, false, true, false, true, 256, DefaultBiomeMagnifier.INSTANCE, BlockTags.INFINIBURN_OVERWORLD.getName(), Dimension.OVERWORLD.getRegistryName(), 0.0F);
    }

    public static ChunkGenerator createVoidGenerator(DynamicRegistries rm) {
        MutableRegistry<Biome> biomeRegistry = rm.func_243612_b(Registry.BIOME_KEY);

        DimensionStructuresSettings structuresConfig = new DimensionStructuresSettings(
            Optional.of(DimensionStructuresSettings.field_236192_c_),
            Maps.newHashMap(ImmutableMap.of(
//                StructureFeature.VILLAGE, StructuresConfig.DEFAULT_STRUCTURES.get(StructureFeature.VILLAGE)
            ))
        );
        FlatGenerationSettings flatChunkGeneratorConfig = new FlatGenerationSettings(structuresConfig,
            biomeRegistry);
//        flatChunkGeneratorConfig.getLayers().add(new FlatChunkGeneratorLayer(1, Blocks.BEDROCK));
        flatChunkGeneratorConfig.updateLayers();

        return new FlatChunkGenerator(flatChunkGeneratorConfig);
    }

    public static void addDimension(
        long argSeed,
        SimpleRegistry<Dimension> registry,
        RegistryKey<Dimension> key,
        RegistryKey<World> worldKey,
        Supplier<DimensionType> dimensionTypeSupplier,
        ChunkGenerator chunkGenerator
    ) {
        if (!registry.containsKey(key.getRegistryName())) {
            registry.register(
                key,
                new Dimension(
                    dimensionTypeSupplier,
                    chunkGenerator
                ),
                Lifecycle.experimental()
            );
        }
    }

    public static void addAlternateDimensions(SimpleRegistry<Dimension> registry,
                                              DynamicRegistries rm,
                                              long seed) {
        addDimension(
            seed,
            registry,
            alternate5Option,
            dimensionRegistryKey,
            () -> surfaceTypeObject,
            createVoidGenerator(rm)
        );
    }



}
