package kmerrill285.featurescript.scripts.commands;

import kmerrill285.featurescript.scripts.objects.CodeBlock;

public abstract class Command {
	protected CodeBlock block;
	public Command(CodeBlock block) {
		this.block = block;
	}
	
	public abstract boolean run();
}
