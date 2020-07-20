package xyz.przemyk.simpleplanes;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.play.client.CEntityActionPacket;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

import java.util.HashSet;

@Mod.EventBusSubscriber
public class PlanesEvents {
    public static final ResourceLocation COAL_TAG = new ResourceLocation("minecraft", "coals");

    @SubscribeEvent
    public static void interact(PlayerInteractEvent.RightClickItem event) {
        PlayerEntity player = event.getPlayer();
        Entity entity = player.getLowestRidingEntity();
        if (entity instanceof PlaneEntity) {
            ItemStack itemStack = player.getHeldItem(event.getHand());

            if (itemStack.isEmpty()) {
                return;
            }

            PlaneEntity planeEntity = (PlaneEntity) entity;
            if (planeEntity.getFuel() < 200) {
                //func_230235_a_ - contains
                if (ItemTags.getCollection().getOrCreate(COAL_TAG).func_230235_a_(itemStack.getItem())) {
                    ((PlaneEntity) entity).addFuel();
                    if (!player.isCreative()) {
                        itemStack.shrink(1);
                    }
                }
            }

            HashSet<Upgrade> upgradesToRemove = new HashSet<>();
            for (Upgrade upgrade : planeEntity.upgrades.values()) {
                if (upgrade.onItemRightClick(event)) {
                    upgradesToRemove.add(upgrade);
                }
            }

            for (Upgrade upgrade : upgradesToRemove) {
                planeEntity.upgrades.remove(upgrade.getType().getRegistryName());
            }

            // some upgrade may shrink itemStack so we need to check if it's empty
            if (itemStack.isEmpty()) {
                return;
            }
            if (itemStack.getItem() == Items.PHANTOM_MEMBRANE) {
                planeEntity.setNoGravity(!planeEntity.hasNoGravity());
            }

            planeEntity.tryToAddUpgrade(player, itemStack);
        }
    }

    // TODO: rotate player with plane?

    public static boolean playerRotationNeedToPop = false;

    @SuppressWarnings("rawtypes")
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onRenderPre(RenderLivingEvent.Pre event) {
        Entity entity = event.getEntity().getLowestRidingEntity();
        if (entity instanceof PlaneEntity) {

            PlaneEntity planeEntity = (PlaneEntity) entity;
            MatrixStack matrixStack = event.getMatrixStack();
            matrixStack.push();
            playerRotationNeedToPop = true;
            double y1 = 0.7D;
            boolean fpv = Minecraft.getInstance().player != null && planeEntity.isPassenger(Minecraft.getInstance().player) && (Minecraft.getInstance()).gameSettings.thirdPersonView == 0;
            if (fpv) {
                matrixStack.translate(0.0D, y1, 0.0D);
            }

            matrixStack.translate(0, 0.7, 0);
            Quaternion q = MathUtil.lerpQ(event.getPartialRenderTick(), planeEntity.getQ_Prev(), planeEntity.getQ_Client());
//            Quaternion q =planeEntity.getQ();
            q.set(q.getX(), -q.getY(), -q.getZ(), q.getW());
            matrixStack.rotate(q);
            final float rotationYaw = MathUtil.lerpAngle(event.getPartialRenderTick(), entity.prevRotationYaw, entity.rotationYaw);

            matrixStack.rotate(Vector3f.YP.rotationDegrees(rotationYaw));
            matrixStack.translate(0, -0.7, 0);
            if (fpv) {
                matrixStack.translate(0.0D, -y1, 0.0D);
            }
            if(MathUtil.degreesDifferenceAbs(planeEntity.rotationRoll,0)>90){
                event.getEntity().rotationYawHead=planeEntity.rotationYaw*2-event.getEntity().rotationYawHead;
            }
            if(MathUtil.degreesDifferenceAbs(planeEntity.prevRotationRoll,0)>90){
                event.getEntity().prevRotationYawHead=planeEntity.prevRotationYaw*2-event.getEntity().prevRotationYawHead;
            }
//            event.getEntity().rotationYaw*=-1;
//            event.getEntity().prevRotationYaw*=-1;
//            event.getEntity().renderYawOffset*=-1;

        }
    }

    @SuppressWarnings("rawtypes")
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onRenderPost(RenderLivingEvent.Post event) {
        if (playerRotationNeedToPop) {
            playerRotationNeedToPop = false;
            event.getMatrixStack().pop();
//            event.getEntity().rotationYaw*=-1;
//            event.getEntity().prevRotationYaw*=-1;
//            event.getEntity().renderYawOffset*=-1;
            Entity entity = event.getEntity().getLowestRidingEntity();
            PlaneEntity planeEntity = (PlaneEntity) entity;

            if(MathUtil.degreesDifferenceAbs(planeEntity.rotationRoll,0)>90){
                event.getEntity().rotationYawHead=planeEntity.rotationYaw*2-event.getEntity().rotationYawHead;
            }
            if(MathUtil.degreesDifferenceAbs(planeEntity.prevRotationRoll,0)>90){
                event.getEntity().prevRotationYawHead=planeEntity.prevRotationYaw*2-event.getEntity().prevRotationYawHead;
            }

        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onClientPlayerTick(TickEvent.PlayerTickEvent event) {
        final PlayerEntity player = event.player;
        if (player instanceof ClientPlayerEntity &&
                player.getRidingEntity() instanceof PlaneEntity &&
                event.phase == TickEvent.Phase.END) {
            //todo: is this the right place, prevent player from looking away when flying
            if ((Minecraft.getInstance()).gameSettings.thirdPersonView == 0) {
                float f = MathHelper.wrapDegrees(player.rotationPitch - 0);
                float f1 = MathHelper.clamp(f, -50, 50);
                float perc = (f1 - f) * 0.5f;
                player.prevRotationPitch += perc;
                player.rotationPitch += perc;
            }
            boolean flag = Minecraft.getInstance().gameSettings.keyBindSprint.isKeyDown();
            final ClientPlayerEntity clientPlayerEntity = (ClientPlayerEntity) player;
            clientPlayerEntity.setSprinting(flag);
            if (flag != clientPlayerEntity.serverSprintState || Math.random() < 0.1) {
                CEntityActionPacket.Action centityactionpacket$action = flag ? CEntityActionPacket.Action.START_SPRINTING : CEntityActionPacket.Action.STOP_SPRINTING;
                clientPlayerEntity.connection.sendPacket(new CEntityActionPacket(player, centityactionpacket$action));
                clientPlayerEntity.serverSprintState = flag;
            }

        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onCameraSetup(EntityViewRenderEvent.CameraSetup event) {
        final Entity entity = event.getInfo().getRenderViewEntity();
        if (entity instanceof ClientPlayerEntity &&
                entity.getRidingEntity() instanceof PlaneEntity) {
            PlaneEntity planeEntity = (PlaneEntity) entity.getRidingEntity();
            ClientPlayerEntity playerEntity = (ClientPlayerEntity) entity;

            if (!event.getInfo().isThirdPerson()) {
                double partialTicks = event.getRenderPartialTicks();

                Quaternion q_prev = planeEntity.getQ_Prev();
                int max = 105;
                float diff = MathUtil.clamp(MathUtil.wrapSubtractDegrees(planeEntity.prevRotationYaw, playerEntity.prevRotationYaw), -max, max);
                float pitch = MathUtil.clamp(event.getPitch(), -45, 45);
                q_prev.multiply(Vector3f.YP.rotationDegrees(diff));
                q_prev.multiply(Vector3f.XP.rotationDegrees(pitch));
                MathUtil.Angels angles_prev = MathUtil.ToEulerAngles(q_prev);

                Quaternion q_client = planeEntity.getQ_Client();
                diff = MathUtil.clamp(MathUtil.wrapSubtractDegrees(planeEntity.rotationYaw, playerEntity.rotationYaw), -max, max);
                q_client.multiply(Vector3f.YP.rotationDegrees(diff));
                q_client.multiply(Vector3f.XP.rotationDegrees(pitch));
                MathUtil.Angels angles = MathUtil.ToEulerAngles(q_client);
                Quaternion r = MathUtil.ToQuaternion(11, 0, 0);
                r.multiply(Vector3f.XP.rotationDegrees(89.999f));


                event.setPitch(-(float) MathUtil.lerpAngle180(partialTicks, angles_prev.pitch, angles.pitch));
                event.setYaw((float) MathUtil.lerpAngle(partialTicks, angles_prev.yaw, angles.yaw));
//                event.setYaw((float) MathUtil.lerpAngle(partialTicks, playerEntity.prevRotationYaw, playerEntity.rotationYaw));
                event.setRoll(-(float) MathUtil.lerpAngle(partialTicks, angles_prev.roll, angles.roll));
            } /* else {
                event.getInfo().pos =planeEntity.getPositionVec();
            } */
        }
    }


}
