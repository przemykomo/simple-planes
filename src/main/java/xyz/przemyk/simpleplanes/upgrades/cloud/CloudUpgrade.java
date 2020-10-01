package xyz.przemyk.simpleplanes.upgrades.cloud;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

import static xyz.przemyk.simpleplanes.upgrades.cloud.CloudBlock.placeCloud;

public class CloudUpgrade extends Upgrade {

    public CloudUpgrade(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.CLOUD.get(), planeEntity);
    }

    private int cooldown = 16;
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
        if (cooldown <= 0 || (planeEntity.getOnGround() && planeEntity.not_moving_time > 100)) {
            return true;
        }
        planeEntity.setMotion(planeEntity.getMotion().scale(0.9));
        if (planeEntity.ticksExisted % 5 != 0 || planeEntity.world.isRemote) {
            return false;
        }
        BlockPos.Mutable blockPos = planeEntity.getPosition().toMutable();
        int planeHeight = blockPos.getY();
        if (planeHeight < height) {
            height = Math.max(planeHeight - 1, 1);
        }
        blockPos.setY(height);
        --cooldown;
        placeCloud(blockPos, planeEntity.world);

        return false;
    }


    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight, float partialticks) {

    }

    @Override
    public NonNullList<ItemStack> getDrops() {
        return NonNullList.create();
    }

    @Override
    public void onApply(ItemStack itemStack, PlayerEntity playerEntity) {
        height = Math.max(planeEntity.getPosition().getY() - 1, 1);
    }
}
