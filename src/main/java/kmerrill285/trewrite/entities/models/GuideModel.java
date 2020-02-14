package kmerrill285.trewrite.entities.models;

import com.cout970.modelloader.animation.AnimatedModel;
import com.cout970.modelloader.animation.AnimatedNode;
import com.cout970.modelloader.api.Utilities;
import com.mojang.blaze3d.platform.GlStateManager;

import kmerrill285.trewrite.entities.npc.EntityGuide;
import kmerrill285.trewrite.util.Models;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;

public class GuideModel extends EntityModel<EntityGuide> {

	public GuideModel() {

	}

	@Override
	public void render(EntityGuide entity, float x, float y, float z, float f3, float f4, float f5) {
		AnimatedModel idle = Models.GUIDE.get("idle");
		AnimatedModel walking = Models.GUIDE.get("walking");
		AnimatedModel jumping = Models.GUIDE.get("jumping");
		AnimatedModel running = Models.GUIDE.get("running");
		AnimatedModel idle_hurt = Models.GUIDE.get("idle_hurt");
		
		
		AnimatedModel current_animation = walking;
		
		
		double animation_speed = 1.0f;
		
		
		double motion = Math.sqrt(Math.pow(entity.getMotion().x, 2) + Math.pow(entity.getMotion().z, 2)) / 2.0;
		double walked = Math.abs(entity.prevDistanceWalkedModified - entity.distanceWalkedOnStepModified);
		if (motion > 0) {
			current_animation = walking;
			animation_speed *= 2;
			if (walked > 0.134) {
				if (entity.isSprinting())
				current_animation = running;
				animation_speed *= 1.5;
				if (Minecraft.getInstance().gameSettings.keyBindForward.isKeyDown() == false ||
						Minecraft.getInstance().gameSettings.keyBindBack.isKeyDown()) {
					current_animation = walking;
				}
			}
		} else {
			current_animation = idle;
			if (entity.getHealth() <= entity.getMaxHealth() * 0.3) {
				current_animation = idle_hurt;
			}
		}
		
		if (entity.onGround == false && entity.world.getBlockState(entity.getPosition().down()) != null && entity.world.getBlockState(entity.getPosition().down()).getMaterial().blocksMovement() == false) {
			current_animation = jumping;
		}
		double animation_time = ((Utilities.worldTime() * animation_speed) % 30) / 30.0f;

		double realtime = animation_time;

		
		if (current_animation != null) {
			GlStateManager.pushMatrix();
			GlStateManager.rotated(180, 0, 0, 1);
			GlStateManager.rotated(180, 0, 1, 0);
			
			double mul = 1.0;
			
			GlStateManager.rotated(motion * 50 * mul * animation_speed, 1, 0, 0);

			GlStateManager.translated(-0.5, -1.5, -0.5);
			if (current_animation == running)
				GlStateManager.translated(0, 0, 0.5);
			
			
			
			current_animation.render(realtime);
			GlStateManager.popMatrix();
		}
	}
	public void setRotationAngle(RendererModel modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}