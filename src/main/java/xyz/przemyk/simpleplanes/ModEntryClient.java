package xyz.przemyk.simpleplanes;

import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.ActionResult;
import org.lwjgl.glfw.GLFW;
import xyz.przemyk.simpleplanes.events.OnRenderEvent;
import xyz.przemyk.simpleplanes.events.RenderHudEvents;
import xyz.przemyk.simpleplanes.render.*;
import xyz.przemyk.simpleplanes.setup.SimplePlanesEntities;

@Environment(EnvType.CLIENT)
public class ModEntryClient implements ClientModInitializer {
    public static KeyBinding keyBind;
    PlaneGui gui;

    @Override
    public void onInitializeClient() {
        gui = new PlaneGui();
        EntityRendererRegistry.INSTANCE.register(SimplePlanesEntities.PLANE,PlaneRenderer::new);
        EntityRendererRegistry.INSTANCE.register(SimplePlanesEntities.LARGE_PLANE, LargePlaneRenderer::new);
        EntityRendererRegistry.INSTANCE.register(SimplePlanesEntities.HELICOPTER, HelicopterRenderer::new);
        EntityRendererRegistry.INSTANCE.register(SimplePlanesEntities.MEGA_PLANE, MegaPlaneRenderer::new);
//        MinecraftForge.EVENT_BUS.register(new PlaneGui());
        keyBind = new KeyBinding("key.plane_boost.desc", GLFW.GLFW_KEY_SPACE, "key.simpleplanes.category");
        KeyBindingHelper.registerKeyBinding(keyBind);
        AutoConfig.getGuiRegistry(SimplePlanesConfig.class);


        ClientTickEvents.END_CLIENT_TICK.register(PlanesClientEvents::onClientPlayerTick );
        OnRenderEvent.PRE.EVENT.register(PlanesClientEvents::pre);
        OnRenderEvent.POST.EVENT.register(PlanesClientEvents::post);
        RenderHudEvents.EVENT.register(PlaneGui::renderGameOverlayPre);
        RenderHudEvents.EVENT.register(gui::renderExperience);
        RenderHudEvents.EVENT.register(gui::renderGameOverlayPost);
//        ClothClientHooks.SCREEN_RENDER_POST
    }
}
