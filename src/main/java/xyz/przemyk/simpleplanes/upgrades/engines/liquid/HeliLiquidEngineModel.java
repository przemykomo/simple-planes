package xyz.przemyk.simpleplanes.upgrades.engines.liquid;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

public class HeliLiquidEngineModel extends EntityModel<PlaneEntity> {
	private final ModelPart LiquidEngine;

	public HeliLiquidEngineModel(ModelPart root) {
		this.LiquidEngine = root.getChild("LiquidEngine");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition LiquidEngine = partdefinition.addOrReplaceChild("LiquidEngine", CubeListBuilder.create(), PartPose.offset(0.0F, -17.0F, -47.5F));

		PartDefinition cube_r1 = LiquidEngine.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 16).addBox(1.3159F, 1.5F, -14.5143F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.4841F, 14.0F, 61.9743F, 0.0F, 0.0F, -1.5708F));

		PartDefinition cube_r2 = LiquidEngine.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(10, 16).addBox(1.3159F, 1.5F, -14.5143F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.4841F, 14.0F, 61.9743F, 0.0F, 0.0F, -1.5708F));

		PartDefinition cube_r3 = LiquidEngine.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 0).addBox(1.3159F, -3.5F, -14.5143F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.4841F, 16.0F, 61.9743F, 0.0F, 0.0F, -1.5708F));

		PartDefinition cube_r4 = LiquidEngine.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(6, 7).addBox(1.3159F, -3.5F, -14.5143F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-3.6841F, -3.5F, -13.1743F, 7.0F, 7.0F, 20.0F, new CubeDeformation(-0.18F)), PartPose.offsetAndRotation(6.4841F, 11.0F, 61.9743F, 0.0F, 0.0F, -1.5708F));

		PartDefinition cube_r5 = LiquidEngine.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 54).addBox(-0.1841F, 2.0F, -13.5143F, 2.0F, 3.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.4841F, 8.0F, 63.6143F, 0.0F, 0.0F, -1.5708F));

		PartDefinition cube_r6 = LiquidEngine.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(20, 62).addBox(-0.1841F, 1.0F, -13.5143F, 2.0F, 3.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.4841F, 8.0F, 63.6143F, 0.0F, 0.0F, -1.5708F));

		PartDefinition cube_r7 = LiquidEngine.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(8, 27).addBox(-4.3159F, 1.5F, -14.5143F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.4841F, 14.0F, 61.9743F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r8 = LiquidEngine.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(8, 31).addBox(-4.3159F, 1.5F, -14.5143F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.4841F, 14.0F, 61.9743F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r9 = LiquidEngine.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(12, 0).addBox(-3.3159F, -3.5F, -14.5143F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.4841F, 16.0F, 61.9743F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r10 = LiquidEngine.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(0, 27).addBox(-3.3159F, -3.5F, -14.5143F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 27).addBox(-3.3159F, -3.5F, -13.1743F, 7.0F, 7.0F, 20.0F, new CubeDeformation(-0.18F)), PartPose.offsetAndRotation(-6.4841F, 11.0F, 61.9743F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r11 = LiquidEngine.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(56, 62).addBox(-1.8159F, 2.0F, -13.5143F, 2.0F, 3.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.4841F, 8.0F, 63.6143F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r12 = LiquidEngine.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(70, 0).addBox(-1.8159F, 1.0F, -13.5143F, 2.0F, 3.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.4841F, 8.0F, 63.6143F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r13 = LiquidEngine.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(38, 11).addBox(-1.8159F, -4.0F, -13.5143F, 8.0F, 8.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.4841F, 10.0F, 63.6143F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r14 = LiquidEngine.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(40, 62).addBox(-0.6477F, -3.5F, 1.3655F, 7.0F, 7.0F, 7.0F, new CubeDeformation(-0.177F)), PartPose.offsetAndRotation(-6.6721F, 11.1851F, 63.3106F, 0.0F, -0.7418F, 0.0F));

		PartDefinition cube_r15 = LiquidEngine.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(38, 38).addBox(-6.1841F, -4.0F, -13.5143F, 8.0F, 8.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.4841F, 10.0F, 63.6143F, 0.0F, 0.0F, -1.5708F));

		PartDefinition cube_r16 = LiquidEngine.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(70, 35).addBox(-6.3523F, -3.5F, 1.3655F, 7.0F, 7.0F, 7.0F, new CubeDeformation(-0.177F)), PartPose.offsetAndRotation(6.6721F, 11.1851F, 63.3106F, 0.0F, 0.7418F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(PlaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		LiquidEngine.render(poseStack, vertexConsumer, packedLight, packedOverlay);
	}
}