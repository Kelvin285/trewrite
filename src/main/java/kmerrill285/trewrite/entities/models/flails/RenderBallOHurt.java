package kmerrill285.trewrite.entities.models.flails;

import com.mojang.blaze3d.platform.GlStateManager;

import kmerrill285.trewrite.entities.projectiles.flails.EntityBallOHurt;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class RenderBallOHurt extends MobRenderer<EntityBallOHurt, ModelBallOHurt>
{
    private ResourceLocation texture = new ResourceLocation("trewrite:textures/entity/flail/ball_of_hurt.png");

    
    public RenderBallOHurt(EntityRendererManager renderManagerIn)
    {
    	
        super(renderManagerIn, new ModelBallOHurt(), 4.0f);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity>) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doe
     */
    public void doRender(EntityBallOHurt entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
//    	BossStatus.setBossStatus(entity, true);
        this.shadowSize = 0.25F;
        entity.rotationYaw = 0;
        entity.prevRotationYaw = 0;
        entity.rotationYawHead = 0;
        entity.prevRotationYawHead = 0;
        super.doRender(entity, x, y, z, 0, partialTicks);
    }

    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    protected void preRenderCallback(EntityBallOHurt entity, float partialTickTime)
    {
//    	GlStateManager.rotated(entity.getMotion().x * 360 * 2, 1, 0, 0);
//    	GlStateManager.rotated(entity.getMotion().z * 360 * 2, 0, 0, 1);

    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityBallOHurt entity)
    {
    	return texture;
    }
}
