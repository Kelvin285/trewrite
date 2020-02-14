package kmerrill285.trewrite.entities.projectiles;

import kmerrill285.trewrite.blocks.BlocksT;
import kmerrill285.trewrite.blocks.pots.Pot;
import kmerrill285.trewrite.core.items.ItemStackT;
import kmerrill285.trewrite.entities.EntitiesT;
import kmerrill285.trewrite.entities.EntityItemT;
import kmerrill285.trewrite.items.ItemT;
import kmerrill285.trewrite.items.ThrowableItem;
import kmerrill285.trewrite.world.WorldStateHolder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.SnowballEntity;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion.Mode;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class EntityThrowingT extends SnowballEntity
{

	public ItemT arrow;
	public float damage;
	public float knockback;
	
	public int lifetime;
    public boolean sticky = false;
    public boolean stuck = false;
    public boolean destroyTiles = false;
    public boolean bouncingAI = false;
    public int lightValue = 0;
    
    public double bounce = 0.5f;
    private boolean isExplosive = false;
	
    public LivingEntity thrower;
    
	public EntityThrowingT(World worldIn, LivingEntity shooter) {
		super(worldIn, shooter);
		this.thrower = shooter;
	}
	
	public LivingEntity getThrower() 
	{
		return thrower;
	}

	public EntityThrowingT(World worldIn, double x, double y, double z) {
		super(worldIn, x, y, z);
	}

	public EntityThrowingT(EntityType<? extends SnowballEntity> p_i50172_1_, World p_i50172_2_) {
		super(p_i50172_1_, p_i50172_2_);
	}
	
	public EntityThrowingT(World world) {
    	super(EntitiesT.THROWING, world);
    }
	
	public boolean hitGround = false;
	
    public double prevMotionX, prevMotionY, prevMotionZ;
    public double motionX, motionY, motionZ;

	public void tick() {
		if (!this.bouncingAI) {
			super.tick();

			if (arrow instanceof ThrowableItem) {
				((ThrowableItem)arrow).throwingTick(this);
			}
		}
		if (world.getBlockState(getPosition()).getBlock() instanceof Pot) {
			world.setBlockState(getPosition(), BlocksT.AIR_BLOCK.getDefaultState());
		}
		if (lightValue > 0) {
			WorldStateHolder.get(world).setLight(getPosition(), lightValue, world.getDimension().getType());
		}
		
		if (this.bouncingAI) {
			this.noClip = false;
			this.setNoGravity(true);
//			this.motionX = this.getMotion().x;
//			this.motionY = this.getMotion().y;
//			this.motionZ = this.getMotion().z;
			
			float height = 0.1f;
			if (world.getBlockState(getPosition().add(0, height-0.1f, 0)).isSolid()) {
				this.motionY = 0.0;
				this.posY+=0.05f;
			} else {
		        this.motionY -= 0.03;
			}
			if (world.getBlockState(getPosition().add(0, height+0.1f, 0)).isSolid()) {
				this.motionY = 0;
			}
			posY+=0.5;
			if (world.getBlockState(getPosition().add(-0.1f, height, 0)).isSolid()) {
				this.motionX = 0;
			}
			if (world.getBlockState(getPosition().add(0.1f, height, 0)).isSolid()) {
				this.motionX = 0;
			}
			if (world.getBlockState(getPosition().add(0, height, -0.1f)).isSolid()) {
				this.motionZ = 0;
			}
			if (world.getBlockState(getPosition().add(0, height, 0.1f)).isSolid()) {
				this.motionZ = 0;
			}
			posY-=0.5;
			
			
			if (this.stuck == true) {
	        	this.motionX = 0;
	        	this.motionY = 0;
	        	this.motionZ = 0;
	        }
	        
	        this.motionX *= 0.9800000190734863D;
	        this.motionY *= 0.9800000190734863D;
	        this.motionZ *= 0.9800000190734863D;
	        
	        if (this.onGround)
	        {
	            this.motionX *= 0.699999988079071D;
	            this.motionZ *= 0.699999988079071D;
//	            this.motionY *= -0.5D;
	        }
	        
	        if (this.stuck == true) {
	        	this.motionX = 0;
	        	this.motionY = 0;
	        	this.motionZ = 0;
	        }
			
			if (this.lifetime-- <= 0)
	        {

	            if (!this.world.isRemote)
	            {
	                this.explode();
	            }
	            

	        }
	        else
	        {
	            this.handleWaterMovement();
	            this.world.addParticle(ParticleTypes.SMOKE, this.posX, this.posY + 0.5D, this.posZ, 0.0D, 0.0D, 0.0D);
	        }
			 if (Math.abs(prevMotionY) > Math.abs(motionY) && motionY == 0) {
					motionY = -prevMotionY * bounce;
					if (this.sticky == true)
						this.stuck = true;
				}
		        
		        if (Math.abs(prevMotionX) > Math.abs(motionX) && motionX == 0) {
					motionX = -prevMotionX * bounce;
					if (this.sticky == true)
						this.stuck = true;
				}
		        
		        if (Math.abs(prevMotionZ) > Math.abs(motionZ) && motionZ == 0) {
					motionZ = -prevMotionZ * bounce;
					if (this.sticky == true)
						this.stuck = true;
				}
		        
		        this.prevMotionX = motionX + 0;
		        this.prevMotionY = motionY + 0;
		        this.prevMotionZ = motionZ + 0;
		        
		        if (this.stuck == true) {
		        	this.motionX = 0;
		        	this.motionY = 0;
		        	this.motionZ = 0;
		        }
			setMotion(motionX, motionY, motionZ);
			posX += motionX;
			posY += motionY;
			posZ += motionZ;
//			this.prevPosX = posX;
//			this.prevPosY = posY;
//			this.prevPosZ = posZ;
			
			for (LivingEntity entity : world.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(this.getPosition().add(-1, -1, -1), this.getPosition().add(1, 1, 1)))) {
				if (entity.getPositionVec().distanceTo(getPositionVec()) <= 1)
				{
					if (lifetime < 70 && entity.isInvulnerable() == false) {
						explode();
					}
				}
			}
			
		}
	}

	 protected float getGravityVelocity() {
	      return this.isExplosive ? 0.0F : 0.03F;
	   }
	
	public double getYOffset() 
	{
		return -0f;
	}
	
	public void explode() {
		if (!this.isExplosive) return;
		world.createExplosion(this, DamageSource.causeExplosionDamage(this.getThrower()), posX, posY, posZ, 3, false, this.destroyTiles ? Mode.BREAK : Mode.NONE);
		for (LivingEntity entity : world.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(this.getPosition().add(-3, -3, -3), this.getPosition().add(3, 3, 3)))) {
			if (entity.getPositionVec().distanceTo(getPositionVec()) <= 3)
			if (entity instanceof PlayerEntity) {
				if (entity == this.getThrower()) {
					entity.attackEntityFrom(DamageSource.causeExplosionDamage(this.getThrower()), this.damage);
				}
			} else {
				entity.attackEntityFrom(DamageSource.causeExplosionDamage(this.getThrower()), this.damage);
			}
		}
		WorldStateHolder.get(world).setLight(getPosition(), 15, world.getDimension().getType());
        this.remove();
	}
	
	public void setThrowingAttribs(int lifetime, boolean sticky, boolean destroyTiles, boolean bounce, int lightValue, boolean explosive) {
        this.lifetime = lifetime;
        this.lightValue = lightValue;
        this.bouncingAI = true;
        this.setMotion(this.getMotion().x * 0.5, this.getMotion().y * 0.5, this.getMotion().z * 0.5);
        this.sticky = sticky;
        this.destroyTiles = destroyTiles;
        this.bounce = 0.7f;
        if (bounce == true) {
        	this.bounce = 1.0f;
        }
        this.isExplosive = explosive;
        this.motionX = this.getMotion().x;
        this.motionY = this.getMotion().y;
        this.motionZ = this.getMotion().z;
        this.setMotion(0, 0, 0);
        this.setVelocity(0, 0, 0);
	}
	
	public void applyEntityCollision(Entity entityIn) {

	}
	
	public void remove() {
		super.remove();
		if (arrow != null) {
			ThrowableItem a = (ThrowableItem)arrow;
			if (rand.nextDouble() <= a.recovery) {
				EntityItemT.spawnItem(this.getEntityWorld(), this.getPosition(), new ItemStackT(arrow, 1));
			}
		}
		
	}
	
		/**
	    * Called when this EntityThrowable hits a block or entity.
	    */
	   protected void onImpact(RayTraceResult result) {
		 if (this.isExplosive == false)
	     if (arrow != null) {
	    	 
	    	 ThrowableItem a = (ThrowableItem)arrow;
	    	 if (isExplosive == false)
	    	 a.onImpact(result, this);
	     }
	     

	   }
	   @Override
		public IPacket<?> createSpawnPacket() {
			return NetworkHooks.getEntitySpawningPacket(this);
		}
   
}