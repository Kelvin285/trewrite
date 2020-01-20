package kmerrill285.trewrite.core.network.client;

import java.util.function.Supplier;

import kmerrill285.trewrite.blocks.BlockT;
import kmerrill285.trewrite.world.dimension.DimensionRegistry;
import kmerrill285.trewrite.world.dimension.Dimensions;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.network.NetworkEvent;


public class CPacketChangeBlock {
	int x, y, z, dimension, state, height;
	boolean moving;
	public CPacketChangeBlock(int x, int y, int z, int dimension, BlockState block, boolean moving) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.dimension = dimension;
		this.state = Block.getStateId(block);
		this.height = 0;
		this.moving = moving;
	}
	
	public CPacketChangeBlock(int x, int y, int z, int dimension, BlockState block, int height, boolean moving) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.dimension = dimension;
		this.state = Block.getStateId(block);
		this.height = height;
		this.moving = moving;
	}
	
	public void encode(PacketBuffer buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeInt(dimension);
		buf.writeInt(state);
		buf.writeInt(height);
		buf.writeBoolean(moving);
    }
	
	public CPacketChangeBlock(PacketBuffer buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		dimension = buf.readInt();
		state = buf.readInt();
		height = buf.readInt();
		moving = buf.readBoolean();
	}
	
	public void handle(Supplier<NetworkEvent.Context> ctx) {
		
		ctx.get().enqueueWork(() -> {
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
				case 3:
					t = DimensionManager.registerOrGetDimension(Dimensions.undergroundLocation, DimensionRegistry.undergroundDimension, null, true);
					break;
				case 4:
					t = DimensionManager.registerOrGetDimension(Dimensions.underworldLocation, DimensionRegistry.underworldDimension, null, true);
					break;
				default:
					t = DimensionType.OVERWORLD;
			}
			if (t == null) return;
			
			DimensionManager.keepLoaded(t, true);
			ServerWorld dim = DimensionManager.getWorld(player.getServer(), t, true, true);
			DimensionManager.keepLoaded(t, true);
			System.out.println("set bloch " + dim.getBlockState(new BlockPos(x, y, z)));
			BlockState bstate = dim.getBlockState(new BlockPos(x, y, z));
			//	public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
			dim.setBlockState(new BlockPos(x, y, z), Block.getStateById(state), moving ? 64 : 1);
        });
        ctx.get().setPacketHandled(true);
	}
}
