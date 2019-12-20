package kmerrill285.trewrite.core.network.server;

import java.util.function.Supplier;

import kmerrill285.trewrite.core.inventory.InventoryChestTerraria;
import kmerrill285.trewrite.core.inventory.InventoryTerraria;
import kmerrill285.trewrite.core.inventory.container.ContainerTerrariaChest;
import kmerrill285.trewrite.core.inventory.container.ContainerTerrariaInventory;
import kmerrill285.trewrite.core.items.ItemStackT;
import kmerrill285.trewrite.items.ItemsT;
import kmerrill285.trewrite.items.modifiers.ItemModifier;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;


public class SPacketSyncInventoryChest {
	
	private int slotId;
    private ItemStackT stack;
    private int inventoryArea;
	
	public SPacketSyncInventoryChest(int inventoryArea, int slotId, ItemStackT stack) {
		this.slotId = slotId;
		this.stack = stack;
		this.inventoryArea = inventoryArea;
	}
	
	public void encode(PacketBuffer buf) {
		buf.writeInt(inventoryArea);
        buf.writeInt(slotId);
        if (stack != null) {
        	buf.writeString(stack.item.itemName);
            buf.writeInt(stack.size);
            buf.writeInt(stack.modifier);
        } else {
        	buf.writeString("dirt_block");
        	buf.writeInt(-1);
        	buf.writeInt(-1);
        }
        
    }
	
	public SPacketSyncInventoryChest(PacketBuffer buf) {
		this(buf.readInt(), buf.readInt(), new ItemStackT(ItemsT.getItemFromString(buf.readString(100).trim()), buf.readInt(), ItemModifier.getModifier(buf.readInt())));
	}
	
	public void handle(Supplier<NetworkEvent.Context> ctx) {
		System.out.println("SYNCING INVENTORY SLOT");
		ctx.get().enqueueWork(() -> {
			
//          Entity entity = Minecraft.getInstance().world.getEntityByID(entityId);
//
//          if (entity instanceof EntityLivingBase) {
//          	
//          }
			
			int i = slotId;
			ItemStackT stack = this.stack;
			if (stack != null) {
				if (stack.size < 0) stack = null;
			}
			int invArea = inventoryArea;
			InventoryChestTerraria inventory = ContainerTerrariaChest.inventory;
			InventoryTerraria inventoryPlayer = ContainerTerrariaInventory.inventory;
			switch (invArea) {
			case 0:
				System.out.println("SLOT MAIN IN CHEST " + i + " SET TO " + (stack != null ? stack.item.itemName : null));
				inventory.main[i].stack = stack;
				inventoryPlayer.main[i].stack = stack;
				break;
			case 1:
				System.out.println("SLOT HOTBAR IN CHEST " + i + " SET TO " + (stack != null ? stack.item.itemName : null));
				inventory.hotbar[i].stack = stack;
				inventoryPlayer.hotbar[i].stack = stack;
				break;
			case 2:
				System.out.println("SLOT TRASH IN CHEST SET TO " + (stack != null ? stack.item.itemName : null));
				inventory.trash.stack = stack;
				inventoryPlayer.trash.stack = stack;
				break;
			case 3:
				System.out.println("SLOT CHEST IN CHEST " + i + " SET TO " + (stack != null ? stack.item.itemName : null));
				inventory.chest[i].stack = stack;
				break;
			}
//            Entity entity = Minecraft.getInstance().world.getEntityByID(entityId);
//
//            if (entity instanceof EntityLivingBase) {
//            	
//            }
        });
        ctx.get().setPacketHandled(true);
	}
}
