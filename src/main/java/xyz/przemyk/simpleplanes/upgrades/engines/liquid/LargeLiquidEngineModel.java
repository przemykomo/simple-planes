//package xyz.przemyk.simpleplanes.upgrades.engines.liquid;
//
//import com.mojang.blaze3d.vertex.PoseStack;
//import com.mojang.blaze3d.vertex.VertexConsumer;
//import net.minecraft.client.model.EntityModel;
//import net.minecraft.client.model.geom.ModelPart;
//import net.minecraft.client.model.geom.PartPose;
//import net.minecraft.client.model.geom.builders.*;
//import xyz.przemyk.simpleplanes.entities.PlaneEntity;
//
//public class LargeLiquidEngineModel extends EntityModel<PlaneEntity> {
//	private final ModelPart LiquidEngine;
//
//	public LargeLiquidEngineModel(ModelPart root) {
//		this.LiquidEngine = root.getChild("LiquidEngine");
//	}
//
//	public static LayerDefinition createBodyLayer() {
//		MeshDefinition meshdefinition = new MeshDefinition();
//		PartDefinition partdefinition = meshdefinition.getRoot();
//
//		PartDefinition LiquidEngine = partdefinition.addOrReplaceChild("LiquidEngine", CubeListBuilder.create().texOffs(49, 78).addBox(-1.0F, 20.9853F, 121.9F, 4.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
//				.texOffs(49, 78).addBox(-55.0F, 20.9853F, 121.9F, 4.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
//				.texOffs(0, 39).addBox(-3.0F, 20.9853F, 92.0F, 8.0F, 9.0F, 30.0F, new CubeDeformation(0.001F))
//				.texOffs(25, 81).addBox(-3.0F, 28.9853F, 93.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.25F))
//				.texOffs(0, 0).addBox(-3.0F, 20.9853F, 85.0F, 8.0F, 4.0F, 7.0F, new CubeDeformation(0.001F))
//				.texOffs(0, 0).addBox(-57.0F, 20.9853F, 92.0F, 8.0F, 9.0F, 30.0F, new CubeDeformation(0.001F))
//				.texOffs(0, 0).addBox(-57.0F, 20.9853F, 85.0F, 8.0F, 4.0F, 7.0F, new CubeDeformation(0.001F))
//				.texOffs(77, 51).addBox(-57.0F, 28.9853F, 93.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.25F)), PartPose.offset(26.0F, -28.0F, -73.5F));
//
//		PartDefinition octagon_r1 = LiquidEngine.addOrReplaceChild("octagon_r1", CubeListBuilder.create().texOffs(11, 81).mirror().addBox(0.0F, -5.0147F, -2.5F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-4.2F, 29.0F, 117.0F, 0.0F, -0.5236F, 0.0F));
//
//		PartDefinition octagon_r2 = LiquidEngine.addOrReplaceChild("octagon_r2", CubeListBuilder.create().texOffs(11, 81).mirror().addBox(0.0F, -5.0147F, -2.5F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-4.2F, 29.0F, 113.0F, 0.0F, -0.5236F, 0.0F));
//
//		PartDefinition octagon_r3 = LiquidEngine.addOrReplaceChild("octagon_r3", CubeListBuilder.create().texOffs(11, 81).mirror().addBox(0.0F, -5.0147F, -2.5F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-4.2F, 29.0F, 109.0F, 0.0F, -0.5236F, 0.0F));
//
//		PartDefinition octagon_r4 = LiquidEngine.addOrReplaceChild("octagon_r4", CubeListBuilder.create().texOffs(11, 81).mirror().addBox(0.0F, -5.0147F, -2.5F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-4.2F, 29.0F, 105.0F, 0.0F, -0.5236F, 0.0F));
//
//		PartDefinition octagon_r5 = LiquidEngine.addOrReplaceChild("octagon_r5", CubeListBuilder.create().texOffs(0, 78).addBox(-2.0F, -5.0147F, -2.5F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-47.8F, 29.0F, 113.0F, 0.0F, 0.5236F, 0.0F));
//
//		PartDefinition octagon_r6 = LiquidEngine.addOrReplaceChild("octagon_r6", CubeListBuilder.create().texOffs(0, 78).addBox(-2.0F, -5.0147F, -2.5F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-47.8F, 29.0F, 109.0F, 0.0F, 0.5236F, 0.0F));
//
//		PartDefinition octagon_r7 = LiquidEngine.addOrReplaceChild("octagon_r7", CubeListBuilder.create().texOffs(0, 78).addBox(-2.0F, -5.0147F, -2.5F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-47.8F, 29.0F, 117.0F, 0.0F, 0.5236F, 0.0F));
//
//		PartDefinition octagon_r8 = LiquidEngine.addOrReplaceChild("octagon_r8", CubeListBuilder.create().texOffs(0, 78).addBox(-2.0F, -5.0147F, -2.5F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-47.8F, 29.0F, 105.0F, 0.0F, 0.5236F, 0.0F));
//
//		PartDefinition octagon_r9 = LiquidEngine.addOrReplaceChild("octagon_r9", CubeListBuilder.create().texOffs(77, 27).addBox(-0.5F, -0.5F, -11.5F, 1.0F, 1.0F, 23.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(-53.0F, 20.2853F, 110.5F, 0.0F, 0.0F, -0.7854F));
//
//		PartDefinition octagon_r10 = LiquidEngine.addOrReplaceChild("octagon_r10", CubeListBuilder.create().texOffs(46, 0).addBox(-3.99F, -0.86F, -11.5F, 4.0F, 2.0F, 23.0F, new CubeDeformation(0.0002F)), PartPose.offsetAndRotation(-49.27F, 21.8053F, 110.5F, 0.0F, 0.0F, 0.2967F));
//
//		PartDefinition octagon_r11 = LiquidEngine.addOrReplaceChild("octagon_r11", CubeListBuilder.create().texOffs(46, 39).addBox(-0.01F, -0.86F, -11.5F, 4.0F, 2.0F, 23.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-56.73F, 21.8053F, 110.5F, 0.0F, 0.0F, -0.2967F));
//
//		PartDefinition octagon_r12 = LiquidEngine.addOrReplaceChild("octagon_r12", CubeListBuilder.create().texOffs(0, 18).addBox(3.0F, -1.0147F, 0.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.001F))
//				.texOffs(12, 18).addBox(3.0F, -1.0147F, -3.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.001F))
//				.texOffs(0, 39).addBox(3.0F, -1.0147F, -7.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.001F))
//				.texOffs(12, 39).addBox(3.0F, -1.0147F, -11.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-54.3F, 29.0F, 114.5F, 0.0F, 0.0F, -0.3927F));
//
//		PartDefinition octagon_r13 = LiquidEngine.addOrReplaceChild("octagon_r13", CubeListBuilder.create().texOffs(46, 0).addBox(-6.0F, -1.0147F, 0.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.001F))
//				.texOffs(46, 9).addBox(-6.0F, -1.0147F, 4.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.001F))
//				.texOffs(46, 39).addBox(-6.0F, -1.0147F, 8.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.001F))
//				.texOffs(0, 48).addBox(-6.0F, -1.0147F, -3.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-51.7F, 29.0F, 106.5F, 0.0F, 0.0F, 0.3927F));
//
//		PartDefinition octagon_r14 = LiquidEngine.addOrReplaceChild("octagon_r14", CubeListBuilder.create().texOffs(0, 78).addBox(-0.5F, -0.5F, -11.5F, 1.0F, 1.0F, 23.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(1.0F, 20.2853F, 110.5F, 0.0F, 0.0F, 0.7854F));
//
//		PartDefinition octagon_r15 = LiquidEngine.addOrReplaceChild("octagon_r15", CubeListBuilder.create().texOffs(53, 64).addBox(-0.01F, -0.86F, -11.5F, 4.0F, 2.0F, 23.0F, new CubeDeformation(0.0002F)), PartPose.offsetAndRotation(-2.73F, 21.8053F, 110.5F, 0.0F, 0.0F, -0.2967F));
//
//		PartDefinition octagon_r16 = LiquidEngine.addOrReplaceChild("octagon_r16", CubeListBuilder.create().texOffs(77, 2).addBox(-3.99F, -0.86F, -11.5F, 4.0F, 2.0F, 23.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.73F, 21.8053F, 110.5F, 0.0F, 0.0F, 0.2967F));
//
//		PartDefinition octagon_r17 = LiquidEngine.addOrReplaceChild("octagon_r17", CubeListBuilder.create().texOffs(12, 48).addBox(-6.0F, -1.0147F, 0.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.001F))
//				.texOffs(46, 48).addBox(-6.0F, -1.0147F, -3.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.001F))
//				.texOffs(0, 57).addBox(-6.0F, -1.0147F, -7.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.001F))
//				.texOffs(12, 57).addBox(-6.0F, -1.0147F, -11.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(2.3F, 29.0F, 114.5F, 0.0F, 0.0F, 0.3927F));
//
//		PartDefinition octagon_r18 = LiquidEngine.addOrReplaceChild("octagon_r18", CubeListBuilder.create().texOffs(76, 27).addBox(3.0F, -1.0147F, 0.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.001F))
//				.texOffs(77, 0).addBox(3.0F, -1.0147F, 4.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.001F))
//				.texOffs(77, 9).addBox(3.0F, -1.0147F, 8.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.001F))
//				.texOffs(77, 36).addBox(3.0F, -1.0147F, -3.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-0.3F, 29.0F, 106.5F, 0.0F, 0.0F, -0.3927F));
//
//		PartDefinition octagon_r19 = LiquidEngine.addOrReplaceChild("octagon_r19", CubeListBuilder.create().texOffs(95, 62).addBox(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(-0.17F)), PartPose.offsetAndRotation(-53.0044F, 27.1603F, 91.999F, 0.0F, 0.7854F, 3.1416F));
//
//		PartDefinition octagon_r20 = LiquidEngine.addOrReplaceChild("octagon_r20", CubeListBuilder.create().texOffs(52, 90).addBox(-3.0F, -0.5F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(-0.17F)), PartPose.offsetAndRotation(0.9956F, 24.6603F, 91.999F, 0.0F, 0.7854F, 0.0F));
//
//		return LayerDefinition.create(meshdefinition, 256, 256);
//	}
//
//	@Override
//	public void setupAnim(PlaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {}
//
//	@Override
//	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
//		LiquidEngine.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
//	}
//}