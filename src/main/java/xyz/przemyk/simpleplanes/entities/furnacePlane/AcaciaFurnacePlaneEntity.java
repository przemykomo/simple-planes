package xyz.przemyk.simpleplanes.entities.furnacePlane;

import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import xyz.przemyk.simpleplanes.setup.SimplePlanesEntities;
import xyz.przemyk.simpleplanes.setup.SimplePlanesItems;

public class AcaciaFurnacePlaneEntity extends FurnacePlaneEntity {

    public AcaciaFurnacePlaneEntity(EntityType<? extends FurnacePlaneEntity> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

    public AcaciaFurnacePlaneEntity(World worldIn, double x, double y, double z) {
        super(SimplePlanesEntities.ACACIA_FURNACE_PLANE.get(), worldIn, x, y, z);
    }

    @Override
    protected void dropItem() {
        entityDropItem(new ItemStack(SimplePlanesItems.ACACIA_FURNACE_PLANE.get()));
    }
}
