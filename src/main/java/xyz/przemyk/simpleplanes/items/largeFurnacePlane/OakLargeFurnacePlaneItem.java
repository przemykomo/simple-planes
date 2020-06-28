package xyz.przemyk.simpleplanes.items.largeFurnacePlane;

import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import xyz.przemyk.simpleplanes.entities.largeFurnacePlane.JungleLargeFurnacePlaneEntity;
import xyz.przemyk.simpleplanes.entities.largeFurnacePlane.OakLargeFurnacePlaneEntity;
import xyz.przemyk.simpleplanes.items.AbstractPlaneItem;

public class OakLargeFurnacePlaneItem extends AbstractPlaneItem<OakLargeFurnacePlaneEntity> {

    public OakLargeFurnacePlaneItem(Properties properties) {
        super(properties);
    }

    @Override
    protected OakLargeFurnacePlaneEntity createPlane(World worldIn, RayTraceResult raytraceresult) {
        return new OakLargeFurnacePlaneEntity(worldIn, raytraceresult.getHitVec().x, raytraceresult.getHitVec().y, raytraceresult.getHitVec().z);
    }
}
