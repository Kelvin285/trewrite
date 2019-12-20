package kmerrill285.trewrite.core.network.client;

import java.util.function.Supplier;

import kmerrill285.trewrite.core.inventory.container.TerrariaChestContainerHandler;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkHooks;

public class CPacketOpenChestTerraria {
		public String playername;
	 	public CPacketOpenChestTerraria(String playername) {
	 		this.playername = playername;
	 	}
	 	
	 	public void encode(PacketBuffer buf) {
//	        buf.writeFloat(this.oldMouseX);
//	        buf.writeFloat(this.oldMouseY);
	 		buf.writeString(this.playername);
	    }
	 	
	 	public CPacketOpenChestTerraria(PacketBuffer buf) {
	 		//read values in the order they were sent
	        this(buf.readString(100).trim());
	    }
	 	
	 	public void handle(Supplier<NetworkEvent.Context> ctx) {

	        ctx.get().enqueueWork(() -> {
	        	ServerPlayerEntity sender = ctx.get().getSender();
	            if (sender != null) {
	                NetworkHooks.openGui(sender, new TerrariaChestContainerHandler());
	            }
	        });
	        ctx.get().setPacketHandled(true);
	    }
}
