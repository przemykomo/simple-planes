package xyz.przemyk.simpleplanes.client.render.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

public class CargoPlaneMetalModel extends EntityModel<PlaneEntity> {

	private final ModelPart Parts;

	public CargoPlaneMetalModel(ModelPart root) {
		this.Parts = root.getChild("Parts");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Parts = partdefinition.addOrReplaceChild("Parts", CubeListBuilder.create().texOffs(51, 149).addBox(-8.9F, 2.7329F, 7.283F, 6.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(237, 63).addBox(-10.9F, 10.7329F, 6.283F, 10.0F, 14.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 192).addBox(-10.9F, 24.7329F, -7.717F, 10.0F, 2.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(237, 46).addBox(-15.4F, 18.6729F, -45.517F, 19.0F, 3.0F, 8.0F, new CubeDeformation(-0.04F))
				.texOffs(0, 237).addBox(-1.1F, 1.7329F, 53.283F, 4.0F, 9.0F, 27.0F, new CubeDeformation(0.01F))
				.texOffs(0, 237).mirror().addBox(-14.7F, 1.7329F, 53.283F, 4.0F, 9.0F, 27.0F, new CubeDeformation(0.01F)).mirror(false)
				.texOffs(204, 273).mirror().addBox(-75.7F, 2.2329F, 56.283F, 4.0F, 6.0F, 15.0F, new CubeDeformation(-0.03F)).mirror(false)
				.texOffs(204, 273).mirror().addBox(-120.7F, 2.2329F, 58.283F, 4.0F, 6.0F, 15.0F, new CubeDeformation(-0.03F)).mirror(false)
				.texOffs(204, 273).addBox(59.9F, 2.2329F, 56.283F, 4.0F, 6.0F, 15.0F, new CubeDeformation(-0.03F))
				.texOffs(204, 273).addBox(104.9F, 2.2329F, 58.283F, 4.0F, 6.0F, 15.0F, new CubeDeformation(-0.03F))
				.texOffs(242, 191).mirror().addBox(-31.7F, 2.2329F, 54.283F, 4.0F, 6.0F, 23.0F, new CubeDeformation(-0.03F)).mirror(false)
				.texOffs(242, 191).addBox(15.9F, 2.2329F, 54.283F, 4.0F, 6.0F, 23.0F, new CubeDeformation(-0.03F))
				.texOffs(72, 281).addBox(-1.105F, 19.6175F, 74.9956F, 4.0F, 19.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(72, 281).mirror().addBox(-14.695F, 19.6175F, 74.9956F, 4.0F, 19.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(276, 0).addBox(-12.9F, -1.5671F, 8.283F, 14.0F, 13.0F, 2.0F, new CubeDeformation(-0.004F))
				.texOffs(0, 273).addBox(-20.4F, 10.7329F, 9.213F, 29.0F, 27.0F, 1.0F, new CubeDeformation(0.1F))
				.texOffs(0, 220).addBox(-19.4F, 32.8329F, 119.283F, 27.0F, 3.0F, 14.0F, new CubeDeformation(0.0F))
				.texOffs(197, 128).addBox(-13.9F, 25.8329F, 207.283F, 16.0F, 3.0F, 18.0F, new CubeDeformation(0.0F))
				.texOffs(242, 169).addBox(-18.45F, 33.9329F, -47.217F, 25.0F, 1.0F, 56.0F, new CubeDeformation(0.0F))
				.texOffs(204, 252).addBox(-23.9F, 18.7329F, 76.183F, 36.0F, 19.0F, 2.0F, new CubeDeformation(0.01F))
				.texOffs(198, 58).addBox(-20.9F, 37.6329F, 10.283F, 30.0F, 1.0F, 66.0F, new CubeDeformation(0.0F))
				.texOffs(82, 244).addBox(-23.9F, 32.7329F, 10.283F, 3.0F, 5.0F, 66.0F, new CubeDeformation(0.02F))
				.texOffs(0, 239).addBox(9.1F, 32.7329F, 10.283F, 3.0F, 5.0F, 66.0F, new CubeDeformation(0.02F)), PartPose.offset(5.9F, -16.7329F, -9.283F));

		PartDefinition cube_r1 = Parts.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(232, 97).addBox(-2.1F, -2.8815F, -16.9557F, 4.0F, 4.0F, 12.0F, new CubeDeformation(-0.05F))
				.texOffs(203, 65).addBox(20.0F, -2.8815F, -27.9557F, 4.0F, 6.0F, 26.0F, new CubeDeformation(-0.03F))
				.texOffs(204, 273).addBox(-24.0F, -2.8815F, -25.9557F, 4.0F, 6.0F, 15.0F, new CubeDeformation(-0.03F)), PartPose.offsetAndRotation(40.1061F, 32.9957F, 67.7387F, 0.0F, 0.0F, 3.0892F));

		PartDefinition cube_r2 = Parts.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(250, 273).addBox(-3.0F, -0.25F, -5.5F, 6.0F, 1.0F, 14.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(40.3567F, 35.868F, 55.283F, 0.0F, 0.0F, 3.1416F));

		PartDefinition cube_r3 = Parts.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(250, 273).mirror().addBox(-3.0F, -0.25F, -5.5F, 6.0F, 1.0F, 14.0F, new CubeDeformation(-0.05F)).mirror(false), PartPose.offsetAndRotation(-52.1567F, 35.868F, 55.283F, 0.0F, 0.0F, -3.1416F));

		PartDefinition cube_r4 = Parts.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(232, 97).mirror().addBox(-1.9F, -2.8815F, -16.9557F, 4.0F, 4.0F, 12.0F, new CubeDeformation(-0.05F)).mirror(false)
				.texOffs(203, 65).mirror().addBox(-24.0F, -2.8815F, -27.9557F, 4.0F, 6.0F, 26.0F, new CubeDeformation(-0.03F)).mirror(false)
				.texOffs(204, 273).mirror().addBox(20.0F, -2.8815F, -25.9557F, 4.0F, 6.0F, 15.0F, new CubeDeformation(-0.03F)).mirror(false), PartPose.offsetAndRotation(-51.9061F, 32.9957F, 67.7387F, 0.0F, 0.0F, -3.0892F));

		PartDefinition cube_r5 = Parts.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(77, 124).mirror().addBox(4.9F, -4.0F, -2.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(-0.01F)).mirror(false)
				.texOffs(77, 124).addBox(-6.9F, -4.0F, -2.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(-5.9F, 1.5F, 12.8F, 0.6981F, 0.0F, 0.0F));

		PartDefinition cube_r6 = Parts.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(217, 192).addBox(-1.0F, 1.5F, 9.8F, 2.0F, 37.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(217, 192).mirror().addBox(10.8F, 1.5F, 9.8F, 2.0F, 37.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(111, 248).mirror().addBox(11.8F, -1.5F, -7.2F, 1.0F, 2.0F, 16.0F, new CubeDeformation(0.01F)).mirror(false)
				.texOffs(166, 61).addBox(-0.1F, -1.5F, -7.2F, 12.0F, 1.0F, 2.0F, new CubeDeformation(-0.01F))
				.texOffs(111, 248).addBox(-1.0F, -1.5F, -7.2F, 1.0F, 2.0F, 16.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-11.8F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));

		PartDefinition Wheel_r1 = Parts.addOrReplaceChild("Wheel_r1", CubeListBuilder.create().texOffs(77, 113).addBox(-1.5F, -3.0F, -3.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(-9.948F, 48.9443F, -1.2184F, 0.0F, -1.5708F, 0.3665F));

		PartDefinition Wheel_r2 = Parts.addOrReplaceChild("Wheel_r2", CubeListBuilder.create().texOffs(93, 87).addBox(0.0F, -3.0F, -1.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.002F))
				.texOffs(72, 149).mirror().addBox(0.0F, -10.0F, -1.0F, 3.0F, 10.0F, 2.0F, new CubeDeformation(0.002F)).mirror(false), PartPose.offsetAndRotation(-2.852F, 48.9443F, 0.2816F, 0.0F, 1.5708F, 0.0F));

		PartDefinition Wheel_r3 = Parts.addOrReplaceChild("Wheel_r3", CubeListBuilder.create().texOffs(77, 113).mirror().addBox(-1.5F, -3.0F, -3.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.002F)).mirror(false), PartPose.offsetAndRotation(-1.852F, 48.9443F, -1.2184F, 0.0F, 1.5708F, -0.3665F));

		PartDefinition Wheel_r4 = Parts.addOrReplaceChild("Wheel_r4", CubeListBuilder.create().texOffs(88, 149).mirror().addBox(-2.0F, -12.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offsetAndRotation(-2.852F, 48.9443F, -2.7184F, 1.5708F, 1.0472F, 1.5708F));

		PartDefinition Wheel_r5 = Parts.addOrReplaceChild("Wheel_r5", CubeListBuilder.create().texOffs(112, 151).mirror().addBox(0.0F, -10.0F, -1.0F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offsetAndRotation(-2.852F, 48.9443F, 0.2816F, -1.5708F, 1.0472F, -1.5708F));

		PartDefinition Wheel_r6 = Parts.addOrReplaceChild("Wheel_r6", CubeListBuilder.create().texOffs(72, 149).addBox(-3.0F, -10.0F, -1.0F, 3.0F, 10.0F, 2.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(-8.948F, 48.9443F, 0.2816F, 0.0F, -1.5708F, 0.0F));

		PartDefinition Wheel_r7 = Parts.addOrReplaceChild("Wheel_r7", CubeListBuilder.create().texOffs(88, 149).addBox(0.0F, -12.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-8.948F, 48.9443F, -2.7184F, 1.5708F, -1.0472F, -1.5708F));

		PartDefinition Wheel_r8 = Parts.addOrReplaceChild("Wheel_r8", CubeListBuilder.create().texOffs(112, 151).addBox(-2.0F, -10.0F, -1.0F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-8.948F, 48.9443F, 0.2816F, -1.5708F, -1.0472F, 1.5708F));

		PartDefinition Wheel_r9 = Parts.addOrReplaceChild("Wheel_r9", CubeListBuilder.create().texOffs(128, 167).addBox(-11.0F, 4.2113F, -8.0014F, 5.0F, 5.0F, 4.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-11.9F, 40.7329F, 7.283F, 0.0F, -1.5708F, 0.0F));

		PartDefinition Wheel_r10 = Parts.addOrReplaceChild("Wheel_r10", CubeListBuilder.create().texOffs(29, 118).addBox(-3.136F, -0.432F, 0.5F, 5.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.948F, 45.3393F, 2.9546F, 0.0F, -1.5708F, 0.0F));

		PartDefinition Wheel_r11 = Parts.addOrReplaceChild("Wheel_r11", CubeListBuilder.create().texOffs(5, 96).addBox(12.5F, -7.068F, 0.5F, 5.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.948F, 48.437F, 13.7802F, 0.0F, 1.5708F, 0.0F));

		PartDefinition Wheel_r12 = Parts.addOrReplaceChild("Wheel_r12", CubeListBuilder.create().texOffs(5, 118).addBox(-14.5481F, -2.088F, 0.5F, 5.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.95F, 36.0967F, -6.9477F, -1.5708F, 0.7854F, -1.5708F));

		PartDefinition Wheel_r13 = Parts.addOrReplaceChild("Wheel_r13", CubeListBuilder.create().texOffs(5, 96).addBox(-3.088F, -0.4519F, 0.5F, 5.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.949F, 48.8749F, 3.1456F, -1.5708F, -0.7854F, 1.5708F));

		PartDefinition Wheel_r14 = Parts.addOrReplaceChild("Wheel_r14", CubeListBuilder.create().texOffs(5, 106).addBox(-4.5F, -3.068F, 0.5F, 5.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.948F, 51.5123F, 0.7816F, 0.0F, -1.5708F, 0.0F));

		PartDefinition Wheel_r15 = Parts.addOrReplaceChild("Wheel_r15", CubeListBuilder.create().texOffs(5, 118).addBox(-0.4519F, -3.088F, 0.5F, 5.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.95F, 48.8734F, -5.5823F, -1.5708F, -0.7854F, 1.5708F));

		PartDefinition Wheel_r16 = Parts.addOrReplaceChild("Wheel_r16", CubeListBuilder.create().texOffs(29, 118).addBox(5.864F, -0.432F, 0.5F, 5.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.948F, 45.3379F, 3.6072F, 0.0F, 1.5708F, 0.0F));

		PartDefinition Wheel_r17 = Parts.addOrReplaceChild("Wheel_r17", CubeListBuilder.create().texOffs(129, 151).addBox(-3.1841F, -18.4519F, 0.5F, 5.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.949F, 55.7019F, -15.4832F, -1.5708F, 0.7854F, -1.5708F));

		PartDefinition Wheel_r18 = Parts.addOrReplaceChild("Wheel_r18", CubeListBuilder.create().texOffs(24, 116).addBox(-3.5007F, 1.4466F, -32.452F, 7.0F, 7.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(0, 104).addBox(1.4486F, -3.5007F, -32.453F, 7.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.9F, 44.8156F, 89.7795F, 0.0F, -1.5708F, 0.0F));

		PartDefinition Wheel_r19 = Parts.addOrReplaceChild("Wheel_r19", CubeListBuilder.create().texOffs(0, 116).addBox(-3.5007F, 1.4515F, -32.455F, 7.0F, 7.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(0, 92).addBox(1.4495F, -3.5007F, -32.454F, 7.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.9F, 44.8156F, 89.7795F, -1.5708F, -0.7854F, 1.5708F));

		PartDefinition Wheel_r20 = Parts.addOrReplaceChild("Wheel_r20", CubeListBuilder.create().texOffs(0, 104).addBox(1.4486F, -3.4993F, 27.453F, 7.0F, 7.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(24, 116).addBox(-3.5007F, -8.4466F, 27.452F, 7.0F, 7.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(154, 280).addBox(-3.5007F, -3.4466F, 26.452F, 7.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.9F, 44.8156F, 89.7795F, 0.0F, 1.5708F, 0.0F));

		PartDefinition Wheel_r21 = Parts.addOrReplaceChild("Wheel_r21", CubeListBuilder.create().texOffs(0, 92).addBox(1.4495F, -3.4993F, 27.454F, 7.0F, 7.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(0, 116).addBox(-3.5007F, -8.4515F, 27.455F, 7.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.9F, 44.8156F, 89.7795F, -1.5708F, 0.7854F, -1.5708F));

		PartDefinition Wheel_r22 = Parts.addOrReplaceChild("Wheel_r22", CubeListBuilder.create().texOffs(0, 104).mirror().addBox(-8.4486F, -3.4993F, 27.453F, 7.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(154, 280).mirror().addBox(-3.4993F, -3.4466F, 26.452F, 7.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(24, 116).mirror().addBox(-3.4993F, -8.4466F, 27.452F, 7.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-5.9F, 44.8156F, 89.7795F, 0.0F, -1.5708F, 0.0F));

		PartDefinition Wheel_r23 = Parts.addOrReplaceChild("Wheel_r23", CubeListBuilder.create().texOffs(0, 92).mirror().addBox(-8.4495F, -3.4993F, 27.454F, 7.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 116).mirror().addBox(-3.4993F, -8.4515F, 27.455F, 7.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-5.9F, 44.8156F, 89.7795F, -1.5708F, -0.7854F, 1.5708F));

		PartDefinition Wheel_r24 = Parts.addOrReplaceChild("Wheel_r24", CubeListBuilder.create().texOffs(237, 79).addBox(-1.5F, -1.0F, 0.5F, 3.0F, 2.0F, 10.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(17.9254F, 33.3433F, 89.7802F, 0.0F, 1.5708F, 1.405F));

		PartDefinition Wheel_r25 = Parts.addOrReplaceChild("Wheel_r25", CubeListBuilder.create().texOffs(80, 195).addBox(-1.5F, -4.0F, -15.0F, 3.0F, 3.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.552F, 47.869F, 89.7802F, 0.0F, 1.5708F, 0.5585F));

		PartDefinition Wheel_r26 = Parts.addOrReplaceChild("Wheel_r26", CubeListBuilder.create().texOffs(203, 97).addBox(-1.5F, -4.0F, -23.0F, 3.0F, 3.0F, 23.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(20.552F, 47.869F, 89.7802F, 3.1416F, 0.8727F, -2.5831F));

		PartDefinition Wheel_r27 = Parts.addOrReplaceChild("Wheel_r27", CubeListBuilder.create().texOffs(237, 79).mirror().addBox(-1.5F, -1.0F, 0.5F, 3.0F, 2.0F, 10.0F, new CubeDeformation(-0.01F)).mirror(false), PartPose.offsetAndRotation(-29.7254F, 33.3433F, 89.7802F, 0.0F, -1.5708F, -1.405F));

		PartDefinition Wheel_r28 = Parts.addOrReplaceChild("Wheel_r28", CubeListBuilder.create().texOffs(80, 195).mirror().addBox(-1.5F, -4.0F, -15.0F, 3.0F, 3.0F, 15.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-32.352F, 47.869F, 89.7802F, 0.0F, -1.5708F, -0.5585F));

		PartDefinition Wheel_r29 = Parts.addOrReplaceChild("Wheel_r29", CubeListBuilder.create().texOffs(0, 104).mirror().addBox(-8.4486F, -3.5007F, -32.453F, 7.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(24, 116).mirror().addBox(-3.4993F, 1.4466F, -32.452F, 7.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-5.9F, 44.8156F, 89.7795F, 0.0F, 1.5708F, 0.0F));

		PartDefinition Wheel_r30 = Parts.addOrReplaceChild("Wheel_r30", CubeListBuilder.create().texOffs(0, 92).mirror().addBox(-8.4495F, -3.5007F, -32.454F, 7.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 116).mirror().addBox(-3.4993F, 1.4515F, -32.455F, 7.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-5.9F, 44.8156F, 89.7795F, -1.5708F, 0.7854F, -1.5708F));

		PartDefinition cube_r7 = Parts.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(203, 97).mirror().addBox(-7.0F, 0.0F, -1.0F, 7.0F, 15.0F, 2.0F, new CubeDeformation(-0.005F)).mirror(false), PartPose.offsetAndRotation(1.1F, -1.5671F, 9.283F, 0.0F, 0.0F, -0.5585F));

		PartDefinition cube_r8 = Parts.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(203, 97).addBox(0.0F, 0.0F, -1.0F, 7.0F, 15.0F, 2.0F, new CubeDeformation(-0.005F)), PartPose.offsetAndRotation(-12.9F, -1.5671F, 9.283F, 0.0F, 0.0F, 0.5585F));

		PartDefinition cube_r9 = Parts.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(194, 44).mirror().addBox(-2.0F, 0.0F, -12.0F, 4.0F, 2.0F, 12.0F, new CubeDeformation(-0.04F)).mirror(false)
				.texOffs(194, 44).addBox(11.6F, 0.0F, -12.0F, 4.0F, 2.0F, 12.0F, new CubeDeformation(-0.04F)), PartPose.offsetAndRotation(-12.7F, 3.9316F, 122.226F, 0.0698F, 0.0F, 0.0F));

		PartDefinition cube_r10 = Parts.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(72, 248).addBox(-2.0F, 0.0F, -31.0F, 4.0F, 2.0F, 31.0F, new CubeDeformation(0.0F))
				.texOffs(72, 248).mirror().addBox(-49.6F, 0.0F, -31.0F, 4.0F, 2.0F, 31.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(17.9F, 4.0316F, 108.026F, 0.0698F, 0.0F, 0.0F));

		PartDefinition cube_r11 = Parts.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(72, 248).addBox(-2.0F, 0.0F, 11.0F, 4.0F, 2.0F, 31.0F, new CubeDeformation(0.01F))
				.texOffs(72, 248).mirror().addBox(-49.6F, 0.0F, 11.0F, 4.0F, 2.0F, 31.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(17.9F, 1.7329F, 66.283F, -0.0524F, 0.0F, 0.0F));

		PartDefinition cube_r12 = Parts.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(72, 237).addBox(0.0F, 0.0F, -1.0F, 2.0F, 31.0F, 2.0F, new CubeDeformation(-0.03F))
				.texOffs(72, 237).mirror().addBox(-47.6F, 0.0F, -1.0F, 2.0F, 31.0F, 2.0F, new CubeDeformation(-0.03F)).mirror(false), PartPose.offsetAndRotation(16.9F, 7.2329F, 59.783F, 0.6109F, 0.0F, 0.0F));

		PartDefinition cube_r13 = Parts.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(154, 229).addBox(0.0F, -2.0F, -1.0F, 2.0F, 33.0F, 2.0F, new CubeDeformation(-0.03F))
				.texOffs(154, 229).mirror().addBox(-47.6F, -2.0F, -1.0F, 2.0F, 33.0F, 2.0F, new CubeDeformation(-0.03F)).mirror(false), PartPose.offsetAndRotation(16.9F, 7.2329F, 96.783F, -0.6109F, 0.0F, 0.0F));

		PartDefinition cube_r14 = Parts.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(280, 252).addBox(0.0F, 0.0F, -1.0F, 2.0F, 30.0F, 2.0F, new CubeDeformation(-0.03F))
				.texOffs(280, 252).mirror().addBox(-47.6F, 0.0F, -1.0F, 2.0F, 30.0F, 2.0F, new CubeDeformation(-0.03F)).mirror(false), PartPose.offsetAndRotation(16.9F, 7.2329F, 58.783F, -0.6109F, 0.0F, 0.0F));

		PartDefinition cube_r15 = Parts.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(72, 248).addBox(20.0F, -2.6988F, -2.1149F, 4.0F, 2.0F, 31.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(40.1061F, 32.9957F, 67.7387F, -0.0524F, 0.0F, 3.0892F));

		PartDefinition cube_r16 = Parts.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(72, 248).addBox(20.0F, 0.9279F, -2.2073F, 4.0F, 2.0F, 31.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(40.1061F, 32.9957F, 67.7387F, 0.0698F, 0.0F, 3.0892F));

		PartDefinition cube_r17 = Parts.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(72, 248).mirror().addBox(-24.0F, -2.6988F, -2.1149F, 4.0F, 2.0F, 31.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(-51.9061F, 32.9957F, 67.7387F, -0.0524F, 0.0F, -3.0892F));

		PartDefinition cube_r18 = Parts.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(72, 248).mirror().addBox(-24.0F, 0.9279F, -2.2073F, 4.0F, 2.0F, 31.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-51.9061F, 32.9957F, 67.7387F, 0.0698F, 0.0F, -3.0892F));

		PartDefinition cube_r19 = Parts.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(72, 237).addBox(0.0F, -2.0F, -1.0F, 2.0F, 31.0F, 2.0F, new CubeDeformation(-0.03F))
				.texOffs(72, 237).mirror().addBox(-135.6F, -2.0F, -1.0F, 2.0F, 31.0F, 2.0F, new CubeDeformation(-0.03F)).mirror(false), PartPose.offsetAndRotation(60.9F, 7.2329F, 94.283F, -0.6109F, 0.0F, 0.0F));

		PartDefinition cube_r20 = Parts.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(90, 248).addBox(0.0F, 0.0F, -1.0F, 2.0F, 29.0F, 2.0F, new CubeDeformation(-0.03F))
				.texOffs(90, 248).mirror().addBox(-135.6F, 0.0F, -1.0F, 2.0F, 29.0F, 2.0F, new CubeDeformation(-0.03F)).mirror(false), PartPose.offsetAndRotation(60.9F, 7.2329F, 60.283F, 0.6109F, 0.0F, 0.0F));

		PartDefinition cube_r21 = Parts.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(140, 279).addBox(0.0F, 0.0F, -1.0F, 2.0F, 28.0F, 2.0F, new CubeDeformation(-0.03F))
				.texOffs(140, 279).mirror().addBox(-135.6F, 0.0F, -1.0F, 2.0F, 28.0F, 2.0F, new CubeDeformation(-0.03F)).mirror(false), PartPose.offsetAndRotation(60.9F, 7.2329F, 59.283F, -0.6109F, 0.0F, 0.0F));

		PartDefinition cube_r22 = Parts.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(72, 248).addBox(-24.0F, -2.4895F, -6.1094F, 4.0F, 2.0F, 31.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(40.1061F, 32.9957F, 62.7387F, -0.0524F, 0.0F, 3.0892F));

		PartDefinition cube_r23 = Parts.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(72, 248).addBox(-24.0F, 0.6489F, -6.1976F, 4.0F, 2.0F, 31.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(40.1061F, 32.9957F, 62.7387F, 0.0698F, 0.0F, 3.0892F));

		PartDefinition cube_r24 = Parts.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(72, 248).addBox(-2.0F, 0.0F, 11.0F, 4.0F, 2.0F, 31.0F, new CubeDeformation(0.01F))
				.texOffs(72, 248).mirror().addBox(-227.6F, 0.0F, 11.0F, 4.0F, 2.0F, 31.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(106.9F, 1.7329F, 62.283F, -0.0524F, 0.0F, 0.0F));

		PartDefinition cube_r25 = Parts.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(72, 248).addBox(-2.0F, 0.0F, -31.0F, 4.0F, 2.0F, 31.0F, new CubeDeformation(0.0F))
				.texOffs(72, 248).mirror().addBox(-227.6F, 0.0F, -31.0F, 4.0F, 2.0F, 31.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(106.9F, 4.0316F, 104.026F, 0.0698F, 0.0F, 0.0F));

		PartDefinition cube_r26 = Parts.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(72, 248).addBox(-2.0F, 0.0F, 11.0F, 4.0F, 2.0F, 31.0F, new CubeDeformation(0.01F))
				.texOffs(72, 248).mirror().addBox(-137.6F, 0.0F, 11.0F, 4.0F, 2.0F, 31.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(61.9F, 1.7329F, 60.283F, -0.0524F, 0.0F, 0.0F));

		PartDefinition cube_r27 = Parts.addOrReplaceChild("cube_r27", CubeListBuilder.create().texOffs(72, 248).addBox(-2.0F, 0.0F, -31.0F, 4.0F, 2.0F, 31.0F, new CubeDeformation(0.0F))
				.texOffs(72, 248).mirror().addBox(-137.6F, 0.0F, -31.0F, 4.0F, 2.0F, 31.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(61.9F, 4.0316F, 102.026F, 0.0698F, 0.0F, 0.0F));

		PartDefinition cube_r28 = Parts.addOrReplaceChild("cube_r28", CubeListBuilder.create().texOffs(72, 248).mirror().addBox(20.0F, 0.6489F, -6.1976F, 4.0F, 2.0F, 31.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-51.9061F, 32.9957F, 62.7387F, 0.0698F, 0.0F, -3.0892F));

		PartDefinition cube_r29 = Parts.addOrReplaceChild("cube_r29", CubeListBuilder.create().texOffs(72, 248).mirror().addBox(20.0F, -2.4895F, -6.1094F, 4.0F, 2.0F, 31.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(-51.9061F, 32.9957F, 62.7387F, -0.0524F, 0.0F, -3.0892F));

		PartDefinition cube_r30 = Parts.addOrReplaceChild("cube_r30", CubeListBuilder.create().texOffs(82, 220).addBox(-1.0F, -53.0F, -1.0F, 2.0F, 53.0F, 2.0F, new CubeDeformation(-0.03F))
				.texOffs(82, 220).addBox(-1.0F, -53.0F, -18.0F, 2.0F, 53.0F, 2.0F, new CubeDeformation(-0.03F)), PartPose.offsetAndRotation(61.4F, 31.2329F, 77.283F, 0.0F, 0.0F, 1.0908F));

		PartDefinition cube_r31 = Parts.addOrReplaceChild("cube_r31", CubeListBuilder.create().texOffs(82, 220).mirror().addBox(-1.0F, -53.0F, -1.0F, 2.0F, 53.0F, 2.0F, new CubeDeformation(-0.03F)).mirror(false)
				.texOffs(82, 220).mirror().addBox(-1.0F, -53.0F, 16.0F, 2.0F, 53.0F, 2.0F, new CubeDeformation(-0.03F)).mirror(false), PartPose.offsetAndRotation(-73.2F, 31.2329F, 60.283F, 0.0F, 0.0F, -1.0908F));

		PartDefinition cube_r32 = Parts.addOrReplaceChild("cube_r32", CubeListBuilder.create().texOffs(154, 252).mirror().addBox(-2.0F, 0.0F, 0.0F, 4.0F, 2.0F, 42.0F, new CubeDeformation(0.01F)).mirror(false)
				.texOffs(154, 252).addBox(11.6F, 0.0F, 0.0F, 4.0F, 2.0F, 42.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-12.7F, 1.7329F, 80.283F, -0.0524F, 0.0F, 0.0F));

		PartDefinition cube_r33 = Parts.addOrReplaceChild("cube_r33", CubeListBuilder.create().texOffs(154, 252).mirror().addBox(-2.0F, -1.0F, -17.0F, 4.0F, 11.0F, 17.0F, new CubeDeformation(-0.01F)).mirror(false)
				.texOffs(154, 252).addBox(11.6F, -1.0F, -17.0F, 4.0F, 11.0F, 17.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(-12.7F, 13.2744F, 87.4304F, -0.6632F, 0.0F, 0.0F));

		PartDefinition cube_r34 = Parts.addOrReplaceChild("cube_r34", CubeListBuilder.create().texOffs(242, 191).mirror().addBox(-2.0F, 0.0F, 0.0F, 4.0F, 9.0F, 6.0F, new CubeDeformation(-0.005F)).mirror(false)
				.texOffs(242, 191).addBox(11.6F, 0.0F, 0.0F, 4.0F, 9.0F, 6.0F, new CubeDeformation(-0.005F)), PartPose.offsetAndRotation(-12.7F, 11.2756F, 78.3606F, -0.384F, 0.0F, 0.0F));

		PartDefinition cube_r35 = Parts.addOrReplaceChild("cube_r35", CubeListBuilder.create().texOffs(271, 0).mirror().addBox(-2.0F, 0.0F, -127.3F, 4.0F, 8.0F, 38.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(271, 0).addBox(11.6F, 0.0F, -127.3F, 4.0F, 8.0F, 38.0F, new CubeDeformation(0.0F))
				.texOffs(165, 170).mirror().addBox(-2.0F, 0.0F, -65.3F, 4.0F, 13.0F, 69.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(165, 170).addBox(11.6F, 0.0F, -65.3F, 4.0F, 13.0F, 69.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-12.7F, 6.8329F, 205.583F, 0.0349F, 0.0F, 0.0F));

		PartDefinition cube_r36 = Parts.addOrReplaceChild("cube_r36", CubeListBuilder.create().texOffs(121, 87).mirror().addBox(-11.391F, 14.5F, -12.8561F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.02F)).mirror(false)
				.texOffs(123, 195).mirror().addBox(-11.391F, -1.9F, -12.8561F, 2.0F, 17.0F, 2.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(-5.9F, 20.6329F, -32.6478F, 0.0F, -0.1571F, 0.0F));

		PartDefinition cube_r37 = Parts.addOrReplaceChild("cube_r37", CubeListBuilder.create().texOffs(121, 87).addBox(9.391F, 14.5F, -12.8561F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.02F))
				.texOffs(123, 195).addBox(9.391F, -1.9F, -12.8561F, 2.0F, 17.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-5.9F, 20.6329F, -32.6478F, 0.0F, 0.1571F, 0.0F));

		PartDefinition cube_r38 = Parts.addOrReplaceChild("cube_r38", CubeListBuilder.create().texOffs(166, 64).addBox(0.9F, -0.5F, -18.2F, 10.0F, 1.0F, 1.0F, new CubeDeformation(-0.01F))
				.texOffs(0, 168).mirror().addBox(-0.2F, -0.5F, 7.6F, 12.0F, 1.0F, 2.0F, new CubeDeformation(0.01F)).mirror(false)
				.texOffs(219, 273).mirror().addBox(10.8F, -0.5F, -18.2F, 2.0F, 1.0F, 27.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(219, 273).addBox(-1.0F, -0.5F, -18.2F, 2.0F, 1.0F, 27.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-11.8F, 11.1F, -21.0F, 0.0F, 0.0F, 0.0F));

		PartDefinition cube_r39 = Parts.addOrReplaceChild("cube_r39", CubeListBuilder.create().texOffs(0, 237).addBox(-6.0F, -14.5F, -8.0F, 11.0F, 13.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(35, 237).addBox(5.0F, -14.5F, -8.0F, 2.0F, 13.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.15F, 36.2329F, -45.717F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r40 = Parts.addOrReplaceChild("cube_r40", CubeListBuilder.create().texOffs(152, 156).addBox(-8.5F, -1.1F, 0.0F, 18.0F, 2.0F, 1.0F, new CubeDeformation(0.055F)), PartPose.offsetAndRotation(-6.391F, 35.2329F, -46.0038F, -0.0436F, 0.0F, 0.0F));

		PartDefinition cube_r41 = Parts.addOrReplaceChild("cube_r41", CubeListBuilder.create().texOffs(0, 310).addBox(3.8424F, -0.5F, -16.7817F, 13.0F, 2.0F, 13.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(-5.9F, 35.8329F, -32.6478F, -0.1231F, 0.7816F, -0.0869F));

		PartDefinition cube_r42 = Parts.addOrReplaceChild("cube_r42", CubeListBuilder.create().texOffs(242, 161).addBox(3.8424F, -1.45F, -16.7817F, 13.0F, 3.0F, 13.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-5.9F, 20.2329F, -32.5478F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r43 = Parts.addOrReplaceChild("cube_r43", CubeListBuilder.create().texOffs(51, 158).mirror().addBox(-11.391F, -9.5F, -4.8561F, 5.0F, 1.0F, 2.0F, new CubeDeformation(0.02F)).mirror(false)
				.texOffs(218, 37).mirror().addBox(-11.391F, -9.4F, -4.8561F, 2.0F, 16.0F, 2.0F, new CubeDeformation(0.01F)).mirror(false)
				.texOffs(0, 149).mirror().addBox(-11.391F, -1.4F, -11.8561F, 2.0F, 10.0F, 9.0F, new CubeDeformation(0.03F)).mirror(false)
				.texOffs(209, 37).mirror().addBox(-11.391F, 6.6F, -4.8561F, 2.0F, 2.0F, 24.0F, new CubeDeformation(0.02F)).mirror(false)
				.texOffs(129, 159).mirror().addBox(-11.391F, -9.5F, 6.1439F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.02F)).mirror(false)
				.texOffs(218, 37).mirror().addBox(-11.391F, -9.4F, 6.1439F, 2.0F, 16.0F, 2.0F, new CubeDeformation(0.01F)).mirror(false)
				.texOffs(129, 159).mirror().addBox(-9.391F, -9.5F, 17.1439F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.01F)).mirror(false)
				.texOffs(123, 195).mirror().addBox(-11.391F, -9.5F, 17.1439F, 2.0F, 17.0F, 2.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(-5.9F, 20.1329F, -32.5478F, 0.0F, -0.1571F, 0.0F));

		PartDefinition cube_r44 = Parts.addOrReplaceChild("cube_r44", CubeListBuilder.create().texOffs(166, 64).addBox(0.9F, 0.0F, -1.0F, 10.0F, 1.0F, 1.0F, new CubeDeformation(-0.01F))
				.texOffs(242, 226).addBox(-1.0F, 0.0F, -12.0F, 2.0F, 1.0F, 12.0F, new CubeDeformation(0.0F))
				.texOffs(242, 226).mirror().addBox(10.8F, 0.0F, -12.0F, 2.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-11.8F, 10.6F, -39.2F, 0.829F, 0.0F, 0.0F));

		PartDefinition cube_r45 = Parts.addOrReplaceChild("cube_r45", CubeListBuilder.create().texOffs(51, 158).addBox(6.391F, -9.5F, -4.8561F, 5.0F, 1.0F, 2.0F, new CubeDeformation(0.02F))
				.texOffs(218, 37).addBox(9.391F, -9.4F, -4.8561F, 2.0F, 16.0F, 2.0F, new CubeDeformation(0.01F))
				.texOffs(0, 149).addBox(9.391F, -1.4F, -11.8561F, 2.0F, 10.0F, 9.0F, new CubeDeformation(0.03F))
				.texOffs(209, 37).addBox(9.391F, 6.6F, -4.8561F, 2.0F, 2.0F, 24.0F, new CubeDeformation(0.02F))
				.texOffs(129, 159).addBox(5.391F, -9.5F, 6.1439F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.02F))
				.texOffs(218, 37).addBox(9.391F, -9.4F, 6.1439F, 2.0F, 16.0F, 2.0F, new CubeDeformation(0.01F))
				.texOffs(129, 159).addBox(3.391F, -9.5F, 17.1439F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.01F))
				.texOffs(123, 195).addBox(9.391F, -9.5F, 17.1439F, 2.0F, 17.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-5.9F, 20.1329F, -32.5478F, 0.0F, 0.1571F, 0.0F));

		PartDefinition cube_r46 = Parts.addOrReplaceChild("cube_r46", CubeListBuilder.create().texOffs(273, 191).mirror().addBox(-1.5F, 11.0F, 0.0F, 3.0F, 19.0F, 3.0F, new CubeDeformation(-0.011F)).mirror(false)
				.texOffs(273, 191).addBox(-34.5F, 11.0F, 0.0F, 3.0F, 19.0F, 3.0F, new CubeDeformation(-0.011F)), PartPose.offsetAndRotation(10.6F, 10.3329F, 83.383F, -0.6981F, 0.0F, 0.0F));

		PartDefinition cube_r47 = Parts.addOrReplaceChild("cube_r47", CubeListBuilder.create().texOffs(28, 149).mirror().addBox(-1.5F, 8.7F, 0.0F, 3.0F, 13.0F, 2.0F, new CubeDeformation(-0.012F)).mirror(false)
				.texOffs(28, 149).addBox(-34.5F, 8.7F, 0.0F, 3.0F, 13.0F, 2.0F, new CubeDeformation(-0.012F)), PartPose.offsetAndRotation(10.6F, 18.3329F, 25.383F, -0.8727F, 0.0F, 0.0F));

		PartDefinition cube_r48 = Parts.addOrReplaceChild("cube_r48", CubeListBuilder.create().texOffs(220, 301).mirror().addBox(-1.5F, 0.0F, -3.0F, 3.0F, 29.0F, 3.0F, new CubeDeformation(-0.011F)).mirror(false)
				.texOffs(220, 301).addBox(-34.5F, 0.0F, -3.0F, 3.0F, 29.0F, 3.0F, new CubeDeformation(-0.011F)), PartPose.offsetAndRotation(10.6F, 10.7329F, 10.283F, 0.6981F, 0.0F, 0.0F));

		PartDefinition cube_r49 = Parts.addOrReplaceChild("cube_r49", CubeListBuilder.create().texOffs(0, 168).addBox(-9.8F, -0.4F, 0.0F, 10.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-12.8F, 11.0F, -13.4F, 0.0F, 0.6807F, 0.0F));

		PartDefinition cube_r50 = Parts.addOrReplaceChild("cube_r50", CubeListBuilder.create().texOffs(281, 129).addBox(-8.5F, -0.4F, 14.6F, 2.0F, 1.0F, 15.0F, new CubeDeformation(0.0F))
				.texOffs(281, 129).mirror().addBox(18.3F, -0.4F, 14.6F, 2.0F, 1.0F, 15.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-11.8F, 11.0F, -21.0F, 0.0F, 0.0F, 0.0F));

		PartDefinition cube_r51 = Parts.addOrReplaceChild("cube_r51", CubeListBuilder.create().texOffs(0, 168).mirror().addBox(-0.2F, -0.4F, 0.0F, 10.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.0F, 11.0F, -13.4F, 0.0F, -0.6807F, 0.0F));

		PartDefinition cube_r52 = Parts.addOrReplaceChild("cube_r52", CubeListBuilder.create().texOffs(152, 159).mirror().addBox(0.0F, 0.0F, -1.0F, 16.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.0F, -1.5F, -6.2F, 0.0F, 0.0F, 1.0123F));

		PartDefinition cube_r53 = Parts.addOrReplaceChild("cube_r53", CubeListBuilder.create().texOffs(152, 159).addBox(-16.0F, 0.0F, -1.0F, 16.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-12.8F, -1.5F, -6.2F, 0.0F, 0.0F, -1.0123F));

		PartDefinition cube_r54 = Parts.addOrReplaceChild("cube_r54", CubeListBuilder.create().texOffs(276, 15).mirror().addBox(0.0F, -1.5F, -8.2F, 1.0F, 2.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(276, 15).addBox(-12.8F, -1.5F, -8.2F, 1.0F, 2.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.3373F, -8.5476F, 1.0908F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 512, 512);
	}

	@Override
	public void setupAnim(PlaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		Parts.render(poseStack, vertexConsumer, packedLight, packedOverlay);
	}
}