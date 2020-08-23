package kmerrill285.trewrite.entities.models.npc.guide;

import java.util.HashMap;
import java.util.UUID;

import kmerrill285.trewrite.entities.npc.EntityGuide;
import net.minecraft.client.renderer.entity.model.EntityModel;

public class GuideModelManager  extends EntityModel<EntityGuide>  {
public HashMap<UUID, GuideModel> models = new HashMap<UUID, GuideModel>();
	
	@Override
	public void render(EntityGuide entity, float x, float y, float z, float f3, float f4, float f5) {
		if (models.containsKey(entity.getUniqueID()) == false) {
			models.put(entity.getUniqueID(), new GuideModel());
		}
		models.get(entity.getUniqueID()).render(entity, x, y, z, f3, f4, f5);
	}
}
