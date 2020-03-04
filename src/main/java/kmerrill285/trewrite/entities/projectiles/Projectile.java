package kmerrill285.trewrite.entities.projectiles;

import kmerrill285.trewrite.blocks.BlocksT;
import kmerrill285.trewrite.blocks.pots.Pot;
import kmerrill285.trewrite.entities.EntitiesT;
import kmerrill285.trewrite.items.ItemT;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public abstract class Projectile extends MobEntity
{

	public ItemT weapon;
	public int piercing;

	public double damage, knockback;
		
	public boolean hostile;
	public boolean breakOnGround;
	public boolean breakOnImpact;
	public boolean despawnDueToAge;
	public boolean dead;
	public Entity owner;
	
	public int maxAge = 20 * 5;
	
	public Projectile(World worldIn, EntityType<? extends Projectile> type) {
		super(type, worldIn);
	}

	public Projectile(World worldIn, double x, double y, double z) {
		super(EntitiesT.MAGIC_PROJECTILE, worldIn);
		this.setPosition(x, y, z);
	}

	public Projectile(EntityType<? extends Projectile> p_i50172_1_, World p_i50172_2_) {
		super(p_i50172_1_, p_i50172_2_);
	}
	
	public Projectile(World world) {
    	super(EntitiesT.MAGIC_PROJECTILE, world);
    }
	
	public abstract void init();
	public abstract void doAIStuff();
	
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
		this.doAIStuff();
		
		if (owner == null) {
			remove();
			return;
		}
		if (owner instanceof LivingEntity) {
			if (((LivingEntity)owner).getHealth() <= 0) {
				remove();
				return;
			}
		}
		if (dead) {
			remove();
			return;
		}
		
		if (world.getBlockState(getPosition()).getBlock() instanceof Pot) {
			world.setBlockState(getPosition(), BlocksT.AIR_BLOCK.getDefaultState());
		}
		
		float f = MathHelper.sqrt(func_213296_b(getMotion()));
		this.rotationYaw = (float)(MathHelper.atan2(getMotion().x, getMotion().z) * (double)(180F / (float)Math.PI));
	    this.rotationPitch = (float)(MathHelper.atan2(getMotion().y, (double)f) * (double)(180F / (float)Math.PI));
	    this.prevRotationYaw = this.rotationYaw;
	    this.prevRotationPitch = this.rotationPitch;
	    if (despawnDueToAge)
		if (this.ticksExisted > maxAge) {
			this.remove();
			return;
		}
		
	    if (!world.isRemote()) {
			   float rd = 1000.0f;
			      Vec3d vec3d = getPositionVec();
			      Vec3d vec3d1 = getMotion();
			      Vec3d vec3d2 = vec3d.add(vec3d1.x, vec3d1.y, vec3d1.z);
			     //   public static EntityRayTraceResult func_221269_a(World p_221269_0_, Entity p_221269_1_, Vec3d p_221269_2_, Vec3d p_221269_3_, AxisAlignedBB p_221269_4_, Predicate<Entity> p_221269_5_, double p_221269_6_) {
		
			      AxisAlignedBB bb = getBoundingBox().expand(vec3d1.scale(rd)).grow(1.0D, 1.0D, 1.0D);
		          EntityRayTraceResult result = ProjectileHelper.func_221269_a(world, this, vec3d, vec3d2, bb, (p_215312_0_) -> {
		             return !p_215312_0_.isSpectator() && p_215312_0_.canBeCollidedWith();
		          }, rd);

		          
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
			
			
			
			if (entity instanceof LivingEntity && !(entity instanceof Projectile)) {
				if (!hostile && entity instanceof PlayerEntity) return;
				if (hostile && !(entity instanceof PlayerEntity)) return;
				entity.attackEntityFrom(DamageSource.GENERIC, (float)damage);
				((LivingEntity)entity).knockBack(entity, (float)knockback, (float)getMotion().x % 2, (float)getMotion().z % 2);
				
				piercing--;
				if (piercing < 0) {
					if (!breakOnImpact) return;
					remove();
				}
			}
		}
		if (breakOnGround) {
			if (result.getType() == RayTraceResult.Type.BLOCK)
				remove();
		}
	}
	
	public void createProjectile(EntityType<? extends Projectile> projectile, BlockPos pos, World world, Entity shooter) {
		Projectile p = projectile.create(world, null, null, null, pos, SpawnReason.EVENT, false, false);
		p.init();
		p.owner = shooter;
		world.addEntity(p);
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