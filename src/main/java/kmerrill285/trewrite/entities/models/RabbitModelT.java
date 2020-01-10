package kmerrill285.trewrite.entities.models;

import com.mojang.blaze3d.platform.GlStateManager;

import kmerrill285.trewrite.util.Util;
import net.minecraft.client.renderer.entity.model.RabbitModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.passive.RabbitEntity;

public class RabbitModelT<T extends RabbitEntity> extends RabbitModel<T> {
	private final RendererModel santaHat;

	
	public RabbitModelT() {
		super();
		 this.santaHat = new RendererModel(this, 32, 0);
	     this.santaHat.addBox(-2.5F, -2.0F, -5.0F, 5, 4, 5);
	     this.santaHat.setRotationPoint(0.0F, 16.0F, -1.0F);
	     this.santaHat.mirror = true;
	     this.santaHat.textureWidth = 0;
	     this.santaHat.textureHeight = 0;
	}
	
	 public void render(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		 
	      this.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
	      super.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
	      
	      if (Util.isChristmas()) {
		      if (this.isChild) {
		         float f = 1.5F;
		         GlStateManager.pushMatrix();
		         GlStateManager.scalef(0.56666666F, 0.56666666F, 0.56666666F);
		         GlStateManager.translatef(0.0F, 22.0F * scale, 2.0F * scale);
		         GlStateManager.bindTexture(0);
		         GlStateManager.color3f(1f, 1f, 1f);
		         this.santaHat.offsetY = -0.3F;
		         this.santaHat.offsetZ = -0.0F;
		         GlStateManager.pushMatrix();
		         GlStateManager.scalef(1.0F, 0.3F, 1.0F);
		         GlStateManager.translatef(0.0f, 1.7f, 0.0f);
		         this.santaHat.render(scale);
		         
		         GlStateManager.color3f(1f, 0f, 0f);
		         this.santaHat.offsetY = -0.5f;
		         GlStateManager.scalef(0.8f, 1.0f, 0.8f);
		         this.santaHat.render(scale);
		         this.santaHat.offsetY = -0.7f;
		         GlStateManager.scalef(0.8f, 1.0f, 0.8f);
		         this.santaHat.render(scale);
		         
		         this.santaHat.offsetY = -0.9f;
		         GlStateManager.scalef(0.8f, 1.0f, 0.8f);
		         this.santaHat.render(scale);
		         
		         GlStateManager.popMatrix();
	
		         GlStateManager.popMatrix();
		      } else {
		         GlStateManager.pushMatrix();
		         GlStateManager.scalef(0.6F, 0.6F, 0.6F);
		         GlStateManager.translatef(0.0F, 16.0F * scale, 0.0F);
		         GlStateManager.bindTexture(0);
		         GlStateManager.color3f(1f, 1f, 1f);
		         this.santaHat.offsetY = -0.3F;
		         this.santaHat.offsetZ = -0.0F;
		         GlStateManager.pushMatrix();
		         GlStateManager.scalef(1.0F, 0.3F, 1.0F);
		         GlStateManager.translatef(0.0f, 1.7f, 0.0f);
		         this.santaHat.render(scale);
		         
		         GlStateManager.color3f(1f, 0f, 0f);
		         this.santaHat.offsetY = -0.5f;
		         GlStateManager.scalef(0.8f, 1.0f, 0.8f);
		         this.santaHat.render(scale);
		         this.santaHat.offsetY = -0.7f;
		         GlStateManager.scalef(0.8f, 1.0f, 0.8f);
		         this.santaHat.render(scale);
		         
		         this.santaHat.offsetY = -0.9f;
		         GlStateManager.scalef(0.8f, 1.0f, 0.8f);
		         this.santaHat.render(scale);
		         
		         GlStateManager.popMatrix();
	
		         GlStateManager.popMatrix();
		      }
	      }
	   }
	 
	 public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
	     super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
	      this.santaHat.rotateAngleX = headPitch * ((float)Math.PI / 180F);
	      this.santaHat.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);

	   }

}
