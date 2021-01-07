package xyz.przemyk.simpleplanes.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.util.ResourceLocation;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.LargePlaneEntity;

public class LargePlaneRenderer extends AbstractPlaneRenderer<LargePlaneEntity> {

    protected final LargeFurnacePlaneModel planeModel = new LargeFurnacePlaneModel();

    public LargePlaneRenderer(EntityRendererManager renderManager) {
        super(renderManager);
        shadowSize = 1.0f;
    }

    @Override
    protected EntityModel<LargePlaneEntity> getModel() {
        return planeModel;
    }
}
