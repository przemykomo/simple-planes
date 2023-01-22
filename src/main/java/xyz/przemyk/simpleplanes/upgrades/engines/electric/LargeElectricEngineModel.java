//package xyz.przemyk.simpleplanes.upgrades.engines.electric;
//
//import com.mojang.blaze3d.vertex.PoseStack;
//import com.mojang.blaze3d.vertex.VertexConsumer;
//import net.minecraft.client.model.EntityModel;
//import net.minecraft.client.model.geom.ModelPart;
//import net.minecraft.client.model.geom.PartPose;
//import net.minecraft.client.model.geom.builders.*;
//import xyz.przemyk.simpleplanes.entities.PlaneEntity;
//
//public class LargeElectricEngineModel extends EntityModel<PlaneEntity> {
//	private final ModelPart ElectricalEngine;
//
//	public LargeElectricEngineModel(ModelPart root) {
//		this.ElectricalEngine = root.getChild("ElectricalEngine");
//	}
//
//	public static LayerDefinition createBodyLayer() {
//		MeshDefinition meshdefinition = new MeshDefinition();
//		PartDefinition partdefinition = meshdefinition.getRoot();
//
//		PartDefinition ElectricalEngine = partdefinition.addOrReplaceChild("ElectricalEngine", CubeListBuilder.create().texOffs(40, 45).addBox(-4.0F, -1.5088F, -13.98F, 8.0F, 6.0F, 23.0F, new CubeDeformation(0.001F))
//		.texOffs(0, 0).addBox(-2.0F, -1.5088F, -16.08F, 4.0F, 6.0F, 27.0F, new CubeDeformation(0.0F))
//		.texOffs(40, 45).mirror().addBox(-58.0F, -1.5088F, -13.98F, 8.0F, 6.0F, 23.0F, new CubeDeformation(0.001F)).mirror(false)
//		.texOffs(0, 0).mirror().addBox(-56.0F, -1.5088F, -16.08F, 4.0F, 6.0F, 27.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(27.0F, -5.4941F, 39.48F));
//
//		PartDefinition octagon_r1 = ElectricalEngine.addOrReplaceChild("octagon_r1", CubeListBuilder.create().texOffs(84, 107).mirror().addBox(-0.121F, -0.879F, -11.51F, 1.0F, 1.0F, 23.0F, new CubeDeformation(0.0004F)).mirror(false), PartPose.offsetAndRotation(-54.0097F, 4.6565F, -2.49F, 0.0F, 0.0F, 2.3562F));
//
//		PartDefinition octagon_r2 = ElectricalEngine.addOrReplaceChild("octagon_r2", CubeListBuilder.create().texOffs(80, 26).mirror().addBox(-3.8647F, -1.0096F, -11.49F, 4.0F, 2.0F, 23.0F, new CubeDeformation(0.0004F)).mirror(false), PartPose.offsetAndRotation(-54.0097F, 4.6565F, -2.49F, 0.0F, 0.0F, 2.8449F));
//
//		PartDefinition octagon_r3 = ElectricalEngine.addOrReplaceChild("octagon_r3", CubeListBuilder.create().texOffs(80, 52).mirror().addBox(-0.1353F, -1.0096F, -11.49F, 4.0F, 2.0F, 23.0F, new CubeDeformation(0.0004F)).mirror(false), PartPose.offsetAndRotation(-54.0097F, 4.6565F, -2.49F, 0.0F, 0.0F, -2.8449F));
//
//		PartDefinition octagon_r4 = ElectricalEngine.addOrReplaceChild("octagon_r4", CubeListBuilder.create().texOffs(110, 109).mirror().addBox(-0.5F, -0.5F, -11.5F, 1.0F, 1.0F, 23.0F, new CubeDeformation(-0.01F)).mirror(false), PartPose.offsetAndRotation(-54.0F, -2.2088F, -2.48F, 0.0F, 0.0F, -0.7854F));
//
//		PartDefinition octagon_r5 = ElectricalEngine.addOrReplaceChild("octagon_r5", CubeListBuilder.create().texOffs(0, 94).mirror().addBox(-0.01F, -0.86F, -11.5F, 4.0F, 2.0F, 23.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-57.73F, -0.6888F, -2.48F, 0.0F, 0.0F, -0.2967F));
//
//		PartDefinition octagon_r6 = ElectricalEngine.addOrReplaceChild("octagon_r6", CubeListBuilder.create().texOffs(96, 81).mirror().addBox(-3.99F, -0.86F, -11.5F, 4.0F, 2.0F, 23.0F, new CubeDeformation(0.0002F)).mirror(false), PartPose.offsetAndRotation(-50.27F, -0.6888F, -2.48F, 0.0F, 0.0F, 0.2967F));
//
//		PartDefinition octagon_r7 = ElectricalEngine.addOrReplaceChild("octagon_r7", CubeListBuilder.create().texOffs(84, 107).addBox(-0.879F, -0.879F, -11.51F, 1.0F, 1.0F, 23.0F, new CubeDeformation(0.0004F)), PartPose.offsetAndRotation(0.0097F, 4.6565F, -2.49F, 0.0F, 0.0F, -2.3562F));
//
//		PartDefinition octagon_r8 = ElectricalEngine.addOrReplaceChild("octagon_r8", CubeListBuilder.create().texOffs(80, 26).addBox(-0.1353F, -1.0096F, -11.49F, 4.0F, 2.0F, 23.0F, new CubeDeformation(0.0004F)), PartPose.offsetAndRotation(0.0097F, 4.6565F, -2.49F, 0.0F, 0.0F, -2.8449F));
//
//		PartDefinition octagon_r9 = ElectricalEngine.addOrReplaceChild("octagon_r9", CubeListBuilder.create().texOffs(80, 52).addBox(-3.8647F, -1.0096F, -11.49F, 4.0F, 2.0F, 23.0F, new CubeDeformation(0.0004F)), PartPose.offsetAndRotation(0.0097F, 4.6565F, -2.49F, 0.0F, 0.0F, 2.8449F));
//
//		PartDefinition octagon_r10 = ElectricalEngine.addOrReplaceChild("octagon_r10", CubeListBuilder.create().texOffs(110, 109).addBox(-0.5F, -0.5F, -11.5F, 1.0F, 1.0F, 23.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(0.0F, -2.2088F, -2.48F, 0.0F, 0.0F, 0.7854F));
//
//		PartDefinition octagon_r11 = ElectricalEngine.addOrReplaceChild("octagon_r11", CubeListBuilder.create().texOffs(0, 94).addBox(-3.99F, -0.86F, -11.5F, 4.0F, 2.0F, 23.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.73F, -0.6888F, -2.48F, 0.0F, 0.0F, 0.2967F));
//
//		PartDefinition octagon_r12 = ElectricalEngine.addOrReplaceChild("octagon_r12", CubeListBuilder.create().texOffs(96, 81).addBox(-0.01F, -0.86F, -11.5F, 4.0F, 2.0F, 23.0F, new CubeDeformation(0.0002F)), PartPose.offsetAndRotation(-3.73F, -0.6888F, -2.48F, 0.0F, 0.0F, -0.2967F));
//
//		return LayerDefinition.create(meshdefinition, 256, 256);
//	}
//
//	@Override
//	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
//		ElectricalEngine.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
//	}
//
//	@Override
//	public void setupAnim(PlaneEntity p_102618_, float p_102619_, float p_102620_, float p_102621_, float p_102622_, float p_102623_) {}
//}