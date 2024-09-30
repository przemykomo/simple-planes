package xyz.przemyk.simpleplanes.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.CameraType;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.CalculateDetachedCameraDistanceEvent;
import net.neoforged.neoforge.client.event.RenderLivingEvent;
import net.neoforged.neoforge.client.event.ViewportEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.joml.Quaternionf;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.misc.MathUtil;
import xyz.przemyk.simpleplanes.network.*;

@EventBusSubscriber(Dist.CLIENT)
public class ClientEventHandler {

    public static KeyMapping moveHeliUpKey;
    public static KeyMapping openPlaneInventoryKey;
    public static KeyMapping dropPayloadKey;
    public static KeyMapping throttleUp;
    public static KeyMapping throttleDown;
    public static KeyMapping pitchUp;
    public static KeyMapping pitchDown;
    public static KeyMapping yawRight;
    public static KeyMapping yawLeft;

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onRenderPre(RenderLivingEvent.Pre<LivingEntity, ?> event) {
        LivingEntity livingEntity = event.getEntity();
        Entity entity = livingEntity.getRootVehicle();
        if (entity instanceof PlaneEntity planeEntity) {
            PoseStack matrixStack = event.getPoseStack();
            matrixStack.pushPose();

            matrixStack.translate(0, 0.375, 0);
            Quaternionf quaternion = MathUtil.lerpQ(event.getPartialTick(), planeEntity.getQ_Prev(), planeEntity.getQ_Client());
            quaternion.set(quaternion.x(), -quaternion.y(), -quaternion.z(), quaternion.w());
            matrixStack.mulPose(quaternion);
            float rotationYaw = MathUtil.lerpAngle(event.getPartialTick(), entity.yRotO, entity.getYRot());

            matrixStack.mulPose(Axis.YP.rotationDegrees(rotationYaw));
            matrixStack.translate(0, -0.375, 0);

            if (MathUtil.degreesDifferenceAbs(planeEntity.rotationRoll, 0) > 90) { //TODO: Do I actually need this?
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
        LivingEntity livingEntity = event.getEntity();
        Entity entity = livingEntity.getRootVehicle();
        if (entity instanceof PlaneEntity planeEntity) {
            event.getPoseStack().popPose();

            if (MathUtil.degreesDifferenceAbs(planeEntity.rotationRoll, 0) > 90) {
                livingEntity.yHeadRot = planeEntity.getYRot() * 2 - event.getEntity().yHeadRot;
            }
            if (MathUtil.degreesDifferenceAbs(planeEntity.prevRotationRoll, 0) > 90) {
                livingEntity.yHeadRotO = planeEntity.yRotO * 2 - event.getEntity().yHeadRotO;
            }
        }
    }

    private static boolean oldMoveHeliUpState = false;
    private static boolean oldPitchUpState = false;
    private static boolean oldPitchDownState = false;
    private static boolean oldYawRightState = false;
    private static boolean oldYawLeftState = false;

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onClientPlayerTick(PlayerTickEvent.Post event) {
        final Player player = event.getEntity();
        if (player instanceof LocalPlayer) {
            if (player.getVehicle() instanceof PlaneEntity planeEntity) {
                Minecraft mc = Minecraft.getInstance();
                if (mc.options.getCameraType() != CameraType.FIRST_PERSON) {
                    planeEntity.applyYawToEntity(player);
                }

                if (mc.screen == null && mc.getOverlay() == null && openPlaneInventoryKey.consumeClick()) {
                    PacketDistributor.sendToServer(new OpenPlaneInventoryPacket());
                } else if (dropPayloadKey.consumeClick()) {
                    planeEntity.dropPayload();
                }

                if (throttleUp.consumeClick()) {
                    PacketDistributor.sendToServer(new ChangeThrottlePacket(ChangeThrottlePacket.Direction.UP));
                } else if (throttleDown.consumeClick()) {
                    PacketDistributor.sendToServer(new ChangeThrottlePacket(ChangeThrottlePacket.Direction.DOWN));
                }

                boolean isMoveHeliUp = moveHeliUpKey.isDown();
                boolean isPitchUp = pitchUp.isDown();
                boolean isPitchDown = pitchDown.isDown();
                boolean isYawRight = yawRight.isDown();
                boolean isYawLeft = yawLeft.isDown();

                if (isMoveHeliUp != oldMoveHeliUpState) {
                    PacketDistributor.sendToServer(new MoveHeliUpPacket(isMoveHeliUp));
                }

                if (isPitchUp != oldPitchUpState || isPitchDown != oldPitchDownState) {
                    PacketDistributor.sendToServer(new PitchPacket((byte) Boolean.compare(isPitchUp, isPitchDown)));
                }

                if (isYawRight != oldYawRightState || isYawLeft != oldYawLeftState) {
                    PacketDistributor.sendToServer(new YawPacket((byte) Boolean.compare(isYawRight, isYawLeft)));
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
    }

    //TODO: make it so player rotation variables correspond to what he is actually looking at, so that guns etc. shoot in the right direction
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onCameraSetup(ViewportEvent.ComputeCameraAngles event) {
        Camera camera = event.getCamera();
        Entity player = camera.getEntity();
        if (player.getVehicle() instanceof PlaneEntity planeEntity) {
            if (!camera.isDetached()) {
                double partialTicks = event.getPartialTick();

                Quaternionf qPrev = planeEntity.getQ_Prev();
                Quaternionf qNow = planeEntity.getQ_Client();

                qPrev.mul(Axis.YP.rotationDegrees(player.yRotO));
                qPrev.mul(Axis.XP.rotationDegrees(event.getPitch()));
                MathUtil.EulerAngles eulerAnglesPrev = MathUtil.toEulerAngles(qPrev);

                qNow.mul(Axis.YP.rotationDegrees(player.getYRot()));
                qNow.mul(Axis.XP.rotationDegrees(event.getPitch()));
                MathUtil.EulerAngles eulerAnglesNow = MathUtil.toEulerAngles(qNow);

                event.setPitch(-(float) MathUtil.lerpAngle(partialTicks, eulerAnglesPrev.pitch, eulerAnglesNow.pitch));
                event.setYaw((float) MathUtil.lerpAngle(partialTicks, eulerAnglesPrev.yaw, eulerAnglesNow.yaw));
                event.setRoll(-(float) MathUtil.lerpAngle(partialTicks, eulerAnglesPrev.roll, eulerAnglesNow.roll));
            }
        }
    }

    @SubscribeEvent
    public static void onCalculateDetachedCameraDistance(CalculateDetachedCameraDistanceEvent event) {
        if (event.getCamera().getEntity().getVehicle() instanceof PlaneEntity planeEntity) {
            event.setDistance((float) (4.0 * planeEntity.getCameraDistanceMultiplayer()));
        }
    }
}
