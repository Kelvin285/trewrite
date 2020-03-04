package kmerrill285.trewrite.entities.projectiles.hostile;

import kmerrill285.trewrite.blocks.BlocksT;
import kmerrill285.trewrite.blocks.pots.Pot;
import kmerrill285.trewrite.entities.EntitiesT;
import kmerrill285.trewrite.events.ScoreboardEvents;
import net.minecraft.block.Blocks;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
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

public class EntityEyeLaser extends MobEntity implements IEntityAdditionalSpawnData {

	public int damage = 22;
	public EntityEyeLaser(EntityType<EntityEyeLaser> type, World worldIn) {
		super(type, worldIn);
	}
	
	public EntityEyeLaser(World worldIn) {
		super(EntitiesT.COIN, worldIn);
	}
		
	public EntityEyeLaser(World worldIn, double x, double y, double z) {
		super(EntitiesT.COIN, worldIn);
		this.setPosition(x, y, z);
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
	
	@Override
	public void collideWithEntity(Entity e) {
	}
	
	@Override
	protected void collideWithNearbyEntities() {
	}
	public AxisAlignedBB getCollisionBoundingBox() {
		
	      return null;
	   }
	private int age = 0;
	public float hoverStart = 0.1f;
	
	public boolean dead = false;
	public int getAge() {
		return age;
	}
	
	public boolean canDespawn() {
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
		if (this.removed || this.getHealth() <= 0 || this.age >= 20 * 5) {
			super.tick();
		}
		
		if (world.getBlockState(getPosition()).getBlock() instanceof Pot) {
			world.setBlockState(getPosition(), BlocksT.AIR_BLOCK.getDefaultState());
		}
		
		this.setNoGravity(true);
		age++;
		if (age >= 20 * 5) {
			this.remove();
			return;
		}
		if (world.getBlockState(this.getPosition()).getMaterial().blocksMovement()) {
			this.remove();
			return;
		}
		if (world.getBlockState(this.getPosition().down()).getMaterial().blocksMovement()) {
			this.remove();
			return;
		}
		for (int i = 0; i < 10; i++)
			world.addParticle(ParticleTypes.PORTAL, posX, posY, posZ, 0, 0, 0);
		
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
		posX += getMotion().x;
		posY += getMotion().y;
		posZ += getMotion().z;
		
		this.lookAt(EntityAnchorArgument.Type.EYES, getPositionVec().add(this.getMotion().x, this.getMotion().y, this.getMotion().z));
	}
	public boolean hasNoGravity() {
		return true;
	}
	protected void onImpact(RayTraceResult result) {
		if (result.getType() == RayTraceResult.Type.ENTITY) {
			
			Entity entity = ((EntityRayTraceResult)result).getEntity();
			if (entity instanceof PlayerEntity) {
				PlayerEntity player = (PlayerEntity)entity;
				player.attackEntityFrom(DamageSource.MAGIC, damage);
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
	
	public float lerp(float a, float b, float f) 
	{
	    return (a * (1.0f - f)) + (b * f);
	}
	
	@Override
	protected void registerData() {
		super.registerData();
	}

	@Override
	public void read(CompoundNBT compound) {
//		System.out.println("read " + compound);
		super.read(compound);
		age = compound.getInt("age");
	}

	@Override
	public void readAdditional(CompoundNBT compound) {
//		System.out.println("read additional " + compound);
		age = compound.getInt("age");
	}

	@Override
	public void writeAdditional(CompoundNBT compound) {
//		System.out.println("write additional " + compound);
		compound.putInt("age", age);
	}
	@Override
	public void readSpawnData(PacketBuffer additionalData) {
	}

	@Override
	public void writeSpawnData(PacketBuffer buffer) {		
	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}


}
