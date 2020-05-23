package kmerrill285.trewrite.entities.projectiles.magic_projectiles;

import kmerrill285.trewrite.entities.EntitiesT;
import kmerrill285.trewrite.entities.projectiles.EntityMagicProjectile;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

public class SpaceGunProjectile extends EntityMagicProjectile {

	public SpaceGunProjectile(World worldIn, LivingEntity shooter) {
		super(worldIn, shooter, EntitiesT.SPACE_GUN);
	}
	
	public SpaceGunProjectile(EntityType<SpaceGunProjectile> p_i50172_1_, World p_i50172_2_) {
		super(p_i50172_1_, p_i50172_2_);
	}
	
	public SpaceGunProjectile(World world) {
    	super(EntitiesT.SPACE_GUN, world);
    }
	
	
}
