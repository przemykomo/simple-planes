package xyz.przemyk.simpleplanes.mixin;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.przemyk.simpleplanes.client.ClientEventHandler;

@Mixin(Minecraft.class)
public class MixinMinecraft {

    @Inject(method = "handleKeybinds", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;setScreen(Lnet/minecraft/client/gui/screens/Screen;)V", ordinal = 1), cancellable = true)
    private void onInventoryOpen(CallbackInfo ci) {
        ClientEventHandler.planeInventory(ci);
    }
}
