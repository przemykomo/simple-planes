package xyz.przemyk.simpleplanes.client;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.*;
import org.lwjgl.glfw.GLFW;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.client.gui.ModifyUpgradesScreen;
import xyz.przemyk.simpleplanes.client.gui.PlaneInventoryScreen;
import xyz.przemyk.simpleplanes.client.gui.PlaneWorkbenchScreen;
import xyz.przemyk.simpleplanes.client.gui.StorageScreen;
import xyz.przemyk.simpleplanes.client.render.PlaneItemColors;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesContainers;
import xyz.przemyk.simpleplanes.setup.SimplePlanesItems;
import xyz.przemyk.simpleplanes.upgrades.booster.BoosterUpgrade;

@EventBusSubscriber(value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class ModBusClientEventHandler {

    public static final ResourceLocation HUD_TEXTURE = ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "textures/gui/plane_hud.png");

    @SubscribeEvent
    public static void registerMenuScreens(RegisterMenuScreensEvent event) {
        event.register(SimplePlanesContainers.PLANE_WORKBENCH.get(), PlaneWorkbenchScreen::new);
        event.register(SimplePlanesContainers.UPGRADES_REMOVAL.get(), ModifyUpgradesScreen::new);
        event.register(SimplePlanesContainers.STORAGE.get(), StorageScreen::new);
        event.register(SimplePlanesContainers.PLANE_INVENTORY.get(), PlaneInventoryScreen::new);
    }

    @SubscribeEvent
    public static void planeColor(RegisterColorHandlersEvent.Item event) {
        SimplePlanesItems.getPlaneItems().forEach(item -> event.register(PlaneItemColors::getColor, item));
    }

    @SubscribeEvent
    public static void reloadTextures(TextureAtlasStitchedEvent event) {
        PlaneItemColors.clearCache();
    }

    @SubscribeEvent
    public static void registerKeyBindings(RegisterKeyMappingsEvent event) {
        ClientEventHandler.moveHeliUpKey = new KeyMapping("key.move_heli_up.desc", GLFW.GLFW_KEY_SPACE, "key.simpleplanes.category");
        ClientEventHandler.openPlaneInventoryKey = new KeyMapping("key.plane_inventory_open.desc", GLFW.GLFW_KEY_X, "key.simpleplanes.category");
        ClientEventHandler.dropPayloadKey = new KeyMapping("key.plane_drop_payload.desc", GLFW.GLFW_KEY_C, "key.simpleplanes.category");
        ClientEventHandler.throttleUp = new KeyMapping("key.plane_throttle_up.desc", GLFW.GLFW_KEY_UP, "key.simpleplanes.category");
        ClientEventHandler.throttleDown = new KeyMapping("key.plane_throttle_down.desc", GLFW.GLFW_KEY_DOWN, "key.simpleplanes.category");
        ClientEventHandler.pitchUp = new KeyMapping("key.plane_pitch_up.desc", GLFW.GLFW_KEY_W, "key.simpleplanes.category");
        ClientEventHandler.pitchDown = new KeyMapping("key.plane_pitch_down.desc", GLFW.GLFW_KEY_S, "key.simpleplanes.category");
        ClientEventHandler.yawRight = new KeyMapping("key.plane_yaw_right.desc", GLFW.GLFW_KEY_RIGHT, "key.simpleplanes.category");
        ClientEventHandler.yawLeft = new KeyMapping("key.plane_yaw_left.desc", GLFW.GLFW_KEY_LEFT, "key.simpleplanes.category");
        event.register(ClientEventHandler.moveHeliUpKey);
        event.register(ClientEventHandler.openPlaneInventoryKey);
        event.register(ClientEventHandler.dropPayloadKey);
        event.register(ClientEventHandler.throttleUp);
        event.register(ClientEventHandler.throttleDown);
        event.register(ClientEventHandler.pitchUp);
        event.register(ClientEventHandler.pitchDown);
        event.register(ClientEventHandler.yawRight);
        event.register(ClientEventHandler.yawLeft);
    }

    @SubscribeEvent
    public static void registerHUDOverlay(RegisterGuiLayersEvent event) {
        event.registerAboveAll(ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "plane_hud"), (guiGraphics, deltaTracker) -> {
            Minecraft mc = Minecraft.getInstance();

            int scaledWidth = mc.getWindow().getGuiScaledWidth();
            int scaledHeight = mc.getWindow().getGuiScaledHeight();

            if (mc.player.getVehicle() instanceof PlaneEntity planeEntity) {
                int left_align = scaledWidth / 2 + 91;

                int health = planeEntity.getHealth();
                float healthMax = planeEntity.getMaxHealth();
                int hearts = (int) (healthMax);

                if (hearts > 10) hearts = 10;

                final int FULL = 0;
                final int EMPTY = 16;
                final int GOLD = 32;
                int max_row_size = 5;

                for (int heart = 0; hearts > 0; heart += max_row_size) {
                    int top = scaledHeight - mc.gui.rightHeight;

                    int rowCount = Math.min(hearts, max_row_size);
                    hearts -= rowCount;

                    for (int i = 0; i < rowCount; ++i) {
                        int x = left_align - i * 16 - 16;
                        int vOffset = 35;
                        if (i + heart + 10 < health)
                            guiGraphics.blit(HUD_TEXTURE, x, top, GOLD, vOffset, 16, 9);
                        else if (i + heart < health)
                            guiGraphics.blit(HUD_TEXTURE, x, top, FULL, vOffset, 16, 9);
                        else
                            guiGraphics.blit(HUD_TEXTURE, x, top, EMPTY, vOffset, 16, 9);
                    }
                    mc.gui.rightHeight += 10;

                    guiGraphics.blit(HUD_TEXTURE, scaledWidth - 24, scaledHeight - 42, 0, 84, 22, 40);
                    int throttle = planeEntity.getThrottle();
                    if (throttle > 0) {
                        int throttleScaled = throttle * 28 / BoosterUpgrade.MAX_THROTTLE;
                        guiGraphics.blit(HUD_TEXTURE, scaledWidth - 24 + 10, scaledHeight - 42 + 6 + 28 - throttleScaled, 22, 90 + 28 - throttleScaled, 2, throttleScaled);
                    }

                    if (planeEntity.engineUpgrade != null) {
                        ItemStack offhandStack = mc.player.getOffhandItem();
                        HumanoidArm primaryHand = mc.player.getMainArm();
                        planeEntity.engineUpgrade.renderPowerHUD(guiGraphics, (primaryHand == HumanoidArm.LEFT || offhandStack.isEmpty()) ? HumanoidArm.LEFT : HumanoidArm.RIGHT, scaledWidth, scaledHeight, deltaTracker.getGameTimeDeltaPartialTick(false));
                    }
                }
            }
        });
    }
}
