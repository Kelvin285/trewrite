package kmerrill285.modelloader;

import java.util.ArrayList;

public class ModelPart {
	
	public ModelTransformation transformation;
	public String name;
	
	public ArrayList<ModelPart> children = new ArrayList<ModelPart>();
	public ModelPart parent;
	public ArrayList<Vertex> vertexCoords = new ArrayList<Vertex>();
	
	public ModelPart(String name) {
		this.name = name;
	}
}
