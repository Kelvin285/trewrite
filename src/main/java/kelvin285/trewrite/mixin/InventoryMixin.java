package kelvin285.trewrite.mixin;

import net.minecraft.inventory.Inventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Inventory.class)
public interface InventoryMixin {
    @Shadow
    int MAX_COUNT_PER_STACK = 999;

    @Inject(at = @At("RETURN"), method = "getMaxCountPerStack", cancellable = true)
    default void getMaxCountPerStack(CallbackInfoReturnable<Integer> info) {
        info.setReturnValue(999);
    }
}
