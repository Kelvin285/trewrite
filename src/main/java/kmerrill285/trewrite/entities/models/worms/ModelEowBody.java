package kmerrill285.trewrite.entities.models.worms;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.ModelBox;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;

public class ModelEowBody extends EntityModel<MobEntity> {
	private final RendererModel shape1;
	private final RendererModel shape2;
	private final RendererModel shape3;
	private final RendererModel shape4;
	private final RendererModel shape5;
	private final RendererModel shape6;
	private final RendererModel shape7;
	private final RendererModel shape8;
	private final RendererModel shape9;
	private final RendererModel shape10;
	private final RendererModel shape11;
	private final RendererModel shape12;

	public ModelEowBody() {
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

		shape3 = new RendererModel(this);
		shape3.setRotationPoint(0.0F, 16.0F, 0.0F);
		setRotationAngle(shape3, 3.1416F, -0.7854F, 0.0F);
		shape3.cubeList.add(new ModelBox(shape3, 35, 11, -9.8F, -4.0F, -1.0F, 6, 21, 0, 0.0F, false));

		shape4 = new RendererModel(this);
		shape4.setRotationPoint(0.0F, 16.0F, 0.0F);
		setRotationAngle(shape4, 0.0F, -0.7854F, 0.0F);
		shape4.cubeList.add(new ModelBox(shape4, 35, 11, -8.8F, -3.0F, 1.0F, 6, 21, 0, 0.0F, false));

		shape5 = new RendererModel(this);
		shape5.setRotationPoint(0.0F, 16.0F, 0.0F);
		setRotationAngle(shape5, 3.1416F, 0.7854F, 0.0F);
		shape5.cubeList.add(new ModelBox(shape5, 35, 11, 2.2F, -4.0F, 0.0F, 6, 21, 0, 0.0F, true));

		shape6 = new RendererModel(this);
		shape6.setRotationPoint(0.0F, 16.0F, 0.0F);
		setRotationAngle(shape6, 0.0F, 0.5236F, 0.0F);
		shape6.cubeList.add(new ModelBox(shape6, 35, 11, 2.2F, -3.0F, 0.0F, 6, 21, 0, 0.0F, true));

		shape7 = new RendererModel(this);
		shape7.setRotationPoint(0.0F, 16.0F, 0.0F);
		setRotationAngle(shape7, 3.1416F, 0.7854F, 0.0F);
		shape7.cubeList.add(new ModelBox(shape7, 35, 16, -9.8F, -2.0F, -1.0F, 6, 21, 0, 0.0F, false));

		shape8 = new RendererModel(this);
		shape8.setRotationPoint(0.0F, 16.0F, 0.0F);
		setRotationAngle(shape8, 0.0F, -1.0472F, 0.0F);
		shape8.cubeList.add(new ModelBox(shape8, 35, 16, 2.2F, -3.0F, 0.0F, 6, 16, 0, 0.0F, true));

		shape9 = new RendererModel(this);
		shape9.setRotationPoint(0.0F, 16.0F, 0.0F);
		setRotationAngle(shape9, 3.1416F, -0.7854F, 0.0F);
		shape9.cubeList.add(new ModelBox(shape9, 35, 16, 2.2F, -2.0F, 0.0F, 6, 21, 0, 0.0F, true));

		shape10 = new RendererModel(this);
		shape10.setRotationPoint(0.0F, 16.0F, 0.0F);
		setRotationAngle(shape10, 0.0F, 0.7854F, 0.0F);
		shape10.cubeList.add(new ModelBox(shape10, 35, 16, -8.8F, -3.0F, 1.0F, 6, 21, 0, 0.0F, false));

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
		shape3.render(f5);
		shape4.render(f5);
		shape5.render(f5);
		shape6.render(f5);
		shape7.render(f5);
		shape8.render(f5);
		shape9.render(f5);
		shape10.render(f5);
		shape11.render(f5);
		shape12.render(f5);
	}
	public void setRotationAngle(RendererModel RendererModel, float x, float y, float z) {
		RendererModel.rotateAngleX = x;
		RendererModel.rotateAngleY = y;
		RendererModel.rotateAngleZ = z;
	}
}