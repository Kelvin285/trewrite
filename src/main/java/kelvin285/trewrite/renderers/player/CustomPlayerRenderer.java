package kelvin285.trewrite.renderers.player;

import kelvin285.trewrite.mixin.PlayerEntityMixin;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class CustomPlayerRenderer<T extends LivingEntity&IAnimatable> extends GeoEntityRenderer<T> {

    public CustomPlayerRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new CustomPlayerModel());
    }

    public void render(PlayerEntity entity, float entityYaw, float partialTicks, MatrixStack stack, VertexConsumerProvider bufferIn, int packedLightIn) {
        super.render((T)entity, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
    }

    public void applyRotations(T entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw,
                                  float partialTicks) {
        super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
    }
}
