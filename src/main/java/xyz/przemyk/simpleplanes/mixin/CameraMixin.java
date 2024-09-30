package xyz.przemyk.simpleplanes.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.Camera;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import xyz.przemyk.simpleplanes.entities.LargePlaneEntity;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

@Mixin(Camera.class)
public class CameraMixin {

    @Shadow private float partialTickTime;

    @Shadow private float eyeHeight;

    @Shadow private float eyeHeightOld;

    @WrapOperation(
        method = "setup",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/Camera;setPosition(DDD)V",
            ordinal = 0
        )
    )
    private void wrapSetPos(Camera camera, double x, double y, double z, Operation<Void> original) {
        Entity player = camera.getEntity();
        if (player.getVehicle() instanceof PlaneEntity planeEntity && !camera.isDetached()) {
            float heightDiff = -0.3f;
//            if (planeEntity instanceof LargePlaneEntity) {
//                heightDiff = -0.5f;
//            }

            double partialTicks = this.partialTickTime;

            Quaternionf qPrev = planeEntity.getQ_Prev();
            Quaternionf qNow = planeEntity.getQ_Client();

            Vector3f eyePrev = new Vector3f(0, this.eyeHeightOld + heightDiff, 0);
            Vector3f eyeNow = new Vector3f(0, this.eyeHeight + heightDiff, 0);
            eyePrev.rotate(qPrev);
            eyeNow.rotate(qNow);

            original.call(camera,
                Mth.lerp(partialTicks, player.xo - eyePrev.x(), player.getX() - eyeNow.x()),
                Mth.lerp(partialTicks, player.yo + eyePrev.y(), player.getY() + eyeNow.y()) + 0.375,
                Mth.lerp(partialTicks, player.zo + eyePrev.z(), player.getZ() + eyeNow.z()));
        } else {
            original.call(camera, x, y, z);
        }
    }
}
