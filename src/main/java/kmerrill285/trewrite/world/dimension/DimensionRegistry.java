package kmerrill285.trewrite.world.dimension;

import net.minecraftforge.common.ModDimension;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ObjectHolder;

@EventBusSubscriber(modid = "trewrite", bus = Bus.MOD)
public class DimensionRegistry {
	 @ObjectHolder("trewrite:sky")
    public static final ModDimension skyDimension = null;

  @SubscribeEvent
	public static void onDimensionRegistryEvent(RegistryEvent.Register<ModDimension> event)
	{
		event.getRegistry().register(new SkyDimension().setRegistryName("trewrite:sky"));
	}

}

