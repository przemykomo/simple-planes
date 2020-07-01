package xyz.przemyk.simpleplanes.items.largeFurnacePlane;

import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import xyz.przemyk.simpleplanes.entities.largeFurnacePlane.DarkOakLargeFurnacePlaneEntity;
import xyz.przemyk.simpleplanes.entities.largeFurnacePlane.JungleLargeFurnacePlaneEntity;
import xyz.przemyk.simpleplanes.items.AbstractPlaneItem;

public class JungleLargeFurnacePlaneItem extends AbstractPlaneItem<JungleLargeFurnacePlaneEntity> {

    public JungleLargeFurnacePlaneItem(Properties properties) {
        super(properties);
    }

    @Override
    protected JungleLargeFurnacePlaneEntity createPlane(World worldIn, RayTraceResult raytraceresult) {
        return new JungleLargeFurnacePlaneEntity(worldIn, raytraceresult.getHitVec().x, raytraceresult.getHitVec().y, raytraceresult.getHitVec().z);
    }
}
