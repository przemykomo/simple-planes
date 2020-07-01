package xyz.przemyk.simpleplanes.render.furnacePlane;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import xyz.przemyk.simpleplanes.entities.furnacePlane.FurnacePlaneEntity;

public class SpruceFurnacePlaneRenderer extends FurnacePlaneRenderer {

    public static final ResourceLocation TEXTURE_SPRUCE = new ResourceLocation("simpleplanes", "textures/entity/plane/furnace/spruce.png");
    public static final ResourceLocation TEXTURE_SPRUCE_POWERED = new ResourceLocation("simpleplanes", "textures/entity/plane/furnace_powered/spruce.png");

    public SpruceFurnacePlaneRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public ResourceLocation getEntityTexture(FurnacePlaneEntity entity) {
        if (entity.isPowered()) {
            return TEXTURE_SPRUCE_POWERED;
        }

        return TEXTURE_SPRUCE;
    }
}
