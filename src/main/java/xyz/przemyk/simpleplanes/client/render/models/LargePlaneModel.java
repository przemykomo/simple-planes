package xyz.przemyk.simpleplanes.client.render.models;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import xyz.przemyk.simpleplanes.entities.LargePlaneEntity;

@SuppressWarnings("FieldCanBeLocal")
public class LargePlaneModel extends EntityModel<LargePlaneEntity> {

    private final ModelRenderer Body;
    private final ModelRenderer bone3;
    private final ModelRenderer bone4;
    private final ModelRenderer bone;
    private final ModelRenderer bone2;
    private final ModelRenderer bone5;
    private final ModelRenderer bone6;
    private final ModelRenderer bone7;
    private final ModelRenderer bone8;
    private final ModelRenderer bone9;
    private final ModelRenderer bb_main;
    private final ModelRenderer left_r1;

    public LargePlaneModel() {
        textureWidth = 16;
        textureHeight = 16;

        Body = new ModelRenderer(this);
        Body.setRotationPoint(0.0F, 17.0F, 0.0F);


        bone3 = new ModelRenderer(this);
        bone3.setRotationPoint(0.0F, 0.0F, 18.0F);
        Body.addChild(bone3);
        bone3.setTextureOffset(0, 64).addBox(-9.0F, -13.0F, -34.0F, 2.0F, 13.0F, 53.0F, 0.0F, false);
        bone3.setTextureOffset(0, 10).addBox(-7.0F, -1.0F, -34.0F, 14.0F, 2.0F, 53.0F, 0.0F, false);
        bone3.setTextureOffset(55, 77).addBox(7.0F, -13.0F, -34.0F, 2.0F, 13.0F, 53.0F, 0.0F, false);
        bone3.setTextureOffset(0, 64).addBox(-7.0F, -13.0F, 18.0F, 14.0F, 13.0F, 2.0F, 0.0F, false);
        bone3.setTextureOffset(0, 38).addBox(-8.0F, -13.0F, -36.0F, 16.0F, 13.0F, 2.0F, 0.0F, false);
        bone3.setTextureOffset(110, 77).addBox(-8.0F, -14.0F, -35.0F, 16.0F, 1.0F, 11.0F, 0.0F, false);
        bone3.setTextureOffset(0, 10).addBox(-1.0F, -12.0F, 20.0F, 2.0F, 11.0F, 17.0F, 0.0F, false);
        bone3.setTextureOffset(0, 0).addBox(-1.0F, -12.0F, 37.0F, 2.0F, 9.0F, 3.0F, 0.0F, false);
        bone3.setTextureOffset(55, 64).addBox(-19.0F, -8.0F, 23.0F, 38.0F, 2.0F, 12.0F, 0.0F, false);
        bone3.setTextureOffset(21, 10).addBox(-1.0F, -16.0F, 23.0F, 2.0F, 4.0F, 14.0F, 0.0F, false);

        bone4 = new ModelRenderer(this);
        bone4.setRotationPoint(-2.0F, 1.0F, -7.0F);
        Body.addChild(bone4);


        bone = new ModelRenderer(this);
        bone.setRotationPoint(-4.0F, 7.0F, 0.0F);
        bone4.addChild(bone);


        bone2 = new ModelRenderer(this);
        bone2.setRotationPoint(9.0F, 7.0F, 0.0F);
        bone4.addChild(bone2);


        bone5 = new ModelRenderer(this);
        bone5.setRotationPoint(0.0F, 0.0F, 0.0F);
        Body.addChild(bone5);
        setRotationAngle(bone5, 0.0F, 0.0F, -0.0873F);


        bone6 = new ModelRenderer(this);
        bone6.setRotationPoint(0.0F, 0.0F, 0.0F);
        Body.addChild(bone6);
        setRotationAngle(bone6, 0.0F, 0.0F, 0.0873F);


        bone7 = new ModelRenderer(this);
        bone7.setRotationPoint(7.0F, -14.0F, -9.0F);
        Body.addChild(bone7);
        setRotationAngle(bone7, 0.0F, 0.0F, 0.3491F);


        bone8 = new ModelRenderer(this);
        bone8.setRotationPoint(-7.0F, -14.0F, -9.0F);
        Body.addChild(bone8);
        setRotationAngle(bone8, 0.0F, 0.0F, -0.3491F);


        bone9 = new ModelRenderer(this);
        bone9.setRotationPoint(0.0F, 24.0F, 0.0F);
        bone9.setTextureOffset(0, 38).addBox(-6.0F, -1.0F, -8.0F, 12.0F, 2.0F, 3.0F, 0.0F, false);
        bone9.setTextureOffset(37, 45).addBox(-8.0F, -3.0F, -9.0F, 2.0F, 5.0F, 5.0F, 0.0F, false);
        bone9.setTextureOffset(12, 59).addBox(-3.0F, -7.0F, -8.0F, 1.0F, 6.0F, 3.0F, 0.0F, false);
        bone9.setTextureOffset(16, 59).addBox(2.0F, -7.0F, -8.0F, 1.0F, 6.0F, 3.0F, 0.0F, false);
        bone9.setTextureOffset(1, 2).addBox(6.0F, -3.0F, -9.0F, 2.0F, 5.0F, 5.0F, 0.0F, false);

        bb_main = new ModelRenderer(this);
        bb_main.setRotationPoint(0.0F, 24.0F, 0.0F);
        bb_main.setTextureOffset(30, 45).addBox(-27.9564F, -28.0019F, -11.0F, 2.0F, 19.0F, 2.0F, 0.0F, false);
        bb_main.setTextureOffset(74, 58).addBox(9.0F, -10.0F, -15.0F, 25.0F, 2.0F, 10.0F, 0.0F, false);
        bb_main.setTextureOffset(74, 58).addBox(-33.5413F, -9.7605F, -15.0F, 25.0F, 2.0F, 10.0F, 0.0F, false);
        bb_main.setTextureOffset(74, 58).addBox(-33.1968F, -29.7295F, -15.0F, 66.0F, 2.0F, 10.0F, 0.0F, false);
        bb_main.setTextureOffset(30, 45).addBox(-6.989F, -27.7476F, -11.0F, 2.0F, 20.0F, 2.0F, 0.0F, false);
        bb_main.setTextureOffset(8, 59).addBox(5.324F, -28.1751F, -11.0F, 2.0F, 20.0F, 2.0F, 0.0F, false);

        left_r1 = new ModelRenderer(this);
        left_r1.setRotationPoint(25.8637F, -18.0544F, -9.8508F);
        bb_main.addChild(left_r1);
        setRotationAngle(left_r1, 0.0F, 0.0436F, 0.0F);
        left_r1.setTextureOffset(8, 59).addBox(-1.0F, -9.5F, -1.0F, 2.0F, 19.0F, 2.0F, 0.0F, false);
    }

    @Override
    public void setRotationAngles(LargePlaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
        //previously the render function, render code was moved to a method below
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        Body.render(matrixStack, buffer, packedLight, packedOverlay);
        bone9.render(matrixStack, buffer, packedLight, packedOverlay);
        bb_main.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
