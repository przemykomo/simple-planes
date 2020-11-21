//package xyz.przemyk.simpleplanes.integration;
//
//import net.minecraft.block.Block;
//import net.minecraft.block.BlockRenderType;
//import net.minecraft.block.BlockState;
//import net.minecraft.item.BlockItemUseContext;
//import net.minecraft.pathfinding.PathType;
//import net.minecraft.state.DirectionProperty;
//import net.minecraft.state.IntegerProperty;
//import net.minecraft.state.StateContainer;
//import net.minecraft.state.properties.BlockStateProperties;
//import net.minecraft.util.Direction;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.util.math.shapes.IBooleanFunction;
//import net.minecraft.util.math.shapes.ISelectionContext;
//import net.minecraft.util.math.shapes.VoxelShape;
//import net.minecraft.util.math.shapes.VoxelShapes;
//import net.minecraft.world.IBlockReader;
//
//public class ChargingBlockBase extends Block {
//    public static final IntegerProperty CHARGES = BlockStateProperties.CHARGES;
//    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
//
//    public ChargingBlockBase(Properties properties) {
//        super(properties);
//        this.setDefaultState(this.stateContainer.getBaseState().with(CHARGES, 0));
//        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.SOUTH));
//    }
//
//    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
//        builder.add(CHARGES);
//        builder.add(FACING);
//    }
//
//    public boolean isTransparent(BlockState state) {
//        return true;
//    }
//
//    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
//        switch ((Direction) state.get(FACING)) {
//            case NORTH:
//                return NORTH_SHAPE;
//            case SOUTH:
//                return SOUTH_SHAPE;
//            case EAST:
//                return EAST_SHAPE;
//            case WEST:
//                return WEST_SHAPE;
//            default:
//                return EAST_SHAPE;
//        }
//    }
//
//    public BlockState getStateForPlacement(BlockItemUseContext context) {
//
//        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
//    }
//
//    public BlockRenderType getRenderType(BlockState state) {
//        return BlockRenderType.MODEL;
//    }
//
//    public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
//        return false;
//    }
//
////    public VoxelShape getRenderShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
////        return SHAPE;
////    }
//
//    private static VoxelShape EAST_SHAPE;
//    private static VoxelShape NORTH_SHAPE;
//    private static VoxelShape WEST_SHAPE;
//    private static VoxelShape SOUTH_SHAPE;
//
//    static {
//        NORTH_SHAPE = VoxelShapes.create(0.125, 0.19375, 0.3125, 0.875, 1.38125, 0.9375);
//        WEST_SHAPE = VoxelShapes.create(0.3125, 0.19375, 0.125, 0.9375, 1.38125, 0.875);
//        SOUTH_SHAPE = VoxelShapes.create(0.125, 0.19375, 0.0625, 0.875, 1.38125, 0.6875);
//        EAST_SHAPE = VoxelShapes.create(0.0625, 0.19375, 0.125, 0.6875, 1.38125, 0.875);
//        VoxelShape bottom = VoxelShapes.create(0.0, 0.0, 0.0, 1.0, 0.1875, 1.0);
//        EAST_SHAPE = VoxelShapes.combineAndSimplify(EAST_SHAPE, bottom, IBooleanFunction.OR);
//        NORTH_SHAPE = VoxelShapes.combineAndSimplify(NORTH_SHAPE, bottom, IBooleanFunction.OR);
//        WEST_SHAPE = VoxelShapes.combineAndSimplify(WEST_SHAPE, bottom, IBooleanFunction.OR);
//        SOUTH_SHAPE = VoxelShapes.combineAndSimplify(SOUTH_SHAPE, bottom, IBooleanFunction.OR);
//    }
//}
