package xyz.przemyk.simpleplanes.upgrades.armor;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

public class LargeArmorModel extends EntityModel<PlaneEntity> {
	private final ModelPart Armor;

	public LargeArmorModel(ModelPart root) {
		this.Armor = root.getChild("Armor");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Armor = partdefinition.addOrReplaceChild("Armor", CubeListBuilder.create().texOffs(20, 42).addBox(5.0F, -13.0F, 82.5F, 1.0F, 18.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(27, 91).addBox(5.0F, -13.0F, 85.5F, 1.0F, 5.0F, 25.0F, new CubeDeformation(0.0F))
				.texOffs(43, 18).addBox(5.0F, -12.4F, 109.5F, 1.0F, 14.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 2).addBox(5.0F, -13.0F, 110.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.002F))
				.texOffs(102, 104).addBox(-10.0F, -4.0F, 6.5F, 1.0F, 9.0F, 21.0F, new CubeDeformation(0.0F))
				.texOffs(35, 50).addBox(-10.0F, -12.0F, 6.5F, 1.0F, 8.0F, 33.0F, new CubeDeformation(0.0F))
				.texOffs(0, 42).addBox(9.0F, -12.0F, 6.5F, 1.0F, 8.0F, 33.0F, new CubeDeformation(0.0F))
				.texOffs(58, 104).addBox(9.0F, -4.0F, 6.5F, 1.0F, 9.0F, 21.0F, new CubeDeformation(0.0F))
				.texOffs(37, 7).addBox(9.0F, -2.0F, 31.5F, 1.0F, 7.0F, 35.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-10.0F, -2.0F, 31.5F, 1.0F, 7.0F, 35.0F, new CubeDeformation(0.0F))
				.texOffs(37, 18).addBox(-6.0F, -12.4F, 109.5F, 1.0F, 14.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 83).addBox(-6.0F, -13.0F, 85.5F, 1.0F, 5.0F, 25.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-6.0F, -13.0F, 110.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.002F))
				.texOffs(26, 0).addBox(-6.0F, -13.0F, 82.5F, 1.0F, 18.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 15.0F, -18.5F));

		PartDefinition cube_r1 = Armor.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(74, 0).addBox(5.0F, 0.0F, -44.0F, 1.0F, 13.0F, 26.0F, new CubeDeformation(-0.001F))
				.texOffs(77, 65).addBox(-6.0F, 0.0F, -44.0F, 1.0F, 13.0F, 26.0F, new CubeDeformation(-0.001F)), PartPose.offsetAndRotation(0.0F, -13.6594F, 127.3627F, 0.1309F, 0.0F, 0.0F));

		PartDefinition cube_r2 = Armor.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 0).addBox(3.7851F, -9.5433F, -3.513F, 1.0F, 17.0F, 12.0F, new CubeDeformation(0.0001F))
				.texOffs(37, 0).addBox(-9.2149F, -9.5433F, -4.513F, 14.0F, 17.0F, 1.0F, new CubeDeformation(0.0001F)), PartPose.offsetAndRotation(0.1922F, -4.4576F, 1.7021F, 0.0F, 0.7854F, -3.1416F));

		PartDefinition cube_r3 = Armor.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 42).addBox(1.5F, -12.0F, -4.0F, 1.0F, 18.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.5F, -1.0F, 76.5F, 0.0F, 0.4538F, 0.0F));

		PartDefinition cube_r4 = Armor.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(70, 49).addBox(-2.5F, -12.0F, -4.0F, 1.0F, 18.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.5F, -1.0F, 76.5F, 0.0F, -0.4538F, 0.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(PlaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		Armor.render(poseStack, vertexConsumer, packedLight, packedOverlay);
	}
}