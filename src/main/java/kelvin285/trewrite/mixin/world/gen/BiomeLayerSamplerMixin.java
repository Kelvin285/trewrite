package kelvin285.trewrite.mixin.world.gen;

import kelvin285.trewrite.mixin_interfaces.BiomeLayerSamplerAccessor;
import net.minecraft.world.biome.layer.util.CachingLayerSampler;
import net.minecraft.world.biome.source.BiomeLayerSampler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BiomeLayerSampler.class)
public class BiomeLayerSamplerMixin implements BiomeLayerSamplerAccessor {
    @Shadow @Final private CachingLayerSampler sampler;

    @Override
    public int sampleInt(int x, int z) {
        return this.sampler.sample(x, z);
    }
}
