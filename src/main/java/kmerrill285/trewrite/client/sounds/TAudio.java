package kmerrill285.trewrite.client.sounds;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class TAudio {
	
	public enum SoundEvents {
		  MENU_MUSIC("menu_music"),
		  COINS("coins"),
		  CORRUPTION_AMBIENT("corruption_ambient"),
		  PURITY_AMBIENT("purity_ambient"),
		  NIGHT_AMBIENT("night_ambient"),
		  WIND_AMBIENT("wind_ambient")
		  ;
		
		  private SoundEvent sound;
		  SoundEvents(String name) {
		    ResourceLocation loc = new ResourceLocation("trewrite", name);
		    sound = new SoundEvent(loc).setRegistryName(name);
		  }
		  public SoundEvent getSound() {
		    return sound;
		  }
	}
	
	@SubscribeEvent
	public static void registerAudio(final RegistryEvent.Register<SoundEvent> event) {
		for (SoundEvents e : SoundEvents.values()) {
			event.getRegistry().register(e.getSound());
		}
	}
}
