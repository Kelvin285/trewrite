package kmerrill285.trewrite.entities;

import kmerrill285.trewrite.blocks.BlocksT;
import kmerrill285.trewrite.entities.monsters.EntityBlueSlime;
import kmerrill285.trewrite.entities.monsters.EntityDemonEye;
import kmerrill285.trewrite.entities.monsters.EntityDrownedT;
import kmerrill285.trewrite.entities.monsters.EntityEyeOfCthulhu;
import kmerrill285.trewrite.entities.monsters.EntityZombieT;
import kmerrill285.trewrite.entities.passive.EntityBunnyT;
import kmerrill285.trewrite.entities.projectiles.EntityArrowT;
import kmerrill285.trewrite.entities.projectiles.EntityThrowingT;
import kmerrill285.trewrite.util.Util;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class EntitiesT {
	   public static EntityType<EntityItemT> ITEM;
	   public static EntityType<EntityBlueSlime> BLUE_SLIME;
	   public static EntityType<EntityArrowT> ARROW;
	   public static EntityType<EntityThrowingT> THROWING;
	   public static EntityType<EntityDemonEye> DEMON_EYE;
	   public static EntityType<EntityEyeOfCthulhu> EYE_OF_CTHULHU;
	   public static EntityType<EntityZombieT> ZOMBIE;
	   public static EntityType<EntityDrownedT> DROWNED;
	   public static EntityType<EntityBunnyT> BUNNY;
	   
	   
	   @SubscribeEvent
		public static void registerEntities(final RegistryEvent.Register<EntityType<?>> event) {
		   
		 EntitiesT.ITEM = register("trewrite" + ":entityitemt", EntityType.Builder.<EntityItemT>create(EntityItemT::new, EntityClassification.MISC).immuneToFire().setCustomClientFactory((spawnEntity, world) -> new EntityItemT(world)));
		 EntitiesT.BLUE_SLIME = register("trewrite" + ":entityblueslime", EntityType.Builder.<EntityBlueSlime>create(EntityBlueSlime::new, EntityClassification.MISC).setCustomClientFactory((spawnEntity, world) -> new EntityBlueSlime(world)));
		 
		 EntitiesT.ARROW = register("trewrite" + ":entityarrow", EntityType.Builder.<EntityArrowT>create(EntityArrowT::new, EntityClassification.MISC).immuneToFire().setCustomClientFactory((spawnEntity, world) -> new EntityArrowT(world)));
		 EntitiesT.THROWING = register("trewrite" + ":entitythrowing", EntityType.Builder.<EntityThrowingT>create(EntityThrowingT::new, EntityClassification.MISC).immuneToFire().setCustomClientFactory((spawnEntity, world) -> new EntityThrowingT(world)));
		 EntitiesT.DEMON_EYE = register("trewrite" + ":entitydemoneye", EntityType.Builder.<EntityDemonEye>create(EntityDemonEye::new, EntityClassification.MISC).size(1, 1).setCustomClientFactory((spawnEntity, world) -> new EntityDemonEye(world)));

		 EntitiesT.EYE_OF_CTHULHU = register("trewrite" + ":entityeyeofcthulhu", EntityType.Builder.<EntityEyeOfCthulhu>create(EntityEyeOfCthulhu::new, EntityClassification.MISC).size(4, 4).setCustomClientFactory((spawnEntity, world) -> new EntityEyeOfCthulhu(world)));
		 EntitiesT.ZOMBIE = register("trewrite" + ":zombie", EntityType.Builder.<EntityZombieT>create(EntityZombieT::new, EntityClassification.MISC).setCustomClientFactory((spawnEntity, world) -> new EntityZombieT(world)));
		 EntitiesT.DROWNED = register("trewrite" + ":drowned", EntityType.Builder.<EntityDrownedT>create(EntityDrownedT::new, EntityClassification.MISC).setCustomClientFactory((spawnEntity, world) -> new EntityDrownedT(world)));
		 EntitiesT.BUNNY = register("trewrite" + ":bunny", EntityType.Builder.<EntityBunnyT>create(EntityBunnyT::new, EntityClassification.MISC).setCustomClientFactory((spawnEntity, world) -> new EntityBunnyT(world)));
		 
		 
		 SpawnCondition.spawnConditions.put(EntitiesT.BLUE_SLIME, new SpawnCondition(Util.surfaceLevel - 15, 255, SpawnCondition.VERY_COMMON, BlocksT.DIRT_BLOCK, BlocksT.GRASS_BLOCK, BlocksT.HIGHLANDS_GRASS, BlocksT.PODZOL));
		 SpawnCondition.spawnConditions.put(EntitiesT.DEMON_EYE, new SpawnCondition(Util.surfaceLevel - 15, 255, SpawnCondition.COMMON, BlocksT.DIRT_BLOCK, BlocksT.GRASS_BLOCK, BlocksT.HIGHLANDS_GRASS, BlocksT.BOG_GRASS, BlocksT.JUNGLE_GRASS, BlocksT.MUD, BlocksT.SAND, BlocksT.RED_SAND, BlocksT.PODZOL));
		 SpawnCondition.spawnConditions.put(EntitiesT.BUNNY, new SpawnCondition(Util.surfaceLevel - 15, 255, SpawnCondition.VERY_COMMON, BlocksT.DIRT_BLOCK, BlocksT.GRASS_BLOCK, BlocksT.HIGHLANDS_GRASS, BlocksT.PODZOL));
		 SpawnCondition.spawnConditions.put(EntitiesT.DROWNED, new SpawnCondition(Util.surfaceLevel - 15, 255, SpawnCondition.VERY_COMMON, BlocksT.DIRT_BLOCK, BlocksT.GRASS_BLOCK, BlocksT.HIGHLANDS_GRASS, BlocksT.PODZOL));
		 SpawnCondition.spawnConditions.put(EntitiesT.ZOMBIE, new SpawnCondition(Util.surfaceLevel - 15, 255, SpawnCondition.VERY_COMMON, BlocksT.DIRT_BLOCK, BlocksT.GRASS_BLOCK, BlocksT.HIGHLANDS_GRASS, BlocksT.BOG_GRASS, BlocksT.JUNGLE_GRASS, BlocksT.MUD, BlocksT.SAND, BlocksT.RED_SAND, BlocksT.PODZOL));
		 SpawnCondition.spawnConditions.put(EntitiesT.DROWNED, new SpawnCondition(Util.surfaceLevel - 15, 255, SpawnCondition.COMMON, BlocksT.DIRT_BLOCK, BlocksT.GRASS_BLOCK, BlocksT.HIGHLANDS_GRASS, BlocksT.BOG_GRASS, BlocksT.JUNGLE_GRASS, BlocksT.MUD, BlocksT.SAND, BlocksT.RED_SAND, BlocksT.PODZOL));

	   
	   }
	   
	   private static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> builder) {
		      return Registry.register(Registry.ENTITY_TYPE, id, builder.build(id));
		   }
}
