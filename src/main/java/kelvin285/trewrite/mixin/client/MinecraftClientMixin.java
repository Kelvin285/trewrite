package kelvin285.trewrite.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    @Shadow
    public ClientPlayerInteractionManager interactionManager;
    @Shadow
    private int itemUseCooldown;
    @Shadow
    protected int attackCooldown;

    @Shadow
    public ClientPlayerEntity player;

    @Inject(at = @At("HEAD"), method = "doAttack", cancellable = true)
    private void doAttack(CallbackInfo info) {
        System.out.println("attack: " + this.attackCooldown);
        if (attackCooldown > 0) {
            info.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "tick", cancellable = true)
    private void tick(CallbackInfo info) {
        if (player != null) {
            if (player.getMainHandStack() != null) {
                this.attackCooldown = player.getMainHandStack().getCooldown();
            }
        }
    }
}
