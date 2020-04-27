package kmerrill285.featurescript.scripts.objects;

import java.util.ArrayList;
import java.util.HashMap;

public class Script {
	
	public String name;
	public CodeBlock object;
	public String returnType;
	
	public HashMap<String, Script> functions = new HashMap<String, Script>();
	public ArrayList<String> imports = new ArrayList<String>();
	public ArrayList<String> args = new ArrayList<String>();
	
	public Script(String name) {
		this.name = name;
	}
	
	public void run(Object...args) {
		for (int i = 0; i < args.length; i++) {
			if (i < this.args.size())
			object.setVariable(this.args.get(i), args[i]);
		}
		object.run();
	}
	
	public void executeFunction(String function, Object...args) {
		if (functions.containsKey(function)) {
			functions.get(function).run(args);
		}
	}
	
	public void setVariable(String variable, Object var) {
		object.variables.put(variable, var);
	}
	
	public Object getVariable(String name) {
		return object.variables.get(name);
	}

	public String getImportList() {
		String list = "load(\"nashorn:mozilla_compat.js\");\n";
		
		for (String str : imports) {
			try {
				if (Class.forName(str) != null) {
					if (str.startsWith("java") == false) {
						list += "importClass(Packages."+str+")\n";
					} else {
						list += "importClass("+str+")\n";
					}
				} else {
					if (str.startsWith("java") == false) {
						list += "importPackage(Packages."+str+")\n";
					} else {
						list += "importPackage("+str+")\n";
					}
				}
			
			}catch (Exception e) {
				try {
					if (str.startsWith("java") == false) {
						list += "importPackage(Packages."+str+")\n";
					} else {
						list += "importPackage("+str+")\n";
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		return list;
	}
}
