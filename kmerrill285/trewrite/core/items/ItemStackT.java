package kmerrill285.trewrite.core.items;

import kmerrill285.trewrite.items.ItemT;
import net.minecraft.item.ItemStack;

public class ItemStackT {
	
	public ItemT item;
	public int size;
	public ItemStack itemForRender;
	
	public ItemStackT(ItemT item) {
		this.item = item;
		this.size = 1;
		this.itemForRender = new ItemStack(item);
	}
	
	public ItemStackT(ItemT item, int size) {
		this.item = item;
		this.size = size;
		this.itemForRender = new ItemStack(item);
	}
	
}
