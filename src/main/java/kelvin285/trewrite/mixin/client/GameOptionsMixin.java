package kelvin285.trewrite.mixin.client;

import kelvin285.trewrite.options.CustomOptions;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;
import java.util.ArrayList;

@Mixin(GameOptions.class)
public class GameOptionsMixin {

    @Shadow
    public KeyBinding keyForward;
    @Shadow
    public KeyBinding keyLeft;
    @Shadow
    public KeyBinding keyBack;
    @Shadow
    public KeyBinding keyRight;
    @Shadow
    public KeyBinding keyJump;
    @Shadow
    public KeyBinding keySneak;
    @Shadow
    public KeyBinding keySprint;
    @Shadow
    public KeyBinding keyInventory;
    @Shadow
    public KeyBinding keySwapHands;
    @Shadow
    public KeyBinding keyDrop;
    @Shadow
    public KeyBinding keyUse;
    @Shadow
    public KeyBinding keyAttack;
    @Shadow
    public KeyBinding keyPickItem;
    @Shadow
    public KeyBinding keyChat;
    @Shadow
    public KeyBinding keyPlayerList;
    @Shadow
    public KeyBinding keyCommand;
    @Shadow
    public KeyBinding keySocialInteractions;
    @Shadow
    public KeyBinding keyScreenshot;
    @Shadow
    public KeyBinding keyTogglePerspective;
    @Shadow
    public KeyBinding keySmoothCamera;
    @Shadow
    public KeyBinding keyFullscreen;
    @Shadow
    public KeyBinding keySpectatorOutlines;
    @Shadow
    public KeyBinding keyAdvancements;
    @Shadow
    public KeyBinding[] keysHotbar;
    @Shadow
    public KeyBinding keySaveToolbarActivator;
    @Shadow
    public KeyBinding keyLoadToolbarActivator;
    @Shadow
    public KeyBinding[] keysAll;

    @Inject(at = @At("RETURN"), method = "<init>")
    public void Init(MinecraftClient client, File optionsFile, CallbackInfo info) {
        //InputUtil.GLFW_KEY_EQUAL
        //InputUtil.GLFW_KEY_MINUS
        CustomOptions.ZOOM_IN = new KeyBinding("trewrite:key.zoom_in", InputUtil.GLFW_KEY_EQUAL, "key.categories.gameplay");
        CustomOptions.ZOOM_OUT = new KeyBinding("trewrite:key.zoom_out", InputUtil.GLFW_KEY_MINUS, "key.categories.gameplay");

        this.keysAll = (KeyBinding[])ArrayUtils.addAll(new KeyBinding[]{this.keyAttack, this.keyUse, this.keyForward, this.keyLeft, this.keyBack, this.keyRight, this.keyJump, this.keySneak, this.keySprint, this.keyDrop, this.keyInventory, this.keyChat, this.keyPlayerList, this.keyPickItem, this.keyCommand, this.keySocialInteractions, this.keyScreenshot, this.keyTogglePerspective, this.keySmoothCamera, this.keyFullscreen, this.keySpectatorOutlines, this.keySwapHands, this.keySaveToolbarActivator, this.keyLoadToolbarActivator, this.keyAdvancements, CustomOptions.ZOOM_IN, CustomOptions.ZOOM_OUT}, this.keysHotbar);


    }
}
