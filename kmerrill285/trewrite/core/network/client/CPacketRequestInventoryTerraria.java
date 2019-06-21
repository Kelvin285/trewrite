package kmerrill285.trewrite.core.network.client;

import java.util.Set;
import java.util.function.Supplier;

import kmerrill285.trewrite.core.inventory.InventoryTerraria;
import kmerrill285.trewrite.core.network.NetworkHandler;
import kmerrill285.trewrite.core.network.server.SPacketSyncInventoryTerraria;
import kmerrill285.trewrite.events.WorldEvents;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;

public class CPacketRequestInventoryTerraria {
		public String playername;
		public String worldfile;
	 	public CPacketRequestInventoryTerraria(String playername, String worldFile) {
	 		this.playername = playername;
	 		this.worldfile = worldFile;
	 	}
	 	
	 	public static void encode(CPacketRequestInventoryTerraria msg, PacketBuffer buf) {
//	        buf.writeFloat(msg.oldMouseX);
//	        buf.writeFloat(msg.oldMouseY);
	 		buf.writeString(msg.playername);
	 		buf.writeString(msg.worldfile);
	    }
	 	
	 	public static CPacketRequestInventoryTerraria decode(PacketBuffer buf) {
	 		//read values in the order they were sent
	 		System.out.println("DECODING INVENTORY REQUEST");
	        return new CPacketRequestInventoryTerraria(buf.readString(100).trim(), buf.readString(100).trim());
	    }
	 	
	 	public static void handle(CPacketRequestInventoryTerraria msg, Supplier<NetworkEvent.Context> ctx) {

	 		System.out.println("HANDLING INVENTORY REQUEST");
	 		
	        ctx.get().enqueueWork(() -> {
	            EntityPlayerMP sender = ctx.get().getSender();

	            if (sender != null) {
	            	System.out.println("INVENTORY PACKET HAS VALID SENDER");
	            	
	    	 		new Thread() {
	    	 			public void run() {
	    	 				EntityPlayerMP sender = ctx.get().getSender();
	    	 				System.out.println("sender: " + sender);
	    	 				Set<String> players = WorldEvents.inventories.keySet();
	    	    	 		boolean hasPlayer = false;
	    	    	 		for (String player : players) {
	    	    	 			if (player.equals(msg.playername)) {
	    	    	 				hasPlayer = true;
	    	    	 				break;
	    	    	 			}
	    	    	 		}
    	            		System.out.println("Loading and sending over an inventory.");
    	    	 			InventoryTerraria inventory = new InventoryTerraria(false);
    	    	 			inventory.player = sender;
    	    	 			inventory.load(msg.playername, msg.worldfile);
//	    	    	 			NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> sender), new SPacketSendInventoryTerraria(msg.playername, inv));
    	    	 			sendInventoryData(msg, ctx, sender, inventory);
    	    	 			inventory.canSave = true;
    	    	 			
    	    	 			System.out.println("UNLOCKING INVENTORIES");
    	    	 			
    	    	 			System.out.println(WorldEvents.inventories);

    	    	 			System.out.println("PUT PLAYER INTO INVENTORY LIST");

    	    	 			WorldEvents.inventories.put(msg.playername, inventory);

	    	            	
	    	 			}
	    	 		}.start();
	            	
	            }
	        });
	    }
	 	private static void sendInventoryData(CPacketRequestInventoryTerraria msg, Supplier<NetworkEvent.Context> ctx, EntityPlayerMP sender, InventoryTerraria inventory) {
	 		System.out.println("SEND INVENTORY DATA: " + msg + ", " + ctx + ", " + sender + ", " + inventory);
	 		for (int i = 0; i < 30; i++) {
	 			NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> sender), new SPacketSyncInventoryTerraria(0, 0, i, inventory.main[i].stack));
	 		}
	 		for (int i = 0; i < 10; i++) {
	 			NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> sender), new SPacketSyncInventoryTerraria(0, 1, i, inventory.hotbar[i].stack));
	 		}
	 		for (int i = 0; i < 3; i++) {
	 			NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> sender), new SPacketSyncInventoryTerraria(0, 2, i, inventory.armor[i].stack));
	 			NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> sender), new SPacketSyncInventoryTerraria(0, 3, i, inventory.armorVanity[i].stack));
	 			NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> sender), new SPacketSyncInventoryTerraria(0, 4, i, inventory.armorDyes[i].stack));
	 		}
	 		for (int i = 0; i < 5; i++) {
	 			NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> sender), new SPacketSyncInventoryTerraria(0, 5, i, inventory.accessory[i].stack));
	 			NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> sender), new SPacketSyncInventoryTerraria(0, 6, i, inventory.accessoryVanity[i].stack));
	 			NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> sender), new SPacketSyncInventoryTerraria(0, 7, i, inventory.accessoryDyes[i].stack));
	 		}
	 		NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> sender), new SPacketSyncInventoryTerraria(0, 8, 0, inventory.trash.stack));
	 	}
}
