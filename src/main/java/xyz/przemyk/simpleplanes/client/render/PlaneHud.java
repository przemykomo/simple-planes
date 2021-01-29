package xyz.przemyk.simpleplanes.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.HelicopterEntity;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.upgrades.furnace.FurnaceEngineUpgrade;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(Dist.CLIENT)
public class PlaneHud {

    private static final ResourceLocation TEXTURE = new ResourceLocation(SimplePlanesMod.MODID, "textures/gui/plane_hud.png");

    private int blitOffset;

    @SubscribeEvent()
    public void renderGameOverlayPre(RenderGameOverlayEvent.Pre event) {
        Minecraft mc = Minecraft.getInstance();
        int scaledWidth = mc.getMainWindow().getScaledWidth();
        int scaledHeight = mc.getMainWindow().getScaledHeight();
        MatrixStack matrixStack = event.getMatrixStack();

        if (mc.player.getRidingEntity() instanceof PlaneEntity) {
            PlaneEntity planeEntity = (PlaneEntity) mc.player.getRidingEntity();
            if (event.getType() == ElementType.ALL) {
                mc.getTextureManager().bindTexture(TEXTURE);
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
                            blit(matrixStack, x, top, GOLD, vOffset, 16, 9);
                        else if (i + heart < health)
                            blit(matrixStack, x, top, FULL, vOffset, 16, 9);
                        else
                            blit(matrixStack, x, top, EMPTY, vOffset, 16, 9);
                    }
                    right_height += 10;
                }

                if (planeEntity.engineUpgrade instanceof FurnaceEngineUpgrade) {
                    FurnaceEngineUpgrade furnaceEngineUpgrade = (FurnaceEngineUpgrade) planeEntity.engineUpgrade;
                    ItemStack offhandStack = mc.player.getHeldItemOffhand();
                    HandSide primaryHand = mc.player.getPrimaryHand();
                    int i = scaledWidth / 2;
                    int lastBlitOffset = blitOffset;
                    blitOffset = -90;
                    if (primaryHand == HandSide.LEFT || offhandStack.isEmpty()) {
                        // render on left side
                        blit(matrixStack, i - 91 - 29, scaledHeight - 40, 0, 44, 22, 40);
                    } else {
                        // render on right side
                        blit(matrixStack, i + 91, scaledHeight - 40, 0, 44, 22, 40);
                    }

                    if (furnaceEngineUpgrade.burnTime > 0) {
                        int burnTimeTotal = furnaceEngineUpgrade.burnTimeTotal == 0 ? 200 : furnaceEngineUpgrade.burnTimeTotal;
                        int burnLeftScaled = furnaceEngineUpgrade.burnTime * 13 / burnTimeTotal;
                        if (primaryHand == HandSide.LEFT || offhandStack.isEmpty()) {
                            // render on left side
                            blit(matrixStack, i - 91 - 29 + 4, scaledHeight - 40 + 16 - burnLeftScaled, 22, 56 - burnLeftScaled, 14, burnLeftScaled + 1);
                        } else {
                            // render on right side
                            blit(matrixStack, i + 91 + 4, scaledHeight - 40 + 16 - burnLeftScaled, 22, 56 - burnLeftScaled, 14, burnLeftScaled + 1);
                        }
                    }

                    blitOffset = lastBlitOffset;

                    ItemStack fuelStack = furnaceEngineUpgrade.itemStackHandler.getStackInSlot(0);
                    if (!fuelStack.isEmpty()) {
                        int i2 = scaledHeight - 16 - 3;
                        if (primaryHand == HandSide.LEFT || offhandStack.isEmpty()) {
                            // render on left side
                            renderHotbarItem(matrixStack, i - 91 - 26, i2, event.getPartialTicks(), mc.player, fuelStack, mc.getItemRenderer(), mc);
                        } else {
                            // render on right side
                            renderHotbarItem(matrixStack, i + 91 + 3, i2, event.getPartialTicks(), mc.player, fuelStack, mc.getItemRenderer(), mc);
                        }
                    }
                }

                if (planeEntity.mountMessage) {
                    planeEntity.mountMessage = false;
                    if (planeEntity instanceof HelicopterEntity) {
                        mc.ingameGUI.setOverlayMessage(new TranslationTextComponent("helicopter.onboard", mc.gameSettings.keyBindSneak.func_238171_j_(),
                                SimplePlanesMod.keyBind.func_238171_j_()), false);
                    } else {
                        mc.ingameGUI.setOverlayMessage(new TranslationTextComponent("plane.onboard", mc.gameSettings.keyBindSneak.func_238171_j_(),
                                SimplePlanesMod.keyBind.func_238171_j_()), false);
                    }

                }
            } else if (event.getType() == ElementType.FOOD) {
                event.setCanceled(true);
            }
        }
    }

    private void renderHotbarItem(MatrixStack matrixStack, int x, int y, float partialTicks, PlayerEntity player, ItemStack stack, ItemRenderer itemRenderer, Minecraft mc) {
        if (!stack.isEmpty()) {
            float f = (float)stack.getAnimationsToGo() - partialTicks;
            if (f > 0.0F) {
                matrixStack.push();
                float f1 = 1.0F + f / 5.0F;
                matrixStack.translate((float)(x + 8), (float)(y + 12), 0.0F);
                matrixStack.scale(1.0F / f1, (f1 + 1.0F) / 2.0F, 1.0F);
                matrixStack.translate((float)(-(x + 8)), (float)(-(y + 12)), 0.0F);
            }

            itemRenderer.renderItemAndEffectIntoGUI(player, stack, x, y);
            if (f > 0.0F) {
                matrixStack.pop();
            }

            itemRenderer.renderItemOverlays(mc.fontRenderer, stack, x, y);
        }
    }

    private void blit(MatrixStack matrixStack, int x, int y, int uOffset, int vOffset, int uWidth, int vHeight) {
        AbstractGui.blit(matrixStack, x, y, blitOffset, (float)uOffset, (float)vOffset, uWidth, vHeight, 256, 256);
    }
}