package xyz.przemyk.simpleplanes.client.render.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

import static xyz.przemyk.simpleplanes.client.render.PlaneRenderer.getPropellerRotation;

public class CargoPropellerModel extends EntityModel<PlaneEntity> {

	private final ModelPart IronPropeller2;
	private final ModelPart IronPropeller3;
	private final ModelPart IronPropeller4;
	private final ModelPart IronPropeller5;

	public CargoPropellerModel(ModelPart root) {
		this.IronPropeller2 = root.getChild("IronPropeller2");
		this.IronPropeller3 = root.getChild("IronPropeller3");
		this.IronPropeller4 = root.getChild("IronPropeller4");
		this.IronPropeller5 = root.getChild("IronPropeller5");
	}

	@SuppressWarnings("unused")
    public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition IronPropeller2 = partdefinition.addOrReplaceChild("IronPropeller2", CubeListBuilder.create(), PartPose.offset(90.35F, 0.9472F, 30.1583F));

		PartDefinition cube_r1 = IronPropeller2.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(6, 0).addBox(-1.0F, -5.0F, -1.9964F, 2.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-2.0F, -2.0F, -2.9964F, 4.0F, 4.0F, 12.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.2464F, 0.0F, 0.0F, 0.0F));

		PartDefinition cube_r2 = IronPropeller2.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -5.0F, -1.9964F, 2.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.2464F, 0.0F, 0.0F, -1.5708F));

		PartDefinition cube_r3 = IronPropeller2.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(20, 16).addBox(-3.0F, -22.0F, 0.9964F, 4.0F, 17.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 1.2536F, 0.0F, 3.1416F, -1.5708F));

		PartDefinition cube_r4 = IronPropeller2.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(20, 0).addBox(-2.0F, 0.6238F, -3.6238F, 4.0F, 3.0F, 3.0F, new CubeDeformation(-0.11F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.2464F, -0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r5 = IronPropeller2.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(32, 6).addBox(0.2072F, -5.8273F, -1.9964F, 2.0F, 4.0F, 1.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.2464F, 0.0F, 0.0F, -2.1468F));

		PartDefinition cube_r6 = IronPropeller2.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 16).addBox(-3.0F, -22.0F, 0.9964F, 4.0F, 17.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -1.7464F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r7 = IronPropeller2.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(26, 6).addBox(0.2072F, -5.8273F, -1.9964F, 2.0F, 4.0F, 1.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.2464F, 0.0F, 0.0F, 0.9948F));

		PartDefinition cube_r8 = IronPropeller2.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(32, 11).addBox(0.2072F, -5.8273F, -1.9964F, 2.0F, 4.0F, 1.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.2464F, 0.0F, 0.0F, -0.576F));

		PartDefinition cube_r9 = IronPropeller2.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(20, 6).addBox(0.2072F, -5.8273F, -1.9964F, 2.0F, 4.0F, 1.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.2464F, 0.0F, 0.0F, 2.5656F));

		PartDefinition cube_r10 = IronPropeller2.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(10, 16).addBox(-3.0F, -22.0F, -1.9964F, 4.0F, 17.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.2464F, 0.0F, 0.0F, 3.1416F));

		PartDefinition cube_r11 = IronPropeller2.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(30, 16).addBox(-3.0F, -22.0F, -1.9964F, 4.0F, 17.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.2464F, 0.0F, 0.0F, 0.0F));

		PartDefinition IronPropeller3 = partdefinition.addOrReplaceChild("IronPropeller3", CubeListBuilder.create(), PartPose.offset(46.0F, 0.9472F, 28.1494F));

		PartDefinition cube_r12 = IronPropeller3.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(6, 0).addBox(-1.0F, -5.0F, -1.9964F, 2.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-2.0F, -2.0F, -2.9964F, 4.0F, 4.0F, 12.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.247F, 0.0F, 0.0F, 0.0F));

		PartDefinition cube_r13 = IronPropeller3.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -5.0F, -1.9964F, 2.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(20, 16).addBox(-3.0F, -22.0F, -1.9964F, 4.0F, 17.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.247F, 0.0F, 0.0F, -1.5708F));

		PartDefinition cube_r14 = IronPropeller3.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(20, 0).addBox(-2.0F, 0.6238F, -3.6238F, 4.0F, 3.0F, 3.0F, new CubeDeformation(-0.11F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.247F, -0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r15 = IronPropeller3.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(32, 6).addBox(0.2072F, -5.8273F, -1.9964F, 2.0F, 4.0F, 1.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.247F, 0.0F, 0.0F, -2.1468F));

		PartDefinition cube_r16 = IronPropeller3.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(0, 16).addBox(-3.0F, -22.0F, -1.9964F, 4.0F, 17.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, -1.753F, 0.0F, 3.1416F, 1.5708F));

		PartDefinition cube_r17 = IronPropeller3.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(26, 6).addBox(0.2072F, -5.8273F, -1.9964F, 2.0F, 4.0F, 1.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.247F, 0.0F, 0.0F, 0.9948F));

		PartDefinition cube_r18 = IronPropeller3.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(32, 11).addBox(0.2072F, -5.8273F, -1.9964F, 2.0F, 4.0F, 1.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.247F, 0.0F, 0.0F, -0.576F));

		PartDefinition cube_r19 = IronPropeller3.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(20, 6).addBox(0.2072F, -5.8273F, -1.9964F, 2.0F, 4.0F, 1.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.247F, 0.0F, 0.0F, 2.5656F));

		PartDefinition cube_r20 = IronPropeller3.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(10, 16).addBox(-3.0F, -22.0F, -1.9964F, 4.0F, 17.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.247F, 0.0F, 0.0F, 3.1416F));

		PartDefinition cube_r21 = IronPropeller3.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(30, 16).addBox(-3.0F, -22.0F, 0.9964F, 4.0F, 17.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 0.0F, 1.2541F, 0.0F, 3.1416F, 0.0F));

		PartDefinition IronPropeller4 = partdefinition.addOrReplaceChild("IronPropeller4", CubeListBuilder.create(), PartPose.offset(-46.0F, 0.9472F, 28.15F));

		PartDefinition cube_r22 = IronPropeller4.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(6, 0).addBox(-1.0F, -5.0F, -1.9964F, 2.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-2.0F, -2.0F, -2.9964F, 4.0F, 4.0F, 12.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.2464F, 0.0F, 0.0F, 0.0F));

		PartDefinition cube_r23 = IronPropeller4.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -5.0F, -1.9964F, 2.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.2464F, 0.0F, 0.0F, -1.5708F));

		PartDefinition cube_r24 = IronPropeller4.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(20, 16).addBox(-3.0F, -22.0F, 0.9964F, 4.0F, 17.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -1.7464F, 0.0F, 0.0F, -1.5708F));

		PartDefinition cube_r25 = IronPropeller4.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(20, 0).addBox(-2.0F, 0.6238F, -3.6238F, 4.0F, 3.0F, 3.0F, new CubeDeformation(-0.11F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.2464F, -0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r26 = IronPropeller4.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(32, 6).addBox(0.2072F, -5.8273F, -1.9964F, 2.0F, 4.0F, 1.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.2464F, 0.0F, 0.0F, -2.1468F));

		PartDefinition cube_r27 = IronPropeller4.addOrReplaceChild("cube_r27", CubeListBuilder.create().texOffs(0, 16).addBox(-3.0F, -22.0F, -1.9964F, 4.0F, 17.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.2464F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r28 = IronPropeller4.addOrReplaceChild("cube_r28", CubeListBuilder.create().texOffs(26, 6).addBox(0.2072F, -5.8273F, -1.9964F, 2.0F, 4.0F, 1.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.2464F, 0.0F, 0.0F, 0.9948F));

		PartDefinition cube_r29 = IronPropeller4.addOrReplaceChild("cube_r29", CubeListBuilder.create().texOffs(32, 11).addBox(0.2072F, -5.8273F, -1.9964F, 2.0F, 4.0F, 1.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.2464F, 0.0F, 0.0F, -0.576F));

		PartDefinition cube_r30 = IronPropeller4.addOrReplaceChild("cube_r30", CubeListBuilder.create().texOffs(20, 6).addBox(0.2072F, -5.8273F, -1.9964F, 2.0F, 4.0F, 1.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.2464F, 0.0F, 0.0F, 2.5656F));

		PartDefinition cube_r31 = IronPropeller4.addOrReplaceChild("cube_r31", CubeListBuilder.create().texOffs(10, 16).addBox(-3.0F, -22.0F, 0.9964F, 4.0F, 17.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 0.0F, 1.2536F, 0.0F, 3.1416F, 3.1416F));

		PartDefinition cube_r32 = IronPropeller4.addOrReplaceChild("cube_r32", CubeListBuilder.create().texOffs(30, 16).addBox(-3.0F, -22.0F, -1.9964F, 4.0F, 17.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 0.0F, -1.7464F, 0.0F, 3.1416F, 0.0F));

		PartDefinition IronPropeller5 = partdefinition.addOrReplaceChild("IronPropeller5", CubeListBuilder.create(), PartPose.offset(-90.35F, 0.9472F, 30.1589F));

		PartDefinition cube_r33 = IronPropeller5.addOrReplaceChild("cube_r33", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -5.0F, -1.9964F, 2.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-2.0F, -2.0F, -2.9964F, 4.0F, 4.0F, 12.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.2459F, 0.0F, 0.0F, 0.0F));

		PartDefinition cube_r34 = IronPropeller5.addOrReplaceChild("cube_r34", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -5.0F, -1.9964F, 2.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(20, 16).addBox(-3.0F, -22.0F, -1.9964F, 4.0F, 17.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.2459F, 0.0F, 0.0F, -1.5708F));

		PartDefinition cube_r35 = IronPropeller5.addOrReplaceChild("cube_r35", CubeListBuilder.create().texOffs(20, 0).addBox(-2.0F, 0.6238F, -3.6238F, 4.0F, 3.0F, 3.0F, new CubeDeformation(-0.11F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.2459F, -0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r36 = IronPropeller5.addOrReplaceChild("cube_r36", CubeListBuilder.create().texOffs(32, 11).addBox(0.2072F, -5.8273F, -1.9964F, 2.0F, 4.0F, 1.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.2459F, 0.0F, 0.0F, -2.1468F));

		PartDefinition cube_r37 = IronPropeller5.addOrReplaceChild("cube_r37", CubeListBuilder.create().texOffs(0, 16).addBox(-3.0F, -22.0F, 0.9964F, 4.0F, 17.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, 1.253F, 0.0F, 3.1416F, 1.5708F));

		PartDefinition cube_r38 = IronPropeller5.addOrReplaceChild("cube_r38", CubeListBuilder.create().texOffs(32, 11).addBox(0.2072F, -5.8273F, -1.9964F, 2.0F, 4.0F, 1.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.2459F, 0.0F, 0.0F, 0.9948F));

		PartDefinition cube_r39 = IronPropeller5.addOrReplaceChild("cube_r39", CubeListBuilder.create().texOffs(32, 11).addBox(0.2072F, -5.8273F, -1.9964F, 2.0F, 4.0F, 1.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.2459F, 0.0F, 0.0F, -0.576F));

		PartDefinition cube_r40 = IronPropeller5.addOrReplaceChild("cube_r40", CubeListBuilder.create().texOffs(32, 11).addBox(0.2072F, -5.8273F, -1.9964F, 2.0F, 4.0F, 1.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.2459F, 0.0F, 0.0F, 2.5656F));

		PartDefinition cube_r41 = IronPropeller5.addOrReplaceChild("cube_r41", CubeListBuilder.create().texOffs(10, 16).addBox(-3.0F, -22.0F, 0.9964F, 4.0F, 17.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 0.0F, 1.253F, 0.0F, 3.1416F, 3.1416F));

		PartDefinition cube_r42 = IronPropeller5.addOrReplaceChild("cube_r42", CubeListBuilder.create().texOffs(30, 16).addBox(-3.0F, -22.0F, -1.9964F, 4.0F, 17.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.2459F, 0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(PlaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		IronPropeller2.zRot = IronPropeller3.zRot = IronPropeller4.zRot = IronPropeller5.zRot = getPropellerRotation(entity, limbSwing);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		IronPropeller2.render(poseStack, vertexConsumer, packedLight, packedOverlay);
		IronPropeller3.render(poseStack, vertexConsumer, packedLight, packedOverlay);
		IronPropeller4.render(poseStack, vertexConsumer, packedLight, packedOverlay);
		IronPropeller5.render(poseStack, vertexConsumer, packedLight, packedOverlay);
	}
}