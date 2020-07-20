package xyz.przemyk.simpleplanes.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import xyz.przemyk.simpleplanes.entities.LargePlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;

@SuppressWarnings("FieldCanBeLocal")
public class LargeFurnacePlaneModel extends EntityModel<LargePlaneEntity> {
    public static final int TICKS_PER_PROPELLER_ROTATION = 5; //TODO: move to config

    private final ModelRenderer Body;
    private final ModelRenderer bone_propeller;
    private final ModelRenderer bone3;
    private final ModelRenderer bone4;
    private final ModelRenderer bone;
    private final ModelRenderer bone2;
    private final ModelRenderer bone5;
    private final ModelRenderer bone6;
    private final ModelRenderer bone7;
    private final ModelRenderer bone8;

    public LargeFurnacePlaneModel() {
        textureWidth = 256;
        textureHeight = 256;

        Body = new ModelRenderer(this);
        Body.setRotationPoint(0.0F, 17.0F, 0.0F);
//        setRotationAngle(Body, -0.1745F, 0.0F, 0.0F);
        Body.setTextureOffset(0, 0).addBox(-42.0F, -22.0F, -15.0F, 84.0F, 1.0F, 9.0F, 0.0F, false);

        bone_propeller = new ModelRenderer(this);
        bone_propeller.setRotationPoint(0.0F, -7.0F, -21.0F);
        Body.addChild(bone_propeller);
        setRotationAngle(bone_propeller, 0.0F, 0.0F, 0.6109F);
        bone_propeller.setTextureOffset(110, 110).addBox(-16.0F, -1.0F, -1.0F, 32.0F, 2.0F, 1.0F, 0.0F, false);

        bone3 = new ModelRenderer(this);
        bone3.setRotationPoint(0.0F, 0.0F, 18.0F);
        Body.addChild(bone3);
        bone3.setTextureOffset(0, 64).addBox(-8.0F, -13.0F, -34.0F, 1.0F, 13.0F, 53.0F, 0.0F, false);
        bone3.setTextureOffset(0, 10).addBox(-7.0F, -1.0F, -34.0F, 14.0F, 1.0F, 53.0F, 0.0F, false);
        bone3.setTextureOffset(55, 77).addBox(7.0F, -13.0F, -34.0F, 1.0F, 13.0F, 53.0F, 0.0F, false);
        bone3.setTextureOffset(0, 64).addBox(-7.0F, -13.0F, 18.0F, 14.0F, 13.0F, 1.0F, 0.0F, false);
        bone3.setTextureOffset(81, 10).addBox(-8.0F, -16.0F, 3.0F, 16.0F, 16.0F, 16.0F, -1.0F, false);
        bone3.setTextureOffset(0, 38).addBox(-8.0F, -13.0F, -35.0F, 16.0F, 13.0F, 1.0F, 0.0F, false);
        bone3.setTextureOffset(21, 18).addBox(-2.0F, -9.0F, -36.0F, 4.0F, 4.0F, 1.0F, 0.0F, false);
        bone3.setTextureOffset(7, 17).addBox(-1.0F, -8.0F, -39.0F, 2.0F, 2.0F, 3.0F, 0.0F, false);
        bone3.setTextureOffset(110, 77).addBox(-8.0F, -14.0F, -35.0F, 16.0F, 1.0F, 11.0F, 0.0F, false);
        bone3.setTextureOffset(0, 10).addBox(-1.0F, -12.0F, 19.0F, 2.0F, 11.0F, 17.0F, 0.0F, false);
        bone3.setTextureOffset(38, 10).addBox(-1.0F, -11.0F, 36.0F, 2.0F, 9.0F, 1.0F, 0.0F, false);
        bone3.setTextureOffset(0, 0).addBox(-1.0F, -9.0F, 37.0F, 2.0F, 6.0F, 1.0F, 0.0F, false);
        bone3.setTextureOffset(55, 64).addBox(-19.0F, -8.0F, 22.0F, 38.0F, 1.0F, 12.0F, 0.0F, false);
        bone3.setTextureOffset(21, 10).addBox(-1.0F, -13.0F, 22.0F, 2.0F, 1.0F, 13.0F, 0.0F, false);
        bone3.setTextureOffset(24, 42).addBox(-1.0F, -14.0F, 24.0F, 2.0F, 1.0F, 10.0F, 0.0F, false);
        bone3.setTextureOffset(0, 10).addBox(-1.0F, -15.0F, 27.0F, 2.0F, 1.0F, 6.0F, 0.0F, false);

        bone4 = new ModelRenderer(this);
        bone4.setRotationPoint(-2.0F, 1.0F, -7.0F);
        Body.addChild(bone4);
        bone4.setTextureOffset(21, 24).addBox(-4.0F, 5.0F, 0.0F, 12.0F, 1.0F, 1.0F, 0.0F, false);
        bone4.setTextureOffset(20, 52).addBox(4.0F, -1.0F, 0.0F, 1.0F, 6.0F, 1.0F, 0.0F, false);
        bone4.setTextureOffset(16, 52).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 6.0F, 1.0F, 0.0F, false);

        bone = new ModelRenderer(this);
        bone.setRotationPoint(-4.0F, 7.0F, 0.0F);
        bone4.addChild(bone);
        bone.setTextureOffset(21, 10).addBox(-1.0F, -3.0F, -2.0F, 1.0F, 3.0F, 5.0F, 0.0F, false);
        bone.setTextureOffset(8, 52).addBox(-1.0F, -4.0F, -1.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);
        bone.setTextureOffset(38, 45).addBox(-1.0F, 0.0F, -1.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);

        bone2 = new ModelRenderer(this);
        bone2.setRotationPoint(9.0F, 7.0F, 0.0F);
        bone4.addChild(bone2);
        bone2.setTextureOffset(0, 17).addBox(-1.0F, -3.0F, -2.0F, 1.0F, 3.0F, 5.0F, 0.0F, false);
        bone2.setTextureOffset(41, 19).addBox(-1.0F, -4.0F, -1.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);
        bone2.setTextureOffset(9, 22).addBox(-1.0F, 0.0F, -1.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);

        bone5 = new ModelRenderer(this);
        bone5.setRotationPoint(0.0F, 0.0F, 0.0F);
        Body.addChild(bone5);
        setRotationAngle(bone5, 0.0F, 0.0F, -0.0873F);
        bone5.setTextureOffset(81, 52).addBox(8.0F, -1.0F, -15.0F, 31.0F, 1.0F, 9.0F, 0.0F, false);
        bone5.setTextureOffset(48, 44).addBox(27.0F, -19.0F, -14.0F, 1.0F, 18.0F, 1.0F, 0.0F, false);
        bone5.setTextureOffset(46, 25).addBox(27.0F, -19.0F, -8.0F, 1.0F, 18.0F, 1.0F, 0.0F, false);

        bone6 = new ModelRenderer(this);
        bone6.setRotationPoint(0.0F, 0.0F, 0.0F);
        Body.addChild(bone6);
        setRotationAngle(bone6, 0.0F, 0.0F, 0.0873F);
        bone6.setTextureOffset(81, 42).addBox(-39.0F, -1.0F, -15.0F, 31.0F, 1.0F, 9.0F, 0.0F, false);
        bone6.setTextureOffset(42, 26).addBox(-29.0F, -19.0F, -14.0F, 1.0F, 18.0F, 1.0F, 0.0F, false);
        bone6.setTextureOffset(38, 26).addBox(-29.0F, -19.0F, -8.0F, 1.0F, 18.0F, 1.0F, 0.0F, false);

        bone7 = new ModelRenderer(this);
        bone7.setRotationPoint(7.0F, -14.0F, -9.0F);
        Body.addChild(bone7);
        setRotationAngle(bone7, 0.0F, 0.0F, 0.3491F);
        bone7.setTextureOffset(4, 52).addBox(0.0F, -8.0F, -4.0F, 1.0F, 8.0F, 1.0F, 0.0F, false);
        bone7.setTextureOffset(0, 52).addBox(0.0F, -8.0F, 0.0F, 1.0F, 8.0F, 1.0F, 0.0F, false);

        bone8 = new ModelRenderer(this);
        bone8.setRotationPoint(-7.0F, -14.0F, -9.0F);
        Body.addChild(bone8);
        setRotationAngle(bone8, 0.0F, 0.0F, -0.3491F);
        bone8.setTextureOffset(48, 10).addBox(-1.0F, -8.0F, -4.0F, 1.0F, 8.0F, 1.0F, 0.0F, false);
        bone8.setTextureOffset(44, 10).addBox(-1.0F, -8.0F, 0.0F, 1.0F, 8.0F, 1.0F, 0.0F, false);
    }

    @Override
    public void setRotationAngles(LargePlaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
        bone_propeller.showModel = !entity.upgrades.containsKey(SimplePlanesUpgrades.DRAGON.getId());
        if (entity.isPowered()) {
            bone_propeller.rotateAngleZ = ((entity.ticksExisted+limbSwing) % TICKS_PER_PROPELLER_ROTATION) / (float) (TICKS_PER_PROPELLER_ROTATION / 10.0f * Math.PI);
        } else {
            bone_propeller.rotateAngleZ = 1;
        }
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        Body.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
