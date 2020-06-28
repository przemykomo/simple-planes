package xyz.przemyk.simpleplanes.items.furnacePlane;

import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import xyz.przemyk.simpleplanes.entities.furnacePlane.BirchFurnacePlaneEntity;
import xyz.przemyk.simpleplanes.entities.furnacePlane.OakFurnacePlaneEntity;
import xyz.przemyk.simpleplanes.items.AbstractPlaneItem;

public class BirchFurnacePlaneItem extends AbstractPlaneItem<BirchFurnacePlaneEntity> {

    public BirchFurnacePlaneItem(Properties properties) {
        super(properties);
    }

    @Override
    protected BirchFurnacePlaneEntity createPlane(World worldIn, RayTraceResult raytraceresult) {
        return new BirchFurnacePlaneEntity(worldIn, raytraceresult.getHitVec().x, raytraceresult.getHitVec().y, raytraceresult.getHitVec().z);
    }
}
