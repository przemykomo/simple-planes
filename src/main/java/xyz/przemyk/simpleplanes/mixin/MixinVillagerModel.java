package xyz.przemyk.simpleplanes.mixin;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.client.render.entity.model.ModelWithHat;
import net.minecraft.client.render.entity.model.ModelWithHead;
import net.minecraft.client.render.entity.model.VillagerResemblingModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(VillagerResemblingModel.class)
public abstract class MixinVillagerModel<T extends Entity> extends CompositeEntityModel<T> implements ModelWithHead, ModelWithHat {

    @Shadow
    public ModelPart head;
    @Shadow
    public ModelPart field_17141;
    @Shadow
    public ModelPart field_17142;
    @Shadow
    public ModelPart torso;
    @Shadow
    public ModelPart robe;
    @Shadow
    public ModelPart arms;
    @Shadow
    public ModelPart rightLeg;
    @Shadow
    public ModelPart leftLeg;
    @Shadow
    public ModelPart nose;


    @Overwrite
    public void setAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        boolean flag = false;
        if (entityIn instanceof VillagerEntity) {
            flag = ((VillagerEntity) entityIn).getHeadRollingTimeLeft() > 0;
        }
        this.head.yaw = netHeadYaw * ((float) Math.PI / 180F);
        this.head.pitch = headPitch * ((float) Math.PI / 180F);
        if (flag) {
            this.head.roll = 0.3F * MathHelper.sin(0.45F * ageInTicks);
            this.head.pitch = 0.4F;
        } else {
            this.head.roll = 0.0F;
        }
        if (this.riding) {
            this.rightLeg.pitch = -1.4137167F;
            this.rightLeg.yaw = ((float) Math.PI / 10F);
            this.rightLeg.roll = 0.07853982F;
            this.leftLeg.pitch = -1.4137167F;
            this.leftLeg.yaw = (-(float) Math.PI / 10F);
            this.leftLeg.roll = -0.07853982F;
        } else {
            this.rightLeg.pitch = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
            this.leftLeg.pitch = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount * 0.5F;
            this.rightLeg.yaw = 0.0F;
            this.leftLeg.yaw = 0.0F;
        }
        this.rightLeg.pivotY = 12.0F;
        this.leftLeg.pivotY = 12.0F;
        this.head.pivotY = 0.0F;
        this.torso.pivotY = 0.0F;

        this.arms.pivotY = 3.0F;
        this.arms.pivotZ = -1.0F;
        this.arms.pitch = -0.75F;
    }


}
