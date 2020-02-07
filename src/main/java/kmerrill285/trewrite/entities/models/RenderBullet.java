package kmerrill285.trewrite.entities.models;

import kmerrill285.trewrite.core.items.ItemStackT;
import kmerrill285.trewrite.entities.EntityItemT;
import kmerrill285.trewrite.items.ItemsT;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderBullet extends EntityRenderer<Entity>
{
    private ResourceLocation texture;
    private RenderEntityItemT r;
    public RenderBullet(EntityRendererManager renderManagerIn, String texture)
    {
        super(renderManagerIn);
        this.texture = new ResourceLocation(texture);
        r = new RenderEntityItemT(this.renderManager, Minecraft.getInstance().getItemRenderer());

    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity>) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doe
     */
    public void doRender(Entity entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
//    	BossStatus.setBossStatus(entity, true);
        this.shadowSize = 0.25F;
        super.doRender(entity, x, y, z, 0, partialTicks);
        r.doRender(EntityItemT.spawnItem(entity.world, entity.getPosition(), new ItemStackT(ItemsT.MUSKET_BALL)), x, y, z, entityYaw, partialTicks);
    }

    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    protected void preRenderCallback(Entity entitylivingbaseIn, float partialTickTime)
    {
    	
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(Entity entity)
    {
    	return texture;
    }
}
