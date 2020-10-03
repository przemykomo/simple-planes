package xyz.przemyk.simpleplanes;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesItems;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

import java.util.HashSet;

public class PlanesEvents {
    public static final Identifier NOT_COAL_TAG = new Identifier("simpleplanes", "not_fuel");

    public static TypedActionResult<ItemStack> onUseItem(PlayerEntity player, World world, Hand hand) {
        ItemStack stackInHand = player.getStackInHand(hand);

        if (world.isClient) {
            return TypedActionResult.pass(stackInHand);
        }

        if (player.getVehicle() instanceof PlaneEntity) {
            PlaneEntity planeEntity = (PlaneEntity) player.getVehicle();
            return interact(player, world, hand, stackInHand, planeEntity);
        }
        return TypedActionResult.pass(stackInHand);
    }

    public static ActionResult interact(PlayerEntity player, World world, Hand hand, Entity entity, EntityHitResult hitResult) {
        if (entity instanceof PlaneEntity&&!world.isClient) {
            ItemStack itemStack = player.getStackInHand(hand);
            PlaneEntity planeEntity = (PlaneEntity) entity;
            if(planeEntity.isConnectedThroughVehicle(player)) {
                return ActionResult.PASS;
            }
            TypedActionResult<ItemStack> result = interact(player, world, hand, itemStack, planeEntity);
            return result.getResult();
        }
        return ActionResult.PASS;
    }

    public static TypedActionResult<ItemStack> interact(PlayerEntity player, World world, Hand hand, ItemStack itemStack, PlaneEntity planeEntity) {
        if (itemStack.isEmpty()) {
            return TypedActionResult.pass(itemStack);
        }

        HashSet<Upgrade> upgradesToRemove = new HashSet<>();
        for (Upgrade upgrade : planeEntity.upgrades.values()) {
            if (upgrade.onItemRightClick(player, world, hand, itemStack)) {
                upgradesToRemove.add(upgrade);
            }
        }

        for (Upgrade upgrade : upgradesToRemove) {
            planeEntity.upgrades.remove(upgrade.getType().getRegistryName());
        }

        // some upgrade may shrink itemStack so we need to check if it's empty
        if (itemStack.isEmpty()) {
            return TypedActionResult.consume(itemStack);
        }
        if (itemStack.getItem() instanceof PickaxeItem) {
            if (!world.isClient() && planeEntity.getY() > 110 && planeEntity.getY() < 160 && world.getDimension().hasSkyLight()) {
                itemStack.damage(1, player, (playerEntity) -> {
                    playerEntity.sendToolBreakStatus(hand);
                });
                if (world.random.nextInt(50) == 0) {
                    player.addExperience(100);
                    player.giveItemStack(SimplePlanesItems.CLOUD.getDefaultStack());
                }
            }
        }

        planeEntity.tryToAddUpgrade(player, itemStack);
        return TypedActionResult.consume(itemStack);
    }

}
