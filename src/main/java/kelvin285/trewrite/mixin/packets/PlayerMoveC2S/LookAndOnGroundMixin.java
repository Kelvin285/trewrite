package kelvin285.trewrite.mixin.packets.PlayerMoveC2S;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerMoveC2SPacket.LookAndOnGround.class)
public abstract class LookAndOnGroundMixin extends PlayerMoveC2SPacket {
    protected LookAndOnGroundMixin(double x, double y, double z, float yaw, float pitch, boolean onGround, boolean changePosition, boolean changeLook) {
        super(x, y, z, yaw, pitch, onGround, changePosition, changeLook);
    }

    @Inject(at = @At("HEAD"), method = "read", cancellable = true)
    private static void read(PacketByteBuf buf, CallbackInfoReturnable<LookAndOnGround> info) {
        float look_rotation = buf.readFloat();
        float p = buf.readFloat();
        float y = buf.readFloat();
        boolean bl = buf.readUnsignedByte() != 0;
        var packet = new LookAndOnGround(y, p, bl);
        try {
            PlayerMoveC2SPacket.class.getDeclaredField("look_rotation").set(packet, look_rotation);
        } catch (Exception e) {
            e.printStackTrace();
        }
        info.setReturnValue(packet);
    }

    @Inject(at = @At("HEAD"), method = "write", cancellable = true)
    public void write(PacketByteBuf buf, CallbackInfo info) {
        try {
            buf.writeFloat(PlayerMoveC2SPacket.class.getDeclaredField("look_rotation").getFloat(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
        buf.writeFloat(pitch);
        buf.writeFloat(yaw);
        buf.writeByte(onGround ? 1 : 0);
        info.cancel();
    }
}
