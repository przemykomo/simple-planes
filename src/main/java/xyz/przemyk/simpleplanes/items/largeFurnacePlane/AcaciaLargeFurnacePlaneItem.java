package xyz.przemyk.simpleplanes.items.largeFurnacePlane;

import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import xyz.przemyk.simpleplanes.entities.furnacePlane.AcaciaFurnacePlaneEntity;
import xyz.przemyk.simpleplanes.entities.largeFurnacePlane.AcaciaLargeFurnacePlaneEntity;
import xyz.przemyk.simpleplanes.items.AbstractPlaneItem;

public class AcaciaLargeFurnacePlaneItem extends AbstractPlaneItem<AcaciaLargeFurnacePlaneEntity> {

    public AcaciaLargeFurnacePlaneItem(Properties properties) {
        super(properties);
    }

    @Override
    protected AcaciaLargeFurnacePlaneEntity createPlane(World worldIn, RayTraceResult raytraceresult) {
        return new AcaciaLargeFurnacePlaneEntity(worldIn, raytraceresult.getHitVec().x, raytraceresult.getHitVec().y, raytraceresult.getHitVec().z);
    }
}
