package kmerrill285.stackeddimensions.render;

import java.lang.reflect.Field;
import java.util.List;

import javax.annotation.Nullable;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import kmerrill285.stackeddimensions.StackedDimensions;
import kmerrill285.stackeddimensions.Util;
import net.minecraft.block.BlockState;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.IParticleData;
import net.minecraft.profiler.IProfiler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(value=Dist.CLIENT)
public class RenderWorld extends ClientWorld {

	   public WorldRenderer worldRenderer;
//   private final Int2ObjectMap<Entity> entitiesById = new Int2ObjectOpenHashMap<>();
//      private final List<Entity> globalEntities = Lists.newArrayList();

	public RenderWorld(ClientPlayNetHandler connection, WorldSettings settings, DimensionType dimType,
			int i, IProfiler profiler, WorldRenderer worldRenderer) {
		super(connection, settings, dimType, i, profiler, worldRenderer);
		this.chunkProvider = new StackedChunkProvider(this, 16);
		this.worldRenderer = worldRenderer;
	}

	public void refreshEntities() {
		try {
			Field f = ClientWorld.class.getDeclaredField(StackedDimensions.DEBUG ? "entitiesById" : "field_217429_b");
			Util.makeFieldAccessible(f);
			Int2ObjectMap<Entity> ebi = (Int2ObjectMap<Entity>) f.get(this);
			ebi.clear();
		} catch (Exception e) {
			
		}
		
		try {
			Field f = ClientWorld.class.getDeclaredField(StackedDimensions.DEBUG ? "globalEntities" : "field_217428_a");
			Util.makeFieldAccessible(f);
			List<Entity> ebi = (List<Entity>) f.get(this);
			ebi.clear();
		} catch (Exception e) {
			
		}
		
	}
	
	public StackedChunkProvider chunkProvider;
	
	public Biome getBiome(BlockPos pos) {
		return Biomes.DEFAULT;
	}
	
	public Biome getBiomeBody(BlockPos pos) {
		return Biomes.DEFAULT;
	}
//	public ClientWorld(ClientPlayNetHandler p_i51056_1_, WorldSettings p_i51056_2_, DimensionType dimType, int p_i51056_4_, IProfiler p_i51056_5_, WorldRenderer p_i51056_6_) {
//	      super(new WorldInfo(p_i51056_2_, "MpServer"), dimType, (p_217422_1_, p_217422_2_) -> {
//	         return new ClientChunkProvider((ClientWorld)p_217422_1_, p_i51056_4_);
//	      }, p_i51056_5_, true);
//	      this.connection = p_i51056_1_;
//	      this.worldRenderer = p_i51056_6_;
//	      this.setSpawnPoint(new BlockPos(8, 64, 8));
//	      this.calculateInitialSkylight();
//	      this.calculateInitialWeather();
//	      this.gatherCapabilities(dimension.initCapabilities());
//	      net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.world.WorldEvent.Load(this));
//	   }
	
	public void markSurroundingsForRerender(int sectionX, int sectionY, int sectionZ) {
	      this.worldRenderer.markSurroundingsForRerender(sectionX, sectionY, sectionZ);
	   }
	
	public void notifyBlockUpdate(BlockPos pos, BlockState oldState, BlockState newState, int flags) {
	      this.worldRenderer.notifyBlockUpdate(this, pos, oldState, newState, flags);
	   }

	   public void func_225319_b(BlockPos p_225319_1_, BlockState p_225319_2_, BlockState p_225319_3_) {
	      this.worldRenderer.func_224746_a(p_225319_1_, p_225319_2_, p_225319_3_);
	   }

	   public void sendBlockBreakProgress(int breakerId, BlockPos pos, int progress) {
	      this.worldRenderer.sendBlockBreakProgress(breakerId, pos, progress);
	   }

	   public void playBroadcastSound(int id, BlockPos pos, int data) {
	      this.worldRenderer.broadcastSound(id, pos, data);
	   }

	   public void playEvent(@Nullable PlayerEntity player, int type, BlockPos pos, int data) {
	      try {
	         this.worldRenderer.playEvent(player, type, pos, data);
	      } catch (Throwable throwable) {
	         CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Playing level event");
	         CrashReportCategory crashreportcategory = crashreport.makeCategory("Level event being played");
	         crashreportcategory.addDetail("Block coordinates", CrashReportCategory.getCoordinateInfo(pos));
	         crashreportcategory.addDetail("Event source", player);
	         crashreportcategory.addDetail("Event type", type);
	         crashreportcategory.addDetail("Event data", data);
	         throw new ReportedException(crashreport);
	      }
	   }

	   public void addParticle(IParticleData particleData, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
	      this.worldRenderer.addParticle(particleData, particleData.getType().getAlwaysShow(), x, y, z, xSpeed, ySpeed, zSpeed);
	   }

	   public void addParticle(IParticleData particleData, boolean forceAlwaysRender, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
	      this.worldRenderer.addParticle(particleData, particleData.getType().getAlwaysShow() || forceAlwaysRender, x, y, z, xSpeed, ySpeed, zSpeed);
	   }

	   public void addOptionalParticle(IParticleData particleData, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
	      this.worldRenderer.addParticle(particleData, false, true, x, y, z, xSpeed, ySpeed, zSpeed);
	   }

	   public void func_217404_b(IParticleData p_217404_1_, boolean p_217404_2_, double p_217404_3_, double p_217404_5_, double p_217404_7_, double p_217404_9_, double p_217404_11_, double p_217404_13_) {
	      this.worldRenderer.addParticle(p_217404_1_, p_217404_1_.getType().getAlwaysShow() || p_217404_2_, true, p_217404_3_, p_217404_5_, p_217404_7_, p_217404_9_, p_217404_11_, p_217404_13_);
	   }
	
	public StackedChunkProvider getChunkProvider() {
		return this.chunkProvider;
	}
	
	public Chunk getChunk(int x, int z) {
		return this.chunkProvider.getChunk(x, z, ChunkStatus.SPAWN, true);
	}
	
	public Chunk getChunkAt(BlockPos pos) {
		return this.chunkProvider.getChunk(pos.getX() / 16, pos.getZ() / 16, ChunkStatus.SPAWN, true);
	}
	
	public IChunk getChunk(int x, int z, ChunkStatus status) {
		return this.chunkProvider.getChunk(x, z, status, true);
	}
	
	public IChunk getChunk(int x, int z, ChunkStatus status, boolean load) {
		return this.chunkProvider.getChunk(x, z, status, load);
	}
	
	public IChunk getChunk(BlockPos pos) {
		return this.chunkProvider.getChunk(pos.getX() / 16, pos.getZ() / 16, ChunkStatus.SPAWN, true);
	}
	
	
	public IProfiler getProfile() {
		return this.getProfiler();
	}
	
	public void setProfile(IProfiler p) {
//		this.profiler = p;
	}

	public void onChunkUnloaded(Chunk chunk) {
		
	}

}
