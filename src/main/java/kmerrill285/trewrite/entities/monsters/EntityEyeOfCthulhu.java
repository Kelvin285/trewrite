package kmerrill285.trewrite.entities.monsters;

import javax.annotation.Nullable;

import kmerrill285.trewrite.core.items.ItemStackT;
import kmerrill285.trewrite.entities.EntitiesT;
import kmerrill285.trewrite.entities.EntityItemT;
import kmerrill285.trewrite.items.ItemsT;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FlyingEntity;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class EntityEyeOfCthulhu extends FlyingEntity {
	
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

	
	 public EntityEyeOfCthulhu(EntityType<? extends EntityEyeOfCthulhu> type, World worldIn) {
		super(type, worldIn);
		init();
		
		//play roar sound.  Wither sound is just a placeholder
	    worldIn.playSound((PlayerEntity)null, posX, posY, posZ, SoundEvents.ENTITY_WITHER_SPAWN, SoundCategory.HOSTILE, 2.0F, 0.5F);
	    
	    if (!worldIn.isRemote()) {
			worldIn.getServer().sendMessage(new StringTextComponent("/tellraw @a {\"text\":\"The Eye of Cthulhu has awoken!\",\"bold\":true,\"color\":\"blue\"}"));
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

    	 this.maxHealth = 2800 + (500 * (worldIn.getPlayers().size() - 1));
 	     this.bosshealth = maxHealth;
 	    this.setHealth(maxHealth);
         return spawnDataIn;
         
    }
    
    
    
    public void dropLoot(DamageSource source, boolean b) {
//		this.dropItem(Item.getItemFromBlock(Blocks.corrupt_ore), this.rand.nextInt(87 - 30) + 30);
//		
//		this.dropItem(Items.corrupt_arrow, this.rand.nextInt(49 - 20) + 20);
//		
//		this.dropItem(Items.shield_of_cthulu, this.worldObj.playerEntities.size());
    	
    	
		EntityItemT.spawnItem(this.getEntityWorld(), this.getPosition(), new ItemStackT(ItemsT.DEMONITE_ORE, this.rand.nextInt(87 - 30) + 30, null));
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
    
    public void tick() {
    	super.tick();
   	 	
    	

    	this.preventDespawn();
    	
    	this.setNoGravity(true);
    	this.noClip = true;
    	
    	double motionY = this.getMotion().y;
    	double motionX = this.getMotion().x;
    	double motionZ = this.getMotion().z;
    	
    	if (phase == 1) {
			defense = 12;
		} else {
			defense = 0;
		}

		
		if (!world.isRemote) { 
			motionY = 0;
			this.rotationPitch = 0;
			this.rotationYaw = 0;
			this.rotationYawHead = 0;
			PlayerEntity target = null;
			double distance = 1000;
			for (int i = 0; i < world.getPlayers().size(); i++) {
				double dist = world.getPlayers().get(i).getPositionVector().distanceTo(this.getPositionVector());
//				this.tryDamagePlayer(world.getPlayers().get(i));
				if (dist < distance) {
					distance = dist;
					target = world.getPlayers().get(i);
				}
			}
			
			
			if (world.getDayTime() % 24000 < 15000 || world.getDayTime() % 24000 > 22500) {
				this.velY = 10;
			}
			if (phase == 0) {
				phase = 1;
			}
			if (phase == 1 || phase == 2) {
				
				if (spawnEyes == true) {
					if (eyesNeeded == 0) {
						
						eyesNeeded = rand.nextInt(2) + 2;
						if (getHealth() <= maxHealth * 0.25f)
							eyesNeeded = 1;
					}
					else {
						
						if (this.ticksExisted % (35) == 0 && rand.nextBoolean() == true) {
							if (eyes < eyesNeeded) {
								eyes++;
								if (phase == 1) {
									if (world.getEntitiesWithinAABB(EntityDemonEye.class, this.getBoundingBox().expand(15, 15, 15)).size() <= 8) {
										EntityDemonEye eye = EntitiesT.DEMON_EYE.create(world, null, null, null, this.getPosition(), SpawnReason.EVENT, false, false);
										eye.setPosition(posX, posY, posZ);
										eye.setHealth(8);
										eye.getAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(12);
										world.addEntity(eye);
										eye.money = 0;
										eye.noClip = true;
									}
									
								}
							} else {
								
								spawnEyes = false;
								dashed = 0;
							}
						}
					}
					if (target != null) {
						if (velX > -4 && this.posX > target.posX + target.getWidth()) {
							velX -= 0.08;
							if (velX > 4) {
								velX -= 0.04;
							}
							else if (velX > 0) {
								velX -= 0.2;
							}
							if (velX < -4) {
								velX = -4;
							}
						} else if (velX < 4 && posX + 1 < target.posX) {
							velX += 0.08;
							if (velX < -4) {
								velX += 0.04;
							}
							else if (velX < 0) {
								velX += 0.2;
							}
							if (velX > 4) {
								velX = 4;
							}
						}
						
						if (velZ > -4 && this.posZ > target.posZ + target.getWidth()) {
							velZ -= 0.08;
							if (velZ > 4) {
								velZ -= 0.04;
							}
							else if (velZ > 0f) {
								velZ -= 0.2;
							}
							if (velZ < -4) {
								velZ = -4;
							}
						} else if (velZ < 4f && posZ + 1 < target.posZ) {
							velZ += 0.08f;
							if (velZ < -4) {
								velZ += 0.04;
							}
							else if (velZ < 0f) {
								velZ += 0.2;
							}
							if (velZ > 4) {
								velZ = 4;
							}
						}
						
						if (velY > -2.5 && posY > target.posY + target.getHeight() + 5) {
							velY -= 0.1f;
							if (velY > 2.5) {
								velY -= 0.05;
							} else if (velY > 0f) {
								velY -= 0.15;
							}
							if (velY < -2.5) {
								velY = -2.5;
							}
						} else if (velY < 2.5 && posY + 1 < target.posY + 5) {
							velY += 0.1f;
							if (velY < -2.5) {
								velY += 0.05;
							}
							else if (velY < 0) {
								velY += 0.15;
							}
							if (velY > 2.5) {
								velY = 2.5;
							}
						}
						
						
					}

				} else {
					
					float speed = 0.5f;
					if (phase == 2)
						speed = 0.75f;
					if (getHealth() <= maxHealth * 0.4f)
						speed = 1.2f;
					
					if (target != null);
					if (this.ticksExisted % 35 == 0) {
						
						if (getHealth() > maxHealth * 0.4f) {
//							if (Minecraft.getMinecraft() != null) {
//				            	MakeSound sound = new MakeSound();
//				    			sound.playSound("sounds/boss/Roar_0.wav", (Minecraft.getMinecraft().gameSettings.getSoundLevel(SoundCategory.MOBS) * Minecraft.getMinecraft().gameSettings.getSoundLevel(SoundCategory.MASTER)));
//				    		}
							if (dashed < 3) {
								dashed += 1;
							} else {
								this.eyesNeeded = 0;
								this.spawnEyes = true;
								this.eyes = 0;
							}
						} else {
							if (getHealth() < maxHealth * 0.25f) {
								speed = 1.4f;
//								if (Minecraft.getMinecraft() != null) {
//					            	MakeSound sound = new MakeSound();
//					    			sound.playSound("sounds/boss/Roar_2.wav", (Minecraft.getMinecraft().gameSettings.getSoundLevel(SoundCategory.MOBS) * Minecraft.getMinecraft().gameSettings.getSoundLevel(SoundCategory.MASTER)));
//					    		}
							} else {
								if (dashed < 6) {
									dashed += 1;
								} else {
									dashed = 0;
								}
								if (dashed < 3) {
//									if (Minecraft.getMinecraft() != null) {
//						            	MakeSound sound = new MakeSound();
//						    			sound.playSound("sounds/boss/Roar_0.wav", (Minecraft.getMinecraft().gameSettings.getSoundLevel(SoundCategory.MOBS) * Minecraft.getMinecraft().gameSettings.getSoundLevel(SoundCategory.MASTER)));
//						    		}
									speed = 1.0f;
								} else {
									speed = 1.2f;
//									if (Minecraft.getMinecraft() != null) {
//						            	MakeSound sound = new MakeSound();
//						    			sound.playSound("sounds/boss/Roar_2.wav", (Minecraft.getMinecraft().gameSettings.getSoundLevel(SoundCategory.MOBS) * Minecraft.getMinecraft().gameSettings.getSoundLevel(SoundCategory.MASTER)));
//						    		}
								}
							}
						}
						if (target != null) {
							if (velX > 4 * speed) {
								velX = 4 * speed;
							}
							if (velX < -4 * speed) {
								velX = -4 * speed;
							}
							
							if (velY > 4 * speed) {
								velY = 4 * speed;
							}
							if (velY < -4 * speed) {
								velY = -4 * speed;
							}
							
							if (velZ > 4 * speed) {
								velZ = 4 * speed;
							}
							if (velZ < -4 * speed) {
								velZ = -4 * speed;
							}
							
							if (velX > -4 * speed && this.posX > target.posX + target.getWidth()) {
								
//								if (velX < -4 * speed) {
									velX = -4 * speed;
//								}
							} else if (velX < 4 * speed && posX + 1 < target.posX) {
								
//								if (velX > 4 * speed) {
									velX = 4 * speed;
//								}
							}
							
							if (velZ > -4 * speed && this.posZ > target.posZ + target.getWidth()) {
								
//								if (velZ < -4 * speed) {
									velZ = -4 * speed;
//								}
							} else if (velZ < 4f * speed && posZ + 1 < target.posZ) {
								
//								if (velZ > 4 * speed) {
									velZ = 4 * speed;
//								}
							}
							
							if (velY > -4 * speed && posY > target.posY + target.getHeight()) {
//								velY -= 0.1f * speed;
//								if (velY > 2.5 * speed) {
//									velY -= 0.05 * speed;
//								} else if (velY > 0f) {
//									velY -= 0.15 * speed;
//								}
//								if (velY < -2.5 * speed) {
									velY = -4 * speed;
//								}
							} else if (velY < 4 * speed && posY + 1 < target.posY) {
//								velY += 0.1f * speed;
//								if (velY < -2.5 * speed) {
//									velY += 0.05 * speed;
//								}
//								else if (velY < 0) {
//									velY += 0.15 * speed;
//								}
//								if (velY > 2.5 * speed) {
									velY = 4 * speed;
//								}
							}
							
							
						}
					}
				}
			}
			if (getHealth() <= maxHealth * 0.65f) {
				phase = 2;
			}
			
			bounce = false;
			oldVelX = velX + 0;
			oldVelY = velY + 0;
			oldVelZ = velZ + 0;
			motionX = velX * 0.075f;
			motionY = velY * 0.075f;
			motionZ = velZ * 0.075f;
			
			
			
			
			if (velZ != 0) {
				double angle = Math.atan2(velX, velZ);
				
				
				this.ry = (float) (-1 * (float)Math.toDegrees(angle));
				
				double angle2 = Math.atan2(velY, velZ);
				
				this.rx = (float)angle2 * 180f / (float)Math.PI;
	
				
				this.rotationYaw = ry;
				
	//			if (velX < 0 && velZ > 0) {
	//				this.ry += 180;
	//			}
	//			
	//			if (velX > 0 && velZ < 0) {
	//				this.ry += 180;
	//			}
			}
			
			if (velX != 0) {
				
			}
			
			
		}
		
		
		
    	this.setMotion(motionX, motionY, motionZ);
    	this.dataManager.set(phase_data, phase);
    	
    	if (!world.isRemote) {
    		this.setHealth(this.bosshealth);
    	}
    	this.maxHurtTime = 0;
    	this.hurtResistantTime = 0;
    	
    	if (this.getDataManager().get(EntityEyeOfCthulhu.phase_data).intValue() != 1) {
			if (this.transformedRotation < 360 * 5 - 10) {
				this.transformedRotation += (360 * 5 - transformedRotation) * 0.08f;
				this.ry = (float) this.transformedRotation;
				this.rx = 0;
				this.rz = 0;
				this.velX = 0;
				this.velY = 0;
				this.velZ = 0;
				motionX = 0;
				motionY = 0;
				motionZ = 0;
				this.rotationYaw = ry;
			}
		}
    }
    
    public boolean attackEntityFrom(DamageSource source, float amount) {
    	if (transformedRotation > 0 && transformedRotation < 360 * 5 - 10) {
    		return false;
    	}
    	amount -= defense;
    	if (amount < 1) amount = 1;
    	this.bosshealth -= amount;
    	super.setHealth(this.bosshealth);
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
    
}
