package kmerrill285.trewrite.world.client;

import java.lang.reflect.Field;

import com.mojang.blaze3d.platform.GlStateManager;

import kmerrill285.trewrite.Trewrite;
import kmerrill285.trewrite.core.network.NetworkHandler;
import kmerrill285.trewrite.core.network.client.CPacketChangeBlock;
import kmerrill285.trewrite.core.network.client.CPacketRequestChunks;
import kmerrill285.trewrite.entities.EntitiesT;
import kmerrill285.trewrite.events.EntityEvents;
import kmerrill285.trewrite.events.OverlayEvents;
import kmerrill285.trewrite.items.ItemBlockT;
import kmerrill285.trewrite.util.Util;
import kmerrill285.trewrite.world.TRenderInfo;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.culling.ClippingHelper;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
@OnlyIn(value=Dist.CLIENT)
public class TerrariaChunkRenderer {
	
	public static int NO_DIMENSION = -2;
	public static boolean rendering = false;
	public static boolean resetWorldRenderer = false;
	public static void update (RenderWorldLastEvent event, int dimension, int height, boolean up) {
		
		Entity entity = Minecraft.getInstance().getRenderViewEntity();

	      
		rendering = false;
		if (rendering == true) return;
		rendering = true;

		if (OverlayEvents.gameRenderer == null) {
			OverlayEvents.gameRenderer = new GameRenderer(Minecraft.getInstance(), Minecraft.getInstance().getResourceManager());
		}
		if (OverlayEvents.tRenderInfo == null) {
			OverlayEvents.tRenderInfo = new TRenderInfo();
		}
		
		if (OverlayEvents.renderWorld == null) {
			OverlayEvents.renderWorld = new RenderWorld(Minecraft.getInstance().getConnection(), new WorldSettings(0L, GameType.CREATIVE, false, false, WorldType.FLAT), DimensionType.OVERWORLD, 3, Minecraft.getInstance().getProfiler(), OverlayEvents.worldRenderer);
			((RenderWorld)OverlayEvents.renderWorld).setProfile(Minecraft.getInstance().getProfiler());	
		}
		
		if (OverlayEvents.worldRenderer == null) {
			OverlayEvents.worldRenderer = new WorldRenderer(Minecraft.getInstance());
			OverlayEvents.worldRenderer.setWorldAndLoadRenderers((ClientWorld)OverlayEvents.renderWorld);
		}
		
		((RenderWorld)OverlayEvents.renderWorld).setProfile(Minecraft.getInstance().getProfiler());
	     
		
			
			ClippingHelper clip2 = new ClippingHelper() {
				public boolean isBoxInFrustum(double a, double b, double c, double d, double e, double f) {
					return true;
				}
			};
			
			if (OverlayEvents.renderWorld != null)
				OverlayEvents.camera = EntitiesT.ZOMBIE.create(entity.world, null, null, null, new BlockPos(0, 0, 0), SpawnReason.EVENT, false, false);
			if (OverlayEvents.camera == null) {
				GlStateManager.popMatrix();
				rendering = false;
				return;
			}
			
			
			
			OverlayEvents.camera.rotationPitch = 0;
			OverlayEvents.camera.rotationYaw = 0;
			OverlayEvents.camera.rotationYawHead = 0;
			
			
			if (OverlayEvents.worldRenderer == null) {
				OverlayEvents.worldRenderer = new WorldRenderer(Minecraft.getInstance());
			}
			if (up) {
				
				if (entity.posY > 150) {
					
					if (OverlayEvents.reloadOverworld == false) {
						OverlayEvents.reloadOverworld = true;
						OverlayEvents.worldRenderer.setWorldAndLoadRenderers((RenderWorld)OverlayEvents.renderWorld);
					}
				} else {
					OverlayEvents.renderWorld = null;
					OverlayEvents.reloadOverworld = false;
					rendering = false;
					return;
				}
			}
			if (!up) {		
				if (entity.posY < 50) {
					if (OverlayEvents.reloadOverworld == false) {
						OverlayEvents.reloadOverworld = true;
						OverlayEvents.worldRenderer.setWorldAndLoadRenderers((RenderWorld)OverlayEvents.renderWorld);
					}
				} else {
					OverlayEvents.renderWorld = null;
					OverlayEvents.reloadOverworld = false;
					rendering = false;
					return;
					
				}
			}
			
			
			
			if (OverlayEvents.loadRenderers) {
				OverlayEvents.worldRenderer.setWorldAndLoadRenderers((RenderWorld)OverlayEvents.renderWorld);
				OverlayEvents.loadRenderers = false;
			}
			
			
			if (OverlayEvents.setupWorld == false) {
				OverlayEvents.setupWorld = true;
				new Thread() {
					public void run() {
						try {
							Thread.sleep(10000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						TerrariaChunkRenderer.resetWorldRenderer = true;
					}
				}.start();
			}
		  if (TerrariaChunkRenderer.resetWorldRenderer) {
			  OverlayEvents.worldRenderer.setWorldAndLoadRenderers((RenderWorld)OverlayEvents.renderWorld);
			  TerrariaChunkRenderer.resetWorldRenderer = false;
			  
		  }
	      ICamera icamera = new Frustum(clip2);
	      Minecraft.getInstance().getTextureManager().bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
			
	      
	      OverlayEvents.tRenderInfo.update(OverlayEvents.camera.world, OverlayEvents.camera, true, true, event.getPartialTicks());
	      Vec3d eyePos = Minecraft.getInstance().gameRenderer.getActiveRenderInfo().getProjectedView();
	      OverlayEvents.tRenderInfo.setPosition(new Vec3d(eyePos.x, eyePos.y - height, eyePos.z));
		  if ((Minecraft.getInstance().gameSettings.thirdPersonView == 1)) {
			  Vec3d f = entity.getForward();
			  double zoom = 4;
			  zoom = 0;
			  OverlayEvents.tRenderInfo.setPosition(new Vec3d(eyePos.x - f.x * zoom, eyePos.y - height - f.y * zoom, eyePos.z - f.z * zoom));
		  }
		  if ((Minecraft.getInstance().gameSettings.thirdPersonView == 2)) {
			  Vec3d f = entity.getForward();
			  double zoom = -4;
			  zoom = 0;
			  OverlayEvents.tRenderInfo.setPosition(new Vec3d(eyePos.x - f.x * zoom, eyePos.y - height - f.y * zoom, eyePos.z - f.z * zoom));
		  }
			OverlayEvents.tRenderInfo.setDirection(0, 0);
			
			
			

			double d0 = OverlayEvents.tRenderInfo.getProjectedView().x;
		    double d1 = OverlayEvents.tRenderInfo.getProjectedView().y;
		    double d2 = OverlayEvents.tRenderInfo.getProjectedView().z;
		    icamera.setPosition(d0, d1, d2);
		    
		  OverlayEvents.worldRenderer.func_224745_a(OverlayEvents.tRenderInfo);
		  OverlayEvents.worldRenderer.setupTerrain(OverlayEvents.tRenderInfo, icamera, OverlayEvents.frameCount++, true);
		   
		  GlStateManager.matrixMode(5888);
		  GlStateManager.pushMatrix();
	      GlStateManager.disableAlphaTest();
	      OverlayEvents.worldRenderer.renderBlockLayer(BlockRenderLayer.SOLID, OverlayEvents.tRenderInfo);
	      GlStateManager.enableAlphaTest();
	      Minecraft.getInstance().getTextureManager().getTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE).setBlurMipmap(false, Minecraft.getInstance().gameSettings.mipmapLevels > 0); // FORGE: fix flickering leaves when mods mess up the blurMipmap settings
	      OverlayEvents.worldRenderer.renderBlockLayer(BlockRenderLayer.CUTOUT_MIPPED, OverlayEvents.tRenderInfo);
	      Minecraft.getInstance().getTextureManager().getTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE).restoreLastBlurMipmap();
	      Minecraft.getInstance().getTextureManager().getTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE).setBlurMipmap(false, false);
	      OverlayEvents.worldRenderer.renderBlockLayer(BlockRenderLayer.CUTOUT, OverlayEvents.tRenderInfo);
	      Minecraft.getInstance().getTextureManager().getTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE).restoreLastBlurMipmap();
	      GlStateManager.shadeModel(7424);
	      GlStateManager.alphaFunc(516, 0.1F);
	      GlStateManager.matrixMode(5888);
	      
	      GlStateManager.popMatrix();
	      
	     
	      
	      RayTraceResult result = null;
	      double rd = (double)Minecraft.getInstance().playerController.getBlockReachDistance();

	      Vec3d vec3d = entity.getEyePosition(event.getPartialTicks()).subtract(0,height,0);
	      Vec3d vec3d1 = entity.getLook(event.getPartialTicks());
	      Vec3d vec3d2 = vec3d.add(vec3d1.x * rd, vec3d1.y * rd, vec3d1.z * rd);
	      result = OverlayEvents.renderWorld.rayTraceBlocks(new RayTraceContext(vec3d, vec3d2, RayTraceContext.BlockMode.OUTLINE, false ? RayTraceContext.FluidMode.ANY : RayTraceContext.FluidMode.NONE, entity));
	      if (result != null && result.getHitVec() != null) {
//	    	  OverlayEvents.worldRenderer.sendBlockBreakProgress(0, new BlockPos(result.getHitVec()), 0);
	          GlStateManager.disableAlphaTest();
	          Minecraft.getInstance().getProfiler().endStartSection("outline");
	          if (!net.minecraftforge.client.ForgeHooksClient.onDrawBlockHighlight(OverlayEvents.worldRenderer, OverlayEvents.tRenderInfo, result, 0, event.getPartialTicks()))
	          OverlayEvents.worldRenderer.drawSelectionBox(OverlayEvents.tRenderInfo, result, 0);
	          GlStateManager.enableAlphaTest();
	          Minecraft.getInstance().objectMouseOver = result;
	       }
	      if (result != null) {
	    	  OverlayEvents.blockHit = result;
	    	  A:
	    	  if (Minecraft.getInstance().gameSettings.keyBindAttack.isKeyDown()) {
	    		  if (result.getType() == RayTraceResult.Type.BLOCK) {
	    			  
		    		  new Thread() {
		    				public void run() {
		    					try {
		    						Thread.sleep(500);
		    					} catch (InterruptedException e) {
		    						e.printStackTrace();
		    					}
		    					OverlayEvents.loadRenderers = true;
		    					try {
		    						Thread.sleep(500);
		    					} catch (InterruptedException e) {
		    						e.printStackTrace();
		    					}
		    					OverlayEvents.loadRenderers = true;
		    				}
		    			}.start();
	    			  
		    		  BlockRayTraceResult r = (BlockRayTraceResult)result;
		    		  OverlayEvents.renderWorld.setBlockState(r.getPos().add(-16, 0, 0), OverlayEvents.renderWorld.getBlockState(r.getPos()));
		    		  if (Minecraft.getInstance().player.swingProgressInt == 3) {
		    			  Minecraft.getInstance().world.playSound(r.getPos().add(0, height, 0), OverlayEvents.renderWorld.getBlockState(r.getPos()).getSoundType().getHitSound(), SoundCategory.PLAYERS, 1.0F, 1.0F, true);
		    		  }
		    		  Minecraft.getInstance().player.swingArm(Hand.MAIN_HAND);
		    		  
		    		  BlockParticleData particles = new BlockParticleData(ParticleTypes.BLOCK, OverlayEvents.renderWorld.getBlockState(r.getPos()));
		    		  Minecraft.getInstance().world.addParticle(particles, r.getPos().getX() + 0.5, r.getPos().getY() + height + 0.5, r.getPos().getZ() + 0.5, r.getFace().getXOffset() * 0.5, r.getFace().getYOffset() * 0.5, r.getFace().getZOffset() * 0.5);
		    		 
		    		  double hardness = OverlayEvents.renderWorld.getBlockState(r.getPos()).getBlockHardness(OverlayEvents.renderWorld, r.getPos());
		    		  Block block = OverlayEvents.renderWorld.getBlockState(r.getPos()).getBlock();
		    		  //        public BreakSpeed(PlayerEntity player, BlockState state, float original, BlockPos pos)
		    		  BreakSpeed bspeed = new BreakSpeed(Minecraft.getInstance().player, block.getDefaultState(), (float) hardness, r.getPos());
		    		  EntityEvents.getMiningSpeed(bspeed);
		    		  
		    		  
		    		  OverlayEvents.blockMiningProgress += bspeed.getNewSpeed();
		    		  if (OverlayEvents.blockMiningProgress < 0) {
		    			  OverlayEvents.blockMiningProgress = 0;
		    		  }
		    		  if (OverlayEvents.blockMiningProgress > 100.0F) {
		    			  OverlayEvents.blockMiningProgress = 0;
		    			  Minecraft.getInstance().world.playSound(r.getPos().add(0, height, 0), OverlayEvents.renderWorld.getBlockState(r.getPos()).getSoundType().getBreakSound(), SoundCategory.PLAYERS, 1.0F, 1.0F, true);
						  OverlayEvents.loadRenderers = true;
		    			  NetworkHandler.INSTANCE.sendToServer(new CPacketChangeBlock(r.getPos().getX(), r.getPos().getY(), r.getPos().getZ(), dimension, Blocks.AIR.getDefaultState(), height, false));
						  OverlayEvents.loadRenderers = true;
		    			  OverlayEvents.renderWorld.setBlockState(r.getPos().add(-16,0,-16), Blocks.AIR.getDefaultState());
		    		  }
		    		  
	    		  }
	    	  }
	    	  if (Minecraft.getInstance().gameSettings.keyBindUseItem.isKeyDown() && entity instanceof PlayerEntity) {
		    	  if (result.getType() == RayTraceResult.Type.BLOCK) {
		    		  System.out.println("wryyy");
		    		  OverlayEvents.loadRenderers = true;
		    	  }
	    	  }
	    	  if (Minecraft.getInstance().gameSettings.keyBindUseItem.isKeyDown() && entity instanceof PlayerEntity && ((PlayerEntity)entity).getHeldItemMainhand().getItem() instanceof ItemBlockT) {
	    		  Vec3d vec = new Vec3d(result.getHitVec().x, result.getHitVec().y, result.getHitVec().z);
	    		  BlockPos p = new BlockPos(vec);
	    		  vec = vec.subtract(p.getX(), p.getY(), p.getZ());
	    		  if (Minecraft.getInstance().player.isSwingInProgress == false)
		    	  if (result.getType() == RayTraceResult.Type.BLOCK) {
		    		  
		    		  new Thread() {
		    				public void run() {
		    					try {
		    						Thread.sleep(500);
		    					} catch (InterruptedException e) {
		    						e.printStackTrace();
		    					}
		    					OverlayEvents.loadRenderers = true;
		    					try {
		    						Thread.sleep(500);
		    					} catch (InterruptedException e) {
		    						e.printStackTrace();
		    					}
		    					OverlayEvents.loadRenderers = true;
		    				}
		    			}.start();
		    		  
		    		  BlockRayTraceResult r = (BlockRayTraceResult)result;
		    		  try {
							Field y = Vec3d.class.getDeclaredField(Trewrite.DEBUG ? "y" : "field_72448_b");
							Util.makeFieldAccessible(y);
							y.set(r.getHitVec(), r.getHitVec().y + height);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
		    		  try {
							Field y = BlockRayTraceResult.class.getDeclaredField(Trewrite.DEBUG ? "pos" : "field_216356_c");
							Util.makeFieldAccessible(y);
							y.set(r, r.getPos().add(r.getFace().getXOffset(), height + r.getFace().getYOffset(), r.getFace().getZOffset()));
						} catch (Exception e1) {
							e1.printStackTrace();
						}
		    		  OverlayEvents.renderWorld.setBlockState(r.getPos(), OverlayEvents.renderWorld.getBlockState(r.getPos()));
		    		  Minecraft.getInstance().player.swingArm(Hand.MAIN_HAND);
		    		  net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock evt = net.minecraftforge.common.ForgeHooks
		    	                 .onRightClickBlock(Minecraft.getInstance().player, Hand.MAIN_HAND, r.getPos(), r.getFace());
		    	  }
	    	  }
	      }
	      

	      //destroy progress
	      GlStateManager.enableBlend();
	      GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
	      Minecraft.getInstance().getTextureManager().getTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE).setBlurMipmap(false, false);
	      
//	      Tessellator.getInstance().getBuffer().finishDrawing();
//	      OverlayEvents.worldRenderer.func_215318_a(Tessellator.getInstance(), Tessellator.getInstance().getBuffer(), OverlayEvents.tRenderInfo);
	      
	      Minecraft.getInstance().getTextureManager().getTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE).restoreLastBlurMipmap();
	      GlStateManager.disableBlend();

	      GlStateManager.enableCull();
	      GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
	      GlStateManager.alphaFunc(516, 0.1F);
	      GlStateManager.enableBlend();
	      GlStateManager.depthMask(false);
	      Minecraft.getInstance().getTextureManager().bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
	      GlStateManager.shadeModel(7425);
	      OverlayEvents.worldRenderer.renderBlockLayer(BlockRenderLayer.TRANSLUCENT, OverlayEvents.tRenderInfo);
	      GlStateManager.shadeModel(7424);
	      GlStateManager.depthMask(true);
	      GlStateManager.enableCull();
	      GlStateManager.disableBlend();
	      
	    if (OverlayEvents.renderWorld != null) {
	    	World world = OverlayEvents.renderWorld;
	    	for (int i = 0; i < 2; i++) {
	    		double wall = 0.25;
	    		if (entity.getMotion().x < 0) {
	    		    BlockPos pos = entity.getPosition().up(i).add(entity.getMotion().x + 0.5, 0, 0).subtract(new Vec3i(0, height, 0));
	    	    	if (world.isAreaLoaded(pos, pos)) {
	    	    		BlockState state = world.getBlockState(pos);
	    	    		if (state.getMaterial().blocksMovement()) {
	    	    			if (entity.posX < pos.getX() + 1) {
	    	    				if (entity.posZ > pos.getZ() + wall && entity.posZ < pos.getZ() + 1 - wall) {
	    	    					if (entity.posY >= pos.getY() + height && entity.posY < pos.getY() + height + 0.5) {

	    	    	    				entity.move(MoverType.SELF, new Vec3d((pos.getX() + 1) - entity.posX, 0, 0));
	    		    	    			entity.setMotion(0, entity.getMotion().y, entity.getMotion().z);
	    		    	    			if (world.isAreaLoaded(pos.up(), pos.up())) {
	    		    	    				if (world.getBlockState(pos.up()).getMaterial().blocksMovement() == false) {
	    		    	    					entity.move(MoverType.SELF, new Vec3d(0, pos.getY() + height + 1 - entity.posY, 0));
	    		    	    				}
	    		    	    			}
	    	    					}
	    	    					if (entity.posY + 1 >= pos.getY() + height && entity.posY + 1 < pos.getY() + height + 0.5) {
	    	    						entity.move(MoverType.SELF, new Vec3d((pos.getX() + 1) - entity.posX, 0, 0));
	    		    	    			entity.setMotion(0, entity.getMotion().y, entity.getMotion().z);
	    	    					}
	    	    				}
			    				
	    	    			}
	    	    		}
	    	    	}
	    		}
	    		
	    		if (entity.getMotion().x > 0) {
	    		    BlockPos pos = entity.getPosition().up(i).add(entity.getMotion().x, 0, 0).subtract(new Vec3i(0, height, 0));
	    	    	if (world.isAreaLoaded(pos, pos)) {
	    	    		BlockState state = world.getBlockState(pos);
	    	    		if (state.getMaterial().blocksMovement()) {
	    	    			if (entity.posX > pos.getX()) {
	    	    				if (entity.posZ > pos.getZ() + wall && entity.posZ < pos.getZ() + 1 - wall) {
	    	    					if (entity.posY >= pos.getY() + height && entity.posY < pos.getY() + height + 0.5) {

	    	    	    				entity.move(MoverType.SELF, new Vec3d(pos.getX() - entity.posX, 0, 0));
	    		    	    			entity.setMotion(0, entity.getMotion().y, entity.getMotion().z);
	    		    	    			if (world.isAreaLoaded(pos.up(), pos.up())) {
	    		    	    				if (world.getBlockState(pos.up()).getMaterial().blocksMovement() == false) {
	    		    	    					entity.move(MoverType.SELF, new Vec3d(0, pos.getY() + height + 1 - entity.posY, 0));
	    		    	    				}
	    		    	    			}
	    	    					}
	    	    					if (entity.posY + 1 >= pos.getY() + height && entity.posY + 1 < pos.getY() + height + 0.5) {
	    	    						entity.move(MoverType.SELF, new Vec3d(pos.getX() - entity.posX, 0, 0));
	    		    	    			entity.setMotion(0, entity.getMotion().y, entity.getMotion().z);
	    	    					}
	    	    				}
			    				
	    	    			}
	    	    		}
	    	    	}
	    		}
	    		
	    		if (entity.getMotion().z < 0) {
	    		    BlockPos pos = entity.getPosition().up(i).add(0, 0, entity.getMotion().z + 0.5).subtract(new Vec3i(0, height, 0));
	    	    	if (world.isAreaLoaded(pos, pos)) {
	    	    		BlockState state = world.getBlockState(pos);
	    	    		if (state.getMaterial().blocksMovement()) {
	    	    			if (entity.posZ < pos.getZ() + 1) {
	    	    				if (entity.posX > pos.getX() + wall && entity.posX < pos.getX() + 1 - wall) {
	    	    					if (entity.posY >= pos.getY() + height && entity.posY < pos.getY() + height + 0.5) {

	    	    	    				entity.move(MoverType.SELF, new Vec3d(0, 0, (pos.getZ() + 1) - entity.posZ));
	    		    	    			entity.setMotion(entity.getMotion().x, entity.getMotion().y, 0);
	    		    	    			if (world.isAreaLoaded(pos.up(), pos.up())) {
	    		    	    				if (world.getBlockState(pos.up()).getMaterial().blocksMovement() == false) {
	    		    	    					entity.move(MoverType.SELF, new Vec3d(0, pos.getY() + height + 1 - entity.posY, 0));
	    		    	    				}
	    		    	    			}
	    	    					}
	    	    					if (entity.posY + 1 >= pos.getY() + height && entity.posY + 1 < pos.getY() + height + 0.5) {
	    	    						entity.move(MoverType.SELF, new Vec3d(0, 0, (pos.getZ() + 1) - entity.posZ));
	    		    	    			entity.setMotion(entity.getMotion().x, entity.getMotion().y, 0);
	    	    					}
	    	    				}
			    				
	    	    			}
	    	    		}
	    	    	}
	    		}
	    		
	    		if (entity.getMotion().z > 0) {
	    		    BlockPos pos = entity.getPosition().up(i).add(0, 0, entity.getMotion().z).subtract(new Vec3i(0, height, 0));
	    	    	if (world.isAreaLoaded(pos, pos)) {
	    	    		BlockState state = world.getBlockState(pos);
	    	    		if (state.getMaterial().blocksMovement()) {
	    	    			if (entity.posZ > pos.getZ()) {
	    	    				if (entity.posX > pos.getX() + wall && entity.posX < pos.getX() + 1 - wall) {
	    	    					if (entity.posY >= pos.getY() + height && entity.posY < pos.getY() + height + 0.5) {

	    	    	    				entity.move(MoverType.SELF, new Vec3d(0, 0, pos.getZ() - entity.posZ));
	    		    	    			entity.setMotion(entity.getMotion().x, entity.getMotion().y, 0);
	    		    	    			
	    		    	    			if (world.isAreaLoaded(pos.up(), pos.up())) {
	    		    	    				if (world.getBlockState(pos.up()).getMaterial().blocksMovement() == false) {
	    		    	    					entity.move(MoverType.SELF, new Vec3d(0, pos.getY() + height + 1 - entity.posY, 0));
	    		    	    				}
	    		    	    			}
	    	    					}
	    	    					if (entity.posY + 1 >= pos.getY() + height && entity.posY + 1 < pos.getY() + height + 0.5) {
	    	    						entity.move(MoverType.SELF, new Vec3d(0, 0, pos.getZ() - entity.posZ));
	    		    	    			entity.setMotion(entity.getMotion().x, entity.getMotion().y, 0);
	    	    					}
	    	    				}
			    				
	    	    			}
	    	    		}
	    	    		
	    	    	}
	    		}
	    		
//	    		if (entity.getMotion().y < 0) {
//	    		    BlockPos pos = entity.getPosition().up(i).add(0, entity.getMotion().y + 0.5f, 0).subtract(new Vec3i(0, height, 0));
//	    	    	if (world.isAreaLoaded(pos, pos)) {
//	    	    		BlockState state = world.getBlockState(pos);
//	    	    		if (state.getMaterial().blocksMovement()) {
//	    	    			if (entity.posY < pos.getY() + height + 1) {
//	    	    				entity.move(MoverType.SELF, new Vec3d(0, pos.getY() + height + 1 - entity.posY, 0));
//		    	    			entity.setMotion(entity.getMotion().x, 0, entity.getMotion().z);
//
//		    	    			entity.onGround = true;
//		    	    			NetworkHandler.INSTANCE.sendToServer(new CPacketNegateFall());
//	    	    			}
//	    	    			
//	    	    		}
//	    	    	}
//	    		}
	    		
	    		if (entity.getMotion().y > 0) {
	    		    BlockPos pos = entity.getPosition().up(i).add(0, entity.getMotion().y, 0).subtract(new Vec3i(0, height, 0));
	    	    	if (world.isAreaLoaded(pos, pos)) {
	    	    		BlockState state = world.getBlockState(pos);
	    	    		if (state.getMaterial().blocksMovement()) {
	    	    			if (entity.posY + 1 > pos.getY() + height - 1) {
	    	    				if (entity.posX > pos.getX() && entity.posX < pos.getX() + 1) {
	    	    					if (entity.posZ > pos.getZ() && entity.posZ < pos.getZ() + 1) {
	    	    	    				entity.move(MoverType.SELF, new Vec3d(0, (pos.getY() + height - 1) - (entity.posY + 1), 0));
	    		    	    			entity.setMotion(entity.getMotion().x, 0, entity.getMotion().z);
	    	    					}
	    	    				}
	    	    			}
	    	    		}
	    	    	}
	    		}
	    		
	    	}
	    		
	    }
	    
		OverlayEvents.ticks++;
		if (OverlayEvents.ticks > 0) {

			if (OverlayEvents.renderWorld != null)
			{
				
				if (OverlayEvents.worldRenderer == null) {
					OverlayEvents.worldRenderer = new WorldRenderer(Minecraft.getInstance());
				}
				((RenderWorld)OverlayEvents.renderWorld).worldRenderer = OverlayEvents.worldRenderer;
				
			}
			if (OverlayEvents.loadingChunks == false) {
				OverlayEvents.loadingChunks = true;
				final int HEIGHT = height;
				new Thread() {
					public void run() {
						int distmin = 4;
						int dist = distmin + 4;
						for (int x = -dist; x < dist; x++) {
							for (int z = -dist; z < dist; z++) {
								int X = entity.chunkCoordX + x;
								int Z = entity.chunkCoordZ + z;
								if (Math.abs(x) < distmin && Math.abs(z) < distmin) {
//									if (OverlayEvents.renderWorld.chunkExists(X, Z) == false ||
//											OverlayEvents.renderWorld.getChunk(X, Z) instanceof EmptyChunk) {
									if (entity != null)
								    NetworkHandler.INSTANCE.sendToServer(new CPacketRequestChunks(entity.chunkCoordX + x, entity.chunkCoordZ + z, dimension));
//									}
								} else {
									if (OverlayEvents.renderWorld != null)
									if (((RenderWorld)OverlayEvents.renderWorld).chunkProvider != null)
									if (((RenderWorld)OverlayEvents.renderWorld).chunkProvider.chunkExists(X, Z))
										((RenderWorld)OverlayEvents.renderWorld).chunkProvider.unloadChunk(X, Z);
								}
								try {
									Thread.sleep(5);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								
							}
						}
						OverlayEvents.loadingChunks = false;
					}
				}.start();
				
				
			}
			
			
		    OverlayEvents.ticks = 0;
		}
		rendering = false;
		
	}
}
