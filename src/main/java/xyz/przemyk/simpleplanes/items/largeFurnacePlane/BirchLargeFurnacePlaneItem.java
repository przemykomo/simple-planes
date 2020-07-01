package xyz.przemyk.simpleplanes.items.largeFurnacePlane;

import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import xyz.przemyk.simpleplanes.entities.largeFurnacePlane.AcaciaLargeFurnacePlaneEntity;
import xyz.przemyk.simpleplanes.entities.largeFurnacePlane.BirchLargeFurnacePlaneEntity;
import xyz.przemyk.simpleplanes.items.AbstractPlaneItem;

public class BirchLargeFurnacePlaneItem extends AbstractPlaneItem<BirchLargeFurnacePlaneEntity> {

    public BirchLargeFurnacePlaneItem(Properties properties) {
        super(properties);
    }

    @Override
    protected BirchLargeFurnacePlaneEntity createPlane(World worldIn, RayTraceResult raytraceresult) {
        return new BirchLargeFurnacePlaneEntity(worldIn, raytraceresult.getHitVec().x, raytraceresult.getHitVec().y, raytraceresult.getHitVec().z);
    }
}
