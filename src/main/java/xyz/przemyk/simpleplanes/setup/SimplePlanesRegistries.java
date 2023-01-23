package xyz.przemyk.simpleplanes.setup;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.upgrades.UpgradeType;

public class SimplePlanesRegistries {

    public static final Registry<UpgradeType> UPGRADE_TYPES = FabricRegistryBuilder.createSimple(UpgradeType.class, new ResourceLocation(SimplePlanesMod.MODID, "upgrade_types")).buildAndRegister();

    public static void init() {}
}
