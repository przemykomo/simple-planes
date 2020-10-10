//
//package xyz.przemyk.simpleplanes.mixin;
//
//
//import net.minecraft.client.renderer.ActiveRenderInfo;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.Shadow;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Redirect;
//import xyz.przemyk.simpleplanes.MixinHelper;
//
//@Mixin(ActiveRenderInfo.class)
//public abstract class MixinActiveRenderInfo {
//    @Shadow
//    abstract double calcCameraDistance(double startingDistance);
//    @Redirect(
//        method = "Lnet/minecraft/client/renderer/ActiveRenderInfo;update(Lnet/minecraft/world/IBlockReader;Lnet/minecraft/entity/Entity;ZZF)V",
//        at = @At(
//            value = "INVOKE",
//            target = "Lnet/minecraft/client/renderer/ActiveRenderInfo;calcCameraDistance(D)D"
//        )
//    )
//    public double calcCameraDistanceRe(ActiveRenderInfo ac, double startingDistance) {
//        return MixinHelper.onCalcCameraDistanceOverwrite(ac,startingDistance,  ((MixinActiveRenderInfo)((Object)ac))::calcCameraDistance);
//    }
//}
