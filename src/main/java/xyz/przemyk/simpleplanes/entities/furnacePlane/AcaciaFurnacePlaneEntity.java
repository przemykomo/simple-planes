package xyz.przemyk.simpleplanes.entities.furnacePlane;

import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import xyz.przemyk.simpleplanes.SimplePlanesRegistries;

public class AcaciaFurnacePlaneEntity extends FurnacePlaneEntity {

    public AcaciaFurnacePlaneEntity(EntityType<? extends FurnacePlaneEntity> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

    public AcaciaFurnacePlaneEntity(World worldIn, double x, double y, double z) {
        super(SimplePlanesRegistries.ACACIA_FURNACE_PLANE_ENTITY.get(), worldIn, x, y, z);
    }

    @Override
    protected void dropItem() {
        entityDropItem(new ItemStack(SimplePlanesRegistries.ACACIA_FURNACE_PLANE_ITEM.get()));
    }
}
