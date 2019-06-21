package kmerrill285.trewrite.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Shortsword extends Broadsword {

	public Shortsword() {
		super();
		this.melee = true;
	}
	
	public boolean onAttack(Entity target, BlockPos pos, EntityPlayer player, World worldIn, EnumHand handIn) {
		if (target != null)
			if (target.getPositionVector().distanceTo(player.getPositionVector()) <= 1.25f)
				return super.onAttack(target, pos, player, worldIn, handIn);
		return false;
	}
	
}

