package xyz.przemyk.simpleplanes;

import net.minecraft.client.render.Camera;
import net.minecraft.client.render.entity.model.VillagerResemblingModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.util.math.MathHelper;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.mixin.MixinVillagerModel;

public class MixinHelper {
    public static double onCalcCameraDistanceOverwrite(Camera ac, double d) {
        Entity entity = ac.getFocusedEntity();
        if (entity.hasVehicle() && entity.getVehicle() instanceof PlaneEntity) {
            return (d * 2);
        }
        return (d);
    }

    public static <T extends Entity> void setRotationAngles(VillagerResemblingModel model, T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        MixinVillagerModel<T> model1 = (MixinVillagerModel) ((Object) model);
        boolean flag = false;
        if (entityIn instanceof VillagerEntity) {
            flag = ((VillagerEntity) entityIn).getHeadRollingTimeLeft() > 0;
        }
        model1.head.yaw = netHeadYaw * ((float) Math.PI / 180F);
        model1.head.pitch = headPitch * ((float) Math.PI / 180F);
        if (flag) {
            model1.head.roll = 0.3F * MathHelper.sin(0.45F * ageInTicks);
            model1.head.pitch = 0.4F;
        } else {
            model1.head.roll = 0.0F;
        }
        if (model1.riding) {
            model1.rightLeg.pitch = -1.4137167F;
            model1.rightLeg.yaw = ((float) Math.PI / 10F);
            model1.rightLeg.roll = 0.07853982F;
            model1.leftLeg.pitch = -1.4137167F;
            model1.leftLeg.yaw = (-(float) Math.PI / 10F);
            model1.leftLeg.roll = -0.07853982F;
        } else {
            model1.rightLeg.pitch = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
            model1.leftLeg.pitch = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount * 0.5F;
            model1.rightLeg.yaw = 0.0F;
            model1.leftLeg.yaw = 0.0F;
        }
        model1.rightLeg.pivotZ = 0.1F;
        model1.leftLeg.pivotZ = 0.1F;
        model1.rightLeg.pivotY = 12.0F;
        model1.leftLeg.pivotY = 12.0F;
        model1.head.pivotY = 0.0F;
        model1.torso.pivotY = 0.0F;

        model1.arms.pivotY = 3.0F;
        model1.arms.pivotZ = -1.0F;
        model1.arms.pitch = -0.75F;

    }
}
