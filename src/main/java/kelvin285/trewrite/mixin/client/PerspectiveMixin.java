package kelvin285.trewrite.mixin.client;

import net.minecraft.client.option.Perspective;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Perspective.class)
public class PerspectiveMixin {

    @Shadow
    private static Perspective[] VALUES;

    @Inject(at = @At("HEAD"), method = "isFirstPerson", cancellable = true)
    public void isFirstPerson(CallbackInfoReturnable<Boolean> info) {
        info.setReturnValue(false);
    }

    @Inject(at = @At("HEAD"), method = "isFrontView", cancellable = true)
    public void isFrontView(CallbackInfoReturnable<Boolean> info) {
        info.setReturnValue(false);
    }

    public Perspective next() {
        return VALUES[2];
    }
}
