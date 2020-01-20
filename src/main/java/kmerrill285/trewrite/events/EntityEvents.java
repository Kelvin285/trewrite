package kmerrill285.trewrite.events;

import kmerrill285.trewrite.blocks.Bed;
import kmerrill285.trewrite.blocks.BlockT;
import kmerrill285.trewrite.blocks.BlocksT;
import kmerrill285.trewrite.blocks.Chest;
import kmerrill285.trewrite.core.inventory.InventoryChestTerraria;
import kmerrill285.trewrite.core.inventory.InventorySlot;
import kmerrill285.trewrite.core.inventory.InventoryTerraria;
import kmerrill285.trewrite.core.inventory.container.ContainerTerrariaInventory;
import kmerrill285.trewrite.core.items.ItemStackT;
import kmerrill285.trewrite.core.network.NetworkHandler;
import kmerrill285.trewrite.core.network.client.CPacketCloseInventoryTerraria;
import kmerrill285.trewrite.core.network.client.CPacketEquipItemTerraria;
import kmerrill285.trewrite.core.network.client.CPacketRequestInventoryTerraria;
import kmerrill285.trewrite.core.network.client.CPacketSyncInventoryTerraria;
import kmerrill285.trewrite.core.network.server.SPacketForceMovement;
import kmerrill285.trewrite.core.network.server.SPacketSyncInventoryTerraria;
import kmerrill285.trewrite.entities.EntityItemT;
import kmerrill285.trewrite.entities.monsters.EntityEyeOfCthulhu;
import kmerrill285.trewrite.items.Armor;
import kmerrill285.trewrite.items.Axe;
import kmerrill285.trewrite.items.Broadsword;
import kmerrill285.trewrite.items.Hammer;
import kmerrill285.trewrite.items.ItemBlockT;
import kmerrill285.trewrite.items.ItemT;
import kmerrill285.trewrite.items.ItemsT;
import kmerrill285.trewrite.items.Pickaxe;
import kmerrill285.trewrite.items.Shortsword;
import kmerrill285.trewrite.items.accessories.Accessory;
import kmerrill285.trewrite.items.modifiers.ItemModifier;
import kmerrill285.trewrite.items.terraria.accessories.HermesBoots;
import kmerrill285.trewrite.util.Conversions;
import kmerrill285.trewrite.util.Util;
import kmerrill285.trewrite.world.WorldStateHolder;
import kmerrill285.trewrite.world.dimension.DimensionRegistry;
import kmerrill285.trewrite.world.dimension.Dimensions;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FlyingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Items;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class EntityEvents {
	
	@SubscribeEvent
	public static void handleBlockBreak(BreakEvent e) {
		if (!(e.getState().getBlock() instanceof BlockT)) {
    		if (e.getState().getMaterial() == Material.ROCK || e.getState().getMaterial() == Material.IRON)
    		if (Item.getItemFromBlock(e.getState().getBlock()) != null) {
    			Item item = Item.getItemFromBlock(e.getState().getBlock());
    			EntityItemT.spawnItem(e.getWorld().getWorld(), e.getPos(), new ItemStackT(item, 1));
    		}
		}
	}
	
	@SubscribeEvent
	public static void handleItemToss(ItemTossEvent event) {
		
		if (event.getPlayer() != null) {
			PlayerEntity player = event.getPlayer();
			
			if (player.world.isRemote) { 
				InventoryTerraria inventory = WorldEvents.getOrLoadInventory(player, player.world);
				
				if (Util.terrariaInventory) {
					event.setCanceled(true);
				}
			} else {
				InventoryTerraria inventory = WorldEvents.getOrLoadInventory(player, player.world);
				if (inventory != null) {
					if (inventory.open) {
						event.setCanceled(true);
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void handleEntitySpawns(EntityJoinWorldEvent event) {
		
		if (event.getEntity() instanceof ItemEntity) {
			ItemEntity item = (ItemEntity)event.getEntity();
			EntityItemT.spawnItem(event.getWorld(), item.getPosition(), new ItemStackT(item.getItem().getItem(), item.getItem().getCount()));
			
			event.getEntity().remove();
		}
		
		if (event.getEntity() instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity)event.getEntity();
			
			
			if (player.getAttribute(SharedMonsterAttributes.MAX_HEALTH).getBaseValue() < 100) {
				player.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100);
				player.setHealth(100);
			} else {
				player.setHealth((float)player.getAttribute(SharedMonsterAttributes.MAX_HEALTH).getBaseValue() / 2.0f);
			}
			
			if (!event.getWorld().isRemote) {
				BlockPos pos = WorldStateHolder.get(event.getWorld().getServer().getWorld(DimensionType.OVERWORLD)).spawnPositions.get(player.getScoreboardName());
				if (pos != null) {
					if ((player.world.getBlockState(pos).getBlock() instanceof Bed)) {
						player.setPositionAndUpdate(pos.getX(), pos.getY(), pos.getZ());
					} else {
						Dimensions.teleportPlayer((ServerPlayerEntity)player, DimensionType.OVERWORLD, player.getPosition());
						player.setSpawnDimenion(DimensionType.OVERWORLD);
					}
				}
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
			if (event.getSource() == DamageSource.DROWN || event.getSource() == DamageSource.IN_WALL) {
				if (!(event.getEntityLiving() instanceof PlayerEntity)) {
					event.setCanceled(true);
				}
			}
			if (event.getSource() == DamageSource.LAVA) {
				event.setAmount(event.getAmount() * 10);
			}
			if (event.getSource() == DamageSource.ON_FIRE) {
				event.setAmount(event.getAmount() * 5);
			}
			if (event.getSource() == DamageSource.HOT_FLOOR) {
				if (event.getEntityLiving() instanceof PlayerEntity)
				if (!event.getEntityLiving().world.isRemote) {
					PlayerEntity player = (PlayerEntity)event.getEntityLiving();
					InventoryTerraria inventory = WorldEvents.getOrLoadInventory(player, player.world);
					if (inventory != null) {
						boolean hasSkull = false;
						for (int i = 0; i < inventory.accessory.length; i++) {
							InventorySlot slot = inventory.accessory[i];
							if (slot.stack != null) {
								if (slot.stack.item == ItemsT.OBSIDIAN_SKULL) {
									hasSkull = true;
									break;
								}
							}
						}
						if (hasSkull) {
							event.setAmount(0);
							event.setCanceled(true);
						}
					}
				}
			}
		}
		
	}
	
	@SubscribeEvent
	public static void handleKnockback(LivingKnockBackEvent event) {
		
		if (event.getEntityLiving() instanceof EntityEyeOfCthulhu) {
			event.setCanceled(true);
			event.setStrength(0);
			event.setRatioX(0);
			event.setRatioZ(0);
			return;
		}
		
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
							inventory = WorldEvents.getOrLoadInventory(player, player.world);
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
				inventory = WorldEvents.getOrLoadInventory(player, player.world);
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
				inventory = WorldEvents.getOrLoadInventory(player, player.world);
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
	
	//public ItemUseContext(PlayerEntity player, Hand handIn, BlockRayTraceResult rayTraceResultIn) {
	@SubscribeEvent
	public static void handleInteract(PlayerInteractEvent.RightClickBlock event) {		
		
		
		if (event.getItemStack() != null) {
			
			if (event.getItemStack().getItem() == Items.BUCKET ||
					event.getItemStack().getItem() == Items.WATER_BUCKET ||
					event.getItemStack().getItem() == Items.LAVA_BUCKET) {
				ActionResult<ItemStack> result = event.getItemStack().getItem().onItemRightClick(event.getWorld(), event.getEntityPlayer(), event.getHand());
				if (result.getType() == ActionResultType.SUCCESS) {
					PlayerEntity player = event.getEntityPlayer();
					
					
					if (player != null) {
						if (event.getWorld().isRemote) {
							InventoryTerraria inventory = ContainerTerrariaInventory.inventory;
							if (inventory != null) {
								
								if (inventory.hotbar[inventory.hotbarSelected].stack != null) {
									if (result.getResult().getItem() != Items.BUCKET) {
										
										player.setHeldItem(Hand.MAIN_HAND, new ItemStack(Items.BUCKET));
						    			
						    			
									} else {
										inventory.hotbar[inventory.hotbarSelected].stack = new ItemStackT(result.getResult().getItem(), 1);
									}
								}
							

								return;
							}
						} 
						else {
							InventoryTerraria inventory = WorldEvents.getOrLoadInventory(player, player.world);
							
							
							
								if (inventory.hotbar[inventory.hotbarSelected].stack != null) {
									if (result.getResult().getItem() != Items.BUCKET) {
										
										if (inventory.hotbar[inventory.hotbarSelected].stack.size > 0) {
											inventory.hotbar[inventory.hotbarSelected].decrementStack(1);
								 			NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity)player), new SPacketSyncInventoryTerraria(0, inventory.hotbar[inventory.hotbarSelected].area, inventory.hotbar[inventory.hotbarSelected].id, inventory.hotbar[inventory.hotbarSelected].stack));

											
//											EntityItemT.spawnItem(player.world, player.getPosition(), new ItemStackT(inventory.hotbar[inventory.hotbarSelected].stack.item, inventory.hotbar[inventory.hotbarSelected].stack.size-1), 0);
							    			EntityItemT.spawnItem(player.world, player.getPosition(), new ItemStackT(result.getResult().getItem()), 0);
										}
						    			
						    			
						    			
									} else {
										inventory.hotbar[inventory.hotbarSelected].stack = new ItemStackT(result.getResult().getItem(), 1);
									}
								}
							

							return;
						}
						
					}
					
					
					
				} else {
					event.setCanceled(true);
				}
			}
			Item item = event.getItemStack().getItem();
			
			if (item instanceof BlockItem) {
				BlockItem block = (BlockItem)item.getItem();
				if (block.onItemRightClick(event.getWorld(), event.getEntityPlayer(), event.getHand()).getType() == ActionResultType.PASS) {
					PlayerEntity player = event.getEntityPlayer();
					

					
					if (player != null) {
						if (event.getWorld().isRemote) {
							if (Util.terrariaInventory) {
								InventoryTerraria inventory = ContainerTerrariaInventory.inventory;
								if (inventory.hotbar[inventory.hotbarSelected].stack != null) {
									inventory.hotbar[inventory.hotbarSelected].stack.size --;
									event.getItemStack().grow(1);
									if (inventory.hotbar[inventory.hotbarSelected].stack.size <= 0) {
										inventory.hotbar[inventory.hotbarSelected].stack = null;
										event.getItemStack().shrink(1);
									}
								}
							} else {
								event.getItemStack().shrink(1);
							}
						} else {
							InventoryTerraria inventory = WorldEvents.getOrLoadInventory(player, player.world);
							if (inventory != null && inventory.open == true) {
								if (inventory.hotbar[inventory.hotbarSelected].stack != null) {
									inventory.hotbar[inventory.hotbarSelected].stack.size --;
									event.getItemStack().grow(1);
									if (inventory.hotbar[inventory.hotbarSelected].stack.size <= 0) {
										inventory.hotbar[inventory.hotbarSelected].stack = null;
										event.getItemStack().shrink(1);
									}
								}
								
							} else {
								event.getItemStack().shrink(1);
							}
							
							NetworkHandler.INSTANCE.sendToServer(new CPacketSyncInventoryTerraria(0, inventory.hotbar[inventory.hotbarSelected].area, inventory.hotbar[inventory.hotbarSelected].id, inventory.hotbar[inventory.hotbarSelected].stack));
						}
						
					}
					
					
					
				} else {
					event.setCanceled(true);
				}
				return;
			}
			
			if (event.getItemStack().getItem() instanceof ItemT) {
				if (event.getHand() == Hand.OFF_HAND) {
					event.setCanceled(true);
					return;
				}
				((ItemT)event.getItemStack().getItem()).onUse(null, event.getPos(), event.getEntityPlayer(), event.getWorld(), event.getHand());
				
				if (Util.blockHit != null)
					if (Util.blockHit.getType() == RayTraceResult.Type.BLOCK)
				if (event.getItemStack().getItem() instanceof ItemBlockT) {
					if (event.getPos().getY() > 254 || event.getPos().getY() < 1) {
						
						ItemBlockT block = (ItemBlockT)event.getItemStack().getItem();
						
						ItemUseContext context = new ItemUseContext(event.getPlayer(), event.getHand(), (BlockRayTraceResult) OverlayEvents.blockHit);
						
						block.tryPlace(new BlockItemUseContext(context));
					}
				}
				if (event.getItemStack().getItem() != Items.AIR)
				if (((ItemT)event.getItemStack().getItem()).consumable) {
					boolean shrink = false;
					
					if (event.getEntityPlayer().getHealth() < event.getEntityPlayer().getMaxHealth()) {
						boolean heal = false;
						if (((ItemT)event.getItemStack().getItem()).potionSickness > 0) {
							Score potionSickness = ScoreboardEvents.getScore(event.getEntityPlayer().getWorldScoreboard(), event.getEntityPlayer(), ScoreboardEvents.POTION_SICKNESS);
							if (potionSickness.getScorePoints() > 0) heal = false;
							else potionSickness.setScorePoints(((ItemT)event.getItemStack().getItem()).potionSickness * 20);
						} else {
							heal = true;
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
						mana.setScorePoints(mana.getScorePoints() + ((ItemT)event.getItemStack().getItem()).manaHeal);
						shrink = true;
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
								InventoryTerraria inventory = WorldEvents.getOrLoadInventory(player, player.world);
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
								InventoryTerraria inventory = WorldEvents.getOrLoadInventory(player, player.world);
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
									InventoryTerraria inventory = WorldEvents.getOrLoadInventory(player, player.world);
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
		
		if (Util.blockHit != null) {
			if (Util.blockHit.getType() == RayTraceResult.Type.BLOCK) {
				event.setNewSpeed(-1);
				return;
			}
		}
		
		getMiningSpeed(event);
		
		
	}
	
	public static void getMiningSpeed(BreakSpeed event) {
		if (event.getEntityPlayer() != null) {
			PlayerEntity player = event.getEntityPlayer();
			
			if (player.getHeldItemMainhand() != null)
			{
				
				double speedMul = 1.0;
				InventoryTerraria inventory = null;
				if (!player.world.isRemote) {
					inventory = WorldEvents.getOrLoadInventory(player, player.world);
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
				} else {
					if (_item instanceof ItemT) {
						ItemT item = (ItemT)_item;
						
						float miningSpeed = BlocksT.DIRT_BLOCK.getMiningSpeed(item);
						if (miningSpeed > 0) miningSpeed *= speedMul;
						event.setNewSpeed(miningSpeed * 0.35f);
						
						if (blockstate.getMaterial() == Material.ROCK || blockstate.getMaterial() == Material.IRON) {
							miningSpeed = BlocksT.STONE_BLOCK.getMiningSpeed(item);
							if (miningSpeed > 0) miningSpeed *= speedMul;
							event.setNewSpeed(miningSpeed * 3.0f);
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
		if (event.getPlayer() != null) {
			PlayerEntity player = event.getPlayer();
			
			player.setNoGravity(true);
			
			
			
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
		if (event.getEntity() == null) return;
		if (event.getEntity().world == null) return;
		if (event.getEntity().world.isRemote == false) return;
		if (event.getEntity() != null) {
			event.getEntity().setNoGravity(true);
			if (event.getEntityLiving() instanceof PlayerEntity) {
				PlayerEntity player = (PlayerEntity)event.getEntityLiving();
				player.getAttribute(PlayerEntity.ENTITY_GRAVITY).setBaseValue(0.0f);
				player.setNoGravity(true);
				InventoryTerraria inventory = ContainerTerrariaInventory.inventory;
				
				boolean hasBoots = false;
				for (int i = 0; i < inventory.accessory.length; i++) {
					if (inventory.accessory[i].stack != null) {
						if (inventory.accessory[i].stack.item instanceof HermesBoots) {
							((Accessory)inventory.accessory[i].stack.item).accessoryTick(player);
							hasBoots = true;
							break;
						}
					}
				}
				if (hasBoots == false) {
					HermesBoots HERMES_BOOTS = (HermesBoots)ItemsT.HERMES_BOOTS;
					if (HERMES_BOOTS.baseSpeed != 0.0f) {
						player.setAIMoveSpeed((float) HERMES_BOOTS.baseSpeed);
					}
				}
				
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
	public static void handleEntityEvent(EntityEvent event) {
//		if (event.getEntity() != null && !(event.getEntity() instanceof PlayerEntity)) {
//			DimensionType sky = DimensionManager.registerOrGetDimension(Dimensions.skyLocation, DimensionRegistry.skyDimension, null, true);
//			Entity entity = event.getEntity();
//			if (entity.dimension.getId() == sky.getId()) {
//				if (entity.posY < 0) {
//					entity.changeDimension(DimensionType.OVERWORLD);
//				}
//			}
//			if (entity.dimension.getId() == DimensionType.OVERWORLD.getId()) {
//				if (entity.posY > 255) {
//					entity.changeDimension(sky);
//				}
//			}
//		}
	}
	@SubscribeEvent
	public static void handleLivingEvent(LivingEvent event) {
		
		
		if (event.getEntity() != null) {
			if (event.getEntityLiving() instanceof PlayerEntity) {
				PlayerEntity player = (PlayerEntity)event.getEntityLiving();
				player.getAttribute(PlayerEntity.ENTITY_GRAVITY).setBaseValue(0.0f);
				
				
				
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
					inventory = WorldEvents.getOrLoadInventory(player, player.world);
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
			Entity entity = event.getEntity();
			boolean falling = true;

			
			if (falling)
			if (!event.getEntityLiving().isInWater()) {
//				event.getEntity().getMotion().add(0, -Conversions.convertToIngame(9.82f / 20.0f), 0);
				if (event.getEntity() instanceof PlayerEntity) {
					boolean nope = false;
					if (event.getEntity().world.isRemote)
					if (event.getEntity() instanceof ClientPlayerEntity) {
						ClientPlayerEntity player = (ClientPlayerEntity)event.getEntity();
						
						if (Minecraft.getInstance().gameRenderer.getActiveRenderInfo().isThirdPerson()) {
							event.getEntity().setMotion(new Vec3d(motion.getX(), motion.getY() - Conversions.convertToIngame(9.82f / 50.0f), motion.getZ()));
							nope = true;
						}
						
					}
					if (!nope) {
						event.getEntity().setMotion(new Vec3d(motion.getX(), motion.getY() - Conversions.convertToIngame(9.82f / 20.0f), motion.getZ()));
					}
				} else {
					event.getEntity().setMotion(new Vec3d(motion.getX(), motion.getY() - Conversions.convertToIngame(9.82f / 20.0f), motion.getZ()));
				}
			}
			event.getEntity().stepHeight = 1.0f;

			if (event.getEntityLiving() != null) {
				if (event.getEntityLiving().hurtTime > 0) {
					event.getEntityLiving().setMotion(event.getEntityLiving().getMotion().x, 0, event.getEntityLiving().getMotion().z);
				}
			}
			
			
			
			if (!entity.getEntityWorld().isRemote)
			{
				DimensionType sky = DimensionManager.registerOrGetDimension(Dimensions.skyLocation, DimensionRegistry.skyDimension, null, true);
				ServerWorld world = null;
				int height = 0;
				if (entity.dimension.getId() == DimensionType.OVERWORLD.getId()) {
					if (entity.posY > 150) {
						if (sky != null) {
							DimensionManager.keepLoaded(sky, true);
							world = DimensionManager.getWorld(entity.getServer(), sky, true, true);
							DimensionManager.keepLoaded(sky, true);
							height = 256;
						}
					}
				}
				if (entity.dimension.getId() == sky.getId()) {
					if (entity.posY < 50) {
						if (DimensionType.OVERWORLD != null) {
							DimensionManager.keepLoaded(DimensionType.OVERWORLD, true);
							world = DimensionManager.getWorld(entity.getServer(), DimensionType.OVERWORLD, true, true);
							DimensionManager.keepLoaded(DimensionType.OVERWORLD, true);
							height = -256;
						}
					}
				}
				
				if (world != null) {
					
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
			    	    						
			    	    						moveEntity(entity, (pos.getX() + 1) - entity.posX, 0, 0, false);
			    		    	    			if (world.isAreaLoaded(pos.up(), pos.up())) {
			    		    	    				if (world.getBlockState(pos.up()).getMaterial().blocksMovement() == false) {
			    		    	    					moveEntity(entity, 0, pos.getY() + height + 1 - entity.posY, 0, false);
			    		    	    				}
			    		    	    			}
			    	    					}
			    	    					if (entity.posY + 1 >= pos.getY() + height && entity.posY + 1 < pos.getY() + height + 0.5) {
			    		    	    			moveEntity(entity, (pos.getX() + 1) - entity.posX, 0, 0, false);
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

			    	    						moveEntity(entity, pos.getX() - entity.posX, 0, 0, false);
			    		    	    			if (world.isAreaLoaded(pos.up(), pos.up())) {
			    		    	    				if (world.getBlockState(pos.up()).getMaterial().blocksMovement() == false) {
			    		    	    					moveEntity(entity, 0, pos.getY() + height + 1 - entity.posY, 0, false);
			    		    	    				}
			    		    	    			}
			    	    					}
			    	    					if (entity.posY + 1 >= pos.getY() + height && entity.posY + 1 < pos.getY() + height + 0.5) {
			    		    	    			moveEntity(entity, pos.getX() - entity.posX, 0, 0, false);
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

			    	    						moveEntity(entity, 0, 0, (pos.getZ() + 1) - entity.posZ, false);
			    		    	    			if (world.isAreaLoaded(pos.up(), pos.up())) {
			    		    	    				if (world.getBlockState(pos.up()).getMaterial().blocksMovement() == false) {
			    		    	    					moveEntity(entity, 0, pos.getY() + height + 1 - entity.posY, 0, false);
			    		    	    				}
			    		    	    			}
			    	    					}
			    	    					if (entity.posY + 1 >= pos.getY() + height && entity.posY + 1 < pos.getY() + height + 0.5) {
			    		    	    			moveEntity(entity, 0, 0, (pos.getZ() + 1) - entity.posZ, false);
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

			    	    						moveEntity(entity, 0, 0, pos.getZ() - entity.posZ, false);
			    		    	    			
			    		    	    			if (world.isAreaLoaded(pos.up(), pos.up())) {
			    		    	    				if (world.getBlockState(pos.up()).getMaterial().blocksMovement() == false) {
			    		    	    					moveEntity(entity, 0, pos.getY() + height + 1 - entity.posY, 0, false);
			    		    	    				}
			    		    	    			}
			    		    	    			
			    	    					}
			    	    					if (entity.posY + 1 >= pos.getY() + height && entity.posY + 1 < pos.getY() + height + 0.5) {
			    		    	    			moveEntity(entity, 0, 0, pos.getZ() - entity.posZ, false);
			    	    					}
			    	    				}
					    				
			    	    			}
			    	    		}
			    	    		
			    	    	}
			    		}
			    		if (entity.posY <= 0) {
			    			if (entity.getMotion().y < 0) {
				    			
				    		    BlockPos pos = entity.getPosition().up(i).add(0, entity.getMotion().y + 0.5f, 0).subtract(new Vec3i(0, height, 0));
			    	    		BlockState state = world.getBlockState(pos);
			    	    		
			    	    		if (state.getMaterial().blocksMovement()) {
			    	    			
			    	    			if (entity.posY - 1 < pos.getY() + height + 1) {
			    	    				
			    	    				moveEntity(entity, 0, (pos.getY()) + height + 1 - entity.posY - 1, 0, true);
				    	    			falling = false;

			    	    			}
			    	    			
			    	    		}
				    		}
			    		}
			    		else {
			    			if (entity.getMotion().y < 0) {
				    			
				    		    BlockPos pos = entity.getPosition().up(i).add(0, entity.getMotion().y + 0.5f, 0).subtract(new Vec3i(0, height, 0));
			    	    		BlockState state = world.getBlockState(pos);
			    	    		
			    	    		if (state.getMaterial().blocksMovement()) {
			    	    			
			    	    			if (entity.posY < pos.getY() + height + 1) {
			    	    				
			    	    				moveEntity(entity, 0, pos.getY() + height + 1 - entity.posY, 0, true);
				    	    			falling = false;

			    	    			}
			    	    			
			    	    		}
				    		}
			    		}
			    		
			    		if (entity.getMotion().y > 0) {
			    			
			    		    BlockPos pos = entity.getPosition().up(i).add(0, entity.getMotion().y, 0).subtract(new Vec3i(0, height, 0));
			    	    	if (world.isAreaLoaded(pos, pos)) {
			    	    		BlockState state = world.getBlockState(pos);
			    	    		if (state.getMaterial().blocksMovement()) {
			    	    			if (entity.posY + 1 > pos.getY() + height - 1) {
			    	    				if (entity.posX > pos.getX() && entity.posX < pos.getX() + 1) {
			    	    					if (entity.posZ > pos.getZ() && entity.posZ < pos.getZ() + 1) {
			    	    	    				moveEntity(entity, 0, (pos.getY() + height - 1) - (entity.posY + 1), 0, false);
			    	    					}
			    	    				}
			    	    			}
			    	    		}
			    	    	}
			    		}
			    		
			    	}
				}
			}
		}
		
		
	}
	
	
	public static void moveEntity(Entity entity, double x, double y, double z, boolean onGround) {
		if (entity == null) return;
		if (entity instanceof PlayerEntity) {
			if (entity.world.isRemote) {
				entity.move(MoverType.PISTON, new Vec3d(x, y, z));
				entity.setMotion(new Vec3d(x != 0 ? 0 : entity.getMotion().x, y != 0 ? 0 : entity.getMotion().y, z != 0 ? 0 : entity.getMotion().z));
			} else {
				entity.move(MoverType.PISTON, new Vec3d(x*0.75, y*0.75, z*0.75));
				entity.setMotion(new Vec3d(x != 0 ? 0 : entity.getMotion().x, y != 0 ? 0 : entity.getMotion().y, z != 0 ? 0 : entity.getMotion().z));
				if (entity == null) return;
				try {
					NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity)entity), new SPacketForceMovement(x, y, z, onGround));
				}catch (Exception e) {
					
				}
			}
		} else {
			entity.move(MoverType.PISTON, new Vec3d(x, y, z));
			entity.setMotion(new Vec3d(x != 0 ? 0 : entity.getMotion().x, y != 0 ? 0 : entity.getMotion().y, z != 0 ? 0 : entity.getMotion().z));
		}
		if (onGround) {
			entity.onGround = true;
			entity.isAirBorne = false;
			entity.fallDistance = 0;
 		}
	}
	//Minecraft Falling Damage =(number of blocks fallen x ½) - 1½
	//Terraria falling damage = 10(h - 25)
	
	@SubscribeEvent
	public static void handleLivingJumpEvent(LivingEvent.LivingJumpEvent event) {
		A:
		if (event.getEntity() instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity) event.getEntity();
			InventoryTerraria inventory = null;
			if (!player.world.isRemote) {
				inventory = WorldEvents.getOrLoadInventory(player, player.world);
				for (int i = 0; i < 3; i++) {
					BlockPos pos = new BlockPos(player.getPosition().down(i));
					if (player.world.getBlockState(pos).getBlock() == BlocksT.DIMENSION_BLOCK) {
						player.world.setBlockState(pos, Blocks.AIR.getDefaultState());
						break;
					}
				}
			}
			else {
				break A;
			}
			
			for (int i = 0; i < inventory.accessory.length; i++) {
				InventorySlot slot = inventory.accessory[i];
				if (slot.stack != null) {
					if (slot.stack.item == ItemsT.CLOUD_IN_A_BOTTLE) {
						player.onGround = true;
						player.fallDistance = 0;
					}
				}
			}
		}
		
//		if (event.getEntity() instanceof EntitySlime) System.out.println("true");
		if (event.getEntityLiving() != null) {
			if (event.getEntityLiving().isInWater()) {
				System.out.println("water");
			}
			Vec3d motion = event.getEntityLiving().getMotion();
			
			if (!(event.getEntityLiving() instanceof RabbitEntity))
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
