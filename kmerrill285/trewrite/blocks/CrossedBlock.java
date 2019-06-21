package kmerrill285.trewrite.blocks;

import kmerrill285.trewrite.items.ItemT;
import net.minecraft.block.Block;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.init.Fluids;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CrossedBlock extends BasicBlock {
	
	public static VoxelShape MUSHROOM_SHAPE = Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 6.0D, 11.0D);
	public static VoxelShape BLOCK_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
	
	public Shape shape = Shape.BLOCK;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	
	public CrossedBlock(Properties properties, float hardness, float difficulty, boolean pick, boolean axe,
			boolean hammer, boolean material, String name, String drop) {
		super(properties, hardness, difficulty, pick, axe, hammer, material, name, drop);
		this.setDefaultState(this.getDefaultState().with(WATERLOGGED, Boolean.valueOf(false)));
	}
	
	public CrossedBlock(Properties properties, float hardness, float difficulty, boolean pick, boolean axe,
			boolean hammer, boolean material, String name, int health, int mana, String drop) {
		super(properties, hardness, difficulty, pick, axe, hammer, material, name, health, mana, drop);
		this.setDefaultState(this.getDefaultState().with(WATERLOGGED, Boolean.valueOf(false)));
	}
	
	public boolean isSolid(IBlockState state) {
		return false;
	}
	
	public CrossedBlock setShape(Shape block) {
		this.shape = block;
		return this;
	}
	
   public boolean isFullCube(IBlockState state) {
      return false;
   }
   
   public BlockFaceShape getBlockFaceShape(IBlockReader worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
      return BlockFaceShape.UNDEFINED;
   }

   public int getOpacity(IBlockState state, IBlockReader worldIn, BlockPos pos) {
      return 0;
   }
	
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT;
	}
	
   public enum Shape {
	   BLOCK, MUSHROOM
   }
   
   public Fluid pickupFluid(IWorld worldIn, BlockPos pos, IBlockState state) {
	      if (state.get(WATERLOGGED)) {
	         worldIn.setBlockState(pos, state.with(WATERLOGGED, Boolean.valueOf(false)), 3);
	         return Fluids.WATER;
	      } else {
	         return Fluids.EMPTY;
	      }
	   }
	
	 public IFluidState getFluidState(IBlockState state) {
	      return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
	   }

	   public boolean canContainFluid(IBlockReader worldIn, BlockPos pos, IBlockState state, Fluid fluidIn) {
	      return !state.get(WATERLOGGED) && fluidIn == Fluids.WATER;
	   }
	   

	   public boolean receiveFluid(IWorld worldIn, BlockPos pos, IBlockState state, IFluidState fluidStateIn) {
	      if (!state.get(WATERLOGGED) && fluidStateIn.getFluid() == Fluids.WATER) {
	         if (!worldIn.isRemote()) {
	            worldIn.setBlockState(pos, state.with(WATERLOGGED, Boolean.valueOf(true)), 3);
	            worldIn.getPendingFluidTicks().scheduleTick(pos, fluidStateIn.getFluid(), fluidStateIn.getFluid().getTickRate(worldIn));
	         }

	         return true;
	      } else {
	         return false;
	      }
	   }
	   
	   protected void fillStateContainer(StateContainer.Builder<Block, IBlockState> builder) {
		      builder.add(WATERLOGGED);
		   }
}
