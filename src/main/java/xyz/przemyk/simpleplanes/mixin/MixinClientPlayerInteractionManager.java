package xyz.przemyk.simpleplanes.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.HorseBaseEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
@Mixin(ClientPlayerInteractionManager.class)
public abstract class MixinClientPlayerInteractionManager {
    //    public abstract boolean hasRidingInventory();
    @Shadow
    MinecraftClient client;

    @Overwrite
    public boolean hasRidingInventory() {
        Entity vehicle = this.client.player.getVehicle();

        return this.client.player.hasVehicle() &&
            (vehicle instanceof HorseBaseEntity||
                (vehicle instanceof PlaneEntity&&((PlaneEntity)vehicle).hasChest()));
    }
    ;

}
