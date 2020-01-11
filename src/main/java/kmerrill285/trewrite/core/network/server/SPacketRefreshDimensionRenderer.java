package kmerrill285.trewrite.core.network.server;

import java.util.function.Supplier;

import kmerrill285.trewrite.events.OverlayEvents;
import kmerrill285.trewrite.util.Util;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

public class SPacketRefreshDimensionRenderer {
	
	public SPacketRefreshDimensionRenderer() {
		
	}
	
	public void encode(PacketBuffer buf) {
		
    }
	
	public SPacketRefreshDimensionRenderer(PacketBuffer buf) {
		
	}
	
	public void handle(Supplier<NetworkEvent.Context> ctx) {
		
		ctx.get().enqueueWork(() -> {
			Util.refreshDimensionRenderer = true;
        });
        ctx.get().setPacketHandled(true);
	}
}
