package xyz.przemyk.simpleplanes.items.largeFurnacePlane;

import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import xyz.przemyk.simpleplanes.entities.largeFurnacePlane.CrimsonLargeFurnacePlaneEntity;
import xyz.przemyk.simpleplanes.entities.largeFurnacePlane.SpruceLargeFurnacePlaneEntity;
import xyz.przemyk.simpleplanes.items.AbstractPlaneItem;

public class CrimsonLargeFurnacePlaneItem extends AbstractPlaneItem<CrimsonLargeFurnacePlaneEntity> {

    public CrimsonLargeFurnacePlaneItem(Properties properties) {
        super(properties);
    }

    @Override
    protected CrimsonLargeFurnacePlaneEntity createPlane(World worldIn, RayTraceResult raytraceresult) {
        return new CrimsonLargeFurnacePlaneEntity(worldIn, raytraceresult.getHitVec().x, raytraceresult.getHitVec().y, raytraceresult.getHitVec().z);
    }
}
