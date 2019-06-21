package kmerrill285.trewrite;

import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import kmerrill285.trewrite.commands.GiveTerraria;
import kmerrill285.trewrite.core.client.EventHandlerClient;
import kmerrill285.trewrite.core.client.KeyRegistry;
import kmerrill285.trewrite.core.inventory.container.ContainerTerrariaInventory;
import kmerrill285.trewrite.core.inventory.container.GuiContainerTerraria;
import kmerrill285.trewrite.core.inventory.container.TerrariaContainerHandler;
import kmerrill285.trewrite.core.network.NetworkHandler;
import kmerrill285.trewrite.crafting.Recipes;
import kmerrill285.trewrite.entities.EntityItemT;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.FMLPlayMessages;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("trewrite")
public class Trewrite
{
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();
	public static final EntityType<EntityItemT> ITEM_ENTITY_TYPE = EntityType.register("trewrite" + ":entityitemt", EntityType.Builder.create(EntityItemT.class, EntityItemT::new).tracker(256, 1, true));

    
    
    public Trewrite() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        NetworkHandler.register();
        //MinecraftForge.EVENT_BUS.register(EntityEvents.class);
        
        
    }

    private void setup(final FMLCommonSetupEvent event)
    {
    	
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
//      CuriosAPI.registerIcon("accessory", new ResourceLocation("trewrite", "gui/accessory"));

    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
//        InterModComms.sendTo("curios", CuriosAPI.IMC.REGISTER_TYPE, () -> new CurioIMCMessage("accessory").setSize(5));
    }

    private void processIMC(final InterModProcessEvent event)
    {
        // some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.getMessageSupplier().get()).
                collect(Collectors.toList()));
    }
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        // do something when the server starts
        LOGGER.info("HELLO from server starting");
//        GiveTerraria.register(event.getCommandDispatcher());
    }
    
    @Mod.EventBusSubscriber(modid = "trewrite", value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientProxy {

        @SubscribeEvent
        public static void setupClient(FMLClientSetupEvent evt) {
            MinecraftForge.EVENT_BUS.register(new EventHandlerClient());
//            MinecraftForge.EVENT_BUS.resgister(new GuiEventHandler());
//            MinecraftForge.EVENT_BUS.addListener(ClientProxy::onTextureStitch);
            ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.GUIFACTORY, () -> Trewrite.ClientProxy::getGuiContainer);
            KeyRegistry.registerKeys();
//            CuriosAPI.registerIcon("ring", new ResourceLocation(MODID, "item/empty_ring_slot"));
//            CuriosAPI.registerIcon("necklace", new ResourceLocation(MODID, "item/empty_necklace_slot"));
//            CuriosAPI.registerIcon("body", new ResourceLocation(MODID, "item/empty_body_slot"));
//            CuriosAPI.registerIcon("back", new ResourceLocation(MODID, "item/empty_back_slot"));
//            CuriosAPI.registerIcon("head", new ResourceLocation(MODID, "item/empty_head_slot"));
//            CuriosAPI.registerIcon("belt", new ResourceLocation(MODID, "item/empty_belt_slot"));
//            CuriosAPI.registerIcon("charm", new ResourceLocation(MODID, "item/empty_charm_slot"));
        }

//        @SubscribeEvent
//        public static void postSetupClient(FMLLoadCompleteEvent evt) {
//            Map<String, RenderPlayer> skinMap = Minecraft.getInstance().getRenderManager().getSkinMap();
//
////            for (RenderPlayer render : skinMap.values()) {
////                render.addLayer(new LayerCurios());
////            }
//        }

//        public static void onTextureStitch(TextureStitchEvent.Pre evt) {
//            TextureMap map = evt.getMap();
//            IResourceManager manager = Minecraft.getInstance().getResourceManager();
//            CuriosRegistry.processIcons();
//
//            for (ResourceLocation resource : CuriosAPI.getIcons().values()) {
//                map.registerSprite(manager, resource);
//            }
//            map.registerSprite(manager, new ResourceLocation("curios:item/empty_generic_slot"));
//        }

        private static GuiScreen getGuiContainer(FMLPlayMessages.OpenContainer msg) {
        	
            if (msg.getId().equals(TerrariaContainerHandler.ID)) {
                EntityPlayerSP sp = Minecraft.getInstance().player;
                PacketBuffer buffer = msg.getAdditionalData();
                return new GuiContainerTerraria(new ContainerTerrariaInventory(sp), 0, 0);
            }
            return null;
        }
    }
}
