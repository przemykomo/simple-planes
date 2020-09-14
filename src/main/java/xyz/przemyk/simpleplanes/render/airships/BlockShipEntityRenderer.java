package xyz.przemyk.simpleplanes.render.airships;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.BlockShip.BlockShipEntity;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.render.AbstractPlaneRenderer;
import xyz.przemyk.simpleplanes.render.HelicopterModel;

public class BlockShipEntityRenderer extends AbstractPlaneRenderer<BlockShipEntity> {
    protected final BlockShipEntityModel planeModel = new BlockShipEntityModel();

    public BlockShipEntityRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    protected void renderEngine(BlockShipEntity planeEntity, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        BlockShipEntityModel.renderEngine(planeEntity, partialTicks, matrixStackIn, bufferIn, packedLightIn, OverlayTexture.NO_OVERLAY);
    }

    @Override
    protected EntityModel<BlockShipEntity> getModel() {
        return planeModel;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public ResourceLocation getEntityTexture(BlockShipEntity entity) {
        //        if (entity.isPowered()) {
        //            return new ResourceLocation(SimplePlanesMod.MODID, "textures/entity/plane/furnace_powered/"+entity.getMaterial().name+".png");
        //        }
        return new ResourceLocation(SimplePlanesMod.MODID, "textures/entity/plane/furnace/" + entity.getMaterial().name + ".png");
    }
}
