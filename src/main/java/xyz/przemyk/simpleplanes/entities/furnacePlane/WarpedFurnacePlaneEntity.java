package xyz.przemyk.simpleplanes.entities.furnacePlane;

import net.minecraft.entity.EntityType;
import net.minecraft.world.World;
import xyz.przemyk.simpleplanes.SimplePlanesRegistries;

public class WarpedFurnacePlaneEntity extends FurnacePlaneEntity {

    public WarpedFurnacePlaneEntity(EntityType<? extends FurnacePlaneEntity> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

    public WarpedFurnacePlaneEntity(World worldIn, double x, double y, double z) {
        super(SimplePlanesRegistries.WARPED_FURNACE_PLANE_ENTITY.get(), worldIn, x, y, z);
    }

    @Override
    protected void dropItem() {
        entityDropItem(SimplePlanesRegistries.WARPED_FURNACE_PLANE_ITEM.get());
    }
}
