package xyz.przemyk.simpleplanes.client.render.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

public class HelicopterMetalModel extends EntityModel<PlaneEntity> {
	private final ModelPart Parts;

	public HelicopterMetalModel(ModelPart root) {
		this.Parts = root.getChild("Parts");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Parts = partdefinition.addOrReplaceChild("Parts", CubeListBuilder.create().texOffs(0, 17).addBox(-5.0F, -10.0F, -33.0F, 10.0F, 1.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(24, 55).addBox(-5.0F, -25.0F, -19.0F, 10.0F, 15.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 25).addBox(-3.0F, -31.0F, -19.0F, 6.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-5.0F, -10.0F, -16.0F, 10.0F, 1.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(0, 55).addBox(-5.0F, -25.0F, -2.0F, 10.0F, 15.0F, 2.0F, new CubeDeformation(-0.01F))
		.texOffs(0, 17).addBox(-3.0F, -31.0F, -2.0F, 6.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 55).addBox(-16.0F, -2.5F, -38.0F, 4.0F, 2.0F, 53.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(12.0F, -2.5F, -38.0F, 4.0F, 2.0F, 53.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition cube_r1 = Parts.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(16, 34).addBox(-7.0F, -11.25F, -19.4F, 14.0F, 1.0F, 1.0F, new CubeDeformation(0.001F))
		.texOffs(61, 0).addBox(-7.0F, -36.25F, -19.4F, 14.0F, 2.0F, 2.0F, new CubeDeformation(0.001F))
		.texOffs(61, 4).addBox(-7.0F, -21.25F, -19.4F, 14.0F, 2.0F, 2.0F, new CubeDeformation(0.001F))
		.texOffs(61, 8).addBox(7.0F, -36.25F, -19.4F, 2.0F, 26.0F, 2.0F, new CubeDeformation(0.001F))
		.texOffs(61, 55).addBox(-9.0F, -36.25F, -19.4F, 2.0F, 26.0F, 2.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-0.001F, -15.599F, -37.301F, -1.5708F, 0.0F, 0.0F));

		PartDefinition cube_r2 = Parts.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 50).addBox(-7.0F, 0.0F, 0.0F, 14.0F, 1.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-0.001F, -34.999F, -27.051F, -0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r3 = Parts.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(32, 36).addBox(7.0F, -21.25F, -17.5F, 2.0F, 2.0F, 8.0F, new CubeDeformation(0.001F))
		.texOffs(69, 13).addBox(-9.0F, -21.25F, -17.5F, 2.0F, 2.0F, 8.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -15.5F, -37.3F, -1.5708F, 0.0F, 0.0F));

		PartDefinition cube_r4 = Parts.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 34).addBox(7.0F, 0.0F, 2.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(-0.0012F))
		.texOffs(16, 36).addBox(-9.0F, 0.0F, 2.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(-0.0012F)), PartPose.offsetAndRotation(0.0F, -35.0F, -1.05F, -1.5533F, 0.0F, 0.0F));

		PartDefinition cube_r5 = Parts.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 0).addBox(7.0F, -20.95F, -6.5F, 2.0F, 14.0F, 2.0F, new CubeDeformation(-0.002F))
		.texOffs(8, 0).addBox(-9.002F, -20.95F, -6.5F, 2.0F, 14.0F, 2.0F, new CubeDeformation(-0.002F)), PartPose.offsetAndRotation(0.001F, -15.5913F, -37.2697F, -0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r6 = Parts.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(61, 36).addBox(0.0F, 0.0F, -1.5F, 12.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(61, 41).addBox(0.0F, 0.0F, 15.5F, 12.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.0F, -11.7F, -27.5F, 0.0F, 0.0F, 1.0036F));

		PartDefinition cube_r7 = Parts.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(61, 46).addBox(-12.0F, 0.0F, -1.5F, 12.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(69, 8).addBox(-12.0F, 0.0F, 15.5F, 12.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.0F, -11.7F, -27.5F, 0.0F, 0.0F, -1.0036F));

		PartDefinition cube_r8 = Parts.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(0, 110).addBox(-1.5F, 3.0F, 1.0F, 3.0F, 59.0F, 3.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(0.0F, -31.5F, 70.0F, -1.2828F, 0.0F, 0.0F));

		PartDefinition cube_r9 = Parts.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(36, 0).addBox(2.0F, -25.0F, -6.0F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -25.0F, 44.5F, -1.5708F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		Parts.render(poseStack, vertexConsumer, packedLight, packedOverlay);
	}

	@Override
	public void setupAnim(PlaneEntity p_102618_, float p_102619_, float p_102620_, float p_102621_, float p_102622_, float p_102623_) {}
}