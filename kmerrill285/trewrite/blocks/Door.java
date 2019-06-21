package kmerrill285.trewrite.blocks;

import javax.annotation.Nullable;

import kmerrill285.trewrite.Trewrite;
import kmerrill285.trewrite.entities.EntityItemT;
import kmerrill285.trewrite.items.ItemT;
import kmerrill285.trewrite.items.ItemsT;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoorHingeSide;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class Door extends BlockT {

	public static final DirectionProperty FACING = BlockHorizontal.HORIZONTAL_FACING;
   public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
   public static final EnumProperty<DoorHingeSide> HINGE = BlockStateProperties.DOOR_HINGE;
   public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
   public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
   public static final VoxelShape SOUTH_AABB = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 3.0D);
   public static final VoxelShape NORTH_AABB = Block.makeCuboidShape(0.0D, 0.0D, 13.0D, 16.0D, 16.0D, 16.0D);
   public static final VoxelShape WEST_AABB = Block.makeCuboidShape(13.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
   public static final VoxelShape EAST_AABB = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 3.0D, 16.0D, 16.0D);

	
	public Door(float hardness, float difficulty, String name) {
		super(Properties.create(Material.GROUND).sound(SoundType.WOOD), hardness, difficulty, name);
		this.setDefaultState(this.stateContainer.getBaseState().with(FACING, EnumFacing.NORTH).with(OPEN, Boolean.valueOf(false)).with(HINGE, DoorHingeSide.LEFT).with(POWERED, Boolean.valueOf(false)).with(HALF, DoubleBlockHalf.LOWER));
		this.setLocation(name);
		this.pick = true;
	}
	

	   public VoxelShape getShape(IBlockState state, IBlockReader worldIn, BlockPos pos) {
	      EnumFacing enumfacing = state.get(FACING);
	      boolean flag = !state.get(OPEN);
	      boolean flag1 = state.get(HINGE) == DoorHingeSide.RIGHT;
	      switch(enumfacing) {
	      case EAST:
	      default:
	         return flag ? EAST_AABB : (flag1 ? NORTH_AABB : SOUTH_AABB);
	      case SOUTH:
	         return flag ? SOUTH_AABB : (flag1 ? EAST_AABB : WEST_AABB);
	      case WEST:
	         return flag ? WEST_AABB : (flag1 ? SOUTH_AABB : NORTH_AABB);
	      case NORTH:
	         return flag ? NORTH_AABB : (flag1 ? WEST_AABB : EAST_AABB);
	      }
	   }
	   
	   public IBlockState updatePostPlacement(IBlockState stateIn, EnumFacing facing, IBlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
	      DoubleBlockHalf doubleblockhalf = stateIn.get(HALF);
	      if (facing.getAxis() == EnumFacing.Axis.Y && doubleblockhalf == DoubleBlockHalf.LOWER == (facing == EnumFacing.UP)) {
	         return facingState.getBlock() == this && facingState.get(HALF) != doubleblockhalf ? stateIn.with(FACING, facingState.get(FACING)).with(OPEN, facingState.get(OPEN)).with(HINGE, facingState.get(HINGE)).with(POWERED, facingState.get(POWERED)) : Blocks.AIR.getDefaultState();
	      } else {
	         return doubleblockhalf == DoubleBlockHalf.LOWER && facing == EnumFacing.DOWN && !stateIn.isValidPosition(worldIn, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	      }
	   }


		public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
			if (state.get(HALF) == DoubleBlockHalf.UPPER) return;
			ItemT drop = ItemsT.getItemFromString(((BlockT)state.getBlock()).drop);
			if (drop != null) {
				EntityItemT item = (EntityItemT) Trewrite.ITEM_ENTITY_TYPE.spawnEntity(worldIn.getWorld(), null, null, pos, false, false);
				item.item = drop.itemName;
				item.stack = 1;
			}
		}
		
		public boolean allowsMovement(IBlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
		      switch(type) {
		      case LAND:
		         return state.get(OPEN);
		      case WATER:
		         return false;
		      case AIR:
		         return state.get(OPEN);
		      default:
		         return false;
		      }
		   }
		

		   public boolean isFullCube(IBlockState state) {
		      return false;
		   }

		   private int getCloseSound() {
		      return 1012;
		   }

		   private int getOpenSound() {
		      return 1006;
		   }

		   @Nullable
		   public IBlockState getStateForPlacement(BlockItemUseContext context) {
		      BlockPos blockpos = context.getPos();
		      if (blockpos.getY() < 255 && context.getWorld().getBlockState(blockpos.up()).isReplaceable(context)) {
		         World world = context.getWorld();
		         boolean flag = world.isBlockPowered(blockpos) || world.isBlockPowered(blockpos.up());
		         return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing()).with(HINGE, this.getHingeSide(context)).with(POWERED, Boolean.valueOf(flag)).with(OPEN, Boolean.valueOf(flag)).with(HALF, DoubleBlockHalf.LOWER);
		      } else {
		         return null;
		      }
		   }
		   

		   public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		      worldIn.setBlockState(pos.up(), state.with(HALF, DoubleBlockHalf.UPPER), 3);
		   }

		   private DoorHingeSide getHingeSide(BlockItemUseContext p_208073_1_) {
		      IBlockReader iblockreader = p_208073_1_.getWorld();
		      BlockPos blockpos = p_208073_1_.getPos();
		      EnumFacing enumfacing = p_208073_1_.getPlacementHorizontalFacing();
		      BlockPos blockpos1 = blockpos.up();
		      EnumFacing enumfacing1 = enumfacing.rotateYCCW();
		      IBlockState iblockstate = iblockreader.getBlockState(blockpos.offset(enumfacing1));
		      IBlockState iblockstate1 = iblockreader.getBlockState(blockpos1.offset(enumfacing1));
		      EnumFacing enumfacing2 = enumfacing.rotateY();
		      IBlockState iblockstate2 = iblockreader.getBlockState(blockpos.offset(enumfacing2));
		      IBlockState iblockstate3 = iblockreader.getBlockState(blockpos1.offset(enumfacing2));
		      int i = (iblockstate.isBlockNormalCube() ? -1 : 0) + (iblockstate1.isBlockNormalCube() ? -1 : 0) + (iblockstate2.isBlockNormalCube() ? 1 : 0) + (iblockstate3.isBlockNormalCube() ? 1 : 0);
		      boolean flag = iblockstate.getBlock() == this && iblockstate.get(HALF) == DoubleBlockHalf.LOWER;
		      boolean flag1 = iblockstate2.getBlock() == this && iblockstate2.get(HALF) == DoubleBlockHalf.LOWER;
		      if ((!flag || flag1) && i <= 0) {
		         if ((!flag1 || flag) && i >= 0) {
		            int j = enumfacing.getXOffset();
		            int k = enumfacing.getZOffset();
		            float f = p_208073_1_.getHitX();
		            float f1 = p_208073_1_.getHitZ();
		            return (j >= 0 || !(f1 < 0.5F)) && (j <= 0 || !(f1 > 0.5F)) && (k >= 0 || !(f > 0.5F)) && (k <= 0 || !(f < 0.5F)) ? DoorHingeSide.LEFT : DoorHingeSide.RIGHT;
		         } else {
		            return DoorHingeSide.LEFT;
		         }
		      } else {
		         return DoorHingeSide.RIGHT;
		      }
		   }

		   public boolean onBlockActivated(IBlockState state, World worldIn, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		      
		         state = state.cycle(OPEN);
		         worldIn.setBlockState(pos, state, 10);
		         worldIn.playEvent(player, state.get(OPEN) ? this.getOpenSound() : this.getCloseSound(), pos, 0);
		         return true;
		      
		   }

		   public void toggleDoor(World worldIn, BlockPos pos, boolean open) {
		      IBlockState iblockstate = worldIn.getBlockState(pos);
		      if (iblockstate.getBlock() == this && iblockstate.get(OPEN) != open) {
		         worldIn.setBlockState(pos, iblockstate.with(OPEN, Boolean.valueOf(open)), 10);
		         this.playSound(worldIn, pos, open);
		      }
		   }

		   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		      boolean flag = worldIn.isBlockPowered(pos) || worldIn.isBlockPowered(pos.offset(state.get(HALF) == DoubleBlockHalf.LOWER ? EnumFacing.UP : EnumFacing.DOWN));
		      if (blockIn != this && flag != state.get(POWERED)) {
		         if (flag != state.get(OPEN)) {
		            this.playSound(worldIn, pos, flag);
		         }

		         worldIn.setBlockState(pos, state.with(POWERED, Boolean.valueOf(flag)).with(OPEN, Boolean.valueOf(flag)), 2);
		      }

		   }

		   public boolean isValidPosition(IBlockState state, IWorldReaderBase worldIn, BlockPos pos) {
		      IBlockState iblockstate = worldIn.getBlockState(pos.down());
		      if (state.get(HALF) == DoubleBlockHalf.LOWER) {
		         return iblockstate.isTopSolid();
		      } else {
		         return iblockstate.getBlock() == this;
		      }
		   }

		   private void playSound(World p_196426_1_, BlockPos p_196426_2_, boolean p_196426_3_) {
		      p_196426_1_.playEvent((EntityPlayer)null, p_196426_3_ ? this.getOpenSound() : this.getCloseSound(), p_196426_2_, 0);
		   }

		   public IItemProvider getItemDropped(IBlockState state, World worldIn, BlockPos pos, int fortune) {
		      return (IItemProvider)(state.get(HALF) == DoubleBlockHalf.UPPER ? Items.AIR : super.getItemDropped(state, worldIn, pos, fortune));
		   }


		   public EnumPushReaction getPushReaction(IBlockState state) {
		      return EnumPushReaction.IGNORE;
		   }

		   public BlockRenderLayer getRenderLayer() {
		      return BlockRenderLayer.CUTOUT;
		   }


		   public IBlockState rotate(IBlockState state, Rotation rot) {
		      return state.with(FACING, rot.rotate(state.get(FACING)));
		   }


		   public IBlockState mirror(IBlockState state, Mirror mirrorIn) {
		      return mirrorIn == Mirror.NONE ? state : state.rotate(mirrorIn.toRotation(state.get(FACING))).cycle(HINGE);
		   }


		   @OnlyIn(Dist.CLIENT)
		   public long getPositionRandom(IBlockState state, BlockPos pos) {
		      return MathHelper.getCoordinateRandom(pos.getX(), pos.down(state.get(HALF) == DoubleBlockHalf.LOWER ? 0 : 1).getY(), pos.getZ());
		   }

		   protected void fillStateContainer(StateContainer.Builder<Block, IBlockState> builder) {
		      builder.add(HALF, FACING, OPEN, HINGE, POWERED);
		   }


		   public BlockFaceShape getBlockFaceShape(IBlockReader worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		      return BlockFaceShape.UNDEFINED;
		   }
}
