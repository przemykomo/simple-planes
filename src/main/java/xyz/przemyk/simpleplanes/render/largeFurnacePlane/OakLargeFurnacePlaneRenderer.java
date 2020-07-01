package xyz.przemyk.simpleplanes.render.largeFurnacePlane;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import xyz.przemyk.simpleplanes.entities.largeFurnacePlane.LargeFurnacePlaneEntity;

public class OakLargeFurnacePlaneRenderer extends LargeFurnacePlaneRenderer {

    public static final ResourceLocation TEXTURE_OAK = new ResourceLocation("simpleplanes", "textures/entity/plane/large_furnace/oak.png");
    public static final ResourceLocation TEXTURE_OAK_POWERED = new ResourceLocation("simpleplanes", "textures/entity/plane/large_furnace_powered/oak.png");

    public OakLargeFurnacePlaneRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public ResourceLocation getEntityTexture(LargeFurnacePlaneEntity entity) {
        if (entity.isPowered()) {
            return TEXTURE_OAK_POWERED;
        }

        return TEXTURE_OAK;
    }
}
