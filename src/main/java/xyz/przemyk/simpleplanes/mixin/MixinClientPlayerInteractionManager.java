package xyz.przemyk.simpleplanes.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.HorseBaseEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
@Mixin(ClientPlayerInteractionManager.class)
public abstract class MixinClientPlayerInteractionManager {
    //    public abstract boolean hasRidingInventory();
    @Shadow
    MinecraftClient client;

    @Inject(method="hasRidingInventory()Z",at=@At("TAIL"),cancellable = true)
    public void hasRidingInventory(CallbackInfoReturnable<Boolean> cbi) {
        Entity vehicle = this.client.player.getVehicle();
        if(this.client.player.hasVehicle() &&
            (vehicle instanceof HorseBaseEntity||
                (vehicle instanceof PlaneEntity&&((PlaneEntity)vehicle).hasChest())))
        {
            cbi.setReturnValue(true);
        }
    }


}
