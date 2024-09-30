package xyz.przemyk.simpleplanes.upgrades.armor;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

public class CargoArmorModel extends EntityModel<PlaneEntity> {

	private final ModelPart Armor;

	public CargoArmorModel(ModelPart root) {
		this.Armor = root.getChild("Armor");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Armor = partdefinition.addOrReplaceChild("Armor", CubeListBuilder.create().texOffs(154, 113).addBox(-18.0F, -37.0F, 46.0F, 36.0F, 3.0F, 6.0F, new CubeDeformation(0.04F))
		.texOffs(46, 97).addBox(17.8313F, -7.113F, -2.2265F, 1.0F, 6.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(0, 207).addBox(17.8313F, -7.113F, 4.7735F, 1.0F, 6.0F, 25.0F, new CubeDeformation(0.0F))
		.texOffs(40, 18).addBox(-9.8313F, -19.363F, 203.2735F, 1.0F, 7.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(24, 18).addBox(8.8313F, -19.363F, 203.2735F, 1.0F, 7.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(24, 75).addBox(-18.8313F, -7.113F, -2.2265F, 1.0F, 6.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(203, 203).addBox(-18.8313F, -7.113F, 4.7735F, 1.0F, 6.0F, 25.0F, new CubeDeformation(0.0F))
		.texOffs(124, 146).addBox(-16.8313F, -0.113F, 0.7735F, 9.0F, 1.0F, 56.0F, new CubeDeformation(0.0F))
		.texOffs(0, 18).addBox(-5.8313F, -0.113F, 0.7735F, 23.0F, 1.0F, 56.0F, new CubeDeformation(0.0F))
		.texOffs(63, 146).addBox(-15.8313F, -0.113F, 58.7735F, 32.0F, 1.0F, 12.0F, new CubeDeformation(0.0F))
		.texOffs(92, 75).addBox(26.9687F, 0.487F, 83.5235F, 6.0F, 3.0F, 6.0F, new CubeDeformation(0.02F))
		.texOffs(190, 234).addBox(26.9687F, -4.513F, 71.5235F, 6.0F, 8.0F, 13.0F, new CubeDeformation(0.0F))
		.texOffs(190, 234).addBox(-33.0313F, -4.513F, 71.5235F, 6.0F, 8.0F, 13.0F, new CubeDeformation(0.0F))
		.texOffs(92, 75).addBox(-33.0313F, 0.487F, 83.5235F, 6.0F, 3.0F, 6.0F, new CubeDeformation(0.02F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition cube_r1 = Armor.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(92, 93).addBox(-13.5F, -6.5F, 13.5F, 9.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -12.8761F, 198.5789F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r2 = Armor.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(10, 18).addBox(10.5F, -6.5F, 13.5F, 4.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -12.8761F, 198.5789F, 0.0F, -0.7854F, 0.0F));

		PartDefinition cube_r3 = Armor.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(63, 183).addBox(-12.5F, 0.5F, 0.5F, 12.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0429F, -11.6261F, 199.4289F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r4 = Armor.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 70).addBox(-2.609F, 1.5F, 16.1439F, 13.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(171, 36).addBox(1.391F, 1.5F, 19.1439F, 9.0F, 1.0F, 23.0F, new CubeDeformation(0.0F))
		.texOffs(154, 89).addBox(-1.609F, 1.5F, -9.8561F, 12.0F, 1.0F, 23.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, -41.8308F, -0.0883F, 0.1565F, -0.0138F));

		PartDefinition cube_r5 = Armor.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 128).addBox(-10.391F, 1.5F, 16.1439F, 13.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(110, 160).addBox(-10.391F, 1.5F, 19.1439F, 9.0F, 1.0F, 23.0F, new CubeDeformation(0.0F))
		.texOffs(63, 159).addBox(-10.391F, 1.5F, -9.8561F, 12.0F, 1.0F, 23.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, -5.0F, -41.8308F, -0.0883F, -0.1565F, 0.0138F));

		PartDefinition cube_r6 = Armor.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(225, 99).addBox(-2.1F, 0.2F, -21.0F, 4.0F, 1.0F, 23.0F, new CubeDeformation(0.155F))
		.texOffs(92, 108).addBox(-2.1F, -1.5F, -21.0F, 4.0F, 2.0F, 23.0F, new CubeDeformation(0.16F)), PartPose.offsetAndRotation(148.6629F, -35.35F, 68.1742F, 0.0F, 0.7418F, 0.0F));

		PartDefinition cube_r7 = Armor.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(40, 75).addBox(-3.0F, -7.037F, -4.0F, 6.0F, 7.0F, 4.0F, new CubeDeformation(0.01F))
		.texOffs(40, 75).addBox(57.0F, -7.037F, -4.0F, 6.0F, 7.0F, 4.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-30.0313F, 0.487F, 89.5235F, 0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r8 = Armor.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(0, 0).addBox(-18.0F, -3.0F, 1.0F, 118.0F, 3.0F, 6.0F, new CubeDeformation(0.02F)), PartPose.offsetAndRotation(36.0265F, -34.0F, 45.7861F, 0.0F, -0.0436F, 0.0F));

		PartDefinition cube_r9 = Armor.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(212, 37).addBox(31.8861F, -1.4315F, 6.815F, 4.0F, 2.0F, 23.0F, new CubeDeformation(0.11F))
		.texOffs(225, 163).addBox(31.8861F, 0.1685F, 6.815F, 4.0F, 1.0F, 23.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(72.7185F, -9.2685F, 54.4557F, 0.0F, 0.7418F, -0.0524F));

		PartDefinition cube_r10 = Armor.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(102, 18).addBox(-56.7058F, -1.6315F, -20.7881F, 87.0F, 3.0F, 6.0F, new CubeDeformation(-0.08F)), PartPose.offsetAndRotation(72.7185F, -9.2685F, 54.4557F, 0.0F, -0.0436F, -0.0524F));

		PartDefinition cube_r11 = Armor.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(225, 123).addBox(-1.9F, 0.2F, -21.0F, 4.0F, 1.0F, 23.0F, new CubeDeformation(0.155F))
		.texOffs(27, 207).addBox(-1.9F, -1.5F, -21.0F, 4.0F, 2.0F, 23.0F, new CubeDeformation(0.16F)), PartPose.offsetAndRotation(-148.6629F, -35.35F, 68.1742F, 0.0F, -0.7418F, 0.0F));

		PartDefinition cube_r12 = Armor.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(0, 0).addBox(-13.4768F, -1.5F, -39.313F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(24, 75).mirror().addBox(-13.4768F, -18.5F, -42.313F, 1.0F, 8.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(233, 227).mirror().addBox(-13.4768F, -18.5F, -66.313F, 1.0F, 8.0F, 22.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(99, 203).mirror().addBox(-13.4768F, -9.5F, -62.313F, 1.0F, 8.0F, 25.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 75).mirror().addBox(-13.4768F, -18.5F, 29.687F, 1.0F, 9.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(198, 163).mirror().addBox(-13.4768F, -18.5F, 2.687F, 1.0F, 8.0F, 25.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(91, 75).mirror().addBox(-13.4768F, -9.5F, 2.687F, 1.0F, 10.0F, 61.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.0F, -7.2F, 134.5139F, 0.0875F, 0.0695F, 0.0061F));

		PartDefinition cube_r13 = Armor.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(233, 227).addBox(12.4768F, -18.5F, -66.313F, 1.0F, 8.0F, 22.0F, new CubeDeformation(0.0F))
		.texOffs(99, 203).addBox(12.4768F, -9.5F, -62.313F, 1.0F, 8.0F, 25.0F, new CubeDeformation(0.0F))
		.texOffs(24, 75).addBox(12.4768F, -18.5F, -42.313F, 1.0F, 8.0F, 14.0F, new CubeDeformation(0.0F))
		.texOffs(0, 9).addBox(12.4768F, -1.5F, -39.313F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 75).addBox(12.4768F, -18.5F, 29.687F, 1.0F, 9.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(91, 75).addBox(12.4768F, -9.5F, 2.687F, 1.0F, 10.0F, 61.0F, new CubeDeformation(0.0F))
		.texOffs(198, 163).addBox(12.4768F, -18.5F, 2.687F, 1.0F, 8.0F, 25.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -7.2F, 134.5139F, 0.0875F, -0.0695F, -0.0061F));

		PartDefinition cube_r14 = Armor.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(0, 66).addBox(-12.0F, -1.0F, 23.0F, 24.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(0, 75).addBox(-8.0F, -1.0F, 64.0F, 16.0F, 1.0F, 60.0F, new CubeDeformation(0.0F))
		.texOffs(102, 55).addBox(-12.0F, -1.0F, 46.0F, 24.0F, 1.0F, 18.0F, new CubeDeformation(0.0F))
		.texOffs(102, 36).addBox(-14.0F, -1.0F, 26.0F, 28.0F, 1.0F, 18.0F, new CubeDeformation(0.0F))
		.texOffs(154, 122).addBox(-16.0F, -1.0F, 0.0F, 32.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(154, 74).addBox(-13.0F, -1.0F, 7.0F, 26.0F, 1.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.1687F, 0.887F, 70.7735F, 0.0873F, 0.0F, 0.0F));

		PartDefinition cube_r15 = Armor.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(0, 33).addBox(-1.2F, -1.837F, 1.0715F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.2F))
		.texOffs(0, 123).addBox(-9.2F, -1.837F, -21.0F, 18.0F, 2.0F, 3.0F, new CubeDeformation(-0.2F))
		.texOffs(168, 62).addBox(-13.2F, -1.1F, 7.0F, 26.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.1687F, -1.613F, -32.7265F, -0.0873F, 0.0F, 0.0F));

		PartDefinition cube_r16 = Armor.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(126, 203).addBox(-0.5F, -2.0F, 0.0F, 1.0F, 2.0F, 22.0F, new CubeDeformation(0.0F))
		.texOffs(209, 64).addBox(-0.5F, -3.0F, -30.0F, 1.0F, 3.0F, 25.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-14.8959F, -3.0F, -23.795F, -0.0883F, -0.1565F, 0.0138F));

		PartDefinition cube_r17 = Armor.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(0, 18).addBox(-12.391F, -8.5F, 20.1439F, 1.0F, 26.0F, 22.0F, new CubeDeformation(0.0F))
		.texOffs(0, 136).addBox(-12.391F, 9.9F, -9.7561F, 1.0F, 5.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -20.5F, -41.8308F, 0.0F, -0.1571F, 0.0F));

		PartDefinition cube_r18 = Armor.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(0, 9).addBox(-100.0F, -3.0F, 1.0F, 118.0F, 3.0F, 6.0F, new CubeDeformation(0.02F)), PartPose.offsetAndRotation(-36.0265F, -34.0F, 45.7861F, 0.0F, 0.0436F, 0.0F));

		PartDefinition cube_r19 = Armor.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(0, 169).addBox(-0.5F, -3.0F, -30.0F, 1.0F, 3.0F, 25.0F, new CubeDeformation(0.0F))
		.texOffs(178, 203).addBox(-0.5F, -2.0F, 0.0F, 1.0F, 2.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(14.8959F, -3.0F, -23.795F, -0.0883F, 0.1565F, -0.0138F));

		PartDefinition cube_r20 = Armor.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(92, 75).addBox(11.391F, 9.9F, -9.7561F, 1.0F, 5.0F, 28.0F, new CubeDeformation(0.0F))
		.texOffs(0, 75).addBox(11.391F, -8.5F, 20.1439F, 1.0F, 26.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -20.5F, -41.8308F, 0.0F, 0.1571F, 0.0F));

		PartDefinition cube_r21 = Armor.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(16, 31).addBox(-1.0F, -3.5F, 0.0F, 1.0F, 7.0F, 2.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(9.8313F, -15.863F, 209.2735F, 0.0F, -0.7854F, 0.0F));

		PartDefinition cube_r22 = Armor.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(0, 18).addBox(0.0F, -3.5F, 0.0F, 1.0F, 7.0F, 8.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(-9.8313F, -15.863F, 209.2735F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r23 = Armor.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(58, 213).addBox(-35.8861F, -1.4315F, 6.815F, 4.0F, 2.0F, 23.0F, new CubeDeformation(0.11F))
		.texOffs(230, 203).addBox(-35.8861F, 0.1685F, 6.815F, 4.0F, 1.0F, 23.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-72.7185F, -9.2685F, 54.4557F, 0.0F, -0.7418F, 0.0524F));

		PartDefinition cube_r24 = Armor.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(102, 27).addBox(-30.2942F, -1.6315F, -20.7881F, 87.0F, 3.0F, 6.0F, new CubeDeformation(-0.08F)), PartPose.offsetAndRotation(-72.7185F, -9.2685F, 54.4557F, 0.0F, 0.0436F, 0.0524F));

		return LayerDefinition.create(meshdefinition, 512, 512);
	}

	@Override
	public void setupAnim(PlaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		Armor.render(poseStack, vertexConsumer, packedLight, packedOverlay);
	}
}