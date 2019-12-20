package kmerrill285.trewrite;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import kmerrill285.trewrite.core.network.NetworkHandler;
import kmerrill285.trewrite.events.EntityEvents;
import kmerrill285.trewrite.events.WorldEvents;
import kmerrill285.trewrite.items.modifiers.ItemModifier;
import kmerrill285.trewrite.world.TerrariaWorldType;
import kmerrill285.trewrite.world.WorldStateHolder;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent.WorldTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("trewrite")
public class Trewrite
{
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public static final boolean DEBUG = true;
    
    public Trewrite() {
    	
    	new ItemModifier();
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        
//        FMLJavaModLoadingContext.get().getModEventBus().addListener(OverlayEvents::handleOverlayEvent);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(EntityEvents.class);
//        MinecraftForge.EVENT_BUS.register(ScoreboardEvents.class);
        MinecraftForge.EVENT_BUS.register(WorldEvents.class);
        NetworkHandler.register();
        
        MinecraftForge.EVENT_BUS.addListener(Trewrite::onWorldTick);
        
        WorldType type = WorldType.WORLD_TYPES[0];
        WorldType[] types2 = new WorldType[WorldType.WORLD_TYPES.length + 1];
        for (int i = 0; i < WorldType.WORLD_TYPES.length; i++) {
        	types2[i + 1] = WorldType.WORLD_TYPES[i];
        }
        types2[0] = new TerrariaWorldType("Terraria-Style");
        WorldType.WORLD_TYPES = types2;
    }
    
	public static void onWorldTick(WorldTickEvent event)
	{
		World world = event.world;
		if (!world.isRemote) {
			WorldStateHolder holder = WorldStateHolder.get(world);
		}
	}

    private void setup(final FMLCommonSetupEvent event)
    {
    	System.out.println("TREWRITE MOD SETUP");
//    	MinecraftForge.EVENT_BUS.register(new EntityEvents());
//    	MinecraftForge.EVENT_BUS.register(new OverlayEvents());
//    	MinecraftForge.EVENT_BUS.register(new ScoreboardEvents());
//    	MinecraftForge.EVENT_BUS.register(new WorldEvents());
        // some preinit code
    	
    	
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
//    	RenderingRegistry.registerEntityRenderingHandler(EntityItemT.class, manager -> new RenderEntityItemT(manager, Minecraft.getInstance().getItemRenderer()));
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // some example code to dispatch IMC to another mod
    }

    private void processIMC(final InterModProcessEvent event)
    {
        // some example code to receive and process InterModComms from other mods
    }
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        // do something when the server starts
    }
    
    
}
