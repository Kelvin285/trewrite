package kmerrill285.featurescript;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import kmerrill285.featurescript.scripts.objects.Script;

public class ScriptLoader {
	public static Script loadScript(String script) {
		Script sc = null;
		File file = new File(script);		
		if (file.exists()) {
			Scanner scanner = null;
			try {
				scanner = new Scanner(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			String data = "";
			while (scanner.hasNext()) {
				data += scanner.nextLine()+"\n";
			}
			scanner.close();
			data = data.replace("{","\n{");
			data = data.replace("}","\n}");
			sc = ScriptReader.parseScript(data, file.getName());
		} else {
			System.err.println("Could not find file " + file);
			System.exit(1);
		}
		return sc;
	}
	
	public static Script loadScript(File file) {
		Script sc = null;
		if (file.exists()) {
			Scanner scanner = null;
			try {
				scanner = new Scanner(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			String data = "";
			while (scanner.hasNext()) {
				data += scanner.nextLine()+"\n";
			}
			scanner.close();
			data = data.replace("{","\n{");
			data = data.replace("}","\n}");
			sc = ScriptReader.parseScript(data, file.getName());
		} else {
			System.err.println("Could not find file " + file);
			System.exit(1);
		}
		return sc;
	}
}
