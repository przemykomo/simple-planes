package xyz.przemyk.simpleplanes.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.przemyk.simpleplanes.client.ClientEventHandler;

@Mixin(GameRenderer.class)
public abstract class MixinGameRenderer implements ResourceManagerReloadListener, AutoCloseable {
    @Shadow @Final private Camera mainCamera;

    @Inject(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Camera;getXRot()F"))
    private void computeCameraAngles(float partialTicks, long l, PoseStack poseStack, CallbackInfo ci) {
        ClientEventHandler.onCameraSetup(mainCamera, partialTicks, poseStack);
    }
}
