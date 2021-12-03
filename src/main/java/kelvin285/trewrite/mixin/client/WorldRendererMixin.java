package kelvin285.trewrite.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import kelvin285.trewrite.resources.FastNoiseLite;
import net.minecraft.client.gl.GlUniform;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.*;
import net.minecraft.client.render.chunk.ChunkBuilder;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {

    private float wind_time = 0;
    private float wind_strength = 2;
    private FastNoiseLite noise = new FastNoiseLite();

    @Inject(at=@At("HEAD"), method="renderLayer")
    private void renderLayer(RenderLayer renderLayer, MatrixStack matrices, double d, double e, double f, Matrix4f matrix4f, CallbackInfo info) {
        Shader shader = RenderSystem.getShader();
        if (shader.modelViewMat != null) {
            shader.modelViewMat.set(matrices.peek().getModel());
        }

        wind_time+=0.01f;

        if (shader.getUniform("wind_time") != null &&
        shader.getUniform("wind_dir") != null &&
                shader.getUniform("wind_strength") != null) {
            Vec3f windDir = new Vec3f(noise.GetNoise(wind_time, 0), 0, noise.GetNoise(0, wind_time));
            windDir.normalize();
            shader.getUniform("wind_time").set(wind_time);
            shader.getUniform("wind_dir").set(windDir.getX(), windDir.getY(), windDir.getZ());
            shader.getUniform("wind_strength").set(2.0f);
        }

    }

    @Inject(at=@At("RETURN"), method="renderLayer")
    private void renderLayerReturn(RenderLayer renderLayer, MatrixStack matrices, double d, double e, double f, Matrix4f matrix4f, CallbackInfo info) {
        Shader shader = RenderSystem.getShader();

        if (shader.getUniform("wind_time") != null &&
                shader.getUniform("wind_dir") != null &&
        shader.getUniform("wind_strength") != null) {
            Vec3f windDir = new Vec3f(noise.GetNoise(wind_time, 0), 0, noise.GetNoise(0, wind_time));
            windDir.normalize();
            shader.getUniform("wind_time").set(wind_time);
            shader.getUniform("wind_dir").set(windDir.getX(), windDir.getY(), windDir.getZ());
            shader.getUniform("wind_strength").set(2.0f);
        }

    }
}
