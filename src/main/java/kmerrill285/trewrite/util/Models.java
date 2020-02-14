package kmerrill285.trewrite.util;

import java.util.Map;

import com.cout970.modelloader.animation.AnimatedModel;
import com.cout970.modelloader.api.ItemTransforms;
import com.cout970.modelloader.api.ModelConfig;
import com.cout970.modelloader.api.ModelRegisterEvent;
import com.cout970.modelloader.api.ModelRetrieveEvent;

import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.util.ResourceLocation;

public class Models {
	public static Map<String, AnimatedModel> GUIDE;
	public static void loadModels (final ModelRetrieveEvent event) {
		
		GUIDE = event.getAnimations("trewrite", "guide", "inventory");
	}
	public static void registerModels(ModelRegisterEvent event) {
        ModelConfig guide = new ModelConfig(
        		new ResourceLocation("trewrite" + ":models/entity/guide_model_gltf.gltf"),
            ItemTransforms.BLOCK_DEFAULT
        );
        
        event.registerModel("trewrite", "guide", "inventory", guide.withAnimation(true));
	}
}
