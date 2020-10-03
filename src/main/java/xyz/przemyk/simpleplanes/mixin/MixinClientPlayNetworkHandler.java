package xyz.przemyk.simpleplanes.mixin;

import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.przemyk.simpleplanes.entities.AbstractPlaneEntityType;

@Mixin(ClientPlayNetworkHandler.class)
public class MixinClientPlayNetworkHandler {
    @Shadow
    private ClientWorld world;

    // Set the staged entity type
    @Inject(method = "onEntitySpawn", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/packet/s2c/play/EntitySpawnS2CPacket;getX()D"))
    private void handleEntitySpawn(EntitySpawnS2CPacket pkt, CallbackInfo ci) {
        Entity toSpawn = null;
        double x = pkt.getX();
        double y = pkt.getY();
        double z = pkt.getZ();
        EntityType<?> entityTypeId = pkt.getEntityTypeId();
        if (entityTypeId instanceof AbstractPlaneEntityType) {
            AbstractPlaneEntityType entityType = (AbstractPlaneEntityType) entityTypeId;
            toSpawn = entityType.create(world);
        }

        if (toSpawn != null) {
            // Copied from end of ClientPlayNetworkHandler#onEntitySpawn
            toSpawn.setPos(x,y,z);
            toSpawn.updateTrackedPosition(x, y, z);
            toSpawn.refreshPositionAfterTeleport(x, y, z);
            toSpawn.pitch = (float) (pkt.getPitch() * 360) / 256.0F;
            toSpawn.yaw = (float) (pkt.getYaw() * 360) / 256.0F;
            toSpawn.setEntityId(pkt.getId());
            toSpawn.setUuid(pkt.getUuid());
            this.world.addEntity(pkt.getId(), toSpawn);
        }
    }
}
