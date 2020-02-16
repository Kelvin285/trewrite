package kmerrill285.trewrite.entities;

import kmerrill285.trewrite.blocks.BlocksT;
import kmerrill285.trewrite.blocks.pots.Pot;
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

public class EntityFlail extends MobEntity implements IEntityAdditionalSpawnData {

	public Entity owner;
	public String ownername;
	public float damage;
	public float knockback;
	public EntityFlail(EntityType<? extends EntityFlail> type, World worldIn) {
		super(type, worldIn);
	}
	
	public EntityFlail(World worldIn) {
		super(EntitiesT.FLAIL, worldIn);
	}
		
	public EntityFlail(World worldIn, double x, double y, double z) {
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
	
	
	
	public boolean grabbed = false;
	@Override
	
	public void tick() {
		
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
		age++;
		if (age > 5) {
			if (owner != null) {
				
				
				if (getPositionVec().distanceTo(owner.getPositionVec().add(0, 1, 0)) > 1) {
					this.noClip = true;
					
					posX = lerp(posX, owner.posX, 0.2);
					posY = lerp(posY, owner.posY + 1, 0.2);
					posZ = lerp(posZ, owner.posZ, 0.2);
					setPosition(posX, posY, posZ);
					this.setMotion(0, 0, 0);
				} else {
					this.age = 0;
					remove();
					return;
				}
				
				
			} else {
				this.age = 0;
				remove();
				return;
			}
			
		} else {
			this.noClip = false;
		
		
		
		if (this.owner instanceof LivingEntity) {
			LivingEntity e = (LivingEntity)owner;
			if (e.getHealth() <= 0) {
				this.age = 0;
				remove();
				return;
			}
		}
		
		float speed = 5f;
		float acceleration = 0.05f;
		int dirX = posX < owner.posX ? 1 : -1, dirY = posY < (owner.posY + 1) ? 1 : -1, dirZ = posZ < owner.posZ ? 1 : -1;

		
		
			
		if (getPositionVec().distanceTo(owner.getPositionVec().add(0, 2.5, 0)) > 3) {

			setMotion(getMotion().add(acceleration * dirX, acceleration * dirY, acceleration * dirZ));
			
//			if (Math.abs(getMotion().x) >= speed) {
//				getMotion().add(-acceleration * dirX, acceleration * dirY, 0);
//			}
//			if (Math.abs(getMotion().y) >= speed) {
//				getMotion().add(acceleration * dirX, -acceleration * dirY, acceleration * dirZ);
//			}
//			if (Math.abs(getMotion().z) >= speed) {
//				getMotion().add(0, acceleration * dirY, -acceleration * dirZ);
//			}
			
			
		}
		
		if (getPositionVec().distanceTo(owner.getPositionVec().add(0, 1, 0)) > 5) {
			posX = lerp(posX, owner.posX, 0.2);
			posY = lerp(posY, owner.posY + 1, 0.2);
			posZ = lerp(posZ, owner.posZ, 0.2);
		}
		
				
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
	   this.setRotation(0, 0);

	}

	protected void hitEntity(LivingEntity entity) {
		
	}
	
	protected void onImpact(RayTraceResult result) {
		if (result.getType() == RayTraceResult.Type.ENTITY) {
			Entity entity = ((EntityRayTraceResult)result).getEntity();
			if (entity != owner) {
				entity.attackEntityFrom(DamageSource.GENERIC, damage);
				if (entity instanceof LivingEntity) {
					hitEntity((LivingEntity)entity);
					((LivingEntity)entity).knockBack(entity, Conversions.feetToMeters * knockback, Conversions.feetToMeters * -this.getMotion().x, Conversions.feetToMeters * -this.getMotion().z);
				}
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
	
	public double lerp(double a, double b, double f) 
	{
	    return (a * (1.0 - f)) + (b * f);
	}
	
	public static EntityFlail spawnOrb(World worldIn, BlockPos pos, Entity owner, float damage, float knockback, EntityType<? extends EntityFlail> flail) {
		EntityFlail orb = flail.create(worldIn, null, null, null, pos, SpawnReason.EVENT, false, false);
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
