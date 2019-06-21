package kmerrill285.trewrite.entities;

import java.util.List;

import kmerrill285.trewrite.Trewrite;
import kmerrill285.trewrite.core.inventory.InventorySlot;
import kmerrill285.trewrite.core.inventory.InventoryTerraria;
import kmerrill285.trewrite.core.inventory.container.ContainerTerrariaInventory;
import kmerrill285.trewrite.core.inventory.container.GuiContainerTerraria;
import kmerrill285.trewrite.core.items.ItemStackT;
import kmerrill285.trewrite.core.network.NetworkHandler;
import kmerrill285.trewrite.core.network.server.SPacketSyncInventoryTerraria;
import kmerrill285.trewrite.events.WorldEvents;
import kmerrill285.trewrite.items.ItemsT;
import kmerrill285.trewrite.util.Util;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.PacketDistributor;

public class EntityItemT extends Entity implements IEntityAdditionalSpawnData {

	public String item = "";
	public int stack = 1;
	
	public int pickupDelay = 0;
	
	public EntityItemT(World worldIn) {
		super(Trewrite.ITEM_ENTITY_TYPE, worldIn);
		this.setSize(0.5f, 0.5f);	
	}
	
	public void setItem(ItemStackT item) {
		this.item = item.item.itemName;
		this.stack = item.size;
	}
	
	public ItemStackT getItem() {
		return new ItemStackT(ItemsT.getItemFromString(item), stack);
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
	
	@Override
	public void tick() {
		super.tick();
		this.setNoGravity(false);
		this.age++;
		boolean moving = false;
		
		if (pickupDelay > 0) {
			pickupDelay--;
			this.move(MoverType.SELF, 0, -0.2f, 0);
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
		List<EntityPlayer> players = world.playerEntities;
		
			double dist = Integer.MAX_VALUE;
			EntityPlayer closest = null;
			for (EntityPlayer player : players) {
				double d = player.getPositionVector().distanceTo(getPositionVector());
				if (d < dist) {
					dist = d;
					closest = player;
				}
			}
			if (closest != null && dist < 3.0f) {
				String name = closest.getScoreboardName();
				InventoryTerraria inventory = WorldEvents.inventories.get(name);
				if (world.isRemote) inventory = ContainerTerrariaInventory.inventory;
				if (inventory != null && ItemsT.getItemFromString(item) != null && dead == false) {
					InventorySlot slot = null;
					
					for (int i = 0; i < inventory.hotbar.length; i++) {
						if (inventory.hotbar[i].stack == null && slot == null) {
							slot = inventory.hotbar[i];
						}
						if (inventory.hotbar[i].stack != null && inventory.hotbar[i].stack.item == ItemsT.getItemFromString(item)) {
							if (inventory.hotbar[i].stack.size < inventory.hotbar[i].stack.item.maxStack) {
								slot = inventory.hotbar[i];
								break;
							}
						}
					}
					
					if (slot == null)
					for (int i = 0; i < inventory.main.length; i++) {
						if (inventory.main[i].stack == null && slot == null) {
							slot = inventory.main[i];
						}
						if (inventory.main[i].stack != null && inventory.main[i].stack.item == ItemsT.getItemFromString(item)) {
							if (inventory.main[i].stack.size < inventory.main[i].stack.item.maxStack) {
								slot = inventory.main[i];
								break;
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
								if (!world.isRemote)
								slot.stack = new ItemStackT(ItemsT.getItemFromString(item), stack);
								stack = 0;
							} else {
								if (!world.isRemote) {
									slot.stack.size += stack;
									if (slot.stack.size > slot.stack.item.maxStack) {
										stack = slot.stack.size - slot.stack.item.maxStack;
										slot.stack.size = slot.stack.item.maxStack;
									} else {
										stack = 0;
									}
								} else {
									stack = 0;
								}
							}
							if (!world.isRemote) {
								final EntityPlayerMP player = (EntityPlayerMP)closest;
					 			NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new SPacketSyncInventoryTerraria(0, slot.area, slot.id, slot.stack));
							} else {
								Util.resetRecipes = true;
							}
						}
					}
				}
			}
		if (moving == false) {
			this.move(MoverType.SELF, 0, -0.2f, 0);
		}
	}

	public float lerp(float a, float b, float f) 
	{
	    return (a * (1.0f - f)) + (b * f);
	}
	
	public static DataParameter<Integer> stack_data = EntityDataManager.createKey(EntityItemT.class, DataSerializers.VARINT);
	public static DataParameter<String> item_data = EntityDataManager.createKey(EntityItemT.class, DataSerializers.STRING);
	@Override
	protected void registerData() {
		this.dataManager.register(stack_data, stack);
		this.dataManager.register(item_data, item);
	}
	
	@Override
	public void read(NBTTagCompound compound) {
		super.read(compound);
		age = compound.getInt("age");
		item = compound.getString("item");
		stack = compound.getInt("size");
	}

	@Override
	public void readAdditional(NBTTagCompound compound) {
		age = compound.getInt("age");
		item = compound.getString("item");
		stack = compound.getInt("size");
	}

	@Override
	public void writeAdditional(NBTTagCompound compound) {
		compound.setInt("age", age);
		compound.setString("item", item);
		compound.setInt("size", stack);
	}

	@Override
	public void writeSpawnData(PacketBuffer buffer) {
		buffer.writeString(item);
		buffer.writeInt(stack);
	}

	@Override
	public void readSpawnData(PacketBuffer additionalData) {
		item = additionalData.readString(100).trim();
		stack = additionalData.readInt();
	}

}
