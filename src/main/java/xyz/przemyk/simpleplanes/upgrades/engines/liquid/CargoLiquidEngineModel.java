package xyz.przemyk.simpleplanes.upgrades.engines.liquid;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

public class CargoLiquidEngineModel extends EntityModel<PlaneEntity> {

	private final ModelPart LiquidEngine;

	public CargoLiquidEngineModel(ModelPart root) {
		this.LiquidEngine = root.getChild("LiquidEngine");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition LiquidEngine = partdefinition.addOrReplaceChild("LiquidEngine", CubeListBuilder.create().texOffs(9, 2).addBox(-91.75F, -31.9528F, 48.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(14, 35).addBox(-92.75F, -31.9528F, 48.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(7, 35).addBox(-88.75F, -31.9528F, 48.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(98, 30).addBox(-92.75F, -33.9528F, 50.0F, 5.0F, 20.0F, 18.0F, new CubeDeformation(0.0F))
		.texOffs(156, 0).addBox(-93.25F, -22.9528F, 69.5F, 6.0F, 6.0F, 10.0F, new CubeDeformation(-0.5F))
		.texOffs(173, 138).addBox(-93.25F, -28.9528F, 69.5F, 6.0F, 6.0F, 8.0F, new CubeDeformation(-0.5F))
		.texOffs(47, 48).addBox(-96.25F, -28.9528F, 48.0F, 12.0F, 12.0F, 22.0F, new CubeDeformation(0.0F))
		.texOffs(145, 51).addBox(-93.25F, -25.9528F, 39.0F, 6.0F, 6.0F, 11.0F, new CubeDeformation(0.0F))
		.texOffs(142, 128).mirror().addBox(-49.0F, -25.9472F, 37.4F, 6.0F, 6.0F, 11.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 35).mirror().addBox(-52.0F, -28.9472F, 46.4F, 12.0F, 12.0F, 22.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(154, 93).mirror().addBox(-49.0F, -22.9472F, 67.9F, 6.0F, 6.0F, 10.0F, new CubeDeformation(-0.5F)).mirror(false)
		.texOffs(170, 43).mirror().addBox(-49.0F, -28.9472F, 67.9F, 6.0F, 6.0F, 8.0F, new CubeDeformation(-0.5F)).mirror(false)
		.texOffs(94, 83).mirror().addBox(-48.5F, -33.9472F, 48.4F, 5.0F, 20.0F, 18.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 35).mirror().addBox(-48.5F, -31.9472F, 46.4F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(5, 16).mirror().addBox(-44.5F, -31.9472F, 46.4F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 8).mirror().addBox(-47.5F, -31.9472F, 46.4F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(9, 2).mirror().addBox(88.75F, -31.9528F, 48.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(14, 35).mirror().addBox(91.75F, -31.9528F, 48.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(7, 35).mirror().addBox(87.75F, -31.9528F, 48.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(98, 30).mirror().addBox(87.75F, -33.9528F, 50.0F, 5.0F, 20.0F, 18.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(156, 0).mirror().addBox(87.25F, -22.9528F, 69.5F, 6.0F, 6.0F, 10.0F, new CubeDeformation(-0.5F)).mirror(false)
		.texOffs(173, 138).mirror().addBox(87.25F, -28.9528F, 69.5F, 6.0F, 6.0F, 8.0F, new CubeDeformation(-0.5F)).mirror(false)
		.texOffs(47, 48).mirror().addBox(84.25F, -28.9528F, 48.0F, 12.0F, 12.0F, 22.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(145, 51).mirror().addBox(87.25F, -25.9528F, 39.0F, 6.0F, 6.0F, 11.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(142, 128).addBox(43.0F, -25.9472F, 37.4F, 6.0F, 6.0F, 11.0F, new CubeDeformation(0.0F))
		.texOffs(0, 35).addBox(40.0F, -28.9472F, 46.4F, 12.0F, 12.0F, 22.0F, new CubeDeformation(0.0F))
		.texOffs(154, 93).addBox(43.0F, -22.9472F, 67.9F, 6.0F, 6.0F, 10.0F, new CubeDeformation(-0.5F))
		.texOffs(170, 43).addBox(43.0F, -28.9472F, 67.9F, 6.0F, 6.0F, 8.0F, new CubeDeformation(-0.5F))
		.texOffs(94, 83).addBox(43.5F, -33.9472F, 48.4F, 5.0F, 20.0F, 18.0F, new CubeDeformation(0.0F))
		.texOffs(0, 35).addBox(47.5F, -31.9472F, 46.4F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(5, 16).addBox(43.5F, -31.9472F, 46.4F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 8).addBox(44.5F, -31.9472F, 46.4F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition cube_r1 = LiquidEngine.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(69, 146).addBox(57.55F, -6.0F, -3.0F, 8.0F, 12.0F, 8.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(1.75F, -23.1528F, 89.2F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r2 = LiquidEngine.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(102, 152).mirror().addBox(-3.0F, -6.0F, -3.0F, 8.0F, 12.0F, 8.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offsetAndRotation(90.25F, -23.1528F, 46.6F, 0.0F, -0.7854F, 0.0F));

		PartDefinition cube_r3 = LiquidEngine.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(123, 69).addBox(-4.0F, -5.0F, -9.0F, 6.0F, 5.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(43.05F, -17.9528F, 57.4F, 0.0F, 0.0F, -0.9599F));

		PartDefinition cube_r4 = LiquidEngine.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(123, 104).addBox(-2.0F, -5.0F, -9.0F, 6.0F, 5.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(48.95F, -17.9528F, 57.4F, 0.0F, 0.0F, 0.9599F));

		PartDefinition cube_r5 = LiquidEngine.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(125, 6).mirror().addBox(-4.0F, -5.0F, -9.0F, 6.0F, 5.0F, 18.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(87.35F, -17.9528F, 59.0F, 0.0F, 0.0F, -0.9599F));

		PartDefinition cube_r6 = LiquidEngine.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(111, 128).mirror().addBox(-2.0F, -5.0F, -9.0F, 6.0F, 5.0F, 18.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(93.25F, -17.9528F, 59.0F, 0.0F, 0.0F, 0.9599F));

		PartDefinition cube_r7 = LiquidEngine.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(86, 167).addBox(-6.25F, -12.8159F, 1.6798F, 3.0F, 9.0F, 9.0F, new CubeDeformation(-0.26F))
		.texOffs(111, 173).addBox(3.25F, -12.8159F, 1.6798F, 3.0F, 9.0F, 9.0F, new CubeDeformation(-0.26F))
		.texOffs(86, 167).mirror().addBox(-88.75F, -12.8159F, 1.6798F, 3.0F, 9.0F, 9.0F, new CubeDeformation(-0.26F)).mirror(false)
		.texOffs(111, 173).mirror().addBox(-98.25F, -12.8159F, 1.6798F, 3.0F, 9.0F, 9.0F, new CubeDeformation(-0.26F)).mirror(false), PartPose.offsetAndRotation(46.0F, -21.4472F, 58.15F, -0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r8 = LiquidEngine.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(18, 133).addBox(-1.0F, -25.0F, -1.0F, 2.0F, 50.0F, 2.0F, new CubeDeformation(-0.03F)), PartPose.offsetAndRotation(-46.9411F, -21.3106F, 68.0F, 0.0F, 0.0F, -2.0857F));

		PartDefinition cube_r9 = LiquidEngine.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(27, 146).addBox(-1.0F, -50.0F, -18.0F, 2.0F, 50.0F, 2.0F, new CubeDeformation(-0.03F)), PartPose.offsetAndRotation(-68.7F, -9.0F, 68.0F, 0.0F, 0.0F, 1.0559F));

		PartDefinition cube_r10 = LiquidEngine.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(177, 60).mirror().addBox(-6.0F, -5.0F, -4.0F, 3.0F, 9.0F, 9.0F, new CubeDeformation(-0.26F)).mirror(false)
		.texOffs(178, 79).mirror().addBox(3.5F, -5.0F, -4.0F, 3.0F, 9.0F, 9.0F, new CubeDeformation(-0.26F)).mirror(false), PartPose.offsetAndRotation(90.0F, -22.2353F, 70.0F, 0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r11 = LiquidEngine.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(69, 146).mirror().addBox(-65.55F, -6.0F, -3.0F, 8.0F, 12.0F, 8.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offsetAndRotation(-1.75F, -23.1528F, 89.2F, 0.0F, -0.7854F, 0.0F));

		PartDefinition cube_r12 = LiquidEngine.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(102, 152).addBox(-5.0F, -6.0F, -3.0F, 8.0F, 12.0F, 8.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(-90.25F, -23.1528F, 46.6F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r13 = LiquidEngine.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(123, 69).mirror().addBox(-2.0F, -5.0F, -10.6F, 6.0F, 5.0F, 18.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-43.05F, -17.9528F, 59.0F, 0.0F, 0.0F, 0.9599F));

		PartDefinition cube_r14 = LiquidEngine.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(123, 104).mirror().addBox(-4.0F, -5.0F, -10.6F, 6.0F, 5.0F, 18.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-48.95F, -17.9528F, 59.0F, 0.0F, 0.0F, -0.9599F));

		PartDefinition cube_r15 = LiquidEngine.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(125, 6).addBox(-2.0F, -5.0F, -9.0F, 6.0F, 5.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-87.35F, -17.9528F, 59.0F, 0.0F, 0.0F, 0.9599F));

		PartDefinition cube_r16 = LiquidEngine.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(111, 128).addBox(-4.0F, -5.0F, -9.0F, 6.0F, 5.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-93.25F, -17.9528F, 59.0F, 0.0F, 0.0F, -0.9599F));

		PartDefinition cube_r17 = LiquidEngine.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(18, 133).mirror().addBox(-1.0F, -25.0F, -1.0F, 2.0F, 50.0F, 2.0F, new CubeDeformation(-0.03F)).mirror(false), PartPose.offsetAndRotation(46.9411F, -21.3106F, 51.0F, 0.0F, 0.0F, 2.0857F));

		PartDefinition cube_r18 = LiquidEngine.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(27, 146).mirror().addBox(-1.0F, -50.0F, -1.0F, 2.0F, 50.0F, 2.0F, new CubeDeformation(-0.03F)).mirror(false), PartPose.offsetAndRotation(68.7F, -9.0F, 68.0F, 0.0F, 0.0F, -1.0559F));

		PartDefinition cube_r19 = LiquidEngine.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(177, 60).addBox(3.0F, -5.0F, -4.0F, 3.0F, 9.0F, 9.0F, new CubeDeformation(-0.26F))
		.texOffs(178, 79).addBox(-6.5F, -5.0F, -4.0F, 3.0F, 9.0F, 9.0F, new CubeDeformation(-0.26F)), PartPose.offsetAndRotation(-90.0F, -22.2353F, 70.0F, 0.7854F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(PlaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		LiquidEngine.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}