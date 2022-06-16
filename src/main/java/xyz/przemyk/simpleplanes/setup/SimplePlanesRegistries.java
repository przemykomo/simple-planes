package xyz.przemyk.simpleplanes.setup;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegistryBuilder;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.upgrades.UpgradeType;

import java.util.function.Supplier;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = SimplePlanesMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SimplePlanesRegistries {

    public static final ResourceLocation UPGRADE_TYPES_NAME = new ResourceLocation(SimplePlanesMod.MODID, "upgrade_types");
    public static Supplier<IForgeRegistry<UpgradeType>> UPGRADE_TYPES;

    @SubscribeEvent
    public static void registerRegistries(final NewRegistryEvent event) {
        UPGRADE_TYPES = event.create(new RegistryBuilder<UpgradeType>().setName(UPGRADE_TYPES_NAME));
    }
}
