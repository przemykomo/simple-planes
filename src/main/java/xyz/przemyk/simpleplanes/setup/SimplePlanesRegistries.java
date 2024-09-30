package xyz.przemyk.simpleplanes.setup;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.upgrades.UpgradeType;

@SuppressWarnings("unused")
@EventBusSubscriber(modid = SimplePlanesMod.MODID, bus = EventBusSubscriber.Bus.MOD)
public class SimplePlanesRegistries {

    public static final ResourceKey<Registry<UpgradeType>> UPGRADE_TYPE_REGISTRY_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "upgrade_types"));
    public static final Registry<UpgradeType> UPGRADE_TYPE = new RegistryBuilder<>(UPGRADE_TYPE_REGISTRY_KEY)
        .sync(true).create();

    @SubscribeEvent
    public static void registerRegistries(NewRegistryEvent event) {
        event.register(UPGRADE_TYPE);
    }
}
