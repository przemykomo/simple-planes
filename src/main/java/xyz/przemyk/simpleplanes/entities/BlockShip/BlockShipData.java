package xyz.przemyk.simpleplanes.entities.BlockShip;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.IDataSerializer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.palette.IPalette;
import net.minecraft.util.palette.IdentityPalette;
import net.minecraft.util.palette.PalettedContainer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlockShipData {
    private Map<BlockPos, PalettedContainer<BlockState>> data;
    private static final IPalette<BlockState> REGISTRY_PALETTE = new IdentityPalette<>(Block.BLOCK_STATE_IDS, Blocks.AIR.getDefaultState());
    public BlockPos p1, p2;
    public List<BlockPos> seats;

    public BlockShipData() {
//        this.data = createContainer();
        data = new HashMap<>();
        p1 = new BlockPos(0, 0, 0);
        p2 = new BlockPos(0, 0, 0);
        seats = new ArrayList<>();
    }

    public Vector3d getCenter() {
        return new Vector3d((p1.getX() + p2.getX() + 1.) / 2.,
            (p1.getY() + p2.getY() + 1.) / 2.,
            (p1.getZ() + p2.getZ() + 1.) / 2.);
    }

    public void recalculateSize() {
        boolean any = false;
        Iterable<BlockPos> allInBoxMutable = BlockPos.getAllInBoxMutable(p1, p2);
        p1 = new BlockPos(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
        p2 = new BlockPos(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
        for (BlockPos pos : allInBoxMutable) {
            if (!get(pos).isAir()) {
                any = true;
                updateBounds(pos);
            }
        }
    }

    private PalettedContainer<BlockState> createContainer() {
        return new PalettedContainer<BlockState>(REGISTRY_PALETTE, Block.BLOCK_STATE_IDS, NBTUtil::readBlockState, NBTUtil::writeBlockState, Blocks.AIR.getDefaultState());
    }

    public void read(PacketBuffer packetBufferIn) {
        int size = packetBufferIn.readInt();
        for (int i = 0; i < size; i++) {
            BlockPos pos = packetBufferIn.readBlockPos();
            PalettedContainer<BlockState> container = createContainer();
            container.read(packetBufferIn);
            this.data.put(pos, container);
        }
        p1 = packetBufferIn.readBlockPos();
        p2 = packetBufferIn.readBlockPos();
        size = packetBufferIn.readInt();
        for (int i = 0; i < size; i++) {
            BlockPos pos = packetBufferIn.readBlockPos();
            this.seats.add(pos);
        }
    }

    public void write(PacketBuffer packetBufferIn) {
        packetBufferIn.writeInt(data.size());
        data.forEach((i, blockStatePalettedContainer) -> {
            packetBufferIn.writeBlockPos(i);
            blockStatePalettedContainer.write(packetBufferIn);
        });
        packetBufferIn.writeBlockPos(p1);
        packetBufferIn.writeBlockPos(p2);
        packetBufferIn.writeInt(seats.size());
        seats.forEach(pos -> {
            packetBufferIn.writeBlockPos(pos);
        });
    }

    public void readAdditional(CompoundNBT compound) {
        ListNBT shipData = compound.getList("ShipData", 10);
        for (int i = 0; i < shipData.size(); ++i) {
            CompoundNBT compoundnbt1 = shipData.getCompound(i);
            BlockPos j = readBlockPos(compoundnbt1, "ShipIndex");
            PalettedContainer<BlockState> container = createContainer();
            container.readChunkPalette(compoundnbt1.getList("Palette", 10), compoundnbt1.getLongArray("BlockStates"));
            this.data.put(j, container);
        }

        this.p1 = readBlockPos(compound, "p1");
        this.p2 = readBlockPos(compound, "p2");
        ListNBT seats = compound.getList("Seats", 10);
        this.seats = new ArrayList<>();
        for (int i = 0; i < seats.size(); ++i) {
            CompoundNBT compoundnbt1 = seats.getCompound(i);
            BlockPos j = readBlockPos(compoundnbt1, "seat");
            this.seats.add(j);
        }

    }

    private BlockPos readBlockPos(CompoundNBT compound, String p1) {
        int[] p1s = compound.getIntArray(p1);
        return new BlockPos(p1s[0], p1s[1], p1s[2]);
    }

    public void writeAditonal(CompoundNBT compound) {
        compound.putInt("ShipSize", data.size());
        ListNBT listNBT = new ListNBT();
        data.forEach((i, blockStatePalettedContainer) -> {
            CompoundNBT compoundnbt1 = new CompoundNBT();
            putPos(compoundnbt1, i, "ShipIndex");
            blockStatePalettedContainer.writeChunkPalette(compoundnbt1, "Palette", "BlockStates");
            listNBT.add(compoundnbt1);
        });
        compound.put("ShipData", listNBT);
        BlockPos p1 = this.p1;
        putPos(compound, p1, "p1");
        putPos(compound, p2, "p2");
        ListNBT listNBT2 = new ListNBT();
        seats.forEach(pos -> {
                CompoundNBT compoundnbt1 = new CompoundNBT();
                putPos(compoundnbt1, pos, "seat");
                listNBT2.add(compoundnbt1);
            }
        );
        compound.put("Seats", listNBT2);
    }

    private void putPos(CompoundNBT compound, BlockPos pos, String name) {
        int[] coordlist = {pos.getX(), pos.getY(), pos.getZ()};
        compound.putIntArray(name, coordlist);
    }


    public void swap(BlockPos pos, BlockState blockState1) {
        swap(pos.getX(),pos.getY(),pos.getZ(),blockState1);
    }

    public void swap(int x, int y, int z, BlockState blockState1) {
        int x1 = x & 15;
        int x2 = x >> 4;
        int y1 = y & 15;
        int y2 = y >> 4;
        int z1 = z & 15;
        int z2 = z >> 4;
        BlockPos index = new BlockPos(x2, y2, z2);
        data.putIfAbsent(index, createContainer());
        PalettedContainer<BlockState> palettedContainer = this.data.get(index);
        palettedContainer.swap(x1, y1, z1, blockState1);
        updateBounds(x, y, z);
    }

    private void updateBounds(BlockPos pos) {
        updateBounds(pos.getX(), pos.getY(), pos.getZ());
    }

    private void updateBounds(int x, int y, int z) {
        if (x < p1.getX()) {
            p1 = new BlockPos(x, p1.getY(), p1.getZ());
        }
        if (y < p1.getY()) {
            p1 = new BlockPos(p1.getX(), y, p1.getZ());
        }
        if (z < p1.getZ()) {
            p1 = new BlockPos(p1.getX(), p1.getY(), z);
        }

        if (x > p2.getX()) {
            p2 = new BlockPos(x, p2.getY(), p2.getZ());
        }
        if (y > p2.getY()) {
            p2 = new BlockPos(p2.getX(), y, p2.getZ());
        }
        if (z > p2.getZ()) {
            p2 = new BlockPos(p2.getX(), p2.getY(), z);
        }
    }

    private static int getIndex(int x, int y, int z) {
        return y << 20 | z << 10 | x;
    }

    public BlockState get(BlockPos pos) {
        return get(pos.getX(), pos.getY(), pos.getZ());
    }

    public BlockState get(int x, int y, int z) {
        int x1 = x & 15;
        int x2 = x >> 4;
        int y1 = y & 15;
        int y2 = y >> 4;
        int z1 = z & 15;
        int z2 = z >> 4;
        BlockPos index = new BlockPos(x2, y2, z2);
        if (data.containsKey(index)) {
            return data.get(index).get(x1, y1, z1);
        }

        return Blocks.AIR.getDefaultState();
    }

    public static class Serializer implements IDataSerializer<BlockShipData> {
        @Override
        public void write(PacketBuffer buf, BlockShipData value) {
            value.write(buf);
        }

        @Override
        public BlockShipData read(PacketBuffer buf) {
            BlockShipData blockShipData = new BlockShipData();
            blockShipData.read(buf);
            return blockShipData;
        }

        @Override
        public BlockShipData copyValue(BlockShipData value) {
            return value;
        }

    }
}