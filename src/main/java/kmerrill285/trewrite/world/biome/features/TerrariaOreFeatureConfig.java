package kmerrill285.trewrite.world.biome.features;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.pattern.BlockMatcher;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;

public class TerrariaOreFeatureConfig implements IFeatureConfig {
	   public final BlockState target;
	   public final int size;
	   public final BlockState state;
	   

	   public TerrariaOreFeatureConfig(BlockState target, BlockState state, int size) {
	      this.size = size;
	      this.state = state;
	      this.target = target;
	   }

	   public <T> Dynamic<T> serialize(DynamicOps<T> ops) {
	      return new Dynamic<>(ops, ops.createMap(ImmutableMap.of(ops.createString("size"), ops.createInt(this.size), ops.createString("target"), BlockState.serialize(ops, this.target).getValue(), ops.createString("state"), BlockState.serialize(ops, this.state).getValue())));
	   }
	   
	   public static TerrariaOreFeatureConfig deserialize(Dynamic<?> p_214641_0_) {
		      int i = p_214641_0_.get("size").asInt(0);
		      BlockState blockstate = p_214641_0_.get("state").map(BlockState::deserialize).orElse(Blocks.AIR.getDefaultState());
		      BlockState target = p_214641_0_.get("target").map(BlockState::deserialize).orElse(Blocks.AIR.getDefaultState());
		      return new TerrariaOreFeatureConfig(target, blockstate, i);
		   }

	   
}
