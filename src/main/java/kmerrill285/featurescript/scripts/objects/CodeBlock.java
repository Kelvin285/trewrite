package kmerrill285.featurescript.scripts.objects;

import java.util.ArrayList;
import java.util.HashMap;

import kmerrill285.featurescript.ScriptReader;
import kmerrill285.featurescript.scripts.commands.Command;
import kmerrill285.featurescript.scripts.commands.ElseCommand;
import kmerrill285.featurescript.scripts.commands.ExecuteCommand;
import kmerrill285.featurescript.scripts.commands.IfCommand;
import kmerrill285.featurescript.scripts.commands.PrintCommand;
import kmerrill285.featurescript.scripts.commands.ReturnCommand;
import kmerrill285.featurescript.scripts.commands.RunCodeBlockCommand;
import kmerrill285.featurescript.scripts.commands.RunFunctionCommand;
import kmerrill285.featurescript.scripts.commands.SetVariableCommand;
import kmerrill285.featurescript.scripts.commands.WhileCommand;

public class CodeBlock {
	public HashMap<String, Object> variables;
	public String[] lines;
	public int line;
	
	public CodeBlock parent;
	public ArrayList<Command> commands;
	
	public Script script;
	
	public CodeBlock(int line, CodeBlock parent, Script script) {
		variables = new HashMap<String, Object>();
		this.line = line;
		commands = new ArrayList<Command>();
		this.parent = parent;
		this.script = script;
	}
	
	private ArrayList<String> varnames = new ArrayList<String>();

	public void parseLine(String line, String filename) {
		
		if (line.startsWith("setvar")) {
			String[] data = line.split(" ");
			if (data.length < 2) {
				System.err.println("You must give a variable a name! Example: \"var i:0\" or \"var i\"");
			} else {
				String variable = data[1];
				if (variable.split(":").length > 0) {
					String sub = line.substring(("setvar "+variable.split(":")[0]+":").length());
					commands.add(new SetVariableCommand(this, variable.split(":")[0], sub));
					ScriptReader.debugPrint("Added SetVariable command: set " + variable.split(":")[0] + " to " + sub);
				} else {
					String sub = line.substring(("setvar "+variable).length());
					commands.add(new SetVariableCommand(this, variable, sub));
					ScriptReader.debugPrint("Added SetVariable command: set " + variable + " to " + sub);
				}
			}
		}
		else
		if (line.startsWith("run")) {
			if (line.split(" ").length >= 2) {
				String function = line.split(" ")[1];
				String[] split = line.split(":");
				
				commands.add(new RunFunctionCommand(this, function, split));
			} else {
				System.err.println("You need to call to a function to run it!  Example: \"run init\"");
				System.exit(1);
			}
		}
		else
		if (line.startsWith("print")) {
			String s = line.substring("print".length());
			commands.add(new PrintCommand(this, s));
			ScriptReader.debugPrint("Added Print command: " + s);
		}
		else
		if (line.startsWith("return")) {
			commands.add(new ReturnCommand(this, ""));
			ScriptReader.debugPrint("Added Return command: " + "");
		}
		else
		if (line.startsWith("if")) {
			String s = line.substring("if".length());
			commands.add(new IfCommand(this, s));
			ScriptReader.debugPrint("Added If command: " + s);
		}
		else
		if (line.startsWith("while")) {
			String s = line.substring("while".length());
			commands.add(new WhileCommand(this, s));
			ScriptReader.debugPrint("Added While command: " + s);
		}
		else
		if (line.startsWith("else")) {
			commands.add(new ElseCommand(this));
			ScriptReader.debugPrint("Added Else command");
		}
		if (line.startsWith("import"))
			;
		else
		if (line.startsWith("function"))
			;
		else
			if (line.length() > 1) {
				if (!line.startsWith("var"))
					if (!line.startsWith("while"))
						if (!line.startsWith("print"))
							if (!line.startsWith("if"))
								if (!line.startsWith("return"))
									if (!line.startsWith("run"))
										if (!line.startsWith("else"))
					if (!line.startsWith("setvar")) {
						commands.add(new ExecuteCommand(this, line));
					}
			}
		
		
//		if (line.startsWith("execute")) {
//			if (line.split(" ").length >= 2) {
//				commands.add(new ExecuteCommand(this, line.substring("execute ".length())));
//			} else {
//				System.err.println("You need to have code to execute if you use \"execute\"!  Example: \"execute array.put(5)\"");
//				System.exit(1);
//			}
//		}
	}

	public ArrayList<String> getVarnames(ArrayList<String> varnames) {
		for (int i = 0; i < this.varnames.size(); i++) {
			if (!varnames.contains(this.varnames.get(i)))
			varnames.add(this.varnames.get(i));
		}
		if (parent != null) {
			return parent.getVarnames(varnames);
		}
		return varnames;
	}
	
	public HashMap<String, Object> getVariables(HashMap<String, Object> vars) {
		for (String str : variables.keySet()) {
			if (!vars.containsKey(str)) {
				vars.put(str, variables.get(str));
			}
		}
		if (parent != null) {
			return parent.getVariables(vars);
		}
		return vars;
	}
	
	public void setVariable(String name, Object object) {
		for (String str : variables.keySet()) {
			if (str.equals(name)) {
				variables.put(str, object);
				return;
			}
		}
		if (parent != null) {
			parent.setVariable(name, object);
		}
	}
	
	public Object run() {
		
		for (int i = 0; i < commands.size(); i++) {
			if (commands.get(i) instanceof IfCommand) {
				if (commands.get(i).run()) {
					i++;
					if (i < commands.size()) {
						if (commands.get(i) instanceof RunCodeBlockCommand) {
							commands.get(i).run();
							i++;
							if (i < commands.size())
							if (commands.get(i)	instanceof ElseCommand) {
								i++;
								i++;
							}	
						} else {
							System.err.println("If statements must always be followed by a code block! (\"{\" and \"}\")");
							System.exit(1);
						}
					}
				} else {
					i++;
					if (commands.get(i) instanceof ElseCommand) {
						i++;
					}
				}
				
			} 
			else if (commands.get(i) instanceof WhileCommand) {
				while (commands.get(i).run()) {
					i++;
					if (i < commands.size()) {
						if (commands.get(i) instanceof RunCodeBlockCommand) {
							commands.get(i).run();
						} else {
							System.err.println("While statements must always be followed by a code block! (\"{\" and \"}\")");
							System.exit(1);
						}
					} else {
						System.err.println("While statements must always be followed by a code block! (\"{\" and \"}\")");
						System.exit(1);
					}
					i--;
				}
				i++;
			}
			else {
				commands.get(i).run();
			}
		}
		return null;
	}

	

	
}
