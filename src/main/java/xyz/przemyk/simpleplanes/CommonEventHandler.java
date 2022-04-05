package xyz.przemyk.simpleplanes;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.przemyk.simpleplanes.entities.LargePlaneEntity;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

@Mod.EventBusSubscriber
public class CommonEventHandler {

    @SubscribeEvent
    public static void interact(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getPlayer();
        Entity entity = player.getRootVehicle();
        if (entity instanceof PlaneEntity) {
            ItemStack itemStack = player.getItemInHand(event.getHand());

            if (!itemStack.isEmpty()) {
//                if (itemStack.getItem() == Items.TNT && entity instanceof LargePlaneEntity &&
//                        ((LargePlaneEntity) entity).tryToAddTNT(player, itemStack)) {
//                    return;
//                }
                PlaneEntity planeEntity = (PlaneEntity) entity;

                for (Upgrade upgrade : planeEntity.upgrades.values()) {
                    upgrade.onItemRightClick(event);
                }
            }
        }
    }
}
