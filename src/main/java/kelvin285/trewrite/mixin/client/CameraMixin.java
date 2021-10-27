package kelvin285.trewrite.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Quaternion;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public class CameraMixin {

    @Shadow
    private Entity focusedEntity;
    @Shadow
    private Quaternion rotation;
    @Shadow
    private float cameraY;
    @Shadow
    private float lastCameraY;
    @Shadow
    private float pitch;
    @Shadow
    private float yaw;
    @Shadow
    private boolean ready;
    @Shadow
    private BlockView area;
    @Shadow
    private boolean thirdPerson;

    @Shadow
    public void setPos(double x, double y, double z) {
    }

    @Shadow
    public void setRotation(float yaw, float pitch) {
    }

    @Shadow
    private double clipToSpace(double desiredCameraDistance) {
        return 0;
    }

    @Shadow
    protected void moveBy(double x, double y, double z) {

    }

    public float custom_yaw = 0;
    public float custom_pitch = 0;

    private double last_mouse_x = -1;
    private double last_mouse_y = -1;

    @Inject(at = @At("HEAD"), method = "update", cancellable = true)
    public void update(BlockView area, Entity focusedEntity, boolean thirdPerson, boolean inverseView, float tickDelta, CallbackInfo info) {
        this.ready = true;
        this.area = area;
        this.focusedEntity = focusedEntity;
        this.thirdPerson = thirdPerson;
        var mouse = MinecraftClient.getInstance().mouse;
        GameOptions options = MinecraftClient.getInstance().options;
        if (last_mouse_x == -1) {
            last_mouse_x = mouse.getX();
            last_mouse_y = mouse.getY();
        }

        double sensitivity = MinecraftClient.getInstance().options.mouseSensitivity;
        double delta_x = (mouse.getX() - last_mouse_x) * sensitivity;
        double delta_y = (mouse.getY() - last_mouse_y) * sensitivity;
        if (options.invertYMouse) delta_y *= -1;
        if (options.keyUse.isPressed()) {
            custom_pitch += delta_y * tickDelta;
            custom_yaw += delta_x * tickDelta;
        }
        custom_pitch = MathHelper.clamp(custom_pitch, -90, 90);
        last_mouse_x = mouse.getX();
        last_mouse_y = mouse.getY();
        UnlockCursor();
    }

    public void LockCursor() {
        var mouse = MinecraftClient.getInstance().mouse;
        try {
            Mouse.class.getDeclaredField("needs_to_lock").set(mouse, true);
            mouse.lockCursor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void UnlockCursor() {
        var mouse = MinecraftClient.getInstance().mouse;
        try {
            Mouse.class.getDeclaredField("needs_to_lock").set(mouse, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mouse.unlockCursor();
    }
}
