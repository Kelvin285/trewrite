package kmerrill285.trewrite.core.client;

import kmerrill285.trewrite.core.inventory.container.Containers;
import kmerrill285.trewrite.core.inventory.container.GuiContainerTerrariaChest;
import kmerrill285.trewrite.core.inventory.container.GuiContainerTerrariaInventory;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientProxy {

    @SubscribeEvent
    public static void setupClient(FMLClientSetupEvent evt) {
    	MinecraftForge.EVENT_BUS.register(new EventHandlerClient());
    	ScreenManager.registerFactory(Containers.INVENTORY, GuiContainerTerrariaInventory::new);
    	ScreenManager.registerFactory(Containers.CHEST, GuiContainerTerrariaChest::new);
        KeyRegistry.registerKeys();
    }
}