package kmerrill285.trewrite.entities.models;

import com.cout970.modelloader.animation.AnimatedModel;
import com.cout970.modelloader.animation.AnimatedNode;
import com.cout970.modelloader.api.TRSTransformation;
import com.cout970.modelloader.api.Utilities;
import com.cout970.modelloader.mutable.MutableTRSTransformation;
import com.mojang.blaze3d.platform.GlStateManager;

import kmerrill285.trewrite.util.Models;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.HandSide;

public class PlayerModel extends EntityModel<PlayerEntity> {

	public PlayerModel() {

	}

	@Override
	public void render(PlayerEntity entity, float x, float y, float z, float f3, float f4, float f5) {
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
			if (entity.getHealth() <= 30) {
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
			
//			GlStateManager.rotated(Math.cos(Math.toRadians(entity.getYaw(f5))) * entity.getMotion().z * 100, 1, 0, 0);
//			GlStateManager.rotated(-Math.sin(Math.toRadians(entity.getYaw(f5))) * entity.getMotion().x * 100, 1, 0, 0);
			double cosine = Math.cos(Math.toRadians(entity.getYaw(f5)));
			double sine = Math.sin(Math.toRadians(entity.getYaw(f5)));
			
			
			
			boolean backwards = false;
			double mul = 1.0;
			if (Minecraft.getInstance().gameSettings.keyBindBack.isKeyDown()) {
				mul = 0.25;
			} else {
				if (Minecraft.getInstance().gameSettings.keyBindSprint.isKeyDown()) {
					mul = 1.25;
				}
				if (current_animation == running) mul = 2.0;
				if (current_animation == jumping) mul = 0.5;
			}
//			GlStateManager.rotated(motion * 360 * entity.getForward().getX(), 1, 0, 0);
			GlStateManager.rotated(motion * 50 * mul * animation_speed, 1, 0, 0);

			GlStateManager.translated(-0.5, -1.5, -0.5);
			if (current_animation == running)
				GlStateManager.translated(0, 0, 0.5);
//			GlStateManager.translated(entity.posX, entity.posY, entity.posZ);
			
			
			current_animation.render(realtime);
			
			
			if (entity.getPrimaryHand() == HandSide.LEFT) { 
				AnimatedNode left_hand = current_animation.getRootNodes().get(0).getChildren().get(0).getChildren().get(0).getChildren().get(0).getChildren().get(0).getChildren().get(0).getChildren().get(0).getChildren().get(1).getChildren().get(0).getChildren().get(0).getChildren().get(0).getChildren().get(0).getChildren().get(0).getChildren().get(0);
				
				TRSTransformation transformation = current_animation.getNodeTransform(left_hand.getIndex(), (float)(realtime - (int)realtime));
				
				GlStateManager.pushMatrix();
				transformation.glMultiply();
				GlStateManager.translated(-0.88, -0.7, -0.5);
				
				if (entity.getHeldItemMainhand() != null) {
					Item item = entity.getHeldItemMainhand().getItem();
					if (item != null) {
						GlStateManager.pushMatrix();
				        

				         GlStateManager.translated(0.88, 0.6, 0.6);
				         // Forge: moved this call down, fixes incorrect offset while sneaking.
				         GlStateManager.rotatef(100.0F, 1.0F, 0.0F, 0.0F);
				         GlStateManager.rotatef(180.0F, 0.0F, 1.0F, 0.0F);
				         
				         Minecraft.getInstance().getFirstPersonRenderer().renderItemSide(entity, entity.getHeldItemMainhand(), TransformType.FIRST_PERSON_RIGHT_HAND, false);
				         GlStateManager.popMatrix();
				         
				         GlStateManager.popMatrix();
					}
				}
			}
			
			if (entity.getPrimaryHand() == HandSide.RIGHT) { 
				AnimatedNode right_hand = current_animation.getRootNodes().get(0).getChildren().get(0).getChildren().get(0).getChildren().get(0).getChildren().get(0).getChildren().get(0).getChildren().get(0).getChildren().get(2).getChildren().get(0).getChildren().get(0).getChildren().get(0).getChildren().get(0).getChildren().get(0).getChildren().get(0);
				

				
				TRSTransformation transformation = current_animation.getNodeTransform(right_hand.getIndex(), (float)realtime);
				
				GlStateManager.pushMatrix();
				transformation.glMultiply();
				GlStateManager.translated(-0.68, -0.7, -0.5);
				
				if (entity.getHeldItemMainhand() != null) {
					Item item = entity.getHeldItemMainhand().getItem();
					if (item != null) {
						GlStateManager.pushMatrix();
				        

				         GlStateManager.translated(0.88, 0.6, 0.6);
				         // Forge: moved this call down, fixes incorrect offset while sneaking.
				         GlStateManager.rotatef(100.0F, 1.0F, 0.0F, 0.0F);
				         GlStateManager.rotatef(180.0F, 0.0F, 1.0F, 0.0F);
				         
				         Minecraft.getInstance().getFirstPersonRenderer().renderItemSide(entity, entity.getHeldItemMainhand(), TransformType.FIRST_PERSON_RIGHT_HAND, false);
				         GlStateManager.popMatrix();
				         
				         GlStateManager.popMatrix();
					}
				}
			}
			
			GlStateManager.popMatrix();

				
			
			

		}
	}
	public void setRotationAngle(RendererModel modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}