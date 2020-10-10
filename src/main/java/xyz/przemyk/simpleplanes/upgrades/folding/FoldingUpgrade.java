package xyz.przemyk.simpleplanes.upgrades.folding;

import net.minecraft.util.ResourceLocation;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

public class FoldingUpgrade extends Upgrade {
    public static final ResourceLocation TEXTURE = new ResourceLocation(SimplePlanesMod.MODID, "textures/plane_upgrades/folding.png");

    public FoldingUpgrade(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.FOLDING, planeEntity);
    }

    @Override
    public void render(float partialticks, float scale) {
        FoldingModel.INSTANCE.render(planeEntity, partialticks, 0, 0, 0, 0, scale);
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }
}
