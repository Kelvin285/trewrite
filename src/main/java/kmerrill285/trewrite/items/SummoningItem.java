package kmerrill285.trewrite.items;

import net.minecraft.item.ItemGroup;

public class SummoningItem extends ItemT {
	public SummoningItem(String name) {
		super(new Properties().group(ItemGroup.COMBAT), name);
		this.setMaxStack(1);
	}
}
