package xyz.przemyk.simpleplanes.upgrades.armor;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

public class HeliArmorModel extends EntityModel<PlaneEntity> {
	private final ModelPart Armor;

	public HeliArmorModel(ModelPart root) {
		this.Armor = root.getChild("Armor");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Armor = partdefinition.addOrReplaceChild("Armor", CubeListBuilder.create(), PartPose.offset(0.0F, 15.0F, -18.5F));

		PartDefinition cube_r1 = Armor.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(48, 68).addBox(-10.0F, -20.0F, -4.0F, 1.0F, 17.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(0, 55).addBox(-10.0F, -3.1F, -4.0F, 1.0F, 8.0F, 2.0F, new CubeDeformation(-0.001F))
				.texOffs(24, 90).addBox(-10.0F, 1.8F, -3.0F, 1.0F, 12.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(92, 49).addBox(-10.0F, 19.0F, -3.2F, 1.0F, 7.0F, 6.0F, new CubeDeformation(0.009F))
				.texOffs(0, 0).addBox(-10.0F, 26.0F, -3.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(64, 68).addBox(9.0F, -20.0F, -4.0F, 1.0F, 17.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(37, 55).addBox(9.0F, -3.1F, -4.0F, 1.0F, 8.0F, 2.0F, new CubeDeformation(-0.001F))
				.texOffs(0, 4).addBox(9.0F, 26.0F, -3.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(72, 92).addBox(9.0F, 19.0F, -3.2F, 1.0F, 7.0F, 6.0F, new CubeDeformation(0.009F))
				.texOffs(38, 90).addBox(9.0F, 1.8F, -3.0F, 1.0F, 12.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 7.5F, -1.5708F, 0.0F, 0.0F));

		PartDefinition cube_r2 = Armor.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(95, 62).addBox(-1.5F, -11.6F, 13.1F, 3.0F, 7.0F, 3.0F, new CubeDeformation(0.006F))
				.texOffs(96, 94).addBox(15.502F, -11.6F, 13.1F, 3.0F, 7.0F, 3.0F, new CubeDeformation(0.006F)), PartPose.offsetAndRotation(-8.501F, -17.8061F, -15.3402F, -2.3562F, 0.0F, 0.0F));

		PartDefinition cube_r3 = Armor.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(80, 68).addBox(-11.0F, 5.0F, -11.0F, 3.0F, 4.0F, 9.0F, new CubeDeformation(-0.191F))
				.texOffs(80, 81).addBox(6.4F, 5.0F, -11.0F, 3.0F, 4.0F, 9.0F, new CubeDeformation(-0.191F))
				.texOffs(0, 0).addBox(6.4F, 4.0F, -11.0F, 1.0F, 5.0F, 9.0F, new CubeDeformation(-0.192F))
				.texOffs(0, 41).addBox(-9.0F, 4.0F, -11.0F, 16.0F, 5.0F, 9.0F, new CubeDeformation(-0.193F)), PartPose.offsetAndRotation(0.8F, -1.0F, -14.7F, -1.5708F, 0.0F, 0.0F));

		PartDefinition cube_r4 = Armor.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 90).addBox(-10.0F, -20.85F, -7.5F, 3.0F, 20.0F, 3.0F, new CubeDeformation(0.006F))
				.texOffs(12, 90).addBox(7.002F, -20.85F, -7.5F, 3.0F, 20.0F, 3.0F, new CubeDeformation(0.006F)), PartPose.offsetAndRotation(-0.001F, -5.8913F, -18.7697F, -0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r5 = Armor.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(94, 12).addBox(-10.0F, -21.45F, -19.4F, 2.0F, 12.0F, 3.0F, new CubeDeformation(0.006F))
				.texOffs(52, 92).addBox(-10.0F, -38.25F, -19.4F, 2.0F, 17.0F, 3.0F, new CubeDeformation(0.002F))
				.texOffs(62, 92).addBox(7.998F, -38.25F, -19.4F, 2.0F, 17.0F, 3.0F, new CubeDeformation(0.002F))
				.texOffs(86, 94).addBox(7.998F, -21.45F, -19.4F, 2.0F, 12.0F, 3.0F, new CubeDeformation(0.006F)), PartPose.offsetAndRotation(0.001F, -6.599F, -18.801F, -1.5708F, 0.0F, 0.0F));

		PartDefinition cube_r6 = Armor.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 55).addBox(-10.0F, 3.8F, -14.0F, 1.0F, 24.0F, 11.0F, new CubeDeformation(0.0F))
				.texOffs(24, 55).addBox(9.012F, 3.8F, -14.0F, 1.0F, 24.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.006F, -0.994F, 7.256F, -1.5708F, 0.0F, 0.0F));

		PartDefinition cube_r7 = Armor.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(13, 55).addBox(-10.0F, -22.45F, -16.5F, 3.0F, 3.0F, 8.0F, new CubeDeformation(0.006F))
				.texOffs(86, 28).addBox(7.0F, -22.45F, -16.5F, 3.0F, 3.0F, 8.0F, new CubeDeformation(0.006F)), PartPose.offsetAndRotation(0.0F, -6.5F, -19.0F, -1.5708F, 0.0F, 0.0F));

		PartDefinition cube_r8 = Armor.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(48, 55).addBox(-8.0F, -4.0F, -2.96F, 16.0F, 7.0F, 6.0F, new CubeDeformation(-0.003F)), PartPose.offsetAndRotation(0.0F, -3.2179F, -19.24F, -2.3562F, 0.0F, 0.0F));

		PartDefinition cube_r9 = Armor.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(61, 0).addBox(-8.0F, -6.95F, -6.5F, 16.0F, 6.0F, 6.0F, new CubeDeformation(-0.003F)), PartPose.offsetAndRotation(0.0F, -6.55F, -19.55F, -0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r10 = Armor.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(61, 12).addBox(-8.0F, -11.25F, -19.4F, 16.0F, 1.0F, 1.0F, new CubeDeformation(0.006F)), PartPose.offsetAndRotation(-0.001F, -6.599F, -19.501F, -1.5708F, 0.0F, 0.0F));

		PartDefinition cube_r11 = Armor.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(61, 14).addBox(-7.0F, 0.0F, 0.0F, 14.0F, 2.0F, 1.0F, new CubeDeformation(0.006F)), PartPose.offsetAndRotation(-0.001F, -25.999F, -9.251F, -0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r12 = Armor.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(0, 0).addBox(-10.0F, -38.25F, -19.5F, 20.0F, 2.0F, 21.0F, new CubeDeformation(0.006F)), PartPose.offsetAndRotation(0.0F, -6.5F, -18.8F, -1.5708F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(PlaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		Armor.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}