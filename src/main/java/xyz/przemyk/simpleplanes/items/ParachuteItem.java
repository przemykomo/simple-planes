package xyz.przemyk.simpleplanes.items;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import xyz.przemyk.simpleplanes.entities.ParachuteEntity;

import net.minecraft.world.item.Item.Properties;

public class ParachuteItem extends Item {

    public ParachuteItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        if (!player.isPassenger()) {
            if (!level.isClientSide) {
                ParachuteEntity parachuteEntity = new ParachuteEntity(level);
                parachuteEntity.setPos(player.position());
                parachuteEntity.setDeltaMovement(player.getDeltaMovement());
                player.startRiding(parachuteEntity, true);
                level.addFreshEntity(parachuteEntity);
            }
            player.playSound(SoundEvents.ARMOR_EQUIP_LEATHER, 1.0f, 1.0f);
            player.awardStat(Stats.ITEM_USED.get(this));
            if (!player.getAbilities().instabuild) {
                itemStack.shrink(1);
            }

            return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide);
        }
        return InteractionResultHolder.pass(itemStack);
    }
}
