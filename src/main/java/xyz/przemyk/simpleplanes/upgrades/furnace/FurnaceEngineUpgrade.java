package xyz.przemyk.simpleplanes.upgrades.furnace;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.network.PacketBuffer;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.EngineUpgrade;

public class FurnaceEngineUpgrade extends EngineUpgrade {
//TODO
    public FurnaceEngineUpgrade(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.FURNACE_ENGINE.get(), planeEntity);
    }

    @Override
    public boolean isPowered() {
        return true;
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight, float partialTicks) {

    }

    @Override
    public void writePacket(PacketBuffer buffer) {

    }

    @Override
    public void readPacket(PacketBuffer buffer) {

    }
}
