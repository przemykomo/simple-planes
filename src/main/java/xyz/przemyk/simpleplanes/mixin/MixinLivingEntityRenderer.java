
package xyz.przemyk.simpleplanes.mixin;


import net.minecraft.client.render.Camera;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import xyz.przemyk.simpleplanes.MixinHelper;

@Mixin(LivingEntityRenderer.class)
public abstract class MixinLivingEntityRenderer {
//    @Inject(method = "net/minecraft/client/render/entity/LivingEntityRenderer.render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V",
//    at = @At(""),locals = LocalCapture.PRINT)
//    public void preRender(){
//
//    }
}
