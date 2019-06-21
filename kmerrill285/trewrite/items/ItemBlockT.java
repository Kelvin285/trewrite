package kmerrill285.trewrite.items;

import java.util.List;

import javax.annotation.Nullable;

import kmerrill285.trewrite.blocks.BlockT;
import kmerrill285.trewrite.core.inventory.InventoryTerraria;
import kmerrill285.trewrite.core.inventory.container.ContainerTerrariaInventory;
import kmerrill285.trewrite.events.WorldEvents;
import kmerrill285.trewrite.util.Util;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class ItemBlockT extends ItemT {

	public Block block;
	
	public ItemBlockT(BlockT blockIn, String name) {
		super(new Properties().group(ItemGroup.BUILDING_BLOCKS));
		setRegistryName(blockIn.getRegistryName());
		this.maxStack = 999;
		this.block = blockIn;
		ItemsT.items.put(name, this);
		this.itemName = name;
	}

	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		if (this.getBlock() instanceof BlockT)
			
			
		if (((BlockT)this.getBlock()).health > 0)
			tooltip.add(new TextComponentString("heals " + ((BlockT)this.getBlock()).health + " health"));
		if (((BlockT)this.getBlock()).mana > 0)
			tooltip.add(new TextComponentString("restores " + ((BlockT)this.getBlock()).mana + " mana"));
		if (((BlockT)this.getBlock()).consumable)
			tooltip.add(new TextComponentString("consumable"));
		if (((BlockT)this.getBlock()).material)
			tooltip.add(new TextComponentString("material"));
		if (((BlockT)this.getBlock()).tooltip.isEmpty() == false)
			tooltip.add(new TextComponentString(""+((BlockT)this.getBlock()).tooltip));
	}
	
	
	
	/**
	    * Called when this item is used when targetting a Block
	    */
	   public EnumActionResult onItemUse(ItemUseContext p_195939_1_) {
	      return this.tryPlace(new BlockItemUseContext(p_195939_1_));
	   }

	   public EnumActionResult tryPlace(BlockItemUseContext p_195942_1_) {
	      if (!p_195942_1_.canPlace()) {
	         return EnumActionResult.FAIL;
	      } else {
	         IBlockState iblockstate = this.getStateForPlacement(p_195942_1_);
	         if (iblockstate == null) {
	            return EnumActionResult.FAIL;
	         } else if (!this.placeBlock(p_195942_1_, iblockstate)) {
	            return EnumActionResult.FAIL;
	         } else {
	            BlockPos blockpos = p_195942_1_.getPos();
	            World world = p_195942_1_.getWorld();
	            EntityPlayer entityplayer = p_195942_1_.getPlayer();
	            ItemStack itemstack = p_195942_1_.getItem();
	            IBlockState iblockstate1 = world.getBlockState(blockpos);
	            Block block = iblockstate1.getBlock();
	            if (block == iblockstate.getBlock()) {
	               this.onBlockPlaced(blockpos, world, entityplayer, itemstack, iblockstate1);
	               block.onBlockPlacedBy(world, blockpos, iblockstate1, entityplayer, itemstack);
	               if (entityplayer instanceof EntityPlayerMP) {
	                  CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP)entityplayer, blockpos, itemstack);
	               }
	            }

	            SoundType soundtype = iblockstate1.getSoundType(world, blockpos, p_195942_1_.getPlayer());
	            world.playSound(entityplayer, blockpos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
	            if (world.isRemote && Util.terrariaInventory == false)
	            itemstack.shrink(1);
	            if (world.isRemote && Util.terrariaInventory == true)
	            if (ContainerTerrariaInventory.inventory.hotbar[ContainerTerrariaInventory.inventory.hotbarSelected].stack != null) {
		            ContainerTerrariaInventory.inventory.hotbar[ContainerTerrariaInventory.inventory.hotbarSelected].stack.size--;
		            if (ContainerTerrariaInventory.inventory.hotbar[ContainerTerrariaInventory.inventory.hotbarSelected].stack.size <= 0) {
		            	ContainerTerrariaInventory.inventory.hotbar[ContainerTerrariaInventory.inventory.hotbarSelected].stack = null;
		            }
	            }
	            if (!world.isRemote) {
	            	String name = entityplayer.getScoreboardName();
	            	InventoryTerraria inventory = WorldEvents.inventories.get(name);
	            	
	            	if (inventory.open == false) {
	            		itemstack.shrink(1);
	            	} else {
	            		if (inventory.hotbar[inventory.hotbarSelected].stack != null) {
				            inventory.hotbar[inventory.hotbarSelected].stack.size--;
				            if (inventory.hotbar[inventory.hotbarSelected].stack.size <= 0) {
				            	inventory.hotbar[inventory.hotbarSelected].stack = null;
				            }
			            }
	            	}
	            	
	            	
	            }
	            
	            return EnumActionResult.SUCCESS;
	         }
	      }
	   }

	   protected boolean onBlockPlaced(BlockPos p_195943_1_, World p_195943_2_, @Nullable EntityPlayer p_195943_3_, ItemStack p_195943_4_, IBlockState p_195943_5_) {
	      return setTileEntityNBT(p_195943_2_, p_195943_3_, p_195943_1_, p_195943_4_);
	   }

	   @Nullable
	   protected IBlockState getStateForPlacement(BlockItemUseContext p_195945_1_) {
	      IBlockState iblockstate = this.getBlock().getStateForPlacement(p_195945_1_);
	      return iblockstate != null && this.canPlace(p_195945_1_, iblockstate) ? iblockstate : null;
	   }

	   protected boolean canPlace(BlockItemUseContext p_195944_1_, IBlockState p_195944_2_) {
		   try {
	      return p_195944_2_.isValidPosition(p_195944_1_.getWorld(), p_195944_1_.getPos()) && p_195944_1_.getWorld().checkNoEntityCollision(p_195944_2_, p_195944_1_.getPos());
		   }catch (Exception e) {
			   
		   }
		   return true;
		   }

	   protected boolean placeBlock(BlockItemUseContext p_195941_1_, IBlockState p_195941_2_) {
	      return p_195941_1_.getWorld().setBlockState(p_195941_1_.getPos(), p_195941_2_, 11);
	   }

	   public static boolean setTileEntityNBT(World worldIn, @Nullable EntityPlayer player, BlockPos pos, ItemStack stackIn) {
	      MinecraftServer minecraftserver = worldIn.getServer();
	      if (minecraftserver == null) {
	         return false;
	      } else {
	         NBTTagCompound nbttagcompound = stackIn.getChildTag("BlockEntityTag");
	         if (nbttagcompound != null) {
	            TileEntity tileentity = worldIn.getTileEntity(pos);
	            if (tileentity != null) {
	               if (!worldIn.isRemote && tileentity.onlyOpsCanSetNbt() && (player == null || !player.canUseCommandBlock())) {
	                  return false;
	               }

	               NBTTagCompound nbttagcompound1 = tileentity.write(new NBTTagCompound());
	               NBTTagCompound nbttagcompound2 = nbttagcompound1.copy();
	               nbttagcompound1.merge(nbttagcompound);
	               nbttagcompound1.setInt("x", pos.getX());
	               nbttagcompound1.setInt("y", pos.getY());
	               nbttagcompound1.setInt("z", pos.getZ());
	               if (!nbttagcompound1.equals(nbttagcompound2)) {
	                  tileentity.read(nbttagcompound1);
	                  tileentity.markDirty();
	                  return true;
	               }
	            }
	         }

	         return false;
	      }
	   }

	   /**
	    * Returns the unlocalized name of this item.
	    */
	   public String getTranslationKey() {
	      return this.getBlock().getTranslationKey();
	   }

	   /**
	    * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
	    */
	   public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
	      if (this.isInGroup(group)) {
	         this.getBlock().fillItemGroup(group, items);
	      }

	   }
	   
	   public Block getBlock() {
		   return this.block;
	   }
}
