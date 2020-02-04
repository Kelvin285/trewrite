package kmerrill285.trewrite.paintings;

import net.minecraft.block.Block;
import net.minecraft.entity.item.PaintingType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class PaintingsT {
	@SubscribeEvent
	public static void registerPaintings(final RegistryEvent.Register<PaintingType> event) {
		event.getRegistry().registerAll(
				);
	}
}
