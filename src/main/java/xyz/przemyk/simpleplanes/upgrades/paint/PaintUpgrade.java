package xyz.przemyk.simpleplanes.upgrades.paint;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;

import com.mojang.blaze3d.matrix.MatrixStack;

import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

public class PaintUpgrade extends Upgrade
{
    public static Map<ResourceLocation,String> PAINTS = new HashMap<>();

    public static void init(){
        PAINTS.put(Items.GOLD_BLOCK.getRegistryName(),"gold");
        PAINTS.put(Items.field_234794_rw_.getRegistryName(),"netherite");
    }
    public PaintUpgrade(PlaneEntity planeEntity)
    {
        super(SimplePlanesUpgrades.PAINT.get(), planeEntity);
    }

    @Override
    public boolean tick()
    {
        return false;
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight, float partialticks)
    {
    }

    @Override
    public void onApply(ItemStack itemStack, PlayerEntity playerEntity)
    {
        planeEntity.setMaterial(PAINTS.get(itemStack.getItem().getRegistryName()));
        itemStack.setCount(0);
    }
}
