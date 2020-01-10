package kmerrill285.trewrite.entities;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SpawnCondition {
	
	public static double VERY_COMMON = 1.0, COMMON = 0.5, UNCOMMON = 0.1, RARE = 0.025;
	
	public List<Block> blocks;
	public int minElevation;
	public int maxElevation;
	public double spawnChance;
	public boolean ocean;
	
	public SpawnCondition(int minElevation, int maxElevation, double spawnChance, Block...blocks) {
		this.minElevation = minElevation;
		this.maxElevation = maxElevation;
		this.ocean = false;
		
		this.blocks = Arrays.asList(blocks);
		this.spawnChance = spawnChance;
	}
	
	public SpawnCondition(int minElevation, int maxElevation, boolean ocean, double spawnChance, Block...blocks) {
		this.minElevation = minElevation;
		this.maxElevation = maxElevation;
		this.ocean = ocean;
		
		this.blocks = Arrays.asList(blocks);
		this.spawnChance = spawnChance;
	}
	
	public static boolean canSpawn(EntityType entity, BlockPos pos, World world, Random rand) {
		SpawnCondition condition = spawnConditions.get(entity);
		BlockPos pos2 = new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ());
		if (condition == null) return false;
		
		boolean spawnableBlock = false;
		for (int i = 0; i < 3; i++) {
			if (world.getBlockState(pos2).getMaterial().blocksMovement() == true) {
				spawnableBlock = condition.blocks.contains(world.getBlockState(pos2).getBlock());
				
				break;
			}
			pos2 = pos2.down();
		}
		
		
		boolean spawn = pos.getY() >= condition.minElevation && pos.getY() <= condition.maxElevation && spawnableBlock && rand.nextDouble() <= condition.spawnChance;
		if (condition.ocean) {
			if (new Vec3d(pos.getX(), pos.getY(), pos.getZ()).distanceTo(new Vec3d(0, pos.getY(), 0)) >= 5000) {
				return spawn;
			}
		}
		return spawn;
	}
	
	public static HashMap<EntityType<?>, SpawnCondition> spawnConditions = new HashMap<EntityType<?>, SpawnCondition>();
}
