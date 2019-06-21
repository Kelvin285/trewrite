package kmerrill285.trewrite.items;

import java.util.List;

import com.google.common.collect.Multimap;

import kmerrill285.trewrite.blocks.BlocksT;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Item.Properties;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class Pickaxe extends ItemT {

	public Pickaxe() {
		super(new Properties().group(ItemGroup.TOOLS).maxStackSize(1));
		this.melee = true;
		this.maxStack = 1;
	}

	public void onUse(Entity entity, BlockPos pos, EntityPlayer player, World worldIn, EnumHand handIn) {
		if (pos != null && player != null) {
			Block block = worldIn.getBlockState(pos).getBlock();
			if (block.getDefaultState() == BlocksT.GRASS_PATH.getDefaultState()) {
				List<EntityLiving> list = worldIn.getEntitiesWithinAABB(EntityLiving.class, new AxisAlignedBB(pos.getX() - 1, pos.getY(), pos.getZ() - 1, pos.getX() + 1, pos.getY() + 3, pos.getZ()));
				
				for (EntityLiving en : list) {
					if (en.onGround == true && en.isAirBorne == false)
					en.move(MoverType.SHULKER_BOX, 0, 0.25f, 0.0f);
				}
				if (player.getPositionVector().distanceTo(new Vec3d(pos.getX(), pos.getY(), pos.getZ())) <= 2.0f) {
					player.move(MoverType.SHULKER_BOX, 0, 0.25f, 0.0f);
					player.posY+=0.25f;
				}
				
				
				net.minecraft.util.SoundEvent e = SoundEvents.ITEM_HOE_TILL;
				player.swingArm(handIn);
				
				SoundType sound = block.getDefaultState().getSoundType(worldIn, pos, player);
				worldIn.playSound(null, pos, e, SoundCategory.BLOCKS, sound.getVolume(), sound.getPitch());
				
				worldIn.setBlockState(pos, BlocksT.DIRT_BLOCK.getDefaultState());
				
				
			}
			 
			if (block.getDefaultState() == BlocksT.GRASS_BLOCK.getDefaultState()) {
				
				net.minecraft.util.SoundEvent e = SoundEvents.ITEM_HOE_TILL;
				player.swingArm(handIn);
				
				SoundType sound = block.getDefaultState().getSoundType(worldIn, pos, player);
				worldIn.playSound(null, pos, e, SoundCategory.BLOCKS, sound.getVolume(), sound.getPitch());
				
				worldIn.setBlockState(pos, BlocksT.DIRT_BLOCK.getDefaultState());
			}
		}
	}
	
	
	
}
