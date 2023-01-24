package xyz.przemyk.simpleplanes.upgrades.seats;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

public class WoodenSeatsModel extends EntityModel<PlaneEntity> {
	private final ModelPart Seats;

	public WoodenSeatsModel(ModelPart root) {
		this.Seats = root.getChild("Seats");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Seats = partdefinition.addOrReplaceChild("Seats", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition cube_r1 = Seats.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, 8.6667F, -6.6667F, 10.0F, 2.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(14.0F, -12.6667F, 24.6667F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r2 = Seats.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 16).addBox(-5.0F, 8.6667F, -6.6667F, 10.0F, 2.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-14.0F, -12.6667F, 24.6667F, 0.0F, 1.5708F, 0.0F));

		return LayerDefinition.create(meshdefinition, 16, 16);
	}

	@Override
	public void setupAnim(PlaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		Seats.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}