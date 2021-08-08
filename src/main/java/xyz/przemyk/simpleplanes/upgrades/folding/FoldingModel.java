//package xyz.przemyk.simpleplanes.upgrades.folding;
//// Made with Blockbench 3.5.2
//// Exported for Minecraft version 1.15
//
//import com.mojang.blaze3d.vertex.PoseStack;
//import com.mojang.blaze3d.vertex.VertexConsumer;
//import net.minecraft.client.model.EntityModel;
//import net.minecraft.client.model.geom.ModelPart;
//import com.mojang.math.Vector3f;
//import xyz.przemyk.simpleplanes.entities.PlaneEntity;
//
//@SuppressWarnings("FieldCanBeLocal")
//public class FoldingModel extends EntityModel<PlaneEntity> {
//    public static final FoldingModel INSTANCE = new FoldingModel();
//    private final ModelPart rightWing;
//    private final ModelPart leftWing = new ModelPart(this, 22, 0);
//
//    public FoldingModel() {
//        this.leftWing.addBox(-10.0F, 0.0F, 0.0F, 10.0F, 20.0F, 2.0F, 1.0F);
//        this.rightWing = new ModelPart(this, 22, 0);
//        this.rightWing.mirror = true;
//        this.rightWing.addBox(0.0F, 0.0F, 0.0F, 10.0F, 20.0F, 2.0F, 1.0F);
//    }
//
//    @Override
//    public void setupAnim(PlaneEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
//
//    }
//
//    @Override
//    public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
//        matrixStack.pushPose();
//        matrixStack.mulPose(Vector3f.XP.rotationDegrees(90));
//        matrixStack.translate(0, 1.1, -0.2);
//        matrixStack.scale(0.5f, 0.5f, 0.5f);
//
//        rightWing.render(matrixStack, buffer, packedLight, packedOverlay);
//        leftWing.render(matrixStack, buffer, packedLight, packedOverlay);
//        matrixStack.popPose();
//
//    }
//
//}