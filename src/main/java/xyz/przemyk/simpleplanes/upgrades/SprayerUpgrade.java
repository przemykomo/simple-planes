package xyz.przemyk.simpleplanes.upgrades;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import xyz.przemyk.simpleplanes.entities.furnacePlane.FurnacePlaneEntity;

public class SprayerUpgrade extends Upgrade {
    public static final SprayModel model = new SprayModel();
    //TODO: different texture
    public static final ResourceLocation TEXTURE_ACACIA = new ResourceLocation("simpleplanes", "textures/entity/plane/furnace/acacia.png");

    public SprayerUpgrade(UpgradeType type, FurnacePlaneEntity planeEntity) {
        super(type, planeEntity);
    }

    private int i = 0;

    @Override
    public void tick() {
        if (!planeEntity.world.isRemote() && ++i % 10 == 0) {
            Vector2f front = planeEntity.getHorizontalFrontPos();
            ((ServerWorld) planeEntity.world).spawnParticle(ParticleTypes.BARRIER, planeEntity.getPosX() + front.x, planeEntity.getPosY() + 1.0, planeEntity.getPosZ() + front.y, 0, 0, 0, 0, 0.0);
        }
    }

    @Override
    public void onItemRightClick(PlayerInteractEvent.RightClickItem event) {

    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight) {
        IVertexBuilder ivertexbuilder = buffer.getBuffer(model.getRenderType(TEXTURE_ACACIA));
        model.render(matrixStack, ivertexbuilder, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }
}
