package xyz.przemyk.simpleplanes.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.przemyk.simpleplanes.Config;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.HelicopterEntity;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(Dist.CLIENT)
public class PlaneGui extends AbstractGui {
    private final ResourceLocation bar = new ResourceLocation(SimplePlanesMod.MODID, "textures/gui/hpbar.png");
    private final int tex_width = 182, tex_height = 5, bar_width = 182, bar_height = 6;

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void renderGameOverlayPre(RenderGameOverlayEvent.Pre event) {
        Minecraft mc = Minecraft.getInstance();
        int scaledWidth = mc.getMainWindow().getScaledWidth();
        int scaledHeight = mc.getMainWindow().getScaledHeight();


        if (mc.player.getRidingEntity() instanceof PlaneEntity) {
            PlaneEntity planeEntity = (PlaneEntity) mc.player.getRidingEntity();
            if (event.getType() == ElementType.ALL) {
                if (planeEntity.mountmassage) {
                    planeEntity.mountmassage = false;
                    if (planeEntity instanceof HelicopterEntity) {
                        Minecraft.getInstance().ingameGUI
                            .setOverlayMessage(new TranslationTextComponent("helicopter.onboard", mc.gameSettings.keyBindSneak.isPressed(),
                                SimplePlanesMod.keyBind.isPressed()), false);
                    } else {
                        Minecraft.getInstance().ingameGUI
                            .setOverlayMessage(new TranslationTextComponent("plane.onboard", mc.gameSettings.keyBindSneak.isPressed(),
                                SimplePlanesMod.keyBind.isPressed()), false);
                    }

                }
            }
            if (event.getType() == RenderGameOverlayEvent.ElementType.EXPERIENCE) {
                event.setCanceled(true);
                int x = scaledWidth / 2 - 91;
                int y = scaledHeight - 32 + 3;

                mc.getTextureManager().bindTexture(bar);
                float fuel = planeEntity.getFuel();
//                fuel = (float) Math.log1p(fuel);
                float max_fuel =planeEntity.getMaxFuel();
//                max_fuel = (float) Math.log1p(max_fuel);
                float part = fuel / max_fuel;
                part = (float) Math.pow(part, 0.5);
                part = MathHelper.clamp(part, 0, 1);
                int currentWidth = (int) (bar_width * part);

                blit(x, y, 0, 0, tex_width, tex_height);
                int bar_image_pos = planeEntity.isSprinting() ? tex_height * 2 : tex_height;
                blit(x, y, 0, bar_image_pos, currentWidth, tex_height);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void renderGameOverlayPost(RenderGameOverlayEvent.Post event) {
//        if(true)return;
        Minecraft mc = Minecraft.getInstance();
        int scaledWidth = mc.getMainWindow().getScaledWidth();
        int scaledHeight = mc.getMainWindow().getScaledHeight();

        if (mc.player.getRidingEntity() instanceof PlaneEntity) {
            PlaneEntity planeEntity = (PlaneEntity) mc.player.getRidingEntity();
            if (event.getType() == ElementType.ALL) {
                mc.getTextureManager().bindTexture(bar);
                int left_align = scaledWidth / 2 + 91;

                int health = (int) Math.ceil(planeEntity.getHealth());
                float healthMax = planeEntity.getMaxHealth();
                int hearts = (int) (healthMax);

                if (hearts > 10) hearts = 10;

                final int FULL = 0;
                final int EMPTY = 16;
                final int GOLD = 32;
                int right_height = 39;
                int max_row_size = 5;

                for (int heart = 0; hearts > 0; heart += max_row_size) {
                    int top = scaledHeight - right_height;

                    int rowCount = Math.min(hearts, max_row_size);
                    hearts -= rowCount;

                    for (int i = 0; i < rowCount; ++i) {
                        int x = left_align - i * 16 - 16;
                        if (i + heart + 10 < health)
                            blit(x, top, GOLD, 15, 16, 9);
                        else if (i + heart < health)
                            blit(x, top, FULL, 15, 16, 9);
                        else
                            blit(x, top, EMPTY, 15, 16, 9);
                    }
                    right_height += 10;
                }
            }

        }
    }

}

