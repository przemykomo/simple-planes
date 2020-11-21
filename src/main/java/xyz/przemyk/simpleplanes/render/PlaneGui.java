package xyz.przemyk.simpleplanes.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.HelicopterEntity;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.events.RenderHudEvents;

@Environment(EnvType.CLIENT)
public class PlaneGui extends DrawableHelper {
    private final Identifier bar = new Identifier(SimplePlanesMod.MODID, "textures/gui/hpbar.png");
    private final int tex_width = 182, tex_height = 5, bar_width = 182, bar_height = 6;

    public static ActionResult renderGameOverlayPre(RenderHudEvents.Stage stage, MatrixStack matrixStack) {
        MinecraftClient mc = MinecraftClient.getInstance();
        int scaledWidth = mc.getWindow().getScaledWidth();
        int scaledHeight = mc.getWindow().getScaledHeight();

        if (mc.player.getVehicle() instanceof PlaneEntity) {
            PlaneEntity planeEntity = (PlaneEntity) mc.player.getVehicle();
            if (stage == RenderHudEvents.Stage.FIRST) {
                if (planeEntity.mountmassage) {
                    planeEntity.mountmassage = false;
                    if (planeEntity instanceof HelicopterEntity) {
                        MinecraftClient.getInstance().inGameHud
                            .setOverlayMessage(new TranslatableText("helicopter.onboard", mc.options.keySneak.getBoundKeyLocalizedText(),
                                mc.options.keySprint.getBoundKeyLocalizedText()), false);
                    } else {
                        MinecraftClient.getInstance().inGameHud
                            .setOverlayMessage(new TranslatableText("plane.onboard", mc.options.keySneak.getBoundKeyLocalizedText(),
                                mc.options.keySprint.getBoundKeyLocalizedText()), false);
                    }

                }
            }
        }
        return ActionResult.PASS;
    }

    public ActionResult renderExperience(RenderHudEvents.Stage stage, MatrixStack matrixStack) {
        if (stage != RenderHudEvents.Stage.EXP) {
            return ActionResult.PASS;
        }
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player.getVehicle() instanceof PlaneEntity) {
            PlaneEntity planeEntity = (PlaneEntity) mc.player.getVehicle();

            int scaledWidth = mc.getWindow().getScaledWidth();
            int scaledHeight = mc.getWindow().getScaledHeight();
            int y = scaledHeight - 32 + 3;
            int x = scaledWidth / 2 - 91;

            mc.getTextureManager().bindTexture(bar);
            float fuel = planeEntity.getFuel();
//                fuel = (float) Math.log1p(fuel);
            float max_fuel = planeEntity.getMaxFuel();
//                max_fuel = (float) Math.log1p(max_fuel);
            float part = fuel / max_fuel;
            part = (float) Math.pow(part, 0.5);
                int i = (int) part;

//                part = MathHelper.clamp(part, 0, 1);
                part = part - i;
            int currentWidth = (int) (bar_width * part);

            drawTexture(matrixStack, x, y, 0, tex_height * i, tex_width, tex_height);
            drawTexture(matrixStack, x, y, 0, tex_height * (i + 1), currentWidth, tex_height);
            if (planeEntity.isSprinting()) {
                drawTexture(matrixStack, x, y, 0, 30, i == 0 ? currentWidth : tex_width, tex_height);
            }
            return ActionResult.CONSUME;
//                blit(mStack, x, y, 0, tex_height * i, tex_width, tex_height);
//                blit(mStack, x, y, 0, tex_height * (i + 1), currentWidth, tex_height);
//                if (planeEntity.isSprinting()) {
//                    blit(mStack, x, y, 0, 30, i == 0 ? currentWidth : tex_width, tex_height);
//                }
        }
        return ActionResult.PASS;
    }

    public ActionResult renderGameOverlayPost(RenderHudEvents.Stage stage,MatrixStack mStack) {
//        if(true)return;
        if(stage == RenderHudEvents.Stage.LAST) {
            MinecraftClient mc = MinecraftClient.getInstance();
            int scaledWidth = mc.getWindow().getScaledWidth();
            int scaledHeight = mc.getWindow().getScaledHeight();

            if (mc.player.getVehicle() instanceof PlaneEntity) {
                PlaneEntity planeEntity = (PlaneEntity) mc.player.getVehicle();
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
                            drawTexture(mStack, x, top, GOLD, vOffset, 16, 9);
                        else if (i + heart < health)
                            drawTexture(mStack, x, top, FULL, vOffset, 16, 9);
                        else
                            drawTexture(mStack, x, top, EMPTY, vOffset, 16, 9);
                    }
                    right_height += 10;

                }

            }
        }
        return ActionResult.PASS;
    }

}

