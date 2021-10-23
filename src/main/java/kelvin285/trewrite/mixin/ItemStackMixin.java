package kelvin285.trewrite.mixin;

import kelvin285.trewrite.items.ItemModifier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public class ItemStackMixin {
    public ItemModifier modifier = ItemModifier.NONE;

    @Inject(method = "<init>(Lnet/minecraft/nbt/NbtCompound;)V", at = @At("RETURN"))
    private void Constructor(NbtCompound nbt, CallbackInfo info) {
        this.modifier = ItemModifier.valueOf(nbt.getString("modifier"));
    }

    @Inject(at = @At("RETURN"), method = "writeNbt", cancellable = true)
    public void writeNbt(NbtCompound nbt, CallbackInfoReturnable<NbtCompound> info) {
        nbt.putString("modifier", modifier.name());
    }
}
