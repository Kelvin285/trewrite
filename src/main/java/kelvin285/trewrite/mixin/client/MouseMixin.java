package kelvin285.trewrite.mixin.client;

import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(Mouse.class)
public class MouseMixin {
    @Shadow
    public boolean cursorLocked;

    public boolean needs_to_lock;

    @Inject(at = @At("HEAD"), method = "lockCursor", cancellable = true)
    public void lockCursor(CallbackInfo info) {
        if (!needs_to_lock) {
            info.cancel();
        }
    }

    public boolean isCursorLocked() {
        return this.cursorLocked && needs_to_lock;
    }

}
