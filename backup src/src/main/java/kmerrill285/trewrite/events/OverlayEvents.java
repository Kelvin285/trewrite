package kmerrill285.trewrite.events;

import org.lwjgl.opengl.GL11;

import kmerrill285.trewrite.core.inventory.InventorySlot;
import kmerrill285.trewrite.core.inventory.InventoryTerraria;
import kmerrill285.trewrite.core.inventory.container.ContainerTerrariaInventory;
import kmerrill285.trewrite.core.inventory.container.GuiContainerTerrariaInventory;
import kmerrill285.trewrite.core.items.ItemStackT;
import kmerrill285.trewrite.entities.models.layers.TerrariaBipedArmorLayer;
import kmerrill285.trewrite.items.Armor;
import kmerrill285.trewrite.items.ItemsT;
import kmerrill285.trewrite.util.Conversions;
import kmerrill285.trewrite.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.FORGE)
public class OverlayEvents {

	public static boolean debug = false;
	
	@SubscribeEvent
	@OnlyIn(value=Dist.CLIENT)
	public static void handlePlayerRenderEvent(RenderPlayerEvent event) {
		
		
		
	}
	
	
	static boolean addedLayer = false;
	@SubscribeEvent
	@OnlyIn(value=Dist.CLIENT)
	public static void handlePrePlayerRenderEvent(RenderPlayerEvent.Pre event) {
		//      this.addLayer(new BipedArmorLayer<>(this, new BipedModel(0.5F), new BipedModel(1.0F)));
		if (!addedLayer) {
			event.getRenderer().addLayer(new TerrariaBipedArmorLayer<>(event.getRenderer(), new BipedModel(0.5f), new BipedModel(1.0f)));
			addedLayer = true;
		}
		
	}
	
	@SubscribeEvent
	@OnlyIn(value=Dist.CLIENT)
	public static void handlePostPlayerRenderEvent(RenderPlayerEvent.Post event) {
		}
	
	@SubscribeEvent
	@OnlyIn(value=Dist.CLIENT)
	public static void handleOverlayEvent(RenderGameOverlayEvent event) {
		boolean copper_watch = false;
		boolean silver_watch = false;
		boolean gold_watch = false;
		boolean depth_meter = false;		
		
		if (ContainerTerrariaInventory.inventory != null) {
			InventoryTerraria inventory = ContainerTerrariaInventory.inventory;
			for (InventorySlot slot : inventory.accessory) {
				ItemStackT stack = slot.stack;
				if (stack == null) {
					continue;
				}
				if (stack.item == null) {
					continue;
				}
				if (stack.item == ItemsT.COPPER_WATCH) {
					copper_watch = true;
				}
				if (stack.item == ItemsT.SILVER_WATCH) {
					silver_watch = true;
				}
				if (stack.item == ItemsT.GOLD_WATCH) {
					gold_watch = true;
				}
				if (stack.item == ItemsT.DEPTH_METER) {
					depth_meter = true;
				}
			}
		}
		
		long dayTime = Minecraft.getInstance().getRenderViewEntity().getEntityWorld().getDayTime() + 6000;
		if (Minecraft.getInstance().player != null) {
			Minecraft.getInstance().player.getFoodStats().setFoodSaturationLevel(5.0f);
		}
		
		if (gold_watch != false) {
			long fullday = (dayTime % 24000);
			long day = (fullday / 1000) % 12;
			long hour = dayTime % 1000;
			
			String t = ":00 ";
			
			long minute = (int)Math.round(hour / 16.67f);
			
			if (minute >= 60) {
				minute -= 60;
				day++;
				day %= 12;
			}
			
			if (minute < 10) t = ":0"+minute+" ";
			else
				t = ":"+minute+" ";
			
    		String AMPM = "AM";
    		if (fullday >= 12000)
    			AMPM = "PM";
    		if (day == 0) day = 12;
    		Util.watchTime = day + t + AMPM;
		}else
		if (silver_watch != false) {
			long fullday = (dayTime % 24000);
			long day = (fullday / 1000) % 12;
			long hour = dayTime % 1000;
			
			String t = ":00 ";
			if (hour > 500) t = ":30 ";
			
    		String AMPM = "AM";
    		if (fullday >= 12000)
    			AMPM = "PM";
    		if (day == 0) day = 12;
    		Util.watchTime = day + t + AMPM;
		} else {
			if (copper_watch != false) {
				
				long fullday = (dayTime % 24000);
				long day = (fullday / 1000) % 12;
	    		String AMPM = "AM";
	    		if (fullday >= 12000)
	    			AMPM = "PM";
	    		if (day == 0) day = 12;
	    		Util.watchTime = day + ":00 " + AMPM;
			}
		}
		int accessory = 0;
		int textSize = 10;
		
		if (event.getType() == ElementType.HOTBAR) {
			if (Util.terrariaInventory == true) {
				Minecraft instance = Minecraft.getInstance();
		        instance.getTextureManager().bindTexture(GuiContainerTerrariaInventory.INVENTORY_BACKGROUND);
		        //23, 168 = hotbar deselected
		        //46, 168 = hotbar selected
		        
		        int scaledWidth, scaledHeight;
				scaledWidth = instance.mainWindow.getScaledWidth();
			    scaledHeight = instance.mainWindow.getScaledHeight();
				
			    int xx = scaledWidth / 2 - 91 - (20);
			    int yy = scaledHeight - 39 + 16;
			    InventoryTerraria inventory = ContainerTerrariaInventory.inventory;
			    for (int i = 0; i < 10; i++) {
			    	if (inventory.hotbarSelected == i) {
			    		instance.getTextureManager().bindTexture(GuiContainerTerrariaInventory.INVENTORY_BACKGROUND);
			    		GuiContainerTerrariaInventory.drawTexturedRectangle(xx + i * 22, yy, 46, 168, 22, 22);
			    	} else {
			    		instance.getTextureManager().bindTexture(GuiContainerTerrariaInventory.INVENTORY_BACKGROUND);
			    		GuiContainerTerrariaInventory.drawTexturedRectangle(xx + i * 22, yy, 23, 168, 22, 22);
			    	}
			    	if (inventory.hotbar[i].stack != null)
			    		GuiContainerTerrariaInventory.renderItemIntoGUI(inventory.hotbar[i].stack, xx + i * 22 + 2, yy + 3);
			    	
			    }
//		        if (Minecraft.getInstance().getRenderViewEntity() instanceof PlayerEntity) {
//		        	PlayerEntity player = (PlayerEntity)Minecraft.getInstance().getRenderViewEntity();
////		        		player.setHeldItem(EnumHand.OFF_HAND, new ItemStack(Items.AIR));
//		        	if (inventory.hotbar[inventory.hotbarSelected].stack != null)
//			        	player.inventory.mainInventory.set(player.inventory.currentItem, new ItemStack(inventory.hotbar[inventory.hotbarSelected].stack.itemForRender.getItem(), 1));
//		        	else
//			        	player.inventory.mainInventory.set(player.inventory.currentItem, new ItemStack(Items.AIR, 1));
//		        }
				event.setCanceled(true);
			}
		}
		
//		instance.renderEngine.bindTexture(new ResourceLocation("snapshot", "textures/gui/lunchbox.png"));
//		drawTexturedModalRect(xPos, yPos, 0, 0, 32, 32);
		if (event.getType() == ElementType.EXPERIENCE) {
			int i = 0;
			Minecraft instance = Minecraft.getInstance();
			if (Util.watchTime != "") {
				instance.ingameGUI.drawString(instance.fontRenderer, ""+Util.watchTime, 5, 10 + i * textSize, 0xFFFFFF);
				i++;
			}
			if (depth_meter) {
				instance.ingameGUI.drawString(instance.fontRenderer, "Level "+(int)Minecraft.getInstance().getRenderViewEntity().posY + "." + (int)((Minecraft.getInstance().getRenderViewEntity().posY * 10) % 10), 5, 10 + i * textSize, 0xFFFFFF);
				i++;
			}
			accessory = i;
		}
		Util.watchTime = "";
		if (event.getType() == ElementType.EXPERIENCE) {
			Minecraft instance = Minecraft.getInstance();
			
			int scaledWidth, scaledHeight;
			scaledWidth = instance.mainWindow.getScaledWidth();
		    scaledHeight = instance.mainWindow.getScaledHeight();
			
		    int xx = scaledWidth / 2 - 91;
		    int yy = 10;
		    		    
		    if (instance.getRenderViewEntity() instanceof PlayerEntity) {
		    	GL11.glColor4f(1F, 1F, 1F, 1F);
				GL11.glDisable(GL11.GL_LIGHTING);
				
				GL11.glEnable (GL11.GL_BLEND); 
				GL11.glBlendFunc (GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA); 
				
				instance.getTextureManager().bindTexture(new ResourceLocation("trewrite", "textures/gui/icons.png"));
				
				instance.ingameGUI.blit(Conversions.toScreenX(xx), Conversions.toScreenY(yy), 9 * 1, 9 * 2, 9, 9);
				
				instance.ingameGUI.blit(Conversions.toScreenX(xx + 10 * 5), Conversions.toScreenY(yy), 9 * 2, 9 * 2, 9, 9);
				
				instance.ingameGUI.blit(Conversions.toScreenX(xx + 10 * 10), Conversions.toScreenY(yy), 9 * 3, 9 * 2, 9, 9);

				instance.ingameGUI.blit(Conversions.toScreenX(xx + 10 * 15), Conversions.toScreenY(yy), 9 * 4, 9 * 2, 9, 9);

				
				PlayerEntity player = (PlayerEntity)instance.getRenderViewEntity();
				int coins = (int) Util.renderCoins;
				
				int copper = coins % 100;
				int silver = (coins / 100) % 100;
				int gold = (coins / 10000) % 100;
				int platinum = (coins / 1000000) % 100;
				
				instance.ingameGUI.drawString(instance.fontRenderer, ""+copper, xx + 10, yy, 0xFFFFFF);
				instance.ingameGUI.drawString(instance.fontRenderer, ""+silver, xx + 10 + 10 * 5, yy, 0xFFFFFF);
				instance.ingameGUI.drawString(instance.fontRenderer, ""+gold, xx + 10 + 10 * 10, yy, 0xFFFFFF);
				instance.ingameGUI.drawString(instance.fontRenderer, ""+platinum, xx + 10 + 10 * 15, yy, 0xFFFFFF);
				
					
				//RENDER BUFFS / DEBUFFS
				int debuff = 0;
				if (Util.renderPotionSickness > 0) {
					instance.getTextureManager().bindTexture(new ResourceLocation("trewrite", "textures/gui/icons.png"));
					instance.ingameGUI.blit(Conversions.toScreenX(5), Conversions.toScreenY(12 * 2 + 12 * debuff) + accessory * textSize, 12 * 0, 27 + 12 * 0, 12, 12); //debuff background
					instance.ingameGUI.blit(Conversions.toScreenX(5), Conversions.toScreenY(12 * 2 + 12 * debuff) + accessory * textSize, 12 * 0, 27 + 12 * 1, 12, 12); //debuff image
					instance.ingameGUI.drawString(instance.fontRenderer, "Potion Sickness ["+Util.getTimerString(Util.renderPotionSickness)+"]", 5 + 12 + 3, 12 * 2 + 12 * debuff + 3 + accessory * textSize, 0xFFFFFF);
					debuff++;
				}
				
				if (Util.renderManaSickness > 0) {
					instance.getTextureManager().bindTexture(new ResourceLocation("trewrite", "textures/gui/icons.png"));
					instance.ingameGUI.blit(Conversions.toScreenX(5), Conversions.toScreenY(12 * 2 + 12 * debuff) + accessory * textSize, 12 * 1, 27 + 12 * 0, 12, 12); //debuff background
					instance.ingameGUI.blit(Conversions.toScreenX(5), Conversions.toScreenY(12 * 2 + 12 * debuff) + accessory * textSize, 12 * 1, 27 + 12 * 1, 12, 12); //debuff image
					instance.ingameGUI.drawString(instance.fontRenderer, "Mana Sickness x"+Util.renderManaSicknessEffect+" ["+Util.getTimerString(Util.renderManaSickness)+"]", 5 + 12 + 3, 12 * 2 + 12 * debuff + 3 + accessory * textSize, 0xFFFFFF);
					debuff++;
				}
				
				GL11.glColor4f(1F, 1F, 1F, 1F);
				instance.getTextureManager().bindTexture(new ResourceLocation("minecraft", "textures/gui/icons.png"));
				
				
		    }
			//event.setCanceled(true);
		}
		
		if (event.getType() == ElementType.FOOD) {
			event.setCanceled(true);
			Minecraft instance = Minecraft.getInstance();
			
			int scaledWidth, scaledHeight;
			scaledWidth = instance.mainWindow.getScaledWidth();
		    scaledHeight = instance.mainWindow.getScaledHeight();
			
		    int xx = scaledWidth / 2 - 91 + 10 * 10;
		    int yy = scaledHeight - 39 - 10;
		    
		    if (instance.getRenderViewEntity() instanceof PlayerEntity) {
				PlayerEntity player = (PlayerEntity)instance.getRenderViewEntity();
				int xPos = 0;
				int yPos = 0;
				GL11.glColor4f(1F, 1F, 1F, 1F);
				GL11.glDisable(GL11.GL_LIGHTING);
				
				GL11.glEnable (GL11.GL_BLEND); 
				GL11.glBlendFunc (GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA); 
				
				instance.getTextureManager().bindTexture(new ResourceLocation("trewrite", "textures/gui/icons.png"));
				//healthbar
				
				int mana = ScoreboardEvents.getMana(player);
				int max_mana = ScoreboardEvents.getMaxMana(player);
				
				int len = (int)(mana / 20);
				
				int len2 = (int)(max_mana / 20);
				
				boolean half = (int)mana % 20 <= 19 && (int)mana % 20 > 0;
				
				boolean shake = false;
				
				int X = xx;
				int Y = yy+10;
				
				
				
				for (int i = 0; i < len2; i++) {
					if (i <= 10) {
						instance.ingameGUI.blit(Conversions.toScreenX(X + i * 10), Conversions.toScreenY(Y), 9 * 0, 9 * 1, 9, 9);
					} else {
						instance.ingameGUI.blit(Conversions.toScreenX(X + (i - 10) * 10), Conversions.toScreenY(Y - 10), 9 * 0, 9 * 1, 9, 9);
					}
				}
				
				
				for (int i = 0; i < len; i++) {
					if (i <= 10) {
						instance.ingameGUI.blit(Conversions.toScreenX(X + i * 10), Conversions.toScreenY(Y + addShake(shake)), 9 * 4, 9 * 1, 9, 9);
					} else {
						instance.ingameGUI.blit(Conversions.toScreenX(X + (i - 10) * 10), Conversions.toScreenY(Y - 10 + addShake(shake)), 9 * 4, 9 * 1, 9, 9);
					}
				}
				if (half == true) {
					GL11.glColor4f(1F, 1F, 1F, ((int)mana % 20) / 20.0f);
					int i = len;
					if (i <= 10) {
						instance.ingameGUI.blit(Conversions.toScreenX(X + i * 10), Conversions.toScreenY(Y + addShake(shake)), 9 * 4, 9 * 1, 9, 9);
					} else {
						instance.ingameGUI.blit(Conversions.toScreenX(X + (i - 10) * 10), Conversions.toScreenY(Y - 10 + addShake(shake)), 9 * 4, 9 * 1, 9, 9);
					}
				}
				GL11.glColor4f(1F, 1F, 1F, 1F);
				instance.getTextureManager().bindTexture(new ResourceLocation("minecraft", "textures/gui/icons.png"));
			}
		    
		}
		
		if (event.getType() == ElementType.ARMOR) {
			Minecraft instance = Minecraft.getInstance();
			
			int scaledWidth, scaledHeight;
			scaledWidth = instance.mainWindow.getScaledWidth();
		    scaledHeight = instance.mainWindow.getScaledHeight();
			
		    int xx = scaledWidth / 2 - 91 + 10 * 18 + 9;
		    int yy = scaledHeight - 39 + 25;
		    if (Util.terrariaInventory) xx += (22 / 2); 
		    GL11.glColor4f(1F, 1F, 1F, 1F);
			GL11.glDisable(GL11.GL_LIGHTING);
			
			GL11.glEnable (GL11.GL_BLEND); 
			GL11.glBlendFunc (GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA); 
			
			instance.getTextureManager().bindTexture(new ResourceLocation("trewrite", "textures/gui/icons.png"));
		    
			//draw armor sprite
			instance.ingameGUI.blit(Conversions.toScreenX(xx), Conversions.toScreenY(yy), 9 * 0, 9 * 2, 9, 9);
			
			if (instance.getRenderViewEntity() instanceof PlayerEntity) {
				PlayerEntity player = (PlayerEntity)instance.getRenderViewEntity();
				int armor = player.getTotalArmorValue();
				
				InventoryTerraria inventory = ContainerTerrariaInventory.inventory;
				if (inventory != null) {

					for (int i = 0; i < 3; i++) {
						if (inventory.armor[i].stack != null) {
							if (inventory.armor[i].stack.item instanceof Armor) {
								Armor a = (Armor)inventory.armor[i].stack.item;
								armor += a.defense;
							}
						}
					}
				}
				
				
				instance.ingameGUI.drawString(instance.fontRenderer, ""+armor, xx + 10, yy, 0xFFFFFF);
			}
			
			GL11.glColor4f(1F, 1F, 1F, 1F);
			instance.getTextureManager().bindTexture(new ResourceLocation("minecraft", "textures/gui/icons.png"));
			event.setCanceled(true);
		}
		
		if (event.getType() == ElementType.HEALTH) {
			
			Minecraft instance = Minecraft.getInstance();
			
			int scaledWidth, scaledHeight;
			scaledWidth = instance.mainWindow.getScaledWidth();
		    scaledHeight = instance.mainWindow.getScaledHeight();
			
		    int xx = scaledWidth / 2 - 91;
		    int yy = scaledHeight - 39;
		    
			if (instance.getRenderViewEntity() instanceof PlayerEntity) {
				PlayerEntity player = (PlayerEntity)instance.getRenderViewEntity();
				int xPos = 0;
				int yPos = 0;
				GL11.glColor4f(1F, 1F, 1F, 1F);
				GL11.glDisable(GL11.GL_LIGHTING);
				
				GL11.glEnable (GL11.GL_BLEND); 
				GL11.glBlendFunc (GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA); 
				
				instance.getTextureManager().bindTexture(new ResourceLocation("trewrite", "textures/gui/icons.png"));
				//healthbar
				
				
				
				int len = (int)(player.getHealth() / 20);
				
				int len2 = (int)(player.getMaxHealth() / 20);
				
				boolean half = (int)player.getHealth() % 20 <= 19 && (int)player.getHealth() % 20 > 0;
				
				boolean shake = player.getHealth() <= 40;
				
				int X = xx;
				int Y = yy;
				
				
				
				for (int i = 0; i < len2; i++) {
					
					if (i < 10) {
						instance.ingameGUI.blit(Conversions.toScreenX(X + i * 10), Conversions.toScreenY(Y), 9 * 0, 9 * 0, 9, 9);
					} else {
						instance.ingameGUI.blit(Conversions.toScreenX(X + (i - 10) * 10), Conversions.toScreenY(Y - 10), 9 * 0, 9 * 0, 9, 9);
					}
				}
				
				
				for (int i = 0; i < len; i++) {
					if (i < 10) {
						instance.ingameGUI.blit(Conversions.toScreenX(X + i * 10), Conversions.toScreenY(Y + addShake(shake)), 9 * 4, 9 * 0, 9, 9);
					} else {
						instance.ingameGUI.blit(Conversions.toScreenX(X + (i - 10) * 10), Conversions.toScreenY(Y - 10 + addShake(shake)), 9 * 4, 9 * 0, 9, 9);
					}
				}
				if (half == true) {
					GL11.glColor4f(1F, 1F, 1F, ((int)player.getHealth() % 20) / 20.0f);
					int i = len;
					if (i < 10) {
						instance.ingameGUI.blit((int)Conversions.toScreenX(X + i * 10), (int)Conversions.toScreenY(Y + addShake(shake)), 9 * 4, 9 * 0, 9, 9);
					} else {
						instance.ingameGUI.blit((int)Conversions.toScreenX(X + (i - 10) * 10), (int)Conversions.toScreenY(Y - 10 + addShake(shake)), 9 * 4, 9 * 0, 9, 9);
					}
				}
				GL11.glColor4f(1F, 1F, 1F, 1F);
				instance.getTextureManager().bindTexture(new ResourceLocation("minecraft", "textures/gui/icons.png"));
			}
			
			
			event.setCanceled(true);
		}
		
	}
	
	private static int addShake (boolean shake) {
		return 0;
	}
	
	
	
	
}
