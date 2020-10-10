//package xyz.przemyk.simpleplanes.upgrades.energy;
//
//import com.mojang.blaze3d.matrix.MatrixStack;
//import net.minecraft.block.Blocks;
//import net.minecraft.client.renderer.IRenderTypeBuffer;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.item.ItemStack;
//import net.minecraft.item.Items;
//import net.minecraft.nbt.NBTTagCompound;
//import net.minecraftforge.event.entity.player.PlayerInteractEvent;
//import xyz.przemyk.simpleplanes.Config;
//import xyz.przemyk.simpleplanes.entities.PlaneEntity;
//import xyz.przemyk.simpleplanes.render.EngineModel;
//import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
//
//public class PowerCell extends AbstractEngine {
//
//    public PowerCell(PlaneEntity planeEntity) {
//        super(SimplePlanesUpgrades.POWER_CELL.get(), planeEntity);
//    }
//
//    @Override
//    public boolean onItemRightClick(PlayerInteractEvent.RightClickItem event) {
//        EntityPlayer player = event.getPlayer();
//        ItemStack itemStack = event.getItemStack();
//        if (!player.world.isRemote &&planeEntity.getFuel() < Config.INSTANCE.FLY_TICKS_PER_COAL.get() / 4) {
//            //func_230235_a_ - contains
//            if ((itemStack.getItem() == Items.REDSTONE)) {
//                planeEntity.addFuelMaxed();
//                if (!player.isCreative()) {
//                    itemStack.shrink(1);
//                }
//            }
//        }
//        return false;
//    }
//
//    @Override
//    public void render(float partialticks) {
//        EngineModel.renderEngine(planeEntity,partialticks,matrixStack,buffer,packedLight, Blocks.REDSTONE_LAMP);
//    }
//    @Override
//    public void deserializeNBT(NBTTagCompound nbt) {
//        super.deserializeNBT(nbt);
//        planeEntity.setMaxFuel(Config.INSTANCE.ENERGY_MAX_FUEL.get());
//    }
//
//    @Override
//    public void onApply(ItemStack itemStack, EntityPlayer playerEntity) {
//        super.onApply(itemStack, playerEntity);
//        planeEntity.setMaxFuel(Config.INSTANCE.ENERGY_MAX_FUEL.get());
//    }
//
//}