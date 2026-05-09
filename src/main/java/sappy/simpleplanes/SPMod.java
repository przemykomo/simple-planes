package sappy.simpleplanes;

import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import sappy.simpleplanes.network.SPNetwork;

@Mod(SPMod.MODID)
public class SPMod {

    public static final String MODID = "simpleplanes";

    public SPMod(IEventBus bus, ModContainer modContainer) {
        SPConfig.init(modContainer);
        SPItems.init(bus);
        SPEntities.init(bus);
        SPSounds.init(bus);
        bus.addListener(SPNetwork::register);
        bus.addListener(SPRegistries::register);
    }

    public static Identifier texture(String filename) {
        return Identifier.fromNamespaceAndPath(MODID, "textures/plane_upgrades/" + filename);
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MODID, path);
    }
}
