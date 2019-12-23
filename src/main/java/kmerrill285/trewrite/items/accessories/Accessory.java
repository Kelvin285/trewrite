package kmerrill285.trewrite.items.accessories;

import kmerrill285.trewrite.items.ItemT;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class Accessory extends ItemT {
    public enum AccessoryType {
    	WATCH, DEPTH_METER, BAND_OF_REGENERATION
    }
    
	public Accessory(Properties properties, String name) {
		super(properties, name);
		accessory = true;
	}
	
	public int getItemStackLimit(ItemStack stack) {
		return 1;
	}
	
	public void accessoryTick(PlayerEntity player) {
		
	}
	
	public void accessoryTickClient(PlayerEntity player) {
		
	}
}
