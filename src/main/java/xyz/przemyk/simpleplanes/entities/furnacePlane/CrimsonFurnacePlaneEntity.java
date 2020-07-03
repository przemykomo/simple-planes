package xyz.przemyk.simpleplanes.entities.furnacePlane;

import net.minecraft.entity.EntityType;
import net.minecraft.world.World;
import xyz.przemyk.simpleplanes.setup.SimplePlanesEntities;
import xyz.przemyk.simpleplanes.setup.SimplePlanesItems;

public class CrimsonFurnacePlaneEntity extends FurnacePlaneEntity {

    public CrimsonFurnacePlaneEntity(EntityType<? extends FurnacePlaneEntity> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

    public CrimsonFurnacePlaneEntity(World worldIn) {
        super(SimplePlanesEntities.CRIMSON_FURNACE_PLANE.get(), worldIn);
    }

    @Override
    protected void dropItem() {
        entityDropItem(SimplePlanesItems.CRIMSON_FURNACE_PLANE.get());
    }
}
