package kelvin285.trewrite.mixin.client;

import kelvin285.trewrite.renderers.player.CustomPlayerRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Quaternion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public class PlayerEntityRendererMixin {

    public CustomPlayerRenderer renderer;

    @Inject(method = "<init>", at = @At("RETURN"))
    void Init(EntityRendererFactory.Context context, boolean slim, CallbackInfo info) {
        renderer = new CustomPlayerRenderer(context);
    }

    @Inject(at = @At("HEAD"), method = "render", cancellable = true)
    public void render(AbstractClientPlayerEntity abstractClientPlayerEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo info) {
        //f = yaw
        //g = pitch
        var camera = MinecraftClient.getInstance().gameRenderer.getCamera();

        float custom_pitch = 0;
        float custom_yaw = 0;
        try {
            custom_pitch = Camera.class.getDeclaredField("custom_pitch").getFloat(camera);
            custom_yaw = Camera.class.getDeclaredField("custom_yaw").getFloat(camera);
            ClientPlayerEntity.class.getDeclaredMethod("UpdateRender").invoke(abstractClientPlayerEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        abstractClientPlayerEntity.setYaw(MathHelper.lerpAngleDegrees(0.25f, abstractClientPlayerEntity.getYaw(), custom_yaw) % 360.0F);
        abstractClientPlayerEntity.setPitch(MathHelper.lerpAngleDegrees(0.25f, abstractClientPlayerEntity.getPitch(), custom_pitch) % 360.0F);


        renderer.render(abstractClientPlayerEntity, f, g, matrixStack, vertexConsumerProvider, i);
        info.cancel();
    }
    @Inject(at = @At("HEAD"), method = "setupTransforms", cancellable = true)
    protected void setupTransforms(AbstractClientPlayerEntity abstractClientPlayerEntity, MatrixStack matrixStack, float f, float g, float h, CallbackInfo info) {

        renderer.applyRotations(abstractClientPlayerEntity, matrixStack, f, g, h);
        info.cancel();
    }
}
