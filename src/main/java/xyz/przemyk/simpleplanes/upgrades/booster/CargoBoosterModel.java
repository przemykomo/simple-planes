package xyz.przemyk.simpleplanes.upgrades.booster;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

public class CargoBoosterModel extends EntityModel<PlaneEntity> {

	private final ModelPart Booster;

	public CargoBoosterModel(ModelPart root) {
		this.Booster = root.getChild("Booster");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Booster = partdefinition.addOrReplaceChild("Booster", CubeListBuilder.create(), PartPose.offset(-4.0F, 12.0F, 100.0F));

		PartDefinition cube_r1 = Booster.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-18.0F, -2.0F, 4.0F, 44.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 4).mirror().addBox(22.5F, -2.5F, 10.9F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 9).mirror().addBox(23.0F, -2.0F, 8.9F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 4).mirror().addBox(22.5F, -2.5F, -8.1F, 3.0F, 3.0F, 17.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 4).addBox(-17.5F, -2.5F, 10.9F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 9).addBox(-17.0F, -2.0F, 8.9F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 4).addBox(-17.5F, -2.5F, -8.1F, 3.0F, 3.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1745F, 0.0F, 0.0F));

		PartDefinition cube_r2 = Booster.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-22.0F, -1.0F, -1.0F, 44.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(4.0F, 4.8834F, 5.0977F, -1.7453F, 0.0F, 0.0F));

		PartDefinition cube_r3 = Booster.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 0).addBox(-18.0F, -2.0F, -1.0F, 44.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 4).mirror().addBox(-17.5F, -2.5F, -8.1F, 3.0F, 3.0F, 17.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 9).mirror().addBox(-17.0F, -2.0F, 8.9F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 4).mirror().addBox(-17.5F, -2.5F, 10.9F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 4).addBox(22.5F, -2.5F, -8.1F, 3.0F, 3.0F, 17.0F, new CubeDeformation(0.0F))
		.texOffs(0, 9).addBox(23.0F, -2.0F, 8.9F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 4).addBox(22.5F, -2.5F, 10.9F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.0F, 0.0F, -0.1745F, 0.0F, 0.0F));

		PartDefinition cube_r4 = Booster.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-22.0F, -1.0F, -1.0F, 44.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(4.0F, -0.9848F, 0.1736F, -1.7453F, 0.0F, 0.0F));

		PartDefinition cube_r5 = Booster.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(8, 7).mirror().addBox(-0.5F, -1.5F, 0.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.05F)).mirror(false), PartPose.offsetAndRotation(24.3526F, -2.5731F, -8.8338F, -0.2444F, -0.7703F, 0.1719F));

		PartDefinition cube_r6 = Booster.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(8, 7).mirror().addBox(-0.5F, -1.5F, 0.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.05F)).mirror(false), PartPose.offsetAndRotation(-15.6474F, 2.4269F, -8.8338F, -0.2444F, -0.7703F, 0.1719F));

		PartDefinition cube_r7 = Booster.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(8, 7).addBox(-1.5F, -1.5F, 0.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.05F)), PartPose.offsetAndRotation(23.6474F, 2.4269F, -8.8338F, -0.2444F, 0.7703F, -0.1719F));

		PartDefinition cube_r8 = Booster.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(8, 7).addBox(-1.5F, -1.5F, 0.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.05F)), PartPose.offsetAndRotation(-16.3526F, -2.5731F, -8.8338F, -0.2444F, 0.7703F, -0.1719F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(PlaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		Booster.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}