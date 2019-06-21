package kmerrill285.trewrite.events;

import java.util.HashMap;
import java.util.Set;

import kmerrill285.trewrite.Trewrite;
import kmerrill285.trewrite.blocks.BlockT;
import kmerrill285.trewrite.core.inventory.InventoryTerraria;
import kmerrill285.trewrite.core.items.ItemStackT;
import kmerrill285.trewrite.core.network.NetworkHandler;
import kmerrill285.trewrite.core.network.server.SPacketSpawnItemTerraria;
import kmerrill285.trewrite.entities.EntityItemT;
import kmerrill285.trewrite.items.ItemT;
import kmerrill285.trewrite.items.ItemsT;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.event.world.WorldEvent.Load;
import net.minecraftforge.event.world.WorldEvent.Save;
import net.minecraftforge.event.world.WorldEvent.Unload;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class WorldEvents {
	public static HashMap<String, InventoryTerraria> inventories = new HashMap<String, InventoryTerraria>();

	@SubscribeEvent
	public static void blockBreakEvent(HarvestDropsEvent event) {
		System.out.println("IS REMOTE: " + event.getWorld().isRemote());
		if (event.getState().getBlock() instanceof BlockT) {
			IBlockState state = event.getState();
			IWorld worldIn = event.getWorld();
			BlockPos pos = event.getPos();
			ItemT drop = ItemsT.getItemFromString(((BlockT)state.getBlock()).drop);
			if (drop != null) {
				EntityItemT item = (EntityItemT) Trewrite.ITEM_ENTITY_TYPE.spawnEntity(worldIn.getWorld(), null, null, pos, false, false);
				item.item = drop.itemName;
				item.stack = 1;
				if (!worldIn.isRemote())
				NetworkHandler.INSTANCE.sendToServer(new SPacketSpawnItemTerraria(new ItemStackT(drop, item.stack), pos));

			}
		}
		
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
			inventories.get(player).save(player, event.getWorld().getWorldInfo().getWorldName());
		}
		System.out.println("inventories saved.");
	}
	
	@SubscribeEvent
	public static void worldUnloadEvent(Unload event) {
		System.out.println("WORLD UNLOADED");
		for (String player : inventories.keySet()) {
			if (inventories.get(player).open) {
				InventoryTerraria inventory = inventories.get(player);
				for (int i = 0; i < 9; i++) {
					if (inventory.savedHotbar[i] != null) {
						inventory.player.inventory.setInventorySlotContents(i, inventory.savedHotbar[i]);
					}
				}
			}
		}
		inventories.clear();
	}
	
	@SubscribeEvent
	public static void worldLoadEvent(Load event) {
		
	}
}
