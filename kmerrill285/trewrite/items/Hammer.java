package kmerrill285.trewrite.items;

import kmerrill285.trewrite.blocks.BlocksT;
import kmerrill285.trewrite.blocks.Platform;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Hammer extends ItemT {

	public Hammer() {
		super(new Properties().group(ItemGroup.TOOLS).maxStackSize(1));
		this.melee = true;
		this.maxStack = 1;
	}

	public void onUse(Entity entity, BlockPos pos, EntityPlayer player, World worldIn, EnumHand handIn) {
		if (pos != null && player != null) {
			Block block = worldIn.getBlockState(pos).getBlock();
			 
			if (block.getDefaultState() == BlocksT.DIRT_BLOCK.getDefaultState() || 
					block.getDefaultState() == BlocksT.GRASS_BLOCK.getDefaultState()) {
				
				net.minecraft.util.SoundEvent e = SoundEvents.ITEM_SHOVEL_FLATTEN;
				player.swingArm(handIn);
				
				SoundType sound = block.getDefaultState().getSoundType(worldIn, pos, player);
				worldIn.playSound(null, pos, e, SoundCategory.BLOCKS, sound.getVolume(), sound.getPitch());
				
				worldIn.setBlockState(pos, BlocksT.GRASS_PATH.getDefaultState());
			}
			if (block instanceof Platform) {
				SlabType slabtype = worldIn.getBlockState(pos).get(Platform.TYPE);
				Boolean waterlogged = worldIn.getBlockState(pos).get(Platform.WATERLOGGED);
				if (slabtype == SlabType.BOTTOM) {
					worldIn.setBlockState(pos, block.getDefaultState().with(Platform.TYPE, SlabType.TOP).with(Platform.WATERLOGGED, waterlogged));
				}
				if (slabtype == SlabType.TOP) {
					worldIn.setBlockState(pos, block.getDefaultState().with(Platform.TYPE, SlabType.BOTTOM).with(Platform.WATERLOGGED, waterlogged));
				}
			}
		}
	}
	
	
	
}
