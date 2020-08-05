package xyz.przemyk.simpleplanes;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.play.client.CEntityActionPacket;
import net.minecraft.network.play.client.CEntityActionPacket.Action;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import com.mojang.blaze3d.matrix.MatrixStack;

import xyz.przemyk.simpleplanes.entities.PlaneEntity;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(Dist.CLIENT)
public class PlanesClientEvents
{
    public static boolean playerRotationNeedToPop = false;

    @SuppressWarnings("rawtypes")
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onRenderPre(RenderLivingEvent.Pre event)
    {
        Entity entity = event.getEntity().getLowestRidingEntity();
        if (entity instanceof PlaneEntity)
        {

            PlaneEntity planeEntity = (PlaneEntity) entity;
            MatrixStack matrixStack = event.getMatrixStack();
            matrixStack.push();
            playerRotationNeedToPop = true;
            double y1 = 0.7D;
            boolean fpv = Minecraft.getInstance().player != null && planeEntity.isPassenger(Minecraft.getInstance().player)
                    && (Minecraft.getInstance()).gameSettings.thirdPersonView == 0;
            if (fpv)
            {
                matrixStack.translate(0.0D, y1, 0.0D);
            }

            matrixStack.translate(0, 0.7, 0);
            Quaternion q = MathUtil.lerpQ(event.getPartialRenderTick(), planeEntity.getQ_Prev(), planeEntity.getQ_Client());
            q.set(q.getX(), -q.getY(), -q.getZ(), q.getW());
            matrixStack.rotate(q);
            final float rotationYaw = MathUtil.lerpAngle(event.getPartialRenderTick(), entity.prevRotationYaw, entity.rotationYaw);

            matrixStack.rotate(Vector3f.YP.rotationDegrees(rotationYaw));
            matrixStack.translate(0, -0.7, 0);
            if (fpv)
            {
                matrixStack.translate(0.0D, -y1, 0.0D);
            }
            if (MathUtil.degreesDifferenceAbs(planeEntity.rotationRoll, 0) > 90)
            {
                event.getEntity().rotationYawHead = planeEntity.rotationYaw * 2 - event.getEntity().rotationYawHead;
            }
            if (MathUtil.degreesDifferenceAbs(planeEntity.prevRotationRoll, 0) > 90)
            {
                event.getEntity().prevRotationYawHead = planeEntity.prevRotationYaw * 2 - event.getEntity().prevRotationYawHead;
            }
        }
    }

    @SuppressWarnings("rawtypes")
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onRenderPost(RenderLivingEvent.Post event)
    {
        if (playerRotationNeedToPop)
        {
            playerRotationNeedToPop = false;
            event.getMatrixStack().pop();
            Entity entity = event.getEntity().getLowestRidingEntity();
            PlaneEntity planeEntity = (PlaneEntity) entity;

            if (MathUtil.degreesDifferenceAbs(planeEntity.rotationRoll, 0) > 90)
            {
                event.getEntity().rotationYawHead = planeEntity.rotationYaw * 2 - event.getEntity().rotationYawHead;
            }
            if (MathUtil.degreesDifferenceAbs(planeEntity.prevRotationRoll, 0) > 90)
            {
                event.getEntity().prevRotationYawHead = planeEntity.prevRotationYaw * 2 - event.getEntity().prevRotationYawHead;
            }

        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onClientPlayerTick(PlayerTickEvent event)
    {
        final PlayerEntity player = event.player;
        if (player instanceof ClientPlayerEntity &&
                player.getRidingEntity() instanceof PlaneEntity &&
                event.phase == Phase.END)
        {
            //todo: is this the right place, prevent player from looking away when flying
            PlaneEntity planeEntity = (PlaneEntity) player.getRidingEntity();
            if ((Minecraft.getInstance()).gameSettings.thirdPersonView == 0)
            {
                float d = planeEntity.rotationYaw - planeEntity.prevRotationYaw;
                player.rotationYaw += d;
                float f = MathHelper.wrapDegrees(player.rotationYaw - planeEntity.rotationYaw);
                float f1 = MathHelper.clamp(f, -105.0F, 105.0F);

                float diff = (f1 - f);
                player.prevRotationYaw += diff;
                player.rotationYaw += diff;
                player.setRotationYawHead(player.rotationYaw);

                f = MathHelper.wrapDegrees(player.rotationPitch - 0);
                f1 = MathHelper.clamp(f, -50, 50);
                float perc = (f1 - f) * 0.5f;
                player.prevRotationPitch += perc;
                player.rotationPitch += perc;
            }
            else
            {
                planeEntity.applyYawToEntity(player);
            }

            boolean flag = Minecraft.getInstance().gameSettings.keyBindSprint.isKeyDown();
            final ClientPlayerEntity clientPlayerEntity = (ClientPlayerEntity) player;
            clientPlayerEntity.setSprinting(flag);
            if (flag != clientPlayerEntity.serverSprintState || Math.random() < 0.1)
            {
                Action centityactionpacket$action = flag ? Action.START_SPRINTING : Action.STOP_SPRINTING;
                clientPlayerEntity.connection.sendPacket(new CEntityActionPacket(player, centityactionpacket$action));
                clientPlayerEntity.serverSprintState = flag;
            }

        }
    }

    /**
     * fixes first person view
     */
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onCameraSetup(EntityViewRenderEvent.CameraSetup event)
    {
        final Entity entity = event.getInfo().getRenderViewEntity();
        if (entity instanceof ClientPlayerEntity &&
                entity.getRidingEntity() instanceof PlaneEntity)
        {
            PlaneEntity planeEntity = (PlaneEntity) entity.getRidingEntity();
            ClientPlayerEntity playerEntity = (ClientPlayerEntity) entity;

            if (!event.getInfo().isThirdPerson())
            {
                double partialTicks = event.getRenderPartialTicks();

                Quaternion q_prev = planeEntity.getQ_Prev();
                int max = 105;
                float diff = MathUtil.clamp(MathUtil.wrapSubtractDegrees(planeEntity.prevRotationYaw, playerEntity.prevRotationYaw), -max, max);
                float pitch = MathUtil.clamp(event.getPitch(), -45, 45);
                q_prev.multiply(Vector3f.YP.rotationDegrees(diff));
                q_prev.multiply(Vector3f.XP.rotationDegrees(pitch));
                MathUtil.Angels angles_prev = MathUtil.toEulerAngles(q_prev);

                Quaternion q_client = planeEntity.getQ_Client();
                diff = MathUtil.clamp(MathUtil.wrapSubtractDegrees(planeEntity.rotationYaw, playerEntity.rotationYaw), -max, max);
                q_client.multiply(Vector3f.YP.rotationDegrees(diff));
                q_client.multiply(Vector3f.XP.rotationDegrees(pitch));
                MathUtil.Angels angles = MathUtil.toEulerAngles(q_client);

                event.setPitch(-(float) MathUtil.lerpAngle180(partialTicks, angles_prev.pitch, angles.pitch));
                event.setYaw((float) MathUtil.lerpAngle(partialTicks, angles_prev.yaw, angles.yaw));
                event.setRoll(-(float) MathUtil.lerpAngle(partialTicks, angles_prev.roll, angles.roll));
            }
        }
    }
}
