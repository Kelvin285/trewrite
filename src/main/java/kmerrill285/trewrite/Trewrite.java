package kmerrill285.trewrite;

import java.lang.reflect.Field;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import kmerrill285.trewrite.blocks.BlocksT;
import kmerrill285.trewrite.core.inventory.InventorySlot;
import kmerrill285.trewrite.core.inventory.InventoryTerraria;
import kmerrill285.trewrite.core.items.ItemStackT;
import kmerrill285.trewrite.core.network.NetworkHandler;
import kmerrill285.trewrite.core.network.server.SPacketSendAccessories;
import kmerrill285.trewrite.entities.EntitiesT;
import kmerrill285.trewrite.entities.EntityItemT;
import kmerrill285.trewrite.entities.monsters.EntityEyeOfCthulhu;
import kmerrill285.trewrite.events.EntityEvents;
import kmerrill285.trewrite.events.WorldEvents;
import kmerrill285.trewrite.items.Armor;
import kmerrill285.trewrite.items.ItemsT;
import kmerrill285.trewrite.items.accessories.Accessory;
import kmerrill285.trewrite.items.modifiers.ItemModifier;
import kmerrill285.trewrite.util.Util;
import kmerrill285.trewrite.world.DimensionTypeT;
import kmerrill285.trewrite.world.EntitySpawner;
import kmerrill285.trewrite.world.TerrariaDimension;
import kmerrill285.trewrite.world.TerrariaWorldType;
import kmerrill285.trewrite.world.WorldStateHolder;
import kmerrill285.trewrite.world.dimension.DimensionRegistry;
import kmerrill285.trewrite.world.dimension.Dimensions;
import net.minecraft.block.Blocks;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent.WorldTickEvent;
import net.minecraftforge.event.world.BlockEvent.NeighborNotifyEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.PacketDistributor;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("trewrite")
public class Trewrite
{
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public static final boolean DEBUG = true;
    
   
    
    public Trewrite() {
    	
    	new ItemModifier();
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        
//        FMLJavaModLoadingContext.get().getModEventBus().addListener(OverlayEvents::handleOverlayEvent);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(EntityEvents.class);
//        MinecraftForge.EVENT_BUS.register(ScoreboardEvents.class);
        MinecraftForge.EVENT_BUS.register(WorldEvents.class);
        NetworkHandler.register();
        
        MinecraftForge.EVENT_BUS.addListener(Trewrite::onWorldTick);
        MinecraftForge.EVENT_BUS.addListener(Trewrite::onBlockUpdate);

        try {
        	Field f = DimensionType.class.getDeclaredField(DEBUG ? "OVERWORLD" : "field_223227_a_");
        	Util.makeFieldAccessible(f);
        	
        	DimensionType OVERWORLD = new DimensionTypeT(1, "", "", TerrariaDimension::new, true);
        	DimensionType type = Registry.register(Registry.DIMENSION_TYPE, OVERWORLD.getId(), "custom_overworld", OVERWORLD);
        	f.set(null, type);
        	//return Registry.register(Registry.DIMENSION_TYPE, type.id, key, type);

        }catch (Exception e) {
        	e.printStackTrace();
        }
        
        WorldType type = WorldType.WORLD_TYPES[0];
        WorldType[] types2 = new WorldType[WorldType.WORLD_TYPES.length + 1];
        for (int i = 0; i < WorldType.WORLD_TYPES.length; i++) {
        	types2[i + 1] = WorldType.WORLD_TYPES[i];
        }
        types2[0] = new TerrariaWorldType("Terraria-Style");
        WorldType.WORLD_TYPES = types2;
    }
    
    @SubscribeEvent
	public static void onBlockUpdate(NeighborNotifyEvent e) {
    	
    	
    	
    	if (e.getState().getBlock() == Blocks.STONE || e.getState().getBlock() == Blocks.COBBLESTONE) {
    		e.getWorld().setBlockState(e.getPos(), BlocksT.STONE_BLOCK.getDefaultState(), 0);
    	}
    	if (e.getState().getBlock() == Blocks.OBSIDIAN) {
    		e.getWorld().setBlockState(e.getPos(), BlocksT.OBSIDIAN.getDefaultState(), 0);
    	}
    	if (e.getState().getBlock() == Blocks.ICE) {
    		e.getWorld().setBlockState(e.getPos(), Blocks.WATER.getDefaultState(), 0);
    	}
    }
    
    public static int ticks = 0;
    public static boolean spawningEye = false;
    public static boolean oncePerDay = false;
    
	public static void onWorldTick(WorldTickEvent event)
	{
	
//		if (DimensionManager.getWorld(event.world.getServer(), t, true, true) == null) {
//			DimensionManager.initWorld(event.world.getServer(), t);
//		}

		
		World world = event.world;
		
		DimensionType sky = DimensionManager.registerOrGetDimension(Dimensions.skyLocation, DimensionRegistry.skyDimension, null, true);
		
		
		world.getGameRules().get(GameRules.DO_WEATHER_CYCLE).set(false, world.getServer());

		for (PlayerEntity player : world.getPlayers()) {
			InventoryTerraria inventory = WorldEvents.getOrLoadInventory(player, world);
			if (player.getPosition().getY() < -3) {
				if (player.dimension == sky) {
					Dimensions.teleportPlayer((ServerPlayerEntity)player, DimensionType.OVERWORLD, new BlockPos(player.getPosition().getX(), 255, player.getPosition().getZ()));
					return;
				}
			}
			
			
			if (player.getPosition().getY() > 255) {
				if (player.dimension == DimensionType.OVERWORLD) {
					Dimensions.teleportPlayer((ServerPlayerEntity)player, sky, new BlockPos(player.getPosition().getX(), player.getPosition().getY() - 256, player.getPosition().getZ()));
					return;
				}
			}

			int defense = 0;
			
			
			if (inventory != null) {
				
				for (int i = 0; i < 3; i++) {
					InventorySlot slot = inventory.armor[i];
					if (slot.stack != null) {
						if (slot.stack.item instanceof Armor) {
							Armor armor = (Armor)slot.stack.item;
							defense += armor.defense;
						}
					}
				}
				
				for (int i = 0; i < inventory.accessory.length; i++) {
					InventorySlot slot = inventory.accessory[i];
					if (slot.stack != null) {
						if (slot.stack.item instanceof Accessory) {
							Accessory a = (Accessory)slot.stack.item;
							a.accessoryTick(player);
							
							ItemModifier modifier = ItemModifier.getModifier(slot.stack.modifier);
							if (modifier != null) {
								if (modifier.defense > 0) {
									defense += modifier.defense;
								}
							}
						}
					}
				}
			}
			
			float maxHealth = player.getMaxHealth();
			if (maxHealth >= 200 && defense >= 2) {
				if (!world.isRemote) {
					if (world.getDayTime() % 24000 >= 11000) {
						if (oncePerDay == false) {
							oncePerDay = true;
							if (world.rand.nextInt(10) == 0) {
								spawningEye = true;
						    	world.getServer().sendMessage(new StringTextComponent("/tellraw @a {\"text\":\"You feel an evil presence watching you.\",\"bold\":true,\"color\":\"blue\"}"));
							}
						}
					}
				}
			}
		}
		
		if (!world.isRemote) {
			if (world.getDayTime() % 24000 <= 1000) {
				oncePerDay = false;
			}
			if (spawningEye == true && world.getDayTime() % 24000 > 17500) {
				if (world.getPlayers().size() > 0) {
					PlayerEntity player = world.getPlayers().get(world.rand.nextInt(world.getPlayers().size()));
					float posX = 0, posY = world.rand.nextInt(20) - 10, posZ = 0;
		    		float rad = 20;
		    		
		    		float rotation = world.rand.nextInt(360);
		    		posX = (float) (Math.cos(Math.toDegrees(rotation)) * rad);
		    		posZ = (float) (Math.sin(Math.toDegrees(rotation)) * rad);
		    		
		    		EntityEyeOfCthulhu eye = EntitiesT.EYE_OF_CTHULHU.create(world, null, null, null, player.getPosition(), SpawnReason.EVENT, false, false);
					eye.setPosition(player.getPosition().getX() + posX, player.getPosition().getY() + posY, player.getPosition().getZ() + posZ);
					world.addEntity(eye);
					spawningEye = false;
				}
			}
			
			Trewrite.ticks++;
			if (Trewrite.ticks % 20 == 0) {
				new Thread() {
					public void run() {
						try {
							for (PlayerEntity player : event.world.getPlayers()) {
								SPacketSendAccessories packet = new SPacketSendAccessories(player);
								if (event.world instanceof ServerWorld)
								for (ServerPlayerEntity send : ((ServerWorld)event.world).getPlayers())
		    	    	 			NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> send), packet);
								
							}
						}catch (Exception e) {
							e.printStackTrace();
						}
					}
				}.start();
				
			}
			
			WorldStateHolder holder = WorldStateHolder.get(world);
			Util.minSpawnDistance = 15.0;
			Util.entitySpawnRate = 1.0/25.0;
			
			if (world.rand.nextDouble() <= Util.starChance / 3.0) {
				if (world.getPlayers().size() > 0) {
					PlayerEntity player = world.getPlayers().get(world.rand.nextInt(world.getPlayers().size()));
					double x = player.posX + world.rand.nextInt(180) - 90, y = 255, z = player.posZ + world.rand.nextInt(180) - 90;
					
					EntityItemT item = EntitiesT.ITEM.create(world, null, null, null, new BlockPos(x, y, z), SpawnReason.EVENT, false, false);
					item.setItem(new ItemStackT(ItemsT.FALLEN_STAR, 1, null));
//					EntityItemT item = new EntityItemT(worldIn, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), stack);
					item.pickupDelay = 0;
					world.addEntity(item);
					item.hitGround = false;
				}
			}
			
			

			
				
			
			
			if (world.rand.nextDouble() <= Util.entitySpawnRate) {
				for (PlayerEntity player : world.getPlayers()) {
					double x = player.posX + world.rand.nextInt(180) - 90, y = player.posY + world.rand.nextInt(180) - 90, z = player.posZ + world.rand.nextInt(180) - 90;
					for (PlayerEntity p2 : world.getPlayers()) {
						if (p2.getPositionVec().distanceTo(new Vec3d(x, y, z)) >= Util.minSpawnDistance) {
							new Thread () {
								public void run() {
									
									EntitySpawner.spawnEntities(player, x, y, z);
								}
							}.start();
							
							break;
						}
					}
					
					
					
				}
			}
		}
	}

    private void setup(final FMLCommonSetupEvent event)
    {
    	System.out.println("TREWRITE MOD SETUP");
//    	MinecraftForge.EVENT_BUS.register(new EntityEvents());
//    	MinecraftForge.EVENT_BUS.register(new OverlayEvents());
//    	MinecraftForge.EVENT_BUS.register(new ScoreboardEvents());
//    	MinecraftForge.EVENT_BUS.register(new WorldEvents());
        // some preinit code
    	
    	
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
//    	RenderingRegistry.registerEntityRenderingHandler(EntityItemT.class, manager -> new RenderEntityItemT(manager, Minecraft.getInstance().getItemRenderer()));
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // some example code to dispatch IMC to another mod
    }

    private void processIMC(final InterModProcessEvent event)
    {
        // some example code to receive and process InterModComms from other mods
    }
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        // do something when the server starts
    }
    
    
}
