package xyz.przemyk.simpleplanes.setup;

import com.electronwill.nightconfig.core.ConfigSpec;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.HelicopterEntityType;
import xyz.przemyk.simpleplanes.entities.LargePlaneEntityType;
import xyz.przemyk.simpleplanes.entities.MegaPlaneEntityType;
import xyz.przemyk.simpleplanes.entities.PlaneEntityType;

@SuppressWarnings("unused")
public class SimplePlanesEntities {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, SimplePlanesMod.MODID);

    public static void init() {
        ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final RegistryObject<PlaneEntityType> PLANE = ENTITIES.register("plane", PlaneEntityType::new);
    public static final RegistryObject<LargePlaneEntityType> LARGE_PLANE = ENTITIES.register("large_plane", LargePlaneEntityType::new);
    public static final RegistryObject<HelicopterEntityType> HELICOPTER = ENTITIES.register("helicopter", HelicopterEntityType::new);
    public static final RegistryObject<MegaPlaneEntityType> MEGA_PLANE = ENTITIES.register("mega_plane", MegaPlaneEntityType::new);

}
