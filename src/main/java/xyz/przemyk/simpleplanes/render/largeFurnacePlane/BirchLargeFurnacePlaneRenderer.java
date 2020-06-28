package xyz.przemyk.simpleplanes.render.largeFurnacePlane;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import xyz.przemyk.simpleplanes.entities.largeFurnacePlane.LargeFurnacePlaneEntity;

public class BirchLargeFurnacePlaneRenderer extends LargeFurnacePlaneRenderer {

    public static final ResourceLocation TEXTURE_BIRCH = new ResourceLocation("simpleplanes", "textures/entity/plane/large_furnace/birch.png");
    public static final ResourceLocation TEXTURE_BIRCH_POWERED = new ResourceLocation("simpleplanes", "textures/entity/plane/large_furnace_powered/birch.png");

    public BirchLargeFurnacePlaneRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public ResourceLocation getEntityTexture(LargeFurnacePlaneEntity entity) {
        if (entity.isPowered()) {
            return TEXTURE_BIRCH_POWERED;
        }

        return TEXTURE_BIRCH;
    }
}
