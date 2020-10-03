package xyz.przemyk.simpleplanes.upgrades.paint;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

import java.util.HashMap;
import java.util.Map;

public class PaintUpgrade extends Upgrade {
    public static Map<Identifier, String> PAINTS = new HashMap<>();

    public static void init() {
//        PAINTS.put(Items.GOLD_BLOCK.getRegistryName(), "gold");
//        PAINTS.put(Items.NETHERITE_BLOCK.getRegistryName(), "netherite");
    }

    public PaintUpgrade(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.PAINT, planeEntity);
    }

    @Override
    public boolean tick() {
        return false;
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider buffer, int packedLight, float partialticks) {
    }

    @Override
    public void onApply(ItemStack itemStack, PlayerEntity playerEntity) {
//        planeEntity.setMaterial(PAINTS.get(itemStack.getItem().getRegistryName()));
//        itemStack.setCount(0);
    }
}
