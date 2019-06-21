package kmerrill285.trewrite.blocks;

import kmerrill285.trewrite.blocks.CrossedBlock.Shape;
import kmerrill285.trewrite.blocks.ores.CopperOre;
import kmerrill285.trewrite.blocks.ores.GoldOre;
import kmerrill285.trewrite.blocks.ores.IronOre;
import kmerrill285.trewrite.blocks.ores.SilverOre;
import net.minecraft.block.Block;
import net.minecraft.block.Block.Properties;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class BlocksT {
	
	public static BlockT DIRT_BLOCK;
	public static BlockT IRON_ORE;
	public static BasicBlock STONE_BLOCK;
	public static BasicPlant MUSHROOM;
	public static BlockT GOLD_ORE;
	public static BlockT COPPER_ORE;
	public static BlockT SILVER_ORE;
	public static CrossedBlock LIFE_CRYSTAL;
	public static BlockT GRASS_BLOCK;
	public static BlockT GRASS_PATH;
	public static BlockT FURNACE;
	public static BlockT FLOWER;
	public static Torch TORCH;
	public static Torch TORCH_WALL;
	public static Torch BLUE_TORCH;
	public static Torch BLUE_TORCH_WALL;
	public static Torch ICE_TORCH;
	public static Torch ICE_TORCH_WALL;
	public static Torch ORANGE_TORCH;
	public static Torch ORANGE_TORCH_WALL;
	public static Torch PINK_TORCH;
	public static Torch PINK_TORCH_WALL;
	public static Torch RED_TORCH;
	public static Torch RED_TORCH_WALL;
	public static Torch ULTRABRIGHT_TORCH;
	public static Torch ULTRABRIGHT_TORCH_WALL;
	public static Torch WHITE_TORCH;
	public static Torch WHITE_TORCH_WALL;
	public static Torch YELLOW_TORCH;
	public static Torch YELLOW_TORCH_WALL;
	public static Torch GREEN_TORCH;
	public static Torch GREEN_TORCH_WALL;
	public static Torch PURPLE_TORCH;
	public static Torch PURPLE_TORCH_WALL;
	public static Torch CURSED_TORCH;
	public static Torch CURSED_TORCH_WALL;
	public static Torch ICHOR_TORCH;
	public static Torch ICHOR_TORCH_WALL;
	public static Torch CORRUPT_TORCH;
	public static Torch CORRUPT_TORCH_WALL;
	public static Torch RAINBOW_TORCH;
	public static Torch RAINBOW_TORCH_WALL;
	public static Torch BONE_TORCH;
	public static Torch BONE_TORCH_WALL;
	public static Tree FOREST_TREE;
	public static Tree CORRUPT_TREE;
	public static Tree CRIMSON_TREE;
	public static Tree BOREAL_TREE;
	public static Tree JUNGLE_TREE;
	public static Tree HALLOWED_TREE;
	public static Tree PALM_TREE;
	public static Door DOOR;
	public static CrossedBlock BOTTLE;
	public static BlockT TABLE;
	public static BlockT CHAIR;
	public static BlockT IRON_ANVIL;
	public static BlockT WORKBENCH;
	public static BlockT WOOD_PLATFORM;
	public static BlockT WOOD;
	
	public static float GROUND_HARDNESS = 10.0f, STONE_HARDNESS = 15.0f, ORE_HARDNESS = 20.0f, DUNGEON_HARDNESS = 50.0f;
	
	@SubscribeEvent
	public static void registerBlocks(final RegistryEvent.Register<Block> event) {
		event.getRegistry().registerAll(
					DIRT_BLOCK = new DirtBlock(Properties.create(Material.GROUND).sound(SoundType.GROUND)).setLocation("dirt_block"),
					IRON_ORE = new IronOre(Properties.create(Material.GROUND).sound(SoundType.STONE)),
					STONE_BLOCK = new BasicBlock(Properties.create(Material.GROUND).sound(SoundType.STONE), STONE_HARDNESS, 15, true, false, false, true, "stone_block", "stone_block"),
					MUSHROOM = (BasicPlant) new BasicPlant(Properties.create(Material.PLANTS).sound(SoundType.PLANT).doesNotBlockMovement(), 0, 0, true, true, true, true, "mushroom", 15, 0, "mushroom").setShape(Shape.MUSHROOM).setPotionSickness(60).setSell(250).setConsumable().setMaterial(),
					GOLD_ORE = new GoldOre(Properties.create(Material.GROUND).sound(SoundType.STONE)),
					COPPER_ORE = new CopperOre(Properties.create(Material.GROUND).sound(SoundType.STONE)),
					SILVER_ORE = new SilverOre(Properties.create(Material.GROUND).sound(SoundType.STONE)),
					LIFE_CRYSTAL = (CrossedBlock) new CrossedBlock(Properties.create(Material.GROUND).sound(SoundType.GLASS).doesNotBlockMovement().lightValue(3), STONE_HARDNESS, 15, true, false, false, true, "life_crystal", "life_crystal").setShape(Shape.BLOCK).setSell(15000).setConsumable().setMaterial().setTooltip("Permanently increases maximum life by 20"),
					GRASS_BLOCK = new GrassBlock(Properties.create(Material.GROUND).sound(SoundType.GROUND)).setLocation("grass_block"),
					GRASS_PATH = new GrassPath(Properties.create(Material.GROUND).sound(SoundType.GROUND)).setLocation("grass_path"),
					FURNACE = new BasicDirectional(Properties.create(Material.GROUND).sound(SoundType.STONE), STONE_HARDNESS, 15, true, false, true, false, "furnace", "furnace").setFullCube(false).setFaceShape(BlockFaceShape.UNDEFINED).setSell(60),
					FLOWER = (BasicPlant) new BasicPlant(Properties.create(Material.PLANTS).sound(SoundType.PLANT).doesNotBlockMovement(), 0, 0, true, true, true, false, "flower", "no drop").setShape(Shape.MUSHROOM),
					TORCH = (Torch) new Torch("torch", "torch").setBuy(50),
					TORCH_WALL = new TorchWall("torch", "torch"),
					BLUE_TORCH = (Torch) new Torch("blue_torch", "blue_torch").setSell(40),
					BLUE_TORCH_WALL = new TorchWall("blue_torch", "blue_torch"),
					ICE_TORCH = (Torch) new Torch("ice_torch", "ice_torch").setSell(12),
					ICE_TORCH_WALL = new TorchWall("ice_torch", "ice_torch"),
					ORANGE_TORCH = (Torch) new Torch("orange_torch", "orange_torch").setSell(40),
					ORANGE_TORCH_WALL = new TorchWall("orange_torch", "orange_torch"),
					PINK_TORCH = (Torch) new Torch("pink_torch", "pink_torch").setSell(16),
					PINK_TORCH_WALL = new TorchWall("pink_torch", "pink_torch"),
					RED_TORCH = (Torch) new Torch("red_torch", "red_torch").setSell(40),
					RED_TORCH_WALL = new TorchWall("red_torch", "red_torch"),
					WHITE_TORCH = (Torch) new Torch(12, "white_torch", "white_torch").setSell(100),
					WHITE_TORCH_WALL = new TorchWall(12, "white_torch", "white_torch"),
					YELLOW_TORCH = (Torch) new Torch("yellow_torch", "yellow_torch").setSell(40),
					YELLOW_TORCH_WALL = new TorchWall("yellow_torch", "yellow_torch"),
					GREEN_TORCH = (Torch) new Torch("green_torch", "green_torch").setSell(40),
					GREEN_TORCH_WALL = new TorchWall("green_torch", "green_torch"),
					PURPLE_TORCH = (Torch) new Torch("purple_torch", "purple_torch").setSell(40),
					PURPLE_TORCH_WALL = new TorchWall("purple_torch", "purple_torch"),
					CURSED_TORCH = (Torch) new Torch("cursed_torch", "cursed_torch").setSell(60),
					CURSED_TORCH_WALL = new TorchWall("cursed_torch", "cursed_torch"),
					ICHOR_TORCH = (Torch) new Torch("ichor_torch", "ichor_torch").setSell(66),
					ICHOR_TORCH_WALL = new TorchWall("ichor_torch", "ichor_torch"),
					CORRUPT_TORCH = (Torch) new Torch("corrupt_torch", "corrupt_torch").setSell(60),
					CORRUPT_TORCH_WALL = new TorchWall("corrupt_torch", "corrupt_torch"),
					ULTRABRIGHT_TORCH = (Torch) new Torch(8, "ultrabright_torch", "ultrabright_torch").setSell(60),
					ULTRABRIGHT_TORCH_WALL = new TorchWall(8, "ultrabright_torch", "ultrabright_torch"),
					RAINBOW_TORCH = (Torch) new Torch("rainbow_torch", "rainbow_torch").setSell(60),
					RAINBOW_TORCH_WALL = new TorchWall("rainbow_torch", "rainbow_torch"),
					BONE_TORCH = (Torch) new Torch("bone_torch", "bone_torch").setSell(60),
					BONE_TORCH_WALL = new TorchWall("bone_torch", "bone_torch"),
					FOREST_TREE = (Tree) new Tree(Properties.create(Material.WOOD).sound(SoundType.WOOD), ORE_HARDNESS * 3, 15, false, true, false, false, "trees/forest_tree", "wood").addAllowed("grass_block", "trees/forest_tree"),
					CORRUPT_TREE = (Tree) new Tree(Properties.create(Material.WOOD).sound(SoundType.WOOD), ORE_HARDNESS * 3, 15, false, true, false, false, "trees/corrupt_tree", "ebonwood").addAllowed("trees/corrupt_tree"),
					CRIMSON_TREE = (Tree) new Tree(Properties.create(Material.WOOD).sound(SoundType.WOOD), ORE_HARDNESS * 3, 15, false, true, false, false, "trees/crimson_tree", "shadewood").addAllowed("trees/crimson_tree"),
					HALLOWED_TREE = (Tree) new Tree(Properties.create(Material.WOOD).sound(SoundType.WOOD), ORE_HARDNESS * 3, 15, false, true, false, false, "trees/hallowed_tree", "pearlwood").addAllowed("trees/hallowed_tree"),
					JUNGLE_TREE = (Tree) new Tree(Properties.create(Material.WOOD).sound(SoundType.WOOD), ORE_HARDNESS * 3, 15, false, true, false, false, "trees/jungle_tree", "rich_mahogany").addAllowed("trees/jungle_tree"),
					BOREAL_TREE = (Tree) new Tree(Properties.create(Material.WOOD).sound(SoundType.WOOD), ORE_HARDNESS * 3, 15, false, true, false, false, "trees/boreal_tree", "boreal_wood").addAllowed("trees/boreal_tree"),
					PALM_TREE = (Tree) new Tree(Properties.create(Material.WOOD).sound(SoundType.WOOD), ORE_HARDNESS * 3, 15, false, true, false, false, "trees/palm_tree", "palm_wood").addAllowed("trees/palm_tree"),
					DOOR = (Door) new Door(GROUND_HARDNESS, 15, "door").setSell(40),
					BOTTLE = (CrossedBlock) new CrossedBlock(Properties.create(Material.GROUND).sound(SoundType.GLASS).doesNotBlockMovement(), 0, 0, true, true, false, true, "bottle", "bottle").setShape(Shape.MUSHROOM).setMaterial(),
					TABLE = new BasicBlock(Properties.create(Material.GROUND).sound(SoundType.WOOD), 15, 15, true, true, true, false, "table", "table").setFaceShape(BlockFaceShape.UNDEFINED).setSell(15),
					CHAIR = new BasicDirectional(Properties.create(Material.GROUND).sound(SoundType.WOOD), 15, 15, true, true, true, false, "chair", "chair").setFullCube(false).setFaceShape(BlockFaceShape.UNDEFINED).setSell(10).setShape("platform_bottom"),
					IRON_ANVIL = new BasicDirectional(Properties.create(Material.GROUND).sound(SoundType.ANVIL), 15, 15, true, true, true, false, "iron_anvil", "iron_anvil").setFullCube(false).setFaceShape(BlockFaceShape.UNDEFINED).setSell(1000),
					WORKBENCH = new BasicBlock(Properties.create(Material.GROUND).sound(SoundType.WOOD), 15, 15, true, true, true, false, "workbench", "workbench").setFaceShape(BlockFaceShape.UNDEFINED).setSell(30),
					WOOD_PLATFORM = new Platform(Properties.create(Material.GROUND).sound(SoundType.WOOD), true, "wood_platform"),
					WOOD = new BasicBlock(Properties.create(Material.GROUND).sound(SoundType.WOOD), 25, 15, true, false, false, true, "wood", "wood")

				);
	}
	
	
}
