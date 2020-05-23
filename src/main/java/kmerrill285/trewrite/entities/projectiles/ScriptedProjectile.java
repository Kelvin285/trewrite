package kmerrill285.trewrite.entities.projectiles;

import kmerrill285.featurescript.FeatureScript;
import kmerrill285.featurescript.scripts.objects.Script;
import net.minecraft.entity.EntityType;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ScriptedProjectile extends Projectile {

	public Script script;
	
	public ScriptedProjectile(EntityType<? extends Projectile> p_i50172_1_, World p_i50172_2_) {
		super(p_i50172_1_, p_i50172_2_);
	}
	
	public ScriptedProjectile(World p_i50172_2_, EntityType<? extends Projectile> p_i50172_1_) {
		super(p_i50172_1_, p_i50172_2_);
	}
	
	public ScriptedProjectile(World p_i50172_2_) {
		super(p_i50172_2_);
	}

	public void setScript(Script script) {
		this.script = script;
		this.dataManager.set(script_name, script.name);
	}
	
	@Override
	public void init() {
		script.setVariable("projectile", this);
		script.executeFunction("setupProjectile");
	}

	@Override
	public void doAIStuff() {
		String script_name = this.getDataManager().get(this.script_name);
		if (script_name.contentEquals("") == false) {
			if (script == null) {
				script = FeatureScript.scripts.get(script_name);
			}
		}
		script.executeFunction("doProjectileAI");
	}
	
	
	
	protected void onImpact(RayTraceResult result) {
		if (result.getType() == RayTraceResult.Type.ENTITY) {
			EntityRayTraceResult trace = (EntityRayTraceResult)result;
			script.executeFunction("onEntityHit", trace);
		}
		
		if (result.getType() == RayTraceResult.Type.BLOCK) {
			script.executeFunction("onHitBlock", result);
		}
		super.onImpact(result);
	}
	
    public static final DataParameter<String> script_name = EntityDataManager.createKey(ScriptedProjectile.class, DataSerializers.STRING);
    
    @Override
	protected void registerData() {
		super.registerData();
		this.dataManager.register(script_name, "");
	}


}
