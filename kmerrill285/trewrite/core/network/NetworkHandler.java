package kmerrill285.trewrite.core.network;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import kmerrill285.trewrite.core.network.client.CPacketCloseInventoryTerraria;
import kmerrill285.trewrite.core.network.client.CPacketEquipItemTerraria;
import kmerrill285.trewrite.core.network.client.CPacketOpenInventoryTerraria;
import kmerrill285.trewrite.core.network.client.CPacketOpenInventoryVanilla;
import kmerrill285.trewrite.core.network.client.CPacketRequestInventoryTerraria;
import kmerrill285.trewrite.core.network.client.CPacketSyncInventoryTerraria;
import kmerrill285.trewrite.core.network.client.CPacketThrowItemTerraria;
import kmerrill285.trewrite.core.network.server.SPacketSendInventoryTerraria;
import kmerrill285.trewrite.core.network.server.SPacketSpawnItemTerraria;
import kmerrill285.trewrite.core.network.server.SPacketSyncInventoryTerraria;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class NetworkHandler {
	private static final String PTC_VERSION = "1";
	public static final SimpleChannel INSTANCE = NetworkRegistry.ChannelBuilder
            .named(new ResourceLocation("trewrite", "main"))
            .networkProtocolVersion(() -> PTC_VERSION)
            .clientAcceptedVersions(PTC_VERSION::equals)
            .serverAcceptedVersions(PTC_VERSION::equals)
            .simpleChannel();
	
	private static int id = 0;
	
	public static void register() {
        registerMessage(CPacketOpenInventoryTerraria.class, CPacketOpenInventoryTerraria::encode, CPacketOpenInventoryTerraria::decode, CPacketOpenInventoryTerraria::handle);
        registerMessage(CPacketOpenInventoryVanilla.class, CPacketOpenInventoryVanilla::encode, CPacketOpenInventoryVanilla::decode, CPacketOpenInventoryVanilla::handle);
        registerMessage(CPacketSyncInventoryTerraria.class, CPacketSyncInventoryTerraria::encode, CPacketSyncInventoryTerraria::decode, CPacketSyncInventoryTerraria::handle);
        registerMessage(CPacketRequestInventoryTerraria.class, CPacketRequestInventoryTerraria::encode, CPacketRequestInventoryTerraria::decode, CPacketRequestInventoryTerraria::handle);
        registerMessage(CPacketEquipItemTerraria.class, CPacketEquipItemTerraria::encode, CPacketEquipItemTerraria::decode, CPacketEquipItemTerraria::handle);
        registerMessage(CPacketCloseInventoryTerraria.class, CPacketCloseInventoryTerraria::encode, CPacketCloseInventoryTerraria::decode, CPacketCloseInventoryTerraria::handle);
        registerMessage(CPacketThrowItemTerraria.class, CPacketThrowItemTerraria::encode, CPacketThrowItemTerraria::decode, CPacketThrowItemTerraria::handle);

        registerMessage(SPacketSendInventoryTerraria.class, SPacketSendInventoryTerraria::encode, SPacketSendInventoryTerraria::decode, SPacketSendInventoryTerraria::handle);
        registerMessage(SPacketSyncInventoryTerraria.class, SPacketSyncInventoryTerraria::encode, SPacketSyncInventoryTerraria::decode, SPacketSyncInventoryTerraria::handle);
        registerMessage(SPacketSpawnItemTerraria.class, SPacketSpawnItemTerraria::encode, SPacketSpawnItemTerraria::decode, SPacketSpawnItemTerraria::handle);

	}
	
	 private static <MSG> void registerMessage(Class<MSG> messageType, BiConsumer<MSG, PacketBuffer> encoder, Function<PacketBuffer, MSG> decoder, BiConsumer<MSG, Supplier<NetworkEvent.Context>> messageConsumer) {
		 INSTANCE.registerMessage(id++, messageType, encoder, decoder, messageConsumer);
	}
}
