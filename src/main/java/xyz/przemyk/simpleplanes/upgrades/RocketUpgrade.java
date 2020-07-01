package xyz.przemyk.simpleplanes.upgrades;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.projectile.AbstractFireballEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import xyz.przemyk.simpleplanes.entities.furnacePlane.FurnacePlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;

import java.util.Random;

import static net.minecraft.item.Items.*;

public class RocketUpgrade extends Upgrade {

    public static final RocketModel ROCKET_MODEL = new RocketModel();
    //TODO: different texture
    public static final ResourceLocation TEXTURE = new ResourceLocation("simpleplanes", "textures/plane_upgrades/rocket.png");

    public RocketUpgrade(FurnacePlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.ROCKET_UPGRADE_TYPE.get(),planeEntity);
    }

    @Override
    public void tick() {

    }


    @Override
    public void onItemRightClick(PlayerInteractEvent.RightClickItem event) {
        ItemStack itemStack = event.getPlayer().getHeldItem(event.getHand());
        Vector3d motion = planeEntity.getMotion();

        float pitch = planeEntity.getPitch(motion);
        if(planeEntity.getOnGround())
        {
            pitch = 30;
        }
        motion = planeEntity.getVec(planeEntity.rotationYaw, pitch)
                .scale(Math.max(0.25,motion.length()));

        if (itemStack.getItem().equals(GUNPOWDER)) {
            if (!event.getPlayer().isCreative()) {
                itemStack.shrink(1);
            }
            {
                Vector3d m = planeEntity.getMotion();
                planeEntity.setMotion(m.add(motion.scale(0.2/motion.length())));
            }
        }
    }


    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight) {
        IVertexBuilder ivertexbuilder = buffer.getBuffer(ROCKET_MODEL.getRenderType(TEXTURE));
        ROCKET_MODEL.render(matrixStack, ivertexbuilder, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }

    private Vector3d getPos(Random random){
        return Vector3d.ZERO;
    }
}
