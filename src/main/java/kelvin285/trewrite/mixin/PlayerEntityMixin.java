package kelvin285.trewrite.mixin;

import kelvin285.trewrite.audio.AudioRegistry;
import kelvin285.trewrite.renderers.animations.CustomAnimationController;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
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

import javax.tools.Tool;
import java.util.List;

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
    private AnimationBuilder anim_sword_idle;
    private AnimationBuilder anim_sword_walk;
    private AnimationBuilder anim_sword_run;
    private AnimationBuilder anim_sword_idle_sneak;
    private AnimationBuilder anim_sword_walk_sneak;
    private AnimationBuilder anim_sword_jump;
    private AnimationBuilder anim_sword_jump2;
    private AnimationBuilder anim_sword_flip;
    private AnimationBuilder anim_sword_swing;
    private AnimationBuilder anim_sword_swing2;
    private AnimationBuilder anim_swim;
    private AnimationBuilder anim_swim_idle;
    private AnimationBuilder anim_shortsword_swing;
    private AnimationBuilder anim_shortsword_swing2;
    private AnimationBuilder anim_bow;
    private AnimationBuilder anim_trident;
    private AnimationBuilder anim_throw;

    private float flip_time = 0;
    private AnimationBuilder current_jump_anim;

    protected int attack_state = 0;
    protected int attack_timer = 0;

    public float look_rotation;

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Shadow
    public void incrementStat(Identifier stat) {

    }

    @Shadow
    public void addExhaustion(float exhaustion) {

    }

    @Override
    public boolean isUsingItem() {
        if (getMainHandStack() != null) {
            if (getMainHandStack().getItem() instanceof MiningToolItem) {
                return false;
            }
        }
        return super.isUsingItem();
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



    @Override
    public void swingHand(Hand hand) {
        super.swingHand(hand);
        if (attack_timer < 5) {
            attack_state++;
            attack_state %= 2;
            attack_timer = 30;
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
            anim_sword_idle = new AnimationBuilder().addAnimation("player.sword_idle", true);
            anim_sword_walk = new AnimationBuilder().addAnimation("player.sword_walk", true);
            anim_sword_run = new AnimationBuilder().addAnimation("player.sword_run", true);
            anim_sword_idle_sneak = new AnimationBuilder().addAnimation("player.sword_idle_sneak", true);
            anim_sword_walk_sneak = new AnimationBuilder().addAnimation("player.sword_walk_sneak", true);
            anim_sword_jump = new AnimationBuilder().addAnimation("player.sword_jump", true);
            anim_sword_jump2 = new AnimationBuilder().addAnimation("player.sword_jump2", true);
            anim_sword_flip = new AnimationBuilder().addAnimation("player.sword_flip", true);
            anim_sword_swing = new AnimationBuilder().addAnimation("player.sword_swing", true);
            anim_sword_swing2 = new AnimationBuilder().addAnimation("player.sword_swing2", true);
            anim_shortsword_swing = new AnimationBuilder().addAnimation("player.shortsword_swing", true);
            anim_shortsword_swing2 = new AnimationBuilder().addAnimation("player.shortsword_swing2", true);
            anim_bow = new AnimationBuilder().addAnimation("player.bow", true);
            anim_throw = new AnimationBuilder().addAnimation("player.throw", true);
            anim_trident = new AnimationBuilder().addAnimation("player.trident", true);
            anim_swim_idle = new AnimationBuilder().addAnimation("player.swim_idle", true);
            anim_swim = new AnimationBuilder().addAnimation("player.swim", true);
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
        boolean swim_flag = isSwimming() || isTouchingWater() && !isOnGround() || isSubmergedInWater();
        if (swim_flag) {
            current_idle = anim_swim_idle;
            current_walk = anim_swim;

            if (isSwimming()) {
                walking = true;
            }
        }

        if (isInSwimmingPose() && !isTouchingWater()) {
            swim_flag = true;
            if (!walking) {
                controller.speed = 0;
            }
            current_idle = anim_swim;
            current_walk = anim_swim;
        }

        boolean sword_flag = false;
        boolean trident_flag = false;
        boolean tool_flag = false;
        if (getMainHandStack() != null) {
            Item item = getMainHandStack().getItem();
            if (item != null) {
                if (item instanceof SwordItem) {
                    if (isSneaking()) {
                        current_idle = anim_sword_idle_sneak;
                        current_walk = anim_sword_walk_sneak;
                    } else {
                        current_idle = anim_sword_idle_sneak;
                        current_walk = (sprinting ? anim_sword_run : anim_sword_walk_sneak);
                    }
                    sword_flag = true;
                }
                if (item instanceof ToolItem) {
                    tool_flag = true;
                }
                if (item instanceof TridentItem) {
                    trident_flag = true;
                }

            }
        }


        var current_anim = walking ? current_walk : current_idle;

        if (this.isOnGround() || isSubmergedInWater() || isSwimming()) {
            if (walking && !sprinting) {
                controller.speed = 0.75f;
                if (sword_flag && !isSneaking()) {
                    controller.speed = 3;
                }
            }
            if (!walking) {
                controller.speed = 0.25f;
            }
        }

        if (!this.isOnGround() && !swim_flag) {
            current_anim = current_jump_anim;
            if (sword_flag) {
                if (current_jump_anim == anim_jump) {
                    current_anim = anim_sword_jump;
                } else {
                    current_anim = anim_sword_jump2;
                }
            }
            if (this.flip_time > 0) {
                flip_time--;
                current_anim = anim_flip;
                if (sword_flag) {
                    current_anim = anim_sword_flip;
                }
                controller.speed = 2.5f;
            }
        } else {
            if (attack_timer > 0) {
                if (sword_flag || tool_flag) {
                    controller.speed = 4;
                    if (attack_state == 0) {
                        current_anim = anim_sword_swing;
                    } else {
                        current_anim = anim_sword_swing2;
                    }
                } else if (trident_flag) {
                    controller.speed = 4;
                    if (attack_state == 0) {
                        current_anim = anim_trident;
                    } else {
                        current_anim = anim_shortsword_swing2;
                    }
                }
            }
        }
        if (attack_timer > 0) {
            attack_timer--;
        }
        event.getController().setAnimation(current_anim);
        return PlayState.CONTINUE;
    }

    @Inject(at = @At("HEAD"), method = "tick", cancellable = true)
    public void tick(CallbackInfo info) {
        if (this.isOnGround()) {
            jump_timer++;
        }
        if (attack_timer > 0) {
            float box_width = 1;
            float box_height = 2;
            Vec3d dir = new Vec3d(-(float) Math.sin(Math.toRadians(180 - look_rotation)) * 0.5f, 0, -(float) Math.cos(Math.toRadians(180 - look_rotation)) * 0.5f).normalize();
            Vec3d box_pos = this.getPos().add(dir);
            Box box = new Box(box_pos.x - box_width / 2, box_pos.y, box_pos.z - box_width / 2, box_pos.x + box_width / 2, box_pos.y + box_height, box_pos.z + box_width / 2);
            List<Entity> entities = world.getOtherEntities(this, box);
            var stack = this.getMainHandStack();
            if (stack != null) {
                var item = stack.getItem();
                if (item != null) {
                    float attack_damage = 0;
                    if (item instanceof HoeItem) {
                        attack_damage = 1;
                    }
                    for (var v : stack.getAttributeModifiers(EquipmentSlot.MAINHAND).get(EntityAttributes.GENERIC_ATTACK_DAMAGE)) {
                        if (v.getOperation() == EntityAttributeModifier.Operation.ADDITION) {
                            attack_damage += (float) v.getValue();
                        }
                    }
                    if (attack_damage > 0) {
                        for (int i = 0; i < entities.size(); i++) {
                            entities.get(i).damage(DamageSource.player((PlayerEntity) (Object) this), attack_damage);
                        }
                    }
                }
            }
            attack_timer--;
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
