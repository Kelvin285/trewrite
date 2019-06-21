package kmerrill285.trewrite.items;

import java.util.List;

import com.google.common.collect.Multimap;

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

public class Axe extends ItemT {

	public Axe() {
		super(new Properties().group(ItemGroup.TOOLS).maxStackSize(1));
		this.melee = true;
		this.maxStack = 1;
	}

	public void onUse(Entity entity, BlockPos pos, EntityPlayer player, World worldIn, EnumHand handIn) {
//		if (pos != null && player != null) {
//			Block block = worldIn.getBlockState(pos).getBlock();
//			 
//			if (block.getDefaultState() == Blocks.DIRT.getDefaultState() || 
//					block.getDefaultState() == Blocks.GRASS_BLOCK.getDefaultState()) {
//				
//				net.minecraft.util.SoundEvent e = SoundEvents.ITEM_SHOVEL_FLATTEN;
//				player.swingArm(handIn);
//				
//				SoundType sound = block.getDefaultState().getSoundType(worldIn, pos, player);
//				worldIn.playSound(null, pos, e, SoundCategory.BLOCKS, sound.getVolume(), sound.getPitch());
//				
//				worldIn.setBlockState(pos, Blocks.GRASS_PATH.getDefaultState());
//			}
//		}
	}
	
	
	
}
