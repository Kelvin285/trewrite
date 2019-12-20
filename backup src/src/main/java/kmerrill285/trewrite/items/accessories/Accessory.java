package kmerrill285.trewrite.items.accessories;

import java.util.UUID;

import kmerrill285.trewrite.items.ItemT;
import net.minecraft.item.ItemStack;

public class Accessory extends ItemT {
    public enum AccessoryType {
    	WATCH, DEPTH_METER
    }
    
	public Accessory(Properties properties, String name) {
		super(properties, name);
		accessory = true;
	}
	
	public int getItemStackLimit(ItemStack stack) {
		return 1;
	}
}
