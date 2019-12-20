package kmerrill285.trewrite.entities.monsters;

import javax.annotation.Nullable;

import kmerrill285.trewrite.core.items.ItemStackT;
import kmerrill285.trewrite.entities.EntitiesT;
import kmerrill285.trewrite.entities.EntityItemT;
import kmerrill285.trewrite.items.ItemsT;
import kmerrill285.trewrite.util.Util;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class EntityBlueSlime extends SlimeEntity
{
    //float f = (float)this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getValue();
    //float f1 = (float)this.getAttribute(SharedMonsterAttributes.ATTACK_KNOCKBACK).getValue();
	
    public EntityBlueSlime(EntityType<? extends SlimeEntity> type, World worldIn) {
		super(type, worldIn);
		init();
	}

    public EntityBlueSlime(World world) {
    	super(EntitiesT.BLUE_SLIME, world);
    	init();
    }
    
    public void init() {
    	this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(25);
    	this.setHealth(25);
    }
    @Nullable
    public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
    	 this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(25);
         this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)(0.2F + 0.1F * (float)1));
         return spawnDataIn;

    }
    
    protected void setSlimeSize(int size, boolean resetHealth) {
        this.setPosition(this.posX, this.posY, this.posZ);
        this.recalculateSize();
       
        if (resetHealth) {
           this.setHealth(this.getMaxHealth());
        }

        this.experienceValue = 0;
        
     }

    public void dropLoot(DamageSource source, boolean b) {
		EntityItemT.spawnItem(this.getEntityWorld(), this.getPosition(), new ItemStackT(ItemsT.GEL, Util.randomValue(1, 3, rand), null));

    }
    
	public void remove() {		
    	super.remove();
    }
    
	/**
     * Returns the size of the slime.
     */
    public int getSlimeSize()
    {
        return 1;
    }

    protected void alterSquishAmount()
    {
        this.squishAmount *= 0.6F;
    }

    /**
     * Gets the amount of time the slime needs to wait between jumps.
     */
    protected int getJumpDelay()
    {
        return this.rand.nextInt(40) + 10;
    }

    /**
     * Applies a velocity to each of the entities pushing them away from each other. Args: entity
     */
    public void applyEntityCollision(Entity entityIn)
    {
        super.applyEntityCollision(entityIn);
    }

    /**
     * Called by a player entity when they collide with an entity
     */
    public void onCollideWithPlayer(PlayerEntity entityIn)
    {
    	if (entityIn != null)
        if (this.canDamagePlayer())
        {
            this.dealDamage(entityIn);
        }
    }
    
    protected void dealDamage(LivingEntity entityIn) {
        if (this.isAlive()) {
           int i = this.getSlimeSize();
           if (this.getDistanceSq(entityIn) < 0.6D * (double)i * 0.6D * (double)i && this.canEntityBeSeen(entityIn) && entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float)this.getAttackStrength())) {
              this.playSound(SoundEvents.ENTITY_SLIME_ATTACK, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
              this.applyEnchantments(this, entityIn);
           }
        }

     }

    /**
     * Indicates weather the slime is able to damage the player (based upon the slime's size)
     */
    protected boolean canDamagePlayer()
    {
        return true;
    }

    /**
     * Gets the amount of damage dealt to the player when "attacked" by the slime.
     */
    protected int getAttackStrength()
    {
        return 7;
    }

    /**
     * Returns the volume for the sounds this mob makes.
     */
    protected float getSoundVolume()
    {
        return 0.4F * (float)this.getSlimeSize();
    }

    /**
     * The speed it takes to move the entityliving's rotationPitch through the faceEntity method. This is only currently
     * use in wolves.
     */
    public int getVerticalFaceSpeed()
    {
        return 0;
    }

    /**
     * Returns true if the slime makes a sound when it jumps (based upon the slime's size)
     */
    protected boolean makesSoundOnJump()
    {
        return this.getSlimeSize() > 0;
    }

    /**
     * Returns true if the slime makes a sound when it lands after a jump (based upon the slime's size)
     */
    protected boolean makesSoundOnLand()
    {
        return this.getSlimeSize() > 2;
    }
}