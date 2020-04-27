package kmerrill285.trewrite.items;

import com.google.common.collect.Multimap;

import kmerrill285.featurescript.scripts.objects.Script;
import kmerrill285.trewrite.blocks.BlocksT;
import kmerrill285.trewrite.core.inventory.InventorySlot;
import kmerrill285.trewrite.core.inventory.InventoryTerraria;
import kmerrill285.trewrite.core.inventory.container.ContainerTerrariaInventory;
import kmerrill285.trewrite.core.items.ItemStackT;
import kmerrill285.trewrite.crafting.CraftingRecipe;
import kmerrill285.trewrite.crafting.Recipes;
import kmerrill285.trewrite.events.ScoreboardEvents;
import kmerrill285.trewrite.events.WorldEvents;
import kmerrill285.trewrite.items.modifiers.EnumModifierType;
import kmerrill285.trewrite.items.modifiers.ItemModifier;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FeatureScriptItem extends ItemT {
	private Script script;
	public FeatureScriptItem(String name, Script script) {
		super(new Properties().group(getItemGroup(script)), script.name);
		this.script = script;
		script.setVariable("item", this);
		script.executeFunction("setupItem");
		
		if (script.getVariable("modifier_type") != null)
		this.MODIFIER_TYPE = EnumModifierType.valueOf((String)script.getVariable("modifier_type"));
	}
	
	public void onLeftClick(Entity entity, BlockPos pos, PlayerEntity player, World worldIn, Hand handIn) {
		player.getCooldownTracker().setCooldown(this, (int) ((this.useTime - this.useTime * speed) * (30.0 / 60.0)));
		script.executeFunction("left_click");
	}
	
	public boolean onAttack(Entity target, BlockPos pos, PlayerEntity player, World worldIn, Hand handIn, double attackMod) {
		player.getCooldownTracker().setCooldown(this, (int) ((this.useTime - this.useTime * speed) * (30.0 / 60.0)));
		script.executeFunction("attack");
		return super.onAttack(target, pos, player, worldIn, handIn, attackMod);
	}
	
	public void onUse(Entity entity, BlockPos pos, PlayerEntity player, World worldIn, Hand handIn) {
		player.getCooldownTracker().setCooldown(this, (int) ((this.useTime - this.useTime * speed) * (30.0 / 60.0)));
		super.onUse(entity, pos, player, worldIn, handIn);
		script.executeFunction("use");
	}
	
	 public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
			
	      ItemStack itemstack = playerIn.getHeldItem(handIn);
	      
	      InventoryTerraria inventory = null;
	      if (!worldIn.isRemote) {
	    	  inventory = WorldEvents.getOrLoadInventory(playerIn);
	      } else 
	      {
	    	  inventory = ContainerTerrariaInventory.inventory;
	      }
	      
	      int ii = 0;
	      boolean shoot = false;
	      
	      InventorySlot bowSlot = inventory.hotbar[inventory.hotbarSelected];
	      float velocity = this.velocity;
	      float kb = 1.0f;
	      float speed = 1.0f;
	      float dmg = 1.0f;
	      float crit = 1.0f;
	      
	      if (bowSlot == null) {
	    	  return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
	      }
	      
	      if (bowSlot.stack == null) {
	    	  return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
	      }
	      if (!(bowSlot.stack.item == this)) {
	    	  return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
	      }
	      if (ItemModifier.getModifier(bowSlot.stack.modifier) == null) {
	    	  bowSlot.stack.reforge(bowSlot.stack.item);
	    	  return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
	      }
	      
		playerIn.getCooldownTracker().setCooldown(this, (int) ((this.useTime - this.useTime * speed) * (30.0 / 60.0)));
		
		String helmet = "";
		String chestplate = "";
		String leggings = "";
		
		script.setVariable("helmet", helmet);
		script.setVariable("chestplate", chestplate);
		script.setVariable("leggings", leggings);
		
		script.executeFunction("right_click");
		
		int mana_use = this.mana;
		if (script.getVariable("mana_use") != null) {
			mana_use = (Integer)script.getVariable("mana_use");
		}
		int mana = ScoreboardEvents.getScore(playerIn.getWorldScoreboard(), playerIn, ScoreboardEvents.MANA).getScorePoints();
		if (mana - mana_use > 0) {
			mana -= mana_use;
			ScoreboardEvents.getScore(playerIn.getWorldScoreboard(), playerIn, ScoreboardEvents.MANA).setScorePoints(mana);
		} else {
			mana = 0;
			ScoreboardEvents.getScore(playerIn.getWorldScoreboard(), playerIn, ScoreboardEvents.MANA).setScorePoints(mana);
			return super.onItemRightClick(worldIn, playerIn, handIn);
		}
		
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	
	public Multimap<String, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot) {
		return super.getAttributeModifiers(equipmentSlot);
	}
	public static ItemGroup getItemGroup(Script script) {
		if (script.getVariable("item_group") != null) {
			String group = (String)script.getVariable("item_group");
			if (group.equals("COMBAT")) return ItemGroup.COMBAT;
			if (group.equals("BUILDING_BLOCKS")) return ItemGroup.BUILDING_BLOCKS;
			if (group.equals("DECORATIONS")) return ItemGroup.DECORATIONS;
			if (group.equals("FOOD")) return ItemGroup.FOOD;
			if (group.equals("HOTBAR")) return ItemGroup.HOTBAR;
			if (group.equals("INVENTORY")) return ItemGroup.INVENTORY;
			if (group.equals("MATERIALS")) return ItemGroup.MATERIALS;
			if (group.equals("MISC")) return ItemGroup.MISC;
			if (group.equals("REDSTONE")) return ItemGroup.REDSTONE;
			if (group.equals("SEARCH")) return ItemGroup.SEARCH;
			if (group.equals("TOOLS")) return ItemGroup.TOOLS;
			if (group.equals("TRANSPORTATION")) return ItemGroup.TRANSPORTATION;
		}
		return ItemGroup.MISC;
	}
	
	public void setCraftingRecipes() {
		script.executeFunction("set_recipes");
//		String input = (String)script.getVariable("crafting_input");
//		String crafting_block = (String) script.getVariable("crafting_block");
//		
//		String output = (String) script.getVariable("crafting_output");
//		Integer output_stack = (Integer) script.getVariable("crafting_output_stack");
//		
//		String[] craftData = input.replace(" ", "").split(",");
//		ItemStackT[] stacks = new ItemStackT[craftData.length];
//		for (int i = 0; i < craftData.length; i++) {
//			if (craftData[i].split(":").length > 0) {
//				stacks[i] = new ItemStackT(ItemsT.getItemFromString(craftData[i].split(":")[0]), Integer.parseInt(craftData[i].split(":")[1]));
//			} else {
//				stacks[i] = new ItemStackT(ItemsT.getItemFromString(craftData[i]), 1);
//			}
//		}
//		System.out.println("ADD RECIPES");
//		Recipes.addRecipe(new CraftingRecipe(new ItemStackT(ItemsT.getItemFromString(output), output_stack), BlocksT.BLOCKS.get(crafting_block), stacks));
	}
	
}
