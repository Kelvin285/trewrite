package kmerrill285.trewrite.client.gui.dialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.mojang.blaze3d.platform.GlStateManager;

import kmerrill285.trewrite.Trewrite;
import kmerrill285.trewrite.blocks.BlockT;
import kmerrill285.trewrite.client.gui.inventory.InventoryTerraria;
import kmerrill285.trewrite.client.gui.inventory.container.ContainerTerrariaInventory;
import kmerrill285.trewrite.client.gui.inventory.container.GuiContainerTerrariaInventory;
import kmerrill285.trewrite.crafting.CraftingRecipe;
import kmerrill285.trewrite.crafting.Recipes;
import kmerrill285.trewrite.items.ItemStackT;
import kmerrill285.trewrite.world.TerrariaDimension;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.registry.Registry;

public class GuideDialog extends Dialog {

	private static String name = "Scott";
	
	String[][] day_quotes = {
			{"Greetings, %player%.", "Is there something I can help you with?"},
			
			};
	
	private String[] quote;
	private String[] happiness = {"I'm quite used to not having", "a home, but I wouldn't mind one."};
	
	private Screen screen = Screen.MAIN;
	
	private boolean left_pressed;
	
	private int craftingPage = 0;
	private HashMap<Item, ArrayList<CraftingRecipe>> recipesForItem = new HashMap<Item, ArrayList<CraftingRecipe>>();

	private int selectedRecipe = -1;
	
	private enum Screen {
		MAIN, QUEST, CRAFTING, HAPPINESS
	}
	
	public GuideDialog(DialogGui gui) {
		super(gui);
		if (TerrariaDimension.beach > 15) {
			happiness = new String[]{"I don't really like the Ocean.", "There's little to accomplish."};
		}
		if (TerrariaDimension.corruption > 15) {
			happiness = new String[]{"I hate the Corruption, the terrors","here can tear a person apart","in moments."};
		}
		quote = day_quotes[new Random().nextInt(day_quotes.length)];
		
		if (!Trewrite.IsDaytime(Minecraft.getInstance().world)) {
			quote = new String[] {"You should stay indoors at night.", "It is very dangerous to be wandering", "around in the dark"};
		}
		
		InventoryTerraria inventory = ContainerTerrariaInventory.inventory;
		for (Block block : Recipes.recipes.keySet()) {
			List<CraftingRecipe> list = Recipes.recipes.get(block);
			for (CraftingRecipe recipe : list) {
				boolean hasAllItems = true;
				for (ItemStackT stack : recipe.input) { 
					boolean hasItem = false;
					for (int i = 0; i < inventory.hotbar.length; i++) {
						if (inventory.hotbar[i].stack != null) {
							if (inventory.hotbar[i].stack.item == stack.item) {
								hasItem = true;
								break;
							}
						}
					}
					if (!hasItem) {
						for (int i = 0; i < inventory.main.length; i++) {
							if (inventory.main[i].stack != null) {
								if (inventory.main[i].stack.item == stack.item) {
									hasItem = true;
									break;
								}
							}
						}
					}
					if (!hasItem) {
						hasAllItems = false;
						break;
					}
				}
				if (hasAllItems) {
					ArrayList<CraftingRecipe> r = recipesForItem.get(recipe.output.item);
					if (r == null) {
						r = new ArrayList<CraftingRecipe>();
					}
					r.add(recipe);
					recipesForItem.put(recipe.output.item, r);
				}
			}
		}
	}

	@Override
	public void Update() {
		
	}

	@Override
	public void Render() {
		
		switch (screen) {
		case CRAFTING:
			RenderCraftingDialog();
			break;
		case HAPPINESS:
			RenderHappinessDialog();
			break;
		case MAIN:
			RenderMainDialog();
			break;
		case QUEST:
			RenderQuestDialog();
			break;
		default:
			break;
		}
	}
	
	private void RenderOptions(int y_offset) {
		int x = Minecraft.getInstance().mainWindow.getScaledWidth() / 2 - 100;
		int y = Minecraft.getInstance().mainWindow.getScaledHeight() / 2 - 80 + y_offset;
		
		gui.DrawDialog(x - 9, y + 70, 70, 20);
		
		gui.DrawDialog(x + 71, y + 70, 70, 20);
		
		gui.DrawDialog(x + 151, y + 70, 70, 20);
		
		gui.drawString(Minecraft.getInstance().fontRenderer, "Quest", x + 15, y + 80, 0xffffff);
		gui.drawString(Minecraft.getInstance().fontRenderer, "Crafting", x + 89, y + 80, 0xffffff);
		gui.drawString(Minecraft.getInstance().fontRenderer, "Happiness", x + 165, y + 80, 0xffffff);
		
		double mx = Minecraft.getInstance().mouseHelper.getMouseX();
		double my = Minecraft.getInstance().mouseHelper.getMouseY();
		
		my *= (float)Minecraft.getInstance().mainWindow.getScaledHeight() / (float)Minecraft.getInstance().mainWindow.getFramebufferHeight();
		mx *= (float)Minecraft.getInstance().mainWindow.getScaledWidth() / (float)Minecraft.getInstance().mainWindow.getFramebufferWidth();

		
		if (mx > x - 9 && my > y + 70 && mx < x + 69 && my < y + 98) {
			if (left_pressed) {
				screen = Screen.QUEST;
			}
		}
		
		if (mx > x + 71 && my > y + 70 && mx < x + 150 && my < y + 98) {
			if (left_pressed) {
				screen = Screen.CRAFTING;
			}
		}
		
		if (mx > x + 71 + (165 - 89) && my > y + 70 && mx < x + 150 + (165 - 89) && my < y + 98) {
			if (left_pressed) {
				screen = Screen.HAPPINESS;
			}
		}
		
		left_pressed = false;
	}
	
	private void RenderHappinessDialog() {
		for (int i = 0; i < quote.length; i++) {
			happiness[i] = happiness[i].replace("%player%", Minecraft.getInstance().player.getScoreboardName());
		}
		
		int x = Minecraft.getInstance().mainWindow.getScaledWidth() / 2 - 100;
		int y = Minecraft.getInstance().mainWindow.getScaledHeight() / 2 - 80;
		
		gui.DrawDialog(x, y - 30, 110, 20);
		gui.drawString(Minecraft.getInstance().fontRenderer, name + " the Guide", x + 10, y - 20, 0xffffff);
		gui.DrawDialog(x, y, 210, 50);
		
		for (int i = 0; i < happiness.length; i++) {
			gui.drawString(Minecraft.getInstance().fontRenderer, (i == 0 ? "\"" : "") + happiness[i] + (i == happiness.length - 1 ? "\"" : ""), x + 10, y + 10 * i + 10, 0xffffff);
		}
		
		RenderOptions(0);
	}
	
	private void RenderCraftingDialog() {
		
		boolean left_pressed = this.left_pressed;
		RenderOptions(90);

		
		int x = 20;
		int y = 20;
		int rx = 0;
		int ry = 0;
		craftingPage = 0;
		
		gui.DrawDialog(x - 5, y - 10, 11 * 20, 9 * 20);
		
		double mx = Minecraft.getInstance().mouseHelper.getMouseX();
		double my = Minecraft.getInstance().mouseHelper.getMouseY();
		
		my *= (float)Minecraft.getInstance().mainWindow.getScaledHeight() / (float)Minecraft.getInstance().mainWindow.getFramebufferHeight();
		mx *= (float)Minecraft.getInstance().mainWindow.getScaledWidth() / (float)Minecraft.getInstance().mainWindow.getFramebufferWidth();
		
		Object[] items = recipesForItem.keySet().toArray();
		int i = 0;
		
		String item_string = "";
		for (Item item : recipesForItem.keySet()) {
			if (ry >= craftingPage && ry <= craftingPage + 8) {
				GuiContainerTerrariaInventory.renderItemIntoGUI(new ItemStackT(item), x + rx * 20, y + ry * 20 - craftingPage * 20);
				int X = x + rx * 20;
				int Y = y + ry * 20 - craftingPage * 20;
				if (left_pressed) {
					
					if (mx >= X && my >= Y && mx <= X + 20 && my <= Y + 20) {
						selectedRecipe = i;
					}
				}
				if (mx > X && my >= Y && mx < X + 20 && my < Y + 20) {
					String name = item.getName().getFormattedText();
					item_string = name;
				}
			}
			
			
			rx ++;
			if (rx > 10) {
				rx = 0;
				ry++;
			}
			i++;
		}
		
		
		if (selectedRecipe != -1) {
			gui.DrawDialog(x + 11 * 20 + 10, y - 10, 8 * 20, 9 * 20);
			ArrayList<CraftingRecipe> recipes = recipesForItem.get(items[selectedRecipe]);
			
			boolean workbench = false;
			
			for (i = 0; i < recipes.size(); i++) {
				if (recipes.get(i).block != null) {
					String name = recipes.get(i).block.getRegistryName().toString().toLowerCase();
					
					if (name.contains("workbench")) {
						if (workbench) {
							recipes.remove(i);
						}
						workbench = true;
					}
				}
				
			}
			int I = 0;
			for (i = 0; i < recipes.size(); i++) {
				CraftingRecipe recipe = recipes.get(i);
				GuiContainerTerrariaInventory.renderItemIntoGUI(recipe.output, x + 12 * 20, y + 20 * I);
				int X = x + 12 * 20;
				int Y = y + 20 * I;
				if (mx > X && my >= Y && mx < X + 20 && my < Y + 20) {
					item_string = "Output: " + recipe.output.item.getName().getFormattedText();
				}
				
				gui.drawString(Minecraft.getInstance().fontRenderer, "=", x + 12 * 20 + 20, y + 20 * I + 5, 0xffffff);
				
				if (recipe.block != null) {
					gui.drawString(Minecraft.getInstance().fontRenderer, "+", x + 12 * 20 + 20, y + 20 * I + 5 + 20, 0xffffff);
					
					Item item = Registry.ITEM.getOrDefault(recipe.block.getRegistryName());
					if (recipe.block == Blocks.WATER) { 
						item = Items.WATER_BUCKET;
					}
					GuiContainerTerrariaInventory.renderItemIntoGUI(new ItemStackT(item), x + 12 * 20 + 30, y + 20 * I + 20);
					X = x + 12 * 20 + 30;
					Y = y + 20 * I + 20;
					if (mx > X && my >= Y && mx < X + 20 && my < Y + 20) {
						String name = item.getName().getFormattedText();
						if (item == Items.WATER_BUCKET) { 
							name = "Water Source";
						}
						gui.drawString(Minecraft.getInstance().fontRenderer, "Station: " + name, (int)mx + 10, (int)my, 0xffffff);
					}
				}
				
				for (int j = 0; j < recipe.input.length; j++) {
					
					GuiContainerTerrariaInventory.renderItemIntoGUI(recipe.input[j], x + 12 * 20 + j * 20 + 30, y + 20 * I);
					
					X = x + 12 * 20 + j * 20 + 30;
					Y = y + 20 * I;
					if (mx > X && my >= Y && mx < X + 20 && my < Y + 20) {
						item_string = recipe.input[j].item.getName().getFormattedText();
					}
				}
				
				if (recipe.block != null)I++;
				I++;
			}
		}
		
		if (item_string != null) {
			gui.drawString(Minecraft.getInstance().fontRenderer, item_string, (int)mx + 10, (int)my, 0xffffff);
		}
		
	}
	
	private void RenderQuestDialog() {
		
		int x = Minecraft.getInstance().mainWindow.getScaledWidth() / 2 - 100;
		int y = Minecraft.getInstance().mainWindow.getScaledHeight() / 2 - 80;
		
		gui.DrawDialog(x, y - 30, 110, 20);
		gui.drawString(Minecraft.getInstance().fontRenderer, name + " the Guide", x + 10, y - 20, 0xffffff);
		gui.DrawDialog(x, y, 210, 150);

		gui.drawString(Minecraft.getInstance().fontRenderer, "\"I don't have any quests for you right", x + 10, y + 10, 0xffffff);
		gui.drawString(Minecraft.getInstance().fontRenderer, "now.  Try coming back again later!\"", x + 10, y + 20, 0xffffff);
		
		RenderOptions(100);
	}
	
	private void RenderMainDialog() {
		for (int i = 0; i < quote.length; i++) {
			quote[i] = quote[i].replace("%player%", Minecraft.getInstance().player.getScoreboardName());
		}
		
		int x = Minecraft.getInstance().mainWindow.getScaledWidth() / 2 - 100;
		int y = Minecraft.getInstance().mainWindow.getScaledHeight() / 2 - 80;
		
		gui.DrawDialog(x, y - 30, 110, 20);
		gui.drawString(Minecraft.getInstance().fontRenderer, name + " the Guide", x + 10, y - 20, 0xffffff);
		gui.DrawDialog(x, y, 210, 50);
		
		for (int i = 0; i < quote.length; i++) {
			gui.drawString(Minecraft.getInstance().fontRenderer, (i == 0 ? "\"" : "") + quote[i] + (i == quote.length - 1 ? "\"" : ""), x + 10, y + 10 * i + 10, 0xffffff);
		}
		
		RenderOptions(0);
	}

	@Override
	protected void mouseClicked(double x, double y, int code) {
		if (code == 0) left_pressed = true;
	}

}
