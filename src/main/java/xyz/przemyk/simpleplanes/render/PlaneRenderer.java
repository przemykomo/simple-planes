package xyz.przemyk.simpleplanes.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

public class PlaneRenderer extends AbstractPlaneRenderer<PlaneEntity> {

    protected final FurnacePlaneModel planeModel = new FurnacePlaneModel();

    public PlaneRenderer(RenderManager renderManager) {
        super(renderManager);
        shadowSize = 0.6F;
    }

    @Override
    protected ModelBase getModel() {
        return planeModel;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public ResourceLocation getEntityTexture(PlaneEntity entity) {
        //        if (entity.isPowered()) {
        //            return new ResourceLocation(SimplePlanesMod.MODID, "textures/entity/plane/furnace_powered/"+entity.getMaterial().name+".png");
        //        }
        return new ResourceLocation(SimplePlanesMod.MODID, "textures/entity/plane/furnace/" + entity.getMaterial().name + ".png");
    }
}
