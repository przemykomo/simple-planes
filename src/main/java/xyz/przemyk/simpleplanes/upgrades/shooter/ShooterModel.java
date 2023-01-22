//package xyz.przemyk.simpleplanes.upgrades.shooter;
//
//import com.mojang.blaze3d.vertex.PoseStack;
//import com.mojang.blaze3d.vertex.VertexConsumer;
//import net.minecraft.client.model.EntityModel;
//import net.minecraft.client.model.geom.ModelPart;
//import net.minecraft.client.model.geom.PartPose;
//import net.minecraft.client.model.geom.builders.*;
//import xyz.przemyk.simpleplanes.entities.PlaneEntity;
//
//public class ShooterModel extends EntityModel<PlaneEntity> {
//
//    private final ModelPart Shooter;
//    private final ModelPart bb_main;
//
//    public ShooterModel(ModelPart root) {
//        this.Shooter = root.getChild("Shooter");
//        this.bb_main = root.getChild("bb_main");
//    }
//
//    @SuppressWarnings("unused")
//    public static LayerDefinition createBodyLayer() {
//        MeshDefinition meshdefinition = new MeshDefinition();
//        PartDefinition partdefinition = meshdefinition.getRoot();
//
//        PartDefinition Shooter = partdefinition.addOrReplaceChild("Shooter", CubeListBuilder.create().texOffs(0, 16).addBox(4.5F, -17.5F, -15.0F, 3.0F, 3.0F, 12.0F, new CubeDeformation(0.0F))
//                .texOffs(0, 0).addBox(-7.5F, -17.5F, -15.0F, 3.0F, 3.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
//
//        PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(26, 4).addBox(0.0F, -22.0F, -5.0F, 0.0F, 6.0F, 1.0F, new CubeDeformation(0.01F))
//                .texOffs(26, 4).addBox(-0.5F, -21.0F, -13.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
//                .texOffs(26, 4).addBox(1.0F, -23.0F, -13.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.01F))
//                .texOffs(26, 4).addBox(0.0F, -22.0F, -13.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.01F))
//                .texOffs(26, 4).addBox(-1.0F, -23.0F, -13.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 24.0F, 0.0F));
//
//        PartDefinition cube_r1 = bb_main.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(26, 4).addBox(-3.5F, -6.5F, -4.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.01F))
//                .texOffs(26, 4).addBox(-5.5F, -6.5F, -4.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-5.501F, -17.499F, -9.0F, 0.0F, 0.0F, 1.5708F));
//
//        return LayerDefinition.create(meshdefinition, 32, 32);
//    }
//
//    @Override
//    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
//        Shooter.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
//        bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
//    }
//
//    @Override
//    public void setupAnim(PlaneEntity p_102618_, float p_102619_, float p_102620_, float p_102621_, float p_102622_, float p_102623_) {}
//}