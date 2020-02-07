package kmerrill285.trewrite.entities.projectiles;

import kmerrill285.trewrite.core.items.ItemStackT;
import kmerrill285.trewrite.entities.EntitiesT;
import kmerrill285.trewrite.entities.EntityItemT;
import kmerrill285.trewrite.items.Arrow;
import kmerrill285.trewrite.items.Bullet;
import kmerrill285.trewrite.items.ItemsT;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.world.World;

public class EntityBullet extends ArrowEntity
{

	public Bullet bullet;
	public int piercing;
	
	public EntityBullet(World worldIn, LivingEntity shooter) {
		super(worldIn, shooter);
	}

	public EntityBullet(World worldIn, double x, double y, double z) {
		super(worldIn, x, y, z);
	}

	public EntityBullet(EntityType<? extends ArrowEntity> p_i50172_1_, World p_i50172_2_) {
		super(p_i50172_1_, p_i50172_2_);
	}
	
	public EntityBullet(World world) {
    	super(EntitiesT.ARROW, world);
    }
	
	public boolean hitGround = false;
	
	public void tick() {
		super.tick();
		
		if (this.hasNoGravity()) {
			if (this.ticksExisted > 20 * 5) {
				this.remove();
			}
		}
		
		if (this.timeInGround > 0) {
			this.remove();
			if (bullet != null) {
				bullet.onBulletHit(this, null);
			}
		}
		if (bullet != null) {
			
			bullet.bulletTick(this);
			this.setNoGravity(true);
		}
	}
	
	public void arrowHit(LivingEntity hit) {
		super.arrowHit(hit);
		if (bullet != null) {
			bullet.onBulletHit(this, hit);
		}
	}
	
   
}