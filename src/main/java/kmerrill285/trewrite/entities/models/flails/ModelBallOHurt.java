package kmerrill285.trewrite.entities.models.flails;

import kmerrill285.trewrite.entities.EntityFlail;
import kmerrill285.trewrite.entities.projectiles.flails.EntityBallOHurt;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.ModelBox;

public class ModelBallOHurt extends EntityModel<EntityBallOHurt> {
	private final RendererModel Spike;
	private final RendererModel Main;
	private final RendererModel Spikes;
	private final RendererModel bone2;
	private final RendererModel Spike4;
	private final RendererModel Spike5;
	private final RendererModel bone;
	private final RendererModel Spike3;
	private final RendererModel Spike2;
	private final RendererModel Spike15;
	private final RendererModel Spikes2;
	private final RendererModel bone3;
	private final RendererModel Spike6;
	private final RendererModel Spike7;
	private final RendererModel bone4;
	private final RendererModel Spike8;
	private final RendererModel Spike9;
	private final RendererModel Spike10;
	private final RendererModel Spike11;
	private final RendererModel Spike12;
	private final RendererModel Spike13;
	private final RendererModel Spike14;
	private final RendererModel Main2;
	private final RendererModel Main3;
	private final RendererModel Main4;
	private final RendererModel Main5;
	private final RendererModel Main6;
	private final RendererModel Main7;
	private final RendererModel Spike16;
	private final RendererModel Spike17;
	private final RendererModel Spike18;

	public ModelBallOHurt() {
		textureWidth = 64;
		textureHeight = 64;

		Spike = new RendererModel(this);
		Spike.setRotationPoint(0.1F, 14.0F, 0.0F);
		setRotationAngle(Spike, 3.1416F, 0.0F, 0.0F);
		Spike.cubeList.add(new ModelBox(Spike, 0, 28, -2.5F, -1.5F, 0.0F, 5, 7, 0, 0.0F, false));
		Spike.cubeList.add(new ModelBox(Spike, 0, 23, 0.0F, -1.5F, -2.5F, 0, 7, 5, 0.0F, false));

		Main = new RendererModel(this);
		Main.setRotationPoint(0.0F, 24.0F, 0.0F);
		Main.cubeList.add(new ModelBox(Main, 0, 0, -6.5F, -6.5F, -6.5F, 13, 13, 13, 0.0F, false));

		Spikes = new RendererModel(this);
		Spikes.setRotationPoint(0.0F, 24.0F, 0.0F);

		bone2 = new RendererModel(this);
		bone2.setRotationPoint(0.0F, 0.0F, 0.0F);
		setRotationAngle(bone2, 0.0F, -1.5708F, 0.0F);
		Spikes.addChild(bone2);

		Spike4 = new RendererModel(this);
		Spike4.setRotationPoint(0.1F, -10.0F, 6.4F);
		setRotationAngle(Spike4, 2.4435F, 3.1416F, 0.0F);
		bone2.addChild(Spike4);
		Spike4.cubeList.add(new ModelBox(Spike4, 1, 26, -1.5F, 2.0F, -12.3F, 3, 7, 0, 0.0F, false));
		Spike4.cubeList.add(new ModelBox(Spike4, 1, 23, 0.0F, 2.0F, -13.8F, 0, 7, 3, 0.0F, false));

		Spike5 = new RendererModel(this);
		Spike5.setRotationPoint(0.1F, -10.0F, 6.4F);
		setRotationAngle(Spike5, 2.4435F, 0.0F, 0.0F);
		bone2.addChild(Spike5);
		Spike5.cubeList.add(new ModelBox(Spike5, 1, 26, -1.5F, -6.0F, -2.3F, 3, 7, 0, 0.0F, false));
		Spike5.cubeList.add(new ModelBox(Spike5, 1, 23, 0.0F, -6.0F, -3.8F, 0, 7, 3, 0.0F, false));

		bone = new RendererModel(this);
		bone.setRotationPoint(0.0F, 0.0F, 0.0F);
		Spikes.addChild(bone);

		Spike3 = new RendererModel(this);
		Spike3.setRotationPoint(0.1F, -10.0F, 6.4F);
		setRotationAngle(Spike3, 2.4435F, 3.1416F, 0.0F);
		bone.addChild(Spike3);
		Spike3.cubeList.add(new ModelBox(Spike3, 1, 26, -1.5F, 2.0F, -12.3F, 3, 7, 0, 0.0F, false));
		Spike3.cubeList.add(new ModelBox(Spike3, 1, 23, 0.0F, 2.0F, -13.8F, 0, 7, 3, 0.0F, false));

		Spike2 = new RendererModel(this);
		Spike2.setRotationPoint(0.1F, -10.0F, 6.4F);
		setRotationAngle(Spike2, 2.4435F, 0.0F, 0.0F);
		bone.addChild(Spike2);
		Spike2.cubeList.add(new ModelBox(Spike2, 1, 26, -1.5F, -6.0F, -2.3F, 3, 7, 0, 0.0F, false));
		Spike2.cubeList.add(new ModelBox(Spike2, 1, 23, 0.0F, -6.0F, -3.8F, 0, 7, 3, 0.0F, false));

		Spike15 = new RendererModel(this);
		Spike15.setRotationPoint(0.0F, 24.0F, 0.0F);
		setRotationAngle(Spike15, 2.3562F, 3.1416F, -1.5708F);
		Spike15.cubeList.add(new ModelBox(Spike15, 1, 26, -2.0F, 6.0F, 0.0F, 3, 7, 0, 0.0F, false));
		Spike15.cubeList.add(new ModelBox(Spike15, 1, 23, -0.4F, 6.0F, -1.5F, 0, 7, 3, 0.0F, false));

		Spikes2 = new RendererModel(this);
		Spikes2.setRotationPoint(0.0F, 24.0F, 0.0F);
		setRotationAngle(Spikes2, 3.1416F, 0.0F, 0.0F);

		bone3 = new RendererModel(this);
		bone3.setRotationPoint(0.0F, 0.0F, 0.0F);
		setRotationAngle(bone3, 0.0F, -1.5708F, 0.0F);
		Spikes2.addChild(bone3);

		Spike6 = new RendererModel(this);
		Spike6.setRotationPoint(0.1F, -10.0F, 6.4F);
		setRotationAngle(Spike6, 2.4435F, 3.1416F, 0.0F);
		bone3.addChild(Spike6);
		Spike6.cubeList.add(new ModelBox(Spike6, 1, 26, -1.5F, 2.0F, -12.3F, 3, 7, 0, 0.0F, false));
		Spike6.cubeList.add(new ModelBox(Spike6, 1, 23, 0.0F, 2.0F, -13.8F, 0, 7, 3, 0.0F, false));

		Spike7 = new RendererModel(this);
		Spike7.setRotationPoint(0.1F, -10.0F, 6.4F);
		setRotationAngle(Spike7, 2.4435F, 0.0F, 0.0F);
		bone3.addChild(Spike7);
		Spike7.cubeList.add(new ModelBox(Spike7, 1, 26, -1.5F, -6.0F, -2.3F, 3, 7, 0, 0.0F, false));
		Spike7.cubeList.add(new ModelBox(Spike7, 1, 23, 0.0F, -6.0F, -3.8F, 0, 7, 3, 0.0F, false));

		bone4 = new RendererModel(this);
		bone4.setRotationPoint(0.0F, 0.0F, 0.0F);
		Spikes2.addChild(bone4);

		Spike8 = new RendererModel(this);
		Spike8.setRotationPoint(0.1F, -10.0F, 6.4F);
		setRotationAngle(Spike8, 2.4435F, 3.1416F, 0.0F);
		bone4.addChild(Spike8);
		Spike8.cubeList.add(new ModelBox(Spike8, 1, 26, -1.5F, 2.0F, -12.3F, 3, 7, 0, 0.0F, false));
		Spike8.cubeList.add(new ModelBox(Spike8, 1, 23, 0.0F, 2.0F, -13.8F, 0, 7, 3, 0.0F, false));

		Spike9 = new RendererModel(this);
		Spike9.setRotationPoint(0.1F, -10.0F, 6.4F);
		setRotationAngle(Spike9, 2.4435F, 0.0F, 0.0F);
		bone4.addChild(Spike9);
		Spike9.cubeList.add(new ModelBox(Spike9, 1, 26, -1.5F, -6.0F, -2.3F, 3, 7, 0, 0.0F, false));
		Spike9.cubeList.add(new ModelBox(Spike9, 1, 23, 0.0F, -6.0F, -3.8F, 0, 7, 3, 0.0F, false));

		Spike10 = new RendererModel(this);
		Spike10.setRotationPoint(0.1F, 14.0F, 0.0F);
		Spike10.cubeList.add(new ModelBox(Spike10, 0, 28, -2.5F, 18.5F, 0.0F, 5, 7, 0, 0.0F, false));
		Spike10.cubeList.add(new ModelBox(Spike10, 0, 23, 0.0F, 18.5F, -2.5F, 0, 7, 5, 0.0F, false));

		Spike11 = new RendererModel(this);
		Spike11.setRotationPoint(0.1F, 25.0F, 0.0F);
		setRotationAngle(Spike11, 0.0F, 0.0F, -1.5708F);
		Spike11.cubeList.add(new ModelBox(Spike11, 0, 28, -1.5F, 8.4F, 0.0F, 5, 7, 0, 0.0F, false));
		Spike11.cubeList.add(new ModelBox(Spike11, 0, 23, 1.0F, 8.4F, -2.5F, 0, 7, 5, 0.0F, false));

		Spike12 = new RendererModel(this);
		Spike12.setRotationPoint(0.1F, 25.0F, 0.0F);
		setRotationAngle(Spike12, -1.5708F, 0.0F, -1.5708F);
		Spike12.cubeList.add(new ModelBox(Spike12, 0, 28, -1.5F, 8.4F, 0.0F, 5, 7, 0, 0.0F, false));
		Spike12.cubeList.add(new ModelBox(Spike12, 0, 23, 1.0F, 8.4F, -2.5F, 0, 7, 5, 0.0F, false));

		Spike13 = new RendererModel(this);
		Spike13.setRotationPoint(0.1F, 25.0F, 0.0F);
		setRotationAngle(Spike13, 3.1416F, 0.0F, -1.5708F);
		Spike13.cubeList.add(new ModelBox(Spike13, 0, 28, -1.5F, 8.4F, 0.0F, 5, 7, 0, 0.0F, false));
		Spike13.cubeList.add(new ModelBox(Spike13, 0, 23, 1.0F, 8.4F, -2.5F, 0, 7, 5, 0.0F, false));

		Spike14 = new RendererModel(this);
		Spike14.setRotationPoint(0.1F, 25.0F, 0.0F);
		setRotationAngle(Spike14, 1.5708F, 0.0F, -1.5708F);
		Spike14.cubeList.add(new ModelBox(Spike14, 0, 28, -1.5F, 8.4F, 0.0F, 5, 7, 0, 0.0F, false));
		Spike14.cubeList.add(new ModelBox(Spike14, 0, 23, 1.0F, 8.4F, -2.5F, 0, 7, 5, 0.0F, false));

		Main2 = new RendererModel(this);
		Main2.setRotationPoint(0.0F, 24.0F, 0.0F);
		setRotationAngle(Main2, 0.6981F, 0.0F, 0.0F);
		Main2.cubeList.add(new ModelBox(Main2, 13, 27, -8.5F, -3.5F, -3.5F, 7, 7, 7, 0.0F, false));

		Main3 = new RendererModel(this);
		Main3.setRotationPoint(0.0F, 24.0F, 0.0F);
		setRotationAngle(Main3, 0.6981F, -1.5708F, 0.0F);
		Main3.cubeList.add(new ModelBox(Main3, 13, 27, -8.5F, -3.5F, -3.5F, 7, 7, 7, 0.0F, false));

		Main4 = new RendererModel(this);
		Main4.setRotationPoint(0.0F, 24.0F, 0.0F);
		setRotationAngle(Main4, 0.6981F, 3.1416F, 0.0F);
		Main4.cubeList.add(new ModelBox(Main4, 13, 27, -8.5F, -3.5F, -3.5F, 7, 7, 7, 0.0F, false));

		Main5 = new RendererModel(this);
		Main5.setRotationPoint(0.0F, 24.0F, 0.0F);
		setRotationAngle(Main5, 0.6981F, 1.5708F, 0.0F);
		Main5.cubeList.add(new ModelBox(Main5, 13, 27, -8.5F, -3.5F, -3.5F, 7, 7, 7, 0.0F, false));

		Main6 = new RendererModel(this);
		Main6.setRotationPoint(0.0F, 24.0F, 0.0F);
		setRotationAngle(Main6, -0.7854F, 0.0F, 1.5708F);
		Main6.cubeList.add(new ModelBox(Main6, 13, 27, -8.5F, -3.5F, -3.5F, 7, 7, 7, 0.0F, false));

		Main7 = new RendererModel(this);
		Main7.setRotationPoint(0.0F, 24.0F, 0.0F);
		setRotationAngle(Main7, -0.7854F, 3.1416F, 1.5708F);
		Main7.cubeList.add(new ModelBox(Main7, 13, 27, -8.5F, -3.5F, -3.5F, 7, 7, 7, 0.0F, false));

		Spike16 = new RendererModel(this);
		Spike16.setRotationPoint(0.0F, 24.0F, 0.0F);
		setRotationAngle(Spike16, 0.7854F, 3.1416F, -1.5708F);
		Spike16.cubeList.add(new ModelBox(Spike16, 1, 26, -2.0F, 6.0F, 0.0F, 3, 7, 0, 0.0F, false));
		Spike16.cubeList.add(new ModelBox(Spike16, 1, 23, -0.4F, 6.0F, -1.5F, 0, 7, 3, 0.0F, false));

		Spike17 = new RendererModel(this);
		Spike17.setRotationPoint(0.0F, 24.0F, 0.0F);
		setRotationAngle(Spike17, -0.7854F, 3.1416F, -1.5708F);
		Spike17.cubeList.add(new ModelBox(Spike17, 1, 26, -2.0F, 6.0F, 0.0F, 3, 7, 0, 0.0F, false));
		Spike17.cubeList.add(new ModelBox(Spike17, 1, 23, -0.4F, 6.0F, -1.5F, 0, 7, 3, 0.0F, false));

		Spike18 = new RendererModel(this);
		Spike18.setRotationPoint(0.0F, 24.0F, 0.0F);
		setRotationAngle(Spike18, -2.3562F, 3.1416F, -1.5708F);
		Spike18.cubeList.add(new ModelBox(Spike18, 1, 26, -2.0F, 6.0F, 0.0F, 3, 7, 0, 0.0F, false));
		Spike18.cubeList.add(new ModelBox(Spike18, 1, 23, -0.4F, 6.0F, -1.5F, 0, 7, 3, 0.0F, false));
	}

	@Override
	public void render(EntityBallOHurt entity, float f, float f1, float f2, float f3, float f4, float f5) {
		Spike.render(f5);
		Main.render(f5);
		Spikes.render(f5);
		Spike15.render(f5);
		Spikes2.render(f5);
		Spike10.render(f5);
		Spike11.render(f5);
		Spike12.render(f5);
		Spike13.render(f5);
		Spike14.render(f5);
		Main2.render(f5);
		Main3.render(f5);
		Main4.render(f5);
		Main5.render(f5);
		Main6.render(f5);
		Main7.render(f5);
		Spike16.render(f5);
		Spike17.render(f5);
		Spike18.render(f5);
		
	}
	public void setRotationAngle(RendererModel RendererModel, float x, float y, float z) {
		RendererModel.rotateAngleX = x;
		RendererModel.rotateAngleY = y;
		RendererModel.rotateAngleZ = z;
	}
}