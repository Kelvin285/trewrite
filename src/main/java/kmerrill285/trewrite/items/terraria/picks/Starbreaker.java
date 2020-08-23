package kmerrill285.trewrite.items.terraria.picks;

import kmerrill285.trewrite.blocks.BlocksT;
import kmerrill285.trewrite.crafting.CraftingRecipe;
import kmerrill285.trewrite.crafting.Recipes;
import kmerrill285.trewrite.items.ItemStackT;
import kmerrill285.trewrite.items.ItemsT;
import kmerrill285.trewrite.items.Pickaxe;
import kmerrill285.trewrite.util.Conversions;

public class Starbreaker extends Pickaxe {

	public Starbreaker() {
		super();
		this.pick = 55;
		this.damage = 8;
		this.knockback = 3;
		this.useTime = 10;
		this.speed = 40;
		this.sellPrice = 2500;
		this.buyPrice = Conversions.sellToBuy(sellPrice);
		this.setTooltip("Fell from the stars");
		this.setLocation("star_breaker");
	}
	
	public void setCraftingRecipes() {
		Recipes.addRecipe(new CraftingRecipe(new ItemStackT(ItemsT.STARBREAKER), BlocksT.IRON_ANVIL, new ItemStackT(ItemsT.METEORITE_BAR, 17)));
	}

}
