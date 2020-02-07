package kmerrill285.trewrite.entities;

import java.util.Random;

import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.NetworkHooks;

public class EntityCoinPortal extends MobEntity implements IEntityAdditionalSpawnData {
	
	private int coins;
	
	public EntityCoinPortal(EntityType<EntityCoinPortal> type, World worldIn) {
		super(type, worldIn);
		coins = new Random().nextInt(10) + 5;
	}
	
	public EntityCoinPortal(World worldIn) {
		super(EntitiesT.COIN_PORTAL, worldIn);
		coins = new Random().nextInt(10) + 5;
	}
		
	public EntityCoinPortal(World worldIn, double x, double y, double z) {
		super(EntitiesT.COIN_PORTAL, worldIn);
		this.setPosition(x, y, z);
		coins = new Random().nextInt(10) + 5;
		
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
		this.noClip = true;
		if (age == 0) coins = new Random().nextInt(10) + 5;
		this.age++;
		if (age % 20 == 0 && coins > 0) {
			coins--;
			EntityCoin coin = EntityCoin.spawnCoin(world, getPosition(), EntityCoin.GOLD, 1);
			coin.posX += rand.nextDouble();
			coin.posY += rand.nextDouble();
			coin.posZ += rand.nextDouble();
			if (coins <= 0) {
				this.remove();
			}
		} else {
			if (coins <= 0)
			this.remove();
		}
	}

	public float lerp(float a, float b, float f) 
	{
	    return (a * (1.0f - f)) + (b * f);
	}
	
	
	

	public static EntityCoinPortal spawnCoinPortal(World worldIn, BlockPos pos) {
		EntityCoinPortal star = EntitiesT.COIN_PORTAL.create(worldIn, null, null, null, pos, SpawnReason.EVENT, false, false);
		star.coins = new Random().nextInt(10) + 5;
		worldIn.addEntity(star);
		return star;
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
		coins = compound.getInt("coins");

	}

	@Override
	public void writeAdditional(CompoundNBT compound) {
//		System.out.println("write additional " + compound);
		compound.putInt("age", age);
		compound.putInt("coins", coins);

	}

	@Override
	public void writeSpawnData(PacketBuffer buffer) {
//		System.out.println("writeSpawnData " + buffer);
		buffer.writeInt(coins);
	}

	@Override
	public void readSpawnData(PacketBuffer additionalData) {
//		System.out.println("READ SPAWN DATA: " + additionalData);

		coins = additionalData.readInt();
	}
	
	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected void registerData() {
		super.registerData();
	}

}
