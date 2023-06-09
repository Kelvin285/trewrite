package kelvin285.trewrite.mixin.client;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.render.Camera;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolItem;
import net.minecraft.item.TridentItem;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.*;
import net.minecraft.world.RaycastContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.client.input.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Field;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {
    @Shadow
    private Input input;
    @Shadow
    private MinecraftClient client;
    @Shadow
    private ClientPlayNetworkHandler networkHandler;

    public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    private float attack_timer = 0;
    private boolean attack_moved = false;

    public float getMovementSpeed() {
        return (float)this.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED) * (attack_timer <= 0 ? 1 : 0.5f);
    }

    @Inject(at = @At("RETURN"), method = "tick", cancellable = true)
    public void tick(CallbackInfo info) {
        Field look_rotation = null;
        float LOOK_ROTATION = 0;
        try {
            look_rotation = PlayerEntity.class.getDeclaredField("look_rotation");
            attack_timer = PlayerEntity.class.getDeclaredField("attack_timer").getFloat(this);
            LOOK_ROTATION = look_rotation.getFloat(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        boolean dash_flag = false;
        if (getMainHandStack() != null) {
            Item item = getMainHandStack().getItem();
            if (item != null) {
                if (item instanceof ToolItem || item instanceof SwordItem || item instanceof TridentItem) {
                    dash_flag = true;
                }
            }
        }

        if (attack_timer > 6 && isSprinting()) {
            if (!attack_moved) {
                if (dash_flag) {
                    this.setVelocity(0, getVelocity().y, 0);
                    this.addVelocity(-(float)Math.sin(Math.toRadians(180 - LOOK_ROTATION)) * 0.5f, 0, -(float)Math.cos(Math.toRadians(180 - LOOK_ROTATION)) * 0.5f);
                }
                attack_moved = true;
            }
        } else {
            attack_moved = false;
        }

        this.stepHeight = 1.0f;
        var camera = client.gameRenderer.getCamera();
        GameOptions options = MinecraftClient.getInstance().options;

        boolean walking = false;
        if (options.keyForward.isPressed() || options.keyBack.isPressed() || options.keyLeft.isPressed() || options.keyRight.isPressed()) {
            if (!isSneaking()) {
                if (options.keySprint.isPressed() || options.sprintToggled) {
                    this.setSprinting(true);
                }
            }
            walking = true;
        }

        if (input.jumping && !this.isSwimming()) {
            if (this.getVelocity().length() > 0.08 && this.getVelocity().y > 0) {
                this.addVelocity(0, 0.04, 0);
            }
        }

        if (options.keyAttack.isPressed()) {
            swingHand(Hand.MAIN_HAND);
            this.networkHandler.sendPacket(new HandSwingC2SPacket(Hand.MAIN_HAND));
        }
    }

    public void UpdateRender() {

        var camera = client.gameRenderer.getCamera();
        GameOptions options = MinecraftClient.getInstance().options;
        var mouse = MinecraftClient.getInstance().mouse;
        //    public RaycastContext(Vec3d start, Vec3d end, RaycastContext.ShapeType shapeType, RaycastContext.FluidHandling fluidHandling, Entity entity) {

        try {
            Vec3d observed_pos = null;
            HitResult target = (HitResult)MinecraftClient.class.getDeclaredField("observed_hit").get(MinecraftClient.getInstance());
            if (target != null) {
                observed_pos = target.getPos();
            }
            PlayerEntity.class.getDeclaredField("observed_pos").set(this, observed_pos);

            this.networkHandler.sendPacket(new PlayerMoveC2SPacket.LookAndOnGround(this.getYaw(), this.getPitch(), this.onGround));

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (options.keyForward.isPressed() || options.keyBack.isPressed() || options.keyLeft.isPressed() || options.keyRight.isPressed()) {

            float rot = 0;
            if (options.keyBack.isPressed()) {
                rot = 180;
                if (options.keyForward.isPressed()) {
                    rot = 0;
                }
            }

            if (options.keyRight.isPressed()) {
                if (options.keyForward.isPressed()) {
                    rot = 45;
                } else if (options.keyBack.isPressed()) {
                    rot = 90 + 45;
                } else {
                    rot = 90;
                }
            }
            if (options.keyLeft.isPressed()) {
                if (options.keyForward.isPressed()) {
                    rot = -45;
                } else if (options.keyBack.isPressed()) {
                    rot = -90 - 45;
                } else {
                    rot = -90;
                }
            }
            Field look_rotation = null;
            float LOOK_ROTATION = 0;
            try {

                look_rotation = PlayerEntity.class.getDeclaredField("look_rotation");
                LOOK_ROTATION = look_rotation.getFloat(this);

                float dampener = attack_timer > 0 ? 0.5f : 1;
                look_rotation.set(this, MathHelper.lerpAngleDegrees(0.5f * dampener, LOOK_ROTATION, getYaw() + rot));

                this.setHeadYaw(LOOK_ROTATION);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

}