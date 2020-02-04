package kmerrill285.trewrite.core.network.client;

import java.util.function.Supplier;

import kmerrill285.trewrite.events.ScoreboardEvents;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.scoreboard.Score;
import net.minecraftforge.fml.network.NetworkEvent;


public class CPacketHeal {
	private float heal;
	public CPacketHeal(float heal) {
		this.heal = heal;
	}
	
	public void encode(PacketBuffer buf) {
		buf.writeFloat(heal);
    }
	
	public CPacketHeal(PacketBuffer buf) {
		heal = buf.readFloat();
	}
	
	public void handle(Supplier<NetworkEvent.Context> ctx) {
		
		ctx.get().enqueueWork(() -> {
			ServerPlayerEntity player = ctx.get().getSender();
			player.heal(heal);
        });
        ctx.get().setPacketHandled(true);
	}
}
