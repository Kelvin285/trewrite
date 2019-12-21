package kmerrill285.trewrite.world;

import kmerrill285.trewrite.entities.EntitiesT;
import kmerrill285.trewrite.util.Util;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntitySpawner {
	
	public static EntityType[] groundDaytime = {EntitiesT.BLUE_SLIME, EntitiesT.BUNNY};
	public static EntityType[] groundNighttime = {EntitiesT.ZOMBIE, EntitiesT.DEMON_EYE};
	public static EntityType[] groundWaterDaytime = {};
	public static EntityType[] groundWaterNighttime = {EntitiesT.DROWNED};
	public static EntityType[] skyEntities = {};
	public static EntityType[] skyWaterEntities = {};
	public static EntityType[] caveEntities = {};
	public static EntityType[] caveWaterEntities = {EntitiesT.DROWNED};
	public static EntityType[] underworldEntities = {};
	
	public static EntityType[] oceanEntities = {};
	
	public static void spawnEntities(PlayerEntity player, double x, double y, double z) {
		World world = player.getEntityWorld();
		
		if (world.getEntitiesWithinAABB(Entity.class, player.getBoundingBox().expand(50, 50, 50)).size() <= 30) {
			if (y <= Util.underworldLevel) return;
			
			if (player.posY >= Util.skyLevel) {
				if (!spawnSkyEntity(world, x, y, z)) spawnGroundEntity(world, x, y, z);
			}
			if (player.posY < Util.skyLevel && player.posY >= Util.surfaceLevel) {
				spawnGroundEntity(world, x, y, z);
			}
			if (player.posY < Util.surfaceLevel && player.posY >= Util.caveLevel) {
				spawnCaveEntity(world, x, y, z);
			}
			if (player.posY < Util.caveLevel) {
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
		Entity e = type.create(world, null, null, null, pos, SpawnReason.EVENT, false, false);
		e.setPosition(pos.getX(), pos.getY(), pos.getZ());
		if (world.getEntitiesWithinAABB(e.getClass(), e.getBoundingBox().expand(20,20,20)).size() <= 8)
		world.addEntity(e);
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
