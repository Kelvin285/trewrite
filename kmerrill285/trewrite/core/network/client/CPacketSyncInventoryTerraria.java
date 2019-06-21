package kmerrill285.trewrite.core.network.client;

import java.util.function.Supplier;

import kmerrill285.trewrite.core.inventory.InventorySlot;
import kmerrill285.trewrite.core.inventory.InventoryTerraria;
import kmerrill285.trewrite.core.items.ItemStackT;
import kmerrill285.trewrite.events.WorldEvents;
import kmerrill285.trewrite.items.ItemsT;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;


public class CPacketSyncInventoryTerraria {
	
	private int entityId;
	private int slotId;
    private ItemStackT stack;
    private int inventoryArea;
	
	public CPacketSyncInventoryTerraria(int entityId, int inventoryArea, int slotId, ItemStackT stack) {
		this.entityId = entityId;
		this.slotId = slotId;
		this.stack = stack;
		this.inventoryArea = inventoryArea;
		if (this.stack == null) this.stack = new ItemStackT(ItemsT.DIRT_BLOCK, -1);
	}
	
	public static void encode(CPacketSyncInventoryTerraria msg, PacketBuffer buf) {
		buf.writeInt(msg.entityId);
		buf.writeInt(msg.inventoryArea);
        buf.writeInt(msg.slotId);
        buf.writeString(msg.stack.item.itemName);
        buf.writeInt(msg.stack.size);
    }
	
	public static CPacketSyncInventoryTerraria decode(PacketBuffer buf) {
		return new CPacketSyncInventoryTerraria(buf.readInt(), buf.readInt(), buf.readInt(), new ItemStackT(ItemsT.getItemFromString(buf.readString(100).trim()), buf.readInt()));
	}
	
	public static void handle(CPacketSyncInventoryTerraria msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			EntityPlayerMP sender = ctx.get().getSender();
			if (sender != null) {
				String name = sender.getScoreboardName();
				int i = msg.slotId;
				ItemStackT stack = msg.stack;
				if (stack.size < 0) {
					stack = null;
				}
				InventoryTerraria inventory = WorldEvents.inventories.get(name);
				if (inventory != null) {
					System.out.println("INVENTORY EXISTS.  SYNCING FROM CLIENT TO SERVER");
				} else return;
				
				if (msg.inventoryArea == 8) {
					inventory.trash.stack = stack;
				} else {
					InventorySlot[] slots = null;
					int area = msg.inventoryArea;
					if (area == 0) slots = inventory.main;
					if (area == 1) slots = inventory.hotbar;
					if (area == 2) slots = inventory.armor;
					if (area == 3) slots = inventory.armorVanity;
					if (area == 4) slots = inventory.armorDyes;
					if (area == 5) slots = inventory.accessory;
					if (area == 6) slots = inventory.accessoryVanity;
					if (area == 7) slots = inventory.accessoryDyes;
					if (slots[i] != null) slots[i].stack = stack;
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
