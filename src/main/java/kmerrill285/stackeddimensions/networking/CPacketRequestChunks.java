package kmerrill285.stackeddimensions.networking;

import java.util.function.Supplier;

import kmerrill285.stackeddimensions.Util;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;


public class CPacketRequestChunks {
	int x, z;
	ResourceLocation dimension;
	public CPacketRequestChunks(int x, int z, ResourceLocation dimension) {
		this.x = x;
		this.z = z;
		this.dimension = dimension;
		
	}
	
	public void encode(PacketBuffer buf) {
		buf.writeInt(x);
		buf.writeInt(z);
		buf.writeResourceLocation(dimension);
    }
	
	public CPacketRequestChunks(PacketBuffer buf) {
		x = buf.readInt();
		z = buf.readInt();
		dimension = buf.readResourceLocation();
	}
	
	public void handle(Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			
			ServerPlayerEntity player = ctx.get().getSender();
			
			DimensionType t = Util.getDimension(dimension);
			
			
			if (t == null) return;
			DimensionManager.keepLoaded(t, true);
			ServerWorld dim = DimensionManager.getWorld(player.getServer(), t, true, true);
			DimensionManager.keepLoaded(t, true);
			Chunk chunk = dim.getChunk(x, z);
	 		NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity)player), new SPacketSendChunk(chunk, x, z, false));
			
			
        });
        ctx.get().setPacketHandled(true);
	}
}
