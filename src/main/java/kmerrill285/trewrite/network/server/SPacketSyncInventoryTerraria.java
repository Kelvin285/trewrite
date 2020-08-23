package kmerrill285.trewrite.network.server;

import java.util.function.Supplier;

import kmerrill285.trewrite.client.gui.inventory.InventoryTerraria;
import kmerrill285.trewrite.client.gui.inventory.container.ContainerTerrariaInventory;
import kmerrill285.trewrite.items.ItemStackT;
import kmerrill285.trewrite.items.ItemsT;
import kmerrill285.trewrite.items.modifiers.ItemModifier;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;


public class SPacketSyncInventoryTerraria {
	
	private int entityId;
	private int slotId;
    private ItemStackT stack;
    private int inventoryArea;
	
	public SPacketSyncInventoryTerraria(int entityId, int inventoryArea, int slotId, ItemStackT stack) {
		this.entityId = entityId;
		this.slotId = slotId;
		this.stack = stack;
		this.inventoryArea = inventoryArea;
	}
	
	public static void encode(SPacketSyncInventoryTerraria msg, PacketBuffer buf) {
		buf.writeInt(msg.entityId);
		buf.writeInt(msg.inventoryArea);
        buf.writeInt(msg.slotId);
        if (msg.stack != null) {
        	buf.writeString(ItemsT.getStringForItem(msg.stack.item));
            buf.writeInt(msg.stack.size);
            buf.writeInt(msg.stack.modifier);
        } else {
        	buf.writeString("dirt_block");
        	buf.writeInt(-1);
        	buf.writeInt(-1);
        }
        
    }
	
	public static SPacketSyncInventoryTerraria decode(PacketBuffer buf) {
		int a = buf.readInt();
		int b = buf.readInt();
		int c = buf.readInt();
		String item = buf.readString(100).trim();
//		if (item.contentEquals("null") || item.equalsIgnoreCase("null") || item.startsWith("null")) {
//			return new SPacketSyncInventoryTerraria(buf.readInt(), buf.readInt(), buf.readInt(), null);
//		}
		return new SPacketSyncInventoryTerraria(a, b, c, new ItemStackT(ItemsT.getItemFromString(item), buf.readInt(), ItemModifier.getModifier(buf.readInt())));
	}
	
	public static void handle(SPacketSyncInventoryTerraria msg, Supplier<NetworkEvent.Context> ctx) {
//		System.out.println("SYNCING INVENTORY SLOT");
		ctx.get().enqueueWork(() -> {
			
//          Entity entity = Minecraft.getInstance().world.getEntityByID(msg.entityId);
//
//          if (entity instanceof EntityLivingBase) {
//          	
//          }
			
			int i = msg.slotId;
			ItemStackT stack = msg.stack;
			if (stack != null) {
				if (stack.size < 0) stack = null;
			}
			int invArea = msg.inventoryArea;
			InventoryTerraria inventory = ContainerTerrariaInventory.inventory;
			switch (invArea) {
			case 0:
//				System.out.println("SLOT MAIN " + i + " SET TO " + (stack != null ? ItemsT.getStringForItem(stack.item) : null));
				inventory.main[i].stack = stack;
				break;
			case 1:
//				System.out.println("SLOT HOTBAR " + i + " SET TO " + (stack != null ? ItemsT.getStringForItem(stack.item) : null));
				inventory.hotbar[i].stack = stack;
				break;
			case 2:
//				System.out.println("SLOT ARMOR " + i + " SET TO " + (stack != null ? ItemsT.getStringForItem(stack.item) : null));
				inventory.armor[i].stack = stack;
				break;
			case 3:
//				System.out.println("SLOT ARMOR VANITY " + i + " SET TO " + (stack != null ? ItemsT.getStringForItem(stack.item) : null));
				inventory.armorVanity[i].stack = stack;
				break;
			case 4:
//				System.out.println("SLOT ARMOR DYES " + i + " SET TO " + (stack != null ? ItemsT.getStringForItem(stack.item) : null));
				inventory.armorDyes[i].stack = stack;
				break;
			case 5:
//				System.out.println("SLOT ACCESSORY " + i + " SET TO " + (stack != null ? ItemsT.getStringForItem(stack.item) : null));
				inventory.accessory[i].stack = stack;
				break;
			case 6:
//				System.out.println("SLOT ACCESSORY VANITY " + i + " SET TO " + (stack != null ? ItemsT.getStringForItem(stack.item) : null));
				inventory.accessoryVanity[i].stack = stack;
				break;
			case 7:
//				System.out.println("SLOT ACCESSORY DYES " + i + " SET TO " + (stack != null ? ItemsT.getStringForItem(stack.item) : null));
				inventory.accessoryDyes[i].stack = stack;
				break;
			case 8:
//				System.out.println("SLOT TRASH SET TO " + (stack != null ? ItemsT.getStringForItem(stack.item) : null));
				inventory.trash.stack = stack;
				break;
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
