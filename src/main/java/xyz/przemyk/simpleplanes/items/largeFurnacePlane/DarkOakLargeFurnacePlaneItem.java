package xyz.przemyk.simpleplanes.items.largeFurnacePlane;

import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import xyz.przemyk.simpleplanes.entities.largeFurnacePlane.BirchLargeFurnacePlaneEntity;
import xyz.przemyk.simpleplanes.entities.largeFurnacePlane.DarkOakLargeFurnacePlaneEntity;
import xyz.przemyk.simpleplanes.items.AbstractPlaneItem;

public class DarkOakLargeFurnacePlaneItem extends AbstractPlaneItem<DarkOakLargeFurnacePlaneEntity> {

    public DarkOakLargeFurnacePlaneItem(Properties properties) {
        super(properties);
    }

    @Override
    protected DarkOakLargeFurnacePlaneEntity createPlane(World worldIn, RayTraceResult raytraceresult) {
        return new DarkOakLargeFurnacePlaneEntity(worldIn, raytraceresult.getHitVec().x, raytraceresult.getHitVec().y, raytraceresult.getHitVec().z);
    }
}
