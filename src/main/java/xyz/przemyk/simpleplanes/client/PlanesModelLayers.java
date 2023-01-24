package xyz.przemyk.simpleplanes.client;

import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.model.ShulkerModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.client.render.PlaneRenderer;
import xyz.przemyk.simpleplanes.client.render.UpgradesModels;
import xyz.przemyk.simpleplanes.client.render.models.*;
import xyz.przemyk.simpleplanes.setup.SimplePlanesEntities;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.armor.ArmorModel;
import xyz.przemyk.simpleplanes.upgrades.armor.ArmorWindowModel;
import xyz.przemyk.simpleplanes.upgrades.armor.HeliArmorModel;
import xyz.przemyk.simpleplanes.upgrades.armor.LargeArmorModel;
import xyz.przemyk.simpleplanes.upgrades.booster.BoosterModel;
import xyz.przemyk.simpleplanes.upgrades.booster.HeliBoosterModel;
import xyz.przemyk.simpleplanes.upgrades.booster.LargeBoosterModel;
import xyz.przemyk.simpleplanes.upgrades.engines.furnace.FurnaceEngineModel;
import xyz.przemyk.simpleplanes.upgrades.engines.furnace.HeliFurnaceEngineModel;
import xyz.przemyk.simpleplanes.upgrades.engines.furnace.LargeFurnaceEngineModel;
import xyz.przemyk.simpleplanes.upgrades.floating.FloatingModel;
import xyz.przemyk.simpleplanes.upgrades.floating.HeliFloatingModel;
import xyz.przemyk.simpleplanes.upgrades.floating.LargeFloatingModel;
import xyz.przemyk.simpleplanes.upgrades.seats.*;
import xyz.przemyk.simpleplanes.upgrades.shooter.HeliShooterModel;
import xyz.przemyk.simpleplanes.upgrades.shooter.LargeShooterModel;
import xyz.przemyk.simpleplanes.upgrades.shooter.ShooterModel;

public class PlanesModelLayers {
    public static final ModelLayerLocation PLANE_LAYER = new ModelLayerLocation(new ResourceLocation(SimplePlanesMod.MODID, "plane"), "main");
    public static final ModelLayerLocation PLANE_METAL_LAYER = new ModelLayerLocation(new ResourceLocation(SimplePlanesMod.MODID, "plane"), "metal");
    public static final ModelLayerLocation PROPELLER_LAYER = new ModelLayerLocation(new ResourceLocation(SimplePlanesMod.MODID, "plane"), "propeller");

    public static final ModelLayerLocation LARGE_PLANE_LAYER = new ModelLayerLocation(new ResourceLocation(SimplePlanesMod.MODID, "large_plane"), "main");
    public static final ModelLayerLocation LARGE_PLANE_METAL_LAYER = new ModelLayerLocation(new ResourceLocation(SimplePlanesMod.MODID, "large_plane"), "metal");
    public static final ModelLayerLocation LARGE_PROPELLER_LAYER = new ModelLayerLocation(new ResourceLocation(SimplePlanesMod.MODID, "large_plane"), "propeller");

    public static final ModelLayerLocation HELICOPTER_LAYER = new ModelLayerLocation(new ResourceLocation(SimplePlanesMod.MODID, "helicopter"), "main");
    public static final ModelLayerLocation HELICOPTER_METAL_LAYER = new ModelLayerLocation(new ResourceLocation(SimplePlanesMod.MODID, "helicopter"), "metal");
    public static final ModelLayerLocation HELICOPTER_PROPELLER_LAYER = new ModelLayerLocation(new ResourceLocation(SimplePlanesMod.MODID, "helicopter"), "propeller");
//
//    public static final ModelLayerLocation PARACHUTE_LAYER = new ModelLayerLocation(new ResourceLocation(SimplePlanesMod.MODID, "parachute"), "main");
//
    public static final ModelLayerLocation FURNACE_ENGINE = new ModelLayerLocation(new ResourceLocation(SimplePlanesMod.MODID, "furnace_engine"), "main");
    public static final ModelLayerLocation LARGE_FURNACE_ENGINE = new ModelLayerLocation(new ResourceLocation(SimplePlanesMod.MODID, "furnace_engine"), "large");
    public static final ModelLayerLocation HELI_FURNACE_ENGINE = new ModelLayerLocation(new ResourceLocation(SimplePlanesMod.MODID, "furnace_engine"), "heli");
//
//    public static final ModelLayerLocation ELECTRIC_ENGINE = new ModelLayerLocation(new ResourceLocation(SimplePlanesMod.MODID, "electric_engine"), "main");
//    public static final ModelLayerLocation LARGE_ELECTRIC_ENGINE = new ModelLayerLocation(new ResourceLocation(SimplePlanesMod.MODID, "electric_engine"), "large");
//    public static final ModelLayerLocation HELI_ELECTRIC_ENGINE = new ModelLayerLocation(new ResourceLocation(SimplePlanesMod.MODID, "electric_engine"), "heli");
//
//    public static final ModelLayerLocation LIQUID_ENGINE = new ModelLayerLocation(new ResourceLocation(SimplePlanesMod.MODID, "liquid_engine"), "main");
//    public static final ModelLayerLocation LARGE_LIQUID_ENGINE = new ModelLayerLocation(new ResourceLocation(SimplePlanesMod.MODID, "liquid_engine"), "large");
//    public static final ModelLayerLocation HELI_LIQUID_ENGINE = new ModelLayerLocation(new ResourceLocation(SimplePlanesMod.MODID, "liquid_engine"), "heli");
//
    public static final ModelLayerLocation BOOSTER = new ModelLayerLocation(new ResourceLocation(SimplePlanesMod.MODID, "booster"), "main");
    public static final ModelLayerLocation LARGE_BOOSTER = new ModelLayerLocation(new ResourceLocation(SimplePlanesMod.MODID, "booster"), "large");
    public static final ModelLayerLocation HELI_BOOSTER = new ModelLayerLocation(new ResourceLocation(SimplePlanesMod.MODID, "booster"), "heli");

    public static final ModelLayerLocation SHOOTER = new ModelLayerLocation(new ResourceLocation(SimplePlanesMod.MODID, "shooter"), "main");
    public static final ModelLayerLocation LARGE_SHOOTER = new ModelLayerLocation(new ResourceLocation(SimplePlanesMod.MODID, "shooter"), "large");
    public static final ModelLayerLocation HELI_SHOOTER = new ModelLayerLocation(new ResourceLocation(SimplePlanesMod.MODID, "shooter"), "heli");

    public static final ModelLayerLocation FLOATING = new ModelLayerLocation(new ResourceLocation(SimplePlanesMod.MODID, "floating"), "main");
    public static final ModelLayerLocation LARGE_FLOATING = new ModelLayerLocation(new ResourceLocation(SimplePlanesMod.MODID, "floating"), "large");
    public static final ModelLayerLocation HELI_FLOATING = new ModelLayerLocation(new ResourceLocation(SimplePlanesMod.MODID, "floating"), "heli");

    public static final ModelLayerLocation ARMOR = new ModelLayerLocation(new ResourceLocation(SimplePlanesMod.MODID, "armor"), "main");
    public static final ModelLayerLocation LARGE_ARMOR = new ModelLayerLocation(new ResourceLocation(SimplePlanesMod.MODID, "armor"), "large");
    public static final ModelLayerLocation HELI_ARMOR = new ModelLayerLocation(new ResourceLocation(SimplePlanesMod.MODID, "armor"), "heli");
    public static final ModelLayerLocation ARMOR_WINDOW = new ModelLayerLocation(new ResourceLocation(SimplePlanesMod.MODID, "armor"), "window");
//
//    public static final ModelLayerLocation SOLAR_PANEL = new ModelLayerLocation(new ResourceLocation(SimplePlanesMod.MODID, "solar_panel"), "main");
//    public static final ModelLayerLocation LARGE_SOLAR_PANEL = new ModelLayerLocation(new ResourceLocation(SimplePlanesMod.MODID, "solar_panel"), "large");
//
    public static final ModelLayerLocation SEATS = new ModelLayerLocation(new ResourceLocation(SimplePlanesMod.MODID, "seats"), "main");
    public static final ModelLayerLocation LARGE_SEATS = new ModelLayerLocation(new ResourceLocation(SimplePlanesMod.MODID, "seats"), "large");
    public static final ModelLayerLocation HELI_SEATS = new ModelLayerLocation(new ResourceLocation(SimplePlanesMod.MODID, "seats"), "heli");
    public static final ModelLayerLocation WOODEN_SEATS = new ModelLayerLocation(new ResourceLocation(SimplePlanesMod.MODID, "seats"), "wooden");
    public static final ModelLayerLocation WOODEN_HELI_SEATS = new ModelLayerLocation(new ResourceLocation(SimplePlanesMod.MODID, "seats"), "wooden_heli");

    public static void registerLayers() {
        EntityModelLayerRegistry.registerModelLayer(PLANE_LAYER, PlaneModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(PLANE_METAL_LAYER, PlaneMetalModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(PROPELLER_LAYER, PropellerModel::createBodyLayer);

        EntityModelLayerRegistry.registerModelLayer(LARGE_PLANE_LAYER, LargePlaneModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(LARGE_PLANE_METAL_LAYER, LargePlaneMetalModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(LARGE_PROPELLER_LAYER, LargePropellerModel::createBodyLayer);

        EntityModelLayerRegistry.registerModelLayer(HELICOPTER_LAYER, HelicopterModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(HELICOPTER_METAL_LAYER, HelicopterMetalModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(HELICOPTER_PROPELLER_LAYER, HelicopterPropellerModel::createBodyLayer);
//
//        event.registerLayerDefinition(PARACHUTE_LAYER, ParachuteModel::createBodyLayer);
//
//
//
        EntityModelLayerRegistry.registerModelLayer(FURNACE_ENGINE, FurnaceEngineModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(LARGE_FURNACE_ENGINE, LargeFurnaceEngineModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(HELI_FURNACE_ENGINE, HeliFurnaceEngineModel::createBodyLayer);
//
//        event.registerLayerDefinition(ELECTRIC_ENGINE, ElectricEngineModel::createBodyLayer);
//        event.registerLayerDefinition(LARGE_ELECTRIC_ENGINE, LargeElectricEngineModel::createBodyLayer);
//        event.registerLayerDefinition(HELI_ELECTRIC_ENGINE, HeliElectricEngineModel::createBodyLayer);
//
//        event.registerLayerDefinition(LIQUID_ENGINE, LiquidEngineModel::createBodyLayer);
//        event.registerLayerDefinition(LARGE_LIQUID_ENGINE, LargeLiquidEngineModel::createBodyLayer);
//        event.registerLayerDefinition(HELI_LIQUID_ENGINE, HeliLiquidEngineModel::createBodyLayer);
//
        EntityModelLayerRegistry.registerModelLayer(BOOSTER, BoosterModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(LARGE_BOOSTER, LargeBoosterModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(HELI_BOOSTER, HeliBoosterModel::createBodyLayer);

        EntityModelLayerRegistry.registerModelLayer(SHOOTER, ShooterModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(LARGE_SHOOTER, LargeShooterModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(HELI_SHOOTER, HeliShooterModel::createBodyLayer);

        EntityModelLayerRegistry.registerModelLayer(FLOATING, FloatingModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(LARGE_FLOATING, LargeFloatingModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(HELI_FLOATING, HeliFloatingModel::createBodyLayer);

        EntityModelLayerRegistry.registerModelLayer(ARMOR, ArmorModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(LARGE_ARMOR, LargeArmorModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(HELI_ARMOR, HeliArmorModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ARMOR_WINDOW, ArmorWindowModel::createBodyLayer);
//
//      EntityModelLayerRegistry.registerModelLayeron(SOLAR_PANEL, SolarPanelModel::createBodyLayer);
//      EntityModelLayerRegistry.registerModelLayeron(LARGE_SOLAR_PANEL, LargeSolarPanelModel::createBodyLayer);
//
        EntityModelLayerRegistry.registerModelLayer(SEATS, SeatsModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(LARGE_SEATS, LargeSeatsModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(HELI_SEATS, HeliSeatsModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(WOODEN_SEATS, WoodenSeatsModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(WOODEN_HELI_SEATS, WoodenHeliSeatsModel::createBodyLayer);
    }

    public static void registerRenderers() {
        EntityRendererRegistry.register(SimplePlanesEntities.PLANE, context -> new PlaneRenderer<>(context, new PlaneModel(context.bakeLayer(PlanesModelLayers.PLANE_LAYER)), new PlaneMetalModel(context.bakeLayer(PLANE_METAL_LAYER)), new PropellerModel(context.bakeLayer(PlanesModelLayers.PROPELLER_LAYER)), 0.6f, new ResourceLocation(SimplePlanesMod.MODID, "textures/plane_upgrades/plane_metal.png"), new ResourceLocation(SimplePlanesMod.MODID, "textures/plane_upgrades/iron_propeller.png")));
        EntityRendererRegistry.register(SimplePlanesEntities.LARGE_PLANE, context -> new PlaneRenderer<>(context, new LargePlaneModel(context.bakeLayer(PlanesModelLayers.LARGE_PLANE_LAYER)), new LargePlaneMetalModel(context.bakeLayer(LARGE_PLANE_METAL_LAYER)), new LargePropellerModel(context.bakeLayer(PlanesModelLayers.LARGE_PROPELLER_LAYER)), 1.0f, new ResourceLocation(SimplePlanesMod.MODID, "textures/plane_upgrades/large_plane_metal.png"), new ResourceLocation(SimplePlanesMod.MODID, "textures/plane_upgrades/iron_large_propeller.png")));
        EntityRendererRegistry.register(SimplePlanesEntities.HELICOPTER, context -> new PlaneRenderer<>(context, new HelicopterModel(context.bakeLayer(PlanesModelLayers.HELICOPTER_LAYER)), new HelicopterMetalModel(context.bakeLayer(HELICOPTER_METAL_LAYER)), new HelicopterPropellerModel(context.bakeLayer(PlanesModelLayers.HELICOPTER_PROPELLER_LAYER)), 0.6f, new ResourceLocation(SimplePlanesMod.MODID, "textures/plane_upgrades/helicopter_metal.png"), new ResourceLocation(SimplePlanesMod.MODID, "textures/plane_upgrades/iron_helicopter_propeller.png")));
//
//        event.registerEntityRenderer(SimplePlanesEntities.PARACHUTE.get(), context -> new ParachuteRenderer(context, new ParachuteModel(entityModelSet.bakeLayer(PlanesModelLayers.PARACHUTE_LAYER))));

    }

    public static void bakeModelLayers(EntityRendererProvider.Context context) {
        UpgradesModels.SHULKER_FOLDING = new ShulkerModel<>(context.bakeLayer(ModelLayers.SHULKER));
//
        UpgradesModels.MODEL_ENTRIES.put(SimplePlanesUpgrades.FURNACE_ENGINE, new UpgradesModels.ModelEntry(
                new FurnaceEngineModel(context.bakeLayer(FURNACE_ENGINE)), SimplePlanesMod.texture("furnace_engine.png"),
                new LargeFurnaceEngineModel(context.bakeLayer(LARGE_FURNACE_ENGINE)), SimplePlanesMod.texture("furnace_engine_large.png"),
                new HeliFurnaceEngineModel(context.bakeLayer(HELI_FURNACE_ENGINE)), SimplePlanesMod.texture("furnace_engine_heli.png")));

//
//        UpgradesModels.MODEL_ENTRIES.put(SimplePlanesUpgrades.ELECTRIC_ENGINE.get(), new UpgradesModels.ModelEntry(
//                new ElectricEngineModel(entityModelSet.bakeLayer(ELECTRIC_ENGINE)), SimplePlanesMod.texture("electric_engine.png"),
//                new LargeElectricEngineModel(entityModelSet.bakeLayer(LARGE_ELECTRIC_ENGINE)), SimplePlanesMod.texture("electric_engine_large.png"),
//                new HeliElectricEngineModel(entityModelSet.bakeLayer(HELI_ELECTRIC_ENGINE)), SimplePlanesMod.texture("electric_engine_heli.png")));
//
//        UpgradesModels.MODEL_ENTRIES.put(SimplePlanesUpgrades.LIQUID_ENGINE.get(), new UpgradesModels.ModelEntry(
//                new LiquidEngineModel(entityModelSet.bakeLayer(LIQUID_ENGINE)), SimplePlanesMod.texture("liquid_engine.png"),
//                new LargeLiquidEngineModel(entityModelSet.bakeLayer(LARGE_LIQUID_ENGINE)), SimplePlanesMod.texture("liquid_engine_large.png"),
//                new HeliLiquidEngineModel(entityModelSet.bakeLayer(HELI_LIQUID_ENGINE)), SimplePlanesMod.texture("liquid_engine_heli.png")));

        UpgradesModels.MODEL_ENTRIES.put(SimplePlanesUpgrades.BOOSTER, new UpgradesModels.ModelEntry(
                new BoosterModel(context.bakeLayer(BOOSTER)), SimplePlanesMod.texture("booster.png"),
                new LargeBoosterModel(context.bakeLayer(LARGE_BOOSTER)), SimplePlanesMod.texture("booster_large.png"),
                new HeliBoosterModel(context.bakeLayer(HELI_BOOSTER)), SimplePlanesMod.texture("booster_heli.png")));

        UpgradesModels.MODEL_ENTRIES.put(SimplePlanesUpgrades.SHOOTER, new UpgradesModels.ModelEntry(
                new ShooterModel(context.bakeLayer(SHOOTER)), SimplePlanesMod.texture("shooter.png"),
                new LargeShooterModel(context.bakeLayer(LARGE_SHOOTER)), SimplePlanesMod.texture("shooter_large.png"),
                new HeliShooterModel(context.bakeLayer(HELI_SHOOTER)), SimplePlanesMod.texture("shooter_heli.png")));

        UpgradesModels.MODEL_ENTRIES.put(SimplePlanesUpgrades.FLOATY_BEDDING, new UpgradesModels.ModelEntry(
                new FloatingModel(context.bakeLayer(FLOATING)), SimplePlanesMod.texture("floating.png"),
                new LargeFloatingModel(context.bakeLayer(LARGE_FLOATING)), SimplePlanesMod.texture("floating_large.png"),
                new HeliFloatingModel(context.bakeLayer(HELI_FLOATING)), SimplePlanesMod.texture("floating_heli.png")));

        UpgradesModels.MODEL_ENTRIES.put(SimplePlanesUpgrades.ARMOR, new UpgradesModels.ModelEntry(
                new ArmorModel(context.bakeLayer(ARMOR)), SimplePlanesMod.texture("armor.png"),
                new LargeArmorModel(context.bakeLayer(LARGE_ARMOR)), SimplePlanesMod.texture("armor_large.png"),
                new HeliArmorModel(context.bakeLayer(HELI_ARMOR)), SimplePlanesMod.texture("armor_heli.png")));
//
//        UpgradesModels.MODEL_ENTRIES.put(SimplePlanesUpgrades.SOLAR_PANEL.get(), new UpgradesModels.ModelEntry(
//                new SolarPanelModel(entityModelSet.bakeLayer(SOLAR_PANEL)), SimplePlanesMod.texture("solar_panel.png"),
//                new LargeSolarPanelModel(entityModelSet.bakeLayer(LARGE_SOLAR_PANEL)), SimplePlanesMod.texture("solar_panel_large.png"),
//                null, null));
//
        UpgradesModels.SEATS = new SeatsModel(context.bakeLayer(SEATS));
        UpgradesModels.LARGE_SEATS = new LargeSeatsModel(context.bakeLayer(LARGE_SEATS));
        UpgradesModels.HELI_SEATS = new HeliSeatsModel(context.bakeLayer(HELI_SEATS));
        UpgradesModels.WOODEN_SEATS = new WoodenSeatsModel(context.bakeLayer(WOODEN_SEATS));
        UpgradesModels.WOODEN_HELI_SEATS = new WoodenHeliSeatsModel(context.bakeLayer(WOODEN_HELI_SEATS));
        UpgradesModels.ARMOR_WINDOW = new ArmorWindowModel(context.bakeLayer(ARMOR_WINDOW));
    }
}
