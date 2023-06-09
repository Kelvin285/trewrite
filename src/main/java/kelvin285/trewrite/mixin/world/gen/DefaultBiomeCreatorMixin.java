package kelvin285.trewrite.mixin.world.gen;

import kelvin285.trewrite.world.gen.features.CustomFeatures;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.ConfiguredStructureFeatures;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilders;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DefaultBiomeCreator.class)
public abstract class DefaultBiomeCreatorMixin {
    @Shadow
    private static int getSkyColor(float temperature) {return 0;}

    @Inject(at=@At("HEAD"), method="createPlains", cancellable = true)
    private static void createPlains(boolean sunflower, CallbackInfoReturnable<Biome> info) {
        SpawnSettings.Builder builder = new SpawnSettings.Builder();
        DefaultBiomeFeatures.addPlainsMobs(builder);
        if (!sunflower) {
            builder.playerSpawnFriendly();
        }

        GenerationSettings.Builder builder2 = (new GenerationSettings.Builder()).surfaceBuilder(ConfiguredSurfaceBuilders.GRASS);

        DefaultBiomeFeatures.addDefaultUndergroundStructures(builder2);
        DefaultBiomeFeatures.addLandCarvers(builder2);
        DefaultBiomeFeatures.addDungeons(builder2);
        DefaultBiomeFeatures.addPlainsTallGrass(builder2);
        builder2.feature(GenerationStep.Feature.VEGETAL_DECORATION, CustomFeatures.TREES_PLAINS);
        if (sunflower) {
            builder2.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.PATCH_SUNFLOWER);
        }

        DefaultBiomeFeatures.addDefaultOres(builder2);
        DefaultBiomeFeatures.addDefaultDisks(builder2);

        DefaultBiomeFeatures.addPlainsFeatures(builder2);
        if (sunflower) {
            builder2.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.PATCH_SUGAR_CANE);
        }

        DefaultBiomeFeatures.addDefaultMushrooms(builder2);
        if (sunflower) {
            builder2.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.PATCH_PUMPKIN);
        } else {
            DefaultBiomeFeatures.addDefaultVegetation(builder2);
        }

        var ret = (new Biome.Builder()).precipitation(Biome.Precipitation.RAIN).category(Biome.Category.PLAINS).depth(0.125F).scale(0.5F).temperature(0.8F).downfall(0.4F).effects((new BiomeEffects.Builder()).waterColor(0x093dbf).waterFogColor(0x08289f).skyColor(0x4447ea).fogColor(12638463).grassColor(0x5f9f35).skyColor(getSkyColor(0.8F)).moodSound(BiomeMoodSound.CAVE).build()).spawnSettings(builder.build()).generationSettings(builder2.build()).build();
        info.setReturnValue(ret);
    }
}
