package xyz.przemyk.simpleplanes;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
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
import xyz.przemyk.simpleplanes.setup.SimplePlanesRegistries;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;
import xyz.przemyk.simpleplanes.upgrades.UpgradeType;

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

            for (UpgradeType upgradeType : SimplePlanesRegistries.UPGRADE_TYPES.getValues()) {
                if (upgradeType.IsThisItem(itemStack) && planeEntity.canAddUpgrade(upgradeType)) {
                    final Upgrade upgrade = upgradeType.instanceSupplier.apply(planeEntity);
                    planeEntity.upgrades.put(upgradeType.getRegistryName(), upgrade);
                    upgrade.onApply(itemStack,player);
                    if (!player.isCreative()) {
                        itemStack.shrink(1);
                    }
                    planeEntity.upgradeChanged();
                }
            }
        }
    }

    // TODO: rotate player with plane?

    public static boolean playerRotationNeedToPop = false;

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onRenderPre(RenderLivingEvent.Pre event) {
        Entity entity = event.getEntity().getLowestRidingEntity();
        if (entity instanceof PlaneEntity) {

            PlaneEntity planeEntity = (PlaneEntity) entity;
            MatrixStack matrixStack = event.getMatrixStack();
            matrixStack.push();
            playerRotationNeedToPop = true;
            matrixStack.translate(0, 0.7, 0);
            Quaternion q =MathUtil.lerpQ(event.getPartialRenderTick(),planeEntity.getQ_Prev(),planeEntity.getQ_Client());
//            Quaternion q =planeEntity.getQ();
            q.set(q.getX(),-q.getY(),-q.getZ(),q.getW());
            matrixStack.rotate(q);
            final float rotationYaw = MathHelper.lerp(event.getPartialRenderTick(), entity.prevRotationYaw, entity.rotationYaw);;
            matrixStack.rotate(Vector3f.YP.rotationDegrees(rotationYaw));
            matrixStack.translate(0, -0.7, 0);


        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onRenderPost(RenderLivingEvent.Post event) {
        if (playerRotationNeedToPop) {
            playerRotationNeedToPop = false;
            event.getMatrixStack().pop();
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onClientPlayerTick(TickEvent.PlayerTickEvent event) {
        final PlayerEntity player = event.player;
        if (player instanceof ClientPlayerEntity &&
                player.getRidingEntity() instanceof PlaneEntity &&
                event.phase == TickEvent.Phase.END) {
            boolean flag = Minecraft.getInstance().gameSettings.keyBindSprint.isKeyDown();
            final ClientPlayerEntity clientPlayerEntity = (ClientPlayerEntity) player;
            clientPlayerEntity.setSprinting(flag);
            if (flag != clientPlayerEntity.serverSprintState||Math.random()<0.1) {
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
                entity.getRidingEntity() instanceof PlaneEntity && !event.getInfo().isThirdPerson()) {
            PlaneEntity planeEntity = (PlaneEntity) entity.getRidingEntity();
            ClientPlayerEntity playerEntity = (ClientPlayerEntity) entity;
            double partialTicks = event.getRenderPartialTicks();
            event.setPitch(-(float) MathUtil.lerpAngle(partialTicks,planeEntity.prevRotationPitch,planeEntity.rotationPitch));
            event.setYaw((float) MathUtil.lerpAngle(partialTicks,planeEntity.prevRotationYaw,planeEntity.rotationYaw));
            event.setRoll(-(float) MathUtil.lerpAngle(partialTicks,planeEntity.prevRotationRoll,planeEntity.rotationRoll));
        }
    }


}
