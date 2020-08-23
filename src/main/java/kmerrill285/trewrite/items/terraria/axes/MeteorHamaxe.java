package kmerrill285.trewrite.items.terraria.axes;

import kmerrill285.trewrite.blocks.BlocksT;
import kmerrill285.trewrite.crafting.CraftingRecipe;
import kmerrill285.trewrite.crafting.Recipes;
import kmerrill285.trewrite.items.Axe;
import kmerrill285.trewrite.items.ItemStackT;
import kmerrill285.trewrite.items.ItemsT;
import kmerrill285.trewrite.util.Conversions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MeteorHamaxe extends Axe {

	public MeteorHamaxe() {
		super();
		this.axe = 100;
		this.hammer = 60;
		this.damage = 20;
		this.knockback = 7;
		this.useTime = 30;
		this.speed = 16;
		this.sellPrice = 3000;
		this.buyPrice = Conversions.sellToBuy(sellPrice);
		this.setLocation("meteor_hamaxe");
	}
	
	public void setCraftingRecipes() {
		Recipes.addRecipe(new CraftingRecipe(new ItemStackT(ItemsT.METEOR_HAMAXE), BlocksT.IRON_ANVIL, new ItemStackT(ItemsT.METEORITE_BAR, 20)));
	}

}
