package kmerrill285.trewrite.entities.models.wall_of_flesh;

import kmerrill285.trewrite.entities.monsters.bosses.wof.EntityWallOfFlesh;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.ModelBox;
import net.minecraft.entity.Entity;

public class ModelWallOfFlesh extends EntityModel<EntityWallOfFlesh> {
	private final RendererModel bone;
	private final RendererModel bone2;
	private final RendererModel bone3;

	public ModelWallOfFlesh() {
		textureWidth = 4099;
		textureHeight = 5120;

		bone = new RendererModel(this);
		bone.setRotationPoint(0.0F, 24.0F, 0.0F);
		bone.cubeList.add(new ModelBox(bone, 0, 10, -512.0F, -4096.0F, -524.0F, 1024, 4096, 1024, 0.0F, false));

		bone2 = new RendererModel(this);
		bone2.setRotationPoint(0.0F, 24.0F, 0.0F);
		setRotationAngle(bone2, 0.0F, -0.7854F, 0.0F);
		bone2.cubeList.add(new ModelBox(bone2, 0, 10, 0.0F, -4096.0F, -912.0F, 1024, 4096, 1024, 0.0F, false));

		bone3 = new RendererModel(this);
		bone3.setRotationPoint(0.0F, 24.0F, 0.0F);
		setRotationAngle(bone3, 0.0F, 0.7854F, 0.0F);
		bone3.cubeList.add(new ModelBox(bone3, 0, 10, -1024.0F, -4096.0F, -912.0F, 1024, 4096, 1024, 0.0F, false));
	}

	@Override
	public void render(EntityWallOfFlesh entity, float f, float f1, float f2, float f3, float f4, float f5) {
		bone.render(f5);
		bone2.render(f5);
		bone3.render(f5);
	}
	public void setRotationAngle(RendererModel RendererModel, float x, float y, float z) {
		RendererModel.rotateAngleX = x;
		RendererModel.rotateAngleY = y;
		RendererModel.rotateAngleZ = z;
	}
}