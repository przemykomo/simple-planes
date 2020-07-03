package xyz.przemyk.simpleplanes.entities.furnacePlane;

import net.minecraft.entity.EntityType;
import net.minecraft.world.World;
import xyz.przemyk.simpleplanes.setup.SimplePlanesEntities;
import xyz.przemyk.simpleplanes.setup.SimplePlanesItems;

public class WarpedFurnacePlaneEntity extends FurnacePlaneEntity {

    public WarpedFurnacePlaneEntity(EntityType<? extends FurnacePlaneEntity> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

    public WarpedFurnacePlaneEntity(World worldIn) {
        super(SimplePlanesEntities.WARPED_FURNACE_PLANE.get(), worldIn);
    }

    @Override
    protected void dropItem() {
        entityDropItem(SimplePlanesItems.WARPED_FURNACE_PLANE.get());
    }
}
