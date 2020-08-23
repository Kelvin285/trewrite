package kmerrill285.stackeddimensions.networking;

import java.util.function.Supplier;

import kmerrill285.stackeddimensions.StackedDimensions;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;


public class SPacketSendRenderBlock {
	
	public BlockPos pos;
	public BlockState state;
	public SPacketSendRenderBlock(BlockPos pos, BlockState state) {
		this.pos = pos;
		this.state = state;
	}
	
	public void encode(PacketBuffer buf) {
		buf.writeBlockPos(pos);
		buf.writeInt(Block.getStateId(state));
    }
	
	public SPacketSendRenderBlock(PacketBuffer buf) {
		pos = buf.readBlockPos();
		state = Block.getStateById(buf.readInt());
	}
	
	public void handle(Supplier<NetworkEvent.Context> ctx) {
		
		ctx.get().enqueueWork(() -> {
			if (StackedDimensions.renderWorld != null) {
				if (pos != null && state != null)
					if (StackedDimensions.renderWorld.chunkExists(pos.getX() / 16, pos.getZ() / 16))
				StackedDimensions.renderWorld.setBlockState(pos, state);
			}
        });
        ctx.get().setPacketHandled(true);
	}
}
