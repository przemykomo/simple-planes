package sappy.simpleplanes;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;
import sappy.simpleplanes.upgrades.UpgradeType;

public class SPRegistries {
    public static final ResourceKey<Registry<UpgradeType>> UPGRADE_TYPE_REGISTRY_KEY = ResourceKey
            .createRegistryKey(SPMod.id("upgrade_types"));
    public static final Registry<UpgradeType> UPGRADE_TYPE = new RegistryBuilder<>(UPGRADE_TYPE_REGISTRY_KEY)
            .sync(true).create();

    public static void register(NewRegistryEvent event) {
        event.register(UPGRADE_TYPE);
    }
}
