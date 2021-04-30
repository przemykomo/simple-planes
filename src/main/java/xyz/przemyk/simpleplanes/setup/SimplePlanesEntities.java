package xyz.przemyk.simpleplanes.setup;

import com.google.common.collect.ImmutableSet;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.*;

@SuppressWarnings("unused")
public class SimplePlanesEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, SimplePlanesMod.MODID);

    public static void init() {
        ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final RegistryObject<EntityType<PlaneEntity>> PLANE = ENTITIES.register("plane", () -> createEntityType(PlaneEntity::new, EntitySize.scalable(2F, 1.5F)));
    public static final RegistryObject<EntityType<LargePlaneEntity>> LARGE_PLANE = ENTITIES.register("large_plane", () -> createEntityType(LargePlaneEntity::new, EntitySize.scalable(2F, 1.5F)));
    public static final RegistryObject<EntityType<HelicopterEntity>> HELICOPTER = ENTITIES.register("helicopter", () -> createEntityType(HelicopterEntity::new, EntitySize.scalable(2F, 1.5F)));

    private static <T extends PlaneEntity> EntityType<T> createEntityType(EntityType.IFactory<T> factory, EntitySize size) {
        return new EntityType<>(factory, EntityClassification.MISC, true, true, false, true, ImmutableSet.of(), size, 5, 3);
    }
}
