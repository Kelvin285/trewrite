package kmerrill285.trewrite.entities.monsters.bosses;

import javax.annotation.Nullable;

import kmerrill285.trewrite.entities.EntitiesT;
import kmerrill285.trewrite.entities.EntityCoin;
import kmerrill285.trewrite.entities.EntityHeart;
import kmerrill285.trewrite.entities.EntityItemT;
import kmerrill285.trewrite.entities.IHostile;
import kmerrill285.trewrite.entities.projectiles.EntityVileSpit;
import kmerrill285.trewrite.items.ItemStackT;
import kmerrill285.trewrite.items.ItemsT;
import kmerrill285.trewrite.util.Util;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.NetworkHooks;

public class EntityEowBody extends MobEntity implements IEntityAdditionalSpawnData, IHostile {

	public float velX, velY, velZ;
	public float oldVelX, oldVelY, oldVelZ;
	
	public int life = 60;
	
	public int[] ai = new int[5];
	public PlayerEntity target = null;
	public float rx, ry, rz;
	
	public int money;
	
	public double motionX, motionY, motionZ;
	public MobEntity owner;
	public boolean ALREADY_SPAWNED = false;
	public boolean REMOVED = false;
	
	public MobEntity child;
	
	public EntityEowBody(EntityType<? extends EntityEowBody> type, World worldIn) {
		super(type, worldIn);
	}
	
	public EntityEowBody(World worldIn) {
		super(EntitiesT.EOW_BODY, worldIn);
	}
	
	@Nullable
    public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
    	 this.getAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(4);
 		 this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(life);
         return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }
	
	@Override
	public void dropLoot(DamageSource source, boolean b) {
		if (REMOVED) return;
		
		
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
		if (this.rand.nextInt(100) <= 50)
			EntityItemT.spawnItem(this.getEntityWorld(), this.getPosition(), new ItemStackT(ItemsT.DEMONITE_ORE, rand.nextInt(5 - 2) + 2, null));
		
//		if (this.rand.nextInt(100) <= 50)
//			EntityItemT.spawnItem(this.getEntityWorld(), this.getPosition(), new ItemStackT(ItemsT.SHADOW_SCALE, rand.nextInt(3 - 2) + 2, null));

		EntityCoin.spawnCoin(world, getPosition(), EntityCoin.SILVER, 3);
    }
	
	
	/**
     * Checks if the entity is in range to render.
     */
    @OnlyIn(Dist.CLIENT)
    public boolean isInRangeToRenderDist(double distance) {
       double d0 = 128.0D * getRenderDistanceWeight();
       return distance < d0 * d0;
    }
	
	public void onCollideWithPlayer(PlayerEntity player) {
		player.attackEntityFrom(DamageSource.causeMobDamage(this), 4);
	}
	
	public boolean canDespawn(double dist) {
		return ALREADY_SPAWNED && REMOVED;
	}
	
	int dirX = 0;
	int dirY = 0;
	int dirZ = 0;
	
	public void tick() {
		super.tick();
		ALREADY_SPAWNED = true;
		
		if (this.getHealth() <= 0) {
			return;
		}
		this.preventDespawn();
    	if (!world.isRemote) {
			boolean despawn = true;
	    	for (int i = 0; i < world.getPlayers().size(); i++) {
	    		if (world.getPlayers().get(i).getHealth() > 0) {
	    			despawn = false;
	    			break;
	    		}
	    	}
	    	
	    	if (despawn) {REMOVED = true; remove();}
		} else {
			return;
		}


		
    	
		if (this.owner == null) {
			
		} else {
			
			double distance = 1000;
			
			for (int i = 0; i < world.getPlayers().size(); i++) {
				double dist = world.getPlayers().get(i).getPositionVector().distanceTo(this.getPositionVector());
				if (dist < distance) {
					distance = dist;
					target = world.getPlayers().get(i);
				}
			}
			
			if (this.target != null) {
				if (rand.nextInt(1000) <= 5) {
					EntityVileSpit spit = EntitiesT.VILE_SPIT.create(world);
					spit.setPosition(posX, posY, posZ);
					
					Vec3d point = new Vec3d(posX - target.posX, (posY + 1) - target.posY, posZ - target.posZ).normalize();
					point = point.mul(2, 2, 2);
					
					spit.setMotion(-point.x, -point.y, -point.z);
					
					world.addEntity(spit);
				}
					
			}
			
			
			if (this.owner.getHealth() <= 0 && getHealth() > 0) {
				if (!world.isRemote) {
					
					EntityEowHead wormBody = EntityEowHead.spawnWormHead(world, getPosition(), 65, false);
					wormBody.setPosition(posX, posY, posZ);
					wormBody.spawnThings = false;
			        if (child instanceof EntityEowBody) {
			        	((EntityEowBody)child).owner = wormBody;
			        }
			        if (child instanceof EntityEowTail) {
			        	((EntityEowTail)child).owner = wormBody;
			        }
			        
			        wormBody.child = child;
			        
			        this.setHealth(0);
				}
			}
		
			
			
			if (this.child == null && !world.isRemote) {
				EntityEowTail wormBody = EntityEowTail.spawnWormBody(world, getPosition(), 220, this);

		        this.child = wormBody;
			}
			
			float dirX = (float)(owner.posX + 0.5f - (posX + 0.5f));
			float dirY = (float)(owner.posY + 0.5f - (posY + 0.5f));
			float dirZ = (float)(owner.posZ + 0.5f - (posZ + 0.5f));
			
			this.rotationYaw = (float)Math.toDegrees(Math.atan2(dirZ, dirX)) - 90;
			
			float length = (float)Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ) * 2f;
			
			
			
			float dist = (length - (float)12f) / length;
			
			float posX = dirX * dist;
			float posY = dirY * dist;
			float posZ = dirZ * dist;
			
			velX = 0;
			velY = 0;
			velZ = 0;
			
			this.posX = this.posX + posX;
			this.posY = this.posY + posY;
			this.posZ = this.posZ + posZ;
			
			this.setPosition(this.posX, this.posY, this.posZ);
		}
		
		
		this.oldVelX = velX + 0;
		this.oldVelY = velY + 0;
		this.oldVelZ = velZ + 0;
		
		this.motionX = velX * 0.01f;
		this.motionY = velY * 0.01f;
		this.motionZ = velZ * 0.01f;
//			
		this.setMotion(0, 0, 0);
////		this.posX += motionX;
////		this.posY += motionY;
////		this.posZ += motionZ;
		
	}
	public boolean attackEntityFrom(DamageSource source, float amount) {
    	if (source == DamageSource.IN_WALL || source == DamageSource.FALL || source == DamageSource.CRAMMING) return false;
    	return super.attackEntityFrom(source, amount);
    }
	public static EntityEowBody spawnWormBody(World worldIn, BlockPos pos, float life, MobEntity owner) {
		EntityEowBody head = EntitiesT.EOW_BODY.create(worldIn, null, null, null, pos, SpawnReason.EVENT, false, false);		
		head.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(life);
		head.setHealth(life);
		head.money = 300;
		head.owner = owner;
		worldIn.addEntity(head);
		return head;
	}
	
	@Override
	protected void registerData() {
		super.registerData();
	}
	
	@Override
	public void read(CompoundNBT compound) {
//		System.out.println("read " + compound);
		super.read(compound);
		if (compound.getBoolean("spawned")) {REMOVED = true; if (!world.isRemote()) remove();}
	}

	@Override
	public void readAdditional(CompoundNBT compound) {
//		System.out.println("read additional " + compound);
		if (compound.getBoolean("spawned")) {REMOVED = true; if (!world.isRemote()) remove();}
	}

	@Override
	public void writeAdditional(CompoundNBT compound) {
//		System.out.println("write additional " + compound);
		compound.putBoolean("spawned", ALREADY_SPAWNED);
	}

	@Override
	public void writeSpawnData(PacketBuffer buffer) {
//		System.out.println("writeSpawnData " + buffer);
		buffer.writeBoolean(ALREADY_SPAWNED);
	}

	@Override
	public void readSpawnData(PacketBuffer additionalData) {
//		System.out.println("READ SPAWN DATA: " + additionalData);

		if (additionalData.readBoolean()) {REMOVED = true; if (!world.isRemote()) remove();}
	}
	
	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}
