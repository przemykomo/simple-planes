package xyz.przemyk.simpleplanes;

import java.util.HashSet;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

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
                int burnTime = ForgeHooks.getBurnTime(itemStack);
                if(burnTime>0) {
                    int fuel = (int) ((burnTime / 1600f) * Config.FLY_TICKS_PER_COAL.get());
                    ((PlaneEntity) entity).addFuel(fuel);
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
            if (itemStack.getItem() == Items.PHANTOM_MEMBRANE) {
                planeEntity.setNoGravity(!planeEntity.hasNoGravity());
            }

            planeEntity.tryToAddUpgrade(player, itemStack);
        }
    }

    // TODO: rotate player with plane?

}
