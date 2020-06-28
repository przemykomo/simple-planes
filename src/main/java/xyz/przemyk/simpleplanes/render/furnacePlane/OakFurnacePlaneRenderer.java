package xyz.przemyk.simpleplanes.render.furnacePlane;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import xyz.przemyk.simpleplanes.entities.furnacePlane.FurnacePlaneEntity;

public class OakFurnacePlaneRenderer extends FurnacePlaneRenderer {

    public static final ResourceLocation TEXTURE_OAK = new ResourceLocation("simpleplanes", "textures/entity/plane/furnace/oak.png");
    public static final ResourceLocation TEXTURE_OAK_POWERED = new ResourceLocation("simpleplanes", "textures/entity/plane/furnace_powered/oak.png");

    public OakFurnacePlaneRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public ResourceLocation getEntityTexture(FurnacePlaneEntity entity) {
        if (entity.isPowered()) {
            return TEXTURE_OAK_POWERED;
        }
        return TEXTURE_OAK;
    }
}
