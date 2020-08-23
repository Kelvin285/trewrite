package kmerrill285.trewrite.client.gui.dialog;

import javax.annotation.Nullable;

import kmerrill285.trewrite.client.gui.inventory.container.Containers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;

public class DialogContainer extends Container {

	 	@Nullable
	    public DialogContainer (int windowId, PlayerInventory inv) {
	    	super(Containers.DIALOG, windowId);
	    }
	    @Nullable
	    public DialogContainer(int windowId, PlayerInventory inv, PacketBuffer extraData) {
	    	super(Containers.DIALOG, windowId);
	    }
	    
		
		@Override
		public boolean canInteractWith(PlayerEntity playerIn) {
			return true;
		}

		@Override
	    public void onContainerClosed(PlayerEntity playerIn) {
			
		}

}
