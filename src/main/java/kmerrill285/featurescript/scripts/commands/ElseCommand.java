package kmerrill285.featurescript.scripts.commands;

import java.util.ArrayList;
import java.util.HashMap;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import kmerrill285.featurescript.scripts.objects.CodeBlock;
import kmerrill285.featurescript.scripts.variables.Variable;

public class ElseCommand extends Command {

	
	public ElseCommand(CodeBlock block) {
		super(block);
	}

	@Override
	public boolean run() {
		return true;
	}
	
}
