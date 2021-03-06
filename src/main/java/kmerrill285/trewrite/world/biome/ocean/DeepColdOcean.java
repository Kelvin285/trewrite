package kmerrill285.trewrite.world.biome.ocean;

import kmerrill285.trewrite.blocks.BlocksT;
import kmerrill285.trewrite.world.biome.features.TerrariaFeatures;
import kmerrill285.trewrite.world.biome.features.TerrariaOreFeatureConfig;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.FrequencyConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class DeepColdOcean extends Biome {

	public DeepColdOcean() {
	      super((new Biome.Builder()).surfaceBuilder(new ConfiguredSurfaceBuilder<SurfaceBuilderConfig>(SurfaceBuilder.DEFAULT, 
					new SurfaceBuilderConfig(BlocksT.CLAY_BLOCK.getDefaultState(), BlocksT.STONE_BLOCK.getDefaultState(), BlocksT.STONE_BLOCK.getDefaultState())
					)).precipitation(Biome.RainType.RAIN).category(Biome.Category.DESERT).depth(-2.5F).scale(0.4F).temperature(0.5F).downfall(0.5F).waterColor(4020182).waterFogColor(329011).parent((String)null));
		  this.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(TerrariaFeatures.CAVE, new ProbabilityConfig(0.14285715F)));//	      this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntitiesT.BLUE_SLIME, 12, 4, 4));
	      this.setRegistryName("trewrite:deep_cold_ocean");
	      
	      addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(TerrariaFeatures.PALM_TREES, IFeatureConfig.NO_FEATURE_CONFIG, Placement.COUNT_HEIGHTMAP_DOUBLE, new FrequencyConfig(48)));
	      addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(TerrariaFeatures.ORES, new TerrariaOreFeatureConfig(BlocksT.STONE_BLOCK.getDefaultState(), BlocksT.IRON_ORE.getDefaultState() , 17), Placement.COUNT_RANGE, new CountRangeConfig(20, 0, 0, 128)));
		  addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(TerrariaFeatures.ORES, new TerrariaOreFeatureConfig(BlocksT.DIRT_BLOCK.getDefaultState(), BlocksT.IRON_ORE.getDefaultState(), 17), Placement.COUNT_RANGE, new CountRangeConfig(20, 0, 0, 128)));
		  addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(TerrariaFeatures.ORES, new TerrariaOreFeatureConfig(BlocksT.GRASS_BLOCK.getDefaultState(), BlocksT.IRON_ORE.getDefaultState(), 17), Placement.COUNT_RANGE, new CountRangeConfig(20, 0, 0, 128)));
		  addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(TerrariaFeatures.ORES, new TerrariaOreFeatureConfig(BlocksT.CLAY_BLOCK.getDefaultState(), BlocksT.SAND.getDefaultState(), 45), Placement.COUNT_RANGE, new CountRangeConfig(20, 0, 0, 128)));

	
	}
	 /**
	    * Gets the current temperature at the given location, based off of the default for this biome, the elevation of the
	    * position, and {@linkplain #TEMPERATURE_NOISE} some random perlin noise.
	    */
	   public float getTemperature(BlockPos pos) {
	      return super.getTemperature(pos);
	   }
}
