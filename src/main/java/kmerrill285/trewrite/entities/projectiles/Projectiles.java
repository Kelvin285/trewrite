package kmerrill285.trewrite.entities.projectiles;

import kmerrill285.featurescript.FeatureScript;
import kmerrill285.trewrite.entities.EntitiesT;
import kmerrill285.trewrite.items.ItemT;
import kmerrill285.trewrite.util.Conversions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SpawnReason;

public class Projectiles {
	public static ScriptedProjectile shootProjectile(String projname, Entity entity, ItemT item) {
		ScriptedProjectile projectile = (ScriptedProjectile) EntitiesT.SCRIPTED_PROJECTILES.get(projname).create(entity.world, null, null, null, entity.getPosition().up(), SpawnReason.EVENT, false, false);
		projectile.setScript(FeatureScript.projectiles.get(projname));
		projectile.shoot(entity, entity.rotationPitch, entity.rotationYaw, 0, item.velocity * Conversions.feetToMeters, 0);
		return projectile;
	}
}
