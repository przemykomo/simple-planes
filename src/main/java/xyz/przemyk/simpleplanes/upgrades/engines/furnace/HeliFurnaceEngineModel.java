package xyz.przemyk.simpleplanes.upgrades.engines.furnace;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

public class HeliFurnaceEngineModel extends EntityModel<PlaneEntity> {
	private final ModelPart FurnaceEngine;

	public HeliFurnaceEngineModel(ModelPart root) {
		this.FurnaceEngine = root.getChild("FurnaceEngine");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition FurnaceEngine = partdefinition.addOrReplaceChild("FurnaceEngine", CubeListBuilder.create(), PartPose.offset(0.0F, -17.0F, -47.5F));

		PartDefinition cube_r1 = FurnaceEngine.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 29).addBox(-3.3159F, -3.5F, -15.5143F, 7.0F, 7.0F, 21.0F, new CubeDeformation(-0.18F))
		.texOffs(41, 42).addBox(-3.8159F, -4.0F, -13.5143F, 10.0F, 8.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.8841F, 10.0F, 63.6143F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r2 = FurnaceEngine.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(22, 67).addBox(-0.6477F, -3.5F, 1.3655F, 7.0F, 7.0F, 7.0F, new CubeDeformation(-0.179F)), PartPose.offsetAndRotation(-5.8841F, 10.0F, 63.6143F, 0.0F, -0.7418F, 1.5708F));

		PartDefinition cube_r3 = FurnaceEngine.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 0).addBox(-3.6841F, -3.5F, -15.5143F, 7.0F, 7.0F, 21.0F, new CubeDeformation(-0.18F))
		.texOffs(41, 13).addBox(-6.1841F, -4.0F, -13.5143F, 10.0F, 8.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.8841F, 10.0F, 63.6143F, 0.0F, 0.0F, -1.5708F));

		PartDefinition cube_r4 = FurnaceEngine.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 58).addBox(-6.3523F, -3.5F, 1.3655F, 7.0F, 7.0F, 7.0F, new CubeDeformation(-0.179F)), PartPose.offsetAndRotation(5.8841F, 10.0F, 63.6143F, 0.0F, 0.7418F, -1.5708F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		FurnaceEngine.render(poseStack, vertexConsumer, packedLight, packedOverlay);
	}

	@Override
	public void setupAnim(PlaneEntity p_102618_, float p_102619_, float p_102620_, float p_102621_, float p_102622_, float p_102623_) {}
}