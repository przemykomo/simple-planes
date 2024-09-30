package xyz.przemyk.simpleplanes.client.render.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

import static xyz.przemyk.simpleplanes.client.render.PlaneRenderer.getPropellerRotation;

public class LargePropellerModel extends EntityModel<PlaneEntity> {
	private final ModelPart IronPropeller;
	private final ModelPart bone;

	public LargePropellerModel(ModelPart root) {
		this.IronPropeller = root.getChild("IronPropeller");
		this.bone = root.getChild("bone");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition IronPropeller = partdefinition.addOrReplaceChild("IronPropeller", CubeListBuilder.create().texOffs(22, 20).addBox(-0.4787F, -1.25F, 7.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(22, 20).addBox(-0.4759F, -0.8454F, 7.5F, 1.0F, 2.0F, 5.0F, new CubeDeformation(0.001F))
				.texOffs(17, 13).addBox(0.229F, -0.5463F, 7.502F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(17, 13).addBox(-1.1835F, -0.5491F, 7.502F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(7, 25).addBox(0.6F, -0.5F, 10.7F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
				.texOffs(0, 6).addBox(2.5F, -1.5F, 10.7F, 10.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(27.0F, -5.0F, 42.0F));

		PartDefinition octagon_r1 = IronPropeller.addOrReplaceChild("octagon_r1", CubeListBuilder.create().texOffs(0, 0).addBox(2.5725F, -1.4743F, 0.1F, 10.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(21, 11).addBox(0.6725F, -0.4743F, 0.1F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0585F, -0.05F, 10.6F, 0.0F, 0.0F, 2.0944F));

		PartDefinition octagon_r2 = IronPropeller.addOrReplaceChild("octagon_r2", CubeListBuilder.create().texOffs(0, 3).addBox(2.4859F, -1.5757F, 0.1F, 10.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(21, 8).addBox(0.5859F, -0.5757F, 0.1F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0585F, -0.05F, 10.6F, 0.0F, 0.0F, -2.0944F));

		PartDefinition octagon_r3 = IronPropeller.addOrReplaceChild("octagon_r3", CubeListBuilder.create().texOffs(0, 18).addBox(-1.2306F, -0.5251F, -3.099F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.001F))
				.texOffs(0, 18).addBox(0.1768F, -0.5223F, -3.099F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.003F))
				.texOffs(0, 24).addBox(-0.5283F, -1.2314F, -3.097F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(0, 24).addBox(-0.5255F, 0.184F, -3.097F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.003F)), PartPose.offsetAndRotation(0.0585F, -0.05F, 10.6F, 0.0F, 0.0F, -0.7854F));

		PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(22, 20).mirror().addBox(-0.5213F, -1.25F, -2.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(17, 13).mirror().addBox(-1.229F, -0.5463F, -2.498F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(7, 25).mirror().addBox(-2.6F, -0.5F, 0.7F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false)
				.texOffs(0, 6).mirror().addBox(-12.5F, -1.5F, 0.7F, 10.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(22, 20).mirror().addBox(-0.5241F, -0.8454F, -2.5F, 1.0F, 2.0F, 5.0F, new CubeDeformation(0.001F)).mirror(false)
				.texOffs(17, 13).mirror().addBox(0.1835F, -0.5491F, -2.498F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-27.0F, -5.0F, 52.0F));

		PartDefinition octagon_r4 = bone.addOrReplaceChild("octagon_r4", CubeListBuilder.create().texOffs(0, 18).mirror().addBox(0.2306F, -0.5251F, -5.099F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.001F)).mirror(false)
				.texOffs(0, 24).mirror().addBox(-0.4717F, -1.2314F, -5.097F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 18).mirror().addBox(-1.1768F, -0.5223F, -5.099F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.003F)).mirror(false)
				.texOffs(0, 24).mirror().addBox(-0.4745F, 0.184F, -5.097F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.003F)).mirror(false), PartPose.offsetAndRotation(-0.0585F, -0.05F, 2.6F, 0.0F, 0.0F, 0.7854F));

		PartDefinition octagon_r5 = bone.addOrReplaceChild("octagon_r5", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-12.5725F, -1.4743F, -1.9F, 10.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(21, 11).mirror().addBox(-2.6725F, -0.4743F, -1.9F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(-0.0585F, -0.05F, 2.6F, 0.0F, 0.0F, -2.0944F));

		PartDefinition octagon_r6 = bone.addOrReplaceChild("octagon_r6", CubeListBuilder.create().texOffs(0, 3).mirror().addBox(-12.4859F, -1.5757F, -1.9F, 10.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(21, 8).mirror().addBox(-2.5859F, -0.5757F, -1.9F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(-0.0585F, -0.05F, 2.6F, 0.0F, 0.0F, 2.0944F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		IronPropeller.render(poseStack, vertexConsumer, packedLight, packedOverlay);
		bone.render(poseStack, vertexConsumer, packedLight, packedOverlay);
	}

	@Override
	public void setupAnim(PlaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		IronPropeller.zRot = getPropellerRotation(entity, limbSwing);
		bone.zRot = -IronPropeller.zRot;
	}
}