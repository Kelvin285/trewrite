package kmerrill285.trewrite.entities.models.player;

import java.util.HashMap;
import java.util.UUID;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.player.PlayerEntity;

public class PlayerModelManager extends EntityModel<PlayerEntity> {
	
	public HashMap<UUID, PlayerModel> models = new HashMap<UUID, PlayerModel>();
	
	@Override
	public void render(PlayerEntity entity, float x, float y, float z, float f3, float f4, float f5) {
		if (models.containsKey(entity.getUniqueID()) == false) {
			models.put(entity.getUniqueID(), new PlayerModel());
		}
		models.get(entity.getUniqueID()).render(entity, x, y, z, f3, f4, f5);
	}
}
