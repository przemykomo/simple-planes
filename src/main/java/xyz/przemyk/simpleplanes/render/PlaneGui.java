package xyz.przemyk.simpleplanes.render;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.opengl.GL11;
import xyz.przemyk.simpleplanes.Config;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(Dist.CLIENT)
public class PlaneGui extends AbstractGui {
    private final ResourceLocation bar = new ResourceLocation(SimplePlanesMod.MODID, "textures/gui/hpbar.png");
    private final int tex_width = 182, tex_height = 5, bar_width = 182, bar_height = 6;


    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void renderGameOverlay(RenderGameOverlayEvent.Pre event) {
        Minecraft mc = Minecraft.getInstance();
        int scaledWidth = mc.getMainWindow().getScaledWidth();
        int scaledHeight = mc.getMainWindow().getScaledHeight();

        if (mc.player.getRidingEntity() instanceof PlaneEntity) {
            PlaneEntity planeEntity = (PlaneEntity) mc.player.getRidingEntity();
            if (event.getType() == RenderGameOverlayEvent.ElementType.EXPERIENCE) {
                event.setCanceled(true);
                int x = scaledWidth / 2 - 91;
                int y = scaledHeight - 32 + 3;

                mc.getTextureManager().bindTexture(bar);
                float oneUnit = (float) bar_width / Config.FLY_TICKS_PER_COAL.get();
                int currentWidth = (int) (oneUnit * planeEntity.getFuel());

                func_238474_b_(event.getMatrixStack(), x, y, 0, 0, tex_width, tex_height);
                int bar_image_pos = planeEntity.isSprinting() ? tex_height * 2 : tex_height;
                func_238474_b_(event.getMatrixStack(), x, y, 0, bar_image_pos, currentWidth, tex_height);
            }

            if (event.getType() == RenderGameOverlayEvent.ElementType.JUMPBAR) {
                event.setCanceled(true);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                RenderSystem.disableBlend();

                mc.getProfiler().startSection("jumpBar");
                mc.getTextureManager().bindTexture(AbstractGui.field_230665_h_);
                float f = planeEntity.getFuel();
                f /= Config.FLY_TICKS_PER_COAL.get();
                int i = 182;
                int j = (int) (f * 183.0F);
                int k = event.getWindow().getScaledHeight() - 32 + 3;
                int p_238446_2_ = event.getWindow().getScaledWidth() / 2 - 91;
                mc.ingameGUI.func_238474_b_(event.getMatrixStack(), p_238446_2_, k, 0, 84, 182, 5);
                if (j > 0) {
                    mc.ingameGUI.func_238474_b_(event.getMatrixStack(), p_238446_2_, k, 0, 89, j, 5);
                }

                mc.getProfiler().endSection();

                RenderSystem.enableBlend();
                mc.getProfiler().endSection();
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

            }

        }
    }

}

