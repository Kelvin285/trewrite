package kelvin285.trewrite.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.advancement.AdvancementsScreen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.screen.multiplayer.SocialInteractionsScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.Perspective;
import net.minecraft.client.util.NarratorManager;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.security.auth.callback.Callback;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    @Shadow
    GameOptions options;
    @Shadow
    ClientPlayerInteractionManager interactionManager;
    @Shadow
    ClientPlayerEntity player;
    @Shadow
    int itemUseCooldown;
    @Shadow
    Mouse mouse;
    @Shadow
    Screen currentScreen;

    public HitResult observed_hit;

    @Inject(at = @At("HEAD"), method = "handleInputEvents", cancellable = true)
    private void handleInputEvents(CallbackInfo info) {
        Item item = player.getMainHandStack().getItem();
        boolean flag = (item instanceof MiningToolItem || item instanceof AxeItem) && !(item instanceof HoeItem);
        if (this.player.isUsingItem()) {

            if (!this.options.keyAttack.isPressed()) {
                this.interactionManager.stopUsingItem(this.player);
            }

            label115:
            while(true) {
                if (!this.options.keyAttack.wasPressed()) {
                    while(this.options.keyUse.wasPressed()) {
                    }

                    while(true) {
                        if (this.options.keyPickItem.wasPressed()) {
                            continue;
                        }
                        break label115;
                    }
                }
            }
        } else {
            while(this.options.keyAttack.wasPressed()) {
                this.doAttack();

                if (player.getMainHandStack() != null) {

                    if (!flag) {
                        this.doItemUse();
                    }
                }
            }

            while(this.options.keyPickItem.wasPressed()) {
                this.doItemPick();
            }
        }

        if (this.options.keyAttack.isPressed() && this.itemUseCooldown == 0 && !this.player.isUsingItem()) {
            if (!flag) {
                this.doItemUse();
            }
        }

        this.handleBlockBreaking(this.currentScreen == null && this.options.keyAttack.isPressed());
    }

    @Shadow
    void doItemUse(){}
    @Shadow
    void doItemPick(){}
    @Shadow
    void doAttack(){}
    @Shadow
    private void handleBlockBreaking(boolean bl) {}

    @Inject(at = @At("HEAD"), method = "doAttack", cancellable = true)
    void doAttack(CallbackInfo info) {
        if (player.getMainHandStack() != null) {
            Item item = player.getMainHandStack().getItem();
            boolean flag = item instanceof MiningToolItem || item instanceof TridentItem ||
                    item instanceof SwordItem;
            if  (!flag) {
                info.cancel();
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "doItemUse", cancellable = true)
    void doItemUse(CallbackInfo info) {
        if (!this.options.keyAttack.isPressed() || options.keyUse.wasPressed()) {
            info.cancel();
        }
    }
    @Inject(at = @At("HEAD"), method = "handleBlockBreaking", cancellable = true)
    private void handleBlockBreaking(boolean bl, CallbackInfo info) {
        if (player.getMainHandStack() != null) {
            Item item = player.getMainHandStack().getItem();
            boolean flag = item instanceof PickaxeItem || item instanceof AxeItem ||
                    item instanceof ShovelItem || item instanceof ShearsItem || item instanceof HoeItem;
            if  (!flag) {
                info.cancel();
            }
        }
    }

}
