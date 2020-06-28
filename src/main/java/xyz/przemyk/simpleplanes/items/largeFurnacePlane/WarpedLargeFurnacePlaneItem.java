package xyz.przemyk.simpleplanes.items.largeFurnacePlane;

import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import xyz.przemyk.simpleplanes.entities.largeFurnacePlane.SpruceLargeFurnacePlaneEntity;
import xyz.przemyk.simpleplanes.entities.largeFurnacePlane.WarpedLargeFurnacePlaneEntity;
import xyz.przemyk.simpleplanes.items.AbstractPlaneItem;

public class WarpedLargeFurnacePlaneItem extends AbstractPlaneItem<WarpedLargeFurnacePlaneEntity> {

    public WarpedLargeFurnacePlaneItem(Properties properties) {
        super(properties);
    }

    @Override
    protected WarpedLargeFurnacePlaneEntity createPlane(World worldIn, RayTraceResult raytraceresult) {
        return new WarpedLargeFurnacePlaneEntity(worldIn, raytraceresult.getHitVec().x, raytraceresult.getHitVec().y, raytraceresult.getHitVec().z);
    }
}
