package kmerrill285.trewrite.core.network.client;

import java.util.function.Supplier;

import kmerrill285.trewrite.core.inventory.InventoryTerraria;
import kmerrill285.trewrite.events.WorldEvents;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraftforge.fml.network.NetworkEvent;


public class CPacketNegateFall {
	
	
	public CPacketNegateFall() {
	}
	
	public void encode(PacketBuffer buf) {
    }
	
	public CPacketNegateFall(PacketBuffer buf) {
	}
	
	public void handle(Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			ServerPlayerEntity sender = ctx.get().getSender();
			if (sender != null) {
				sender.fallDistance = 0;
			}
        });
        ctx.get().setPacketHandled(true);
	}
}
