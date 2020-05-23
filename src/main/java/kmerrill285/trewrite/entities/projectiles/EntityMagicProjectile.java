package kmerrill285.trewrite.entities.projectiles;

import kmerrill285.trewrite.blocks.BlocksT;
import kmerrill285.trewrite.blocks.pots.Pot;
import kmerrill285.trewrite.entities.EntitiesT;
import kmerrill285.trewrite.items.MagicWeapon;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext.BlockMode;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityMagicProjectile extends MobEntity
{

	public MagicWeapon weapon;
	public int piercing;

	public double damage, knockback;
	
	public LivingEntity shooter;
	
	public int maxAge = 20 * 5;
	
	public EntityMagicProjectile(World worldIn, LivingEntity shooter, EntityType<? extends EntityMagicProjectile> type) {
		super(type, worldIn);
		this.shooter = shooter;
	}

	public EntityMagicProjectile(World worldIn, double x, double y, double z) {
		super(EntitiesT.MAGIC_PROJECTILE, worldIn);
		this.setPosition(x, y, z);
	}

	public EntityMagicProjectile(EntityType<? extends EntityMagicProjectile> p_i50172_1_, World p_i50172_2_) {
		super(p_i50172_1_, p_i50172_2_);
	}
	
	public EntityMagicProjectile(World world) {
    	super(EntitiesT.MAGIC_PROJECTILE, world);
    }
	
	public boolean hitGround = false;
	
	public void setDamage(double damage) {
		this.damage = damage;
	}
	
	public void setKnockback(double knockback) {
		this.knockback = knockback;
	}
	
	public boolean isInvulnerable() {
		return true;
	}
	@Override
	public void collideWithEntity(Entity e) {
	}
	
	@Override
	protected void collideWithNearbyEntities() {
	}
	
	public AxisAlignedBB getCollisionBoundingBox() {
	      return null;
	   }
	
	public boolean hasNoGravity() {
		return true;
	}
	
	public void tick() {
		super.tick();
		if (world.getBlockState(getPosition()).getBlock() instanceof Pot) {
			world.setBlockState(getPosition(), BlocksT.AIR_BLOCK.getDefaultState());
		}
		
		float f = MathHelper.sqrt(func_213296_b(getMotion()));
		this.rotationYaw = (float)(MathHelper.atan2(getMotion().x, getMotion().z) * (double)(180F / (float)Math.PI));
	    this.rotationPitch = (float)(MathHelper.atan2(getMotion().y, (double)f) * (double)(180F / (float)Math.PI));
	    this.prevRotationYaw = this.rotationYaw;
	    this.prevRotationPitch = this.rotationPitch;
		if (this.ticksExisted > maxAge) {
			this.remove();
			return;
		}
		
		
		
		if (weapon != null) {
			
			weapon.tick(this);
		}
		
	    AxisAlignedBB axisalignedbb = this.getBoundingBox().expand(this.getMotion()).grow(1.0D);
	      
	      if (!world.isRemote()) {
			   float rd = 1000.0f;
			      Vec3d vec3d = getPositionVec();
			      Vec3d vec3d1 = getMotion();
			      Vec3d vec3d2 = vec3d.add(vec3d1.x, vec3d1.y, vec3d1.z);
			     //   public static EntityRayTraceResult func_221269_a(World p_221269_0_, Entity p_221269_1_, Vec3d p_221269_2_, Vec3d p_221269_3_, AxisAlignedBB p_221269_4_, Predicate<Entity> p_221269_5_, double p_221269_6_) {
		
			      AxisAlignedBB bb = getBoundingBox().expand(vec3d1.scale(rd)).grow(1.0D, 1.0D, 1.0D);
		          RayTraceResult result = ProjectileHelper.func_221269_a(world, this, vec3d, vec3d2, bb, (p_215312_0_) -> {
		             return !p_215312_0_.isSpectator() && p_215312_0_.canBeCollidedWith();
		          }, rd);
		          
		          boolean blockHit = false;
		          if (result == null) {
		        	  blockHit = true;
		          } else {
		        	  if (result.getType() == RayTraceResult.Type.MISS) {
		        		  blockHit = true;
		        	  }
		          }
		          
		          if (blockHit) {
		        	  AxisAlignedBB box = new AxisAlignedBB(posX - getWidth() * 0.1f + getMotion().getX(), posY - getHeight() * 0.1f + getMotion().getY(), posZ - getWidth() * 0.1f + getMotion().getZ(), posX + getWidth() * 1.1f + getMotion().getX(), posY + getHeight() * 1.1f + getMotion().getY(), posZ + getWidth() + 1.1f + getMotion().getZ());
		        	  result = ProjectileHelper.func_221267_a(this, box, (e) -> {return false;}, BlockMode.COLLIDER, true);
		          }
		          if (result != null) {
		        	  onImpact(result);
		          }
		   }
	}
	public boolean attackEntityFrom(DamageSource source, float amount) {
		return false;
	}
	protected void onImpact(RayTraceResult result) {
		if (result.getType() == RayTraceResult.Type.ENTITY)
		if (weapon != null) {
			
			Entity entity = ((EntityRayTraceResult)result).getEntity();
			
			
			
			if (entity instanceof LivingEntity && entity != shooter && !(entity instanceof EntityMagicProjectile)) {
				
				weapon.hit(this, (LivingEntity)entity);
				entity.attackEntityFrom(DamageSource.GENERIC, (float)damage);
				((LivingEntity)entity).knockBack(entity, (float)knockback, -(float)getMotion().x / 2.0f, -(float)getMotion().z / 2.0f);
				
				piercing--;
				if (piercing < 0) {
					remove();
				}
			}
		}
		if (result.getType() == RayTraceResult.Type.BLOCK) {
			if (world.getBlockState(((BlockRayTraceResult)result).getPos()).getMaterial().blocksMovement())
			if (this.noClip == false) {
				remove();
			}
		}
	}

	public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
	      Vec3d vec3d = (new Vec3d(x, y, z)).normalize().add(this.rand.nextGaussian() * (double)0.0075F * (double)inaccuracy, this.rand.nextGaussian() * (double)0.0075F * (double)inaccuracy, this.rand.nextGaussian() * (double)0.0075F * (double)inaccuracy).scale((double)velocity);
	      this.setMotion(vec3d);
	      float f = MathHelper.sqrt(func_213296_b(vec3d));
	      this.rotationYaw = (float)(MathHelper.atan2(vec3d.x, vec3d.z) * (double)(180F / (float)Math.PI));
	      this.rotationPitch = (float)(MathHelper.atan2(vec3d.y, (double)f) * (double)(180F / (float)Math.PI));
	      this.prevRotationYaw = this.rotationYaw;
	      this.prevRotationPitch = this.rotationPitch;
	   }
	
	public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
	      float f = -MathHelper.sin(rotationYawIn * ((float)Math.PI / 180F)) * MathHelper.cos(rotationPitchIn * ((float)Math.PI / 180F));
	      float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * ((float)Math.PI / 180F));
	      float f2 = MathHelper.cos(rotationYawIn * ((float)Math.PI / 180F)) * MathHelper.cos(rotationPitchIn * ((float)Math.PI / 180F));
	      this.shoot((double)f, (double)f1, (double)f2, velocity, inaccuracy);
	      Vec3d vec3d = entityThrower.getMotion();
	      this.setMotion(this.getMotion().add(vec3d.x, entityThrower.onGround ? 0.0D : vec3d.y, vec3d.z));
	   }
	
   
}