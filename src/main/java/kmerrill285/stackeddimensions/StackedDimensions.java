package kmerrill285.stackeddimensions;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import kmerrill285.stackeddimensions.configuration.Config;
import kmerrill285.stackeddimensions.configuration.DimensionConfigs;
import kmerrill285.stackeddimensions.configuration.DimensionConfiguration;
import kmerrill285.stackeddimensions.events.CommonEventHandler;
import kmerrill285.stackeddimensions.networking.NetworkHandler;
import kmerrill285.stackeddimensions.render.StackedRenderInfo;
import kmerrill285.stackeddimensions.world.StackedWorldType;
import kmerrill285.trewrite.Trewrite;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent.WorldTickEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// The value here should match an entry in the META-INF/mods.toml file
public class StackedDimensions
{
	
	public static ArmorStandEntity camera;
	public static GameRenderer gameRenderer;
	
	public static StackedRenderInfo renderInfo;
	
	public static WorldRenderer worldRenderer;
	
	public static boolean setupWorld = false;
	public static boolean finishSetup = false;
	public static boolean startSetup = false;
	public static boolean reloadOverworld = false;

	public static World renderWorld;
	
	public static boolean loadingChunks = false;
	
	public static int ticks = 0;
		
	static boolean addedLayer = false;

	public static boolean loadRenderers = false;

	public static RayTraceResult blockHit;
	public static boolean DEBUG = true;
	public static int frameCount;
	public static boolean refreshEntities;
	public static List<Entity> renderEntities = new ArrayList<Entity>();
	public static boolean needsToReset;
	
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public StackedDimensions() {
    	DEBUG = Trewrite.DEBUG;
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        
        MinecraftForge.EVENT_BUS.register(this);
        
        NetworkHandler.register();
        MinecraftForge.EVENT_BUS.addListener(StackedDimensions::onWorldTick);
        Config.loadConfig();
        
        MinecraftForge.EVENT_BUS.register(CommonEventHandler.class);

        WorldType[] types2 = new WorldType[WorldType.WORLD_TYPES.length + 1];
        for (int i = 0; i < WorldType.WORLD_TYPES.length; i++) {
        	types2[i + 1] = WorldType.WORLD_TYPES[i];
        }
        types2[0] = new StackedWorldType("Stacked");
        WorldType.WORLD_TYPES = types2;
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        
    }

    public static void onWorldTick(WorldTickEvent event)
	{
	
//		if (DimensionManager.getWorld(event.world.getServer(), t, true, true) == null) {
//			DimensionManager.initWorld(event.world.getServer(), t);
//		}

		
		World world = event.world;
		
		

		DimensionConfiguration config = DimensionConfigs.getConfig(world.dimension.getType().getRegistryName());
		if (config != null)
		for (PlayerEntity player : world.getPlayers()) {
			
			if (player.dimension.getId() == config.getDimension().getId())
			if (player.getPosition().getY() < config.getMin() + 256 && config.below != null) {
				int max = 255;
				DimensionConfiguration c2 = DimensionConfigs.getConfig(config.below);
				if (c2 != null) {
					max = c2.getMax() - 1;
				}
				Util.teleportPlayer((ServerPlayerEntity)player, Util.getDimension(config.below), new BlockPos(player.getPosition().getX(), max, player.getPosition().getZ()));
				needsToReset = true;
				return;
			}
			
			if (player.dimension.getId() == config.getDimension().getId())
			if (player.getPosition().getY() > config.getMax() - 1 && config.above != null) {
				Util.teleportPlayer((ServerPlayerEntity)player, Util.getDimension(config.above), new BlockPos(player.getPosition().getX(), player.getPosition().getY() - (config.getMax()), player.getPosition().getZ()));
				needsToReset = true;
				return;
			}
			
		}
	}
}
