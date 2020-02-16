package kmerrill285.trewrite.entities.models.summoning;

import kmerrill285.trewrite.entities.summoning.EntitySummoningImp;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.ModelBox;

public class SummoningImpModel extends EntityModel<EntitySummoningImp> {
	private final RendererModel bone;
	private final RendererModel bone4;
	private final RendererModel bone6;
	private final RendererModel wingl;
	private final RendererModel wingr;
	private final RendererModel bone5;

	public SummoningImpModel() {
		textureWidth = 64;
		textureHeight = 64;

		bone = new RendererModel(this);
		bone.setRotationPoint(0.0F, 24.0F, 0.0F);
		bone.cubeList.add(new ModelBox(bone, 12, 25, -2.0F, -7.0F, -5.0F, 5, 2, 1, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 13, 22, -1.0F, -8.0F, -5.0F, 3, 1, 1, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 7, 4, -4.0F, -13.0F, -4.0F, 9, 8, 7, 0.0F, false));

		bone4 = new RendererModel(this);
		bone4.setRotationPoint(0.0F, 0.0F, 0.0F);
		setRotationAngle(bone4, 0.0F, -1.5708F, -0.2618F);
		bone.addChild(bone4);
		bone4.cubeList.add(new ModelBox(bone4, 25, 20, -3.0F, -9.6F, -8.0F, 4, 5, 1, 0.0F, false));

		bone6 = new RendererModel(this);
		bone6.setRotationPoint(0.0F, 0.0F, 0.0F);
		setRotationAngle(bone6, 0.0F, 1.5708F, 0.2618F);
		bone.addChild(bone6);
		bone6.cubeList.add(new ModelBox(bone6, 25, 20, -1.0F, -9.6F, -7.0F, 4, 5, 1, 0.0F, false));

		wingl = new RendererModel(this);
		wingl.setRotationPoint(0.0F, 24.0F, 0.0F);
		setRotationAngle(wingl, -1.5708F, 0.0F, 1.5708F);
		wingl.cubeList.add(new ModelBox(wingl, 42, 14, -7.5F, -6.0F, -3.0F, 3, 4, 1, 0.0F, false));

		wingr = new RendererModel(this);
		wingr.setRotationPoint(0.0F, 24.0F, 0.0F);
		setRotationAngle(wingr, -1.5708F, 0.0F, 1.5708F);
		wingr.cubeList.add(new ModelBox(wingr, 42, 14, -7.5F, -6.0F, 1.0F, 3, 4, 1, 0.0F, false));

		bone5 = new RendererModel(this);
		bone5.setRotationPoint(0.0F, 24.0F, 0.0F);
		setRotationAngle(bone5, 0.7854F, 0.0F, 0.0F);
		bone5.cubeList.add(new ModelBox(bone5, 31, 30, 1.0F, 0.4645F, -0.5355F, 3, 2, 2, 0.0F, false));
		bone5.cubeList.add(new ModelBox(bone5, 31, 30, 2.0F, -4.5355F, 0.4645F, 3, 2, 2, 0.0F, false));
		bone5.cubeList.add(new ModelBox(bone5, 31, 30, -4.0F, -4.5355F, 0.4645F, 3, 2, 2, 0.0F, false));
		bone5.cubeList.add(new ModelBox(bone5, 31, 30, -3.0F, 0.4645F, -0.5355F, 3, 2, 2, 0.0F, false));
		bone5.cubeList.add(new ModelBox(bone5, 6, 29, -3.0F, -5.5355F, 1.4645F, 7, 7, 5, 0.0F, false));
	}

	@Override
	public void render(EntitySummoningImp entity, float f, float f1, float f2, float f3, float f4, float f5) {
		bone.render(f5);
		wingl.render(f5);
		wingr.render(f5);
		bone5.render(f5);
	}
	public void setRotationAngle(RendererModel RendererModel, float x, float y, float z) {
		RendererModel.rotateAngleX = x;
		RendererModel.rotateAngleY = y;
		RendererModel.rotateAngleZ = z;
	}
}