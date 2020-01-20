package kmerrill285.trewrite.world.client;

import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSection;

public class ChunkEncoder {
	private static int EMPTY = 0, WATER = 1, LAVA = 2, FLOWING_WATER = 3, FLOWING_LAVA = 4;
	
	
	public static void readIntoChunk(Chunk chunk, PacketBuffer buf) {
		if (chunk == null) {
			return;
		}
		ChunkSection[] storage = chunk.getSections();
		
		int[] s = new int[storage.length];
		for (int i = 0; i < storage.length; i++) {
			s[i] = buf.readInt();
		}
		int[] y = new int[storage.length];
		for (int i = 0; i < storage.length; i++) {
			y[i] = buf.readInt();
		}
		
		for (int i = 0; i < storage.length; i++) {
			if (s[i] == 1) {
				if (storage[i] == Chunk.EMPTY_SECTION) {
					storage[i] = new ChunkSection(y[i]);
				}
				storage[i].read(buf);
			} else {
				storage[i] = Chunk.EMPTY_SECTION;
			}
		}
		
	}
	
	public static PacketBuffer encodeChunk(Chunk chunk, PacketBuffer buf) {
		
		ChunkSection[] storage = chunk.getSections();
				
		
		
		for (int i = 0; i < storage.length; i++) {
			if (i != 0 && i != storage.length-1)
			{
				buf.writeInt(0);
				continue;
			}
			if (storage[i] != Chunk.EMPTY_SECTION) {
				buf.writeInt(1);
			} else {
				buf.writeInt(0);
			}
		}
		
		for (int i = 0; i < storage.length; i++) {
			if (i != 0 && i != storage.length-1)
			{
				buf.writeInt(0);
				continue;
			}
			if (storage[i] != Chunk.EMPTY_SECTION) {
				buf.writeInt(storage[i].getYLocation());
			} else {
				buf.writeInt(0);
			}
		}
		
		for (int i = 0; i < storage.length; i++) {
			if (i != 0 && i != storage.length-1)
			{
				continue;
			}
			if (storage[i] != Chunk.EMPTY_SECTION) {
				storage[i].write(buf);
			}
		}
		return buf;
	}
}
