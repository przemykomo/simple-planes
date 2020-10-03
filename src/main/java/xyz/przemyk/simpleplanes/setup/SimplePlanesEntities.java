package xyz.przemyk.simpleplanes.setup;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import xyz.przemyk.simpleplanes.entities.HelicopterEntityType;
import xyz.przemyk.simpleplanes.entities.LargePlaneEntityType;
import xyz.przemyk.simpleplanes.entities.MegaPlaneEntityType;
import xyz.przemyk.simpleplanes.entities.PlaneEntityType;

import static xyz.przemyk.simpleplanes.SimplePlanesMod.MODID;

@SuppressWarnings("unused")
public class SimplePlanesEntities {

    public static final PlaneEntityType PLANE = Registry.register(Registry.ENTITY_TYPE, new Identifier(MODID, "plane"), new PlaneEntityType());
    public static final LargePlaneEntityType LARGE_PLANE = Registry.register(Registry.ENTITY_TYPE, new Identifier(MODID, "large_plane"), new LargePlaneEntityType());
    public static final HelicopterEntityType HELICOPTER = Registry.register(Registry.ENTITY_TYPE, new Identifier(MODID, "helicopter"), new HelicopterEntityType());
    public static final MegaPlaneEntityType MEGA_PLANE = Registry.register(Registry.ENTITY_TYPE, new Identifier(MODID, "mega_plane"), new MegaPlaneEntityType());

}
