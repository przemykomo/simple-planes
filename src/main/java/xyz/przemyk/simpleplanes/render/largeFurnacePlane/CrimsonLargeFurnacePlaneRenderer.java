package xyz.przemyk.simpleplanes.render.largeFurnacePlane;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import xyz.przemyk.simpleplanes.entities.largeFurnacePlane.LargeFurnacePlaneEntity;

public class CrimsonLargeFurnacePlaneRenderer extends LargeFurnacePlaneRenderer {

    public static final ResourceLocation TEXTURE_CRIMSON = new ResourceLocation("simpleplanes", "textures/entity/plane/large_furnace/crimson.png");
    public static final ResourceLocation TEXTURE_CRIMSON_POWERED = new ResourceLocation("simpleplanes", "textures/entity/plane/large_furnace_powered/crimson.png");

    public CrimsonLargeFurnacePlaneRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public ResourceLocation getEntityTexture(LargeFurnacePlaneEntity entity) {
        if (entity.isPowered()) {
            return TEXTURE_CRIMSON_POWERED;
        }

        return TEXTURE_CRIMSON;
    }
}
