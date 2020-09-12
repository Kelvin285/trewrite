package kmerrill285.trewrite.entities.npc;

import kmerrill285.trewrite.client.gui.dialog.DialogGui;
import kmerrill285.trewrite.client.gui.dialog.GuideDialog;
import kmerrill285.trewrite.entities.EntitiesT;
import kmerrill285.trewrite.network.NetworkHandler;
import kmerrill285.trewrite.network.client.CPacketOpenDialogGui;
import kmerrill285.trewrite.world.WorldStateHolder;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookAtWithoutMovingGoal;
import net.minecraft.entity.ai.goal.MoveTowardsRestrictionGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.NetworkHooks;

public class EntityGuide extends CreatureEntity implements IEntityAdditionalSpawnData {

	public static final int COPPER = 0, SILVER = 1, GOLD = 2, PLATINUM = 3;
	public int coin = EntityGuide.COPPER;
	public int amount = 1;
	
	public EntityGuide(EntityType<EntityGuide> type, World worldIn) {
		super(type, worldIn);
	}
	
	public EntityGuide(World worldIn) {
		super(EntitiesT.GUIDE, worldIn);
	}
		
	public EntityGuide(World worldIn, double x, double y, double z) {
		super(EntitiesT.GUIDE, worldIn);
		this.setPosition(x, y, z);
	}

	protected void registerGoals() {
	    this.goalSelector.addGoal(1, new LookAtGoal(this, MobEntity.class, 8.0F));	
	    
		this.goalSelector.addGoal(4, new MoveTowardsRestrictionGoal(this, 1.0D));
	    this.goalSelector.addGoal(8, new WaterAvoidingRandomWalkingGoal(this, 0.35D));
	    this.goalSelector.addGoal(9, new LookAtWithoutMovingGoal(this, PlayerEntity.class, 3.0F, 1.0F));

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
		return new EntitySize(0.5f, 2.0f, true);
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
	
	
	public void onRemovedFromWorld() {
		if (this.world.dimension.getType() == DimensionType.OVERWORLD) {
			WorldStateHolder.get(world).guideInWorld = false;
		}
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
		if (source.getTrueSource() instanceof PlayerEntity) return false;
		return super.attackEntityFrom(source, amount);
	}
	
	
	
	public boolean grabbed = false;
	@Override
	public void tick() {		
		super.tick();
		if (getAttackTarget() != null) {
			if (getAttackTarget().getPositionVec().distanceTo(getPositionVec()) >= 5) {
				this.setAttackTarget(null);
			}
			this.setPosition(prevPosX, prevPosY, prevPosZ);
		}
		else {
		}
		
	}
	
	public float getAiMoveSpeed() {
		if (getAttackTarget() instanceof PlayerEntity) {
			return 0;
		}
		return super.getAIMoveSpeed();
	}
	
	public ActionResultType applyPlayerInteraction(PlayerEntity player, Vec3d vec, Hand hand) {
		
		if (hand == Hand.OFF_HAND) {
			System.out.println("true");
			if (player.world.isRemote()) {
				NetworkHandler.INSTANCE.sendToServer(new CPacketOpenDialogGui(player.getScoreboardName()));
				
				DialogGui.currentDialog = new GuideDialog(null);
				this.setAttackTarget(player);
			}
		}
		
		return super.applyPlayerInteraction(player, vec, hand);
		
	}

	public float lerp(float a, float b, float f) 
	{
	    return (a * (1.0f - f)) + (b * f);
	}
	
	@Override
	protected void registerData() {
		super.registerData();
	}
	
	@Override
	public void read(CompoundNBT compound) {
//		System.out.println("read " + compound);
		super.read(compound);
	}

	@Override
	public void readAdditional(CompoundNBT compound) {
//		System.out.println("read additional " + compound);
	}

	@Override
	public void writeAdditional(CompoundNBT compound) {
//		System.out.println("write additional " + compound);
	}

	@Override
	public void writeSpawnData(PacketBuffer buffer) {
//		System.out.println("writeSpawnData " + buffer);
	}

	@Override
	public void readSpawnData(PacketBuffer additionalData) {
		
	}
	
	

	public static EntityGuide spawnGuide(World worldIn, BlockPos pos) {
		EntityGuide guide = EntitiesT.GUIDE.create(worldIn, null, null, null, pos, SpawnReason.EVENT, false, false);
		worldIn.addEntity(guide);
		return guide;
	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

}
