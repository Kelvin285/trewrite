package kmerrill285.trewrite.entities.models;

import com.mojang.blaze3d.platform.GlStateManager;

import kmerrill285.trewrite.entities.EntityCoin;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.ModelBox;

public class CoinModel extends EntityModel<EntityCoin> {
	private final RendererModel bb_main;

	public CoinModel() {
		textureWidth = 16;
		textureHeight = 16;
		
		bb_main = new RendererModel(this);
		bb_main.setRotationPoint(0.0F, 24.0F, 0.0F);
		bb_main.cubeList.add(new ModelBox(bb_main, 0, 0, -8.0F, -16.0F, 0.0F, 16, 16, 0, 0.0F, false));
	}

	@Override
	public void render(EntityCoin entity, float f, float f1, float f2, float f3, float f4, float f5) {
		bb_main.rotateAngleY = (float)(System.nanoTime() / 1000000000.0);
		bb_main.render(f5);
	}
	public void setRotationAngle(RendererModel modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}