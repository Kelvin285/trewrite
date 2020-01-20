package kmerrill285.trewrite.world.biome;

import java.util.ArrayList;

import kmerrill285.trewrite.blocks.BlocksT;
import kmerrill285.trewrite.util.Util;
import kmerrill285.trewrite.world.biome.features.TerrariaFeatures;
import kmerrill285.trewrite.world.biome.features.TerrariaOreFeatureConfig;
import kmerrill285.trewrite.world.biome.ocean.BeachBiome;
import kmerrill285.trewrite.world.biome.ocean.ColdOcean;
import kmerrill285.trewrite.world.biome.ocean.DeepColdOcean;
import kmerrill285.trewrite.world.biome.ocean.DeepFrozenOcean;
import kmerrill285.trewrite.world.biome.ocean.DeepWarmOcean;
import kmerrill285.trewrite.world.biome.ocean.FrozenOcean;
import kmerrill285.trewrite.world.biome.ocean.Glaciers;
import kmerrill285.trewrite.world.biome.ocean.ShallowOcean;
import kmerrill285.trewrite.world.biome.ocean.WarmOcean;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.FrequencyConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class BiomeT {

	public static Biome LOWLANDS = Biomes.PLAINS;
	public static Biome HIGHLANDS = Biomes.MOUNTAINS;
	public static Biome CORRUPT_LOWLANDS = Biomes.PLAINS;
	public static Biome CORRUPT_HIGHLANDS = Biomes.MOUNTAINS;
	public static Biome DESERT = Biomes.DESERT;
	public static Biome DESERT_HILLS = Biomes.DESERT;
	public static Biome HUMID_HIGHLANDS = Biomes.MOUNTAINS;
	public static Biome HUMID_MIDLANDS = Biomes.MOUNTAINS;
	public static Biome MIDLANDS = Biomes.PLAINS;
	public static Biome BOG = Biomes.SWAMP;
	public static Biome SWAMP = Biomes.SWAMP;
	public static Biome JUNGLE = Biomes.JUNGLE;
	public static Biome JUNGLE_HIGHLANDS = Biomes.JUNGLE;
	public static Biome CORRUPT_JUNGLE = Biomes.JUNGLE;
	public static Biome CORRUPT_JUNGLE_HIGHLANDS = Biomes.JUNGLE;
	public static Biome CORRUPT_DESERT = Biomes.DESERT;
	public static Biome CORRUPT_DESERT_HILLS = Biomes.DESERT;
	public static Biome COLD_STONE_PLAINS = Biomes.DESERT;
	public static Biome LOW_BOREAL_FOREST = Biomes.DEFAULT;
	public static Biome SAVANNA = Biomes.DEFAULT;
	public static Biome MESA = Biomes.DEFAULT;
	public static Biome ICE_SPIKES = Biomes.DEFAULT;
	public static Biome LIVINGWOOD_FOREST = Biomes.DEFAULT;
	public static Biome SAVANNA_MIDLANDS = Biomes.DEFAULT;
	public static Biome LOWLANDS_FOREST = Biomes.DEFAULT;
	public static Biome FROZEN_PLAINS = Biomes.DEFAULT;
	public static Biome ARCTIC_DESERT = Biomes.DEFAULT;
	public static Biome TAIGA_LOWLANDS = Biomes.DEFAULT;
	public static Biome DRY_LOWLANDS = Biomes.DEFAULT;
	public static Biome SHRUBLANDS = Biomes.DEFAULT;
	public static Biome FROZEN_HILLS = Biomes.DEFAULT;
	public static Biome STONE_HILLS = Biomes.DEFAULT;
	public static Biome HIGHRISE = Biomes.DEFAULT;
	public static Biome BOREAL_HILLS = Biomes.DEFAULT;
	public static Biome SPRUCE_HILLS = Biomes.DEFAULT;
	public static Biome MESA_HILLS = Biomes.DEFAULT;
	public static Biome ICE_SPIKES_HILLS = Biomes.DEFAULT;
	public static Biome TERRACED_MIDLANDS = Biomes.DEFAULT;
	public static Biome MUSHROOM_MIDLANDS = Biomes.DEFAULT;
	public static Biome FROZEN_HIGHLANDS = Biomes.DEFAULT;
	public static Biome STONE_HIGHLANDS = Biomes.DEFAULT;
	public static Biome MASSIVE_PLATEAU = Biomes.DEFAULT;
	public static Biome BOREAL_MOUNTAINS = Biomes.DEFAULT;
	public static Biome SPRUCE_MOUNTAINS = Biomes.DEFAULT;
	public static Biome DRY_FOREST = Biomes.DEFAULT;
	public static Biome ICE_PEAKS = Biomes.DEFAULT;
	public static Biome TERRACED_HIGHLANDS = Biomes.DEFAULT;
	public static Biome MUSHROOM_HIGHLANDS = Biomes.DEFAULT;
	
	public static Biome BEACH = Biomes.DEFAULT;
	public static Biome COLD_OCEAN = Biomes.DEFAULT;
	public static Biome DEEP_COLD_OCEAN = Biomes.DEFAULT;
	public static Biome DEEP_FROZEN_OCEAN = Biomes.DEFAULT;
	public static Biome FROZEN_OCEAN = Biomes.DEFAULT;
	public static Biome GLACIERS = Biomes.DEFAULT;
	public static Biome WARM_OCEAN = Biomes.DEFAULT;
	public static Biome SHALLOW_OCEAN = Biomes.DEFAULT;
	public static Biome DEEP_WARM_OCEAN = Biomes.DEFAULT;
	public static Biome SKY_ISLANDS = Biomes.DEFAULT;
	public static Biome UNDERGROUND = Biomes.DEFAULT;
	public static Biome UNDERWORLD = Biomes.DEFAULT;

	public static ArrayList<Biome> biomes = new ArrayList<Biome>();

	@SubscribeEvent

	public static void onRegisterBiomes(RegistryEvent.Register<Biome> event)
	{
		if(biomeRegistry == null)
			biomeRegistry = event.getRegistry();
		
		registerBiome(LOWLANDS = new LowlandsBiome(), "lowlands", 10, BiomeManager.BiomeType.WARM,Type.PLAINS);
		registerBiome(HIGHLANDS = new HighlandsBiome(), "highlands", 10, BiomeManager.BiomeType.WARM,Type.MOUNTAIN);
		registerBiome(CORRUPT_LOWLANDS = new CorruptLowlandsBiome(), "corrupt_lowlands", 10, BiomeManager.BiomeType.WARM,Type.PLAINS);
		registerBiome(CORRUPT_HIGHLANDS = new CorruptHighlandsBiome(), "corrupt_highlands", 10, BiomeManager.BiomeType.WARM,Type.MOUNTAIN);
		registerBiome(DESERT = new DesertBiome(), "desert", 10, BiomeManager.BiomeType.DESERT,Type.HOT);
		registerBiome(HUMID_HIGHLANDS = new HumidHighlandsBiome(), "humid_highlands", 10, BiomeManager.BiomeType.WARM,Type.MOUNTAIN);
		registerBiome(MIDLANDS = new MidlandsBiome(), "midlands", 10, BiomeManager.BiomeType.WARM,Type.HILLS);
		registerBiome(HUMID_MIDLANDS = new HumidMidlandsBiome(), "humid_midlands", 10, BiomeManager.BiomeType.WARM,Type.HILLS);
		registerBiome(DESERT_HILLS = new DesertHillsBiome(), "desert_hills", 10, BiomeManager.BiomeType.DESERT,Type.HOT);
		registerBiome(BOG = new BogBiome(), "bog", 10, BiomeManager.BiomeType.WARM,Type.SWAMP);
		registerBiome(SWAMP = new SwampBiome(), "swamp", 10, BiomeManager.BiomeType.WARM,Type.SWAMP);
		registerBiome(JUNGLE = new JungleBiome(), "jungle", 10, BiomeManager.BiomeType.WARM,Type.JUNGLE);
		registerBiome(JUNGLE_HIGHLANDS = new JungleHighlands(), "jungle_highlands", 10, BiomeManager.BiomeType.WARM,Type.JUNGLE);
		registerBiome(CORRUPT_JUNGLE_HIGHLANDS = new CorruptJungleHighlands(), "corrupt_jungle_highlands", 10, BiomeManager.BiomeType.WARM,Type.JUNGLE);
		registerBiome(CORRUPT_JUNGLE = new CorruptJungleBiome(), "corrupt_jungle", 10, BiomeManager.BiomeType.WARM,Type.JUNGLE);
		registerBiome(CORRUPT_DESERT_HILLS = new CorruptDesertHillsBiome(), "corrupt_desert_hills", 10, BiomeManager.BiomeType.DESERT,Type.HOT);
		registerBiome(CORRUPT_DESERT = new CorruptDesertBiome(), "corrupt_desert", 10, BiomeManager.BiomeType.DESERT,Type.HOT);
		registerBiome(COLD_STONE_PLAINS = new ColdStonePlains(), "stone_plains", 10, BiomeManager.BiomeType.DESERT,Type.DRY);
		registerBiome(LOW_BOREAL_FOREST = new LowBorealForest(), "low_boreal_forest", 10, BiomeManager.BiomeType.COOL,Type.COLD);
		registerBiome(SAVANNA = new SavannaBiome(), "savanna", 10, BiomeManager.BiomeType.WARM,Type.DRY);
		registerBiome(MESA = new MesaBiome(), "mesa", 10, BiomeManager.BiomeType.DESERT,Type.DRY);
		registerBiome(ICE_SPIKES = new IceSpikesBiome(), "ice_spikes", 10, BiomeManager.BiomeType.ICY,Type.DRY);
		registerBiome(LIVINGWOOD_FOREST = new LivingwoodForest(), "livingwood_forest", 10, BiomeManager.BiomeType.COOL,Type.FOREST);
		registerBiome(SAVANNA_MIDLANDS = new SavannaMidlands(), "savanna_midlands", 10, BiomeManager.BiomeType.WARM,Type.SAVANNA);
		registerBiome(LOWLANDS_FOREST = new LowlandsForestBiome(), "lowlands_forest", 10, BiomeManager.BiomeType.WARM,Type.FOREST);
		registerBiome(FROZEN_PLAINS = new FrozenPlains(), "frozen_plains", 10, BiomeManager.BiomeType.COOL,Type.PLAINS);
		registerBiome(ARCTIC_DESERT = new ArcticDesert(), "arctic_desert", 10, BiomeManager.BiomeType.DESERT,Type.COLD);
		registerBiome(TAIGA_LOWLANDS = new TaigaLowlandsBiome(), "taiga_lowlands", 10, BiomeManager.BiomeType.COOL,Type.FOREST);
		registerBiome(DRY_LOWLANDS = new DryLowlandsBiome(), "dry_lowlands", 10, BiomeManager.BiomeType.WARM,Type.DRY);
		registerBiome(SHRUBLANDS = new ShrublandsBiome(), "shrublands", 10, BiomeManager.BiomeType.COOL,Type.LUSH);
		registerBiome(FROZEN_HILLS = new FrozenHills(), "frozen_hills", 10, BiomeManager.BiomeType.ICY,Type.DRY);
		registerBiome(STONE_HILLS = new StoneHills(), "stone_hills", 10, BiomeManager.BiomeType.COOL,Type.DRY);
		registerBiome(HIGHRISE = new HighriseBiome(), "highrise", 10, BiomeManager.BiomeType.WARM,Type.HILLS);
		registerBiome(BOREAL_HILLS = new BorealHillsBiome(), "boreal_hills", 10, BiomeManager.BiomeType.ICY,Type.HILLS);
		registerBiome(SPRUCE_HILLS = new SpruceHills(), "spruce_hills", 10, BiomeManager.BiomeType.ICY,Type.HILLS);
		registerBiome(MESA_HILLS = new MesaHillsBiome(), "mesa_hills", 10, BiomeManager.BiomeType.DESERT,Type.HILLS);
		registerBiome(ICE_SPIKES_HILLS = new IceSpikesHillsBiome(), "ice_spikes_hills", 10, BiomeManager.BiomeType.ICY,Type.HILLS);
		registerBiome(TERRACED_MIDLANDS = new TerracedMidlands(), "terraced_midlands", 10, BiomeManager.BiomeType.WARM,Type.HILLS);
		registerBiome(MUSHROOM_MIDLANDS = new MushroomMidlandsBiome(), "mushroom_midlands", 10, BiomeManager.BiomeType.WARM,Type.HILLS);
		registerBiome(FROZEN_HIGHLANDS = new FrozenHighlands(), "frozen_highlands", 10, BiomeManager.BiomeType.ICY,Type.MOUNTAIN);
		registerBiome(STONE_HIGHLANDS = new StoneHighlands(), "stone_highlands", 10, BiomeManager.BiomeType.DESERT,Type.MOUNTAIN);
		registerBiome(MASSIVE_PLATEAU = new MassivePlateau(), "massive_plateau", 10, BiomeManager.BiomeType.WARM,Type.MOUNTAIN);
		registerBiome(BOREAL_MOUNTAINS = new BorealMountainsBiome(), "boreal_mountains", 10, BiomeManager.BiomeType.ICY,Type.MOUNTAIN);
		registerBiome(SPRUCE_MOUNTAINS = new SpruceMountains(), "spruce_mountains", 10, BiomeManager.BiomeType.COOL,Type.MOUNTAIN);
		registerBiome(DRY_FOREST = new DryForestBiome(), "dry_forest", 10, BiomeManager.BiomeType.WARM,Type.MOUNTAIN);
		registerBiome(ICE_PEAKS = new IcePeaks(), "ice_peaks", 10, BiomeManager.BiomeType.ICY,Type.MOUNTAIN);
		registerBiome(TERRACED_HIGHLANDS = new TerracedHighlands(), "terraced_highlands", 10, BiomeManager.BiomeType.WARM,Type.MOUNTAIN);
		registerBiome(MUSHROOM_HIGHLANDS = new MushroomHighlandsBiome(), "mushroom_highlands", 10, BiomeManager.BiomeType.WARM,Type.MOUNTAIN);
		registerBiome(BEACH = new BeachBiome(), "beach", 10, BiomeManager.BiomeType.WARM,Type.BEACH);
		registerBiome(COLD_OCEAN = new ColdOcean(), "cold_ocean", 10, BiomeManager.BiomeType.WARM,Type.BEACH);
		registerBiome(DEEP_COLD_OCEAN = new DeepColdOcean(), "deep_cold_ocean", 10, BiomeManager.BiomeType.COOL,Type.OCEAN);
		registerBiome(DEEP_FROZEN_OCEAN = new DeepFrozenOcean(), "deep_frozen_ocean", 10, BiomeManager.BiomeType.ICY,Type.OCEAN);
		registerBiome(DEEP_WARM_OCEAN = new DeepWarmOcean(), "deep_warm_ocean", 10, BiomeManager.BiomeType.WARM,Type.OCEAN);
		registerBiome(FROZEN_OCEAN = new FrozenOcean(), "frozen_ocean", 10, BiomeManager.BiomeType.ICY,Type.BEACH);
		registerBiome(GLACIERS = new Glaciers(), "glaciers", 10, BiomeManager.BiomeType.ICY,Type.BEACH);
		registerBiome(SHALLOW_OCEAN = new ShallowOcean(), "shallow_ocean", 10, BiomeManager.BiomeType.WARM,Type.BEACH);
		registerBiome(WARM_OCEAN = new WarmOcean(), "warm_ocean", 10, BiomeManager.BiomeType.WARM,Type.BEACH);
		registerBiome(SKY_ISLANDS = new SkyIslandsBiome(), "sky_islands", 10, BiomeManager.BiomeType.WARM, Type.END);
		registerBiome(UNDERGROUND = new UndergroundBiome(), "underground", 10, BiomeManager.BiomeType.WARM, Type.NETHER);
		registerBiome(UNDERWORLD = new UnderworldBiome(), "underworld", 10, BiomeManager.BiomeType.WARM, Type.NETHER);

		for (Biome biome : biomes) 
		{
			
			biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(TerrariaFeatures.ORES, new TerrariaOreFeatureConfig(BlocksT.ASH_BLOCK.getDefaultState(), BlocksT.HELLSTONE.getDefaultState() , 17), Placement.COUNT_RANGE, new CountRangeConfig(20, 0, 0, Util.underworldLevel + 50)));

			
			biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(TerrariaFeatures.ORES, new TerrariaOreFeatureConfig(BlocksT.STONE_BLOCK.getDefaultState(), BlocksT.IRON_ORE.getDefaultState() , 17), Placement.COUNT_RANGE, new CountRangeConfig(20, 0, 0, 255)));
			biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(TerrariaFeatures.ORES, new TerrariaOreFeatureConfig(BlocksT.DIRT_BLOCK.getDefaultState(), BlocksT.IRON_ORE.getDefaultState(), 17), Placement.COUNT_RANGE, new CountRangeConfig(20, 0, 0, 255)));
			biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(TerrariaFeatures.ORES, new TerrariaOreFeatureConfig(BlocksT.GRASS_BLOCK.getDefaultState(), BlocksT.IRON_ORE.getDefaultState(), 17), Placement.COUNT_RANGE, new CountRangeConfig(20, 0, 0, 255)));
			
			biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(TerrariaFeatures.ORES, new TerrariaOreFeatureConfig(BlocksT.STONE_BLOCK.getDefaultState(), BlocksT.COPPER_ORE.getDefaultState() , 17), Placement.COUNT_RANGE, new CountRangeConfig(10, 0, 0, 255)));
			biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(TerrariaFeatures.ORES, new TerrariaOreFeatureConfig(BlocksT.DIRT_BLOCK.getDefaultState(), BlocksT.COPPER_ORE.getDefaultState(), 17), Placement.COUNT_RANGE, new CountRangeConfig(10, 0, 0, 255)));
			biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(TerrariaFeatures.ORES, new TerrariaOreFeatureConfig(BlocksT.GRASS_BLOCK.getDefaultState(), BlocksT.COPPER_ORE.getDefaultState(), 17), Placement.COUNT_RANGE, new CountRangeConfig(10, 0, 0, 255)));
		
		
			biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(TerrariaFeatures.ORES, new TerrariaOreFeatureConfig(BlocksT.STONE_BLOCK.getDefaultState(), BlocksT.GOLD_ORE.getDefaultState() , 17), Placement.COUNT_RANGE, new CountRangeConfig(20, 0, 0, 255)));
			biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(TerrariaFeatures.ORES, new TerrariaOreFeatureConfig(BlocksT.STONE_BLOCK.getDefaultState(), BlocksT.SILVER_ORE.getDefaultState() , 17), Placement.COUNT_RANGE, new CountRangeConfig(10, 0, 0, 255)));

			
			biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(TerrariaFeatures.ORES, new TerrariaOreFeatureConfig(BlocksT.STONE_BLOCK.getDefaultState(), BlocksT.DEMONITE_ORE.getDefaultState() , 3), Placement.COUNT_RANGE, new CountRangeConfig(5, 0, 0, 128)));
			biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(TerrariaFeatures.ORES, new TerrariaOreFeatureConfig(BlocksT.EBONSTONE.getDefaultState(), BlocksT.DEMONITE_ORE.getDefaultState() , 8), Placement.COUNT_RANGE, new CountRangeConfig(20, 0, 0, 255)));
			
			
			biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(TerrariaFeatures.LIFE_CRYSTAL, IFeatureConfig.NO_FEATURE_CONFIG, Placement.COUNT_HEIGHTMAP_DOUBLE, new FrequencyConfig(70)));
			
		}
	}
	
	private static IForgeRegistry<Biome> biomeRegistry;
	
	public static void registerBiome(Biome biome, String name, int weight, BiomeManager.BiomeType spawnType, Type... types)

	{

		if(biomeRegistry == null)
			throw new NullPointerException("Biome Registry not set");
		
		biomeRegistry.register(biome);
		BiomeDictionary.addTypes(biome, types);

		BiomeManager.addBiome(spawnType, new BiomeManager.BiomeEntry(biome, weight));
		BiomeManager.addSpawnBiome(biome);
		BiomeT.biomes.add(biome);
	}
}
