package xyz.przemyk.simpleplanes.upgrades.island;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

public class IslandUpgrade extends Upgrade {

    public IslandUpgrade(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.ISLAND.get(), planeEntity);
    }

    private int cooldown = 8;
    private int height = 128;

    @SuppressWarnings("ConstantConditions")
    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.putInt("cooldown", cooldown);
        return compoundNBT;
    }

    @Override
    public void deserializeNBT(CompoundNBT compoundNBT) {
        cooldown = compoundNBT.getInt("cooldown");
    }

    @Override
    public boolean tick() {
        if (cooldown <= 0 || (planeEntity.getOnGround() && planeEntity.not_moving_time > 50)) {
            return true;
        }
        BlockPos.Mutable blockPos = planeEntity.getPosition().toMutable();
        int planeHeight = blockPos.getY();
        if (planeHeight < height) {
            height = Math.max(planeHeight - 1, 1);
        }
        blockPos.setY(height);
        if (planeEntity.world.getBlockState(blockPos).isAir(planeEntity.world, blockPos)) {
            planeEntity.world.setBlockState(blockPos, Blocks.BLACK_CONCRETE.getDefaultState());
            --cooldown;
        }
        planeEntity.setMotion(planeEntity.getMotion().scale(0.9));
        return false;
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight, float partialticks) {

    }

    @Override
    public ItemStack getDrops() {
        return ItemStack.EMPTY;
    }

    @Override
    public void onApply(ItemStack itemStack, PlayerEntity playerEntity) {
        height = Math.max(planeEntity.getPosition().getY() - 1, 1);
    }
}
