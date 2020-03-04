package kmerrill285.trewrite.entities;

import java.util.List;

import kmerrill285.trewrite.events.ScoreboardEvents;
import kmerrill285.trewrite.items.ItemT;
import kmerrill285.trewrite.world.WorldStateHolder;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.NetworkHooks;

public class EntityCoin extends MobEntity implements IEntityAdditionalSpawnData {

	public static final int COPPER = 0, SILVER = 1, GOLD = 2, PLATINUM = 3;
	public int coin = EntityCoin.COPPER;
	public int amount = 1;
	
	public EntityCoin(EntityType<EntityCoin> type, World worldIn) {
		super(type, worldIn);
	}
	
	public EntityCoin(World worldIn) {
		super(EntitiesT.COIN, worldIn);
	}
		
	public EntityCoin(World worldIn, double x, double y, double z) {
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
		
		
		
		this.setNoGravity(false);
		this.age++;
		boolean moving = false;
		this.entityCollisionReduction = 1.0f;
		if (age > 60 * 5 * 20 || dead == true) { this.remove(); }
		
		if (age > 60 * 5 * 20) dead = true;
		
		if (dead == true) {

			this.remove();
			this.removed = true;
			return;
		}
		
		List<? extends EntityCoin> items = world.getEntitiesWithinAABB(EntityCoin.class, new AxisAlignedBB(getPositionVec().add(-5, -5, -5), getPositionVec().add(5, 5, 5)));
		noClip = false;
		for (int i = 0; i < items.size(); i++) {
			EntityCoin item = items.get(i);
			if (item != this) {
				if (item.ticksExisted > this.ticksExisted) {
					if (item.coin == coin)
					if (item.amount + this.amount <= 99) {
						if (getPositionVec().distanceTo(item.getPositionVec()) > 1) {
							noClip = true;
							posX = lerp((float)posX, (float)item.posX, 0.5f);
							posY = lerp((float)posY, (float)item.posY, 0.5f);
							posZ = lerp((float)posZ, (float)item.posZ, 0.5f);
							setPosition(posX, posY, posZ);
						} else {
							item.amount += this.amount;
							remove();
							this.removed = true;
							return;
						}
					}
				}
			}
		}
		
		World world = this.world;
		List<? extends PlayerEntity> players = world.getPlayers();
		
			double dist = Integer.MAX_VALUE;
			PlayerEntity closest = null;
			for (PlayerEntity player : players) {
				double d = player.getPositionVector().distanceTo(getPositionVector());
				if (d < dist) {
					dist = d;
					closest = player;
				}
			}
			if (closest != null && dist < 2.0f) {
				if (dead == false && closest.getHealth() <= closest.getMaxHealth()) {
					
					float newX = lerp((float)posX, (float)closest.posX, 0.35f);
					float newY = lerp((float)posY, (float)closest.posY, 0.35f);
					float newZ = lerp((float)posZ, (float)closest.posZ, 0.35f);
					
					this.posX = newX;
					this.posY = newY;
					this.posZ = newZ;
					moving = true;
					
					if (dist < 1f) {
						if (!world.isRemote) {
							final ServerPlayerEntity player = (ServerPlayerEntity)closest;
							
				            if (grabbed == false) {
				            	int value = this.amount;
				            	if (this.coin == EntityCoin.SILVER) {
				            		value *= 100;
				            	}
				            	if (this.coin == EntityCoin.GOLD) {
				            		value *= 10000;
				            	}
				            	if (this.coin == EntityCoin.PLATINUM) {
				            		value *= 1000000;
				            	}
				            	ScoreboardEvents.getScore(player.getWorldScoreboard(), player, ScoreboardEvents.COINS).increaseScore(value);
				            	grabbed = true;
				            	this.remove();
				            }
						}
					}
					
				}
			}
		if (moving == false) {
			 
			this.move(MoverType.SELF, new Vec3d(0, -0.5f, 0));
		}
	}

	public float lerp(float a, float b, float f) 
	{
	    return (a * (1.0f - f)) + (b * f);
	}
	
	@Override
	protected void registerData() {
		super.registerData();
//		this.dataManager.register(stack_data, stack);
//		this.dataManager.register(item_data, item);
//		this.dataManager.register(pickup_delay, pickupDelay);
	}
	
	@Override
	public void read(CompoundNBT compound) {
//		System.out.println("read " + compound);
		super.read(compound);
		age = compound.getInt("age");
		coin = compound.getInt("coin");
		amount = compound.getInt("amount");
	}

	@Override
	public void readAdditional(CompoundNBT compound) {
//		System.out.println("read additional " + compound);
		age = compound.getInt("age");
		coin = compound.getInt("coin");
		amount = compound.getInt("amount");
	}

	@Override
	public void writeAdditional(CompoundNBT compound) {
//		System.out.println("write additional " + compound);
		compound.putInt("age", age);
		compound.putInt("coin", coin);
		compound.putInt("amount", amount);
	}

	@Override
	public void writeSpawnData(PacketBuffer buffer) {
//		System.out.println("writeSpawnData " + buffer);
		buffer.writeInt(coin);
		buffer.writeInt(amount);
	}

	@Override
	public void readSpawnData(PacketBuffer additionalData) {
//		System.out.println("READ SPAWN DATA: " + additionalData);

		coin = additionalData.readInt();
		amount = additionalData.readInt();
	}
	
	

	public static EntityCoin spawnCoin(World worldIn, BlockPos pos, int type, int value) {
		EntityCoin coin = EntitiesT.COIN.create(worldIn, null, null, null, pos, SpawnReason.EVENT, false, false);
		coin.coin = type;
		coin.amount = value;
		worldIn.addEntity(coin);
		return coin;
	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

}
