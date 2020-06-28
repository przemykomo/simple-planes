package xyz.przemyk.simpleplanes.render.largeFurnacePlane;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import xyz.przemyk.simpleplanes.entities.largeFurnacePlane.LargeFurnacePlaneEntity;

public class WarpedLargeFurnacePlaneRenderer extends LargeFurnacePlaneRenderer {

    public static final ResourceLocation TEXTURE_WARPED = new ResourceLocation("simpleplanes", "textures/entity/plane/large_furnace/warped.png");
    public static final ResourceLocation TEXTURE_WARPED_POWERED = new ResourceLocation("simpleplanes", "textures/entity/plane/large_furnace_powered/warped.png");

    public WarpedLargeFurnacePlaneRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public ResourceLocation getEntityTexture(LargeFurnacePlaneEntity entity) {
        if (entity.isPowered()) {
            return TEXTURE_WARPED_POWERED;
        }

        return TEXTURE_WARPED;
    }
}
