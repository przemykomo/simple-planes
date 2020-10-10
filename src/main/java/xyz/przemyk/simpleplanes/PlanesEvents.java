package xyz.przemyk.simpleplanes;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

import java.util.HashSet;

@Mod.EventBusSubscriber
public class PlanesEvents {
    public static final ResourceLocation NOT_COAL_TAG = new ResourceLocation("simpleplanes", "not_fuel");

    @SubscribeEvent
    public static void interact(PlayerInteractEvent.RightClickItem event) {
        EntityPlayer player = event.getEntityPlayer();
        Entity entity = player.getLowestRidingEntity();
        if (entity instanceof PlaneEntity) {
            ItemStack itemStack = player.getHeldItem(event.getHand());

            if (itemStack.isEmpty()) {
                return;
            }
            PlaneEntity planeEntity = (PlaneEntity) entity;
            planeEntity.rightClickUpgrades(player,event.getHand(),itemStack);

            // some upgrade may shrink itemStack so we need to check if it's empty
            if (itemStack.isEmpty()) {
                return;
            }
//            if (itemStack.getItem() instanceof ItemPickaxe) {
//                //todo: only if has clouds
//                if (!event.getWorld().isRemote && planeEntity.posY > 110 && planeEntity.posY < 160) {
//                    itemStack.damageItem(1,player);
//                    if (event.getWorld().rand.nextInt(50) == 0) {
//                        player.addExperience(100);
//                        player.addItemStackToInventory(SimplePlanesItems.CLOUD.getDefaultInstance());
//                    }
//                }
//            }

            planeEntity.tryToAddUpgrade(player, itemStack);
        }
    }
}
