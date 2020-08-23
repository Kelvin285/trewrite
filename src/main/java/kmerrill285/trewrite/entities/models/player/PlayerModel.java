package kmerrill285.trewrite.entities.models.player;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import com.google.common.io.Resources;
import com.mojang.blaze3d.platform.GlStateManager;

import kmerrill285.modelloader.BlankModel;
import kmerrill285.modelloader.CustomModel;
import kmerrill285.modelloader.ModelLoader;
import kmerrill285.modelloader.animation.Animation;
import kmerrill285.trewrite.client.ClientProxy;
import kmerrill285.trewrite.client.gui.inventory.InventoryTerraria;
import kmerrill285.trewrite.client.gui.inventory.container.ContainerTerrariaInventory;
import kmerrill285.trewrite.events.WorldEvents;
import kmerrill285.trewrite.items.Armor;
import kmerrill285.trewrite.items.ItemBlockT;
import kmerrill285.trewrite.items.ItemStackT;
import kmerrill285.trewrite.items.ItemT;
import kmerrill285.trewrite.items.modifiers.ItemModifier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;

public class PlayerModel extends EntityModel<PlayerEntity> {

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
	
	public PlayerModel() {
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
	public CustomModel armorHead = null;
	public CustomModel armorEyes = null;
	public CustomModel armorPants = null;
	public CustomModel armorShoes = null;
	public CustomModel armorShirt = null;
	public ResourceLocation armorTexHead = null;
	public ResourceLocation armorTexEyes = null;
	public ResourceLocation armorTexPants = null;
	public ResourceLocation armorTexShoes = null;
	public ResourceLocation armorTexShirt = null;
	public boolean noShoes;
	@Override
	public void render(PlayerEntity entity, float x, float y, float z, float f3, float f4, float f5) {
		
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
		
		if (hairTexture == null ||
				shirtTexture == null ||
				pantsTexture == null ||
				shoesTexture == null ||
				skinTexture == null ||
				eyesTexture == null) {
			System.out.println(hairTexture);
			System.out.println(shirtTexture);
			System.out.println(pantsTexture);
			System.out.println(shoesTexture);
			System.out.println(skinTexture);
			System.out.println(eyesTexture);
		}
		
		ArrayList<ItemStack> armor = ClientProxy.playerArmor.get(entity.getScoreboardName());
		
		noShoes = false;
		
		armorHead = null;
		armorShirt = null;
		armorPants = null;
		armorShoes = null;
		armorEyes = null;
		armorTexHead = null;
		armorTexShirt = null;
		armorTexPants = null;
		armorTexShoes = null;
		armorTexEyes = null;
		
		for (ItemStack stack : armor) {
			if (stack != null) {
				if (stack.getItem() instanceof Armor) {
					((Armor)stack.getItem()).renderArmor(this);
				}
			}
		}
		
		if (armorHead != null) hairModel = armorHead;
		if (armorShirt != null) shirtModel = armorShirt;
		if (armorPants != null) pantsModel = armorPants;
		if (armorShoes != null) shoesModel = armorShoes;
		if (armorEyes != null) eyes = armorEyes;
		
		if (armorTexHead != null) hairTexture = armorTexHead;
		if (armorTexShirt != null) shirtTexture = armorTexShirt;
		if (armorTexPants != null) pantsTexture = armorTexPants;
		if (armorTexShoes != null) shoesTexture = armorTexShoes;
		if (armorTexEyes != null) eyesTexture = armorTexEyes;
				
		if (noShoes) shoesModel = null;
		
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
		
		double rx = -pitch * 2;
		double rz = pitch * Math.sin(Math.toRadians(ry)) * 2;
		body.extraRotation.put("Neck", new Vec3d(rx * 0.5f, -ry, -0));
		eyes.extraRotation.put("Neck", new Vec3d(rx * 0.5f, -ry, -0));
		
		if (hairModel != null) {
			hairModel.extraRotation.put("Neck", new Vec3d(rx * 0.5f, -ry, -0));
		}
		
		double motion = Math.sqrt(Math.pow(entity.getMotion().x, 2) + Math.pow(entity.getMotion().z, 2)) / 2.0;
		
		
		float animation_speed = 1f;
		float leg_animation_speed = 1f;
		float leanMul = 1.0f;
		if (motion > 0) {
			if (body.animationController.nextAnimation != WALKING) {
				body.animationController.currentAnimation = WALKING;
			}
			legs.animationController.currentAnimation = WALKING;
			animation_speed = 2f;
			if (entity.isSneaking()) {
				animation_speed = 1f;
				body.animationController.currentAnimation = SNEAK_WALK;
				legs.animationController.currentAnimation = SNEAK_WALK;
			}
			else
			if (entity.isSprinting()) {
				animation_speed = 3f;
				body.animationController.currentAnimation = RUNNING;
				legs.animationController.currentAnimation = RUNNING;
				leanMul = 2.0f;
			}
			
			
		} else {
			animation_speed = 0.1f;
			if (body.animationController.nextAnimation != IDLE) {
				body.animationController.currentAnimation = IDLE;
			}
			legs.animationController.currentAnimation = IDLE;
			if (entity.isSneaking()) {
				body.animationController.currentAnimation = SNEAK_IDLE;
				legs.animationController.currentAnimation = SNEAK_IDLE;
			}
		}
		if (entity.isAirBorne && entity.isSwimming() == false && entity.isInWater() == false && entity.isInLava() == false && 
				!entity.onGround && !entity.isOnLadder()) {
			body.animationController.currentAnimation = JUMPING;
			legs.animationController.currentAnimation = JUMPING;
			
		}
		if (body.animationController.currentAnimation == null) {
			body.animationController.currentAnimation = WALKING;
		}
		if (legs.animationController.currentAnimation == null) {
			legs.animationController.currentAnimation = WALKING;
		}
		leg_animation_speed = animation_speed;
		
		GlStateManager.rotated(motion * 100 * leanMul, 1, 0, 0);
		
		body.extraRotation.remove("Right_Arm_Top");
		body.extraRotation.remove("Left_Arm_Top");
		body.extraRotation.remove("Left_Arm_Bottom");
		body.extraRotation.remove("Right_Arm_Bottom");

		body.extraRotation.remove("Right_Hand");
		body.extraRotation.remove("Left_Hand");
		body.extraRotation.remove("Body_Upper");
		eyes.extraRotation.remove("Body_Upper");
		
		if (shirtModel != null) {
			shirtModel.extraRotation.remove("Body_Upper");
			shirtModel.extraRotation.remove("Right_Arm_Top");
			shirtModel.extraRotation.remove("Left_Arm_Top");
			shirtModel.extraRotation.remove("Left_Arm_Bottom");
			shirtModel.extraRotation.remove("Right_Arm_Bottom");

			shirtModel.extraRotation.remove("Right_Hand");
			shirtModel.extraRotation.remove("Left_Hand");
		}
		if (hairModel != null) {
			hairModel.extraRotation.remove("Body_Upper");
		}
		
		if (body.animationController.currentAnimation == JUMPING) {
			double m = entity.getMotion().y * 25;
			if (m < -75) m = -75;
			if (m > 25) m = 25;
			body.extraRotation.put("Right_Arm_Top", new Vec3d(motion * 100, 0, -m + 45));
			body.extraRotation.put("Left_Arm_Top", new Vec3d(motion * 100, 0, m - 45));
			
			if (shirtModel != null) {
				shirtModel.extraRotation.put("Right_Arm_Top", new Vec3d(0, 0, -m + 45));
				shirtModel.extraRotation.put("Left_Arm_Top", new Vec3d(0, 0, m - 45));
			}
		}
		
		
		
		if (body.animationController.currentAnimation == RUNNING) {
			body.extraRotation.put("Right_Hand", new Vec3d(-25, 0, 0));
		}
		
		if (entity.getHeldItemMainhand() != null) {
			Item item = entity.getHeldItemMainhand().getItem();
			
			if (item != null) {
				if (item instanceof ItemT) {
					double reload = entity.getCooldownTracker().getCooldown(item, f5);

					if (((ItemT)item).animation.equals(ItemT.PICKAXE_ANIMATION) ||
							((ItemT)item).animation.equals(ItemT.HAMMER_ANIMATION) ||
							((ItemT)item).animation.equals(ItemT.THROWING_ANIMATION) ||
							((ItemT)item) instanceof ItemBlockT) {
						if (entity.isSwingInProgress) {
							if (item instanceof ItemBlockT)
								((ItemT)item).useTime = 5;
							if (entity.getCooldownTracker().getCooldown(item, f5) == 0) {
								entity.getCooldownTracker().setCooldown(item, ((ItemT)item).useTime / 3);
							}
						}
						if (entity.getCooldownTracker().getCooldown(item, f5) > 0) {
							
							body.animationController.currentAnimation = MINING;
							if (legs.animationController.currentAnimation == IDLE) {
								legs.animationController.currentAnimation = MINING;
							}
							body.animationController.currentAnimation.currentFrame = (1.0f - entity.getCooldownTracker().getCooldown(item, f5)) * 60.0f;
							body.extraRotation.put("Right_Arm_Top", new Vec3d(rx, -45, 45));
							
							if (hairModel != null) {
								hairModel.animationController.currentAnimation = MINING;
								hairModel.animationController.nextAnimation = MINING;
								
							}
						}
						
					}
					
					if (((ItemT)item).animation.equals(ItemT.AXE_ANIMATION) ||
							((ItemT)item).animation.equals(ItemT.BROADSWORD_ANIMATION) ||
							((ItemT)item).animation.equals(ItemT.SHORTSWORD_ANIMATION)) {
						
						if (entity.swingProgressInt == 1) {
							if (entity.getCooldownTracker().getCooldown(item, f5) == 0) {
								entity.getCooldownTracker().setCooldown(item, ((ItemT)item).useTime / 3);
							}
						}

						if (entity.getCooldownTracker().getCooldown(item, f5) > 0) {

							body.animationController.currentAnimation = MINING;
							if (legs.animationController.currentAnimation == IDLE) {
								legs.animationController.currentAnimation = MINING;
							}
							body.animationController.currentAnimation.currentFrame = (1.0f - entity.getCooldownTracker().getCooldown(item, f5)) * 60;
							body.extraRotation.put("Right_Arm_Top", new Vec3d(rx, 0, 0));
							body.animationController.nextAnimation = MINING;
							if (hairModel != null) {
								hairModel.animationController.currentAnimation = MINING;
								hairModel.animationController.nextAnimation = MINING;
							}
						}
						
					}
					
					if (((ItemT)item).animation.equals(ItemT.BOW_ANIMATION))
						
						if (reload > 0) {
							body.extraRotation.put("Right_Arm_Top", new Vec3d(rx + 90, 0, -20));
							body.extraRotation.put("Left_Arm_Top", new Vec3d(rx + 90, 0, 30 + Math.sin(reload)));
							body.extraRotation.put("Right_Hand", new Vec3d(0, 0, 20));

							body.extraRotation.put("Body_Upper", new Vec3d(0, -ry, 0));
							body.extraRotation.put("Neck", new Vec3d(rx, 0, -0));
							
							if (shirtModel != null) {
								shirtModel.extraRotation.put("Body_Upper", new Vec3d(0, -ry, 0));
								shirtModel.extraRotation.put("Right_Arm_Top", new Vec3d(rx + 90, 0, -20));
								shirtModel.extraRotation.put("Left_Arm_Top", new Vec3d(rx + 90, 0, 30 + Math.sin(reload)));
								shirtModel.extraRotation.put("Right_Hand", new Vec3d(0, 0, 20));

							}
							if (hairModel != null) {
								hairModel.extraRotation.put("Body_Upper", new Vec3d(0, -ry, 0));
								hairModel.extraRotation.put("Neck", new Vec3d(rx, 0, -0));
							}
							if (eyes != null) {
								eyes.extraRotation.put("Body_Upper", new Vec3d(0, -ry, 0));
								eyes.extraRotation.put("Neck", new Vec3d(rx, 0, -0));
							}
							body.animationController.currentAnimation = IDLE;
						} else {
							body.extraRotation.remove("Body_Upper");
							
							if (shirtModel != null) {
								shirtModel.extraRotation.remove("Body_Upper");
							}
							if (hairModel != null) {
								hairModel.extraRotation.remove("Body_Upper");
							}
							if (eyes != null) {
								eyes.extraRotation.remove("Body_Upper");
							}
						}
					if (((ItemT)item).animation.equals(ItemT.GUN_ANIMATION))
						{
							body.extraRotation.put("Right_Arm_Top", new Vec3d(rx + 75, 0, -20));
							body.extraRotation.put("Left_Arm_Top", new Vec3d(rx + 75, 0, 30));
							
							body.extraRotation.put("Right_Hand", new Vec3d(-30, 0, 40));
							
							int recoil = 0;
							if ((int)(reload * 10) == 1) {
								body.extraRotation.put("Right_Arm_Top", new Vec3d(rx + 75 + 5, 0, -20));
								body.extraRotation.put("Left_Arm_Top", new Vec3d(rx + 75 + 5, 0, 30));
								recoil = 2;
							}
							body.extraRotation.put("Neck", new Vec3d(rx, 0, -0));
							body.extraRotation.put("Body_Upper", new Vec3d(recoil, -ry, 0));

							if (shirtModel != null) {
								shirtModel.extraRotation.put("Body_Upper", new Vec3d(recoil, -ry, 0));
								shirtModel.extraRotation.put("Right_Arm_Top", new Vec3d(rx + 75, 0, -20));
								shirtModel.extraRotation.put("Left_Arm_Top", new Vec3d(rx + 75, 0, 30));
								
								shirtModel.extraRotation.put("Right_Hand", new Vec3d(-30, 0, 40));
								
							}
							if (hairModel != null) {
								hairModel.extraRotation.put("Body_Upper", new Vec3d(recoil, -ry, 0));
								hairModel.extraRotation.put("Neck", new Vec3d(rx, 0, -0));
							}
							if (eyes != null) {
								eyes.extraRotation.put("Body_Upper", new Vec3d(recoil, -ry, 0));
								eyes.extraRotation.put("Neck", new Vec3d(rx, 0, -0));
							}
							body.animationController.currentAnimation = IDLE;
						}
					if (((ItemT)item).animation.equals(ItemT.STAFF_ANIMATION))
						if (reload > 0) {
							body.extraRotation.put("Right_Arm_Top", new Vec3d(rx + 90, 0, -20));
							body.extraRotation.put("Right_Hand", new Vec3d(0, 0, 20));
							
							
							body.extraRotation.put("Body_Upper", new Vec3d(0, -ry, 0));
							body.extraRotation.put("Neck", new Vec3d(rx, 0, -0));
							
							if (shirtModel != null) {
								shirtModel.extraRotation.put("Body_Upper", new Vec3d(0, -ry, 0));
								shirtModel.extraRotation.put("Right_Arm_Top", new Vec3d(rx + 90, 0, -20));
								shirtModel.extraRotation.put("Right_Hand", new Vec3d(0, 0, 20));
								
							}
							if (hairModel != null) {
								hairModel.extraRotation.put("Body_Upper", new Vec3d(0, -ry, 0));
								hairModel.extraRotation.put("Neck", new Vec3d(rx, 0, -0));
							}
							if (eyes != null) {
								eyes.extraRotation.put("Body_Upper", new Vec3d(0, -ry, 0));
								eyes.extraRotation.put("Neck", new Vec3d(rx, 0, -0));
							}
							body.animationController.currentAnimation = IDLE;
							
						} else {
							body.extraRotation.remove("Body_Upper");
							
							if (shirtModel != null) {
								shirtModel.extraRotation.remove("Body_Upper");
							}
							if (hairModel != null) {
								hairModel.extraRotation.remove("Body_Upper");
							}
							if (eyes != null) {
								eyes.extraRotation.remove("Body_Upper");
							}
						}
				}
				
			}
		}
		animation_speed *= 0.25f;
		double sneakTranslate = 0.1;

		if (eyes.animationController.nextAnimation != body.animationController.nextAnimation) {
			eyes.animationController.nextAnimation = body.animationController.nextAnimation;
			eyes.animationController.currentAnimation = body.animationController.currentAnimation;
			eyes.animationController.currentAnimation.currentFrame = body.animationController.currentAnimation.currentFrame;
		}
		
		if (hairModel != null) {
			hairModel.animationController.nextAnimation = body.animationController.nextAnimation;
			hairModel.animationController.currentAnimation = body.animationController.currentAnimation;
			hairModel.animationController.currentAnimation.currentFrame = body.animationController.currentAnimation.currentFrame;

			Minecraft.getInstance().getRenderManager().textureManager.bindTexture(hairTexture);
			
			if (entity.isSneaking() && legs.animationController.currentAnimation == SNEAK_IDLE || legs.animationController.currentAnimation == SNEAK_WALK) {
				if (hairModel.animationController.currentAnimation == SNEAK_IDLE || hairModel.animationController.currentAnimation == SNEAK_WALK) {
					hairModel.render(entity, x, y, z, f3, f4, f5);
				} else {
					GlStateManager.translated(0, sneakTranslate, 0);
					hairModel.render(entity, x, y, z, f3, f4, f5);
					GlStateManager.translated(0, -sneakTranslate, 0);
				}
			} else {
				hairModel.render(entity, x, y, z, f3, f4, f5);
			}
		}
				
		if (shirtModel != null) {
			shirtModel.animationController.nextAnimation = body.animationController.nextAnimation;
			shirtModel.animationController.currentAnimation = body.animationController.currentAnimation;
			shirtModel.animationController.currentAnimation.currentFrame = body.animationController.currentAnimation.currentFrame;

			Minecraft.getInstance().getRenderManager().textureManager.bindTexture(shirtTexture);
			if (entity.isSneaking() && legs.animationController.currentAnimation == SNEAK_IDLE || legs.animationController.currentAnimation == SNEAK_WALK) {
				if (shirtModel.animationController.currentAnimation == SNEAK_IDLE || shirtModel.animationController.currentAnimation == SNEAK_WALK) {
					shirtModel.render(entity, x, y, z, f3, f4, f5);
				} else {
					GlStateManager.translated(0, sneakTranslate, 0);
					shirtModel.render(entity, x, y, z, f3, f4, f5);
					GlStateManager.translated(0, -sneakTranslate, 0);
				}
			} else {
				shirtModel.render(entity, x, y, z, f3, f4, f5);
			}
		}
		if (pantsModel != null) {
			pantsModel.animationController.nextAnimation = legs.animationController.nextAnimation;
			pantsModel.animationController.currentAnimation = legs.animationController.currentAnimation;
			pantsModel.animationController.currentAnimation.currentFrame = legs.animationController.currentAnimation.currentFrame;
			Minecraft.getInstance().getRenderManager().textureManager.bindTexture(pantsTexture);
			pantsModel.render(entity, x, y, z, f3, f4, f5);
		}
		
		if (shoesModel != null) {
			shoesModel.animationController.nextAnimation = legs.animationController.nextAnimation;
			shoesModel.animationController.currentAnimation = legs.animationController.currentAnimation;
			shoesModel.animationController.currentAnimation.currentFrame = legs.animationController.currentAnimation.currentFrame;
			
			Minecraft.getInstance().getRenderManager().textureManager.bindTexture(shoesTexture);
			
			shoesModel.render(entity, x, y, z, f3, f4, f5);
		}
		body.animationController.nextAnimation = body.animationController.currentAnimation;
		eyes.animationController.nextAnimation = eyes.animationController.currentAnimation;
		legs.animationController.nextAnimation = legs.animationController.currentAnimation;

		if (body.animationController.currentAnimation != legs.animationController.currentAnimation) {
			body.animationController.update(animation_speed);
			eyes.animationController.update(animation_speed);
			legs.animationController.update(leg_animation_speed * 0.3f);
		} else {
			legs.animationController.update(leg_animation_speed * 0.3f);
		}
		
		eyes.animationController.currentAnimation = body.animationController.currentAnimation;
		eyes.animationController.nextAnimation = eyes.animationController.currentAnimation;
		
		body.animationController.nextAnimation = body.animationController.currentAnimation;
		Minecraft.getInstance().getRenderManager().textureManager.bindTexture(skinTexture);
		if (entity.isSneaking() && legs.animationController.currentAnimation == SNEAK_IDLE || legs.animationController.currentAnimation == SNEAK_WALK) {
			if (body.animationController.currentAnimation == SNEAK_IDLE || body.animationController.currentAnimation == SNEAK_WALK) {
				body.render(entity, x, y, z, f3, f4, f5);
			} else {
				GlStateManager.translated(0, sneakTranslate, 0);
				body.render(entity, x, y, z, f3, f4, f5);
				GlStateManager.translated(0, -sneakTranslate, 0);
			}
		} else {
			body.render(entity, x, y, z, f3, f4, f5);
		}
		
		
		Minecraft.getInstance().getRenderManager().textureManager.bindTexture(skinTexture);
		legs.render(entity, x, y, z, f3, f4, f5);
		
		GlStateManager.pushMatrix();
		GlStateManager.translatef(0.0f, 0.0f, 0.015f);
		Minecraft.getInstance().getRenderManager().textureManager.bindTexture(eyesTexture);
		if (entity.isSneaking() && legs.animationController.currentAnimation == SNEAK_IDLE || legs.animationController.currentAnimation == SNEAK_WALK) {
			if (eyes.animationController.currentAnimation == SNEAK_IDLE || eyes.animationController.currentAnimation == SNEAK_WALK) {
				eyes.render(entity, x, y, z, f3, f4, f5);
			} else {
				GlStateManager.translated(0, sneakTranslate, 0);
				eyes.render(entity, x, y, z, f3, f4, f5);
				GlStateManager.translated(0, -sneakTranslate, 0);
			}
		} else {
			eyes.render(entity, x, y, z, f3, f4, f5);
		}
		GlStateManager.popMatrix();
	}
	public void setRotationAngle(RendererModel modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}