package kmerrill285.trewrite.items;

import java.util.HashMap;

import kmerrill285.trewrite.blocks.BlocksT;
import kmerrill285.trewrite.crafting.Recipes;
import kmerrill285.trewrite.items.accessories.Accessory;
import kmerrill285.trewrite.items.accessories.Accessory.WearSlot;
import kmerrill285.trewrite.items.basic.BasicBroadsword;
import kmerrill285.trewrite.items.basic.BasicItem;
import kmerrill285.trewrite.items.terraria.accessories.CloudInABottle;
import kmerrill285.trewrite.items.terraria.accessories.HermesBoots;
import kmerrill285.trewrite.items.terraria.accessories.ObsidianSkull;
import kmerrill285.trewrite.items.terraria.arrows.FlamingArrow;
import kmerrill285.trewrite.items.terraria.arrows.JestersArrow;
import kmerrill285.trewrite.items.terraria.arrows.UnholyArrow;
import kmerrill285.trewrite.items.terraria.arrows.WoodenArrow;
import kmerrill285.trewrite.items.terraria.axes.CopperAxe;
import kmerrill285.trewrite.items.terraria.axes.IronAxe;
import kmerrill285.trewrite.items.terraria.axes.WarAxeOfTheNight;
import kmerrill285.trewrite.items.terraria.boss_summon.SuspiciousLookingEye;
import kmerrill285.trewrite.items.terraria.bows.DemonBow;
import kmerrill285.trewrite.items.terraria.bows.WoodenBow;
import kmerrill285.trewrite.items.terraria.broadswords.CopperBroadsword;
import kmerrill285.trewrite.items.terraria.broadswords.IronBroadsword;
import kmerrill285.trewrite.items.terraria.broadswords.LightsBane;
import kmerrill285.trewrite.items.terraria.hammers.CopperHammer;
import kmerrill285.trewrite.items.terraria.hammers.IronHammer;
import kmerrill285.trewrite.items.terraria.loot_bags.Present;
import kmerrill285.trewrite.items.terraria.picks.CopperPickaxe;
import kmerrill285.trewrite.items.terraria.picks.IronPickaxe;
import kmerrill285.trewrite.items.terraria.shortswords.CopperShortsword;
import kmerrill285.trewrite.items.terraria.shortswords.IronShortsword;
import kmerrill285.trewrite.items.terraria.throwable.Shuriken;
import kmerrill285.trewrite.items.terraria.tools.MagicMirror;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.MinecraftForge;
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
	public static ItemAcorn ACORN;
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
	public static ItemBlockT CHEST;
	public static ItemT COPPER_BAR;
	public static ItemT IRON_BAR;
	public static ItemT SILVER_BAR;
	public static ItemBlockT IRON_ANVIL;
	public static ItemBlockT CORRUPT_GRASS;
	public static ItemBlockT VILE_MUSHROOM;
	public static ItemBlockT DEMONITE_ORE;
	public static ItemBlockT EBONSTONE;
	public static ItemBlockT SAND;
	public static ItemBlockT EBONSAND;
	public static ItemBlockT SUNFLOWER;
	public static ItemBlockT PIGGY_BANK;
	
	public static ItemBlockT EBONWOOD_PLATFORM;
	public static ItemBlockT EBONWOOD;
	public static ItemBlockT SHADEWOOD_PLATFORM;
	public static ItemBlockT SHADEWOOD;
	public static ItemBlockT PEARLWOOD_PLATFORM;
	public static ItemBlockT PEARLWOOD;
	public static ItemBlockT RICH_MAHOGANY_PLATFORM;
	public static ItemBlockT RICH_MAHOGANY;
	public static ItemBlockT BOREAL_WOOD_PLATFORM;
	public static ItemBlockT BOREAL_WOOD;
	public static ItemBlockT PALM_WOOD_PLATFORM;
	public static ItemBlockT PALM_WOOD;
	public static ItemBlockT MUD;
	public static ItemBlockT DEEP_MUD;
	public static ItemBlockT ICE;
	public static ItemBlockT SNOW;
	public static ItemBlockT PURPLE_ICE;
	public static ItemBlockT LEAVES;
	public static ItemBlockT LIVING_WOOD_PLATFORM;
	public static ItemBlockT CLAY_BLOCK;
	public static ItemBlockT RED_SAND;
	public static ItemBlockT CACTUS_BLOCK;
	public static ItemBlockT GLOWING_MUSHROOM;
	public static CopperAxe COPPER_AXE;
	public static CopperPickaxe COPPER_PICKAXE;
	public static CopperHammer COPPER_HAMMER;
	public static CopperShortsword COPPER_SHORTSWORD;
	public static CopperBroadsword COPPER_BROADSWORD;

	public static Armor GOGGLES;
	
	public static ItemT LENS;
	
	public static ItemT WOODEN_BOW;
	public static ItemT WOODEN_ARROW;
	public static ItemT FLAMING_ARROW;
	
	public static ItemT SHURIKEN;
	
	public static ItemT SUSPICIOUS_LOOKING_EYE;
	
	public static ItemT DEMONITE_BAR;
	
	public static ItemT DEMON_BOW;
	public static ItemT WAR_AXE_OF_THE_NIGHT;
	public static ItemT LIGHTS_BANE;
	
	public static ItemT CHAIN;
	
	public static ItemT UNHOLY_ARROW;
	
	public static ItemT WORM_TOOTH;
	
	public static ItemT BAND_OF_REGENERATION;
	
	public static ItemT MAGIC_MIRROR;
	
	public static ItemT FALLEN_STAR;
	public static ItemT JESTERS_ARROW;
	
	public static ItemT CLOUD_IN_A_BOTTLE;
	
	public static ItemT GLASS;
	public static ItemT GLASS_PLATFORM;
	
	public static ItemT ANGEL_STATUE;
	
	public static ItemT HERMES_BOOTS;
	
	public static ItemT ASH_BLOCK;
	
	public static ItemT IRIDESCENT_BRICK;
	
	public static ItemT HELLSTONE;
	public static ItemT HELLSTONE_BRICKS;
	
	public static ItemT HELLFORGE;
	
	public static ItemT OBSIDIAN;
	
	public static ItemT OBSIDIAN_SKULL;
	
	public static ItemT OBSIDIAN_VASE;
	
	public static ItemT ICE_MIRROR;
	
	public static ItemT PRESENT;
	
	public static ItemT OBSIDIAN_BRICK;
	public static ItemT OBSIDIAN_CHEST;
	
	public static ItemT OBSIDIAN_PLATFORM;
	
	public static ItemT RYORAMAS_BLADE;
	
	public static ItemT OBSIDIAN_LAMP;
	
	public static ItemT OBSIDIAN_PIANO;
	
	public static ItemT OBSIDIAN_DOOR;
	public static ItemT OBSIDIAN_CHANDELIER;
	public static ItemT OBSIDIAN_LANTERN;
	public static ItemT OBSIDIAN_CANDELABRA;
	public static ItemT OBSIDIAN_CANDLE;




	
	public static ItemT ANY_WOOD = new ItemT().setItemName("ANY_WOOD");
	public static ItemT ANY_IRON = new ItemT().setItemName("ANY_IRON");
	public static ItemT ANY_SAND = new ItemT().setItemName("ANY_SAND");
	
	public static HashMap<String, ItemT> items = new HashMap<String, ItemT>();
	
	public static String getStringForItem(Item item) {
		if (item == null) {
			return "null";
		}
		String str = item.getRegistryName().getNamespace()+":"+item.getRegistryName().toString();
		if (item.getRegistryName().toString().contains(item.getRegistryName().getNamespace())) {
			str = item.getRegistryName().toString();
		}
		return str;
	}
	
	public static Item getItemFromString(String name) {
		
		try {
			return Registry.ITEM.getValue(new ResourceLocation(name)).get();
		}catch (Exception e) {
//			e.printStackTrace();
		}
		
		try {
			return Registry.ITEM.getValue(new ResourceLocation("trewrite:"+name)).get();
		}catch (Exception e) {
//			e.printStackTrace();
		}
		
		return null;
//		return ItemsT.items.get(name);
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
				COPPER_WATCH = (Accessory) new Accessory(new Properties().group(ItemGroup.MISC), "copper_watch").setWearable(WearSlot.SINGLE_LEG).setTooltip("Tells the time").setBuySell(200),
				SILVER_WATCH = (Accessory) new Accessory(new Properties().group(ItemGroup.MISC), "silver_watch").setWearable(WearSlot.SINGLE_LEG).setTooltip("Tells the time").setBuySell(1000),
				GOLD_WATCH = (Accessory) new Accessory(new Properties().group(ItemGroup.MISC), "gold_watch").setWearable(WearSlot.SINGLE_LEG).setTooltip("Tells the time").setBuySell(2000),
				DEPTH_METER = (Accessory) new Accessory(new Properties().group(ItemGroup.MISC), "depth_meter").setTooltip("Shows depth").setBuySell(2500),
				LIFE_CRYSTAL = (ItemBlockT) new ItemBlockT(BlocksT.LIFE_CRYSTAL, "life_crystal").setMaxStack(99),
				ACORN = (ItemAcorn) new ItemAcorn(BlocksT.FOREST_SAPLING, "acorn").setMaxStack(99),
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
				LESSER_MANA_POTION = new ItemT(new Properties().group(ItemGroup.BREWING), "lesser_mana_potion").setMaterial().setConsumable().setManaSickness(5).setManaHeal(50).setMaxStack(30),
				CHAIR = (ItemBlockT) new ItemBlockT(BlocksT.CHAIR, "chair").setMaxStack(99),
				TABLE = (ItemBlockT) new ItemBlockT(BlocksT.TABLE, "table").setMaxStack(99),
				WOOD_PLATFORM = (ItemBlockT) new ItemBlockT(BlocksT.WOOD_PLATFORM, "wood_platform").setMaxStack(999),
				WOOD = (ItemBlockT) new ItemBlockT(BlocksT.WOOD, "wood").setMaxStack(999),
				WORKBENCH = (ItemBlockT) new ItemBlockT(BlocksT.WORKBENCH, "workbench").setMaxStack(99),
				CHEST = (ItemBlockT) new ItemBlockT(BlocksT.CHEST, "chest").setBuySell(100).setMaxStack(99),
				COPPER_BAR = new MetalBar(150, "copper_bar"),
				SILVER_BAR = new MetalBar(600, "silver_bar"),
				IRON_BAR = new MetalBar(300, "iron_bar"),
				IRON_ANVIL = (ItemBlockT) new ItemBlockT(BlocksT.IRON_ANVIL, "iron_anvil").setMaxStack(99),
				DEMONITE_ORE = (ItemBlockT) new ItemBlockT(BlocksT.DEMONITE_ORE, "demonite_ore").setMaxStack(999),
				VILE_MUSHROOM = (ItemBlockT) new ItemBlockT(BlocksT.VILE_MUSHROOM, "vile_mushroom").setMaxStack(99),
				CORRUPT_GRASS = (ItemBlockT) new ItemBlockT(BlocksT.CORRUPT_GRASS, "corrupt_grass"),
				EBONSTONE = (ItemBlockT) new ItemBlockT(BlocksT.EBONSTONE, "ebonstone"),
				SAND = (ItemBlockT) new ItemBlockT(BlocksT.SAND, "sand"),
				EBONSAND = (ItemBlockT) new ItemBlockT(BlocksT.EBONSAND, "ebonsand"),
				SUNFLOWER = (ItemBlockT) new ItemBlockT(BlocksT.SUNFLOWER, "sunflower"),
				PIGGY_BANK = (ItemBlockT) new ItemBlockT(BlocksT.PIGGY_BANK, "piggy_bank"),
				EBONWOOD_PLATFORM = (ItemBlockT) new ItemBlockT(BlocksT.EBONWOOD_PLATFORM, "ebonwood_platform").setMaxStack(999),
				EBONWOOD = (ItemBlockT) new ItemBlockT(BlocksT.EBONWOOD, "ebonwood").setMaxStack(999),
				PEARLWOOD_PLATFORM = (ItemBlockT) new ItemBlockT(BlocksT.PEARLWOOD_PLATFORM, "pearlwood_platform").setMaxStack(999),
				PEARLWOOD = (ItemBlockT) new ItemBlockT(BlocksT.PEARLWOOD, "pearlwood").setMaxStack(999),
				SHADEWOOD_PLATFORM = (ItemBlockT) new ItemBlockT(BlocksT.SHADEWOOD_PLATFORM, "shadewood_platform").setMaxStack(999),
				SHADEWOOD = (ItemBlockT) new ItemBlockT(BlocksT.SHADEWOOD, "shadewood").setMaxStack(999),
				RICH_MAHOGANY_PLATFORM = (ItemBlockT) new ItemBlockT(BlocksT.RICH_MAHOGANY_PLATFORM, "rich_mahogany_platform").setMaxStack(999),
				RICH_MAHOGANY = (ItemBlockT) new ItemBlockT(BlocksT.RICH_MAHOGANY, "rich_mahogany").setMaxStack(999),
				BOREAL_WOOD_PLATFORM = (ItemBlockT) new ItemBlockT(BlocksT.BOREAL_WOOD_PLATFORM, "boreal_wood_platform").setMaxStack(999),
				BOREAL_WOOD = (ItemBlockT) new ItemBlockT(BlocksT.BOREAL_WOOD, "boreal_wood").setMaxStack(999),
				PALM_WOOD_PLATFORM = (ItemBlockT) new ItemBlockT(BlocksT.PALM_WOOD_PLATFORM, "palm_wood_platform").setMaxStack(999),
				PALM_WOOD = (ItemBlockT) new ItemBlockT(BlocksT.PALM_WOOD, "palm_wood").setMaxStack(999),
				MUD = (ItemBlockT) new ItemBlockT(BlocksT.MUD, "mud").setMaxStack(999),
				DEEP_MUD = (ItemBlockT) new ItemBlockT(BlocksT.DEEP_MUD, "deep_mud").setMaxStack(999),
				ICE = (ItemBlockT) new ItemBlockT(BlocksT.ICE, "ice").setMaxStack(999),
				SNOW = (ItemBlockT) new ItemBlockT(BlocksT.SNOW, "snow").setMaxStack(999),
				PURPLE_ICE = (ItemBlockT) new ItemBlockT(BlocksT.PURPLE_ICE, "purple_ice").setMaxStack(999),
				LEAVES = (ItemBlockT) new ItemBlockT(BlocksT.LEAVES, "leaves").setMaxStack(999),
				LIVING_WOOD_PLATFORM = (ItemBlockT) new ItemBlockT(BlocksT.LIVING_WOOD_PLATFORM, "living_wood_platform").setMaxStack(999),
				CLAY_BLOCK = (ItemBlockT) new ItemBlockT(BlocksT.CLAY_BLOCK, "clay_block").setMaxStack(999),
				RED_SAND = (ItemBlockT) new ItemBlockT(BlocksT.RED_SAND, "red_sand").setMaxStack(999),
				CACTUS_BLOCK = (ItemBlockT) new ItemBlockT(BlocksT.CACTUS_BLOCK, "cactus_block").setMaxStack(999),
				GLOWING_MUSHROOM = (ItemBlockT) new ItemBlockT(BlocksT.GLOWING_MUSHROOM, "glowing_mushroom").setMaxStack(999).setBuySell(10),
				COPPER_AXE = (CopperAxe) new CopperAxe().setBuySell(80),
				COPPER_PICKAXE = (CopperPickaxe) new CopperPickaxe().setBuySell(100),
				COPPER_HAMMER = (CopperHammer) new CopperHammer().setBuySell(80),
				COPPER_BROADSWORD = (CopperBroadsword) new CopperBroadsword().setBuySell(90),
				COPPER_SHORTSWORD = (CopperShortsword) new CopperShortsword().setBuySell(70),
				GOGGLES = (Armor) new Armor(new Properties().group(ItemGroup.COMBAT), "goggles", Armor.ArmorType.HEAD, 1).setMaxStack(1).setBuySell(200),
				LENS = new ItemT(new Properties().group(ItemGroup.MATERIALS), "lens").setMaterial().setBuySell(100).setMaxStack(99),
				WOODEN_BOW = new WoodenBow(),
				WOODEN_ARROW = new WoodenArrow().setMaxStack(999).setMaterial(),
				FLAMING_ARROW = new FlamingArrow().setMaxStack(999),
				SHURIKEN = new Shuriken().setMaxStack(999),
				SUSPICIOUS_LOOKING_EYE = new SuspiciousLookingEye(new Properties().group(ItemGroup.MISC), "suspicious_looking_eye").setMaxStack(30),
				DEMONITE_BAR = new ItemT(new Properties().group(ItemGroup.MATERIALS), "demonite_bar").setMaterial().setBuySell(3200).setMaxStack(99),
				DEMON_BOW = new DemonBow(),
				WAR_AXE_OF_THE_NIGHT = new WarAxeOfTheNight(),
				CHAIN = new ItemBlockT(BlocksT.CHAIN, "chain").setMaterial().setBuySell(40).setMaxStack(999),
				LIGHTS_BANE = new LightsBane(),
				UNHOLY_ARROW = new UnholyArrow(),
				WORM_TOOTH = new ItemT(new Properties().group(ItemGroup.MATERIALS), "worm_tooth").setMaterial().setBuySell(20).setMaxStack(99),
				BAND_OF_REGENERATION = (Accessory) new Accessory(new Properties().group(ItemGroup.MISC), "band_of_regeneration").setTooltip("Slowly regenerates life").setMaterial().setBuySell(10000),
				MAGIC_MIRROR = new MagicMirror("magic_mirror").setMaterial(),
				JESTERS_ARROW = new JestersArrow(),
				FALLEN_STAR = new ItemT(new Properties().group(ItemGroup.MATERIALS), "fallen_star").setMaterial().setMaxStack(99).setBuySell(500).setAmmo().setTooltip("Dissapears after the sunrise"),
				CLOUD_IN_A_BOTTLE = new CloudInABottle().setWearable(WearSlot.SINGLE_LEG),
				GLASS = new ItemBlockT(BlocksT.GLASS, "glass").setMaterial().setMaxStack(999),
				GLASS_PLATFORM = new ItemBlockT(BlocksT.GLASS_PLATFORM, "glass_platform").setMaterial().setMaxStack(999),
				ANGEL_STATUE = new ItemBlockT(BlocksT.ANGEL_STATUE, "angel_statue").setMaxStack(99).setBuySell(60),
				HERMES_BOOTS = new HermesBoots().setWearable(WearSlot.FEET),
				ASH_BLOCK = new ItemBlockT(BlocksT.ASH_BLOCK, "ash_block"),
				IRIDESCENT_BRICK = new ItemBlockT(BlocksT.IRIDESCENT_BRICK, "iridescent_brick"),
				HELLSTONE = new ItemBlockT(BlocksT.HELLSTONE, "hellstone"),
				HELLSTONE_BRICKS = new ItemBlockT(BlocksT.HELLSTONE_BRICKS, "hellstone_bricks"),
				HELLFORGE = new ItemBlockT(BlocksT.HELLFORGE, "hellforge"),
				OBSIDIAN = new ItemBlockT(BlocksT.OBSIDIAN, "obsidian"),
				OBSIDIAN_SKULL = new ObsidianSkull(),
				ICE_MIRROR = new MagicMirror("ice_mirror").setMaterial(),
				PRESENT = new Present(new Properties().group(ItemGroup.MISC), "present"),
				OBSIDIAN_BRICK = new ItemBlockT(BlocksT.OBSIDIAN_BRICK, "obsidian_brick"),
				OBSIDIAN_CHEST = new ItemBlockT(BlocksT.OBSIDIAN_CHEST, "obsidian_chest"),
				OBSIDIAN_PLATFORM = new ItemBlockT(BlocksT.OBSIDIAN_PLATFORM, "obsidian_platform"),
				RYORAMAS_BLADE = new BasicBroadsword(65, 4, 24, 25000, "ryoramas_blade").setTooltip("Made with pure green diamond"),
				OBSIDIAN_VASE = new ItemBlockT(BlocksT.OBSIDIAN_VASE, "obsidian_vase").setBuySell(60),
				OBSIDIAN_LAMP = new ItemBlockT(BlocksT.OBSIDIAN_LAMP, "obsidian_lamp").setBuySell(100),
				OBSIDIAN_PIANO = new ItemBlockT(BlocksT.OBSIDIAN_PIANO, "obsidian_piano").setBuySell(60),
				OBSIDIAN_DOOR = new ItemBlockT(BlocksT.OBSIDIAN_DOOR, "obsidian_door"),
				OBSIDIAN_CHANDELIER = new ItemBlockT(BlocksT.OBSIDIAN_CHANDELIER, "obsidian_chandelier"),
				OBSIDIAN_LANTERN = new ItemBlockT(BlocksT.OBSIDIAN_LANTERN, "obsidian_lantern"),
						OBSIDIAN_CANDELABRA = new ItemBlockT(BlocksT.OBSIDIAN_CANDELABRA, "obsidian_candelabra"),
				OBSIDIAN_CANDLE = new ItemBlockT(BlocksT.OBSIDIAN_CANDLE, "obsidian_candle")


				);
		Recipes.addAllRecipes();
		
	}
}
