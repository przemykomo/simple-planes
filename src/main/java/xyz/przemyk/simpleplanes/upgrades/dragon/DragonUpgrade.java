package xyz.przemyk.simpleplanes.upgrades.dragon;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

public class DragonUpgrade extends Upgrade {

    public DragonUpgrade(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.DRAGON, planeEntity);
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        super.deserializeNBT(nbt);
        planeEntity.setMaxSpeed(2f);
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider buffer, int packedLight, float partialticks) {
        DragonModel.renderDragon(planeEntity, partialticks, matrixStack, buffer, packedLight);
    }

    @Override
    public void onApply(ItemStack itemStack, PlayerEntity playerEntity) {
        if (!planeEntity.world.isClient) {
            planeEntity.setMaxSpeed(2f);
        }
    }
}
