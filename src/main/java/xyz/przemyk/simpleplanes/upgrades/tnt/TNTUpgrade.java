package xyz.przemyk.simpleplanes.upgrades.tnt;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesEntities;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.LargeUpgrade;

public class TNTUpgrade extends LargeUpgrade {

    public TNTUpgrade(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.TNT.get(), planeEntity);
    }

    @Override
    public void onApply(ItemStack itemStack, PlayerEntity playerEntity) {
        super.onApply(itemStack, playerEntity);
    }

    @Override
    public void onItemRightClick(PlayerInteractEvent.RightClickItem event) {
        ItemStack itemStack = event.getPlayer().getItemInHand(event.getHand());
        if (itemStack.getItem() == Items.FLINT_AND_STEEL) {
            TNTEntity tntEntity = new TNTEntity(planeEntity.level, planeEntity.getX() - 1.0, planeEntity.getY(), planeEntity.getZ(),
                event.getPlayer());
            tntEntity.setDeltaMovement(planeEntity.getDeltaMovement());
            planeEntity.level.addFreshEntity(tntEntity);
            itemStack.hurtAndBreak(1, event.getPlayer(), playerEntity -> playerEntity.broadcastBreakEvent(event.getHand()));
            remove();
        }
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight, float partialTicks) {
        matrixStack.pushPose();
        EntityType<?> entityType = planeEntity.getType();

        if (entityType == SimplePlanesEntities.HELICOPTER.get()) {
            matrixStack.translate(0, 0, -0.15);
        } else if (entityType == SimplePlanesEntities.LARGE_PLANE.get()) {
            matrixStack.translate(0, 0, 0.1);
        }

        matrixStack.mulPose(Vector3f.ZP.rotationDegrees(180));
        matrixStack.translate(-0.4, -1, 0.3);
        matrixStack.scale(0.82f, 0.82f, 0.82f);
        BlockState state = Blocks.TNT.defaultBlockState();
        Minecraft.getInstance().getBlockRenderer().renderBlock(state, matrixStack, buffer, packedLight, OverlayTexture.NO_OVERLAY, EmptyModelData.INSTANCE);
        matrixStack.popPose();
    }

    @Override
    public void writePacket(PacketBuffer buffer) {}

    @Override
    public void readPacket(PacketBuffer buffer) {}

    @Override
    public void dropItems() {
        planeEntity.spawnAtLocation(Items.TNT);
    }
}
