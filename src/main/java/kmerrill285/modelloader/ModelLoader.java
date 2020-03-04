package kmerrill285.modelloader;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class ModelLoader {
	public static boolean DEBUG_LOADING = false;
	public static Model loadModelFromFile(String modelname) {
		
		File file = null;
		try {
			file = new File(ModelLoader.class.getClassLoader().getResource("assets/trewrite/models/entity/"+modelname+".json").toURI().getPath());
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}
		String str = "";
		try {
			Scanner scanner = new Scanner(file);
			while (scanner.hasNext()) {
				str += scanner.nextLine()+"\n";
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
		}
		return loadModel(str);
	}
	
	public static Model loadModel(String model_data) {
		String[] lines = model_data.split("\n");
		String title = "";
		
		ModelPart part = null;
		ModelTransformation transform = null;
		
		Model model = new Model();
		
		ArrayList<Vertex> vertexCoords = new ArrayList<Vertex>();
		Stack<String> currentParent = new Stack<String>();
		for (String line : lines) {
			line = line.replace("\n", "");
			line = line.replace(" ", "");
			line = line.replace("\"", "");
			line = line.trim();
			String first = line.split(":")[0];
			if (DEBUG_LOADING)
			System.out.println(line);

			if (first.contains("title")) {
				title = line.split(":")[1].replace(",","");
			}
			if(first.contains("children")) {
				currentParent.add(part.name);
				if (line.contains("[]")) {
					currentParent.pop();
				}
			}
			if (first.startsWith("[")) {
				String coords = first.replace("[","").replace("],","").replace("]","");
				Vertex vert = new Vertex(0, 0, 0);
				String[] data = coords.split(",");
				vert.x = Float.parseFloat(data[0]);
				vert.y = Float.parseFloat(data[1]);
				vert.z = Float.parseFloat(data[2]);
				vertexCoords.add(vert);
			}
			if (first.contains("],")) {
				if (part != null) {
					part.vertexCoords.addAll(vertexCoords);
				}
				vertexCoords.clear();
			}else {
				if (first.contains("]") && !first.contains(",")) {
					if (currentParent.size() > 0) {
						String last = currentParent.pop();
						if (DEBUG_LOADING)
						System.out.println("LAST PARENT: " + last);
					}
				}
			}
			if (first.contains("name")) {
				part = new ModelPart(line.split(":")[1].replace(",",""));
				transform = new ModelTransformation();
				part.transformation = transform;
				model.parts.put(part.name, part);
				if (currentParent.size() > 0) {
					String current = currentParent.lastElement();
					if (!(current.contains(part.name))) {
						part.parent = model.parts.get(current);
						model.parts.get(current).children.add(part);
					}
				}
			}
			if (first.contains("position")) {
				String[] pos = line.split(":")[1].replace("[","").replace("],","").split(",");
				float x = Float.parseFloat(pos[0]);
				float y = Float.parseFloat(pos[1]);
				float z = Float.parseFloat(pos[2]);
				transform.x = x;
				transform.y = y;
				transform.z = z;
			}
			if (first.contains("offsetFromPivot")) {
				String[] pos = line.split(":")[1].replace("[","").replace("],","").split(",");
				float x = Float.parseFloat(pos[0]);
				float y = Float.parseFloat(pos[1]);
				float z = Float.parseFloat(pos[2]);
				transform.offsX = x;
				transform.offsY = y;
				transform.offsZ = z;
			}
			if (first.contains("size")) {
				String[] pos = line.split(":")[1].replace("[","").replace("],","").split(",");
				float x = Float.parseFloat(pos[0]);
				float y = Float.parseFloat(pos[1]);
				float z = Float.parseFloat(pos[2]);
				transform.size_x = x;
				transform.size_y = y;
				transform.size_z = z;
			}
			if (first.contains("rotation")) {
				String[] pos = line.split(":")[1].replace("[","").replace("],","").split(",");
				float x = Float.parseFloat(pos[0]);
				float y = Float.parseFloat(pos[1]);
				float z = Float.parseFloat(pos[2]);
				transform.rotX = x;
				transform.rotY = y;
				transform.rotZ = z;
			}
			if (first.contains("texOffset")) {
				String[] pos = line.split(":")[1].replace("[","").replace("],","").split(",");
				float x = Float.parseFloat(pos[0]);
				float y = Float.parseFloat(pos[1]);
				transform.U = x;
				transform.V = y;
			}
			
			
		}
		if (DEBUG_LOADING)
		System.out.println("END OF FILE");
		System.out.println("loaded model: " + title);
		if (DEBUG_LOADING)
		for (String str : model.parts.keySet()) {
			ModelPart p = model.parts.get(str);
			System.out.println(p.name + ", " + p.children.size());
		}
		return model;
	}
}
