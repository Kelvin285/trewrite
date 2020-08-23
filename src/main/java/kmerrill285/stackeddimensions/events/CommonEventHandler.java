package kmerrill285.stackeddimensions.events;

import kmerrill285.stackeddimensions.Util;
import kmerrill285.stackeddimensions.configuration.DimensionConfigs;
import kmerrill285.stackeddimensions.configuration.DimensionConfiguration;
import kmerrill285.stackeddimensions.networking.NetworkHandler;
import kmerrill285.stackeddimensions.networking.SPacketForceMovement;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.world.WorldEvent.Unload;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class CommonEventHandler {
	
	@OnlyIn(value=Dist.CLIENT)
	@SubscribeEvent
	public static void worldUnloadEventClient(Unload event) {
		Util.refreshDimensionRenderer = true;
	}
	
	@SubscribeEvent
	public static void handleLivingEvent(EntityEvent event) {
		Entity entity = event.getEntity();
		
		if (entity == null) return;
		World world = event.getEntity().world;
		DimensionConfiguration config = DimensionConfigs.getConfig(event.getEntity().dimension.getRegistryName());
		if (config != null) {
			
			if (!(event.getEntity() instanceof PlayerEntity)) {
				if (event.getEntity().posY > config.getMax() - 1) {
					if (config.getAbove() != null) {
						event.getEntity().teleportKeepLoaded(event.getEntity().posX, 1,event.getEntity().posZ);
						event.getEntity().posY = 1;
						event.getEntity().changeDimension(config.getAbove());
						
						return;
					}
				} else
				if (event.getEntity().posY < config.getMin() + 256) {
					
					int max = 255;
					DimensionConfiguration c2 = DimensionConfigs.getConfig(config.below);
					if (c2 != null) {
						max = c2.getMax() - 1;
					}
					
					if (config.getBelow() != null) {
						event.getEntity().teleportKeepLoaded(event.getEntity().posX, max,event.getEntity().posZ);
						event.getEntity().posY = max;
						event.getEntity().changeDimension(config.getBelow());
						return;
					}
				}
			}
			
		}
			
		
		
	}
	
	
	public static void moveEntity(Entity entity, double x, double y, double z, boolean onGround) {
		if (entity == null) return;
		if (entity instanceof PlayerEntity) {
			if (entity.world.isRemote) {
				entity.move(MoverType.PISTON, new Vec3d(x, y, z));
				entity.setMotion(new Vec3d(x != 0 ? 0 : entity.getMotion().x, y != 0 ? 0 : entity.getMotion().y, z != 0 ? 0 : entity.getMotion().z));
			} else {
				entity.move(MoverType.PISTON, new Vec3d(x*0.75, y*0.75, z*0.75));
				entity.setMotion(new Vec3d(x != 0 ? 0 : entity.getMotion().x, y != 0 ? 0 : entity.getMotion().y, z != 0 ? 0 : entity.getMotion().z));
				if (entity == null) return;
				try {
					NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity)entity), new SPacketForceMovement(x, y, z, onGround));
				}catch (Exception e) {
					
				}
			}
		} else {
			entity.move(MoverType.PISTON, new Vec3d(x, y, z));
			entity.setMotion(new Vec3d(x != 0 ? 0 : entity.getMotion().x, y != 0 ? 0 : entity.getMotion().y, z != 0 ? 0 : entity.getMotion().z));
		}
		if (onGround) {
			entity.onGround = true;
			entity.isAirBorne = false;
			entity.fallDistance = 0;
 		}
	}
}
