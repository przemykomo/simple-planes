package xyz.przemyk.simpleplanes.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.HelicopterEntity;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(Dist.CLIENT)
public class PlaneGui extends AbstractGui {
    private final ResourceLocation bar = new ResourceLocation(SimplePlanesMod.MODID, "textures/gui/hpbar.png");

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void renderGameOverlayPre(RenderGameOverlayEvent.Pre event) {
        Minecraft mc = Minecraft.getInstance();

        if (mc.player.getRidingEntity() instanceof PlaneEntity) {
            PlaneEntity planeEntity = (PlaneEntity) mc.player.getRidingEntity();
            if (event.getType() == ElementType.ALL) {
                if (planeEntity.mountMessage) {
                    planeEntity.mountMessage = false;
                    if (planeEntity instanceof HelicopterEntity) {
                        Minecraft.getInstance().ingameGUI
                            .setOverlayMessage(new TranslationTextComponent("helicopter.onboard", mc.gameSettings.keyBindSneak.func_238171_j_(),
                                SimplePlanesMod.keyBind.func_238171_j_()), false);
                    } else {
                        Minecraft.getInstance().ingameGUI
                            .setOverlayMessage(new TranslationTextComponent("plane.onboard", mc.gameSettings.keyBindSneak.func_238171_j_(),
                                SimplePlanesMod.keyBind.func_238171_j_()), false);
                    }

                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void renderGameOverlayPost(RenderGameOverlayEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        int scaledWidth = mc.getMainWindow().getScaledWidth();
        int scaledHeight = mc.getMainWindow().getScaledHeight();
        MatrixStack mStack = event.getMatrixStack();

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
                        int vOffset = 35;
                        if (i + heart + 10 < health)
                            blit(mStack, x, top, GOLD, vOffset, 16, 9);
                        else if (i + heart < health)
                            blit(mStack, x, top, FULL, vOffset, 16, 9);
                        else
                            blit(mStack, x, top, EMPTY, vOffset, 16, 9);
                    }
                    right_height += 10;
                }
            }
        }
    }
}