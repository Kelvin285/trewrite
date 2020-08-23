package kmerrill285.stackeddimensions.networking;

import java.util.function.Supplier;

import kmerrill285.stackeddimensions.StackedDimensions;
import kmerrill285.stackeddimensions.Util;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;


public class SPacketSendEntity {
	
	public EntityType<?> entity;
	public double x, y, z;
	public CompoundNBT nbt;
	public String name;
	public float rotationPitch, rotationYaw, yawHead;
	public SPacketSendEntity(EntityType<?> entity, double x, double y, double z, float rotationPitch, float rotationYaw, float yawHead, CompoundNBT nbt, String name) {
		this.entity = entity;
		this.x = x;
		this.y = y;
		this.z = z;
		this.nbt = nbt;
		this.name = name;
		this.rotationPitch = rotationPitch;
		this.rotationYaw = rotationYaw;
		this.yawHead = yawHead;
		
	}
	
	public void encode(PacketBuffer buf) {
		buf.writeResourceLocation(entity.getRegistryName());
		buf.writeDouble(x);
		buf.writeDouble(y);
		buf.writeDouble(z);
		buf.writeCompoundTag(nbt);
		buf.writeString(name);
		buf.writeFloat(rotationPitch);
		buf.writeFloat(rotationYaw);
		buf.writeFloat(yawHead);
    }
	
	public SPacketSendEntity(PacketBuffer buf) {
		entity = Util.getEntity(buf.readResourceLocation());
		x = buf.readDouble();
		y = buf.readDouble();
		z = buf.readDouble();
		nbt = buf.readCompoundTag();
		name = buf.readString(100).trim();
		rotationPitch = buf.readFloat();
		rotationYaw = buf.readFloat();
		yawHead = buf.readFloat();
	}
	
	public void handle(Supplier<NetworkEvent.Context> ctx) {
		
		
		ctx.get().enqueueWork(() -> {
			if (StackedDimensions.renderWorld != null) {
				World world = StackedDimensions.renderWorld;
				Entity e = entity.create(world);
				e.read(nbt);				
				e.rotationPitch = rotationPitch;
				e.rotationYaw = rotationYaw;
				StackedDimensions.renderEntities.add(e);
				e.rotationPitch = rotationPitch;
				e.prevRotationPitch = rotationPitch;
				e.rotationYaw = rotationYaw;
				e.prevRotationYaw = rotationYaw;
				if (e instanceof LivingEntity) {
					LivingEntity living = (LivingEntity)e;
					living.renderYawOffset = rotationYaw;
					living.prevRenderYawOffset = rotationYaw;
					living.rotationYawHead = yawHead;
					living.prevRotationYawHead = yawHead;
				}
			}
        });
        ctx.get().setPacketHandled(true);
	}
}
