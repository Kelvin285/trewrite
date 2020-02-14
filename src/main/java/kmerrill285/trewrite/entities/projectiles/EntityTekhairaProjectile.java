package kmerrill285.trewrite.entities.projectiles;

import kmerrill285.trewrite.blocks.BlocksT;
import kmerrill285.trewrite.blocks.pots.Pot;
import kmerrill285.trewrite.entities.EntitiesT;
import kmerrill285.trewrite.items.terraria.broadswords.Tekhaira;
import kmerrill285.trewrite.world.WorldStateHolder;
import net.minecraft.block.Blocks;
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
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityTekhairaProjectile extends MobEntity
{

	public Tekhaira weapon;
	public int piercing;

	public double damage, knockback;
	
	public LivingEntity shooter;
	
	public int maxAge = 20 * 5;
	
	public Vec3d VELOCITY = new Vec3d(0, 0, 0);
	
	public EntityTekhairaProjectile(World worldIn, LivingEntity shooter, EntityType<? extends EntityTekhairaProjectile> type) {
		super(type, worldIn);
		this.shooter = shooter;
	}

	public EntityTekhairaProjectile(World worldIn, double x, double y, double z) {
		super(EntitiesT.TEKHAIRA_PROJECTILE, worldIn);
		this.setPosition(x, y, z);
	}

	public EntityTekhairaProjectile(EntityType<? extends EntityTekhairaProjectile> p_i50172_1_, World p_i50172_2_) {
		super(p_i50172_1_, p_i50172_2_);
	}
	
	public EntityTekhairaProjectile(World world) {
    	super(EntitiesT.TEKHAIRA_PROJECTILE, world);
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
	public void tick() {
		super.tick();
		if (world.getBlockState(getPosition()).getBlock() instanceof Pot) {
			world.setBlockState(getPosition(), BlocksT.AIR_BLOCK.getDefaultState());
		}
		
		WorldStateHolder.get(world).setLight(getPosition(), 15, world.getDimension().getType());
		
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
		
		RayTraceResult raytraceresult = ProjectileHelper.func_221267_a(this, axisalignedbb, (p_213880_1_) -> {
	         return !p_213880_1_.isSpectator() && p_213880_1_.canBeCollidedWith();
	      }, RayTraceContext.BlockMode.OUTLINE, true);

	      if (raytraceresult.getType() != RayTraceResult.Type.MISS) {
	         if (raytraceresult.getType() == RayTraceResult.Type.BLOCK && this.world.getBlockState(((BlockRayTraceResult)raytraceresult).getPos()).getBlock() == Blocks.NETHER_PORTAL) {
	            this.setPortal(((BlockRayTraceResult)raytraceresult).getPos());
	         } else if (!net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult)){
	            this.onImpact(raytraceresult);
	         }
	      }
	      this.setMotion(VELOCITY);
	}
	public boolean attackEntityFrom(DamageSource source, float amount) {
		return false;
	}
	public boolean hasNoGravity() {
		return true;
	}
	
	protected void onImpact(RayTraceResult result) {
		if (result.getType() == RayTraceResult.Type.ENTITY)
		if (weapon != null) {
			
			Entity entity = ((EntityRayTraceResult)result).getEntity();
			
			
			
			if (entity instanceof LivingEntity && entity != shooter && !(entity instanceof EntityTekhairaProjectile)) {
				
				weapon.hit(this, (LivingEntity)entity);
				entity.attackEntityFrom(DamageSource.GENERIC, (float)damage);
				((LivingEntity)entity).knockBack(entity, (float)knockback, (float)getMotion().x % 2, (float)getMotion().z % 2);
				
				piercing--;
				if (piercing < 0) {
					remove();
				}
			} else {
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
	      VELOCITY = new Vec3d(this.getMotion().x + 0, this.getMotion().y + 0, this.getMotion().z + 0);
	   }
	
   
}