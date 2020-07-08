package xyz.przemyk.simpleplanes.upgrades.shooter;

import net.minecraft.dispenser.IBlockSource;
import net.minecraft.item.ItemStack;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

public interface IShooterBehavior {
    void shoot(PlaneEntity planeEntity, ItemStack itemStack);

}
