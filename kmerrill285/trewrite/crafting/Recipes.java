package kmerrill285.trewrite.crafting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import kmerrill285.trewrite.blocks.BlockT;
import kmerrill285.trewrite.blocks.BlocksT;
import kmerrill285.trewrite.core.items.ItemStackT;
import kmerrill285.trewrite.items.ItemsT;

public class Recipes {
	public static HashMap<BlockT, List<CraftingRecipe>> recipes = new HashMap<BlockT, List<CraftingRecipe>>();
	
	public static void addRecipe(CraftingRecipe recipe) {
		if (recipes.get(recipe.block) == null)
		recipes.put(recipe.block, new ArrayList<CraftingRecipe>());
		recipes.get(recipe.block).add(recipe);
	}
	
	public static List<CraftingRecipe> getRecipesForBlock(BlockT block) {
		return recipes.get(block);
	}
	
	public static void addAllRecipes() {
		recipes.clear();
		addRecipe(new CraftingRecipe(new ItemStackT(ItemsT.WOOD_PLATFORM, 2), null, new ItemStackT(ItemsT.WOOD, 1)));
		addRecipe(new CraftingRecipe(new ItemStackT(ItemsT.WOOD, 1), null, new ItemStackT(ItemsT.WOOD_PLATFORM, 2)));
		addRecipe(new CraftingRecipe(new ItemStackT(ItemsT.WORKBENCH, 1), null, new ItemStackT(ItemsT.WOOD, 10)));
		addRecipe(new CraftingRecipe(new ItemStackT(ItemsT.TORCH, 3), null, new ItemStackT(ItemsT.WOOD, 1), new ItemStackT(ItemsT.GEL, 1)));
		
		addRecipe(new CraftingRecipe(new ItemStackT(ItemsT.CHAIR, 1), BlocksT.WORKBENCH, new ItemStackT(ItemsT.WOOD, 4)));
		addRecipe(new CraftingRecipe(new ItemStackT(ItemsT.DOOR, 1), BlocksT.WORKBENCH, new ItemStackT(ItemsT.WOOD, 6)));
		addRecipe(new CraftingRecipe(new ItemStackT(ItemsT.WOODEN_SWORD, 1), BlocksT.WORKBENCH, new ItemStackT(ItemsT.WOOD, 7)));
		addRecipe(new CraftingRecipe(new ItemStackT(ItemsT.TABLE, 1), BlocksT.WORKBENCH, new ItemStackT(ItemsT.WOOD, 8)));
		addRecipe(new CraftingRecipe(new ItemStackT(ItemsT.FURNACE, 1), BlocksT.WORKBENCH, new ItemStackT(ItemsT.WOOD, 4), new ItemStackT(ItemsT.TORCH, 3), new ItemStackT(ItemsT.STONE_BLOCK, 20)));

		addRecipe(new CraftingRecipe(new ItemStackT(ItemsT.GOLD_BAR, 1), BlocksT.FURNACE, new ItemStackT(ItemsT.GOLD_ORE, 4)));
		
		addRecipe(new CraftingRecipe(new ItemStackT(ItemsT.LESSER_HEALING_POTION, 2), BlocksT.BOTTLE, new ItemStackT(ItemsT.MUSHROOM, 1), new ItemStackT(ItemsT.GEL, 2), new ItemStackT(ItemsT.BOTTLE, 2)));
	}
}
