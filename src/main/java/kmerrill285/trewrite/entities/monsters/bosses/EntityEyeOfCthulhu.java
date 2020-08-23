package kmerrill285.trewrite.entities.monsters.bosses;

import javax.annotation.Nullable;

import kmerrill285.trewrite.entities.EntitiesT;
import kmerrill285.trewrite.entities.EntityCoin;
import kmerrill285.trewrite.entities.EntityHeart;
import kmerrill285.trewrite.entities.EntityItemT;
import kmerrill285.trewrite.entities.IHostile;
import kmerrill285.trewrite.entities.monsters.EntityDemonEye;
import kmerrill285.trewrite.items.ItemStackT;
import kmerrill285.trewrite.items.ItemsT;
import kmerrill285.trewrite.util.Util;
import kmerrill285.trewrite.world.WorldStateHolder;
import net.minecraft.command.arguments.EntityAnchorArgument.Type;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FlyingEntity;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

public class EntityEyeOfCthulhu extends FlyingEntity implements IEntityAdditionalSpawnData, IHostile {
	
	public int damage = 18;
	
	public float rx, ry, rz;
	
	public boolean collision;

	public boolean bounce;
	
	public double velX, velY, velZ;
	public double oldVelX, oldVelY, oldVelZ;
	
	public double speed = 2;
	public double acc = 0.01;

	public static int phase = 1;
	
	public boolean spawnEyes = true;
	public int dashed = 0;
	public int eyes = 0;
	public int eyesNeeded = 0;
	
	public int maxHealth;
	public float bosshealth;
	
	public double transformedRotation = 0;
	
	public int defense = 0;
	
    public static final DataParameter<Integer> phase_data = EntityDataManager.createKey(EntityEyeOfCthulhu.class, DataSerializers.VARINT);

	public boolean ALREADY_SPAWNED = false;
	public boolean REMOVED = false;
	
	private int mode = 0;
	private final int NORMAL = 0, EXPERT = 1, REVENGANCE = 2, DEATH = 3;
    
	 public EntityEyeOfCthulhu(EntityType<? extends EntityEyeOfCthulhu> type, World worldIn) {
		super(type, worldIn);
		init();
		
		//play roar sound.  Wither sound is just a placeholder
	    worldIn.playSound((PlayerEntity)null, posX, posY, posZ, SoundEvents.ENTITY_WITHER_SPAWN, SoundCategory.HOSTILE, 2.0F, 0.5F);
	    
	    if (!worldIn.isRemote()) {
	    	world.getServer().getPlayerList().sendMessage(new StringTextComponent("The Eye of Cthulhu has awoken!").applyTextStyles(TextFormatting.BLUE, TextFormatting.BOLD));
	    }
	    
	    this.maxHealth = 2800 + (500 * (worldIn.getPlayers().size() - 1));
	    this.bosshealth = maxHealth;
	     this.setHealth(maxHealth);
	    
	    
	    phase = 0;
	}

    public EntityEyeOfCthulhu(World world) {
    	super(EntitiesT.DEMON_EYE, world);
    	init();
    }
    
    public void init() {
    	
    }
    
    @Nullable
    public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
    	 this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(2800 + (500 * (worldIn.getPlayers().size() - 1)));
    	 this.getAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1000);

    	
    	 switch (mode) {
	    	 case (NORMAL):
	    		 this.maxHealth = 2800;
	    	 	this.damage = 15;
	    	 break;
	    	 case (EXPERT):
	    	 	this.damage = 30;
	    	 	doExpertModeScaling(3640);
	    	 break;
	    	 case (REVENGANCE):
	    		 this.damage = 30;
	    	 	 doExpertModeScaling(4550);
	    	 break;
	    	 case (DEATH):
	    		 this.damage = 30;
	    	 	 doExpertModeScaling(4550);
	    	 break;
    	 }
 	     this.bosshealth = maxHealth;
 	    this.setHealth(maxHealth);
         return spawnDataIn;
         
    }
    
    private void doExpertModeScaling(double maxHealth) {
    	 if (world.getPlayers().size() > 1) {
 	    	double[] healthAdded = new double[world.getPlayers().size()];
	    	 	
 	    	healthAdded[0] = 0.35;
 	    	
 	    	for (int i = 1; i < world.getPlayers().size(); i++) {
 	    		healthAdded[i] = healthAdded[i - 1] + (1 - healthAdded[i- 1]) / 3.0;
 	    	}
 	    	
 	    	double multiplayerFactor = 1;
 	    	for (int i = 0; i < healthAdded.length; i++) {
 	    		multiplayerFactor += healthAdded[i];
 	    	}
 	    	if (multiplayerFactor > 1000) multiplayerFactor = 1000;
 	    	this.maxHealth = (int)(maxHealth * multiplayerFactor);
 	    } else {
 	    	this.maxHealth = (int)maxHealth;
 	    }
    }
    
    
    
    public void dropLoot(DamageSource source, boolean b) {
    	if (REMOVED) return;
//		this.dropItem(Item.getItemFromBlock(Blocks.corrupt_ore), this.rand.nextInt(87 - 30) + 30);
//		
//		this.dropItem(Items.corrupt_arrow, this.rand.nextInt(49 - 20) + 20);
//		
//		this.dropItem(Items.shield_of_cthulu, this.worldObj.playerEntities.size());
    	WorldStateHolder.get(world).eyeDefeated = true;
    	if (!world.isRemote()) {
	    	world.getServer().getPlayerList().sendMessage(new StringTextComponent("The Eye of Cthulhu has been defeated!").applyTextStyles(TextFormatting.BLUE, TextFormatting.BOLD));
    	}
		EntityItemT.spawnItem(this.getEntityWorld(), this.getPosition(), new ItemStackT(ItemsT.DEMONITE_ORE, this.rand.nextInt(87 - 30) + 30, null));
		
		if (Util.isChristmas()) {
			if (rand.nextDouble() <= 0.0769) {
				EntityItemT.spawnItem(this.getEntityWorld(), this.getPosition(), new ItemStackT(ItemsT.PRESENT, 1, null));
			}
		}
		
		EntityCoin.spawnCoin(world, getPosition(), EntityCoin.GOLD, 7);
		EntityCoin.spawnCoin(world, getPosition(), EntityCoin.SILVER, 50);
		
		for (int i = 0; i < 4; i++) {
			EntityHeart.spawnHeart(this.getEntityWorld(), this.getPosition().add(rand.nextInt(2) - 1, 0, rand.nextInt(2) - 1));
		}
		
		
		
    }
    
    
    
    public void onCollideWithPlayer(PlayerEntity player)
    {
    	tryDamagePlayer(player);
    }
    
    public void tryDamagePlayer(PlayerEntity player) {
    	if (transformedRotation > 0 && transformedRotation < 360 * 5 - 10) {
    		return;
    	}
    	if (player != null)
    		{
    			if (player.getDistance(this) <= 1.5)
        			if (phase == 1)
        				player.attackEntityFrom(DamageSource.causeMobDamage(this), 15);
        			else
        				player.attackEntityFrom(DamageSource.causeMobDamage(this), 23);
        		
	    		 
    		}
		}
    		
    
    
    public float getHealth() {
    	return this.bosshealth;
    }
    
    public boolean canRenderOnFire() {
		return false;
	}
    
    public void setFire(int seconds) {
    	//bosses are immune to fire
    }
    
    public BlockPos lastTarget;
    
    public void tick() {
    	ALREADY_SPAWNED = true;
    	
    	
    	boolean despawn = true;
    	for (int i = 0; i < world.getPlayers().size(); i++) {
    		if (world.getPlayers().get(i).getHealth() > 0) {
    			despawn = false;
    			break;
    		}
    	}
    	
    	if (despawn) {REMOVED = true; remove();}
    	
    	super.tick();
   	 	
    	
   	 	this.getAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(9999);
   	 	
   	 	if (this.getAttackTarget() == null) {
   	 		targetClosest();
   	 	}
   	 	if (this.getAttackTarget() == null) {
   	 		REMOVED = true; remove();
   	 		return;
   	 	}
	 	
	 	if (!world.isRemote) {
	 		this.setHealth(this.bosshealth);
	 	}
	 	this.maxHurtTime = 0;
	 	this.hurtResistantTime = 0;
   	 	
	   	 boolean night = world.getDayTime() % 24000L > 15000 && world.getDayTime() % 24000L < 22000;
	   	 if (!world.isRemote()) {
	   		if (!night) {
				velY = 2;
			} else {
				switch(mode) {
				case NORMAL:
					doNormalModeAI();
					break;
				case EXPERT:
					doExpertModeAI();
				case REVENGANCE:
					doRevenganceAI();
				case DEATH:
					doDeathModeAI();
				}
			}
	   		this.setMotion(velX, velY, velZ);
	   	 }
	   	 if (this.dataManager.get(phase_data).intValue() > 0) {
	   		 if (transformedRotation == 0) transformedRotation = 1;
	   	 }
	   	 if (transformedRotation > 0 && transformedRotation < (360 * 5) - 1) {
	   		 transformedRotation = MathHelper.lerp(0.05f, transformedRotation, 360 * 5);
	   		 this.lookAt(Type.EYES, this.getPositionVec().add(Math.cos(transformedRotation), this.getPositionVec().y, Math.sin(transformedRotation)));
	   	 } else {
	   		 if (transformedRotation > 360 * 5 - 1) transformedRotation = 360 * 5;
	   		 this.lookAt(Type.EYES, this.getAttackTarget().getEyePosition(0));
	   	 }
	   	 
	   	
	 }
	 
    int lastTick = 0;
	 private void doNormalModeAI() {
		 
	 	if (this.bosshealth > this.maxHealth / 2) {
	 		this.dataManager.set(phase_data, 0);
	 		if (this.spawnEyes) {
	 			this.hoverOverTarget();
	 			if (this.ticksExisted > lastTick + 20) {
	 				this.lastTick = ticksExisted;
	 				EntityDemonEye eye = EntitiesT.DEMON_EYE.create(world, null, null, null, this.getPosition(), SpawnReason.EVENT, false, false);
					eye.setPosition(posX, posY, posZ);
					eye.setHealth(8);
					eye.getAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(12);
					world.addEntity(eye);
					eye.money = 0;
					eye.noClip = true;
					eye.getDataManager().set(EntityDemonEye.type_data, 0);
					eye.setCustomName(new StringTextComponent("Servant of Cthulhu"));
					eyes++;
					if (eyes > rand.nextInt(2) + 3) {
						spawnEyes = false;
						eyes = 0;
					}
	 			}
	 		} else {
	 			if (this.dashed == -1) {
	 				if (!hoverOverTarget()) {
	 					dashed = 0;
	 					lastTick = this.ticksExisted;
	 				}
	 			} else {
	 				
	 				if (this.ticksExisted > lastTick + 40) {

	 					double dashSpeed = 0.5;
	 					dash(dashSpeed);
	 					
	 					dashed++;
	 					lastTick = this.ticksExisted;
	 				} else {
	 					this.velX *= 0.98f;
	 					this.velY *= 0.98f;
	 					this.velZ *= 0.98f;
	 				}
	 				if (dashed == 4) {
	 					this.spawnEyes = true;
	 					dashed = -1;
	 				}
	 			}
	 			
	 		}
	 	} else {
	 		this.dataManager.set(phase_data, 1);
	 		if (transformedRotation == 0) transformedRotation = 1;
	 		this.defense = 0;
	 		this.damage = 20; // just doing 5 more damage because I don't know the actual damage value
	 		
	 		if (this.spawnEyes) {
	 			this.hoverOverTarget();
	 			if (this.ticksExisted > lastTick + 20 * 3) { // instead of spawning eyes now, the Eye of Cthulhu will just hover over the player
	 				spawnEyes = false;
	 			}
	 		} else {
	 			if (this.dashed == -1) {
	 				if (!hoverOverTarget()) {
	 					dashed = 0;
	 					lastTick = this.ticksExisted;
	 				}
	 			} else {
	 				
	 				if (this.ticksExisted > lastTick + 40) {

	 					double dashSpeed = 0.75; // slightly faster dash speed, but not that much faster
	 					dash(dashSpeed);
	 					
	 					dashed++;
	 					lastTick = this.ticksExisted;
	 				} else {
	 					this.velX *= 0.98f;
	 					this.velY *= 0.98f;
	 					this.velZ *= 0.98f;
	 				}
	 				if (dashed == 4) {
	 					this.spawnEyes = true;
	 					dashed = -1;
	 				}
	 			}
	 			
	 		}
	 	}
	 	
	 }
	 
	 
	 private void doExpertModeAI() {
	 	
	 }
	 
	 private void doRevenganceAI() {
	 	
	 }
	 
	 private void doDeathModeAI() {
	 	
	 }
	 
	 private void dash(double dashSpeed) {
		 LivingEntity target = this.getAttackTarget();
		 Vec3d tPos = new Vec3d(target.lastTickPosX, target.lastTickPosY, target.lastTickPosZ);
		 Vec3d vec = new Vec3d(tPos.x, tPos.y, tPos.z).subtract(getPositionVec());
		 double length = vec.length();
		 
		 if (length > 0) {
		 	 Vec3d normal = new Vec3d(vec.x / length, vec.y / length, vec.z / length);
		 	 velX = normal.x * dashSpeed;
		 	 velY = normal.y * dashSpeed;
		 	 velZ = normal.z * dashSpeed;
		 }
	 }
	 
	 private boolean hoverOverTarget() {
		 if (this.ticksExisted < lastTick + 20 * 4) {
			Vec3d targetPos = this.getAttackTarget().getPositionVec();
			Vec3d up = targetPos.add(0, this.getHeight() * 3, 0);
			
			double accel = 0.05f;
			double maxVel = 0.2f;
			
			if (velX < -maxVel) {
				velX += accel * 0.25;
			}
			else if (velX > maxVel) {
				velX -= accel * 0.25;
			} else {
				if (posX < up.x) {
					velX += accel;
				}
				else if (posX > up.x) { 
					velX -= accel;
				}
			}
			if (velY < -maxVel) {
				velY += accel * 0.25;
			}
			else if (velY > maxVel) {
				velY -= accel * 0.25;
			} else {
				if (posY < up.y) { 
					velY += accel * 4.0f;
				}
				else if (posY > up.y) {
					velY -= accel * 4.0f;
				}
			}
			if (velZ < -maxVel) {
				velZ += accel * 0.25;
			}
			else if (velZ > maxVel) {
				velZ -= accel * 0.25;
			} else {
				if (posZ < up.z) { 
					velZ += accel;
				}
				else if (posZ > up.z) { 
					velZ -= accel;
				}
			}
			return true;
		}
		 return false;
	 }
    
    public void targetClosest() {
    	this.setAttackTarget(world.getClosestPlayer(posX + getWidth() / 2, posY + getHeight() / 2, posZ + getWidth() / 2));
    }
    
    
    public void remove() {
    	super.remove();
    	
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
    	if (source == DamageSource.IN_WALL || source == DamageSource.FALL || source == DamageSource.CRAMMING) return false;
    	if (transformedRotation > 0 && transformedRotation < 360 * 5 - 10) {
    		return false;
    	}
    	amount -= defense;
    	if (amount < 1) amount = 1;
    	this.bosshealth -= amount;
    	super.setHealth(this.bosshealth);
    	super.attackEntityFrom(source, 0);
    	if (this.bosshealth <= 0) {
    		this.dropLoot(DamageSource.GENERIC, true);
    		this.remove();
    	}
    	return true;
    }
    
    protected void dealDamage(LivingEntity entityIn) {
        if (this.isAlive()) {
           if (this.canEntityBeSeen(entityIn) && entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float)this.getAttackStrength())) {
              this.playSound(SoundEvents.ENTITY_SLIME_ATTACK, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
              this.applyEnchantments(this, entityIn);
           }
        }
        
     }
    @Override
    public void knockBack(Entity entityIn, float strength, double xRatio, double zRatio) {
    }
    
    protected int getAttackStrength()
    {
        return 18;
    }
    
	@Override
	protected void registerData() {
		super.registerData();
		this.dataManager.register(phase_data, 1);
	}
	

	@Override
	public void read(CompoundNBT compound) {
//		System.out.println("read " + compound);
		super.read(compound);
		this.bosshealth = compound.getFloat("bosshealth");
		if (compound.getBoolean("spawned")) {REMOVED = true; if (!world.isRemote()) remove();}
	}

	@Override
	public void readAdditional(CompoundNBT compound) {
//		System.out.println("read additional " + compound);
		this.bosshealth = compound.getFloat("bosshealth");
		if (compound.getBoolean("spawned")) {REMOVED = true; if (!world.isRemote()) remove();}
	}

	@Override
	public void writeAdditional(CompoundNBT compound) {
//		System.out.println("write additional " + compound);
		compound.putFloat("bosshealth", bosshealth);
		compound.putBoolean("spawned", ALREADY_SPAWNED);
	}

	@Override
	public void writeSpawnData(PacketBuffer buffer) {
//		System.out.println("writeSpawnData " + buffer);
		buffer.writeFloat(bosshealth);
		buffer.writeBoolean(ALREADY_SPAWNED);
	}

	@Override
	public void readSpawnData(PacketBuffer additionalData) {
//		System.out.println("READ SPAWN DATA: " + additionalData);

		this.bosshealth = additionalData.readFloat();
		if (additionalData.readBoolean()) {REMOVED = true; if (!world.isRemote()) remove();}
	}
    
}
