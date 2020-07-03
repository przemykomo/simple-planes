package xyz.przemyk.simpleplanes.entities.furnacePlane;

import net.minecraft.entity.EntityType;
import net.minecraft.world.World;
import xyz.przemyk.simpleplanes.setup.SimplePlanesEntities;
import xyz.przemyk.simpleplanes.setup.SimplePlanesItems;

public class SpruceFurnacePlaneEntity extends FurnacePlaneEntity {

    public SpruceFurnacePlaneEntity(EntityType<? extends FurnacePlaneEntity> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

    public SpruceFurnacePlaneEntity(World worldIn) {
        super(SimplePlanesEntities.SPRUCE_FURNACE_PLANE.get(), worldIn);
    }

    @Override
    protected void dropItem() {
        entityDropItem(SimplePlanesItems.SPRUCE_FURNACE_PLANE.get());
    }
}
