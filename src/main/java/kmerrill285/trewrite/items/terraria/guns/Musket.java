package kmerrill285.trewrite.items.terraria.guns;

import kmerrill285.trewrite.items.Gun;
import net.minecraft.item.Item.Properties;
import net.minecraft.item.ItemGroup;

public class Musket extends Gun {
	public Musket() {
		super(new Properties().group(ItemGroup.COMBAT).maxStackSize(1), "musket", 31);
		this.knockback = 5.25f;
		this.useTime = 36;
		this.velocity = 9;
		this.setBuySell(20000);
		this.critChance = 11;
	}
}
