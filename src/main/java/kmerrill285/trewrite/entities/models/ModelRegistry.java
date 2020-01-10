package kmerrill285.trewrite.entities.models;

import kmerrill285.trewrite.entities.EntityItemT;
import kmerrill285.trewrite.entities.monsters.EntityBlueSlime;
import kmerrill285.trewrite.entities.monsters.EntityDemonEye;
import kmerrill285.trewrite.entities.monsters.EntityEyeOfCthulhu;
import kmerrill285.trewrite.entities.passive.EntityBunnyT;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RabbitRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModelRegistry

{

    @SubscribeEvent
    public static void registerAllModels(ModelRegistryEvent event)
    {
        //Entity
    	RenderingRegistry.registerEntityRenderingHandler(EntityItemT.class, manager -> new RenderEntityItemT(manager, Minecraft.getInstance().getItemRenderer()));
    	RenderingRegistry.registerEntityRenderingHandler(EntityBlueSlime.class, manager -> new RenderEntitySlime(manager, "blueslime"));
    	RenderingRegistry.registerEntityRenderingHandler(EntityDemonEye.class, manager -> new RenderDemonEye(manager));
    	RenderingRegistry.registerEntityRenderingHandler(EntityEyeOfCthulhu.class, manager -> new RenderEOC(manager));
    	RenderingRegistry.registerEntityRenderingHandler(EntityBunnyT.class, manager -> new RabbitRendererT(manager));

    }

}