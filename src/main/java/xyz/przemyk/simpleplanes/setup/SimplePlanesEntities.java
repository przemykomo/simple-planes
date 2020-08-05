package xyz.przemyk.simpleplanes.setup;

import net.minecraft.entity.EntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.AbstractPlaneEntityType;
import xyz.przemyk.simpleplanes.entities.HelicopterEntity;
import xyz.przemyk.simpleplanes.entities.LargePlaneEntityType;
import xyz.przemyk.simpleplanes.entities.PlaneEntityType;

@Mod.EventBusSubscriber(modid = SimplePlanesMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
@SuppressWarnings("unused")
public class SimplePlanesEntities
{

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, SimplePlanesMod.MODID);

    @SubscribeEvent
    public static void registerEntities(RegistryEvent.Register<EntityType<?>> event)
    {
        event.getRegistry().register(PLANE);
        event.getRegistry().register(LARGE_PLANE);
        event.getRegistry().register(HELICOPTER);

    }

    public static final PlaneEntityType PLANE = (PlaneEntityType) new PlaneEntityType(false).setRegistryName("plane");
    public static final LargePlaneEntityType LARGE_PLANE = (LargePlaneEntityType) new LargePlaneEntityType(false).setRegistryName("large_plane");
    public static final AbstractPlaneEntityType<HelicopterEntity> HELICOPTER = (AbstractPlaneEntityType<HelicopterEntity>) new AbstractPlaneEntityType(
            HelicopterEntity::new, false).setRegistryName("helicopter");

}
