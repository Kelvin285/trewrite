package kmerrill285.trewrite.commands;

import java.util.Collection;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.suggestion.SuggestionProvider;

import kmerrill285.trewrite.core.items.ItemStackT;
import kmerrill285.trewrite.core.network.NetworkHandler;
import kmerrill285.trewrite.core.network.client.CPacketThrowItemTerraria;
import kmerrill285.trewrite.items.ItemsT;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.ResourceLocationArgument;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class GiveTerraria {

//	private static final SuggestionProvider<CommandSource> ITEM_ID_SUGGESTIONS = (context, builder) ->
//
//	ISuggestionProvider.func_212476_a(ForgeRegistries.ITEMS.getKeys().stream(), builder);
//
//	private GiveTerraria() {
//
//	}
//
//	public static void register(CommandDispatcher<CommandSource> dispatcher) {
//		dispatcher.register(Commands.literal("givet").requires(source -> source.hasPermissionLevel(2))
//
//						.then(Commands.argument("itemID", ResourceLocationArgument.resourceLocation())
//
//								.suggests(ITEM_ID_SUGGESTIONS)
//
//								.executes(context -> giveItem(
//
//										context.getSource(),
//
//										ResourceLocationArgument.getResourceLocation(context, "itemID"),
//
//										EntityArgument.getPlayers(context, "targets"),
//
//										1
//
//								))
//
//								.then(Commands.argument("count", IntegerArgumentType.integer())
//
//										.executes(context -> giveItem(
//
//												context.getSource(),
//
//												ResourceLocationArgument.getResourceLocation(context, "itemID"),
//
//												EntityArgument.getPlayers(context, "targets"),
//
//												IntegerArgumentType.getInteger(context, "count")
//
//										)
//
//								)))
//
//		);
//	}
//
//	private static int giveItem(CommandSource source, ResourceLocation loc, Collection<EntityPlayerMP> players, int i) {
//		String string = loc.getPath();
//		NetworkHandler.INSTANCE.sendToServer(new CPacketThrowItemTerraria(0, -1, 0, new ItemStackT(ItemsT.getItemFromString(string), i)));
//		return 1;
//	}
}
