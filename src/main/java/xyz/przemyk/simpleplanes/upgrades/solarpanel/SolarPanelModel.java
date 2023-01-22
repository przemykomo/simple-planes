//package xyz.przemyk.simpleplanes.upgrades.solarpanel;
//
//import com.mojang.blaze3d.vertex.PoseStack;
//import com.mojang.blaze3d.vertex.VertexConsumer;
//import net.minecraft.client.model.EntityModel;
//import net.minecraft.client.model.geom.ModelPart;
//import net.minecraft.client.model.geom.PartPose;
//import net.minecraft.client.model.geom.builders.*;
//import xyz.przemyk.simpleplanes.entities.PlaneEntity;
//
//public class SolarPanelModel extends EntityModel<PlaneEntity> {
//    private final ModelPart bb_main;
//
//    public SolarPanelModel(ModelPart root) {
//        this.bb_main = root.getChild("bb_main");
//    }
//
//    public static LayerDefinition createBodyLayer() {
//        MeshDefinition meshdefinition = new MeshDefinition();
//        PartDefinition partdefinition = meshdefinition.getRoot();
//
//        PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(0, 14).addBox(15.0F, -22.0F, -2.1F, 37.0F, 1.0F, 13.0F, new CubeDeformation(0.0F))
//                .texOffs(0, 0).addBox(-52.0F, -22.0F, -2.1F, 37.0F, 1.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
//
//        return LayerDefinition.create(meshdefinition, 128, 128);
//    }
//
//    @Override
//    public void setupAnim(PlaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {}
//
//    @Override
//    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
//        bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
//    }
//}
