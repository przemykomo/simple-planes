package xyz.przemyk.simpleplanes.items;

import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import xyz.przemyk.simpleplanes.PlaneType;
import xyz.przemyk.simpleplanes.entities.furnacePlane.FurnacePlaneEntity;

public class FurnacePlaneItem extends AbstractFurnacePlaneItem<FurnacePlaneEntity> {

    public FurnacePlaneItem(PlaneType typeIn, Properties properties) {
        super(typeIn, properties.maxStackSize(1));
    }

    protected FurnacePlaneEntity createPlane(World worldIn, RayTraceResult raytraceresult) {
        return new FurnacePlaneEntity(type, worldIn, raytraceresult.getHitVec().x, raytraceresult.getHitVec().y, raytraceresult.getHitVec().z);
    }
}
