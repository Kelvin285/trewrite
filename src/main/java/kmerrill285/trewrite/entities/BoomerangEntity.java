package kmerrill285.trewrite.entities;

import kmerrill285.trewrite.blocks.BlocksT;
import kmerrill285.trewrite.blocks.pots.Pot;
import kmerrill285.trewrite.items.Boomerang;
import kmerrill285.trewrite.items.ItemT;
import kmerrill285.trewrite.util.Conversions;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.NetworkHooks;

public class BoomerangEntity extends MobEntity implements IEntityAdditionalSpawnData {

	public Entity owner;
	public String ownername;
	public float damage;
	public float knockback;
	public boolean RETURN = false;
	public ItemT item;
	
	public boolean pierce = false;
	
	public BoomerangEntity(EntityType<? extends BoomerangEntity> type, World worldIn) {
		super(type, worldIn);
	}
	
	public BoomerangEntity(World worldIn) {
		super(EntitiesT.FLAIL, worldIn);
	}
		
	public BoomerangEntity(World worldIn, double x, double y, double z) {
		super(EntitiesT.FLAIL, worldIn);
	}

	/**
     * Checks if the entity is in range to render.
     */
    @OnlyIn(Dist.CLIENT)
    public boolean isInRangeToRenderDist(double distance) {
       double d0 = 64.0D * getRenderDistanceWeight();
       return distance < d0 * d0;
    }
	
	public EntitySize getSize(Pose pose) {
		return EntitySize.fixed(0.5f, 0.5f);
	}
	
	public boolean canUpdate() {
		return true;
	}
	
	public void canUpdate(boolean value) {
		
	}
	
	private int age = 0;
	public float hoverStart = 0.1f;
	
	public boolean dead = false;
	public int getAge() {
		return age;
	}
	public void setAge(int i) {
		this.age = i;
	}
	public boolean canDespawn() {
		return true;
	}
	public boolean isInvulnerable() {
		return true;
	}
	
	public void playHurtSound(DamageSource source) {
		
	}
	
	public boolean attackEntityFrom(DamageSource source, float amount) {
		return false;
	}
	
	
	private double velX, velY, velZ;
	public boolean grabbed = false;
	@Override
	public void tick() {
		if (this.getHealth() <= 0) {
			this.age = 0;
			remove();
		}
		this.noClip = RETURN;
		if (ownername != null && world.isRemote) {
			if (owner == null) {
				for (int i = 0; i < world.getPlayers().size(); i++) {
					if (world.getPlayers().get(i).getScoreboardName().contentEquals(ownername)) {
						owner = world.getPlayers().get(i);
						break;
					}
				}
			}
		}
		
		if (owner != null) {
			ownername = owner.getScoreboardName();
		}
		
		
		
		if (world.getBlockState(getPosition()).getBlock() instanceof Pot) {
			world.setBlockState(getPosition(), BlocksT.AIR_BLOCK.getDefaultState());
		}
		
		
		
		super.tick();
		
		if (world.isRemote) return;
		
		
		if (this.owner == null) {
			this.age = 0;
			remove();
			return;
		}
		
		if (this.owner instanceof LivingEntity) {
			LivingEntity e = (LivingEntity)owner;
			if (e.getHealth() <= 0) {
				this.age = 0;
				remove();
				return;
			}
		}
		
		if (RETURN) {

			float speed = 1f;
			float acceleration = 0.08f;
			int dirX = posX < owner.posX ? 1 : -1, dirY = posY < (owner.posY + 1) ? 1 : -1, dirZ = posZ < owner.posZ ? 1 : -1;

			
			if (getPositionVec().distanceTo(owner.getPositionVec()) > 1.5) {
				
				velX += acceleration*dirX;
				velY += acceleration*dirY;
				velZ += acceleration*dirZ;
				
				if (Math.abs(velX) >= speed * 0.5) {
					velX -= acceleration*dirX;
				}
				
				if (Math.abs(velZ) >= speed * 0.5) {
					velZ -= acceleration*dirZ;
				}
				
				if (Math.abs(velY) >= speed * 0.5) {
					velY -= acceleration*dirY;
				}
				
				setMotion(0, 0, 0);
				posX += velX;
				posY += velY;
				posZ += velZ;
				this.setPosition(posX, posY, posZ);
				this.noClip = true;
			} else {
				this.age = 0;
				remove();
				return;
			}
			
		}
		
		if (getPositionVec().distanceTo(owner.getPositionVec().add(0, 1, 0)) > 10) {
			posX = lerp(posX, owner.posX, 0.2);
			posY = lerp(posY, owner.posY + 1, 0.2);
			posZ = lerp(posZ, owner.posZ, 0.2);
			setPosition(posX, posY, posZ);
		}
		
		if (getPositionVec().distanceTo(owner.getPositionVec().add(0, 2.5, 0)) > 8) {
			RETURN = true;
		}
		
		if (!world.isRemote()) {
			   float rd = 1000.0f;
			      Vec3d vec3d = getPositionVec();
			      Vec3d vec3d1 = getMotion();
			      Vec3d vec3d2 = vec3d.add(vec3d1.x, vec3d1.y, vec3d1.z);
		
			      AxisAlignedBB bb = getBoundingBox().expand(vec3d1.scale(rd)).grow(1.0D, 1.0D, 1.0D);
		          EntityRayTraceResult result = ProjectileHelper.func_221269_a(world, this, vec3d, vec3d2, bb, (p_215312_0_) -> {
		             return !p_215312_0_.isSpectator() && p_215312_0_.canBeCollidedWith();
		          }, rd);

		          
		          if (result != null) {
		        	  onImpact(result);
		          }
		   }
	}

	protected void hitEntity(LivingEntity entity) {
		
	}
	
	public boolean hasNoGravity() {
		return true;
	}
	
	protected void onImpact(RayTraceResult result) {
		if (result.getType() == RayTraceResult.Type.ENTITY) {
			Entity entity = ((EntityRayTraceResult)result).getEntity();
			if (entity != owner) {
				entity.attackEntityFrom(DamageSource.GENERIC, damage);
				if (item instanceof Boomerang) {
					((Boomerang)item).onHitEntity(entity);
				}
				if (entity instanceof LivingEntity) {
					hitEntity((LivingEntity)entity);
					((LivingEntity)entity).knockBack(entity, Conversions.feetToMeters * knockback, Conversions.feetToMeters * -this.getMotion().x, Conversions.feetToMeters * -this.getMotion().z);
				}
			}
			if (pierce == false) RETURN = true;
		} else {
			RETURN = true;
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
	
	public double lerp(double a, double b, double f) 
	{
	    return (a * (1.0 - f)) + (b * f);
	}
	
	public static BoomerangEntity spawnOrb(World worldIn, BlockPos pos, Entity owner, float damage, float knockback, EntityType<? extends BoomerangEntity> flail) {
		BoomerangEntity orb = flail.create(worldIn, null, null, null, pos, SpawnReason.EVENT, false, false);
		orb.owner = owner;
		orb.damage = damage;
		orb.knockback = knockback;
		worldIn.addEntity(orb);
		return orb;
	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	public void writeSpawnData(PacketBuffer buffer) {
		buffer.writeString(ownername);
	}

	@Override
	public void readSpawnData(PacketBuffer additionalData) {
		ownername = additionalData.readString().trim();
	}

}
