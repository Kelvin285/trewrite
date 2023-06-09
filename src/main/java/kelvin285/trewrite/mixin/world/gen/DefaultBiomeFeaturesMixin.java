package kelvin285.trewrite.mixin.world.gen;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.world.gen.feature.DefaultBiomeFeatures.addBatsAndMonsters;
import static net.minecraft.world.gen.feature.DefaultBiomeFeatures.addFarmAnimals;

@Mixin(DefaultBiomeFeatures.class)
public class DefaultBiomeFeaturesMixin {
    @Inject(at=@At("HEAD"), method="addPlainsMobs", cancellable = true)
    private static void addPlainsMobs(SpawnSettings.Builder builder, CallbackInfo info) {
        addFarmAnimals(builder);
        //builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.HORSE, 5, 2, 6));
        //builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.DONKEY, 1, 1, 3));
        //addBatsAndMonsters(builder);
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.SLIME, 20, 1, 1));
        builder.spawn(SpawnGroup.WATER_AMBIENT, new SpawnSettings.SpawnEntry(EntityType.COD, 10, 3, 6));
        builder.spawn(SpawnGroup.WATER_AMBIENT, new SpawnSettings.SpawnEntry(EntityType.SALMON, 10, 3, 6));
        //builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.SHEEP, 12, 4, 4));
        info.cancel();
    }

}
