package xyz.przemyk.simpleplanes.client.render.models;// Made with Blockbench 3.6.3
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

import static xyz.przemyk.simpleplanes.client.render.PlaneRenderer.getPropellerRotation;

public class HelicopterPropellerModel extends EntityModel<PlaneEntity> {
    private final ModelRenderer p;
    private final ModelRenderer bone_propeller;
    private final ModelRenderer bone_propeller2;

    public HelicopterPropellerModel() {
        texWidth = 16;
        texHeight = 16;

        p = new ModelRenderer(this);
        p.setPos(0.0F, -16.0F, 12.0F);
        p.texOffs(0, 0).addBox(2.0F, 6.0F, 42.0F, 3.0F, 2.0F, 2.0F, 0.0F, false);
        p.texOffs(0, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, 0.0F, false);

        bone_propeller = new ModelRenderer(this);
        bone_propeller.setPos(0.0F, 0.0F, 0.0F);
        p.addChild(bone_propeller);
        setRotationAngle(bone_propeller, 0.0F, 3.1416F, 0.0F);
        bone_propeller.texOffs(0, 0).addBox(-31.0F, -1.0F, -3.0F, 62.0F, 1.0F, 6.0F, 0.0F, false);

        bone_propeller2 = new ModelRenderer(this);
        bone_propeller2.setPos(5.5F, 7.0F, 43.0F);
        p.addChild(bone_propeller2);
        setRotationAngle(bone_propeller2, 0.0F, -0.0436F, 0.0F);
        bone_propeller2.texOffs(0, 0).addBox(-0.5F, -7.0F, -1.0F, 1.0F, 14.0F, 2.0F, 0.0F, false);

    }

    @Override
    public void setupAnim(PlaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

        if (entity.isPowered() && !entity.getParked()) {
            bone_propeller.yRot =
                getPropellerRotation(entity, limbSwing);
            bone_propeller2.xRot =
                getPropellerRotation(entity, limbSwing);
        } else {
            bone_propeller.yRot = 0;
            bone_propeller2.xRot = 0;
        }
    }


    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        p.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }

}