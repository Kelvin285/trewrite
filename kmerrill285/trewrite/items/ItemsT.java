package kmerrill285.trewrite.items;

import java.util.HashMap;

import kmerrill285.trewrite.blocks.BlocksT;
import kmerrill285.trewrite.crafting.Recipes;
import kmerrill285.trewrite.items.accessories.Accessory;
import kmerrill285.trewrite.items.basic.BasicBroadsword;
import kmerrill285.trewrite.items.basic.BasicItem;
import kmerrill285.trewrite.items.terraria.IronAxe;
import kmerrill285.trewrite.items.terraria.IronBroadsword;
import kmerrill285.trewrite.items.terraria.IronHammer;
import kmerrill285.trewrite.items.terraria.IronPickaxe;
import kmerrill285.trewrite.items.terraria.IronShortsword;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class ItemsT {
	public static IronPickaxe IRON_PICKAXE;
	public static ItemBlockT DIRT_BLOCK;
	public static IronBroadsword IRON_BROADSWORD;
	public static IronShortsword IRON_SHORTSWORD;
	public static ItemBlockT IRON_ORE;
	public static MetalBar GOLD_BAR;
	public static BasicItem GEL;
	public static BasicBroadsword WOODEN_SWORD;
	public static IronAxe IRON_AXE;
	public static ItemBlockT STONE_BLOCK;
	public static ItemBlockT MUSHROOM;
	public static ItemBlockT COPPER_ORE;
	public static ItemBlockT GOLD_ORE;
	public static ItemBlockT SILVER_ORE;
	@ObjectHolder("trewrite:copper_watch")
	public static Accessory COPPER_WATCH;
	@ObjectHolder("trewrite:silver_watch")
	public static Accessory SILVER_WATCH;
	@ObjectHolder("trewrite:gold_watch")
	public static Accessory GOLD_WATCH;
	@ObjectHolder("trewrite:depth_meter")
	public static Accessory DEPTH_METER;
	public static ItemBlockT LIFE_CRYSTAL;
	public static ItemT ACORN;
	public static ItemBlockT BOTTLE;
	public static ItemT LESSER_HEALING_POTION;
	public static ItemBlockT GRASS_BLOCK;
	public static ItemBlockT GRASS_PATH;
	public static IronHammer IRON_HAMMER;
	public static ItemBlockT FURNACE;
	public static ItemBlockT TORCH;
	public static ItemBlockT BLUE_TORCH;
	public static ItemBlockT GREEN_TORCH;
	public static ItemBlockT YELLOW_TORCH;
	public static ItemBlockT RED_TORCH;
	public static ItemBlockT PURPLE_TORCH;
	public static ItemBlockT ORANGE_TORCH;
	public static ItemBlockT WHITE_TORCH;
	public static ItemBlockT PINK_TORCH;
	public static ItemBlockT CORRUPT_TORCH;
	public static ItemBlockT ICHOR_TORCH;
	public static ItemBlockT CURSED_TORCH;
	public static ItemBlockT RAINBOW_TORCH;
	public static ItemBlockT BONE_TORCH;
	public static ItemBlockT ULTRABRIGHT_TORCH;
	public static ItemBlockT ICE_TORCH;
	public static ItemBlockT DOOR;
	public static ItemT LESSER_MANA_POTION;
	public static ItemBlockT CHAIR;
	public static ItemBlockT TABLE;
	public static ItemBlockT WOOD_PLATFORM;
	public static ItemBlockT WOOD;
	public static ItemBlockT WORKBENCH;

	
	public static HashMap<String, ItemT> items = new HashMap<String, ItemT>();
	
	public static ItemT getItemFromString(String name) {
		return ItemsT.items.get(name);
	}
	
	@SubscribeEvent
	public static void registerItems(final RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll(
				IRON_PICKAXE = new IronPickaxe(),
				DIRT_BLOCK = (ItemBlockT) new ItemBlockT(BlocksT.DIRT_BLOCK, "dirt_block"),
				IRON_BROADSWORD = new IronBroadsword(),
				IRON_SHORTSWORD = new IronShortsword(),
				IRON_ORE = (ItemBlockT) new ItemBlockT(BlocksT.IRON_ORE, "iron_ore"),
				GOLD_BAR = new MetalBar(1200, "gold_bar"),
				GEL = new BasicItem(new Properties().group(ItemGroup.MATERIALS), 1, "gel", true),
				WOODEN_SWORD = new BasicBroadsword(7, 4, 24, 20, "wooden_sword"),
				IRON_AXE = new IronAxe(),
				STONE_BLOCK = (ItemBlockT) new ItemBlockT(BlocksT.STONE_BLOCK, "stone_block"),
				MUSHROOM = (ItemBlockT) new ItemBlockT(BlocksT.MUSHROOM, "mushroom").setMaxStack(99),
				COPPER_ORE = (ItemBlockT) new ItemBlockT(BlocksT.COPPER_ORE, "copper_ore"),
				GOLD_ORE = (ItemBlockT) new ItemBlockT(BlocksT.GOLD_ORE, "gold_ore"),
				SILVER_ORE = (ItemBlockT) new ItemBlockT(BlocksT.SILVER_ORE, "silver_ore"),
				COPPER_WATCH = (Accessory) new Accessory(new Properties().group(ItemGroup.MISC), "copper_watch").setTooltip("Tells the time").setBuySell(200),
				SILVER_WATCH = (Accessory) new Accessory(new Properties().group(ItemGroup.MISC), "silver_watch").setTooltip("Tells the time").setBuySell(1000),
				GOLD_WATCH = (Accessory) new Accessory(new Properties().group(ItemGroup.MISC), "gold_watch").setTooltip("Tells the time").setBuySell(2000),
				DEPTH_METER = (Accessory) new Accessory(new Properties().group(ItemGroup.MISC), "depth_meter").setTooltip("Shows depth").setBuySell(2500),
				LIFE_CRYSTAL = (ItemBlockT) new ItemBlockT(BlocksT.LIFE_CRYSTAL, "life_crystal").setMaxStack(99),
				ACORN = new ItemT(new Properties().group(ItemGroup.MISC), "acorn").setConsumable().setMaxStack(99),
				BOTTLE = (ItemBlockT) new ItemBlockT(BlocksT.BOTTLE, "bottle").setMaterial(),
				LESSER_HEALING_POTION = new ItemT(new Properties().group(ItemGroup.BREWING), "lesser_healing_potion").setMaterial().setConsumable().setPotionSickness(60).setHeal(50).setMaxStack(30),
				GRASS_BLOCK = (ItemBlockT) new ItemBlockT(BlocksT.GRASS_BLOCK, "grass_block"),
				GRASS_PATH = (ItemBlockT) new ItemBlockT(BlocksT.GRASS_PATH, "grass_path"),
				IRON_HAMMER = new IronHammer(),
				FURNACE = (ItemBlockT) new ItemBlockT(BlocksT.FURNACE, "furnace").setMaxStack(99),
				TORCH = (WallOrFloorBlock) new WallOrFloorBlock(BlocksT.TORCH, BlocksT.TORCH_WALL, "torch").setMaxStack(99),
				BLUE_TORCH = (WallOrFloorBlock) new WallOrFloorBlock(BlocksT.BLUE_TORCH, BlocksT.BLUE_TORCH_WALL, "blue_Torch").setMaxStack(99),
				GREEN_TORCH = (WallOrFloorBlock) new WallOrFloorBlock(BlocksT.GREEN_TORCH, BlocksT.GREEN_TORCH_WALL, "green_torch").setMaxStack(99),
				YELLOW_TORCH = (WallOrFloorBlock) new WallOrFloorBlock(BlocksT.YELLOW_TORCH, BlocksT.YELLOW_TORCH_WALL, "yellow_torch").setMaxStack(99),
				RED_TORCH = (WallOrFloorBlock) new WallOrFloorBlock(BlocksT.RED_TORCH, BlocksT.RED_TORCH_WALL, "red_torch").setMaxStack(99),
				PURPLE_TORCH = (WallOrFloorBlock) new WallOrFloorBlock(BlocksT.PURPLE_TORCH, BlocksT.PURPLE_TORCH_WALL, "purple_torch").setMaxStack(99),
				PINK_TORCH = (WallOrFloorBlock) new WallOrFloorBlock(BlocksT.PINK_TORCH, BlocksT.PINK_TORCH_WALL, "pink_torch").setMaxStack(99),
				WHITE_TORCH = (WallOrFloorBlock) new WallOrFloorBlock(BlocksT.WHITE_TORCH, BlocksT.WHITE_TORCH_WALL, "white_torch").setMaxStack(99),
				ORANGE_TORCH = (WallOrFloorBlock) new WallOrFloorBlock(BlocksT.ORANGE_TORCH, BlocksT.ORANGE_TORCH_WALL, "orange_torch").setMaxStack(99),
				CORRUPT_TORCH = (WallOrFloorBlock) new WallOrFloorBlock(BlocksT.CORRUPT_TORCH, BlocksT.CORRUPT_TORCH_WALL, "corrupt_torch").setMaxStack(99),
				CURSED_TORCH = (WallOrFloorBlock) new WallOrFloorBlock(BlocksT.CURSED_TORCH, BlocksT.CURSED_TORCH_WALL, "cursed_torch").setMaxStack(99),
				ICHOR_TORCH = (WallOrFloorBlock) new WallOrFloorBlock(BlocksT.ICHOR_TORCH, BlocksT.ICHOR_TORCH_WALL, "ichor_torch").setMaxStack(99),
				RAINBOW_TORCH = (WallOrFloorBlock) new WallOrFloorBlock(BlocksT.RAINBOW_TORCH, BlocksT.RAINBOW_TORCH_WALL, "rainbow_torch").setMaxStack(99),
				BONE_TORCH = (WallOrFloorBlock) new WallOrFloorBlock(BlocksT.BONE_TORCH, BlocksT.BONE_TORCH_WALL, "bone_torch").setMaxStack(99),
				ULTRABRIGHT_TORCH = (WallOrFloorBlock) new WallOrFloorBlock(BlocksT.ULTRABRIGHT_TORCH, BlocksT.ULTRABRIGHT_TORCH_WALL, "ultrabright_torch").setMaxStack(99),
				ICE_TORCH = (WallOrFloorBlock) new WallOrFloorBlock(BlocksT.ICE_TORCH, BlocksT.ICE_TORCH_WALL, "ice_torch").setMaxStack(99),
				DOOR = (ItemBlockT) new ItemBlockT(BlocksT.DOOR, "door").setMaxStack(99),
				LESSER_HEALING_POTION = new ItemT(new Properties().group(ItemGroup.BREWING), "lesser_mana_potion").setMaterial().setConsumable().setManaSickness(5).setManaHeal(50).setMaxStack(30),
				CHAIR = (ItemBlockT) new ItemBlockT(BlocksT.CHAIR, "chair").setMaxStack(99),
				TABLE = (ItemBlockT) new ItemBlockT(BlocksT.TABLE, "table").setMaxStack(99),
				WOOD_PLATFORM = (ItemBlockT) new ItemBlockT(BlocksT.WOOD_PLATFORM, "wood_platform").setMaxStack(999),
				WOOD = (ItemBlockT) new ItemBlockT(BlocksT.WOOD, "wood").setMaxStack(999),
				WORKBENCH = (ItemBlockT) new ItemBlockT(BlocksT.WORKBENCH, "workbench").setMaxStack(99)
				);
		Recipes.addAllRecipes();
	}
}
