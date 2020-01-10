package kmerrill285.trewrite.items;

import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraft.util.SoundEvents;

public class Armor extends ItemT {
	public enum ArmorType {
		HEAD,
		CHEST,
		LEGS
	}
	
	public ArmorType type;
	
	
	public Armor(Properties properties, String name, ArmorType type, int defense) {
		super(properties, name);
		this.defense = defense;
		this.type = type;
	}
}
