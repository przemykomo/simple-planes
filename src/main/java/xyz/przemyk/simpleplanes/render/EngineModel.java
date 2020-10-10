package xyz.przemyk.simpleplanes.render;
// Made with Blockbench 3.5.2
// Exported for Minecraft version 1.15

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import xyz.przemyk.simpleplanes.entities.HelicopterEntity;
import xyz.przemyk.simpleplanes.entities.MegaPlaneEntity;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

public class EngineModel extends ModelBase {
    private final ModelRenderer Body;
    static EngineModel INSTANCE = new EngineModel();

    public EngineModel() {
        textureWidth = 64;
        textureHeight = 64;

        Body = new ModelRenderer(this);
        Body.setRotationPoint(0.0F, 17.0F, 0.0F);
        Body.setTextureOffset(0, 0).addBox(-8, -16, 3, 16, 16, 16, -1);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    public static void renderEngine(PlaneEntity planeEntity, float partialTicks, float scale) {
        //		if(true)return;
        if (planeEntity instanceof HelicopterEntity) {
            GlStateManager.translate(0, -0.7, 0.65);
            GlStateManager.scale(0.8, 0.8, 0.8);
        } else if (planeEntity instanceof MegaPlaneEntity) {
            GlStateManager.translate(0, -0.1, -3);
        } else if (planeEntity.isLarge()) {
            GlStateManager.translate(0, 0, 1.1);
        }

//        GlStateManager.rotate(rotationDegreesZ(180));
//        GlStateManager.translate(-0.4, -1, 0.3);
//        GlStateManager.scale(0.82f, 0.82f, 0.82f);
//        IBlockState state = planeEntity.isPowered() ? Blocks.LIT_FURNACE.getDefaultState() : Blocks.FURNACE.getDefaultState();
//        renderBlock(planeEntity, planeEntity.getEntityWorld(), state);

        INSTANCE.render(planeEntity, 0, 0, 0, 0, 0, scale);
    }

    @Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        Body.render(scale);
    }
}