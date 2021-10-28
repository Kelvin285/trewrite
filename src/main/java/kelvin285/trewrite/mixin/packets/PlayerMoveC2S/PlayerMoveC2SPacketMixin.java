package kelvin285.trewrite.mixin.packets.PlayerMoveC2S;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerMoveC2SPacket.class)
public class PlayerMoveC2SPacketMixin {
    public float look_rotation;

    @Inject(at = @At("RETURN"), method = "<init>")
    protected void Init(double x, double y, double z, float yaw, float pitch, boolean onGround, boolean changePosition, boolean changeLook, CallbackInfo info) {
        try {
            look_rotation = PlayerEntity.class.getDeclaredField("look_rotation").getFloat(MinecraftClient.getInstance().player);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
