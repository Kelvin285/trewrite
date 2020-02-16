package kmerrill285.trewrite.entities;

import java.util.List;

import kmerrill285.trewrite.core.inventory.InventorySlot;
import kmerrill285.trewrite.core.inventory.InventoryTerraria;
import kmerrill285.trewrite.core.inventory.container.ContainerTerrariaInventory;
import kmerrill285.trewrite.core.items.ItemStackT;
import kmerrill285.trewrite.core.network.NetworkHandler;
import kmerrill285.trewrite.core.network.server.SPacketSyncInventoryTerraria;
import kmerrill285.trewrite.events.WorldEvents;
import kmerrill285.trewrite.items.ItemT;
import kmerrill285.trewrite.items.ItemsT;
import kmerrill285.trewrite.items.modifiers.ItemModifier;
import kmerrill285.trewrite.util.Util;
import kmerrill285.trewrite.world.WorldStateHolder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
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

public class EntityItemT extends Entity implements IEntityAdditionalSpawnData {

	public String item = "";
	public int stack = 1;
	public int modifier = -1;
	
	public int pickupDelay = 0;
	
	public EntityItemT(EntityType<EntityItemT> type, World worldIn) {
		super(type, worldIn);
		this.hitGround = true;
	}
	
	public EntityItemT(World worldIn) {
		super(EntitiesT.ITEM, worldIn);
		this.hitGround = true;
	}
		
	public EntityItemT(World worldIn, double x, double y, double z, ItemStackT stack) {
		super(EntitiesT.ITEM, worldIn);
		this.setPosition(x, y, z);
		if (stack == null) {
			this.item = ItemsT.getStringForItem(ItemsT.DIRT_BLOCK);
			this.stack = 1;
			this.modifier = -1;
		} else {
			this.item = ItemsT.getStringForItem(stack.item);
			this.stack = stack.size;
			this.modifier = stack.modifier;
		}
		this.hitGround = true;
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
	
	public void setItem(ItemStackT item) {
		
		this.item = ItemsT.getStringForItem(item.item);
		this.stack = item.size;
		this.modifier = item.modifier;
		
		if (item.size == 1) {
			this.stack = 1;
		}
	}
	
	public ItemStackT getItem() {
		if (item == null) {
			System.out.println("item is null!");
			return new ItemStackT(ItemsT.getItemFromString("dirt_block"), stack, ItemModifier.getModifier(modifier));
		}
		return new ItemStackT(ItemsT.getItemFromString(item), stack, ItemModifier.getModifier(modifier));
	}
	
	public boolean cannotPickup() {
		return true;
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
	
	public boolean hitGround = false;
	
	public void applyEntityCollision (Entity entityIn) {
		if (this.getItem() != null)
			if (this.getItem().item == ItemsT.FALLEN_STAR)
		if (hitGround == false) {
			if (entityIn instanceof LivingEntity) {
				if (!(entityIn instanceof PlayerEntity)) {
					entityIn.attackEntityFrom(DamageSource.GENERIC, 2500);
				}
			}
		}
	}
	
	public boolean grabbed = false;
	@Override
	public void tick() {
		
		
		
		if (this.onGround) hitGround = true;
		super.tick();
		
		
		if (!world.isRemote()) {
			if (this.getItem() != null) {
				if (this.getItem().item instanceof ItemT) {
					ItemT item = (ItemT)this.getItem().item;
					if (item.lightValue > 0)
					WorldStateHolder.get(world).setLight(getPosition(), item.lightValue, world.getDimension().getType());
				}
				if (this.getItem().item == ItemsT.FALLEN_STAR) {
					WorldStateHolder.get(world).setLight(getPosition(), 15, world.getDimension().getType());
				}
			}
		}
		
		this.setNoGravity(false);
		this.age++;
		boolean moving = false;
		
		
		
		if (hitGround == false) {
			this.move(MoverType.SELF, new Vec3d(0, -1.0f, 0));
		}
		
		if (this.getItem() != null) {
			if (this.getItem().item == ItemsT.FALLEN_STAR) {
				if (hitGround == false)
					for (int i = 0; i < 10; i++)
		    	 world.addParticle(ParticleTypes.END_ROD, posX, posY, posZ, rand.nextDouble() - 0.5f, rand.nextDouble(), rand.nextDouble() - 0.5f);

				
				if (world.getDayTime() % 24000 < 15000 || world.getDayTime() % 24000 > 22000) {
					this.remove();
				}
			}
		}
		
		if (pickupDelay > 0) {
			pickupDelay--;
			this.move(MoverType.SELF, new Vec3d(0, -0.4f, 0));
			return;
		}
		
		if (this.stack <= 0 || this.item == null || age > 60 * 5 * 20 || dead == true) { this.remove(); }
		
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
			if (closest != null && dist < 5.0f) {
				InventoryTerraria inventory = WorldEvents.getOrLoadInventory(closest);
				if (world.isRemote) inventory = ContainerTerrariaInventory.inventory;
				if (inventory != null && ItemsT.getItemFromString(item) != null && dead == false) {
					
					InventorySlot slot = null;
					
					for (int i = 0; i < inventory.hotbar.length; i++) {
						if (inventory.hotbar[i].stack == null && slot == null) {
							slot = inventory.hotbar[i];
						}
						if (inventory.hotbar[i].stack != null && inventory.hotbar[i].stack.item == ItemsT.getItemFromString(item)) {
							if (inventory.hotbar[i].stack.item instanceof ItemT) {
								if (inventory.hotbar[i].stack.size < ((ItemT)inventory.hotbar[i].stack.item).maxStack) {
									slot = inventory.hotbar[i];
									break;
								}
							} else {
								if (inventory.hotbar[i].stack.size < inventory.hotbar[i].stack.itemForRender.getMaxStackSize()) {
									slot = inventory.hotbar[i];
									break;
								}
							}
						}
					}
					
					if (slot == null)
					for (int i = 0; i < inventory.main.length; i++) {
						if (inventory.main[i].stack == null && slot == null) {
							slot = inventory.main[i];
						}
						if (inventory.main[i].stack != null && inventory.main[i].stack.item == ItemsT.getItemFromString(item)) {
							if (inventory.main[i].stack.item instanceof ItemT) {
								if (inventory.main[i].stack.size < ((ItemT)inventory.main[i].stack.item).maxStack) {
									slot = inventory.main[i];
									break;
								}
							} else {
								if (inventory.main[i].stack.size < inventory.main[i].stack.itemForRender.getMaxStackSize()) {
									slot = inventory.main[i];
									break;
								}
							}
						}
					}
					
					if (slot != null) {
						float newX = lerp((float)posX, (float)closest.posX, 0.35f);
						float newY = lerp((float)posY, (float)closest.posY, 0.35f);
						float newZ = lerp((float)posZ, (float)closest.posZ, 0.35f);
						
						this.posX = newX;
						this.posY = newY;
						this.posZ = newZ;
						moving = true;
						
						if (dist < 0.25f) {
							if (slot.stack == null) {
								if (!world.isRemote && grabbed == false) {
									slot.stack = new ItemStackT(ItemsT.getItemFromString(item), stack, ItemModifier.getModifier(modifier));
									stack = 0;
									
								}
							} else {
								if (!world.isRemote && grabbed == false) {
									slot.stack.size += stack;
									if (slot.stack.item instanceof ItemT) {
										if (slot.stack.size > ((ItemT)slot.stack.item).maxStack) {
											stack = slot.stack.size - ((ItemT)slot.stack.item).maxStack;
											slot.stack.size = ((ItemT)slot.stack.item).maxStack;
										} else {
											stack = 0;
										}
									} else {
										if (slot.stack.size > slot.stack.itemForRender.getMaxStackSize()) {
											stack = slot.stack.size - slot.stack.itemForRender.getMaxStackSize();
											slot.stack.size = slot.stack.itemForRender.getMaxStackSize();
										} else {
											stack = 0;
										}
									}
								} else {
									stack = 0;
								}
							}
							if (!world.isRemote) {
								final ServerPlayerEntity player = (ServerPlayerEntity)closest;
					            if (this.dead == false)
					            	if (this.removed == false) {
									      world.playSound((PlayerEntity)null, posX, posY, posZ, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.5F, 2.0f + this.rand.nextFloat() * 2.0f - 1.0f);

//								this.playSound(SoundEvents.ENTITY_ITEM_PICKUP, 0.4F, 2.0F + this.rand.nextFloat() * 2.0F);
					            	}
					            if (grabbed == false) {
					            	NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new SPacketSyncInventoryTerraria(0, slot.area, slot.id, slot.stack));
					            	grabbed = true;
					            	this.remove();
					            }
							} else {
//					               this.playSound(SoundEvents.ENTITY_ITEM_PICKUP, 0.4F, 2.0F + this.rand.nextFloat() * 0.4F);

								Util.resetRecipes = true;
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
	
	@Override
	protected void registerData() {
//		this.dataManager.register(stack_data, stack);
//		this.dataManager.register(item_data, item);
//		this.dataManager.register(pickup_delay, pickupDelay);
	}
	
	@Override
	public void read(CompoundNBT compound) {
//		System.out.println("read " + compound);
		super.read(compound);
		age = compound.getInt("age");
		item = compound.getString("item");
		stack = compound.getInt("size");
	}

	@Override
	public void readAdditional(CompoundNBT compound) {
//		System.out.println("read additional " + compound);
		age = compound.getInt("age");
		item = compound.getString("item");
		stack = compound.getInt("size");
	}

	@Override
	public void writeAdditional(CompoundNBT compound) {
//		System.out.println("write additional " + compound);
		compound.putInt("age", age);
		compound.putString("item", item);
		compound.putInt("size", stack);
	}

	@Override
	public void writeSpawnData(PacketBuffer buffer) {
//		System.out.println("writeSpawnData " + buffer);
		buffer.writeString(item);
		buffer.writeInt(stack);
		buffer.writeInt(pickupDelay);
	}

	@Override
	public void readSpawnData(PacketBuffer additionalData) {
//		System.out.println("READ SPAWN DATA: " + additionalData);

		item = additionalData.readString(100);
		stack = additionalData.readInt();
		pickupDelay = additionalData.readInt();
	}
	
	

	public static EntityItemT spawnItem(World worldIn, BlockPos pos, ItemStackT stack, int pickupDelay) {
		EntityItemT item = EntitiesT.ITEM.create(worldIn, null, null, null, pos, SpawnReason.EVENT, false, false);
		item.setItem(stack);
//		EntityItemT item = new EntityItemT(worldIn, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), stack);
		item.pickupDelay = pickupDelay;
		worldIn.addEntity(item);
		return item;
	}
	
	public static EntityItemT spawnItem(World worldIn, BlockPos pos, ItemStackT stack) {
		return EntityItemT.spawnItem(worldIn, pos, stack, 0);
	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

}
