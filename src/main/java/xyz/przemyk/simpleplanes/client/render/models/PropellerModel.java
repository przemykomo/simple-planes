package xyz.przemyk.simpleplanes.client.render.models;// Made with Blockbench 3.6.3
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

import static xyz.przemyk.simpleplanes.client.render.PlaneRenderer.getPropellerRotation;

public class PropellerModel extends EntityModel<PlaneEntity> {
    private final ModelRenderer Body;
    private final ModelRenderer bone_propeller;

    public PropellerModel() {
        texWidth = 32;
        texHeight = 32;

        Body = new ModelRenderer(this);
        Body.setPos(0.0F, 17.0F, 0.0F);

        bone_propeller = new ModelRenderer(this);
        bone_propeller.setPos(0.0F, -7.0F, -21.0F);
        Body.addChild(bone_propeller);
        setRotationAngle(bone_propeller, 0.0F, 0.0F, 0.6109F);
        bone_propeller.texOffs(0, 0).addBox(-10.0F, -1.0F, -1.0F, 20.0F, 2.0F, 1.0F, 0.0F, false);
        Body.texOffs(17, 31).addBox(-1.0F, -8.0F, -21.0F, 2.0F, 2.0F, 3.0F, 0.0F, false);

    }

    @Override
    public void setupAnim(PlaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

        if (entity.isPowered() && !entity.getParked()) {
            bone_propeller.zRot = getPropellerRotation(entity, limbSwing);
        } else {
            bone_propeller.zRot = 1;
        }
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        Body.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}