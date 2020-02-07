package kmerrill285.trewrite.core.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

public class CommandsT {
	public static void register(CommandDispatcher<CommandSource> cmd) {
		LiteralCommandNode<CommandSource> command = cmd.register(WormholeCommand.register(cmd));
		cmd.register(Commands.literal("wh").redirect(command));
	}
}
