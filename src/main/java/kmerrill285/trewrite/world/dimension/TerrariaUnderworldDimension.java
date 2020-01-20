package kmerrill285.trewrite.world.dimension;

import kmerrill285.trewrite.blocks.BlocksT;
import kmerrill285.trewrite.util.Util;
import kmerrill285.trewrite.world.biome.provider.SkyBiomeProvider;
import kmerrill285.trewrite.world.biome.provider.TerrariaBiomeProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MusicTicker.MusicType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.biome.provider.OverworldBiomeProviderSettings;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.dimension.OverworldDimension;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.OverworldGenSettings;

public class TerrariaUnderworldDimension extends OverworldDimension {

	public TerrariaUnderworldDimension(World worldIn, DimensionType typeIn) {
		super(worldIn, typeIn);
	}
	
	public Vec3d currentColor = new Vec3d(0, 0, 0);
	public Vec3d newColor = new Vec3d(0, 0, 0);
	
	@Override

	public ChunkGenerator<?> createChunkGenerator()

	{
			OverworldGenSettings overworldGenSettings = new OverworldGenSettings();
	        
//	        SingleBiomeProviderSettings biomeProviderSettings = new SingleBiomeProviderSettings();
	        OverworldBiomeProviderSettings biomeProviderSettings = new OverworldBiomeProviderSettings();
	        biomeProviderSettings.setWorldInfo(world.getWorldInfo());

	        biomeProviderSettings.setGeneratorSettings(overworldGenSettings);	
//	        biomeProviderSettings.setBiome(BiomeT.LOWLANDS);
	        
	        return new TerrariaUnderworldChunkGenerator(world, new SkyBiomeProvider(biomeProviderSettings), overworldGenSettings);
	}
	
	public float getCloudHeight() {
		return 99999f;
	}
	
	public Vec3d getFogColor(float celestialAngle, float partialTicks) {
		
		if (Minecraft.getInstance().player != null) {
			if (Minecraft.getInstance().player.posY < Util.caveLevel) {
				return currentColor.mul(1.5f, 1.5f, 1.5f);
			}
		}
		
		Vec3d mix = currentColor.add(super.getFogColor(celestialAngle, partialTicks)).mul(1.5f, 1.5f, 1.5f);
		return new Vec3d(mix.x / 2.0, mix.y / 2.0, mix.z / 2.0);
	}
	
	public boolean isNether() {
		return false;
	}
	
	public MusicType getMusicType() {
		
		return MusicType.NETHER;
	}
	
	public double getVoidFogFactor() {
		return 0.0;
	}
	
	public Vec3d getSkyColor(BlockPos cameraPos, float partialTicks) {
		
		
		World world = this.getWorld();
		
		if (cameraPos.getY() < Util.caveLevel) {
			newColor = new Vec3d(0.25, 0, 0);
		}
		
		BlockPos rand = cameraPos.add(world.rand.nextInt(100) - 50, -30, world.rand.nextInt(100) - 50);
		long e = world.getGameTime() % 5;
		
		if (Minecraft.getInstance().isGamePaused() == false)
		if (world.rand.nextInt(10) == 0 && e == 0)
		{
			for (int i = 0; i < 35; i++) {
				BlockPos pos2 = new BlockPos(rand.getX(), rand.getY() + i, rand.getZ());
				
				float speed = 0.25f;
				
				if (world.getBlockState(new BlockPos(pos2.getX(), pos2.getY() + 1, pos2.getZ())).getBlock() == Blocks.AIR) {
					if (world.getBlockState(pos2).getBlock() == BlocksT.CORRUPT_GRASS ||
							world.getBlockState(pos2).getBlock() == BlocksT.EBONSTONE ||
							world.getBlockState(pos2).getBlock() == BlocksT.EBONSAND) {
						for (int a = 0; a < world.rand.nextInt(10); a++)
						world.addParticle(ParticleTypes.PORTAL, pos2.getX() + world.rand.nextDouble() - 0.5f, pos2.up().getY(), pos2.getZ() + world.rand.nextDouble() - 0.5f, 0, 0.1f, 0);
					}
					
					if (world.getBlockState(pos2).getBlock() == BlocksT.SAND||
							world.getBlockState(pos2).getBlock() == BlocksT.RED_SAND||
							world.getBlockState(pos2).getBlock() == BlocksT.EBONSAND) {
						for (int a = 0; a < world.rand.nextInt(10); a++) {
							world.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, pos2.getX(), pos2.up().getY(), pos2.getZ(), (world.rand.nextDouble() - 0.5f) * speed, 0.01f, (world.rand.nextDouble()) * speed + ((double)a / 10.0) * speed);
						}
					}
				}
				
				
			}
		}
		
		double mul = 0.02f;
		
		
		
		currentColor = new Vec3d(currentColor.x + (newColor.x - currentColor.x) * mul,
				currentColor.y + (newColor.y - currentColor.y) * mul,
				currentColor.z + (newColor.z - currentColor.z) * mul);
		
		
		return currentColor;
		
	}

}
