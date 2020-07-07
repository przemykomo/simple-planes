package xyz.przemyk.simpleplanes;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
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
                if (itemStack.getItem() == upgradeType.getUpgradeItem() && planeEntity.canAddUpgrade(upgradeType)) {
                    if (!player.isCreative()) {
                        itemStack.shrink(1);
                    }
                    planeEntity.upgrades.put(upgradeType.getRegistryName(), upgradeType.instanceSupplier.apply(planeEntity));
                }
            }
        }
    }

    /* TODO: rotate player with plane?

    public static boolean playerRotationNeedToPop = false;

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onRenderPre(RenderPlayerEvent.Pre event) {
        Entity entity = event.getPlayer().getLowestRidingEntity();
        if (entity instanceof FurnacePlaneEntity) {
            MatrixStack matrixStack = event.getMatrixStack();
            matrixStack.push();
            matrixStack.rotate(Vector3f.XN.rotationDegrees(((FurnacePlaneEntity) entity).getPitch()));
            playerRotationNeedToPop = true;
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onRenderPost(RenderPlayerEvent.Post event) {
        if (playerRotationNeedToPop) {
            playerRotationNeedToPop = false;
            event.getMatrixStack().pop();
        }
    }
     */
}
