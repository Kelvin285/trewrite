package kmerrill285.trewrite.entities.projectiles;

import kmerrill285.trewrite.core.items.ItemStackT;
import kmerrill285.trewrite.entities.EntitiesT;
import kmerrill285.trewrite.entities.EntityItemT;
import kmerrill285.trewrite.items.Arrow;
import kmerrill285.trewrite.items.ItemT;
import kmerrill285.trewrite.items.ItemsT;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.world.World;

public class EntityArrowT extends ArrowEntity
{

	public ItemT arrow;
	public int piercing;
	
	public EntityArrowT(World worldIn, LivingEntity shooter) {
		super(worldIn, shooter);
	}

	public EntityArrowT(World worldIn, double x, double y, double z) {
		super(worldIn, x, y, z);
	}

	public EntityArrowT(EntityType<? extends ArrowEntity> p_i50172_1_, World p_i50172_2_) {
		super(p_i50172_1_, p_i50172_2_);
	}
	
	public EntityArrowT(World world) {
    	super(EntitiesT.ARROW, world);
    }
	
	public boolean hitGround = false;
	
	public void tick() {
		super.tick();
		if (this.timeInGround > 0) {
			this.remove();
		}
		if (arrow != null) {
			if (arrow instanceof Arrow)
				((Arrow)arrow).arrowTick(this);
		}
	}
	
	public void remove() {
		super.remove();
		if (arrow != null) {
			Arrow a = (Arrow)arrow;
			if (rand.nextDouble() <= a.recovery) {
				if (rand.nextDouble() <= a.dropRegular) {
					EntityItemT.spawnItem(this.getEntityWorld(), this.getPosition(), new ItemStackT(ItemsT.WOODEN_ARROW, 1));
				} else {
					EntityItemT.spawnItem(this.getEntityWorld(), this.getPosition(), new ItemStackT(arrow, 1));
				}
			}
		}
		
	}
	
   
}