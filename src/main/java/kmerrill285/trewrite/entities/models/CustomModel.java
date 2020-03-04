package kmerrill285.trewrite.entities.models;

import java.util.HashMap;

import com.mojang.blaze3d.platform.GlStateManager;

import kmerrill285.modelloader.Model;
import kmerrill285.modelloader.ModelPart;
import kmerrill285.modelloader.ModelTransformation;
import kmerrill285.modelloader.Vertex;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.ModelBox;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

public class CustomModel extends EntityModel<Entity> {
	public final HashMap<String, RendererModel> parts;
	public final HashMap<String, Vec3d> extraRotation;

	public Model model;
	
	public CustomModel(Model model, int tex_width, int tex_height) {
		textureWidth = tex_width;
		textureHeight = tex_height;
		this.model = model;
		parts = new HashMap<String, RendererModel>();
		extraRotation = new HashMap<String, Vec3d>();
		for (String str : model.parts.keySet()) {
			RendererModel renderPart = new RendererModel(this);
			ModelPart part = model.parts.get(str);
			ModelTransformation transform = part.transformation;
			renderPart.setRotationPoint(-part.transformation.size_x * 0.5f, -part.transformation.size_y * 0.5f, -part.transformation.size_z * 0.5f);
			ModelBox box = new ModelBox(renderPart, (int)transform.U, (int)transform.V, 0, 0, 0, (int)transform.size_x, (int)transform.size_y, (int)transform.size_z, 0.0f, false);
			renderPart.cubeList.add(box);
			parts.put(str, renderPart);
		}
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		
		for (String str : parts.keySet()) {
			boolean canRender = true;
			canRender = model.parts.get(str).parent == null;
			if (canRender) {
				GlStateManager.pushMatrix();
				GlStateManager.rotatef(180, 0, 1, 0);
				GlStateManager.translatef(0, 1.5f, 0);
				renderModelPart(str, f5);
				GlStateManager.popMatrix();
			}
			
		}
	}
	
	public void renderModelPart(String part_name, float f5) {
		ModelPart part = model.parts.get(part_name);
		float mul = 1.0f / 16.0f;
		GlStateManager.pushMatrix();
		
		Vertex pos = new Vertex(part.transformation.x + part.transformation.offsX, part.transformation.y + part.transformation.offsY, part.transformation.z + part.transformation.offsZ);
		
		GlStateManager.translatef((float)pos.x * mul, -(float)pos.y * mul, (float)pos.z * mul);
		
		if (extraRotation.get(part.name) != null) {
			GlStateManager.rotated(extraRotation.get(part.name).x, 1, 0, 0);
			GlStateManager.rotated(extraRotation.get(part.name).y, 0, 1, 0);
			GlStateManager.rotated(extraRotation.get(part.name).z, 0, 0, 1);
		}
		
		GlStateManager.rotatef(-part.transformation.rotX, 1, 0, 0);
		GlStateManager.rotatef(-part.transformation.rotY, 0, 1, 0);
		GlStateManager.rotatef(-part.transformation.rotZ, 0, 0, 1);
		
		if (model.parts.get(part_name).children != null) {
			for (ModelPart modelpart : model.parts.get(part_name).children) {
				renderModelPart(modelpart.name, f5);
			}
		}
		GlStateManager.pushMatrix();
		
		GlStateManager.scalef(1, 1, -1);
		
		if (part.vertexCoords.size() > 0) {
			Vertex trueScale = new Vertex(part.transformation.size_x / part.vertexCoords.get(5).x,
					part.transformation.size_y / part.vertexCoords.get(5).y,
					part.transformation.size_z / part.vertexCoords.get(5).z);
			float m = 0.55f;
			GlStateManager.scalef(trueScale.x * m, trueScale.y * m, trueScale.z * m);
			
		}
		
		parts.get(part_name).render(f5);
		GlStateManager.popMatrix();
		
		GlStateManager.popMatrix();
	}
	
	public void setRotationAngle(RendererModel modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
