package xyz.przemyk.simpleplanes.integration;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ObjectHolder;
import vazkii.botania.api.mana.ManaItemHandler;
import xyz.przemyk.simpleplanes.Config;
import xyz.przemyk.simpleplanes.PlaneMaterial;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.HelicopterEntity;
import xyz.przemyk.simpleplanes.entities.LargePlaneEntity;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.items.InformationItem;
import xyz.przemyk.simpleplanes.items.PlaneItem;
import xyz.przemyk.simpleplanes.setup.SimplePlanesEntities;
import xyz.przemyk.simpleplanes.setup.SimplePlanesItems;
import xyz.przemyk.simpleplanes.setup.SimplePlanesMaterials;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;
import xyz.przemyk.simpleplanes.upgrades.UpgradeType;
import xyz.przemyk.simpleplanes.upgrades.energy.AbstractEngine;

import static xyz.przemyk.simpleplanes.setup.SimplePlanesItems.SIMPLE_PLANES_ITEM_GROUP;

public class BotaniaIntegration implements IModIntegration {
    @ObjectHolder(SimplePlanesMod.MODID + ":mana")
    public static final PlaneMaterial MANA = null;
    public static UpgradeType MANA_UPGRADE;
    RegistryObject<Item> MANA_UPGRADE_ITEM = null;

    @Override
    public void init() {
        System.out.println("Botania To The Sky");

        String name = "mana";
//        SimplePlanesRegistries.PLANE_MATERIALS.register(new PlaneMaterial(name));
        SimplePlanesItems.ITEMS.register(name + "_plane", () -> new PlaneItem(new Item.Properties().group(SIMPLE_PLANES_ITEM_GROUP), world -> {
            PlaneEntity planeEntity = new PlaneEntity(SimplePlanesEntities.PLANE.get(), world, SimplePlanesMaterials.getMaterial(name));
            add_mana(planeEntity);
            return planeEntity;
        }));
        SimplePlanesItems.ITEMS.register(name + "_large_plane", () -> new PlaneItem(new Item.Properties().group(SIMPLE_PLANES_ITEM_GROUP), world -> {
            PlaneEntity planeEntity = new LargePlaneEntity(SimplePlanesEntities.LARGE_PLANE.get(), world, SimplePlanesMaterials.getMaterial(name));
            add_mana(planeEntity);
            return planeEntity;
        }));
        SimplePlanesItems.ITEMS.register(name + "_helicopter", () -> new PlaneItem(new Item.Properties().group(SIMPLE_PLANES_ITEM_GROUP), world -> {
            PlaneEntity planeEntity = new HelicopterEntity(SimplePlanesEntities.HELICOPTER.get(), world, SimplePlanesMaterials.getMaterial(name));
            add_mana(planeEntity);
            return planeEntity;
        }));
        MANA_UPGRADE_ITEM = SimplePlanesItems.ITEMS
            .register("mana_upgrade", () -> new InformationItem(new TranslationTextComponent("description.simpleplanes.mana_upgrade")));
//        SimplePlanesRegistries.UPGRADE_TYPES.register(new UpgradeType(daisy,ManaUpgrade::new));
        FMLJavaModLoadingContext.get().getModEventBus().register(this);


    }

    private void add_mana(PlaneEntity planeEntity) {
        Upgrade upgrade = MANA_UPGRADE.instanceSupplier.apply(planeEntity);
        upgrade.onApply(null, null);
        planeEntity.upgrades.put(MANA_UPGRADE.getRegistryName(), upgrade);
    }


    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void registerUpgrade(RegistryEvent.Register<UpgradeType> event) {
//        ResourceLocation daisy = new ResourceLocation("botania", "pure_daisy");
        MANA_UPGRADE = new UpgradeType(MANA_UPGRADE_ITEM.get(), ManaUpgrade::new, planeEntity -> !planeEntity.isImmuneToFire());
        MANA_UPGRADE.setRegistryName("mana");
        event.getRegistry().register(MANA_UPGRADE);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void registerMaterial(RegistryEvent.Register<PlaneMaterial> event) {
        String name = "mana";
        event.getRegistry().register(new PlaneMaterial(name, false).setRegistryName(SimplePlanesMod.MODID, name));
    }

    public static class ManaUpgrade extends AbstractEngine {

        public ManaUpgrade(UpgradeType type, PlaneEntity planeEntity) {
            super(type, planeEntity);
        }

        public ManaUpgrade(PlaneEntity planeEntity) {
            super(MANA_UPGRADE, planeEntity);
        }

        @Override
        public NonNullList<ItemStack> getDrops() {
            return NonNullList.create();
        }

        @Override
        public boolean tick() {
            if (planeEntity.world.isRemote) {
                return false;
            }
            PlayerEntity player = planeEntity.getPlayer();
            if (planeEntity.getFuel() <= 0 && player != null) {
                ItemStack itemStack = planeEntity.getItemStack();
                boolean got_mana = ManaItemHandler.INSTANCE.getValue().requestManaExactForTool(itemStack, player, Config.MANA_COST.get(), true);
                if (got_mana) {
                    planeEntity.addFuelMaxed(Config.FLY_TICKS_PER_MANA.get());
                }
            }
            return false;
        }

        @Override
        public void onApply(ItemStack itemStack, PlayerEntity playerEntity) {
            super.onApply(itemStack,playerEntity);
            planeEntity.setMaterial("mana");
        }

        @Override
        public void render(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight, float partialticks) {

        }

    }
}
