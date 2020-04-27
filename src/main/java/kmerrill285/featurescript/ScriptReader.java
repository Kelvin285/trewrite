package kmerrill285.featurescript;

import java.util.HashMap;
import java.util.Stack;

import kmerrill285.featurescript.scripts.commands.RunCodeBlockCommand;
import kmerrill285.featurescript.scripts.objects.CodeBlock;
import kmerrill285.featurescript.scripts.objects.Script;
import kmerrill285.featurescript.scripts.variables.BooleanVariable;
import kmerrill285.featurescript.scripts.variables.DoubleIntegerVariable;
import kmerrill285.featurescript.scripts.variables.DoubleVariable;
import kmerrill285.featurescript.scripts.variables.IntegerVariable;
import kmerrill285.featurescript.scripts.variables.LongVariable;
import kmerrill285.featurescript.scripts.variables.SingleVariable;
import kmerrill285.featurescript.scripts.variables.StringVariable;
import kmerrill285.featurescript.scripts.variables.TripleIntegerVariable;
import kmerrill285.featurescript.scripts.variables.TripleVariable;
import kmerrill285.featurescript.scripts.variables.Variable;

public class ScriptReader {
	
	public static HashMap<String, Class<? extends Variable<Object>>> primitives;
	
	static {
		primitives = new HashMap<String, Class<? extends Variable<Object>>>();
		primitives.put("string", StringVariable.class);
		primitives.put("boolean", BooleanVariable.class);
		primitives.put("double", DoubleVariable.class);
		primitives.put("single", SingleVariable.class);
		primitives.put("triple", TripleVariable.class);
		primitives.put("Lsingle", LongVariable.class);
		primitives.put("Isingle", IntegerVariable.class);
		primitives.put("Idouble", DoubleIntegerVariable.class);
		primitives.put("Itriple", TripleIntegerVariable.class);
	}
	
	public static boolean DEBUG = false;
	
	public static Script parseScript(String script_data, String filename) {
		String[] lines = script_data.split("\n");
		Stack<CodeBlock> currentBlock = new Stack<CodeBlock>();
		Script script = null;
		Script function = null;
		for (int i = 0; i < lines.length; i++) {
			String line = removeIndents(lines[i]);
			if (DEBUG)
			System.out.println(line);
			if (!line.isEmpty()) {
				if (line.startsWith("import")) {
					String[] split = line.split(" ");
					if (split.length != 2) {
						throwError(filename, line, "Wrong usage of import! Example: \"import java.awt.Point\"");
					}
					if (script != null) {
						script.imports.add(split[1].trim());
					}
				}
				if (line.startsWith("script")) {
					if (script != null) {
						throwError(filename, line, "You can't create a second script inside of another script!");
					}
					String[] sc = line.split(":");
					if (sc.length < 2) {
						throwError(filename, line, "Scripts must have a name! (example: \"script:glowing_block\")");
					}
					if (sc.length > 2) {
						throwError(filename, line, "Too many arguments! (example: \"script:glowing_block\")");
					}
					String name = sc[1];
					name = name.trim();
					debugPrint("script:"+name);
					script = new Script(name);
				}
				else
				if (line.startsWith("function")) {
					if (currentBlock.size() == 1) {
						if (function != null) {
							throwError(filename, line, "You can't create a function inside of another function!");
						}
						String[] sc = line.split(" ");
						if (sc.length < 2) {
							throwError(filename, line, "Functions have to have names!  Example (function:void init) where \"init\" is the name");
						}
						
						String name = sc[1];
						function = new Script(name);
						if (sc.length > 2) {
							for (int a = 2; a < sc.length; a++) {
								String ar = sc[a];
								function.args.add(sc[a]);
							}
						}
					} else {
						throwError(filename, line, "You can't create a function outside of the main code block!");
					}
				}
				else
				if (line.startsWith("{")) {
					CodeBlock block = null;
					if (currentBlock.size() > 0) {
						block = new CodeBlock(i, currentBlock.lastElement(), script);
						debugPrint("CodeBlock parent: " + currentBlock.lastElement());
					} else {
						block = new CodeBlock(i, null, script);
					}
					if (script == null) {
						throwError(filename, line, "You can't start a new code block outside of a script!");
					}
					if (script.object != null && currentBlock.size() == 0) {
						throwError(filename, line, "You can't start a new code block outside of a script!");
					}
					
					debugPrint("Started new code block!");
					if (script.object == null) {
						script.object = block;
						debugPrint("Added code block to script!");
					}
					boolean addCodeBlockCommand = true;
					if (function != null) {
						if (function.object == null) {
							function.object = block;
							script.functions.put(function.name, function);
							debugPrint("Added new function ("+function.name+")!");
							addCodeBlockCommand = false;
							for (int a = 0; a < function.args.size(); a++) {
								function.setVariable(function.args.get(a), null);
							}
						}
					}
					if (currentBlock.size() > 0)
					if (addCodeBlockCommand) {
						currentBlock.lastElement().commands.add(new RunCodeBlockCommand(currentBlock.lastElement(), block));
						debugPrint("added Code Block command");
					}
					currentBlock.add(block);
				}
				else
				if (line.startsWith("}")) {
					if (currentBlock.size() == 0) {
						throwError(filename, line, "Missing opening bracket (\"{\") for code block");
					}
					
					currentBlock.pop();
					debugPrint("Finished code block!");
					if (currentBlock.size() == 0) {
						debugPrint("Added script " + script.name + " to library");
						FeatureScript.addScript(script);
						return script;
					}
					
					if (currentBlock.size() == 1) {
						if (function != null) {
							function = null;
							debugPrint("Finished function");
						}
					}
					
				}
				if (currentBlock.size() > 0) {
					currentBlock.lastElement().parseLine(line, filename);
				}
			}			
		}
		if (currentBlock.size() > 0) {
			throwError(filename, "end of file", "missing closing bracket (\"}\") for code block!");
		}
		return script;
	}
	
	public static String removeIndents(String str) {
		char[] c = str.toCharArray();
		String line = "";
		boolean start = false;
		boolean string = false;
		for (char ch : c) {
			if (ch != ' ') {
				start = true;
			}
			if (ch == '"') string = !string;
			if (ch == '#' && string == false) break;
			if (start) {
				line += ch;
			}
		}
		return line.replace("\t", "");
	}
	
	public static void debugPrint(String line) {
		if (DEBUG) {
			System.err.println(line);
		}
	}
	
	public static void throwError(String name, String line, String error) {
		System.err.println(name + " line ("+line+") " + error);
		System.exit(1);
	}
}
