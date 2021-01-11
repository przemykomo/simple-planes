package xyz.przemyk.simpleplanes.client.render;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.EntityModel;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

public class HelicopterRenderer extends AbstractPlaneRenderer<PlaneEntity> {

    protected final HelicopterModel planeModel = new HelicopterModel();

    public HelicopterRenderer(EntityRendererManager renderManager) {
        super(renderManager);
        propellerModel = new HelicopterPropellerModel();
        shadowSize = 0.6F;
    }

    @Override
    protected EntityModel<PlaneEntity> getModel() {
        return planeModel;
    }
}
