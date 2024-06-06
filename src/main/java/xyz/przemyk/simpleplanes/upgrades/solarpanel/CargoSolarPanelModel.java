package xyz.przemyk.simpleplanes.upgrades.solarpanel;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

public class CargoSolarPanelModel extends EntityModel<PlaneEntity> {

	private final ModelPart SolarPanel;

	public CargoSolarPanelModel(ModelPart root) {
		this.SolarPanel = root.getChild("SolarPanel");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition SolarPanel = partdefinition.addOrReplaceChild("SolarPanel", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition right_wing_panel_r1 = SolarPanel.addOrReplaceChild("right_wing_panel_r1", CubeListBuilder.create().texOffs(0, 14).mirror().addBox(-11.5F, -0.5F, 8.5F, 37.0F, 1.0F, 13.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.5F, -37.5F, 59.4F, 0.0F, -1.5708F, 0.0F));

		PartDefinition left_wing_panel_r1 = SolarPanel.addOrReplaceChild("left_wing_panel_r1", CubeListBuilder.create().texOffs(0, 14).addBox(-25.5F, -0.5F, 8.5F, 37.0F, 1.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -37.5F, 59.4F, 0.0F, 1.5708F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(PlaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		SolarPanel.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}