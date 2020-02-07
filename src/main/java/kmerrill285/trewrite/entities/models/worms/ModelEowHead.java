package kmerrill285.trewrite.entities.models.worms;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.ModelBox;
import net.minecraft.entity.MobEntity;

public class ModelEowHead extends EntityModel<MobEntity> {
	private final RendererModel shape1;
	private final RendererModel shape1_1;
	private final RendererModel shape1_2;
	private final RendererModel shape1_3;
	private final RendererModel shape1_16;
	private final RendererModel shape1_4;
	private final RendererModel shape1_5;
	private final RendererModel shape1_6;
	private final RendererModel shape1_7;
	private final RendererModel shape1_8;
	private final RendererModel shape1_10;
	private final RendererModel shape1_9;
	private final RendererModel shape1_11;
	private final RendererModel shape1_12;
	private final RendererModel shape1_13;
	private final RendererModel shape1_14;
	private final RendererModel shape1_15;
	private final RendererModel shape1_17;


	public ModelEowHead() {
		textureWidth = 64;
		textureHeight = 32;

		shape1 = new RendererModel(this);
		shape1.setRotationPoint(0.0F, 16.0F, 0.0F);

		shape1_1 = new RendererModel(this);
		shape1_1.setRotationPoint(0.0F, 16.0F, 0.0F);
		setRotationAngle(shape1_1, 0.9425F, 0.0F, 0.0F);
		shape1_1.cubeList.add(new ModelBox(shape1_1, 0, 0, -4.0F, -4.0F, -4.0F, 8, 8, 8, 0.0F, false));
		shape1_1.cubeList.add(new ModelBox(shape1_1, 0, 0, -4.0F, 3.0F, 1.0F, 8, 8, 8, 0.0F, false));
		shape1_1.cubeList.add(new ModelBox(shape1_1, 16, 0, -4.0F, -4.0F, -4.0F, 8, 0, 8, 0.0F, false));

		shape1_2 = new RendererModel(this);
		shape1_2.setRotationPoint(0.0F, 16.0F, 0.0F);

		shape1_3 = new RendererModel(this);
		shape1_3.setRotationPoint(-4.0F, 16.0F, 0.0F);
		setRotationAngle(shape1_3, 0.0F, 0.0F, -0.2618F);
		shape1_3.cubeList.add(new ModelBox(shape1_3, 5, 19, -9.0F, 2.0F, -13.0F, 9, 0, 13, 0.0F, false));

		shape1_16 = new RendererModel(this);
		shape1_16.setRotationPoint(0.0F, 0.0F, 0.0F);

		shape1_4 = new RendererModel(this);
		shape1_4.setRotationPoint(-4.0F, 16.0F, 0.0F);
		setRotationAngle(shape1_4, 0.0F, 0.0F, 0.3491F);
		shape1_4.cubeList.add(new ModelBox(shape1_4, 5, 19, -10.0F, -3.0F, -13.0F, 9, 0, 13, 0.0F, false));

		shape1_5 = new RendererModel(this);
		shape1_5.setRotationPoint(0.0F, 16.0F, 0.0F);
		shape1_5.cubeList.add(new ModelBox(shape1_5, 0, 0, -3.9F, -4.0F, -1.0F, 7, 8, 8, 0.0F, false));

		shape1_6 = new RendererModel(this);
		shape1_6.setRotationPoint(-4.0F, 16.0F, 0.0F);
		setRotationAngle(shape1_6, 0.0F, 0.0F, 2.7925F);
		shape1_6.cubeList.add(new ModelBox(shape1_6, 5, 19, -17.5F, 0.2F, -13.3F, 9, 0, 13, 0.0F, false));

		shape1_7 = new RendererModel(this);
		shape1_7.setRotationPoint(-4.0F, 16.0F, 0.0F);
		setRotationAngle(shape1_7, 0.0F, 0.0F, -2.8798F);
		shape1_7.cubeList.add(new ModelBox(shape1_7, 5, 19, -16.8F, 0.2F, -13.3F, 9, 0, 13, 0.0F, false));

		shape1_8 = new RendererModel(this);
		shape1_8.setRotationPoint(0.0F, 4.0F, 0.0F);
		setRotationAngle(shape1_8, -1.5708F, 0.0F, 0.0F);
		shape1_8.cubeList.add(new ModelBox(shape1_8, 47, 0, 3.6F, 3.0F, 12.0F, 7, 21, 0, 0.0F, true));
		shape1_8.cubeList.add(new ModelBox(shape1_8, 47, 0, -10.3F, 3.0F, 12.0F, 7, 21, 0, 0.0F, false));

		shape1_10 = new RendererModel(this);
		shape1_10.setRotationPoint(0.0F, 4.0F, 0.0F);
		setRotationAngle(shape1_10, -1.5708F, -0.5236F, 0.0F);
		shape1_10.cubeList.add(new ModelBox(shape1_10, 2, 16, 5.6F, -9.0F, 12.0F, 7, 16, 0, 0.0F, true));

		shape1_9 = new RendererModel(this);
		shape1_9.setRotationPoint(0.0F, 4.0F, 0.0F);
		setRotationAngle(shape1_9, -1.5708F, 0.5236F, 0.0F);
		shape1_9.cubeList.add(new ModelBox(shape1_9, 2, 16, -12.4F, -9.0F, 12.0F, 7, 16, 0, 0.0F, false));

		shape1_11 = new RendererModel(this);
		shape1_11.setRotationPoint(0.0F, 4.0F, 0.0F);
		setRotationAngle(shape1_11, 3.1416F, 2.1817F, 0.0F);
		shape1_11.cubeList.add(new ModelBox(shape1_11, 34, 11, -2.4F, -15.0F, 5.0F, 7, 21, 0, 0.0F, true));

		shape1_12 = new RendererModel(this);
		shape1_12.setRotationPoint(0.0F, 4.0F, 0.0F);
		setRotationAngle(shape1_12, 3.1416F, -2.1817F, 0.0F);
		shape1_12.cubeList.add(new ModelBox(shape1_12, 34, 11, -4.1F, -15.0F, 4.2F, 7, 21, 0, 0.0F, false));

		shape1_13 = new RendererModel(this);
		shape1_13.setRotationPoint(0.0F, 4.0F, 0.0F);
		setRotationAngle(shape1_13, 3.1416F, 2.1817F, 0.0F);

		shape1_14 = new RendererModel(this);
		shape1_14.setRotationPoint(0.0F, 4.0F, 0.0F);
		setRotationAngle(shape1_14, 3.1416F, 2.1817F, 2.618F);
		shape1_14.cubeList.add(new ModelBox(shape1_14, 35, 22, -11.4F, 13.0F, -1.0F, 7, 10, 0, 0.0F, true));

		shape1_15 = new RendererModel(this);
		shape1_15.setRotationPoint(0.0F, 4.0F, 0.0F);
		setRotationAngle(shape1_15, 3.1416F, 1.1345F, -2.618F);
		shape1_15.cubeList.add(new ModelBox(shape1_15, 35, 22, -11.4F, 13.1F, 2.4F, 7, 10, 0, 0.0F, true));

		shape1_17 = new RendererModel(this);
		shape1_17.setRotationPoint(0.0F, 0.0F, 0.0F);
	}

	@Override
	public void render(MobEntity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		shape1.render(f5);
		shape1_1.render(f5);
		shape1_2.render(f5);
		shape1_3.render(f5);
		shape1_16.render(f5);
		shape1_4.render(f5);
		shape1_5.render(f5);
		shape1_6.render(f5);
		shape1_7.render(f5);
		shape1_8.render(f5);
		shape1_10.render(f5);
		shape1_9.render(f5);
		shape1_11.render(f5);
		shape1_12.render(f5);
		shape1_13.render(f5);
		shape1_14.render(f5);
		shape1_15.render(f5);
		shape1_17.render(f5);
	}
	public void setRotationAngle(RendererModel RendererModel, float x, float y, float z) {
		RendererModel.rotateAngleX = x;
		RendererModel.rotateAngleY = y;
		RendererModel.rotateAngleZ = z;
	}
}