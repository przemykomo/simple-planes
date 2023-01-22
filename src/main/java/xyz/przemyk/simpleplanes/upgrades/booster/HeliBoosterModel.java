//package xyz.przemyk.simpleplanes.upgrades.booster;
//
//import com.mojang.blaze3d.vertex.PoseStack;
//import com.mojang.blaze3d.vertex.VertexConsumer;
//import net.minecraft.client.model.EntityModel;
//import net.minecraft.client.model.geom.ModelPart;
//import net.minecraft.client.model.geom.PartPose;
//import net.minecraft.client.model.geom.builders.*;
//import xyz.przemyk.simpleplanes.entities.PlaneEntity;
//
//public class HeliBoosterModel extends EntityModel<PlaneEntity> {
//	private final ModelPart Booster;
//
//	public HeliBoosterModel(ModelPart root) {
//		this.Booster = root.getChild("Booster");
//	}
//
//	public static LayerDefinition createBodyLayer() {
//		MeshDefinition meshdefinition = new MeshDefinition();
//		PartDefinition partdefinition = meshdefinition.getRoot();
//
//		PartDefinition Booster = partdefinition.addOrReplaceChild("Booster", CubeListBuilder.create().texOffs(47, 50).addBox(-10.0F, 9.5F, -18.0F, 20.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
//		.texOffs(7, 6).addBox(9.0F, 9.5F, -12.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
//		.texOffs(0, 50).addBox(-10.0F, 5.5F, -18.0F, 20.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
//		.texOffs(7, 2).addBox(9.0F, 5.5F, -12.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
//		.texOffs(0, 4).addBox(-11.0F, 5.5F, -12.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
//		.texOffs(0, 0).addBox(-11.0F, 9.5F, -12.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
//		.texOffs(0, 0).addBox(-14.0F, 8.5F, -21.0F, 3.0F, 3.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 13.0F));
//
//		PartDefinition cube_r1 = Booster.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.5F, -9.5F, 3.0F, 3.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(12.5F, 10.0F, -11.5F, 0.0F, 0.0F, -1.5708F));
//
//		PartDefinition cube_r2 = Booster.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.5F, -9.5F, 3.0F, 3.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(12.5F, 6.0F, -11.5F, 0.0F, 0.0F, -3.1416F));
//
//		PartDefinition cube_r3 = Booster.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.5F, -9.5F, 3.0F, 3.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-12.5F, 6.0F, -11.5F, 0.0F, 0.0F, 1.5708F));
//
//		return LayerDefinition.create(meshdefinition, 128, 128);
//	}
//
//	@Override
//	public void setupAnim(PlaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {}
//
//	@Override
//	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
//		Booster.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
//	}
//}