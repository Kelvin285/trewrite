package kmerrill285.trewrite.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class GrassPath extends DirtBlock {

	public VoxelShape SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 15.0D, 16.0D);
	
	public GrassPath(Properties properties) {
		super(properties);
	}
	@OnlyIn(value=Dist.CLIENT)
	public VoxelShape getShape(IBlockState state, IBlockReader worldIn, BlockPos pos) {
	      return SHAPE;
	   }

	   /**
	    * @deprecated call via {@link IBlockState#isFullCube()} whenever possible. Implementing/overriding is fine.
	    */
	   public boolean isFullCube(IBlockState state) {
	      return false;
	   }
	
	public IItemProvider getItemDropped(IBlockState state, World worldIn, BlockPos pos, int fortune) {
	      return BlocksT.DIRT_BLOCK;
	}
	
	public boolean canSupport(IBlockState state) {
		return false;
	}

}
