package kmerrill285.trewrite.world.dimension;

import kmerrill285.trewrite.blocks.BlocksT;
import kmerrill285.trewrite.core.network.NetworkHandler;
import kmerrill285.trewrite.core.network.server.SPacketForceMovement;
import kmerrill285.trewrite.core.network.server.SPacketRefreshDimensionRenderer;
import kmerrill285.trewrite.events.OverlayEvents;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.world.RegisterDimensionsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.network.PacketDistributor;

@EventBusSubscriber(modid="trewrite", bus=Bus.FORGE)
public class Dimensions {

	public static DimensionType THE_SKY;
	
	public static final ResourceLocation skyLocation = new ResourceLocation("trewrite", "sky");

	@SubscribeEvent
	public static void onRegisterDimensionsEvent(RegisterDimensionsEvent event) {
		if (DimensionType.byName(skyLocation) == null)
		{
			Dimensions.THE_SKY = DimensionManager.registerDimension(skyLocation, DimensionRegistry.skyDimension, null, true);
		}
	}
	
	// HOW TO GET TO YOUR DIMENSION

	public static void teleportPlayer(ServerPlayerEntity player, DimensionType destinationType, BlockPos destinationPos)
	{
		ServerWorld nextWorld = player.getServer().getWorld(destinationType);
		nextWorld.getChunk(destinationPos);	// make sure the chunk is loaded
		
		NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new SPacketRefreshDimensionRenderer());
		player.teleport(nextWorld, destinationPos.getX(), destinationPos.getY(), destinationPos.getZ(), player.rotationYaw, player.rotationPitch);

		
		player.setPositionAndUpdate(destinationPos.getX(), destinationPos.getY(), destinationPos.getZ());
		if (player.getPosition().getY() == 0) {
			player.setPositionAndUpdate(destinationPos.getX(), destinationPos.getY()+1, destinationPos.getZ());
		}
		BlockPos pos2 = new BlockPos(destinationPos.getX(), 0, destinationPos.getZ());
		if (nextWorld.getBlockState(pos2) != null) {
			if (nextWorld.getBlockState(pos2).getBlock() == Blocks.AIR ||
					nextWorld.getBlockState(pos2).getBlock() == Blocks.CAVE_AIR) {
				nextWorld.setBlockState(pos2, BlocksT.DIMENSION_BLOCK.getDefaultState());
			}
		} else {
			nextWorld.setBlockState(pos2, BlocksT.DIMENSION_BLOCK.getDefaultState());
		}
		NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new SPacketRefreshDimensionRenderer());
		
	}
}