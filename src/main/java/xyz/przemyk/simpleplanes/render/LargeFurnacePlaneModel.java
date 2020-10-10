package xyz.przemyk.simpleplanes.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import xyz.przemyk.simpleplanes.entities.LargePlaneEntity;

@SuppressWarnings("FieldCanBeLocal")
public class LargeFurnacePlaneModel extends ModelBase {
    public static final int TICKS_PER_PROPELLER_ROTATION = 5; //TODO: move to config

    private final ModelRenderer Body;
    //    private final ModelRenderer bone_propeller;
    private final ModelRenderer bone3;
    private final ModelRenderer bone4;
    private final ModelRenderer bone;
    private final ModelRenderer bone2;
    private final ModelRenderer bone5;
    private final ModelRenderer bone6;
    private final ModelRenderer bone7;
    private final ModelRenderer bone8;

    public LargeFurnacePlaneModel() {
        textureWidth = 16;
        textureHeight = 16;

        Body = new ModelRenderer(this);
        Body.setRotationPoint(0, 17, 0);
        //        setRotationAngle(Body, -0.1745F, 0, 0);
        Body.setTextureOffset(0, 0).addBox(-42, -22, -15, 84, 1, 9, 0);

        //        bone_propeller = new ModelRenderer(this);
        //        bone_propeller.setRotationPoint(0, -7, -21);
        //        Body.addChild(bone_propeller);
        //        setRotationAngle(bone_propeller, 0, 0, 0.6109F);
        //        bone_propeller.setTextureOffset(110, 110).addBox(-16, -1, -1, 32, 2, 1, 0);

        bone3 = new ModelRenderer(this);
        bone3.setRotationPoint(0, 0, 18);
        Body.addChild(bone3);
        bone3.setTextureOffset(0, 64).addBox(-8, -13, -34, 1, 13, 53, 0);
        bone3.setTextureOffset(0, 10).addBox(-7, -1, -34, 14, 1, 53, 0);
        bone3.setTextureOffset(55, 77).addBox(7, -13, -34, 1, 13, 53, 0);
        bone3.setTextureOffset(0, 64).addBox(-7, -13, 18, 14, 13, 1, 0);
        //        bone3.setTextureOffset(81, 10).addBox(-8, -16, 3, 16, 16, 16, -1);
        bone3.setTextureOffset(0, 38).addBox(-8, -13, -35, 16, 13, 1, 0);
        bone3.setTextureOffset(21, 18).addBox(-2, -9, -36, 4, 4, 1, 0);
        bone3.setTextureOffset(110, 77).addBox(-8, -14, -35, 16, 1, 11, 0);
        bone3.setTextureOffset(0, 10).addBox(-1, -12, 19, 2, 11, 17, 0);
        bone3.setTextureOffset(38, 10).addBox(-1, -11, 36, 2, 9, 1, 0);
        bone3.setTextureOffset(0, 0).addBox(-1, -9, 37, 2, 6, 1, 0);
        bone3.setTextureOffset(55, 64).addBox(-19, -8, 22, 38, 1, 12, 0);
        bone3.setTextureOffset(21, 10).addBox(-1, -13, 22, 2, 1, 13, 0);
        bone3.setTextureOffset(24, 42).addBox(-1, -14, 24, 2, 1, 10, 0);
        bone3.setTextureOffset(0, 10).addBox(-1, -15, 27, 2, 1, 6, 0);

        bone4 = new ModelRenderer(this);
        bone4.setRotationPoint(-2, 1, -7);
        Body.addChild(bone4);
        bone4.setTextureOffset(21, 24).addBox(-4, 5, 0, 12, 1, 1, 0);
        bone4.setTextureOffset(20, 52).addBox(4, -1, 0, 1, 6, 1, 0);
        bone4.setTextureOffset(16, 52).addBox(-1, -1, 0, 1, 6, 1, 0);

        bone = new ModelRenderer(this);
        bone.setRotationPoint(-4, 7, 0);
        bone4.addChild(bone);
        bone.setTextureOffset(21, 10).addBox(-1, -3, -2, 1, 3, 5, 0);
        bone.setTextureOffset(8, 52).addBox(-1, -4, -1, 1, 1, 3, 0);
        bone.setTextureOffset(38, 45).addBox(-1, 0, -1, 1, 1, 3, 0);

        bone2 = new ModelRenderer(this);
        bone2.setRotationPoint(9, 7, 0);
        bone4.addChild(bone2);
        bone2.setTextureOffset(0, 17).addBox(-1, -3, -2, 1, 3, 5, 0);
        bone2.setTextureOffset(41, 19).addBox(-1, -4, -1, 1, 1, 3, 0);
        bone2.setTextureOffset(9, 22).addBox(-1, 0, -1, 1, 1, 3, 0);

        bone5 = new ModelRenderer(this);
        bone5.setRotationPoint(0, 0, 0);
        Body.addChild(bone5);
        setRotationAngle(bone5, 0, 0, -0.0873F);
        bone5.setTextureOffset(81, 52).addBox(8, -1, -15, 31, 1, 9, 0);
        bone5.setTextureOffset(48, 44).addBox(27, -19, -14, 1, 18, 1, 0);
        bone5.setTextureOffset(46, 25).addBox(27, -19, -8, 1, 18, 1, 0);

        bone6 = new ModelRenderer(this);
        bone6.setRotationPoint(0, 0, 0);
        Body.addChild(bone6);
        setRotationAngle(bone6, 0, 0, 0.0873F);
        bone6.setTextureOffset(81, 42).addBox(-39, -1, -15, 31, 1, 9, 0);
        bone6.setTextureOffset(42, 26).addBox(-29, -19, -14, 1, 18, 1, 0);
        bone6.setTextureOffset(38, 26).addBox(-29, -19, -8, 1, 18, 1, 0);

        bone7 = new ModelRenderer(this);
        bone7.setRotationPoint(7, -14, -9);
        Body.addChild(bone7);
        setRotationAngle(bone7, 0, 0, 0.3491F);
        bone7.setTextureOffset(4, 52).addBox(0, -8, -4, 1, 8, 1, 0);
        bone7.setTextureOffset(0, 52).addBox(0, -8, 0, 1, 8, 1, 0);

        bone8 = new ModelRenderer(this);
        bone8.setRotationPoint(-7, -14, -9);
        Body.addChild(bone8);
        setRotationAngle(bone8, 0, 0, -0.3491F);
        bone8.setTextureOffset(48, 10).addBox(-1, -8, -4, 1, 8, 1, 0);
        bone8.setTextureOffset(44, 10).addBox(-1, -8, 0, 1, 8, 1, 0);
    }

    @Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        Body.render(scale);
    }


    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
