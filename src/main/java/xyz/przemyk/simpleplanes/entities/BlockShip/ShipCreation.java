package xyz.przemyk.simpleplanes.entities.BlockShip;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.przemyk.simpleplanes.setup.SimplePlanesEntities;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ShipCreation {
    public static BlockShipEntity create_ship(World world, PlayerEntity player, Hand hand, final BlockPos pos) {
//        int balloons = Integer.parseInt(ConfigUtils.config.get("balloon"));
//        int floats = Integer.parseInt(ConfigUtils.config.get("float"));
//        int wheels = Integer.parseInt(ConfigUtils.config.get("wheel"));
//        String whitelist = ConfigUtils.config.getOrDefault("whitelist", "false");
        int type = -1;
        int blocks = 0;
        int balances = 0;
        HashMap<String, Integer> used = new HashMap<>();
//        ListTag list = new ListTag();
//        ListTag addons = new ListTag();
//        CompoundTag storage = new CompoundTag();
        Set<BlockPos> to_visit = new HashSet<>();
        Set<BlockPos> visited = new HashSet<>();
        int max_posx = 3;
        int max_posy = 3;
        int max_posz = 3;

        int min_posx = -3;
        int min_posz = -3;
        int min_posy = -3;
        to_visit.add(pos);
        BlockState blockState = world.getBlockState(pos);
        if(islegal(blockState)) {
            visited.add(pos);
        }
        Scan_world scan_world = new Scan_world(world, to_visit, visited, max_posx, max_posz, min_posx, min_posz, max_posy, min_posy).invoke();

        BlockShipData data = new BlockShipData();
        for (Iterator<BlockPos> blockPosIterator = visited.iterator(); blockPosIterator.hasNext(); ) {
            BlockPos corrent_pos = blockPosIterator.next();
            BlockPos diff = corrent_pos.subtract(pos);
//            BlockPos diff = corrent_pos;
            int i = diff.getX();
            int j = diff.getY();
            int k = diff.getZ();

            BlockState blockState1 = world.getBlockState(corrent_pos);
            Block block = blockState1.getBlock();
            System.out.println(block.getRegistryName());
//            addIfCan(used, block.getTranslationKey(), 1);
            data.swap(i, j, k, blockState1);
        }
        for (Iterator<BlockPos> blockPosIterator = visited.iterator(); blockPosIterator.hasNext(); ) {
            BlockPos corrent_pos = blockPosIterator.next();
            world.setBlockState(corrent_pos, Blocks.AIR.getDefaultState());


//            BlockState blockState = blockState1;
//            list.add(StringTag.of(
//                Block.getRawIdFromState(blockState) + " " + i + " " + j + " " + k));
//            blocks++;

//            if (block == Blocks.BLAST_FURNACE) {
//                addons.add(StringTag.of("engine"));
//            }
//            if (block == Blocks.REDSTONE_LAMP) {
//                addons.add(StringTag.of("altitude"));
//            }
//
//            if (world.getBlockEntity(corrent_pos) != null) {
//                CompoundTag data = world.getBlockEntity(corrent_pos).toTag(new CompoundTag());
//                storage.put(i + " " + j + " " + k, data);
//            }
//
//
//            if (block == PlatosTransporters.FLOAT_BLOCK && (type == 0 || type == -1)) {
//                type = 0; //watership
//                balances += floats;
//
//            }
//            if (block == PlatosTransporters.BALLOON_BLOCK && (type == 1 || type == -1)) {
//                type = 1; //airship
//                balances += balloons;
//
//            }
//            if (block == PlatosTransporters.WHEEL_BLOCK && (type == 2 || type == -1)) {
//                type = 2; //carriage
//                balances += wheels;
//            }
        }
        System.out.println("blocks: " + blocks);
        System.out.println("balances: " + balances);
//        if (type == -1) {
//            player.sendMessage(new LiteralText("No wheel/float/balloon found"), false);
//            return ActionResult.FAIL;
//        }
//        if (balances < blocks) {
//            player.sendMessage(new LiteralText("Cannot assemble, not enough floats/balloons/wheels"), false);
//            if (type == 0) {
//                player.sendMessage(new LiteralText("Requires " + blocks / floats + " floats, you have " + balances / floats), false);
//            }
//            if (type == 1) {
//                player.sendMessage(new LiteralText("Requires " + blocks / balloons + " balloons, you have " + balances / balloons), false);
//            }
//            if (type == 2) {
//                player.sendMessage(new LiteralText("Requires " + blocks / wheels + " wheels, you have" + balances / wheels), false);
//            }
//            return ActionResult.FAIL;
//        }
//        list.forEach(block ->
//        {
//            String[] vv = block.asString().split(" ");
//            if (world.getBlockEntity(pos.add(Integer.parseInt(vv[1]), Integer.parseInt(vv[2]), Integer.parseInt(vv[3]))) != null) {
//                Clearable.clear(world.getBlockEntity(pos.add(Integer.parseInt(vv[1]), Integer.parseInt(vv[2]), Integer.parseInt(vv[3]))));
//            }
//            world.setBlockState(pos.add(Integer.parseInt(vv[1]), Integer.parseInt(vv[2]), Integer.parseInt(vv[3])), Blocks.AIR.getDefaultState());
//        });

        BlockShipEntity entity = new BlockShipEntity(SimplePlanesEntities.BLOCK_SHIP.get(), world);
        int offset = 1;
//        if (player.getStackInHand(hand).getItem() == PlatosTransporters.LIFT_JACK_ITEM) {
//            if (player.getStackInHand(hand).hasTag()) {
//                offset = player.getStackInHand(hand).getTag().getInt("off");
//            }
//        }
        entity.setData(data);
//        entity.setBoundingBox(new AxisAlignedBB(min_posx, min_posy, min_posz, max_posx, min_posy, min_posz));
//        entity.setModel(list, getDirection(state), offset, type, storage, addons);
//        entity.teleport(player.getX(), player.getY(), player.getZ());
//        world.spawnEntity(entity);
//        player.startRiding(entity, true);
        return entity;
    }


    private static int getDirection(BlockState state) {
        if (state.get(BlockStateProperties.HORIZONTAL_FACING) == Direction.EAST) {
            return 90;
        }
        if (state.get(BlockStateProperties.HORIZONTAL_FACING) == Direction.SOUTH) {
            return 180;
        }
        if (state.get(BlockStateProperties.HORIZONTAL_FACING) == Direction.WEST) {
            return 270;
        }
        return 0;
    }

    private static HashMap<String, Integer> addIfCan(HashMap<String, Integer> input, String key, int mod) {
        if (input.containsKey(key)) {
            input.put(key, input.get(key) + mod);
        } else {
            input.put(key, mod);
        }
        return input;
    }

    public static boolean islegal(BlockState state) {
        return !state.isAir() &&
            !state.getBlock().getTranslationKey().contains("ore")
            && state.getFluidState().isEmpty()
            && state.isSolid();
//        ((whitelist.equals("true") && PlatosTransporters.BOAT_MATERIAL.contains(world.getBlockState(current_pos).getBlock()))
//            || (whitelist.equals("false") && !PlatosTransporters.BOAT_MATERIAL_BLACKLIST.contains(world.getBlockState(current_pos).getBlock())));
    }

    private static class Scan_world {
        protected World world;
        protected Set<BlockPos> to_visit;
        protected Set<BlockPos> visited;
        protected int max_posx;
        protected int max_posz;
        protected int min_posx;
        protected int min_posz;
        protected int max_posy;
        protected int min_posy;

        public Scan_world(World world, Set<BlockPos> to_visit, Set<BlockPos> visited, int max_posx, int max_posz, int min_posx, int min_posz, int max_posy, int min_posy) {
            this.world = world;
            this.to_visit = to_visit;
            this.visited = visited;
            this.max_posx = max_posx;
            this.max_posz = max_posz;
            this.min_posx = min_posx;
            this.min_posz = min_posz;
            this.max_posy = max_posy;
            this.min_posy = min_posy;
        }

        public int getMax_posx() {
            return max_posx;
        }

        public int getMin_posx() {
            return min_posx;
        }

        public int getMin_posz() {
            return min_posz;
        }

        public int getMin_posy() {
            return min_posy;
        }

        public Scan_world invoke() {
//            return this;
            while (!to_visit.isEmpty()) {
                BlockPos look_around_pos = to_visit.iterator().next();
                to_visit.remove(look_around_pos);

                for (int x = -1; x < 2; x++) {
                    for (int y = -1; y < 2; y++) {
                        for (int z = -1; z < 2; z++) {
                            BlockPos current_pos = look_around_pos.add(x, y, z);

                            BlockState blockState = world.getBlockState(current_pos);
                            if (islegal(blockState)) {
                                if (!visited.add(current_pos)) {
                                    //skip center
                                    continue;
                                }

                                if (current_pos.getX() > max_posx) {
                                    max_posx = current_pos.getX();
                                }
                                if (current_pos.getX() < min_posx) {
                                    min_posx = current_pos.getX();
                                }
                                if (current_pos.getY() > max_posy) {
                                    max_posy = current_pos.getY();
                                }
                                if (current_pos.getY() < min_posy) {
                                    min_posy = current_pos.getY();
                                }
                                if (current_pos.getZ() > max_posz) {
                                    max_posz = current_pos.getZ();
                                }
                                if (current_pos.getZ() < min_posz) {
                                    min_posz = current_pos.getZ();
                                }
                                to_visit.add(current_pos);
                            }
                        }
                    }
                }
            }
            return this;
        }
    }

    private static class s2 extends Scan_world {
        public s2(World world, Set<BlockPos> to_visit, Set<BlockPos> visited, int max_posx, int max_posz, int min_posx, int min_posz, int max_posy, int min_posy) {
            super(world, to_visit, visited, max_posx, max_posz, min_posx, min_posz, max_posy, min_posy);
        }

        @Override
        public Scan_world invoke() {
            BlockPos pos = visited.iterator().next();
            max_posx = 3;
            max_posz = 3;
            max_posy = 3;
            for (int x = 0; x < 3; x++) {
                for (int y = 0; y < 3; y++) {
                    for (int z = 0; z < 3; z++) {
                        visited.add(new BlockPos(x, y, z).add(pos));
                    }
                }
            }

            return this;
        }
    }
}
