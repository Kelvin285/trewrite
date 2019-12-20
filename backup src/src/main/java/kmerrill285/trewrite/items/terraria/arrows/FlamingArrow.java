package kmerrill285.trewrite.items.terraria.arrows;

import java.util.Random;

import kmerrill285.trewrite.entities.projectiles.EntityArrowT;
import kmerrill285.trewrite.items.Arrow;
import net.minecraft.item.ItemGroup;

public class FlamingArrow extends Arrow {

	public FlamingArrow() {
		super(new Properties().group(ItemGroup.COMBAT), "flaming_arrow", 7);
		this.maxStack = 999;
		this.setBuySell(2);
		this.velocity = 3.5f;
		this.recovery = 1.0f / 3.0f;
		this.dropRegular = 0.75f;
	}
	
	public void onArrowShoot(EntityArrowT arrow) {
		if (new Random().nextDouble() <= 0.333) {
			arrow.setFire(1);
		}
	}

}
