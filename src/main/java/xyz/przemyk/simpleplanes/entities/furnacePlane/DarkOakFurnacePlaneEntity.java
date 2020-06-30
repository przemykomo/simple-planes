package xyz.przemyk.simpleplanes.entities.furnacePlane;

import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import xyz.przemyk.simpleplanes.setup.SimplePlanesEntities;
import xyz.przemyk.simpleplanes.setup.SimplePlanesItems;

public class DarkOakFurnacePlaneEntity extends FurnacePlaneEntity {

    public DarkOakFurnacePlaneEntity(EntityType<? extends FurnacePlaneEntity> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

    public DarkOakFurnacePlaneEntity(World worldIn, double x, double y, double z) {
        super(SimplePlanesEntities.DARK_OAK_FURNACE_PLANE.get(), worldIn, x, y, z);
    }

    @Override
    protected void dropItem() {
        entityDropItem(new ItemStack(SimplePlanesItems.DARK_OAK_FURNACE_PLANE.get()));
    }
}
