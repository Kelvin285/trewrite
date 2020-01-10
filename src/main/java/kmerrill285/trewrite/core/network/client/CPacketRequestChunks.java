package kmerrill285.trewrite.core.network.client;

import java.util.function.Supplier;

import kmerrill285.trewrite.core.network.NetworkHandler;
import kmerrill285.trewrite.core.network.server.SPacketSendChunk;
import kmerrill285.trewrite.world.dimension.DimensionRegistry;
import kmerrill285.trewrite.world.dimension.Dimensions;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;


public class CPacketRequestChunks {
	int x, z, dimension;
	public CPacketRequestChunks(int x, int z, int dimension) {
		this.x = x;
		this.z = z;
		this.dimension = dimension;
	}
	
	public void encode(PacketBuffer buf) {
		buf.writeInt(x);
		buf.writeInt(z);
		buf.writeInt(dimension);
    }
	
	public CPacketRequestChunks(PacketBuffer buf) {
		x = buf.readInt();
		z = buf.readInt();
		dimension = buf.readInt();
	}
	
	public void handle(Supplier<NetworkEvent.Context> ctx) {
		
		ctx.get().enqueueWork(() -> {
			if (Minecraft.getInstance().world == null) {
				return;
			}
			ServerPlayerEntity player = ctx.get().getSender();
			
			DimensionType t = null;
			switch (dimension) {
				case -1:
					t = DimensionType.THE_NETHER;
					break;
				case 0:
					t = DimensionType.OVERWORLD;
					break;
				case 1:
					t = DimensionType.THE_END;
					break;
				case 2:
					t = DimensionManager.registerOrGetDimension(Dimensions.skyLocation, DimensionRegistry.skyDimension, null, true);
					break;
				default:
					t = DimensionType.OVERWORLD;
			}
			
			if (t == null) return;
			DimensionManager.keepLoaded(t, true);
			ServerWorld dim = DimensionManager.getWorld(player.getServer(), t, true, true);
			DimensionManager.keepLoaded(t, true);
			
			Chunk chunk = dim.getChunk(x, z);
	 		NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity)player), new SPacketSendChunk(chunk, x, z));
			
        });
        ctx.get().setPacketHandled(true);
	}
}
