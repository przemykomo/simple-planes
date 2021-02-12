package xyz.przemyk.simpleplanes.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.settings.PointOfView;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;
import xyz.przemyk.simpleplanes.MathUtil;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.client.gui.FurnaceEngineScreen;
import xyz.przemyk.simpleplanes.client.gui.PlaneWorkbenchScreen;
import xyz.przemyk.simpleplanes.client.gui.RemoveUpgradesScreen;
import xyz.przemyk.simpleplanes.client.render.PlaneRenderer;
import xyz.przemyk.simpleplanes.client.render.models.*;
import xyz.przemyk.simpleplanes.entities.HelicopterEntity;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.network.BoostPacket;
import xyz.przemyk.simpleplanes.network.OpenEngineInventoryPacket;
import xyz.przemyk.simpleplanes.network.OpenInventoryPacket;
import xyz.przemyk.simpleplanes.network.PlaneNetworking;
import xyz.przemyk.simpleplanes.setup.SimplePlanesContainers;
import xyz.przemyk.simpleplanes.setup.SimplePlanesEntities;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;
import xyz.przemyk.simpleplanes.upgrades.furnace.FurnaceEngineUpgrade;
import xyz.przemyk.simpleplanes.upgrades.storage.ChestUpgrade;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ClientEventHandler {

    @OnlyIn(Dist.CLIENT)
    public static KeyBinding keyBind;
    @OnlyIn(Dist.CLIENT)
    public static KeyBinding openEngineInventoryKey;

    public static void clientSetup() {
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.PLANE.get(), manager -> new PlaneRenderer<>(manager, new PlaneModel(), new PropellerModel(), 0.6F));
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.LARGE_PLANE.get(), manager -> new PlaneRenderer<>(manager, new LargePlaneModel(), new PropellerModel(), 1.0F));
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.HELICOPTER.get(), manager -> new PlaneRenderer<>(manager, new HelicopterModel(), new HelicopterPropellerModel(), 0.6F));

        keyBind = new KeyBinding("key.plane_boost.desc", GLFW.GLFW_KEY_SPACE, "key.simpleplanes.category");
        openEngineInventoryKey = new KeyBinding("key.plane_engine_open.desc", GLFW.GLFW_KEY_X, "key.simpleplanes.category");
        ClientRegistry.registerKeyBinding(keyBind);
        ClientRegistry.registerKeyBinding(openEngineInventoryKey);

        ScreenManager.registerFactory(SimplePlanesContainers.PLANE_WORKBENCH.get(), PlaneWorkbenchScreen::new);
        ScreenManager.registerFactory(SimplePlanesContainers.FURNACE_ENGINE.get(), FurnaceEngineScreen::new);
        ScreenManager.registerFactory(SimplePlanesContainers.UPGRADES_REMOVAL.get(), RemoveUpgradesScreen::new);
    }

    private static boolean playerRotationNeedToPop = false;

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onRenderPre(RenderLivingEvent.Pre<LivingEntity, ?> event) {
        LivingEntity livingEntity = event.getEntity();
        Entity entity = livingEntity.getLowestRidingEntity();
        if (entity instanceof PlaneEntity) {
            PlaneEntity planeEntity = (PlaneEntity) entity;
            MatrixStack matrixStack = event.getMatrixStack();
            matrixStack.push();
            playerRotationNeedToPop = true;
            double firstPersonYOffset = 0.7D;
            boolean isPlayerRidingInFirstPersonView = Minecraft.getInstance().player != null && planeEntity.isPassenger(Minecraft.getInstance().player)
                && (Minecraft.getInstance()).gameSettings.pointOfView == PointOfView.FIRST_PERSON;
            if (isPlayerRidingInFirstPersonView) {
                matrixStack.translate(0.0D, firstPersonYOffset, 0.0D);
            }

            matrixStack.translate(0, 0.7, 0);
            Quaternion quaternion = MathUtil.lerpQ(event.getPartialRenderTick(), planeEntity.getQ_Prev(), planeEntity.getQ_Client());
            quaternion.set(quaternion.getX(), -quaternion.getY(), -quaternion.getZ(), quaternion.getW());
            matrixStack.rotate(quaternion);
            float rotationYaw = MathUtil.lerpAngle(event.getPartialRenderTick(), entity.prevRotationYaw, entity.rotationYaw);

            matrixStack.rotate(Vector3f.YP.rotationDegrees(rotationYaw));
            matrixStack.translate(0, -0.7, 0);
            if (isPlayerRidingInFirstPersonView) {
                matrixStack.translate(0.0D, -firstPersonYOffset, 0.0D);
            }
            if (MathUtil.degreesDifferenceAbs(planeEntity.rotationRoll, 0) > 90) {
                livingEntity.rotationYawHead = planeEntity.rotationYaw * 2 - livingEntity.rotationYawHead;
            }
            if (MathUtil.degreesDifferenceAbs(planeEntity.prevRotationRoll, 0) > 90) {
                livingEntity.prevRotationYawHead = planeEntity.prevRotationYaw * 2 - livingEntity.prevRotationYawHead;
            }
        }
    }

    @SuppressWarnings("rawtypes")
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onRenderPost(RenderLivingEvent.Post event) {
        if (playerRotationNeedToPop) {
            playerRotationNeedToPop = false;
            event.getMatrixStack().pop();
            Entity entity = event.getEntity().getLowestRidingEntity();
            PlaneEntity planeEntity = (PlaneEntity) entity;

            if (MathUtil.degreesDifferenceAbs(planeEntity.rotationRoll, 0) > 90) {
                event.getEntity().rotationYawHead = planeEntity.rotationYaw * 2 - event.getEntity().rotationYawHead;
            }
            if (MathUtil.degreesDifferenceAbs(planeEntity.prevRotationRoll, 0) > 90) {
                event.getEntity().prevRotationYawHead = planeEntity.prevRotationYaw * 2 - event.getEntity().prevRotationYawHead;
            }
        }
    }

    private static boolean old_sprint = false;

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onClientPlayerTick(PlayerTickEvent event) {
        final PlayerEntity player = event.player;
        if ((event.phase == Phase.END) && (player instanceof ClientPlayerEntity)) {
            if (player.getRidingEntity() instanceof PlaneEntity) {
                PlaneEntity planeEntity = (PlaneEntity) player.getRidingEntity();
                Minecraft mc = Minecraft.getInstance();
                if (mc.gameSettings.pointOfView == PointOfView.FIRST_PERSON) {
                    float yawDiff = planeEntity.rotationYaw - planeEntity.prevRotationYaw;
                    player.rotationYaw += yawDiff;
                    float relativePlayerYaw = MathHelper.wrapDegrees(player.rotationYaw - planeEntity.rotationYaw);
                    float clampedRelativePlayerYaw = MathHelper.clamp(relativePlayerYaw, -105.0F, 105.0F);

                    float diff = (clampedRelativePlayerYaw - relativePlayerYaw);
                    player.prevRotationYaw += diff;
                    player.rotationYaw += diff;
                    player.setRotationYawHead(player.rotationYaw);

                    relativePlayerYaw = MathHelper.wrapDegrees(player.rotationPitch - 0);
                    clampedRelativePlayerYaw = MathHelper.clamp(relativePlayerYaw, -50, 50);
                    float perc = (clampedRelativePlayerYaw - relativePlayerYaw) * 0.5f;
                    player.prevRotationPitch += perc;
                    player.rotationPitch += perc;
                } else {
                    planeEntity.applyYawToEntity(player);
                }

                if (planeEntity.engineUpgrade != null && mc.currentScreen == null && mc.loadingGui == null && openEngineInventoryKey.isPressed() && planeEntity.engineUpgrade.canOpenGui()) {
                    PlaneNetworking.INSTANCE.sendToServer(new OpenEngineInventoryPacket());
                }

                boolean isSprinting = keyBind.isKeyDown();
                if (isSprinting != old_sprint || Math.random() < 0.1) {
                    PlaneNetworking.INSTANCE.sendToServer(new BoostPacket(isSprinting));
                }
                old_sprint = isSprinting;
            } else {
                old_sprint = false;
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onCameraSetup(EntityViewRenderEvent.CameraSetup event) {
        ActiveRenderInfo renderInfo = event.getInfo();
        Entity entity = renderInfo.getRenderViewEntity();
        if (entity instanceof ClientPlayerEntity && entity.getRidingEntity() instanceof PlaneEntity) {
            PlaneEntity planeEntity = (PlaneEntity) entity.getRidingEntity();
            ClientPlayerEntity playerEntity = (ClientPlayerEntity) entity;

            if (renderInfo.isThirdPerson()) {
                renderInfo.movePosition(-renderInfo.calcCameraDistance(4.0D * (planeEntity.getCameraDistanceMultiplayer() - 1.0)), 0.0D, 0.0D);
            } else {
                double partialTicks = event.getRenderPartialTicks();

                Quaternion q_prev = planeEntity.getQ_Prev();
                int max = 105;
                float diff = (float) MathHelper.clamp(MathUtil.wrapSubtractDegrees(planeEntity.prevRotationYaw, playerEntity.prevRotationYaw), -max, max);
                float pitch = MathHelper.clamp(event.getPitch(), -45, 45);
                q_prev.multiply(Vector3f.YP.rotationDegrees(diff));
                q_prev.multiply(Vector3f.XP.rotationDegrees(pitch));
                MathUtil.EulerAngles angles_prev = MathUtil.toEulerAngles(q_prev);

                Quaternion q_client = planeEntity.getQ_Client();
                diff = (float) MathHelper.clamp(MathUtil.wrapSubtractDegrees(planeEntity.rotationYaw, playerEntity.rotationYaw), -max, max);
                q_client.multiply(Vector3f.YP.rotationDegrees(diff));
                q_client.multiply(Vector3f.XP.rotationDegrees(pitch));
                MathUtil.EulerAngles angles = MathUtil.toEulerAngles(q_client);

                event.setPitch(-(float) MathUtil.lerpAngle180(partialTicks, angles_prev.pitch, angles.pitch));
                event.setYaw((float) MathUtil.lerpAngle(partialTicks, angles_prev.yaw, angles.yaw));
                event.setRoll(-(float) MathUtil.lerpAngle(partialTicks, angles_prev.roll, angles.roll));
            }
        }
    }

    public static final ResourceLocation HUD_TEXTURE = new ResourceLocation(SimplePlanesMod.MODID, "textures/gui/plane_hud.png");

    private static int blitOffset;

    @SubscribeEvent()
    public static void renderGameOverlayPre(RenderGameOverlayEvent.Pre event) {
        Minecraft mc = Minecraft.getInstance();
        int scaledWidth = mc.getMainWindow().getScaledWidth();
        int scaledHeight = mc.getMainWindow().getScaledHeight();
        MatrixStack matrixStack = event.getMatrixStack();

        if (mc.player.getRidingEntity() instanceof PlaneEntity) {
            PlaneEntity planeEntity = (PlaneEntity) mc.player.getRidingEntity();
            if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
                mc.getTextureManager().bindTexture(HUD_TEXTURE);
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
                                keyBind.func_238171_j_()), false);
                    } else {
                        mc.ingameGUI.setOverlayMessage(new TranslationTextComponent("plane.onboard", mc.gameSettings.keyBindSneak.func_238171_j_(),
                                keyBind.func_238171_j_()), false);
                    }

                }
            } else if (event.getType() == RenderGameOverlayEvent.ElementType.FOOD) {
                event.setCanceled(true);
            }
        }
    }

    private static void renderHotbarItem(MatrixStack matrixStack, int x, int y, float partialTicks, PlayerEntity player, ItemStack stack, ItemRenderer itemRenderer, Minecraft mc) {
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

    private static void blit(MatrixStack matrixStack, int x, int y, int uOffset, int vOffset, int uWidth, int vHeight) {
        AbstractGui.blit(matrixStack, x, y, blitOffset, (float)uOffset, (float)vOffset, uWidth, vHeight, 256, 256);
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void planeInventory(GuiOpenEvent event) {
        final ClientPlayerEntity player = Minecraft.getInstance().player;
        if (event.getGui() instanceof InventoryScreen && player.getRidingEntity() instanceof PlaneEntity) {
//            event.setCanceled(true);
            final PlaneEntity plane = (PlaneEntity) player.getRidingEntity();
            Upgrade chest = plane.upgrades.getOrDefault(SimplePlanesUpgrades.CHEST.getId(), null);
            if (chest instanceof ChestUpgrade) {

                ChestUpgrade chest1 = (ChestUpgrade) chest;
                IInventory inventory = chest1.inventory;
                if (inventory != null) {
                    event.setCanceled(true);
//                    PlaneNetworking.OPEN_INVENTORY.sendToServer(true);
                    PlaneNetworking.INSTANCE.sendToServer(new OpenInventoryPacket());
                }
//                StringTextComponent hi = new StringTextComponent("hi");
//                ScreenManager.openScreen(ContainerType.GENERIC_9X3,event.getGui().getMinecraft(),0,hi);
//                ChestScreen gui = new ChestScreen(ChestContainer.createGeneric9X3(1, player.inventory, inventory), player.inventory, hi);
//                event.setGui(gui);
            }
        }
    }
}
