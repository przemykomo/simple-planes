package xyz.przemyk.simpleplanes.upgrades.dragon;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

public class DragonUpgrade extends Upgrade {

    public DragonUpgrade(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.DRAGON, planeEntity);
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        super.deserializeNBT(nbt);
        planeEntity.setMaxSpeed(2f);
    }

    @Override
    public void render(float partialticks, float scale) {
        DragonModel.renderDragon(planeEntity, partialticks);
    }

    @Override
    public void onApply(ItemStack itemStack, EntityPlayer playerEntity) {
        if (!planeEntity.world.isRemote) {
            planeEntity.setMaxSpeed(2f);
        }
    }
}
