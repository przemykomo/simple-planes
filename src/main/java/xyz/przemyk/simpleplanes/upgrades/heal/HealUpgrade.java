package xyz.przemyk.simpleplanes.upgrades.heal;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.collection.DefaultedList;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

public class HealUpgrade extends Upgrade {

    public HealUpgrade(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.HEAL, planeEntity);
    }

    private int cooldown = 10;

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
        if (cooldown > 0) {
            --cooldown;
        } else {
            return true;
        }
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
        int health = planeEntity.getHealth();
        int m = planeEntity.getMaxHealth() * 2;
        if (health < m) {
            int heal = planeEntity.getOnGround() ? 2 : 1;
            planeEntity.setHealth(Math.min(health + heal, m));
        }
        planeEntity.health_timer = 0;
    }
}
