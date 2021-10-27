package kelvin285.trewrite.audio;

import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class AudioRegistry {
    public enum CustomSoundEvent {
        MENU_MUSIC("menu_music"),
        COINS("coins"),
        PLAYER_BIG_JUMP("player.big_jump"),
        PLAYER_FLIP("player.flip"),
        CORRUPTION_AMBIENT("corruption_ambient"),
        PURITY_AMBIENT("purity_ambient"),
        NIGHT_AMBIENT("night_ambient"),
        WIND_AMBIENT("wind_ambient")
        ;

        private SoundEvent sound;
        private String name = "";
        CustomSoundEvent(String name) {
            Identifier loc = new Identifier("trewrite", name);
            this.name = "trewrite:"+name;
            sound = new SoundEvent(loc);
        }
        public SoundEvent getSound() {
            return sound;
        }
    }

    public static void RegisterSoundEvents() {
        for (CustomSoundEvent e : CustomSoundEvent.values()) {
            Registry.register(Registry.SOUND_EVENT, e.name, e.getSound());
        }
    }
}
