package kmerrill285.trewrite.entities.models;
//Paste this code into your mod.

import com.mojang.blaze3d.platform.GlStateManager;

import kmerrill285.trewrite.entities.projectiles.EntityTekhairaProjectile;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.ModelBox;
import net.minecraft.particles.ParticleTypes;

public class ModelTekhaira extends EntityModel<EntityTekhairaProjectile> {
	private final RendererModel bone2;
	private final RendererModel bone3;

	public ModelTekhaira() {
		textureWidth = 16;
		textureHeight = 16;

		bone2 = new RendererModel(this);
		bone2.setRotationPoint(0.0F, 16.0F, 0.0F);
		setRotationAngle(bone2, 0.0F, 0.0F, 0.7854F);
		bone2.cubeList.add(new ModelBox(bone2, 0, 1, -16.0F, -15.0F, 0.0F, 16, 15, 0, 0.0F, false));

		bone3 = new RendererModel(this);
		bone3.setRotationPoint(0.0F, 16.0F, 0.0F);
		setRotationAngle(bone3, 1.5708F, -0.7854F, 0.0F);
		bone3.cubeList.add(new ModelBox(bone3, 0, 1, -8.0F, -7.0F, 11.4F, 16, 15, 0, 0.0F, true));
	}

	@Override
	public void render(EntityTekhairaProjectile entity, float f, float f1, float f2, float f3, float f4, float f5) {
		GlStateManager.translated(0, 1, 0);
		GlStateManager.rotated(45, 0, 1, 0);
//		bone2.render(f5);
		bone3.render(f5);
	}
	public void setRotationAngle(RendererModel RendererModel, float x, float y, float z) {
		RendererModel.rotateAngleX = x;
		RendererModel.rotateAngleY = y;
		RendererModel.rotateAngleZ = z;
	}
}