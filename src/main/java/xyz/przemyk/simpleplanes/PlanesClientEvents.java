package xyz.przemyk.simpleplanes;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.handler.PlaneNetworking;
import xyz.przemyk.simpleplanes.math.MathUtil;
import xyz.przemyk.simpleplanes.math.Quaternion;
import xyz.przemyk.simpleplanes.proxy.ClientProxy;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;
import xyz.przemyk.simpleplanes.upgrades.storage.ChestUpgrade;
//import xyz.przemyk.simpleplanes.upgrades.storage.ChestUpgrade;

import static xyz.przemyk.simpleplanes.math.MathUtil.rotationDegreesX;
import static xyz.przemyk.simpleplanes.math.MathUtil.rotationDegreesY;

@Mod.EventBusSubscriber(value = Side.CLIENT)
public class PlanesClientEvents {
    private static boolean playerRotationNeedToPop = false;

    @SuppressWarnings("rawtypes")
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onRenderPre(RenderLivingEvent.Pre event) {
        Entity entity = event.getEntity().getLowestRidingEntity();
        if (entity instanceof PlaneEntity) {
            PlaneEntity planeEntity = (PlaneEntity) entity;
            GlStateManager.pushMatrix();
            playerRotationNeedToPop = true;
            double firstPersonYOffset = 0.7D;
            boolean isPlayerRidingInFirstPersonView = Minecraft.getMinecraft().player != null && planeEntity.isPassenger(Minecraft.getMinecraft().player)
                && (Minecraft.getMinecraft()).gameSettings.thirdPersonView == 0;
            if (isPlayerRidingInFirstPersonView) {
                GlStateManager.translate(0.0D, firstPersonYOffset, 0.0D);
            }

            GlStateManager.translate(0, 0.7, 0);
            GlStateManager.translate(event.getX(),event.getY(),event.getZ());
            Quaternion quaternion = MathUtil.lerpQ(event.getPartialRenderTick(), planeEntity.getQ_Prev(), planeEntity.getQ_Client());
            quaternion.set(quaternion.getX(), -quaternion.getY(), -quaternion.getZ(), quaternion.getW());
            GlStateManager.rotate(quaternion.convert());
            final float rotationYaw = MathUtil.lerpAngle(event.getPartialRenderTick(), entity.prevRotationYaw, entity.rotationYaw);

            GlStateManager.rotate(rotationDegreesY(rotationYaw).convert());
            GlStateManager.translate(-event.getX(),-event.getY(),-event.getZ());
            GlStateManager.translate(0, -0.7, 0);
            if (isPlayerRidingInFirstPersonView) {
                GlStateManager.translate(0.0D, -firstPersonYOffset, 0.0D);
            }
            if (MathUtil.degreesDifferenceAbs(planeEntity.rotationRoll, 0) > 90) {
                event.getEntity().rotationYawHead = planeEntity.rotationYaw * 2 - event.getEntity().rotationYawHead;
            }
            if (MathUtil.degreesDifferenceAbs(planeEntity.prevRotationRoll, 0) > 90) {
                event.getEntity().prevRotationYawHead = planeEntity.prevRotationYaw * 2 - event.getEntity().prevRotationYawHead;
            }
        }
    }

    @SuppressWarnings("rawtypes")
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onRenderPost(RenderLivingEvent.Post event) {
        if (playerRotationNeedToPop) {
            playerRotationNeedToPop = false;
            GlStateManager.popMatrix();
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

    static boolean old_sprint = false;

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onClientPlayerTick(TickEvent.PlayerTickEvent event) {
        final EntityPlayer player = event.player;
        if ((event.phase == TickEvent.Phase.END) && (player instanceof EntityPlayerSP)) {
            if (player.getRidingEntity() instanceof PlaneEntity) {
                PlaneEntity planeEntity = (PlaneEntity) player.getRidingEntity();
                if ((Minecraft.getMinecraft()).gameSettings.thirdPersonView == 0) {
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

                final EntityPlayerSP clientEntityPlayer = (EntityPlayerSP) player;
                boolean isSprinting = ClientProxy.keyBind.isKeyDown()||clientEntityPlayer.isSprinting()||Minecraft.getMinecraft().gameSettings.keyBindSprint.isKeyDown();

                if (isSprinting != old_sprint || Math.random() < 0.1) {
                    PlaneNetworking.INSTANCE.sendToServer(new PlaneNetworking.BoostMSG.MSG(isSprinting));
                }
                old_sprint = isSprinting;
            } else {
                old_sprint = false;
            }
        }
    }

    /**
     * fixes first person view
     */
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onCameraSetup(EntityViewRenderEvent.CameraSetup event) {
        final Entity entity = event.getEntity();
        if (entity instanceof EntityPlayerSP && entity.getRidingEntity() instanceof PlaneEntity) {
            PlaneEntity planeEntity = (PlaneEntity) entity.getRidingEntity();
            EntityPlayerSP playerEntity = (EntityPlayerSP) entity;

            if ((Minecraft.getMinecraft()).gameSettings.thirdPersonView == 0) {
                double partialTicks = event.getRenderPartialTicks();

                Quaternion q_prev = planeEntity.getQ_Prev();
                int max = 105;
                float diff = (float) MathHelper.clamp(MathUtil.wrapSubtractDegrees(planeEntity.prevRotationYaw, playerEntity.prevRotationYaw), -max, max);
                float pitch = MathHelper.clamp(event.getPitch(), -45, 45);
                Quaternion.mul(q_prev,rotationDegreesY(diff),q_prev);
                Quaternion.mul(q_prev,rotationDegreesX(pitch),q_prev);
                MathUtil.EulerAngles angles_prev = MathUtil.toEulerAngles(q_prev);

                Quaternion q_client = planeEntity.getQ_Client();
                diff = (float) MathHelper.clamp(MathUtil.wrapSubtractDegrees(planeEntity.rotationYaw, playerEntity.rotationYaw), -max, max);
                Quaternion.mul(q_client,rotationDegreesY(diff),q_client);
                Quaternion.mul(q_client,rotationDegreesX(pitch),q_client);
                MathUtil.EulerAngles angles = MathUtil.toEulerAngles(q_client);

                event.setPitch(-(float) MathUtil.lerpAngle180(partialTicks, angles_prev.pitch, angles.pitch));
                event.setYaw((float) MathUtil.lerpAngle(partialTicks, angles_prev.yaw, angles.yaw)+180);
                event.setRoll(-(float) MathUtil.lerpAngle(partialTicks, angles_prev.roll, angles.roll));
            }
        }
    }


    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void planeInventory(GuiOpenEvent event) {
        final EntityPlayerSP player = Minecraft.getMinecraft().player;
        if (event.getGui() instanceof GuiInventory && player.getRidingEntity() instanceof PlaneEntity) {
            final PlaneEntity plane = (PlaneEntity) player.getRidingEntity();
            Upgrade chest = plane.upgrades.getOrDefault(SimplePlanesUpgrades.CHEST.getId(), null);
            if (chest instanceof ChestUpgrade) {

                ChestUpgrade chest1 = (ChestUpgrade) chest;
                IInventory inventory = chest1.inventory;
                if (inventory != null) {
                    event.setCanceled(true);
                    PlaneNetworking.INSTANCE.sendToServer(new PlaneNetworking.InventoryMSG.MSG());
                }
//                StringTextComponent hi = new StringTextComponent("hi");
//                ScreenManager.openScreen(ContainerType.GENERIC_9X3,event.getGui().getMinecraft(),0,hi);
//                ChestScreen gui = new ChestScreen(ChestContainer.createGeneric9X3(1, player.inventory, inventory), player.inventory, hi);
//                event.setGui(gui);
            }
        }
    }

}
