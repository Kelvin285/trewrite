package kmerrill285.trewrite.items;


import kmerrill285.trewrite.core.inventory.InventorySlot;
import kmerrill285.trewrite.core.inventory.InventoryTerraria;
import kmerrill285.trewrite.core.inventory.container.ContainerTerrariaInventory;
import kmerrill285.trewrite.entities.projectiles.EntityThrowingT;
import kmerrill285.trewrite.events.WorldEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class ThrowableItem extends ItemT {
	public double recovery = 1.0f;

	public ThrowableItem(Properties properties, String name, int damage) {
		super(properties, name);
		this.damage = damage;
		this.throwing = true;

		
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
	      
	      
		  playerIn.getCooldownTracker().setCooldown(this, (int) (this.useTime / 3));
		  
		  if (this.velocity <= 0) this.velocity = 9;
		  
	      if (inventory.hotbar[inventory.hotbarSelected].stack != null) {
	    	  if (inventory.hotbar[inventory.hotbarSelected].stack.item instanceof ThrowableItem) {
	    		  ThrowableItem thrown = (ThrowableItem) inventory.hotbar[inventory.hotbarSelected].stack.item;
	    	      if (!playerIn.abilities.isCreativeMode) {
	    	    	  inventory.hotbar[inventory.hotbarSelected].decrementStack(1);
	    	      }
	    	      worldIn.playSound((PlayerEntity)null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
	    	      
	    	      
	        	  if (!worldIn.isRemote) {
	        		  EntityThrowingT thrownentity = new EntityThrowingT(worldIn, playerIn);
	     	          thrownentity.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, (velocity) * (1.0f/6.0f), 0.0F);
	     	          thrownentity.damage = damage;
	     	          thrownentity.knockback = knockback;
	     	          thrownentity.func_213884_b(new ItemStack(this));
	     	          thrownentity.arrow = this;
	     	          worldIn.addEntity(thrownentity);
	     	      }
	    	  }
	      }
	      
	      
	      

	      return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
	   }
	 
	 public void throwingTick(EntityThrowingT entity) {
		 
	 }
}
