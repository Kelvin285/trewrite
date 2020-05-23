package kmerrill285.trewrite.items;

import kmerrill285.modelloader.CustomModel;
import kmerrill285.modelloader.ModelLoader;
import kmerrill285.trewrite.core.inventory.InventorySlot;
import kmerrill285.trewrite.entities.models.PlayerModel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;

public class Armor extends ItemT {
	public enum ArmorType {
		HEAD,
		CHEST,
		LEGS
	}
	public enum RenderType {
		EYES, HEAD, SHIRT, PANTS, SHOES
	}
	
	public ArmorType type;
	public ResourceLocation texture;
	public CustomModel model;
	public boolean noShoes;
	public RenderType render;

	
	public Armor(Properties properties, String name, ArmorType type, RenderType render, int defense, int width, int height, boolean noShoes) {
		super(properties, name);
		this.defense = defense;
		this.type = type;
		this.texture = new ResourceLocation("trewrite:textures/models/armor/"+name+".png");
		this.model = new CustomModel(ModelLoader.loadModelFromFile("armor/"+name), width, height);
		this.render = render;
		this.noShoes = noShoes;
	}


	public void renderArmor(PlayerModel model) {
		if (noShoes == true) model.noShoes = true;
		if (this == ItemsT.GOGGLES) this.model = null;
		if (render == RenderType.EYES) {
			if (this.model != null)
			model.armorEyes = this.model;
			model.armorTexEyes = texture;
		}
		if (render == RenderType.HEAD) {
			model.armorHead = this.model;
			model.armorTexHead = texture;
		}
		if (render == RenderType.SHIRT) {
			model.armorShirt = this.model;
			model.armorTexShirt = texture;
		}
		if (render == RenderType.SHOES) {
			model.armorShoes = this.model;
			model.armorTexShoes = texture;
		}
		if (render == RenderType.PANTS) {
			model.armorPants = this.model;
			model.armorTexPants = texture;
		}
		
	}


	public int getDefense(InventorySlot[] armor) {
		if (this == ItemsT.WOODEN_HELMET) {
			if (armor[1].stack != null && armor[2].stack != null)
			if (armor[1].stack.item == ItemsT.WOODEN_CHESTPLATE && armor[2].stack.item == ItemsT.WOODEN_GREAVES) {
				return defense + 1;
			}
		}
		return defense;
	}
}
