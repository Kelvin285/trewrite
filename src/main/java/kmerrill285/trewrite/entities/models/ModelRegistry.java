package kmerrill285.trewrite.entities.models;

import kmerrill285.trewrite.entities.EntityCoin;
import kmerrill285.trewrite.entities.EntityCoinPortal;
import kmerrill285.trewrite.entities.EntityHeart;
import kmerrill285.trewrite.entities.EntityItemT;
import kmerrill285.trewrite.entities.EntityRope;
import kmerrill285.trewrite.entities.EntityShadowOrb;
import kmerrill285.trewrite.entities.EntityStar;
import kmerrill285.trewrite.entities.models.boomerangs.RenderBoomerang;
import kmerrill285.trewrite.entities.models.flails.RenderBallOHurt;
import kmerrill285.trewrite.entities.models.projectiles.RenderEyeLaser;
import kmerrill285.trewrite.entities.models.summoning.RenderSummoningImp;
import kmerrill285.trewrite.entities.models.wall_of_flesh.RenderTheHungry;
import kmerrill285.trewrite.entities.models.wall_of_flesh.RenderWallOfFlesh;
import kmerrill285.trewrite.entities.models.wall_of_flesh.RenderWallOfFleshEye;
import kmerrill285.trewrite.entities.models.wall_of_flesh.RenderWallOfFleshMouth;
import kmerrill285.trewrite.entities.models.worms.RenderEowBody;
import kmerrill285.trewrite.entities.models.worms.RenderEowHead;
import kmerrill285.trewrite.entities.models.worms.RenderEowTail;
import kmerrill285.trewrite.entities.models.worms.RenderLeechBody;
import kmerrill285.trewrite.entities.models.worms.RenderLeechHead;
import kmerrill285.trewrite.entities.models.worms.RenderLeechTail;
import kmerrill285.trewrite.entities.models.worms.RenderWormBody;
import kmerrill285.trewrite.entities.models.worms.RenderWormHead;
import kmerrill285.trewrite.entities.models.worms.RenderWormTail;
import kmerrill285.trewrite.entities.monsters.EntityBlueSlime;
import kmerrill285.trewrite.entities.monsters.EntityDemonEye;
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
import kmerrill285.trewrite.entities.projectiles.EntityTekhairaProjectile;
import kmerrill285.trewrite.entities.projectiles.boomerangs.EntityEnchantedBoomerang;
import kmerrill285.trewrite.entities.projectiles.flails.EntityBallOHurt;
import kmerrill285.trewrite.entities.projectiles.hostile.EntityEyeLaser;
import kmerrill285.trewrite.entities.projectiles.magic_projectiles.VilethornProjectile;
import kmerrill285.trewrite.entities.summoning.EntitySummoningImp;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModelRegistry

{

	public static RenderPlayer renderPlayer;
	
    @SubscribeEvent
    public static void registerAllModels(ModelRegistryEvent event)
    {
    	RenderingRegistry.registerEntityRenderingHandler(EntityItemT.class, manager -> new RenderEntityItemT(manager, Minecraft.getInstance().getItemRenderer()));
    	RenderingRegistry.registerEntityRenderingHandler(EntityBlueSlime.class, manager -> new RenderEntitySlime(manager, "blueslime"));
    	RenderingRegistry.registerEntityRenderingHandler(EntityDemonEye.class, manager -> new RenderDemonEye(manager));
    	RenderingRegistry.registerEntityRenderingHandler(EntityEyeOfCthulhu.class, manager -> new RenderEOC(manager));
    	RenderingRegistry.registerEntityRenderingHandler(EntityBunnyT.class, manager -> new RabbitRendererT(manager));
    	RenderingRegistry.registerEntityRenderingHandler(EntityCoin.class, manager -> new RenderCoin(manager));
    	RenderingRegistry.registerEntityRenderingHandler(EntityHeart.class, manager -> new RenderHeart(manager));
    	RenderingRegistry.registerEntityRenderingHandler(EntityStar.class, manager -> new RenderStar(manager));
    	RenderingRegistry.registerEntityRenderingHandler(EntityCoinPortal.class, manager -> new RenderCoinPortal(manager));
    	RenderingRegistry.registerEntityRenderingHandler(EntityRope.class, manager -> new RenderRope(manager));
    	RenderingRegistry.registerEntityRenderingHandler(EntityWormHead.class, manager -> new RenderWormHead(manager));
    	RenderingRegistry.registerEntityRenderingHandler(EntityWormBody.class, manager -> new RenderWormBody(manager));
    	RenderingRegistry.registerEntityRenderingHandler(EntityWormTail.class, manager -> new RenderWormTail(manager));
    	
    	RenderingRegistry.registerEntityRenderingHandler(EntityEowHead.class, manager -> new RenderEowHead(manager));
    	RenderingRegistry.registerEntityRenderingHandler(EntityEowBody.class, manager -> new RenderEowBody(manager));
    	RenderingRegistry.registerEntityRenderingHandler(EntityEowTail.class, manager -> new RenderEowTail(manager));
    	RenderingRegistry.registerEntityRenderingHandler(EntityShadowOrb.class, manager -> new RenderShadowOrb(manager));
    	RenderingRegistry.registerEntityRenderingHandler(EntityGuide.class, manager -> new RenderGuide(manager));
    	RenderingRegistry.registerEntityRenderingHandler(VilethornProjectile.class, manager -> new RenderVilethorn(manager));
    	RenderingRegistry.registerEntityRenderingHandler(EntityBallOHurt.class, manager -> new RenderBallOHurt(manager));
    	RenderingRegistry.registerEntityRenderingHandler(EntityTekhairaProjectile.class, manager -> new RenderTekhaira(manager));
    	RenderingRegistry.registerEntityRenderingHandler(EntityEnchantedBoomerang.class, manager -> new RenderBoomerang(manager, "enchanted_boomerang"));
    	RenderingRegistry.registerEntityRenderingHandler(EntitySummoningImp.class, manager -> new RenderSummoningImp(manager));
    	RenderingRegistry.registerEntityRenderingHandler(EntityWallOfFlesh.class, manager -> new RenderWallOfFlesh(manager));
    	RenderingRegistry.registerEntityRenderingHandler(EntityWallOfFleshEye.class, manager -> new RenderWallOfFleshEye(manager));
    	RenderingRegistry.registerEntityRenderingHandler(EntityWallOfFleshMouth.class, manager -> new RenderWallOfFleshMouth(manager));
    	RenderingRegistry.registerEntityRenderingHandler(EntityLeechHead.class, manager -> new RenderLeechHead(manager));
    	RenderingRegistry.registerEntityRenderingHandler(EntityLeechBody.class, manager -> new RenderLeechBody(manager));
    	RenderingRegistry.registerEntityRenderingHandler(EntityLeechTail.class, manager -> new RenderLeechTail(manager));
    	RenderingRegistry.registerEntityRenderingHandler(EntityEyeLaser.class, manager -> new RenderEyeLaser(manager));
    	RenderingRegistry.registerEntityRenderingHandler(TheHungryEntity.class, manager -> new RenderTheHungry(manager));


    	RenderingRegistry.registerEntityRenderingHandler(PlayerEntity.class, manager -> renderPlayer = new RenderPlayer(manager));

    }

}