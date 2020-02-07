package kmerrill285.trewrite.entities;

import java.util.List;

import kmerrill285.trewrite.core.inventory.InventorySlot;
import kmerrill285.trewrite.core.inventory.InventoryTerraria;
import kmerrill285.trewrite.core.inventory.container.ContainerTerrariaInventory;
import kmerrill285.trewrite.core.items.ItemStackT;
import kmerrill285.trewrite.core.network.NetworkHandler;
import kmerrill285.trewrite.core.network.server.SPacketSyncInventoryTerraria;
import kmerrill285.trewrite.events.ScoreboardEvents;
import kmerrill285.trewrite.events.WorldEvents;
import kmerrill285.trewrite.items.ItemT;
import kmerrill285.trewrite.items.ItemsT;
import kmerrill285.trewrite.items.modifiers.ItemModifier;
import kmerrill285.trewrite.util.Util;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.network.PacketDistributor;

public class EntityStar extends MobEntity {
	
	public EntityStar(EntityType<EntityStar> type, World worldIn) {
		super(type, worldIn);
	}
	
	public EntityStar(World worldIn) {
		super(EntitiesT.STAR, worldIn);
	}
		
	public EntityStar(World worldIn, double x, double y, double z) {
		super(EntitiesT.STAR, worldIn);
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

			if (closest != null && dist < 8.0f) {
				if (dist <= 2.0f)
				if (dead == false && ScoreboardEvents.getScore(closest.getWorldScoreboard(), closest, ScoreboardEvents.MANA).getScorePoints() < ScoreboardEvents.getScore(closest.getWorldScoreboard(), closest, ScoreboardEvents.MAX_MANA).getScorePoints()) {
					
					float newX = lerp((float)posX, (float)closest.posX, 0.35f);
					float newY = lerp((float)posY, (float)closest.posY, 0.35f);
					float newZ = lerp((float)posZ, (float)closest.posZ, 0.35f);
					
					this.posX = newX;
					this.posY = newY;
					this.posZ = newZ;
					moving = true;
					
					if (dist < 0.25f) {
						if (!world.isRemote) {
							final ServerPlayerEntity player = (ServerPlayerEntity)closest;
							
				            if (grabbed == false) {
				            	ScoreboardEvents.getScore(closest.getWorldScoreboard(), closest, ScoreboardEvents.MANA).increaseScore(100);
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
	
	
	

	public static EntityStar spawnStar(World worldIn, BlockPos pos) {
		EntityStar star = EntitiesT.STAR.create(worldIn, null, null, null, pos, SpawnReason.EVENT, false, false);
		worldIn.addEntity(star);
		return star;
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
