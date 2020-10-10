package xyz.przemyk.simpleplanes.setup;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.HelicopterEntity;
import xyz.przemyk.simpleplanes.entities.LargePlaneEntity;
import xyz.przemyk.simpleplanes.entities.MegaPlaneEntity;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

import static xyz.przemyk.simpleplanes.SimplePlanesMod.MODID;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = SimplePlanesMod.MODID)
public class SimplePlanesEntities {
    private static int ID = 0;
    static EntityEntry PLANE = EntityEntryBuilder.create()
        .entity(PlaneEntity.class)
        .id(new ResourceLocation(MODID, "plane"), ID++)
        .name("plane")
        .factory(PlaneEntity::new)
        .tracker(64, 3, true)
        .build();
    static EntityEntry LARGE_PLANE = EntityEntryBuilder.create()
        .entity(LargePlaneEntity.class)
        .id(new ResourceLocation(MODID, "large_plane"), ID++)
        .name("large_plane")
        .factory(LargePlaneEntity::new)
        .tracker(64, 3, true)
        .build();
    static EntityEntry HELICOPTER = EntityEntryBuilder.create()
        .entity(HelicopterEntity.class)
        .id(new ResourceLocation(MODID, "helicopter"), ID++)
        .name("helicopter")
        .factory(HelicopterEntity::new)
        .tracker(64, 3, true)
        .build();
    static EntityEntry MEGA_PLANE = EntityEntryBuilder.create()
        .entity(MegaPlaneEntity.class)
        .id(new ResourceLocation(MODID, "mega_plane"), ID++)
        .name("mega_plane")
        .factory(MegaPlaneEntity::new)
        .tracker(64, 3, true)
        .build();

    @SubscribeEvent
    public static void registerEntities(RegistryEvent.Register<EntityEntry> event) {
        event.getRegistry().register(PLANE);
        event.getRegistry().register(LARGE_PLANE);
        event.getRegistry().register(HELICOPTER);
        event.getRegistry().register(MEGA_PLANE);
    }
}
