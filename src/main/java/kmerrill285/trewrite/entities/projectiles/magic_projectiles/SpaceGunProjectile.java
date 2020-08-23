package kmerrill285.trewrite.entities.projectiles.magic_projectiles;

import javax.vecmath.Vector3f;

import kmerrill285.trewrite.blocks.BlocksT;
import kmerrill285.trewrite.blocks.pots.Pot;
import kmerrill285.trewrite.entities.EntitiesT;
import kmerrill285.trewrite.entities.projectiles.EntityMagicProjectile;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.Pose;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceContext.BlockMode;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SpaceGunProjectile extends EntityMagicProjectile {

	public SpaceGunProjectile(World worldIn, LivingEntity shooter) {
		super(worldIn, shooter, EntitiesT.SPACE_GUN);
	}
	
	public SpaceGunProjectile(EntityType<SpaceGunProjectile> p_i50172_1_, World p_i50172_2_) {
		super(p_i50172_1_, p_i50172_2_);
	}
	
	public SpaceGunProjectile(World world) {
    	super(EntitiesT.SPACE_GUN, world);
    }
	public void tick() {
		
		
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
	    	  
	    	  {
	    		  BlockRayTraceResult result = this.world.rayTraceBlocks(new RayTraceContext(getPositionVec(), new Vec3d(getMotion().x, getMotion().y, getMotion().z).add(getPositionVec()), RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, this));

	    		  if (result != null) {
		    		  onImpact(result);
		    	  }
	    	  }
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
	      
		this.move(MoverType.SELF, this.getMotion());

	}
	
	
}
