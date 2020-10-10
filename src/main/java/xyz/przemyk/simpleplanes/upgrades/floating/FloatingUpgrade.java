package xyz.przemyk.simpleplanes.upgrades.floating;

import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import xyz.przemyk.simpleplanes.MathUtil;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.HelicopterEntity;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

public class FloatingUpgrade extends Upgrade {
    public static final ResourceLocation TEXTURE = new ResourceLocation(SimplePlanesMod.MODID, "textures/plane_upgrades/floating.png");
    public static final ResourceLocation LARGE_TEXTURE = new ResourceLocation(SimplePlanesMod.MODID, "textures/plane_upgrades/floating_large.png");
    //    public static final ResourceLocation HELICOPTER_TEXTURE = new ResourceLocation(SimplePlanesMod.MODID, "textures/plane_upgrades/floating_large.png");
    public static final ResourceLocation HELICOPTER_TEXTURE = new ResourceLocation("textures/blocks/wool_colored_white.png");

    public FloatingUpgrade(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.FLOATING, planeEntity);
    }

    @Override
    public boolean tick() {
        if (planeEntity.isAboveWater()) {
            Vec3d motion = planeEntity.getMotion();
            double f = 1;
            double y = MathUtil.lerp(1, motion.y, Math.max(motion.y, 0));
            planeEntity.setMotion(motion.x * f, y, motion.z * f);
            if (planeEntity.world.getBlockState(new BlockPos(planeEntity.getPositionVector().add(0, 0.5, 0))).getBlock() == Blocks.WATER) {
                planeEntity.setMotion(planeEntity.getMotion().add(0, 0.04, 0));
            }
        }
        return false;
    }

    @Override
    public void render(float partialTicks, float scale) {
        if (planeEntity.isLarge()) {
            if (planeEntity instanceof HelicopterEntity) {
                HelicopterFloatingModel.INSTANCE
                    .render(planeEntity, 1.0F, 1.0F,
                        1.0F, 1.0F, 0, scale);
            } else {
                LargeFloatingModel.INSTANCE
                    .render(planeEntity, 1.0F, 1.0F,
                        1.0F, 1.0F, 0, scale);
            }
        } else {
            FloatingModel.INSTANCE
                .render(planeEntity, 1.0F, 1.0F,
                    1.0F, 1.0F, 0, scale);
        }
    }

    @Override
    public ResourceLocation getTexture() {
        if (planeEntity.isLarge()) {
            if (planeEntity instanceof HelicopterEntity) {
                return HELICOPTER_TEXTURE;
            }
            return LARGE_TEXTURE;
        }
        return TEXTURE;
    }
}
