package xyz.przemyk.simpleplanes.upgrades.heal;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

public class HealingUpgrade extends Upgrade {

    public HealingUpgrade(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.HEALING.get(), planeEntity);
    }

    private int cooldown = 10;

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
    public void tick() {
        if (cooldown > 0) {
            --cooldown;
        } else {
            remove();
        }
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight, float partialTicks) {}

    @Override
    public void onApply(ItemStack itemStack, PlayerEntity playerEntity) {
        int health = planeEntity.getHealth();
        int m = planeEntity.getMaxHealth() * 2;
        if (health < m) {
            int heal = planeEntity.getOnGround() ? 2 : 1;
            planeEntity.setHealth(Math.min(health + heal, m));
        }
        planeEntity.health_timer = 0;
    }

    @Override
    public void writePacket(PacketBuffer buffer) {}

    @Override
    public void readPacket(PacketBuffer buffer) {}
}
