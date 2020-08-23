package kmerrill285.stackeddimensions.networking;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.function.Supplier;

import kmerrill285.stackeddimensions.StackedDimensions;
import kmerrill285.stackeddimensions.Util;
import kmerrill285.stackeddimensions.configuration.DimensionConfigs;
import kmerrill285.stackeddimensions.configuration.DimensionConfiguration;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;


public class CPacketSendBlockClick {
	int x, y, z, fx, fy, fz, button;
	ResourceLocation dimension;
	
	private static HashMap<MiningPos, Float> mining = new HashMap<MiningPos, Float>();
	private class MiningPos {
		private BlockPos pos;
		private DimensionType type;
		
		public MiningPos(BlockPos pos, DimensionType type) {
			this.pos = pos;
			this.type = type;
		}
		
		public boolean equals(Object p) {
			if (p instanceof MiningPos)
			return type.equals(((MiningPos)p).type) && pos.equals(((MiningPos)p).pos);
			return false;
		}
	}
	
	public CPacketSendBlockClick(int x, int y, int z, int fx, int fy, int fz, ResourceLocation dimension, int button) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.fx = fx;
		this.fy = fy;
		this.fz = fz;
		this.dimension = dimension;
		this.button = button;
	}
	
	public void encode(PacketBuffer buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeInt(fx);
		buf.writeInt(fy);
		buf.writeInt(fz);
		buf.writeResourceLocation(dimension);
		buf.writeInt(button);
		new Thread() {
			public void run() {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				StackedDimensions.loadRenderers = true;
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				StackedDimensions.loadRenderers = true;
			}
		}.start();
    }
	
	public CPacketSendBlockClick(PacketBuffer buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		fx = buf.readInt();
		fy = buf.readInt();
		fz = buf.readInt();
		dimension = buf.readResourceLocation();
		button = buf.readInt();
	}
	
	private void sendRenderBlock(BlockPos pos, BlockState state, ServerPlayerEntity player, DimensionConfiguration config) {
 		NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity)player), new SPacketSendRenderBlock(pos, state));
 		if (config != null) {
 			for (ServerPlayerEntity sp : player.getServer().getPlayerList().getPlayers()) {
 				if (sp != player) {
 					if (config.getDimension() != null) {
 	 					if (sp.dimension == config.getDimension() && sp.posY <= 30) {
 	 						NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity)sp), new SPacketSendRenderBlock(pos, state));
 	 					}
 	 				}
 				}
 				
 	 		}
 		}
	}
	private void sendChunk(int x, int z, DimensionType t, ServerPlayerEntity player, DimensionConfiguration config) {
		DimensionManager.keepLoaded(t, true);
		ServerWorld dim = DimensionManager.getWorld(player.getServer(), t, true, true);
		DimensionManager.keepLoaded(t, true);
		Chunk chunk = dim.getChunk(x, z);
 		NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity)player), new SPacketSendChunk(chunk, x, z, true));
 		if (config != null) {
 			for (ServerPlayerEntity sp : player.getServer().getPlayerList().getPlayers()) {
 				if (sp != player) {
 					if (config.getAbove() != null) {
 	 					if (sp.dimension == config.getAbove() && sp.posY <= 30) {
 	 						NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity)sp), new SPacketSendChunk(chunk, x, z, true));
 	 					}
 	 				}
 					if (config.getBelow() != null) {
 	 					if (sp.dimension == config.getBelow() && sp.posY >= 255-30) {
 	 						NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity)sp), new SPacketSendChunk(chunk, x, z, true));
 	 					}
 	 				}
 				}
 				
 	 		}
 		}
 		
	}
	
	public void handle(Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			
			ServerPlayerEntity player = ctx.get().getSender();

			if (button == 1) 
			{
				net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock evt = net.minecraftforge.common.ForgeHooks
		                 .onRightClickBlock(player, Hand.MAIN_HAND, new BlockPos(x, y, z), Direction.getFacingFromVector(fx, fy, fz));
				DimensionConfiguration config = DimensionConfigs.getConfig(player.dimension.getRegistryName());
				DimensionConfiguration c2 = null;
				if (player.posY < 100) {
					if (config != null) {
						if (config.below != null) {
							c2 = DimensionConfigs.getConfig(config.below);
						}
					}
				}
				if (player.posY > 100) {
					if (config != null) {
						if (config.above != null) {
							c2 = DimensionConfigs.getConfig(config.above);
						}
					}
				}
				if (c2 == null) {
					c2 = config;
				}
				if (player.posY < 100) {
					
					if (y + fy > c2.getMax()) {
						
						if (player.getHeldItemMainhand() != null) {
							//   public BlockRayTraceResult(Vec3d hitVec, Direction faceIn, BlockPos posIn, boolean isInside) {
								if (player.getHeldItemMainhand().getItem() instanceof BlockItem) {
									Vec3d hitVec = new Vec3d(x, y-(c2.getMax()), z);
									Direction faceIn = Direction.getFacingFromVector(fx, fy, fz);
									BlockPos posIn = new BlockPos(x, y-(c2.getMax()), z);
									boolean isInside = false;
									BlockRayTraceResult result = new BlockRayTraceResult(hitVec, faceIn, posIn, isInside);
									ItemUseContext context = new ItemUseContext(player, Hand.MAIN_HAND, result);
									//   public ItemUseContext(PlayerEntity player, Hand handIn, BlockRayTraceResult rayTraceResultIn)
									if (!player.isCreative()) {
										player.getHeldItemMainhand().getItem().onItemUse(context);
										sendRenderBlock(posIn, context.getWorld().getBlockState(posIn), player, config);
									}
									else
										{
											ItemStack stack = new ItemStack(player.getHeldItemMainhand().getItem(), player.getHeldItemMainhand().getCount() + 0);
											player.getHeldItemMainhand().getItem().onItemUse(context);
											player.setItemStackToSlot(EquipmentSlotType.MAINHAND, stack);
											sendRenderBlock(posIn, context.getWorld().getBlockState(posIn), player, config);
										}
								}
							
						}
						
					} else {
						if (config != null && config.getBelow() != null)
						if (player.getHeldItemMainhand().getItem() instanceof BlockItem) {
							Vec3d hitVec = new Vec3d(x, y, z);
							Direction faceIn = Direction.getFacingFromVector(fx, fy, fz);
							BlockPos posIn = new BlockPos(x, y, z);
							boolean isInside = false;
							BlockRayTraceResult result = new BlockRayTraceResult(hitVec, faceIn, posIn, isInside);
							ItemUseContext context = new ItemUseContext(player, Hand.MAIN_HAND, result);
							
							try {
								Field world = context.getClass().getDeclaredField(StackedDimensions.DEBUG ? "world" : "field_196006_g");
								try {
									Util.makeFieldAccessible(world);
								} catch (Exception e) {
									e.printStackTrace();
								}
								DimensionType type = config.getBelow();
								if (type != null) {
									ServerWorld w = player.getEntityWorld().getServer().getWorld(type);
									try {
										world.set(context, w);
									} catch (IllegalArgumentException e) {
										e.printStackTrace();
									} catch (IllegalAccessException e) {
										e.printStackTrace();
									}
								}
							} catch (NoSuchFieldException e) {
								e.printStackTrace();
							} catch (SecurityException e) {
								e.printStackTrace();
							}
//							context.getWorld().setBlockState(posIn, Blocks.REDSTONE_BLOCK.getDefaultState());
//							System.out.println(context.getWorld().getBlockState(posIn));
							//   public ItemUseContext(PlayerEntity player, Hand handIn, BlockRayTraceResult rayTraceResultIn)
							if (!player.isCreative()) {
								player.getHeldItemMainhand().getItem().onItemUse(context);
								sendChunk(context.getPos().getX() / 16, context.getPos().getZ() / 16, config.getBelow(), player, config);
								sendRenderBlock(posIn, context.getWorld().getBlockState(posIn), player, config);
							}
							else
								{
									ItemStack stack = new ItemStack(player.getHeldItemMainhand().getItem(), player.getHeldItemMainhand().getCount() + 0);
									player.getHeldItemMainhand().getItem().onItemUse(context);
									player.setItemStackToSlot(EquipmentSlotType.MAINHAND, stack);
									sendChunk(context.getPos().getX() / 16, context.getPos().getZ() / 16, config.getBelow(), player, config);
									sendRenderBlock(posIn, context.getWorld().getBlockState(posIn), player, config);
								}
						}
					}
				}
				
				if (player.posY > 100) {
					System.out.println(y + fy);
					if (y + fy < c2.getMin()+256) {
						
						if (player.getHeldItemMainhand() != null) {
							//   public BlockRayTraceResult(Vec3d hitVec, Direction faceIn, BlockPos posIn, boolean isInside) {
								if (player.getHeldItemMainhand().getItem() instanceof BlockItem) {
									
									Vec3d hitVec = new Vec3d(x, y+(config.getMax()), z);
									Direction faceIn = Direction.getFacingFromVector(fx, fy, fz);
									BlockPos posIn = new BlockPos(x, y+(config.getMax()), z);
									boolean isInside = false;
									BlockRayTraceResult result = new BlockRayTraceResult(hitVec, faceIn, posIn, isInside);
									ItemUseContext context = new ItemUseContext(player, Hand.MAIN_HAND, result);
									//   public ItemUseContext(PlayerEntity player, Hand handIn, BlockRayTraceResult rayTraceResultIn)
									if (!player.isCreative()) {
										player.getHeldItemMainhand().getItem().onItemUse(context);
										sendChunk(context.getPos().getX() / 16, context.getPos().getZ() / 16, config.getAbove(), player, config);
										sendRenderBlock(posIn, context.getWorld().getBlockState(posIn), player, config);
									}
									else
										{
											ItemStack stack = new ItemStack(player.getHeldItemMainhand().getItem(), player.getHeldItemMainhand().getCount() + 0);
											player.getHeldItemMainhand().getItem().onItemUse(context);
											player.setItemStackToSlot(EquipmentSlotType.MAINHAND, stack);
											sendChunk(context.getPos().getX() / 16, context.getPos().getZ() / 16, config.getAbove(), player, config);
											sendRenderBlock(posIn, context.getWorld().getBlockState(posIn), player, config);
										}
								}
							
						}
					} else {
						
						if (config != null && config.getAbove() != null)
						if (player.getHeldItemMainhand().getItem() instanceof BlockItem) {
							
							Vec3d hitVec = new Vec3d(x, y, z);
							Direction faceIn = Direction.getFacingFromVector(fx, fy, fz);
							BlockPos posIn = new BlockPos(x, y, z);
							boolean isInside = false;
							BlockRayTraceResult result = new BlockRayTraceResult(hitVec, faceIn, posIn, isInside);
							ItemUseContext context = new ItemUseContext(player, Hand.MAIN_HAND, result);

							try {
								Field world = context.getClass().getDeclaredField(StackedDimensions.DEBUG ? "world" : "field_196006_g");
								try {
									Util.makeFieldAccessible(world);
								} catch (Exception e) {
									e.printStackTrace();
								}
								DimensionType type = config.getAbove();
								if (type != null) {
									ServerWorld w = player.getEntityWorld().getServer().getWorld(type);
									try {
										world.set(context, w);
									} catch (IllegalArgumentException e) {
										e.printStackTrace();
									} catch (IllegalAccessException e) {
										e.printStackTrace();
									}
								}
							} catch (NoSuchFieldException e) {
								e.printStackTrace();
							} catch (SecurityException e) {
								e.printStackTrace();
							}
							//   public ItemUseContext(PlayerEntity player, Hand handIn, BlockRayTraceResult rayTraceResultIn)
							if (!player.isCreative()) {
								player.getHeldItemMainhand().getItem().onItemUse(context);
								sendChunk(context.getPos().getX() / 16, context.getPos().getZ() / 16, config.getAbove(), player, config);
								sendRenderBlock(posIn, context.getWorld().getBlockState(posIn), player, config);
							}
							else
								{
									ItemStack stack = new ItemStack(player.getHeldItemMainhand().getItem(), player.getHeldItemMainhand().getCount() + 0);
									player.getHeldItemMainhand().getItem().onItemUse(context);
									player.setItemStackToSlot(EquipmentSlotType.MAINHAND, stack);
									sendChunk(context.getPos().getX() / 16, context.getPos().getZ() / 16, config.getAbove(), player, config);
									sendRenderBlock(posIn, context.getWorld().getBlockState(posIn), player, config);
								}
						}
					}
				}
				
			}
			if (button == 0) {
				
				net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickBlock evt = net.minecraftforge.common.ForgeHooks
		                 .onLeftClickBlock(player, new BlockPos(x, y, z), Direction.getFacingFromVector(fx, fy, fz));
				DimensionConfiguration config = DimensionConfigs.getConfig(player.dimension.getRegistryName());
				if (config != null) {
					if (player.posY < 100) {
						DimensionType type = config.getBelow();
						if (type != null) {
							if (player.isCreative()) {
								ServerWorld world = player.getEntityWorld().getServer().getWorld(type);
								world.setBlockState(new BlockPos(x, y, z), Blocks.AIR.getDefaultState());
								sendChunk(x / 16, z / 16, config.getBelow(), player, config);
								sendRenderBlock(new BlockPos(x, y, z), player.getEntityWorld().getBlockState(new BlockPos(x, y, z)), player, config);
							} else {
								BlockPos bpos = new BlockPos(x, y, z);
								MiningPos pos = new MiningPos(bpos, type);
								boolean hasPos = false;
								for (MiningPos p : CPacketSendBlockClick.mining.keySet()) {
									if (p.equals(pos)) {
										pos = p;
										hasPos = true;
										break;
									}
								}

								if (!hasPos) {
									CPacketSendBlockClick.mining.put(pos, 0.0F);
								}
								
								ServerWorld world = player.getEntityWorld().getServer().getWorld(type);
								//   public void func_225416_a(BlockPos p_225416_1_, CPlayerDiggingPacket.Action p_225416_2_, Direction p_225416_3_, int p_225416_4_) {
								
								BlockState state = world.getBlockState(bpos);
								float f = state.getPlayerRelativeBlockHardness(player, world, bpos);
								net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickBlock event = net.minecraftforge.common.ForgeHooks.onLeftClickBlock(player, bpos, Direction.getFacingFromVector(fx, fy, fz));
								if (!(event.isCanceled() || (!player.isCreative() && event.getUseItem() == net.minecraftforge.eventbus.api.Event.Result.DENY))) { // Restore block and te data
							    	  CPacketSendBlockClick.mining.put(pos, CPacketSendBlockClick.mining.get(pos) + f * 0.5f);
							    	  if (CPacketSendBlockClick.mining.get(pos) > 1) {
							    		  world.getBlockState(bpos).onReplaced(world, bpos, Blocks.AIR.getDefaultState(), false);
										  Block.spawnDrops(world.getBlockState(bpos), world, bpos);
										  world.setBlockState(new BlockPos(x, y, z), Blocks.AIR.getDefaultState());
										  CPacketSendBlockClick.mining.remove(pos);
										  sendChunk(x / 16, z / 16, config.getBelow(), player, config);
										  sendRenderBlock(new BlockPos(x, y, z), player.getEntityWorld().getBlockState(new BlockPos(x, y, z)), player, config);
							    	  }
							      }
							}
						}
					}
					if (player.posY > 100) {
						DimensionType type = config.getAbove();
						if (type != null) {
							if (player.isCreative()) {
								ServerWorld world = player.getEntityWorld().getServer().getWorld(type);
								world.setBlockState(new BlockPos(x, y, z), Blocks.AIR.getDefaultState());
								sendChunk(x / 16, z / 16, config.getAbove(), player, config);
								sendRenderBlock(new BlockPos(x, y, z), player.getEntityWorld().getBlockState(new BlockPos(x, y, z)), player, config);
							} else {
								BlockPos bpos = new BlockPos(x, y, z);
								MiningPos pos = new MiningPos(bpos, type);
								boolean hasPos = false;
								for (MiningPos p : CPacketSendBlockClick.mining.keySet()) {
									if (p.equals(pos)) {
										pos = p;
										hasPos = true;
										break;
									}
								}

								if (!hasPos) {
									CPacketSendBlockClick.mining.put(pos, 0.0F);
								}
								
								ServerWorld world = player.getEntityWorld().getServer().getWorld(type);
								//   public void func_225416_a(BlockPos p_225416_1_, CPlayerDiggingPacket.Action p_225416_2_, Direction p_225416_3_, int p_225416_4_) {
								
								BlockState state = world.getBlockState(bpos);
								float f = state.getPlayerRelativeBlockHardness(player, world, bpos);
								net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickBlock event = net.minecraftforge.common.ForgeHooks.onLeftClickBlock(player, bpos, Direction.getFacingFromVector(fx, fy, fz));
								if (!(event.isCanceled() || (!player.isCreative() && event.getUseItem() == net.minecraftforge.eventbus.api.Event.Result.DENY))) { // Restore block and te data
							    	  CPacketSendBlockClick.mining.put(pos, CPacketSendBlockClick.mining.get(pos) + f * 0.5f);
							    	  if (CPacketSendBlockClick.mining.get(pos) > 1) {
							    		  world.getBlockState(bpos).onReplaced(world, bpos, Blocks.AIR.getDefaultState(), false);
										  Block.spawnDrops(world.getBlockState(bpos), world, bpos);
										  world.setBlockState(new BlockPos(x, y, z), Blocks.AIR.getDefaultState());
										  CPacketSendBlockClick.mining.remove(pos);
										  sendChunk(x / 16, z / 16, config.getAbove(), player, config);
										  sendRenderBlock(new BlockPos(x, y, z), player.getEntityWorld().getBlockState(new BlockPos(x, y, z)), player, config);
							    	  }
							      }
							}
						}
					}
				}
//				player.world.setBlockState(new BlockPos(x, y, z), Blocks.AIR.getDefaultState());
			}
        });
        ctx.get().setPacketHandled(true);
	}
}
