package xyz.przemyk.simpleplanes.client.render.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

public class CargoPlaneModel extends EntityModel<PlaneEntity> {
	private final ModelPart Body;

	public CargoPlaneModel(ModelPart root) {
		this.Body = root.getChild("Body");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Body = partdefinition.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(7, 6).addBox(30.7F, -25.7F, 188.95F, 20.0F, 3.0F, 15.0F, new CubeDeformation(-0.01F))
				.texOffs(6, 6).addBox(-50.7F, -25.7F, 188.95F, 20.0F, 3.0F, 15.0F, new CubeDeformation(-0.01F))
				.texOffs(6, 1).addBox(-50.5F, -25.7F, 183.0F, 101.0F, 3.0F, 15.0F, new CubeDeformation(0.0F))
				.texOffs(15, 8).addBox(-49.5F, -40.7F, 185.0F, 99.0F, 3.0F, 15.0F, new CubeDeformation(0.0F))
				.texOffs(15, 6).addBox(30.7F, -40.7F, 190.95F, 21.0F, 3.0F, 15.0F, new CubeDeformation(-0.01F))
				.texOffs(13, 6).addBox(-51.7F, -40.7F, 190.95F, 21.0F, 3.0F, 15.0F, new CubeDeformation(-0.01F))
				.texOffs(7, 1).addBox(-9.4571F, -30.0F, -23.9142F, 19.0F, 7.0F, 4.0F, new CubeDeformation(-0.02F))
				.texOffs(159, 198).addBox(-9.0232F, -40.4F, 187.2008F, 18.0F, 20.0F, 11.0F, new CubeDeformation(-0.011F))
				.texOffs(92, 198).addBox(-1.5F, -63.7726F, 179.3837F, 3.0F, 25.0F, 25.0F, new CubeDeformation(-0.011F))
				.texOffs(8, 8).addBox(-6.5F, -30.0884F, -48.336F, 13.0F, 1.0F, 25.0F, new CubeDeformation(-0.04F))
				.texOffs(186, 409).addBox(-4.0F, -15.0F, -16.0F, 8.0F, 12.0F, 15.0F, new CubeDeformation(0.0F))
				.texOffs(1, 11).addBox(-18.0F, -22.0F, 67.0F, 36.0F, 19.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(221, 304).addBox(-18.0F, -37.0F, 46.0F, 36.0F, 3.0F, 33.0F, new CubeDeformation(0.02F))
				.texOffs(9, 0).addBox(-18.0F, -3.0F, -1.3F, 36.0F, 3.0F, 73.0F, new CubeDeformation(0.0F))
				.texOffs(13, 12).addBox(15.0F, -8.0F, 1.0F, 3.0F, 5.0F, 66.0F, new CubeDeformation(0.0F))
				.texOffs(12, 13).addBox(-18.0F, -8.0F, 1.0F, 3.0F, 5.0F, 66.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition cube_r1 = Body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(16, 4).addBox(-1.0F, -13.5F, -18.0F, 2.0F, 27.0F, 36.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -16.5F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r2 = Body.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(14, 15).addBox(-16.5F, -1.5F, -16.5F, 33.0F, 3.0F, 33.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, -35.5F, 86.9F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r3 = Body.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(7, 0).addBox(-100.0F, -3.0F, 1.0F, 118.0F, 3.0F, 33.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-36.0265F, -34.0F, 45.7861F, 0.0F, 0.0436F, 0.0F));

		PartDefinition cube_r4 = Body.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 0).addBox(-30.2942F, -1.6315F, -20.7881F, 87.0F, 3.0F, 33.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-72.7185F, -9.2685F, 54.4557F, 0.0F, 0.0436F, 0.0524F));

		PartDefinition cube_r5 = Body.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(9, 10).addBox(-11.5F, 0.1F, -11.5F, 23.0F, 1.0F, 23.0F, new CubeDeformation(0.145F))
				.texOffs(9, 10).addBox(-11.5F, -1.6F, -11.5F, 23.0F, 2.0F, 23.0F, new CubeDeformation(0.147F)), PartPose.offsetAndRotation(-135.1669F, -35.25F, 67.6558F, 0.0F, 0.829F, 0.0F));

		PartDefinition cube_r6 = Body.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(9, 15).addBox(-11.5F, -1.55F, -11.5F, 23.0F, 2.0F, 23.0F, new CubeDeformation(0.09F))
				.texOffs(9, 15).addBox(-11.5F, 0.05F, -11.5F, 23.0F, 1.0F, 23.0F, new CubeDeformation(0.095F)), PartPose.offsetAndRotation(-103.0359F, -10.7387F, 51.4838F, -3.1416F, -0.829F, -3.0892F));

		PartDefinition cube_r7 = Body.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(0, 1).addBox(-70.0F, -3.0F, 23.0F, 54.0F, 3.0F, 11.0F, new CubeDeformation(-0.011F)), PartPose.offsetAndRotation(5.4735F, -34.0F, 56.5861F, 0.0F, -0.1309F, 0.0F));

		PartDefinition cube_r8 = Body.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(8, 1).addBox(5.7128F, -1.6315F, -0.957F, 51.0F, 3.0F, 11.0F, new CubeDeformation(-0.11F)), PartPose.offsetAndRotation(-72.7185F, -9.2685F, 54.4557F, 0.0F, -0.1309F, 0.0524F));

		PartDefinition cube_r9 = Body.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(3, 1).addBox(16.0F, -3.0F, 23.0F, 54.0F, 3.0F, 11.0F, new CubeDeformation(-0.011F)), PartPose.offsetAndRotation(-5.4735F, -34.0F, 56.5861F, 0.0F, 0.1309F, 0.0F));

		PartDefinition cube_r10 = Body.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(18, 10).addBox(-11.5F, -1.6F, -11.5F, 23.0F, 2.0F, 23.0F, new CubeDeformation(0.147F))
				.texOffs(2, 10).addBox(-11.5F, 0.1F, -11.5F, 23.0F, 1.0F, 23.0F, new CubeDeformation(0.145F)), PartPose.offsetAndRotation(135.1669F, -35.25F, 67.6558F, 0.0F, -0.829F, 0.0F));

		PartDefinition cube_r11 = Body.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(0, 0).addBox(-18.0F, -3.0F, 1.0F, 118.0F, 3.0F, 33.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(36.0265F, -34.0F, 45.7861F, 0.0F, -0.0436F, 0.0F));

		PartDefinition cube_r12 = Body.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(11, 10).addBox(-11.5F, 0.05F, -11.5F, 23.0F, 1.0F, 23.0F, new CubeDeformation(0.095F))
				.texOffs(11, 10).addBox(-11.5F, -1.55F, -11.5F, 23.0F, 2.0F, 23.0F, new CubeDeformation(0.09F)), PartPose.offsetAndRotation(103.0359F, -10.7387F, 51.4838F, 0.0F, -0.829F, -0.0524F));

		PartDefinition cube_r13 = Body.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(0, 0).addBox(-56.7058F, -1.6315F, -20.7881F, 87.0F, 3.0F, 33.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(72.7185F, -9.2685F, 54.4557F, 0.0F, -0.0436F, -0.0524F));

		PartDefinition cube_r14 = Body.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(8, 7).addBox(-56.7128F, -1.6315F, -0.957F, 51.0F, 3.0F, 11.0F, new CubeDeformation(-0.11F)), PartPose.offsetAndRotation(72.7185F, -9.2685F, 54.4557F, 0.0F, 0.1309F, -0.0524F));

		PartDefinition cube_r15 = Body.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(15, 0).addBox(-11.391F, -0.5F, -12.8561F, 18.0F, 2.0F, 56.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, -41.8308F, -0.0883F, -0.1565F, 0.0138F));

		PartDefinition cube_r16 = Body.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(234, 252).addBox(-6.609F, -0.5F, -12.8561F, 18.0F, 2.0F, 56.0F, new CubeDeformation(-0.011F)), PartPose.offsetAndRotation(0.0F, -5.0F, -41.8308F, -0.0883F, 0.1565F, -0.0138F));

		PartDefinition cube_r17 = Body.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(25, 14).addBox(-13.4768F, -0.5F, -26.313F, 18.0F, 2.0F, 27.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-13.4768F, -20.5F, 0.687F, 14.0F, 22.0F, 64.0F, new CubeDeformation(0.0F))
				.texOffs(13, 8).addBox(-13.4768F, -20.5F, -65.313F, 18.0F, 22.0F, 39.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -7.2F, 134.5139F, 0.0875F, 0.0695F, 0.0061F));

		PartDefinition cube_r18 = Body.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(11, 14).addBox(-4.5232F, -0.5F, -26.313F, 18.0F, 2.0F, 27.0F, new CubeDeformation(-0.011F))
				.texOffs(2, 0).addBox(-0.5232F, -20.5F, 0.687F, 14.0F, 22.0F, 64.0F, new CubeDeformation(-0.011F))
				.texOffs(15, 8).addBox(-4.5232F, -20.5F, -65.313F, 18.0F, 22.0F, 39.0F, new CubeDeformation(-0.011F)), PartPose.offsetAndRotation(0.0F, -7.2F, 134.5139F, 0.0875F, -0.0695F, -0.0061F));

		PartDefinition cube_r19 = Body.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(9, 9).addBox(-16.0F, -11.0F, 0.0F, 16.0F, 20.0F, 7.0F, new CubeDeformation(-0.03F)), PartPose.offsetAndRotation(13.3961F, -16.8055F, 135.3051F, 0.1121F, -0.6776F, -0.0705F));

		PartDefinition cube_r20 = Body.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(9, 9).addBox(0.0F, -11.0F, 0.0F, 16.0F, 20.0F, 7.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(-13.3961F, -16.8055F, 135.3051F, 0.1121F, 0.6776F, 0.0705F));

		PartDefinition cube_r21 = Body.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(13, 7).addBox(-11.391F, -1.5F, -12.8561F, 2.0F, 17.0F, 10.0F, new CubeDeformation(0.0F))
				.texOffs(7, 5).addBox(-11.391F, 8.5F, -2.8561F, 2.0F, 8.0F, 22.0F, new CubeDeformation(0.0F))
				.texOffs(15, 1).addBox(-11.391F, -9.5F, 19.1439F, 8.0F, 28.0F, 24.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(0.0F, -20.5F, -41.8308F, 0.0F, -0.1571F, 0.0F));

		PartDefinition cube_r22 = Body.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(0, 4).addBox(9.391F, 8.5F, -2.8561F, 2.0F, 8.0F, 22.0F, new CubeDeformation(0.0F))
				.texOffs(2, 6).addBox(9.391F, -1.5F, -12.8561F, 2.0F, 17.0F, 10.0F, new CubeDeformation(0.0F))
				.texOffs(348, 144).addBox(3.391F, -9.5F, 19.1439F, 8.0F, 28.0F, 24.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(0.0F, -20.5F, -41.8308F, 0.0F, 0.1571F, 0.0F));

		PartDefinition cube_r23 = Body.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(0, 0).addBox(-9.0F, -0.5F, -6.5F, 18.0F, 2.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0429F, -12.7261F, 199.8289F, 0.0F, 0.0F, 0.0F));

		PartDefinition cube_r24 = Body.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(0, 15).addBox(7.0F, -5.5F, -5.5F, 2.0F, 8.0F, 15.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(0.2353F, -13.6261F, 199.8284F, 0.0F, 0.0F, 0.0F));

		PartDefinition cube_r25 = Body.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(0, 15).addBox(-9.0F, -5.5F, -5.5F, 2.0F, 8.0F, 15.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(-0.1495F, -13.6261F, 199.8284F, 0.0F, 0.0F, 0.0F));

		PartDefinition cube_r26 = Body.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(75, 289).addBox(-13.5F, -0.5F, 0.5F, 13.0F, 2.0F, 13.0F, new CubeDeformation(-0.15F)), PartPose.offsetAndRotation(0.0429F, -12.6261F, 199.4289F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r27 = Body.addOrReplaceChild("cube_r27", CubeListBuilder.create().texOffs(12, 3).addBox(-13.5F, -6.5F, 0.5F, 2.0F, 8.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0429F, -12.5261F, 199.4289F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r28 = Body.addOrReplaceChild("cube_r28", CubeListBuilder.create().texOffs(8, 12).addBox(-13.5F, -6.5F, 11.5F, 13.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0429F, -12.5261F, 199.4289F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r29 = Body.addOrReplaceChild("cube_r29", CubeListBuilder.create().texOffs(11, 0).addBox(-2.0F, -27.0F, -24.3F, 3.0F, 31.0F, 24.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(52.6205F, -27.0438F, 209.4582F, 0.0F, 0.0F, -0.0873F));

		PartDefinition cube_r30 = Body.addOrReplaceChild("cube_r30", CubeListBuilder.create().texOffs(14, 0).addBox(-1.5F, -16.0F, -27.0F, 3.0F, 16.0F, 27.0F, new CubeDeformation(-0.03F)), PartPose.offsetAndRotation(52.4711F, -23.0155F, 209.1582F, 0.4974F, 0.0F, -0.0873F));

		PartDefinition cube_r31 = Body.addOrReplaceChild("cube_r31", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -5.0F, -1.0F, 3.0F, 36.0F, 24.0F, new CubeDeformation(0.02F)), PartPose.offsetAndRotation(49.8842F, -54.1213F, 185.4534F, -0.2173F, -0.0018F, -0.089F));

		PartDefinition cube_r32 = Body.addOrReplaceChild("cube_r32", CubeListBuilder.create().texOffs(9, 0).addBox(-1.5F, -16.0F, -27.0F, 3.0F, 16.0F, 27.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(-52.3505F, -23.0716F, 209.1F, 0.4974F, 0.0F, 0.0873F));

		PartDefinition cube_r33 = Body.addOrReplaceChild("cube_r33", CubeListBuilder.create().texOffs(12, 0).addBox(-1.0F, -27.0F, -24.3F, 3.0F, 31.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-52.5F, -27.2F, 209.3F, 0.0F, 0.0F, 0.0873F));

		PartDefinition cube_r34 = Body.addOrReplaceChild("cube_r34", CubeListBuilder.create().texOffs(13, 0).addBox(-1.5F, -5.0F, -1.0F, 3.0F, 36.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-49.6842F, -54.1213F, 185.4534F, -0.2173F, 0.0018F, 0.089F));

		PartDefinition cube_r35 = Body.addOrReplaceChild("cube_r35", CubeListBuilder.create().texOffs(0, 72).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 41.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -69.4F, 179.0F, -0.2182F, 0.0F, 0.0F));

		PartDefinition cube_r36 = Body.addOrReplaceChild("cube_r36", CubeListBuilder.create().texOffs(7, 16).addBox(-1.5F, 7.0F, -1.0F, 3.0F, 76.0F, 22.0F, new CubeDeformation(-0.02F)), PartPose.offsetAndRotation(0.0F, -41.5F, 180.0F, -1.3526F, 0.0F, 0.0F));

		PartDefinition cube_r37 = Body.addOrReplaceChild("cube_r37", CubeListBuilder.create().texOffs(6, 16).addBox(-6.5F, -8.5F, -6.5F, 13.0F, 4.0F, 13.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.0429F, -32.25F, 198.8858F, 0.1231F, 0.7816F, 0.0869F));

		PartDefinition cube_r38 = Body.addOrReplaceChild("cube_r38", CubeListBuilder.create().texOffs(217, 144).addBox(-9.0F, 0.0F, -12.0F, 18.0F, 5.0F, 12.0F, new CubeDeformation(-0.012F)), PartPose.offsetAndRotation(-0.0232F, -40.7F, 187.2008F, 0.9163F, 0.0F, 0.0F));

		PartDefinition cube_r39 = Body.addOrReplaceChild("cube_r39", CubeListBuilder.create().texOffs(21, 7).addBox(-23.0F, -2.0F, -11.0F, 23.0F, 3.0F, 11.0F, new CubeDeformation(-0.011F)), PartPose.offsetAndRotation(-8.5F, -38.7F, 200.0F, 0.0F, 0.2618F, 0.0F));

		PartDefinition cube_r40 = Body.addOrReplaceChild("cube_r40", CubeListBuilder.create().texOffs(13, 7).addBox(0.0F, -2.0F, -11.0F, 23.0F, 3.0F, 11.0F, new CubeDeformation(-0.011F)), PartPose.offsetAndRotation(8.5F, -38.7F, 200.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r41 = Body.addOrReplaceChild("cube_r41", CubeListBuilder.create().texOffs(1, 0).addBox(-28.0F, -2.0F, -11.0F, 50.0F, 3.0F, 13.0F, new CubeDeformation(-0.012F)), PartPose.offsetAndRotation(25.5F, -38.7F, 190.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r42 = Body.addOrReplaceChild("cube_r42", CubeListBuilder.create().texOffs(2, 0).addBox(-22.0F, -2.0F, -11.0F, 50.0F, 3.0F, 13.0F, new CubeDeformation(-0.011F)), PartPose.offsetAndRotation(-25.5F, -38.7F, 190.0F, 0.0F, 0.2618F, 0.0F));

		PartDefinition cube_r43 = Body.addOrReplaceChild("cube_r43", CubeListBuilder.create().texOffs(2, 0).addBox(-19.0F, -2.0F, -11.0F, 43.0F, 3.0F, 13.0F, new CubeDeformation(-0.012F)), PartPose.offsetAndRotation(25.5F, -23.7F, 187.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition cube_r44 = Body.addOrReplaceChild("cube_r44", CubeListBuilder.create().texOffs(8, 0).addBox(-24.0F, -2.0F, -11.0F, 43.0F, 3.0F, 13.0F, new CubeDeformation(-0.012F)), PartPose.offsetAndRotation(-25.5F, -23.7F, 187.0F, 0.0F, 0.2618F, 0.0F));

		PartDefinition cube_r45 = Body.addOrReplaceChild("cube_r45", CubeListBuilder.create().texOffs(13, 7).addBox(-23.0F, -2.0F, -11.0F, 23.0F, 3.0F, 11.0F, new CubeDeformation(-0.011F)), PartPose.offsetAndRotation(-8.5F, -23.7F, 198.0F, 0.0F, 0.2618F, 0.0F));

		PartDefinition cube_r46 = Body.addOrReplaceChild("cube_r46", CubeListBuilder.create().texOffs(5, 7).addBox(0.0F, -2.0F, -11.0F, 23.0F, 3.0F, 11.0F, new CubeDeformation(-0.011F)), PartPose.offsetAndRotation(8.5F, -23.7F, 198.0F, 0.0F, -0.2618F, 0.0F));

		return LayerDefinition.create(meshdefinition, 16, 16);
	}

	@Override
	public void setupAnim(PlaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		Body.render(poseStack, vertexConsumer, packedLight, packedOverlay);
	}
}