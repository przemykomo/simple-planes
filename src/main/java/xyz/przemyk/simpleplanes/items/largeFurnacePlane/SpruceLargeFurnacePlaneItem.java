package xyz.przemyk.simpleplanes.items.largeFurnacePlane;

import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import xyz.przemyk.simpleplanes.entities.largeFurnacePlane.OakLargeFurnacePlaneEntity;
import xyz.przemyk.simpleplanes.entities.largeFurnacePlane.SpruceLargeFurnacePlaneEntity;
import xyz.przemyk.simpleplanes.items.AbstractPlaneItem;

public class SpruceLargeFurnacePlaneItem extends AbstractPlaneItem<SpruceLargeFurnacePlaneEntity> {

    public SpruceLargeFurnacePlaneItem(Properties properties) {
        super(properties);
    }

    @Override
    protected SpruceLargeFurnacePlaneEntity createPlane(World worldIn, RayTraceResult raytraceresult) {
        return new SpruceLargeFurnacePlaneEntity(worldIn, raytraceresult.getHitVec().x, raytraceresult.getHitVec().y, raytraceresult.getHitVec().z);
    }
}
