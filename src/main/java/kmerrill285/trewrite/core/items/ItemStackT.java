package kmerrill285.trewrite.core.items;

import java.util.ArrayList;
import java.util.List;

import kmerrill285.trewrite.items.modifiers.ItemModifier;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class ItemStackT {
	
	public Item item;
	public int size;
	public ItemStack itemForRender;
	public int modifier;
		
	public ItemStackT(Item item, ItemModifier modifier) {
		this.item = item;
		this.size = 1;
		this.itemForRender = new ItemStack(item);
		this.modifier = ItemModifier.getIdForModifier(modifier);
	}
	
	public ItemStackT(Item item) {
		this.item = item;
		this.size = 1;
		this.itemForRender = new ItemStack(item);
		this.modifier = -1;
	}
	
	public ItemStackT(Item item, int size) {
		this.item = item;
		this.size = size;
		this.itemForRender = new ItemStack(item);
		this.modifier = -1;
	}
	
	public ItemStackT(Item item, int size, ItemModifier modifier) {
		this.item = item;
		this.size = size;
		this.itemForRender = new ItemStack(item);
		this.modifier = ItemModifier.getIdForModifier(modifier);
	}
	
	public void reforge(Item item) {
		
		this.modifier = ItemModifier.getIdForModifier(ItemModifier.getRandomModifier(item));
	}

	public List<ITextComponent> getTooltip(PlayerEntity player, ITooltipFlag advanced) {
		List<ITextComponent> tooltips = new ArrayList<ITextComponent>();
		ItemModifier mod = ItemModifier.getModifier(modifier);
		if (this.itemForRender != null) {
			tooltips.addAll(itemForRender.getTooltip(player, advanced));
		}
		
		if (mod != null) {
			if (this.itemForRender != null) {
				this.itemForRender.setDisplayName(new StringTextComponent(mod.name + " " + this.itemForRender.getItem().getName().getString()));
			}
			tooltips.add(new StringTextComponent(mod.name));
			mod.addTooltips(tooltips);
		}
		
		return tooltips;
	}
	
}
