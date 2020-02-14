package kmerrill285.trewrite.core.commands;

import java.util.Collection;
import java.util.Random;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import kmerrill285.trewrite.core.inventory.InventorySlot;
import kmerrill285.trewrite.core.inventory.InventoryTerraria;
import kmerrill285.trewrite.core.network.NetworkHandler;
import kmerrill285.trewrite.core.network.server.SPacketSyncInventoryTerraria;
import kmerrill285.trewrite.events.WorldEvents;
import kmerrill285.trewrite.items.ItemsT;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.PacketDistributor;
public class WormholeCommand implements Command<CommandSource>{
	
	private static final WormholeCommand CMD = new WormholeCommand();
	
	public static LiteralArgumentBuilder<CommandSource> register(CommandDispatcher<CommandSource> cmd) {
		return Commands.literal("wh").then(
				Commands.argument("destination", EntityArgument.entity()).executes(new WormholeCommand()));
	}

	private static int teleportToEntity(CommandSource source, Collection<? extends Entity> entities, Entity entity) {
		//	public SPacketSyncInventoryTerraria(int entityId, int inventoryArea, int slotId, ItemStackT stack) {
		if (entity instanceof PlayerEntity == false) return 1;
		InventoryTerraria inventory = WorldEvents.getOrLoadInventory((PlayerEntity) entity);
		if (inventory != null) {
			InventorySlot slot = null;
			for (int i = 0; i < inventory.main.length; i++) {
				if (inventory.main[i].stack != null) {
					if (inventory.main[i].stack.item == ItemsT.WORMHOLE_POTION) {
						slot = inventory.main[i];
						break;
					}
				}
			}
			if (slot == null)
			for (int i = 0; i < inventory.hotbar.length; i++) {
				if (inventory.hotbar[i].stack != null) {
					if (inventory.hotbar[i].stack.item == ItemsT.WORMHOLE_POTION) {
						slot = inventory.hotbar[i];
						break;
					}
				}
			}
			if (slot != null) {
				slot.decrementStack(1);
				NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity)entity), new SPacketSyncInventoryTerraria(0, slot.area, slot.id, slot.stack));
				try {
					source.asPlayer().teleport((ServerWorld)entity.getEntityWorld(), entity.posX, entity.posY, entity.posZ, entity.rotationYaw + new Random().nextInt(360), entity.rotationPitch);
				} catch (CommandSyntaxException e) {
					e.printStackTrace();
				}
			}
		}
		
		return 1;
	}

	@Override
	public int run(CommandContext<CommandSource> context) throws CommandSyntaxException {

        return teleportToEntity(context.getSource(), null, EntityArgument.getEntity(context, "destination"));

	}
}
