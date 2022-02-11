package xyz.przemyk.simpleplanes.upgrades.seats;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.FriendlyByteBuf;
import xyz.przemyk.simpleplanes.client.render.PlaneRenderer;
import xyz.przemyk.simpleplanes.client.render.UpgradesModels;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesItems;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

public class SeatsUpgrade extends Upgrade {

    public SeatsUpgrade(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.SEATS.get(), planeEntity);
    }

    @Override
    public void render(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, float partialTicks) {
        UpgradesModels.SEATS.renderToBuffer(matrixStack, buffer.getBuffer(UpgradesModels.SEATS.renderType(PlaneRenderer.getMaterialTexture(planeEntity))), packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public void writePacket(FriendlyByteBuf buffer) {}

    @Override
    public void readPacket(FriendlyByteBuf buffer) {}

    @Override
    public void onRemoved() {
        planeEntity.ejectPassengers();
        planeEntity.spawnAtLocation(SimplePlanesItems.SEATS.get());
    }
}
