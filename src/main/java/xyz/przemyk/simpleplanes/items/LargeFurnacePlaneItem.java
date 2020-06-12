package xyz.przemyk.simpleplanes.items;

import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import xyz.przemyk.simpleplanes.PlaneType;
import xyz.przemyk.simpleplanes.entities.largeFurnacePlane.LargeFurnacePlaneEntity;

public class LargeFurnacePlaneItem extends AbstractFurnacePlaneItem<LargeFurnacePlaneEntity> {

    public LargeFurnacePlaneItem(PlaneType typeIn, Properties properties) {
        super(typeIn, properties);
    }

    @Override
    protected LargeFurnacePlaneEntity createPlane(World worldIn, RayTraceResult raytraceresult) {
        return new LargeFurnacePlaneEntity(type, worldIn, raytraceresult.getHitVec().x, raytraceresult.getHitVec().y, raytraceresult.getHitVec().z);
    }
}
