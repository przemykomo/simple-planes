package xyz.przemyk.simpleplanes.upgrades.cloud;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

import static xyz.przemyk.simpleplanes.upgrades.cloud.CloudBlock.placeCloud;

public class CloudUpgrade extends Upgrade {

    public CloudUpgrade(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.CLOUD, planeEntity);
    }

    private int cooldown = 16;
    private int height = 128;

    @SuppressWarnings("ConstantConditions")
    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compoundNBT = new CompoundTag();
        compoundNBT.putInt("cooldown", cooldown);
        return compoundNBT;
    }

    @Override
    public void deserializeNBT(CompoundTag compoundNBT) {
        cooldown = compoundNBT.getInt("cooldown");
    }

    @Override
    public boolean tick() {
        if (cooldown <= 0 || (planeEntity.getOnGround() && planeEntity.not_moving_time > 100)) {
            return true;
        }
        planeEntity.setVelocity(planeEntity.getVelocity().multiply(0.9));
        if (planeEntity.age % 5 != 0 || planeEntity.world.isClient) {
            return false;
        }
        BlockPos.Mutable blockPos = planeEntity.getBlockPos().mutableCopy();
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
    public void render(MatrixStack matrixStack, VertexConsumerProvider buffer, int packedLight, float partialticks) {

    }

    @Override
    public DefaultedList<ItemStack> getDrops() {
        return DefaultedList.of();
    }

    @Override
    public void onApply(ItemStack itemStack, PlayerEntity playerEntity) {
        height = Math.max(planeEntity.getBlockPos().getY() - 1, 1);
    }
}
