package xyz.przemyk.simpleplanes.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.settings.PointOfView;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.przemyk.simpleplanes.MathUtil;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.network.BoostPacket;
import xyz.przemyk.simpleplanes.network.OpenEngineInventoryPacket;
import xyz.przemyk.simpleplanes.network.PlaneNetworking;

import static xyz.przemyk.simpleplanes.SimplePlanesMod.keyBind;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ClientEvents {

    private static boolean playerRotationNeedToPop = false;

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onRenderPre(RenderLivingEvent.Pre<LivingEntity, ?> event) {
        LivingEntity livingEntity = event.getEntity();
        Entity entity = livingEntity.getLowestRidingEntity();
        if (entity instanceof PlaneEntity) {
            PlaneEntity planeEntity = (PlaneEntity) entity;
            MatrixStack matrixStack = event.getMatrixStack();
            matrixStack.push();
            playerRotationNeedToPop = true;
            double firstPersonYOffset = 0.7D;
            boolean isPlayerRidingInFirstPersonView = Minecraft.getInstance().player != null && planeEntity.isPassenger(Minecraft.getInstance().player)
                && (Minecraft.getInstance()).gameSettings.pointOfView == PointOfView.FIRST_PERSON;
            if (isPlayerRidingInFirstPersonView) {
                matrixStack.translate(0.0D, firstPersonYOffset, 0.0D);
            }

            matrixStack.translate(0, 0.7, 0);
            Quaternion quaternion = MathUtil.lerpQ(event.getPartialRenderTick(), planeEntity.getQ_Prev(), planeEntity.getQ_Client());
            quaternion.set(quaternion.getX(), -quaternion.getY(), -quaternion.getZ(), quaternion.getW());
            matrixStack.rotate(quaternion);
            float rotationYaw = MathUtil.lerpAngle(event.getPartialRenderTick(), entity.prevRotationYaw, entity.rotationYaw);

            matrixStack.rotate(Vector3f.YP.rotationDegrees(rotationYaw));
            matrixStack.translate(0, -0.7, 0);
            if (isPlayerRidingInFirstPersonView) {
                matrixStack.translate(0.0D, -firstPersonYOffset, 0.0D);
            }
            if (MathUtil.degreesDifferenceAbs(planeEntity.rotationRoll, 0) > 90) {
                livingEntity.rotationYawHead = planeEntity.rotationYaw * 2 - livingEntity.rotationYawHead;
            }
            if (MathUtil.degreesDifferenceAbs(planeEntity.prevRotationRoll, 0) > 90) {
                livingEntity.prevRotationYawHead = planeEntity.prevRotationYaw * 2 - livingEntity.prevRotationYawHead;
            }
        }
    }

    @SuppressWarnings("rawtypes")
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onRenderPost(RenderLivingEvent.Post event) {
        if (playerRotationNeedToPop) {
            playerRotationNeedToPop = false;
            event.getMatrixStack().pop();
            Entity entity = event.getEntity().getLowestRidingEntity();
            PlaneEntity planeEntity = (PlaneEntity) entity;

            if (MathUtil.degreesDifferenceAbs(planeEntity.rotationRoll, 0) > 90) {
                event.getEntity().rotationYawHead = planeEntity.rotationYaw * 2 - event.getEntity().rotationYawHead;
            }
            if (MathUtil.degreesDifferenceAbs(planeEntity.prevRotationRoll, 0) > 90) {
                event.getEntity().prevRotationYawHead = planeEntity.prevRotationYaw * 2 - event.getEntity().prevRotationYawHead;
            }
        }
    }

    private static boolean old_sprint = false;

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onClientPlayerTick(PlayerTickEvent event) {
        final PlayerEntity player = event.player;
        if ((event.phase == Phase.END) && (player instanceof ClientPlayerEntity)) {
            if (player.getRidingEntity() instanceof PlaneEntity) {
                PlaneEntity planeEntity = (PlaneEntity) player.getRidingEntity();
                Minecraft mc = Minecraft.getInstance();
                if (mc.gameSettings.pointOfView == PointOfView.FIRST_PERSON) {
                    float yawDiff = planeEntity.rotationYaw - planeEntity.prevRotationYaw;
                    player.rotationYaw += yawDiff;
                    float relativePlayerYaw = MathHelper.wrapDegrees(player.rotationYaw - planeEntity.rotationYaw);
                    float clampedRelativePlayerYaw = MathHelper.clamp(relativePlayerYaw, -105.0F, 105.0F);

                    float diff = (clampedRelativePlayerYaw - relativePlayerYaw);
                    player.prevRotationYaw += diff;
                    player.rotationYaw += diff;
                    player.setRotationYawHead(player.rotationYaw);

                    relativePlayerYaw = MathHelper.wrapDegrees(player.rotationPitch - 0);
                    clampedRelativePlayerYaw = MathHelper.clamp(relativePlayerYaw, -50, 50);
                    float perc = (clampedRelativePlayerYaw - relativePlayerYaw) * 0.5f;
                    player.prevRotationPitch += perc;
                    player.rotationPitch += perc;
                } else {
                    planeEntity.applyYawToEntity(player);
                }

                if (planeEntity.engineUpgrade != null && mc.currentScreen == null && mc.loadingGui == null && SimplePlanesMod.openEngineInventoryKey.isPressed() && planeEntity.engineUpgrade.canOpenGui()) {
                    PlaneNetworking.INSTANCE.sendToServer(new OpenEngineInventoryPacket());
                }

                boolean isSprinting = keyBind.isKeyDown();
                if (isSprinting != old_sprint || Math.random() < 0.1) {
                    PlaneNetworking.INSTANCE.sendToServer(new BoostPacket(isSprinting));
                }
                old_sprint = isSprinting;
            } else {
                old_sprint = false;
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onCameraSetup(EntityViewRenderEvent.CameraSetup event) {
        ActiveRenderInfo renderInfo = event.getInfo();
        Entity entity = renderInfo.getRenderViewEntity();
        if (entity instanceof ClientPlayerEntity && entity.getRidingEntity() instanceof PlaneEntity) {
            PlaneEntity planeEntity = (PlaneEntity) entity.getRidingEntity();
            ClientPlayerEntity playerEntity = (ClientPlayerEntity) entity;

            if (renderInfo.isThirdPerson()) {
                renderInfo.movePosition(-renderInfo.calcCameraDistance(4.0D * (planeEntity.getCameraDistanceMultiplayer() - 1.0)), 0.0D, 0.0D);
            } else {
                double partialTicks = event.getRenderPartialTicks();

                Quaternion q_prev = planeEntity.getQ_Prev();
                int max = 105;
                float diff = (float) MathHelper.clamp(MathUtil.wrapSubtractDegrees(planeEntity.prevRotationYaw, playerEntity.prevRotationYaw), -max, max);
                float pitch = MathHelper.clamp(event.getPitch(), -45, 45);
                q_prev.multiply(Vector3f.YP.rotationDegrees(diff));
                q_prev.multiply(Vector3f.XP.rotationDegrees(pitch));
                MathUtil.EulerAngles angles_prev = MathUtil.toEulerAngles(q_prev);

                Quaternion q_client = planeEntity.getQ_Client();
                diff = (float) MathHelper.clamp(MathUtil.wrapSubtractDegrees(planeEntity.rotationYaw, playerEntity.rotationYaw), -max, max);
                q_client.multiply(Vector3f.YP.rotationDegrees(diff));
                q_client.multiply(Vector3f.XP.rotationDegrees(pitch));
                MathUtil.EulerAngles angles = MathUtil.toEulerAngles(q_client);

                event.setPitch(-(float) MathUtil.lerpAngle180(partialTicks, angles_prev.pitch, angles.pitch));
                event.setYaw((float) MathUtil.lerpAngle(partialTicks, angles_prev.yaw, angles.yaw));
                event.setRoll(-(float) MathUtil.lerpAngle(partialTicks, angles_prev.roll, angles.roll));
            }
        }
    }

//    @SubscribeEvent(priority = EventPriority.HIGH)
//    public static void planeInventory(GuiOpenEvent event) {
//        final ClientPlayerEntity player = Minecraft.getInstance().player;
//        if (event.getGui() instanceof InventoryScreen && player.getRidingEntity() instanceof PlaneEntity) {
////            event.setCanceled(true);
//            final PlaneEntity plane = (PlaneEntity) player.getRidingEntity();
//            Upgrade chest = plane.upgrades.getOrDefault(SimplePlanesUpgrades.CHEST.getId(), null);
//            if (chest instanceof ChestUpgrade) {
//
//                ChestUpgrade chest1 = (ChestUpgrade) chest;
//                IInventory inventory = chest1.inventory;
//                if (inventory != null) {
//                    event.setCanceled(true);
////                    PlaneNetworking.OPEN_INVENTORY.sendToServer(true);
//                    PlaneNetworking.INSTANCE.sendToServer(new OpenInventoryPacket());
//                }
////                StringTextComponent hi = new StringTextComponent("hi");
////                ScreenManager.openScreen(ContainerType.GENERIC_9X3,event.getGui().getMinecraft(),0,hi);
////                ChestScreen gui = new ChestScreen(ChestContainer.createGeneric9X3(1, player.inventory, inventory), player.inventory, hi);
////                event.setGui(gui);
//            }
//        }
//    }
}
