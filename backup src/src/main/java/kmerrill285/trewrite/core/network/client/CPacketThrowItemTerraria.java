package kmerrill285.trewrite.core.network.client;

import java.util.function.Supplier;

import kmerrill285.trewrite.core.inventory.InventorySlot;
import kmerrill285.trewrite.core.inventory.InventoryTerraria;
import kmerrill285.trewrite.core.items.ItemStackT;
import kmerrill285.trewrite.core.network.NetworkHandler;
import kmerrill285.trewrite.core.network.server.SPacketSyncInventoryTerraria;
import kmerrill285.trewrite.entities.EntityItemT;
import kmerrill285.trewrite.events.WorldEvents;
import kmerrill285.trewrite.items.ItemsT;
import kmerrill285.trewrite.items.modifiers.ItemModifier;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;


public class CPacketThrowItemTerraria {
	
	private int entityId;
	private int slotId;
    private ItemStackT stack;
    private int inventoryArea;
	
	public CPacketThrowItemTerraria(int entityId, int inventoryArea, int slotId, ItemStackT stack) {
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
        buf.writeString(this.stack.item.itemName);
        buf.writeInt(this.stack.size);
        buf.writeInt(this.stack.modifier);
    }
	
	public CPacketThrowItemTerraria(PacketBuffer buf) {
		this(buf.readInt(), buf.readInt(), buf.readInt(), new ItemStackT(ItemsT.getItemFromString(buf.readString(100).trim()), buf.readInt(), ItemModifier.getModifier(buf.readInt())));
	}
	
	public void handle(Supplier<NetworkEvent.Context> ctx) {
		System.out.println("handle drop item packet");
		ctx.get().enqueueWork(() -> {
			ServerPlayerEntity sender = ctx.get().getSender();
			if (sender != null) {
				System.out.println("Sender is not null");
				String name = sender.getScoreboardName();
				int i = this.slotId;
				ItemStackT stack = this.stack;
				if (stack.size < 0) {
					stack = null;
				}
				InventoryTerraria inventory = WorldEvents.inventories.get(name);
				if (inventory == null) return;
				System.out.println("Inventory is not null");
				if (this.inventoryArea == 8) {
					System.out.println("trash");
					inventory.trash.stack = stack;
				} else {
					System.out.println("start drop");
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
					
					if (area == -1) {
						ItemStackT s = new ItemStackT(this.stack.item, this.stack.size, ItemModifier.getModifier(this.stack.modifier));
//						EntityItemT item = (EntityItemT) EntitiesT.ITEM.spawn(sender.world, null, null, sender.getPosition().up(), SpawnReason.EVENT, false, false);
//						item.item = s.item.itemName;
//						item.stack = s.size;
//						item.pickupDelay = 20 * 4;
						EntityItemT.spawnItem(sender.world, sender.getPosition().up(), new ItemStackT(s.item, s.size, ItemModifier.getModifier(s.modifier)), 20 * 4);
						System.out.println("drop item");
					} else {
						if (slots[i] != null) {
							ItemStackT s = new ItemStackT(slots[i].stack.item, slots[i].stack.size, ItemModifier.getModifier(slots[i].stack.modifier));
							slots[i].stack = null;
				 			NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> sender), new SPacketSyncInventoryTerraria(0, area, i, null));
				 			
//				 			EntityItemT item = (EntityItemT) EntitiesT.ITEM.spawn(sender.world, null, null, sender.getPosition().up(), SpawnReason.EVENT, false, false);
//				 			item.item = s.item.itemName;
//							item.stack = s.size;
//							item.pickupDelay = 20 * 4;
							EntityItemT.spawnItem(sender.world, sender.getPosition().up(), new ItemStackT(s.item, s.size, ItemModifier.getModifier(s.modifier)), 20 * 4);
							System.out.println("drop item");
						}
					}
					
					
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
