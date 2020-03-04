package kmerrill285.trewrite.core.network.client;

import java.util.function.Supplier;

import kmerrill285.trewrite.core.inventory.InventoryChestTerraria;
import kmerrill285.trewrite.events.WorldEvents;
import kmerrill285.trewrite.world.WorldStateHolder;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;


public class CPacketSaveChestTerraria {
	
	private String pos;
	
	public CPacketSaveChestTerraria(String position) {
		this.pos = position;
	}
	
	public void encode(PacketBuffer buf) {
		buf.writeString(this.pos);
    }
	
	public CPacketSaveChestTerraria(PacketBuffer buf) {
		this(buf.readString(100).trim());
	}
	
	public void handle(Supplier<NetworkEvent.Context> ctx) {
		System.out.println("handle drop item packet");
		ctx.get().enqueueWork(() -> {
			ServerPlayerEntity sender = ctx.get().getSender();
			if (sender != null) {
//				inventories.get(player).save(player, event.getWorld().getWorldInfo().getWorldName());
				String position = this.pos;
				//InventoryChestTerraria chest = WorldStateHolder.get(sender.world).chests.get(position);
				//System.out.println("SAVING CHEST AT " + position);
				//chest.save(position, sender.getEntityWorld().getWorldInfo().getWorldName());
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
