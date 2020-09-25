package xyz.przemyk.simpleplanes.upgrades.heal;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

public class HealUpgrade extends Upgrade {

    public HealUpgrade(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.HEAL.get(), planeEntity);
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
    public boolean tick() {
        if (cooldown > 0) {
            --cooldown;
        } else {
            return true;
        }
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
        int health = planeEntity.getHealth();
        int m = planeEntity.getMaxHealth() * 2;
        if (health < m) {
            int heal = planeEntity.getOnGround() ? 2 : 1;
            planeEntity.setHealth(Math.min(health + heal, m));
        }
        planeEntity.health_timer = 0;
    }
}
