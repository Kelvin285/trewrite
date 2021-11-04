package kelvin285.trewrite.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.*;
import net.minecraft.world.BlockStateRaycastContext;
import net.minecraft.world.RaycastContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Shadow
    private MinecraftClient client;

    @Inject(at = @At("HEAD"), method = "updateTargetedEntity", cancellable = true)
    public void updateTargetedEntity(float tickDelta, CallbackInfo info) {
        if (this.client.world != null) {
            Camera camera = MinecraftClient.getInstance().gameRenderer.getCamera();
            GameOptions options = MinecraftClient.getInstance().options;

            Entity entity = this.client.getCameraEntity();
            if (entity != null) {

                var mouse = MinecraftClient.getInstance().mouse;
                var window = MinecraftClient.getInstance().getWindow();
                float aspect = window.getWidth() / window.getHeight();
                double screen_x = mouse.getX() / window.getWidth();
                double screen_y = (mouse.getY() / window.getHeight());
                double angle_x = (screen_x - 0.5f) * aspect;
                double angle_y = (screen_y - 0.5f) * (1.0f / aspect);
                double fov = Math.toRadians(options.fov) / 2.0;

                float distance = MinecraftClient.getInstance().interactionManager.getReachDistance();
                boolean includeFluids = false;
                Vec3d pos = camera.getPos();

                Vec3d eye = entity.getEyePos();
                double dist = eye.subtract(pos).length();
                double hyp_x = dist / Math.acos(fov);
                double right = angle_x * Math.sin(fov) * hyp_x * 4f;

                double hyp_y = dist / Math.acos(fov * (1.0f / aspect));
                double up = angle_y * Math.sin(fov) * hyp_y * (2.2f);
                Vector4f vec = new Vector4f((float)right, (float)up, 0, 0);
                vec.rotate(camera.getRotation());
                Vec3d move = new Vec3d(-vec.getX(), -vec.getY(), -vec.getZ());
                move.rotateX((float)Math.toRadians(entity.getPitch()));
                move.rotateY((float)Math.toRadians(180 - entity.getYaw()));
                Vec3d dir = eye.subtract(pos).add(move).normalize();

                Vec3d end = pos.add(dir.x * 15, dir.y * 15, dir.z * 15);

                var cast = client.world.raycast(new RaycastContext(pos, end, RaycastContext.ShapeType.OUTLINE, includeFluids ? RaycastContext.FluidHandling.ANY : RaycastContext.FluidHandling.NONE, entity));
                MinecraftClient.getInstance().crosshairTarget = cast;
                try {
                    MinecraftClient.class.getDeclaredField("observed_hit").set(MinecraftClient.getInstance(), cast);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (MinecraftClient.getInstance().crosshairTarget.getPos().distanceTo(entity.getPos()) > distance || options.keyUse.isPressed()) {
                    MinecraftClient.getInstance().crosshairTarget = BlockHitResult.createMissed(end, Direction.fromVector(0, 1, 0), new BlockPos(end));
                }

            }
        }
        info.cancel();
    }

    private Vec3d getRotationVec(float pitch, float yaw, float roll) {
        float f = pitch * 0.017453292F;
        float g = -yaw * 0.017453292F;
        float r = roll * 0.017453292F;
        float cos_yaw = MathHelper.cos(g);
        float sin_yaw = MathHelper.sin(g);
        float cos_pitch = MathHelper.cos(f);
        float sin_pitch = MathHelper.sin(f);
        float cos_roll = MathHelper.cos(r);
        float sin_roll = MathHelper.sin(r);
        return new Vec3d((double)((sin_yaw + cos_roll) * cos_pitch), (double)(-sin_pitch), (double)((cos_yaw + sin_roll) * cos_pitch));
    }

}
