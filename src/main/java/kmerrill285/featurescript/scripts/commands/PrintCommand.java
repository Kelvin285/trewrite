package kmerrill285.featurescript.scripts.commands;

import java.util.HashMap;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import kmerrill285.featurescript.scripts.objects.CodeBlock;
import kmerrill285.featurescript.scripts.variables.StringVariable;
import kmerrill285.featurescript.scripts.variables.Variable;

public class PrintCommand extends Command {

	private String value;
	
	public PrintCommand(CodeBlock block, String value) {
		super(block);
		this.value = value.trim();
	}

	@Override
	public boolean run() {
		HashMap<String, Object> variables = block.getVariables(new HashMap<String, Object>());
		
		ScriptEngineManager scm = new ScriptEngineManager();
		ScriptEngine jsEngine = scm.getEngineByName("JavaScript");
		String value = this.value;
		String val2 = block.script.getImportList();
		try {
			jsEngine.eval(val2);
		} catch (ScriptException e1) {
			e1.printStackTrace();
		}
		
		for (String str : variables.keySet()) {
			jsEngine.put(str, variables.get(str));
		}
		
		try {
			System.out.println(jsEngine.eval(value));
		} catch (ScriptException e) {
			e.printStackTrace();
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
