package xyz.przemyk.simpleplanes.render.largeFurnacePlane;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import xyz.przemyk.simpleplanes.entities.largeFurnacePlane.LargeFurnacePlaneEntity;

public class JungleLargeFurnacePlaneRenderer extends LargeFurnacePlaneRenderer {

    public static final ResourceLocation TEXTURE_JUNGLE = new ResourceLocation("simpleplanes", "textures/entity/plane/large_furnace/jungle.png");
    public static final ResourceLocation TEXTURE_JUNGLE_POWERED = new ResourceLocation("simpleplanes", "textures/entity/plane/large_furnace_powered/jungle.png");

    public JungleLargeFurnacePlaneRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public ResourceLocation getEntityTexture(LargeFurnacePlaneEntity entity) {
        if (entity.isPowered()) {
            return TEXTURE_JUNGLE_POWERED;
        }

        return TEXTURE_JUNGLE;
    }
}
