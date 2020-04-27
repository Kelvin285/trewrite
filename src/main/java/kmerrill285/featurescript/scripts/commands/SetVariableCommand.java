package kmerrill285.featurescript.scripts.commands;

import java.util.ArrayList;
import java.util.HashMap;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import kmerrill285.featurescript.scripts.objects.CodeBlock;
import kmerrill285.featurescript.scripts.variables.StringVariable;
import kmerrill285.featurescript.scripts.variables.Variable;

public class SetVariableCommand extends Command {

	private String name;
	private String value;
	
	public SetVariableCommand(CodeBlock block, String name, String value) {
		super(block);
		this.name = name.trim();
		this.value = value.trim();
	}

	@Override
	public boolean run() {
		HashMap<String, Object> variables = block.getVariables(new HashMap<String, Object>());
		
		ScriptEngineManager scm = new ScriptEngineManager();
		ScriptEngine jsEngine = scm.getEngineByName("JavaScript");
		String value = this.value;
		value = name + " = " + value;
		String val2 = block.script.getImportList();
		try {
			jsEngine.eval(val2);
		} catch (ScriptException e1) {
			e1.printStackTrace();
		}
		for (String str : variables.keySet()) {
			jsEngine.put(str, variables.get(str));
		}
		if (!variables.containsKey(name)) {
			jsEngine.put(name, null);
		}
		
		try {
			jsEngine.eval(value);
		} catch (ScriptException e) {
			e.printStackTrace();
		}
		if (!variables.containsKey(name)) {
			block.variables.put(name, jsEngine.get(name));
		}
		else {
			block.setVariable(name, jsEngine.get(name));
		}
		for (String str : variables.keySet()) {
			if (block.variables.containsKey(str)) {
				block.variables.put(str, jsEngine.get(str));
			} else {
				block.setVariable(str, jsEngine.get(str));
			}
		}
		return true;
	}
	
}
