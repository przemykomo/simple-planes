package xyz.przemyk.simpleplanes.entities.largeFurnacePlane;

import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import xyz.przemyk.simpleplanes.SimplePlanesRegistries;

public class JungleLargeFurnacePlaneEntity extends LargeFurnacePlaneEntity {

    public JungleLargeFurnacePlaneEntity(EntityType<? extends LargeFurnacePlaneEntity> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

    public JungleLargeFurnacePlaneEntity(World worldIn, double x, double y, double z) {
        super(SimplePlanesRegistries.JUNGLE_LARGE_FURNACE_PLANE_ENTITY.get(), worldIn, x, y, z);
    }

    @Override
    protected void dropItem() {
        entityDropItem(new ItemStack(SimplePlanesRegistries.JUNGLE_LARGE_FURNACE_PLANE_ITEM.get()));
    }
}
