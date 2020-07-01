package xyz.przemyk.simpleplanes.render.furnacePlane;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import xyz.przemyk.simpleplanes.entities.furnacePlane.FurnacePlaneEntity;

public class JungleFurnacePlaneRenderer extends FurnacePlaneRenderer {

    public static final ResourceLocation TEXTURE_JUNGLE = new ResourceLocation("simpleplanes", "textures/entity/plane/furnace/jungle.png");
    public static final ResourceLocation TEXTURE_JUNGLE_POWERED = new ResourceLocation("simpleplanes", "textures/entity/plane/furnace_powered/jungle.png");

    public JungleFurnacePlaneRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public ResourceLocation getEntityTexture(FurnacePlaneEntity entity) {
        if (entity.isPowered()) {
            return TEXTURE_JUNGLE_POWERED;
        }

        return TEXTURE_JUNGLE;
    }
}
