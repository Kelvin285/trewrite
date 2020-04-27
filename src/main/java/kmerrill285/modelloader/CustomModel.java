package kmerrill285.modelloader;

import java.util.HashMap;

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

	public Model model;
	public AnimationController animationController;
	
	public CustomModel(Model model, int tex_width, int tex_height) {
		textureWidth = tex_width;
		textureHeight = tex_height;
		this.model = model;
		this.animationController = new AnimationController();
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
		
		Animation currentAnimation = this.animationController.currentAnimation;
		Animation newAnimation = this.animationController.nextAnimation;
		
		Vertex animPos = null;
		Vertex animRot = null;
		
		Vertex nextPos = null;
		Vertex nextRot = null;
		
		AnimationFrame currentPositionFrame = null;
		AnimationFrame nextPositionFrame = null;
		
		AnimationFrame currentRotationFrame = null;
		AnimationFrame nextRotationFrame = null;
		
		Vertex newAnimPos = null;
		Vertex newAnimRot = null;
		Vertex newNextAnimPos = null;
		Vertex newNextAnimRot = null;
		
		AnimationFrame newCurrentPositionFrame = null;
		AnimationFrame newNextPositionFrame = null;
		
		AnimationFrame newCurrentRotationFrame = null;
		AnimationFrame newNextRotationFrame = null;
		
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
				AnimationFrameData frameData = frame.frameData.get(part_name);
				if (frameData != null) {
					if (animPos == null) {
						if (frameData.position != null) {
							animPos = new Vertex(frameData.position.x + 0, frameData.position.y + 0, frameData.position.z + 0);
							currentPositionFrame = frame;
						}
					}
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
				AnimationFrameData frameData = frame.frameData.get(part_name);
				if (frameData != null) {
					if (nextPos == null) {
						if (frameData.position != null) {
							nextPos = new Vertex(frameData.position.x + 0, frameData.position.y + 0, frameData.position.z + 0);
							nextPositionFrame = frame;
						}
					}
					if (nextRot == null) {
						if (frameData.rotation != null) {
							nextRot = new Vertex(frameData.rotation.x + 0, frameData.rotation.y + 0, frameData.rotation.z + 0);
							nextRotationFrame = frame;
						}
					}
				}
			}
		}
		
		if (newAnimation != null && newAnimation != currentAnimation) {
			//get current position frame data
			int iterations = 0;
			for (int i = (int)newAnimation.currentFrame; i >= -newAnimation.duration; i--) {
				if (i < 0) {
					i = (int)newAnimation.duration + 1;
					iterations++;
					if (iterations > 1) break;
				}
				AnimationFrame frame = newAnimation.getFrameFor(i);
				AnimationFrameData frameData = frame.frameData.get(part_name);
				if (frameData != null) {
					if (newAnimPos == null) {
						if (frameData.position != null) {
							newAnimPos = new Vertex(frameData.position.x + 0, frameData.position.y + 0, frameData.position.z + 0);
							newCurrentPositionFrame = frame;
						}
					}
					if (newAnimRot == null) {
						if (frameData.rotation != null) {
							newAnimRot = new Vertex(frameData.rotation.x + 0, frameData.rotation.y + 0, frameData.rotation.z + 0);
							newCurrentRotationFrame = frame;
						}
					}
				}
			}
			
			iterations = 0;
			for (int i = (int)newAnimation.currentFrame; i <= newAnimation.duration * 2; i++) {
				if (i > newAnimation.duration) {
					i = -1;
					iterations++;
					if (iterations > 1) break;
				}
				AnimationFrame frame = newAnimation.getNextFrameAfter(i);
				AnimationFrameData frameData = frame.frameData.get(part_name);
				if (frameData != null) {
					if (nextPos == null) {
						if (frameData.position != null) {
							newNextAnimPos = new Vertex(frameData.position.x + 0, frameData.position.y + 0, frameData.position.z + 0);
							newNextPositionFrame = frame;
						}
					}
					if (nextRot == null) {
						if (frameData.rotation != null) {
							newNextAnimRot = new Vertex(frameData.rotation.x + 0, frameData.rotation.y + 0, frameData.rotation.z + 0);
							newNextRotationFrame = frame;
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
			
			if (newAnimPos != null) {
				animPos.x = (float)Util.lerp(animPos.x, newAnimPos.x, animationController.time);
				animPos.y = (float)Util.lerp(animPos.y, newAnimPos.y, animationController.time);
				animPos.z = (float)Util.lerp(animPos.z, newAnimPos.z, animationController.time);
			}
			
			pos.x += animPos.x;
			pos.y += animPos.y;
			pos.z += animPos.z;
		}
		
		GlStateManager.translatef((float)pos.x * mul, -(float)pos.y * mul, (float)pos.z * mul);
		
		if (extraRotation.get(part.name) != null) {
			GlStateManager.rotated(extraRotation.get(part.name).x, 1, 0, 0);
			GlStateManager.rotated(extraRotation.get(part.name).y, 0, 1, 0);
			GlStateManager.rotated(extraRotation.get(part.name).z, 0, 0, 1);
		}
		
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
			
			if (newAnimRot != null) {
				animRot.x = (float)Util.lerp(animRot.x, newAnimRot.x, animationController.time);
				animRot.y = (float)Util.lerp(animRot.y, newAnimRot.y, animationController.time);
				animRot.z = (float)Util.lerp(animRot.z, newAnimRot.z, animationController.time);
			}
			
			GlStateManager.rotated(-animRot.x, 1, 0, 0);
			GlStateManager.rotated(-animRot.y, 0, 1, 0);
			GlStateManager.rotated(-animRot.z, 0, 0, 1);
		}
		
		GlStateManager.rotatef(-part.transformation.rotX, 1, 0, 0);
		GlStateManager.rotatef(-part.transformation.rotY, 0, 1, 0);
		GlStateManager.rotatef(-part.transformation.rotZ, 0, 0, 1);
		
		if (model.parts.get(part_name).children != null) {
			for (ModelPart modelpart : model.parts.get(part_name).children) {
				if (modelpart != null)
				renderModelPart(modelpart.name, f5);
			}
		}
		GlStateManager.pushMatrix();
		
		GlStateManager.scalef(1, 1, -1);
		
		if (part.vertexCoords.size() > 0) {
			Vertex trueScale = new Vertex(Math.abs(part.vertexCoords.get(5).x / part.transformation.size_x),
					 Math.abs(part.vertexCoords.get(5).y / part.transformation.size_y),
					 Math.abs(part.vertexCoords.get(5).z / part.transformation.size_z));
			float m = 2.1f;
			
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
