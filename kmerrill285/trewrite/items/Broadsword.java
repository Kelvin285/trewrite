package kmerrill285.trewrite.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Broadsword extends ItemT {

	public Broadsword() {
		super(new Properties().group(ItemGroup.COMBAT).maxStackSize(1));
		this.melee = true;
		this.maxStack = 1;
	}
	
	public boolean onAttack(Entity target, BlockPos pos, EntityPlayer player, World worldIn, EnumHand handIn) {
		return super.onAttack(target, pos, player, worldIn, handIn);
	}
	
}

