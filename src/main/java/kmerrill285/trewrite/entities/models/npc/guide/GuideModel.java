package kmerrill285.trewrite.entities.models.npc.guide;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;

import com.google.common.io.Resources;
import com.mojang.blaze3d.platform.GlStateManager;

import kmerrill285.modelloader.BlankModel;
import kmerrill285.modelloader.CustomModel;
import kmerrill285.modelloader.ModelLoader;
import kmerrill285.modelloader.animation.Animation;
import kmerrill285.trewrite.client.gui.inventory.InventoryTerraria;
import kmerrill285.trewrite.client.gui.inventory.container.ContainerTerrariaInventory;
import kmerrill285.trewrite.entities.npc.EntityGuide;
import kmerrill285.trewrite.events.WorldEvents;
import kmerrill285.trewrite.items.ItemStackT;
import kmerrill285.trewrite.items.ItemT;
import kmerrill285.trewrite.items.modifiers.ItemModifier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;

public class GuideModel {

	public HashMap<String, CustomModel> HAIR;
	public HashMap<String, CustomModel> SHIRT;
	public HashMap<String, CustomModel> SHOES;
	public HashMap<String, CustomModel> PANTS;
	
	public HashMap<String, ResourceLocation> TEXTURES;
	
	public CustomModel body;
	public CustomModel legs;

	public CustomModel eyes;
	
	public String hair;
	public String shirt;
	public String shoes;
	public String pants;
	public String skin_texture;
	public String eyes_texture;
	
	public Animation IDLE;
	public Animation WALKING;
	public Animation RUNNING;
	public Animation MINING;
	public Animation STABBING;
	public Animation JUMPING;
	public Animation SNEAK_IDLE;
	public Animation SNEAK_WALK;

	
	public GuideModel() {
		HAIR = new HashMap<String, CustomModel>();
		SHIRT = new HashMap<String, CustomModel>();
		SHOES = new HashMap<String, CustomModel>();
		PANTS = new HashMap<String, CustomModel>();
		TEXTURES = new HashMap<String, ResourceLocation>();
		body = new CustomModel(ModelLoader.loadModelFromFile("player/PlayerModel"), 128, 64);
		legs = new CustomModel(ModelLoader.loadModelFromFile("player/PlayerModel"), 128, 64);
		body.blacklist.add("Left_Leg_Top");
		body.blacklist.add("Right_Leg_Top");
		legs.blacklist.add("Body_Lower");
		eyes = new CustomModel(ModelLoader.loadModelFromFile("player/eyes"), 64, 32);
		IDLE = ModelLoader.loadAnimationFromFile("player/Idle");
		WALKING = ModelLoader.loadAnimationFromFile("player/Walking");
		RUNNING = ModelLoader.loadAnimationFromFile("player/Running");
		MINING = ModelLoader.loadAnimationFromFile("player/Mining");
		STABBING = ModelLoader.loadAnimationFromFile("player/Stabbing");
		JUMPING = ModelLoader.loadAnimationFromFile("player/Jump");
		SNEAK_IDLE = ModelLoader.loadAnimationFromFile("player/Sneak_Idle");
		SNEAK_WALK = ModelLoader.loadAnimationFromFile("player/Sneak_Walk");


		body.animationController.setNextAnimation(IDLE, 1.0f);
		
			
		for (int i = 0; i < Minecraft.getInstance().getResourceManager().getAllResourceLocations("textures/entity/player/hair/", (e) -> {return e.endsWith(".png");}).size(); i++) {
			try {
				URL url = Resources.getResource("assets/trewrite/models/entity/player/hair/hair"+(i+1)+".json");
				
				if (url == null) break;
				URI uri = url.toURI();
				if (uri == null) break;
				HAIR.put("hair"+(i+1), new CustomModel(ModelLoader.loadModelFromFile("player/hair/hair"+(i+1)), 64, 64));
				TEXTURES.put("hair"+(i+1), new ResourceLocation("trewrite:textures/entity/player/hair/hair"+(i+1)+".png"));
			} catch (URISyntaxException e1) {
				e1.printStackTrace();
				break;
			}
		}

	
		for (int i = 0; i < Minecraft.getInstance().getResourceManager().getAllResourceLocations("textures/entity/player/shirt/", (e) -> {return e.endsWith(".png");}).size(); i++) {
			try {
				URL url = Resources.getResource("assets/trewrite/models/entity/player/shirt/shirt"+(i+1)+".json");
				if (url == null) break;
				URI uri = url.toURI();
				if (uri == null) break;
				SHIRT.put("shirt"+(i+1), new CustomModel(ModelLoader.loadModelFromFile("player/shirt/shirt"+(i+1)), 128, 64));
				TEXTURES.put("shirt"+(i+1), new ResourceLocation("trewrite:textures/entity/player/shirt/shirt"+(i+1)+".png"));
			} catch (URISyntaxException e1) {
				e1.printStackTrace();
				break;
			}
		}
		
		
		for (int i = 0; i < Minecraft.getInstance().getResourceManager().getAllResourceLocations("textures/entity/player/shoes/", (e) -> {return e.endsWith(".png");}).size(); i++) {
			try {
				URL url = Resources.getResource("assets/trewrite/models/entity/player/shoes/shoes"+(i+1)+".json");
				if (url == null) break;
				URI uri = url.toURI();
				if (uri == null) break;
				SHOES.put("shoes"+(i+1), new CustomModel(ModelLoader.loadModelFromFile("player/shoes/shoes"+(i+1)), 32, 32));
				TEXTURES.put("shoes"+(i+1), new ResourceLocation("trewrite:textures/entity/player/shoes/shoes"+(i+1)+".png"));
			} catch (URISyntaxException e1) {
				e1.printStackTrace();
				break;
			}
		}
		
		
		for (int i = 0; i < Minecraft.getInstance().getResourceManager().getAllResourceLocations("textures/entity/player/pants/", (e) -> {return e.endsWith(".png");}).size(); i++) {
			try {
				URL url = Resources.getResource("assets/trewrite/models/entity/player/pants/pants"+(i+1)+".json");
				if (url == null) break;
				URI uri = url.toURI();
				if (uri == null) break;
				PANTS.put("pants"+(i+1), new CustomModel(ModelLoader.loadModelFromFile("player/pants/pants"+(i+1)), 64, 32));
				TEXTURES.put("pants"+(i+1), new ResourceLocation("trewrite:textures/entity/player/pants/pants"+(i+1)+".png"));
			} catch (URISyntaxException e1) {
				e1.printStackTrace();
				break;
			}
		}
		
		
		for (int i = 0; i < Minecraft.getInstance().getResourceManager().getAllResourceLocations("textures/entity/player/skin/", (e) -> {return e.endsWith(".png");}).size(); i++) {
			try {
				URL url = Resources.getResource("assets/trewrite/textures/entity/player/skin/skin"+(i+1)+".png");
				if (url == null) break;
				URI uri = url.toURI();
				if (uri == null) break;
				TEXTURES.put("skin"+(i+1), new ResourceLocation("trewrite:textures/entity/player/skin/skin"+(i+1)+".png"));
				} catch (URISyntaxException e1) {
				e1.printStackTrace();
				break;
			}
		}
		
		
		
		for (int i = 0; i < Minecraft.getInstance().getResourceManager().getAllResourceLocations("textures/entity/player/eyes/", (e) -> {return e.endsWith(".png");}).size(); i++) {
			try {
				URL url = Resources.getResource("assets/trewrite/textures/entity/player/eyes/eyes"+(i+1)+".png");
				if (url == null) break;
				URI uri = url.toURI();
				if (uri == null) break;
				TEXTURES.put("eyes"+(i+1), new ResourceLocation("trewrite:textures/entity/player/eyes/eyes"+(i+1)+".png"));
				
			} catch (URISyntaxException e1) {
				e1.printStackTrace();
				break;
			}
			
		}
		
		
		shirt = "shirt1";
		hair = "hair1";
		shoes = "shoes1";
		pants = "pants1";
		skin_texture = "skin1";
		eyes_texture = "eyes1";
		
		BlankModel toolModel = new BlankModel() {
			@Override
			public void render(Entity e) {
				if (e instanceof PlayerEntity) {
					PlayerEntity entity = (PlayerEntity)e;
					if (entity.getPrimaryHand() == HandSide.RIGHT) { 
						GlStateManager.pushMatrix();
						GlStateManager.translated(-0.68, -0.7, -0.5);
						
						if (entity.getHeldItemMainhand() != null) {
							Item item = entity.getHeldItemMainhand().getItem();
							if (item != null) {
								GlStateManager.pushMatrix();
						        
								
						         GlStateManager.translated(0.75, 0.7, 0.4);
						         // Forge: moved this call down, fixes incorrect offset while sneaking.
						         GlStateManager.rotatef(-70.0F, 1.0F, 0.0F, 0.0F);
						         GlStateManager.rotatef(180.0F, 0.0F, 1.0F, 0.0F);
						         
						         if (item instanceof ItemT) {
						        	 InventoryTerraria inventory = null;
						        	 if (e.world.isRemote()) {
						        		 inventory = ContainerTerrariaInventory.inventory;
						        	 } else {
						        		 inventory = WorldEvents.getOrLoadInventory(entity);
						        	 }
						        	 ItemStackT holding = null;
						        	 if (inventory != null) {
						        		 if (inventory.hotbar[inventory.hotbarSelected] != null) {
						        			 if (inventory.hotbar[inventory.hotbarSelected].stack != null) {
						        				 ItemModifier modifier = ItemModifier.getModifier(inventory.hotbar[inventory.hotbarSelected].stack.modifier);
						        				 double scale = 0;
						        				 if (modifier != null) {
						        					 scale = modifier.size / 100.0;
						        				 }
						        				 scale += ((ItemT)item).scale;
						        				 
						        				 
						        				 
						        				 GlStateManager.pushMatrix();
						        				 GlStateManager.rotated(((ItemT)item).rotX, 1, 0, 0);
						        				 GlStateManager.rotated(((ItemT)item).rotY, 0, 1, 0);
						        				 GlStateManager.rotated(((ItemT)item).rotZ, 0, 0, 1);
						        				 GlStateManager.translated(((ItemT)item).offsX, ((ItemT)item).offsY, ((ItemT)item).offsZ);
						        				 GlStateManager.scaled(scale, scale, scale);
						        				 GlStateManager.translated(-scale*0.025, 0, 0);
										         Minecraft.getInstance().getFirstPersonRenderer().renderItemSide(entity, entity.getHeldItemMainhand(), TransformType.FIRST_PERSON_RIGHT_HAND, false);
						        				 GlStateManager.popMatrix();
						        			 }
						        		 }
						        	 }
						         } else {
							         Minecraft.getInstance().getFirstPersonRenderer().renderItemSide(entity, entity.getHeldItemMainhand(), TransformType.FIRST_PERSON_RIGHT_HAND, false);
						         }
						         
						         GlStateManager.popMatrix();
						         
						         GlStateManager.popMatrix();
						         //new ResourceLocation("trewrite:textures/entity/player/hair/hair"+(i+1)+".png")
						 		Minecraft.getInstance().getRenderManager().textureManager.bindTexture(TEXTURES.get(skin_texture));

							}
						}
					}
				}
			}
		};
		this.body.addExtraRenderer("Right_Hand", toolModel);
	}
	
	public void render(EntityGuide entity, float x, float y, float z, float f3, float f4, float f5) {
		
		hair = "hair2";
		shirt = "shirt3";
		pants = "pants3";
		
		CustomModel hairModel = HAIR.get(hair);
		CustomModel shirtModel = SHIRT.get(shirt);
		CustomModel pantsModel = PANTS.get(pants);
		CustomModel shoesModel = SHOES.get(shoes);
		CustomModel eyes = this.eyes;
		
		
		ResourceLocation hairTexture = TEXTURES.get(hair);
		ResourceLocation shirtTexture = TEXTURES.get(shirt);
		ResourceLocation pantsTexture = TEXTURES.get(pants);
		ResourceLocation shoesTexture = TEXTURES.get(shoes);
		ResourceLocation skinTexture = TEXTURES.get(skin_texture);
		ResourceLocation eyesTexture = TEXTURES.get(eyes_texture);
		
		
		float animationSpeed = 0.1f;
		body.animationController.currentAnimation = IDLE;
		
		
		if (new Vec3d(entity.getMotion().x, 0, entity.getMotion().z).length() > 0.01f) {
			body.animationController.currentAnimation = WALKING;
			animationSpeed = 1.0f;
		}
		
		body.animationController.update(animationSpeed);
		
		
		 //new ResourceLocation("trewrite:textures/entity/player/hair/hair"+(i+1)+".png")
 		Minecraft.getInstance().getRenderManager().textureManager.bindTexture(skinTexture);
		body.render(entity, x, y, z, f3, f4, f5);
		Minecraft.getInstance().getRenderManager().textureManager.bindTexture(skinTexture);
		legs.render(entity, x, y, z, f3, f4, f5);
		Minecraft.getInstance().getRenderManager().textureManager.bindTexture(eyesTexture);
		eyes.render(entity, x, y, z, f3, f4, f5);
		Minecraft.getInstance().getRenderManager().textureManager.bindTexture(hairTexture);
		hairModel.render(entity, x, y, z, f3, f4, f5);
		Minecraft.getInstance().getRenderManager().textureManager.bindTexture(shirtTexture);
		shirtModel.render(entity, x, y, z, f3, f4, f5);
		Minecraft.getInstance().getRenderManager().textureManager.bindTexture(shoesTexture);
		shoesModel.render(entity, x, y, z, f3, f4, f5);
		Minecraft.getInstance().getRenderManager().textureManager.bindTexture(pantsTexture);
		pantsModel.render(entity, x, y, z, f3, f4, f5);
		
		eyes.animationController.currentAnimation = body.animationController.currentAnimation;
		hairModel.animationController.currentAnimation = body.animationController.currentAnimation;
		shirtModel.animationController.currentAnimation = body.animationController.currentAnimation;
		shoesModel.animationController.currentAnimation = legs.animationController.currentAnimation;
		pantsModel.animationController.currentAnimation = legs.animationController.currentAnimation;
		
		eyes.animationController.nextAnimation = body.animationController.currentAnimation;
		hairModel.animationController.nextAnimation = body.animationController.currentAnimation;
		shirtModel.animationController.nextAnimation = body.animationController.currentAnimation;
		shoesModel.animationController.nextAnimation = legs.animationController.currentAnimation;
		pantsModel.animationController.nextAnimation = legs.animationController.currentAnimation;
		
		eyes.animationController.time = body.animationController.time;
		hairModel.animationController.time = body.animationController.time;
		shirtModel.animationController.time = body.animationController.time;
		shoesModel.animationController.time = legs.animationController.time;
		pantsModel.animationController.time = legs.animationController.time;
		
		legs.animationController.time = body.animationController.time;
		
		legs.animationController.currentAnimation = body.animationController.currentAnimation;
		legs.animationController.nextAnimation = legs.animationController.currentAnimation;
		body.animationController.nextAnimation = body.animationController.currentAnimation;
	}
}