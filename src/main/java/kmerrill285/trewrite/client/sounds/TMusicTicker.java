package kmerrill285.trewrite.client.sounds;

import java.util.Random;

import kmerrill285.trewrite.blocks.BlocksT;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.gui.screen.WinGameScreen;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.EndDimension;
import net.minecraft.world.dimension.NetherDimension;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TMusicTicker extends MusicTicker {
   private final Random random = new Random();
   private final Minecraft client;
   private ISound currentMusic;
   private int timeUntilNextMusic = 100;
   
   private ISound currentAmbient;
   private int timeUntilNextAmbient = 100;

   private boolean locked = false;
   private boolean lockedAmbient = false;
   
   public TMusicTicker(Minecraft client) {
      super(client);
      this.client = client;
   }

   public void tick() {
      tickMusic();
      tickAmbient();
   }
   
   private TMusicTicker.AmbientTrack getAmbientTrackType() {
	   if (client.player != null) {
		   BlockPos cameraPos = client.player.getPosition();
		   
		   World world = client.world;
			int corruption = 0;
			int highlands = 0;
			int dark = 0;
			int desert = 0;
			int mushroom = 0;
			int jungle = 0;
			int snow = 0;
			int beach = 0;
			
			if (world != null) {
				
				
				
				for (int x = -15; x < 15; x++) {
					for (int y = -15; y < 15; y++) {
						for (int z = -15; z < 15; z++) {
							BlockPos pos2 = new BlockPos(cameraPos.getX() + x, cameraPos.getY() + y, cameraPos.getZ() + z);
							Block block = world.getBlockState(pos2).getBlock();
							if (block == BlocksT.HIGHLANDS_GRASS) {
								highlands++;
							}
							if (block == BlocksT.SNOW || block == BlocksT.ICE) {
								snow++;
							}
							if (block == BlocksT.PODZOL || block == BlocksT.DEEP_MUD || block == BlocksT.BOG_GRASS) {
								dark++;
							}
							if (block == BlocksT.CORRUPT_GRASS || block == BlocksT.EBONSTONE || block == BlocksT.EBONSAND || block == BlocksT.PURPLE_ICE) {
								corruption++;
							}
							if (block == BlocksT.JUNGLE_GRASS) {
								jungle++;
							}
							if (block == BlocksT.MUSHROOM_GRASS) {
								mushroom++;
							}
							if (block == BlocksT.SAND && new Vec3d(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ()).distanceTo(new Vec3d(0, cameraPos.getY(), 0)) < 4500) {
								desert++;
							} else {
								beach++;
							}
						}
					}
				}
			}
			if (desert > 15) {
				return AmbientTrack.WIND_AMBIENT;
			}
			if (snow > 15) {
				return AmbientTrack.WIND_AMBIENT;
			}
			if (corruption > 15) {
				return AmbientTrack.CORRUPTION_AMBIENT;
			}
			
			boolean night = client.world.getDayTime() % 24000L > 15000 && client.world.getDayTime() % 24000L < 22000;
			   
			if (night) {
				return AmbientTrack.NIGHT_AMBIENT;
			}
	   }
	  
	   
	   return AmbientTrack.PURITY_AMBIENT;
   }
   
   private TMusicTicker.MusicType getMusicType() {
	   if (client.player == null) {
		   return TMusicTicker.MusicType.MENU;
	   }
	   return null;
   }
   
   private void tickMusic() {
	   TMusicTicker.MusicType musicticker$musictype = getMusicType();
	   if (musicticker$musictype == null) return;
	      if (this.currentMusic != null) {
	         if (!musicticker$musictype.getSound().getName().equals(this.currentMusic.getSoundLocation())) {
	            this.client.getSoundHandler().stop(this.currentMusic);
	            this.timeUntilNextMusic = MathHelper.nextInt(this.random, 0, musicticker$musictype.getMinDelay() / 2);
	         }

	         if (!this.client.getSoundHandler().isPlaying(this.currentMusic)) {
	            this.currentMusic = null;
	            this.timeUntilNextMusic = Math.min(MathHelper.nextInt(this.random, musicticker$musictype.getMinDelay(), musicticker$musictype.getMaxDelay()), this.timeUntilNextMusic);
	         }
	      }

	      this.timeUntilNextMusic = Math.min(this.timeUntilNextMusic, musicticker$musictype.getMaxDelay());
	      if (this.currentMusic == null && this.timeUntilNextMusic-- <= 0) {
	         this.play(musicticker$musictype);
	      }
   }
   
   private void tickAmbient() {
	   TMusicTicker.AmbientTrack musicticker$musictype = getAmbientTrackType();

	   if (musicticker$musictype == null) return;
	      if (this.currentAmbient != null) {
	         if (!musicticker$musictype.getSound().getName().equals(this.currentAmbient.getSoundLocation())) {
	            this.client.getSoundHandler().stop(this.currentAmbient);
	            this.timeUntilNextAmbient = MathHelper.nextInt(this.random, 0, musicticker$musictype.getMinDelay() / 2);
	         }

	         if (!this.client.getSoundHandler().isPlaying(this.currentAmbient)) {
	            this.currentAmbient = null;
	            this.timeUntilNextAmbient = Math.min(MathHelper.nextInt(this.random, musicticker$musictype.getMinDelay(), musicticker$musictype.getMaxDelay()), this.timeUntilNextAmbient);
	         }
	      }

	      this.timeUntilNextAmbient = Math.min(this.timeUntilNextAmbient, musicticker$musictype.getMaxDelay());
	      if (this.currentAmbient == null && this.timeUntilNextAmbient-- <= 0) {
	         this.playAmbient(musicticker$musictype);
	      }
   }

   /**
    * Plays a music track for the maximum allowable period of time
    */
   public void play(MusicTicker.MusicType type) {
	  if (locked) return;
      this.currentMusic = SimpleSound.music(type.getSound());
      this.client.getSoundHandler().play(this.currentMusic);
      this.timeUntilNextMusic = Integer.MAX_VALUE;
   }
   
   public void play(TMusicTicker.MusicType type) {
	  if (locked) return;
      this.currentMusic = SimpleSound.music(type.getSound());
      this.client.getSoundHandler().play(this.currentMusic);
      this.timeUntilNextMusic = Integer.MAX_VALUE;
   }
   
   public void playAmbient(TMusicTicker.AmbientTrack type) {
		  if (lockedAmbient) return;
	      this.currentAmbient = SimpleSound.music(type.getSound());
	      this.client.getSoundHandler().play(this.currentAmbient);
	      this.timeUntilNextAmbient = Integer.MAX_VALUE;
	   }

   public void stop() {
      if (this.currentMusic != null) {
         this.client.getSoundHandler().stop(this.currentMusic);
         this.currentMusic = null;
         this.timeUntilNextMusic = 0;
      }
      this.locked = false;
   }
   
   public void stopAmbient() {
	      if (this.currentAmbient != null) {
	         this.client.getSoundHandler().stop(this.currentAmbient);
	         this.currentAmbient = null;
	         this.timeUntilNextMusic = 0;
	      }
	      this.locked = false;
	   }
   
   public void lock() {
	   this.locked = true;
   }
   
   public void unlock() {
	   this.locked = false;
   }
   
   public void lockAmbient() {
	   this.lockedAmbient = true;
   }
   
   public void unlockAmbient() {
	   this.lockedAmbient = false;
   }

   public boolean isPlaying(MusicTicker.MusicType type) {
      return this.currentMusic == null ? false : type.getSound().getName().equals(this.currentMusic.getSoundLocation());
   }
   
   public boolean isPlaying(TMusicTicker.MusicType type) {
	      return this.currentMusic == null ? false : type.getSound().getName().equals(this.currentMusic.getSoundLocation());
	   }
   
   public boolean isPlayingAmbient(TMusicTicker.AmbientTrack type) {
	      return this.currentAmbient == null ? false : type.getSound().getName().equals(this.currentAmbient.getSoundLocation());
	   }

   @OnlyIn(Dist.CLIENT)
   public static enum AmbientTrack {
	   CORRUPTION_AMBIENT(TAudio.SoundEvents.CORRUPTION_AMBIENT.getSound(), 0, 0),
	   PURITY_AMBIENT(TAudio.SoundEvents.PURITY_AMBIENT.getSound(), 0, 0),
	   NIGHT_AMBIENT(TAudio.SoundEvents.NIGHT_AMBIENT.getSound(), 0, 0),
	   WIND_AMBIENT(TAudio.SoundEvents.WIND_AMBIENT.getSound(), 0, 0)
	   ;
	   
      private final SoundEvent sound;
      private final int minDelay;
      private final int maxDelay;

      private AmbientTrack(SoundEvent sound, int minDelayIn, int maxDelayIn) {
         this.sound = sound;
         this.minDelay = minDelayIn;
         this.maxDelay = maxDelayIn;
      }

      /**
       * Gets the {@link SoundEvent} containing the current music track's location
       */
      public SoundEvent getSound() {
         return this.sound;
      }

      /**
       * Returns the minimum delay between playing music of this type.
       */
      public int getMinDelay() {
         return this.minDelay;
      }

      /**
       * Returns the maximum delay between playing music of this type.
       */
      public int getMaxDelay() {
         return this.maxDelay;
      }
   }
   
   @OnlyIn(Dist.CLIENT)
   public static enum MusicType {
      MENU(TAudio.SoundEvents.MENU_MUSIC.getSound(), 20, 20);

      private final SoundEvent sound;
      private final int minDelay;
      private final int maxDelay;

      private MusicType(SoundEvent sound, int minDelayIn, int maxDelayIn) {
         this.sound = sound;
         this.minDelay = minDelayIn;
         this.maxDelay = maxDelayIn;
      }

      /**
       * Gets the {@link SoundEvent} containing the current music track's location
       */
      public SoundEvent getSound() {
         return this.sound;
      }

      /**
       * Returns the minimum delay between playing music of this type.
       */
      public int getMinDelay() {
         return this.minDelay;
      }

      /**
       * Returns the maximum delay between playing music of this type.
       */
      public int getMaxDelay() {
         return this.maxDelay;
      }
   }
}
