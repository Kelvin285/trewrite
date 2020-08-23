package kmerrill285.trewrite.network.client;

import java.util.function.Supplier;

import kmerrill285.trewrite.events.ScoreboardEvents;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.scoreboard.Score;
import net.minecraftforge.fml.network.NetworkEvent;


public class CPacketChangeScore {
	private String score;
	private int add;
	public CPacketChangeScore(String score, int add) {
		this.score = score;
		this.add = add;
	}
	
	public void encode(PacketBuffer buf) {
		buf.writeString(score);
		buf.writeInt(add);
    }
	
	public CPacketChangeScore(PacketBuffer buf) {
		score = buf.readString(100).trim();
		add = buf.readInt();
	}
	
	public void handle(Supplier<NetworkEvent.Context> ctx) {
		
		ctx.get().enqueueWork(() -> {
			ServerPlayerEntity player = ctx.get().getSender();
			
			Score sc = ScoreboardEvents.getScore(player.getWorldScoreboard(), player, score);
			sc.setScorePoints(add);
        });
        ctx.get().setPacketHandled(true);
	}
}
