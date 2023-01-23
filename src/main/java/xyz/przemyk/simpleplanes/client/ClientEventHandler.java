package xyz.przemyk.simpleplanes.client;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.CameraType;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.lwjgl.glfw.GLFW;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.client.gui.PlaneWorkbenchScreen;
import xyz.przemyk.simpleplanes.client.render.PlaneItemColors;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.misc.MathUtil;
import xyz.przemyk.simpleplanes.network.*;
import xyz.przemyk.simpleplanes.setup.SimplePlanesContainers;
import xyz.przemyk.simpleplanes.setup.SimplePlanesItems;
import xyz.przemyk.simpleplanes.upgrades.booster.BoosterUpgrade;

@Environment(EnvType.CLIENT)
public class ClientEventHandler implements ClientModInitializer {

    public static final ResourceLocation HUD_TEXTURE = new ResourceLocation(SimplePlanesMod.MODID, "textures/gui/plane_hud.png");

    public static KeyMapping moveHeliUpKey;
    public static KeyMapping openPlaneInventoryKey;
    public static KeyMapping dropPayloadKey;
    public static KeyMapping throttleUp;
    public static KeyMapping throttleDown;
    public static KeyMapping pitchUp;
    public static KeyMapping pitchDown;
    public static KeyMapping yawRight;
    public static KeyMapping yawLeft;

    @Override
    public void onInitializeClient() {
        SimplePlanesNetworking.registerS2CPackets();
        MenuScreens.register(SimplePlanesContainers.PLANE_WORKBENCH, PlaneWorkbenchScreen::new);
//        MenuScreens.register(SimplePlanesContainers.UPGRADES_REMOVAL.get(), RemoveUpgradesScreen::new);
//        MenuScreens.register(SimplePlanesContainers.STORAGE.get(), StorageScreen::new);
//        MenuScreens.register(SimplePlanesContainers.PLANE_INVENTORY.get(), PlaneInventoryScreen::new);

        PlanesModelLayers.registerRenderers();
        PlanesModelLayers.registerLayers();

        registerKeyBindings();
        ClientTickEvents.END_CLIENT_TICK.register(ClientEventHandler::onClientPlayerTick);
        HudRenderCallback.EVENT.register(ClientEventHandler::renderHUDOverlay);
        SimplePlanesItems.getPlaneItems().forEach(item -> {
            ColorProviderRegistry.ITEM.register(PlaneItemColors::getColor, item);
        });
    }

//    public static void planeColor(RegisterColorHandlersEvent.Item event) {
//        SimplePlanesItems.getPlaneItems().forEach(item -> event.register(PlaneItemColors::getColor, item));
//    }

//
//    static {
//        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
//        eventBus.addListener(ClientEventHandler::planeColor);
//        eventBus.addListener(ClientEventHandler::reloadTextures);
//        eventBus.addListener(ClientEventHandler::registerKeyBindings);
//        eventBus.addListener(ClientEventHandler::registerHUDOverlay);
//    }

    public static void registerKeyBindings() {
        moveHeliUpKey = new KeyMapping("key.move_heli_up.desc", GLFW.GLFW_KEY_SPACE, "key.simpleplanes.category");
        openPlaneInventoryKey = new KeyMapping("key.plane_inventory_open.desc", GLFW.GLFW_KEY_X, "key.simpleplanes.category");
        dropPayloadKey = new KeyMapping("key.plane_drop_payload.desc", GLFW.GLFW_KEY_C, "key.simpleplanes.category");
        throttleUp = new KeyMapping("key.plane_throttle_up.desc", GLFW.GLFW_KEY_UP, "key.simpleplanes.category");
        throttleDown = new KeyMapping("key.plane_throttle_down.desc", GLFW.GLFW_KEY_DOWN, "key.simpleplanes.category");
        pitchUp = new KeyMapping("key.plane_pitch_up.desc", GLFW.GLFW_KEY_W, "key.simpleplanes.category");
        pitchDown = new KeyMapping("key.plane_pitch_down.desc", GLFW.GLFW_KEY_S, "key.simpleplanes.category");
        yawRight = new KeyMapping("key.plane_yaw_right.desc", GLFW.GLFW_KEY_RIGHT, "key.simpleplanes.category");
        yawLeft = new KeyMapping("key.plane_yaw_left.desc", GLFW.GLFW_KEY_LEFT, "key.simpleplanes.category");
        KeyBindingHelper.registerKeyBinding(moveHeliUpKey);
        KeyBindingHelper.registerKeyBinding(openPlaneInventoryKey);
        KeyBindingHelper.registerKeyBinding(dropPayloadKey);
        KeyBindingHelper.registerKeyBinding(throttleUp);
        KeyBindingHelper.registerKeyBinding(throttleDown);
        KeyBindingHelper.registerKeyBinding(pitchUp);
        KeyBindingHelper.registerKeyBinding(pitchDown);
        KeyBindingHelper.registerKeyBinding(yawRight);
        KeyBindingHelper.registerKeyBinding(yawLeft);
    }

    public static void renderHUDOverlay(PoseStack matrixStack, float partialTicks) {
        Minecraft mc = Minecraft.getInstance();

        int scaledWidth = mc.getWindow().getGuiScaledWidth();
        int scaledHeight = mc.getWindow().getGuiScaledHeight();

        if (mc.player.getVehicle() instanceof PlaneEntity planeEntity) {
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, HUD_TEXTURE);

            int left_align = scaledWidth / 2 + 91;

            int health = (int) Math.ceil(planeEntity.getHealth());
            float healthMax = planeEntity.getMaxHealth();
            int hearts = (int) (healthMax);

            if (hearts > 10) hearts = 10;

            final int FULL = 0;
            final int EMPTY = 16;
            final int GOLD = 32;
            int max_row_size = 5;

            for (int heart = 0; hearts > 0; heart += max_row_size) {
                int top = scaledHeight - 39;

                int rowCount = Math.min(hearts, max_row_size);
                hearts -= rowCount;

                for (int i = 0; i < rowCount; ++i) {
                    int x = left_align - i * 16 - 16;
                    int vOffset = 35;
                    if (i + heart + 10 < health)
                        ClientUtil.blit(matrixStack, 0, x, top, GOLD, vOffset, 16, 9);
                    else if (i + heart < health)
                        ClientUtil.blit(matrixStack, 0, x, top, FULL, vOffset, 16, 9);
                    else
                        ClientUtil.blit(matrixStack, 0, x, top, EMPTY, vOffset, 16, 9);
                }
            }

            ClientUtil.blit(matrixStack, -90, scaledWidth - 24, scaledHeight - 42, 0, 84, 22, 40);
            int throttle = planeEntity.getThrottle();
            if (throttle > 0) {
                int throttleScaled = throttle * 28 / BoosterUpgrade.MAX_THROTTLE;
                ClientUtil.blit(matrixStack, -90, scaledWidth - 24 + 10, scaledHeight - 42 + 6 + 28 - throttleScaled, 22, 90 + 28 - throttleScaled, 2, throttleScaled);
            }

//                if (planeEntity.engineUpgrade != null) {
//                    ItemStack offhandStack = mc.player.getOffhandItem();
//                    HumanoidArm primaryHand = mc.player.getMainArm();
//                    planeEntity.engineUpgrade.renderPowerHUD(matrixStack, (primaryHand == HumanoidArm.LEFT || offhandStack.isEmpty()) ? HumanoidArm.LEFT : HumanoidArm.RIGHT, scaledWidth, scaledHeight, partialTicks);
//                }
        }
    }
//
//    public static void reloadTextures(TextureStitchEvent.Post event) {
//        PlaneItemColors.clearCache();
//    }
//
    public static void onRenderPre(LivingEntity livingEntity, float partialTicks, PoseStack matrixStack) {
        Entity entity = livingEntity.getRootVehicle();
        if (entity instanceof PlaneEntity planeEntity) {
            matrixStack.pushPose();

            matrixStack.translate(0, 0.375, 0);
            Quaternion quaternion = MathUtil.lerpQ(partialTicks, planeEntity.getQ_Prev(), planeEntity.getQ_Client());
            quaternion.set(quaternion.i(), -quaternion.j(), -quaternion.k(), quaternion.r());
            matrixStack.mulPose(quaternion);
            float rotationYaw = MathUtil.lerpAngle(partialTicks, entity.yRotO, entity.getYRot());

            matrixStack.mulPose(Vector3f.YP.rotationDegrees(rotationYaw));
            matrixStack.translate(0, -0.375, 0);

            if (MathUtil.degreesDifferenceAbs(planeEntity.rotationRoll, 0) > 90) { //TODO: Do I actually need this?
                livingEntity.yHeadRot = planeEntity.getYRot() * 2 - livingEntity.yHeadRot;
            }
            if (MathUtil.degreesDifferenceAbs(planeEntity.prevRotationRoll, 0) > 90) {
                livingEntity.yHeadRotO = planeEntity.yRotO * 2 - livingEntity.yHeadRotO;
            }
        }
    }

    public static void onRenderPost(LivingEntity livingEntity, PoseStack matrixStack) {
        Entity entity = livingEntity.getRootVehicle();
        if (entity instanceof PlaneEntity planeEntity) {
            matrixStack.popPose();

            if (MathUtil.degreesDifferenceAbs(planeEntity.rotationRoll, 0) > 90) {
                livingEntity.yHeadRot = planeEntity.getYRot() * 2 - livingEntity.yHeadRot;
            }
            if (MathUtil.degreesDifferenceAbs(planeEntity.prevRotationRoll, 0) > 90) {
                livingEntity.yHeadRotO = planeEntity.yRotO * 2 - livingEntity.yHeadRotO;
            }
        }
    }

    private static boolean oldMoveHeliUpState = false;
    private static boolean oldPitchUpState = false;
    private static boolean oldPitchDownState = false;
    private static boolean oldYawRightState = false;
    private static boolean oldYawLeftState = false;

    public static void onClientPlayerTick(Minecraft minecraft) {
        Player player = minecraft.player;
        if (player != null && player.getVehicle() instanceof PlaneEntity planeEntity) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.options.getCameraType() != CameraType.FIRST_PERSON) {
                planeEntity.applyYawToEntity(player);
            }

            if (mc.screen == null && mc.getOverlay() == null && openPlaneInventoryKey.consumeClick()) {
//                SimplePlanesNetworking.INSTANCE.sendToServer(new OpenPlaneInventoryPacket());
            } else if (dropPayloadKey.consumeClick()) {
//                for (Upgrade upgrade : planeEntity.upgrades.values()) {
//                    if (upgrade.canBeDroppedAsPayload()) {
//                        upgrade.dropAsPayload();
//                        SimplePlanesNetworking.INSTANCE.sendToServer(new DropPayloadPacket());
//                        break;
//                    }
//                }
            }

            if (throttleUp.consumeClick()) {
                ChangeThrottlePacket.send(ChangeThrottlePacket.Type.UP);
            } else if (throttleDown.consumeClick()) {
                ChangeThrottlePacket.send(ChangeThrottlePacket.Type.DOWN);
            }

            // fabric doesn't seem to support conflicting key bindings
            boolean isMoveHeliUp = InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), moveHeliUpKey.key.getValue());
            boolean isPitchUp = InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), pitchUp.key.getValue());
            boolean isPitchDown = InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), pitchDown.key.getValue());
            boolean isYawRight = yawRight.isDown();
            boolean isYawLeft = yawLeft.isDown();

            if (isMoveHeliUp != oldMoveHeliUpState) {
                MoveHeliUpPacket.send(isMoveHeliUp);
            }

            if (isPitchUp != oldPitchUpState || isPitchDown != oldPitchDownState) {
                PitchPacket.send((byte) Boolean.compare(isPitchUp, isPitchDown));
            }

            if (isYawRight != oldYawRightState || isYawLeft != oldYawLeftState) {
                YawPacket.send((byte) Boolean.compare(isYawRight, isYawLeft));
            }

            oldMoveHeliUpState = isMoveHeliUp;
            oldPitchUpState = isPitchUp;
            oldPitchDownState = isPitchDown;
            oldYawRightState = isYawRight;
            oldYawLeftState = isYawLeft;
        } else {
            oldMoveHeliUpState = false;
            oldPitchUpState = false;
            oldPitchDownState = false;
            oldYawRightState = false;
            oldYawLeftState = false;
        }
    }
//
//    //TODO: make it so player rotation variables correspond to what he is actually looking at, so that guns etc. shoot in the right direction
//    @SubscribeEvent(priority = EventPriority.LOWEST)
//    public static void onCameraSetup(ViewportEvent.ComputeCameraAngles event) {
//        Camera camera = event.getCamera();
//        Entity player = camera.getEntity();
//        if (player.getVehicle() instanceof PlaneEntity planeEntity) {
//            if (camera.isDetached()) {
//                camera.move(-camera.getMaxZoom(4.0D * (planeEntity.getCameraDistanceMultiplayer() - 1.0)), 0.0D, 0.0D);
//            } else {
//                float heightDiff = 0;
//                if (planeEntity instanceof LargePlaneEntity) {
//                    heightDiff = -0.1f;
//                }
//
//                double partialTicks = event.getPartialTick();
//
//                Quaternion qPrev = planeEntity.getQ_Prev();
//                Quaternion qNow = planeEntity.getQ_Client();
//
//                Vector3f eyePrev = new Vector3f(0, Player.DEFAULT_EYE_HEIGHT + heightDiff, 0);
//                Vector3f eyeNow = eyePrev.copy();
//                eyePrev.transform(qPrev);
//                eyeNow.transform(qNow);
//                camera.setPosition(new Vec3(Mth.lerp(partialTicks, player.xo - eyePrev.x(), player.getX() - eyeNow.x()),
//                        Mth.lerp(partialTicks, player.yo + eyePrev.y(), player.getY() + eyeNow.y()) + 0.375,
//                        Mth.lerp(partialTicks, player.zo + eyePrev.z(), player.getZ() + eyeNow.z())));
//
//                qPrev.mul(Vector3f.YP.rotationDegrees(player.yRotO));
//                qPrev.mul(Vector3f.XP.rotationDegrees(event.getPitch()));
//                MathUtil.EulerAngles eulerAnglesPrev = MathUtil.toEulerAngles(qPrev);
//
//                qNow.mul(Vector3f.YP.rotationDegrees(player.getYRot()));
//                qNow.mul(Vector3f.XP.rotationDegrees(event.getPitch()));
//                MathUtil.EulerAngles eulerAnglesNow = MathUtil.toEulerAngles(qNow);
//
//                event.setPitch(-(float) MathUtil.lerpAngle(partialTicks, eulerAnglesPrev.pitch, eulerAnglesNow.pitch));
//                event.setYaw((float) MathUtil.lerpAngle(partialTicks, eulerAnglesPrev.yaw, eulerAnglesNow.yaw));
//                event.setRoll(-(float) MathUtil.lerpAngle(partialTicks, eulerAnglesPrev.roll, eulerAnglesNow.roll));
//            }
//        }
//    }
//
//    @SubscribeEvent(priority = EventPriority.HIGH)
//    public static void planeInventory(ScreenEvent.Opening event) {
//        final LocalPlayer player = Minecraft.getInstance().player;
//        if (event.getScreen() instanceof InventoryScreen && player.getVehicle() instanceof LargePlaneEntity largePlaneEntity) {
//            if (largePlaneEntity.hasStorageUpgrade()) {
//                event.setCanceled(true);
//                SimplePlanesNetworking.INSTANCE.sendToServer(new OpenInventoryPacket());
//            }
//        }
//    }
}
