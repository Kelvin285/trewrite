package kmerrill285.trewrite.items.terraria.magic_weapons;

import kmerrill285.trewrite.blocks.BlocksT;
import kmerrill285.trewrite.client.gui.inventory.InventorySlot;
import kmerrill285.trewrite.client.gui.inventory.InventoryTerraria;
import kmerrill285.trewrite.client.gui.inventory.container.ContainerTerrariaInventory;
import kmerrill285.trewrite.crafting.CraftingRecipe;
import kmerrill285.trewrite.crafting.Recipes;
import kmerrill285.trewrite.entities.EntitiesT;
import kmerrill285.trewrite.entities.projectiles.EntityMagicProjectile;
import kmerrill285.trewrite.entities.projectiles.magic_projectiles.SpaceGunProjectile;
import kmerrill285.trewrite.events.ScoreboardEvents;
import kmerrill285.trewrite.events.WorldEvents;
import kmerrill285.trewrite.items.ItemStackT;
import kmerrill285.trewrite.items.ItemT;
import kmerrill285.trewrite.items.ItemsT;
import kmerrill285.trewrite.items.MagicWeapon;
import kmerrill285.trewrite.items.modifiers.ItemModifier;
import kmerrill285.trewrite.world.WorldStateHolder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.Score;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SpaceGun extends MagicWeapon {

	public SpaceGun() {
		super(new Properties().group(ItemGroup.COMBAT), "space_gun", 10);
		this.mana = 7;
		this.knockback = 0.75f;
		this.damage = 19;
		this.velocity = 10;
		this.useTime = 17;
		this.setBuySell(4000);
		this.setMaxStack(1);
		rotX = 45;
		offsY = -scale * -0.05;
		offsZ = -scale * 0.20;
		animation = ItemT.GUN_ANIMATION;
	}

	public void setCraftingRecipes() {
		Recipes.addRecipe(new CraftingRecipe(new ItemStackT(ItemsT.SPACE_GUN, 1), BlocksT.IRON_ANVIL, new ItemStackT(ItemsT.METEORITE_BAR, 30), new ItemStackT(ItemsT.FALLEN_STAR, 2)));
	}
	
	
	@Override
	public void shoot(EntityMagicProjectile projectile) {
		if (projectile.world.isRemote) return;
		EntityMagicProjectile last = projectile;
		last.piercing = 2;
		last.maxAge = 20 * 3;
		last.setNoGravity(true);
		
	}

	@Override
	public void tick(EntityMagicProjectile projectile) {
		projectile.rotationPitch = 0;
		projectile.prevRotationPitch = 0;
		if (projectile.world.isRemote) return;
		projectile.setNoGravity(true);
		
		
		WorldStateHolder.get(projectile.world).setLight(projectile.getPosition(), 15, projectile.world.getDimension().getType());
	}

	@Override
	public void hit(EntityMagicProjectile projectile, LivingEntity entity) {
	}

	
	public void onLeftClick(Entity entity, BlockPos pos, PlayerEntity player, World worldIn, Hand handIn) {
		super.onLeftClick(entity, pos, player, worldIn, handIn);
		this.onItemRightClick(worldIn, player, handIn);
	}
	
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
	    ItemStack itemstack = playerIn.getHeldItem(handIn);

	      Score mana = ScoreboardEvents.getScore(worldIn.getScoreboard(), playerIn, ScoreboardEvents.MANA);
		  if (mana == null) return new ActionResult<>(ActionResultType.FAIL, itemstack);
		  
		
	      
	      InventoryTerraria inventory = null;
	      if (!worldIn.isRemote) {
	    	  inventory = WorldEvents.getOrLoadInventory(playerIn);
	      } else 
	      {
	    	  inventory = ContainerTerrariaInventory.inventory;
	      }
	      
	      
	      InventorySlot bowSlot = inventory.hotbar[inventory.hotbarSelected];
	      float velocity = this.velocity;
	      float kb = 1.0f;
	      float speed = 1.0f;
	      float dmg = 1.0f;
	      float crit = 1.0f;
	      float manaUse = this.mana;
	      
	      if (bowSlot == null) {
	    	  return new ActionResult<>(ActionResultType.FAIL, itemstack);
	      }
	      
	      if (bowSlot.stack == null) {
	    	  return new ActionResult<>(ActionResultType.FAIL, itemstack);
	      }
	      if (!(bowSlot.stack.item == this)) {
	    	  return new ActionResult<>(ActionResultType.FAIL, itemstack);
	      }
	      if (ItemModifier.getModifier(bowSlot.stack.modifier) == null) {
	    	  bowSlot.stack.reforge(bowSlot.stack.item);
	    	  return new ActionResult<>(ActionResultType.FAIL, itemstack);
	      }
	      
	      
	      
	      if (bowSlot.stack != null) {
	    	  
	    	  if (bowSlot.stack.item instanceof MagicWeapon) {
	    		  velocity += velocity * (ItemModifier.getModifier(bowSlot.stack.modifier).velocity / 100.0);
	    	  }
	    	  kb = (float) (ItemModifier.getModifier(bowSlot.stack.modifier).knockback / 100.0f);
	    	  crit = (float) (ItemModifier.getModifier(bowSlot.stack.modifier).crit);
	    	  speed = (float) (ItemModifier.getModifier(bowSlot.stack.modifier).speed / 100.0f);
	    	  dmg = (float) (ItemModifier.getModifier(bowSlot.stack.modifier).damage / 100.0f);
	    	  manaUse += manaUse * (float) (ItemModifier.getModifier(bowSlot.stack.modifier).manaCost / 100.0f);
	      }
	      
		  if (ScoreboardEvents.getScore(playerIn.getWorldScoreboard(), playerIn, ScoreboardEvents.MAGIC_POWER).getScorePoints() > 0) {
			  dmg += 0.2;
		  }
		  
		  int meteorSet = 0;
		  if (inventory != null) {
	    	  for (InventorySlot slot : inventory.armor) { 
	    		  if (slot != null) {
	    			  if (slot.stack != null) {
	    				  if (slot.stack.item == ItemsT.METEOR_HELMET || slot.stack.item == ItemsT.METEOR_SUIT || slot.stack.item == ItemsT.METEOR_LEGGINGS) {
	    					  dmg *= 1.07f;
	    					  meteorSet++;
	    				  }
	    				  
	    			  }
	    		  }
	    	  }
	      }
		  
		  if (meteorSet == 3) manaUse = 0;
	      
	      if (mana.getScorePoints() - manaUse < 0) return new ActionResult<>(ActionResultType.FAIL, itemstack);
		  mana.setScorePoints(mana.getScorePoints() - (int)manaUse);
		  if (mana.getScorePoints() < 0) mana.setScorePoints(0);
		  ScoreboardEvents.getScore(playerIn.getWorldScoreboard(), playerIn, ScoreboardEvents.MANA_TIMER).setScorePoints(0);
	      
		  dmg -= 0.05 * ScoreboardEvents.getScore(playerIn.getWorldScoreboard(), playerIn, ScoreboardEvents.MANA_SICKNESS_EFFECT).getScorePoints();
	      
		  
		  
		  playerIn.getCooldownTracker().setCooldown(this, (int) ((this.useTime - this.useTime * speed) * (30.0 / 60.0)));
		  
	      
	     
  	  if (!worldIn.isRemote) {
			double vel = (velocity) * (1.0f/6.0f);
  		 
  		 	SpaceGunProjectile projectile = EntitiesT.SPACE_GUN.create(worldIn, null, null, playerIn, playerIn.getPosition().up(), SpawnReason.EVENT, false, false);
	         projectile.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, (float)vel, 0.0F);
	         
	         projectile.posX += playerIn.getForward().x;
	         projectile.posY += playerIn.getForward().y;
	         projectile.posZ += playerIn.getForward().z;
//	         projectile.setPosition(projectile.posX + projectile.getMotion().x, projectile.posY + projectile.getMotion().y, projectile.posZ + projectile.getMotion().z);
	         double damage = (this.damage + this.damage * dmg) * (1.0 + random.nextDouble() * 0.05f);
	         projectile.setDamage(damage);
	         if (random.nextInt(100) <= this.critChance + crit) {
	        	projectile.setDamage(this.damage + this.damage * dmg * 2);
	         }
	         projectile.setKnockback((int) (this.knockback) + (int) ((this.knockback) * kb));
	         projectile.shooter = playerIn;
	         shoot(projectile);
	         
	         projectile.weapon = this;
	         worldIn.addEntity(projectile);
	 	     
	      }
	      

	      return new ActionResult<>(ActionResultType.FAIL, itemstack);
	   }
}
