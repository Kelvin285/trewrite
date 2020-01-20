package kmerrill285.trewrite.blocks;

import java.util.Random;

import kmerrill285.trewrite.core.inventory.InventorySlot;
import kmerrill285.trewrite.core.inventory.InventoryTerraria;
import kmerrill285.trewrite.events.WorldEvents;
import kmerrill285.trewrite.items.ItemsT;
import kmerrill285.trewrite.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.pathfinding.PathType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class DimensionBlock extends BlockT {


	
	public DimensionBlock(Properties properties) {
		super(properties.tickRandomly(), 0, 0, "dimension_block");
		this.drop = "null";
		this.pick = true;
		this.axe = true;
		this.hammer = true;
	}
	
	public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
	       entityIn.fall(fallDistance, 0.0F);
	       worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
	   }
	
	public void tick(BlockState state, World worldIn, BlockPos pos, Random random) {
		Vec3d p = new Vec3d(pos);
		boolean close = false;
		for (PlayerEntity player : worldIn.getPlayers()) {
			if (player == null) break;
			if (Math.sqrt(player.getDistanceSq(p)) <= 2) {
				close = true;
				break;
			}
		}
		if (close == false) {
			worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
		}
	}
	
	
}
