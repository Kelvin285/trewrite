package kmerrill285.trewrite.entities.models.skeleton.undead_miner;

import java.util.HashMap;
import java.util.UUID;

import kmerrill285.trewrite.entities.monsters.EntityUndeadMiner;
import net.minecraft.client.renderer.entity.model.EntityModel;

public class UndeadMinerModelManager extends EntityModel<EntityUndeadMiner> {
	
	public HashMap<UUID, UndeadMinerModel> models = new HashMap<UUID, UndeadMinerModel>();
	
	@Override
	public void render(EntityUndeadMiner entity, float x, float y, float z, float f3, float f4, float f5) {
		if (models.containsKey(entity.getUniqueID()) == false) {
			models.put(entity.getUniqueID(), new UndeadMinerModel());
		}
		models.get(entity.getUniqueID()).render(entity, x, y, z, f3, f4, f5);
	}
}
