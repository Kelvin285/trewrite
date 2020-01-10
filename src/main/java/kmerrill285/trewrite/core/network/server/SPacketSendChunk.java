package kmerrill285.trewrite.core.network.server;

import java.util.function.Supplier;

import kmerrill285.trewrite.events.OverlayEvents;
import kmerrill285.trewrite.world.client.ChunkEncoder;
import kmerrill285.trewrite.world.client.TChunkProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.network.NetworkEvent;


public class SPacketSendChunk {
	
	public Chunk chunk;
	public int x, z;
	public PacketBuffer buf;
	public SPacketSendChunk(Chunk chunk, int x, int z) {
		this.chunk = chunk;
		this.x = x;
		this.z = z;
	}
	
	public void encode(PacketBuffer buf) {
		buf.writeInt(x);
		buf.writeInt(z);
		ChunkEncoder.encodeChunk(chunk, buf);
    }
	
	public SPacketSendChunk(PacketBuffer buf) {
		x = buf.readInt();
		z = buf.readInt();

		this.buf = buf;
	}
	
	public void handle(Supplier<NetworkEvent.Context> ctx) {
		int X = x;
		int Z = z;
		
		ctx.get().enqueueWork(() -> {
			if (OverlayEvents.renderWorld != null) {
				try {
					((TChunkProvider)OverlayEvents.renderWorld.getChunkProvider()).func_217250_a(OverlayEvents.renderWorld, X, Z, buf, new CompoundNBT(), 0, true);
					
				}catch (Exception e) {
					
				}
				
				ChunkEncoder.readIntoChunk(OverlayEvents.renderWorld.getChunk(X, Z), buf);
			}
			
        });
        ctx.get().setPacketHandled(true);
	}
}
