package xyz.przemyk.simpleplanes.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.events.RenderHudEvents;

@Mixin(InGameHud.class)
public abstract class MixinInGameHud {
    @Shadow
    abstract int getHeartCount(LivingEntity entity);

    @Inject(method = "renderExperienceBar",
        at = @At("HEAD"), cancellable = true)
    public void beforeRenderExperienceBar(MatrixStack matrices, int x, CallbackInfo cli) {
        ActionResult result = RenderHudEvents.EVENT.invoker().render(RenderHudEvents.Stage.EXP, matrices);
        if (result != ActionResult.PASS) {
            cli.cancel();
        }
    }

    @Inject(method = "renderMountJumpBar",
        at = @At("HEAD"), cancellable = true)
    public void beforeRenderMountJumpBar(MatrixStack matrices, int x, CallbackInfo cli) {
        ActionResult result = RenderHudEvents.EVENT.invoker().render(RenderHudEvents.Stage.EXP, matrices);
        if (result != ActionResult.PASS) {
            cli.cancel();
        }
    }

    @Inject(method = "render(Lnet/minecraft/client/util/math/MatrixStack;F)V",
        at = @At("HEAD"), cancellable = true)
    public void beforeRender(MatrixStack matrices, float tickDelta, CallbackInfo cli) {
        ActionResult result = RenderHudEvents.EVENT.invoker().render(RenderHudEvents.Stage.FIRST, matrices);
        if (result != ActionResult.PASS) {
            cli.cancel();
        }
    }

    //    @ModifyVariable(method = "render(Lnet/minecraft/client/util/math/MatrixStack;F)V",
//    at = @At(value = "INVOKE_ASSIGN",target = "net/minecraft/client/gui/hud/InGameHud.getHeartCount(Lnet/minecraft/entity/LivingEntity;)I")
//    ,name = "aa",remap = false)
//    public int redirectShouldRenderHunger(int health){
//        return 1;
//    }
    @Redirect(method = "renderStatusBars(Lnet/minecraft/client/util/math/MatrixStack;F)V",
        at = @At(value = "INVOKE",
            target = "net/minecraft/client/gui/hud/InGameHud.getHeartCount(Lnet/minecraft/entity/LivingEntity;)I"))
    public int redirectShouldRenderHunger(InGameHud _this, LivingEntity entity) {
        if (MinecraftClient.getInstance().player.getVehicle() instanceof PlaneEntity) {
            return 1;
        }
        return this.getHeartCount(entity);

    }

    @Inject(method = "render(Lnet/minecraft/client/util/math/MatrixStack;F)V",
        at = @At("TAIL"), cancellable = true)
    public void afterRender(MatrixStack matrices, float tickDelta, CallbackInfo cli) {
        ActionResult result = RenderHudEvents.EVENT.invoker().render(RenderHudEvents.Stage.LAST, matrices);
        if (result != ActionResult.PASS) {
            cli.cancel();
        }
    }
}
