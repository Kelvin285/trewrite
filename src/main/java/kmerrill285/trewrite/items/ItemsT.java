package kmerrill285.trewrite.items;

import java.util.HashMap;

import kmerrill285.featurescript.FeatureScript;
import kmerrill285.trewrite.blocks.Bed;
import kmerrill285.trewrite.blocks.BlocksT;
import kmerrill285.trewrite.crafting.Recipes;
import kmerrill285.trewrite.events.ScoreboardEvents;
import kmerrill285.trewrite.items.accessories.Accessory;
import kmerrill285.trewrite.items.accessories.Accessory.WearSlot;
import kmerrill285.trewrite.items.basic.BasicBroadsword;
import kmerrill285.trewrite.items.basic.BasicItem;
import kmerrill285.trewrite.items.terraria.accessories.BandOfStarpower;
import kmerrill285.trewrite.items.terraria.accessories.CloudInABottle;
import kmerrill285.trewrite.items.terraria.accessories.HermesBoots;
import kmerrill285.trewrite.items.terraria.accessories.ObsidianSkull;
import kmerrill285.trewrite.items.terraria.arrows.FlamingArrow;
import kmerrill285.trewrite.items.terraria.arrows.HellfireArrow;
import kmerrill285.trewrite.items.terraria.arrows.JestersArrow;
import kmerrill285.trewrite.items.terraria.arrows.UnholyArrow;
import kmerrill285.trewrite.items.terraria.arrows.WoodenArrow;
import kmerrill285.trewrite.items.terraria.axes.CopperAxe;
import kmerrill285.trewrite.items.terraria.axes.GoldAxe;
import kmerrill285.trewrite.items.terraria.axes.IronAxe;
import kmerrill285.trewrite.items.terraria.axes.MeteorHamaxe;
import kmerrill285.trewrite.items.terraria.axes.MoltenHamaxe;
import kmerrill285.trewrite.items.terraria.axes.SilverAxe;
import kmerrill285.trewrite.items.terraria.axes.WarAxeOfTheNight;
import kmerrill285.trewrite.items.terraria.boomerangs.EnchantedBoomerang;
import kmerrill285.trewrite.items.terraria.boss_summon.SuspiciousLookingEye;
import kmerrill285.trewrite.items.terraria.bows.CopperBow;
import kmerrill285.trewrite.items.terraria.bows.DemonBow;
import kmerrill285.trewrite.items.terraria.bows.GoldBow;
import kmerrill285.trewrite.items.terraria.bows.IronBow;
import kmerrill285.trewrite.items.terraria.bows.MoltenFury;
import kmerrill285.trewrite.items.terraria.bows.SilverBow;
import kmerrill285.trewrite.items.terraria.bows.WoodenBow;
import kmerrill285.trewrite.items.terraria.broadswords.CactusSword;
import kmerrill285.trewrite.items.terraria.broadswords.CopperBroadsword;
import kmerrill285.trewrite.items.terraria.broadswords.FieryGreatsword;
import kmerrill285.trewrite.items.terraria.broadswords.GoldBroadsword;
import kmerrill285.trewrite.items.terraria.broadswords.IronBroadsword;
import kmerrill285.trewrite.items.terraria.broadswords.LightsBane;
import kmerrill285.trewrite.items.terraria.broadswords.SilverBroadsword;
import kmerrill285.trewrite.items.terraria.broadswords.Tekhaira;
import kmerrill285.trewrite.items.terraria.bullet.MeteorShot;
import kmerrill285.trewrite.items.terraria.bullet.MusketBall;
import kmerrill285.trewrite.items.terraria.bullet.SilverBullet;
import kmerrill285.trewrite.items.terraria.clickable.Coin;
import kmerrill285.trewrite.items.terraria.flails.BallOHurt;
import kmerrill285.trewrite.items.terraria.guns.Handgun;
import kmerrill285.trewrite.items.terraria.guns.Musket;
import kmerrill285.trewrite.items.terraria.guns.PhoenixBlaster;
import kmerrill285.trewrite.items.terraria.hammers.CopperHammer;
import kmerrill285.trewrite.items.terraria.hammers.GoldHammer;
import kmerrill285.trewrite.items.terraria.hammers.IronHammer;
import kmerrill285.trewrite.items.terraria.hammers.SilverHammer;
import kmerrill285.trewrite.items.terraria.hammers.WoodenHammer;
import kmerrill285.trewrite.items.terraria.loot_bags.Present;
import kmerrill285.trewrite.items.terraria.magic_weapons.SpaceGun;
import kmerrill285.trewrite.items.terraria.magic_weapons.Vilethorn;
import kmerrill285.trewrite.items.terraria.pets.ShadowOrbItem;
import kmerrill285.trewrite.items.terraria.picks.BonePickaxe;
import kmerrill285.trewrite.items.terraria.picks.CactusPickaxe;
import kmerrill285.trewrite.items.terraria.picks.CopperPickaxe;
import kmerrill285.trewrite.items.terraria.picks.GoldPickaxe;
import kmerrill285.trewrite.items.terraria.picks.IronPickaxe;
import kmerrill285.trewrite.items.terraria.picks.MoltenPickaxe;
import kmerrill285.trewrite.items.terraria.picks.SilverPickaxe;
import kmerrill285.trewrite.items.terraria.picks.Starbreaker;
import kmerrill285.trewrite.items.terraria.potions.DefaultPotion;
import kmerrill285.trewrite.items.terraria.potions.PotionTest;
import kmerrill285.trewrite.items.terraria.shortswords.CopperShortsword;
import kmerrill285.trewrite.items.terraria.shortswords.GoldShortsword;
import kmerrill285.trewrite.items.terraria.shortswords.IronShortsword;
import kmerrill285.trewrite.items.terraria.shortswords.SilverShortsword;
import kmerrill285.trewrite.items.terraria.summoning.ImpStaff;
import kmerrill285.trewrite.items.terraria.throwable.Bomb;
import kmerrill285.trewrite.items.terraria.throwable.Glowstick;
import kmerrill285.trewrite.items.terraria.throwable.Grenade;
import kmerrill285.trewrite.items.terraria.throwable.Shuriken;
import kmerrill285.trewrite.items.terraria.tools.MagicMirror;
import kmerrill285.trewrite.network.NetworkHandler;
import kmerrill285.trewrite.network.client.CPacketChangeScore;
import kmerrill285.trewrite.world.WorldStateHolder;
import kmerrill285.trewrite.world.dimension.Dimensions;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraft.item.ItemGroup;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.scoreboard.Score;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.Heightmap;
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
	public static ItemT OBSIDIAN_CHAIR;
	
	public static ItemT OBSIDIAN_DOOR;
	public static ItemT OBSIDIAN_CHANDELIER;
	public static ItemT OBSIDIAN_LANTERN;
	public static ItemT OBSIDIAN_CANDELABRA;
	public static ItemT OBSIDIAN_CANDLE;
	public static ItemT OBSIDIAN_BED;
	public static ItemT OBSIDIAN_CLOCK;
	public static ItemT OBSIDIAN_SOFA, OBSIDIAN_WORKBENCH, OBSIDIAN_TABLE, OBSIDIAN_SINK, OBSIDIAN_BATHTUB, OBSIDIAN_DRESSER;

	public static ItemT HEALING_POTION;

	public static ItemT COPPER_COIN, SILVER_COIN, GOLD_COIN, PLATINUM_COIN;

	public static ItemT BUILDER_POTION, CALMING_POTION, IRONSKIN_POTION,
	SWIFTNESS_POTION, NIGHT_OWL_POTION, RECALL_POTION, GILLS_POTION,
	REGENERATION_POTION, MINING_POTION, ARCHERY_POTION, HUNTER_POTION,
	FEATHERFALL_POTION, FLIPPER_POTION, GRAVITATION_POTION,
	HEARTREACH_POTION, INVISIBILITY_POTION, THORNS_POTION,
	WATER_WALKING_POTION, SHINE_POTION, BATTLE_POTION, OBSIDIAN_SKIN_POTION,
	MAGIC_POWER_POTION, MANA_REGENERATION_POTION, TITAN_POTION, WORMHOLE_POTION;
	
	public static ItemT HELLFIRE_ARROW;
	public static ItemT GRENADE;
	public static ItemT GLOWSTICK;
	public static ItemT BOMB;
	public static ItemT ROPE;
	public static ItemT ROPE_COIL;
	
	public static ItemT MUSKET_BALL;
	public static ItemT MUSKET;
	
	public static ItemT SHADOW_ORB_ITEM;
	
	public static ItemT OBSIDIAN_BOOKCASE;
	
	public static ItemT MANA_CRYSTAL;
	public static ItemT VILETHORN;
	
	public static ItemT BALL_O_HURT;
	public static ItemT BAND_OF_STARPOWER;
	
	public static ItemT TEKHAIRA;
	
	public static ItemT FIERY_GREATSWORD;
	public static ItemT HELLSTONE_BAR;
	
	public static ItemT MOLTEN_FURY;
	public static ItemT PHOENIX_BLASTER;
	public static ItemT HANDGUN;
	public static ItemT MOLTEN_HAMAXE;
	public static ItemT MOLTEN_PICKAXE;
	
	public static ItemT ENCHANTED_BOOMERANG;
	
	public static ItemT IMP_STAFF;
	
	public static ItemT COPPER_BOW, GOLD_BOW, IRON_BOW, SILVER_BOW;
	public static ItemT CACTUS_PICKAXE, CACTUS_SWORD;
	public static ItemT GOLD_PICKAXE, GOLD_AXE, GOLD_HAMMER, GOLD_BROADSWORD, GOLD_SHORTSWORD;
	public static ItemT SILVER_PICKAXE, SILVER_AXE, SILVER_HAMMER, SILVER_BROADSWORD, SILVER_SHORTSWORD;
	public static ItemT SILVER_BULLET;
	public static ItemT WOODEN_HAMMER;
	
	public static ItemT METEORITE;
	public static ItemT METEORITE_BAR;
	
	public static ItemT SPACE_GUN;

	public static ItemT WOODEN_HELMET;
	public static ItemT WOODEN_CHESTPLATE;
	public static ItemT WOODEN_GREAVES;
	
	public static ItemT RICH_MAHOGANY_HELMET, RICH_MAHOGANY_BREASTPLATE, RICH_MAHOGANY_GREAVES;
	public static ItemT MINING_HELMET, MINING_SHIRT, MINING_PANTS;
	public static ItemT SILVER_HELMET, SILVER_CHESTPLATE, SILVER_GREAVES;
	
	public static ItemT HOOK;
	public static ItemT BONE_PICKAXE;
	
	public static ItemT STARBREAKER;
	
	public static ItemT METEOR_HELMET, METEOR_SUIT, METEOR_LEGGINGS;
	public static ItemT METEOR_HAMAXE;
	public static ItemT METEOR_SHOT;


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
				TORCH = (WallOrFloorBlock) new WallOrFloorBlock(BlocksT.TORCH, BlocksT.TORCH_WALL, "torch").setMaxStack(99).setLightValue(15),
				BLUE_TORCH = (WallOrFloorBlock) new WallOrFloorBlock(BlocksT.BLUE_TORCH, BlocksT.BLUE_TORCH_WALL, "blue_Torch").setMaxStack(99).setLightValue(6),
				GREEN_TORCH = (WallOrFloorBlock) new WallOrFloorBlock(BlocksT.GREEN_TORCH, BlocksT.GREEN_TORCH_WALL, "green_torch").setMaxStack(99).setLightValue(7),
				YELLOW_TORCH = (WallOrFloorBlock) new WallOrFloorBlock(BlocksT.YELLOW_TORCH, BlocksT.YELLOW_TORCH_WALL, "yellow_torch").setMaxStack(99).setLightValue(8),
				RED_TORCH = (WallOrFloorBlock) new WallOrFloorBlock(BlocksT.RED_TORCH, BlocksT.RED_TORCH_WALL, "red_torch").setMaxStack(99).setLightValue(5),
				PURPLE_TORCH = (WallOrFloorBlock) new WallOrFloorBlock(BlocksT.PURPLE_TORCH, BlocksT.PURPLE_TORCH_WALL, "purple_torch").setMaxStack(99).setLightValue(6),
				PINK_TORCH = (WallOrFloorBlock) new WallOrFloorBlock(BlocksT.PINK_TORCH, BlocksT.PINK_TORCH_WALL, "pink_torch").setMaxStack(99).setLightValue(7),
				WHITE_TORCH = (WallOrFloorBlock) new WallOrFloorBlock(BlocksT.WHITE_TORCH, BlocksT.WHITE_TORCH_WALL, "white_torch").setMaxStack(99).setLightValue(12),
				ORANGE_TORCH = (WallOrFloorBlock) new WallOrFloorBlock(BlocksT.ORANGE_TORCH, BlocksT.ORANGE_TORCH_WALL, "orange_torch").setMaxStack(99).setLightValue(7),
				CORRUPT_TORCH = (WallOrFloorBlock) new WallOrFloorBlock(BlocksT.CORRUPT_TORCH, BlocksT.CORRUPT_TORCH_WALL, "corrupt_torch").setMaxStack(99).setLightValue(5),
				CURSED_TORCH = (WallOrFloorBlock) new WallOrFloorBlock(BlocksT.CURSED_TORCH, BlocksT.CURSED_TORCH_WALL, "cursed_torch").setMaxStack(99).setLightValue(5),
				ICHOR_TORCH = (WallOrFloorBlock) new WallOrFloorBlock(BlocksT.ICHOR_TORCH, BlocksT.ICHOR_TORCH_WALL, "ichor_torch").setMaxStack(99).setLightValue(10),
				RAINBOW_TORCH = (WallOrFloorBlock) new WallOrFloorBlock(BlocksT.RAINBOW_TORCH, BlocksT.RAINBOW_TORCH_WALL, "rainbow_torch").setMaxStack(99).setLightValue(12),
				BONE_TORCH = (WallOrFloorBlock) new WallOrFloorBlock(BlocksT.BONE_TORCH, BlocksT.BONE_TORCH_WALL, "bone_torch").setMaxStack(99).setLightValue(7),
				ULTRABRIGHT_TORCH = (WallOrFloorBlock) new WallOrFloorBlock(BlocksT.ULTRABRIGHT_TORCH, BlocksT.ULTRABRIGHT_TORCH_WALL, "ultrabright_torch").setMaxStack(99).setLightValue(7),
				ICE_TORCH = (WallOrFloorBlock) new WallOrFloorBlock(BlocksT.ICE_TORCH, BlocksT.ICE_TORCH_WALL, "ice_torch").setMaxStack(99).setLightValue(7),
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
				GOGGLES = (Armor) new Armor(new Properties().group(ItemGroup.COMBAT), "goggles", Armor.ArmorType.HEAD, Armor.RenderType.EYES, 1, 128, 64, false).setMaxStack(1).setBuySell(200),
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
				OBSIDIAN_CANDLE = new ItemBlockT(BlocksT.OBSIDIAN_CANDLE, "obsidian_candle").setLightValue(3),
				OBSIDIAN_BED = new ItemBlockT(BlocksT.OBSIDIAN_BED, "obsidian_bed"),
				OBSIDIAN_CLOCK = new ItemBlockT(BlocksT.OBSIDIAN_CLOCK, "obsidian_clock"),
				OBSIDIAN_WORKBENCH = new ItemBlockT(BlocksT.OBSIDIAN_WORKBENCH, "obsidian_workbench"),
				OBSIDIAN_TABLE = new ItemBlockT(BlocksT.OBSIDIAN_TABLE, "obsidian_table"),
				OBSIDIAN_DRESSER = new ItemBlockT(BlocksT.OBSIDIAN_DRESSER, "obsidian_dresser"),
				OBSIDIAN_CHAIR = new ItemBlockT(BlocksT.OBSIDIAN_CHAIR, "obsidian_chair"),
				OBSIDIAN_SOFA = new ItemBlockT(BlocksT.OBSIDIAN_SOFA, "obsidian_sofa"),
				OBSIDIAN_SINK = new ItemBlockT(BlocksT.OBSIDIAN_SINK, "obsidian_sink"),
				OBSIDIAN_BATHTUB = new ItemBlockT(BlocksT.OBSIDIAN_BATHTUB, "obsidian_bathtub"),
				HEALING_POTION = new ItemT(new Properties().group(ItemGroup.BREWING), "healing_potion").setMaterial().setConsumable().setPotionSickness(60).setHeal(100).setMaxStack(30).setBuySell(200),
				COPPER_COIN = new Coin(1, "copper_coin"),
				SILVER_COIN = new Coin(100, "silver_coin"),
				GOLD_COIN = new Coin(10000, "gold_coin"),
				PLATINUM_COIN = new Coin(1000000, "platinum_coin", 999),
				BUILDER_POTION = new PotionTest(new Properties().group(ItemGroup.BREWING), "builder_potion", true, true) {
					@Override
					protected boolean doPotionStuff(World world, PlayerEntity player) {
						if (!world.isRemote()) {
							Score score = ScoreboardEvents.getScore(world.getScoreboard(), player, ScoreboardEvents.BUILDER);
							score.setScorePoints(15*60*20);
						} else {
							NetworkHandler.INSTANCE.sendToServer(new CPacketChangeScore(ScoreboardEvents.BUILDER, 15*60*20));
						}
						
						return true;
					}
				}.setTooltip("Increases placement speed and range").setBuySell(200),
				RECALL_POTION = new PotionTest(new Properties().group(ItemGroup.BREWING), "recall_potion", true, false) {
					@Override
					protected boolean doPotionStuff(World worldIn, PlayerEntity playerIn) {
						if (ScoreboardEvents.getScore(playerIn.getWorldScoreboard(), playerIn, ScoreboardEvents.HORRIFIED).getScorePoints() > 0) {
							 playerIn.onKillCommand();
						 }
						if (!worldIn.isRemote) {
						      worldIn.playSound((PlayerEntity)null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ITEM_TRIDENT_THUNDER, SoundCategory.PLAYERS, 1.0F, 0.5F);
						      {
						    	  BlockPos bedPos = playerIn.getBedLocation(playerIn.getSpawnDimension());
									 if (bedPos == null) {
										 bedPos = worldIn.getSpawnPoint();
										 System.out.println(bedPos);
									 }
								  worldIn.playSound((PlayerEntity)null, bedPos.getX(), bedPos.getY(), bedPos.getZ(), SoundEvents.ITEM_TRIDENT_THUNDER, SoundCategory.PLAYERS, 1.0F, 0.5F);

						      }
						     
						     BlockPos bedPos = playerIn.getBedLocation(playerIn.getSpawnDimension());
							 if (bedPos == null) {
								 bedPos = worldIn.getSpawnPoint();
								 System.out.println(bedPos);
							 }
							 playerIn.addPotionEffect(new EffectInstance(Effects.BLINDNESS, 1 * 20, 1, true, true));
							 
							 
							try {
							 if (playerIn instanceof ServerPlayerEntity)
							 if (playerIn.dimension != playerIn.getSpawnDimension())
								 Dimensions.teleportPlayer((ServerPlayerEntity)playerIn, playerIn.getSpawnDimension(), bedPos);
							 
							 HashMap<String, BlockPos> spawns = WorldStateHolder.get(worldIn.getServer().getWorld(DimensionType.OVERWORLD)).spawnPositions;
							 BlockPos spawn = spawns.get(playerIn.getScoreboardName());
							 if (spawn == null || !(worldIn.getBlockState(spawn).getBlock() instanceof Bed)) {
								 int Y = playerIn.world.getChunkAt(bedPos).getTopBlockY(Heightmap.Type.MOTION_BLOCKING, bedPos.getX() % 15, bedPos.getZ() % 15);

								 playerIn.setPositionAndUpdate(bedPos.getX(), Y+1, bedPos.getZ());
								 playerIn.setSpawnDimenion(DimensionType.OVERWORLD);
							 } else {
								 playerIn.setPositionAndUpdate(spawn.getX(), spawn.getY(), spawn.getZ());
								 playerIn.world.getChunkAt(spawn).getTopBlockY(Heightmap.Type.MOTION_BLOCKING, spawn.getX() % 15, spawn.getZ() % 15);
							 }
							 
							 
							 
							 
							 
							}catch (Exception e) {
								e.printStackTrace();
							}
							 playerIn.noClip = false;
						 }
						
						return true;
					}
				}.setTooltip("Teleports you home"),
				CALMING_POTION = new PotionTest(new Properties().group(ItemGroup.BREWING), "calming_potion", true, true) {
					@Override
					protected boolean doPotionStuff(World world, PlayerEntity player) {
						if (!world.isRemote()) {
							Score score = ScoreboardEvents.getScore(world.getScoreboard(), player, ScoreboardEvents.CALMING);
							score.setScorePoints(15*60*20);
						} else {
							NetworkHandler.INSTANCE.sendToServer(new CPacketChangeScore(ScoreboardEvents.CALMING, 15*60*20));
						}
						
						return true;
					}
				}.setTooltip("Reduced enemy aggression"),
				IRONSKIN_POTION = new DefaultPotion(new Properties().group(ItemGroup.BREWING), "ironskin_potion", true, true, ScoreboardEvents.IRONSKIN, 5*60).setTooltip("Increase defense by 8"),
				SWIFTNESS_POTION = new DefaultPotion(new Properties().group(ItemGroup.BREWING), "swiftness_potion", true, true, ScoreboardEvents.SWIFTNESS, 5*60).setTooltip("25% increased movement speed"),
				NIGHT_OWL_POTION = new DefaultPotion(new Properties().group(ItemGroup.BREWING), "night_owl_potion", true, true, ScoreboardEvents.NIGHT_OWL, 4*60).setTooltip("Increases night vision"),
				REGENERATION_POTION = new DefaultPotion(new Properties().group(ItemGroup.BREWING), "regeneration_potion", true, true, ScoreboardEvents.REGENERATION, 5*60).setTooltip("Provides life regeneration"),
				MINING_POTION = new DefaultPotion(new Properties().group(ItemGroup.BREWING), "mining_potion", true, true, ScoreboardEvents.MINING, 8*60).setTooltip("Increases mining speed by 25%"),
				ARCHERY_POTION = new DefaultPotion(new Properties().group(ItemGroup.BREWING), "archery_potion", true, true, ScoreboardEvents.ARCHERY, 4*60).setTooltip("25% increased arrow speed and damage"),
				HUNTER_POTION = new DefaultPotion(new Properties().group(ItemGroup.BREWING), "hunter_potion", true, true, ScoreboardEvents.HUNTER, 5*60).setTooltip("Shows the location of enemies"),
				FEATHERFALL_POTION = new DefaultPotion(new Properties().group(ItemGroup.BREWING), "featherfall_potion", true, true, ScoreboardEvents.FEATHERFALL, 5*60).setTooltip("Press SPACE or SHIFT to control speed of descent"),
				FLIPPER_POTION = new DefaultPotion(new Properties().group(ItemGroup.BREWING), "flipper_potion", true, true, ScoreboardEvents.FLIPPER, 8*60).setTooltip("Lets you move swiftly in liquids"),
				GRAVITATION_POTION = new DefaultPotion(new Properties().group(ItemGroup.BREWING), "gravitation_potion", true, true, ScoreboardEvents.GRAVITATION, 3*60).setTooltip("Double-tap SPACE to reverse gravity"),
				HEARTREACH_POTION = new DefaultPotion(new Properties().group(ItemGroup.BREWING), "heartreach_potion", true, true, ScoreboardEvents.HEARTREACH, 8*60).setTooltip("Increases pickup range for life hearts"),
				INVISIBILITY_POTION = new DefaultPotion(new Properties().group(ItemGroup.BREWING), "invisibility_potion", true, true, ScoreboardEvents.INVISIBILITY, 2*60).setTooltip("Grants invisibilitly"),
				THORNS_POTION = new DefaultPotion(new Properties().group(ItemGroup.BREWING), "thorns_potion", true, true, ScoreboardEvents.THORNS, 2*60).setTooltip("Attackers also take damage"),
				WATER_WALKING_POTION = new DefaultPotion(new Properties().group(ItemGroup.BREWING), "water_walking_potion", true, true, ScoreboardEvents.WATER_WALKING, 5*60).setTooltip("Allows the ability to walk on water"),
				BATTLE_POTION = new DefaultPotion(new Properties().group(ItemGroup.BREWING), "battle_potion", true, true, ScoreboardEvents.BATTLE, 7*60).setTooltip("Increases enemy spawn rate"),
				SHINE_POTION = new DefaultPotion(new Properties().group(ItemGroup.BREWING), "shine_potion", true, true, ScoreboardEvents.SHINE, 5*60).setTooltip("Emits an aura of light"),
				OBSIDIAN_SKIN_POTION = new DefaultPotion(new Properties().group(ItemGroup.BREWING), "obsidian_skin_potion", true, true, ScoreboardEvents.OBSIDIAN_SKIN, 4*60).setTooltip("Provides immunity to lava"),
				MAGIC_POWER_POTION = new DefaultPotion(new Properties().group(ItemGroup.BREWING), "magic_power_potion", true, true, ScoreboardEvents.MAGIC_POWER, 2*60).setTooltip("20% increased magic damage"),
				TITAN_POTION = new DefaultPotion(new Properties().group(ItemGroup.BREWING), "titan_potion", true, true, ScoreboardEvents.TITAN, 2*60).setTooltip("Increases knockback"),
				MANA_REGENERATION_POTION = new DefaultPotion(new Properties().group(ItemGroup.BREWING), "mana_regeneration_potion", true, true, ScoreboardEvents.MANA_REGENERATION, 7*60).setTooltip("Increased mana regeneration"),
				GILLS_POTION = new DefaultPotion(new Properties().group(ItemGroup.BREWING), "gills_potion", true, true, ScoreboardEvents.GILLS, 2*60).setTooltip("Breathe underwater"),

				WORMHOLE_POTION = new ItemT(new Properties().group(ItemGroup.BREWING), "wormhole_potion").setMaterial().setMaxStack(30).setBuySell(200).setTooltip("Use /wh <player> to teleport"),
				HELLFIRE_ARROW = new HellfireArrow(),
				GRENADE = new Grenade().setTooltip("A small explosion that will not destroy tiles"),
				GLOWSTICK = new Glowstick(),
				ROPE = new ItemBlockT(BlocksT.ROPE, "rope"),
				ROPE_COIL = new RopeCoil(),
				BOMB = new Bomb(),
				MUSKET_BALL = new MusketBall(),
				MUSKET = new Musket(),
				SHADOW_ORB_ITEM = new ShadowOrbItem(),
				OBSIDIAN_BOOKCASE = new ItemBlockT(BlocksT.OBSIDIAN_BOOKCASE, "obsidian_bookcase"),
				MANA_CRYSTAL = new PotionTest(new Properties().group(ItemGroup.MISC), "mana_crystal", true, false) {
					@Override
					protected boolean doPotionStuff(World world, PlayerEntity player) {
						if (!world.isRemote()) {
							Score score = ScoreboardEvents.getScore(world.getScoreboard(), player, ScoreboardEvents.MAX_MANA);
							if (score.getScorePoints() < 200) {
								score.increaseScore(20);
								return true;
							} else return false;
						} else {
							Score score = ScoreboardEvents.getScore(world.getScoreboard(), player, ScoreboardEvents.MAX_MANA);
							if (score.getScorePoints() < 200) {
								NetworkHandler.INSTANCE.sendToServer(new CPacketChangeScore(ScoreboardEvents.MAX_MANA, score.getScorePoints() + 20));
								return true;
							} else return false;
						}
					}
				}.setTooltip("Increases maximum mana by 20").setMaxStack(99),
				VILETHORN = new Vilethorn().setBuySell(2000).setTooltip("Summons a vile thorn"),
				BALL_O_HURT = new BallOHurt(),
				BAND_OF_STARPOWER = new BandOfStarpower(),
				TEKHAIRA = new Tekhaira(),
				FIERY_GREATSWORD = new FieryGreatsword().setLightValue(5),
				HELLSTONE_BAR = new MetalBar(4000, "hellstone_bar"),
				MOLTEN_FURY = new MoltenFury(),
				PHOENIX_BLASTER = new PhoenixBlaster(),
				HANDGUN = new Handgun(),
				MOLTEN_HAMAXE = new MoltenHamaxe().setLightValue(5),
				MOLTEN_PICKAXE = new MoltenPickaxe().setLightValue(5),
				ENCHANTED_BOOMERANG = new EnchantedBoomerang(),
				IMP_STAFF = new ImpStaff(),
				COPPER_BOW = new CopperBow(),
				SILVER_BOW = new SilverBow(),
				GOLD_BOW = new GoldBow(),
				IRON_BOW = new IronBow(),
				CACTUS_PICKAXE = new CactusPickaxe(),
				CACTUS_SWORD = new CactusSword(),
				GOLD_PICKAXE = new GoldPickaxe(),
				GOLD_AXE = new GoldAxe(),
				GOLD_HAMMER = new GoldHammer(),
				GOLD_BROADSWORD = new GoldBroadsword(),
				GOLD_SHORTSWORD = new GoldShortsword(),
				SILVER_AXE = new SilverAxe(),
				SILVER_PICKAXE = new SilverPickaxe(),
				SILVER_HAMMER = new SilverHammer(),
				SILVER_BROADSWORD = new SilverBroadsword(),
				SILVER_SHORTSWORD = new SilverShortsword(),
				SILVER_BULLET = new SilverBullet(),
				WOODEN_HAMMER = new WoodenHammer(),
				METEORITE = new ItemBlockT(BlocksT.METEORITE, "meteorite").setBuySell(200),
				METEORITE_BAR = new MetalBar(1400, "meteorite_bar"),
				SPACE_GUN = new SpaceGun(),
				WOODEN_HELMET = (Armor) new Armor(new Properties().group(ItemGroup.COMBAT), "wooden_helmet", Armor.ArmorType.HEAD, Armor.RenderType.HEAD, 1, 128, 64, false).setTooltip("Set bonus: +1 defense"),
				WOODEN_CHESTPLATE = (Armor) new Armor(new Properties().group(ItemGroup.COMBAT), "wooden_chestplate", Armor.ArmorType.CHEST, Armor.RenderType.SHIRT, 1, 128, 64, false).setTooltip("Set bonus: +1 defense"),
				WOODEN_GREAVES = (Armor) new Armor(new Properties().group(ItemGroup.COMBAT), "wooden_greaves", Armor.ArmorType.LEGS, Armor.RenderType.PANTS, 0, 64, 32, true).setTooltip("Set bonus: +1 defense"),
				RICH_MAHOGANY_HELMET = (Armor) new Armor(new Properties().group(ItemGroup.COMBAT), "rich_mahogany_helmet", Armor.ArmorType.HEAD, Armor.RenderType.HEAD, 1, 128, 64, false).setTooltip("Set bonus: +1 defense"),
				RICH_MAHOGANY_BREASTPLATE = (Armor) new Armor(new Properties().group(ItemGroup.COMBAT), "rich_mahogany_breastplate", Armor.ArmorType.CHEST, Armor.RenderType.SHIRT, 1, 128, 64, false).setTooltip("Set bonus: +1 defense"),
				RICH_MAHOGANY_GREAVES = (Armor) new Armor(new Properties().group(ItemGroup.COMBAT), "rich_mahogany_greaves", Armor.ArmorType.LEGS, Armor.RenderType.PANTS, 1, 64, 32, true).setTooltip("Set bonus: +1 defense"),
				MINING_HELMET = (Armor) new Armor(new Properties().group(ItemGroup.COMBAT), "mining_helmet", Armor.ArmorType.HEAD, Armor.RenderType.HEAD, 1, 128, 64, false).setTooltip("Provides light when worn.\nSet bonus: +30% mining speed"),
				MINING_SHIRT = (Armor) new Armor(new Properties().group(ItemGroup.COMBAT), "mining_shirt", Armor.ArmorType.CHEST, Armor.RenderType.SHIRT, 1, 128, 64, false).setTooltip("Set bonus: +30% mining speed"),
				MINING_PANTS = (Armor) new Armor(new Properties().group(ItemGroup.COMBAT), "mining_pants", Armor.ArmorType.LEGS, Armor.RenderType.PANTS, 1, 64, 32, true).setTooltip("Set bonus: +30% mining speed"),
				HOOK = new ItemT(new Properties().group(ItemGroup.MATERIALS), "hook").setMaterial().setTooltip("Sometimes dropped by Skeletons and Piranha").setBuySell(200).setMaxStack(99),
				BONE_PICKAXE = new BonePickaxe(),
				SILVER_HELMET = (Armor) new Armor(new Properties().group(ItemGroup.COMBAT), "silver_helmet", Armor.ArmorType.HEAD, Armor.RenderType.HEAD, 3, 128, 64, false).setTooltip("Set bonus: +3 defense").setBuySell(1500),
				SILVER_CHESTPLATE = (Armor) new Armor(new Properties().group(ItemGroup.COMBAT), "silver_chestplate", Armor.ArmorType.CHEST, Armor.RenderType.SHIRT, 4, 128, 64, false).setTooltip("Set bonus: +3 defense").setBuySell(2500),
				SILVER_GREAVES = (Armor) new Armor(new Properties().group(ItemGroup.COMBAT), "silver_greaves", Armor.ArmorType.LEGS, Armor.RenderType.PANTS, 3, 64, 32, true).setTooltip("Set bonus: +3 defense").setBuySell(2000),
				STARBREAKER = new Starbreaker(),
				METEOR_HELMET = (Armor) new Armor(new Properties().group(ItemGroup.COMBAT), "meteor_helmet", Armor.ArmorType.HEAD, Armor.RenderType.HEAD, 5, 128, 64, false).setTooltip("7% increased magic damage\nSet bonus: Space Gun uses zero mana").setBuySell(9000),
				METEOR_SUIT = (Armor) new Armor(new Properties().group(ItemGroup.COMBAT), "meteor_suit", Armor.ArmorType.CHEST, Armor.RenderType.SHIRT, 6, 128, 64, false).setTooltip("7% increased magic damage\nSet bonus: Space Gun uses zero mana").setBuySell(6000),
				METEOR_LEGGINGS = (Armor) new Armor(new Properties().group(ItemGroup.COMBAT), "meteor_leggings", Armor.ArmorType.LEGS, Armor.RenderType.PANTS, 5, 64, 32, true).setTooltip("7% increased magic damage\nSet bonus: Space Gun uses zero mana").setBuySell(6000),
				METEOR_HAMAXE = new MeteorHamaxe(),
				METEOR_SHOT = new MeteorShot()




				);
		for (String str : FeatureScript.items.keySet()) {
			event.getRegistry().register(new FeatureScriptItem(str, FeatureScript.items.get(str)));
		}
		
		Recipes.addAllRecipes();
		
	}
}
