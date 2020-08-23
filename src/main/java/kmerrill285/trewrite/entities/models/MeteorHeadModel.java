package kmerrill285.trewrite.entities.models;

import com.mojang.blaze3d.platform.GlStateManager;

import kmerrill285.trewrite.entities.monsters.EntityMeteorHead;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;

public class MeteorHeadModel extends EntityModel<EntityMeteorHead> {
    public RendererModel shape1;

    public MeteorHeadModel() {
        this.textureWidth = 32;
        this.textureHeight = 16;
       
        this.shape1 = new RendererModel(this, 0, 0);
        this.shape1.setRotationPoint(-4.0F, 8.0F, -4.0F);
        this.shape1.addBox(0.0F, 0.0F, 0.0F, 8, 8, 8, 0.0F);
        
    }

    @Override
    public void render(EntityMeteorHead entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        GlStateManager.translated(0, 0.5f, 0);
        this.shape1.render(f5);
    }
}
