package kelvin285.trewrite.mixin.packets.PlayerMoveC2S;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerMoveC2SPacket.Full.class)
public abstract class FullMixin extends PlayerMoveC2SPacket {
    protected FullMixin(double x, double y, double z, float yaw, float pitch, boolean onGround, boolean changePosition, boolean changeLook) {
        super(x, y, z, yaw, pitch, onGround, changePosition, changeLook);
    }

    @Inject(at = @At("HEAD"), method = "read", cancellable = true)
    private static void read(PacketByteBuf buf, CallbackInfoReturnable<Full> info) {
        float look_rotation = buf.readFloat();
        Vec3d observed_blockpos = new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble());
        double f = buf.readDouble();
        double g = buf.readDouble();
        double z = buf.readDouble();
        float p = buf.readFloat();
        float y = buf.readFloat();
        boolean bl = buf.readUnsignedByte() != 0;
        var packet = new Full(f, g, z, p, y, bl);
        try {
            PlayerMoveC2SPacket.class.getDeclaredField("look_rotation").set(packet, look_rotation);
            PlayerMoveC2SPacket.class.getDeclaredField("observed_pos").set(packet, observed_blockpos);
        } catch (Exception e) {
            e.printStackTrace();
        }
        info.setReturnValue(packet);
    }

    @Inject(at = @At("HEAD"), method = "write", cancellable = true)
    public void write(PacketByteBuf buf, CallbackInfo info) {
        try {
            buf.writeFloat(PlayerMoveC2SPacket.class.getDeclaredField("look_rotation").getFloat(this));
            Vec3d observed_pos = ((Vec3d)PlayerMoveC2SPacket.class.getDeclaredField("observed_pos").get(this));
            buf.writeDouble(observed_pos.x);
            buf.writeDouble(observed_pos.y);
            buf.writeDouble(observed_pos.z);
        } catch (Exception e) {
            e.printStackTrace();
        }
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
        buf.writeFloat(pitch);
        buf.writeFloat(yaw);
        buf.writeByte(onGround ? 1 : 0);
        info.cancel();
    }
}
