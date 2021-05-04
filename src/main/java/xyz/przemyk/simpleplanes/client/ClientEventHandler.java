package xyz.przemyk.simpleplanes.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.settings.PointOfView;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.*;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.lwjgl.glfw.GLFW;
import xyz.przemyk.simpleplanes.MathUtil;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.client.gui.*;
import xyz.przemyk.simpleplanes.client.render.PlaneRenderer;
import xyz.przemyk.simpleplanes.client.render.models.*;
import xyz.przemyk.simpleplanes.entities.HelicopterEntity;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.network.BoostPacket;
import xyz.przemyk.simpleplanes.network.OpenEngineInventoryPacket;
import xyz.przemyk.simpleplanes.network.OpenInventoryPacket;
import xyz.przemyk.simpleplanes.network.PlaneNetworking;
import xyz.przemyk.simpleplanes.setup.SimplePlanesContainers;
import xyz.przemyk.simpleplanes.setup.SimplePlanesEntities;
import xyz.przemyk.simpleplanes.setup.SimplePlanesItems;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ClientEventHandler {

    @OnlyIn(Dist.CLIENT)
    public static KeyBinding boostKey;
    @OnlyIn(Dist.CLIENT)
    public static KeyBinding openEngineInventoryKey;

    static {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientEventHandler::planeColor);
    }

    public static void clientSetup() {
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.PLANE.get(), manager -> new PlaneRenderer<>(manager, new PlaneModel(), new PropellerModel(), 0.6F));
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.LARGE_PLANE.get(), manager -> new PlaneRenderer<>(manager, new LargePlaneModel(), new PropellerModel(), 1.0F));
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.HELICOPTER.get(), manager -> new PlaneRenderer<>(manager, new HelicopterModel(), new HelicopterPropellerModel(), 0.6F));

        boostKey = new KeyBinding("key.plane_boost.desc", GLFW.GLFW_KEY_SPACE, "key.simpleplanes.category");
        openEngineInventoryKey = new KeyBinding("key.plane_engine_open.desc", GLFW.GLFW_KEY_X, "key.simpleplanes.category");
        ClientRegistry.registerKeyBinding(boostKey);
        ClientRegistry.registerKeyBinding(openEngineInventoryKey);

        ScreenManager.register(SimplePlanesContainers.PLANE_WORKBENCH.get(), PlaneWorkbenchScreen::new);
        ScreenManager.register(SimplePlanesContainers.UPGRADES_REMOVAL.get(), RemoveUpgradesScreen::new);
        ScreenManager.register(SimplePlanesContainers.STORAGE.get(), StorageScreen::new);
        ScreenManager.register(SimplePlanesContainers.FURNACE_ENGINE.get(), FurnaceEngineScreen::new);
        ScreenManager.register(SimplePlanesContainers.ELECTRIC_ENGINE.get(), ElectricEngineScreen::new);
    }

    private static boolean playerRotationNeedToPop = false;

    @SubscribeEvent()
    public static void planeColor(ColorHandlerEvent.Item event) {
        ItemColors itemColors = event.getItemColors();
        SimplePlanesItems.getPlaneItems().forEach(item -> {
//            IColoredMaterialItem coloredMaterialItem = (IColoredMaterialItem) item;
            itemColors.register(xyz.przemyk.simpleplanes.client.render.ItemColors::getColor, item);
        });
    }
//    TODO: reload colors
//    @SubscribeEvent()
//    public static void reloadTextures(AddReloadListenerEvent event) {
//        event.addListener((stage, resourceManager, preparationsProfiler, reloadProfiler, backgroundExecutor, gameExecutor) -> {
//            return
//        });
//    }


    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onRenderPre(RenderLivingEvent.Pre<LivingEntity, ?> event) {
        LivingEntity livingEntity = event.getEntity();
        Entity entity = livingEntity.getRootVehicle();
        if (entity instanceof PlaneEntity) {
            PlaneEntity planeEntity = (PlaneEntity) entity;
            MatrixStack matrixStack = event.getMatrixStack();
            matrixStack.pushPose();
            playerRotationNeedToPop = true;
            double firstPersonYOffset = 0.7D;
            boolean isPlayerRidingInFirstPersonView = Minecraft.getInstance().player != null && planeEntity.hasPassenger(Minecraft.getInstance().player)
                && (Minecraft.getInstance()).options.cameraType == PointOfView.FIRST_PERSON;
            if (isPlayerRidingInFirstPersonView) {
                matrixStack.translate(0.0D, firstPersonYOffset, 0.0D);
            }

            matrixStack.translate(0, 0.7, 0);
            Quaternion quaternion = MathUtil.lerpQ(event.getPartialRenderTick(), planeEntity.getQ_Prev(), planeEntity.getQ_Client());
            quaternion.set(quaternion.i(), -quaternion.j(), -quaternion.k(), quaternion.r());
            matrixStack.mulPose(quaternion);
            float rotationYaw = MathUtil.lerpAngle(event.getPartialRenderTick(), entity.yRotO, entity.yRot);

            matrixStack.mulPose(Vector3f.YP.rotationDegrees(rotationYaw));
            matrixStack.translate(0, -0.7, 0);
            if (isPlayerRidingInFirstPersonView) {
                matrixStack.translate(0.0D, -firstPersonYOffset, 0.0D);
            }
            if (MathUtil.degreesDifferenceAbs(planeEntity.rotationRoll, 0) > 90) {
                livingEntity.yHeadRot = planeEntity.yRot * 2 - livingEntity.yHeadRot;
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
            event.getMatrixStack().popPose();
            Entity entity = event.getEntity().getRootVehicle();
            PlaneEntity planeEntity = (PlaneEntity) entity;

            if (MathUtil.degreesDifferenceAbs(planeEntity.rotationRoll, 0) > 90) {
                event.getEntity().yHeadRot = planeEntity.yRot * 2 - event.getEntity().yHeadRot;
            }
            if (MathUtil.degreesDifferenceAbs(planeEntity.prevRotationRoll, 0) > 90) {
                event.getEntity().yHeadRotO = planeEntity.yRotO * 2 - event.getEntity().yHeadRotO;
            }
        }
    }

    private static boolean oldBoostState = false;

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onClientPlayerTick(PlayerTickEvent event) {
        final PlayerEntity player = event.player;
        if ((event.phase == Phase.END) && (player instanceof ClientPlayerEntity)) {
            if (player.getVehicle() instanceof PlaneEntity) {
                PlaneEntity planeEntity = (PlaneEntity) player.getVehicle();
                Minecraft mc = Minecraft.getInstance();
                if (mc.options.cameraType == PointOfView.FIRST_PERSON) {
                    float yawDiff = planeEntity.yRot - planeEntity.yRotO;
                    player.yRot += yawDiff;
                    float relativePlayerYaw = MathHelper.wrapDegrees(player.yRot - planeEntity.yRot);
                    float clampedRelativePlayerYaw = MathHelper.clamp(relativePlayerYaw, -105.0F, 105.0F);

                    float diff = (clampedRelativePlayerYaw - relativePlayerYaw);
                    player.yRotO += diff;
                    player.yRot += diff;
                    player.setYHeadRot(player.yRot);

                    relativePlayerYaw = MathHelper.wrapDegrees(player.xRot - 0);
                    clampedRelativePlayerYaw = MathHelper.clamp(relativePlayerYaw, -50, 50);
                    float perc = (clampedRelativePlayerYaw - relativePlayerYaw) * 0.5f;
                    player.xRotO += perc;
                    player.xRot += perc;
                } else {
                    planeEntity.applyYawToEntity(player);
                }

                if (planeEntity.engineUpgrade != null && mc.screen == null && mc.overlay == null && openEngineInventoryKey.consumeClick() && planeEntity.engineUpgrade.canOpenGui()) {
                    PlaneNetworking.INSTANCE.sendToServer(new OpenEngineInventoryPacket());
                }

                boolean isBoosting = boostKey.isDown();
                if (isBoosting != oldBoostState || Math.random() < 0.1) {
                    PlaneNetworking.INSTANCE.sendToServer(new BoostPacket(isBoosting));
                }
                oldBoostState = isBoosting;
            } else {
                oldBoostState = false;
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onCameraSetup(EntityViewRenderEvent.CameraSetup event) {
        ActiveRenderInfo renderInfo = event.getInfo();
        Entity entity = renderInfo.getEntity();
        if (entity instanceof ClientPlayerEntity && entity.getVehicle() instanceof PlaneEntity) {
            PlaneEntity planeEntity = (PlaneEntity) entity.getVehicle();
            ClientPlayerEntity playerEntity = (ClientPlayerEntity) entity;

            if (renderInfo.isDetached()) {
                renderInfo.move(-renderInfo.getMaxZoom(4.0D * (planeEntity.getCameraDistanceMultiplayer() - 1.0)), 0.0D, 0.0D);
            } else {
                double partialTicks = event.getRenderPartialTicks();

                Quaternion q_prev = planeEntity.getQ_Prev();
                int max = 105;
                float diff = (float) MathHelper.clamp(MathUtil.wrapSubtractDegrees(planeEntity.yRotO, playerEntity.yRotO), -max, max);
                float pitch = MathHelper.clamp(event.getPitch(), -45, 45);
                q_prev.mul(Vector3f.YP.rotationDegrees(diff));
                q_prev.mul(Vector3f.XP.rotationDegrees(pitch));
                MathUtil.EulerAngles angles_prev = MathUtil.toEulerAngles(q_prev);

                Quaternion q_client = planeEntity.getQ_Client();
                diff = (float) MathHelper.clamp(MathUtil.wrapSubtractDegrees(planeEntity.yRot, playerEntity.yRot), -max, max);
                q_client.mul(Vector3f.YP.rotationDegrees(diff));
                q_client.mul(Vector3f.XP.rotationDegrees(pitch));
                MathUtil.EulerAngles angles = MathUtil.toEulerAngles(q_client);

                event.setPitch(-(float) MathUtil.lerpAngle180(partialTicks, angles_prev.pitch, angles.pitch));
                event.setYaw((float) MathUtil.lerpAngle(partialTicks, angles_prev.yaw, angles.yaw));
                event.setRoll(-(float) MathUtil.lerpAngle(partialTicks, angles_prev.roll, angles.roll));
            }
        }
    }

    public static final ResourceLocation HUD_TEXTURE = new ResourceLocation(SimplePlanesMod.MODID, "textures/gui/plane_hud.png");

    @SubscribeEvent()
    public static void renderGameOverlayPre(RenderGameOverlayEvent.Pre event) {
        Minecraft mc = Minecraft.getInstance();
        int scaledWidth = mc.getWindow().getGuiScaledWidth();
        int scaledHeight = mc.getWindow().getGuiScaledHeight();
        MatrixStack matrixStack = event.getMatrixStack();

        if (mc.player.getVehicle() instanceof PlaneEntity) {
            PlaneEntity planeEntity = (PlaneEntity) mc.player.getVehicle();
            if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
                mc.getTextureManager().bind(HUD_TEXTURE);
                int left_align = scaledWidth / 2 + 91;

                int health = (int) Math.ceil(planeEntity.getHealth());
                float healthMax = planeEntity.getMaxHealth();
                int hearts = (int) (healthMax);

                if (hearts > 10) hearts = 10;

                final int FULL = 0;
                final int EMPTY = 16;
                final int GOLD = 32;
                int right_height = 39;
                int max_row_size = 5;

                for (int heart = 0; hearts > 0; heart += max_row_size) {
                    int top = scaledHeight - right_height;

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
                    right_height += 10;
                }

                ItemStack offhandStack = mc.player.getOffhandItem();
                HandSide primaryHand = mc.player.getMainArm();
                planeEntity.engineUpgrade.renderPowerHUD(matrixStack, (primaryHand == HandSide.LEFT || offhandStack.isEmpty()) ? HandSide.LEFT : HandSide.RIGHT, scaledWidth, scaledHeight, event.getPartialTicks());

                if (planeEntity.mountMessage) {
                    planeEntity.mountMessage = false;
                    if (planeEntity instanceof HelicopterEntity) {
                        mc.gui.setOverlayMessage(new TranslationTextComponent("helicopter.onboard", mc.options.keyShift.getTranslatedKeyMessage(),
                            boostKey.getTranslatedKeyMessage()), false);
                    } else {
                        mc.gui.setOverlayMessage(new TranslationTextComponent("plane.onboard", mc.options.keyShift.getTranslatedKeyMessage(),
                            boostKey.getTranslatedKeyMessage()), false);
                    }

                }
            } else if (event.getType() == RenderGameOverlayEvent.ElementType.FOOD) {
                event.setCanceled(true);
            }
        }
    }

    public static void renderHotbarItem(MatrixStack matrixStack, int x, int y, float partialTicks, PlayerEntity player, ItemStack stack, ItemRenderer itemRenderer, Minecraft mc) {
        if (!stack.isEmpty()) {
            float f = (float) stack.getUseDuration() - partialTicks;
            if (f > 0.0F) {
                matrixStack.pushPose();
                float f1 = 1.0F + f / 5.0F;
                matrixStack.translate((float) (x + 8), (float) (y + 12), 0.0F);
                matrixStack.scale(1.0F / f1, (f1 + 1.0F) / 2.0F, 1.0F);
                matrixStack.translate((float) (-(x + 8)), (float) (-(y + 12)), 0.0F);
            }

            itemRenderer.renderAndDecorateItem(player, stack, x, y);
            if (f > 0.0F) {
                matrixStack.popPose();
            }

            itemRenderer.renderGuiItemDecorations(mc.font, stack, x, y);
        }
    }

    public static void blit(MatrixStack matrixStack, int blitOffset, int x, int y, int uOffset, int vOffset, int uWidth, int vHeight) {
        AbstractGui.blit(matrixStack, x, y, blitOffset, (float) uOffset, (float) vOffset, uWidth, vHeight, 256, 256);
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void planeInventory(GuiOpenEvent event) {
        final ClientPlayerEntity player = Minecraft.getInstance().player;
        if (event.getGui() instanceof InventoryScreen && player.getVehicle() instanceof PlaneEntity) {
            PlaneEntity plane = (PlaneEntity) player.getVehicle();
            if (plane.upgrades.containsKey(SimplePlanesUpgrades.CHEST.getId())) {
                event.setCanceled(true);
                PlaneNetworking.INSTANCE.sendToServer(new OpenInventoryPacket());
            }
        }
    }
}
