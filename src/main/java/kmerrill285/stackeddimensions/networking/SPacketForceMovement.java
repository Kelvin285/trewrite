package kmerrill285.stackeddimensions.networking;

import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.MoverType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.network.NetworkEvent;


public class SPacketForceMovement {
	double x, y, z;
	boolean onGround;
	public SPacketForceMovement(double x, double y, double z, boolean onGround) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.onGround = onGround;
	}
	
	public void encode(PacketBuffer buf) {
		buf.writeDouble(x);
		buf.writeDouble(y);
		buf.writeDouble(z);
		buf.writeBoolean(onGround);
    }
	
	public SPacketForceMovement(PacketBuffer buf) {
		x = buf.readDouble();
		y = buf.readDouble();
		z = buf.readDouble();
		onGround = buf.readBoolean();
	}
	
	public void handle(Supplier<NetworkEvent.Context> ctx) {
		
		ctx.get().enqueueWork(() -> {
			Minecraft.getInstance().player.move(MoverType.PISTON, new Vec3d(x, y, z));
			Minecraft.getInstance().player.setMotion(x != 0 ? 0 : Minecraft.getInstance().player.getMotion().x, y != 0 ? 0 : Minecraft.getInstance().player.getMotion().y, z != 0 ? 0 : Minecraft.getInstance().player.getMotion().z);
			
			Minecraft.getInstance().player.onGround = onGround;
			
        });
        ctx.get().setPacketHandled(true);
	}
}
