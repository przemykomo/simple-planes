package xyz.przemyk.simpleplanes.items.furnacePlane;

import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import xyz.przemyk.simpleplanes.entities.furnacePlane.OakFurnacePlaneEntity;
import xyz.przemyk.simpleplanes.items.AbstractPlaneItem;

public class OakFurnacePlaneItem extends AbstractPlaneItem<OakFurnacePlaneEntity> {

    public OakFurnacePlaneItem(Properties properties) {
        super(properties);
    }

    @Override
    protected OakFurnacePlaneEntity createPlane(World worldIn, RayTraceResult raytraceresult) {
        return new OakFurnacePlaneEntity(worldIn, raytraceresult.getHitVec().x, raytraceresult.getHitVec().y, raytraceresult.getHitVec().z);
    }
}
