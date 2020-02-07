package kmerrill285.trewrite.entities.monsters.bosses;

import javax.annotation.Nullable;

import kmerrill285.trewrite.core.items.ItemStackT;
import kmerrill285.trewrite.entities.EntitiesT;
import kmerrill285.trewrite.entities.EntityCoin;
import kmerrill285.trewrite.entities.EntityHeart;
import kmerrill285.trewrite.entities.EntityItemT;
import kmerrill285.trewrite.items.ItemsT;
import kmerrill285.trewrite.util.Util;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.NetworkHooks;

public class EntityEowHead extends MobEntity implements IEntityAdditionalSpawnData {

	public float velX, velY, velZ;
	public float oldVelX, oldVelY, oldVelZ;
	
	public int life = 150;
	
	public int[] ai = new int[5];
	public PlayerEntity target = null;
	public float rx, ry, rz;
	
	public int money;
	
	public double motionX, motionY, motionZ;
	
	public int segments;
	public boolean spawnThings = true;
	public boolean ALREADY_SPAWNED = false;
	public boolean REMOVED = false;
	
	public MobEntity child = null;
	
	public EntityEowHead(EntityType<? extends EntityEowHead> type, World worldIn) {
		super(type, worldIn);
	}
	
	public EntityEowHead(World worldIn) {
		super(EntitiesT.EOW_HEAD, worldIn);
	}
	
	 @Nullable
	    public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
	    	 this.getAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2);
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
       double d0 = 64.0D * getRenderDistanceWeight();
       return distance < d0 * d0;
    }
	
	public void onCollideWithPlayer(PlayerEntity player) {
		player.attackEntityFrom(DamageSource.causeMobDamage(this), 8);
	}
	
	int dirX = 0;
	int dirY = 0;
	int dirZ = 0;
	
	public boolean canDespawn() {
		return this.ALREADY_SPAWNED && this.REMOVED;
	}
	
	
	public void tick() {
		super.tick();
		
		ALREADY_SPAWNED = true;
    	
		this.noClip = true;
		this.setNoGravity(true);
		if (world.getBlockState(getPosition()).getMaterial().blocksMovement() == false) {
			this.isAirBorne = true;
		} else {
			this.isAirBorne = false;
		}
    	
    	boolean despawn = true;
    	for (int i = 0; i < world.getPlayers().size(); i++) {
    		if (world.getPlayers().get(i).getHealth() > 0) {
    			despawn = false;
    			break;
    		}
    	}
    	
    	if (despawn) {REMOVED = true; remove();}
		
		
		
		if (segments == 0) {
			segments = 48;
			ai[0] = 0;
		}
		segments = 48;
		
		if (!world.isRemote) { 
			if (ai[0] == 0 && spawnThings == true) {
				
				//play roar sound.  Wither sound is just a placeholder
				world.playSound((PlayerEntity)null, posX, posY, posZ, SoundEvents.ENTITY_WITHER_SPAWN, SoundCategory.HOSTILE, 2.0F, 0.5F);
			    
			    if (!world.isRemote()) {
			    	world.getServer().getPlayerList().sendMessage(new StringTextComponent("The Eater of Worlds has awoken!").applyTextStyles(TextFormatting.BLUE, TextFormatting.BOLD));
			    }
				
				EntityEowBody lastWormBody = null;
				for (int i = 0; i < segments; i++) {
					EntityEowBody wormBody = EntityEowBody.spawnWormBody(world, getPosition(), 150, lastWormBody == null ? this : lastWormBody);
					if (lastWormBody == null)
						child = wormBody;
					else
						lastWormBody.child = wormBody;
					lastWormBody = wormBody;
				}
				EntityEowTail wormBody = EntityEowTail.spawnWormBody(world, getPosition(), 220, lastWormBody == null ? this : lastWormBody);
				wormBody.owner = lastWormBody;
				ai[0] = 1;
			}
			
			if (child == null) {
				this.remove();
				return;
			} else 
			{
				if (child.getHealth() <= 0) {
					this.remove();
					return;
				}
			}
			
			boolean collision = false;
			
			if (world.getBlockState(getPosition()).getMaterial().blocksMovement() == true) {
				collision = true;
			}
			
			
			double distance = 1000;
			
			for (int i = 0; i < world.getPlayers().size(); i++) {
				double dist = world.getPlayers().get(i).getPositionVector().distanceTo(this.getPositionVector());
				if (dist < distance) {
					distance = dist;
					target = world.getPlayers().get(i);
				}
			}
			
			if (target != null) {
				float speed = 5f;
				
				float acceleration = 0.25f;
				
				float absVelX = (float)Math.abs(velX);
				float absVelY = (float)Math.abs(velY);
				float absVelZ = (float)Math.abs(velZ);
				
				if (collision == false) {

					
					velY -= acceleration * 2;
					
				}
				else {
					if (System.currentTimeMillis() % 250 <= 25) {
						world.playSound(posX, posY, posZ, SoundEvents.BLOCK_WOOL_STEP, SoundCategory.HOSTILE, 1.0f, 1.0f, false);
					}
				}
					
					if (absVelZ > 0 || dirZ == 0) {
						if (posZ < target.posZ) { 
							dirZ = 1;
						} else {
							dirZ = -1;
						}
					}
					
					if (absVelX > 0 || dirX == 0) {
						if (posX < target.posX) { 
							dirX = 1;
						} else {
							dirX = -1;
						}
					}
					
					if (absVelY > 0 || dirY == 0) {
						if (posY < target.posY) { 
							dirY = 1;
						} else {
							dirY = -1;
						}
					}
					
					if (dirZ == 1) { 
						if (velZ < speed) {
							velZ += acceleration;
						}
					} else {
						if (velZ > -speed) {
							velZ -= acceleration;
						}
					}
					
					if (dirX == 1) { 
						if (velX < speed) {
							velX += acceleration;
						}
					} else {
						if (velX > -speed) {
							velX -= acceleration;
						}
					}
					
					
					if (dirY == 1) { 
						if (velY < speed) {
							velY += acceleration;
						}
					} else {
						if (velY > -speed) {
							velY -= acceleration;
						}
					}
					
					if (absVelZ > speed * 0.5f) {
						if (Math.abs(posZ - target.posZ) < Math.abs(posY - target.posY)) {
							if (Math.abs(posZ + velZ) > Math.abs(posZ - velZ)) {
								velZ -= acceleration;
								velY += acceleration;
							}
						}
					}
					
					if (absVelX > speed * 0.15f) {
						if (Math.abs(posX - target.posX) < Math.abs(posY - target.posY)) {
							if (Math.abs(posX + velX) > Math.abs(posX - velX)) {
								velX -= acceleration;
								velY += acceleration;
							}
						}
					}
					if (absVelY > speed * 0.15f) {
						if (Math.abs(posX - target.posX) > Math.abs(posY - target.posY)) {
							if (Math.abs(posY + velY) > Math.abs(posY - velY)) {
								if (collision)
								velY -= acceleration;
								velX += acceleration;
							}
						}
						
						if (Math.abs(posZ - target.posZ) > Math.abs(posY - target.posY)) {
							if (Math.abs(posY + velY) > Math.abs(posY - velY)) {
								if (collision)
								velY -= acceleration;
								velZ += acceleration;
							}
						}
					}
					
					
					
				
				
				
				
				
//				if (collision == false) {
//					if (velY > -speed) {
//						velY -= acceleration;
//					}
//				} else {
//					
//				}
			}
			
//			this.rz = (float)Math.toDegrees(Math.atan2(velY,  velX));
//			this.rx = (float)Math.toDegrees(Math.atan2(velY,  velZ));
//			this.ry += 10;
			
			this.rotationYaw = (float)Math.toDegrees(Math.atan2(velZ, velX)) - 90;

			this.oldVelX = velX + 0;
			this.oldVelY = velY + 0;
			this.oldVelZ = velZ + 0;
			
			this.motionX = velX * 0.1f;
			this.motionY = velY * 0.1f;
			this.motionZ = velZ * 0.1f;
			
			this.posX += motionX;
			this.posY += motionY;
			this.posZ += motionZ;
			
			this.move(MoverType.PISTON, new Vec3d(motionX, motionY, motionZ));
			this.setMotion(0, 0, 0);
		}
		


	}
	
	public boolean attackEntityFrom(DamageSource source, float amount) {
    	if (source == DamageSource.IN_WALL || source == DamageSource.FALL || source == DamageSource.CRAMMING) return false;
    	return super.attackEntityFrom(source, amount);
    }

	public static EntityEowHead spawnWormHead(World worldIn, BlockPos pos, float life, boolean spawnThings) {
		EntityEowHead head = EntitiesT.EOW_HEAD.create(worldIn, null, null, null, pos, SpawnReason.EVENT, false, false);
		head.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(life);
		head.setHealth(life);
		head.money = 300;
		head.segments = 48;
		head.spawnThings = spawnThings;
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
		if (compound.getBoolean("spawned")) {REMOVED = true; remove();}
	}

	@Override
	public void readAdditional(CompoundNBT compound) {
//		System.out.println("read additional " + compound);
		if (compound.getBoolean("spawned")) {REMOVED = true; remove();}
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

		if (additionalData.readBoolean()) {REMOVED = true; remove();}
	}
	
	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}
