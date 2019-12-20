package kmerrill285.trewrite.entities.projectiles;

import java.util.Random;

import kmerrill285.trewrite.core.items.ItemStackT;
import kmerrill285.trewrite.entities.EntitiesT;
import kmerrill285.trewrite.entities.EntityItemT;
import kmerrill285.trewrite.items.ItemT;
import kmerrill285.trewrite.items.ThrowableItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.SnowballEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityThrowingT extends SnowballEntity
{

	public ItemT arrow;
	public float damage;
	public float knockback;
	
	public EntityThrowingT(World worldIn, LivingEntity shooter) {
		super(worldIn, shooter);
	}

	public EntityThrowingT(World worldIn, double x, double y, double z) {
		super(worldIn, x, y, z);
	}

	public EntityThrowingT(EntityType<? extends SnowballEntity> p_i50172_1_, World p_i50172_2_) {
		super(p_i50172_1_, p_i50172_2_);
	}
	
	public EntityThrowingT(World world) {
    	super(EntitiesT.THROWING, world);
    }
	
	public boolean hitGround = false;
	
	public void tick() {
		super.tick();
		if (arrow instanceof ThrowableItem) {
			((ThrowableItem)arrow).throwingTick(this);
		}
	}
	
	public void applyEntityCollision(Entity entityIn) {

	}
	
	public void remove() {
		super.remove();
		if (arrow != null) {
			ThrowableItem a = (ThrowableItem)arrow;
			if (rand.nextDouble() <= a.recovery) {
				EntityItemT.spawnItem(this.getEntityWorld(), this.getPosition(), new ItemStackT(arrow, 1));
			}
		}
		
	}
	
		/**
	    * Called when this EntityThrowable hits a block or entity.
	    */
	   protected void onImpact(RayTraceResult result) {
		   
		   
	      if (result.getType() == RayTraceResult.Type.ENTITY) {
	         Entity entity = ((EntityRayTraceResult)result).getEntity();
	         
	         entity.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), this.damage);
	         if (entity instanceof LivingEntity) {
	        	 LivingEntity l = (LivingEntity)entity;
	        	 
	        	 double d1 = l.posX - this.posX;

                 double d0;
                 for(d0 = l.posZ - this.posZ; d1 * d1 + d0 * d0 < 1.0E-4D; d0 = (Math.random() - Math.random()) * 0.01D) {
                    d1 = (Math.random() - Math.random()) * 0.01D;
                 }
	        	 
	        	 l.knockBack(this, knockback, d1, d0);
	         }
	      }

	      if (!this.world.isRemote) {
	         this.world.setEntityState(this, (byte)3);
	         this.remove();
	      }

	   }
	
   
}