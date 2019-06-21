package kmerrill285.trewrite.core.network.server;

import java.util.function.Supplier;

import kmerrill285.trewrite.core.inventory.container.ContainerTerrariaInventory;
import kmerrill285.trewrite.core.items.ItemStackT;
import kmerrill285.trewrite.items.ItemsT;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;


public class SPacketSendInventoryTerraria {
	
	private String player, data;
	
	public SPacketSendInventoryTerraria(String player, String data) {
		this.player = player;
		this.data = data;
	}
	
	public static void encode(SPacketSendInventoryTerraria msg, PacketBuffer buf) {
		buf.writeString(msg.player);
		buf.writeString(msg.data);
    }
	
	public static SPacketSendInventoryTerraria decode(PacketBuffer buf) {
		return new SPacketSendInventoryTerraria(buf.readString(100).trim(), buf.readString(100).trim());
	}
	
	public static void handle(SPacketSendInventoryTerraria msg, Supplier<NetworkEvent.Context> ctx) {
		System.out.println("got inventory packet");
		ctx.get().enqueueWork(() -> {
			  Minecraft mc = Minecraft.getInstance();
			  if (mc != null) {
				  if (mc.player != null) {
					  if (msg.player.contentEquals(mc.player.getScoreboardName())) {
						  System.out.println("getting inventory from packet");
						  ContainerTerrariaInventory.inventory.loadFromString(msg.data);
						  System.out.println("loaded inventory from packet");
					  }
				  }
			  }

        });
        ctx.get().setPacketHandled(true);
	}
}
