package kelvin285.trewrite;

import kelvin285.trewrite.audio.AudioRegistry;
import kelvin285.trewrite.mixin.PlayerEntityMixin;
import kelvin285.trewrite.renderers.player.CustomPlayerModel;
import kelvin285.trewrite.renderers.player.CustomPlayerRenderer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.example.registry.EntityRegistry;
import software.bernie.geckolib3.GeckoLib;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimatableModel;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.util.AnimationUtils;

public class Trewrite implements ModInitializer, ClientModInitializer {
    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LogManager.getLogger("trewrite");

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        LOGGER.info("Hello Fabric world!");
        AudioRegistry.RegisterSoundEvents();
    }


    @Override
    public void onInitializeClient() {
        System.out.println("Hello fabric client!");

        GeckoLib.initialize();
        CustomPlayerModel playerModel = new CustomPlayerModel();
        AnimationController.addModelFetcher((IAnimatable object) -> {
            if (object instanceof PlayerEntity) {
                return (IAnimatableModel<Object>) playerModel;
            }
            return null;
        });
    }
}
