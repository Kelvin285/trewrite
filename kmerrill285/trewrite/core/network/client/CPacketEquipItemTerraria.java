package kmerrill285.trewrite.core.network.client;

import java.util.function.Supplier;

import kmerrill285.trewrite.core.inventory.InventoryTerraria;
import kmerrill285.trewrite.events.WorldEvents;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.network.NetworkEvent;


public class CPacketEquipItemTerraria {
	
	private int hotbarId;
	
	public CPacketEquipItemTerraria(int hotbarId) {
		this.hotbarId = hotbarId;
	}
	
	public static void encode(CPacketEquipItemTerraria msg, PacketBuffer buf) {
		buf.writeInt(msg.hotbarId);
    }
	
	public static CPacketEquipItemTerraria decode(PacketBuffer buf) {
		return new CPacketEquipItemTerraria(buf.readInt());
	}
	
	public static void handle(CPacketEquipItemTerraria msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			EntityPlayerMP sender = ctx.get().getSender();
			if (sender != null) {
				String name = sender.getScoreboardName();
				InventoryTerraria inventory = WorldEvents.inventories.get(name);
				if (inventory != null) {
					
					for (int i = 0; i < 9; i++) {
						if (inventory.savedHotbar[i] == null) {
							inventory.savedHotbar[i] = sender.inventory.mainInventory.get(i);
						}
						sender.inventory.setInventorySlotContents(i, new ItemStack(Items.AIR, 1));
					}
					
					sender.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(inventory.hotbar[msg.hotbarId].stack.itemForRender.getItem(), 1));
					inventory.open = true;
					inventory.hotbarSelected = msg.hotbarId;
				}
				
			}
			
			
//            Entity entity = Minecraft.getInstance().world.getEntityByID(msg.entityId);
//
//            if (entity instanceof EntityLivingBase) {
//            	
//            }
        });
        ctx.get().setPacketHandled(true);
	}
}
