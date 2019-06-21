package kmerrill285.trewrite.items;

import javax.annotation.Nullable;

import kmerrill285.trewrite.blocks.BlockT;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReaderBase;

public class WallOrFloorBlock extends ItemBlockT {
	public BlockT second;
	public WallOrFloorBlock(BlockT first, BlockT second, String name) {
		super(first, name);
		this.second = second;
		this.maxStack = 99;
		// TODO Auto-generated constructor stub
	}
	

	   @Nullable
	   protected IBlockState getStateForPlacement(BlockItemUseContext p_195945_1_) {
	      IBlockState iblockstate = this.second.getStateForPlacement(p_195945_1_);
	      IBlockState iblockstate1 = null;
	      IWorldReaderBase iworldreaderbase = p_195945_1_.getWorld();
	      BlockPos blockpos = p_195945_1_.getPos();

	      for(EnumFacing enumfacing : p_195945_1_.getNearestLookingDirections()) {
	         if (enumfacing != EnumFacing.UP) {
	            IBlockState iblockstate2 = enumfacing == EnumFacing.DOWN ? this.getBlock().getStateForPlacement(p_195945_1_) : iblockstate;
	            if (iblockstate2 != null && iblockstate2.isValidPosition(iworldreaderbase, blockpos)) {
	               iblockstate1 = iblockstate2;
	               break;
	            }
	         }
	      }

	      return iblockstate1 != null && iworldreaderbase.checkNoEntityCollision(iblockstate1, blockpos) ? iblockstate1 : null;
	   }

}
