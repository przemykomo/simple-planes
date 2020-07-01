package xyz.przemyk.simpleplanes.render.furnacePlane;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import xyz.przemyk.simpleplanes.entities.furnacePlane.FurnacePlaneEntity;

public class DarkOakFurnacePlaneRenderer extends FurnacePlaneRenderer {

    public static final ResourceLocation TEXTURE_DARK_OAK = new ResourceLocation("simpleplanes", "textures/entity/plane/furnace/dark_oak.png");
    public static final ResourceLocation TEXTURE_DARK_OAK_POWERED = new ResourceLocation("simpleplanes", "textures/entity/plane/furnace_powered/dark_oak.png");

    public DarkOakFurnacePlaneRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public ResourceLocation getEntityTexture(FurnacePlaneEntity entity) {
        if (entity.isPowered()) {
            return TEXTURE_DARK_OAK_POWERED;
        }

        return TEXTURE_DARK_OAK;
    }
}
