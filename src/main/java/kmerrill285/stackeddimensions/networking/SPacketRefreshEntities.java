package kmerrill285.stackeddimensions.networking;

import java.util.function.Supplier;

import kmerrill285.stackeddimensions.StackedDimensions;
import kmerrill285.stackeddimensions.Util;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;


public class SPacketRefreshEntities {
	
	
	public SPacketRefreshEntities() {
		
	}
	
	public void encode(PacketBuffer buf) {
		
    }
	
	public SPacketRefreshEntities(PacketBuffer buf) {
		
	}
	
	public void handle(Supplier<NetworkEvent.Context> ctx) {
		
		
		ctx.get().enqueueWork(() -> {
			if (StackedDimensions.renderWorld != null) {
				StackedDimensions.refreshEntities = true;
				StackedDimensions.renderEntities.clear();
			}
        });
        ctx.get().setPacketHandled(true);
	}
}
