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
	private final ModelPart bone_propeller;
	private final ModelPart bone_propeller2;

	public LargePropellerModel(ModelPart root) {
		this.IronPropeller = root.getChild("IronPropeller");
		this.bone_propeller = this.IronPropeller.getChild("bone_propeller");
		this.bone_propeller2 = this.IronPropeller.getChild("bone_propeller2");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition IronPropeller = partdefinition.addOrReplaceChild("IronPropeller", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition bone_propeller = IronPropeller.addOrReplaceChild("bone_propeller", CubeListBuilder.create().texOffs(22, 20).addBox(-0.6213F, -1.5F, -3.5F, 1.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(17, 13).addBox(-1.5F, -0.6213F, -3.5F, 3.0F, 1.0F, 5.0F, new CubeDeformation(-0.002F))
				.texOffs(7, 25).addBox(0.6F, -0.5F, -0.3F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
				.texOffs(0, 6).addBox(2.5F, -1.5F, -0.3F, 10.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(27.0F, -29.0F, 53.0F));

		PartDefinition octagon_r1 = bone_propeller.addOrReplaceChild("octagon_r1", CubeListBuilder.create().texOffs(0, 3).addBox(3.5458F, -2.8113F, 0.1F, 10.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(21, 8).addBox(1.6458F, -1.8113F, 0.1F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(1.6585F, 0.25F, -0.4F, 0.0F, 0.0F, -2.0944F));

		PartDefinition octagon_r2 = bone_propeller.addOrReplaceChild("octagon_r2", CubeListBuilder.create().texOffs(21, 11).addBox(1.2127F, 1.0613F, 0.1F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
				.texOffs(0, 0).addBox(3.1127F, 0.0613F, 0.1F, 10.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.6585F, 0.25F, -0.4F, 0.0F, 0.0F, 2.0944F));

		PartDefinition octagon_r3 = bone_propeller.addOrReplaceChild("octagon_r3", CubeListBuilder.create().texOffs(0, 18).addBox(-2.496F, -1.9708F, -3.1F, 3.0F, 1.0F, 5.0F, new CubeDeformation(-0.001F))
				.texOffs(0, 24).addBox(-1.6173F, -2.8495F, -3.1F, 1.0F, 3.0F, 5.0F, new CubeDeformation(-0.003F)), PartPose.offsetAndRotation(1.6585F, 0.25F, -0.4F, 0.0F, 0.0F, -0.7854F));

		PartDefinition bone_propeller2 = IronPropeller.addOrReplaceChild("bone_propeller2", CubeListBuilder.create().texOffs(0, 6).mirror().addBox(-12.5F, -1.5F, -0.3F, 10.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(7, 25).mirror().addBox(-2.6F, -0.5F, -0.3F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false)
				.texOffs(17, 13).mirror().addBox(-1.5F, -0.6213F, -3.5F, 3.0F, 1.0F, 5.0F, new CubeDeformation(-0.002F)).mirror(false)
				.texOffs(22, 20).mirror().addBox(-0.6213F, -1.5F, -3.5F, 1.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-27.0F, -29.0F, 53.0F));

		PartDefinition octagon_r4 = bone_propeller2.addOrReplaceChild("octagon_r4", CubeListBuilder.create().texOffs(0, 24).mirror().addBox(0.3746F, -2.8495F, -3.1F, 1.0F, 3.0F, 5.0F, new CubeDeformation(-0.003F)).mirror(false)
				.texOffs(0, 18).mirror().addBox(-0.504F, -1.9708F, -3.1F, 3.0F, 1.0F, 5.0F, new CubeDeformation(-0.001F)).mirror(false), PartPose.offsetAndRotation(-1.6585F, 0.25F, -0.4F, 0.0F, 0.0F, 0.7854F));

		PartDefinition octagon_r5 = bone_propeller2.addOrReplaceChild("octagon_r5", CubeListBuilder.create().texOffs(21, 11).mirror().addBox(-3.2127F, 1.0613F, 0.1F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false)
				.texOffs(0, 0).mirror().addBox(-13.1127F, 0.0613F, 0.1F, 10.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.6585F, 0.25F, -0.4F, 0.0F, 0.0F, -2.0944F));

		PartDefinition octagon_r6 = bone_propeller2.addOrReplaceChild("octagon_r6", CubeListBuilder.create().texOffs(21, 8).mirror().addBox(-3.6458F, -1.8113F, 0.1F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false)
				.texOffs(0, 3).mirror().addBox(-13.5458F, -2.8113F, 0.1F, 10.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.6585F, 0.25F, -0.4F, 0.0F, 0.0F, 2.0944F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		IronPropeller.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public void setupAnim(PlaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		if (entity.isPowered() && !entity.getParked()) {
			bone_propeller.zRot = getPropellerRotation(entity, limbSwing);
			bone_propeller2.zRot = -bone_propeller.zRot;
		} else {
			bone_propeller.zRot = 0;
			bone_propeller2.zRot = 0;
		}
	}
}