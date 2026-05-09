package sappy.simpleplanes.upgrades.jukebox;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EndermanRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.model.data.ModelData;
import net.neoforged.neoforge.network.PacketDistributor;
import sappy.simpleplanes.SPEntities;
import sappy.simpleplanes.client.render.PlaneRenderState;
import sappy.simpleplanes.entities.PlaneEntity;
import sappy.simpleplanes.upgrades.LargeUpgrade;
import sappy.simpleplanes.SPUpgrades;

public class JukeboxUpgrade extends LargeUpgrade {

    private ItemStack record = ItemStack.EMPTY;

    public JukeboxUpgrade(PlaneEntity planeEntity) {
        super(SPUpgrades.JUKEBOX.get(), planeEntity);
    }

    @Override
    public Tag serializeNBT() {
        return ItemStack.CODEC.encodeStart(NbtOps.INSTANCE, record).getOrThrow();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        ItemStack.CODEC.parse(NbtOps.INSTANCE, nbt).ifSuccess(r -> record = r);
    }

    @Override
    public void onItemRightClick(PlayerInteractEvent.RightClickItem event) {
        if (!planeEntity.level().isClientSide()) {
            Player player = event.getEntity();
            ItemStack itemStack = player.getItemInHand(event.getHand());
            if (!itemStack.is(record.getItem())) {
                ItemStack oldRecord = record;
                record = itemStack.copy();
                if (!player.isCreative()) {
                    itemStack.shrink(1);
                }
                if (!oldRecord.isEmpty()) {
                    player.addItem(oldRecord);
                }
                player.awardStat(Stats.PLAY_RECORD);
                PacketDistributor.sendToPlayersTrackingEntity(planeEntity,
                        new JukeboxPacket(BuiltInRegistries.ITEM.getKey(itemStack.getItem()), planeEntity.getId()));
            }
        }
    }

    @Override
    public void render(PlaneRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector,
            CameraRenderState camera) {
        poseStack.pushPose();
        EntityType<?> entityType = planeEntity.getType();

        if (entityType == SPEntities.HELI.get()) {
            poseStack.translate(0, -0.1, -1.28);
        } else {
            poseStack.translate(0, 0, 0.1);
        }

        poseStack.mulPose(Axis.ZP.rotationDegrees(180));
        poseStack.translate(-0.4, -1, 0.3);
        poseStack.scale(0.82f, 0.82f, 0.82f);
        BlockState blockState = Blocks.JUKEBOX.defaultBlockState();
        submitNodeCollector.submitBlock(poseStack, blockState, state.lightCoords, OverlayTexture.NO_OVERLAY,
                state.outlineColor);
        poseStack.popPose();
    }

    @Override
    public void writePacket(RegistryFriendlyByteBuf buffer) {
    }

    @Override
    public void readPacket(RegistryFriendlyByteBuf buffer) {
    }

    @Override
    public void onRemoved() {
        if (planeEntity.level() instanceof ServerLevel level) {
            planeEntity.spawnAtLocation(level, record);
        } else {
            MovingSound.remove(planeEntity);
        }
    }

    @Override
    public ItemStack getItemStack() {
        return Items.JUKEBOX.getDefaultInstance();
    }
}
