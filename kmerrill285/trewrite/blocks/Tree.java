package kmerrill285.trewrite.blocks;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReaderBase;

public class Tree extends BasicPlant {
	
	private ArrayList<String> allowed = new ArrayList<String>();
	
	public Tree(Properties properties, float hardness, float difficulty, boolean pick, boolean axe, boolean hammer,
			boolean material, String name, String drop) {
		super(properties, hardness, difficulty, pick, axe, hammer, material, name, drop);
	}
	
	public Tree addAllowed(String... block) {
		for (String b : block)
				this.allowed.add(b);
		return this;
	}
	
	public boolean isValidPosition(IBlockState state, IWorldReaderBase worldIn, BlockPos pos) {
	      BlockPos blockpos = pos.down();
	      IBlockState iblockstate = worldIn.getBlockState(blockpos);
	      Block block = iblockstate.getBlock().getDefaultState().getBlock();
	      
	      if (block instanceof BlockT) {
	    	  System.out.println(((BlockT)block).name);
	    	  return allowed.contains(((BlockT) block).name);
	      }
	      return iblockstate.isSolid();
	   }
}
