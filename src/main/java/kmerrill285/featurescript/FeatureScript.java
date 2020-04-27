package kmerrill285.featurescript;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;

import kmerrill285.featurescript.scripts.objects.Script;
import kmerrill285.modelloader.ModelLoader;

public class FeatureScript {
	public static HashMap<String, Script> scripts = new HashMap<String, Script>();
	
	public static HashMap<String, Script> items = new HashMap<String, Script>();
	public static HashMap<String, Script> blocks = new HashMap<String, Script>();
	public static HashMap<String, Script> entities = new HashMap<String, Script>();
	public static HashMap<String, Script> projectiles = new HashMap<String, Script>();
	
	public static void load() {
		try {
			String internal = ModelLoader.class.getClassLoader().getResource("features/").toURI().getPath();
			
			String external = "trewrite/features/";
			
			startLoad(internal);
			
			File file = new File(external);
			
			String[] dirs = file.list();
			if (dirs.length > 0)
			for (String str : dirs)
				startLoad(str);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		
	}
	
	private static void startLoad(String directory) {
		load(directory + "item/", FeatureScript.items);
		load(directory + "block/", FeatureScript.blocks);
		load(directory + "entity/", FeatureScript.entities);
		load(directory + "projectile/", FeatureScript.projectiles);
	}
	
	private static void load(String directory, HashMap<String, Script> scripts) {
		File dir = new File(directory);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		File[] files = dir.listFiles();
		if (files != null)
		for (File file : files) {
			Script script = ScriptLoader.loadScript(file.getAbsolutePath());
			script.run();
			script.executeFunction("init");
			scripts.put(script.name, script);
			System.out.println("Added " + script.name + " feature script");
		}
	}

	public static void addScript(Script script) {
		scripts.put(script.name, script);
	}
	
	public static Field getField(Object object, String field) {
		try {
			Field f = object.getClass().getDeclaredField(field);
			return f;
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
}
