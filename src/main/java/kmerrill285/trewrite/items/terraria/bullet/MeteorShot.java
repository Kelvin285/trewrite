package kmerrill285.trewrite.items.terraria.bullet;

import kmerrill285.trewrite.blocks.BlocksT;
import kmerrill285.trewrite.crafting.CraftingRecipe;
import kmerrill285.trewrite.crafting.Recipes;
import kmerrill285.trewrite.items.Bullet;
import kmerrill285.trewrite.items.ItemStackT;
import kmerrill285.trewrite.items.ItemsT;
import net.minecraft.item.ItemGroup;

public class MeteorShot extends Bullet {

	public MeteorShot() {
		super(new Properties().group(ItemGroup.COMBAT), "meteor_shot", 0, 0, 0);
		this.damage = 9;
		this.velocity = 3f;
		this.knockback = 1;
		this.setBuySell(1);
	}
	
	public void setCraftingRecipes() {
		Recipes.addRecipe(new CraftingRecipe(new ItemStackT(ItemsT.METEOR_SHOT, 70), BlocksT.IRON_ANVIL, new ItemStackT(ItemsT.MUSKET_BALL, 70), new ItemStackT(ItemsT.METEORITE_BAR, 1)));
	}

}
