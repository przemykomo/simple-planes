package xyz.przemyk.simpleplanes.upgrades.seats;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

public class WoodenCargoSeatsModel extends EntityModel<PlaneEntity> {

	private final ModelPart Seat;

	public WoodenCargoSeatsModel(ModelPart root) {
		this.Seat = root.getChild("Seat");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Seat = partdefinition.addOrReplaceChild("Seat", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition cube_r1 = Seat.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 10).addBox(-11.391F, -9.5F, -4.8561F, 11.0F, 16.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -20.5F, -41.8308F, 0.0F, -0.1571F, 0.0F));

		PartDefinition cube_r2 = Seat.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 10).addBox(0.391F, -9.5F, -4.8561F, 11.0F, 16.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -20.5F, -41.8308F, 0.0F, 0.1571F, 0.0F));

		PartDefinition cube_r3 = Seat.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(14, 0).addBox(-1.1F, 0.0F, -11.0F, 14.0F, 7.0F, 11.0F, new CubeDeformation(-0.11F)), PartPose.offsetAndRotation(-5.9F, -30.1329F, -48.583F, 0.829F, 0.0F, 0.0F));

		PartDefinition cube_r4 = Seat.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 4).addBox(-13.4768F, -20.5F, -26.313F, 18.0F, 20.0F, 27.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -7.2F, 134.5139F, 0.0875F, 0.0695F, 0.0061F));

		PartDefinition cube_r5 = Seat.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(8, 20).addBox(-4.5232F, -20.5F, -26.313F, 18.0F, 20.0F, 27.0F, new CubeDeformation(-0.011F)), PartPose.offsetAndRotation(0.0F, -7.2F, 134.5139F, 0.0875F, -0.0695F, -0.0061F));

		return LayerDefinition.create(meshdefinition, 16, 16);
	}

	@Override
	public void setupAnim(PlaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		Seat.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}