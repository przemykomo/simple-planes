package xyz.przemyk.simpleplanes.upgrades.engines.liquid;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

public class LiquidEngineModel extends EntityModel<PlaneEntity> {
	private final ModelPart LiquidEngine;

	public LiquidEngineModel(ModelPart root) {
		this.LiquidEngine = root.getChild("LiquidEngine");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition LiquidEngine = partdefinition.addOrReplaceChild("LiquidEngine", CubeListBuilder.create().texOffs(22, 0).addBox(-7.5F, -11.0F, -12.9F, 15.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 30).addBox(-10.5F, -10.5F, -8.1F, 7.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(20, 50).addBox(-10.5F, -12.6213F, -11.2213F, 7.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(0, 49).addBox(-10.5F, -7.3787F, -11.2213F, 7.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(0, 5).addBox(-10.5F, -10.5F, -13.3426F, 7.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-10.5F, -10.5F, -6.3426F, 7.0F, 3.0F, 2.0F, new CubeDeformation(-0.1F))
		.texOffs(10, 54).addBox(5.5F, -12.0F, -11.9F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(7.9F, -12.0F, -11.9F, 2.0F, 2.0F, 18.0F, new CubeDeformation(0.1F))
		.texOffs(0, 0).addBox(7.9F, -8.0F, -11.9F, 2.0F, 2.0F, 18.0F, new CubeDeformation(0.1F))
		.texOffs(10, 54).addBox(5.5F, -12.0F, -8.9F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(10, 54).addBox(5.5F, -8.0F, -8.9F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(10, 54).addBox(5.5F, -12.0F, -2.9F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(10, 54).addBox(5.5F, -8.0F, -2.9F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(10, 54).addBox(5.5F, -12.0F, -5.9F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(10, 54).addBox(5.5F, -8.0F, -5.9F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(34, 36).addBox(-4.0F, -13.0F, -14.0F, 8.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition cube_r1 = LiquidEngine.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(22, 20).addBox(-7.5F, -2.0F, -2.0F, 15.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -9.0F, -11.1F, 0.0F, 0.0F, -2.3562F));

		PartDefinition cube_r2 = LiquidEngine.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(22, 20).addBox(-7.5F, -2.0F, -2.0F, 15.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -9.0F, -11.1F, 0.0F, 0.0F, -0.7854F));

		PartDefinition cube_r3 = LiquidEngine.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(22, 20).addBox(-7.0F, -2.0F, -2.0F, 15.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.5F, -10.9F, 0.0F, 0.0F, -1.5708F));

		PartDefinition cube_r4 = LiquidEngine.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(10, 54).addBox(-18.55F, -5.0F, -5.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.95F, -11.0F, -6.9F, 0.0F, 0.0F, -3.1416F));

		PartDefinition cube_r5 = LiquidEngine.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 40).addBox(-1.0F, -3.0F, 0.0F, 7.0F, 3.0F, 6.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-9.5F, -10.5F, -13.3426F, -0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r6 = LiquidEngine.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 10).addBox(-1.0F, 0.0F, 0.0F, 7.0F, 3.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-9.5F, -7.5F, -13.3426F, 0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r7 = LiquidEngine.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(0, 20).addBox(-1.0F, -3.0F, -2.0F, 7.0F, 3.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-9.5F, -10.5F, -6.1F, 0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r8 = LiquidEngine.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(0, 25).addBox(-1.0F, 0.0F, -2.0F, 7.0F, 3.0F, 2.0F, new CubeDeformation(0.101F)), PartPose.offsetAndRotation(-9.5F, -7.5F, -6.1F, -0.7854F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(PlaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		LiquidEngine.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}