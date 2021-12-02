package kelvin285.trewrite.mixin.world.gen;

import kelvin285.trewrite.resources.FastNoiseLite;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.SaplingBlock;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.collection.Pool;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.*;
import net.minecraft.world.gen.chunk.*;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Mixin(NoiseChunkGenerator.class)
public abstract class NoiseChunkgeneratorMixin extends ChunkGenerator {
    @Shadow
    public long seed;

    private long setup_seed = -1;
    public NoiseChunkgeneratorMixin(BiomeSource biomeSource, StructuresConfig structuresConfig) {
        super(biomeSource, structuresConfig);
    }

    private FastNoiseLite fastNoise;

    private FastNoiseLite detail_noise;
    private FastNoiseLite smoothing_noise;
    private FastNoiseLite flattening_noise;
    private FastNoiseLite hill_noise;

    private boolean set_up = false;
    private void Setup() {
        if (this.seed != setup_seed || fastNoise == null){
            ChunkRandom chunkRandom = new ChunkRandom(seed);
            fastNoise = new FastNoiseLite((int)seed);
            detail_noise = new FastNoiseLite((int)seed);
            detail_noise.SetFractalType(FastNoiseLite.FractalType.FBm);
            detail_noise.SetFractalOctaves(8);
            detail_noise.SetFrequency(1.0f / 1000.0f);

            hill_noise = new FastNoiseLite((int)seed);
            hill_noise.SetFractalType(FastNoiseLite.FractalType.Ridged);
            hill_noise.SetFractalOctaves(8);
            hill_noise.SetFrequency(1.0f / 1000.0f);

            smoothing_noise = new FastNoiseLite((int)seed);
            smoothing_noise.SetFractalType(FastNoiseLite.FractalType.FBm);
            smoothing_noise.SetFractalOctaves(3);
            smoothing_noise.SetFrequency(1.0f / 250.0f);

            flattening_noise = new FastNoiseLite((int)seed);
            flattening_noise.SetFrequency(1.0f / 300.0f);
        }
    }

    public int getSeaLevel()
    {
        return 64;
    }

    public BlockState getBlockState(Chunk chunk, int x, int y, int z, Random random) {
        Setup();
        BlockState state = Blocks.AIR.getDefaultState();

        int height = getSeaLevel();

        int noise_x = x + chunk.getPos().x * 16;
        int noise_z = z + chunk.getPos().z * 16;
        int noise_y = y;

        float flattening = flattening_noise.GetNoise(noise_x, noise_z);
        if (flattening < -0.5f) flattening = -0.5f;
        if (flattening > 0.5f) flattening = 0.5f;
        flattening += 0.5f;

        detail_noise.SetFractalLacunarity(2.4f);
        float detail = detail_noise.GetNoise(noise_x, noise_z);


        hill_noise.SetFractalLacunarity(2.4f);
        detail_noise.SetFractalLacunarity(1.5f);
        float hills = hill_noise.GetNoise(noise_x, noise_z) + detail_noise.GetNoise(noise_x, noise_z);

        height += Math.abs(hills) * 75 * flattening + detail * 25;

        boolean underwater = y <= getSeaLevel();

        if (underwater) {
            state = Blocks.WATER.getDefaultState();
        }
        if (y == height + 1) {
            if (random.nextInt(100) <= 15) {
                if (!underwater) {
                    state = Blocks.GRASS.getDefaultState();
                } else {
                    state = Blocks.TALL_SEAGRASS.getDefaultState();
                }
            }
        }
        if (y == height) {
            state = underwater ? Blocks.GRAVEL.getDefaultState() : Blocks.GRASS_BLOCK.getDefaultState();
        } else if (y < height) {
            state = Blocks.DIRT.getDefaultState();
        }

        return state;
    }

    public void buildSurface(ChunkRegion region, Chunk chunk) {

    }

    public CompletableFuture<Chunk> populateNoise(Executor executor, StructureAccessor accessor, Chunk chunk) {
        BlockPos.Mutable mutable = new BlockPos.Mutable(0, 0, 0);
        Random random = new Random();
        for (int x = 0; x < 16; ++x) {
            for (int z = 0; z < 16; ++z) {
                for (int y = chunk.getHeight(); y >= 0; y--) {
                    mutable.set(x, y, z);
                    BlockState state = getBlockState(chunk, x, y, z, random);
                    BlockState up = getBlockState(chunk, x, y + 1, z, random);
                    if (chunk.getBlockState(mutable).isAir())
                        chunk.setBlockState(mutable, state, false);

                    if (state.getBlock() == Blocks.GRASS_BLOCK && (up.isAir() || up.getBlock() == Blocks.TALL_GRASS)) {
                        if (x - 2 >= 0 && x + 2 < 16 && z - 2 >= 0 && z + 2 < 16) {
                            if (random.nextInt(400) <= 2) {
                                if (random.nextInt(10) <= 7) {
                                    GenerateSpruceTree(mutable, chunk, random);
                                } else {
                                    GeneratePopcicleTree(mutable, chunk, random);
                                }
                            }
                        }
                    }
                }
            }
        }


        return CompletableFuture.completedFuture(chunk);
    }

    public void GeneratePopcicleTree(BlockPos start, Chunk chunk, Random random) {
        BlockPos pos = new BlockPos(start);
        int height = random.nextInt(5) + 7;
        for (int i = 1; i <= height; i++) {
            chunk.setBlockState(pos.up(i), Blocks.OAK_LOG.getDefaultState(), false);

            if (i > 3) {
                if (random.nextInt(10) == 0) chunk.setBlockState(pos.add(-1, i, 0), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 1), false);
                if (random.nextInt(10) == 0) chunk.setBlockState(pos.add(1, i, 0), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 1), false);
                if (random.nextInt(10) == 0) chunk.setBlockState(pos.add(0, i, -1), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 1), false);
                if (random.nextInt(10) == 0) chunk.setBlockState(pos.add(0, i, 1), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 1), false);
            }
        }

        for (int x = -2; x <= 2; x++) {
            for (int y = -2; y <= 2; y++) {
                for (int z = -2; z <= 2; z++) {
                    double rad = Math.sqrt(x * x + y * y + z * z);
                    if (rad <= 2) {
                        boolean flag = true;
                        if (rad > 1) {
                            if (random.nextInt(10) <= 2) {
                                flag = false;
                            }
                        }
                        if (flag) {
                            BlockPos pos1 = pos.add(x, y + height, z);
                            if (chunk.getBlockState(pos1).isAir()) {
                                chunk.setBlockState(pos1, Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 1), false);
                            }
                        }
                    }
                }
            }
        }
    }

    public void GenerateSpruceTree(BlockPos start, Chunk chunk, Random random) {
        BlockPos pos = new BlockPos(start);
        int height = random.nextInt(3) + 7;
        for (int i = 1; i <= height; i++) {
            chunk.setBlockState(pos.up(i), Blocks.OAK_LOG.getDefaultState(), false);
        }
        chunk.setBlockState(pos.up(height + 1), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 1), false);

        chunk.setBlockState(pos.add(-1, height, 0), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 1), false);
        chunk.setBlockState(pos.add(1, height, 0), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 1), false);
        chunk.setBlockState(pos.add(0, height, -1), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 1), false);
        chunk.setBlockState(pos.add(0, height, 1), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 1), false);

        chunk.setBlockState(pos.add(-1, height - 1, 0), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 1), false);
        chunk.setBlockState(pos.add(1, height - 1, 0), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 1), false);
        chunk.setBlockState(pos.add(0, height - 1, -1), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 1), false);
        chunk.setBlockState(pos.add(0, height - 1, 1), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 1), false);
        chunk.setBlockState(pos.add(-1, height - 1, -1), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 1), false);
        chunk.setBlockState(pos.add(1, height - 1, -1), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 1), false);
        chunk.setBlockState(pos.add(-1, height - 1, 1), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 1), false);
        chunk.setBlockState(pos.add(1, height - 1, 1), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 1), false);
        chunk.setBlockState(pos.add(-2, height - 1, 0), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 1), false);
        chunk.setBlockState(pos.add(2, height - 1, 0), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 1), false);
        chunk.setBlockState(pos.add(0, height - 1, -2), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 1), false);
        chunk.setBlockState(pos.add(0, height - 1, 2), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 1), false);


        chunk.setBlockState(pos.add(-1, height - 2, 0), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 1), false);
        chunk.setBlockState(pos.add(1, height - 2, 0), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 1), false);
        chunk.setBlockState(pos.add(0, height - 2, -1), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 1), false);
        chunk.setBlockState(pos.add(0, height - 2, 1), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 1), false);

        chunk.setBlockState(pos.add(-1, height - 3, 0), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 1), false);
        chunk.setBlockState(pos.add(1, height - 3, 0), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 1), false);
        chunk.setBlockState(pos.add(0, height - 3, -1), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 1), false);
        chunk.setBlockState(pos.add(0, height - 3, 1), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 1), false);
        chunk.setBlockState(pos.add(-1, height - 3, -1), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 1), false);
        chunk.setBlockState(pos.add(1, height - 3, -1), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 1), false);
        chunk.setBlockState(pos.add(-1, height - 3, 1), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 1), false);
        chunk.setBlockState(pos.add(1, height - 3, 1), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 1), false);
        chunk.setBlockState(pos.add(-2, height - 3, 0), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 1), false);
        chunk.setBlockState(pos.add(2, height - 3, 0), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 1), false);
        chunk.setBlockState(pos.add(0, height - 3, -2), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 1), false);
        chunk.setBlockState(pos.add(0, height - 3, 2), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 1), false);


        chunk.setBlockState(pos.add(-1, height - 4, 0), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 1), false);
        chunk.setBlockState(pos.add(1, height - 4, 0), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 1), false);
        chunk.setBlockState(pos.add(0, height - 4, -1), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 1), false);
        chunk.setBlockState(pos.add(0, height - 4, 1), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 1), false);


        chunk.setBlockState(pos.add(-1, height - 5, 0), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 1), false);
        chunk.setBlockState(pos.add(1, height - 5, 0), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 1), false);
        chunk.setBlockState(pos.add(0, height - 5, -1), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 1), false);
        chunk.setBlockState(pos.add(0, height - 5, 1), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 1), false);
        chunk.setBlockState(pos.add(-1, height - 5, -1), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 1), false);
        chunk.setBlockState(pos.add(1, height - 5, -1), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 1), false);
        chunk.setBlockState(pos.add(-1, height - 5, 1), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 1), false);
        chunk.setBlockState(pos.add(1, height - 5, 1), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 1), false);
        chunk.setBlockState(pos.add(-2, height - 5, 0), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 1), false);
        chunk.setBlockState(pos.add(2, height - 5, 0), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 1), false);
        chunk.setBlockState(pos.add(0, height - 5, -2), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 1), false);
        chunk.setBlockState(pos.add(0, height - 5, 2), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 1), false);

        chunk.setBlockState(pos.add(2, height - 5, -1), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 1), false);
        chunk.setBlockState(pos.add(2, height - 5, 1), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 1), false);
        chunk.setBlockState(pos.add(-2, height - 5, -1), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 1), false);
        chunk.setBlockState(pos.add(-2, height - 5, 1), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 1), false);

        chunk.setBlockState(pos.add(1, height - 5, -2), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 1), false);
        chunk.setBlockState(pos.add(1, height - 5, 2), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 1), false);
        chunk.setBlockState(pos.add(-1, height - 5, -2), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 1), false);
        chunk.setBlockState(pos.add(-1, height - 5, 2), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 1), false);

    }

    public Chunk populateNoise(StructureAccessor accessor, Chunk chunk, int startY, int noiseSizeY) {

        return chunk;
    }

    @Override
    public Pool<SpawnSettings.SpawnEntry> getEntitySpawnList(Biome biome, StructureAccessor accessor, SpawnGroup group, BlockPos pos) {
        return Pool.empty();
    }

    @Override
    public void populateEntities(ChunkRegion region) {

    }

    @Override
    public void carve(long seed, BiomeAccess access, Chunk chunk, GenerationStep.Carver carver) {

    }

    @Override
    public void generateFeatures(ChunkRegion region, StructureAccessor accessor) {

    }
}
