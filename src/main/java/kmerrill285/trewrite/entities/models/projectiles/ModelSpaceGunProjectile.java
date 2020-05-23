package kmerrill285.trewrite.entities.models.projectiles;
//Made with Blockbench
//Paste this code into your mod.

import com.mojang.blaze3d.platform.GlStateManager;

import kmerrill285.trewrite.entities.projectiles.magic_projectiles.SpaceGunProjectile;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.ModelBox;

public class ModelSpaceGunProjectile extends EntityModel<SpaceGunProjectile> {
	private final RendererModel bb_main;

	public ModelSpaceGunProjectile() {
		textureWidth = 1;
		textureHeight = 1;

		bb_main = new RendererModel(this);
		bb_main.setRotationPoint(0.0F, 24.0F, 0.0F);
		bb_main.cubeList.add(new ModelBox(bb_main, 0, 0, -8.0F, -8.0F, -8.0F, 8, 8, 8, 0.0F, false));
	}

	@Override
	public void render(SpaceGunProjectile entity, float f, float f1, float f2, float f3, float f4, float f5) {
		bb_main.setRotationPoint(0.0F, 8.0F, 0.0F);
		
		GlStateManager.pushMatrix();
		GlStateManager.translated(0, 1.0f, 0);
		bb_main.render(f5);
		GlStateManager.popMatrix();
	}
	public void setRotationAngle(RendererModel RendererModel, float x, float y, float z) {
		RendererModel.rotateAngleX = x;
		RendererModel.rotateAngleY = y;
		RendererModel.rotateAngleZ = z;
	}
}