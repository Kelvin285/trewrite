package kmerrill285.trewrite.world;

import java.util.List;

import kmerrill285.trewrite.entities.EntitiesT;
import kmerrill285.trewrite.entities.SpawnCondition;
import kmerrill285.trewrite.entities.monsters.bosses.EntityEowHead;
import kmerrill285.trewrite.entities.monsters.bosses.EntityEyeOfCthulhu;
import kmerrill285.trewrite.util.Util;
import kmerrill285.trewrite.world.dimension.DimensionRegistry;
import kmerrill285.trewrite.world.dimension.Dimensions;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.DimensionManager;

public class EntitySpawner {
	
	public static EntityType<?>[] groundDaytime = {EntitiesT.BLUE_SLIME, EntitiesT.BUNNY, EntitiesT.METEOR_HEAD};
	public static EntityType<?>[] groundNighttime = {EntitiesT.ZOMBIE, EntitiesT.DEMON_EYE, EntitiesT.METEOR_HEAD};
	public static EntityType<?>[] groundWaterDaytime = {EntitiesT.METEOR_HEAD};
	public static EntityType<?>[] groundWaterNighttime = {EntitiesT.DROWNED, EntitiesT.METEOR_HEAD};
	public static EntityType<?>[] skyEntities = {EntitiesT.METEOR_HEAD};
	public static EntityType<?>[] skyWaterEntities = {EntitiesT.METEOR_HEAD};
	public static EntityType<?>[] caveEntities = {EntitiesT.WORM_HEAD, EntitiesT.UNDEAD_MINER, EntitiesT.METEOR_HEAD};
	public static EntityType<?>[] caveWaterEntities = {EntitiesT.DROWNED, EntitiesT.METEOR_HEAD};
	public static EntityType<?>[] underworldEntities = {EntitiesT.METEOR_HEAD};
	
	public static EntityType<?>[] oceanEntities = {EntitiesT.METEOR_HEAD};
	
	public static void spawnEntities(PlayerEntity player, double x, double y, double z) {
		World world = player.getEntityWorld();
		
		DimensionType sky = DimensionManager.registerOrGetDimension(Dimensions.skyLocation, DimensionRegistry.skyDimension, null, true);
		DimensionType underground = DimensionManager.registerOrGetDimension(Dimensions.undergroundLocation, DimensionRegistry.undergroundDimension, null, true);
		DimensionType underworld = DimensionManager.registerOrGetDimension(Dimensions.underworldLocation, DimensionRegistry.underworldDimension, null, true);
		
		
		List<EntityEyeOfCthulhu> eocs = player.world.getEntitiesWithinAABB(EntityEyeOfCthulhu.class, new AxisAlignedBB(new BlockPos(player.posX - 150, player.posY - 25, player.posZ - 150), new BlockPos(player.posX + 150, player.posY + 25, player.posZ + 150)));
		if (eocs.size() > 0) {
			return;
		}
		
		List<EntityEowHead> eows = player.world.getEntitiesWithinAABB(EntityEowHead.class, new AxisAlignedBB(new BlockPos(player.posX - 150, player.posY - 25, player.posZ - 150), new BlockPos(player.posX + 150, player.posY + 25, player.posZ + 150)));
		if (eows.size() > 0) {
			return;
		}
		
		
		
		if (world.getEntitiesWithinAABB(Entity.class, player.getBoundingBox().expand(50, 50, 50)).size() <= 30) {
			if (y <= Util.underworldLevel) return;
			
			if (world.dimension.getType() == sky) {
				if (!spawnSkyEntity(world, x, y, z)) spawnGroundEntity(world, x, y, z);
			}
			if (world.dimension.getType() == DimensionType.OVERWORLD && y > 45) {
				spawnGroundEntity(world, x, y, z);
			}
			if (world.dimension.getType() == underground || world.dimension.getType() == DimensionType.OVERWORLD && y <= 15) {
				spawnCaveEntity(world, x, y, z);
			}
			if (world.dimension.getType() == underworld) {
				spawnUnderworldEntity(world, x, y, z);
			}
		}
		
	}
	
	public static boolean spawnSkyEntity(World world, double x, double y, double z) {
		BlockPos pos = new BlockPos(x, y, z);
		if (world.getBlockState(pos).getMaterial().blocksMovement())
			return false;
		EntityType[] list = EntitySpawner.skyEntities;
		
		if (world.getBlockState(pos).getMaterial() == Material.WATER) {
			list = EntitySpawner.skyWaterEntities;
		}
		if (list == null) return false;
		if (list.length == 0) return false;
		EntityType e = list[world.rand.nextInt(list.length)];
		if (e == null)
			return false;
		spawnEntityAt(e, pos, world);
		return true;
	}
	
	public static boolean spawnGroundEntity(World world, double x, double y, double z) {
		BlockPos spawnpoint = getSuitableEntitySpawnpoint(world, x, y, z);
		if (spawnpoint == null) return false;
		
		EntityType[] list = null;
		if (world.getDayTime() % 24000 > 15000 && world.getDayTime() % 24000 < 22000) {
			list = EntitySpawner.groundNighttime;
			if (world.getBlockState(spawnpoint).getMaterial() == Material.WATER) {
				list = EntitySpawner.groundWaterNighttime;
			}
		} else if (y < Util.skyLevel) {
			list = EntitySpawner.groundDaytime;
			if (world.getBlockState(spawnpoint).getMaterial() == Material.WATER) {
				list = EntitySpawner.groundWaterDaytime;
			}
		}
		if (list == null) return false;
		if (list.length == 0) return false;
		EntityType e = list[world.rand.nextInt(list.length)];
		if (e == null)
			return false;
		spawnEntityAt(e, spawnpoint, world);
		return true;
	}
	
	public static boolean spawnCaveEntity(World world, double x, double y, double z) {
		BlockPos spawnpoint = getSuitableEntitySpawnpoint(world, x, y, z);
		if (spawnpoint == null) return false;
		EntityType[] list = EntitySpawner.caveEntities;
		if (world.getBlockState(spawnpoint).getMaterial() == Material.WATER) {
			list = EntitySpawner.caveWaterEntities;
		}
		if (list == null) return false;
		if (list.length == 0) return false;
		EntityType e = list[world.rand.nextInt(list.length)];
		if (e == null)
			return false;
		spawnEntityAt(e, spawnpoint, world);
		return true;
	}
	
	public static boolean spawnUnderworldEntity(World world, double x, double y, double z) {
		BlockPos spawnpoint = getSuitableEntitySpawnpoint(world, x, y, z);
		if (spawnpoint == null) return false;
		EntityType[] list = EntitySpawner.underworldEntities;
		if (list == null) return false;
		if (list.length == 0) return false;
		EntityType e = list[world.rand.nextInt(list.length)];
		
		if (e == null)
			return false;
		spawnEntityAt(e, spawnpoint, world);
		return true;
	}
	
	public static void spawnEntityAt(EntityType type, BlockPos pos, World world) {
		
		if (SpawnCondition.canSpawn(type, pos, world, world.rand)) {
			Entity e = type.create(world, null, null, null, pos, SpawnReason.EVENT, false, false);
			e.setPosition(pos.getX(), pos.getY(), pos.getZ());
			if (world.getEntitiesWithinAABB(e.getClass(), e.getBoundingBox().expand(150,20,150)).size() <= 8)
			world.addEntity(e);
		}
	}
	
	public static BlockPos getSuitableEntitySpawnpoint(World world, double x, double y, double z) {
		
		BlockPos pos = new BlockPos(x, y, z);
		if (!world.getBlockState(pos).getMaterial().blocksMovement()) {
			if (!world.getBlockState(new BlockPos(x, y + 1, z)).getMaterial().blocksMovement()) {
				if (world.getBlockState(new BlockPos(x, y - 1, z)).getMaterial().blocksMovement()) {
					return pos;
				}
			}
		}
		
		if (!world.getBlockState(pos).getMaterial().blocksMovement()) {
			for (int i = 0; i < 20; i++) {
				if (world.getBlockState(new BlockPos(x, y - i, z)).getMaterial().blocksMovement()) {
					return new BlockPos(x, y - i + 1, z);
				}
			}
		}
		
		if (world.getBlockState(new BlockPos(x, y, z)).getMaterial().blocksMovement())
		{
			int closestUpAir = 0;
			int closestDownAir = 0;
			for (closestUpAir = 0; closestUpAir < 10; closestUpAir++) {
				if (world.getBlockState(new BlockPos(x, y + closestUpAir, z)).getMaterial().blocksMovement() == false) {
					if (world.getBlockState(new BlockPos(x, y + closestUpAir + 1, z)).getMaterial().blocksMovement() == false) {
						break;
					}
				}
			}
			for (closestDownAir = 0; closestDownAir < 10; closestDownAir++) {
				if (world.getBlockState(new BlockPos(x, y - closestUpAir, z)).getMaterial().blocksMovement() == false) {
					if (world.getBlockState(new BlockPos(x, y - closestUpAir + 1, z)).getMaterial().blocksMovement() == false) {
						break;
					}
				}
			}
			if (closestUpAir < closestDownAir) {
				y = y + closestUpAir;
			} else {
				y = y - closestDownAir;
			}
		}
		if (world.getBlockState(new BlockPos(x, y, z)).getMaterial().blocksMovement())
			return null;
		if (!world.getBlockState(new BlockPos(x, y - 1, z)).getMaterial().blocksMovement())
			return null;
		return new BlockPos(x, y, z);
	}
	
}
