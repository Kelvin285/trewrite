package kmerrill285.trewrite.util;

import java.lang.reflect.Field;

import kmerrill285.trewrite.Trewrite;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class Sounds {
	public static SoundEvent MUSIC_TITLE;
	@SubscribeEvent
	public static void registerSounds(final RegistryEvent.Register<SoundEvent> event) {
		event.getRegistry().registerAll(
				MUSIC_TITLE = getSound("trewrite_menu")
				);
	}
	
	public static SoundEvent getSound(String sound) {
		return new SoundEvent(new ResourceLocation("trewrite:"+sound)).setRegistryName("trewrite:"+sound);
	}
	
	public static void playMusic(SoundEvent event) {
		MusicTicker music = Minecraft.getInstance().getMusicTicker();
		try {
			Field currentMusic = music.getClass().getDeclaredField(Trewrite.DEBUG ? "currentMusic" : "field_147678_c");	
			Util.makeFieldAccessible(currentMusic);
			
			
			
			Field timeUntilNextMusic = music.getClass().getDeclaredField(Trewrite.DEBUG ? "timeUntilNextMusic" : "field_147676_d");	
			Util.makeFieldAccessible(timeUntilNextMusic);
			Minecraft.getInstance().getSoundHandler().stop();
			currentMusic.set(music, SimpleSound.music(event));
			Minecraft.getInstance().getSoundHandler().play((SimpleSound) currentMusic.get(music));
			timeUntilNextMusic.set(music, Integer.MAX_VALUE);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
}
