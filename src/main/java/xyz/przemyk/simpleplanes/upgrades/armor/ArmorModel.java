package xyz.przemyk.simpleplanes.upgrades.armor;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

public class ArmorModel extends EntityModel<PlaneEntity> {
	private final ModelPart Armor;

	public ArmorModel(ModelPart root) {
		this.Armor = root.getChild("Armor");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		partdefinition.addOrReplaceChild("Armor", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, -6.0F, -16.0F, 14.0F, 1.0F, 34.0F, new CubeDeformation(0.0F))
				.texOffs(36, 48).addBox(-10.0F, -20.0F, -16.0F, 1.0F, 13.0F, 34.0F, new CubeDeformation(0.0F))
				.texOffs(0, 35).addBox(9.0F, -20.0F, -16.0F, 1.0F, 13.0F, 34.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-8.0F, -20.0F, -19.0F, 16.0F, 13.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(6, 14).addBox(8.0F, -20.0F, -18.0F, 1.0F, 13.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 14).addBox(-9.0F, -20.0F, -18.0F, 1.0F, 13.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(36, 36).addBox(-8.0F, -7.0F, -18.0F, 16.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(72, 36).addBox(-9.0F, -7.0F, -16.0F, 2.0F, 1.0F, 34.0F, new CubeDeformation(0.0F))
				.texOffs(62, 1).addBox(7.0F, -7.0F, -16.0F, 2.0F, 1.0F, 34.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(PlaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		Armor.render(poseStack, buffer, packedLight, packedOverlay);
	}
}