package xyz.przemyk.simpleplanes.entities.largeFurnacePlane;

import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import xyz.przemyk.simpleplanes.setup.SimplePlanesEntities;
import xyz.przemyk.simpleplanes.setup.SimplePlanesItems;

public class BirchLargeFurnacePlaneEntity extends LargeFurnacePlaneEntity {

    public BirchLargeFurnacePlaneEntity(EntityType<? extends LargeFurnacePlaneEntity> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

    public BirchLargeFurnacePlaneEntity(World worldIn) {
        super(SimplePlanesEntities.BIRCH_LARGE_FURNACE_PLANE.get(), worldIn);
    }

    @Override
    protected void dropItem() {
        entityDropItem(new ItemStack(SimplePlanesItems.BIRCH_LARGE_FURNACE_PLANE.get()));
    }
}
