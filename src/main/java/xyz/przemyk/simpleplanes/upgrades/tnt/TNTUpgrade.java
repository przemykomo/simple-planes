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
import xyz.przemyk.simpleplanes.entities.LargePlaneEntity;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesEntities;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

public class TNTUpgrade extends Upgrade {

    public TNTUpgrade(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.TNT.get(), planeEntity);
        if (planeEntity instanceof LargePlaneEntity) {
            ((LargePlaneEntity) planeEntity).hasTNT = true;
        }
    }

    @Override
    public void onApply(ItemStack itemStack, PlayerEntity playerEntity) {
        super.onApply(itemStack, playerEntity);
    }

    @Override
    public void onItemRightClick(PlayerInteractEvent.RightClickItem event) {
        ItemStack itemStack = event.getPlayer().getHeldItem(event.getHand());
        if (itemStack.getItem() == Items.FLINT_AND_STEEL) {
            TNTEntity tntEntity = new TNTEntity(planeEntity.world, planeEntity.getPosX() - 1.0, planeEntity.getPosY(), planeEntity.getPosZ(),
                event.getPlayer());
            tntEntity.setMotion(planeEntity.getMotion());
            planeEntity.world.addEntity(tntEntity);
            itemStack.damageItem(1, event.getPlayer(), playerEntity -> playerEntity.sendBreakAnimation(event.getHand()));
            if (planeEntity instanceof LargePlaneEntity) {
                ((LargePlaneEntity) planeEntity).hasTNT = false;
            }
            remove();
        }
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight, float partialTicks) {
        matrixStack.push();
        EntityType<?> entityType = planeEntity.getType();

        if (entityType == SimplePlanesEntities.HELICOPTER.get()) {
            matrixStack.translate(0, -0.8, 0.65);
        } else if (entityType == SimplePlanesEntities.LARGE_PLANE.get()) {
            matrixStack.translate(0, 0, 0.1);
        }

        matrixStack.rotate(Vector3f.ZP.rotationDegrees(180));
        matrixStack.translate(-0.4, -1, 0.3);
        matrixStack.scale(0.82f, 0.82f, 0.82f);
        BlockState state = Blocks.TNT.getDefaultState();
        Minecraft.getInstance().getBlockRendererDispatcher().renderBlock(state, matrixStack, buffer, packedLight, OverlayTexture.NO_OVERLAY, EmptyModelData.INSTANCE);
        matrixStack.pop();
    }

    @Override
    public void writePacket(PacketBuffer buffer) {}

    @Override
    public void readPacket(PacketBuffer buffer) {}

    @Override
    public void dropItems() {
        planeEntity.entityDropItem(Items.TNT);
    }
}
