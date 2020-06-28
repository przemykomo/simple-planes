package xyz.przemyk.simpleplanes.render.furnacePlane;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import xyz.przemyk.simpleplanes.entities.furnacePlane.FurnacePlaneEntity;

public class AcaciaFurnacePlaneRenderer extends FurnacePlaneRenderer {

    public static final ResourceLocation TEXTURE_ACACIA           = new ResourceLocation("simpleplanes", "textures/entity/plane/furnace/acacia.png");
    public static final ResourceLocation TEXTURE_ACACIA_POWERED   = new ResourceLocation("simpleplanes", "textures/entity/plane/furnace_powered/acacia.png");

    public AcaciaFurnacePlaneRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public ResourceLocation getEntityTexture(FurnacePlaneEntity entity) {
        if (entity.isPowered()) {
            return TEXTURE_ACACIA_POWERED;
        }

        return TEXTURE_ACACIA;
    }
}
