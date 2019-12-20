package kmerrill285.trewrite.world.biome.provider;

import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.annotation.Nullable;

import com.google.common.collect.Sets;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import kmerrill285.trewrite.util.Util;
import kmerrill285.trewrite.world.biome.BiomeT;
import net.minecraft.block.BlockState;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.biome.provider.OverworldBiomeProviderSettings;
import net.minecraft.world.gen.SimplexNoiseGenerator;
import net.minecraft.world.gen.feature.structure.Structure;

public class TerrariaBiomeProvider extends BiomeProvider {
	   public static final Biome[] biomes = new Biome[]{BiomeT.LOWLANDS, BiomeT.HIGHLANDS, BiomeT.CORRUPT_LOWLANDS, BiomeT.CORRUPT_HIGHLANDS};
	   
	   private final SharedSeedRandom randomTemp;
	   private final SharedSeedRandom randomHumidity;
	   private final SharedSeedRandom randomVariation;
	   private final SharedSeedRandom randomCorruption;

	   private final SimplexNoiseGenerator genElevation;
	   private final SimplexNoiseGenerator genTemperature;
	   private final SimplexNoiseGenerator genHumidity;
	   private final SimplexNoiseGenerator genBiomeVariation;
	   private final SimplexNoiseGenerator genCorruption;
	   private final SharedSeedRandom random;
	   private final OverworldBiomeProviderSettings settingsProvider;
	   public TerrariaBiomeProvider(OverworldBiomeProviderSettings settingsProvider) {
			  this.random = new SharedSeedRandom(settingsProvider.getWorldInfo().getSeed());
			  this.randomTemp = new SharedSeedRandom(settingsProvider.getWorldInfo().getSeed() + 1L);
			  this.randomHumidity = new SharedSeedRandom(settingsProvider.getWorldInfo().getSeed() + 2L);
			  this.randomVariation = new SharedSeedRandom(settingsProvider.getWorldInfo().getSeed() + 3L);
			  this.randomCorruption = new SharedSeedRandom(settingsProvider.getWorldInfo().getSeed() + 4L);

		      this.random.skip(17292);
		      this.genElevation = new SimplexNoiseGenerator(this.random);
		      this.genTemperature = new SimplexNoiseGenerator(this.randomTemp);
		      this.genHumidity = new SimplexNoiseGenerator(this.randomHumidity);
		      this.genBiomeVariation = new SimplexNoiseGenerator(this.randomVariation);
		      this.genCorruption = new SimplexNoiseGenerator(this.randomCorruption);
		      this.settingsProvider = settingsProvider;
	   }

	   /**
	    * Gets the biome from the provided coordinates
	    */
	   public Biome getBiome(int x, int y) {
		   
		   double X = x;
		   double Y = y;
		   
		   
		   
		   double extra = genBiomeVariation.getValue(X / 10.0, Y / 10.0) * 0.2f;
		   
		   double temp = genTemperature.getValue(X / 2500.0, Y / 2500.0) + extra * 0.05f;
		   double humidity = genHumidity.getValue(X / 2500.0, Y / 2500.0) + extra * 0.05f;
		   double elevation = genElevation.getValue(X / 2500.0, Y / 2500.0) + extra * 0.05f; 
		   double landmass = genElevation.getValue(X / 5000.0, Y / 5000.0) + extra * 0.05f;
		   double variation = genBiomeVariation.getValue(X / 100.0, Y / 100.0) + extra * 0.05f;
		   double corruption = genCorruption.getValue(X / 500.0, Y / 500.0) + extra * 0.05f;
		   
		   
		   boolean corrupted = corruption > 0.5f;
		   
		   double distance = Math.sqrt(X * X + Y * Y);
		   
		   Biome current = BiomeT.LOWLANDS;
		   
		   int landState = 0;
		   int OCEAN = -2, VERY_LOW = -1, LOWLANDS = 0, HILLS = 1, MOUNTAINS = 2;
		   int humidState = 0;
		   int DRY = 0, NORMAL = 1, HUMID = 2;
		   int tempState = 0;
		   int FREEZING = -1, COLD = -2, WARM = 1, HOT = 2;
		   
		   landState = VERY_LOW;
		   if (elevation > -0.5F) landState = LOWLANDS;
		   if (elevation > 0.0F) landState = HILLS;
		   if (elevation > 0.5F) landState = MOUNTAINS;
		   
		   humidState = DRY;
		   if (humidity > -0.5) humidState = NORMAL;
		   if (humidity > 0.5) humidState = HUMID;
		   
		   tempState = FREEZING;
		   if (temp > -0.5) tempState = COLD;
		   if (temp > 0) tempState = WARM;
		   if (temp > 0.5) tempState = HOT;
		   
		   if (humidState == DRY)
		   {
			   if (tempState == HOT) {
				   
				   if (landState == LOWLANDS) {
					   current = BiomeT.DESERT;
				   }
				   
				   if (landState == HILLS) {
					   current = BiomeT.DESERT_HILLS;
				   }
				   
				   if (landState == MOUNTAINS) {
					   current = BiomeT.MASSIVE_PLATEAU;
				   }
				   
				   if (landState == VERY_LOW) {
					   current = BiomeT.MESA;
				   }
				   
				   
			   }
			   if (tempState == FREEZING) {
				   if (landState == VERY_LOW) {
					   current = BiomeT.COLD_STONE_PLAINS;
				   }
				   if (landState == LOWLANDS) {
					   current = BiomeT.ARCTIC_DESERT;
				   }
				   if (landState == HILLS) {
					   current = BiomeT.FROZEN_HILLS;
				   }
				   if (landState == MOUNTAINS) {
					   current = BiomeT.FROZEN_HIGHLANDS;
				   }
			   }
			   
			   if (tempState == COLD) {
				   if (landState == VERY_LOW) {
					   current = BiomeT.LOW_BOREAL_FOREST;
				   }
				   if (landState == LOWLANDS) {
					   current = BiomeT.TAIGA_LOWLANDS;
				   }
				   if (landState == HILLS) {
					   current = BiomeT.STONE_HILLS;
				   }
				   if (landState == MOUNTAINS) {
					   current = BiomeT.STONE_HIGHLANDS;
				   }
				   
			   }
			   if (tempState == WARM) {
				   if (landState == VERY_LOW) {
					   current = BiomeT.SAVANNA;
				   }
				   if (landState == LOWLANDS) {
					   current = BiomeT.DRY_LOWLANDS;
				   }
				   if (landState == HILLS) {
					   current = BiomeT.DESERT_HILLS;
				   }
				   if (landState == MOUNTAINS) {
					   current = BiomeT.MASSIVE_PLATEAU;
				   }
			   }
		   }
		   
		   if (humidState == NORMAL) {
			   if (tempState == WARM) {
				   if (landState == LOWLANDS) {
					   current = BiomeT.LOWLANDS;
				   }
				   if (landState == HILLS) {
					   current = BiomeT.MIDLANDS;
				   }
				   if (landState == MOUNTAINS) {
					   current = BiomeT.HIGHLANDS;
				   }
				   if (landState == VERY_LOW) {
					   current = BiomeT.SAVANNA_MIDLANDS;
				   }
			   }
			   if (tempState == FREEZING) {
				   if (landState == VERY_LOW) {
					   current = BiomeT.ICE_SPIKES;
				   }
				   if (landState == HILLS) {
					   current = BiomeT.BOREAL_HILLS;
				   }
				   if (landState == MOUNTAINS) {
					   current = BiomeT.BOREAL_MOUNTAINS;
				   }
			   }
			   if (tempState == COLD ) {
				   if (landState == VERY_LOW) {
					   current = BiomeT.LIVINGWOOD_FOREST;
				   }
				   if (landState == LOWLANDS) {
					   current = BiomeT.SHRUBLANDS;
				   }
				   if (landState == HILLS) {
					   current = BiomeT.SPRUCE_HILLS;
				   }
				   if (landState == MOUNTAINS) {
					   current = BiomeT.SPRUCE_MOUNTAINS;
				   }
			   }
			   if (tempState == HOT) {
				   if (landState == VERY_LOW) {
					   current = BiomeT.LOWLANDS_FOREST;
				   }
				   if (landState == LOWLANDS) {
					   current = BiomeT.DRY_LOWLANDS;
				   }
				   if (landState == HILLS) {
					   current = BiomeT.MESA_HILLS;
				   }
				   if (landState == MOUNTAINS) {
					   current = BiomeT.DRY_FOREST;
				   }
			   }
		   }
		   
		   
		   if (humidState == HUMID) {
			   if (tempState == WARM) {
				   if (landState == VERY_LOW) {
					   current = BiomeT.SWAMP;
				   }
				   if (landState == HILLS) {
					   current = BiomeT.HUMID_MIDLANDS;
				   }
			   }
			   if (tempState == WARM) {
				   if (landState == LOWLANDS) {
					   current = BiomeT.LOWLANDS_FOREST;
				   }
				   if (landState == HILLS) {
					   current = BiomeT.HUMID_MIDLANDS;
				   }
				   if (landState == MOUNTAINS) {
					   current = BiomeT.MUSHROOM_HIGHLANDS;
				   }
				   
				   if (landState == VERY_LOW) {
					   current = BiomeT.BOG;
				   }
			   }
			   if (tempState == HOT) {
				   if (landState == LOWLANDS) {
					   current = BiomeT.JUNGLE;
				   }
				   if (landState == HILLS) {
					   current = BiomeT.MUSHROOM_MIDLANDS;
				   }
				   if (landState == MOUNTAINS) {
					   current = BiomeT.HUMID_HIGHLANDS;
				   }
			   }
			   if (tempState == FREEZING) {
				   if (landState == VERY_LOW) {
					   current = BiomeT.FROZEN_PLAINS;
				   }
				   if (landState == LOWLANDS) {
					   current = BiomeT.FROZEN_PLAINS;
				   }
				   if (landState == HILLS) {
					   current = BiomeT.ICE_SPIKES_HILLS;
				   }
				   if (landState == MOUNTAINS) {
					   current = BiomeT.ICE_PEAKS;
				   }
			   }
			   if (tempState == COLD) {
				   if (landState == LOWLANDS) {
					   current = BiomeT.LOW_BOREAL_FOREST;
				   }
				   if (landState == HILLS) {
					   current = BiomeT.TERRACED_MIDLANDS;
				   }
				   if (landState == MOUNTAINS) {
					   current = BiomeT.TERRACED_HIGHLANDS;
				   }
			   }
		   }
		   
		   
		   
		   if (corrupted) {
			   if (current == BiomeT.LOWLANDS || current == BiomeT.MIDLANDS || current == BiomeT.HUMID_MIDLANDS) current = BiomeT.CORRUPT_LOWLANDS;
			   if (current == BiomeT.HIGHLANDS || current == BiomeT.HUMID_HIGHLANDS) current = BiomeT.CORRUPT_HIGHLANDS;
			   if (current == BiomeT.SWAMP) current = BiomeT.BOG;
			   if (current == BiomeT.DESERT) current = BiomeT.CORRUPT_DESERT;
			   if (current == BiomeT.DESERT_HILLS) current = BiomeT.CORRUPT_DESERT_HILLS;
			   if (current == BiomeT.JUNGLE) current = BiomeT.CORRUPT_JUNGLE;
			   if (current == BiomeT.JUNGLE_HIGHLANDS) current = BiomeT.CORRUPT_JUNGLE_HIGHLANDS; 
		   }
		   
		   if (distance > 4000 + variation && distance + variation < 4000 + 1000) {
			   double angle = Math.toDegrees(Util.getAngle((int)X, (int)Y));
			   long seed = settingsProvider.getWorldInfo().getSeed();
			   boolean northDesert = seed % 2 == 0;
			   if (angle < 0) angle += 360;
			   if (angle > 360) angle -= 360;
			   if (!northDesert) {
				   if (angle > 0 && angle < 180) {
					   current = BiomeT.JUNGLE;
					   if (landState == MOUNTAINS || landState == HILLS) {
						   current = BiomeT.JUNGLE_HIGHLANDS;
					   }
				   }
				   
			   } else {
				   if (angle > 180 && angle < 360) {
					   current = BiomeT.JUNGLE;
					   if (landState == MOUNTAINS || landState == HILLS) {
						   current = BiomeT.JUNGLE_HIGHLANDS;
					   }
				   }
			   }
		   }
		   
		   if (distance > 500 + variation && distance + variation < 750) {
			   double angle = Math.toDegrees(Util.getAngle((int)X, (int)Y));
			   long seed = settingsProvider.getWorldInfo().getSeed();
			   boolean northDesert = seed % 2 == 0;
			   boolean eastBog = seed % 2 == 1;
			   if (angle < 0) angle += 360;
			   if (angle > 360) angle -= 360;
			   if (northDesert) {
				   if (angle > 45 && angle < 180 - 45) {
					   current = BiomeT.DESERT;
					   if (landState == MOUNTAINS || landState == HILLS) {
						   current = BiomeT.DESERT_HILLS;
					   }
				   }
				   if (angle > 45 + 180 && angle < 180 - 45 + 180) {
					   current = BiomeT.BOREAL_HILLS;
					   if (landState == MOUNTAINS || landState == HILLS) {
						   current = BiomeT.BOREAL_MOUNTAINS;
					   }
				   }
			   } else {
				   if (angle > 45 && angle < 180 - 45) {
					   current = BiomeT.BOREAL_HILLS;
					   if (landState == MOUNTAINS || landState == HILLS) {
						   current = BiomeT.BOREAL_MOUNTAINS;
					   }
				   }
				   if (angle > 45 + 180 && angle < 180 - 45 + 180) {
					   current = BiomeT.DESERT;
					   if (landState == MOUNTAINS || landState == HILLS) {
						   current = BiomeT.DESERT_HILLS;
					   }
				   }
			   }
			   if (eastBog) {
				   if (angle > 360 - 45 || angle < 45) {
					   current = BiomeT.BOG;
				   }
				   if (angle > 180 - 45 && angle < 180 + 45) {
					   current = BiomeT.LIVINGWOOD_FOREST;
				   }
			   } else {
				   if (eastBog) {
					   if (angle > 360 - 45 || angle < 45) {
						   current = BiomeT.LIVINGWOOD_FOREST;
					   }
					   if (angle > 180 - 45 && angle < 180 + 45) {
						   current = BiomeT.BOG;
					   }
				   }
			   }
		   }
		   
		   if (distance > 5000) {
			   current = BiomeT.BEACH;
			   if (distance > 5150) {
				   current = BiomeT.SHALLOW_OCEAN;
			   }
			   if (distance > 5250) {
				   if (landState == LOWLANDS || landState == VERY_LOW) {
					   if (tempState == COLD) {
						   current = BiomeT.DEEP_COLD_OCEAN;
					   }
					   if (tempState == FREEZING) {
						   current = BiomeT.DEEP_FROZEN_OCEAN;
					   }
					   if (tempState == WARM || tempState == HOT) {
						   current = BiomeT.DEEP_WARM_OCEAN;
					   }
				   } else {
					   if (tempState == COLD) {
						   current = BiomeT.COLD_OCEAN;
					   }
					   if (tempState == FREEZING) {
						   current = BiomeT.FROZEN_OCEAN;
					   }
					   if (tempState == WARM || tempState == HOT) {
						   current = BiomeT.WARM_OCEAN;
					   }
				   }
			   }
		   }
		   
		   
		   return current;
	   }

	   public Biome[] getBiomes(int x, int z, int width, int length, boolean cacheFlag) {
		      Biome[] abiome = new Biome[width * length];
		      Long2ObjectMap<Biome> long2objectmap = new Long2ObjectOpenHashMap<>();

		      for(int i = 0; i < width; ++i) {
		         for(int j = 0; j < length; ++j) {
		            int k = i + x;
		            int l = j + z;
		            long i1 = ChunkPos.asLong(k, l);
		            Biome biome = long2objectmap.get(i1);
		            if (biome == null) {
		               biome = this.getBiome(k, l);
		               long2objectmap.put(i1, biome);
		            }

		            abiome[i + j * width] = biome;
		         }
		      }

		      return abiome;
		   }

	   public Set<Biome> getBiomesInSquare(int centerX, int centerZ, int sideLength) {
		      int i = centerX - sideLength >> 2;
		      int j = centerZ - sideLength >> 2;
		      int k = centerX + sideLength >> 2;
		      int l = centerZ + sideLength >> 2;
		      int i1 = k - i + 1;
		      int j1 = l - j + 1;
		      return Sets.newHashSet(this.getBiomeBlock(i, j, i1, j1));
		   }

		   @Nullable
		   public BlockPos findBiomePosition(int x, int z, int range, List<Biome> biomes, Random random) {
		      int i = x - range >> 2;
		      int j = z - range >> 2;
		      int k = x + range >> 2;
		      int l = z + range >> 2;
		      int i1 = k - i + 1;
		      int j1 = l - j + 1;
		      Biome[] abiome = this.getBiomeBlock(i, j, i1, j1);
		      BlockPos blockpos = null;
		      int k1 = 0;

		      for(int l1 = 0; l1 < i1 * j1; ++l1) {
		         int i2 = i + l1 % i1 << 2;
		         int j2 = j + l1 / i1 << 2;
		         if (biomes.contains(abiome[l1])) {
		            if (blockpos == null || random.nextInt(k1 + 1) == 0) {
		               blockpos = new BlockPos(i2, 0, j2);
		            }

		            ++k1;
		         }
		      }

		      return blockpos;
		   }

		   public float func_222365_c(int p_222365_1_, int p_222365_2_) {
		      int i = p_222365_1_ / 2;
		      int j = p_222365_2_ / 2;
		      int k = p_222365_1_ % 2;
		      int l = p_222365_2_ % 2;
		      float f = 100.0F - MathHelper.sqrt((float)(p_222365_1_ * p_222365_1_ + p_222365_2_ * p_222365_2_)) * 8.0F;
		      f = MathHelper.clamp(f, -100.0F, 80.0F);

		      for(int i1 = -12; i1 <= 12; ++i1) {
		         for(int j1 = -12; j1 <= 12; ++j1) {
		            long k1 = (long)(i + i1);
		            long l1 = (long)(j + j1);
		            if (k1 * k1 + l1 * l1 > 4096L && this.genElevation.getValue((double)k1, (double)l1) < (double)-0.9F) {
		               float f1 = (MathHelper.abs((float)k1) * 3439.0F + MathHelper.abs((float)l1) * 147.0F) % 13.0F + 9.0F;
		               float f2 = (float)(k - i1 * 2);
		               float f3 = (float)(l - j1 * 2);
		               float f4 = 100.0F - MathHelper.sqrt(f2 * f2 + f3 * f3) * f1;
		               f4 = MathHelper.clamp(f4, -100.0F, 80.0F);
		               f = Math.max(f, f4);
		            }
		         }
		      }

		      return f;
		   }

		   public boolean hasStructure(Structure<?> structureIn) {
		      return this.hasStructureCache.computeIfAbsent(structureIn, (p_205008_1_) -> {
		         for(Biome biome : this.biomes) {
		            if (biome.hasStructure(p_205008_1_)) {
		               return true;
		            }
		         }

		         return false;
		      });
		   }

		   public Set<BlockState> getSurfaceBlocks() {
		      if (this.topBlocksCache.isEmpty()) {
		         for(Biome biome : this.biomes) {
		            this.topBlocksCache.add(biome.getSurfaceBuilderConfig().getTop());
		         }
		      }

		      return this.topBlocksCache;
		   }

}
