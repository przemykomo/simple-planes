package xyz.przemyk.simpleplanes.entities.furnacePlane;

import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import xyz.przemyk.simpleplanes.SimplePlanesRegistries;

public class JungleFurnacePlaneEntity extends FurnacePlaneEntity {

    public JungleFurnacePlaneEntity(EntityType<? extends FurnacePlaneEntity> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

    public JungleFurnacePlaneEntity(World worldIn, double x, double y, double z) {
        super(SimplePlanesRegistries.JUNGLE_FURNACE_PLANE_ENTITY.get(), worldIn, x, y, z);
    }

    @Override
    protected void dropItem() {
        entityDropItem(new ItemStack(SimplePlanesRegistries.JUNGLE_FURNACE_PLANE_ITEM.get()));
    }
}
