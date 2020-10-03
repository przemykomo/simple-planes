package xyz.przemyk.simpleplanes.upgrades.energy;

import net.minecraft.block.Blocks;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.render.EngineModel;

import static xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades.LAVA_ENGINE;

public class LavaEngine extends AbstractEngine {

    public LavaEngine(PlaneEntity planeEntity) {
        super(LAVA_ENGINE, planeEntity);
    }


    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider buffer, int packedLight, float partialticks) {
        EngineModel.renderEngine(planeEntity, partialticks, matrixStack, buffer, packedLight, Blocks.BLAST_FURNACE);
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        super.deserializeNBT(nbt);
        planeEntity.setMaxFuel(SimplePlanesMod.CONFIG.getConfig().LAVA_MAX_FUEL);
    }

    @Override
    public void onApply(ItemStack itemStack, PlayerEntity playerEntity) {
        super.onApply(itemStack, playerEntity);
        planeEntity.setMaxFuel(SimplePlanesMod.CONFIG.getConfig().LAVA_MAX_FUEL);
    }
}
