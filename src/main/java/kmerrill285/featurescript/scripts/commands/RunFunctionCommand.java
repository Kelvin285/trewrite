package kmerrill285.featurescript.scripts.commands;

import java.util.HashMap;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import kmerrill285.featurescript.scripts.objects.CodeBlock;
import kmerrill285.featurescript.scripts.variables.Variable;

public class RunFunctionCommand extends Command {

	private String run;
	private String[] args;
	public RunFunctionCommand(CodeBlock block, String run, String...args) {
		super(block);
		this.run = run;
		this.args = args;
	}

	@Override
	public boolean run() {
		HashMap<String, Object> variables = block.getVariables(new HashMap<String, Object>());
		
		ScriptEngineManager scm = new ScriptEngineManager();
		ScriptEngine jsEngine = scm.getEngineByName("JavaScript");
		Object[] objs = new Object[0];
		if (args.length > 0) {
			objs = new Object[args.length - 1];
			String val2 = block.script.getImportList();
			try {
				jsEngine.eval(val2);
			} catch (ScriptException e1) {
				e1.printStackTrace();
			}
			for (String str : variables.keySet()) {
				jsEngine.put(str, variables.get(str));
			}
			
			for (int i = 1; i < args.length; i++) {
				try {
					objs[i - 1] = jsEngine.eval(args[i]);
				} catch (ScriptException e) {
					e.printStackTrace();
				}
			}
			for (String str : variables.keySet()) {
				if (block.variables.containsKey(str)) {
					block.variables.put(str, jsEngine.get(str));
				} else {
					block.setVariable(str, jsEngine.get(str));
				}
			}
		}
		block.script.executeFunction(run, objs);
		return true;
	}
	
}
