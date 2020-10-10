package xyz.przemyk.simpleplanes.upgrades.heal;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
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
    public NBTTagCompound serializeNBT() {
        NBTTagCompound compoundNBT = new NBTTagCompound();
        compoundNBT.setInteger("cooldown", cooldown);
        return compoundNBT;
    }

    @Override
    public void deserializeNBT(NBTTagCompound compoundNBT) {
        cooldown = compoundNBT.getInteger("cooldown");
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
    public void render(float partialticks, float scale) {

    }

    @Override
    public NonNullList<ItemStack> getDrops() {
        return NonNullList.create();
    }

    @Override
    public void onApply(ItemStack itemStack, EntityPlayer playerEntity) {
        int health = planeEntity.getHealth();
        int m = planeEntity.getMaxHealth() * 2;
        if (health < m) {
            int heal = planeEntity.getOnGround() ? 2 : 1;
            planeEntity.setHealth(Math.min(health + heal, m));
        }
        planeEntity.health_timer = 0;
    }
}
