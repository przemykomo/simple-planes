package xyz.przemyk.simpleplanes.upgrades.floating;
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

@SuppressWarnings("FieldCanBeLocal")
public class FloatingModel extends EntityModel<PlaneEntity> {

    private final ModelPart floating;

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition root = meshDefinition.getRoot();
        root.addOrReplaceChild("floating", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -7.0F, -17.0F, 16.0F, 2.0F, 36.0F), PartPose.offset(0, 24, 0));
        return LayerDefinition.create(meshDefinition, 128, 128);
    }

    public FloatingModel(ModelPart part) {
        floating = part.getChild("floating");
//        texWidth = 128;
//        texHeight = 128;
//
//        floating = new ModelPart(this);
//        floating.setPos(0.0F, 24.0F, 0.0F);
//        floating.texOffs(0, 0).addBox(-8.0F, -7.0F, -17.0F, 16.0F, 2.0F, 36.0F, 0.0F, false);
    }

    @Override
    public void setupAnim(PlaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
        //previously the render function, render code was moved to a method below
    }

    @Override
    public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        floating.render(matrixStack, buffer, packedLight, packedOverlay);
    }
}