package kmerrill285.trewrite.network.client;

import java.util.function.Supplier;

import kmerrill285.trewrite.client.gui.inventory.InventorySlot;
import kmerrill285.trewrite.client.gui.inventory.InventoryTerraria;
import kmerrill285.trewrite.events.WorldEvents;
import kmerrill285.trewrite.items.ItemStackT;
import kmerrill285.trewrite.items.ItemsT;
import kmerrill285.trewrite.items.modifiers.ItemModifier;
import net.minecraft.entity.player.ServerPlayerEntity;
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
		if (this.stack == null) this.stack = new ItemStackT(ItemsT.DIRT_BLOCK, -1, null);
	}
	
	public void encode(PacketBuffer buf) {
		buf.writeInt(this.entityId);
		buf.writeInt(this.inventoryArea);
        buf.writeInt(this.slotId);
        buf.writeString(ItemsT.getStringForItem(this.stack.item));
        buf.writeInt(this.stack.size);
        buf.writeInt(this.stack.modifier);
    }
	
	public CPacketSyncInventoryTerraria(PacketBuffer buf) {
		this(buf.readInt(), buf.readInt(), buf.readInt(), new ItemStackT(ItemsT.getItemFromString(buf.readString(100).trim()), buf.readInt(), ItemModifier.getModifier(buf.readInt())));
	}
	
	public void handle(Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			ServerPlayerEntity sender = ctx.get().getSender();
			if (sender != null) {
				int i = this.slotId;
				ItemStackT stack = this.stack;
				if (stack.size < 0) {
					stack = null;
				}
				InventoryTerraria inventory = WorldEvents.getOrLoadInventory(sender);
				if (inventory != null) {
//					System.out.println("INVENTORY EXISTS.  SYNCING FROM CLIENT TO SERVER: " + i);
				} else return;
				
				if (this.inventoryArea == 8) {
					inventory.trash.stack = stack;
				} else {
					InventorySlot[] slots = null;
					int area = this.inventoryArea;
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
			
			
//            Entity entity = Minecraft.getInstance().world.getEntityByID(this.entityId);
//
//            if (entity instanceof EntityLivingBase) {
//            	
//            }
        });
        ctx.get().setPacketHandled(true);
	}
}
