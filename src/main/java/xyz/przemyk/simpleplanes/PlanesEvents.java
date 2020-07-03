package xyz.przemyk.simpleplanes;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.przemyk.simpleplanes.entities.furnacePlane.FurnacePlaneEntity;
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
        if (entity instanceof FurnacePlaneEntity) {
            ItemStack itemStack = player.getHeldItem(event.getHand());

            if (itemStack.isEmpty()) {
                return;
            }

            FurnacePlaneEntity furnacePlaneEntity = (FurnacePlaneEntity) entity;
            if (furnacePlaneEntity.getFuel() < 200) {
                //func_230235_a_ - contains
                if (ItemTags.getCollection().getOrCreate(COAL_TAG).contains(itemStack.getItem())) {
                    ((FurnacePlaneEntity) entity).addFuel();
                    if (!player.isCreative()) {
                        itemStack.shrink(1);
                    }
                }
            }

            HashSet<Upgrade> upgradesToRemove = new HashSet<>();
            for (Upgrade upgrade : furnacePlaneEntity.upgrades.values()) {
                if (upgrade.onItemRightClick(event)) {
                    upgradesToRemove.add(upgrade);
                }
            }

            for (Upgrade upgrade : upgradesToRemove) {
                furnacePlaneEntity.upgrades.remove(upgrade.getType().getRegistryName());
            }

            // some upgrade may shrink itemStack so we need to check if it's empty
            if (itemStack.isEmpty()) {
                return;
            }

            for (UpgradeType upgradeType : SimplePlanesRegistries.UPGRADE_TYPES.getValues()) {
                if (itemStack.getItem() == upgradeType.getUpgradeItem() && furnacePlaneEntity.canAddUpgrade(upgradeType)) {
                    if (!player.isCreative()) {
                        itemStack.shrink(1);
                    }
                    furnacePlaneEntity.upgrades.put(upgradeType.getRegistryName(), upgradeType.instanceSupplier.apply(furnacePlaneEntity));
                }
            }
        }
    }



}
