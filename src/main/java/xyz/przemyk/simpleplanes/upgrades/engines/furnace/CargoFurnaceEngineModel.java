package xyz.przemyk.simpleplanes.upgrades.engines.furnace;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

public class CargoFurnaceEngineModel extends EntityModel<PlaneEntity> {

	private final ModelPart FurnaceEngine;

	public CargoFurnaceEngineModel(ModelPart root) {
		this.FurnaceEngine = root.getChild("FurnaceEngine");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition FurnaceEngine = partdefinition.addOrReplaceChild("FurnaceEngine", CubeListBuilder.create().texOffs(66, 88).mirror().addBox(-90.75F, -31.0528F, 37.5F, 1.0F, 16.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(58, 58).mirror().addBox(-93.25F, -25.9528F, 32.0F, 6.0F, 6.0F, 15.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(58, 58).mirror().addBox(42.9F, -25.9528F, 30.0F, 6.0F, 6.0F, 15.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(66, 88).mirror().addBox(45.4F, -31.0528F, 35.5F, 1.0F, 16.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(58, 58).addBox(87.25F, -25.9528F, 32.0F, 6.0F, 6.0F, 15.0F, new CubeDeformation(0.0F))
		.texOffs(66, 88).addBox(89.75F, -31.0528F, 37.5F, 1.0F, 16.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(66, 88).addBox(-46.4F, -31.0528F, 35.5F, 1.0F, 16.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(58, 58).addBox(-48.9F, -25.9528F, 30.0F, 6.0F, 6.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition cube_r1 = FurnaceEngine.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(113, 0).addBox(-1.0F, -50.0F, -1.0F, 2.0F, 50.0F, 2.0F, new CubeDeformation(-0.03F))
		.texOffs(113, 0).addBox(-1.0F, -50.0F, 16.0F, 2.0F, 50.0F, 2.0F, new CubeDeformation(-0.03F)), PartPose.offsetAndRotation(68.7F, -9.0F, 51.0F, 0.0F, 0.0F, -1.0559F));

		PartDefinition cube_r2 = FurnaceEngine.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(113, 0).addBox(-1.0F, -50.0F, -1.0F, 2.0F, 50.0F, 2.0F, new CubeDeformation(-0.03F))
		.texOffs(113, 0).addBox(-1.0F, -50.0F, -18.0F, 2.0F, 50.0F, 2.0F, new CubeDeformation(-0.03F)), PartPose.offsetAndRotation(-68.7F, -9.0F, 68.0F, 0.0F, 0.0F, 1.0559F));

		PartDefinition octagon_r1 = FurnaceEngine.addOrReplaceChild("octagon_r1", CubeListBuilder.create().texOffs(85, 64).addBox(-3.0F, -9.0F, 18.4471F, 3.0F, 2.0F, 7.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-67.8471F, -7.5005F, 34.95F, 0.0F, 1.5708F, 0.0F));

		PartDefinition octagon_r2 = FurnaceEngine.addOrReplaceChild("octagon_r2", CubeListBuilder.create().texOffs(46, 0).addBox(-11.5F, -16.0F, -3.5F, 13.0F, 17.0F, 7.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-53.4F, -22.9503F, 43.425F, 0.0F, 1.5708F, 1.5708F));

		PartDefinition octagon_r3 = FurnaceEngine.addOrReplaceChild("octagon_r3", CubeListBuilder.create().texOffs(0, 0).addBox(-14.5F, 0.0F, 0.0F, 16.0F, 17.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-42.4F, -31.4F, 40.45F, 0.0F, 1.5708F, 0.7854F));

		PartDefinition octagon_r4 = FurnaceEngine.addOrReplaceChild("octagon_r4", CubeListBuilder.create().texOffs(0, 24).addBox(-14.5F, -7.3588F, 0.2249F, 16.0F, 17.0F, 7.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-49.3053F, -21.0878F, 40.45F, 0.0F, 1.5708F, -0.7854F));

		PartDefinition octagon_r5 = FurnaceEngine.addOrReplaceChild("octagon_r5", CubeListBuilder.create().texOffs(73, 50).addBox(-24.0F, -14.0F, 25.4471F, 15.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(22, 84).addBox(-10.0F, -11.0F, 18.4471F, 1.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(46, 31).addBox(-24.0F, -15.0F, 17.4471F, 15.0F, 1.0F, 9.0F, new CubeDeformation(0.0F))
		.texOffs(73, 41).addBox(-20.0F, -6.35F, 18.4471F, 13.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(85, 55).addBox(-3.0F, -6.35F, 18.4471F, 3.0F, 2.0F, 7.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-67.8471F, -25.05F, 34.95F, 0.0F, 1.5708F, 0.0F));

		PartDefinition octagon_r6 = FurnaceEngine.addOrReplaceChild("octagon_r6", CubeListBuilder.create().texOffs(77, 24).addBox(9.0F, -14.0F, 25.4471F, 15.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-23.9529F, -25.05F, 34.95F, 0.0F, -1.5708F, 0.0F));

		PartDefinition octagon_r7 = FurnaceEngine.addOrReplaceChild("octagon_r7", CubeListBuilder.create().texOffs(39, 41).addBox(-5.0F, -13.0F, -3.5F, 10.0F, 25.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-45.9F, -26.5F, 49.95F, 0.0F, 1.5708F, 0.0F));

		PartDefinition octagon_r8 = FurnaceEngine.addOrReplaceChild("octagon_r8", CubeListBuilder.create().texOffs(66, 79).addBox(-2.5F, -4.7249F, -4.591F, 4.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-49.3053F, -21.0878F, 36.45F, 0.0F, 1.5708F, -2.3562F));

		PartDefinition octagon_r9 = FurnaceEngine.addOrReplaceChild("octagon_r9", CubeListBuilder.create().texOffs(85, 64).addBox(-1.5F, -1.0F, -3.5F, 3.0F, 2.0F, 7.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-53.3497F, -22.9503F, 36.45F, 0.0F, 1.5708F, 1.5708F));

		PartDefinition octagon_r10 = FurnaceEngine.addOrReplaceChild("octagon_r10", CubeListBuilder.create().texOffs(81, 81).addBox(-2.5F, -7.3588F, 0.2249F, 4.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-49.3053F, -21.0878F, 36.45F, 0.0F, 1.5708F, -0.7854F));

		PartDefinition octagon_r11 = FurnaceEngine.addOrReplaceChild("octagon_r11", CubeListBuilder.create().texOffs(0, 84).addBox(-2.5F, 0.0F, 0.0F, 4.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-37.4503F, -19.4503F, 36.45F, 0.0F, 1.5708F, 2.3562F));

		PartDefinition octagon_r12 = FurnaceEngine.addOrReplaceChild("octagon_r12", CubeListBuilder.create().texOffs(86, 0).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 2.0F, 7.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-37.4503F, -26.4503F, 36.45F, 0.0F, 1.5708F, 1.5708F));

		PartDefinition octagon_r13 = FurnaceEngine.addOrReplaceChild("octagon_r13", CubeListBuilder.create().texOffs(85, 29).addBox(-2.5F, 0.0F, 0.0F, 4.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-42.4F, -31.4F, 36.45F, 0.0F, 1.5708F, 0.7854F));

		PartDefinition cube_r3 = FurnaceEngine.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(72, 88).addBox(-0.5F, -8.0F, -0.5F, 1.0F, 16.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-45.9F, -22.9528F, 36.0F, 0.0F, 0.0F, -1.5708F));

		PartDefinition octagon_r14 = FurnaceEngine.addOrReplaceChild("octagon_r14", CubeListBuilder.create().texOffs(32, 24).addBox(-11.95F, -14.0F, -3.4721F, 19.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-45.9F, -23.05F, 62.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition octagon_r15 = FurnaceEngine.addOrReplaceChild("octagon_r15", CubeListBuilder.create().texOffs(0, 65).addBox(-6.2071F, -7.2071F, 18.4471F, 12.0F, 12.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-67.8471F, -22.2076F, 53.95F, -1.5708F, 0.7854F, -1.5708F));

		PartDefinition octagon_r16 = FurnaceEngine.addOrReplaceChild("octagon_r16", CubeListBuilder.create().texOffs(0, 31).addBox(7.05F, -8.9721F, -8.0F, 0.0F, 17.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-46.4F, -22.4F, 62.025F, 0.0F, 1.5708F, 0.0F));

		PartDefinition octagon_r17 = FurnaceEngine.addOrReplaceChild("octagon_r17", CubeListBuilder.create().texOffs(0, 31).addBox(7.05F, -8.9721F, -8.0F, 0.0F, 17.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(89.75F, -22.4F, 64.025F, 0.0F, 1.5708F, 0.0F));

		PartDefinition octagon_r18 = FurnaceEngine.addOrReplaceChild("octagon_r18", CubeListBuilder.create().texOffs(0, 65).addBox(-6.2071F, -7.2071F, 18.4471F, 12.0F, 12.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(68.3029F, -22.2076F, 55.95F, -1.5708F, 0.7854F, -1.5708F));

		PartDefinition octagon_r19 = FurnaceEngine.addOrReplaceChild("octagon_r19", CubeListBuilder.create().texOffs(32, 24).addBox(-11.95F, -14.0F, -3.4721F, 19.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(90.25F, -23.05F, 64.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition octagon_r20 = FurnaceEngine.addOrReplaceChild("octagon_r20", CubeListBuilder.create().texOffs(85, 55).addBox(-3.0F, -9.0F, 18.4471F, 3.0F, 2.0F, 7.0F, new CubeDeformation(0.01F))
		.texOffs(73, 41).addBox(-20.0F, -9.0F, 18.4471F, 13.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(46, 31).addBox(-24.0F, -17.65F, 17.4471F, 15.0F, 1.0F, 9.0F, new CubeDeformation(0.0F))
		.texOffs(22, 84).addBox(-10.0F, -13.65F, 18.4471F, 1.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(73, 50).addBox(-24.0F, -16.65F, 25.4471F, 15.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(68.3029F, -22.4F, 36.95F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r4 = FurnaceEngine.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(72, 88).addBox(-0.5F, -8.0F, -0.5F, 1.0F, 16.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(90.25F, -22.9528F, 38.0F, 0.0F, 0.0F, -1.5708F));

		PartDefinition octagon_r21 = FurnaceEngine.addOrReplaceChild("octagon_r21", CubeListBuilder.create().texOffs(85, 29).addBox(-2.5F, 0.0F, 0.0F, 4.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(93.75F, -31.4F, 38.45F, 0.0F, 1.5708F, 0.7854F));

		PartDefinition octagon_r22 = FurnaceEngine.addOrReplaceChild("octagon_r22", CubeListBuilder.create().texOffs(86, 0).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 2.0F, 7.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(98.6997F, -26.4503F, 38.45F, 0.0F, 1.5708F, 1.5708F));

		PartDefinition octagon_r23 = FurnaceEngine.addOrReplaceChild("octagon_r23", CubeListBuilder.create().texOffs(0, 84).addBox(-2.5F, 0.0F, 0.0F, 4.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(98.6997F, -19.4503F, 38.45F, 0.0F, 1.5708F, 2.3562F));

		PartDefinition octagon_r24 = FurnaceEngine.addOrReplaceChild("octagon_r24", CubeListBuilder.create().texOffs(81, 81).addBox(-2.5F, -7.3588F, 0.2249F, 4.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(86.8447F, -21.0878F, 38.45F, 0.0F, 1.5708F, -0.7854F));

		PartDefinition octagon_r25 = FurnaceEngine.addOrReplaceChild("octagon_r25", CubeListBuilder.create().texOffs(85, 64).addBox(-1.5F, -1.0F, -3.5F, 3.0F, 2.0F, 7.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(82.8003F, -22.9503F, 38.45F, 0.0F, 1.5708F, 1.5708F));

		PartDefinition octagon_r26 = FurnaceEngine.addOrReplaceChild("octagon_r26", CubeListBuilder.create().texOffs(66, 79).addBox(-2.5F, -4.7249F, -4.591F, 4.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(86.8447F, -21.0878F, 38.45F, 0.0F, 1.5708F, -2.3562F));

		PartDefinition octagon_r27 = FurnaceEngine.addOrReplaceChild("octagon_r27", CubeListBuilder.create().texOffs(39, 41).addBox(-5.0F, -13.0F, -3.5F, 10.0F, 25.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(90.25F, -26.5F, 51.95F, 0.0F, 1.5708F, 0.0F));

		PartDefinition octagon_r28 = FurnaceEngine.addOrReplaceChild("octagon_r28", CubeListBuilder.create().texOffs(77, 24).addBox(9.0F, -14.0F, 25.4471F, 15.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(112.1971F, -25.05F, 36.95F, 0.0F, -1.5708F, 0.0F));

		PartDefinition octagon_r29 = FurnaceEngine.addOrReplaceChild("octagon_r29", CubeListBuilder.create().texOffs(0, 24).addBox(-14.5F, -7.3588F, 0.2249F, 16.0F, 17.0F, 7.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(86.8447F, -21.0878F, 42.45F, 0.0F, 1.5708F, -0.7854F));

		PartDefinition octagon_r30 = FurnaceEngine.addOrReplaceChild("octagon_r30", CubeListBuilder.create().texOffs(0, 0).addBox(-14.5F, 0.0F, 0.0F, 16.0F, 17.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(93.75F, -31.4F, 42.45F, 0.0F, 1.5708F, 0.7854F));

		PartDefinition octagon_r31 = FurnaceEngine.addOrReplaceChild("octagon_r31", CubeListBuilder.create().texOffs(46, 0).addBox(-11.5F, -16.0F, -3.5F, 13.0F, 17.0F, 7.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(82.75F, -22.9503F, 45.425F, 0.0F, 1.5708F, 1.5708F));

		PartDefinition octagon_r32 = FurnaceEngine.addOrReplaceChild("octagon_r32", CubeListBuilder.create().texOffs(85, 64).addBox(-3.0F, -9.0F, 18.4471F, 3.0F, 2.0F, 7.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(68.3029F, -7.5005F, 36.95F, 0.0F, 1.5708F, 0.0F));

		PartDefinition octagon_r33 = FurnaceEngine.addOrReplaceChild("octagon_r33", CubeListBuilder.create().texOffs(0, 31).mirror().addBox(-7.05F, -8.9721F, -8.0F, 0.0F, 17.0F, 17.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(46.4F, -22.4F, 62.025F, 0.0F, -1.5708F, 0.0F));

		PartDefinition octagon_r34 = FurnaceEngine.addOrReplaceChild("octagon_r34", CubeListBuilder.create().texOffs(0, 65).mirror().addBox(-5.7929F, -7.2071F, 18.4471F, 12.0F, 12.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(67.8471F, -22.2076F, 53.95F, -1.5708F, -0.7854F, 1.5708F));

		PartDefinition octagon_r35 = FurnaceEngine.addOrReplaceChild("octagon_r35", CubeListBuilder.create().texOffs(32, 24).mirror().addBox(-7.05F, -14.0F, -3.4721F, 19.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(45.9F, -23.05F, 62.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition octagon_r36 = FurnaceEngine.addOrReplaceChild("octagon_r36", CubeListBuilder.create().texOffs(85, 55).mirror().addBox(0.0F, -9.0F, 18.4471F, 3.0F, 2.0F, 7.0F, new CubeDeformation(0.01F)).mirror(false)
		.texOffs(73, 41).mirror().addBox(7.0F, -9.0F, 18.4471F, 13.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(46, 31).mirror().addBox(9.0F, -17.65F, 17.4471F, 15.0F, 1.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(22, 84).mirror().addBox(9.0F, -13.65F, 18.4471F, 1.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(73, 50).mirror().addBox(9.0F, -16.65F, 25.4471F, 15.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(67.8471F, -22.4F, 34.95F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r5 = FurnaceEngine.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(72, 88).mirror().addBox(-0.5F, -8.0F, -0.5F, 1.0F, 16.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(45.9F, -22.9528F, 36.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition octagon_r37 = FurnaceEngine.addOrReplaceChild("octagon_r37", CubeListBuilder.create().texOffs(85, 29).mirror().addBox(-1.5F, 0.0F, 0.0F, 4.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(42.4F, -31.4F, 36.45F, 0.0F, -1.5708F, -0.7854F));

		PartDefinition octagon_r38 = FurnaceEngine.addOrReplaceChild("octagon_r38", CubeListBuilder.create().texOffs(86, 0).mirror().addBox(-1.5F, 0.0F, 0.0F, 3.0F, 2.0F, 7.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(37.4503F, -26.4503F, 36.45F, 0.0F, -1.5708F, -1.5708F));

		PartDefinition octagon_r39 = FurnaceEngine.addOrReplaceChild("octagon_r39", CubeListBuilder.create().texOffs(0, 84).mirror().addBox(-1.5F, 0.0F, 0.0F, 4.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(37.4503F, -19.4503F, 36.45F, 0.0F, -1.5708F, -2.3562F));

		PartDefinition octagon_r40 = FurnaceEngine.addOrReplaceChild("octagon_r40", CubeListBuilder.create().texOffs(81, 81).mirror().addBox(-1.5F, -7.3588F, 0.2249F, 4.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(49.3053F, -21.0878F, 36.45F, 0.0F, -1.5708F, 0.7854F));

		PartDefinition octagon_r41 = FurnaceEngine.addOrReplaceChild("octagon_r41", CubeListBuilder.create().texOffs(85, 64).mirror().addBox(-1.5F, -1.0F, -3.5F, 3.0F, 2.0F, 7.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(53.3497F, -22.9503F, 36.45F, 0.0F, -1.5708F, -1.5708F));

		PartDefinition octagon_r42 = FurnaceEngine.addOrReplaceChild("octagon_r42", CubeListBuilder.create().texOffs(66, 79).mirror().addBox(-1.5F, -4.7249F, -4.591F, 4.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(49.3053F, -21.0878F, 36.45F, 0.0F, -1.5708F, 2.3562F));

		PartDefinition octagon_r43 = FurnaceEngine.addOrReplaceChild("octagon_r43", CubeListBuilder.create().texOffs(39, 41).mirror().addBox(-5.0F, -13.0F, -3.5F, 10.0F, 25.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(45.9F, -26.5F, 49.95F, 0.0F, -1.5708F, 0.0F));

		PartDefinition octagon_r44 = FurnaceEngine.addOrReplaceChild("octagon_r44", CubeListBuilder.create().texOffs(77, 24).mirror().addBox(-24.0F, -14.0F, 25.4471F, 15.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(23.9529F, -25.05F, 34.95F, 0.0F, 1.5708F, 0.0F));

		PartDefinition octagon_r45 = FurnaceEngine.addOrReplaceChild("octagon_r45", CubeListBuilder.create().texOffs(0, 24).mirror().addBox(-1.5F, -7.3588F, 0.2249F, 16.0F, 17.0F, 7.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(49.3053F, -21.0878F, 40.45F, 0.0F, -1.5708F, 0.7854F));

		PartDefinition octagon_r46 = FurnaceEngine.addOrReplaceChild("octagon_r46", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-1.5F, 0.0F, 0.0F, 16.0F, 17.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(42.4F, -31.4F, 40.45F, 0.0F, -1.5708F, -0.7854F));

		PartDefinition octagon_r47 = FurnaceEngine.addOrReplaceChild("octagon_r47", CubeListBuilder.create().texOffs(46, 0).mirror().addBox(-1.5F, -16.0F, -3.5F, 13.0F, 17.0F, 7.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(53.4F, -22.9503F, 43.425F, 0.0F, -1.5708F, -1.5708F));

		PartDefinition octagon_r48 = FurnaceEngine.addOrReplaceChild("octagon_r48", CubeListBuilder.create().texOffs(85, 64).mirror().addBox(0.0F, -9.0F, 18.4471F, 3.0F, 2.0F, 7.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(67.8471F, -7.5005F, 34.95F, 0.0F, -1.5708F, 0.0F));

		PartDefinition octagon_r49 = FurnaceEngine.addOrReplaceChild("octagon_r49", CubeListBuilder.create().texOffs(85, 64).mirror().addBox(0.0F, -9.0F, 18.4471F, 3.0F, 2.0F, 7.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(-68.3029F, -7.5005F, 36.95F, 0.0F, -1.5708F, 0.0F));

		PartDefinition octagon_r50 = FurnaceEngine.addOrReplaceChild("octagon_r50", CubeListBuilder.create().texOffs(46, 0).mirror().addBox(-1.5F, -16.0F, -3.5F, 13.0F, 17.0F, 7.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(-82.75F, -22.9503F, 45.425F, 0.0F, -1.5708F, -1.5708F));

		PartDefinition octagon_r51 = FurnaceEngine.addOrReplaceChild("octagon_r51", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-1.5F, 0.0F, 0.0F, 16.0F, 17.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-93.75F, -31.4F, 42.45F, 0.0F, -1.5708F, -0.7854F));

		PartDefinition octagon_r52 = FurnaceEngine.addOrReplaceChild("octagon_r52", CubeListBuilder.create().texOffs(0, 24).mirror().addBox(-1.5F, -7.3588F, 0.2249F, 16.0F, 17.0F, 7.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(-86.8447F, -21.0878F, 42.45F, 0.0F, -1.5708F, 0.7854F));

		PartDefinition octagon_r53 = FurnaceEngine.addOrReplaceChild("octagon_r53", CubeListBuilder.create().texOffs(73, 50).mirror().addBox(9.0F, -14.0F, 25.4471F, 15.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(22, 84).mirror().addBox(9.0F, -11.0F, 18.4471F, 1.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(46, 31).mirror().addBox(9.0F, -15.0F, 17.4471F, 15.0F, 1.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(77, 117).mirror().addBox(7.0F, -6.35F, 18.4471F, 13.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(85, 55).mirror().addBox(0.0F, -6.35F, 18.4471F, 3.0F, 2.0F, 7.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(-68.3029F, -25.05F, 36.95F, 0.0F, -1.5708F, 0.0F));

		PartDefinition octagon_r54 = FurnaceEngine.addOrReplaceChild("octagon_r54", CubeListBuilder.create().texOffs(77, 24).mirror().addBox(-24.0F, -14.0F, 25.4471F, 15.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-112.1971F, -25.05F, 36.95F, 0.0F, 1.5708F, 0.0F));

		PartDefinition octagon_r55 = FurnaceEngine.addOrReplaceChild("octagon_r55", CubeListBuilder.create().texOffs(39, 41).mirror().addBox(-5.0F, -13.0F, -3.5F, 10.0F, 25.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-90.25F, -26.5F, 51.95F, 0.0F, -1.5708F, 0.0F));

		PartDefinition octagon_r56 = FurnaceEngine.addOrReplaceChild("octagon_r56", CubeListBuilder.create().texOffs(66, 79).mirror().addBox(-1.5F, -4.7249F, -4.591F, 4.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-86.8447F, -21.0878F, 38.45F, 0.0F, -1.5708F, 2.3562F));

		PartDefinition octagon_r57 = FurnaceEngine.addOrReplaceChild("octagon_r57", CubeListBuilder.create().texOffs(85, 64).mirror().addBox(-1.5F, -1.0F, -3.5F, 3.0F, 2.0F, 7.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(-82.8003F, -22.9503F, 38.45F, 0.0F, -1.5708F, -1.5708F));

		PartDefinition octagon_r58 = FurnaceEngine.addOrReplaceChild("octagon_r58", CubeListBuilder.create().texOffs(81, 81).mirror().addBox(-1.5F, -7.3588F, 0.2249F, 4.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-86.8447F, -21.0878F, 38.45F, 0.0F, -1.5708F, 0.7854F));

		PartDefinition octagon_r59 = FurnaceEngine.addOrReplaceChild("octagon_r59", CubeListBuilder.create().texOffs(0, 84).mirror().addBox(-1.5F, 0.0F, 0.0F, 4.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-98.6997F, -19.4503F, 38.45F, 0.0F, -1.5708F, -2.3562F));

		PartDefinition octagon_r60 = FurnaceEngine.addOrReplaceChild("octagon_r60", CubeListBuilder.create().texOffs(86, 0).mirror().addBox(-1.5F, 0.0F, 0.0F, 3.0F, 2.0F, 7.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(-98.6997F, -26.4503F, 38.45F, 0.0F, -1.5708F, -1.5708F));

		PartDefinition octagon_r61 = FurnaceEngine.addOrReplaceChild("octagon_r61", CubeListBuilder.create().texOffs(85, 29).mirror().addBox(-1.5F, 0.0F, 0.0F, 4.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-93.75F, -31.4F, 38.45F, 0.0F, -1.5708F, -0.7854F));

		PartDefinition cube_r6 = FurnaceEngine.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(72, 88).mirror().addBox(-0.5F, -8.0F, -0.5F, 1.0F, 16.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-90.25F, -22.9528F, 38.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition octagon_r62 = FurnaceEngine.addOrReplaceChild("octagon_r62", CubeListBuilder.create().texOffs(32, 24).mirror().addBox(-7.05F, -14.0F, -3.4721F, 19.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-90.25F, -23.05F, 64.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition octagon_r63 = FurnaceEngine.addOrReplaceChild("octagon_r63", CubeListBuilder.create().texOffs(0, 65).mirror().addBox(-5.7929F, -7.2071F, 18.4471F, 12.0F, 12.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-68.3029F, -22.2076F, 55.95F, -1.5708F, -0.7854F, 1.5708F));

		PartDefinition octagon_r64 = FurnaceEngine.addOrReplaceChild("octagon_r64", CubeListBuilder.create().texOffs(0, 31).mirror().addBox(-7.05F, -8.9721F, -8.0F, 0.0F, 17.0F, 17.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-89.75F, -22.4F, 64.025F, 0.0F, -1.5708F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(PlaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		FurnaceEngine.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}