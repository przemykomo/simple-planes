package xyz.przemyk.simpleplanes.render.furnacePlane;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import xyz.przemyk.simpleplanes.entities.furnacePlane.FurnacePlaneEntity;

public class BirchFurnacePlaneRenderer extends FurnacePlaneRenderer {

    public static final ResourceLocation TEXTURE_BIRCH = new ResourceLocation("simpleplanes", "textures/entity/plane/furnace/birch.png");
    public static final ResourceLocation TEXTURE_BIRCH_POWERED = new ResourceLocation("simpleplanes", "textures/entity/plane/furnace_powered/birch.png");

    public BirchFurnacePlaneRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public ResourceLocation getEntityTexture(FurnacePlaneEntity entity) {
        if (entity.isPowered()) {
            return TEXTURE_BIRCH_POWERED;
        }

        return TEXTURE_BIRCH;
    }
}
