package kelvin285.trewrite.world.gen.features;

import com.google.common.collect.ImmutableList;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.decorator.CountExtraDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.feature.*;

import static net.minecraft.world.gen.feature.ConfiguredFeatures.*;

public class CustomFeatures  extends ConfiguredFeatures{

    private static <FC extends FeatureConfig> ConfiguredFeature<FC, ?> register(String id, ConfiguredFeature<FC, ?> configuredFeature) {
        return (ConfiguredFeature) Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, id, configuredFeature);
    }
    public static ConfiguredFeature<?, ?> TREES_PLAINS = register("trewrite:trees_plains", Feature.RANDOM_SELECTOR.configure(new RandomFeatureConfig(ImmutableList.of(BIRCH_BEES_0002.withChance(0.2F), FANCY_OAK_BEES_0002.withChance(0.1F)), OAK_BEES_0002)).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP_OCEAN_FLOOR_NO_WATER).decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(3, 0.1F, 1))));

}
