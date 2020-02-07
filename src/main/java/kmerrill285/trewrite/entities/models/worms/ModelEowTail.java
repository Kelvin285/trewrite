package kmerrill285.trewrite.entities.models.worms;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.ModelBox;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;

public class ModelEowTail extends EntityModel<MobEntity> {
	private final RendererModel shape1;
	private final RendererModel shape2;
	private final RendererModel shape11;
	private final RendererModel shape12;

	public ModelEowTail() {
		textureWidth = 64;
		textureHeight = 32;

		shape1 = new RendererModel(this);
		shape1.setRotationPoint(0.0F, 16.0F, 0.0F);
		shape1.cubeList.add(new ModelBox(shape1, 0, 0, -3.9F, -4.0F, -4.0F, 7, 8, 8, 0.0F, false));

		shape2 = new RendererModel(this);
		shape2.setRotationPoint(0.0F, 16.0F, 0.0F);
		setRotationAngle(shape2, 0.7854F, 0.0F, 0.0F);
		shape2.cubeList.add(new ModelBox(shape2, 0, 0, -3.95F, -6.0F, -6.0F, 7, 8, 8, 0.0F, false));
		shape2.cubeList.add(new ModelBox(shape2, 0, 0, -4.0F, -2.0F, -2.0F, 8, 8, 8, 0.0F, false));
		shape2.cubeList.add(new ModelBox(shape2, 0, 8, -3.0F, 4.0F, 4.0F, 6, 4, 4, 0.0F, false));
		shape2.cubeList.add(new ModelBox(shape2, 0, 8, -1.5F, 6.0F, 6.0F, 3, 4, 4, 0.0F, false));

		shape11 = new RendererModel(this);
		shape11.setRotationPoint(0.0F, 16.0F, 0.0F);
		setRotationAngle(shape11, -1.5708F, -0.7854F, 0.0F);
		shape11.cubeList.add(new ModelBox(shape11, 3, 17, 1.2F, -2.0F, 0.0F, 6, 15, 0, 0.0F, true));

		shape12 = new RendererModel(this);
		shape12.setRotationPoint(0.0F, 16.0F, 0.0F);
		setRotationAngle(shape12, -1.5708F, 0.7854F, 0.0F);
		shape12.cubeList.add(new ModelBox(shape12, 3, 17, -7.8F, -2.0F, 0.0F, 6, 15, 0, 0.0F, false));
	}

	@Override
	public void render(MobEntity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		shape1.render(f5);
		shape2.render(f5);
		shape11.render(f5);
		shape12.render(f5);
	}
	public void setRotationAngle(RendererModel RendererModel, float x, float y, float z) {
		RendererModel.rotateAngleX = x;
		RendererModel.rotateAngleY = y;
		RendererModel.rotateAngleZ = z;
	}
}