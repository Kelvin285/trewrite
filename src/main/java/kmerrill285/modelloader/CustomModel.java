package kmerrill285.modelloader;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;

import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;

import org.lwjgl.BufferUtils;

import com.mojang.blaze3d.platform.GlStateManager;

import kmerrill285.modelloader.animation.Animation;
import kmerrill285.modelloader.animation.AnimationController;
import kmerrill285.modelloader.animation.AnimationFrame;
import kmerrill285.modelloader.animation.AnimationFrameData;
import kmerrill285.trewrite.util.Util;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.ModelBox;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

public class CustomModel extends EntityModel<Entity> {
	public final HashMap<String, RendererModel> parts;
	public final HashMap<String, Vec3d> extraRotation;
	public final HashMap<String, ArrayList<BlankModel>> extraRenderers;
	public final ArrayList<String> blacklist;

	public Model model;
	public AnimationController animationController;
	
	public CustomModel(Model model, int tex_width, int tex_height) {
		textureWidth = tex_width;
		textureHeight = tex_height;
		this.model = model;
		this.animationController = new AnimationController();
		parts = new HashMap<String, RendererModel>();
		extraRotation = new HashMap<String, Vec3d>();
		extraRenderers = new HashMap<String, ArrayList<BlankModel>>();
		blacklist = new ArrayList<String>();
		for (String str : model.parts.keySet()) {
			RendererModel renderPart = new RendererModel(this);
			ModelPart part = model.parts.get(str);
			ModelTransformation transform = part.transformation;
			renderPart.setRotationPoint(-part.transformation.size_x * 0.5f, -part.transformation.size_y * 0.5f, -part.transformation.size_z * 0.5f);
			ModelBox box = new ModelBox(renderPart, (int)transform.U, (int)transform.V, 0, 0, 0, (int)transform.size_x, (int)transform.size_y, (int)transform.size_z, 0.0f, false);
			renderPart.cubeList.add(box);
			parts.put(str, renderPart);
			extraRenderers.put(str, new ArrayList<BlankModel>());
		}
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		GlStateManager.pushMatrix();

		GlStateManager.scalef(1, -1, -1);
		GlStateManager.translatef(0, -1.5f, 0);
		for (String str : parts.keySet()) {
			boolean canRender = true;
			canRender = model.parts.get(str).parent == null;
			if (canRender) {
				
				renderModelPart(str, entity, f5);
			}
			
		}
		GlStateManager.popMatrix();
	}
	
	public Vertex getRotationForFrame(ModelPart part, Animation currentAnimation) 
	{
		Vertex animRot = null;
		Vertex nextRot = null;
		Vertex newAnimRot = null;
		
		AnimationFrame currentRotationFrame = null;
		AnimationFrame nextRotationFrame = null;
		
		if (currentAnimation != null) {
			//get current position frame data
			int iterations = 0;
			for (int i = (int)currentAnimation.currentFrame; i >= -currentAnimation.duration; i--) {
				if (i < 0) {
					i = (int)currentAnimation.duration + 1;
					iterations++;
					if (iterations > 1) break;
				}
				AnimationFrame frame = currentAnimation.getFrameFor(i);
				AnimationFrameData frameData = frame.frameData.get(part.name);
				if (frameData != null) {
					
					if (animRot == null) {
						if (frameData.rotation != null) {
							animRot = new Vertex(frameData.rotation.x + 0, frameData.rotation.y + 0, frameData.rotation.z + 0);
							currentRotationFrame = frame;
						}
					}
				}
			}
			
			iterations = 0;
			for (int i = (int)currentAnimation.currentFrame; i <= currentAnimation.duration * 2; i++) {
				if (i > currentAnimation.duration) {
					i = -1;
					iterations++;
					if (iterations > 1) break;
				}
				AnimationFrame frame = currentAnimation.getNextFrameAfter(i);
				AnimationFrameData frameData = frame.frameData.get(part.name);
				
				
				if (frameData != null) {
					
					if (nextRot == null) {
						if (frameData.rotation != null) {
							nextRot = new Vertex(frameData.rotation.x + 0, frameData.rotation.y + 0, frameData.rotation.z + 0);
							nextRotationFrame = frame;
						}
					}
				}
			}
		}
		
		
		
		double rotx = 0, roty = 0, rotz = 0;
		if (animRot != null) {
			if (nextRot != null) {
				float time = animationController.currentAnimation.currentFrame;
				float startTime = currentRotationFrame.time;
				float endTime = nextRotationFrame.time;
				if (endTime < startTime) endTime += animationController.currentAnimation.duration;
				float frameLerp = animationController.currentAnimation.getFrameLerp(endTime - startTime, endTime - time);
				
				if (endTime - startTime > 0) {
					animRot.x = (float)Util.lerp(animRot.x, nextRot.x, frameLerp);
					animRot.y = (float)Util.lerp(animRot.y, nextRot.y, frameLerp);
					animRot.z = (float)Util.lerp(animRot.z, nextRot.z, frameLerp);
					
					
				}
			}
			
			roty += animRot.y;
			rotx += animRot.x;
			rotz += animRot.z;
			
//			GlStateManager.rotated(-animRot.y, 0, 1, 0);
//			GlStateManager.rotated(animRot.x, 1, 0, 0); 
//			GlStateManager.rotated(animRot.z, 0, 0, 1);
		}
		return new Vertex((float)rotx, (float)roty, (float)rotz);
	}
	
	public Vertex getPositionForFrame(ModelPart part, Animation currentAnimation) {
		Vertex animPos = null;
		
		Vertex nextPos = null;
		
		
		AnimationFrame currentPositionFrame = null;
		AnimationFrame nextPositionFrame = null;
		
		Vertex newAnimPos = null;
		
		if (currentAnimation != null) {
			//get current position frame data
			int iterations = 0;
			for (int i = (int)currentAnimation.currentFrame; i >= -currentAnimation.duration; i--) {
				if (i < 0) {
					i = (int)currentAnimation.duration + 1;
					iterations++;
					if (iterations > 1) break;
				}
				AnimationFrame frame = currentAnimation.getFrameFor(i);
				AnimationFrameData frameData = frame.frameData.get(part.name);
				if (frameData != null) {
					if (animPos == null) {
						if (frameData.position != null) {
							animPos = new Vertex(frameData.position.x + 0, frameData.position.y + 0, frameData.position.z + 0);
							currentPositionFrame = frame;
						}
					}
				}
			}
			
			iterations = 0;
			for (int i = (int)currentAnimation.currentFrame; i <= currentAnimation.duration * 2; i++) {
				if (i > currentAnimation.duration) {
					i = -1;
					iterations++;
					if (iterations > 1) break;
				}
				AnimationFrame frame = currentAnimation.getNextFrameAfter(i);
				AnimationFrameData frameData = frame.frameData.get(part.name);
				
				
				if (frameData != null) {
					if (nextPos == null) {
						if (frameData.position != null) {
							nextPos = new Vertex(frameData.position.x + 0, frameData.position.y + 0, frameData.position.z + 0);
							nextPositionFrame = frame;
						}
					}
				}
			}
		}
		
		
		Vertex pos = new Vertex(part.transformation.x + part.transformation.offsX, part.transformation.y + part.transformation.offsY, part.transformation.z + part.transformation.offsZ);
		
		
		
		if (animPos != null) {
			if (nextPos != null) {
				float time = animationController.currentAnimation.currentFrame;
				float startTime = currentPositionFrame.time;
				float endTime = nextPositionFrame.time;
				if (endTime < startTime) endTime += animationController.currentAnimation.duration;
				float frameLerp = animationController.currentAnimation.getFrameLerp(endTime - startTime, endTime - time);
				if (endTime - startTime > 0) {
					animPos.x = (float)Util.lerp(animPos.x, nextPos.x, frameLerp);
					animPos.y = (float)Util.lerp(animPos.y, nextPos.y, frameLerp);
					animPos.z = (float)Util.lerp(animPos.z, nextPos.z, frameLerp);
				}
			}
			
			pos.x += animPos.x;
			pos.y += animPos.y;
			pos.z += animPos.z;
		}
		return pos;
	}
	
	public float[] rotateAround(float[] pos, float[] origin, float angle) {
		double x1 = pos[0] - origin[0];
		double y1 = pos[1] - origin[1];

		//APPLY ROTATION
		double nx = x1 * Math.cos(Math.toRadians(angle)) + y1 * Math.sin(Math.toRadians(angle));
		double ny = x1 * Math.sin(Math.toRadians(angle)) + y1 * Math.cos(Math.toRadians(angle));

		//TRANSLATE BACK
		return new float[] {(float)nx + origin[0], (float)ny + origin[1]};
	}
	
	public void renderModelPart(String part_name, Entity entity, float f5) {
		if (blacklist.contains(part_name)) return;
		
		
		
		ModelPart part = model.parts.get(part_name);
		float mul = 1.0f / 16.0f;
		GlStateManager.pushMatrix();
		
		
		Animation currentAnimation = this.animationController.currentAnimation;
		
		
		Vertex pos = getPositionForFrame(part, currentAnimation);
		float posx = pos.x;
		float posy = pos.y;
		float posz = pos.z;
		
		float roty = 0;
		float rotx = 0;
		float rotz = 0;
		
		Vertex rotation = getRotationForFrame(part, currentAnimation);
		
		if (extraRotation.get(part_name) != null) {
			rotx -= extraRotation.get(part_name).x;
			roty += extraRotation.get(part_name).y;
			rotz -= extraRotation.get(part_name).z;
		}
		
		rotx += rotation.x;
		roty -= rotation.y;
		rotz += rotation.z;
		
		roty += part.transformation.rotY;
		rotx += part.transformation.rotX;
		rotz += part.transformation.rotZ;
		
		GlStateManager.translatef((float)pos.x * mul, (float)pos.y * mul, (float)pos.z * mul);
		
		Vector3f translation = new Vector3f((float)pos.x * mul, (float)pos.y * mul, (float)pos.z * mul);
		
		Matrix4f t = new Matrix4f();
		t.set(translation);
		
		Matrix4f rx = new Matrix4f();	
		rx.rotX(-(float)Math.toRadians(rotx));
		
		Matrix4f ry = new Matrix4f();	
		ry.rotY(-(float)Math.toRadians(roty));
		
		Matrix4f rz = new Matrix4f();	
		rz.rotZ(-(float)Math.toRadians(rotz));
		
		Matrix4f mat = new Matrix4f();
		mat.setIdentity();
		
		mat.mul(rx);
		mat.mul(rz);
		mat.mul(ry);


		float[] f = {
				mat.m00, mat.m01, mat.m02, mat.m03, 
				mat.m10, mat.m11, mat.m12, mat.m13,
				mat.m20, mat.m21, mat.m22, mat.m23,
				mat.m30, mat.m31, mat.m32, mat.m33,
		};
		FloatBuffer buffer = BufferUtils.createFloatBuffer(4 * 4);
		buffer.put(f);
		buffer.flip();
//		GlStateManager.multMatrix(buffer);
		
		GlStateManager.rotatef(rotx, 1, 0, 0);
		GlStateManager.rotatef(roty, 0, 1, 0);
		GlStateManager.rotatef(rotz, 0, 0, 1);

		if (model.parts.get(part_name).children != null) {
			for (ModelPart modelpart : model.parts.get(part_name).children) {
				if (modelpart != null) {
					GlStateManager.pushMatrix();

					renderModelPart(modelpart.name, entity, f5);
					GlStateManager.popMatrix();
				}
			}
		}
		
		
		GlStateManager.pushMatrix();
		
		GlStateManager.scalef(1, -1, -1);
		
		if (part.vertexCoords.size() > 0) {
			Vertex trueScale = new Vertex(Math.abs(part.vertexCoords.get(5).x / part.transformation.size_x),
					 Math.abs(part.vertexCoords.get(5).y / part.transformation.size_y),
					 Math.abs(part.vertexCoords.get(5).z / part.transformation.size_z));
			float m = 2.1f;
			
			GlStateManager.scalef(trueScale.x * m, trueScale.y * m, trueScale.z * m);
		}
		
		parts.get(part_name).render(f5);
		if (extraRenderers.get(part_name) != null) {
			for (BlankModel model : extraRenderers.get(part_name)) {
				model.render(entity);
			}
		}
		GlStateManager.popMatrix();
		
		GlStateManager.popMatrix();
	}
	
	public void addExtraRenderer(String part, BlankModel model) {
		if (extraRenderers.get(part) == null) {
			extraRenderers.put(part, new ArrayList<BlankModel>());
		}
		extraRenderers.get(part).add(model);
	}
	
	public void setRotationAngle(RendererModel modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
