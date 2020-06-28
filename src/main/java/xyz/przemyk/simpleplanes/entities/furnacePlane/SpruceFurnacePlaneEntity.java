package xyz.przemyk.simpleplanes.entities.furnacePlane;

import net.minecraft.entity.EntityType;
import net.minecraft.world.World;
import xyz.przemyk.simpleplanes.SimplePlanesRegistries;

public class SpruceFurnacePlaneEntity extends FurnacePlaneEntity {

    public SpruceFurnacePlaneEntity(EntityType<? extends FurnacePlaneEntity> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

    public SpruceFurnacePlaneEntity(World worldIn, double x, double y, double z) {
        super(SimplePlanesRegistries.SPRUCE_FURNACE_PLANE_ENTITY.get(), worldIn, x, y, z);
    }

    @Override
    protected void dropItem() {
        entityDropItem(SimplePlanesRegistries.SPRUCE_FURNACE_PLANE_ITEM.get());
    }
}
