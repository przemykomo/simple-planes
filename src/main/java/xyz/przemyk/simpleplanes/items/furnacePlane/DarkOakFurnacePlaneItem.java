package xyz.przemyk.simpleplanes.items.furnacePlane;

import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import xyz.przemyk.simpleplanes.entities.furnacePlane.DarkOakFurnacePlaneEntity;
import xyz.przemyk.simpleplanes.entities.furnacePlane.OakFurnacePlaneEntity;
import xyz.przemyk.simpleplanes.items.AbstractPlaneItem;

public class DarkOakFurnacePlaneItem extends AbstractPlaneItem<DarkOakFurnacePlaneEntity> {

    public DarkOakFurnacePlaneItem(Properties properties) {
        super(properties);
    }

    @Override
    protected DarkOakFurnacePlaneEntity createPlane(World worldIn, RayTraceResult raytraceresult) {
        return new DarkOakFurnacePlaneEntity(worldIn, raytraceresult.getHitVec().x, raytraceresult.getHitVec().y, raytraceresult.getHitVec().z);
    }
}
