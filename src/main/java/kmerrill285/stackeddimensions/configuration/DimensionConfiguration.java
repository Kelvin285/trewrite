package kmerrill285.stackeddimensions.configuration;

import kmerrill285.stackeddimensions.Util;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.dimension.DimensionType;

public class DimensionConfiguration {
	public ResourceLocation dimension, above, below;
	private int min, max;
	
	public DimensionConfiguration(ResourceLocation dimension, ResourceLocation above, ResourceLocation below, int min, int max) {
		this.dimension = dimension;
		this.above = above;
		this.below = below;
		this.min = min;
		this.max = max;
	}
	
	public int getMin() {
		return min-256;
	}
	
	public int getMax() {
		return max;
	}
	
	public DimensionType getAbove() {
		if (above == null) return null;
		return Util.getDimension(above);
	}
	
	public DimensionType getBelow() {
		if (below == null) return null;
		return Util.getDimension(below);
	}
	
	public DimensionType getDimension() {
		if (dimension == null) return null;
		return Util.getDimension(dimension);
	}
}
