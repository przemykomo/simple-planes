package sappy.simpleplanes.upgrades.engines;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.HumanoidArm;
import sappy.simpleplanes.entities.PlaneEntity;
import sappy.simpleplanes.upgrades.Upgrade;
import sappy.simpleplanes.upgrades.UpgradeType;

public abstract class EngineUpgrade extends Upgrade {

    public EngineUpgrade(UpgradeType type, PlaneEntity planeEntity) {
        super(type, planeEntity);
    }

    @Override
    public void remove() {
        super.remove();
        planeEntity.engineUpgrade = null;
    }

    public abstract boolean isPowered();

    public abstract void renderPowerHUD(GuiGraphics guiGraphics, HumanoidArm side, int scaledWidth, int scaledHeight,
            float partialTicks);
}
