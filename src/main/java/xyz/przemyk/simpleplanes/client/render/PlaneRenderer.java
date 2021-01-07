package xyz.przemyk.simpleplanes.client.render;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.EntityModel;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

public class PlaneRenderer extends AbstractPlaneRenderer<PlaneEntity> {

    protected final FurnacePlaneModel planeModel = new FurnacePlaneModel();

    public PlaneRenderer(EntityRendererManager renderManager) {
        super(renderManager);
        shadowSize = 0.6F;
    }

    @Override
    protected EntityModel<PlaneEntity> getModel() {
        return planeModel;
    }
}
