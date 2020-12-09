package xyz.przemyk.simpleplanes;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Quaternion;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.handler.PlaneNetworking;

@Environment(EnvType.CLIENT)
public class PlanesClientEvents {
    private static int playerRotationNeedToPop = 0;

    public static ActionResult pre(Entity render_entity, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumers, int light) {
        if(playerRotationNeedToPop>0){
            playerRotationNeedToPop++;
            return ActionResult.PASS;
        };
        Entity entity = render_entity.getRootVehicle();
        if (entity instanceof PlaneEntity && render_entity != entity) {
            PlaneEntity planeEntity = (PlaneEntity) entity;
            matrixStack.push();
            playerRotationNeedToPop = 1;
            double firstPersonYOffset = 0.7D;
            boolean isPlayerRidingInFirstPersonView = MinecraftClient.getInstance().player != null && planeEntity.hasPassenger(MinecraftClient.getInstance().player)
                && (MinecraftClient.getInstance()).options.getPerspective().isFirstPerson();
            if (isPlayerRidingInFirstPersonView) {
                matrixStack.translate(0.0D, firstPersonYOffset, 0.0D);
            }

            matrixStack.translate(0, 0.7, 0);
            Quaternion quaternion = MathUtil.lerpQ(tickDelta, planeEntity.getQ_Prev(), planeEntity.getQ_Client());
            quaternion.set(quaternion.getX(), -quaternion.getY(), -quaternion.getZ(), quaternion.getW());
            matrixStack.multiply(quaternion);
            final float rotationYaw = MathUtil.lerpAngle(tickDelta, entity.prevYaw, entity.yaw);

            matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(rotationYaw));
            matrixStack.translate(0, -0.7, 0);
            if (isPlayerRidingInFirstPersonView) {
                matrixStack.translate(0.0D, -firstPersonYOffset, 0.0D);
            }
            if (render_entity instanceof LivingEntity) {
                LivingEntity entity1 = (LivingEntity) render_entity;
                if (MathUtil.angleBetween(planeEntity.rotationRoll, 0) > 90) {
                    entity1.setHeadYaw(planeEntity.yaw * 2 - render_entity.getHeadYaw());
                }
                if (MathUtil.angleBetween(planeEntity.prevRotationRoll, 0) > 90) {
                    entity1.prevYaw = (planeEntity.yaw * 2 - render_entity.getHeadYaw());

                    entity1.prevHeadYaw = planeEntity.prevYaw * 2 - entity1.prevHeadYaw;
                }
            }
        }
        return ActionResult.PASS;
    }

    @SuppressWarnings("rawtypes")
    public static ActionResult post(Entity render_entity, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumers, int light) {
        if (playerRotationNeedToPop == 1) {
            playerRotationNeedToPop = 0;
            matrixStack.pop();
            Entity entity = render_entity.getRootVehicle();
            PlaneEntity planeEntity = (PlaneEntity) entity;
            if (render_entity instanceof LivingEntity) {
                LivingEntity entity1 = (LivingEntity) render_entity;
                if (MathUtil.angleBetween(planeEntity.rotationRoll, 0) > 90) {
                    entity1.headYaw = planeEntity.yaw * 2 - entity1.headYaw;
                }
                if (MathUtil.angleBetween(planeEntity.prevRotationRoll, 0) > 90) {
                    entity1.prevHeadYaw = planeEntity.prevYaw * 2 - entity1.prevHeadYaw;
                }
            }
        }
        else {
            playerRotationNeedToPop--;
        }
        return ActionResult.PASS;
    }

    static boolean old_sprint = false;


    public static void onClientPlayerTick(MinecraftClient client) {
        final PlayerEntity player = client.player;
        if (player != null && player.getVehicle() instanceof PlaneEntity) {
            PlaneEntity planeEntity = (PlaneEntity) player.getVehicle();
            if ((MinecraftClient.getInstance()).options.getPerspective().isFirstPerson()) {
                float yawDiff = planeEntity.yaw - planeEntity.prevYaw;
                player.yaw += yawDiff;
                float relativePlayerYaw = MathHelper.wrapDegrees(player.yaw - planeEntity.yaw);
                float clampedRelativePlayerYaw = MathHelper.clamp(relativePlayerYaw, -105.0F, 105.0F);

                float diff = (clampedRelativePlayerYaw - relativePlayerYaw);
                player.prevYaw += diff;
                player.yaw += diff;
                player.setHeadYaw(player.yaw);

                relativePlayerYaw = MathHelper.wrapDegrees(player.pitch - 0);
                clampedRelativePlayerYaw = MathHelper.clamp(relativePlayerYaw, -50, 50);
                float perc = (clampedRelativePlayerYaw - relativePlayerYaw) * 0.5f;
                player.prevPitch += perc;
                player.pitch += perc;
            } else {
                planeEntity.applyYawToEntity(player);
            }

            final ClientPlayerEntity clientPlayerEntity = (ClientPlayerEntity) player;
            boolean isSprinting = clientPlayerEntity.isSprinting() ||
                client.options.keySprint.isPressed();
            if (isSprinting != old_sprint || Math.random() < 0.1) {
                PlaneNetworking.send_Boost(isSprinting);
            }
            old_sprint = isSprinting;
        } else {
            old_sprint = false;
        }
    }

    /**
     * fixes first person view
     */
    public static void onCameraUpdate(float tickDelta, long limitTime, MatrixStack matrix, GameRenderer gameRenderer) {
        MinecraftClient client = MinecraftClient.getInstance();
        Camera camera = gameRenderer.getCamera();
        final Entity entity = client.getCameraEntity() == null ? client.player : client.getCameraEntity();
        if (entity instanceof ClientPlayerEntity && entity.getVehicle() instanceof PlaneEntity) {
            PlaneEntity planeEntity = (PlaneEntity) entity.getVehicle();
            ClientPlayerEntity playerEntity = (ClientPlayerEntity) entity;
            if (!camera.isThirdPerson()) {
                matrix.multiply(Vector3f.NEGATIVE_Y.getDegreesQuaternion(camera.getYaw() + 180));
                matrix.multiply(Vector3f.NEGATIVE_X.getDegreesQuaternion(camera.getPitch()));


                Quaternion q_prev = planeEntity.getQ_Prev();
                int max = 105;
                float yaw_diff = MathHelper.clamp(MathHelper.subtractAngles(planeEntity.prevYaw, playerEntity.prevYaw), -max, max);
                float pitch = MathHelper.clamp(camera.getPitch(), -45, 45);
                q_prev.hamiltonProduct(Vector3f.POSITIVE_Y.getDegreesQuaternion(yaw_diff));
                q_prev.hamiltonProduct(Vector3f.POSITIVE_X.getDegreesQuaternion(pitch));
                MathUtil.EulerAngles angles_prev = MathUtil.toEulerAngles(q_prev);

                Quaternion q_client = planeEntity.getQ_Client();
                yaw_diff = MathHelper.clamp(MathHelper.subtractAngles(planeEntity.yaw, playerEntity.yaw), -max, max);
                q_client.hamiltonProduct(Vector3f.POSITIVE_Y.getDegreesQuaternion(yaw_diff));
                q_client.hamiltonProduct(Vector3f.POSITIVE_X.getDegreesQuaternion(pitch));
                MathUtil.EulerAngles angles = MathUtil.toEulerAngles(q_client);


                Quaternion q_lerp = MathUtil.lerpQ(tickDelta, q_prev, q_client);
//                matrix.multiply(Vector3f.NEGATIVE_Y.getDegreesQuaternion(180));
//                matrix.multiply(q_client);
                float pitch_lerp = -(float) MathUtil.lerpAngle180(tickDelta, angles_prev.pitch, angles.pitch);
                float yaw_lerp = (float) MathUtil.lerpAngle(tickDelta, angles_prev.yaw, angles.yaw);
                float roll_lerp = -(float) MathUtil.lerpAngle(tickDelta, angles_prev.roll, angles.roll);
                matrix.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(roll_lerp));

                matrix.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(pitch_lerp));
                matrix.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(yaw_lerp + 180));


//                ((MixinCamera)(Object)camera).setPitch(0);
//                ((MixinCamera)(Object)camera).setYaw(0);
            }

        }

    }


}
