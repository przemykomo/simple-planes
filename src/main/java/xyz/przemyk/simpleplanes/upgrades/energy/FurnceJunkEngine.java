package xyz.przemyk.simpleplanes.upgrades.energy;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import xyz.przemyk.simpleplanes.Config;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.render.EngineModel;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;

import static xyz.przemyk.simpleplanes.PlanesEvents.NOT_COAL_TAG;

public class FurnceJunkEngine extends CoalEngine {

    public FurnceJunkEngine(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.SMOKER_ENGINE.get(), planeEntity);
    }

    @Override
    public boolean onItemRightClick(PlayerInteractEvent.RightClickItem event) {
        PlayerEntity player = event.getPlayer();
        ItemStack itemStack = event.getItemStack();
        if (!player.world.isRemote && planeEntity.getFuel() < Config.FLY_TICKS_PER_COAL.get() / 4) {
            //func_230235_a_ - contains
            int burnTime = ForgeHooks.getBurnTime(itemStack);
            if (burnTime > 0) {
                int fuel = (int) ((burnTime / 1600f) * Config.FLY_TICKS_PER_COAL.get());
                if (!ItemTags.createOptional(NOT_COAL_TAG).contains(itemStack.getItem())) {
                    planeEntity.addFuelMaxed(fuel);
                    if (!player.isCreative()) {
                        itemStack.shrink(1);
                    }
                }
            }
        }
        return false;
    }


    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight, float partialticks) {
        EngineModel.renderEngine(planeEntity, partialticks, matrixStack, buffer, packedLight, Blocks.SMOKER);
    }
}
