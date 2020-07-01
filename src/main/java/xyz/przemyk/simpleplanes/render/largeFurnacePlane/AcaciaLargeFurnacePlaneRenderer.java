package xyz.przemyk.simpleplanes.render.largeFurnacePlane;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import xyz.przemyk.simpleplanes.entities.largeFurnacePlane.LargeFurnacePlaneEntity;

public class AcaciaLargeFurnacePlaneRenderer extends LargeFurnacePlaneRenderer {

    public static final ResourceLocation TEXTURE_ACACIA = new ResourceLocation("simpleplanes", "textures/entity/plane/large_furnace/acacia.png");
    public static final ResourceLocation TEXTURE_ACACIA_POWERED = new ResourceLocation("simpleplanes", "textures/entity/plane/large_furnace_powered/acacia.png");

    public AcaciaLargeFurnacePlaneRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public ResourceLocation getEntityTexture(LargeFurnacePlaneEntity entity) {
        if (entity.isPowered()) {
            return TEXTURE_ACACIA_POWERED;
        }

        return TEXTURE_ACACIA;
    }
}
