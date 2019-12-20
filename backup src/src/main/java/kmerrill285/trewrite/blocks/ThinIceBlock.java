package kmerrill285.trewrite.blocks;

import java.util.Random;

import kmerrill285.trewrite.core.items.ItemStackT;
import kmerrill285.trewrite.entities.EntityItemT;
import kmerrill285.trewrite.items.ItemT;
import kmerrill285.trewrite.items.ItemsT;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.BubbleColumnBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.pathfinding.PathType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class ThinIceBlock extends BlockT {

	
	public ThinIceBlock(Properties properties) {
		super(properties, BlocksT.GROUND_HARDNESS, 10.0f, "thin_ice");
		this.pick = true;
	}

	public boolean canSupport(BlockState state) {
		return false;
	}

	public BlockState breakBlock(World worldIn, BlockPos pos, BlockState state) {
		 return Blocks.AIR.getDefaultState();
	 }
	
	public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {

	}
	
   public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
	   if (!worldIn.isRemote()) {
		   if (entityIn.getMotion().y < 0f) {
		    	  worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
		      }
	   }
     
   }
   
   public void tick(BlockState state, World worldIn, BlockPos pos, Random random) {
	      BubbleColumnBlock.placeBubbleColumn(worldIn, pos.up(), false);
	   }

	   public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
	      worldIn.getPendingBlockTicks().scheduleTick(pos, this, this.tickRate(worldIn));
	   }

	   public boolean isNormalCube(BlockState state, IBlockReader worldIn, BlockPos pos) {
	      return true;
	   }

	   /**
	    * How many world ticks before ticking
	    */
	   public int tickRate(IWorldReader worldIn) {
	      return 20;
	   }

	   public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
	      worldIn.getPendingBlockTicks().scheduleTick(pos, this, this.tickRate(worldIn));
	   }

	   public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
	      return false;
	   }

	   public boolean canEntitySpawn(BlockState state, IBlockReader worldIn, BlockPos pos, EntityType<?> type) {
	      return true;
	   }
	
}
