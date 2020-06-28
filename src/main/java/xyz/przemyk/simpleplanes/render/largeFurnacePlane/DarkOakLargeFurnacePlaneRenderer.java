package xyz.przemyk.simpleplanes.render.largeFurnacePlane;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import xyz.przemyk.simpleplanes.entities.largeFurnacePlane.LargeFurnacePlaneEntity;

public class DarkOakLargeFurnacePlaneRenderer extends LargeFurnacePlaneRenderer {

    public static final ResourceLocation TEXTURE_DARK_OAK = new ResourceLocation("simpleplanes", "textures/entity/plane/large_furnace/dark_oak.png");
    public static final ResourceLocation TEXTURE_DARK_OAK_POWERED = new ResourceLocation("simpleplanes", "textures/entity/plane/large_furnace_powered/dark_oak.png");

    public DarkOakLargeFurnacePlaneRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public ResourceLocation getEntityTexture(LargeFurnacePlaneEntity entity) {
        if (entity.isPowered()) {
            return TEXTURE_DARK_OAK_POWERED;
        }

        return TEXTURE_DARK_OAK;
    }
}
