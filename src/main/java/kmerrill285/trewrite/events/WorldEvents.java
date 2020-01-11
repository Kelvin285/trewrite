package kmerrill285.trewrite.events;

import java.util.HashMap;
import java.util.Set;

import kmerrill285.trewrite.core.inventory.InventoryChestTerraria;
import kmerrill285.trewrite.core.inventory.InventoryTerraria;
import kmerrill285.trewrite.core.network.NetworkHandler;
import kmerrill285.trewrite.core.network.server.SPacketRefreshDimensionRenderer;
import kmerrill285.trewrite.util.Util;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.WorldEvent.Load;
import net.minecraftforge.event.world.WorldEvent.Save;
import net.minecraftforge.event.world.WorldEvent.Unload;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;
@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class WorldEvents {
	public static HashMap<String, InventoryTerraria> inventories = new HashMap<String, InventoryTerraria>();
	public static HashMap<String, InventoryChestTerraria> chests = new HashMap<String, InventoryChestTerraria>();

	public static InventoryTerraria getOrLoadInventory(PlayerEntity player, World w) {
		if (!(w instanceof ServerWorld)) {
			return null;
		}
		InventoryTerraria a = inventories.get(player.getScoreboardName());
		if (a != null) return a;
		World world = (ServerWorld)w;
		InventoryTerraria inventory = new InventoryTerraria();
		inventory.load(player.getScoreboardName(), world.getServer().getFolderName());
		return inventory;
	}
	
	@SubscribeEvent
	@OnlyIn(value=Dist.CLIENT)
	public static void loadChunkEvent(ChunkEvent.Load event) {
//		IChunk chunk = event.getChunk();
//		if (OverlayEvents.renderWorld != null) {
//			if (!OverlayEvents.renderWorld.chunkExists(chunk.getPos().x, chunk.getPos().z) ||
//					OverlayEvents.renderWorld.getChunk(chunk.getPos().x, chunk.getPos().z) instanceof EmptyChunk)
//			{
//				NetworkHandler.INSTANCE.sendToServer(new CPacketRequestChunks(chunk.getPos().x, chunk.getPos().z));
//			}
//			
//		}
	}
	
	@SubscribeEvent
	@OnlyIn(value=Dist.CLIENT)
	public static void unloadChunkEvent(ChunkEvent.Unload event) {
//		IChunk chunk = event.getChunk();
//		if (OverlayEvents.renderWorld != null) {
//			if (OverlayEvents.renderWorld.chunkExists(chunk.getPos().x, chunk.getPos().z) &&
//					!(OverlayEvents.renderWorld.getChunk(chunk.getPos().x, chunk.getPos().z) instanceof EmptyChunk)) {
//				System.out.println("UNCHUNK THE LOAD");
//			}
////			OverlayEvents.renderWorld.chunkProvider.unloadChunk(chunk.getPos().x, chunk.getPos().z);
//		}
	}
	
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
			
			if (event.getWorld() instanceof ServerWorld) {
				ServerWorld s = (ServerWorld)event.getWorld();
				inventories.get(player).save(player, s.getServer().getFolderName());
			}
//			inventories.get(player).save(player, Minecraft.getInstance().getIntegratedServer().getFolderName());

		}
		Set<String> chestKeys = chests.keySet();
		for (String chest : chestKeys) {
			if (chests.get(chest).canSave == false) {
				System.out.println("INVENTORY LOCKED FOR CHEST AT POSITION: " + chest);
				continue;
			}
			if (event.getWorld() instanceof ServerWorld) {
				ServerWorld s = (ServerWorld)event.getWorld();
				chests.get(chest).save(chest, s.getServer().getFolderName());
			}
//			chests.get(chest).save(chest, Minecraft.getInstance().getIntegratedServer().getFolderName());
		}
		System.out.println("inventories saved.");
		
		
	}
	@OnlyIn(value=Dist.CLIENT)
	@SubscribeEvent
	public static void worldUnloadEventClient(Unload event) {
		Util.refreshDimensionRenderer = true;
	}
	@SubscribeEvent
	public static void worldUnloadEvent(Unload event) {
		if (!event.getWorld().isRemote())
		for (int i = 0; i < event.getWorld().getPlayers().size(); i++) {
			if (event.getWorld().getPlayers().get(i) != null) {
				final int I = i;
				
				NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity)event.getWorld().getPlayers().get(I)), new SPacketRefreshDimensionRenderer());
			}
		}
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
