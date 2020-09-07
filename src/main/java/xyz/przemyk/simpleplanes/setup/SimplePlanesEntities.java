package xyz.przemyk.simpleplanes.setup;

import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.*;

@SuppressWarnings("unused")
public class SimplePlanesEntities
{

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, SimplePlanesMod.MODID);


    public static void init() {
        ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final RegistryObject<PlaneEntityType> PLANE = ENTITIES.register("plane", PlaneEntityType::new);
    public static final RegistryObject<LargePlaneEntityType> LARGE_PLANE = ENTITIES.register("large_plane", LargePlaneEntityType::new);
    public static final RegistryObject<HelicopterEntityType> HELICOPTER = ENTITIES.register("helicopter", HelicopterEntityType::new);

}
