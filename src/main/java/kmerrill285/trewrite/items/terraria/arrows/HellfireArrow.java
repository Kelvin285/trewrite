package kmerrill285.trewrite.items.terraria.arrows;

import java.util.Random;

import kmerrill285.trewrite.entities.projectiles.EntityArrowT;
import kmerrill285.trewrite.items.Arrow;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.Explosion.Mode;

public class HellfireArrow extends Arrow {

	public HellfireArrow() {
		super(new Properties().group(ItemGroup.COMBAT), "hellfire_arrow", 13);
		this.maxStack = 999;
		this.setBuySell(20);
		this.velocity = 6.5f;
		this.recovery = 0.0f;
		this.dropRegular = 0.75f;
		this.knockback = 8;
	}
	
	public void onArrowHit(EntityArrowT arrow, LivingEntity hit) {
		this.recovery = 0.0f;
		arrow.world.createExplosion(arrow, DamageSource.causeArrowDamage(arrow, null), arrow.posX, arrow.posY, arrow.posZ, 3, false, Mode.NONE);
		for (Entity entity : arrow.world.getEntitiesWithinAABBExcludingEntity(arrow, new AxisAlignedBB(arrow.getPosition().add(-3, -3, -3), arrow.getPosition().add(3, 3, 3)))) {
			if (entity.getPositionVec().distanceTo(arrow.getPositionVec()) <= 3)
				entity.attackEntityFrom(DamageSource.causeArrowDamage(arrow, null), this.damage);
		}
	}
	
	public void onArrowShoot(EntityArrowT arrow) {
		
	}

}
