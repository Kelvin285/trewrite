package kmerrill285.trewrite.entities.models;

import kmerrill285.trewrite.entities.EntityItemT;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)

@Mod.EventBusSubscriber(modid = "trewrite", value = Dist.CLIENT)

public class ModelRegistry

{

    @SubscribeEvent

    public static void registerAllModels(ModelRegistryEvent event)

    {

        //Entity

        RenderingRegistry.registerEntityRenderingHandler(EntityItemT.class, manager -> new RenderEntityItemT(manager, Minecraft.getInstance().getItemRenderer()));

    }

}