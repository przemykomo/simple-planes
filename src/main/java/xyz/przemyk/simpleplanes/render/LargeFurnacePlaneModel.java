package xyz.przemyk.simpleplanes.render;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import xyz.przemyk.simpleplanes.entities.LargePlaneEntity;

@SuppressWarnings("FieldCanBeLocal")
public class LargeFurnacePlaneModel extends EntityModel<LargePlaneEntity> {
    public static final int TICKS_PER_PROPELLER_ROTATION = 5; //TODO: move to config

    private final ModelPart Body;
    //    private final ModelRenderer bone_propeller;
    private final ModelPart bone3;
    private final ModelPart bone4;
    private final ModelPart bone;
    private final ModelPart bone2;
    private final ModelPart bone5;
    private final ModelPart bone6;
    private final ModelPart bone7;
    private final ModelPart bone8;

    public LargeFurnacePlaneModel() {
        textureWidth = 16;
        textureHeight = 16;

        Body = new ModelPart(this);
        Body.setPivot(0.0F, 17.0F, 0.0F);
        //        setRotationAngle(Body, -0.1745F, 0.0F, 0.0F);
        Body.setTextureOffset(0, 0).addCuboid(-42.0F, -22.0F, -15.0F, 84.0F, 1.0F, 9.0F, 0.0F, false);

        //        bone_propeller = new ModelRenderer(this);
        //        bone_propeller.setRotationPoint(0.0F, -7.0F, -21.0F);
        //        Body.addChild(bone_propeller);
        //        setRotationAngle(bone_propeller, 0.0F, 0.0F, 0.6109F);
        //        bone_propeller.setTextureOffset(110, 110).addBox(-16.0F, -1.0F, -1.0F, 32.0F, 2.0F, 1.0F, 0.0F, false);

        bone3 = new ModelPart(this);
        bone3.setPivot(0.0F, 0.0F, 18.0F);
        Body.addChild(bone3);
        bone3.setTextureOffset(0, 64).addCuboid(-8.0F, -13.0F, -34.0F, 1.0F, 13.0F, 53.0F, 0.0F, false);
        bone3.setTextureOffset(0, 10).addCuboid(-7.0F, -1.0F, -34.0F, 14.0F, 1.0F, 53.0F, 0.0F, false);
        bone3.setTextureOffset(55, 77).addCuboid(7.0F, -13.0F, -34.0F, 1.0F, 13.0F, 53.0F, 0.0F, false);
        bone3.setTextureOffset(0, 64).addCuboid(-7.0F, -13.0F, 18.0F, 14.0F, 13.0F, 1.0F, 0.0F, false);
        //        bone3.setTextureOffset(81, 10).addBox(-8.0F, -16.0F, 3.0F, 16.0F, 16.0F, 16.0F, -1.0F, false);
        bone3.setTextureOffset(0, 38).addCuboid(-8.0F, -13.0F, -35.0F, 16.0F, 13.0F, 1.0F, 0.0F, false);
        bone3.setTextureOffset(21, 18).addCuboid(-2.0F, -9.0F, -36.0F, 4.0F, 4.0F, 1.0F, 0.0F, false);
        bone3.setTextureOffset(110, 77).addCuboid(-8.0F, -14.0F, -35.0F, 16.0F, 1.0F, 11.0F, 0.0F, false);
        bone3.setTextureOffset(0, 10).addCuboid(-1.0F, -12.0F, 19.0F, 2.0F, 11.0F, 17.0F, 0.0F, false);
        bone3.setTextureOffset(38, 10).addCuboid(-1.0F, -11.0F, 36.0F, 2.0F, 9.0F, 1.0F, 0.0F, false);
        bone3.setTextureOffset(0, 0).addCuboid(-1.0F, -9.0F, 37.0F, 2.0F, 6.0F, 1.0F, 0.0F, false);
        bone3.setTextureOffset(55, 64).addCuboid(-19.0F, -8.0F, 22.0F, 38.0F, 1.0F, 12.0F, 0.0F, false);
        bone3.setTextureOffset(21, 10).addCuboid(-1.0F, -13.0F, 22.0F, 2.0F, 1.0F, 13.0F, 0.0F, false);
        bone3.setTextureOffset(24, 42).addCuboid(-1.0F, -14.0F, 24.0F, 2.0F, 1.0F, 10.0F, 0.0F, false);
        bone3.setTextureOffset(0, 10).addCuboid(-1.0F, -15.0F, 27.0F, 2.0F, 1.0F, 6.0F, 0.0F, false);

        bone4 = new ModelPart(this);
        bone4.setPivot(-2.0F, 1.0F, -7.0F);
        Body.addChild(bone4);
        bone4.setTextureOffset(21, 24).addCuboid(-4.0F, 5.0F, 0.0F, 12.0F, 1.0F, 1.0F, 0.0F, false);
        bone4.setTextureOffset(20, 52).addCuboid(4.0F, -1.0F, 0.0F, 1.0F, 6.0F, 1.0F, 0.0F, false);
        bone4.setTextureOffset(16, 52).addCuboid(-1.0F, -1.0F, 0.0F, 1.0F, 6.0F, 1.0F, 0.0F, false);

        bone = new ModelPart(this);
        bone.setPivot(-4.0F, 7.0F, 0.0F);
        bone4.addChild(bone);
        bone.setTextureOffset(21, 10).addCuboid(-1.0F, -3.0F, -2.0F, 1.0F, 3.0F, 5.0F, 0.0F, false);
        bone.setTextureOffset(8, 52).addCuboid(-1.0F, -4.0F, -1.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);
        bone.setTextureOffset(38, 45).addCuboid(-1.0F, 0.0F, -1.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);

        bone2 = new ModelPart(this);
        bone2.setPivot(9.0F, 7.0F, 0.0F);
        bone4.addChild(bone2);
        bone2.setTextureOffset(0, 17).addCuboid(-1.0F, -3.0F, -2.0F, 1.0F, 3.0F, 5.0F, 0.0F, false);
        bone2.setTextureOffset(41, 19).addCuboid(-1.0F, -4.0F, -1.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);
        bone2.setTextureOffset(9, 22).addCuboid(-1.0F, 0.0F, -1.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);

        bone5 = new ModelPart(this);
        bone5.setPivot(0.0F, 0.0F, 0.0F);
        Body.addChild(bone5);
        setRotationAngle(bone5, 0.0F, 0.0F, -0.0873F);
        bone5.setTextureOffset(81, 52).addCuboid(8.0F, -1.0F, -15.0F, 31.0F, 1.0F, 9.0F, 0.0F, false);
        bone5.setTextureOffset(48, 44).addCuboid(27.0F, -19.0F, -14.0F, 1.0F, 18.0F, 1.0F, 0.0F, false);
        bone5.setTextureOffset(46, 25).addCuboid(27.0F, -19.0F, -8.0F, 1.0F, 18.0F, 1.0F, 0.0F, false);

        bone6 = new ModelPart(this);
        bone6.setPivot(0.0F, 0.0F, 0.0F);
        Body.addChild(bone6);
        setRotationAngle(bone6, 0.0F, 0.0F, 0.0873F);
        bone6.setTextureOffset(81, 42).addCuboid(-39.0F, -1.0F, -15.0F, 31.0F, 1.0F, 9.0F, 0.0F, false);
        bone6.setTextureOffset(42, 26).addCuboid(-29.0F, -19.0F, -14.0F, 1.0F, 18.0F, 1.0F, 0.0F, false);
        bone6.setTextureOffset(38, 26).addCuboid(-29.0F, -19.0F, -8.0F, 1.0F, 18.0F, 1.0F, 0.0F, false);

        bone7 = new ModelPart(this);
        bone7.setPivot(7.0F, -14.0F, -9.0F);
        Body.addChild(bone7);
        setRotationAngle(bone7, 0.0F, 0.0F, 0.3491F);
        bone7.setTextureOffset(4, 52).addCuboid(0.0F, -8.0F, -4.0F, 1.0F, 8.0F, 1.0F, 0.0F, false);
        bone7.setTextureOffset(0, 52).addCuboid(0.0F, -8.0F, 0.0F, 1.0F, 8.0F, 1.0F, 0.0F, false);

        bone8 = new ModelPart(this);
        bone8.setPivot(-7.0F, -14.0F, -9.0F);
        Body.addChild(bone8);
        setRotationAngle(bone8, 0.0F, 0.0F, -0.3491F);
        bone8.setTextureOffset(48, 10).addCuboid(-1.0F, -8.0F, -4.0F, 1.0F, 8.0F, 1.0F, 0.0F, false);
        bone8.setTextureOffset(44, 10).addCuboid(-1.0F, -8.0F, 0.0F, 1.0F, 8.0F, 1.0F, 0.0F, false);
    }

    @Override
    public void setAngles(LargePlaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        //        bone_propeller.showModel = !entity.upgrades.containsKey(SimplePlanesUpgrades.DRAGON.getId());
        //        if (entity.isPowered()) {
        //            bone_propeller.rotateAngleZ = ((entity.ticksExisted+limbSwing) % TICKS_PER_PROPELLER_ROTATION) / (float) (TICKS_PER_PROPELLER_ROTATION / 10.0f * Math.PI);
        //        } else {
        //            bone_propeller.rotateAngleZ = 1;
        //        }
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        Body.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelPart modelRenderer, float x, float y, float z) {
        modelRenderer.pitch = x;
        modelRenderer.yaw = y;
        modelRenderer.roll = z;
    }
}
