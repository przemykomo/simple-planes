package xyz.przemyk.simpleplanes.upgrades.dragon;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

import com.mojang.blaze3d.matrix.MatrixStack;

import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

public class DragonUpgrade extends Upgrade
{
    public static final DragonModel DRAGON_MODEL = new DragonModel();

    public DragonUpgrade(PlaneEntity planeEntity)
    {
        super(SimplePlanesUpgrades.DRAGON.get(), planeEntity);
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt)
    {
        super.deserializeNBT(nbt);
        planeEntity.setMaxSpeed(1f);
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight, float partialticks)
    {
        DragonModel.renderDragon(planeEntity, partialticks, matrixStack, buffer, packedLight);
    }

    @Override
    public void onApply(ItemStack itemStack, PlayerEntity playerEntity)
    {
        if (!planeEntity.world.isRemote)
        {
            planeEntity.setMaxSpeed(1f);
        }
    }
}
