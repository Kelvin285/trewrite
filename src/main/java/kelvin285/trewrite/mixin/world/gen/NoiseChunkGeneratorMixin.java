package kelvin285.trewrite.mixin.world.gen;

import kelvin285.trewrite.resources.FastNoiseLite;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.collection.Pool;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.*;
import net.minecraft.world.gen.chunk.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Mixin(NoiseChunkGenerator.class)
public abstract class NoiseChunkGeneratorMixin extends ChunkGenerator {
    @Shadow
    public long seed;

    public NoiseChunkGeneratorMixin(BiomeSource biomeSource, StructuresConfig structuresConfig) {
        super(biomeSource, structuresConfig);
    }

    public int getSeaLevel()
    {
        return 64;
    }

    /*
    public void buildSurface(ChunkRegion region, Chunk chunk) {

    }
     */

    /*
    public Chunk populateNoise(StructureAccessor accessor, Chunk chunk, int startY, int noiseSizeY) {

        return chunk;
    }
     */

    /*
    @Override
    public Pool<SpawnSettings.SpawnEntry> getEntitySpawnList(Biome biome, StructureAccessor accessor, SpawnGroup group, BlockPos pos) {
        return Pool.empty();
    }
     */

    @Override
    public void populateEntities(ChunkRegion region) {

    }

    /*
    @Override
    public void carve(long seed, BiomeAccess access, Chunk chunk, GenerationStep.Carver carver) {

    }
     */

    /*
    @Override
    public void generateFeatures(ChunkRegion region, StructureAccessor accessor) {

    }
     */
}
