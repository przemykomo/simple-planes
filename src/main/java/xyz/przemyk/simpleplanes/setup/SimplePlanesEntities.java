package xyz.przemyk.simpleplanes.setup;

import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import xyz.przemyk.simpleplanes.entities.*;

import static xyz.przemyk.simpleplanes.SimplePlanesMod.MODID;

@SuppressWarnings("unused")
public class SimplePlanesEntities {

    public static PlaneEntityType PLANE = null;
    public static LargePlaneEntityType LARGE_PLANE = null;
    public static HelicopterEntityType HELICOPTER = null;
    public static MegaPlaneEntityType MEGA_PLANE = null;
    public static void init() {
        PLANE = (PlaneEntityType) register("plane", new PlaneEntityType());
        LARGE_PLANE = (LargePlaneEntityType) register("large_plane", new LargePlaneEntityType());
        HELICOPTER = (HelicopterEntityType) register("helicopter", new HelicopterEntityType());
        MEGA_PLANE = (MegaPlaneEntityType) register("mega_plane", new MegaPlaneEntityType());
    }
    private static EntityType<?> register(String id, EntityType entityType) {
        return Registry.register(Registry.ENTITY_TYPE, new Identifier(MODID, id), entityType);
    }

}
