package xyz.przemyk.simpleplanes.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.Camera;
import net.minecraft.client.CameraType;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.lwjgl.glfw.GLFW;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.capability.CapClientConfigProvider;
import xyz.przemyk.simpleplanes.client.gui.*;
import xyz.przemyk.simpleplanes.client.render.PlaneItemColors;
import xyz.przemyk.simpleplanes.entities.LargePlaneEntity;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.misc.MathUtil;
import xyz.przemyk.simpleplanes.network.*;
import xyz.przemyk.simpleplanes.setup.SimplePlanesConfig;
import xyz.przemyk.simpleplanes.setup.SimplePlanesContainers;
import xyz.przemyk.simpleplanes.setup.SimplePlanesItems;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ClientEventHandler {

    public static final ResourceLocation HUD_TEXTURE = new ResourceLocation(SimplePlanesMod.MODID, "textures/gui/plane_hud.png");

    public static KeyMapping boostKey;
    public static KeyMapping openEngineInventoryKey;
    public static KeyMapping dropPayloadKey;

    static {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(ClientEventHandler::planeColor);
        eventBus.addListener(ClientEventHandler::reloadTextures);
        eventBus.addListener(ClientEventHandler::registerKeyBindings);
        eventBus.addListener(ClientEventHandler::registerHUDOverlay);
    }

    public static void clientSetup() {
        MenuScreens.register(SimplePlanesContainers.PLANE_WORKBENCH.get(), PlaneWorkbenchScreen::new);
        MenuScreens.register(SimplePlanesContainers.UPGRADES_REMOVAL.get(), RemoveUpgradesScreen::new);
        MenuScreens.register(SimplePlanesContainers.STORAGE.get(), StorageScreen::new);
        MenuScreens.register(SimplePlanesContainers.FURNACE_ENGINE.get(), FurnaceEngineScreen::new);
        MenuScreens.register(SimplePlanesContainers.ELECTRIC_ENGINE.get(), ElectricEngineScreen::new);
    }

    public static void registerKeyBindings(RegisterKeyMappingsEvent event) {
        boostKey = new KeyMapping("key.plane_boost.desc", GLFW.GLFW_KEY_SPACE, "key.simpleplanes.category");
        openEngineInventoryKey = new KeyMapping("key.plane_engine_open.desc", GLFW.GLFW_KEY_X, "key.simpleplanes.category");
        dropPayloadKey = new KeyMapping("key.plane_drop_payload.desc", GLFW.GLFW_KEY_C, "key.simpleplanes.category");
        event.register(boostKey);
        event.register(openEngineInventoryKey);
        event.register(dropPayloadKey);
    }

    public static void registerHUDOverlay(RegisterGuiOverlaysEvent event) {
        event.registerAboveAll("plane_hud", (gui, matrixStack, partialTicks, screenWidth, screenHeight) -> {
            Minecraft mc = Minecraft.getInstance();

            if (mc.gui instanceof ForgeGui forgeGui) {
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
                        int top = scaledHeight - forgeGui.rightHeight;

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
                        forgeGui.rightHeight += 10;
                    }

                    if (planeEntity.engineUpgrade != null) {
                        ItemStack offhandStack = mc.player.getOffhandItem();
                        HumanoidArm primaryHand = mc.player.getMainArm();
                        planeEntity.engineUpgrade.renderPowerHUD(matrixStack, (primaryHand == HumanoidArm.LEFT || offhandStack.isEmpty()) ? HumanoidArm.LEFT : HumanoidArm.RIGHT, scaledWidth, scaledHeight, partialTicks);
                    }
                }
            }
        });
    }

    public static void planeColor(RegisterColorHandlersEvent.Item event) {
        SimplePlanesItems.getPlaneItems().forEach(item -> event.register(PlaneItemColors::getColor, item));
    }

    public static void reloadTextures(TextureStitchEvent.Post event) {
        PlaneItemColors.clearCache();
    }


    private static boolean playerRotationNeedToPop = false;

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onRenderPre(RenderLivingEvent.Pre<LivingEntity, ?> event) {
        LivingEntity livingEntity = event.getEntity();
        Entity entity = livingEntity.getRootVehicle();
        if (entity instanceof PlaneEntity planeEntity) {
            PoseStack matrixStack = event.getPoseStack();
            matrixStack.pushPose();
            playerRotationNeedToPop = true;
            double firstPersonYOffset = 0.7D;
            boolean isPlayerRidingInFirstPersonView = Minecraft.getInstance().player != null && planeEntity.hasPassenger(Minecraft.getInstance().player)
                && (Minecraft.getInstance()).options.cameraType == CameraType.FIRST_PERSON;
            if (isPlayerRidingInFirstPersonView) {
                matrixStack.translate(0.0D, firstPersonYOffset, 0.0D);
            }

            matrixStack.translate(0, 0.35, 0);
            Quaternion quaternion = MathUtil.lerpQ(event.getPartialTick(), planeEntity.getQ_Prev(), planeEntity.getQ_Client());
            quaternion.set(quaternion.i(), -quaternion.j(), -quaternion.k(), quaternion.r());
            matrixStack.mulPose(quaternion);
            float rotationYaw = MathUtil.lerpAngle(event.getPartialTick(), entity.yRotO, entity.getYRot());

            matrixStack.mulPose(Vector3f.YP.rotationDegrees(rotationYaw));
            matrixStack.translate(0, -0.35, 0);
            if (isPlayerRidingInFirstPersonView) {
                matrixStack.translate(0.0D, -firstPersonYOffset, 0.0D);
            }
            if (MathUtil.degreesDifferenceAbs(planeEntity.rotationRoll, 0) > 90) {
                livingEntity.yHeadRot = planeEntity.getYRot() * 2 - livingEntity.yHeadRot;
            }
            if (MathUtil.degreesDifferenceAbs(planeEntity.prevRotationRoll, 0) > 90) {
                livingEntity.yHeadRotO = planeEntity.yRotO * 2 - livingEntity.yHeadRotO;
            }
        }
    }

    @SuppressWarnings("rawtypes")
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onRenderPost(RenderLivingEvent.Post event) {
        if (playerRotationNeedToPop) {
            playerRotationNeedToPop = false;
            event.getPoseStack().popPose();
            Entity entity = event.getEntity().getRootVehicle();
            PlaneEntity planeEntity = (PlaneEntity) entity;

            if (MathUtil.degreesDifferenceAbs(planeEntity.rotationRoll, 0) > 90) {
                event.getEntity().yHeadRot = planeEntity.getYRot() * 2 - event.getEntity().yHeadRot;
            }
            if (MathUtil.degreesDifferenceAbs(planeEntity.prevRotationRoll, 0) > 90) {
                event.getEntity().yHeadRotO = planeEntity.yRotO * 2 - event.getEntity().yHeadRotO;
            }
        }
    }

    private static boolean oldBoostState = false;

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onClientPlayerTick(PlayerTickEvent event) {
        final Player player = event.player;
        if ((event.phase == Phase.END) && (player instanceof LocalPlayer)) {
            if (player.getVehicle() instanceof PlaneEntity planeEntity) {
                Minecraft mc = Minecraft.getInstance();
                if (mc.options.cameraType != CameraType.FIRST_PERSON) {
                    planeEntity.applyYawToEntity(player);
                }

                if (planeEntity.engineUpgrade != null && mc.screen == null && mc.getOverlay() == null && openEngineInventoryKey.consumeClick() && planeEntity.engineUpgrade.canOpenGui()) {
                    SimplePlanesNetworking.INSTANCE.sendToServer(new OpenEngineInventoryPacket());
                } else if (dropPayloadKey.consumeClick()) {
                    for (Upgrade upgrade : planeEntity.upgrades.values()) {
                        if (upgrade.canBeDroppedAsPayload()) {
                            upgrade.dropAsPayload();
                            SimplePlanesNetworking.INSTANCE.sendToServer(new DropPayloadPacket());
                            break;
                        }
                    }
                }

                boolean isBoosting = boostKey.isDown();
                if (isBoosting != oldBoostState || Math.random() < 0.1) {
                    SimplePlanesNetworking.INSTANCE.sendToServer(new BoostPacket(isBoosting));
                }
                oldBoostState = isBoosting;
            } else {
                oldBoostState = false;
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onCameraSetup(ViewportEvent.ComputeCameraAngles event) {
        Camera camera = event.getCamera();
        Entity player = camera.getEntity();
        if (player.getVehicle() instanceof PlaneEntity planeEntity) {
            if (camera.isDetached()) {
                camera.move(-camera.getMaxZoom(4.0D * (planeEntity.getCameraDistanceMultiplayer() - 1.0)), 0.0D, 0.0D);
            } else {
                //TODO: MathUtil.lerpQ?
                double partialTicks = event.getPartialTick();

                Quaternion qPrev = planeEntity.getQ_Prev();
                Quaternion qNow = planeEntity.getQ_Client();

                //TODO: prev and now vectors should use prev and now pos respectively
                Vector3f playerEyesRelativeToPlane = new Vector3f((float) (player.getX() - planeEntity.getX()),
                        (float) (player.getY() - planeEntity.getY() + Player.DEFAULT_EYE_HEIGHT - 0.075f),
                        (float) (player.getZ() - planeEntity.getZ()));

                Vector3f rotationPrev = new Vector3f(playerEyesRelativeToPlane.x(), playerEyesRelativeToPlane.y(), playerEyesRelativeToPlane.z());
                rotationPrev.transform(qPrev);

                Vector3f rotationNow = new Vector3f(playerEyesRelativeToPlane.x(), playerEyesRelativeToPlane.y(), playerEyesRelativeToPlane.z());
                rotationNow.transform(qNow);

                camera.setPosition(new Vec3(Mth.lerp(partialTicks, player.xo - rotationPrev.x(), player.getX() - rotationNow.x()),
                        Mth.lerp(partialTicks, player.yo + rotationPrev.y(), player.getY() + rotationNow.y()) + 0.43f,
                        Mth.lerp(partialTicks, player.zo + rotationPrev.z(), player.getZ() + rotationNow.z())));

                qPrev.mul(Vector3f.YP.rotationDegrees(player.yRotO));
                qPrev.mul(Vector3f.XP.rotationDegrees(event.getPitch()));
                MathUtil.EulerAngles eulerAnglesPrev = MathUtil.toEulerAngles(qPrev);

                qNow.mul(Vector3f.YP.rotationDegrees(player.getYRot()));
                qNow.mul(Vector3f.XP.rotationDegrees(event.getPitch()));
                MathUtil.EulerAngles eulerAnglesNow = MathUtil.toEulerAngles(qNow);

                event.setPitch(-(float) MathUtil.lerpAngle(partialTicks, eulerAnglesPrev.pitch, eulerAnglesNow.pitch));
                event.setYaw((float) MathUtil.lerpAngle(partialTicks, eulerAnglesPrev.yaw, eulerAnglesNow.yaw));
                event.setRoll(-(float) MathUtil.lerpAngle(partialTicks, eulerAnglesPrev.roll, eulerAnglesNow.roll));
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void planeInventory(ScreenEvent.Opening event) {
        final LocalPlayer player = Minecraft.getInstance().player;
        if (event.getScreen() instanceof InventoryScreen && player.getVehicle() instanceof LargePlaneEntity largePlaneEntity) {
            if (largePlaneEntity.hasStorageUpgrade()) {
                event.setCanceled(true);
                SimplePlanesNetworking.INSTANCE.sendToServer(new OpenInventoryPacket());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerLogin(ClientPlayerNetworkEvent.LoggingIn event) {
        SimplePlanesNetworking.INSTANCE.sendToServer(new ClientConfigPacket(SimplePlanesConfig.INVERTED_CONTROLS.get()));
        LocalPlayer player = event.getPlayer();
        if (player != null) {
            player.getCapability(CapClientConfigProvider.CLIENT_CONFIG_CAP).ifPresent(cap -> cap.invertedControls = SimplePlanesConfig.INVERTED_CONTROLS.get());
        }
    }
}
