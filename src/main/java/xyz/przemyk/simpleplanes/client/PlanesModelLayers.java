package xyz.przemyk.simpleplanes.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ShulkerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.client.render.ParachuteRenderer;
import xyz.przemyk.simpleplanes.client.render.PlaneRenderer;
import xyz.przemyk.simpleplanes.client.render.UpgradesModels;
import xyz.przemyk.simpleplanes.client.render.models.*;
import xyz.przemyk.simpleplanes.setup.SimplePlanesEntities;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.armor.*;
import xyz.przemyk.simpleplanes.upgrades.booster.BoosterModel;
import xyz.przemyk.simpleplanes.upgrades.booster.CargoBoosterModel;
import xyz.przemyk.simpleplanes.upgrades.booster.HeliBoosterModel;
import xyz.przemyk.simpleplanes.upgrades.booster.LargeBoosterModel;
import xyz.przemyk.simpleplanes.upgrades.engines.electric.CargoElectricEngineModel;
import xyz.przemyk.simpleplanes.upgrades.engines.electric.ElectricEngineModel;
import xyz.przemyk.simpleplanes.upgrades.engines.electric.HeliElectricEngineModel;
import xyz.przemyk.simpleplanes.upgrades.engines.electric.LargeElectricEngineModel;
import xyz.przemyk.simpleplanes.upgrades.engines.furnace.CargoFurnaceEngineModel;
import xyz.przemyk.simpleplanes.upgrades.engines.furnace.FurnaceEngineModel;
import xyz.przemyk.simpleplanes.upgrades.engines.furnace.HeliFurnaceEngineModel;
import xyz.przemyk.simpleplanes.upgrades.engines.furnace.LargeFurnaceEngineModel;
import xyz.przemyk.simpleplanes.upgrades.engines.liquid.CargoLiquidEngineModel;
import xyz.przemyk.simpleplanes.upgrades.engines.liquid.HeliLiquidEngineModel;
import xyz.przemyk.simpleplanes.upgrades.engines.liquid.LargeLiquidEngineModel;
import xyz.przemyk.simpleplanes.upgrades.engines.liquid.LiquidEngineModel;
import xyz.przemyk.simpleplanes.upgrades.floating.*;
import xyz.przemyk.simpleplanes.upgrades.seats.*;
import xyz.przemyk.simpleplanes.upgrades.shooter.HeliShooterModel;
import xyz.przemyk.simpleplanes.upgrades.shooter.LargeShooterModel;
import xyz.przemyk.simpleplanes.upgrades.shooter.ShooterModel;
import xyz.przemyk.simpleplanes.upgrades.solarpanel.CargoSolarPanelModel;
import xyz.przemyk.simpleplanes.upgrades.solarpanel.LargeSolarPanelModel;
import xyz.przemyk.simpleplanes.upgrades.solarpanel.SolarPanelModel;

@EventBusSubscriber(value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class PlanesModelLayers {
    public static final ModelLayerLocation PLANE_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "plane"), "main");
    public static final ModelLayerLocation PLANE_METAL_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "plane"), "metal");
    public static final ModelLayerLocation PROPELLER_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "plane"), "propeller");

    public static final ModelLayerLocation LARGE_PLANE_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "large_plane"), "main");
    public static final ModelLayerLocation LARGE_PLANE_METAL_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "large_plane"), "metal");
    public static final ModelLayerLocation LARGE_PROPELLER_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "large_plane"), "propeller");

    public static final ModelLayerLocation CARGO_PLANE_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "cargo_plane"), "main");
    public static final ModelLayerLocation CARGO_PLANE_METAL_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "cargo_plane"), "metal");
    public static final ModelLayerLocation CARGO_PROPELLER_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "cargo_plane"), "propeller");

    public static final ModelLayerLocation HELICOPTER_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "helicopter"), "main");
    public static final ModelLayerLocation HELICOPTER_METAL_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "helicopter"), "metal");
    public static final ModelLayerLocation HELICOPTER_PROPELLER_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "helicopter"), "propeller");

    public static final ModelLayerLocation PARACHUTE_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "parachute"), "main");

    public static final ModelLayerLocation FURNACE_ENGINE = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "furnace_engine"), "main");
    public static final ModelLayerLocation LARGE_FURNACE_ENGINE = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "furnace_engine"), "large");
    public static final ModelLayerLocation HELI_FURNACE_ENGINE = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "furnace_engine"), "heli");
    public static final ModelLayerLocation CARGO_FURNACE_ENGINE = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "furnace_engine"), "cargo");

    public static final ModelLayerLocation ELECTRIC_ENGINE = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "electric_engine"), "main");
    public static final ModelLayerLocation LARGE_ELECTRIC_ENGINE = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "electric_engine"), "large");
    public static final ModelLayerLocation HELI_ELECTRIC_ENGINE = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "electric_engine"), "heli");
    public static final ModelLayerLocation CARGO_ELECTRIC_ENGINE = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "electric_engine"), "cargo");

    public static final ModelLayerLocation LIQUID_ENGINE = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "liquid_engine"), "main");
    public static final ModelLayerLocation LARGE_LIQUID_ENGINE = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "liquid_engine"), "large");
    public static final ModelLayerLocation HELI_LIQUID_ENGINE = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "liquid_engine"), "heli");
    public static final ModelLayerLocation CARGO_LIQUID_ENGINE = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "liquid_engine"), "cargo");

    public static final ModelLayerLocation BOOSTER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "booster"), "main");
    public static final ModelLayerLocation LARGE_BOOSTER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "booster"), "large");
    public static final ModelLayerLocation HELI_BOOSTER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "booster"), "heli");
    public static final ModelLayerLocation CARGO_BOOSTER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "booster"), "cargo");

    public static final ModelLayerLocation SHOOTER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "shooter"), "main");
    public static final ModelLayerLocation LARGE_SHOOTER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "shooter"), "large");
    public static final ModelLayerLocation HELI_SHOOTER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "shooter"), "heli");

    public static final ModelLayerLocation FLOATING = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "floating"), "main");
    public static final ModelLayerLocation LARGE_FLOATING = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "floating"), "large");
    public static final ModelLayerLocation CARGO_FLOATING = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "floating"), "cargo");
    public static final ModelLayerLocation HELI_FLOATING = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "floating"), "heli");

    public static final ModelLayerLocation ARMOR = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "armor"), "main");
    public static final ModelLayerLocation LARGE_ARMOR = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "armor"), "large");
    public static final ModelLayerLocation HELI_ARMOR = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "armor"), "heli");
    public static final ModelLayerLocation CARGO_ARMOR = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "armor"), "cargo");
    public static final ModelLayerLocation ARMOR_WINDOW = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "armor"), "window");

    public static final ModelLayerLocation SOLAR_PANEL = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "solar_panel"), "main");
    public static final ModelLayerLocation LARGE_SOLAR_PANEL = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "solar_panel"), "large");
    public static final ModelLayerLocation CARGO_SOLAR_PANEL = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "solar_panel"), "cargo");

    public static final ModelLayerLocation SEATS = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "seats"), "main");
    public static final ModelLayerLocation LARGE_SEATS = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "seats"), "large");
    public static final ModelLayerLocation CARGO_SEATS = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "seats"), "cargo");
    public static final ModelLayerLocation HELI_SEATS = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "seats"), "heli");
    public static final ModelLayerLocation WOODEN_SEATS = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "seats"), "wooden");
    public static final ModelLayerLocation WOODEN_HELI_SEATS = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "seats"), "wooden_heli");
    public static final ModelLayerLocation WOODEN_CARGO_SEATS = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "seats"), "wooden_cargo");
    public static final ModelLayerLocation WOODEN_CARGO_FLOATING = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "floating"), "wooden_cargo");

    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(PLANE_LAYER, PlaneModel::createBodyLayer);
        event.registerLayerDefinition(PLANE_METAL_LAYER, PlaneMetalModel::createBodyLayer);
        event.registerLayerDefinition(PROPELLER_LAYER, PropellerModel::createBodyLayer);

        event.registerLayerDefinition(LARGE_PLANE_LAYER, LargePlaneModel::createBodyLayer);
        event.registerLayerDefinition(LARGE_PLANE_METAL_LAYER, LargePlaneMetalModel::createBodyLayer);
        event.registerLayerDefinition(LARGE_PROPELLER_LAYER, LargePropellerModel::createBodyLayer);

        event.registerLayerDefinition(CARGO_PLANE_LAYER, CargoPlaneModel::createBodyLayer);
        event.registerLayerDefinition(CARGO_PLANE_METAL_LAYER, CargoPlaneMetalModel::createBodyLayer);
        event.registerLayerDefinition(CARGO_PROPELLER_LAYER, CargoPropellerModel::createBodyLayer);

        event.registerLayerDefinition(HELICOPTER_LAYER, HelicopterModel::createBodyLayer);
        event.registerLayerDefinition(HELICOPTER_METAL_LAYER, HelicopterMetalModel::createBodyLayer);
        event.registerLayerDefinition(HELICOPTER_PROPELLER_LAYER, HelicopterPropellerModel::createBodyLayer);

        event.registerLayerDefinition(PARACHUTE_LAYER, ParachuteModel::createBodyLayer);



        event.registerLayerDefinition(FURNACE_ENGINE, FurnaceEngineModel::createBodyLayer);
        event.registerLayerDefinition(LARGE_FURNACE_ENGINE, LargeFurnaceEngineModel::createBodyLayer);
        event.registerLayerDefinition(HELI_FURNACE_ENGINE, HeliFurnaceEngineModel::createBodyLayer);
        event.registerLayerDefinition(CARGO_FURNACE_ENGINE, CargoFurnaceEngineModel::createBodyLayer);

        event.registerLayerDefinition(ELECTRIC_ENGINE, ElectricEngineModel::createBodyLayer);
        event.registerLayerDefinition(LARGE_ELECTRIC_ENGINE, LargeElectricEngineModel::createBodyLayer);
        event.registerLayerDefinition(HELI_ELECTRIC_ENGINE, HeliElectricEngineModel::createBodyLayer);
        event.registerLayerDefinition(CARGO_ELECTRIC_ENGINE, CargoElectricEngineModel::createBodyLayer);

        event.registerLayerDefinition(LIQUID_ENGINE, LiquidEngineModel::createBodyLayer);
        event.registerLayerDefinition(LARGE_LIQUID_ENGINE, LargeLiquidEngineModel::createBodyLayer);
        event.registerLayerDefinition(HELI_LIQUID_ENGINE, HeliLiquidEngineModel::createBodyLayer);
        event.registerLayerDefinition(CARGO_LIQUID_ENGINE, CargoLiquidEngineModel::createBodyLayer);

        event.registerLayerDefinition(BOOSTER, BoosterModel::createBodyLayer);
        event.registerLayerDefinition(LARGE_BOOSTER, LargeBoosterModel::createBodyLayer);
        event.registerLayerDefinition(HELI_BOOSTER, HeliBoosterModel::createBodyLayer);
        event.registerLayerDefinition(CARGO_BOOSTER, CargoBoosterModel::createBodyLayer);

        event.registerLayerDefinition(SHOOTER, ShooterModel::createBodyLayer);
        event.registerLayerDefinition(LARGE_SHOOTER, LargeShooterModel::createBodyLayer);
        event.registerLayerDefinition(HELI_SHOOTER, HeliShooterModel::createBodyLayer);

        event.registerLayerDefinition(FLOATING, FloatingModel::createBodyLayer);
        event.registerLayerDefinition(LARGE_FLOATING, LargeFloatingModel::createBodyLayer);
        event.registerLayerDefinition(CARGO_FLOATING, CargoFloatingModel::createBodyLayer);
        event.registerLayerDefinition(HELI_FLOATING, HeliFloatingModel::createBodyLayer);

        event.registerLayerDefinition(ARMOR, ArmorModel::createBodyLayer);
        event.registerLayerDefinition(LARGE_ARMOR, LargeArmorModel::createBodyLayer);
        event.registerLayerDefinition(HELI_ARMOR, HeliArmorModel::createBodyLayer);
        event.registerLayerDefinition(CARGO_ARMOR, CargoArmorModel::createBodyLayer);
        event.registerLayerDefinition(ARMOR_WINDOW, ArmorWindowModel::createBodyLayer);

        event.registerLayerDefinition(SOLAR_PANEL, SolarPanelModel::createBodyLayer);
        event.registerLayerDefinition(LARGE_SOLAR_PANEL, LargeSolarPanelModel::createBodyLayer);
        event.registerLayerDefinition(CARGO_SOLAR_PANEL, CargoSolarPanelModel::createBodyLayer);

        event.registerLayerDefinition(SEATS, SeatsModel::createBodyLayer);
        event.registerLayerDefinition(LARGE_SEATS, LargeSeatsModel::createBodyLayer);
        event.registerLayerDefinition(CARGO_SEATS, CargoSeatsModel::createBodyLayer);
        event.registerLayerDefinition(HELI_SEATS, HeliSeatsModel::createBodyLayer);
        event.registerLayerDefinition(WOODEN_SEATS, WoodenSeatsModel::createBodyLayer);
        event.registerLayerDefinition(WOODEN_HELI_SEATS, WoodenHeliSeatsModel::createBodyLayer);
        event.registerLayerDefinition(WOODEN_CARGO_SEATS, WoodenCargoSeatsModel::createBodyLayer);
        event.registerLayerDefinition(WOODEN_CARGO_FLOATING, WoodenCargoFloatingModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        EntityModelSet entityModelSet = Minecraft.getInstance().getEntityModels();
        event.registerEntityRenderer(SimplePlanesEntities.PLANE.get(), context -> new PlaneRenderer<>(context, new PlaneModel(entityModelSet.bakeLayer(PlanesModelLayers.PLANE_LAYER)), new PlaneMetalModel(entityModelSet.bakeLayer(PLANE_METAL_LAYER)), new PropellerModel(entityModelSet.bakeLayer(PlanesModelLayers.PROPELLER_LAYER)), 0.6f, ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "textures/plane_upgrades/plane_metal.png"), ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "textures/plane_upgrades/iron_propeller.png")));
        event.registerEntityRenderer(SimplePlanesEntities.LARGE_PLANE.get(), context -> new PlaneRenderer<>(context, new LargePlaneModel(entityModelSet.bakeLayer(PlanesModelLayers.LARGE_PLANE_LAYER)), new LargePlaneMetalModel(entityModelSet.bakeLayer(LARGE_PLANE_METAL_LAYER)), new LargePropellerModel(entityModelSet.bakeLayer(PlanesModelLayers.LARGE_PROPELLER_LAYER)), 1.0f, ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "textures/plane_upgrades/large_plane_metal.png"), ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "textures/plane_upgrades/iron_large_propeller.png")));
        event.registerEntityRenderer(SimplePlanesEntities.CARGO_PLANE.get(), context -> new PlaneRenderer<>(context, new CargoPlaneModel(entityModelSet.bakeLayer(PlanesModelLayers.CARGO_PLANE_LAYER)), new CargoPlaneMetalModel(entityModelSet.bakeLayer(CARGO_PLANE_METAL_LAYER)), new CargoPropellerModel(entityModelSet.bakeLayer(PlanesModelLayers.CARGO_PROPELLER_LAYER)), 1.0f, ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "textures/plane_upgrades/cargo_plane_metal.png"), ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "textures/plane_upgrades/iron_cargo_propeller.png")));
        event.registerEntityRenderer(SimplePlanesEntities.HELICOPTER.get(), context -> new PlaneRenderer<>(context, new HelicopterModel(entityModelSet.bakeLayer(PlanesModelLayers.HELICOPTER_LAYER)), new HelicopterMetalModel(entityModelSet.bakeLayer(HELICOPTER_METAL_LAYER)), new HelicopterPropellerModel(entityModelSet.bakeLayer(PlanesModelLayers.HELICOPTER_PROPELLER_LAYER)), 0.6f, ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "textures/plane_upgrades/helicopter_metal.png"), ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "textures/plane_upgrades/iron_helicopter_propeller.png")));

        event.registerEntityRenderer(SimplePlanesEntities.PARACHUTE.get(), context -> new ParachuteRenderer(context, new ParachuteModel(entityModelSet.bakeLayer(PlanesModelLayers.PARACHUTE_LAYER))));
    }

    @SubscribeEvent
    public static void bakeModelLayers(EntityRenderersEvent.AddLayers event) {
        EntityModelSet entityModelSet = event.getEntityModels();
        UpgradesModels.SHULKER_FOLDING = new ShulkerModel<>(entityModelSet.bakeLayer(ModelLayers.SHULKER));

        UpgradesModels.MODEL_ENTRIES.put(SimplePlanesUpgrades.FURNACE_ENGINE.get(), new UpgradesModels.ModelEntry(
                new FurnaceEngineModel(entityModelSet.bakeLayer(FURNACE_ENGINE)), SimplePlanesMod.texture("furnace_engine.png"),
                new LargeFurnaceEngineModel(entityModelSet.bakeLayer(LARGE_FURNACE_ENGINE)), SimplePlanesMod.texture("furnace_engine_large.png"),
                new HeliFurnaceEngineModel(entityModelSet.bakeLayer(HELI_FURNACE_ENGINE)), SimplePlanesMod.texture("furnace_engine_heli.png"),
                new CargoFurnaceEngineModel(entityModelSet.bakeLayer(CARGO_FURNACE_ENGINE)), SimplePlanesMod.texture("furnace_engine_cargo.png")));

        UpgradesModels.MODEL_ENTRIES.put(SimplePlanesUpgrades.ELECTRIC_ENGINE.get(), new UpgradesModels.ModelEntry(
                new ElectricEngineModel(entityModelSet.bakeLayer(ELECTRIC_ENGINE)), SimplePlanesMod.texture("electric_engine.png"),
                new LargeElectricEngineModel(entityModelSet.bakeLayer(LARGE_ELECTRIC_ENGINE)), SimplePlanesMod.texture("electric_engine_large.png"),
                new HeliElectricEngineModel(entityModelSet.bakeLayer(HELI_ELECTRIC_ENGINE)), SimplePlanesMod.texture("electric_engine_heli.png"),
                new CargoElectricEngineModel(entityModelSet.bakeLayer(CARGO_ELECTRIC_ENGINE)), SimplePlanesMod.texture("electric_engine_cargo.png")));

        UpgradesModels.MODEL_ENTRIES.put(SimplePlanesUpgrades.LIQUID_ENGINE.get(), new UpgradesModels.ModelEntry(
                new LiquidEngineModel(entityModelSet.bakeLayer(LIQUID_ENGINE)), SimplePlanesMod.texture("liquid_engine.png"),
                new LargeLiquidEngineModel(entityModelSet.bakeLayer(LARGE_LIQUID_ENGINE)), SimplePlanesMod.texture("liquid_engine_large.png"),
                new HeliLiquidEngineModel(entityModelSet.bakeLayer(HELI_LIQUID_ENGINE)), SimplePlanesMod.texture("liquid_engine_heli.png"),
                new CargoLiquidEngineModel(entityModelSet.bakeLayer(CARGO_LIQUID_ENGINE)), SimplePlanesMod.texture("liquid_engine_cargo.png")));

        UpgradesModels.MODEL_ENTRIES.put(SimplePlanesUpgrades.BOOSTER.get(), new UpgradesModels.ModelEntry(
                new BoosterModel(entityModelSet.bakeLayer(BOOSTER)), SimplePlanesMod.texture("booster.png"),
                new LargeBoosterModel(entityModelSet.bakeLayer(LARGE_BOOSTER)), SimplePlanesMod.texture("booster_large.png"),
                new HeliBoosterModel(entityModelSet.bakeLayer(HELI_BOOSTER)), SimplePlanesMod.texture("booster_heli.png"),
                new CargoBoosterModel(entityModelSet.bakeLayer(CARGO_BOOSTER)), SimplePlanesMod.texture("booster_cargo.png")));

        UpgradesModels.MODEL_ENTRIES.put(SimplePlanesUpgrades.SHOOTER.get(), new UpgradesModels.ModelEntry(
                new ShooterModel(entityModelSet.bakeLayer(SHOOTER)), SimplePlanesMod.texture("shooter.png"),
                new LargeShooterModel(entityModelSet.bakeLayer(LARGE_SHOOTER)), SimplePlanesMod.texture("shooter_large.png"),
                new HeliShooterModel(entityModelSet.bakeLayer(HELI_SHOOTER)), SimplePlanesMod.texture("shooter_heli.png"),
                null, null));

        UpgradesModels.MODEL_ENTRIES.put(SimplePlanesUpgrades.FLOATY_BEDDING.get(), new UpgradesModels.ModelEntry(
                new FloatingModel(entityModelSet.bakeLayer(FLOATING)), SimplePlanesMod.texture("floating.png"),
                new LargeFloatingModel(entityModelSet.bakeLayer(LARGE_FLOATING)), SimplePlanesMod.texture("floating_large.png"),
                new HeliFloatingModel(entityModelSet.bakeLayer(HELI_FLOATING)), SimplePlanesMod.texture("floating_heli.png"),
                new CargoFloatingModel(entityModelSet.bakeLayer(CARGO_FLOATING)), SimplePlanesMod.texture("floating_cargo.png")));

        UpgradesModels.MODEL_ENTRIES.put(SimplePlanesUpgrades.ARMOR.get(), new UpgradesModels.ModelEntry(
                new ArmorModel(entityModelSet.bakeLayer(ARMOR)), SimplePlanesMod.texture("armor.png"),
                new LargeArmorModel(entityModelSet.bakeLayer(LARGE_ARMOR)), SimplePlanesMod.texture("armor_large.png"),
                new HeliArmorModel(entityModelSet.bakeLayer(HELI_ARMOR)), SimplePlanesMod.texture("armor_heli.png"),
                new CargoArmorModel(entityModelSet.bakeLayer(CARGO_ARMOR)), SimplePlanesMod.texture("armor_cargo.png")));

        UpgradesModels.MODEL_ENTRIES.put(SimplePlanesUpgrades.SOLAR_PANEL.get(), new UpgradesModels.ModelEntry(
                new SolarPanelModel(entityModelSet.bakeLayer(SOLAR_PANEL)), SimplePlanesMod.texture("solar_panel.png"),
                new LargeSolarPanelModel(entityModelSet.bakeLayer(LARGE_SOLAR_PANEL)), SimplePlanesMod.texture("solar_panel_large.png"),
                null, null,
                new CargoSolarPanelModel(entityModelSet.bakeLayer(CARGO_SOLAR_PANEL)), SimplePlanesMod.texture("solar_panel_cargo.png")));

        UpgradesModels.SEATS = new SeatsModel(entityModelSet.bakeLayer(SEATS));
        UpgradesModels.LARGE_SEATS = new LargeSeatsModel(entityModelSet.bakeLayer(LARGE_SEATS));
        UpgradesModels.CARGO_SEATS = new CargoSeatsModel(entityModelSet.bakeLayer(CARGO_SEATS));
        UpgradesModels.HELI_SEATS = new HeliSeatsModel(entityModelSet.bakeLayer(HELI_SEATS));
        UpgradesModels.WOODEN_SEATS = new WoodenSeatsModel(entityModelSet.bakeLayer(WOODEN_SEATS));
        UpgradesModels.WOODEN_HELI_SEATS = new WoodenHeliSeatsModel(entityModelSet.bakeLayer(WOODEN_HELI_SEATS));
        UpgradesModels.WOODEN_CARGO_SEATS = new WoodenCargoSeatsModel(entityModelSet.bakeLayer(WOODEN_CARGO_SEATS));
        UpgradesModels.WOODEN_CARGO_FLOATING = new WoodenCargoFloatingModel(entityModelSet.bakeLayer(WOODEN_CARGO_FLOATING));
        UpgradesModels.ARMOR_WINDOW = new ArmorWindowModel(entityModelSet.bakeLayer(ARMOR_WINDOW));
    }
}
