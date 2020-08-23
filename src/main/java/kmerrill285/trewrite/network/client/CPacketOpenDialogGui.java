package kmerrill285.trewrite.network.client;

import java.util.function.Supplier;

import kmerrill285.trewrite.client.gui.dialog.DialogContainerHandler;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkHooks;

public class CPacketOpenDialogGui {
		public String playername;
	 	public CPacketOpenDialogGui(String playername) {
	 		this.playername = playername;
	 	}
	 	
	 	public void encode(PacketBuffer buf) {
//	        buf.writeFloat(this.oldMouseX);
//	        buf.writeFloat(this.oldMouseY);
	 		buf.writeString(this.playername);
	    }
	 	
	 	public CPacketOpenDialogGui(PacketBuffer buf) {
	 		//read values in the order they were sent
	        this(buf.readString(100).trim());
	    }
	 	
	 	public void handle(Supplier<NetworkEvent.Context> ctx) {

	        ctx.get().enqueueWork(() -> {
	        	ServerPlayerEntity sender = ctx.get().getSender();
	            if (sender != null) {
	                NetworkHooks.openGui(sender, new DialogContainerHandler());
	            }
	        });
	        ctx.get().setPacketHandled(true);
	    }
}
