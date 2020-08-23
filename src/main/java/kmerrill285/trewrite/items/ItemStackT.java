package kmerrill285.trewrite.items;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import kmerrill285.trewrite.items.modifiers.ItemModifier;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class ItemStackT {
	
	public Item item;
	public int size;
	public ItemStack itemForRender;
	public int modifier;
	public int renderStack;
		
	public ItemStackT(Item item, ItemModifier modifier) {
		this.item = item;
		this.size = 1;
		this.itemForRender = new ItemStack(item);
		this.modifier = ItemModifier.getIdForModifier(modifier);
	}
	
	public ItemStackT(String item, ItemModifier modifier) {
		this.item = ItemsT.getItemFromString(item);
		this.size = 1;
		this.itemForRender = new ItemStack(ItemsT.getItemFromString(item));
		this.modifier = ItemModifier.getIdForModifier(modifier);
	}
	
	public ItemStackT(Item item) {
		this.item = item;
		this.size = 1;
		this.itemForRender = new ItemStack(item);
		this.modifier = -1;
	}
	
	public ItemStackT(String item) {
		this.item = ItemsT.getItemFromString(item);
		this.size = 1;
		this.itemForRender = new ItemStack(ItemsT.getItemFromString(item));
		this.modifier = -1;
	}
	
	public ItemStackT(Item item, int size) {
		this.item = item;
		this.size = size;
		this.itemForRender = new ItemStack(item);
		this.modifier = -1;
	}
	
	public ItemStackT(String item, int size) {
		System.out.println(item);
		this.item = ItemsT.getItemFromString(item);
		this.size = size;
		this.itemForRender = new ItemStack(ItemsT.getItemFromString(item));
		this.modifier = -1;
	}
	
	public ItemStackT(Item item, int size, ItemModifier modifier) {
		this.item = item;
		this.size = size;
		this.itemForRender = new ItemStack(item);
		
		
			
		
		if (modifier != null)
		{
			double speed = modifier.speed;
			if(ItemModifier.getModifier(this.modifier) != null)
				this.itemForRender.addAttributeModifier(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(UUID.fromString("FA233E1C-4180-4865-B01B-BCCE9785ACA3"), "Tool modifier", (((ItemT)item).useTime * (speed / 100.0f) - (((ItemT)item).useTime * (speed / 100.0f) * 1.1) + 1) * (speed / 100.0f), Operation.ADDITION), EquipmentSlotType.MAINHAND);
		}
		this.modifier = ItemModifier.getIdForModifier(modifier);
	}
	
	public ItemStackT(String item, int size, ItemModifier modifier) {
		this(item, size);
		if (modifier != null)
		{
			double speed = modifier.speed;
			if(ItemModifier.getModifier(this.modifier) != null)
				this.itemForRender.addAttributeModifier(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(UUID.fromString("FA233E1C-4180-4865-B01B-BCCE9785ACA3"), "Tool modifier", (((ItemT)this.item).useTime * (speed / 100.0f) - (((ItemT)this.item).useTime * (speed / 100.0f) * 1.1) + 1) * (speed / 100.0f), Operation.ADDITION), EquipmentSlotType.MAINHAND);
		}
		this.modifier = ItemModifier.getIdForModifier(modifier);
	}
	
	public void reforge(Item item) {
		
		this.modifier = ItemModifier.getIdForModifier(ItemModifier.getRandomModifier(item));
		
		
		if(ItemModifier.getModifier(this.modifier) != null)
		{
			double speed = ItemModifier.getModifier(this.modifier).speed;
			this.itemForRender.addAttributeModifier(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(UUID.fromString("FA233E1C-4180-4865-B01B-BCCE9785ACA3"), "Tool modifier", (((ItemT)item).useTime * (speed / 100.0f) - (((ItemT)item).useTime * (speed / 100.0f) * 1.1) + 1), Operation.ADDITION), EquipmentSlotType.MAINHAND);
		}
	}

	public List<ITextComponent> getTooltip(PlayerEntity player, ITooltipFlag advanced) {
		List<ITextComponent> tooltips = new ArrayList<ITextComponent>();
		ItemModifier mod = ItemModifier.getModifier(modifier);
		if (this.itemForRender != null) {
			tooltips.addAll(itemForRender.getTooltip(player, advanced));
		}
		
		if (mod != null) {
			if (this.itemForRender != null) {
				this.itemForRender.setDisplayName(new StringTextComponent(mod.name + " " + this.itemForRender.getItem().getName().getString()));
			}
			tooltips.add(new StringTextComponent(mod.name));
			mod.addTooltips(tooltips);
		}
		
		return tooltips;
	}
	
}
