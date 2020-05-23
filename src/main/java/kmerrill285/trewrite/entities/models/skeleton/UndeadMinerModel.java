package kmerrill285.trewrite.entities.models.skeleton;

import javax.swing.text.Utilities;

import com.mojang.blaze3d.platform.GlStateManager;

import kmerrill285.modelloader.CustomModel;
import kmerrill285.modelloader.ModelLoader;
import kmerrill285.modelloader.animation.Animation;
import kmerrill285.trewrite.entities.monsters.EntityUndeadMiner;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraftforge.common.model.Models;

public class UndeadMinerModel extends EntityModel<EntityUndeadMiner> {

	public Animation IDLE;
	public Animation JUMPING;
	public Animation MARCHING;
	public Animation WALKING;
	public CustomModel body;

	
	public UndeadMinerModel() {
		IDLE = ModelLoader.loadAnimationFromFile("undead_miner/idle");
		JUMPING = ModelLoader.loadAnimationFromFile("undead_miner/jump");
		MARCHING = ModelLoader.loadAnimationFromFile("undead_miner/marching");
		WALKING = ModelLoader.loadAnimationFromFile("undead_miner/walking");
		
		body = new CustomModel(ModelLoader.loadModelFromFile("undead_miner/undead_miner"), 128, 64);

		body.animationController.setNextAnimation(IDLE, 1.0f);

	}

	@Override
	public void render(EntityUndeadMiner entity, float x, float y, float z, float f3, float f4, float f5) {

		body.animationController.nextAnimation = MARCHING;
		body.animationController.currentAnimation = MARCHING;
		
		double motion = Math.sqrt(Math.pow(entity.getMotion().x, 2) + Math.pow(entity.getMotion().z, 2)) / 2.0;
		if (motion <= 0.01f) {
			body.animationController.nextAnimation = IDLE;
			body.animationController.currentAnimation = IDLE;
			body.animationController.update(0.1f);
		} else {
			body.animationController.update(0.75f);
		}
		
		
//		body.animationController.time = 0;
		
		GlStateManager.pushMatrix();
		GlStateManager.rotatef(10, 1, 0, 0);
		body.render(entity, x, y, z, f3, f4, f5);
		GlStateManager.popMatrix();
	}
	public void setRotationAngle(RendererModel modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}