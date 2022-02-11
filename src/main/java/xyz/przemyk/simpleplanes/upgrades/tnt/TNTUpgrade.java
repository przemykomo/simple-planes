package xyz.przemyk.simpleplanes.upgrades.tnt;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.network.FriendlyByteBuf;
import com.mojang.math.Vector3f;
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
    public void onApply(ItemStack itemStack, Player playerEntity) {
        super.onApply(itemStack, playerEntity);
    }

    @Override
    public void onItemRightClick(PlayerInteractEvent.RightClickItem event) {
        ItemStack itemStack = event.getPlayer().getItemInHand(event.getHand());
        if (itemStack.getItem() == Items.FLINT_AND_STEEL) {
            itemStack.hurtAndBreak(1, event.getPlayer(), playerEntity -> playerEntity.broadcastBreakEvent(event.getHand()));
            dropAsPayload();
        }
    }

    @Override
    public void render(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, float partialTicks) {
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
        Minecraft.getInstance().getBlockRenderer().renderSingleBlock(state, matrixStack, buffer, packedLight, OverlayTexture.NO_OVERLAY, EmptyModelData.INSTANCE);
        matrixStack.popPose();
    }

    @Override
    public void writePacket(FriendlyByteBuf buffer) {}

    @Override
    public void readPacket(FriendlyByteBuf buffer) {}

    @Override
    public void onRemoved() {
        planeEntity.spawnAtLocation(Items.TNT);
    }

    @Override
    public boolean canBeDroppedAsPayload() {
        return true;
    }

    @Override
    public void dropAsPayload() {
        PrimedTnt tntEntity = new PrimedTnt(planeEntity.level, planeEntity.getX() - 1.0, planeEntity.getY(), planeEntity.getZ(), planeEntity.getPlayer());
        tntEntity.setDeltaMovement(planeEntity.getDeltaMovement());
        planeEntity.level.addFreshEntity(tntEntity);
        remove();
    }
}
