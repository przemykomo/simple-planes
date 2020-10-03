package xyz.przemyk.simpleplanes.mixin;

import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.przemyk.simpleplanes.PlanesClientEvents;

@Mixin(GameRenderer.class)
public abstract class MixinGameRenderer {

    @Inject(method = "renderWorld(FJLnet/minecraft/client/util/math/MatrixStack;)V",
        at = @At(value = "INVOKE", target = "net/minecraft/client/render/WorldRenderer.render(Lnet/minecraft/client/util/math/MatrixStack;FJZLnet/minecraft/client/render/Camera;Lnet/minecraft/client/render/GameRenderer;Lnet/minecraft/client/render/LightmapTextureManager;Lnet/minecraft/util/math/Matrix4f;)V"))
    public void onCameraUpdate(float tickDelta, long limitTime, MatrixStack matrix, CallbackInfo callbackInfo) {
        PlanesClientEvents.onCameraUpdate(tickDelta, limitTime, matrix, (GameRenderer)((Object) this));
//        matrix.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(180));
    }


}
