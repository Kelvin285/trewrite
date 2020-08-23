package kmerrill285.trewrite.entities.monsters;

import kmerrill285.trewrite.entities.EntitiesT;
import kmerrill285.trewrite.entities.EntityCoin;
import kmerrill285.trewrite.entities.EntityHeart;
import kmerrill285.trewrite.entities.EntityItemT;
import kmerrill285.trewrite.entities.IHostile;
import kmerrill285.trewrite.items.ItemStackT;
import kmerrill285.trewrite.items.ItemsT;
import kmerrill285.trewrite.util.Util;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FlyingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

public class EntityMeteorHead extends FlyingEntity implements IHostile {

	
	public PlayerEntity target = null;
	
	
	public EntityMeteorHead(EntityType<? extends FlyingEntity> type, World worldIn) {
		super(type, worldIn);
	}
	
	public EntityMeteorHead(World worldIn) {
		super(EntitiesT.METEOR_HEAD, worldIn);
	}
	
	
	@Override
	public void dropLoot(DamageSource source, boolean b) {
//		EntityItemT.spawnItem(this.getEntityWorld(), this.getPosition(), new ItemStackT(ItemsT.DEMONITE_ORE, this.rand.nextInt(87 - 30) + 30, null));
		if (Util.isChristmas()) {
			if (rand.nextDouble() <= 0.0769) {
				EntityItemT.spawnItem(this.getEntityWorld(), this.getPosition(), new ItemStackT(ItemsT.PRESENT, 1, null));
			}
		}
		
		if (source.getImmediateSource() instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity)source.getImmediateSource();
			if (player.getHealth() <= player.getMaxHealth()) {
				if (rand.nextInt(12) == 0) {
					EntityHeart.spawnHeart(this.getEntityWorld(), this.getPosition());
				}
			}
		}
		
		if (rand.nextDouble() <= 0.25) {
			EntityItemT.spawnItem(this.getEntityWorld(), this.getPosition(), new ItemStackT(ItemsT.METEORITE, 1 + rand.nextInt(3), null));
		}
		
		EntityCoin.spawnCoin(world, getPosition(), EntityCoin.COPPER, 80);
    }
	
	/**
     * Checks if the entity is in range to render.
     */
    @OnlyIn(Dist.CLIENT)
    public boolean isInRangeToRenderDist(double distance) {
       double d0 = 64.0D * getRenderDistanceWeight();
       return distance < d0 * d0;
    }
	
	public void onCollideWithPlayer(PlayerEntity player) {
		player.attackEntityFrom(DamageSource.causeMobDamage(this), 40);
		if (rand.nextInt(100) <= 33) {
			player.setFire(7);
		}
	}
	
	
	
	int dirX = 0;
	int dirY = 0;
	int dirZ = 0;
	
	public void tick() {
		super.tick();
		
		
		if (target == null) {
			target = world.getClosestPlayer(this, 25);
		} else {
			Vec3d targetPos = target.getPositionVec().add(0, target.getEyeHeight() / 2.0f, 0);
			Vec3d pos = getPositionVec();
			Vec3d dir = targetPos.subtract(pos);
			double len = dir.length();
			if (len > 0) {
				dir = new Vec3d(dir.getX() / len, dir.getY() / len, dir.getZ() / len);
				float speed = 1f / 20.0f; // half a block in one tick
				this.setMotion(dir.x * speed, dir.y * speed * 4, dir.z * speed);
				this.lookAt(EntityAnchorArgument.Type.EYES, target.getPositionVec());
				this.noClip = true;
				this.setNoGravity(true);
			}
			if (target.getPositionVec().distanceTo(getPositionVec()) > 25) {
				target = null;
				this.setMotion(0, 0, 0);
				this.noClip = false;
				this.setNoGravity(false);
			}
		}
		
		
	}
	public boolean attackEntityFrom(DamageSource source, float damage) {
    	if (source == DamageSource.IN_WALL || source == DamageSource.FALL || source == DamageSource.CRAMMING) return false;
    	{
    		damage -= 6;
    		if (damage < 1) damage = 1;
    		return super.attackEntityFrom(source, damage);
    	}
    }
	public static void spawnInWorld(World worldIn, BlockPos pos) {
		EntityMeteorHead head = EntitiesT.METEOR_HEAD.create(worldIn, null, null, null, pos, SpawnReason.EVENT, false, false);
		head.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(26);
		head.setHealth(26);
		worldIn.addEntity(head);
	}
	
	
	@Override
	protected void registerData() {
		super.registerData();
	}
	
	@Override
	public void read(CompoundNBT compound) {
		super.read(compound);
	}

	@Override
	public void readAdditional(CompoundNBT compound) {
	}

	@Override
	public void writeAdditional(CompoundNBT compound) {
	}
	
	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}
