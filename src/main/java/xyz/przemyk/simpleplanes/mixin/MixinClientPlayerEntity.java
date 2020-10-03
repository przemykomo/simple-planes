package xyz.przemyk.simpleplanes.mixin;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.handler.PlaneNetworking;


@Mixin(ClientPlayerEntity.class)
public abstract class MixinClientPlayerEntity extends Entity {
    public MixinClientPlayerEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "openRidingInventory", at = @At("HEAD"), cancellable = true)
    public void openRidingInventory(CallbackInfo clir) {
        Entity vehicle = this.getVehicle();
        if (vehicle instanceof PlaneEntity&&((PlaneEntity)vehicle).hasChest()) {
            PlaneNetworking.send_OpenInventory(true);
        }
    }

}
