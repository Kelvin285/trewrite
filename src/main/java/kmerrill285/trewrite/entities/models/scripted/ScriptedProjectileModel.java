package kmerrill285.trewrite.entities.models.scripted;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;

import com.mojang.blaze3d.platform.GlStateManager;

import kmerrill285.modelloader.CustomModel;
import kmerrill285.modelloader.ModelLoader;
import kmerrill285.modelloader.animation.Animation;
import kmerrill285.trewrite.entities.projectiles.ScriptedProjectile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;

public class ScriptedProjectileModel extends EntityModel<ScriptedProjectile> {

	public CustomModel model;

	
	public ScriptedProjectileModel() {
		
	}

	
	@Override
	public void render(ScriptedProjectile entity, float x, float y, float z, float f3, float f4, float f5) {
		if (entity.script != null) {
			entity.script.executeFunction("renderProjectile", entity, x, y, z, f3, f4, f5);
		}
	}
		
	public void setRotationAngle(RendererModel modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}