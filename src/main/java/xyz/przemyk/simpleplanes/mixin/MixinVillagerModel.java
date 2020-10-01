package xyz.przemyk.simpleplanes.mixin;

import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.entity.model.IHeadToggle;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.entity.model.VillagerModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(VillagerModel.class)
public abstract class MixinVillagerModel<T extends Entity> extends SegmentedModel<T> implements IHasHead, IHeadToggle {

    @Shadow
    public ModelRenderer villagerHead;
    @Shadow
    public ModelRenderer hat;
    @Shadow
    public ModelRenderer hatBrim;
    @Shadow
    public ModelRenderer villagerBody;
    @Shadow
    public ModelRenderer clothing;
    @Shadow
    public ModelRenderer villagerArms;
    @Shadow
    public ModelRenderer rightVillagerLeg;
    @Shadow
    public ModelRenderer leftVillagerLeg;
    @Shadow
    public ModelRenderer villagerNose;


    @Overwrite
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        boolean flag = false;
        if (entityIn instanceof AbstractVillagerEntity) {
            flag = ((AbstractVillagerEntity) entityIn).getShakeHeadTicks() > 0;
        }
        this.villagerHead.rotateAngleY = netHeadYaw * ((float) Math.PI / 180F);
        this.villagerHead.rotateAngleX = headPitch * ((float) Math.PI / 180F);
        if (flag) {
            this.villagerHead.rotateAngleZ = 0.3F * MathHelper.sin(0.45F * ageInTicks);
            this.villagerHead.rotateAngleX = 0.4F;
        } else {
            this.villagerHead.rotateAngleZ = 0.0F;
        }
        if (this.isSitting) {
            this.rightVillagerLeg.rotateAngleX = -1.4137167F;
            this.rightVillagerLeg.rotateAngleY = ((float) Math.PI / 10F);
            this.rightVillagerLeg.rotateAngleZ = 0.07853982F;
            this.leftVillagerLeg.rotateAngleX = -1.4137167F;
            this.leftVillagerLeg.rotateAngleY = (-(float) Math.PI / 10F);
            this.leftVillagerLeg.rotateAngleZ = -0.07853982F;
        } else {
            this.rightVillagerLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
            this.leftVillagerLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount * 0.5F;
            this.rightVillagerLeg.rotateAngleY = 0.0F;
            this.leftVillagerLeg.rotateAngleY = 0.0F;
        }
        this.rightVillagerLeg.rotationPointY = 12.0F;
        this.leftVillagerLeg.rotationPointY = 12.0F;
        this.villagerHead.rotationPointY = 0.0F;
        this.villagerBody.rotationPointY = 0.0F;

        this.villagerArms.rotationPointY = 3.0F;
        this.villagerArms.rotationPointZ = -1.0F;
        this.villagerArms.rotateAngleX = -0.75F;
    }


}
