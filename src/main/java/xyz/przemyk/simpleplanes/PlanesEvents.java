package xyz.przemyk.simpleplanes;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.przemyk.simpleplanes.entities.FurnacePlaneEntity;

@Mod.EventBusSubscriber
public class PlanesEvents {
    public static final ResourceLocation COAL_TAG = new ResourceLocation("minecraft", "coals");

    @SubscribeEvent
    public static void interact(PlayerInteractEvent.RightClickItem event) {
        PlayerEntity player = event.getPlayer();
        Entity entity = player.getLowestRidingEntity();
        if (entity instanceof FurnacePlaneEntity && ((FurnacePlaneEntity) entity).getFuel() < 200) {
            ItemStack itemStack = player.getHeldItem(event.getHand());
            if (ItemTags.getCollection().getOrCreate(COAL_TAG).contains(itemStack.getItem())) {
                ((FurnacePlaneEntity) entity).addFuel();
                if (!player.isCreative()) {
                    itemStack.shrink(1);
                }
            }
        }
    }
}
