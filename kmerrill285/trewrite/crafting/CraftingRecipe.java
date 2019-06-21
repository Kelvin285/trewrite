package kmerrill285.trewrite.crafting;

import java.util.ArrayList;
import java.util.List;

import kmerrill285.trewrite.blocks.BlockT;
import kmerrill285.trewrite.core.items.ItemStackT;

public class CraftingRecipe {
	public ItemStackT[] input;
	public ItemStackT output;
	public BlockT block;
	
	public CraftingRecipe(ItemStackT output, BlockT block, ItemStackT...input) {
		this.input = input;
		this.output = output;
		this.block = block;
	}
}
