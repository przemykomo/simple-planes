
package xyz.przemyk.simpleplanes.mixin;


import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import xyz.przemyk.simpleplanes.MixinHelper;

@Mixin(Camera.class)
public abstract class MixinCamera {
    @Shadow
    abstract double clipToSpace(double startingDistance);

    @ModifyArg(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/Camera;clipToSpace(D)D"))
    public double calcCameraDistanceRe(double startingDistance) {
        return MixinHelper.onCalcCameraDistanceOverwrite((Camera) (Object) this, startingDistance);
    }

    @Invoker
    public abstract void invokeSetPos(Vec3d pos);

    @Accessor
    public abstract void setYaw(float yaw);
    @Accessor
    public abstract void setPitch(float pitch);


    @Shadow
    Entity focusedEntity;

}
