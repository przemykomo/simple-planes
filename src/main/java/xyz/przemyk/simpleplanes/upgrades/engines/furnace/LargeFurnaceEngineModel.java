package xyz.przemyk.simpleplanes.upgrades.engines.furnace;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

public class LargeFurnaceEngineModel extends EntityModel<PlaneEntity> {
	private final ModelPart FurnaceEngine;

	public LargeFurnaceEngineModel(ModelPart root) {
		this.FurnaceEngine = root.getChild("FurnaceEngine");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition FurnaceEngine = partdefinition.addOrReplaceChild("FurnaceEngine", CubeListBuilder.create().texOffs(70, 34).addBox(-55.0F, 20.9853F, 121.9F, 4.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-57.0F, 20.9853F, 99.0F, 8.0F, 9.0F, 23.0F, new CubeDeformation(0.001F))
		.texOffs(70, 34).mirror().addBox(-1.0F, 20.9853F, 121.9F, 4.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 0).mirror().addBox(-3.0F, 20.9853F, 99.0F, 8.0F, 9.0F, 23.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offset(26.0F, -28.0F, -73.5F));

		PartDefinition octagon_r1 = FurnaceEngine.addOrReplaceChild("octagon_r1", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-6.0F, -1.0147F, 0.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offsetAndRotation(2.3F, 29.0F, 102.5F, 0.0F, 0.0F, 0.3927F));

		PartDefinition octagon_r2 = FurnaceEngine.addOrReplaceChild("octagon_r2", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-1.5F, -3.0F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offsetAndRotation(-2.6172F, 29.1121F, 108.5F, 0.0F, -1.5708F, 0.3927F));

		PartDefinition octagon_r3 = FurnaceEngine.addOrReplaceChild("octagon_r3", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-1.5F, -3.0F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offsetAndRotation(-2.6172F, 29.1121F, 112.5F, -3.1416F, 0.0F, -2.7489F));

		PartDefinition octagon_r4 = FurnaceEngine.addOrReplaceChild("octagon_r4", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-1.5F, -3.0F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offsetAndRotation(-2.6172F, 29.1121F, 116.5F, 0.0F, 1.5708F, 0.3927F));

		PartDefinition octagon_r5 = FurnaceEngine.addOrReplaceChild("octagon_r5", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-1.5F, -3.0F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offsetAndRotation(4.6172F, 29.1121F, 108.5F, 0.0F, -1.5708F, -0.3927F));

		PartDefinition octagon_r6 = FurnaceEngine.addOrReplaceChild("octagon_r6", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-1.5F, -3.0F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offsetAndRotation(4.6172F, 29.1121F, 112.5F, 0.0F, 1.5708F, -0.3927F));

		PartDefinition octagon_r7 = FurnaceEngine.addOrReplaceChild("octagon_r7", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-1.5F, -3.0F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offsetAndRotation(4.6172F, 29.1121F, 116.5F, 3.1416F, 0.0F, 2.7489F));

		PartDefinition octagon_r8 = FurnaceEngine.addOrReplaceChild("octagon_r8", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-1.5F, -3.0F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offsetAndRotation(4.6172F, 29.1121F, 104.5F, 0.0F, 0.0F, -0.3927F));

		PartDefinition octagon_r9 = FurnaceEngine.addOrReplaceChild("octagon_r9", CubeListBuilder.create().texOffs(0, 64).mirror().addBox(-0.01F, -0.86F, -11.5F, 4.0F, 2.0F, 23.0F, new CubeDeformation(0.0002F)).mirror(false), PartPose.offsetAndRotation(-2.73F, 21.8053F, 110.5F, 0.0F, 0.0F, -0.2967F));

		PartDefinition octagon_r10 = FurnaceEngine.addOrReplaceChild("octagon_r10", CubeListBuilder.create().texOffs(31, 66).mirror().addBox(-3.99F, -0.86F, -11.5F, 4.0F, 2.0F, 23.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(4.73F, 21.8053F, 110.5F, 0.0F, 0.0F, 0.2967F));

		PartDefinition octagon_r11 = FurnaceEngine.addOrReplaceChild("octagon_r11", CubeListBuilder.create().texOffs(70, 0).mirror().addBox(-0.5F, -0.5F, -11.5F, 1.0F, 1.0F, 23.0F, new CubeDeformation(-0.01F)).mirror(false), PartPose.offsetAndRotation(1.0F, 20.2853F, 110.5F, 0.0F, 0.0F, 0.7854F));

		PartDefinition octagon_r12 = FurnaceEngine.addOrReplaceChild("octagon_r12", CubeListBuilder.create().texOffs(0, 0).addBox(3.0F, -1.0147F, 0.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-54.3F, 29.0F, 102.5F, 0.0F, 0.0F, -0.3927F));

		PartDefinition octagon_r13 = FurnaceEngine.addOrReplaceChild("octagon_r13", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -3.0F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-49.3828F, 29.1121F, 108.5F, 0.0F, -1.5708F, -0.3927F));

		PartDefinition octagon_r14 = FurnaceEngine.addOrReplaceChild("octagon_r14", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -3.0F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-49.3828F, 29.1121F, 112.5F, 0.0F, 1.5708F, -0.3927F));

		PartDefinition octagon_r15 = FurnaceEngine.addOrReplaceChild("octagon_r15", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -3.0F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-49.3828F, 29.1121F, 116.5F, -3.1416F, 0.0F, 2.7489F));

		PartDefinition octagon_r16 = FurnaceEngine.addOrReplaceChild("octagon_r16", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -3.0F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-56.6172F, 29.1121F, 108.5F, 0.0F, 1.5708F, 0.3927F));

		PartDefinition octagon_r17 = FurnaceEngine.addOrReplaceChild("octagon_r17", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -3.0F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-56.6172F, 29.1121F, 112.5F, 0.0F, -1.5708F, 0.3927F));

		PartDefinition octagon_r18 = FurnaceEngine.addOrReplaceChild("octagon_r18", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -1.0147F, 0.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-51.7F, 29.0F, 114.5F, 0.0F, 0.0F, 0.3927F));

		PartDefinition octagon_r19 = FurnaceEngine.addOrReplaceChild("octagon_r19", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -3.0F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-56.6172F, 29.1121F, 104.5F, -3.1416F, 0.0F, -2.7489F));

		PartDefinition octagon_r20 = FurnaceEngine.addOrReplaceChild("octagon_r20", CubeListBuilder.create().texOffs(0, 64).addBox(-3.99F, -0.86F, -11.5F, 4.0F, 2.0F, 23.0F, new CubeDeformation(0.0002F)), PartPose.offsetAndRotation(-49.27F, 21.8053F, 110.5F, 0.0F, 0.0F, 0.2967F));

		PartDefinition octagon_r21 = FurnaceEngine.addOrReplaceChild("octagon_r21", CubeListBuilder.create().texOffs(31, 66).addBox(-0.01F, -0.86F, -11.5F, 4.0F, 2.0F, 23.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-56.73F, 21.8053F, 110.5F, 0.0F, 0.0F, -0.2967F));

		PartDefinition octagon_r22 = FurnaceEngine.addOrReplaceChild("octagon_r22", CubeListBuilder.create().texOffs(70, 0).addBox(-0.5F, -0.5F, -11.5F, 1.0F, 1.0F, 23.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(-53.0F, 20.2853F, 110.5F, 0.0F, 0.0F, -0.7854F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		FurnaceEngine.render(poseStack, vertexConsumer, packedLight, packedOverlay);
	}

	@Override
	public void setupAnim(PlaneEntity p_102618_, float p_102619_, float p_102620_, float p_102621_, float p_102622_, float p_102623_) {}
}