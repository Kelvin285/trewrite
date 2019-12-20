package kmerrill285.trewrite.core.network.client;

import java.util.function.Supplier;

import kmerrill285.trewrite.core.inventory.InventoryTerraria;
import kmerrill285.trewrite.events.WorldEvents;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;


public class CPacketCloseInventoryTerraria {
	
	
	public CPacketCloseInventoryTerraria() {
	}
	
	public void encode(PacketBuffer buf) {
    }
	
	public CPacketCloseInventoryTerraria(PacketBuffer buf) {
		
	}
	
	public void handle(Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			ServerPlayerEntity sender = ctx.get().getSender();
			if (sender != null) {
				String name = sender.getScoreboardName();
				InventoryTerraria inventory = WorldEvents.inventories.get(name);
				if (inventory != null) {
					
					for (int i = 0; i < 9; i++) {
						if (inventory.savedHotbar[i] != null) {
							sender.inventory.setInventorySlotContents(i, new ItemStack(inventory.savedHotbar[i].getItem(), inventory.savedHotbar[i].getCount()));
							inventory.savedHotbar[i] = null;
						}
					}
					
					
					
					inventory.open = false;
				}
				
			}
			
			
//            Entity entity = Minecraft.getInstance().world.getEntityByID(this.entityId);
//
//            if (entity instanceof EntityLivingBase) {
//            	
//            }
        });
        ctx.get().setPacketHandled(true);
	}
}
