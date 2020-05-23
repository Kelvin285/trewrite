script:space_gun_projectile {
	import kmerrill285.modelloader
	
	
	setvar projectile:null
	setvar texture:'trewrite:textures/entity/projectiles/space_gun.png'
	setvar model:null
	
	function setupProjectile {
		projectile.piercing = 2
		projectile.hostile = false
		projectile.breakOnImpact = true
		projectile.breakOnGround = true
	}
	
	function doProjectileAI {
		
	}
	
	setvar overrideRender:false
	function renderProjectile projectile x y z f3 f4 f5 {
		if (model == null) {
			model = new CustomModel(ModelLoader.loadModelFromFile('projectile/SpaceGun'), 16, 16)
		} else
			model.render(entity, x, y, z, f3, f4, f5);
	}
	
	function onHitEntity hit {
		
	}
	
	function onHitBlock hit {
		
	}
	
}