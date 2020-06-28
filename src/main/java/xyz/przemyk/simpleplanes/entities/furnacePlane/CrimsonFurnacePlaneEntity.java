package xyz.przemyk.simpleplanes.entities.furnacePlane;

import net.minecraft.entity.EntityType;
import net.minecraft.world.World;
import xyz.przemyk.simpleplanes.SimplePlanesRegistries;

public class CrimsonFurnacePlaneEntity extends FurnacePlaneEntity {

    public CrimsonFurnacePlaneEntity(EntityType<? extends FurnacePlaneEntity> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

    public CrimsonFurnacePlaneEntity(World worldIn, double x, double y, double z) {
        super(SimplePlanesRegistries.CRIMSON_FURNACE_PLANE_ENTITY.get(), worldIn, x, y, z);
    }

    @Override
    protected void dropItem() {
        entityDropItem(SimplePlanesRegistries.CRIMSON_FURNACE_PLANE_ITEM.get());
    }
}
