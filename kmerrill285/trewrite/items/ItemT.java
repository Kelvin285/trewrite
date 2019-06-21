package kmerrill285.trewrite.items;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Multimap;

import kmerrill285.trewrite.util.Conversions;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class ItemT extends Item {

	public int buyPrice;
	public int sellPrice;
	
	public boolean melee, throwing, ranged, summon, magic;
	public int mana = 0;
	public int damage = 0;
	public float knockback = -1;
	public float critChance = 4;
	public int useTime = -1;
	public float pick, axe, hammer;
	public ItemT ammo;
	public boolean isAmmo;
	public int stackSize;
	public String tooltip = "";
	public int heal;
	public int manaHeal;
	public boolean consumable;
	public boolean autoReuse;
	public boolean accessory;
	
	public int potionSickness, manaSickness;
	
	public int speed;
	
	public boolean material;
	
	public String itemName;
	
	public int maxStack = 999;
	
	public EnumAction useAction = EnumAction.NONE;
	
	protected ItemT(Properties properties) {
		super(properties);
	}
	
	public ItemT(Properties properties, String name) {
		super(properties);
		this.setLocation(name);
	}
	
	public ItemT setPotionSickness(int n) {
		this.potionSickness = n;
		return this;
	}
	
	public ItemT setTooltip(String tooltip) {
		this.tooltip = tooltip;
		return this;
	}
	
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		if (pick > 0)
			tooltip.add(new TextComponentString(pick + "% pickaxe power"));
		if (axe > 0)
			tooltip.add(new TextComponentString(axe + "% axe power"));
		if (hammer > 0)
			tooltip.add(new TextComponentString(hammer + "% hammer power"));
		if (melee)
			tooltip.add(new TextComponentString(damage + " melee damage"));
		if (ranged)
			tooltip.add(new TextComponentString(damage + " ranged damage"));
		if (throwing)
			tooltip.add(new TextComponentString(damage + " throwing damage"));
		if (summon)
			tooltip.add(new TextComponentString(damage + " summon damage"));
		if (magic)
			tooltip.add(new TextComponentString(damage + " magic damage"));
		if (mana > 0)
			tooltip.add(new TextComponentString("uses " + damage + " mana"));
		if (critChance > 0 && damage > 0)
			tooltip.add(new TextComponentString(new String(critChance + "% critical strike chance").replace(".0", "")));
		if (knockback > -1)
			tooltip.add(new TextComponentString(kmerrill285.trewrite.util.Util.getKnockbackString(knockback)));
		if (useTime > -1)
			tooltip.add(new TextComponentString(kmerrill285.trewrite.util.Util.getSpeedString(useTime)));
		if (isAmmo)
			tooltip.add(new TextComponentString("ammo"));
		if (heal > 0)
			tooltip.add(new TextComponentString("heals " + heal + " health"));
		if (manaHeal > 0)
			tooltip.add(new TextComponentString("restores " + manaHeal + " mana"));
		if (consumable)
			tooltip.add(new TextComponentString("consumable"));
		if (accessory)
			tooltip.add(new TextComponentString("accessory"));
		if (material)
			tooltip.add(new TextComponentString("material"));
		if (tooltip.isEmpty() == false)
			tooltip.add(new TextComponentString(""+this.tooltip));
		
	}
	
	
	public int getUseDuration(ItemStack stack) {
		return useTime;
	}
	
	public EnumAction getUseAction(ItemStack stack) {
		return useAction;
	}
	
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		onUse(null, null, playerIn, worldIn, handIn);
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	
	public void onUse(Entity entity, BlockPos pos, EntityPlayer player, World worldIn, EnumHand handIn) {
		
	}
	
	public boolean onAttack(Entity target, BlockPos pos, EntityPlayer player, World worldIn, EnumHand handIn) {
		if (target != null) {
			if (new Random().nextInt(100) <= critChance) {
				target.attackEntityFrom(DamageSource.causePlayerDamage(player), damage * 2);
			}
			else
				target.attackEntityFrom(DamageSource.causePlayerDamage(player), damage);
		}
		return true;
	}
	
	protected void setLocation(String name) {
		this.setRegistryName(new ResourceLocation("trewrite", name));
		ItemsT.items.put(name, this);
		this.itemName = name;
	}
	
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
	      Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(equipmentSlot);
	      if (equipmentSlot == EntityEquipmentSlot.MAINHAND) {
	         multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", (double)this.damage - 1, 0));
	         multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", (double)(56 - this.useTime) / (56.0 / 2.0), 0));
	      }

	      return multimap;
	 }

	public ItemT setBuySell(int sell) {
		this.sellPrice = sell;
		this.buyPrice = Conversions.sellToBuy(sell);
		return this;
	}
	
	public ItemT setConsumable() {
		this.consumable = true;
		return this;
	}
	
	public ItemT setHeal(int heal) {
		this.heal = heal;
		return this;
	}
	
	public ItemT setManaHeal(int manaHeal) {
		this.manaHeal = manaHeal;
		return this;
	}

	public ItemT setMaterial() {
		this.material = true;
		return this;
	}
	
	public ItemT setMaxStack(int maxStack) {
		this.maxStack = maxStack;
		return this;
	}

	public ItemT setManaSickness(int i) {
		this.manaSickness = i;
		return this;
	}
}
