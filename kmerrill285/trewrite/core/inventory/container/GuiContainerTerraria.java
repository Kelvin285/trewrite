/*
 * Copyright (C) 2018-2019  C4
 *
 * This file is part of Curios, a mod made for Minecraft.
 *
 * Curios is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Curios is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Curios.  If not, see <https://www.gnu.org/licenses/>.
 */

package kmerrill285.trewrite.core.inventory.container;

import java.util.ArrayList;

import kmerrill285.trewrite.Trewrite;
import kmerrill285.trewrite.blocks.BlockT;
import kmerrill285.trewrite.core.client.KeyRegistry;
import kmerrill285.trewrite.core.inventory.InventorySlot;
import kmerrill285.trewrite.core.inventory.InventorySlot.ItemType;
import kmerrill285.trewrite.core.inventory.InventoryTerraria;
import kmerrill285.trewrite.core.items.ItemStackT;
import kmerrill285.trewrite.core.network.NetworkHandler;
import kmerrill285.trewrite.core.network.client.CPacketSyncInventoryTerraria;
import kmerrill285.trewrite.core.network.client.CPacketThrowItemTerraria;
import kmerrill285.trewrite.crafting.CraftingRecipe;
import kmerrill285.trewrite.crafting.Recipes;
import kmerrill285.trewrite.entities.EntityItemT;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GuiContainerTerraria extends InventoryEffectRenderer {
	
    public float oldMouseX;
    public float oldMouseY;
    
    private boolean buttonClicked;
    
    public InventorySlot selectedSlot = null;
    public InventorySlot trashSlot = null;

    public int WIDTH = 256, HEIGHT = 164;
    
    private int[] TOP_ARMOR = {8, 8}, TOP_ARMOR_VANITY = {80, 8}, TRASHCAN = {8, 66}, INVENTORY_TOPLEFT = {8, 84}, HOTBAR_LEFT = {8, 142}, 
    		ACCESSORY_TOPLEFT = {197, 71}, CRAFTING_TOPLEFT = {120, 8}, ICONS = {69, 168};
    
    private int accessory = 0, trash = 1, dye = 2, vanity = 3, helmet = 4, chestplate = 5, leggings = 6, hat = 7, shirt = 8, pants = 9;
    
    public  ArrayList<CraftingRecipe> recipes = new ArrayList<CraftingRecipe>();
    public  ArrayList<CraftingRecipe> availableRecipes = new ArrayList<CraftingRecipe>();
    public CraftingRecipe selectedRecipe = null;
    public int page = 0;
    public boolean upSelected = false, downSelected = false;
    
    public GuiContainerTerraria(ContainerTerrariaInventory container, float oldMouseX, float oldMouseY) {
        super(container);
        this.allowUserInput = true;
        this.oldMouseX = oldMouseX;
        this.oldMouseY = oldMouseY;
        InventoryTerraria inventory = ContainerTerrariaInventory.inventory;
        
        inventory.holdingSlot = new InventorySlot(ItemType.ANY, 0, 0, 0, 0);
        trashSlot = inventory.trash;
        this.resetRecipes();
    }

    public  void resetRecipes() {
    	
    	ArrayList<BlockT> blocks = new ArrayList<BlockT>();
    	
    	EntityPlayer player = Minecraft.getInstance().player;
    	if (player != null) {
    		if (player.world != null) {
    			BlockPos playerpos = player.getPosition();
    			for (int x = -2; x < 3; x++) {
    				for (int y = -2; y < 3; y++) {
    					for (int z = -2; z < 3; z++) {
    						BlockPos pos2 = new BlockPos(playerpos.getX() + x, playerpos.getY() + y, playerpos.getZ() + z);
    						if (player.world.isBlockLoaded(pos2)) {
    							if (player.world.getBlockState(pos2).getBlock() instanceof BlockT) {
    								BlockT block = (BlockT)player.world.getBlockState(pos2).getBlock();
    								if (Recipes.recipes.containsKey(block)) {
    									System.out.println("HAS BLOCK");
    									blocks.add(block);
    								}
    							}
    						}
    					}
    				}
    			}
    		}
    	}
    	
    	availableRecipes.clear();
    	recipes.clear();
        recipes.addAll(Recipes.getRecipesForBlock(null)); //load all crafting recipes that don't require a crafting station
        for (BlockT block : blocks) {
        	recipes.addAll(Recipes.getRecipesForBlock(block));
        	System.out.println("Found recipes for station: " + block.name);
        }
        System.out.println("recipe size: " + recipes.size());
        for (CraftingRecipe recipe : recipes) {
        	boolean add = true;
        	System.out.println("checking items for recipe");
        	for (ItemStackT stack : recipe.input) {
        		if (hasItems(stack) == false) {
        			add = false;
        			System.out.println("you don't have " + stack.item.itemName + " for " + recipe.output.item.itemName);
        			break;
        		}
        	}
        	if (add == true) {
        		System.out.println("recipe added for " + recipe.output.item.itemName);
        		availableRecipes.add(recipe);
        		}
        }
    }
    
    public boolean hasItems(ItemStackT item) {
    	System.out.println("test has item: " + item.item.itemName);
    	boolean hasItem = false;
    	int stack = 0;
    	InventoryTerraria inventory = ContainerTerrariaInventory.inventory;
    	if (inventory == null) return false;
    	for (int x = 0; x < 30; x++) {
    		if (x < 10)
    		if (inventory.hotbar[x].stack != null) {
    			if (inventory.hotbar[x].stack.item.itemName.contentEquals(item.item.itemName)) {
    				System.out.println("has item");
    				hasItem = true;
    				stack += inventory.hotbar[x].stack.size;
    				}
    		}
    		
    		if (inventory.main[x].stack != null) {
    			if (inventory.main[x].stack.item.itemName.contentEquals(item.item.itemName)) {
    				System.out.println("has item");
    				hasItem = true;
    				stack += inventory.main[x].stack.size;
    				}
    		}
    		System.out.println("stack: " + stack);
    		if (hasItem == true && stack >= item.size) {
    			System.out.println("WE HAVE THE ITEM");
    			return true;
    			}
    	}
    	return false;
    }
    
    @Override
    public void initGui() {
        super.initGui();
        this.guiLeft = (int)((this.width - this.xSize) * 0.35);
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.hasActivePotionEffects = false;
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
        oldMouseX = (float)mouseX;
        oldMouseY = (float)mouseY;
    }
    
    public void renderHoveredToolTip(int p_191948_1_, int p_191948_2_) {
    	InventoryTerraria inventory = ContainerTerrariaInventory.inventory;
        if (inventory.holdingSlot.stack == null && this.selectedSlot != null && this.selectedSlot.stack != null) {
           this.renderToolTip(this.selectedSlot.stack.itemForRender, p_191948_1_, p_191948_2_);
        }
        
        if (this.selectedRecipe != null) {
        	if (this.selectedRecipe.output != null)
        	this.renderToolTip(this.selectedRecipe.output.itemForRender, p_191948_1_, p_191948_2_);
        }

     }

    @Override
    public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {

        if (KeyRegistry.openInventory.isActiveAndMatches(InputMappings.getInputByCode(p_keyPressed_1_, p_keyPressed_2_))) {
            this.mc.player.closeScreen();
            return true;
        } else {
            return super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        //this.fontRenderer.drawString(I18n.format("container.crafting"), 97, 8, 4210752);
    	
        
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public static ResourceLocation INVENTORY_BACKGROUND = new ResourceLocation("trewrite", "textures/gui/inventory.png");
    /**
     * Draws the background layer of this container (behind the item).
     */
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
    	
    	
    	selectedSlot = null;
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(GuiContainerTerraria.INVENTORY_BACKGROUND);
        int i = this.guiLeft;
        int j = this.guiTop;
        this.drawTexturedModalRect(i, j, 0, 0, this.WIDTH, this.HEIGHT);
        this.drawTexturedModalRect(i + TOP_ARMOR[0], j + TOP_ARMOR[1], ICONS[0] + helmet * 17, ICONS[1], 16, 16);
        this.drawTexturedModalRect(i + TOP_ARMOR[0], j + TOP_ARMOR[1] + 16 + 2, ICONS[0] + chestplate * 17, ICONS[1], 16, 16);
        this.drawTexturedModalRect(i + TOP_ARMOR[0], j + TOP_ARMOR[1] + 16 * 2 + 4, ICONS[0] + leggings * 17, ICONS[1], 16, 16);
        
        this.drawTexturedModalRect(i + TOP_ARMOR_VANITY[0], j + TOP_ARMOR_VANITY[1], ICONS[0] + hat * 17, ICONS[1], 16, 16);
        this.drawTexturedModalRect(i + TOP_ARMOR_VANITY[0], j + TOP_ARMOR_VANITY[1] + 16 + 2, ICONS[0] + shirt * 17, ICONS[1], 16, 16);
        this.drawTexturedModalRect(i + TOP_ARMOR_VANITY[0], j + TOP_ARMOR_VANITY[1] + 16 * 2 + 4, ICONS[0] + pants * 17, ICONS[1], 16, 16);
        
        for (int h = 0; h < 3; h++) {
            this.drawTexturedModalRect(i + TOP_ARMOR_VANITY[0] + 16 + 2, j + TOP_ARMOR_VANITY[1] + h * 16 + h * 2, ICONS[0] + dye * 17, ICONS[1], 16, 16);
        }
        
        
        this.drawTexturedModalRect(i + TRASHCAN[0], j + TRASHCAN[1], ICONS[0] + trash * 17, ICONS[1], 16, 16);
        
        for (int h = 0; h < 5; h++) {
        	this.drawTexturedModalRect(i + ACCESSORY_TOPLEFT[0] + 16 * 2 + 4, j + ACCESSORY_TOPLEFT[1] + h * 16 + h * 2, ICONS[0] + accessory * 17, ICONS[1], 16, 16);
        	this.drawTexturedModalRect(i + ACCESSORY_TOPLEFT[0] + 16 + 2, j + ACCESSORY_TOPLEFT[1] + h * 16 + h * 2, ICONS[0] + vanity * 17, ICONS[1], 16, 16);
        	this.drawTexturedModalRect(i + ACCESSORY_TOPLEFT[0], j + ACCESSORY_TOPLEFT[1] + h * 16 + h * 2, ICONS[0] + dye * 17, ICONS[1], 16, 16);
        }
        GuiInventory.drawEntityOnScreen(i + 51, j + 75, 30, (float)(i + 51) - mouseX,
                (float)(j + 75 - 50) - mouseY, this.mc.player);
        
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(GuiContainerTerraria.INVENTORY_BACKGROUND);
        
        //23, 191
        InventoryTerraria inventory = ContainerTerrariaInventory.inventory;
        for (int m = 0; m < InventoryTerraria.MAIN_SLOTS; m++) {
        	if (inventory.main[m].stack != null) {
        		GuiContainerTerraria.renderItemIntoGUI(inventory.main[m].stack, i + inventory.main[m].x, j + inventory.main[m].y);
    		}
        	if (mouseInRect(inventory.main[m].x + i, inventory.main[m].y + j, 16, 16, mouseX, mouseY)) {
        		selectedSlot = inventory.main[m];
        		this.mc.getTextureManager().bindTexture(GuiContainerTerraria.INVENTORY_BACKGROUND);
                GlStateManager.color4f(1.0F, 1.0F, 1.0F, 0.75F);
        		this.drawTexturedRectangle(inventory.main[m].x + i, inventory.main[m].y + j, 23, 191, 16, 16);
        	}
        }
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        for (int m = 0; m < InventoryTerraria.HOTBAR_SLOTS; m++) {
        	if (inventory.hotbar[m].stack != null) {
        		GuiContainerTerraria.renderItemIntoGUI(inventory.hotbar[m].stack, i + inventory.hotbar[m].x, j + inventory.hotbar[m].y);
    		}
        	if (mouseInRect(inventory.hotbar[m].x + i, inventory.hotbar[m].y + j, 16, 16, mouseX, mouseY)) {
        		selectedSlot = inventory.hotbar[m];
        		this.mc.getTextureManager().bindTexture(GuiContainerTerraria.INVENTORY_BACKGROUND);
                GlStateManager.color4f(1.0F, 1.0F, 1.0F, 0.75F);
        		this.drawTexturedRectangle(inventory.hotbar[m].x + i, inventory.hotbar[m].y + j, 23, 191, 16, 16);
        	}
        }
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        for (int m = 0; m < InventoryTerraria.ARMOR_SLOTS; m++) {
        	if (inventory.armor[m].stack != null) {
        		GuiContainerTerraria.renderItemIntoGUI(inventory.armor[m].stack, i + inventory.armor[m].x, j + inventory.armor[m].y);
    		}
        	if (inventory.armorVanity[m].stack != null) {
        		GuiContainerTerraria.renderItemIntoGUI(inventory.armorVanity[m].stack, i + inventory.armorVanity[m].x, j + inventory.armorVanity[m].y);
    		}
        	if (inventory.armorDyes[m].stack != null) {
        		GuiContainerTerraria.renderItemIntoGUI(inventory.armorDyes[m].stack, i + inventory.armorDyes[m].x, j + inventory.armorDyes[m].y);
    		}
        	if (mouseInRect(inventory.armor[m].x + i, inventory.armor[m].y + j, 16, 16, mouseX, mouseY)) {
        		selectedSlot = inventory.armor[m];
        		this.mc.getTextureManager().bindTexture(GuiContainerTerraria.INVENTORY_BACKGROUND);
                GlStateManager.color4f(1.0F, 1.0F, 1.0F, 0.75F);
        		this.drawTexturedRectangle(inventory.armor[m].x + i, inventory.armor[m].y + j, 23, 191, 16, 16);
        	}
        	if (mouseInRect(inventory.armorVanity[m].x + i, inventory.armorVanity[m].y + j, 16, 16, mouseX, mouseY)) {
        		selectedSlot = inventory.armorVanity[m];
        		this.mc.getTextureManager().bindTexture(GuiContainerTerraria.INVENTORY_BACKGROUND);
                GlStateManager.color4f(1.0F, 1.0F, 1.0F, 0.75F);
        		this.drawTexturedRectangle(inventory.armorVanity[m].x + i, inventory.armorVanity[m].y + j, 23, 191, 16, 16);
        	}
        	if (mouseInRect(inventory.armorDyes[m].x + i, inventory.armorDyes[m].y + j, 16, 16, mouseX, mouseY)) {
        		selectedSlot = inventory.armorDyes[m];
        		this.mc.getTextureManager().bindTexture(GuiContainerTerraria.INVENTORY_BACKGROUND);
                GlStateManager.color4f(1.0F, 1.0F, 1.0F, 0.75F);
        		this.drawTexturedRectangle(inventory.armorDyes[m].x + i, inventory.armorDyes[m].y + j, 23, 191, 16, 16);
        	}
        }
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        for (int m = 0; m < InventoryTerraria.ACCESSORY_SLOTS; m++) {
        	if (inventory.accessory[m].stack != null) {
        		GuiContainerTerraria.renderItemIntoGUI(inventory.accessory[m].stack, i + inventory.accessory[m].x, j + inventory.accessory[m].y);
    		}
        	if (inventory.accessoryVanity[m].stack != null) {
        		GuiContainerTerraria.renderItemIntoGUI(inventory.accessoryVanity[m].stack, i + inventory.accessoryVanity[m].x, j + inventory.accessoryVanity[m].y);
    		}
        	if (inventory.accessoryDyes[m].stack != null) {
        		GuiContainerTerraria.renderItemIntoGUI(inventory.accessoryDyes[m].stack, i + inventory.accessoryDyes[m].x, j + inventory.accessoryDyes[m].y);
    		}
        	if (mouseInRect(inventory.accessory[m].x + i, inventory.accessory[m].y + j, 16, 16, mouseX, mouseY)) {
        		selectedSlot = inventory.accessory[m];
        		this.mc.getTextureManager().bindTexture(GuiContainerTerraria.INVENTORY_BACKGROUND);
                GlStateManager.color4f(1.0F, 1.0F, 1.0F, 0.75F);
        		this.drawTexturedRectangle(inventory.accessory[m].x + i, inventory.accessory[m].y + j, 23, 191, 16, 16);
        	}
        	if (mouseInRect(inventory.accessoryVanity[m].x + i, inventory.accessoryVanity[m].y + j, 16, 16, mouseX, mouseY)) {
        		selectedSlot = inventory.accessoryVanity[m];
        		this.mc.getTextureManager().bindTexture(GuiContainerTerraria.INVENTORY_BACKGROUND);
                GlStateManager.color4f(1.0F, 1.0F, 1.0F, 0.75F);
        		this.drawTexturedRectangle(inventory.accessoryVanity[m].x + i, inventory.accessoryVanity[m].y + j, 23, 191, 16, 16);
        	}
        	if (mouseInRect(inventory.accessoryDyes[m].x + i, inventory.accessoryDyes[m].y + j, 16, 16, mouseX, mouseY)) {
        		selectedSlot = inventory.accessoryDyes[m];
        		this.mc.getTextureManager().bindTexture(GuiContainerTerraria.INVENTORY_BACKGROUND);
                GlStateManager.color4f(1.0F, 1.0F, 1.0F, 0.75F);
        		this.drawTexturedRectangle(inventory.accessoryDyes[m].x + i, inventory.accessoryDyes[m].y + j, 23, 191, 16, 16);
        	}
        }
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        if (inventory.trash.stack != null) {
			GuiContainerTerraria.renderItemIntoGUI(inventory.trash.stack, i + inventory.trash.x, j + inventory.trash.y);
		}
        if (mouseInRect(inventory.trash.x + i, inventory.trash.y + j, 16, 16, mouseX, mouseY)) {
        	selectedSlot = inventory.trash;
        	this.mc.getTextureManager().bindTexture(GuiContainerTerraria.INVENTORY_BACKGROUND);
            GlStateManager.color4f(1.0F, 1.0F, 1.0F, 0.75F);
    		this.drawTexturedRectangle(inventory.trash.x + i, inventory.trash.y + j, 23, 191, 16, 16);
    	}
        //inventory start: 7, 83.  Slot size: 18 x 18, Recipe start: 128, 15.  Gap: 1px, Max rows: 2
        //up arrow: 40, 191
        //down arrow: 40, 196
        //arrow size: 7x4
        
        //top arrow location in gui: 241, 9
        //bottom arrow location in gui: 241, 54
        
        {
        	int rx = i + 128;
        	int ry = j + 15;
        	int width = 18;
        	int height = 18;
        	int gap = 1;
        	int row = 6;
        	this.mc.getTextureManager().bindTexture(GuiContainerTerraria.INVENTORY_BACKGROUND);
            GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        	selectedRecipe = null;
            int r = page * 12;
            upSelected = false;
            downSelected = false;
            if (this.availableRecipes.size() > r + 12) {
            	//more pages available
            	this.drawTexturedRectangle(241, 54, 40, 196, 7, 4);
            	if (mouseInRect(241, 54, 7, 4, mouseX, mouseY)) {
            		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 0.75F);
    	    		this.drawTexturedRectangle(241, 54, 23, 191, 7, 4);
    	    		downSelected = true;
            	}
            }
            
            if (page > 0) {
            	this.drawTexturedRectangle(241, 9, 40, 191, 7, 4);
            	if (mouseInRect(241, 9, 7, 4, mouseX, mouseY)) {
            		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 0.75F);
    	    		this.drawTexturedRectangle(241, 9, 23, 191, 7, 4);
    	    		upSelected = true;
            	}
            }
            
            for (int x = 0; x < 6; x++) {
            	for (int y = 0; y < 2; y++) {
            		if (r < this.availableRecipes.size()) {
            			int X = x * 18 + rx + x;
            			int Y = y * 18 + ry + y;
            			this.drawTexturedRectangle(X, Y, 7, 83, 18, 18);
            			GuiContainerTerraria.renderItemIntoGUI(this.availableRecipes.get(r).output, X, Y);
            			
            			if (mouseInRect(X, Y, 18, 18, mouseX, mouseY)) {
            	        	this.mc.getTextureManager().bindTexture(GuiContainerTerraria.INVENTORY_BACKGROUND);
            	            GlStateManager.color4f(1.0F, 1.0F, 1.0F, 0.75F);
            	    		this.drawTexturedRectangle(X + 1, Y + 1, 23, 191, 16, 16);
            	    		
            	    		selectedRecipe = this.availableRecipes.get(r);
            	    	}
            		}
            		r++;
            	}
            }
        }
        
        if (inventory.holdingSlot.stack != null) {
        	renderItemIntoGUI(inventory.holdingSlot.stack, mouseX - 8, mouseY - 8);
        } else {
        	
        }
        
        if (rightDown == true && System.currentTimeMillis() > rightClicked + 500L) {
        	if (selectedRecipe != null) {
        		if (inventory.holdingSlot.stack.item == selectedRecipe.output.item) { 
					if (inventory.holdingSlot.stack.size + selectedRecipe.output.size < inventory.holdingSlot.stack.item.maxStack) {
						inventory.holdingSlot.stack.size += selectedRecipe.output.size;
            			for (ItemStackT stack : selectedRecipe.input) {
            				this.removeItemCrafting(stack);
            			}
            			this.resetRecipes();
					}
				}
        	}
        	else
    		if (selectedSlot.stack != null) 
    		{
    			if (inventory.holdingSlot.stack == null) {
    				inventory.holdingSlot.stack = new ItemStackT(selectedSlot.stack.item);
    				selectedSlot.decrementStack(1);
    			} else {
    				if (inventory.holdingSlot.stack.size + 1 < inventory.holdingSlot.stack.item.maxStack) {
    					inventory.holdingSlot.stack.size++;
    					selectedSlot.decrementStack(1);
    				}
    			}
    		}
        }
        
    }
    
    public static void drawTexturedRectangle(int x, int y, int u, int v, int w, int h) {
    	GlStateManager.disableLighting();
        GlStateManager.disableDepthTest();
//        GlStateManager.disableBlend();
    	Minecraft.getInstance().ingameGUI.drawTexturedModalRect(x, y, u, v, w,h);
//    	GlStateManager.enableBlend();
        GlStateManager.enableLighting();
        GlStateManager.enableDepthTest();
    }
    
    public static void renderItemIntoGUI(ItemStackT item, int x, int y) {
    	//drawString(instance.fontRenderer, ""+Util.watchTime, 5, 10 + i * textSize, 0xFFFFFF);
    	GlStateManager.enableRescaleNormal();
        RenderHelper.enableGUIStandardItemLighting();
        
    	GlStateManager.disableLighting();
        GlStateManager.disableDepthTest();
        GlStateManager.disableBlend();
        Minecraft.getInstance().getItemRenderer().renderItemIntoGUI(item.itemForRender, x, y);
        GlStateManager.enableBlend();
        GlStateManager.enableLighting();
        GlStateManager.enableDepthTest();
        
    	RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        
    	if (item.size > 1) {
    		GlStateManager.disableLighting();
            GlStateManager.disableDepthTest();
            GlStateManager.disableBlend();
            Minecraft.getInstance().fontRenderer.drawStringWithShadow(""+item.size, x + 16 - (""+item.size).length()*5.5f, y+8, 0xFFFFFF);
            GlStateManager.enableBlend();
            GlStateManager.enableLighting();
            GlStateManager.enableDepthTest();
            // Fixes opaque cooldown overlay a bit lower
            // TODO: check if enabled blending still screws things up down the line.
            GlStateManager.enableBlend();
    	}
    	Minecraft.getInstance().getTextureManager().bindTexture(GuiContainerTerraria.INVENTORY_BACKGROUND);
    	
    }
    
    private boolean mouseInRect(int x, int y, int width, int height, int mouseX, int mouseY) {
    	return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    /**
     * Test if the 2D point is in a rectangle (relative to the GUI). Args : rectX, rectY, rectWidth, rectHeight, pointX,
     * pointY
     */
    @Override
    protected boolean isPointInRegion(int rectX, int rectY, int rectWidth, int rectHeight, double pointX, double pointY) {
        return true;
    }
    
    public boolean rightDown = false;
    public long rightClicked = 0;
    
    public void removeItemCrafting(ItemStackT item) {
    	int stack = item.size + 0;
    	InventoryTerraria inventory = ContainerTerrariaInventory.inventory;
    	for (int x = 0; x < 30; x++) {
    		if (x < 10) {
    			if (inventory.hotbar[x] != null) {
    				if (inventory.hotbar[x].stack != null) {
    					if (inventory.hotbar[x].stack.item == item.item) {
    						
    						if (inventory.hotbar[x].stack.size > stack) {
    							inventory.hotbar[x].stack.size -= stack;
    							NetworkHandler.INSTANCE.sendToServer(new CPacketSyncInventoryTerraria(0, inventory.hotbar[x].area, inventory.hotbar[x].id, inventory.hotbar[x].stack));
    							return;
    						} else {
    							stack -= inventory.hotbar[x].stack.size;
    							inventory.hotbar[x].stack = null;
    							
    							NetworkHandler.INSTANCE.sendToServer(new CPacketSyncInventoryTerraria(0, inventory.hotbar[x].area, inventory.hotbar[x].id, inventory.hotbar[x].stack));
    							if (stack <= 0) return;
    						}
    						
    					}
    				}
    			}
    		}
    		if (inventory.main[x] != null) {
				if (inventory.main[x].stack != null) {
					if (inventory.main[x].stack.item == item.item) {
						
						if (inventory.main[x].stack.size > stack) {
							inventory.main[x].stack.size -= stack;
							NetworkHandler.INSTANCE.sendToServer(new CPacketSyncInventoryTerraria(0, inventory.main[x].area, inventory.main[x].id, inventory.main[x].stack));
							return;
						} else {
							stack -= inventory.main[x].stack.size;
							inventory.main[x].stack = null;
							
							NetworkHandler.INSTANCE.sendToServer(new CPacketSyncInventoryTerraria(0, inventory.main[x].area, inventory.main[x].id, inventory.main[x].stack));
							if (stack <= 0) return;
						}
						
					}
				}
			}
    	}
    }
    
    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
    	
    	this.resetRecipes();
    	
    	InventoryTerraria inventory = ContainerTerrariaInventory.inventory;
    	if (inventory.holdingSlot != null && inventory.holdingSlot.stack != null)
        	if (mouseX < this.guiLeft || mouseY < this.guiTop || mouseX > this.guiLeft + this.WIDTH || mouseY > this.guiTop + this.HEIGHT) {
        		if (mouseButton == 0) {
        			EntityItemT item = (EntityItemT) Trewrite.ITEM_ENTITY_TYPE.spawnEntity(mc.player.world, null, null, mc.player.getPosition().up(), false, false);

            		item.item = inventory.holdingSlot.stack.item.itemName;
        			item.stack = inventory.holdingSlot.stack.size;
        			item.pickupDelay = 20 * 4;
            		NetworkHandler.INSTANCE.sendToServer(new CPacketThrowItemTerraria(0, -1, 0, new ItemStackT(inventory.holdingSlot.stack.item, inventory.holdingSlot.stack.size)));
            		inventory.holdingSlot.stack = null;
        		}
            	
        	}
    	
    	if (upSelected == true && mouseButton == 0) {
    		page--;
    	}
    	
    	if (downSelected == true && mouseButton == 0) {
    		page++;
    	}
    	
    	if (this.selectedRecipe != null) {
    		System.out.println("selected recipe");
    		if (inventory.holdingSlot == null || inventory.holdingSlot.stack == null) {
    			System.out.println("no holding slot");
        		if (mouseButton == 0) {
        			inventory.holdingSlot.stack = new ItemStackT(selectedRecipe.output.item, selectedRecipe.output.size);
        			for (ItemStackT stack : selectedRecipe.input) {
        				this.removeItemCrafting(stack);
        			}
        			this.resetRecipes();
        		}
        		if (mouseButton == 1) {
        			inventory.holdingSlot.stack = new ItemStackT(selectedRecipe.output.item, selectedRecipe.output.size);
        			for (ItemStackT stack : selectedRecipe.input) {
        				this.removeItemCrafting(stack);
        			}
        			this.resetRecipes();
        			this.rightDown = true;
        			this.rightClicked = System.currentTimeMillis();
        		}
        	} else {
        		if (inventory.holdingSlot.stack != null) {
        			if (mouseButton == 0) {
        				if (inventory.holdingSlot.stack.item == selectedRecipe.output.item) { 
        					if (inventory.holdingSlot.stack.size + selectedRecipe.output.size < inventory.holdingSlot.stack.item.maxStack) {
        						inventory.holdingSlot.stack.size += selectedRecipe.output.size;
                    			for (ItemStackT stack : selectedRecipe.input) {
                    				this.removeItemCrafting(stack);
                    			}
                    			this.resetRecipes();
        					}
        				}
            			
            		}
        			if (mouseButton == 1) {
        				if (inventory.holdingSlot.stack.item == selectedRecipe.output.item) {
        					if (inventory.holdingSlot.stack.size + selectedRecipe.output.size < inventory.holdingSlot.stack.item.maxStack) {
        						inventory.holdingSlot.stack.size += selectedRecipe.output.size;
                    			for (ItemStackT stack : selectedRecipe.input) {
                    				this.removeItemCrafting(stack);
                    			}
                    			this.resetRecipes();
                    			this.rightDown = true;
                    			this.rightClicked = System.currentTimeMillis();
        					}
        				}
        			}
        		}
        	}
    	}
    	
    	
    	
    	if (selectedSlot == null) return false;
    	
    	
    	
    	rightDown = false;
    	
    	
    	
    	if (KeyRegistry.trash.isKeyDown()) {
	        	if (mouseButton == 0) {
	        		if (selectedSlot.stack != null) {
	        			ItemStackT stack = new ItemStackT(selectedSlot.stack.item, selectedSlot.stack.size);
	        			trashSlot.stack = stack;
	        			selectedSlot.stack = null;
	        			
	        			NetworkHandler.INSTANCE.sendToServer(new CPacketSyncInventoryTerraria(0, selectedSlot.area, selectedSlot.id, selectedSlot.stack));
	        			NetworkHandler.INSTANCE.sendToServer(new CPacketSyncInventoryTerraria(0, trashSlot.area, trashSlot.id, trashSlot.stack));
	                	
	        		}
	        		return true;
	        	}
	        }
    	
    	if (mouseButton == 0) {
    		if (inventory.holdingSlot.stack != null && selectedSlot.isTrashSlot == true) {
    			ItemStackT stack = new ItemStackT(inventory.holdingSlot.stack.item, inventory.holdingSlot.stack.size);
    			selectedSlot.stack = stack;
    			inventory.holdingSlot.stack = null;
    		} else {
    			if (inventory.holdingSlot.stack == null) {
        			if (selectedSlot.stack != null) {
        				inventory.holdingSlot.stack = new ItemStackT(selectedSlot.stack.item, selectedSlot.stack.size);
        				selectedSlot.stack = null;
        			}
        		} else {
        			if (selectedSlot.stack == null && selectedSlot.canInteractWith(inventory.holdingSlot)) {
        				selectedSlot.stack = new ItemStackT(inventory.holdingSlot.stack.item, inventory.holdingSlot.stack.size);
        				inventory.holdingSlot.stack = null;
        			} else {
        				selectedSlot.takeFromStack(inventory.holdingSlot);
        			}
        		}
    		}
    		
    	}
    	if (mouseButton == 1) {
    		rightDown = true;
    		rightClicked = System.currentTimeMillis();
    		if (selectedSlot.stack != null) 
    		{
    			if (inventory.holdingSlot.stack == null) {
    				inventory.holdingSlot.stack = new ItemStackT(selectedSlot.stack.item);
    				selectedSlot.decrementStack(1);
    			} else {
    				if (inventory.holdingSlot.stack.size + 1 < inventory.holdingSlot.stack.item.maxStack) {
    					inventory.holdingSlot.stack.size++;
    					selectedSlot.decrementStack(1);
    				}
    			}
    		}
    	}
		NetworkHandler.INSTANCE.sendToServer(new CPacketSyncInventoryTerraria(0, selectedSlot.area, selectedSlot.id, selectedSlot.stack));
    	this.resetRecipes();

        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public boolean mouseReleased(double mouseReleased1, double mouseReleased3, int mouseReleased5) {
    	if (selectedSlot != null)
    		NetworkHandler.INSTANCE.sendToServer(new CPacketSyncInventoryTerraria(0, selectedSlot.area, selectedSlot.id, selectedSlot.stack));
    	rightDown = false;
        if (this.buttonClicked) {
            this.buttonClicked = false;
            return true;
        } else {
            return super.mouseReleased(mouseReleased1, mouseReleased3, mouseReleased5);
        }
    }
}
