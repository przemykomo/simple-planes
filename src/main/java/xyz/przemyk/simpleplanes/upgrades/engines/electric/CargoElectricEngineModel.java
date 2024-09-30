package xyz.przemyk.simpleplanes.upgrades.engines.electric;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

public class CargoElectricEngineModel extends EntityModel<PlaneEntity> {

	private final ModelPart ElectricEngine;

	public CargoElectricEngineModel(ModelPart root) {
		this.ElectricEngine = root.getChild("ElectricEngine");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition ElectricEngine = partdefinition.addOrReplaceChild("ElectricEngine", CubeListBuilder.create(), PartPose.offset(-46.0F, 0.7236F, 59.2F));

		PartDefinition cube_r1 = ElectricEngine.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -5.2236F, -23.95F, 1.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(7, 0).addBox(11.0F, -5.2236F, -23.95F, 1.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 27).addBox(0.0F, -6.2236F, -23.95F, 12.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 31).addBox(0.0F, 2.7764F, -23.95F, 12.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 88).addBox(1.0F, -6.2236F, -21.95F, 10.0F, 10.0F, 30.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(142.3F, -1.0F, 2.35F, 0.0F, 0.0F, -3.1416F));

		PartDefinition cube_r2 = ElectricEngine.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(132, 99).addBox(1.0F, -8.2486F, -4.95F, 7.0F, 5.0F, 7.0F, new CubeDeformation(0.025F)), PartPose.offsetAndRotation(140.5222F, -18.025F, 21.2433F, 0.0F, -0.7854F, -3.1416F));

		PartDefinition cube_r3 = ElectricEngine.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 44).addBox(1.0F, -8.2236F, -35.95F, 10.0F, 5.0F, 38.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(142.3F, -18.0F, 21.35F, 0.0F, 0.0F, -3.1416F));

		PartDefinition cube_r4 = ElectricEngine.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(81, 0).addBox(-5.0F, 0.7764F, -9.95F, 23.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(129.3F, 8.0F, 2.85F, 0.0F, 0.0F, 2.6442F));

		PartDefinition cube_r5 = ElectricEngine.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(156, 87).addBox(-4.0F, -2.7764F, -9.95F, 18.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(129.3F, -6.4472F, 2.85F, 0.0F, 0.0F, -2.6616F));

		PartDefinition cube_r6 = ElectricEngine.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(51, 109).addBox(-14.0F, 0.7764F, -9.95F, 19.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(143.3F, 8.0F, 2.85F, 0.0F, 0.0F, -2.6616F));

		PartDefinition cube_r7 = ElectricEngine.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(0, 0).addBox(1.0F, -4.7764F, -15.95F, 3.0F, 11.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(138.8F, 9.4028F, 1.35F, 0.0F, 0.0F, 3.1416F));

		PartDefinition cube_r8 = ElectricEngine.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(45, 129).addBox(-1.5F, -12.0F, 0.0F, 3.0F, 3.0F, 24.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(136.3F, -13.5401F, -4.8508F, -0.3578F, 0.0F, -3.1416F));

		PartDefinition cube_r9 = ElectricEngine.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(0, 168).addBox(1.0F, -6.2236F, -20.95F, 3.0F, 10.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(138.8F, -14.1F, 1.35F, -0.7418F, 0.0F, -3.1416F));

		PartDefinition cube_r10 = ElectricEngine.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(0, 103).addBox(-2.0F, -3.2236F, -21.95F, 4.0F, 4.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(136.3F, -1.0F, 31.0F, 0.0F, 0.0F, -3.1416F));

		PartDefinition cube_r11 = ElectricEngine.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(0, 129).addBox(-3.0F, -3.2236F, -21.95F, 6.0F, 6.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(136.3F, 0.0F, 2.0F, 0.0F, 0.0F, -3.1416F));

		PartDefinition cube_r12 = ElectricEngine.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(59, 6).mirror().addBox(1.0F, -8.2236F, -35.95F, 10.0F, 5.0F, 38.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(98.0F, -18.0F, 21.35F, 0.0F, 0.0F, -3.1416F));

		PartDefinition cube_r13 = ElectricEngine.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(76, 135).mirror().addBox(1.0F, -8.2486F, -4.95F, 7.0F, 5.0F, 7.0F, new CubeDeformation(0.025F)).mirror(false), PartPose.offsetAndRotation(96.2222F, -18.025F, 21.2433F, 0.0F, -0.7854F, -3.1416F));

		PartDefinition cube_r14 = ElectricEngine.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(122, 142).mirror().addBox(-3.0F, -3.2236F, -21.95F, 6.0F, 6.0F, 32.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(92.0F, 0.0F, 0.0F, 0.0F, 0.0F, -3.1416F));

		PartDefinition cube_r15 = ElectricEngine.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(51, 104).mirror().addBox(-5.0F, 0.7764F, -9.95F, 23.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(85.0F, 8.0F, 0.85F, 0.0F, 0.0F, 2.6442F));

		PartDefinition cube_r16 = ElectricEngine.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(29, 173).mirror().addBox(-4.0F, -2.7764F, -9.95F, 18.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(85.0F, -6.4472F, 0.85F, 0.0F, 0.0F, -2.6616F));

		PartDefinition cube_r17 = ElectricEngine.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(80, 174).mirror().addBox(-14.0F, -2.7764F, -9.95F, 18.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(99.0F, -6.4472F, 0.85F, 0.0F, 0.0F, 2.6616F));

		PartDefinition cube_r18 = ElectricEngine.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(29, 168).mirror().addBox(-14.0F, 0.7764F, -9.95F, 19.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(99.0F, 8.0F, 0.85F, 0.0F, 0.0F, -2.6616F));

		PartDefinition cube_r19 = ElectricEngine.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(7, 44).mirror().addBox(0.0F, -5.2236F, -23.95F, 1.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(22, 44).mirror().addBox(11.0F, -5.2236F, -23.95F, 1.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(59, 77).mirror().addBox(0.0F, -6.2236F, -23.95F, 12.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(51, 114).mirror().addBox(0.0F, 2.7764F, -23.95F, 12.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(126, 41).mirror().addBox(1.0F, -6.2236F, -21.95F, 10.0F, 10.0F, 30.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(98.0F, -1.0F, 0.35F, 0.0F, 0.0F, -3.1416F));

		PartDefinition cube_r20 = ElectricEngine.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(59, 50).mirror().addBox(1.0F, -4.7764F, -15.95F, 3.0F, 11.0F, 15.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(94.5F, 9.4028F, -0.65F, 0.0F, 0.0F, 3.1416F));

		PartDefinition cube_r21 = ElectricEngine.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(102, 181).mirror().addBox(-1.5F, -12.0F, 0.0F, 3.0F, 3.0F, 24.0F, new CubeDeformation(-0.01F)).mirror(false), PartPose.offsetAndRotation(92.0F, -13.5401F, -6.8508F, -0.3578F, 0.0F, -3.1416F));

		PartDefinition cube_r22 = ElectricEngine.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(177, 19).mirror().addBox(1.0F, -6.2236F, -20.95F, 3.0F, 10.0F, 22.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(94.5F, -14.1F, -0.65F, -0.7418F, 0.0F, -3.1416F));

		PartDefinition cube_r23 = ElectricEngine.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(118, 50).mirror().addBox(-2.0F, -3.2236F, -21.95F, 4.0F, 4.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(92.0F, -1.0F, 29.0F, 0.0F, 0.0F, -3.1416F));

		PartDefinition cube_r24 = ElectricEngine.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-1.0F, -5.2236F, -23.95F, 1.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(7, 0).mirror().addBox(-12.0F, -5.2236F, -23.95F, 1.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 27).mirror().addBox(-12.0F, -6.2236F, -23.95F, 12.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 31).mirror().addBox(-12.0F, 2.7764F, -23.95F, 12.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 88).mirror().addBox(-11.0F, -6.2236F, -21.95F, 10.0F, 10.0F, 30.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-50.3F, -1.0F, 2.35F, 0.0F, 0.0F, 3.1416F));

		PartDefinition cube_r25 = ElectricEngine.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(132, 99).mirror().addBox(-8.0F, -8.2486F, -4.95F, 7.0F, 5.0F, 7.0F, new CubeDeformation(0.025F)).mirror(false), PartPose.offsetAndRotation(-48.5222F, -18.025F, 21.2433F, 0.0F, 0.7854F, 3.1416F));

		PartDefinition cube_r26 = ElectricEngine.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(0, 44).mirror().addBox(-11.0F, -8.2236F, -35.95F, 10.0F, 5.0F, 38.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-50.3F, -18.0F, 21.35F, 0.0F, 0.0F, 3.1416F));

		PartDefinition cube_r27 = ElectricEngine.addOrReplaceChild("cube_r27", CubeListBuilder.create().texOffs(81, 0).mirror().addBox(-18.0F, 0.7764F, -9.95F, 23.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-37.3F, 8.0F, 2.85F, 0.0F, 0.0F, -2.6442F));

		PartDefinition cube_r28 = ElectricEngine.addOrReplaceChild("cube_r28", CubeListBuilder.create().texOffs(156, 87).mirror().addBox(-14.0F, -2.7764F, -9.95F, 18.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-37.3F, -6.4472F, 2.85F, 0.0F, 0.0F, 2.6616F));

		PartDefinition cube_r29 = ElectricEngine.addOrReplaceChild("cube_r29", CubeListBuilder.create().texOffs(51, 109).mirror().addBox(-5.0F, 0.7764F, -9.95F, 19.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-51.3F, 8.0F, 2.85F, 0.0F, 0.0F, 2.6616F));

		PartDefinition cube_r30 = ElectricEngine.addOrReplaceChild("cube_r30", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-4.0F, -4.7764F, -15.95F, 3.0F, 11.0F, 15.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-46.8F, 9.4028F, 1.35F, 0.0F, 0.0F, -3.1416F));

		PartDefinition cube_r31 = ElectricEngine.addOrReplaceChild("cube_r31", CubeListBuilder.create().texOffs(45, 129).mirror().addBox(-1.5F, -12.0F, 0.0F, 3.0F, 3.0F, 24.0F, new CubeDeformation(-0.01F)).mirror(false), PartPose.offsetAndRotation(-44.3F, -13.5401F, -4.8508F, -0.3578F, 0.0F, 3.1416F));

		PartDefinition cube_r32 = ElectricEngine.addOrReplaceChild("cube_r32", CubeListBuilder.create().texOffs(0, 168).mirror().addBox(-4.0F, -6.2236F, -20.95F, 3.0F, 10.0F, 22.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-46.8F, -14.1F, 1.35F, -0.7418F, 0.0F, 3.1416F));

		PartDefinition cube_r33 = ElectricEngine.addOrReplaceChild("cube_r33", CubeListBuilder.create().texOffs(0, 103).mirror().addBox(-2.0F, -3.2236F, -21.95F, 4.0F, 4.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-44.3F, -1.0F, 31.0F, 0.0F, 0.0F, 3.1416F));

		PartDefinition cube_r34 = ElectricEngine.addOrReplaceChild("cube_r34", CubeListBuilder.create().texOffs(0, 129).mirror().addBox(-3.0F, -3.2236F, -21.95F, 6.0F, 6.0F, 32.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-44.3F, 0.0F, 2.0F, 0.0F, 0.0F, 3.1416F));

		PartDefinition cube_r35 = ElectricEngine.addOrReplaceChild("cube_r35", CubeListBuilder.create().texOffs(59, 6).addBox(-11.0F, -8.2236F, -35.95F, 10.0F, 5.0F, 38.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, -18.0F, 21.35F, 0.0F, 0.0F, 3.1416F));

		PartDefinition cube_r36 = ElectricEngine.addOrReplaceChild("cube_r36", CubeListBuilder.create().texOffs(76, 135).addBox(-8.0F, -8.2486F, -4.95F, 7.0F, 5.0F, 7.0F, new CubeDeformation(0.025F)), PartPose.offsetAndRotation(-4.2222F, -18.025F, 21.2433F, 0.0F, 0.7854F, 3.1416F));

		PartDefinition cube_r37 = ElectricEngine.addOrReplaceChild("cube_r37", CubeListBuilder.create().texOffs(122, 142).addBox(-3.0F, -3.2236F, -21.95F, 6.0F, 6.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 3.1416F));

		PartDefinition cube_r38 = ElectricEngine.addOrReplaceChild("cube_r38", CubeListBuilder.create().texOffs(51, 104).addBox(-18.0F, 0.7764F, -9.95F, 23.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.0F, 8.0F, 0.85F, 0.0F, 0.0F, -2.6442F));

		PartDefinition cube_r39 = ElectricEngine.addOrReplaceChild("cube_r39", CubeListBuilder.create().texOffs(29, 173).addBox(-14.0F, -2.7764F, -9.95F, 18.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.0F, -6.4472F, 0.85F, 0.0F, 0.0F, 2.6616F));

		PartDefinition cube_r40 = ElectricEngine.addOrReplaceChild("cube_r40", CubeListBuilder.create().texOffs(80, 174).addBox(-4.0F, -2.7764F, -9.95F, 18.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.0F, -6.4472F, 0.85F, 0.0F, 0.0F, -2.6616F));

		PartDefinition cube_r41 = ElectricEngine.addOrReplaceChild("cube_r41", CubeListBuilder.create().texOffs(29, 168).addBox(-5.0F, 0.7764F, -9.95F, 19.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.0F, 8.0F, 0.85F, 0.0F, 0.0F, 2.6616F));

		PartDefinition cube_r42 = ElectricEngine.addOrReplaceChild("cube_r42", CubeListBuilder.create().texOffs(7, 44).addBox(-1.0F, -5.2236F, -23.95F, 1.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(22, 44).addBox(-12.0F, -5.2236F, -23.95F, 1.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(59, 77).addBox(-12.0F, -6.2236F, -23.95F, 12.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(51, 114).addBox(-12.0F, 2.7764F, -23.95F, 12.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(126, 41).addBox(-11.0F, -6.2236F, -21.95F, 10.0F, 10.0F, 30.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, -1.0F, 0.35F, 0.0F, 0.0F, 3.1416F));

		PartDefinition cube_r43 = ElectricEngine.addOrReplaceChild("cube_r43", CubeListBuilder.create().texOffs(59, 50).addBox(-4.0F, -4.7764F, -15.95F, 3.0F, 11.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, 9.4028F, -0.65F, 0.0F, 0.0F, -3.1416F));

		PartDefinition cube_r44 = ElectricEngine.addOrReplaceChild("cube_r44", CubeListBuilder.create().texOffs(102, 181).addBox(-1.5F, -12.0F, 0.0F, 3.0F, 3.0F, 24.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(0.0F, -13.5401F, -6.8508F, -0.3578F, 0.0F, 3.1416F));

		PartDefinition cube_r45 = ElectricEngine.addOrReplaceChild("cube_r45", CubeListBuilder.create().texOffs(177, 19).addBox(-4.0F, -6.2236F, -20.95F, 3.0F, 10.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, -14.1F, -0.65F, -0.7418F, 0.0F, 3.1416F));

		PartDefinition cube_r46 = ElectricEngine.addOrReplaceChild("cube_r46", CubeListBuilder.create().texOffs(118, 50).addBox(-2.0F, -3.2236F, -21.95F, 4.0F, 4.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 29.0F, 0.0F, 0.0F, 3.1416F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(PlaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		ElectricEngine.render(poseStack, vertexConsumer, packedLight, packedOverlay);
	}
}