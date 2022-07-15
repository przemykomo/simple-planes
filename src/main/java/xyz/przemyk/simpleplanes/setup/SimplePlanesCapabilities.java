package xyz.przemyk.simpleplanes.setup;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.capability.CapClientConfig;
import xyz.przemyk.simpleplanes.capability.CapClientConfigProvider;

public class SimplePlanesCapabilities {

    public static void init() {
        MinecraftForge.EVENT_BUS.addGenericListener(Entity.class, SimplePlanesCapabilities::onAttachCapabilitiesPlayer);
        MinecraftForge.EVENT_BUS.addListener(SimplePlanesCapabilities::onPlayerCloned);
        MinecraftForge.EVENT_BUS.addListener(SimplePlanesCapabilities::onRegisterCapabilities);
    }

    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            if (!event.getObject().getCapability(CapClientConfigProvider.CLIENT_CONFIG_CAP).isPresent()) {
                CapClientConfigProvider capClientConfigProvider = new CapClientConfigProvider();
                event.addCapability(new ResourceLocation(SimplePlanesMod.MODID, "clientconfig"), capClientConfigProvider);
                event.addListener(capClientConfigProvider::invalidate);
            }
        }
    }

    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            event.getOriginal().getCapability(CapClientConfigProvider.CLIENT_CONFIG_CAP).ifPresent(oldCap ->
                    event.getEntity().getCapability(CapClientConfigProvider.CLIENT_CONFIG_CAP).ifPresent(newCap ->
                            newCap.copyFrom(oldCap)));
        }
    }

    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(CapClientConfig.class);
    }
}
