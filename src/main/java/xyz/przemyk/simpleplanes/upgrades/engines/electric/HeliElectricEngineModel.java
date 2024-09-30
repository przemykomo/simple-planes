package xyz.przemyk.simpleplanes.upgrades.engines.electric;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

public class HeliElectricEngineModel extends EntityModel<PlaneEntity> {
	private final ModelPart ElectricalEngine;

	public HeliElectricEngineModel(ModelPart root) {
		this.ElectricalEngine = root.getChild("ElectricalEngine");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition ElectricalEngine = partdefinition.addOrReplaceChild("ElectricalEngine", CubeListBuilder.create().texOffs(36, 40).addBox(2.5F, -35.9F, 8.1F, 6.0F, 8.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(0, 28).addBox(3.45F, -34.9F, 7.1F, 4.0F, 6.0F, 22.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-7.5F, -34.9F, 7.1F, 4.0F, 6.0F, 22.0F, new CubeDeformation(0.0F))
		.texOffs(36, 12).addBox(-8.5F, -35.9F, 8.1F, 6.0F, 8.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -37.0F, -6.5F, 0.0F, 0.0F, -3.1416F));

		PartDefinition bone = ElectricalEngine.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(30, 36).addBox(15.5F, -29.8F, 17.5F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(9, 33).addBox(15.5F, -29.8F, 13.5F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(30, 8).addBox(15.5F, -29.8F, 9.5F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(30, 0).addBox(15.5F, -29.8F, 5.5F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(0, 28).addBox(2.5F, -29.8F, 5.5F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(9, 13).addBox(2.5F, -29.8F, 9.5F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(0, 8).addBox(2.5F, -29.8F, 13.5F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(2.5F, -29.8F, 17.5F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-10.5F, 1.9F, 3.1F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		ElectricalEngine.render(poseStack, vertexConsumer, packedLight, packedOverlay);
	}

	@Override
	public void setupAnim(PlaneEntity p_102618_, float p_102619_, float p_102620_, float p_102621_, float p_102622_, float p_102623_) {}
}