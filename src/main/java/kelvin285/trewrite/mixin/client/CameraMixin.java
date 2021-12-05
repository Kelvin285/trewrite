package kelvin285.trewrite.mixin.client;

import kelvin285.trewrite.options.CustomOptions;
import kelvin285.trewrite.renderers.player.CustomPlayerRenderer;
import kelvin285.trewrite.resources.CameraZoom;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.*;
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
    private Vec3d pos;
    @Shadow
    private BlockPos.Mutable blockPos;
    @Shadow
    private Vec3f horizontalPlane;
    @Shadow
    private Vec3f verticalPlane;
    @Shadow
    private Vec3f diagonalPlane;

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
    protected void setPos(Vec3d vec) {}


    public void moveBy(double x, double y, double z) {
        double d = (double)this.horizontalPlane.getX() * x + (double)this.verticalPlane.getX() * y + (double)this.diagonalPlane.getX() * z;
        double e = (double)this.horizontalPlane.getY() * x + (double)this.verticalPlane.getY() * y + (double)this.diagonalPlane.getY() * z;
        double f = (double)this.horizontalPlane.getZ() * x + (double)this.verticalPlane.getZ() * y + (double)this.diagonalPlane.getZ() * z;
        this.setPos(new Vec3d(this.pos.x + d, this.pos.y + e, this.pos.z + f));
    }

    public float custom_yaw = 0;
    public float custom_pitch = 0;

    private double last_mouse_x = -1;
    private double last_mouse_y = -1;

    private double mouse_x, mouse_y;

    private boolean cursor_locked = false;

    @Inject(at = @At("RETURN"), method = "update", cancellable = true)
    public void update(BlockView area, Entity focusedEntity, boolean thirdPerson, boolean inverseView, float tickDelta, CallbackInfo info) {
        Vec3d last_pos = new Vec3d(this.pos.x, this.pos.y, this.pos.z);
        this.ready = true;
        this.area = area;
        this.focusedEntity = focusedEntity;
        this.thirdPerson = thirdPerson;
        var mouse = MinecraftClient.getInstance().mouse;
        GameOptions options = MinecraftClient.getInstance().options;

        last_mouse_x = mouse_x;
        last_mouse_y = mouse_y;
        mouse_x = mouse.getX();
        mouse_y = mouse.getY();

        if (last_mouse_x == -1) {
            last_mouse_x = mouse_x;
            last_mouse_y = mouse_y;
        }
        double sensitivity = MinecraftClient.getInstance().options.mouseSensitivity;
        double delta_x = (mouse.getX() - last_mouse_x) * sensitivity;
        double delta_y = (mouse.getY() - last_mouse_y) * sensitivity;

        if (options.keyUse.wasPressed() && MinecraftClient.getInstance().currentScreen == null) {
            delta_x = 0;
            delta_y = 0;

            LockCursor();
        }

        if (options.invertYMouse) delta_y *= -1;
        if (options.keyUse.isPressed() && MinecraftClient.getInstance().currentScreen == null) {
            if (!cursor_locked) {
                delta_x = 0;
                delta_y = 0;
            }
            if (!options.keyUse.wasPressed()) {
                custom_pitch += delta_y * tickDelta;
                custom_yaw += delta_x * tickDelta;
            }
            LockCursor();
        } else {
            UnlockCursor();
        }
        custom_pitch = MathHelper.clamp(custom_pitch, -90, 90);

        CameraZoom.UpdateZoom(options);

        this.moveBy(-this.clipToSpace(CameraZoom.ZOOM_LERP), 0.0D, 0.0D);

        double distance = this.pos.distanceTo(focusedEntity.getEyePos());
        if (distance <= 1.5f) {
            CustomPlayerRenderer.CanDrawPlayer = false;
        } else {
            CustomPlayerRenderer.CanDrawPlayer = true;
        }

    }

    public void LockCursor() {
        var mouse = MinecraftClient.getInstance().mouse;
        try {
            Mouse.class.getDeclaredField("needs_to_lock").set(mouse, true);
            mouse.lockCursor();
        } catch (Exception e) {
            e.printStackTrace();
        }
        last_mouse_x = mouse.getX();
        last_mouse_y = mouse.getY();
        cursor_locked = true;
    }

    public void UnlockCursor() {
        var mouse = MinecraftClient.getInstance().mouse;
        try {
            Mouse.class.getDeclaredField("needs_to_lock").set(mouse, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        boolean flag = mouse.isCursorLocked();
        mouse.unlockCursor();
        if (flag) {
            last_mouse_x = mouse.getX();
            last_mouse_y = mouse.getY();
        }
        cursor_locked = false;
    }
}
