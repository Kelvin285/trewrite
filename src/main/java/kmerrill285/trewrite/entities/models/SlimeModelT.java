package kmerrill285.trewrite.entities.models;

import com.mojang.blaze3d.platform.GlStateManager;

import kmerrill285.trewrite.util.Util;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.entity.model.SlimeModel;
import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SlimeModelT<T extends Entity> extends SlimeModel<T> {
	private final RendererModel bow;

   public SlimeModelT(int slimeBodyTexOffY) {
     super(slimeBodyTexOffY);
     this.bow = new RendererModel(this, 32, 0);
     this.bow.addBox(-3.25F, 18.0F, -3.5F, 2, 2, 2);
     
   }

   public void render(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
      super.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
      
      if (Util.isChristmas() == false) return;
      GlStateManager.pushMatrix();
      GlStateManager.disableTexture();
      GlStateManager.translatef(-0.025f, 0.0f, 0.15f);
      GlStateManager.pushMatrix();
      GlStateManager.scalef(4.0f, 1.0f, 1.0f);
      GlStateManager.translatef(0.15f, 0.25f, 0.0f);
      this.bow.render(scale);
      GlStateManager.translatef(0.0f, -0.45f, 0.0f);
      this.bow.render(scale);
      GlStateManager.popMatrix();
      
      GlStateManager.pushMatrix();
      GlStateManager.translatef(-0.05f, -3.5f, 0.0f);

      GlStateManager.scalef(1.0f, 4f, 1.0f);
      this.bow.render(scale);
      GlStateManager.translatef(0.45f, 0.0f, 0.0f);
      this.bow.render(scale);
      
      GlStateManager.popMatrix();
      
      
      GlStateManager.pushMatrix();
      GlStateManager.translatef(0.9f, 0.1f, 0.0f);
      GlStateManager.rotatef(45, 0, 0, 1);
      this.bow.render(scale);
      GlStateManager.translatef(0.1f, -0.1f, 0.0f);
      this.bow.render(scale);
      GlStateManager.popMatrix();
      
      GlStateManager.pushMatrix();
      GlStateManager.translatef(0.17f, -0.25f, 0.1f);
      GlStateManager.scalef(1.0f, 1.0f, 1.6f);
      this.bow.render(scale);
      GlStateManager.popMatrix();
      
      GlStateManager.enableTexture();
      GlStateManager.popMatrix();
   }
}
