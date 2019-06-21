package kmerrill285.trewrite.blocks;

import kmerrill285.trewrite.blocks.CrossedBlock.Shape;
import net.minecraft.block.Block;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.init.Blocks;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReaderBase;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BasicPlant extends BasicBlock {
	
	public static VoxelShape MUSHROOM_SHAPE = Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 6.0D, 11.0D);
	public static final IntegerProperty TYPE = BlockStateProperties.AGE_0_25;
	
	public Shape shape;
	
	public BasicPlant(Properties properties, float hardness, float difficulty, boolean pick, boolean axe,
			boolean hammer, boolean material, String name, String drop) {
		super(properties, hardness, difficulty, pick, axe, hammer, material, name, drop);
		this.setDefaultState(this.stateContainer.getBaseState().with(TYPE, Integer.valueOf(0)));
	}
	
	public BasicPlant(Properties properties, float hardness, float difficulty, boolean pick, boolean axe,
			boolean hammer, boolean material, String name, int health, int mana, String drop) {
		super(properties, hardness, difficulty, pick, axe, hammer, material, name, health, mana, drop);
		this.setDefaultState(this.stateContainer.getBaseState().with(TYPE, Integer.valueOf(0)));
	}
	
	public IBlockState updatePostPlacement(IBlockState stateIn, EnumFacing facing, IBlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		return !stateIn.isValidPosition(worldIn, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}
	
	@OnlyIn(value=Dist.CLIENT)
	public VoxelShape getShape(IBlockState state, IBlockReader worldIn, BlockPos pos) {
		
		
		
		if (shape != null)
	      switch (shape) {
	      	case MUSHROOM:
	      		return MUSHROOM_SHAPE;
	      	case BLOCK:
	      		return super.getShape(state, worldIn, pos);
	      }
	      return super.getShape(state, worldIn, pos);
	}
	
	public boolean isSolid(IBlockState state) {
		return false;
	}
	
	public BasicPlant setShape(Shape shape) {
		this.shape = shape;
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

   public boolean isValidPosition(IBlockState state, IWorldReaderBase worldIn, BlockPos pos) {
      BlockPos blockpos = pos.down();
      IBlockState iblockstate = worldIn.getBlockState(blockpos);
      Block block = iblockstate.getBlock();
      
      if (block instanceof BlockT) {
    	  return ((BlockT)block).canSupport(state);
      }
      return iblockstate.isSolid();
   }
   
   /**
    * Get the OffsetType for this Block. Determines if the model is rendered slightly offset.
    */
   public Block.EnumOffsetType getOffsetType() {
      return Block.EnumOffsetType.XZ;
   }

   /**
    * Return a random long to be passed to {@link IBakedModel#getQuads}, used for random model rotations
    */
   @OnlyIn(Dist.CLIENT)
   public long getPositionRandom(IBlockState state, BlockPos pos) {
      return MathHelper.getCoordinateRandom(pos.getX(), pos.getY(), pos.getZ());
   }
   
   protected void fillStateContainer(StateContainer.Builder<Block, IBlockState> builder) {
	      builder.add(TYPE);
	   }
}
