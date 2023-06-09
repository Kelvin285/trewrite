package kelvin285.trewrite.renderers.player;

import kelvin285.trewrite.mixin.PlayerEntityMixin;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.ModelWithArms;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3f;
import org.spongepowered.asm.mixin.Mixin;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import java.util.ArrayList;
import java.util.List;

public class CustomPlayerRenderer<T extends LivingEntity&IAnimatable> extends GeoEntityRenderer<T> {
    public CustomPlayerModel model;


    public static boolean CanDrawPlayer = true;

    public CustomPlayerRenderer(EntityRendererFactory.Context ctx) {

        super(ctx, new CustomPlayerModel());
        this.model = (CustomPlayerModel)this.getGeoModelProvider();
    }

    public void render(PlayerEntity entity, float entityYaw, float partialTicks, MatrixStack stack, VertexConsumerProvider bufferIn, int packedLightIn) {
        if (entity == MinecraftClient.getInstance().player && !CanDrawPlayer) {
            return;
        }
        super.render((T)entity, entityYaw, partialTicks * 2, stack, bufferIn, packedLightIn);
    }

    @Override
    public void renderRecursively(GeoBone bone, MatrixStack stack, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {

        if (bone.getName().equals("right_hand")) { // rArmRuff is the name of the bone you to set the item to attach too. Please see Note
            stack.push();
            //You'll need to play around with these to get item to render in the correct orientation
            stack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(-75));
            stack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(0));
            stack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(0));
            //You'll need to play around with this to render the item in the correct spot.
            stack.translate(0.3D, 0.2D, 0.4D);
            //Sets the scaling of the item.
            stack.scale(1.25f, 1.25f, 1.25f);
            // Change mainHand to predefined Itemstack and Mode to what transform you would want to use.
            MinecraftClient.getInstance().getItemRenderer().renderItem(mainHand, ModelTransformation.Mode.THIRD_PERSON_RIGHT_HAND,  packedLightIn, packedOverlayIn, stack, this.rtb, 0);
            stack.pop();
            bufferIn = rtb.getBuffer(RenderLayer.getEntityTranslucent(whTexture));
        }
        super.renderRecursively(bone, stack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    public void applyRotations(T entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw,
                               float partialTicks) {
        if (entityLiving == MinecraftClient.getInstance().player && !CanDrawPlayer) {
            return;
        }
        float dampener = 0.5f;
        if (entityLiving.isSprinting()) {
            dampener = 1;
        }

        float yaw = 0;
        try {
            yaw = PlayerEntity.class.getDeclaredField("look_rotation").getFloat(entityLiving);
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