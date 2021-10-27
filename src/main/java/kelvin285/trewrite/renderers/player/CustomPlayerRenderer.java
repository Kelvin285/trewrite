package kelvin285.trewrite.renderers.player;

import kelvin285.trewrite.mixin.PlayerEntityMixin;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3f;
import org.spongepowered.asm.mixin.Mixin;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import java.util.ArrayList;
import java.util.List;

public class CustomPlayerRenderer<T extends LivingEntity&IAnimatable> extends GeoEntityRenderer<T> {
    public CustomPlayerModel model;
    public CustomPlayerRenderer(EntityRendererFactory.Context ctx) {

        super(ctx, new CustomPlayerModel());
        this.model = (CustomPlayerModel)this.getGeoModelProvider();
    }

    public void render(PlayerEntity entity, float entityYaw, float partialTicks, MatrixStack stack, VertexConsumerProvider bufferIn, int packedLightIn) {
       super.render((T)entity, entityYaw, partialTicks * 2, stack, bufferIn, packedLightIn);
    }

    public void applyRotations(T entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw,
                                  float partialTicks) {

        float dampener = 0.5f;
        if (entityLiving.isSprinting()) {
            dampener = 1;
        }

        float yaw = 0;
        try {
            yaw = ClientPlayerEntity.class.getDeclaredField("look_rotation").getFloat(entityLiving);
        }catch (Exception e) {
            e.printStackTrace();
        }
        matrixStackIn.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(-(180.0F - rotationYaw)));
        matrixStackIn.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180.0F - yaw));

        //matrixStackIn.multiply(Quaternion.fromEulerXyz(MathHelper.clamp((float)entityLiving.getVelocity().z * dampener, -10, 10), 0, 0));
        //matrixStackIn.multiply(Quaternion.fromEulerXyz(0, 0, MathHelper.clamp(-(float)entityLiving.getVelocity().x * dampener, -10, 10)));
        super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
    }
}
