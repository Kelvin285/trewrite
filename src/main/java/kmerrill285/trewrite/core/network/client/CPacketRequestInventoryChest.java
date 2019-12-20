package kmerrill285.trewrite.core.network.client;

import java.util.Set;
import java.util.function.Supplier;

import kmerrill285.trewrite.core.inventory.InventoryChestTerraria;
import kmerrill285.trewrite.core.inventory.InventoryTerraria;
import kmerrill285.trewrite.core.network.NetworkHandler;
import kmerrill285.trewrite.core.network.server.SPacketSyncInventoryChest;
import kmerrill285.trewrite.events.WorldEvents;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;

public class CPacketRequestInventoryChest {
		public String playername;
		public String worldfile;
		public String position;
	 	public CPacketRequestInventoryChest(String playername, String worldFile, String position) {
	 		this.playername = playername;
	 		this.worldfile = worldFile;
	 		this.position = position;
	 	}
	 	
	 	public void encode(PacketBuffer buf) {
//	        buf.writeFloat(this.oldMouseX);
//	        buf.writeFloat(this.oldMouseY);
	 		buf.writeString(this.playername);
	 		buf.writeString(this.worldfile);
	 		buf.writeString(this.position);
	    }
	 	
	 	public CPacketRequestInventoryChest(PacketBuffer buf) {
	 		//read values in the order they were sent
	        this(buf.readString(100).trim(), buf.readString(100).trim(), buf.readString(100).trim());
	 		System.out.println("DECODING INVENTORY REQUEST");
	    }
	 	
	 	public void handle(Supplier<NetworkEvent.Context> ctx) {

	 		System.out.println("HANDLING INVENTORY REQUEST");
	 		
	        ctx.get().enqueueWork(() -> {
	        	ServerPlayerEntity sender = ctx.get().getSender();
	        	this.worldfile = sender.getEntityWorld().getWorldInfo().getWorldName();
	            if (sender != null) {
	            	System.out.println("INVENTORY PACKET HAS VALID SENDER");
	            	
	    	 		
	 				System.out.println("sender: " + sender);
	 				Set<String> players = WorldEvents.inventories.keySet();
	    	 		boolean hasPlayer = false;
	    	 		for (String player : players) {
	    	 			if (player.equals(this.playername)) {
	    	 				hasPlayer = true;
	    	 				break;
	    	 			}
	    	 		}
            		System.out.println("Loading and sending over an inventory.");
    	 			InventoryTerraria inventoryPlayer = WorldEvents.inventories.get(sender.getScoreboardName());
    	 			
    	 			InventoryChestTerraria inventory = new InventoryChestTerraria(false);
    	 			inventory.player = sender;
    	 			inventory.load(this.position, this.worldfile);
//	    	    	 			NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> sender), new SPacketSendInventoryTerraria(this.playername, inv));
    	 			sendInventoryData(this, ctx, sender, inventory, inventoryPlayer);
    	 			inventory.canSave = true;
    	 			
    	 			System.out.println("UNLOCKING INVENTORIES");
    	 			
    	 			System.out.println(WorldEvents.inventories);

    	 			System.out.println("PUT PLAYER INTO INVENTORY LIST");

    	 			WorldEvents.chests.put(this.position, inventory);

	            	
	            	
	            }
	        });
	        ctx.get().setPacketHandled(true);
	    }
	 	private void sendInventoryData(CPacketRequestInventoryChest msg, Supplier<NetworkEvent.Context> ctx, ServerPlayerEntity sender, InventoryChestTerraria inventory, InventoryTerraria inventoryPlayer) {
	 		System.out.println("SEND INVENTORY DATA: " + msg + ", " + ctx + ", " + sender + ", " + inventory);
	 		
	 		for (int i = 0; i < 30; i++) {
	 			System.out.println("SEND TO CHEST: " + inventoryPlayer.main[i].stack);
	 			NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> sender), new SPacketSyncInventoryChest(0, i, inventoryPlayer.main[i].stack));
	 		}
	 		
	 		for (int i = 0; i < 10; i++) {
	 			NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> sender), new SPacketSyncInventoryChest(1, i, inventoryPlayer.hotbar[i].stack));
	 		}
	 		
	 		NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> sender), new SPacketSyncInventoryChest(2, 0, inventoryPlayer.trash.stack));
	 		
	 		for (int i = 0; i < 30; i++) {
	 			NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> sender), new SPacketSyncInventoryChest(3, i, inventory.chest[i].stack));
	 		}
	 	}
}
