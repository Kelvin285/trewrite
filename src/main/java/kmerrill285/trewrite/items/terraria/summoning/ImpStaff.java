package kmerrill285.trewrite.items.terraria.summoning;

import kmerrill285.trewrite.items.SummoningItem;

public class ImpStaff extends SummoningItem {

	public ImpStaff(String name) {
		super("imp_staff");
		this.damage = 21;
		this.knockback = 2;
		this.mana = 10;
		this.useTime = 36;
		this.velocity = 10;
		this.setTooltip("Summons an imp to fight for you");
	}

}
