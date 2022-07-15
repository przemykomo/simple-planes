package xyz.przemyk.simpleplanes.upgrades.jukebox;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.przemyk.simpleplanes.client.MovingSound;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.network.JukeboxPacket;
import xyz.przemyk.simpleplanes.network.SimplePlanesNetworking;
import xyz.przemyk.simpleplanes.setup.SimplePlanesEntities;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.LargeUpgrade;

public class JukeboxUpgrade extends LargeUpgrade {

    private ItemStack record = ItemStack.EMPTY;

    public JukeboxUpgrade(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.JUKEBOX.get(), planeEntity);
    }

    @Override
    public CompoundTag serializeNBT() {
        return record.save(new CompoundTag());
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        record = ItemStack.of(nbt);
    }

    @Override
    public void onItemRightClick(PlayerInteractEvent.RightClickItem event) {
        if (!planeEntity.level.isClientSide) {
            Player player = event.getEntity();
            ItemStack itemStack = player.getItemInHand(event.getHand());
            if (itemStack.getItem() instanceof RecordItem newRecordItem && newRecordItem != record.getItem()) {
                ItemStack oldRecord = record;
                record = itemStack.copy();
                if (!player.isCreative()) {
                    itemStack.shrink(1);
                }
                if (!oldRecord.isEmpty()) {
                    player.addItem(oldRecord);
                }
                player.awardStat(Stats.PLAY_RECORD);
                SimplePlanesNetworking.INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> planeEntity), new JukeboxPacket(planeEntity.getId(), ForgeRegistries.ITEMS.getKey(newRecordItem)));
            }
        }
    }

    @Override
    public void render(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, float partialTicks) {
        matrixStack.pushPose();
        EntityType<?> entityType = planeEntity.getType();

        if (entityType == SimplePlanesEntities.HELICOPTER.get()) {
            matrixStack.translate(0, -0.1, -1.28);
        } else {
            matrixStack.translate(0, 0, 0.1);
        }

        matrixStack.mulPose(Vector3f.ZP.rotationDegrees(180));
        matrixStack.translate(-0.4, -1, 0.3);
        matrixStack.scale(0.82f, 0.82f, 0.82f);
        BlockState state = Blocks.JUKEBOX.defaultBlockState();
        Minecraft.getInstance().getBlockRenderer().renderSingleBlock(state, matrixStack, buffer, packedLight, OverlayTexture.NO_OVERLAY, ModelData.EMPTY, null);
        matrixStack.popPose();
    }

    @Override
    public void writePacket(FriendlyByteBuf buffer) {}

    @Override
    public void readPacket(FriendlyByteBuf buffer) {}

    @Override
    public void onRemoved() {
        planeEntity.spawnAtLocation(Items.JUKEBOX);
        planeEntity.spawnAtLocation(record);
        if (planeEntity.level.isClientSide) {
            MovingSound.remove(planeEntity);
        }
    }
}
