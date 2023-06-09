package kelvin285.trewrite.mixin.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.ChunkRandom;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;
import java.util.Optional;
import java.util.Random;

import static net.minecraft.entity.mob.MobEntity.canMobSpawn;

@Mixin(SlimeEntity.class)
public class SlimeEntityMixin {
    @Inject(at=@At("HEAD"), method="canSpawn", cancellable = true)
    private static void canSpawn(EntityType<SlimeEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random, CallbackInfoReturnable info) {
        if (world.getDifficulty() != Difficulty.PEACEFUL && random.nextFloat() < world.getMoonSize() && random.nextInt(10) == 0) {
            info.setReturnValue(canMobSpawn(type, world, spawnReason, pos, random));
        } else {
            info.setReturnValue(false);
        }
    }
}
