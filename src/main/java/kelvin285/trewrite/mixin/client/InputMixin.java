package kelvin285.trewrite.mixin.client;

import net.minecraft.client.input.Input;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Input.class)
public class InputMixin {
    @Shadow
    public boolean pressingForward;
    @Shadow
    public boolean pressingBack;
    @Shadow
    public boolean pressingLeft;
    @Shadow
    public boolean pressingRight;
    @Shadow
    public boolean sneaking;

    @Inject(at = @At("HEAD"), method = "hasForwardMovement", cancellable = true)
    public void hasForwardMovement(CallbackInfoReturnable<Boolean> info) {
        info.setReturnValue(pressingForward || pressingBack || pressingLeft || pressingRight);
    }
}
