//package xyz.przemyk.simpleplanes.upgrades.seats;
//
//import com.mojang.blaze3d.vertex.PoseStack;
//import com.mojang.blaze3d.vertex.VertexConsumer;
//import net.minecraft.client.model.EntityModel;
//import net.minecraft.client.model.geom.ModelPart;
//import net.minecraft.client.model.geom.PartPose;
//import net.minecraft.client.model.geom.builders.*;
//import xyz.przemyk.simpleplanes.entities.PlaneEntity;
//
//public class LargeSeatsModel extends EntityModel<PlaneEntity> {
//	private final ModelPart Seats;
//
//	public LargeSeatsModel(ModelPart root) {
//		this.Seats = root.getChild("Seats");
//	}
//
//	public static LayerDefinition createBodyLayer() {
//		MeshDefinition meshdefinition = new MeshDefinition();
//		PartDefinition partdefinition = meshdefinition.getRoot();
//
//		PartDefinition Seats = partdefinition.addOrReplaceChild("Seats", CubeListBuilder.create().texOffs(0, 28).addBox(-6.0F, -31.54F, 22.0F, 2.0F, 1.0F, 27.0F, new CubeDeformation(0.0F))
//		.texOffs(0, 0).addBox(4.0F, -31.54F, 22.0F, 2.0F, 1.0F, 27.0F, new CubeDeformation(0.0F))
//		.texOffs(0, 19).addBox(-6.0F, -31.54F, 49.0F, 12.0F, 2.0F, 1.0F, new CubeDeformation(0.005F))
//		.texOffs(0, 22).addBox(-4.0F, -27.84F, 58.1F, 8.0F, 2.0F, 1.0F, new CubeDeformation(0.005F))
//		.texOffs(0, 28).addBox(-5.0F, -22.1F, 33.9F, 10.0F, 17.0F, 2.0F, new CubeDeformation(0.0F))
//		.texOffs(31, 9).addBox(-3.0F, -29.1F, 46.9F, 6.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
//		.texOffs(0, 0).addBox(-5.0F, -22.1F, 46.9F, 10.0F, 17.0F, 2.0F, new CubeDeformation(0.0F))
//		.texOffs(31, 0).addBox(-3.0F, -29.1F, 33.9F, 6.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
//		.texOffs(44, 59).addBox(-5.0F, -7.1F, 35.9F, 10.0F, 2.0F, 11.0F, new CubeDeformation(0.0F))
//		.texOffs(0, 59).addBox(-5.0F, -7.1F, 21.9F, 10.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
//
//		PartDefinition cube_r1 = Seats.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(31, 18).addBox(0.0F, -10.0F, -0.5F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.0F, -18.3F, 58.6F, 0.0F, 0.0F, 0.3054F));
//
//		PartDefinition cube_r2 = Seats.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(37, 18).addBox(-2.0F, -10.0F, -0.5F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.0F, -18.3F, 58.6F, 0.0F, 0.0F, -0.3054F));
//
//		PartDefinition cube_r3 = Seats.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(31, 30).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.4951F, -31.5484F, 49.954F, 1.9701F, 0.2266F, 3.1403F));
//
//		PartDefinition cube_r4 = Seats.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(37, 30).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.4951F, -31.5484F, 49.954F, 1.9701F, -0.2266F, -3.1403F));
//
//		PartDefinition cube_r5 = Seats.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(31, 42).addBox(0.0F, -10.0F, -0.5F, 2.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
//		.texOffs(31, 30).addBox(0.0F, -10.0F, -27.5F, 1.0F, 2.0F, 27.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-9.0F, -22.0F, 49.5F, 0.0F, 0.0F, 0.3054F));
//
//		PartDefinition cube_r6 = Seats.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(37, 42).addBox(-2.0F, -10.0F, -0.5F, 2.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
//		.texOffs(31, 1).addBox(-1.0F, -10.0F, -27.5F, 1.0F, 2.0F, 27.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(9.0F, -22.0F, 49.5F, 0.0F, 0.0F, -0.3054F));
//
//		return LayerDefinition.create(meshdefinition, 128, 128);
//	}
//
//	@Override
//	public void setupAnim(PlaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {}
//
//	@Override
//	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
//		Seats.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
//	}
//}