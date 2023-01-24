package xyz.przemyk.simpleplanes.setup;

import com.google.common.collect.ImmutableSet;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.*;

@SuppressWarnings("unused")
public class SimplePlanesEntities {

    public static void init() {

    }

    public static final EntityType<PlaneEntity> PLANE = Registry.register(Registry.ENTITY_TYPE, new ResourceLocation(SimplePlanesMod.MODID, "plane"), createEntityType(PlaneEntity::new, EntityDimensions.scalable(2.5F, 1.8F)));
    public static final EntityType<LargePlaneEntity> LARGE_PLANE = Registry.register(Registry.ENTITY_TYPE, new ResourceLocation(SimplePlanesMod.MODID, "large_plane"), createEntityType(LargePlaneEntity::new, EntityDimensions.scalable(3F, 2.3F)));
    public static final EntityType<HelicopterEntity> HELICOPTER = Registry.register(Registry.ENTITY_TYPE, new ResourceLocation(SimplePlanesMod.MODID, "helicopter"), createEntityType(HelicopterEntity::new, EntityDimensions.scalable(2.5F, 2.2F)));

    public static final EntityType<ParachuteEntity> PARACHUTE = Registry.register(Registry.ENTITY_TYPE, new ResourceLocation(SimplePlanesMod.MODID, "parachute"), createEntityType(ParachuteEntity::new, EntityDimensions.scalable(1.0F, 1.0F)));

    private static <T extends Entity> EntityType<T> createEntityType(EntityType.EntityFactory<T> factory, EntityDimensions size) {
        return new EntityType<>(factory, MobCategory.MISC, true, true, false, true, ImmutableSet.of(), size, 5, 3);
    }
}
