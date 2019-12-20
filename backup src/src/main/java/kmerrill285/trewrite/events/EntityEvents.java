package kmerrill285.trewrite.events;

import java.util.Random;

import kmerrill285.trewrite.blocks.BlockT;
import kmerrill285.trewrite.blocks.BlocksT;
import kmerrill285.trewrite.blocks.Chest;
import kmerrill285.trewrite.core.inventory.InventoryChestTerraria;
import kmerrill285.trewrite.core.inventory.InventorySlot;
import kmerrill285.trewrite.core.inventory.InventoryTerraria;
import kmerrill285.trewrite.core.inventory.container.ContainerTerrariaInventory;
import kmerrill285.trewrite.core.network.NetworkHandler;
import kmerrill285.trewrite.core.network.client.CPacketCloseInventoryTerraria;
import kmerrill285.trewrite.core.network.client.CPacketEquipItemTerraria;
import kmerrill285.trewrite.core.network.client.CPacketRequestInventoryTerraria;
import kmerrill285.trewrite.items.Armor;
import kmerrill285.trewrite.items.Axe;
import kmerrill285.trewrite.items.Broadsword;
import kmerrill285.trewrite.items.Hammer;
import kmerrill285.trewrite.items.ItemBlockT;
import kmerrill285.trewrite.items.ItemT;
import kmerrill285.trewrite.items.Pickaxe;
import kmerrill285.trewrite.items.Shortsword;
import kmerrill285.trewrite.items.modifiers.ItemModifier;
import kmerrill285.trewrite.util.Conversions;
import kmerrill285.trewrite.util.Util;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.FlyingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class EntityEvents {
	
	@SubscribeEvent
	public static void handleItemToss(ItemTossEvent event) {
		
		if (event.getPlayer() != null) {
			PlayerEntity player = event.getPlayer();
			
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
		
		if (event.getEntity() instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity)event.getEntity();
			
			
			if (player.getAttribute(SharedMonsterAttributes.MAX_HEALTH).getBaseValue() < 100) {
				player.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100);
				player.setHealth(100);
			}
			
		}
	}
	@OnlyIn(value=Dist.CLIENT)
	@SubscribeEvent
	public static void handleEntitySpawnsClient(EntityJoinWorldEvent event) {
		if (event.getEntity() instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity)event.getEntity();
			if (Minecraft.getInstance() != null)
				if (Minecraft.getInstance().player != null)
			if (player.getScoreboardName().contentEquals(Minecraft.getInstance().player.getScoreboardName())) {
				System.out.println("JOINED THE WORLD.  REQUESTING INVENTORY DATA FROM SERVER FOR WORLD: " + Minecraft.getInstance().player.world.getWorldInfo().getWorldName());
				
				if (Minecraft.getInstance().isSingleplayer()) {
					NetworkHandler.INSTANCE.sendToServer(new CPacketRequestInventoryTerraria(Minecraft.getInstance().player.getScoreboardName(),Minecraft.getInstance().getIntegratedServer().getFolderName()));

				} else {
					NetworkHandler.INSTANCE.sendToServer(new CPacketRequestInventoryTerraria(Minecraft.getInstance().player.getScoreboardName(),Minecraft.getInstance().player.getEntityWorld().getWorldInfo().getWorldName()));

				}
				
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
			event.getEntityLiving().setMotion(event.getEntityLiving().getMotion().x, 0, event.getEntityLiving().getMotion().z);
		}
		if (event.getOriginalAttacker() != null) {
			if (event.getOriginalAttacker() instanceof PlayerEntity) {
				PlayerEntity player = (PlayerEntity)event.getOriginalAttacker();
				if (player.getHeldItemMainhand() != null) {
					if (player.getHeldItemMainhand().getItem() instanceof ItemT) {
						
						double attackMul = 1.0;
						InventoryTerraria inventory = null;
						if (!player.world.isRemote) {
							inventory = WorldEvents.inventories.get(player.getScoreboardName());
						}
						else {
							inventory = ContainerTerrariaInventory.inventory;
						}
						if (inventory != null) {
							InventorySlot selected = inventory.hotbar[inventory.hotbarSelected];
							if (selected != null) {
								if (selected.stack != null) {
									ItemModifier modifier = ItemModifier.getModifier(selected.stack.modifier);
									if (modifier != null) {
										attackMul += modifier.knockback / 100.0;
									}
								}
							}
						}
						
						event.setStrength((float) (Conversions.feetToMeters * ((ItemT)player.getHeldItemMainhand().getItem()).knockback * 0.2f * attackMul));
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void handleEntityDamage(LivingDamageEvent event) {
		int armor = 0;
		if (event.getEntity() instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity)event.getEntity();
			
			InventoryTerraria inventory = null;
			if (!player.world.isRemote) {
				inventory = WorldEvents.inventories.get(player.getScoreboardName());
			}
			else {
				inventory = ContainerTerrariaInventory.inventory;
			}
			if (inventory != null) {
				
				for (int i = 0; i < 3; i++) {
					if (inventory.armor[i].stack != null) {
						if (inventory.armor[i].stack.item instanceof Armor) {
							Armor a = (Armor)inventory.armor[i].stack.item;
							armor += a.defense;
						}
					}
				}
			}
			event.setAmount(event.getAmount() - armor);
			if (event.getAmount() < 1) event.setAmount(1);
		}
		
		if (event.getSource().getImmediateSource() instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity)event.getSource().getImmediateSource();
			double cooldownMul = 1.0;
			double attackMul = 1.0;
			InventoryTerraria inventory = null;
			if (!player.world.isRemote) {
				inventory = WorldEvents.inventories.get(player.getScoreboardName());
			}
			else {
				inventory = ContainerTerrariaInventory.inventory;
			}
			if (inventory != null) {
				
				for (int i = 0; i < 3; i++) {
					if (inventory.armor[i].stack != null) {
						if (inventory.armor[i].stack.item instanceof Armor) {
							Armor a = (Armor)inventory.armor[i].stack.item;
							armor += a.defense;
						}
					}
				}
				
				InventorySlot selected = inventory.hotbar[inventory.hotbarSelected];
				if (selected != null) {
					if (selected.stack != null) {
						ItemModifier modifier = ItemModifier.getModifier(selected.stack.modifier);
						if (modifier != null) {
							cooldownMul -= modifier.speed / 100.0;
							attackMul += modifier.damage / 100.0;
						}
					}
				}
			}
			
			
			if (player.getHeldItemMainhand() != null) {
				if (player.getHeldItemMainhand().getItem() instanceof ItemT) {
					
					ItemT item = (ItemT)player.getHeldItemMainhand().getItem();
					if (item.melee == true) {
						event.setAmount(event.getAmount() * (float)attackMul);
					}
					player.getCooldownTracker().setCooldown(item, (int)(player.getCooldownTracker().getCooldown(item, 0.0f) * cooldownMul));
				}
			}
		}
		
	}
	
	@SubscribeEvent
	public static void handleLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
		if (event.getPlayer() != null)
		if (event.getItemStack() != null) {
			if (event.getItemStack().getItem() instanceof Hammer) {
				Hammer hammer = (Hammer)event.getItemStack().getItem();
				hammer.onMineBlock(event.getWorld().getBlockState(event.getPos()), event.getEntityPlayer());
			}
		}
	}
	
	@SubscribeEvent
	public static void handleInteract(PlayerInteractEvent.RightClickBlock event) {
		if (event.getItemStack() != null) {
			if (event.getItemStack().getItem() instanceof ItemT) {
				if (event.getHand() == Hand.OFF_HAND) {
					event.setCanceled(true);
					return;
				}
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

						PlayerEntity player = event.getEntityPlayer();
						
					    player.world.playSound((PlayerEntity)null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 1.0F, 1.0F);

						
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
						PlayerEntity player = itemEvent.getEntityPlayer();
						
					    player.world.playSound((PlayerEntity)null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 1.0F, 1.0F);

						
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
							PlayerEntity player = itemEvent.getEntityPlayer();
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
			PlayerEntity player = event.getEntityPlayer();
			
			if (player.getHeldItemMainhand() != null)
			{
				
				double speedMul = 1.0;
				InventoryTerraria inventory = null;
				if (!player.world.isRemote) {
					inventory = WorldEvents.inventories.get(player.getScoreboardName());
				}
				else {
					inventory = ContainerTerrariaInventory.inventory;
				}
				if (inventory != null) {
					InventorySlot selected = inventory.hotbar[inventory.hotbarSelected];
					if (selected != null) {
						if (selected.stack != null) {
							ItemModifier modifier = ItemModifier.getModifier(selected.stack.modifier);
							if (modifier != null) {
								speedMul -= modifier.speed / 100.0;
							}
						}
					}
				}
				
				Item _item = player.getHeldItemMainhand().getItem();
				BlockState blockstate = event.getState();
				
				
				if (blockstate.getBlock() instanceof BlockT) {
					BlockT block = (BlockT)blockstate.getBlock();
					
					
					
					
					if (_item instanceof ItemT) {
						ItemT item = (ItemT)_item;
//						System.out.println(event.getOriginalSpeed() * block.getMiningSpeed(item));
						if (block.difficulty > 0) {
							float miningSpeed = block.getMiningSpeed(item);
							if (miningSpeed > 0) miningSpeed *= speedMul;
							event.setNewSpeed(miningSpeed);
						}
					} else {
						event.setNewSpeed(-1);
					}
					
					if (blockstate.getBlock() instanceof Chest) {
						BlockPos up = event.getPos();
						String position = new String(up.getX()+","+up.getY()+","+up.getZ());
						InventoryChestTerraria chest = new InventoryChestTerraria();
						chest.load(position, event.getEntityPlayer().getEntityWorld().getWorldInfo().getWorldName());
						boolean hasItems = false;
						for (InventorySlot slot : chest.chest) {
							if (slot != null) {
								if (slot.stack != null)
								if (slot.stack.size > 0) {
									hasItems = true;
									break;
								}
							}
						}
						if (hasItems == true) {
							event.setNewSpeed(-1);
						}
					}
				}
				
			}
		}
		
	}
	
	
	@SubscribeEvent
	public static void handlePlayerEvent(PlayerEvent event) {
		if (event.getPlayer() != null) {
			PlayerEntity player = event.getPlayer();
			player.getFoodStats().setFoodLevel(20);
			
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
			if (event.getEntityLiving() instanceof PlayerEntity) {
				PlayerEntity player = (PlayerEntity)event.getEntityLiving();
				
				
				
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
			if (event.getEntityLiving() instanceof PlayerEntity) {
				PlayerEntity player = (PlayerEntity)event.getEntityLiving();
				
				
				
				if (!player.world.isRemote) {
					if (player.getServer() != null) {
						World world = player.getEntityWorld();
						Scoreboard scoreboard = player.getWorldScoreboard();
						
						ScoreboardEvents.handleScoreboard(player, world, scoreboard);
					}
				}
				
				double reachMul = 1.0;
				double attackMul = 1.0;
				double knockbackMul = 1.0;
				InventoryTerraria inventory = null;
				if (!player.world.isRemote) {
					inventory = WorldEvents.inventories.get(player.getScoreboardName());
				}
				else {
					inventory = ContainerTerrariaInventory.inventory;
				}
				if (inventory != null) {
					InventorySlot selected = inventory.hotbar[inventory.hotbarSelected];
					if (selected != null) {
						if (selected.stack != null) {
							ItemModifier modifier = ItemModifier.getModifier(selected.stack.modifier);
							if (modifier != null) {
								reachMul += modifier.size / 100.0;
								attackMul += modifier.damage / 100.0;
								knockbackMul += modifier.knockback / 100.0;
							}
						}
					}
				}
				
				double baseReach = 2.0 * reachMul;
				player.getAttribute(PlayerEntity.REACH_DISTANCE).setBaseValue(baseReach + 4.0);
				
				
        		player.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(attackMul);
        		
				
        		if (player.getHeldItemMainhand() != null) {
        			Item item = player.getHeldItemMainhand().getItem();
        			if (item instanceof ItemBlockT || item instanceof Axe || item instanceof Pickaxe || item instanceof Hammer) {
        				player.getAttribute(PlayerEntity.REACH_DISTANCE).setBaseValue(baseReach + 4.0 + ((ItemT)item).range);
        			}
        			if (item instanceof Broadsword) {
        				player.getAttribute(PlayerEntity.REACH_DISTANCE).setBaseValue(baseReach + 3.0 + ((ItemT)item).range);
        			}
        			if (item instanceof Shortsword) {
        				player.getAttribute(PlayerEntity.REACH_DISTANCE).setBaseValue(baseReach + 1.25 + ((ItemT)item).range);
        			}
        			
        		}
			}
			event.getEntity().setNoGravity(true);
			
			if (!(event.getEntity() instanceof FlyingEntity))
				if (event.getEntity().getMotion().getY() > -Conversions.convertToIngame(Util.terminalVelocity));
			Vec3d motion = event.getEntity().getMotion();
			if (!event.getEntityLiving().isInWater()) {
				event.getEntity().getMotion().add(0, -Conversions.convertToIngame(9.82f / 20.0f), 0);
				event.getEntity().setMotion(new Vec3d(motion.getX(), motion.getY() - Conversions.convertToIngame(9.82f / 20.0f), motion.getZ()));
			}
			event.getEntity().stepHeight = 1.0f;

			if (event.getEntityLiving() != null) {
				if (event.getEntityLiving().hurtTime > 0) {
					event.getEntityLiving().setMotion(event.getEntityLiving().getMotion().x, 0, event.getEntityLiving().getMotion().z);
				}
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
			Vec3d motion = event.getEntityLiving().getMotion();
			event.getEntityLiving().setMotion(motion.getX(), motion.getY() + Conversions.convertToIngame(3.6576f * 2.0f) * 1.0f, motion.getZ());
			
			
			event.getEntity().setNoGravity(true);
		}
		
		
	}
	@SubscribeEvent
	public static void handleLivingFallEvent(LivingFallEvent event) {
		
		float heightInFeet = event.getDistance() * Conversions.metersToFeet;
		if (heightInFeet > 25) {
			event.setDistance((heightInFeet - 25.0f) * Conversions.feetToMeters * 5);
		} else {
			event.setDistance(0);
		}
	}
}
