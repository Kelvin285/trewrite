package kmerrill285.trewrite.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.BlockTorchWall;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Particles;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class Torch extends BlockT {
	protected static final VoxelShape SHAPE = Block.makeCuboidShape(6.0D, 0.0D, 6.0D, 10.0D, 10.0D, 10.0D);
	
	
	public Torch(String name, String drop) {
		super(Properties.create(Material.GROUND).sound(SoundType.WOOD).doesNotBlockMovement().lightValue(10), 0, 0, drop);
		this.pick = true;
		this.axe = true;
		this.hammer = true;
		this.setLocation(name);
	}
	
	public Torch(int light, String name, String drop) {
		super(Properties.create(Material.GROUND).sound(SoundType.WOOD).doesNotBlockMovement().lightValue(light), 0, 0, drop);
		this.pick = true;
		this.axe = true;
		this.hammer = true;
		this.setLocation(name);
	}

	 public VoxelShape getShape(IBlockState state, IBlockReader worldIn, BlockPos pos) {
	      return SHAPE;
	   }


	   public boolean isFullCube(IBlockState state) {
	      return false;
	   }
	   public IBlockState updatePostPlacement(IBlockState stateIn, EnumFacing facing, IBlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		      return facing == EnumFacing.DOWN && !this.isValidPosition(stateIn, worldIn, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
		   }

		   public boolean isValidPosition(IBlockState state, IWorldReaderBase worldIn, BlockPos pos) {
		      IBlockState iblockstate = worldIn.getBlockState(pos.down());
		      return iblockstate.canPlaceTorchOnTop(worldIn, pos);
		   }
		   
		   @OnlyIn(Dist.CLIENT)
		   public void animateTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		      double xx = (double)pos.getX() + 0.5D;
		      double yy = (double)pos.getY() + 0.7D;
		      double zz = (double)pos.getZ() + 0.5D;
		      worldIn.spawnParticle(Particles.SMOKE, xx, yy, zz, 0.0D, 0.0D, 0.0D);
		      worldIn.spawnParticle(Particles.FLAME, xx, yy, zz, 0.0D, 0.0D, 0.0D);
		   }
		   
		   public BlockRenderLayer getRenderLayer() {
			      return BlockRenderLayer.CUTOUT;
			   }
		   
		   public BlockFaceShape getBlockFaceShape(IBlockReader worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
			      return BlockFaceShape.UNDEFINED;
			   }
}
