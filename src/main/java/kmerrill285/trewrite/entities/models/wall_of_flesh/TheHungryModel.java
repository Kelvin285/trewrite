package kmerrill285.trewrite.entities.models.wall_of_flesh;
import com.mojang.blaze3d.platform.GlStateManager;

import kmerrill285.trewrite.entities.monsters.bosses.wof.TheHungryEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.ModelBox;
import net.minecraft.util.math.Vec3d;

public class TheHungryModel extends EntityModel<TheHungryEntity> {
	private final RendererModel bb_main;

	private TheHungryAttachmentModel attachment;
	
	public TheHungryModel() {
		textureWidth = 128;
		textureHeight = 64;

		bb_main = new RendererModel(this);
		bb_main.setRotationPoint(0.0F, 24.0F, 0.0F);
		bb_main.cubeList.add(new ModelBox(bb_main, 0, 0, -16.0F, -32.0F, -16.0F, 32, 32, 32, 0.0F, false));
		attachment = new TheHungryAttachmentModel();
	}

	@Override
	public void render(TheHungryEntity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		bb_main.render(f5);
		
		GlStateManager.pushMatrix();
		GlStateManager.rotated(-entity.rotationYaw, 0, 1, 0);
		if (entity.owner != null) {
			if (entity.firstPos != null) {
				Vec3d pos1 = entity.getPositionVec();
				if (entity.owner != null) {
					Vec3d pos2 = entity.owner.getPositionVec().add(entity.firstPos).subtract(entity.owner.firstPos);
					double dist = pos1.distanceTo(pos2);
					
					
//					GlStateManager.scaled(4, 4, 4);
					double xx = pos2.x - pos1.x;
					double zz = pos1.z - pos2.z;
					double yy = pos1.y - pos2.y;
					
					Vec3d pos3 = new Vec3d(xx, yy, zz).normalize();
					double scale = 1;
					if (dist > 0)
					for (double i = 0; i < dist; i+=0.25) {
						if (i > 0) {
							GlStateManager.pushMatrix();
							GlStateManager.translated(pos3.x * i, pos3.y * i - (scale + 0.5) + 1 * ((dist - i) / dist), pos3.z * i);
							GlStateManager.scaled(scale, scale, scale);
							attachment.render(entity,f, f1, f2, f3, f4, f5);
							GlStateManager.popMatrix();
						}
						
					}
					
					

				}
			}
		}
		GlStateManager.popMatrix();
	}
	public void setRotationAngle(RendererModel RendererModel, float x, float y, float z) {
		RendererModel.rotateAngleX = x;
		RendererModel.rotateAngleY = y;
		RendererModel.rotateAngleZ = z;
	}
}