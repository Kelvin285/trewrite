package kmerrill285.trewrite.blocks;

import kmerrill285.trewrite.items.ItemT;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class BasicBlock extends BlockT {

	public BasicBlock(Properties properties, float hardness, float difficulty, boolean pick, boolean axe, boolean hammer, boolean material, String name, String drop) {
		super(properties, hardness, difficulty, drop);
		this.pick = pick;
		this.axe = axe;
		this.hammer = hammer;
		this.material = material;
		this.setLocation(name);
	}

	
	public BasicBlock(Properties properties, float hardness, float difficulty, boolean pick, boolean axe, boolean hammer, boolean material, String name, int health, int mana, String drop) {
		super(properties, hardness, difficulty, drop);
		this.pick = pick;
		this.axe = axe;
		this.hammer = hammer;
		this.material = material;
		this.setLocation(name);
		this.health = health;
		this.mana = mana;
		this.consumable = true;
	}
	
	private boolean fullCube = true;
	
	private BlockFaceShape faceShape = BlockFaceShape.SOLID;
	
	public boolean isFullCube(IBlockState state) {
	      return fullCube;
	}
	
	public BasicBlock setFullCube(boolean fullCube) {
		this.fullCube = fullCube;
		return this;
	}
	
	public BasicBlock setFaceShape(BlockFaceShape faceShape) {
		this.faceShape = faceShape;
		return this;
	}
	
	 public BlockRenderLayer getRenderLayer() {
		   if (faceShape != BlockFaceShape.SOLID)
			   return BlockRenderLayer.CUTOUT;
		   else
			   return BlockRenderLayer.SOLID;
		}
	
	public BlockFaceShape getBlockFaceShape(IBlockReader worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
	      return faceShape;
	   }
}
