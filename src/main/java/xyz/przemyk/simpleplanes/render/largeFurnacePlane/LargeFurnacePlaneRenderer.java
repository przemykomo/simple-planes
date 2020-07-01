package xyz.przemyk.simpleplanes.render.largeFurnacePlane;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.EntityModel;
import xyz.przemyk.simpleplanes.entities.largeFurnacePlane.LargeFurnacePlaneEntity;
import xyz.przemyk.simpleplanes.render.AbstractPlaneRenderer;

public abstract class LargeFurnacePlaneRenderer extends AbstractPlaneRenderer<LargeFurnacePlaneEntity> {

    protected final LargeFurnacePlaneModel planeModel = new LargeFurnacePlaneModel();

    public LargeFurnacePlaneRenderer(EntityRendererManager renderManager) {
        super(renderManager);
        shadowSize = 1.0f;
    }

    @Override
    protected EntityModel<LargeFurnacePlaneEntity> getModel() {
        return planeModel;
    }

    @Override
    protected float getInAirRotation() {
        return -10f;
    }
}
