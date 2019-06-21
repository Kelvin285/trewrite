package kmerrill285.trewrite.blocks;

import kmerrill285.trewrite.Trewrite;
import kmerrill285.trewrite.entities.EntityItemT;
import kmerrill285.trewrite.items.ItemT;
import kmerrill285.trewrite.items.ItemsT;
import kmerrill285.trewrite.util.Conversions;
import net.minecraft.block.Block;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class BlockT extends Block {

	public int buy, sell;
	public float difficulty = 1;
	public boolean pick, axe, hammer;
	public boolean material;
	public boolean harvest = true;
	public boolean consumable;
	
	public int potionSickness;
	
	public int health, mana;
	
	public String tooltip = "";
	
	public String drop;
	
	public String name;
	public String shape = "";

	public BlockT(Properties properties, float hardness, float difficulty, String drop) {
		super(properties.hardnessAndResistance(hardness * 0.03f));
		this.difficulty = difficulty;
		this.drop = drop;
//		Block b;
//		b.onBlockHarvested(worldIn, pos, state, player);
	}
	
	public boolean canHarvestBlock(IBlockState state, IBlockReader world, BlockPos pos, EntityPlayer player) {
		return true;
	}
	
	public VoxelShape getShape(IBlockState state, IBlockReader worldIn, BlockPos pos) {
		 if (this.shape.contentEquals("platform_bottom")) {
			 return Platform.BOTTOM_SHAPE;
		 }
	      return super.getShape(state, worldIn, pos);
	   }
	public BlockFaceShape getBlockFaceShape(IBlockReader worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		
		
	      return BlockFaceShape.UNDEFINED;
	   }
 
	 public BlockT setShape(String shape) {
		   this.shape = shape;
		   return this;
		   
	 }
	
	 
	 
//	 @Override
//	 public IItemProvider getItemDropped(IBlockState state, World worldIn, BlockPos pos, int fortune) {
//		 
//		 return null;
//	 }
	 
	@Override
	public void onPlayerDestroy(IWorld worldIn, BlockPos pos, IBlockState state) {
		
	}
//	
	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
		
	}
	
	public BlockT setConsumable() {
		this.consumable = true;
		return this;
	}
	
	public BlockT setPotionSickness(int n) {
		this.potionSickness = n;
		return this;
	}
	
	public float getMiningSpeed(ItemT item) {
		if (pick && item.pick >= difficulty) {
			return item.pick / difficulty;
		}
		
		if (axe && item.axe >= difficulty) {
			return item.axe / difficulty;
		}
		
		if (hammer && item.hammer >= difficulty) {
			return item.hammer / difficulty;
		}
		
		return -1;
	}
	
	public boolean canSupport(IBlockState state) {
		return false;
	}
	
	public BlockT setLocation(String name) {
		this.name = name;
		this.setRegistryName(new ResourceLocation("trewrite", name));
		return this;
	}
	
	public BlockT setSell(int sell) {
		this.sell = sell;
		this.buy = Conversions.sellToBuy(sell);
		return this;
	}
	
	public BlockT setBuy(int buy) {
		this.sell = Conversions.buyToSell(buy);
		this.buy = buy;
		return this;
	}
	
	public BlockT setMaterial() {
		this.material = true;
		return this;
	}
	
	public BlockT setTooltip(String tooltip) {
		this.tooltip = tooltip;
		return this;
	}

}
