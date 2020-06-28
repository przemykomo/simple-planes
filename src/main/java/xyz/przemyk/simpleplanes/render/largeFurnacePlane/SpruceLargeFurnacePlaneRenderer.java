package xyz.przemyk.simpleplanes.render.largeFurnacePlane;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import xyz.przemyk.simpleplanes.entities.largeFurnacePlane.LargeFurnacePlaneEntity;

public class SpruceLargeFurnacePlaneRenderer extends LargeFurnacePlaneRenderer {

    public static final ResourceLocation TEXTURE_SPRUCE = new ResourceLocation("simpleplanes", "textures/entity/plane/large_furnace/spruce.png");
    public static final ResourceLocation TEXTURE_SPRUCE_POWERED = new ResourceLocation("simpleplanes", "textures/entity/plane/large_furnace_powered/spruce.png");

    public SpruceLargeFurnacePlaneRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public ResourceLocation getEntityTexture(LargeFurnacePlaneEntity entity) {
        if (entity.isPowered()) {
            return TEXTURE_SPRUCE_POWERED;
        }

        return TEXTURE_SPRUCE;
    }
}
