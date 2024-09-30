package xyz.przemyk.simpleplanes.datapack;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public record PayloadEntry(
    Item item, Block renderBlock, EntityType<?> dropSpawnEntity, CompoundTag compoundTag) {
}
