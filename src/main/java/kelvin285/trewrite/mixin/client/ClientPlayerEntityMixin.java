package kelvin285.trewrite.mixin.client;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.render.Camera;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Arm;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.RaycastContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.client.input.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {
    @Shadow
    private Input input;
    @Shadow
    private MinecraftClient client;

    public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    public float look_rotation = 0;

    @Inject(at = @At("RETURN"), method = "tick", cancellable = true)
    public void tick(CallbackInfo info) {
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

    }

    public void UpdateRender() {

        var camera = client.gameRenderer.getCamera();
        GameOptions options = MinecraftClient.getInstance().options;
        var mouse = MinecraftClient.getInstance().mouse;
        //    public RaycastContext(Vec3d start, Vec3d end, RaycastContext.ShapeType shapeType, RaycastContext.FluidHandling fluidHandling, Entity entity) {

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
            look_rotation = MathHelper.lerpAngleDegrees(0.5f, look_rotation, getYaw() + rot);

            this.setHeadYaw(look_rotation);
        }
    }

}
