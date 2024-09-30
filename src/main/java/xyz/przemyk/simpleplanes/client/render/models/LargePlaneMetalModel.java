package xyz.przemyk.simpleplanes.client.render.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

public class LargePlaneMetalModel extends EntityModel<PlaneEntity> {
	private final ModelPart Parts;
	private final ModelPart bb_main;

	public LargePlaneMetalModel(ModelPart root) {
		this.Parts = root.getChild("Parts");
		this.bb_main = root.getChild("bb_main");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Parts = partdefinition.addOrReplaceChild("Parts", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition bone2 = Parts.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(50, 55).addBox(-5.0F, -7.1F, -12.1F, 10.0F, 2.0F, 15.0F, new CubeDeformation(0.0F))
				.texOffs(0, 26).addBox(-5.0F, -22.1F, 2.9F, 10.0F, 17.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(56, 9).addBox(-3.0F, -29.1F, 2.9F, 6.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(56, 0).addBox(-3.0F, -29.1F, 19.9F, 6.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-5.0F, -22.1F, 19.9F, 10.0F, 17.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 55).addBox(-5.0F, -7.1F, 4.9F, 10.0F, 2.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone3 = Parts.addOrReplaceChild("bone3", CubeListBuilder.create().texOffs(35, 55).addBox(-0.4839F, -0.4108F, -0.4934F, 12.0F, 2.0F, 2.0F, new CubeDeformation(0.005F))
				.texOffs(0, 26).addBox(9.5161F, -0.4108F, -0.3934F, 2.0F, 1.0F, 25.0F, new CubeDeformation(0.0F))
				.texOffs(35, 59).addBox(-0.4839F, -0.4108F, 8.6066F, 12.0F, 1.0F, 2.0F, new CubeDeformation(0.005F))
				.texOffs(0, 0).addBox(-0.4839F, -0.4108F, -0.3934F, 2.0F, 1.0F, 25.0F, new CubeDeformation(0.0F))
				.texOffs(0, 52).addBox(-0.4839F, -0.4108F, 24.6066F, 12.0F, 1.0F, 2.0F, new CubeDeformation(0.005F))
				.texOffs(90, 50).addBox(-29.2839F, 0.4292F, 47.6066F, 4.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F))
				.texOffs(90, 32).addBox(-17.6839F, 0.4292F, 47.6066F, 4.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F))
				.texOffs(90, 28).addBox(-17.6839F, 0.4292F, 32.6066F, 4.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F))
				.texOffs(90, 0).addBox(-29.2839F, 0.4292F, 32.6066F, 4.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F))
				.texOffs(54, 90).addBox(6.4661F, 25.3292F, 1.6066F, 1.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(48, 90).addBox(3.4661F, 25.3292F, 1.6066F, 1.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(56, 48).addBox(-29.2839F, 8.9292F, 49.3066F, 16.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F))
				.texOffs(88, 14).addBox(36.3161F, 0.4292F, 47.6066F, 4.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F))
				.texOffs(87, 24).addBox(24.7161F, 0.4292F, 47.6066F, 4.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F))
				.texOffs(85, 64).addBox(36.3161F, 0.4292F, 32.6066F, 4.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F))
				.texOffs(78, 12).addBox(24.7161F, 0.4292F, 32.6066F, 4.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F))
				.texOffs(56, 20).addBox(24.3161F, 8.9292F, 49.3066F, 16.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F)), PartPose.offset(-5.5161F, -31.1292F, -4.6066F));

		PartDefinition cube_r1 = bone3.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(66, 78).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 13.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.6867F, 0.1102F, 0.1991F));

		PartDefinition octagon_r1 = bone3.addOrReplaceChild("octagon_r1", CubeListBuilder.create().texOffs(72, 72).addBox(-2.0F, -1.868F, 0.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-14.4339F, 29.6612F, 54.1066F, 0.0F, -1.5708F, 0.0F));

		PartDefinition octagon_r2 = bone3.addOrReplaceChild("octagon_r2", CubeListBuilder.create().texOffs(56, 0).addBox(-1.0F, -1.0F, -17.0F, 2.0F, 2.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-14.4339F, 29.5932F, 54.1066F, 0.0F, -1.5708F, -0.3927F));

		PartDefinition octagon_r3 = bone3.addOrReplaceChild("octagon_r3", CubeListBuilder.create().texOffs(56, 28).addBox(-1.0F, -1.0F, -17.0F, 2.0F, 2.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(25.4661F, 29.5932F, 54.1066F, 0.0F, 1.5708F, 0.3927F));

		PartDefinition octagon_r4 = bone3.addOrReplaceChild("octagon_r4", CubeListBuilder.create().texOffs(78, 0).addBox(-6.0F, -2.068F, 0.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(25.4661F, 29.8612F, 50.1066F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r2 = bone3.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(72, 80).addBox(-1.0F, -10.0F, -2.0F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(29, 28).addBox(-37.0F, -21.0F, -2.0F, 2.0F, 21.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(37, 28).addBox(-19.0F, -21.0F, -2.0F, 2.0F, 21.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(45, 0).addBox(-9.0F, -21.0F, -2.0F, 2.0F, 21.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(45, 28).addBox(7.0F, -21.0F, -2.0F, 2.0F, 21.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(24, 72).addBox(-63.0F, -21.0F, -2.0F, 2.0F, 21.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(80, 80).addBox(-55.0F, -10.0F, -2.0F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(32, 72).addBox(-47.0F, -21.0F, -2.0F, 2.0F, 21.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(32.5161F, 16.1292F, 37.6066F, 0.2007F, 0.0F, 0.0F));

		PartDefinition cube_r3 = bone3.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(29, 0).addBox(9.0F, -21.0F, -2.0F, 2.0F, 22.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(37, 0).addBox(27.0F, -21.0F, -2.0F, 2.0F, 22.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(16, 72).addBox(53.0F, -21.0F, -2.0F, 2.0F, 21.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 72).addBox(37.0F, -21.0F, -2.0F, 2.0F, 34.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(40, 72).addBox(-17.0F, -21.0F, -2.0F, 2.0F, 21.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(8, 72).addBox(-1.0F, -21.0F, -2.0F, 2.0F, 34.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-13.4839F, 16.1292F, 52.6066F, 0.2007F, 0.0F, 0.0F));

		PartDefinition cube_r4 = bone3.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(68, 28).addBox(-1.0F, -10.0F, -1.5F, 1.0F, 10.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(29, 28).addBox(-1.0F, -10.0F, -26.5F, 1.0F, 2.0F, 25.0F, new CubeDeformation(0.001F))
				.texOffs(85, 52).addBox(-1.0F, -10.0F, -17.5F, 1.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(14.5161F, 9.1292F, 26.1066F, 0.0F, 0.0F, -0.3054F));

		PartDefinition cube_r5 = bone3.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(29, 1).addBox(0.0F, -10.0F, -9.5F, 1.0F, 2.0F, 25.0F, new CubeDeformation(0.001F))
				.texOffs(88, 72).addBox(0.0F, -10.0F, 15.5F, 1.0F, 10.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(88, 84).addBox(0.0F, -10.0F, -0.5F, 1.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.4839F, 9.1292F, 9.1066F, 0.0F, 0.0F, 0.3054F));

		PartDefinition cube_r6 = bone3.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(60, 78).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 13.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(11.0322F, 0.0F, 0.0F, -0.6867F, -0.1102F, -0.1991F));

		PartDefinition bone = Parts.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(0.0F, 1.6F, -1.9F));

		PartDefinition Wheel_r1 = bone.addOrReplaceChild("Wheel_r1", CubeListBuilder.create().texOffs(102, 32).addBox(-14.5481F, -2.088F, 0.5F, 5.0F, 5.0F, 3.0F, new CubeDeformation(-0.002F)), PartPose.offsetAndRotation(19.95F, -14.2362F, 45.6692F, -1.5708F, 0.7854F, -1.5708F));

		PartDefinition Wheel_r2 = bone.addOrReplaceChild("Wheel_r2", CubeListBuilder.create().texOffs(102, 32).addBox(-3.136F, -0.432F, 0.5F, 5.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(23.952F, -4.9936F, 55.5716F, 0.0F, -1.5708F, 0.0F));

		PartDefinition Wheel_r3 = bone.addOrReplaceChild("Wheel_r3", CubeListBuilder.create().texOffs(102, 32).addBox(-3.088F, -0.4519F, 0.5F, 5.0F, 5.0F, 3.0F, new CubeDeformation(-0.001F)), PartPose.offsetAndRotation(23.951F, -1.4581F, 55.7625F, -1.5708F, -0.7854F, 1.5708F));

		PartDefinition Wheel_r4 = bone.addOrReplaceChild("Wheel_r4", CubeListBuilder.create().texOffs(102, 32).addBox(-4.5F, -3.068F, 0.5F, 5.0F, 5.0F, 3.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(23.952F, 1.1794F, 53.3986F, 0.0F, -1.5708F, 0.0F));

		PartDefinition Wheel_r5 = bone.addOrReplaceChild("Wheel_r5", CubeListBuilder.create().texOffs(102, 32).addBox(-0.4519F, -3.088F, 0.5F, 5.0F, 5.0F, 3.0F, new CubeDeformation(-0.002F)), PartPose.offsetAndRotation(23.95F, -1.4595F, 47.0346F, -1.5708F, -0.7854F, 1.5708F));

		PartDefinition Wheel_r6 = bone.addOrReplaceChild("Wheel_r6", CubeListBuilder.create().texOffs(102, 32).addBox(5.864F, -0.432F, 0.5F, 5.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(19.952F, -4.995F, 56.2242F, 0.0F, 1.5708F, 0.0F));

		PartDefinition Wheel_r7 = bone.addOrReplaceChild("Wheel_r7", CubeListBuilder.create().texOffs(102, 32).addBox(-3.1841F, -18.4519F, 0.5F, 5.0F, 5.0F, 3.0F, new CubeDeformation(-0.001F)), PartPose.offsetAndRotation(19.951F, 5.3689F, 37.1337F, -1.5708F, 0.7854F, -1.5708F));

		PartDefinition Wheel_r8 = bone.addOrReplaceChild("Wheel_r8", CubeListBuilder.create().texOffs(102, 32).addBox(12.5F, -7.068F, 0.5F, 5.0F, 5.0F, 3.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(19.952F, -1.8959F, 66.3972F, 0.0F, 1.5708F, 0.0F));

		PartDefinition Wheel_r9 = bone.addOrReplaceChild("Wheel_r9", CubeListBuilder.create().texOffs(102, 32).mirror().addBox(-1.8159F, -18.4519F, 0.5F, 5.0F, 5.0F, 3.0F, new CubeDeformation(-0.001F)).mirror(false), PartPose.offsetAndRotation(-19.951F, 5.3689F, 37.1337F, -1.5708F, -0.7854F, 1.5708F));

		PartDefinition Wheel_r10 = bone.addOrReplaceChild("Wheel_r10", CubeListBuilder.create().texOffs(102, 32).mirror().addBox(-10.864F, -0.432F, 0.5F, 5.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-19.952F, -4.995F, 56.2242F, 0.0F, -1.5708F, 0.0F));

		PartDefinition Wheel_r11 = bone.addOrReplaceChild("Wheel_r11", CubeListBuilder.create().texOffs(102, 32).mirror().addBox(-4.5481F, -3.088F, 0.5F, 5.0F, 5.0F, 3.0F, new CubeDeformation(-0.002F)).mirror(false), PartPose.offsetAndRotation(-23.95F, -1.4595F, 47.0346F, -1.5708F, 0.7854F, -1.5708F));

		PartDefinition Wheel_r12 = bone.addOrReplaceChild("Wheel_r12", CubeListBuilder.create().texOffs(102, 32).mirror().addBox(-0.5F, -3.068F, 0.5F, 5.0F, 5.0F, 3.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offsetAndRotation(-23.952F, 1.1794F, 53.3986F, 0.0F, 1.5708F, 0.0F));

		PartDefinition Wheel_r13 = bone.addOrReplaceChild("Wheel_r13", CubeListBuilder.create().texOffs(102, 32).mirror().addBox(-1.912F, -0.4519F, 0.5F, 5.0F, 5.0F, 3.0F, new CubeDeformation(-0.001F)).mirror(false), PartPose.offsetAndRotation(-23.951F, -1.4581F, 55.7625F, -1.5708F, 0.7854F, -1.5708F));

		PartDefinition Wheel_r14 = bone.addOrReplaceChild("Wheel_r14", CubeListBuilder.create().texOffs(102, 32).mirror().addBox(9.5481F, -2.088F, 0.5F, 5.0F, 5.0F, 3.0F, new CubeDeformation(-0.002F)).mirror(false), PartPose.offsetAndRotation(-19.95F, -14.2362F, 45.6692F, -1.5708F, -0.7854F, 1.5708F));

		PartDefinition Wheel_r15 = bone.addOrReplaceChild("Wheel_r15", CubeListBuilder.create().texOffs(102, 32).mirror().addBox(-1.864F, -0.432F, 0.5F, 5.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-23.952F, -4.9936F, 55.5716F, 0.0F, 1.5708F, 0.0F));

		PartDefinition Wheel_r16 = bone.addOrReplaceChild("Wheel_r16", CubeListBuilder.create().texOffs(102, 32).mirror().addBox(-17.5F, -7.068F, 0.5F, 5.0F, 5.0F, 3.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offsetAndRotation(-19.952F, -1.8959F, 66.3972F, 0.0F, -1.5708F, 0.0F));

		PartDefinition Wheel_r17 = bone.addOrReplaceChild("Wheel_r17", CubeListBuilder.create().texOffs(105, 32).addBox(-3.5F, -1.5503F, -1.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(-0.002F)), PartPose.offsetAndRotation(-0.001F, -0.2721F, -0.1706F, 0.0F, -1.5708F, 0.0F));

		PartDefinition Wheel_r18 = bone.addOrReplaceChild("Wheel_r18", CubeListBuilder.create().texOffs(105, 32).addBox(0.5F, -1.5503F, -1.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(-0.002F)), PartPose.offsetAndRotation(-0.001F, -0.2721F, 0.0701F, 0.0F, -1.5708F, 0.0F));

		PartDefinition Wheel_r19 = bone.addOrReplaceChild("Wheel_r19", CubeListBuilder.create().texOffs(105, 32).addBox(-1.5503F, -3.5F, -1.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(0.001F, -0.3746F, 0.0721F, -1.5708F, -0.7854F, 1.5708F));

		PartDefinition Wheel_r20 = bone.addOrReplaceChild("Wheel_r20", CubeListBuilder.create().texOffs(105, 32).addBox(-1.5503F, 0.5F, -1.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.001F, -0.199F, -0.1015F, -1.5708F, -0.7854F, 1.5708F));

		PartDefinition Wheel_r21 = bone.addOrReplaceChild("Wheel_r21", CubeListBuilder.create().texOffs(105, 32).addBox(-1.5503F, -3.5F, -1.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(0.0F, -0.4446F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition Wheel_r22 = bone.addOrReplaceChild("Wheel_r22", CubeListBuilder.create().texOffs(105, 32).addBox(-1.5503F, 0.3F, -1.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0015F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition Wheel_r23 = bone.addOrReplaceChild("Wheel_r23", CubeListBuilder.create().texOffs(105, 32).addBox(-3.5F, -1.5503F, -1.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.001F, -0.3746F, -0.1726F, -1.5708F, -0.7854F, 1.5708F));

		PartDefinition Wheel_r24 = bone.addOrReplaceChild("Wheel_r24", CubeListBuilder.create().texOffs(105, 32).addBox(0.5F, -1.5503F, -1.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.001F, -0.199F, 0.001F, -1.5708F, -0.7854F, 1.5708F));

		PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(115, 99).addBox(-1.0F, -0.25F, -3.5F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.05F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		Parts.render(poseStack, vertexConsumer, packedLight, packedOverlay);
		bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay);
	}

	@Override
	public void setupAnim(PlaneEntity p_102618_, float p_102619_, float p_102620_, float p_102621_, float p_102622_, float p_102623_) {}
}