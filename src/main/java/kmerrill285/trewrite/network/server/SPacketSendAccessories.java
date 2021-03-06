package kmerrill285.trewrite.network.server;

import java.util.ArrayList;
import java.util.function.Supplier;

import kmerrill285.trewrite.client.ClientProxy;
import kmerrill285.trewrite.client.gui.inventory.InventoryTerraria;
import kmerrill285.trewrite.events.WorldEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;


public class SPacketSendAccessories {
	
	public String player;
	public PlayerEntity p;
	public ArrayList<ItemStack> items = new ArrayList<ItemStack>();
	public ArrayList<ItemStack> items2 = new ArrayList<ItemStack>();

	public SPacketSendAccessories(PlayerEntity player) {
		this.player = player.getScoreboardName();
		this.p = player;
	}
	
	public void encode(PacketBuffer buf) {
		buf.writeString(player);
        InventoryTerraria inventory = WorldEvents.getOrLoadInventory(p);
        if (inventory != null) {
        	for (int i = 0; i < inventory.accessory.length; i++) {
        		if (inventory.accessoryVanity[i].stack != null) 
        		{
        			buf.writeBoolean(true);
        			buf.writeItemStack(inventory.accessoryVanity[i].stack.itemForRender);
        		} else {
        			if (inventory.accessory[i].stack != null) {
        				buf.writeBoolean(true);
        				buf.writeItemStack(inventory.accessory[i].stack.itemForRender);
        			}
        		}
        	}
        	for (int i = 0; i < inventory.armor.length; i++) {
        		if (inventory.armorVanity[i].stack != null) 
        		{
        			buf.writeBoolean(false);
        			buf.writeItemStack(inventory.armorVanity[i].stack.itemForRender);
        		} else {
        			if (inventory.armor[i].stack != null) {
        				buf.writeBoolean(false);
        				buf.writeItemStack(inventory.armor[i].stack.itemForRender);
        			}
        		}
        	}
        }
    }
	
	public SPacketSendAccessories(PacketBuffer buf) {
		player = buf.readString(100).trim();
		int i = 0;
		while (buf.isReadable()) {
			if (buf.readBoolean()) {
				items.add(buf.readItemStack());
			} else {
				items2.add(buf.readItemStack());
			}
			i++;
		}
	}
	
	public void handle(Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			ClientProxy.playerAccessories.put(player, items);
			ClientProxy.playerArmor.put(player, items2);
        });
        ctx.get().setPacketHandled(true);
	}
}
