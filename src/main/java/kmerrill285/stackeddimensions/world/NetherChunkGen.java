package kmerrill285.stackeddimensions.world;

import java.util.Random;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.NetherChunkGenerator;
import net.minecraft.world.gen.NetherGenSettings;
import net.minecraft.world.lighting.WorldLightManager;

public class NetherChunkGen extends NetherChunkGenerator {

	public NetherChunkGen(World worldIn, BiomeProvider provider, NetherGenSettings settingsIn) {
		super(worldIn, provider, settingsIn);
	}
	@Override
	public void makeBedrock (IChunk c, Random rand) {
		
	}

}
