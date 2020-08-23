package kmerrill285.trewrite.entities;

import java.util.HashMap;

import kmerrill285.featurescript.FeatureScript;
import kmerrill285.trewrite.blocks.BlocksT;
import kmerrill285.trewrite.entities.monsters.EntityBlueSlime;
import kmerrill285.trewrite.entities.monsters.EntityDemonEye;
import kmerrill285.trewrite.entities.monsters.EntityDrownedT;
import kmerrill285.trewrite.entities.monsters.EntityMeteorHead;
import kmerrill285.trewrite.entities.monsters.EntityUndeadMiner;
import kmerrill285.trewrite.entities.monsters.EntityZombieT;
import kmerrill285.trewrite.entities.monsters.bosses.EntityEowBody;
import kmerrill285.trewrite.entities.monsters.bosses.EntityEowHead;
import kmerrill285.trewrite.entities.monsters.bosses.EntityEowTail;
import kmerrill285.trewrite.entities.monsters.bosses.EntityEyeOfCthulhu;
import kmerrill285.trewrite.entities.monsters.bosses.wof.EntityLeechBody;
import kmerrill285.trewrite.entities.monsters.bosses.wof.EntityLeechHead;
import kmerrill285.trewrite.entities.monsters.bosses.wof.EntityLeechTail;
import kmerrill285.trewrite.entities.monsters.bosses.wof.EntityWallOfFlesh;
import kmerrill285.trewrite.entities.monsters.bosses.wof.EntityWallOfFleshEye;
import kmerrill285.trewrite.entities.monsters.bosses.wof.EntityWallOfFleshMouth;
import kmerrill285.trewrite.entities.monsters.bosses.wof.TheHungryEntity;
import kmerrill285.trewrite.entities.monsters.worms.EntityWormBody;
import kmerrill285.trewrite.entities.monsters.worms.EntityWormHead;
import kmerrill285.trewrite.entities.monsters.worms.EntityWormTail;
import kmerrill285.trewrite.entities.npc.EntityGuide;
import kmerrill285.trewrite.entities.passive.EntityBunnyT;
import kmerrill285.trewrite.entities.projectiles.EntityArrowT;
import kmerrill285.trewrite.entities.projectiles.EntityBullet;
import kmerrill285.trewrite.entities.projectiles.EntityMagicProjectile;
import kmerrill285.trewrite.entities.projectiles.EntitySummoningImpFireball;
import kmerrill285.trewrite.entities.projectiles.EntityTekhairaProjectile;
import kmerrill285.trewrite.entities.projectiles.EntityThrowingT;
import kmerrill285.trewrite.entities.projectiles.EntityVileSpit;
import kmerrill285.trewrite.entities.projectiles.ScriptedProjectile;
import kmerrill285.trewrite.entities.projectiles.boomerangs.EntityEnchantedBoomerang;
import kmerrill285.trewrite.entities.projectiles.flails.EntityBallOHurt;
import kmerrill285.trewrite.entities.projectiles.hostile.EntityEyeLaser;
import kmerrill285.trewrite.entities.projectiles.magic_projectiles.SpaceGunProjectile;
import kmerrill285.trewrite.entities.projectiles.magic_projectiles.VilethornProjectile;
import kmerrill285.trewrite.entities.summoning.EntitySummoningImp;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
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
	   public static EntityType<EntityHeart> HEART;
	   public static EntityType<EntityCoin> COIN;
	   public static EntityType<EntityStar> STAR;
	   public static EntityType<EntityCoinPortal> COIN_PORTAL;
	   public static EntityType<EntityRope> ROPE;
	   public static EntityType<EntityWormHead> WORM_HEAD;
	   public static EntityType<EntityWormBody> WORM_BODY;
	   public static EntityType<EntityWormTail> WORM_TAIL;

	   public static EntityType<EntityEowHead> EOW_HEAD;
	   public static EntityType<EntityEowBody> EOW_BODY;
	   public static EntityType<EntityEowTail> EOW_TAIL;
	   
	   public static EntityType<EntityBullet> BULLET;
	   public static EntityType<EntityShadowOrb> SHADOW_ORB;
	   
	   public static EntityType<EntityGuide> GUIDE;
	   public static EntityType<EntityVileSpit> VILE_SPIT;
	   public static EntityType<EntityMagicProjectile> MAGIC_PROJECTILE;
	   public static EntityType<VilethornProjectile> VILETHORN;

	   public static EntityType<EntityFlail> FLAIL;
	   public static EntityType<EntityBallOHurt> BALL_O_HURT;
	   public static EntityType<EntityTekhairaProjectile> TEKHAIRA_PROJECTILE;
	   public static EntityType<EntityEnchantedBoomerang> ENCHANTED_BOOMERANG;
	   public static EntityType<EntitySummoningImp> SUMMONING_IMP;
	   public static EntityType<EntitySummoningImpFireball> SUMMONING_IMP_FIREBALL;
	   public static EntityType<EntityWallOfFlesh> WALL_OF_FLESH;
	   public static EntityType<EntityWallOfFleshEye> WALL_OF_FLESH_EYE;
	   public static EntityType<EntityWallOfFleshMouth> WALL_OF_FLESH_MOUTH;
	   
	   public static EntityType<EntityLeechHead> LEECH_HEAD;
	   public static EntityType<EntityLeechBody> LEECH_BODY;
	   public static EntityType<EntityLeechTail> LEECH_TAIL;

	   public static EntityType<EntityEyeLaser> EYE_LASER;
	   public static EntityType<TheHungryEntity> THE_HUNGRY;
	   
	   public static EntityType<SpaceGunProjectile> SPACE_GUN;
	   
	   public static EntityType<EntityUndeadMiner> UNDEAD_MINER;
	   public static EntityType<EntityMeteorHead> METEOR_HEAD;
	   
	   public static HashMap<String, EntityType<? extends MobEntity>> SCRIPTED_PROJECTILES = new HashMap<String, EntityType<? extends MobEntity>>();

	   
	   @SubscribeEvent
		public static void registerEntities(final RegistryEvent.Register<EntityType<?>> event) {
		   
		 EntitiesT.ITEM = register("trewrite" + ":entityitemt", EntityType.Builder.<EntityItemT>create(EntityItemT::new, EntityClassification.MISC).immuneToFire().setCustomClientFactory((spawnEntity, world) -> new EntityItemT(world)));
		 EntitiesT.BLUE_SLIME = register("trewrite" + ":entityblueslime", EntityType.Builder.<EntityBlueSlime>create(EntityBlueSlime::new, EntityClassification.MISC).size(1.5f, 1.5f).setCustomClientFactory((spawnEntity, world) -> new EntityBlueSlime(world)));
		 
		 EntitiesT.ARROW = register("trewrite" + ":entityarrow", EntityType.Builder.<EntityArrowT>create(EntityArrowT::new, EntityClassification.MISC).immuneToFire().setCustomClientFactory((spawnEntity, world) -> new EntityArrowT(world)));
		 EntitiesT.THROWING = register("trewrite" + ":entitythrowing", EntityType.Builder.<EntityThrowingT>create(EntityThrowingT::new, EntityClassification.MISC).immuneToFire().setCustomClientFactory((spawnEntity, world) -> new EntityThrowingT(world)));
		 EntitiesT.DEMON_EYE = register("trewrite" + ":entitydemoneye", EntityType.Builder.<EntityDemonEye>create(EntityDemonEye::new, EntityClassification.MISC).size(0.75f, 0.75f).setCustomClientFactory((spawnEntity, world) -> new EntityDemonEye(world)));

		 EntitiesT.EYE_OF_CTHULHU = register("trewrite" + ":entityeyeofcthulhu", EntityType.Builder.<EntityEyeOfCthulhu>create(EntityEyeOfCthulhu::new, EntityClassification.MISC).size(4, 4).setCustomClientFactory((spawnEntity, world) -> new EntityEyeOfCthulhu(world)));
		 EntitiesT.ZOMBIE = register("trewrite" + ":zombie", EntityType.Builder.<EntityZombieT>create(EntityZombieT::new, EntityClassification.MISC).setCustomClientFactory((spawnEntity, world) -> new EntityZombieT(world)));
		 EntitiesT.DROWNED = register("trewrite" + ":drowned", EntityType.Builder.<EntityDrownedT>create(EntityDrownedT::new, EntityClassification.MISC).setCustomClientFactory((spawnEntity, world) -> new EntityDrownedT(world)));
		 EntitiesT.BUNNY = register("trewrite" + ":bunny", EntityType.Builder.<EntityBunnyT>create(EntityBunnyT::new, EntityClassification.MISC).setCustomClientFactory((spawnEntity, world) -> new EntityBunnyT(world)));
		 
		 EntitiesT.HEART = register("trewrite" + ":entityheart", EntityType.Builder.<EntityHeart>create(EntityHeart::new, EntityClassification.MISC).immuneToFire().setCustomClientFactory((spawnEntity, world) -> new EntityHeart(world)));
		 EntitiesT.COIN = register("trewrite" + ":entitycoin", EntityType.Builder.<EntityCoin>create(EntityCoin::new, EntityClassification.MISC).immuneToFire().setCustomClientFactory((spawnEntity, world) -> new EntityCoin(world)));
		 EntitiesT.STAR = register("trewrite" + ":entitystar", EntityType.Builder.<EntityStar>create(EntityStar::new, EntityClassification.MISC).immuneToFire().setCustomClientFactory((spawnEntity, world) -> new EntityStar(world)));
		 EntitiesT.COIN_PORTAL = register("trewrite" + ":entitycoinportal", EntityType.Builder.<EntityCoinPortal>create(EntityCoinPortal::new, EntityClassification.MISC).immuneToFire().setCustomClientFactory((spawnEntity, world) -> new EntityCoinPortal(world)));

		 EntitiesT.ROPE = register("trewrite" + ":entityrope", EntityType.Builder.<EntityRope>create(EntityRope::new, EntityClassification.MISC).immuneToFire().setCustomClientFactory((spawnEntity, world) -> new EntityRope(world)));
		 EntitiesT.WORM_HEAD = register("trewrite" + ":entitywormhead", EntityType.Builder.<EntityWormHead>create(EntityWormHead::new, EntityClassification.MISC).size(0.5f, 0.5f).setCustomClientFactory((spawnEntity, world) -> new EntityWormHead(world)));
		 EntitiesT.WORM_BODY = register("trewrite" + ":entitywormbody", EntityType.Builder.<EntityWormBody>create(EntityWormBody::new, EntityClassification.MISC).size(0.5f, 0.5f).setCustomClientFactory((spawnEntity, world) -> new EntityWormBody(world)));
		 EntitiesT.WORM_TAIL = register("trewrite" + ":entitywormtail", EntityType.Builder.<EntityWormTail>create(EntityWormTail::new, EntityClassification.MISC).size(0.5f, 0.5f).setCustomClientFactory((spawnEntity, world) -> new EntityWormTail(world)));

		 EntitiesT.EOW_HEAD = register("trewrite" + ":entityeowhead", EntityType.Builder.<EntityEowHead>create(EntityEowHead::new, EntityClassification.MISC).size(4.0f, 4.0f).immuneToFire().setCustomClientFactory((spawnEntity, world) -> new EntityEowHead(world)));
		 EntitiesT.EOW_BODY = register("trewrite" + ":entityeowbody", EntityType.Builder.<EntityEowBody>create(EntityEowBody::new, EntityClassification.MISC).size(4.0f, 4.0f).immuneToFire().setCustomClientFactory((spawnEntity, world) -> new EntityEowBody(world)));
		 EntitiesT.EOW_TAIL = register("trewrite" + ":entityeowtail", EntityType.Builder.<EntityEowTail>create(EntityEowTail::new, EntityClassification.MISC).size(4.0f, 4.0f).immuneToFire().setCustomClientFactory((spawnEntity, world) -> new EntityEowTail(world)));

		 EntitiesT.BULLET = register("trewrite" + ":entitybullet", EntityType.Builder.<EntityBullet>create(EntityBullet::new, EntityClassification.MISC).immuneToFire().setCustomClientFactory((spawnEntity, world) -> new EntityBullet(world)));
		 EntitiesT.SHADOW_ORB = register("trewrite" + ":entityshadoworb", EntityType.Builder.<EntityShadowOrb>create(EntityShadowOrb::new, EntityClassification.MISC).immuneToFire().setCustomClientFactory((spawnEntity, world) -> new EntityShadowOrb(world)));

		 EntitiesT.GUIDE = register("trewrite" + ":guide", EntityType.Builder.<EntityGuide>create(EntityGuide::new, EntityClassification.MISC).size(0.5f,2.0f).immuneToFire().setCustomClientFactory((spawnEntity, world) -> new EntityGuide(world)));
		 EntitiesT.VILE_SPIT = register("trewrite" + ":vile_spit", EntityType.Builder.<EntityVileSpit>create(EntityVileSpit::new, EntityClassification.MISC).size(0.5f,0.5f).immuneToFire().setCustomClientFactory((spawnEntity, world) -> new EntityVileSpit(world)));
		 EntitiesT.MAGIC_PROJECTILE = register("trewrite" + ":magic_projectile", EntityType.Builder.<EntityMagicProjectile>create(EntityMagicProjectile::new, EntityClassification.MISC).size(0.5f,0.5f).immuneToFire().setCustomClientFactory((spawnEntity, world) -> new EntityMagicProjectile(world)));
		 EntitiesT.VILETHORN = register("trewrite" + ":vilethorn_projectile", EntityType.Builder.<VilethornProjectile>create(VilethornProjectile::new, EntityClassification.MISC).size(1.0f, 1.0f).immuneToFire().setCustomClientFactory((spawnEntity, world) -> new VilethornProjectile(world)));
		 EntitiesT.FLAIL = register("trewrite" + ":flail", EntityType.Builder.<EntityFlail>create(EntityFlail::new, EntityClassification.MISC).size(1.0f, 1.0f).immuneToFire().setCustomClientFactory((spawnEntity, world) -> new EntityFlail(world)));
		 EntitiesT.BALL_O_HURT = register("trewrite" + ":ball_o_hurt", EntityType.Builder.<EntityBallOHurt>create(EntityBallOHurt::new, EntityClassification.MISC).size(1.0f, 1.0f).immuneToFire().setCustomClientFactory((spawnEntity, world) -> new EntityBallOHurt(world)));
		 EntitiesT.TEKHAIRA_PROJECTILE = register("trewrite" + ":tekhaira_projectile", EntityType.Builder.<EntityTekhairaProjectile>create(EntityTekhairaProjectile::new, EntityClassification.MISC).size(1.0f, 1.0f).immuneToFire().setCustomClientFactory((spawnEntity, world) -> new EntityTekhairaProjectile(world)));
		 EntitiesT.ENCHANTED_BOOMERANG = register("trewrite" + ":enchanted_boomerang", EntityType.Builder.<EntityEnchantedBoomerang>create(EntityEnchantedBoomerang::new, EntityClassification.MISC).size(1.0f, 1.0f).immuneToFire().setCustomClientFactory((spawnEntity, world) -> new EntityEnchantedBoomerang(world)));

		 EntitiesT.SUMMONING_IMP = register("trewrite" + ":summoning_imp", EntityType.Builder.<EntitySummoningImp>create(EntitySummoningImp::new, EntityClassification.MISC).size(1.0f, 1.0f).immuneToFire().setCustomClientFactory((spawnEntity, world) -> new EntitySummoningImp(world)));
		 EntitiesT.SUMMONING_IMP_FIREBALL = register("trewrite" + ":summoning_imp_fireball", EntityType.Builder.<EntitySummoningImpFireball>create(EntitySummoningImpFireball::new, EntityClassification.MISC).size(100.0f, 100.0f).immuneToFire().setCustomClientFactory((spawnEntity, world) -> new EntitySummoningImpFireball(world)));
		 EntitiesT.WALL_OF_FLESH = register("trewrite" + ":wall_of_flesh", EntityType.Builder.<EntityWallOfFlesh>create(EntityWallOfFlesh::new, EntityClassification.MONSTER).size(1.0f, 1.0f).immuneToFire().setTrackingRange(50000).setCustomClientFactory((spawnEntity, world) -> new EntityWallOfFlesh(world)));
		 EntitiesT.WALL_OF_FLESH_EYE = register("trewrite" + ":wall_of_flesh_eye", EntityType.Builder.<EntityWallOfFleshEye>create(EntityWallOfFleshEye::new, EntityClassification.MONSTER).size(1.0f, 1.0f).immuneToFire().setTrackingRange(50000).setCustomClientFactory((spawnEntity, world) -> new EntityWallOfFleshEye(world)));
		 EntitiesT.WALL_OF_FLESH_MOUTH = register("trewrite" + ":wall_of_flesh_mouth", EntityType.Builder.<EntityWallOfFleshMouth>create(EntityWallOfFleshMouth::new, EntityClassification.MONSTER).size(1.0f, 1.0f).immuneToFire().setTrackingRange(50000).setCustomClientFactory((spawnEntity, world) -> new EntityWallOfFleshMouth(world)));

		 EntitiesT.LEECH_HEAD = register("trewrite" + ":leech_head", EntityType.Builder.<EntityLeechHead>create(EntityLeechHead::new, EntityClassification.MONSTER).size(1.0f, 1.0f).immuneToFire().setCustomClientFactory((spawnEntity, world) -> new EntityLeechHead(world)));
		 EntitiesT.LEECH_BODY = register("trewrite" + ":leech_body", EntityType.Builder.<EntityLeechBody>create(EntityLeechBody::new, EntityClassification.MONSTER).size(1.0f, 1.0f).immuneToFire().setCustomClientFactory((spawnEntity, world) -> new EntityLeechBody(world)));
		 EntitiesT.LEECH_TAIL = register("trewrite" + ":leech_tail", EntityType.Builder.<EntityLeechTail>create(EntityLeechTail::new, EntityClassification.MONSTER).size(1.0f, 1.0f).immuneToFire().setCustomClientFactory((spawnEntity, world) -> new EntityLeechTail(world)));

		 EntitiesT.EYE_LASER = register("trewrite" + ":eye_laser", EntityType.Builder.<EntityEyeLaser>create(EntityEyeLaser::new, EntityClassification.MONSTER).size(1.0f, 1.0f).immuneToFire().setCustomClientFactory((spawnEntity, world) -> new EntityEyeLaser(world)));
		 EntitiesT.THE_HUNGRY = register("trewrite" + ":the_hungry", EntityType.Builder.<TheHungryEntity>create(TheHungryEntity::new, EntityClassification.MONSTER).size(2.0f, 2.0f).immuneToFire().setCustomClientFactory((spawnEntity, world) -> new TheHungryEntity(world)));
		 
		 EntitiesT.SPACE_GUN = register("trewrite" + ":space_gun", EntityType.Builder.<SpaceGunProjectile>create(SpaceGunProjectile::new, EntityClassification.MISC).size(0.5f, 0.5f).immuneToFire().setCustomClientFactory((spawnEntity, world) -> new SpaceGunProjectile(world)));

		 EntitiesT.UNDEAD_MINER = register("trewrite" + ":undead_miner", EntityType.Builder.<EntityUndeadMiner>create(EntityUndeadMiner::new, EntityClassification.MONSTER).setCustomClientFactory((spawnEntity, world) -> new EntityUndeadMiner(world)));
		 EntitiesT.METEOR_HEAD = register("trewrite" + ":meteor_head", EntityType.Builder.<EntityMeteorHead>create(EntityMeteorHead::new, EntityClassification.MONSTER).size(0.5f, 0.5f).setCustomClientFactory((spawnEntity, world) -> new EntityMeteorHead(world)));

		 
		 for (String str : FeatureScript.projectiles.keySet()) {
			 SCRIPTED_PROJECTILES.put(str, register("trewrite:"+str, EntityType.Builder.<ScriptedProjectile>create(ScriptedProjectile::new, EntityClassification.MISC).size(1.0f, 1.0f).immuneToFire().setCustomClientFactory((spawnEntity, world) -> new ScriptedProjectile(world))));
		 }
		 
		 
		 SpawnCondition.spawnConditions.put(EntitiesT.BLUE_SLIME, new SpawnCondition(0, 255, SpawnCondition.VERY_COMMON, BlocksT.DIRT_BLOCK, BlocksT.GRASS_BLOCK, BlocksT.HIGHLANDS_GRASS, BlocksT.PODZOL));
		 SpawnCondition.spawnConditions.put(EntitiesT.DEMON_EYE, new SpawnCondition(0, 255, SpawnCondition.COMMON, BlocksT.DIRT_BLOCK, BlocksT.GRASS_BLOCK, BlocksT.HIGHLANDS_GRASS, BlocksT.BOG_GRASS, BlocksT.JUNGLE_GRASS, BlocksT.MUD, BlocksT.SAND, BlocksT.RED_SAND, BlocksT.PODZOL));
		 SpawnCondition.spawnConditions.put(EntitiesT.BUNNY, new SpawnCondition(0, 255, SpawnCondition.VERY_COMMON, BlocksT.DIRT_BLOCK, BlocksT.GRASS_BLOCK, BlocksT.HIGHLANDS_GRASS, BlocksT.PODZOL));
		 SpawnCondition.spawnConditions.put(EntitiesT.DROWNED, new SpawnCondition(0, 255, SpawnCondition.VERY_COMMON, BlocksT.DIRT_BLOCK, BlocksT.GRASS_BLOCK, BlocksT.HIGHLANDS_GRASS, BlocksT.PODZOL));
		 SpawnCondition.spawnConditions.put(EntitiesT.ZOMBIE, new SpawnCondition(0, 255, SpawnCondition.VERY_COMMON, BlocksT.DIRT_BLOCK, BlocksT.GRASS_BLOCK, BlocksT.HIGHLANDS_GRASS, BlocksT.BOG_GRASS, BlocksT.JUNGLE_GRASS, BlocksT.MUD, BlocksT.SAND, BlocksT.RED_SAND, BlocksT.PODZOL));
		 SpawnCondition.spawnConditions.put(EntitiesT.DROWNED, new SpawnCondition(0, 255, SpawnCondition.COMMON, BlocksT.DIRT_BLOCK, BlocksT.GRASS_BLOCK, BlocksT.HIGHLANDS_GRASS, BlocksT.BOG_GRASS, BlocksT.JUNGLE_GRASS, BlocksT.MUD, BlocksT.SAND, BlocksT.RED_SAND, BlocksT.PODZOL, Blocks.WATER));
		 SpawnCondition.spawnConditions.put(EntitiesT.WORM_HEAD, new SpawnCondition(0, 255, SpawnCondition.RARE, BlocksT.STONE_BLOCK));
		 SpawnCondition.spawnConditions.put(EntitiesT.UNDEAD_MINER, new SpawnCondition(0, 255, SpawnCondition.RARE, BlocksT.STONE_BLOCK));
		 SpawnCondition.spawnConditions.put(EntitiesT.METEOR_HEAD, new SpawnCondition(0, 255, SpawnCondition.COMMON, BlocksT.METEORITE));
	   }
	   
	   private static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> builder) {
		      return Registry.register(Registry.ENTITY_TYPE, id, builder.build(id));
		   }
}
