//package xyz.przemyk.simpleplanes.upgrades.energy;
//
//import com.mojang.blaze3d.matrix.MatrixStack;
//import net.minecraft.block.Blocks;
//import net.minecraft.client.renderer.IRenderTypeBuffer;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.item.ItemStack;
//import net.minecraft.nbt.NBTTagCompound;
//import net.minecraftforge.event.entity.player.PlayerInteractEvent;
//import xyz.przemyk.simpleplanes.Config;
//import xyz.przemyk.simpleplanes.entities.PlaneEntity;
//import xyz.przemyk.simpleplanes.render.EngineModel;
//
//import static xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades.LAVA_ENGINE;
//
//public class LavaEngine extends AbstractEngine {
//
//    public LavaEngine(PlaneEntity planeEntity) {
//        super(LAVA_ENGINE.get(), planeEntity);
//    }
//
//    @Override
//    public boolean onItemRightClick(PlayerInteractEvent.RightClickItem event) {
//        return super.onItemRightClick(event);
//    }
//
//    @Override
//    public void render(float partialticks) {
//        EngineModel.renderEngine(planeEntity, partialticks, matrixStack, buffer, packedLight, Blocks.BLAST_FURNACE);
//    }
//
//    @Override
//    public void deserializeNBT(NBTTagCompound nbt) {
//        super.deserializeNBT(nbt);
//        planeEntity.setMaxFuel(Config.INSTANCE.LAVA_MAX_FUEL.get());
//    }
//
//    @Override
//    public void onApply(ItemStack itemStack, EntityPlayer playerEntity) {
//        super.onApply(itemStack, playerEntity);
//        planeEntity.setMaxFuel(Config.INSTANCE.LAVA_MAX_FUEL.get());
//    }
//}
