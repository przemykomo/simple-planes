package xyz.przemyk.simpleplanes.upgrades.floating;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

public class CargoFloatingModel extends EntityModel<PlaneEntity> {

	private final ModelPart Pontoons;

	public CargoFloatingModel(ModelPart root) {
		this.Pontoons = root.getChild("Pontoons");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Pontoons = partdefinition.addOrReplaceChild("Pontoons", CubeListBuilder.create().texOffs(119, 35).addBox(64.0361F, -1.4488F, -101.9839F, 8.0F, 8.0F, 28.0F, new CubeDeformation(0.0F))
		.texOffs(46, 120).addBox(64.0361F, -1.4488F, -73.9839F, 8.0F, 4.0F, 30.0F, new CubeDeformation(0.01F))
		.texOffs(0, 0).addBox(67.0061F, -12.4188F, -94.0139F, 2.0F, 10.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(0, 28).addBox(25.9687F, -10.413F, -62.9904F, 8.0F, 10.0F, 18.0F, new CubeDeformation(-0.02F))
		.texOffs(52, 158).addBox(25.9687F, -1.413F, -62.9904F, 8.0F, 9.0F, 14.0F, new CubeDeformation(-0.01F))
		.texOffs(144, 71).addBox(25.9687F, 3.587F, -49.0104F, 8.0F, 4.0F, 22.0F, new CubeDeformation(-0.01F))
		.texOffs(119, 35).mirror().addBox(-72.0361F, -1.4488F, -101.9839F, 8.0F, 8.0F, 28.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(46, 120).addBox(-72.0139F, -1.4488F, -73.9839F, 8.0F, 4.0F, 30.0F, new CubeDeformation(0.03F))
		.texOffs(0, 0).mirror().addBox(-69.0061F, -12.4188F, -94.0139F, 2.0F, 10.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 28).mirror().addBox(-33.9687F, -10.413F, -62.9904F, 8.0F, 10.0F, 18.0F, new CubeDeformation(-0.02F)).mirror(false)
		.texOffs(52, 158).mirror().addBox(-33.9687F, -1.413F, -62.9904F, 8.0F, 9.0F, 14.0F, new CubeDeformation(-0.01F)).mirror(false)
		.texOffs(144, 71).mirror().addBox(-33.9687F, 3.587F, -49.0104F, 8.0F, 4.0F, 22.0F, new CubeDeformation(-0.01F)).mirror(false)
		.texOffs(0, 230).mirror().addBox(-26.0313F, 3.587F, -49.0104F, 52.0F, 4.0F, 22.0F, new CubeDeformation(-0.01F)).mirror(false)
		.texOffs(0, 190).mirror().addBox(-26.0313F, -1.413F, -62.9904F, 52.0F, 9.0F, 14.0F, new CubeDeformation(-0.01F)).mirror(false), PartPose.offset(0.0F, 29.8F, 134.5139F));

		PartDefinition cube_r1 = Pontoons.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 216).mirror().addBox(-26.0F, -3.0F, -3.0F, 52.0F, 6.0F, 6.0F, new CubeDeformation(0.15F)).mirror(false), PartPose.offsetAndRotation(-0.0313F, 3.087F, -62.9904F, 0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r2 = Pontoons.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(100, 98).mirror().addBox(-26.0F, -3.0F, -23.0F, 52.0F, 5.0F, 26.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(188, 0).mirror().addBox(-33.9375F, -3.0F, -23.0F, 8.0F, 5.0F, 26.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(188, 0).addBox(26.0F, -3.0F, -23.0F, 8.0F, 5.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0313F, 5.587F, -26.9904F, -0.1745F, 0.0F, 0.0F));

		PartDefinition cube_r3 = Pontoons.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 150).addBox(3.8424F, -11.5F, -16.7817F, 13.0F, 14.0F, 13.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(-0.0439F, 2.0866F, -177.2127F, -0.1231F, 0.7816F, -0.0869F));

		PartDefinition cube_r4 = Pontoons.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(46, 133).mirror().addBox(-0.1313F, -14.887F, -71.5235F, 8.0F, 6.0F, 6.0F, new CubeDeformation(0.1F)).mirror(false)
		.texOffs(46, 133).addBox(59.6062F, -14.887F, -71.5235F, 8.0F, 6.0F, 6.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-33.7375F, -36.961F, -6.1316F, 0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r5 = Pontoons.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(46, 116).mirror().addBox(-4.0F, 0.0F, -9.0F, 8.0F, 13.0F, 4.0F, new CubeDeformation(-0.03F)).mirror(false)
		.texOffs(72, 58).mirror().addBox(-4.0F, 0.0F, -5.0F, 8.0F, 25.0F, 5.0F, new CubeDeformation(-0.03F)).mirror(false)
		.texOffs(46, 116).addBox(55.9375F, 0.0F, -9.0F, 8.0F, 13.0F, 4.0F, new CubeDeformation(-0.03F))
		.texOffs(72, 58).addBox(55.9375F, 0.0F, -5.0F, 8.0F, 25.0F, 5.0F, new CubeDeformation(-0.03F)), PartPose.offsetAndRotation(-29.9687F, -10.413F, -44.9904F, 1.0009F, 0.0F, 0.0F));

		PartDefinition cube_r6 = Pontoons.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(72, 93).mirror().addBox(-4.0F, 0.0F, -14.0F, 8.0F, 3.0F, 14.0F, new CubeDeformation(-0.03F)).mirror(false)
		.texOffs(72, 93).addBox(55.9375F, 0.0F, -14.0F, 8.0F, 3.0F, 14.0F, new CubeDeformation(-0.03F)), PartPose.offsetAndRotation(-29.9687F, -10.413F, -62.9904F, 1.2627F, 0.0F, 0.0F));

		PartDefinition cube_r7 = Pontoons.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(116, 93).mirror().addBox(-1.0F, -17.0F, -3.0F, 2.0F, 17.0F, 3.0F, new CubeDeformation(-0.03F)).mirror(false)
		.texOffs(116, 93).addBox(135.0121F, -17.0F, -3.0F, 2.0F, 17.0F, 3.0F, new CubeDeformation(-0.03F)), PartPose.offsetAndRotation(-68.0061F, -9.3188F, -84.1139F, -1.3526F, 0.0F, 0.0F));

		PartDefinition cube_r8 = Pontoons.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(119, 0).mirror().addBox(-1.0F, -17.0F, -4.0F, 2.0F, 18.0F, 4.0F, new CubeDeformation(-0.02F)).mirror(false)
		.texOffs(119, 0).addBox(135.0121F, -17.0F, -4.0F, 2.0F, 18.0F, 4.0F, new CubeDeformation(-0.02F)), PartPose.offsetAndRotation(-68.0061F, -2.3188F, -59.1139F, 0.6981F, 0.0F, 0.0F));

		PartDefinition cube_r9 = Pontoons.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(34, 0).mirror().addBox(-1.0F, -11.0F, -4.0F, 2.0F, 11.0F, 4.0F, new CubeDeformation(-0.02F)).mirror(false)
		.texOffs(34, 0).addBox(135.0121F, -11.0F, -4.0F, 2.0F, 11.0F, 4.0F, new CubeDeformation(-0.02F)), PartPose.offsetAndRotation(-68.0061F, -2.3188F, -87.1139F, -0.4363F, 0.0F, 0.0F));

		PartDefinition cube_r10 = Pontoons.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(72, 0).addBox(-140.05F, -4.0F, 0.0F, 8.0F, 4.0F, 31.0F, new CubeDeformation(0.0F))
		.texOffs(72, 0).addBox(-4.0F, -4.0F, 0.0F, 8.0F, 4.0F, 31.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(68.0361F, 6.5512F, -73.9839F, 0.1309F, 0.0F, 0.0F));

		PartDefinition cube_r11 = Pontoons.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(102, 35).mirror().addBox(-4.0F, -3.0F, -3.0F, 8.0F, 6.0F, 8.0F, new CubeDeformation(-0.17F)).mirror(false)
		.texOffs(102, 35).addBox(132.0721F, -3.0F, -3.0F, 8.0F, 6.0F, 8.0F, new CubeDeformation(-0.17F)), PartPose.offsetAndRotation(-68.0361F, 2.5512F, -101.9839F, 0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r12 = Pontoons.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-4.0F, 0.0F, 0.0F, 8.0F, 2.0F, 56.0F, new CubeDeformation(-0.05F)).mirror(false)
		.texOffs(0, 0).addBox(132.1321F, 0.0F, 0.0F, 8.0F, 2.0F, 56.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(-68.0661F, -2.8652F, -100.5696F, -0.0244F, 0.0F, 0.0F));

		PartDefinition cube_r13 = Pontoons.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(0, 58).addBox(-4.3276F, -6.5F, -28.26F, 18.0F, 14.0F, 10.0F, new CubeDeformation(-0.011F)), PartPose.offsetAndRotation(0.0F, -1.6099F, -161.3809F, -0.0883F, 0.1565F, -0.0138F));

		PartDefinition cube_r14 = Pontoons.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(0, 82).addBox(-13.6724F, -6.5F, -28.26F, 18.0F, 14.0F, 10.0F, new CubeDeformation(-0.022F)), PartPose.offsetAndRotation(0.0F, -1.6099F, -161.3809F, -0.0883F, -0.1565F, 0.0138F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(PlaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		Pontoons.render(poseStack, vertexConsumer, packedLight, packedOverlay);
	}
}