package xyz.przemyk.simpleplanes.upgrades.floating;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

public class WoodenCargoFloatingModel extends EntityModel<PlaneEntity> {

	private final ModelPart Pontoonswoodensegment;

	public WoodenCargoFloatingModel(ModelPart root) {
		this.Pontoonswoodensegment = root.getChild("Pontoonswoodensegment");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Pontoonswoodensegment = partdefinition.addOrReplaceChild("Pontoonswoodensegment", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition cube_r1 = Pontoonswoodensegment.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(1, 14).mirror().addBox(-22.0F, -7.0F, -8.5F, 47.0F, 14.0F, 18.0F, new CubeDeformation(0.011F)).mirror(false), PartPose.offsetAndRotation(5.1754F, 5.2115F, -20.9292F, -2.6317F, 1.3913F, -2.6385F));

		PartDefinition cube_r2 = Pontoonswoodensegment.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(1, 14).addBox(-25.0F, -7.0F, -8.5F, 47.0F, 14.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.1754F, 5.2115F, -20.9292F, -2.6317F, -1.3913F, 2.6385F));

		PartDefinition cube_r3 = Pontoonswoodensegment.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(111, 111).mirror().addBox(-9.0F, -7.0F, -9.0F, 130.0F, 14.0F, 18.0F, new CubeDeformation(-0.011F)).mirror(false), PartPose.offsetAndRotation(8.3941F, 5.5691F, 78.2363F, 1.9153F, -1.3667F, -1.9221F));

		PartDefinition cube_r4 = Pontoonswoodensegment.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(111, 111).addBox(-121.0F, -7.0F, -9.0F, 130.0F, 14.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.3941F, 5.5691F, 78.2363F, 1.9153F, 1.3667F, 1.9221F));

		PartDefinition cube_r5 = Pontoonswoodensegment.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(4, 14).addBox(-55.0F, -7.0F, -17.5F, 73.0F, 14.0F, 36.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 6.9F, 16.0F, 0.0F, 1.5708F, 0.0F));

		return LayerDefinition.create(meshdefinition, 16, 16);
	}

	@Override
	public void setupAnim(PlaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		Pontoonswoodensegment.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}