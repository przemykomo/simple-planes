package xyz.przemyk.simpleplanes.render.furnacePlane;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import xyz.przemyk.simpleplanes.entities.furnacePlane.FurnacePlaneEntity;

public class WarpedFurnacePlaneRenderer extends FurnacePlaneRenderer {

    public static final ResourceLocation TEXTURE_WARPED = new ResourceLocation("simpleplanes", "textures/entity/plane/furnace/warped.png");
    public static final ResourceLocation TEXTURE_WARPED_POWERED = new ResourceLocation("simpleplanes", "textures/entity/plane/furnace_powered/warped.png");

    public WarpedFurnacePlaneRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public ResourceLocation getEntityTexture(FurnacePlaneEntity entity) {
        if (entity.isPowered()) {
            return TEXTURE_WARPED_POWERED;
        }

        return TEXTURE_WARPED;
    }
}
