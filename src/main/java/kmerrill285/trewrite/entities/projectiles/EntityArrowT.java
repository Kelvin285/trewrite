package kmerrill285.trewrite.entities.projectiles;

import kmerrill285.trewrite.blocks.BlocksT;
import kmerrill285.trewrite.blocks.pots.Pot;
import kmerrill285.trewrite.core.items.ItemStackT;
import kmerrill285.trewrite.entities.EntitiesT;
import kmerrill285.trewrite.entities.EntityItemT;
import kmerrill285.trewrite.items.Arrow;
import kmerrill285.trewrite.items.ItemT;
import kmerrill285.trewrite.items.ItemsT;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityArrowT extends ArrowEntity
{

	public ItemT arrow;
	public int piercing;
	
	public EntityArrowT(World worldIn, LivingEntity shooter) {
		super(worldIn, shooter);
	}

	public EntityArrowT(World worldIn, double x, double y, double z) {
		super(worldIn, x, y, z);
	}

	public EntityArrowT(EntityType<? extends ArrowEntity> p_i50172_1_, World p_i50172_2_) {
		super(p_i50172_1_, p_i50172_2_);
	}
	
	public EntityArrowT(World world) {
    	super(EntitiesT.ARROW, world);
    }
	
	public boolean hitGround = false;
	
	public void tick() {
		super.tick();
		if (world.getBlockState(getPosition()).getBlock() instanceof Pot) {
			world.setBlockState(getPosition(), BlocksT.AIR_BLOCK.getDefaultState());
		}
		if (this.hasNoGravity()) {
			if (this.ticksExisted > 20 * 5) {
				for (int i = 0; i < 10; i++)
		    	 world.addParticle(ParticleTypes.END_ROD, posX, posY, posZ, 0, 0, 0);

				this.remove();
			}
		}
		
		if (this.timeInGround > 0) {
			this.remove();
			if (arrow != null) {
				((Arrow)arrow).onArrowHit(this, null);
			}
		}
		if (arrow != null) {
			
			if (arrow instanceof Arrow) {
				((Arrow)arrow).arrowTick(this);
				Arrow a = (Arrow)arrow;
				if (a.gravity == false) {
					this.setNoGravity(true);
				}
			}
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
		        	  if (result.getEntity() instanceof LivingEntity) {
		        		  arrowHit((LivingEntity)result.getEntity());
		        	  }
		          }
		   }
	}
	
	public void arrowHit(LivingEntity hit) {
		if (hit.isInvulnerable()) {
			this.remove();
			return;
		}
		super.arrowHit(hit);
		if (arrow != null) {
			((Arrow)arrow).onArrowHit(this, hit);
		}
	}
	
	public void remove() {
		super.remove();
		if (arrow != null) {
			Arrow a = (Arrow)arrow;
			if (rand.nextDouble() <= a.recovery) {
				if (rand.nextDouble() <= a.dropRegular) {
					EntityItemT.spawnItem(this.getEntityWorld(), this.getPosition(), new ItemStackT(ItemsT.WOODEN_ARROW, 1));
				} else {
					EntityItemT.spawnItem(this.getEntityWorld(), this.getPosition(), new ItemStackT(arrow, 1));
				}
			}
		}
		
	}
	
   
}