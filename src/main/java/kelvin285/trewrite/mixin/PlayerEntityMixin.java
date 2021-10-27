package kelvin285.trewrite.mixin;

import kelvin285.trewrite.audio.AudioRegistry;
import kelvin285.trewrite.renderers.animations.CustomAnimationController;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements IAnimatable {

    private AnimationFactory factory = new AnimationFactory(this);

    private AnimationBuilder anim_idle;
    private AnimationBuilder anim_walking;
    private AnimationBuilder anim_running;
    private AnimationBuilder anim_jump;
    private AnimationBuilder anim_jump2;
    private AnimationBuilder anim_flip;
    private AnimationBuilder anim_sneak_idle;
    private AnimationBuilder anim_sneak_walk;
    private float flip_time = 0;
    private AnimationBuilder current_jump_anim;

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Shadow
    public void incrementStat(Identifier stat) {

    }

    @Shadow
    public void addExhaustion(float exhaustion) {

    }

    @Inject(at = @At("HEAD"), method = "damage", cancellable = true)
    public void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> info) {
        if (source == DamageSource.FALL) {
            amount -= 5;
            if (amount < 0) {
                info.setReturnValue(false);
            } else {
                info.setReturnValue(super.damage(source, amount));
            }
        }
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event)
    {

        CustomAnimationController controller = (CustomAnimationController)event.getController();

        controller.speed = 1;

        if (this.anim_idle == null) {
            anim_idle = new AnimationBuilder().addAnimation("player.idle", true);
            anim_walking = new AnimationBuilder().addAnimation("player.walk", true);
            anim_running = new AnimationBuilder().addAnimation("player.run", true);
            anim_jump = new AnimationBuilder().addAnimation("player.jump", true);
            anim_jump2 = new AnimationBuilder().addAnimation("player.jump2", true);
            anim_flip = new AnimationBuilder().addAnimation("player.flip", false);
            anim_sneak_idle = new AnimationBuilder().addAnimation("player.sneak_idle", true);
            anim_sneak_walk = new AnimationBuilder().addAnimation("player.sneak_walk", true);
            current_jump_anim = anim_jump2;
        }
        boolean moving = Math.abs(this.getVelocity().x) > 0.01 || Math.abs(this.getVelocity().z) > 0.01;
        boolean walking = this.isOnGround() && moving;
        boolean sprinting = this.isSprinting();

        //event.getController().easingType = EasingType.EaseInOutCubic;

        var current_idle = anim_idle;

        var current_walk = (sprinting ? this.anim_running : this.anim_walking);

        if (isSneaking()) {
            current_idle = anim_sneak_idle;
            current_walk = anim_sneak_walk;
        }

        var current_anim = walking ? current_walk : current_idle;

        if (this.isOnGround()) {
            if (walking && !sprinting) {
                controller.speed = 0.75f;
            }
            if (!walking) {
                controller.speed = 0.25f;
            }
        }

        if (!this.isOnGround()) {
            current_anim = current_jump_anim;
            if (this.flip_time > 0) {
                flip_time--;
                current_anim = anim_flip;
                controller.speed = 2.5f;
            }
        }
        event.getController().setAnimation(current_anim);
        return PlayState.CONTINUE;
    }

    @Inject(at = @At("HEAD"), method = "tick", cancellable = true)
    public void tick(CallbackInfo info) {
        if (this.isOnGround()) {
            jump_timer++;
        }
    }
    private int jump_timer = 0;
    private int jump_integer = 0;
    private int last_jump_time = 0;

    @Override
    public void jump() {
        super.jump();
        if (this.jump_timer > last_jump_time + 5) {
            jump_integer = 0;
        }
        last_jump_time = this.jump_timer;
        this.incrementStat(Stats.JUMP);
        if (this.isSprinting()) {
            this.addExhaustion(0.2F);
        } else {
            this.addExhaustion(0.05F);
        }

        float dampener = 0.5f;
        if (isSprinting()) dampener = 1.0f;

        if (jump_integer == 0) {
            this.world.playSound((PlayerEntity)null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_PLAYER_BIG_FALL, this.getSoundCategory(), 0.25F * dampener, 0.7F);

            current_jump_anim = anim_jump;
            jump_integer++;
        } else if (jump_integer == 1) {
            current_jump_anim = anim_jump2;
            this.world.playSound((PlayerEntity)null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_PLAYER_BIG_FALL, this.getSoundCategory(), 0.5F * dampener, 0.6F);

            jump_integer = isSprinting() ? 2 : 0;
        } else {
            this.world.playSound((PlayerEntity)null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_PLAYER_BIG_FALL, this.getSoundCategory(), 0.75F, 0.5F);

            this.world.playSound((PlayerEntity)null, this.getX(), this.getY(), this.getZ(), AudioRegistry.CustomSoundEvent.PLAYER_FLIP.getSound(), this.getSoundCategory(), 0.4F, 1.0F);

            current_jump_anim = anim_jump2;
            flip_time = 40;
            jump_integer = 0;
        }


    }


    @Override
    protected float getJumpVelocity() {
        float vel = 0.42F * this.getJumpVelocityMultiplier();
        if (jump_integer > 0) {
            if (jump_integer > 1) {
                vel *= 1.6F;
            } else {
                vel *= 1.25F;
            }
        }
        return vel;
    }

    @Override
    public void registerControllers(AnimationData data)
    {
        data.addAnimationController(new CustomAnimationController(this, "controller", 5, this::predicate));

    }


    @Override
    public AnimationFactory getFactory()
    {
        return this.factory;
    }
}
