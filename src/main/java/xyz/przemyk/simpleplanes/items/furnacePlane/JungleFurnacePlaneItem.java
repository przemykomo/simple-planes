package xyz.przemyk.simpleplanes.items.furnacePlane;

import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import xyz.przemyk.simpleplanes.entities.furnacePlane.JungleFurnacePlaneEntity;
import xyz.przemyk.simpleplanes.entities.furnacePlane.OakFurnacePlaneEntity;
import xyz.przemyk.simpleplanes.items.AbstractPlaneItem;

public class JungleFurnacePlaneItem extends AbstractPlaneItem<JungleFurnacePlaneEntity> {

    public JungleFurnacePlaneItem(Properties properties) {
        super(properties);
    }

    @Override
    protected JungleFurnacePlaneEntity createPlane(World worldIn, RayTraceResult raytraceresult) {
        return new JungleFurnacePlaneEntity(worldIn, raytraceresult.getHitVec().x, raytraceresult.getHitVec().y, raytraceresult.getHitVec().z);
    }
}
