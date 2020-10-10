//package xyz.przemyk.simpleplanes;
//
//import net.minecraft.client.renderer.ActiveRenderInfo;
//import net.minecraft.client.renderer.entity.model.VillagerModel;
//import net.minecraft.entity.Entity;
//import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
//import net.minecraft.entity.merchant.villager.VillagerEntity;
//import net.minecraft.util.math.MathHelper;
//import xyz.przemyk.simpleplanes.entities.PlaneEntity;
//import xyz.przemyk.simpleplanes.mixin.MixinVillagerModel;
//
//import java.util.function.Function;
//
//public class MixinHelper {
//    public static double onCalcCameraDistanceOverwrite(ActiveRenderInfo ac, double d, Function<Double, Double> callback) {
//        Entity entity = ac.getRenderViewEntity();
//        if (entity.isPassenger() && entity.getRidingEntity() instanceof PlaneEntity) {
//            PlaneEntity vehicle = (PlaneEntity) entity.getRidingEntity();
//            return callback.apply(d * vehicle.getCameraDistanceMultiplayer());
//        }
//        return callback.apply(d);
//    }
//
//    public static <T extends Entity> void setRotationAngles(VillagerModel model, T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
//        MixinVillagerModel<T> model1 = (MixinVillagerModel) ((Object) model);
//        boolean flag = false;
//        if (entityIn instanceof AbstractVillagerEntity) {
//            flag = ((AbstractVillagerEntity) entityIn).getShakeHeadTicks() > 0;
//        }
//        model1.villagerHead.rotateAngleY = netHeadYaw * ((float) Math.PI / 180F);
//        model1.villagerHead.rotateAngleX = headPitch * ((float) Math.PI / 180F);
//        if (flag) {
//            model1.villagerHead.rotateAngleZ = 0.3F * MathHelper.sin(0.45F * ageInTicks);
//            model1.villagerHead.rotateAngleX = 0.4F;
//        } else {
//            model1.villagerHead.rotateAngleZ = 0.0F;
//        }
//        if (model1.isSitting) {
//            model1.rightVillagerLeg.rotateAngleX = -1.4137167F;
//            model1.rightVillagerLeg.rotateAngleY = ((float) Math.PI / 10F);
//            model1.rightVillagerLeg.rotateAngleZ = 0.07853982F;
//            model1.leftVillagerLeg.rotateAngleX = -1.4137167F;
//            model1.leftVillagerLeg.rotateAngleY = (-(float) Math.PI / 10F);
//            model1.leftVillagerLeg.rotateAngleZ = -0.07853982F;
//        } else {
//            model1.rightVillagerLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
//            model1.leftVillagerLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount * 0.5F;
//            model1.rightVillagerLeg.rotateAngleY = 0.0F;
//            model1.leftVillagerLeg.rotateAngleY = 0.0F;
//        }
//        model1.rightVillagerLeg.rotationPointZ = 0.1F;
//        model1.leftVillagerLeg.rotationPointZ = 0.1F;
//        model1.rightVillagerLeg.rotationPointY = 12.0F;
//        model1.leftVillagerLeg.rotationPointY = 12.0F;
//        model1.villagerHead.rotationPointY = 0.0F;
//        model1.villagerBody.rotationPointY = 0.0F;
//
//        model1.villagerArms.rotationPointY = 3.0F;
//        model1.villagerArms.rotationPointZ = -1.0F;
//        model1.villagerArms.rotateAngleX = -0.75F;
//
//    }
//}
