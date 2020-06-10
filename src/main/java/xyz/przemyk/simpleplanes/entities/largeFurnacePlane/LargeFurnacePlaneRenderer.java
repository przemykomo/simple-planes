package xyz.przemyk.simpleplanes.entities.largeFurnacePlane;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.util.ResourceLocation;
import xyz.przemyk.simpleplanes.entities.AbstractPlaneRenderer;
import xyz.przemyk.simpleplanes.entities.furnacePlane.FurnacePlaneEntity;

public class LargeFurnacePlaneRenderer extends AbstractPlaneRenderer<LargeFurnacePlaneEntity> {

    public static final ResourceLocation TEXTURE_OAK              = new ResourceLocation("simpleplanes", "textures/entity/plane/large_furnace/oak.png");
    public static final ResourceLocation TEXTURE_OAK_POWERED      = new ResourceLocation("simpleplanes", "textures/entity/plane/large_furnace_powered/oak.png");
    public static final ResourceLocation TEXTURE_SPRUCE           = new ResourceLocation("simpleplanes", "textures/entity/plane/large_furnace/spruce.png");
    public static final ResourceLocation TEXTURE_SPRUCE_POWERED   = new ResourceLocation("simpleplanes", "textures/entity/plane/large_furnace_powered/spruce.png");
    public static final ResourceLocation TEXTURE_BIRCH            = new ResourceLocation("simpleplanes", "textures/entity/plane/large_furnace/birch.png");
    public static final ResourceLocation TEXTURE_BIRCH_POWERED    = new ResourceLocation("simpleplanes", "textures/entity/plane/large_furnace_powered/birch.png");
    public static final ResourceLocation TEXTURE_JUNGLE           = new ResourceLocation("simpleplanes", "textures/entity/plane/large_furnace/jungle.png");
    public static final ResourceLocation TEXTURE_JUNGLE_POWERED   = new ResourceLocation("simpleplanes", "textures/entity/plane/large_furnace_powered/jungle.png");
    public static final ResourceLocation TEXTURE_ACACIA           = new ResourceLocation("simpleplanes", "textures/entity/plane/large_furnace/acacia.png");
    public static final ResourceLocation TEXTURE_ACACIA_POWERED   = new ResourceLocation("simpleplanes", "textures/entity/plane/large_furnace_powered/acacia.png");
    public static final ResourceLocation TEXTURE_DARK_OAK         = new ResourceLocation("simpleplanes", "textures/entity/plane/large_furnace/dark_oak.png");
    public static final ResourceLocation TEXTURE_DARK_OAK_POWERED = new ResourceLocation("simpleplanes", "textures/entity/plane/large_furnace_powered/dark_oak.png");

    protected final LargeFurnacePlaneModel planeModel = new LargeFurnacePlaneModel();

    public LargeFurnacePlaneRenderer(EntityRendererManager renderManager) {
        super(renderManager);
        shadowSize = 1.0f;
    }

    @Override
    protected EntityModel<LargeFurnacePlaneEntity> getModel() {
        return planeModel;
    }

    @Override
    protected float getInAirRotation() {
        return -10f;
    }

    @Override
    public ResourceLocation getEntityTexture(LargeFurnacePlaneEntity entity) {
        if (entity.isPowered()) {
            switch (entity.getPlaneType()) {
                case OAK:
                    return TEXTURE_OAK_POWERED;
                case SPRUCE:
                    return TEXTURE_SPRUCE_POWERED;
                case BIRCH:
                    return TEXTURE_BIRCH_POWERED;
                case JUNGLE:
                    return TEXTURE_JUNGLE_POWERED;
                case ACACIA:
                    return TEXTURE_ACACIA_POWERED;
                case DARK_OAK:
                    return TEXTURE_DARK_OAK_POWERED;
            }
        }

        switch (entity.getPlaneType()) {
            case OAK:
                return TEXTURE_OAK;
            case SPRUCE:
                return TEXTURE_SPRUCE;
            case BIRCH:
                return TEXTURE_BIRCH;
            case JUNGLE:
                return TEXTURE_JUNGLE;
            case ACACIA:
                return TEXTURE_ACACIA;
            case DARK_OAK:
                return TEXTURE_DARK_OAK;
        }

        return TEXTURE_OAK;
    }
}
