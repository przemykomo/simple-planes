package xyz.przemyk.simpleplanes.upgrades.shooter;

import net.minecraft.dispenser.IBlockSource;
import net.minecraft.item.ItemStack;
import xyz.przemyk.simpleplanes.entities.furnacePlane.FurnacePlaneEntity;

public interface IShooterBehavior {
    void shoot(FurnacePlaneEntity planeEntity, ItemStack itemStack);

}
