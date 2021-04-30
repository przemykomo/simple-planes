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

@Mixin(VillagerModel.class)
public abstract class MixinVillagerModel<T extends Entity> extends SegmentedModel<T> implements IHasHead, IHeadToggle {

    @Shadow
    protected ModelRenderer head;

    @Final
    @Shadow
    protected ModelRenderer body;

    @Final
    @Shadow
    protected ModelRenderer arms;

    @Final
    @Shadow
    protected ModelRenderer leg0;

    @Final
    @Shadow
    protected ModelRenderer leg1;

    @SuppressWarnings("SpellCheckingInspection")
    @Inject(method = "setupAnim(Lnet/minecraft/entity/Entity;FFFFF)V", at = @At("RETURN"))
    public void setSittingRotation(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
        if (riding) {
            leg0.xRot = -1.4137167F;
            leg0.yRot = ((float) Math.PI / 10F);
            leg0.zRot = 0.07853982F;
            leg1.xRot = -1.4137167F;
            leg1.yRot = (-(float) Math.PI / 10F);
            leg1.zRot = -0.07853982F;

            leg0.y = 12.0F;
            leg1.y = 12.0F;
            head.y = 0.0F;
            body.y = 0.0F;

            arms.y = 3.0F;
            arms.z = -1.0F;
            arms.xRot = -0.75F;
        }
    }

}
