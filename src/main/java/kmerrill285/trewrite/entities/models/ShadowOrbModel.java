package kmerrill285.trewrite.entities.models;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.ModelBox;
import net.minecraft.entity.MobEntity;

public class ShadowOrbModel extends EntityModel<MobEntity> {
	private final RendererModel bone;

	public ShadowOrbModel() {
		textureWidth = 64;
		textureHeight = 32;

		bone = new RendererModel(this);
		bone.setRotationPoint(0.0F, 16.0F, 0.0F);
		bone.cubeList.add(new ModelBox(bone, 0, 0, -8.0F, -8.0F, -8.0F, 16, 16, 16, 0.0F, false));
	}

	@Override
	public void render(MobEntity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		bone.rotateAngleY = (float)(System.nanoTime() / 1000000000.0);
		bone.render(f5);
	}
	public void setRotationAngle(RendererModel modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}