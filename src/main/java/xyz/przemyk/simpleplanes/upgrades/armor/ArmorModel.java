//package xyz.przemyk.simpleplanes.upgrades.armor;
//
//import com.mojang.blaze3d.vertex.PoseStack;
//import com.mojang.blaze3d.vertex.VertexConsumer;
//import net.minecraft.client.model.EntityModel;
//import net.minecraft.client.model.geom.ModelPart;
//import net.minecraft.client.model.geom.PartPose;
//import net.minecraft.client.model.geom.builders.*;
//import xyz.przemyk.simpleplanes.entities.PlaneEntity;
//
//public class ArmorModel extends EntityModel<PlaneEntity> {
//	private final ModelPart bb_main;
//
//	public ArmorModel(ModelPart root) {
//		this.bb_main = root.getChild("bb_main");
//	}
//
//	public static LayerDefinition createBodyLayer() {
//		MeshDefinition meshdefinition = new MeshDefinition();
//		PartDefinition partdefinition = meshdefinition.getRoot();
//
//		PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(27, 11).addBox(8.7F, -15.0F, -3.5F, 1.0F, 10.0F, 24.0F, new CubeDeformation(0.0F))
//				.texOffs(21, 46).addBox(8.7F, -2.0F, -0.5F, 1.0F, 3.0F, 18.0F, new CubeDeformation(0.0F))
//				.texOffs(27, 0).addBox(8.7F, -13.0F, -10.5F, 1.0F, 8.0F, 7.0F, new CubeDeformation(0.0F))
//				.texOffs(21, 43).addBox(8.7F, -5.0F, -3.5F, 1.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
//				.texOffs(41, 13).addBox(8.7F, -5.0F, 17.5F, 1.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
//				.texOffs(9, 35).addBox(-9.7F, -5.0F, 17.5F, 1.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
//				.texOffs(0, 35).addBox(-9.7F, -2.0F, -0.5F, 1.0F, 3.0F, 18.0F, new CubeDeformation(0.0F))
//				.texOffs(0, 35).addBox(-9.7F, -5.0F, -3.5F, 1.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
//				.texOffs(0, 0).addBox(-9.7F, -13.0F, -10.5F, 1.0F, 8.0F, 7.0F, new CubeDeformation(0.0F))
//				.texOffs(0, 0).addBox(-9.7F, -15.0F, -3.5F, 1.0F, 10.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
//
//		PartDefinition cube_r1 = bb_main.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(17, 0).addBox(-1.2F, -4.4F, 4.0F, 1.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
//			.texOffs(44, 0).addBox(-11.2F, -4.4F, 4.0F, 1.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.65F, -20.0F, -10.5F, -0.2618F, 0.0F, 0.0F));
//
//		PartDefinition cube_r2 = bb_main.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(48, 56).addBox(-0.9F, -5.0F, 12.0F, 1.0F, 12.0F, 12.0F, new CubeDeformation(0.0F))
//				.texOffs(54, 0).addBox(-0.9F, -9.0F, 1.0F, 1.0F, 16.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.7F, -6.0F, 21.5F, 0.0F, 0.1309F, 0.0F));
//
//		PartDefinition cube_r3 = bb_main.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(66, 34).addBox(-0.1F, -5.0F, 12.0F, 1.0F, 12.0F, 12.0F, new CubeDeformation(0.0F))
//				.texOffs(0, 57).addBox(-0.1F, -9.0F, 1.0F, 1.0F, 16.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.7F, -6.0F, 21.5F, 0.0F, -0.1309F, 0.0F));
//
//		return LayerDefinition.create(meshdefinition, 128, 128);
//	}
//
//	@Override
//	public void setupAnim(PlaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {}
//
//	@Override
//	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
//		bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
//	}
//}