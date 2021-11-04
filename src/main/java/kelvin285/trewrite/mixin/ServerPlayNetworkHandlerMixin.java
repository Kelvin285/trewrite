package kelvin285.trewrite.mixin;

import net.minecraft.entity.MovementType;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.NetworkThreadUtils;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameMode;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandlerMixin {

    @Shadow
    public ServerPlayerEntity player;

    @Inject(at = @At("HEAD"), method = "onPlayerMove", cancellable = true)
    public void onPlayerMove(PlayerMoveC2SPacket packet, CallbackInfo info) {
        try {
            float look_rotation = PlayerMoveC2SPacket.class.getDeclaredField("look_rotation").getFloat(packet);
            PlayerEntity.class.getDeclaredField("look_rotation").set(this.player, look_rotation);


            Vec3d observed_pos = (Vec3d)PlayerMoveC2SPacket.class.getDeclaredField("observed_pos").get(packet);
            PlayerEntity.class.getDeclaredField("observed_pos").set(this.player, observed_pos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
