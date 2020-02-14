package kmerrill285.trewrite.entities.models.boomerangs;

import com.mojang.blaze3d.platform.GlStateManager;

import kmerrill285.trewrite.entities.BoomerangEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;

public class BoomerangModel extends EntityModel <BoomerangEntity>{
	public RendererModel shape1;
    public RendererModel shape1_1;
    public RendererModel shape1_2;

    public BoomerangModel() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.shape1_2 = new RendererModel(this, 0, 0);
        this.shape1_2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.shape1_2.addBox(0.0F, 0.0F, 0.0F, 9, 1, 3, 0.0F);
        this.shape1_1 = new RendererModel(this, 0, 0);
        this.shape1_1.setRotationPoint(-3.0F, 0.0F, 0.0F);
        this.shape1_1.addBox(0.0F, 0.0F, 0.0F, 9, 1, 3, 0.0F);
        this.setRotateAngle(shape1_1, 0.0F, 1.5707963267948966F, 0.0F);
        this.shape1 = new RendererModel(this, 0, 4);
        this.shape1.setRotationPoint(-3.0F, 0.0F, 0.0F);
        this.shape1.addBox(0.0F, 0.0F, 0.0F, 3, 1, 3, 0.0F);
    }

    @Override
    public void render(BoomerangEntity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		GlStateManager.rotated((float)(System.nanoTime() / 1000000.0), 0, 1, 0);
		GlStateManager.translated(0, 1.25f, 0);
        this.shape1_2.render(f5);
        this.shape1_1.render(f5);
        this.shape1.render(f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(RendererModel RendererModel, float x, float y, float z) {
        RendererModel.rotateAngleX = x;
        RendererModel.rotateAngleY = y;
        RendererModel.rotateAngleZ = z;
    }
}
