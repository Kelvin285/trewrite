package kelvin285.trewrite.renderers.player;

import kelvin285.trewrite.mixin.PlayerEntityMixin;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

import java.io.File;

public class CustomPlayerModel<T extends LivingEntity&IAnimatable> extends AnimatedGeoModel<T> {

    @Override
    public Identifier getModelLocation(T object) {
        return new Identifier("trewrite", "geo/player/player.geo.json");
    }

    @Override
    public Identifier getTextureLocation(T object) {
        return new Identifier("trewrite", "textures/entities/player/player_full.png");
    }

    @Override
    public Identifier getAnimationFileLocation(T animatable) {
        return new Identifier("trewrite", "animations/player/animations.json");
    }

    @Override
    public Animation getAnimation(String name, IAnimatable animatable) {

        return super.getAnimation(name, animatable);
    }
}
