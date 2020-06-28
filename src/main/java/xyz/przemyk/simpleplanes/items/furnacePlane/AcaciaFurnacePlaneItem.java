package xyz.przemyk.simpleplanes.items.furnacePlane;

import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import xyz.przemyk.simpleplanes.entities.furnacePlane.AcaciaFurnacePlaneEntity;
import xyz.przemyk.simpleplanes.entities.furnacePlane.OakFurnacePlaneEntity;
import xyz.przemyk.simpleplanes.items.AbstractPlaneItem;

public class AcaciaFurnacePlaneItem extends AbstractPlaneItem<AcaciaFurnacePlaneEntity> {

    public AcaciaFurnacePlaneItem(Properties properties) {
        super(properties);
    }

    @Override
    protected AcaciaFurnacePlaneEntity createPlane(World worldIn, RayTraceResult raytraceresult) {
        return new AcaciaFurnacePlaneEntity(worldIn, raytraceresult.getHitVec().x, raytraceresult.getHitVec().y, raytraceresult.getHitVec().z);
    }
}
