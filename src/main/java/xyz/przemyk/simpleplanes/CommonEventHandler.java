package xyz.przemyk.simpleplanes;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

@Mod.EventBusSubscriber
public class CommonEventHandler {

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

            for (Upgrade upgrade : planeEntity.upgrades.values()) {
                upgrade.onItemRightClick(event);
            }
        }
    }
}
