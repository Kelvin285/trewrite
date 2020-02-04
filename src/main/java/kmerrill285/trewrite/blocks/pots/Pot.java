package kmerrill285.trewrite.blocks.pots;

import kmerrill285.trewrite.blocks.CrossedBlock;
import kmerrill285.trewrite.core.items.ItemStackT;
import kmerrill285.trewrite.entities.EntityItemT;
import kmerrill285.trewrite.items.ItemsT;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Pot extends CrossedBlock {

	public Pot(Properties properties, String name) {
		super(properties, 0, 0, true, true, true, false, name, "none");
	}
	
	public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		
	}

}
