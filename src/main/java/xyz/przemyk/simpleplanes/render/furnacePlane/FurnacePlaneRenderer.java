package xyz.przemyk.simpleplanes.render.furnacePlane;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.EntityModel;
import xyz.przemyk.simpleplanes.render.AbstractPlaneRenderer;
import xyz.przemyk.simpleplanes.entities.furnacePlane.FurnacePlaneEntity;
import xyz.przemyk.simpleplanes.upgrades.SprayModel;

public abstract class FurnacePlaneRenderer extends AbstractPlaneRenderer<FurnacePlaneEntity> {

    protected final FurnacePlaneModel planeModel = new FurnacePlaneModel();

    public FurnacePlaneRenderer(EntityRendererManager renderManager) {
        super(renderManager);
        shadowSize = 0.6F;
    }

    @Override
    protected EntityModel<FurnacePlaneEntity> getModel() {
        return planeModel;
    }

    @Override
    protected float getInAirRotation() {
        return -15f;
    }
}
