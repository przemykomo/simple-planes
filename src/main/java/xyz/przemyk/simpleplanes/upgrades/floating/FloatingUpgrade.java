package xyz.przemyk.simpleplanes.upgrades.floating;

import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

import com.mojang.blaze3d.matrix.MatrixStack;

import xyz.przemyk.simpleplanes.MathUtil;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

public class FloatingUpgrade extends Upgrade
{
    public static final ResourceLocation TEXTURE = new ResourceLocation("simpleplanes", "textures/plane_upgrades/floating.png");
    public static final ResourceLocation LARGE_TEXTURE = new ResourceLocation("simpleplanes", "textures/plane_upgrades/floating_large.png");

    public FloatingUpgrade(PlaneEntity planeEntity)
    {
        super(SimplePlanesUpgrades.FLOATING.get(), planeEntity);
    }

    @Override
    public boolean tick()
    {
        if (planeEntity.isAboveWater())
        {

            Vector3d motion = planeEntity.getMotion();
            double f = 1;
            double y = MathUtil.lerp(1, motion.y, Math.max(motion.y, 0));
            planeEntity.setMotion(motion.x * f, y, motion.z * f);
            if (planeEntity.world.getBlockState(new BlockPos(planeEntity.getPositionVec().add(0, 0.5, 0))).getBlock() == Blocks.WATER)
            {
                planeEntity.setMotion(planeEntity.getMotion().add(0, 0.04, 0));
            }
        }
        return false;
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight, float partialTicks)
    {
        if (planeEntity.isLarge())
        {
            LargeFloatingModel.INSTANCE
                    .render(matrixStack, buffer.getBuffer(LargeFloatingModel.INSTANCE.getRenderType(LARGE_TEXTURE)), packedLight, OverlayTexture.NO_OVERLAY,
                            1.0F, 1.0F, 1.0F, 1.0F);
        }
        else
        {
            FloatingModel.INSTANCE
                    .render(matrixStack, buffer.getBuffer(FloatingModel.INSTANCE.getRenderType(TEXTURE)), packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F,
                            1.0F, 1.0F);
        }
    }
}
