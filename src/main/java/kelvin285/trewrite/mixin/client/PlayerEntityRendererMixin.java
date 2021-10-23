package kelvin285.trewrite.mixin.client;

import kelvin285.trewrite.renderers.player.CustomPlayerRenderer;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
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
        renderer.render(abstractClientPlayerEntity, f, g, matrixStack, vertexConsumerProvider, i);
        info.cancel();
    }
    @Inject(at = @At("HEAD"), method = "setupTransforms", cancellable = true)
    protected void setupTransforms(AbstractClientPlayerEntity abstractClientPlayerEntity, MatrixStack matrixStack, float f, float g, float h, CallbackInfo info) {
        renderer.applyRotations(abstractClientPlayerEntity, matrixStack, f, g, h);
    }
}
