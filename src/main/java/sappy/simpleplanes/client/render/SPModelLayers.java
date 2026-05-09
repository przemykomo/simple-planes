package sappy.simpleplanes.client.render;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.AddLayers;
import sappy.simpleplanes.SPEntities;
import sappy.simpleplanes.SPMod;
import sappy.simpleplanes.client.render.models.CargoMetalModel;
import sappy.simpleplanes.client.render.models.CargoModel;
import sappy.simpleplanes.client.render.models.CargoPropellerModel;
import sappy.simpleplanes.client.render.models.HeliMetalModel;
import sappy.simpleplanes.client.render.models.HeliModel;
import sappy.simpleplanes.client.render.models.HeliPropellerModel;
import sappy.simpleplanes.client.render.models.LargeMetalModel;
import sappy.simpleplanes.client.render.models.LargeModel;
import sappy.simpleplanes.client.render.models.LargePropellerModel;
import sappy.simpleplanes.client.render.models.PlaneMetalModel;
import sappy.simpleplanes.client.render.models.PlaneModel;
import sappy.simpleplanes.client.render.models.PlanePropellerModel;
import sappy.simpleplanes.upgrades.armor.ArmorWindowModel;
import sappy.simpleplanes.upgrades.banner.BannerModel;
import sappy.simpleplanes.upgrades.floating.WoodenCargoFloatingModel;
import sappy.simpleplanes.upgrades.seats.CargoSeatsModel;
import sappy.simpleplanes.upgrades.seats.HeliSeatsModel;
import sappy.simpleplanes.upgrades.seats.LargeSeatsModel;
import sappy.simpleplanes.upgrades.seats.SeatsModel;
import sappy.simpleplanes.upgrades.seats.WoodenCargoSeatsModel;
import sappy.simpleplanes.upgrades.seats.WoodenHeliSeatsModel;
import sappy.simpleplanes.upgrades.seats.WoodenSeatsModel;

@Mod(value = SPMod.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = SPMod.MODID, value = Dist.CLIENT)
public class SPModelLayers {

    public static final ModelLayerLocation PLANE_WOOD = new ModelLayerLocation(SPMod.id("plane"), "wood");
    public static final ModelLayerLocation PLANE_METAL = new ModelLayerLocation(SPMod.id("plane"), "metal");
    public static final ModelLayerLocation PLANE_PROPELLER = new ModelLayerLocation(SPMod.id("plane"), "propeller");

    public static final ModelLayerLocation LARGE_WOOD = new ModelLayerLocation(SPMod.id("large_plane"), "wood");
    public static final ModelLayerLocation LARGE_METAL = new ModelLayerLocation(SPMod.id("large_plane"), "metal");
    public static final ModelLayerLocation LARGE_PROPELLER = new ModelLayerLocation(SPMod.id("large_plane"),
            "propeller");

    public static final ModelLayerLocation CARGO_WOOD = new ModelLayerLocation(SPMod.id("cargo_plane"), "wood");
    public static final ModelLayerLocation CARGO_METAL = new ModelLayerLocation(SPMod.id("cargo_plane"), "metal");
    public static final ModelLayerLocation CARGO_PROPELLER = new ModelLayerLocation(SPMod.id("cargo_plane"),
            "propeller");

    public static final ModelLayerLocation HELI_WOOD = new ModelLayerLocation(SPMod.id("heli"), "wood");
    public static final ModelLayerLocation HELI_METAL = new ModelLayerLocation(SPMod.id("heli"), "metal");
    public static final ModelLayerLocation HELI_PROPELLER = new ModelLayerLocation(SPMod.id("heli"),
            "propeller");

    public static final ModelLayerLocation PARACHUTE_LAYER = new ModelLayerLocation(SPMod.id("parachute"), "main");

    public static final ModelLayerLocation FURNACE_ENGINE = new ModelLayerLocation(SPMod.id("furnace_engine"), "main");
    public static final ModelLayerLocation LARGE_FURNACE_ENGINE = new ModelLayerLocation(SPMod.id("furnace_engine"),
            "large");
    public static final ModelLayerLocation HELI_FURNACE_ENGINE = new ModelLayerLocation(SPMod.id("furnace_engine"),
            "heli");
    public static final ModelLayerLocation CARGO_FURNACE_ENGINE = new ModelLayerLocation(SPMod.id("furnace_engine"),
            "cargo");

    public static final ModelLayerLocation ELECTRIC_ENGINE = new ModelLayerLocation(SPMod.id("electric_engine"),
            "main");
    public static final ModelLayerLocation LARGE_ELECTRIC_ENGINE = new ModelLayerLocation(SPMod.id("electric_engine"),
            "large");
    public static final ModelLayerLocation HELI_ELECTRIC_ENGINE = new ModelLayerLocation(SPMod.id("electric_engine"),
            "heli");
    public static final ModelLayerLocation CARGO_ELECTRIC_ENGINE = new ModelLayerLocation(SPMod.id("electric_engine"),
            "cargo");

    public static final ModelLayerLocation LIQUID_ENGINE = new ModelLayerLocation(SPMod.id("liquid_engine"), "main");
    public static final ModelLayerLocation LARGE_LIQUID_ENGINE = new ModelLayerLocation(SPMod.id("liquid_engine"),
            "large");
    public static final ModelLayerLocation HELI_LIQUID_ENGINE = new ModelLayerLocation(SPMod.id("liquid_engine"),
            "heli");
    public static final ModelLayerLocation CARGO_LIQUID_ENGINE = new ModelLayerLocation(SPMod.id("liquid_engine"),
            "cargo");

    public static final ModelLayerLocation BOOSTER = new ModelLayerLocation(SPMod.id("booster"), "main");
    public static final ModelLayerLocation LARGE_BOOSTER = new ModelLayerLocation(SPMod.id("booster"), "large");
    public static final ModelLayerLocation HELI_BOOSTER = new ModelLayerLocation(SPMod.id("booster"), "heli");
    public static final ModelLayerLocation CARGO_BOOSTER = new ModelLayerLocation(SPMod.id("booster"), "cargo");

    public static final ModelLayerLocation SHOOTER = new ModelLayerLocation(SPMod.id("shooter"), "main");
    public static final ModelLayerLocation LARGE_SHOOTER = new ModelLayerLocation(SPMod.id("shooter"), "large");
    public static final ModelLayerLocation HELI_SHOOTER = new ModelLayerLocation(SPMod.id("shooter"), "heli");

    public static final ModelLayerLocation FLOATING = new ModelLayerLocation(SPMod.id("floating"), "main");
    public static final ModelLayerLocation LARGE_FLOATING = new ModelLayerLocation(SPMod.id("floating"), "large");
    public static final ModelLayerLocation CARGO_FLOATING = new ModelLayerLocation(SPMod.id("floating"), "cargo");
    public static final ModelLayerLocation HELI_FLOATING = new ModelLayerLocation(SPMod.id("floating"), "heli");

    public static final ModelLayerLocation ARMOR = new ModelLayerLocation(SPMod.id("armor"), "main");
    public static final ModelLayerLocation LARGE_ARMOR = new ModelLayerLocation(SPMod.id("armor"), "large");
    public static final ModelLayerLocation HELI_ARMOR = new ModelLayerLocation(SPMod.id("armor"), "heli");
    public static final ModelLayerLocation CARGO_ARMOR = new ModelLayerLocation(SPMod.id("armor"), "cargo");
    public static final ModelLayerLocation ARMOR_WINDOW = new ModelLayerLocation(SPMod.id("armor"), "window");

    public static final ModelLayerLocation SOLAR_PANEL = new ModelLayerLocation(SPMod.id("solar_panel"), "main");
    public static final ModelLayerLocation LARGE_SOLAR_PANEL = new ModelLayerLocation(SPMod.id("solar_panel"), "large");
    public static final ModelLayerLocation CARGO_SOLAR_PANEL = new ModelLayerLocation(SPMod.id("solar_panel"), "cargo");

    public static final ModelLayerLocation SEATS = new ModelLayerLocation(SPMod.id("seats"), "main");
    public static final ModelLayerLocation LARGE_SEATS = new ModelLayerLocation(SPMod.id("seats"), "large");
    public static final ModelLayerLocation CARGO_SEATS = new ModelLayerLocation(SPMod.id("seats"), "cargo");
    public static final ModelLayerLocation HELI_SEATS = new ModelLayerLocation(SPMod.id("seats"), "heli");
    public static final ModelLayerLocation WOODEN_SEATS = new ModelLayerLocation(SPMod.id("seats"), "wooden");
    public static final ModelLayerLocation WOODEN_HELI_SEATS = new ModelLayerLocation(SPMod.id("seats"), "wooden_heli");
    public static final ModelLayerLocation WOODEN_CARGO_SEATS = new ModelLayerLocation(SPMod.id("seats"),
            "wooden_cargo");
    public static final ModelLayerLocation WOODEN_CARGO_FLOATING = new ModelLayerLocation(SPMod.id("floating"),
            "wooden_cargo");

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(PLANE_WOOD, PlaneModel::createBodyLayer);
        event.registerLayerDefinition(PLANE_METAL, PlaneMetalModel::createBodyLayer);
        event.registerLayerDefinition(PLANE_PROPELLER, PlanePropellerModel::createBodyLayer);

        event.registerLayerDefinition(LARGE_WOOD, LargeModel::createBodyLayer);
        event.registerLayerDefinition(LARGE_METAL, LargeMetalModel::createBodyLayer);
        event.registerLayerDefinition(LARGE_PROPELLER, LargePropellerModel::createBodyLayer);

        event.registerLayerDefinition(CARGO_WOOD, CargoModel::createBodyLayer);
        event.registerLayerDefinition(CARGO_METAL, CargoMetalModel::createBodyLayer);
        event.registerLayerDefinition(CARGO_PROPELLER, CargoPropellerModel::createBodyLayer);

        event.registerLayerDefinition(HELI_WOOD, HeliModel::createBodyLayer);
        event.registerLayerDefinition(HELI_METAL, HeliMetalModel::createBodyLayer);
        event.registerLayerDefinition(HELI_PROPELLER, HeliPropellerModel::createBodyLayer);

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
        event.registerEntityRenderer(SPEntities.PLANE.get(), context -> {
            var woodModel = new PlaneModel(context.bakeLayer(PLANE_WOOD));
            var metalModel = new PlaneMetalModel(context.bakeLayer(PLANE_METAL));
            var propellerModel = new PlanePropellerModel(context.bakeLayer(PLANE_PROPELLER));
            var metalTexture = SPMod.id("textures/plane_upgrades/plane_metal.png");
            var propellerTexture = SPMod.id("textures/plane_upgrades/iron_propeller.png");
            var offset = new Vec3(0, -2.0, -0.5);
            return new PlaneRenderer(context, woodModel, metalModel, propellerModel, metalTexture, propellerTexture,
                    offset, 0.6f);
        });

        event.registerEntityRenderer(SPEntities.LARGE_PLANE.get(), context -> {
            var woodModel = new LargeModel(context.bakeLayer(LARGE_WOOD));
            var metalModel = new LargeMetalModel(context.bakeLayer(LARGE_METAL));
            var propellerModel = new LargePropellerModel(context.bakeLayer(LARGE_PROPELLER));
            var metalTexture = SPMod.id("textures/plane_upgrades/large_plane_metal.png");
            var propellerTexture = SPMod.id("textures/plane_upgrades/iron_large_propeller.png");
            var offset = new Vec3(0, -1.8, -1.0);
            return new PlaneRenderer(context, woodModel, metalModel, propellerModel, metalTexture, propellerTexture,
                    offset, 1.0f);
        });

        event.registerEntityRenderer(SPEntities.CARGO_PLANE.get(), context -> {
            var woodModel = new CargoModel(context.bakeLayer(CARGO_WOOD));
            var metalModel = new CargoMetalModel(context.bakeLayer(CARGO_METAL));
            var propellerModel = new CargoPropellerModel(context.bakeLayer(CARGO_PROPELLER));
            var metalTexture = SPMod.id("textures/plane_upgrades/cargo_plane_metal.png");
            var propellerTexture = SPMod.id("textures/plane_upgrades/iron_cargo_propeller.png");
            var offset = new Vec3(0, -2.3, -1.0);
            return new PlaneRenderer(context, woodModel, metalModel, propellerModel, metalTexture, propellerTexture,
                    offset, 1.0f);
        });

        event.registerEntityRenderer(SPEntities.HELI.get(), context -> {
            var woodModel = new HeliModel(context.bakeLayer(HELI_WOOD));
            var metalModel = new HeliMetalModel(context.bakeLayer(HELI_METAL));
            var propellerModel = new HeliPropellerModel(context.bakeLayer(HELI_PROPELLER));
            var metalTexture = SPMod.id("textures/plane_upgrades/helicopter_metal.png");
            var propellerTexture = SPMod.id("textures/plane_upgrades/iron_helicopter_propeller.png");
            var offset = new Vec3(0, -1.5, 0.9);
            return new PlaneRenderer(context, woodModel, metalModel, propellerModel, metalTexture, propellerTexture,
                    offset, 0.6f);
        });
    }

    @SubscribeEvent
    public static void addLayers(AddLayers event) {
        var context = event.getContext();
        UpgradesModels.SEATS = new SeatsModel(context.bakeLayer(SEATS));
        UpgradesModels.LARGE_SEATS = new LargeSeatsModel(context.bakeLayer(LARGE_SEATS));
        UpgradesModels.CARGO_SEATS = new CargoSeatsModel(context.bakeLayer(CARGO_SEATS));
        UpgradesModels.HELI_SEATS = new HeliSeatsModel(context.bakeLayer(HELI_SEATS));
        UpgradesModels.WOODEN_SEATS = new WoodenSeatsModel(context.bakeLayer(WOODEN_SEATS));
        UpgradesModels.WOODEN_HELI_SEATS = new WoodenHeliSeatsModel(context.bakeLayer(WOODEN_HELI_SEATS));
        UpgradesModels.WOODEN_CARGO_SEATS = new WoodenCargoSeatsModel(context.bakeLayer(WOODEN_CARGO_SEATS));
        UpgradesModels.WOODEN_CARGO_FLOATING = new WoodenCargoFloatingModel(
                context.bakeLayer(WOODEN_CARGO_FLOATING));
        UpgradesModels.ARMOR_WINDOW = new ArmorWindowModel(context.bakeLayer(ARMOR_WINDOW));
        UpgradesModels.BANNER = new BannerModel(context.getSprites());
    }
}
