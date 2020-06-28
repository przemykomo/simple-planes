package xyz.przemyk.simpleplanes.items.furnacePlane;

import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import xyz.przemyk.simpleplanes.entities.furnacePlane.CrimsonFurnacePlaneEntity;
import xyz.przemyk.simpleplanes.entities.furnacePlane.WarpedFurnacePlaneEntity;
import xyz.przemyk.simpleplanes.items.AbstractPlaneItem;

public class WarpedFurnacePlaneItem extends AbstractPlaneItem<WarpedFurnacePlaneEntity> {

    public WarpedFurnacePlaneItem(Properties properties) {
        super(properties);
    }

    @Override
    protected WarpedFurnacePlaneEntity createPlane(World worldIn, RayTraceResult raytraceresult) {
        return new WarpedFurnacePlaneEntity(worldIn, raytraceresult.getHitVec().x, raytraceresult.getHitVec().y, raytraceresult.getHitVec().z);
    }
}
