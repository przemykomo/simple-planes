package xyz.przemyk.simpleplanes.items.furnacePlane;

import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import xyz.przemyk.simpleplanes.entities.furnacePlane.CrimsonFurnacePlaneEntity;
import xyz.przemyk.simpleplanes.entities.furnacePlane.SpruceFurnacePlaneEntity;
import xyz.przemyk.simpleplanes.items.AbstractPlaneItem;

public class CrimsonFurnacePlaneItem extends AbstractPlaneItem<CrimsonFurnacePlaneEntity> {

    public CrimsonFurnacePlaneItem(Properties properties) {
        super(properties);
    }

    @Override
    protected CrimsonFurnacePlaneEntity createPlane(World worldIn, RayTraceResult raytraceresult) {
        return new CrimsonFurnacePlaneEntity(worldIn, raytraceresult.getHitVec().x, raytraceresult.getHitVec().y, raytraceresult.getHitVec().z);
    }
}
