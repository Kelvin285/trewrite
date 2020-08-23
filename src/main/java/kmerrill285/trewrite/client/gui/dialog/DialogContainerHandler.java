package kmerrill285.trewrite.client.gui.dialog;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class DialogContainerHandler implements INamedContainerProvider {

    public static final ResourceLocation ID = new ResourceLocation("trewrite", "dialog");

    public String getGuiID() {
        return ID.toString();
    }
    
	@Override
	public Container createMenu(int arg0, PlayerInventory arg1, PlayerEntity arg2) {
		return new DialogContainer(arg0, arg1, null);
	}

	@Override
	public ITextComponent getDisplayName() {
		return new StringTextComponent(getGuiID());
	}
}
