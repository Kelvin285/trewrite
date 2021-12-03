package kelvin285.trewrite.mixin.client;

import kelvin285.trewrite.renderers.blocks.BlockBufferVertexConsumer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.block.FluidRenderer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(FluidRenderer.class)
public class FluidRendererMixin {
    private void vertex(VertexConsumer vertexConsumer, double x, double y, double z, float red, float green, float blue, float u, float v, int light) {
        ((BlockBufferVertexConsumer)vertexConsumer.vertex(x, y, z).color(red, green, blue, 1.0F).texture(u, v).light(light).normal(0.0F, 1.0F, 0.0F)).flag(1.0f).next();
    }
}
