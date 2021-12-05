package kelvin285.trewrite.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import kelvin285.trewrite.resources.FastNoiseLite;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.GlUniform;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.*;
import net.minecraft.client.render.chunk.ChunkBuilder;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {

    private float wind_time = 0;
    private float wind_strength = 2;
    private float season_time = 0;
    private FastNoiseLite noise = new FastNoiseLite();

    private static final int StartingDay = 24000 * 20; // sometime in spring

    @Inject(at=@At("HEAD"), method="renderLayer")
    private void renderLayer(RenderLayer renderLayer, MatrixStack matrices, double d, double e, double f, Matrix4f matrix4f, CallbackInfo info) {

        GL15.glActiveTexture(GL15.GL_TEXTURE20);
        MinecraftClient.getInstance().getTextureManager().bindTexture(new Identifier("trewrite", "textures/colormap/seasons"));
        GL15.glActiveTexture(GL15.GL_TEXTURE0);

        Shader shader = RenderSystem.getShader();
        if (shader.modelViewMat != null) {
            shader.modelViewMat.set(matrices.peek().getModel());
        }

        //day length = 24000
        //days in month = 10
        //months in year = 12
        //days in year = 120
        //ticks in year = 2880000

        long world_time = StartingDay + MinecraftClient.getInstance().world.getTimeOfDay();

        season_time = world_time / 2880000.0F;
        season_time %= 1;

        wind_time+=0.01f;

        if (shader.getUniform("wind_time") != null &&
        shader.getUniform("wind_dir") != null &&
                shader.getUniform("wind_strength") != null) {
            Vec3f windDir = new Vec3f(noise.GetNoise(wind_time, 0), 0, noise.GetNoise(0, wind_time));
            windDir.normalize();
            shader.getUniform("wind_time").set(wind_time);
            shader.getUniform("wind_dir").set(windDir.getX(), windDir.getY(), windDir.getZ());
            shader.getUniform("wind_strength").set(2.0f);
            //shader.getUniform("Sampler1").set(20);
            //GL20.glUniform1i(GL20.glGetUniformLocation(shader.getProgramRef(), "Sampler1"), 20);
            shader.getUniform("Season").set(season_time);
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
            shader.getUniform("Season").set(season_time);
        }

    }
}
