package sappy.simpleplanes.client;

import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import com.google.common.reflect.TypeToken;
import net.minecraft.client.CameraType;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.CalculateDetachedCameraDistanceEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.RenderLivingEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.neoforged.neoforge.client.renderstate.RegisterRenderStateModifiersEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import sappy.simpleplanes.SPMod;
import sappy.simpleplanes.entities.PlaneEntity;
import sappy.simpleplanes.misc.MathUtil;
import sappy.simpleplanes.network.ChangeThrottlePacket;
import sappy.simpleplanes.network.PitchPacket;
import sappy.simpleplanes.network.YawPacket;

@Mod(value = SPMod.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = SPMod.MODID, value = Dist.CLIENT)
public class SPClient {

    public static KeyMapping moveHeliUpKey;
    public static KeyMapping openPlaneInventoryKey;
    public static KeyMapping dropPayloadKey;
    public static KeyMapping throttleUp;
    public static KeyMapping throttleDown;
    public static KeyMapping pitchUp;
    public static KeyMapping pitchDown;
    public static KeyMapping yawRight;
    public static KeyMapping yawLeft;

    public SPClient(IEventBus bus, ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
    }

    @SubscribeEvent
    public static void registerKeyBindings(RegisterKeyMappingsEvent event) {
        var category = new KeyMapping.Category(Identifier.fromNamespaceAndPath(SPMod.MODID, "main"));
        event.registerCategory(category);

        moveHeliUpKey = new KeyMapping("key.move_heli_up.desc", GLFW.GLFW_KEY_SPACE, category);
        openPlaneInventoryKey = new KeyMapping("key.plane_inventory_open.desc", GLFW.GLFW_KEY_X, category);
        dropPayloadKey = new KeyMapping("key.plane_drop_payload.desc", GLFW.GLFW_KEY_C, category);
        throttleUp = new KeyMapping("key.plane_throttle_up.desc", GLFW.GLFW_KEY_UP, category);
        throttleDown = new KeyMapping("key.plane_throttle_down.desc", GLFW.GLFW_KEY_DOWN, category);
        pitchUp = new KeyMapping("key.plane_pitch_up.desc", GLFW.GLFW_KEY_W, category);
        pitchDown = new KeyMapping("key.plane_pitch_down.desc", GLFW.GLFW_KEY_S, category);
        yawRight = new KeyMapping("key.plane_yaw_right.desc", GLFW.GLFW_KEY_RIGHT, category);
        yawLeft = new KeyMapping("key.plane_yaw_left.desc", GLFW.GLFW_KEY_LEFT, category);

        event.register(moveHeliUpKey);
        event.register(openPlaneInventoryKey);
        event.register(dropPayloadKey);
        event.register(throttleUp);
        event.register(throttleDown);
        event.register(pitchUp);
        event.register(pitchDown);
        event.register(yawRight);
        event.register(yawLeft);
    }

    private static boolean oldMoveHeliUpState = false;
    private static boolean oldPitchUpState = false;
    private static boolean oldPitchDownState = false;
    private static boolean oldYawRightState = false;
    private static boolean oldYawLeftState = false;

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onClientPlayerTick(PlayerTickEvent.Post event) {
        final Player player = event.getEntity();
        // if (player instanceof LocalPlayer) {
        if (player.getVehicle() instanceof PlaneEntity planeEntity) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.options.getCameraType() != CameraType.FIRST_PERSON) {
                planeEntity.applyYawToEntity(player);
            }

            if (mc.screen == null && mc.getOverlay() == null && openPlaneInventoryKey.consumeClick()) {
                // ClientPacketDistributor.sendToServer(new OpenPlaneInventoryPacket());
            } else if (dropPayloadKey.consumeClick()) {
                // planeEntity.dropPayload();
            }

            if (throttleUp.consumeClick()) {
                ClientPacketDistributor.sendToServer(new ChangeThrottlePacket(ChangeThrottlePacket.Direction.UP));
            } else if (throttleDown.consumeClick()) {
                ClientPacketDistributor.sendToServer(new ChangeThrottlePacket(ChangeThrottlePacket.Direction.DOWN));
            }

            boolean isMoveHeliUp = moveHeliUpKey.isDown();
            boolean isPitchUp = pitchUp.isDown();
            boolean isPitchDown = pitchDown.isDown();
            boolean isYawRight = yawRight.isDown();
            boolean isYawLeft = yawLeft.isDown();

            if (isMoveHeliUp != oldMoveHeliUpState) {
                // ClientPacketDistributor.sendToServer(new MoveHeliUpPacket(isMoveHeliUp));
            }

            if (isPitchUp != oldPitchUpState || isPitchDown != oldPitchDownState) {
                ClientPacketDistributor.sendToServer(new PitchPacket((byte) Boolean.compare(isPitchUp, isPitchDown)));
            }

            if (isYawRight != oldYawRightState || isYawLeft != oldYawLeftState) {
                ClientPacketDistributor.sendToServer(new YawPacket((byte) Boolean.compare(isYawRight, isYawLeft)));
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
        // }
    }

    public static final ContextKey<Boolean> IN_PLANE = new ContextKey<Boolean>(SPMod.id("in_plane"));
    public static final ContextKey<Quaternionf> QUAT = new ContextKey<Quaternionf>(SPMod.id("quat"));
    public static final ContextKey<Float> PLANE_ROT = new ContextKey<Float>(SPMod.id("plane_rot"));

    @SubscribeEvent
    public static void extractPassengerRenderState(RegisterRenderStateModifiersEvent event) {
        event.registerEntityModifier(
                new TypeToken<LivingEntityRenderer<LivingEntity, LivingEntityRenderState, ?>>() {
                },
                (entity, state) -> {
                    if (entity.getRootVehicle() instanceof PlaneEntity planeEntity) {
                        state.setRenderData(IN_PLANE, true);
                        Quaternionf quaternion = MathUtil.lerpQ(state.partialTick, planeEntity.getQuatPrev(),
                                planeEntity.getQuatClient());
                        state.setRenderData(QUAT, quaternion);
                        state.setRenderData(PLANE_ROT, planeEntity.getYRot(state.partialTick));
                    } else {
                        state.setRenderData(IN_PLANE, false);
                    }
                });
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onRenderPre(RenderLivingEvent.Pre<LivingEntity, ?, ?> event) {
        LivingEntityRenderState state = event.getRenderState();
        if (state.getRenderData(IN_PLANE) instanceof Boolean b && b) {
            PoseStack poseStack = event.getPoseStack();
            poseStack.pushPose();

            poseStack.translate(0, 0.375, 0);
            Quaternionf quaternion = state.getRenderData(QUAT);
            quaternion.set(quaternion.x(), -quaternion.y(), -quaternion.z(), quaternion.w());
            poseStack.mulPose(quaternion);

            poseStack.mulPose(Axis.YP.rotationDegrees(state.getRenderData(PLANE_ROT)));
            poseStack.translate(0, -0.375, 0);

            // if (MathUtil.degreesDifferenceAbs(planeEntity.rotationRoll, 0) > 90) { //
            // TODO: Do I actually need this?
            // livingEntity.yHeadRot = planeEntity.getYRot() * 2 - livingEntity.yHeadRot;
            // }
            // if (MathUtil.degreesDifferenceAbs(planeEntity.prevRotationRoll, 0) > 90) {
            // livingEntity.yHeadRotO = planeEntity.yRotO * 2 - livingEntity.yHeadRotO;
            // }
        }
    }

    @SuppressWarnings("rawtypes")
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onRenderPost(RenderLivingEvent.Post event) {
        LivingEntityRenderState state = event.getRenderState();
        if (state.getRenderData(IN_PLANE) instanceof Boolean b && b) {
            event.getPoseStack().popPose();

            // if (MathUtil.degreesDifferenceAbs(planeEntity.rotationRoll, 0) > 90) {
            // livingEntity.yHeadRot = planeEntity.getYRot() * 2 -
            // event.getEntity().yHeadRot;
            // }
            // if (MathUtil.degreesDifferenceAbs(planeEntity.prevRotationRoll, 0) > 90) {
            // livingEntity.yHeadRotO = planeEntity.yRotO * 2 - event.getEntity().yHeadRotO;
            // }
        }
    }

    @SubscribeEvent
    public static void onCalculateDetachedCameraDistance(CalculateDetachedCameraDistanceEvent event) {
        if (event.getCamera().entity().getVehicle() instanceof PlaneEntity planeEntity) {
            event.setDistance((float) (4.0 * planeEntity.getCameraDistanceMultiplier()));
        }
    }
}
