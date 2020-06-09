package xyz.przemyk.simpleplanes;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.przemyk.simpleplanes.entities.FurnacePlaneEntity;
import xyz.przemyk.simpleplanes.items.FurnacePlaneItem;

import java.util.function.BiFunction;

@SuppressWarnings("unused")
public class SimplePlanesRegistries {

    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, SimplePlanesMod.MODID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = new DeferredRegister<>(ForgeRegistries.ENTITIES, SimplePlanesMod.MODID);

    public static void init() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final RegistryObject<FurnacePlaneItem> FURNACE_PLANE_ITEM = ITEMS.register("furnace_plane", () -> new FurnacePlaneItem(new Item.Properties().group(ItemGroup.TRANSPORTATION)));

    public static final RegistryObject<EntityType<FurnacePlaneEntity>> FURNACE_PLANE_ENTITY = registerEntity("furnace_plane", FurnacePlaneEntity::new);

    private static <T extends Entity> RegistryObject<EntityType<T>> registerEntity(String id, BiFunction<EntityType<T>, World, T> function)  {
        EntityType<T> type = EntityType.Builder.create(function::apply, EntityClassification.MISC).size(1, 1).setShouldReceiveVelocityUpdates(true).build(id);
        return ENTITIES.register(id, () -> type);
    }
}
