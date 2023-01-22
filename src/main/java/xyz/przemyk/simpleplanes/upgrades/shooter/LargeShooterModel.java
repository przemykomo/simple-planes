//package xyz.przemyk.simpleplanes.upgrades.shooter;
//
//import com.mojang.blaze3d.vertex.PoseStack;
//import com.mojang.blaze3d.vertex.VertexConsumer;
//import net.minecraft.client.model.EntityModel;
//import net.minecraft.client.model.geom.ModelPart;
//import net.minecraft.client.model.geom.PartPose;
//import net.minecraft.client.model.geom.builders.*;
//import xyz.przemyk.simpleplanes.entities.PlaneEntity;
//
//
//public class LargeShooterModel extends EntityModel<PlaneEntity> {
//	private final ModelPart Shooter;
//
//	public LargeShooterModel(ModelPart root) {
//		this.Shooter = root.getChild("Shooter");
//	}
//
//	public static LayerDefinition createBodyLayer() {
//		MeshDefinition meshdefinition = new MeshDefinition();
//		PartDefinition partdefinition = meshdefinition.getRoot();
//
//		PartDefinition Shooter = partdefinition.addOrReplaceChild("Shooter", CubeListBuilder.create().texOffs(30, 0).addBox(11.5F, -35.0F, 35.0F, 3.0F, 2.0F, 9.0F, new CubeDeformation(0.0F))
//		.texOffs(0, 26).addBox(10.0F, -33.0F, 33.0F, 6.0F, 6.0F, 15.0F, new CubeDeformation(0.0F))
//		.texOffs(28, 26).addBox(12.0F, -31.0F, 16.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F))
//		.texOffs(0, 0).addBox(11.0F, -32.0F, 28.0F, 4.0F, 4.0F, 21.0F, new CubeDeformation(0.0F))
//		.texOffs(0, 0).addBox(11.5F, -31.5F, 12.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 24.0F, 0.0F));
//
//		return LayerDefinition.create(meshdefinition, 64, 64);
//	}
//
//	@Override
//	public void setupAnim(PlaneEntity p_102618_, float p_102619_, float p_102620_, float p_102621_, float p_102622_, float p_102623_) {}
//
//	@Override
//	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
//		Shooter.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
//	}
//}