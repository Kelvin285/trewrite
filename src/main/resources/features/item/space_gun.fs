script:space_gun {
	
	import kmerrill285.trewrite.items.modifiers
	import kmerrill285.trewrite.crafting
	import kmerrill285.trewrite.crafting
	import kmerrill285.trewrite.items
	import kmerrill285.trewrite.core.items
	import kmerrill285.trewrite.blocks
	import java.util
	
	setvar item:null
	setvar item_group:"COMBAT"
	setvar modifier_type:"MAGIC"
		
	function init {
		
	}
	
	function setupItem {
		item.damage = 19
		item.knockback = 0.75
		item.mana = 7
		item.magic = true
		item.useTime = 17
		item.velocity = 10
		item.setBuySell(4000)
		item.maxStack = 1
		item.shoot = "space_gun_projectile"
	}
	
	setvar helmet:""
	setvar chestplate:""
	setvar leggings:""
	
	setvar mana_use:7
	
	setvar crafting_input:""
	setvar crafting_block:""
	setvar crafting_output:""
	setvar crafting_output_stack:1
	
	function getAttributeModifiers equipmentSlotType {
	
	}
	
	setvar right_click_return:true
	function right_click worldIn playerIn handIn{
		if (helmet == 'meteor_helmet' && chestplate == 'meteor_chestplate' && leggings == 'meteor_leggings')
		{
			mana_use = 0
		}
		else
		mana_use = item.mana
	}
	
	function set_recipes {
		Recipes.addRecipe(new CraftingRecipe(new ItemStackT('space_gun', 1), 'iron_anvil', new ItemStackT('meteorite_bar', 30), new ItemStackT('fallen_star', 2)))
	}
	
}