package kelvin285.trewrite.mixin;

import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.Settings.class)
public class ItemSettingsMixin {
    @Shadow
    public int maxDamage;
    @Shadow
    public int maxCount;


    @Inject(at = @At("RETURN"), method = "maxCount", cancellable = true)
    public void maxCount(int maxCount, CallbackInfoReturnable<Item.Settings> info) {
        if (this.maxDamage > 0) {
            throw new RuntimeException("Unable to have damage AND stack.");
        } else {
            this.maxCount = 999;
            info.setReturnValue((Item.Settings)(Object)this);
        }
    }
}
