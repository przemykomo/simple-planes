package xyz.przemyk.simpleplanes.upgrades.tnt;

import net.minecraft.block.Blocks;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.render.BackSeatBlockModel;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

public class TNTUpgrade extends Upgrade {
    public static final Identifier TEXTURE = new Identifier(SimplePlanesMod.MODID, "textures/plane_upgrades/tnt.png");

    public TNTUpgrade(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.TNT, planeEntity);
    }

    @Override
    public boolean onItemRightClick(PlayerEntity player, World world, Hand hand, ItemStack itemStack) {
        if (itemStack.getItem() == Items.FLINT_AND_STEEL) {
            TntEntity tntEntity = new TntEntity(planeEntity.world, planeEntity.getX() - 1.0, planeEntity.getY(), planeEntity.getZ(),
                player);
            tntEntity.setVelocity(planeEntity.getVelocity());
            planeEntity.world.spawnEntity(tntEntity);
            itemStack.damage(1, player, playerEntity -> playerEntity.sendToolBreakStatus(hand));
            return true;
        }

        return false;
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider buffer, int packedLight, float partialticks) {
        BackSeatBlockModel.renderBlock(planeEntity, partialticks, matrixStack, buffer, packedLight, Blocks.TNT.getDefaultState());
    }
}
