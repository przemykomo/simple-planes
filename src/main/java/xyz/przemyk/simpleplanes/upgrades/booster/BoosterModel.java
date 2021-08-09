package xyz.przemyk.simpleplanes.upgrades.booster;
// Made with Blockbench 3.5.2
// Exported for Minecraft version 1.15

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

public class BoosterModel extends EntityModel<PlaneEntity> {

    private final ModelPart Body;

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition root = meshDefinition.getRoot();
        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0, 17, 0));
        body.addOrReplaceChild("booster", CubeListBuilder.create()
                .texOffs(0, 0).addBox(8.0F, -5.0F, 9.0F, 4.0F, 4.0F, 9.0F)
                .texOffs(0, 13).addBox(-12.0F, -5.0F, 9.0F, 4.0F, 4.0F, 9.0F), PartPose.ZERO);
        return LayerDefinition.create(meshDefinition, 32, 32);
    }

    public BoosterModel(ModelPart part) {
        Body = part.getChild("body");
//        texWidth = 32;
//        texHeight = 32;
//
//        Body = new ModelPart(this);
//        Body.setPos(0.0F, 17.0F, 0.0F);
//        setRotationAngle(Body, 0.0F, 0.0F, 0.0F);
//
//        ModelPart booster = new ModelPart(this);
//        booster.setPos(0.0F, 0.0F, 0.0F);
//        Body.addChild(booster);
//        booster.texOffs(0, 0).addBox(8.0F, -5.0F, 9.0F, 4.0F, 4.0F, 9.0F, 0.0F, false);
//        booster.texOffs(0, 13).addBox(-12.0F, -5.0F, 9.0F, 4.0F, 4.0F, 9.0F, 0.0F, false);
    }

    @Override
    public void setupAnim(PlaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        //previously the render function, render code was moved to a method below
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