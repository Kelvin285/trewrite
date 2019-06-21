package kmerrill285.trewrite.blocks;

import kmerrill285.trewrite.items.ItemsT;
import net.minecraft.block.state.IBlockState;

public class DirtBlock extends BlockT {

	public DirtBlock(Properties properties) {
		super(properties, BlocksT.GROUND_HARDNESS, 10.0f, "dirt_block");
		this.pick = true;
		this.material = true;
	}

	public boolean canSupport(IBlockState state) {
		if (state.getBlock().getDefaultState() == BlocksT.MUSHROOM.getDefaultState() ||
				state.getBlock().getDefaultState() == BlocksT.FLOWER.getDefaultState()) {
			return true;
		}
		return false;
	}
	
}
