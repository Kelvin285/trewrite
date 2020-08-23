package kmerrill285.trewrite.entities;

import java.util.List;

import kmerrill285.trewrite.client.gui.inventory.InventorySlot;
import kmerrill285.trewrite.client.gui.inventory.InventoryTerraria;
import kmerrill285.trewrite.client.gui.inventory.container.ContainerTerrariaInventory;
import kmerrill285.trewrite.events.ScoreboardEvents;
import kmerrill285.trewrite.events.WorldEvents;
import kmerrill285.trewrite.items.ItemStackT;
import kmerrill285.trewrite.items.ItemT;
import kmerrill285.trewrite.items.ItemsT;
import kmerrill285.trewrite.items.modifiers.ItemModifier;
import kmerrill285.trewrite.network.NetworkHandler;
import kmerrill285.trewrite.network.server.SPacketSyncInventoryTerraria;
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

public class EntityHeart extends MobEntity {
	
	public EntityHeart(EntityType<EntityHeart> type, World worldIn) {
		super(type, worldIn);
	}
	
	public EntityHeart(World worldIn) {
		super(EntitiesT.HEART, worldIn);
	}
		
	public EntityHeart(World worldIn, double x, double y, double z) {
		super(EntitiesT.HEART, worldIn);
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
				int heartreach = ScoreboardEvents.getScore(closest.getWorldScoreboard(), closest, ScoreboardEvents.HEARTREACH).getScorePoints();
				if (heartreach > 0 && dist < 8.0f || dist <= 2.0f)
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
				            	player.heal(20);
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
	
	
	

	public static EntityHeart spawnHeart(World worldIn, BlockPos pos) {
		EntityHeart heart = EntitiesT.HEART.create(worldIn, null, null, null, pos, SpawnReason.EVENT, false, false);
		worldIn.addEntity(heart);
		return heart;
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
