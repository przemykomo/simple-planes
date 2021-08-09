package xyz.przemyk.simpleplanes.client.render.models;// Made with Blockbench 3.6.3
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

import static xyz.przemyk.simpleplanes.client.render.PlaneRenderer.getPropellerRotation;

public class PropellerModel extends EntityModel<PlaneEntity> {
    private final ModelPart Body;
    private final ModelPart bone_propeller;

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        PartDefinition body = partDefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(17, 31).addBox(-1.0F, -8.0F, -21.0F, 2.0F, 2.0F, 3.0F), PartPose.offset(0, 17, 0));
        body.addOrReplaceChild("bone_propeller", CubeListBuilder.create().texOffs(0, 0).addBox(-10, -1, -1, 20, 2, 1), PartPose.offsetAndRotation(0, -7, -21, 0, 0, 0.6109F));
        return LayerDefinition.create(meshDefinition, 32, 32);
    }

    public PropellerModel(ModelPart part) {
        Body = part.getChild("body");
        bone_propeller = Body.getChild("bone_propeller");
//        texWidth = 32;
//        texHeight = 32;
//
//        Body = new ModelPart(this);
//        Body.setPos(0.0F, 17.0F, 0.0F);
//
//        bone_propeller = new ModelPart(this);
//        bone_propeller.setPos(0.0F, -7.0F, -21.0F);
//        Body.addChild(bone_propeller);
//        setRotationAngle(bone_propeller, 0.0F, 0.0F, 0.6109F);
//        bone_propeller.texOffs(0, 0).addBox(-10.0F, -1.0F, -1.0F, 20.0F, 2.0F, 1.0F, 0.0F, false);
//        Body.texOffs(17, 31).addBox(-1.0F, -8.0F, -21.0F, 2.0F, 2.0F, 3.0F, 0.0F, false);

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
    public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        Body.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelPart modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}