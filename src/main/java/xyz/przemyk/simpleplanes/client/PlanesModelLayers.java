package xyz.przemyk.simpleplanes.client;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.client.render.models.*;
import xyz.przemyk.simpleplanes.upgrades.booster.BoosterModel;
import xyz.przemyk.simpleplanes.upgrades.floating.FloatingModel;
import xyz.przemyk.simpleplanes.upgrades.floating.HelicopterFloatingModel;
import xyz.przemyk.simpleplanes.upgrades.floating.LargeFloatingModel;
import xyz.przemyk.simpleplanes.upgrades.shooter.ShooterModel;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class PlanesModelLayers {
    public static final ModelLayerLocation PLANE_LAYER = new ModelLayerLocation(new ResourceLocation(SimplePlanesMod.MODID, "plane"), "main");
    public static final ModelLayerLocation LARGE_PLANE_LAYER = new ModelLayerLocation(new ResourceLocation(SimplePlanesMod.MODID, "large_plane"), "main");
    public static final ModelLayerLocation HELICOPTER_LAYER = new ModelLayerLocation(new ResourceLocation(SimplePlanesMod.MODID, "helicopter"), "main");
    public static final ModelLayerLocation PROPELLER_LAYER = new ModelLayerLocation(new ResourceLocation(SimplePlanesMod.MODID, "propeller"), "main");
    public static final ModelLayerLocation HELICOPTER_PROPELLER_LAYER = new ModelLayerLocation(new ResourceLocation(SimplePlanesMod.MODID, "helicopter_propeller"), "main");

    public static final ModelLayerLocation BOOSTER = new ModelLayerLocation(new ResourceLocation(SimplePlanesMod.MODID, "booster"), "main");
    public static final ModelLayerLocation SHOOTER = new ModelLayerLocation(new ResourceLocation(SimplePlanesMod.MODID, "shooter"), "main");
    public static final ModelLayerLocation FLOATING = new ModelLayerLocation(new ResourceLocation(SimplePlanesMod.MODID, "floating"), "main"); //todo: maybe the same resource location, but different layer name?
    public static final ModelLayerLocation LARGE_FLOATING = new ModelLayerLocation(new ResourceLocation(SimplePlanesMod.MODID, "large_floating"), "main");
    public static final ModelLayerLocation HELICOPTER_FLOATING = new ModelLayerLocation(new ResourceLocation(SimplePlanesMod.MODID, "helicopter_floating"), "main");

    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(PLANE_LAYER, PlaneModel::createBodyLayer);
        event.registerLayerDefinition(LARGE_PLANE_LAYER, LargePlaneModel::createBodyLayer);
        event.registerLayerDefinition(HELICOPTER_LAYER, HelicopterModel::createBodyLayer);
        event.registerLayerDefinition(PROPELLER_LAYER, PropellerModel::createBodyLayer);
        event.registerLayerDefinition(HELICOPTER_PROPELLER_LAYER, HelicopterPropellerModel::createBodyLayer);

        event.registerLayerDefinition(BOOSTER, BoosterModel::createBodyLayer);
        event.registerLayerDefinition(SHOOTER, ShooterModel::createBodyLayer);
        event.registerLayerDefinition(FLOATING, FloatingModel::createBodyLayer);
        event.registerLayerDefinition(LARGE_FLOATING, LargeFloatingModel::createBodyLayer);
        event.registerLayerDefinition(HELICOPTER_FLOATING, HelicopterFloatingModel::createBodyLayer);
    }
}
