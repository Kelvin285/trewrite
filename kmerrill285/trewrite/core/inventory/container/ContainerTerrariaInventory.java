package kmerrill285.trewrite.core.inventory.container;

import kmerrill285.trewrite.Trewrite;
import kmerrill285.trewrite.core.inventory.InventoryTerraria;
import kmerrill285.trewrite.core.items.ItemStackT;
import kmerrill285.trewrite.core.network.NetworkHandler;
import kmerrill285.trewrite.core.network.client.CPacketThrowItemTerraria;
import kmerrill285.trewrite.entities.EntityItemT;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class ContainerTerrariaInventory extends Container {

	private final EntityPlayer player;
    private final boolean isLocalWorld;
	
    public static InventoryTerraria inventory;
    
    public ContainerTerrariaInventory(EntityPlayer player) {
    	this.player = player;
    	this.isLocalWorld = player.getEntityWorld().isRemote;
    }
    
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}

	@Override
    public void onContainerClosed(EntityPlayer playerIn) {
		if (isLocalWorld)
		if (inventory.holdingSlot != null) {
			if (inventory.holdingSlot.stack != null) {
        		NetworkHandler.INSTANCE.sendToServer(new CPacketThrowItemTerraria(0, -1, 0, new ItemStackT(inventory.holdingSlot.stack.item, inventory.holdingSlot.stack.size)));
        		EntityItemT item = (EntityItemT) Trewrite.ITEM_ENTITY_TYPE.spawnEntity(playerIn.world, null, null, playerIn.getPosition().up(), false, false);
            	item.item = inventory.holdingSlot.stack.item.itemName;
				item.stack = inventory.holdingSlot.stack.size;
				item.pickupDelay = 20 * 4;
			}
		}
	}
	
	static {
		if (ContainerTerrariaInventory.inventory == null)
		ContainerTerrariaInventory.inventory = new InventoryTerraria();
	}
}
