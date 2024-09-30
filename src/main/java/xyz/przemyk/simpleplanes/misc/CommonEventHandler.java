package xyz.przemyk.simpleplanes.misc;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

@EventBusSubscriber
public class CommonEventHandler {

    @SubscribeEvent
    public static void interact(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getEntity();
        Entity entity = player.getRootVehicle();
        if (entity instanceof PlaneEntity) {
            ItemStack itemStack = player.getItemInHand(event.getHand());

            if (!itemStack.isEmpty()) {
                PlaneEntity planeEntity = (PlaneEntity) entity;

                for (Upgrade upgrade : planeEntity.upgrades.values()) {
                    upgrade.onItemRightClick(event);
                }
            }
        }
    }
}
