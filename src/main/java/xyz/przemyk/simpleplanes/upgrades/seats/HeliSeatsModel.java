package xyz.przemyk.simpleplanes.upgrades.seats;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

public class HeliSeatsModel extends EntityModel<PlaneEntity> {
	private final ModelPart Seats;

	public HeliSeatsModel(ModelPart root) {
		this.Seats = root.getChild("Seats");
	}


	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Seats = partdefinition.addOrReplaceChild("Seats", CubeListBuilder.create().texOffs(8, 24).addBox(-8.0F, -8.0F, -24.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 24).addBox(-8.0F, -8.0F, -16.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 12).addBox(6.0F, -8.0F, -16.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(6.0F, -8.0F, -24.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(PlaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		Seats.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}