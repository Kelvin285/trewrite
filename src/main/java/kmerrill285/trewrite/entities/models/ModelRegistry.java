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
import kmerrill285.trewrite.entities.models.worms.RenderEowBody;
import kmerrill285.trewrite.entities.models.worms.RenderEowHead;
import kmerrill285.trewrite.entities.models.worms.RenderEowTail;
import kmerrill285.trewrite.entities.models.worms.RenderWormBody;
import kmerrill285.trewrite.entities.models.worms.RenderWormHead;
import kmerrill285.trewrite.entities.models.worms.RenderWormTail;
import kmerrill285.trewrite.entities.monsters.EntityBlueSlime;
import kmerrill285.trewrite.entities.monsters.EntityDemonEye;
import kmerrill285.trewrite.entities.monsters.bosses.EntityEowBody;
import kmerrill285.trewrite.entities.monsters.bosses.EntityEowHead;
import kmerrill285.trewrite.entities.monsters.bosses.EntityEowTail;
import kmerrill285.trewrite.entities.monsters.bosses.EntityEyeOfCthulhu;
import kmerrill285.trewrite.entities.monsters.worms.EntityWormBody;
import kmerrill285.trewrite.entities.monsters.worms.EntityWormHead;
import kmerrill285.trewrite.entities.monsters.worms.EntityWormTail;
import kmerrill285.trewrite.entities.npc.EntityGuide;
import kmerrill285.trewrite.entities.passive.EntityBunnyT;
import kmerrill285.trewrite.entities.projectiles.EntityTekhairaProjectile;
import kmerrill285.trewrite.entities.projectiles.boomerangs.EntityEnchantedBoomerang;
import kmerrill285.trewrite.entities.projectiles.flails.EntityBallOHurt;
import kmerrill285.trewrite.entities.projectiles.magic_projectiles.VilethornProjectile;
import net.minecraft.client.Minecraft;
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


    	RenderingRegistry.registerEntityRenderingHandler(PlayerEntity.class, manager -> renderPlayer = new RenderPlayer(manager));

    }

}