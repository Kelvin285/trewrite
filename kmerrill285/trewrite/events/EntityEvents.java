package kmerrill285.trewrite.events;

import kmerrill285.trewrite.Trewrite;
import kmerrill285.trewrite.blocks.BlockT;
import kmerrill285.trewrite.blocks.BlocksT;
import kmerrill285.trewrite.core.inventory.InventorySlot;
import kmerrill285.trewrite.core.inventory.InventoryTerraria;
import kmerrill285.trewrite.core.inventory.container.ContainerTerrariaInventory;
import kmerrill285.trewrite.core.network.NetworkHandler;
import kmerrill285.trewrite.core.network.client.CPacketCloseInventoryTerraria;
import kmerrill285.trewrite.core.network.client.CPacketEquipItemTerraria;
import kmerrill285.trewrite.core.network.client.CPacketRequestInventoryTerraria;
import kmerrill285.trewrite.core.network.server.SPacketSyncInventoryTerraria;
import kmerrill285.trewrite.entities.EntityItemT;
import kmerrill285.trewrite.items.ItemBlockT;
import kmerrill285.trewrite.items.ItemT;
import kmerrill285.trewrite.util.Conversions;
import kmerrill285.trewrite.util.Util;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;

@Mod.EventBusSubscriber
public class EntityEvents {
	
	@SubscribeEvent
	public static void handleItemToss(ItemTossEvent event) {
		
		if (event.getPlayer() != null) {
			EntityPlayer player = event.getPlayer();
			
			if (player.world.isRemote) { 
				InventoryTerraria inventory = WorldEvents.inventories.get(player.getScoreboardName());
				
				if (Util.terrariaInventory) {
					event.setCanceled(true);
				}
			} else {
				InventoryTerraria inventory = WorldEvents.inventories.get(player.getScoreboardName());
				
				if (inventory.open) {
					event.setCanceled(true);
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void handleEntitySpawns(EntityJoinWorldEvent event) {
		
		if (event.getEntity() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer)event.getEntity();
			
			
			if (player.getAttribute(SharedMonsterAttributes.MAX_HEALTH).getBaseValue() < 100) {
				player.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100);
				player.setHealth(100);
			}
			
		}
	}
	@OnlyIn(value=Dist.CLIENT)
	@SubscribeEvent
	public static void handleEntitySpawnsClient(EntityJoinWorldEvent event) {
		if (event.getEntity() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer)event.getEntity();
			if (Minecraft.getInstance() != null)
				if (Minecraft.getInstance().player != null)
			if (player.getScoreboardName().contentEquals(Minecraft.getInstance().player.getScoreboardName())) {
				System.out.println("JOINED THE WORLD.  REQUESTING INVENTORY DATA FROM SERVER");
				NetworkHandler.INSTANCE.sendToServer(new CPacketRequestInventoryTerraria(Minecraft.getInstance().player.getScoreboardName(), Minecraft.getInstance().getIntegratedServer().getWorldName()));
			}
			
		}
	}
	
	@SubscribeEvent
	public static void handleDamage (LivingDamageEvent event) {
		if (event.getEntityLiving() != null) {
			
		}
		
	}
	
	@SubscribeEvent
	public static void handleKnockback(LivingKnockBackEvent event) {
		if (event.getEntityLiving() != null) {
			event.getEntityLiving().motionY = 0;
		}
		if (event.getOriginalAttacker() != null) {
			if (event.getOriginalAttacker() instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer)event.getOriginalAttacker();
				if (player.getHeldItemMainhand() != null) {
					if (player.getHeldItemMainhand().getItem() instanceof ItemT) {
						event.setStrength(Conversions.feetToMeters * ((ItemT)player.getHeldItemMainhand().getItem()).knockback * 0.2f);
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void handlePlayerAttack(AttackEntityEvent event) {
		if (event.getTarget() != null) {
			if (event.getEntityPlayer() != null) {
				if (event.getEntityPlayer().getHeldItemMainhand() != null) {
					if (event.getEntityPlayer().getHeldItemMainhand().getItem() instanceof ItemT) {
						ItemT item = (ItemT)event.getEntityPlayer().getHeldItemMainhand().getItem();
						if (item.melee == true) {
							if (item.onAttack(event.getTarget(), null, event.getEntityPlayer(), null, null) == false)
								event.setResult(Result.DENY);
						}
					}
				}
			}
			event.setResult(Result.DENY);
		}
	}
	
	@SubscribeEvent
	public static void handleInteract(PlayerInteractEvent.RightClickBlock event) {
		if (event.getItemStack() != null) {
			if (event.getItemStack().getItem() instanceof ItemT) {
				((ItemT)event.getItemStack().getItem()).onUse(null, event.getPos(), event.getEntityPlayer(), event.getWorld(), event.getHand());
				if (((ItemT)event.getItemStack().getItem()).consumable) {
					boolean shrink = false;
					if (event.getEntityPlayer().getHealth() < event.getEntityPlayer().getMaxHealth()) {
						boolean heal = true;
						if (((ItemT)event.getItemStack().getItem()).potionSickness > 0) {
							Score potionSickness = ScoreboardEvents.getScore(event.getEntityPlayer().getWorldScoreboard(), event.getEntityPlayer(), ScoreboardEvents.POTION_SICKNESS);
							if (potionSickness.getScorePoints() > 0) heal = false;
							else potionSickness.setScorePoints(((ItemT)event.getItemStack().getItem()).potionSickness * 20);
						}
						
						if (heal) {
							event.getEntityPlayer().heal(((ItemT)event.getItemStack().getItem()).heal);
							shrink = true;
						}
					}
					Score mana = ScoreboardEvents.getScore(event.getEntityPlayer().getWorldScoreboard(), event.getEntityPlayer(), ScoreboardEvents.MANA);
					Score maxMana = ScoreboardEvents.getScore(event.getEntityPlayer().getWorldScoreboard(), event.getEntityPlayer(), ScoreboardEvents.MAX_MANA);
					if (mana.getScorePoints() < maxMana.getScorePoints()) {
						Score manaSickness = ScoreboardEvents.getScore(event.getEntityPlayer().getWorldScoreboard(), event.getEntityPlayer(), ScoreboardEvents.MANA_SICKNESS);
						Score manaSicknessEffect = ScoreboardEvents.getScore(event.getEntityPlayer().getWorldScoreboard(), event.getEntityPlayer(), ScoreboardEvents.MANA_SICKNESS_EFFECT);
						if (manaSickness.getScorePoints() < 10 * 20) {
							manaSickness.setScorePoints(manaSickness.getScorePoints() + 5 * 20);
						} else {
							manaSickness.setScorePoints(10 * 20);
						}
						if (manaSicknessEffect.getScorePoints() < 2)
						manaSicknessEffect.setScorePoints(manaSicknessEffect.getScorePoints() + 1);
					}
					if (shrink) {
						EntityPlayer player = event.getEntityPlayer();
						if (player != null) {
							if (event.getWorld().isRemote) {
								if (Util.terrariaInventory) {
									InventoryTerraria inventory = ContainerTerrariaInventory.inventory;
									if (inventory.hotbar[inventory.hotbarSelected].stack != null) {
										inventory.hotbar[inventory.hotbarSelected].stack.size --;
										if (inventory.hotbar[inventory.hotbarSelected].stack.size <= 0)
											inventory.hotbar[inventory.hotbarSelected].stack = null;
									}
								} else {
									event.getItemStack().shrink(1);
								}
							} else {
								InventoryTerraria inventory = WorldEvents.inventories.get(player.getScoreboardName());
								if (inventory != null && inventory.open == true) {
									if (inventory.hotbar[inventory.hotbarSelected].stack != null) {
										inventory.hotbar[inventory.hotbarSelected].stack.size --;
										if (inventory.hotbar[inventory.hotbarSelected].stack.size <= 0)
											inventory.hotbar[inventory.hotbarSelected].stack = null;
									}
								} else {
									event.getItemStack().shrink(1);
								}
							}
						}
					}
						
				}
			}
		}
	}
	
	//MAX LIFE CRYSTALS: 15 (20HP each)
	//MAX LIFE FRUIT: 20 (5HP each)
	
	@SubscribeEvent
	public static void handleInteract(PlayerInteractEvent.RightClickItem itemEvent) {
		
		if (itemEvent.getItemStack() != null) {
			if (itemEvent.getItemStack().getItem() instanceof ItemT) {
				((ItemT)itemEvent.getItemStack().getItem()).onUse(null, itemEvent.getPos(), itemEvent.getEntityPlayer(), itemEvent.getWorld(), itemEvent.getHand());
				if (((ItemT)itemEvent.getItemStack().getItem()).consumable) {
					boolean shrink = false;
					if (itemEvent.getEntityPlayer().getHealth() < itemEvent.getEntityPlayer().getMaxHealth()) {
						boolean heal = true;
						if (((ItemT)itemEvent.getItemStack().getItem()).potionSickness > 0) {
							Score potionSickness = ScoreboardEvents.getScore(itemEvent.getEntityPlayer().getWorldScoreboard(), itemEvent.getEntityPlayer(), ScoreboardEvents.POTION_SICKNESS);
							if (potionSickness.getScorePoints() > 0) heal = false;
							else potionSickness.setScorePoints(((ItemT)itemEvent.getItemStack().getItem()).potionSickness * 20);
						}
						if (heal) {
							itemEvent.getEntityPlayer().heal(((ItemT)itemEvent.getItemStack().getItem()).heal);
							shrink = true;
						}
					}
					
					if (shrink) {
						EntityPlayer player = itemEvent.getEntityPlayer();
						if (player != null) {
							if (itemEvent.getWorld().isRemote) {
								if (Util.terrariaInventory) {
									InventoryTerraria inventory = ContainerTerrariaInventory.inventory;
									if (inventory.hotbar[inventory.hotbarSelected].stack != null) {
										inventory.hotbar[inventory.hotbarSelected].stack.size --;
										if (inventory.hotbar[inventory.hotbarSelected].stack.size <= 0)
											inventory.hotbar[inventory.hotbarSelected].stack = null;
									}
								} else {
									itemEvent.getItemStack().shrink(1);
								}
							} else {
								InventoryTerraria inventory = WorldEvents.inventories.get(player.getScoreboardName());
								if (inventory != null && inventory.open == true) {
									if (inventory.hotbar[inventory.hotbarSelected].stack != null) {
										inventory.hotbar[inventory.hotbarSelected].stack.size --;
										if (inventory.hotbar[inventory.hotbarSelected].stack.size <= 0)
											inventory.hotbar[inventory.hotbarSelected].stack = null;
									}
								} else {
									itemEvent.getItemStack().shrink(1);
								}
							}
						}
					}
				}
			}
			if (itemEvent.getItemStack().getItem() instanceof ItemBlockT && itemEvent.getWorld().getBlockState(itemEvent.getPos()) == Blocks.AIR.getDefaultState()) {
				//((ItemT)itemEvent.getItemStack().getItem()).onUse(null, itemEvent.getPos(), itemEvent.getEntityPlayer(), itemEvent.getWorld(), itemEvent.getHand());
				if (((BlockT)((ItemBlockT)itemEvent.getItemStack().getItem()).getBlock()) != null) {
					BlockT block = ((BlockT)((ItemBlockT)itemEvent.getItemStack().getItem()).getBlock());
					if (block.consumable == true) {
						boolean shrink = false;
						if (itemEvent.getEntityPlayer().getHealth() < itemEvent.getEntityPlayer().getMaxHealth()) {
							Score potionSickness = ScoreboardEvents.getScore(itemEvent.getEntityPlayer().getWorldScoreboard(), itemEvent.getEntityPlayer(), ScoreboardEvents.POTION_SICKNESS);
							boolean heal = true;
							if (block.potionSickness > 0) {
								if (potionSickness.getScorePoints() > 0) heal = false;
								else potionSickness.setScorePoints(block.potionSickness * 20);
							}
							if (heal) {
								itemEvent.getEntityPlayer().heal(block.health);
								shrink = true;
							}
						}
						
						if (block.getDefaultState() == BlocksT.LIFE_CRYSTAL.getDefaultState()) { 
							Score lifeCrystals = ScoreboardEvents.getScore(itemEvent.getEntityPlayer().getWorldScoreboard(), itemEvent.getEntityPlayer(), ScoreboardEvents.LIFE_CRYSTALS);
							if (lifeCrystals.getScorePoints() < 15) {
								lifeCrystals.setScorePoints(lifeCrystals.getScorePoints() + 1);
								itemEvent.getEntityPlayer().heal(20);
								shrink = true;
							} else {
								shrink = false;
							}
						}
						
						if (shrink) {
							EntityPlayer player = itemEvent.getEntityPlayer();
							if (player != null) {
								if (itemEvent.getWorld().isRemote) {
									if (Util.terrariaInventory) {
										InventoryTerraria inventory = ContainerTerrariaInventory.inventory;
										if (inventory.hotbar[inventory.hotbarSelected].stack != null) {
											inventory.hotbar[inventory.hotbarSelected].stack.size --;
											if (inventory.hotbar[inventory.hotbarSelected].stack.size <= 0)
												inventory.hotbar[inventory.hotbarSelected].stack = null;
										}
									} else {
										itemEvent.getItemStack().shrink(1);
									}
								} else {
									InventoryTerraria inventory = WorldEvents.inventories.get(player.getScoreboardName());
									if (inventory != null && inventory.open == true) {
										if (inventory.hotbar[inventory.hotbarSelected].stack != null) {
											inventory.hotbar[inventory.hotbarSelected].stack.size --;
											if (inventory.hotbar[inventory.hotbarSelected].stack.size <= 0)
												inventory.hotbar[inventory.hotbarSelected].stack = null;
										}
									} else {
										itemEvent.getItemStack().shrink(1);
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void handleMining(BreakSpeed event) {
		if (event.getEntityPlayer() != null) {
			EntityPlayer player = event.getEntityPlayer();
			
			if (player.getHeldItemMainhand() != null)
			{
				Item _item = player.getHeldItemMainhand().getItem();
				IBlockState blockstate = event.getState();
				
				if (blockstate.getBlock() instanceof BlockT) {
					BlockT block = (BlockT)blockstate.getBlock();
					if (_item instanceof ItemT) {
						ItemT item = (ItemT)_item;
//						System.out.println(event.getOriginalSpeed() * block.getMiningSpeed(item));
						if (block.difficulty > 0) {
							event.setNewSpeed(block.getMiningSpeed(item));
						}
					} else {
						event.setNewSpeed(-1);
					}
				}
				
			}
		}
		
	}
	
	
	@SubscribeEvent
	public static void handlePlayerEvent(PlayerEvent event) {
		if (event.getEntityPlayer() != null) {
			EntityPlayer player = event.getEntityPlayer();
			
			Score lifeCrystals = ScoreboardEvents.getScore(player.getWorldScoreboard(), player, ScoreboardEvents.LIFE_CRYSTALS);
			
			player.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100 + lifeCrystals.getScorePoints() * 20);
			if (player.getHeldItemMainhand() != null && player.getHeldItemMainhand().getItem() instanceof ItemT)
			if (player.getHeldItemMainhand().getDamage() != 0) {
				player.getHeldItemMainhand().setDamage(0);
			}
		}
		Util.watchTime = "";
	}
	
	
	@OnlyIn(value=Dist.CLIENT)
	@SubscribeEvent
	public static void handleClientLivingEvent(LivingEvent event) {
		
		if (event.getEntity() != null) {
			if (event.getEntityLiving() instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer)event.getEntityLiving();
				World world = player.getEntityWorld();
				Scoreboard scoreboard = player.getWorldScoreboard();
				if (Minecraft.getInstance() != null)
					if (Minecraft.getInstance().player != null)
				if (player.getScoreboardName().contentEquals(Minecraft.getInstance().player.getScoreboardName())) {
					if (Util.terrariaInventory)
						NetworkHandler.INSTANCE.sendToServer(new CPacketEquipItemTerraria(ContainerTerrariaInventory.inventory.hotbarSelected));
					else if (Util.justClosedInventory) {
						Util.justClosedInventory = false;
						NetworkHandler.INSTANCE.sendToServer(new CPacketCloseInventoryTerraria());
					}
					ScoreboardEvents.handleClientScoreboard(player, world, scoreboard);
				}
				
			}
		}
		

	}
	
	@SubscribeEvent
	public static void handleLivingEvent(LivingEvent event) {
		if (event.getEntity() != null) {
			if (event.getEntityLiving() instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer)event.getEntityLiving();
				if (!player.world.isRemote)
				if (player.getServer() != null) {
					World world = player.getEntityWorld();
					Scoreboard scoreboard = player.getWorldScoreboard();
					
					ScoreboardEvents.handleScoreboard(player, world, scoreboard);
				}
			}
			event.getEntity().setNoGravity(true);
			
			if (!(event.getEntity() instanceof EntityFlying))
				if (event.getEntity().motionY > -Conversions.convertToIngame(Util.terminalVelocity));
			event.getEntity().motionY -= Conversions.convertToIngame(9.82f / 20.0f);
			event.getEntity().stepHeight = 1.0f;
			
			if (event.getEntityLiving() != null) {
				if (event.getEntityLiving().hurtTime > 0)
					event.getEntityLiving().motionY = 0;
			}
		}
		
		
	}
	
	//Minecraft Falling Damage =(number of blocks fallen x ½) - 1½
	//Terraria falling damage = 10(h - 25)
	
	@SubscribeEvent
	public static void handleLivingJumpEvent(LivingEvent.LivingJumpEvent event) {
//		if (event.getEntity() instanceof EntitySlime) System.out.println("true");
		if (event.getEntityLiving() != null) {
			if (event.getEntityLiving().isInWater()) {
				System.out.println("water");
			}
			event.getEntityLiving().motionY += Conversions.convertToIngame(3.6576f * 2.0f);
			
			
			event.getEntity().setNoGravity(true);
		}
		
		
	}
	@SubscribeEvent
	public static void handleLivingFallEvent(LivingFallEvent event) {
		
		float heightInFeet = event.getDistance() * Conversions.metersToFeet;
		if (heightInFeet > 25) {
			event.setDistance((heightInFeet - 25.0f) * Conversions.feetToMeters);
		} else {
			event.setDistance(0);
		}
	}
}
