package xyz.przemyk.simpleplanes.setup;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.blocks.ChairBlock;
import xyz.przemyk.simpleplanes.entities.BlockShip.ShipCreation;
import xyz.przemyk.simpleplanes.entities.HelicopterEntity;
import xyz.przemyk.simpleplanes.entities.LargePlaneEntity;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.items.InformationItem;
import xyz.przemyk.simpleplanes.items.PlaneItem;

//@ObjectHolder(SimplePlanesMod.MODID)
@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = SimplePlanesMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SimplePlanesBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, SimplePlanesMod.MODID);


    public static void init() {

        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }


    public static final RegistryObject<Block> CHAIR = BLOCKS.register("chair", ChairBlock::new);
    //
}
