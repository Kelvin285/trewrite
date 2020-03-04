package kmerrill285.trewrite.entities.projectiles;

import kmerrill285.trewrite.blocks.BlocksT;
import kmerrill285.trewrite.blocks.pots.Pot;
import kmerrill285.trewrite.entities.EntitiesT;
import kmerrill285.trewrite.items.Bullet;
import kmerrill285.trewrite.util.Conversions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.entity.projectile.SnowballEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityBullet extends SnowballEntity
{

	public Bullet bullet;
	public int piercing;

	public double damage, knockback;
	
	
	public EntityBullet(World worldIn, LivingEntity shooter) {
		super(worldIn, shooter);
	}

	public EntityBullet(World worldIn, double x, double y, double z) {
		super(worldIn, x, y, z);
	}

	public EntityBullet(EntityType<? extends EntityBullet> p_i50172_1_, World p_i50172_2_) {
		super(p_i50172_1_, p_i50172_2_);
	}
	
	public EntityBullet(World world) {
    	super(EntitiesT.BULLET, world);
    }
	
	public boolean hitGround = false;
	
	public void setDamage(double damage) {
		this.damage = damage;
	}
	
	public void setKnockback(double knockback) {
		this.knockback = knockback;
	}
	public boolean hasNoGravity() {
		return true;
	}
	public void tick() {
		super.tick();
		if (world.getBlockState(getPosition()).getBlock() instanceof Pot) {
			world.setBlockState(getPosition(), BlocksT.AIR_BLOCK.getDefaultState());
		}
		if (this.hasNoGravity()) {
			if (this.ticksExisted > 20 * 5) {
				this.remove();
			}
		}
		
		if (bullet != null) {
			
			bullet.bulletTick(this);
			this.setNoGravity(true);
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
		        	  this.onImpact(result);
		          }
		   }
	}
	
	protected void onImpact(RayTraceResult result) {
		super.onImpact(result);
		if (result.getType() == RayTraceResult.Type.ENTITY)
		if (bullet != null) {
			Entity entity = ((EntityRayTraceResult)result).getEntity();
			if (entity instanceof LivingEntity) {
				bullet.onBulletHit(this, (LivingEntity)entity);
				entity.attackEntityFrom(DamageSource.GENERIC, (float)damage);
				((LivingEntity)entity).knockBack(entity, (float)knockback * 0.01f, -(float)getMotion().normalize().x, -(float)getMotion().normalize().z);
				if (this.piercing > 0) {
					this.piercing--;
				} else {
					remove();
				}
			}
		}
	}
	
   
}