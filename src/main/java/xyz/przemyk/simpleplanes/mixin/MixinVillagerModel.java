package xyz.przemyk.simpleplanes.mixin;

import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.entity.model.IHeadToggle;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.entity.model.VillagerModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(VillagerModel.class)
public abstract class MixinVillagerModel<T extends Entity> extends SegmentedModel<T> implements IHasHead, IHeadToggle {

    @Shadow
    protected ModelRenderer villagerHead;

    @Final
    @Shadow
    protected ModelRenderer villagerBody;

    @Final
    @Shadow
    protected ModelRenderer villagerArms;

    @Final
    @Shadow
    protected ModelRenderer rightVillagerLeg;

    @Final
    @Shadow
    protected ModelRenderer leftVillagerLeg;

    @SuppressWarnings("SpellCheckingInspection")
    @Inject(method = "setRotationAngles(Lnet/minecraft/entity/Entity;FFFFF)V", at = @At("RETURN"))
    public void setSittingRotation(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
        if (isSitting) {
            rightVillagerLeg.rotateAngleX = -1.4137167F;
            rightVillagerLeg.rotateAngleY = ((float) Math.PI / 10F);
            rightVillagerLeg.rotateAngleZ = 0.07853982F;
            leftVillagerLeg.rotateAngleX = -1.4137167F;
            leftVillagerLeg.rotateAngleY = (-(float) Math.PI / 10F);
            leftVillagerLeg.rotateAngleZ = -0.07853982F;

            rightVillagerLeg.rotationPointY = 12.0F;
            leftVillagerLeg.rotationPointY = 12.0F;
            villagerHead.rotationPointY = 0.0F;
            villagerBody.rotationPointY = 0.0F;

            villagerArms.rotationPointY = 3.0F;
            villagerArms.rotationPointZ = -1.0F;
            villagerArms.rotateAngleX = -0.75F;
        }
    }

}
