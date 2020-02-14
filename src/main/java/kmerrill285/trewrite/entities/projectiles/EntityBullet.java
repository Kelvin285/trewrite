package kmerrill285.trewrite.entities.projectiles;

import kmerrill285.trewrite.blocks.BlocksT;
import kmerrill285.trewrite.blocks.pots.Pot;
import kmerrill285.trewrite.entities.EntitiesT;
import kmerrill285.trewrite.items.Bullet;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.SnowballEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
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
	}
	
	protected void onImpact(RayTraceResult result) {
		super.onImpact(result);
		if (result.getType() == RayTraceResult.Type.ENTITY)
		if (bullet != null) {
			Entity entity = ((EntityRayTraceResult)result).getEntity();
			if (entity instanceof LivingEntity) {
				bullet.onBulletHit(this, (LivingEntity)entity);
				entity.attackEntityFrom(DamageSource.GENERIC, (float)damage);
				((LivingEntity)entity).knockBack(entity, (float)knockback, (float)getMotion().x % 2, (float)getMotion().z % 2);
			}
		}
	}
	
   
}