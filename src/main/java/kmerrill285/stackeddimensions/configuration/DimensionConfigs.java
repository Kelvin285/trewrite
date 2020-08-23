package kmerrill285.stackeddimensions.configuration;

import java.util.ArrayList;

import net.minecraft.util.ResourceLocation;

public class DimensionConfigs {
	public static ArrayList<DimensionConfiguration> configs = new ArrayList<DimensionConfiguration>();
	
	public static DimensionConfiguration getConfig(ResourceLocation location) {
		for (DimensionConfiguration config : configs) {
			if (config.dimension.getPath().equals(location.getPath())) {
				return config;
			}
		}
		return null;
	}
}
