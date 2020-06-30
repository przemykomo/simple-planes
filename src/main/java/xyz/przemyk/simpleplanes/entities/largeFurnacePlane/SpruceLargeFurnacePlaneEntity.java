package xyz.przemyk.simpleplanes.entities.largeFurnacePlane;

import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import xyz.przemyk.simpleplanes.setup.SimplePlanesEntities;
import xyz.przemyk.simpleplanes.setup.SimplePlanesItems;

public class SpruceLargeFurnacePlaneEntity extends LargeFurnacePlaneEntity {

    public SpruceLargeFurnacePlaneEntity(EntityType<? extends LargeFurnacePlaneEntity> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

    public SpruceLargeFurnacePlaneEntity(World worldIn, double x, double y, double z) {
        super(SimplePlanesEntities.SPRUCE_LARGE_FURNACE_PLANE.get(), worldIn, x, y, z);
    }

    @Override
    protected void dropItem() {
        entityDropItem(new ItemStack(SimplePlanesItems.SPRUCE_LARGE_FURNACE_PLANE.get()));
    }
}
