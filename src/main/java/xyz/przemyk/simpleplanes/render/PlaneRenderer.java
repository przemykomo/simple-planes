package xyz.przemyk.simpleplanes.render;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.util.ResourceLocation;
import xyz.przemyk.simpleplanes.entities.AbstractPlaneEntityType;
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

    @SuppressWarnings("rawtypes")
    @Override
    public ResourceLocation getEntityTexture(PlaneEntity entity) {
        if (entity.isPowered()) {
            return ((AbstractPlaneEntityType) entity.getType()).texturePowered;
        }
        return ((AbstractPlaneEntityType) entity.getType()).texture;
    }
}
