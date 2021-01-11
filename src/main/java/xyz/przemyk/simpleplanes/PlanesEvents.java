package xyz.przemyk.simpleplanes;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
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
            PlaneEntity planeEntity = (PlaneEntity) entity;

            for (Upgrade upgrade : planeEntity.upgrades.values()) {
                upgrade.onItemRightClick(event);
            }

            // some upgrade may shrink itemStack so we need to check if it's empty
//            if (itemStack.isEmpty()) {
//                return;
//            }

//            planeEntity.tryToAddUpgrade(itemStack);
        }
    }
}
