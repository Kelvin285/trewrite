package kmerrill285.trewrite.core.network.server;

import java.util.function.Supplier;

import kmerrill285.trewrite.Trewrite;
import kmerrill285.trewrite.core.items.ItemStackT;
import kmerrill285.trewrite.entities.EntityItemT;
import kmerrill285.trewrite.items.ItemsT;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;


public class SPacketSpawnItemTerraria {
	
	private ItemStackT stack;
	private BlockPos position;
	
	public SPacketSpawnItemTerraria(ItemStackT stack, BlockPos position) {
		
		this.stack = stack;
		this.position = position;
	}
	
	public static void encode(SPacketSpawnItemTerraria msg, PacketBuffer buf) {
        if (msg.stack != null) {
        	buf.writeString(msg.stack.item.itemName);
            buf.writeInt(msg.stack.size);
        } else {
        	buf.writeString("dirt_block");
        	buf.writeInt(-1);
        }
        buf.writeBlockPos(msg.position);
    }
	
	public static SPacketSpawnItemTerraria decode(PacketBuffer buf) {
		
		String item = buf.readString(100).trim();
//		if (item.contentEquals("null") || item.equalsIgnoreCase("null") || item.startsWith("null")) {
//			return new SPacketSyncInventoryTerraria(buf.readInt(), buf.readInt(), buf.readInt(), null);
//		}
		return new SPacketSpawnItemTerraria(new ItemStackT(ItemsT.getItemFromString(item), buf.readInt()), buf.readBlockPos());
	}
	
	public static void handle(SPacketSpawnItemTerraria msg, Supplier<NetworkEvent.Context> ctx) {
		System.out.println("SPAWN BLOCK ON CLIENT");
		ctx.get().enqueueWork(() -> {
			World world = Minecraft.getInstance().world;
			EntityItemT item = (EntityItemT) Trewrite.ITEM_ENTITY_TYPE.spawnEntity(world, null, null, msg.position, false, false);
			item.item = msg.stack.item.itemName;
			item.stack = msg.stack.size;
        });
        ctx.get().setPacketHandled(true);
	}
}
