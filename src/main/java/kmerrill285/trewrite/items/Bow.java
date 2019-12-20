package kmerrill285.trewrite.items;


import kmerrill285.trewrite.core.inventory.InventorySlot;
import kmerrill285.trewrite.core.inventory.InventoryTerraria;
import kmerrill285.trewrite.core.inventory.container.ContainerTerrariaInventory;
import kmerrill285.trewrite.entities.projectiles.EntityArrowT;
import kmerrill285.trewrite.events.WorldEvents;
import kmerrill285.trewrite.items.modifiers.EnumModifierType;
import kmerrill285.trewrite.items.modifiers.ItemModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.SnowballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class Bow extends ItemT {
	public Bow(Properties properties, String name, int damage) {
		super(properties, name);
		this.damage = damage;
		this.ranged = true;
		MODIFIER_TYPE = EnumModifierType.RANGED;

		
	}
	
	public int getUseDuration(ItemStack t) {
		return 4;
	}
	
	 public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		
	      ItemStack itemstack = playerIn.getHeldItem(handIn);
	      
	      InventoryTerraria inventory = null;
	      if (!worldIn.isRemote) {
	    	  inventory = WorldEvents.inventories.get(playerIn.getScoreboardName());
	      } else 
	      {
	    	  inventory = ContainerTerrariaInventory.inventory;
	      }
	      
	      int ii = 0;
	      boolean shoot = false;
	      
	      InventorySlot bowSlot = inventory.hotbar[inventory.hotbarSelected];
	      float velocity = this.velocity;
	      float kb = 1.0f;
	      float speed = 1.0f;
	      float dmg = 1.0f;
	      float crit = 1.0f;
	      if (bowSlot.stack != null) {
	    	  if (bowSlot.stack.item instanceof Bow) {
	    		  velocity += velocity * (ItemModifier.getModifier(bowSlot.stack.modifier).velocity / 100.0);
	    	  }
	    	  kb = (float) (ItemModifier.getModifier(bowSlot.stack.modifier).knockback / 100.0f);
	    	  crit = (float) (ItemModifier.getModifier(bowSlot.stack.modifier).crit);
	    	  speed = (float) (ItemModifier.getModifier(bowSlot.stack.modifier).speed / 100.0f);
	    	  dmg = (float) (ItemModifier.getModifier(bowSlot.stack.modifier).damage / 100.0f);
	      }
	      
		  playerIn.getCooldownTracker().setCooldown(this, (int) (this.useTime / 3 - ((this.useTime / 3) * speed)));
		  
	      
	      Arrow arrow = (Arrow) ItemsT.WOODEN_ARROW;
	      
	      if (!playerIn.abilities.isCreativeMode) {
		      for (int i = 0; i < inventory.main.length; i++) {
		    	  if (inventory.main[i].stack != null) {
		    		  if (inventory.main[i].stack.item instanceof Arrow) {
		    		      arrow = (Arrow) inventory.main[i].stack.item;
		    			  inventory.main[i].decrementStack(1);
		    			  
		    		      worldIn.playSound((PlayerEntity)null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
		    		      shoot = true;
		    		      ii = i;
		    		      break;
		    		  }
		    	  }
		    	
		      }
		      if (shoot == false) {
		    	  for (int i = 0; i < inventory.hotbar.length; i++) {
			    	  if (inventory.hotbar[i].stack != null) {
			    		  if (inventory.hotbar[i].stack.item instanceof Arrow) {
			    		      arrow = (Arrow) inventory.hotbar[i].stack.item;
			    			  inventory.hotbar[i].decrementStack(1);
			    		      worldIn.playSound((PlayerEntity)null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
			    		      shoot = true;
			    		      ii = i;
			    		      break;
			    		  }
			    	  }
			    	
			      }
		      }
	      } else {
		      worldIn.playSound((PlayerEntity)null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
		      shoot = false;
		      for (int i = 0; i < inventory.main.length; i++) {
		    	  if (inventory.main[i].stack != null) {
		    		  if (inventory.main[i].stack.item instanceof Arrow) {
		    			  arrow = (Arrow) inventory.main[i].stack.item;
		    			  shoot = true;
		    			  break;
		    		  }
		    	  }
		      }
		      
		      if (shoot == false) {
		    	  for (int i = 0; i < inventory.hotbar.length; i++) {
			    	  if (inventory.hotbar[i].stack != null) {
			    		  if (inventory.hotbar[i].stack.item instanceof Arrow) {
			    		      arrow = (Arrow) inventory.hotbar[i].stack.item;
			    		      break;
			    		  }
			    	  }
			    	
			      }
		      }
		      shoot = true;
	      }
	      
	      if (shoot) {
	    	  if (!worldIn.isRemote) {
//	 	         SnowballEntity snowballentity = new SnowballEntity(worldIn, playerIn);
//	 	         snowballentity.func_213884_b(new ItemStack(arrow));
//	 	         snowballentity.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, (velocity + arrow.velocity) * (1.0f/6.0f), 0.0F);
	 	         EntityArrowT arrowentity = new EntityArrowT(worldIn, playerIn);
	 	         arrowentity.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, (velocity + arrow.velocity) * (1.0f/6.0f), 0.0F);
	 	         arrowentity.setDamage(this.damage + this.damage * dmg + (random.nextDouble() * 0.25 * this.damage + this.damage * dmg));
	 	         if (random.nextInt(100) <= this.critChance + crit) {
	 	        	arrowentity.setDamage(this.damage + this.damage * dmg * 2);
	 	         }
	 	         arrowentity.setKnockbackStrength((int) (this.knockback + arrow.knockback) + (int) ((this.knockback + arrow.knockback) * kb));
	 	         arrowentity.arrow = arrow;
	 	         arrow.onArrowShoot(arrowentity);
	 	         worldIn.addEntity(arrowentity);
	 	         
	 	         //worldIn.addEntity(snowballentity);
	 	      }
	      }
	      

	      return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
	   }
}
