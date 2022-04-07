package xyz.przemyk.simpleplanes.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ShulkerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.client.render.ParachuteRenderer;
import xyz.przemyk.simpleplanes.client.render.PlaneRenderer;
import xyz.przemyk.simpleplanes.client.render.UpgradesModels;
import xyz.przemyk.simpleplanes.client.render.models.*;
import xyz.przemyk.simpleplanes.setup.SimplePlanesEntities;
import xyz.przemyk.simpleplanes.upgrades.armor.ArmorModel;
import xyz.przemyk.simpleplanes.upgrades.armor.LargeArmorModel;
import xyz.przemyk.simpleplanes.upgrades.booster.BoosterModel;
import xyz.przemyk.simpleplanes.upgrades.floating.FloatingModel;
import xyz.przemyk.simpleplanes.upgrades.floating.HelicopterFloatingModel;
import xyz.przemyk.simpleplanes.upgrades.floating.LargeFloatingModel;
import xyz.przemyk.simpleplanes.upgrades.seats.SeatsUpgradeModel;
import xyz.przemyk.simpleplanes.upgrades.shooter.ShooterModel;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PlanesModelLayers {
    public static final ModelLayerLocation PLANE_LAYER = new ModelLayerLocation(new ResourceLocation(SimplePlanesMod.MODID, "plane"), "main");
    public static final ModelLayerLocation PLANE_METAL_LAYER = new ModelLayerLocation(new ResourceLocation(SimplePlanesMod.MODID, "plane"), "metal");
    public static final ModelLayerLocation LARGE_PLANE_LAYER = new ModelLayerLocation(new ResourceLocation(SimplePlanesMod.MODID, "large_plane"), "main");
    public static final ModelLayerLocation HELICOPTER_LAYER = new ModelLayerLocation(new ResourceLocation(SimplePlanesMod.MODID, "helicopter"), "main");
    public static final ModelLayerLocation PROPELLER_LAYER = new ModelLayerLocation(new ResourceLocation(SimplePlanesMod.MODID, "propeller"), "main");
    public static final ModelLayerLocation HELICOPTER_PROPELLER_LAYER = new ModelLayerLocation(new ResourceLocation(SimplePlanesMod.MODID, "helicopter_propeller"), "main");

    public static final ModelLayerLocation PARACHUTE_LAYER = new ModelLayerLocation(new ResourceLocation(SimplePlanesMod.MODID, "parachute"), "main");

    public static final ModelLayerLocation BOOSTER = new ModelLayerLocation(new ResourceLocation(SimplePlanesMod.MODID, "booster"), "main");
    public static final ModelLayerLocation SHOOTER = new ModelLayerLocation(new ResourceLocation(SimplePlanesMod.MODID, "shooter"), "main");
    public static final ModelLayerLocation FLOATING = new ModelLayerLocation(new ResourceLocation(SimplePlanesMod.MODID, "floating"), "main");
    public static final ModelLayerLocation LARGE_FLOATING = new ModelLayerLocation(new ResourceLocation(SimplePlanesMod.MODID, "large_floating"), "main");
    public static final ModelLayerLocation HELICOPTER_FLOATING = new ModelLayerLocation(new ResourceLocation(SimplePlanesMod.MODID, "helicopter_floating"), "main");
    public static final ModelLayerLocation ARMOR = new ModelLayerLocation(new ResourceLocation(SimplePlanesMod.MODID, "armor"), "main");
    public static final ModelLayerLocation LARGE_ARMOR = new ModelLayerLocation(new ResourceLocation(SimplePlanesMod.MODID, "large_armor"), "main");
    public static final ModelLayerLocation SEATS = new ModelLayerLocation(new ResourceLocation(SimplePlanesMod.MODID, "seats"), "main");

    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(PLANE_LAYER, PlaneModel::createBodyLayer);
        event.registerLayerDefinition(PLANE_METAL_LAYER, PlaneMetalModel::createBodyLayer);
        event.registerLayerDefinition(LARGE_PLANE_LAYER, LargePlaneModel::createBodyLayer);
        event.registerLayerDefinition(HELICOPTER_LAYER, HelicopterModel::createBodyLayer);
        event.registerLayerDefinition(PROPELLER_LAYER, PropellerModel::createBodyLayer);
        event.registerLayerDefinition(HELICOPTER_PROPELLER_LAYER, HelicopterPropellerModel::createBodyLayer);

        event.registerLayerDefinition(PARACHUTE_LAYER, ParachuteModel::createBodyLayer);

        event.registerLayerDefinition(BOOSTER, BoosterModel::createBodyLayer);
        event.registerLayerDefinition(SHOOTER, ShooterModel::createBodyLayer);
        event.registerLayerDefinition(FLOATING, FloatingModel::createBodyLayer);
        event.registerLayerDefinition(LARGE_FLOATING, LargeFloatingModel::createBodyLayer);
        event.registerLayerDefinition(HELICOPTER_FLOATING, HelicopterFloatingModel::createBodyLayer);
        event.registerLayerDefinition(SEATS, SeatsUpgradeModel::createBodyLayer);

        event.registerLayerDefinition(ARMOR, ArmorModel::createBodyLayer);
        event.registerLayerDefinition(LARGE_ARMOR, LargeArmorModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        EntityModelSet entityModelSet = Minecraft.getInstance().getEntityModels();
        event.registerEntityRenderer(SimplePlanesEntities.PLANE.get(), context -> new PlaneRenderer<>(context, new PlaneModel(entityModelSet.bakeLayer(PlanesModelLayers.PLANE_LAYER)), new PlaneMetalModel(entityModelSet.bakeLayer(PLANE_METAL_LAYER)), new PropellerModel(entityModelSet.bakeLayer(PlanesModelLayers.PROPELLER_LAYER)), 0.6f, new ResourceLocation(SimplePlanesMod.MODID, "textures/plane_upgrades/plane_metal.png")));
        event.registerEntityRenderer(SimplePlanesEntities.LARGE_PLANE.get(), context -> new PlaneRenderer<>(context, new LargePlaneModel(entityModelSet.bakeLayer(PlanesModelLayers.LARGE_PLANE_LAYER)), new PlaneMetalModel(entityModelSet.bakeLayer(PLANE_METAL_LAYER)), new PropellerModel(entityModelSet.bakeLayer(PlanesModelLayers.PROPELLER_LAYER)), 1.0f, new ResourceLocation(SimplePlanesMod.MODID, "textures/plane_upgrades/plane_metal.png")));
        event.registerEntityRenderer(SimplePlanesEntities.HELICOPTER.get(), context -> new PlaneRenderer<>(context, new HelicopterModel(entityModelSet.bakeLayer(PlanesModelLayers.HELICOPTER_LAYER)), new PlaneMetalModel(entityModelSet.bakeLayer(PLANE_METAL_LAYER)), new HelicopterPropellerModel(entityModelSet.bakeLayer(PlanesModelLayers.HELICOPTER_PROPELLER_LAYER)), 0.6f, new ResourceLocation(SimplePlanesMod.MODID, "textures/plane_upgrades/plane_metal.png")));

        event.registerEntityRenderer(SimplePlanesEntities.PARACHUTE.get(), context -> new ParachuteRenderer(context, new ParachuteModel(entityModelSet.bakeLayer(PlanesModelLayers.PARACHUTE_LAYER))));
    }

    @SubscribeEvent
    public static void bakeModelLayers(EntityRenderersEvent.AddLayers event) {
        EntityModelSet entityModelSet = event.getEntityModels();
        UpgradesModels.BOOSTER = new BoosterModel(entityModelSet.bakeLayer(BOOSTER));
        UpgradesModels.SHOOTER = new ShooterModel(entityModelSet.bakeLayer(SHOOTER));
        UpgradesModels.FLOATING = new FloatingModel(entityModelSet.bakeLayer(FLOATING));
        UpgradesModels.LARGE_FLOATING = new LargeFloatingModel(entityModelSet.bakeLayer(LARGE_FLOATING));
        UpgradesModels.HELICOPTER_FLOATING = new HelicopterFloatingModel(entityModelSet.bakeLayer(HELICOPTER_FLOATING));
        UpgradesModels.ARMOR = new ArmorModel(entityModelSet.bakeLayer(ARMOR));
        UpgradesModels.LARGE_ARMOR = new LargeArmorModel(entityModelSet.bakeLayer(LARGE_ARMOR));
        UpgradesModels.SHULKER_FOLDING = new ShulkerModel<>(entityModelSet.bakeLayer(ModelLayers.SHULKER));
        UpgradesModels.SEATS = new SeatsUpgradeModel(entityModelSet.bakeLayer(SEATS));
    }
}
