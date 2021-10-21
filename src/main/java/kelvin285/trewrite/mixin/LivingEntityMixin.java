package kelvin285.trewrite.mixin;

import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(at = @At("RETURN"), method = "getJumpVelocity", cancellable = true)
    protected void getJumpVelocity(CallbackInfoReturnable<Float> info) {
        //info.setReturnValue(info.getReturnValueF() * 1.5f);
    }

    @Inject(at = @At("RETURN"), method = "damage", cancellable = true)
    public void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> info) {
        if (!PlayerEntity.class.isInstance(this)) {
            if (source == DamageSource.FALL) {
                info.setReturnValue(false);
            }
        }
    }
}
