
package xyz.przemyk.simpleplanes.mixin;


import net.minecraft.client.render.Camera;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import xyz.przemyk.simpleplanes.MixinHelper;

@Mixin(ServerPlayNetworkHandler.class)
public interface MixinServerPlayNetworkHandler {
    @Accessor
    public void setVehicleFloatingTicks(int ticks);
}
