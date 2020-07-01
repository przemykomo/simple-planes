package xyz.przemyk.simpleplanes.render.furnacePlane;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import xyz.przemyk.simpleplanes.entities.furnacePlane.FurnacePlaneEntity;

public class CrimsonFurnacePlaneRenderer extends FurnacePlaneRenderer {

    public static final ResourceLocation TEXTURE_CRIMSON = new ResourceLocation("simpleplanes", "textures/entity/plane/furnace/crimson.png");
    public static final ResourceLocation TEXTURE_CRIMSON_POWERED = new ResourceLocation("simpleplanes", "textures/entity/plane/furnace_powered/crimson.png");

    public CrimsonFurnacePlaneRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public ResourceLocation getEntityTexture(FurnacePlaneEntity entity) {
        if (entity.isPowered()) {
            return TEXTURE_CRIMSON_POWERED;
        }

        return TEXTURE_CRIMSON;
    }
}
