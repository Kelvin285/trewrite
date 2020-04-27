package kmerrill285.trewrite.entities.models;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;

import com.mojang.blaze3d.platform.GlStateManager;

import kmerrill285.modelloader.CustomModel;
import kmerrill285.modelloader.ModelLoader;
import kmerrill285.modelloader.animation.Animation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;

public class PlayerModel extends EntityModel<PlayerEntity> {

	public HashMap<String, CustomModel> HAIR;
	public HashMap<String, CustomModel> SHIRT;
	public HashMap<String, CustomModel> SHOES;
	public HashMap<String, CustomModel> PANTS;
	
	public HashMap<String, ResourceLocation> TEXTURES;
	
	public CustomModel body;
	public CustomModel eyes;
	
	public String hair;
	public String shirt;
	public String shoes;
	public String pants;
	public String skin_texture;
	public String eyes_texture;
	
	public Animation IDLE;
	public Animation WALKING;

	
	public PlayerModel() {
		HAIR = new HashMap<String, CustomModel>();
		SHIRT = new HashMap<String, CustomModel>();
		SHOES = new HashMap<String, CustomModel>();
		PANTS = new HashMap<String, CustomModel>();
		TEXTURES = new HashMap<String, ResourceLocation>();
		body = new CustomModel(ModelLoader.loadModelFromFile("player/PlayerModel"), 128, 64);
		eyes = new CustomModel(ModelLoader.loadModelFromFile("player/eyes"), 64, 32);
		IDLE = ModelLoader.loadAnimationFromFile("player/Idle");
		WALKING = ModelLoader.loadAnimationFromFile("player/Walking");

		body.animationController.setNextAnimation(IDLE, 1.0f);
		
		for (int i = 0; true; i++) {
			File file = null;
			try {
				URL url = ModelLoader.class.getClassLoader().getResource("assets/trewrite/models/entity/player/hair/hair"+(i+1)+".json");
				if (url == null) break;
				URI uri = url.toURI();
				if (uri == null) break;
				file = new File(uri.getPath());
			} catch (URISyntaxException e1) {
				e1.printStackTrace();
				break;
			}
			if (file == null) break;
			if (file != null) {
				if (!file.exists()) break;
				HAIR.put("hair"+(i+1), new CustomModel(ModelLoader.loadModelFromFile("player/hair/hair"+(i+1)), 64, 64));
				TEXTURES.put("hair"+(i+1), new ResourceLocation("trewrite:textures/entity/player/hair/hair"+(i+1)+".png"));
			}
		}
		
		for (int i = 0; true; i++) {
			File file = null;
			try {
				URL url = ModelLoader.class.getClassLoader().getResource("assets/trewrite/models/entity/player/shirt/shirt"+(i+1)+".json");
				if (url == null) break;
				URI uri = url.toURI();
				if (uri == null) break;
				file = new File(uri.getPath());} catch (URISyntaxException e1) {
				e1.printStackTrace();
				break;
			}
			if (file == null) break;
			if (file != null) {
				if (!file.exists()) break;
				SHIRT.put("shirt"+(i+1), new CustomModel(ModelLoader.loadModelFromFile("player/shirt/shirt"+(i+1)), 128, 64));
				TEXTURES.put("shirt"+(i+1), new ResourceLocation("trewrite:textures/entity/player/shirt/shirt"+(i+1)+".png"));
			}
		}
		
		for (int i = 0; true; i++) {
			File file = null;
			try {
				URL url = ModelLoader.class.getClassLoader().getResource("assets/trewrite/models/entity/player/shoes/shoes"+(i+1)+".json");
				if (url == null) break;
				URI uri = url.toURI();
				if (uri == null) break;
				file = new File(uri.getPath());} catch (URISyntaxException e1) {
				e1.printStackTrace();
				break;
			}
			if (file == null) break;
			if (file != null) {
				if (!file.exists()) break;
				SHOES.put("shoes"+(i+1), new CustomModel(ModelLoader.loadModelFromFile("player/shoes/shoes"+(i+1)), 32, 32));
				TEXTURES.put("shoes"+(i+1), new ResourceLocation("trewrite:textures/entity/player/shoes/shoes"+(i+1)+".png"));
			}
		}
		
		for (int i = 0; true; i++) {
			File file = null;
			try {
				URL url = ModelLoader.class.getClassLoader().getResource("assets/trewrite/models/entity/player/pants/pants"+(i+1)+".json");
				if (url == null) break;
				URI uri = url.toURI();
				if (uri == null) break;
				file = new File(uri.getPath());} catch (URISyntaxException e1) {
				e1.printStackTrace();
				break;
			}
			if (file == null) break;
			if (file != null) {
				if (!file.exists()) break;
				PANTS.put("pants"+(i+1), new CustomModel(ModelLoader.loadModelFromFile("player/pants/pants"+(i+1)), 64, 32));
				TEXTURES.put("pants"+(i+1), new ResourceLocation("trewrite:textures/entity/player/pants/pants"+(i+1)+".png"));
			}
		}
		
		for (int i = 0; true; i++) {
			File file = null;
			try {
				URL url = ModelLoader.class.getClassLoader().getResource("assets/trewrite/textures/entity/player/skin/skin"+(i+1)+".png");
				if (url == null) break;
				URI uri = url.toURI();
				if (uri == null) break;
				file = new File(uri.getPath());} catch (URISyntaxException e1) {
				e1.printStackTrace();
				break;
			}
			if (file == null) break;
			if (file != null) {
				if (!file.exists()) break;
				TEXTURES.put("skin"+(i+1), new ResourceLocation("trewrite:textures/entity/player/skin/skin"+(i+1)+".png"));
			}
		}
		
		for (int i = 0; true; i++) {
			File file = null;
			try {
				URL url = ModelLoader.class.getClassLoader().getResource("assets/trewrite/textures/entity/player/eyes/eyes"+(i+1)+".png");
				if (url == null) break;
				URI uri = url.toURI();
				if (uri == null) break;
				file = new File(uri.getPath());} catch (URISyntaxException e1) {
				e1.printStackTrace();
				break;
			}
			if (file == null) break;
			if (file != null) {
				if (!file.exists()) break;
				TEXTURES.put("eyes"+(i+1), new ResourceLocation("trewrite:textures/entity/player/eyes/eyes"+(i+1)+".png"));
			}
		}
		
		shirt = "shirt1";
		hair = "hair1";
		shoes = "shoes1";
		pants = "pants1";
		skin_texture = "skin1";
		eyes_texture = "eyes1";
	}

	
	@Override
	public void render(PlayerEntity entity, float x, float y, float z, float f3, float f4, float f5) {
		
		
		CustomModel hairModel = HAIR.get(hair);
		CustomModel shirtModel = SHIRT.get(shirt);
		CustomModel pantsModel = PANTS.get(pants);
		CustomModel shoesModel = SHOES.get(shoes);
		
		ResourceLocation hairTexture = TEXTURES.get(hair);
		ResourceLocation shirtTexture = TEXTURES.get(shirt);
		ResourceLocation pantsTexture = TEXTURES.get(pants);
		ResourceLocation shoesTexture = TEXTURES.get(shoes);
		ResourceLocation skinTexture = TEXTURES.get(skin_texture);
		ResourceLocation eyesTexture = TEXTURES.get(eyes_texture);
		
		double ry = entity.rotationYawHead - entity.renderYawOffset;
		double pitch = entity.rotationPitch;
		if (pitch > 60) pitch = 60;
		if (pitch < -60) pitch = -60;
		int i = 0;
		if (pitch > 20) {
			i = (int)pitch - 20;
			if (i > 30) i = 30;
		}
		if (pitch < -20) {
			i = (int)Math.abs(pitch) - 20;
			if (i > 30) i = 30;
		}
		if (pitch > -20 && pitch < 20) {
			i = -(int)Math.abs(pitch) / 4;
		}
		if (ry > 45 - i) ry = 45 - i;
		if (ry < -45 + i) ry = -45 + i;
		
		double rx = -pitch - pitch * Math.abs(Math.sin(Math.toRadians(ry)));
		double rz = pitch * Math.sin(Math.toRadians(ry)) * 2;
		body.extraRotation.put("Neck", new Vec3d(rx, ry, rz));
		eyes.extraRotation.put("Neck", new Vec3d(rx, ry, rz));
		
		if (hairModel != null) {
			hairModel.extraRotation.put("Neck", new Vec3d(rx, ry, rz));
		}
		
		double motion = Math.sqrt(Math.pow(entity.getMotion().x, 2) + Math.pow(entity.getMotion().z, 2)) / 2.0;
		
//		GlStateManager.rotated(motion * 100, 1, 0, 0);
		
		float animation_speed = 1f;
		if (motion > 0) {
			if (body.animationController.nextAnimation != WALKING) {
				body.animationController.setNextAnimation(WALKING, 1.0f);
				
			}
			animation_speed = 1.0f;
			if (Minecraft.getInstance().gameSettings.keyBindSprint.isKeyDown()) {
				animation_speed = 2.0f;
			}
		} else {
			animation_speed = 0.1f;
			if (body.animationController.nextAnimation != IDLE) {
				body.animationController.setNextAnimation(IDLE, 1.0f);
			}
		}
		
		animation_speed *= 0.25f;
		
		if (eyes.animationController.nextAnimation != body.animationController.nextAnimation) {
			eyes.animationController.setNextAnimation(body.animationController.nextAnimation, 1.0f);
			if (eyes.animationController.currentAnimation != null) {
				if (body.animationController.currentAnimation != null) {
					eyes.animationController.currentAnimation.currentFrame = body.animationController.currentAnimation.currentFrame;
				}
			}
		}
		
		Minecraft.getInstance().getRenderManager().textureManager.bindTexture(skinTexture);
		body.render(entity, x, y, z, f3, f4, f5);
		
		Minecraft.getInstance().getRenderManager().textureManager.bindTexture(eyesTexture);
		eyes.render(entity, x, y, z, f3, f4, f5);
		
		if (hairModel != null) {
			Minecraft.getInstance().getRenderManager().textureManager.bindTexture(hairTexture);
			if (hairModel.animationController.nextAnimation != body.animationController.nextAnimation) {
				hairModel.animationController.setNextAnimation(body.animationController.nextAnimation, 1.0f);
			}
			hairModel.animationController.update(animation_speed);
			hairModel.animationController.currentAnimation = body.animationController.currentAnimation;
			hairModel.animationController.nextAnimation = body.animationController.nextAnimation;
			hairModel.animationController.time = body.animationController.time;
			if (hairModel.animationController.currentAnimation != null) {
				if (body.animationController.currentAnimation != null) {
					hairModel.animationController.currentAnimation.currentFrame = body.animationController.currentAnimation.currentFrame;
				}
			}
			hairModel.render(entity, x, y, z, f3, f4, f5);
		}
		
		if (shirtModel != null) {
			Minecraft.getInstance().getRenderManager().textureManager.bindTexture(shirtTexture);
			if (shirtModel.animationController.nextAnimation != body.animationController.nextAnimation) {
				shirtModel.animationController.setNextAnimation(body.animationController.nextAnimation, 1.0f);
			}
			shirtModel.animationController.update(animation_speed);
			shirtModel.animationController.currentAnimation = body.animationController.currentAnimation;
			shirtModel.animationController.nextAnimation = body.animationController.nextAnimation;
			shirtModel.animationController.time = body.animationController.time;
			if (shirtModel.animationController.currentAnimation != null) {
				if (body.animationController.currentAnimation != null) {
					shirtModel.animationController.currentAnimation.currentFrame = body.animationController.currentAnimation.currentFrame;
				}
			}
			shirtModel.render(entity, x, y, z, f3, f4, f5);
		}
		if (pantsModel != null) {
			Minecraft.getInstance().getRenderManager().textureManager.bindTexture(pantsTexture);
			if (pantsModel.animationController.nextAnimation != body.animationController.nextAnimation) {
				pantsModel.animationController.setNextAnimation(body.animationController.nextAnimation, 1.0f);
			}
			pantsModel.animationController.update(animation_speed);
			pantsModel.animationController.currentAnimation = body.animationController.currentAnimation;
			pantsModel.animationController.nextAnimation = body.animationController.nextAnimation;
			pantsModel.animationController.time = body.animationController.time;
			if (pantsModel.animationController.currentAnimation != null) {
				if (body.animationController.currentAnimation != null) {
					pantsModel.animationController.currentAnimation.currentFrame = body.animationController.currentAnimation.currentFrame;
				}
			}
			pantsModel.render(entity, x, y, z, f3, f4, f5);
		}
		
		if (shoesModel != null) {
			Minecraft.getInstance().getRenderManager().textureManager.bindTexture(shoesTexture);
			if (shoesModel.animationController.nextAnimation != body.animationController.nextAnimation) {
				shoesModel.animationController.setNextAnimation(body.animationController.nextAnimation, 1.0f);
			}
			shoesModel.animationController.update(animation_speed);
			shoesModel.animationController.currentAnimation = body.animationController.currentAnimation;
			shoesModel.animationController.nextAnimation = body.animationController.nextAnimation;
			shoesModel.animationController.time = body.animationController.time;
			if (shoesModel.animationController.currentAnimation != null) {
				if (body.animationController.currentAnimation != null) {
					shoesModel.animationController.currentAnimation.currentFrame = body.animationController.currentAnimation.currentFrame;
				}
			}
			GlStateManager.pushMatrix();
			shoesModel.render(entity, x, y, z, f3, f4, f5);
			GlStateManager.popMatrix();
		}
		
		body.animationController.update(animation_speed);
		eyes.animationController.update(animation_speed);
		
		

//		AnimatedModel idle = Models.GUIDE.get("idle");
//		AnimatedModel walking = Models.GUIDE.get("walking");
//		AnimatedModel jumping = Models.GUIDE.get("jumping");
//		AnimatedModel running = Models.GUIDE.get("running");
//		AnimatedModel idle_hurt = Models.GUIDE.get("idle_hurt");
//		
//		
//		AnimatedModel current_animation = walking;
//		
//		
//		double animation_speed = 1.0f;
//		
//		
//		double motion = Math.sqrt(Math.pow(entity.getMotion().x, 2) + Math.pow(entity.getMotion().z, 2)) / 2.0;
//		double walked = Math.abs(entity.prevDistanceWalkedModified - entity.distanceWalkedOnStepModified);
//
//		if (motion > 0) {
//			current_animation = walking;
//			animation_speed *= 2;
//			if (walked > 0.134) {
//				if (entity.isSprinting())
//				current_animation = running;
//				animation_speed *= 1.5;
//				if (Minecraft.getInstance().gameSettings.keyBindForward.isKeyDown() == false ||
//						Minecraft.getInstance().gameSettings.keyBindBack.isKeyDown()) {
//					current_animation = walking;
//				}
//			}
//		} else {
//			current_animation = idle;
//			if (entity.getHealth() <= 30) {
//				current_animation = idle_hurt;
//			}
//		}
//		if (entity.onGround == false && entity.world.getBlockState(entity.getPosition().down()) != null && entity.world.getBlockState(entity.getPosition().down()).getMaterial().blocksMovement() == false) {
//			current_animation = jumping;
//		}
//		double animation_time = ((Utilities.worldTime() * animation_speed) % 30) / 30.0f;
//
//		double realtime = animation_time;
//
//		
//		if (current_animation != null) {
//			GlStateManager.pushMatrix();
//			GlStateManager.rotated(180, 0, 0, 1);
//			GlStateManager.rotated(180, 0, 1, 0);
//			
////			GlStateManager.rotated(Math.cos(Math.toRadians(entity.getYaw(f5))) * entity.getMotion().z * 100, 1, 0, 0);
////			GlStateManager.rotated(-Math.sin(Math.toRadians(entity.getYaw(f5))) * entity.getMotion().x * 100, 1, 0, 0);
//			double cosine = Math.cos(Math.toRadians(entity.getYaw(f5)));
//			double sine = Math.sin(Math.toRadians(entity.getYaw(f5)));
//			
//			
//			
//			boolean backwards = false;
//			double mul = 1.0;
//			if (Minecraft.getInstance().gameSettings.keyBindBack.isKeyDown()) {
//				mul = 0.25;
//			} else {
//				if (Minecraft.getInstance().gameSettings.keyBindSprint.isKeyDown()) {
//					mul = 1.25;
//				}
//				if (current_animation == running) mul = 2.0;
//				if (current_animation == jumping) mul = 0.5;
//			}
////			GlStateManager.rotated(motion * 360 * entity.getForward().getX(), 1, 0, 0);
//			GlStateManager.rotated(motion * 50 * mul * animation_speed, 1, 0, 0);
//
//			GlStateManager.translated(-0.5, -1.5, -0.5);
//			if (current_animation == running)
//				GlStateManager.translated(0, 0, 0.5);
////			GlStateManager.translated(entity.posX, entity.posY, entity.posZ);
//			
//			
//			current_animation.render(realtime);
//			
//			
//			if (entity.getPrimaryHand() == HandSide.LEFT) { 
//				AnimatedNode left_hand = current_animation.getRootNodes().get(0).getChildren().get(0).getChildren().get(0).getChildren().get(0).getChildren().get(0).getChildren().get(0).getChildren().get(0).getChildren().get(1).getChildren().get(0).getChildren().get(0).getChildren().get(0).getChildren().get(0).getChildren().get(0).getChildren().get(0);
//				
//				TRSTransformation transformation = current_animation.getNodeTransform(left_hand.getIndex(), (float)(realtime - (int)realtime));
//				
//				GlStateManager.pushMatrix();
//				transformation.glMultiply();
//				GlStateManager.translated(-0.88, -0.7, -0.5);
//				
//				if (entity.getHeldItemMainhand() != null) {
//					Item item = entity.getHeldItemMainhand().getItem();
//					if (item != null) {
//						GlStateManager.pushMatrix();
//				        
//
//				         GlStateManager.translated(0.88, 0.6, 0.6);
//				         // Forge: moved this call down, fixes incorrect offset while sneaking.
//				         GlStateManager.rotatef(100.0F, 1.0F, 0.0F, 0.0F);
//				         GlStateManager.rotatef(180.0F, 0.0F, 1.0F, 0.0F);
//				         
//				         Minecraft.getInstance().getFirstPersonRenderer().renderItemSide(entity, entity.getHeldItemMainhand(), TransformType.FIRST_PERSON_RIGHT_HAND, false);
//				         GlStateManager.popMatrix();
//				         
//				         GlStateManager.popMatrix();
//					}
//				}
//			}
//			
//			if (entity.getPrimaryHand() == HandSide.RIGHT) { 
//				AnimatedNode right_hand = current_animation.getRootNodes().get(0).getChildren().get(0).getChildren().get(0).getChildren().get(0).getChildren().get(0).getChildren().get(0).getChildren().get(0).getChildren().get(2).getChildren().get(0).getChildren().get(0).getChildren().get(0).getChildren().get(0).getChildren().get(0).getChildren().get(0);
//				
//
//				
//				TRSTransformation transformation = current_animation.getNodeTransform(right_hand.getIndex(), (float)realtime);
//				
//				GlStateManager.pushMatrix();
//				transformation.glMultiply();
//				GlStateManager.translated(-0.68, -0.7, -0.5);
//				
//				if (entity.getHeldItemMainhand() != null) {
//					Item item = entity.getHeldItemMainhand().getItem();
//					if (item != null) {
//						GlStateManager.pushMatrix();
//				        
//
//				         GlStateManager.translated(0.88, 0.6, 0.6);
//				         // Forge: moved this call down, fixes incorrect offset while sneaking.
//				         GlStateManager.rotatef(100.0F, 1.0F, 0.0F, 0.0F);
//				         GlStateManager.rotatef(180.0F, 0.0F, 1.0F, 0.0F);
//				         
//				         Minecraft.getInstance().getFirstPersonRenderer().renderItemSide(entity, entity.getHeldItemMainhand(), TransformType.FIRST_PERSON_RIGHT_HAND, false);
//				         GlStateManager.popMatrix();
//				         
//				         GlStateManager.popMatrix();
//					}
//				}
//			}
//			
//			GlStateManager.popMatrix();
//
//				
//			
//			
//
//		}
	}
	public void setRotationAngle(RendererModel modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}