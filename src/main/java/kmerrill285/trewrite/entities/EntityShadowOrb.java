package kmerrill285.trewrite.entities;

import java.util.List;

import kmerrill285.trewrite.world.WorldStateHolder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

public class EntityShadowOrb extends MobEntity {

	public Entity owner;
	public long created;
	
	public EntityShadowOrb(EntityType<EntityShadowOrb> type, World worldIn) {
		super(type, worldIn);
	}
	
	public EntityShadowOrb(World worldIn) {
		super(EntitiesT.SHADOW_ORB, worldIn);
	}
		
	public EntityShadowOrb(World worldIn, double x, double y, double z) {
		super(EntitiesT.SHADOW_ORB, worldIn);
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
		super.tick();
		this.noClip = true;
		this.setNoGravity(true);
		if (world.isRemote) return;
		
		
		if (this.owner == null) {
			remove();
			return;
		}
		
		if (this.owner instanceof LivingEntity) {
			LivingEntity e = (LivingEntity)owner;
			if (e.getHealth() <= 0) {
				remove();
				return;
			}
		}
		
		float speed = 3f;
		float acceleration = 0.08f;
		int dirX = posX < owner.posX ? 1 : -1, dirY = posY < (owner.posY + 2.5) ? 1 : -1, dirZ = posZ < owner.posZ ? 1 : -1;

		setMotion(getMotion().add(acceleration * dirX, acceleration * dirY, acceleration * dirZ));
		
		if (Math.abs(getMotion().x) >= speed) {
			getMotion().add(-acceleration * dirX, acceleration * dirY, 0);
		}
		if (Math.abs(getMotion().y) >= speed * 0.5) {
			getMotion().add(acceleration * dirX, -acceleration * dirY * 0.5, acceleration * dirZ);
		}
		if (Math.abs(getMotion().z) >= speed) {
			getMotion().add(0, acceleration * dirY, -acceleration * dirZ);
		}

		if (getPositionVec().distanceTo(owner.getPositionVec().add(0, 2.5, 0)) > 2) {
			posX = lerp(posX, owner.posX, 0.2);
			posY = lerp(posY, owner.posY + 2.5, 0.2);
			posZ = lerp(posZ, owner.posZ, 0.2);
		}
		
//		
//		
//		setPosition(posX, posY, posZ);
//		setMotion(0, 0, 0);
		
		WorldStateHolder.get(world).setLight(getPosition(), 15, world.getDimension().getType());
	}

	public double lerp(double a, double b, double f) 
	{
	    return (a * (1.0 - f)) + (b * f);
	}
	
	public static EntityShadowOrb spawnOrb(World worldIn, BlockPos pos, Entity owner) {
		EntityShadowOrb orb = EntitiesT.SHADOW_ORB.create(worldIn, null, null, null, pos, SpawnReason.EVENT, false, false);
		orb.owner = owner;
		orb.created = System.currentTimeMillis();
		List<Entity> entities = worldIn.getEntitiesWithinAABBExcludingEntity(orb, new AxisAlignedBB(orb.posX - 5, orb.posY - 5, orb.posZ - 5, orb.posX + 5, orb.posY + 5, orb.posZ + 5));
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			if (e instanceof EntityShadowOrb) {
				if (((EntityShadowOrb)e).created < orb.created && ((EntityShadowOrb)e).owner == owner) {
					((EntityShadowOrb)e).setHealth(0);
					entities.remove(i);
					continue;
				}
			}
		}
		worldIn.addEntity(orb);
		return orb;
	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

}
