package kmerrill285.featurescript.scripts.commands;

import kmerrill285.featurescript.scripts.objects.CodeBlock;
import kmerrill285.featurescript.scripts.variables.Variable;

public class RunCodeBlockCommand extends Command {

	private CodeBlock run;
	
	public RunCodeBlockCommand(CodeBlock block, CodeBlock run) {
		super(block);
		this.run = run;
	}

	@Override
	public boolean run() {
		run.run();
		return true;
	}
	
}
