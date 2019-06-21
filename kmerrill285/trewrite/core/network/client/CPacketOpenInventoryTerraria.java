package kmerrill285.trewrite.core.network.client;

import java.util.Set;
import java.util.function.Supplier;

import kmerrill285.trewrite.core.inventory.InventoryTerraria;
import kmerrill285.trewrite.core.inventory.container.TerrariaContainerHandler;
import kmerrill285.trewrite.core.network.NetworkHandler;
import kmerrill285.trewrite.core.network.server.SPacketSendInventoryTerraria;
import kmerrill285.trewrite.events.WorldEvents;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.network.PacketDistributor;

public class CPacketOpenInventoryTerraria {
		public String playername;
	 	public CPacketOpenInventoryTerraria(String playername) {
	 		this.playername = playername;
	 	}
	 	
	 	public static void encode(CPacketOpenInventoryTerraria msg, PacketBuffer buf) {
//	        buf.writeFloat(msg.oldMouseX);
//	        buf.writeFloat(msg.oldMouseY);
	 		buf.writeString(msg.playername);
	    }
	 	
	 	public static CPacketOpenInventoryTerraria decode(PacketBuffer buf) {
	 		//read values in the order they were sent
	        return new CPacketOpenInventoryTerraria(buf.readString(100).trim());
	    }
	 	
	 	public static void handle(CPacketOpenInventoryTerraria msg, Supplier<NetworkEvent.Context> ctx) {

	 		System.out.println("WHAT THE HECK GAME");
	 		
	        ctx.get().enqueueWork(() -> {
	            EntityPlayerMP sender = ctx.get().getSender();
	            System.out.println("WHY ARE YOU NOT WORKING!?");
	            if (sender != null) {
	            	System.out.println("WORK NOW GAME");
	                NetworkHooks.openGui(sender, new TerrariaContainerHandler(), buf -> {
	                    
	                });
	            }
	        });
	    }
}
