package xyz.przemyk.simpleplanes.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.Camera;
import net.minecraft.client.CameraType;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.OverlayRegistry;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.lwjgl.glfw.GLFW;
import xyz.przemyk.simpleplanes.MathUtil;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.capability.CapClientConfigProvider;
import xyz.przemyk.simpleplanes.client.gui.*;
import xyz.przemyk.simpleplanes.client.render.PlaneItemColors;
import xyz.przemyk.simpleplanes.entities.LargePlaneEntity;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
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
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientEventHandler::planeColor);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientEventHandler::reloadTextures);
    }

    public static void clientSetup() {
        boostKey = new KeyMapping("key.plane_boost.desc", GLFW.GLFW_KEY_SPACE, "key.simpleplanes.category");
        openEngineInventoryKey = new KeyMapping("key.plane_engine_open.desc", GLFW.GLFW_KEY_X, "key.simpleplanes.category");
        dropPayloadKey = new KeyMapping("key.plane_drop_payload.desc", GLFW.GLFW_KEY_C, "key.simpleplanes.category");
        ClientRegistry.registerKeyBinding(boostKey);
        ClientRegistry.registerKeyBinding(openEngineInventoryKey);
        ClientRegistry.registerKeyBinding(dropPayloadKey);

        MenuScreens.register(SimplePlanesContainers.PLANE_WORKBENCH.get(), PlaneWorkbenchScreen::new);
        MenuScreens.register(SimplePlanesContainers.UPGRADES_REMOVAL.get(), RemoveUpgradesScreen::new);
        MenuScreens.register(SimplePlanesContainers.STORAGE.get(), StorageScreen::new);
        MenuScreens.register(SimplePlanesContainers.FURNACE_ENGINE.get(), FurnaceEngineScreen::new);
        MenuScreens.register(SimplePlanesContainers.ELECTRIC_ENGINE.get(), ElectricEngineScreen::new);

        OverlayRegistry.registerOverlayTop("Plane HUD", (gui, matrixStack, partialTicks, screenWidth, screenHeight) -> {
            Minecraft mc = Minecraft.getInstance();

            if (mc.gui instanceof ForgeIngameGui forgeIngameGui) {
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
                        int top = scaledHeight - forgeIngameGui.right_height;

                        int rowCount = Math.min(hearts, max_row_size);
                        hearts -= rowCount;

                        for (int i = 0; i < rowCount; ++i) {
                            int x = left_align - i * 16 - 16;
                            int vOffset = 35;
                            if (i + heart + 10 < health)
                                blit(matrixStack, 0, x, top, GOLD, vOffset, 16, 9);
                            else if (i + heart < health)
                                blit(matrixStack, 0, x, top, FULL, vOffset, 16, 9);
                            else
                                blit(matrixStack, 0, x, top, EMPTY, vOffset, 16, 9);
                        }
                        forgeIngameGui.right_height += 10;
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

    private static boolean playerRotationNeedToPop = false;

    public static void planeColor(ColorHandlerEvent.Item event) {
        ItemColors itemColors = event.getItemColors();
        SimplePlanesItems.getPlaneItems().forEach(item -> itemColors.register(PlaneItemColors::getColor, item));
    }

    public static void reloadTextures(TextureStitchEvent.Post event) {
        PlaneItemColors.clearCache();
    }

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

            matrixStack.translate(0, 0.7, 0);
            Quaternion quaternion = MathUtil.lerpQ(event.getPartialTick(), planeEntity.getQ_Prev(), planeEntity.getQ_Client());
            quaternion.set(quaternion.i(), -quaternion.j(), -quaternion.k(), quaternion.r());
            matrixStack.mulPose(quaternion);
            float rotationYaw = MathUtil.lerpAngle(event.getPartialTick(), entity.yRotO, entity.getYRot());

            matrixStack.mulPose(Vector3f.YP.rotationDegrees(rotationYaw));
            matrixStack.translate(0, -0.7, 0);
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
                if (mc.options.cameraType == CameraType.FIRST_PERSON) {
                    float yawDiff = planeEntity.getYRot() - planeEntity.yRotO;
                    player.setYRot(player.getYRot() + yawDiff);
                    float relativePlayerYaw = Mth.wrapDegrees(player.getYRot() - planeEntity.getYRot());
                    float clampedRelativePlayerYaw = Mth.clamp(relativePlayerYaw, -105.0F, 105.0F);

                    float diff = (clampedRelativePlayerYaw - relativePlayerYaw);
                    player.yRotO += diff;
                    player.setYRot(player.getYRot() + diff);
                    player.setYHeadRot(player.getYRot());

                    relativePlayerYaw = Mth.wrapDegrees(player.getXRot() - 0);
                    clampedRelativePlayerYaw = Mth.clamp(relativePlayerYaw, -50, 50);
                    float perc = (clampedRelativePlayerYaw - relativePlayerYaw) * 0.5f;
                    player.xRotO += perc;
                    player.setXRot(player.getXRot() + perc);
                } else {
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
    public static void onCameraSetup(EntityViewRenderEvent.CameraSetup event) {
        Camera renderInfo = event.getCamera();
        Entity entity = renderInfo.getEntity();
        if (entity instanceof LocalPlayer playerEntity && entity.getVehicle() instanceof PlaneEntity planeEntity) {
            if (renderInfo.isDetached()) {
                renderInfo.move(-renderInfo.getMaxZoom(4.0D * (planeEntity.getCameraDistanceMultiplayer() - 1.0)), 0.0D, 0.0D);
            } else {
                double partialTicks = event.getPartialTick();

                Quaternion q_prev = planeEntity.getQ_Prev();
                int max = 105;
                float diff = (float) Mth.clamp(MathUtil.wrapSubtractDegrees(planeEntity.yRotO, playerEntity.yRotO), -max, max);
                float pitch = Mth.clamp(event.getPitch(), -45, 45);
                q_prev.mul(Vector3f.YP.rotationDegrees(diff));
                q_prev.mul(Vector3f.XP.rotationDegrees(pitch));
                MathUtil.EulerAngles angles_prev = MathUtil.toEulerAngles(q_prev);

                Quaternion q_client = planeEntity.getQ_Client();
                diff = (float) Mth.clamp(MathUtil.wrapSubtractDegrees(planeEntity.getYRot(), playerEntity.getYRot()), -max, max);
                q_client.mul(Vector3f.YP.rotationDegrees(diff));
                q_client.mul(Vector3f.XP.rotationDegrees(pitch));
                MathUtil.EulerAngles angles = MathUtil.toEulerAngles(q_client);

                event.setPitch(-(float) MathUtil.lerpAngle180(partialTicks, angles_prev.pitch, angles.pitch));
                event.setYaw((float) MathUtil.lerpAngle(partialTicks, angles_prev.yaw, angles.yaw));
                event.setRoll(-(float) MathUtil.lerpAngle(partialTicks, angles_prev.roll, angles.roll));
            }
        }
    }

    public static void renderHotbarItem(PoseStack matrixStack, int x, int y, float partialTicks, ItemStack stack, Minecraft mc) {
        ItemRenderer itemRenderer = mc.getItemRenderer();
        if (!stack.isEmpty()) {
            float f = (float) stack.getUseDuration() - partialTicks;
            if (f > 0.0F) {
                matrixStack.pushPose();
                float f1 = 1.0F + f / 5.0F;
                matrixStack.translate((float) (x + 8), (float) (y + 12), 0.0F);
                matrixStack.scale(1.0F / f1, (f1 + 1.0F) / 2.0F, 1.0F);
                matrixStack.translate((float) (-(x + 8)), (float) (-(y + 12)), 0.0F);
            }

            itemRenderer.renderAndDecorateItem(stack, x, y);
            if (f > 0.0F) {
                matrixStack.popPose();
            }

            itemRenderer.renderGuiItemDecorations(mc.font, stack, x, y);
        }
    }

    public static void blit(PoseStack matrixStack, int blitOffset, int x, int y, int uOffset, int vOffset, int uWidth, int vHeight) {
        GuiComponent.blit(matrixStack, x, y, blitOffset, (float) uOffset, (float) vOffset, uWidth, vHeight, 256, 256);
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void planeInventory(ScreenOpenEvent event) {
        final LocalPlayer player = Minecraft.getInstance().player;
        if (event.getScreen() instanceof InventoryScreen && player.getVehicle() instanceof LargePlaneEntity largePlaneEntity) {
            if (largePlaneEntity.hasStorageUpgrade()) {
                event.setCanceled(true);
                SimplePlanesNetworking.INSTANCE.sendToServer(new OpenInventoryPacket());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerLogin(ClientPlayerNetworkEvent.LoggedInEvent event) {
        SimplePlanesNetworking.INSTANCE.sendToServer(new ClientConfigPacket(SimplePlanesConfig.INVERTED_CONTROLS.get()));
        LocalPlayer player = event.getPlayer();
        if (player != null) {
            player.getCapability(CapClientConfigProvider.CLIENT_CONFIG_CAP).ifPresent(cap -> cap.invertedControls = SimplePlanesConfig.INVERTED_CONTROLS.get());
        }
    }
}
