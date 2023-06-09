package kelvin285.trewrite.mixin.world.gen;

import kelvin285.trewrite.mixin_interfaces.BiomeLayerSamplerAccessor;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeIds;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.BuiltinBiomes;
import net.minecraft.world.biome.source.BiomeLayerSampler;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.biome.source.VanillaLayeredBiomeSource;
import net.minecraft.world.gen.feature.OceanRuinFeature;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VanillaLayeredBiomeSource.class)
public class VanillaLayeredBiomeSourceMixin {

    @Shadow @Final private Registry<Biome> biomeRegistry;

    @Shadow @Final private BiomeLayerSampler biomeSampler;

    @Inject(at=@At("HEAD"), method="getBiomeForNoiseGen", cancellable = true)
    public void getBiomeForNoiseGen(int biomeX, int biomeY, int biomeZ, CallbackInfoReturnable<Biome> info) {

        int biome_id = ((BiomeLayerSamplerAccessor)this.biomeSampler).sampleInt(biomeX, biomeZ);

        biome_id = BiomeIds.PLAINS;

        RegistryKey<Biome> registryKey = BuiltinBiomes.fromRawId(biome_id);
        Biome biome = this.biomeRegistry.get(registryKey);
        info.setReturnValue(biome);
    }
}
