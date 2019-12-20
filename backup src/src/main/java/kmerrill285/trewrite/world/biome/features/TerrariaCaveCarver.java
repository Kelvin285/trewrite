package kmerrill285.trewrite.world.biome.features;

import java.util.function.Function;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.Dynamic;

import kmerrill285.trewrite.blocks.BlocksT;
import net.minecraft.world.gen.carver.CaveWorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;

public class TerrariaCaveCarver extends CaveWorldCarver {

	public TerrariaCaveCarver(Function<Dynamic<?>, ? extends ProbabilityConfig> p_i49929_1_, int p_i49929_2_) {
		super(p_i49929_1_, p_i49929_2_);
		this.carvableBlocks = ImmutableSet.of(BlocksT.DIRT_BLOCK, BlocksT.STONE_BLOCK, BlocksT.GRASS_BLOCK, BlocksT.HIGHLANDS_GRASS, BlocksT.JUNGLE_GRASS);
	}

}
