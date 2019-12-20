package kmerrill285.trewrite.events;

import java.util.HashMap;
import java.util.Set;

import kmerrill285.trewrite.blocks.Tree;
import kmerrill285.trewrite.core.client.KeyRegistry;
import kmerrill285.trewrite.core.inventory.InventoryChestTerraria;
import kmerrill285.trewrite.core.inventory.InventoryTerraria;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.event.world.WorldEvent.Load;
import net.minecraftforge.event.world.WorldEvent.Save;
import net.minecraftforge.event.world.WorldEvent.Unload;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class WorldEvents {
	public static HashMap<String, InventoryTerraria> inventories = new HashMap<String, InventoryTerraria>();
	public static HashMap<String, InventoryChestTerraria> chests = new HashMap<String, InventoryChestTerraria>();

	@SubscribeEvent
	public static void blockHarvestEvent(HarvestDropsEvent event) {
		System.out.println("HARVEST");
	}
	
	
	@SubscribeEvent
	public static void worldSaveEvent(Save event) {
		
		System.out.println("Saving inventories");
		Set<String> keys = inventories.keySet();
		for (String player : keys) {
			if (inventories.get(player).canSave == false) {
				System.out.println("INVENTORY LOCKED FOR " + player);
				continue;
			}
			
			inventories.get(player).save(player, Minecraft.getInstance().getIntegratedServer().getFolderName());
		}
		Set<String> chestKeys = chests.keySet();
		for (String chest : chestKeys) {
			if (chests.get(chest).canSave == false) {
				System.out.println("INVENTORY LOCKED FOR CHEST AT POSITION: " + chest);
				continue;
			}
			chests.get(chest).save(chest, Minecraft.getInstance().getIntegratedServer().getFolderName());
		}
		System.out.println("inventories saved.");
		
		
	}
	
	@SubscribeEvent
	public static void worldUnloadEvent(Unload event) {
		if (!event.getWorld().isRemote()) {
			System.out.println("WORLD UNLOADED");
			for (String player : inventories.keySet()) {
				if (inventories.get(player).open) {
					InventoryTerraria inventory = inventories.get(player);
					for (int i = 0; i < 9; i++) {
						if (inventory.savedHotbar[i] != null) {
							inventory.player.inventory.setInventorySlotContents(i, inventory.savedHotbar[i]);
						}
					}
					if (inventory.savedOffhand != null)
					inventory.player.inventory.offHandInventory.set(0, inventory.savedOffhand);
				}
			}
			inventories.clear();
		}
	}
	
	@SubscribeEvent
	@OnlyIn(value=Dist.CLIENT)
	public static void worldLoadEvent(Load event) {
	}

}
