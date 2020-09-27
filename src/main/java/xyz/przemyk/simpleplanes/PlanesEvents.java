package xyz.przemyk.simpleplanes;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesItems;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

import java.util.HashSet;

@Mod.EventBusSubscriber
public class PlanesEvents {
    public static final ResourceLocation NOT_COAL_TAG = new ResourceLocation("simpleplanes", "not_fuel");

    @SubscribeEvent
    public static void interact(PlayerInteractEvent.RightClickItem event) {
        PlayerEntity player = event.getPlayer();
        Entity entity = player.getLowestRidingEntity();
        if (entity instanceof PlaneEntity) {
            ItemStack itemStack = player.getHeldItem(event.getHand());

            if (itemStack.isEmpty()) {
                return;
            }
            //            todo: try this maybe?

            PlaneEntity planeEntity = (PlaneEntity) entity;

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
            if (itemStack.getItem() instanceof PickaxeItem) {
                if (!event.getWorld().isRemote() && planeEntity.getPosY() > 128 && event.getWorld().getDimensionType().hasSkyLight())
                    itemStack.damageItem(1, player, (playerEntity) -> {
                        playerEntity.sendBreakAnimation(EquipmentSlotType.MAINHAND);
                    });
                if (event.getWorld().rand.nextInt(20) == 0) {
                    player.giveExperiencePoints(10);
                    player.addItemStackToInventory(SimplePlanesItems.CLOUD.get().getDefaultInstance());
                }
            }

            planeEntity.tryToAddUpgrade(player, itemStack);
        }
    }
}
