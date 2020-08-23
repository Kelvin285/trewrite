package kmerrill285.trewrite.client.gui.dialog;

import com.mojang.blaze3d.platform.GlStateManager;

import kmerrill285.trewrite.client.gui.inventory.container.GuiContainerTerrariaInventory;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DialogGui extends ContainerScreen<DialogContainer> {

    public static ResourceLocation GUI_CORNER = new ResourceLocation("trewrite", "textures/gui/gui_corner.png");
    public static ResourceLocation GUI_EDGE = new ResourceLocation("trewrite", "textures/gui/gui_edge.png");
    public static ResourceLocation GUI_FOREGROUND = new ResourceLocation("trewrite", "textures/gui/gui_foreground.png");
    public static ResourceLocation GUI_BACKGROUND = new ResourceLocation("trewrite", "textures/gui/gui_background.png");

	
    public int guiWidth = 100;
    public int guiHeight = 100;
    
	public DialogGui(DialogContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
	}
	
	@Override
    public void init() {
        super.init();
    }
	
	 @Override
     public void render(int mouseX, int mouseY, float partialTicks) {
         this.renderBackground();
         super.render(mouseX, mouseY, partialTicks);
	 }
	 
	 @Override
     protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		 
         //this.fontRenderer.drawString(I18n.format("container.crafting"), 97, 8, 4210752);
         GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.minecraft.getTextureManager().bindTexture(GuiContainerTerrariaInventory.INVENTORY_BACKGROUND);
         this.minecraft.getTextureManager().bindTexture(GUI_BACKGROUND);
         
         this.guiLeft = 50;
         this.guiTop = 50;
         
         this.guiWidth = 200;
         this.guiHeight = 50;
         
         int i = this.guiLeft;
         int j = this.guiTop;
         
         
         GlStateManager.pushMatrix();
         
         GlStateManager.translatef(i + 3, j + 3, 0);
         GlStateManager.scalef((1.0f / 4.0f) * (guiWidth + 2), (1.0f / 4.0f) * (guiHeight + 2), 1);
         this.blit(0, 0, 0, 8, 4, 4);
         GlStateManager.popMatrix();
         
         
         this.blit(i, j, 0, 0, 4, 4);
         this.blit(i + 4 + guiWidth, j, 4, 0, 4, 4);
         this.blit(i, j + 4 + guiHeight, 0, 4, 4, 4);
         this.blit(i + 4 + guiWidth, j + 4 + guiHeight, 4, 4, 4, 4);
         
         GlStateManager.pushMatrix();
         GlStateManager.translatef(i + 4, j, 0);
         GlStateManager.scalef((1.0f / 2.0f) * guiWidth, 1, 1);
         this.blit(0, 0, 2, 0, 2, 3);
         GlStateManager.popMatrix();
         
         GlStateManager.pushMatrix();
         GlStateManager.translatef(i, j + 4, 0);
         GlStateManager.scalef(1, (1.0f / 2.0f) * guiHeight, 1);
         this.blit(0, 0, 0, 2, 3, 2);
         GlStateManager.popMatrix();
         
         GlStateManager.pushMatrix();
         GlStateManager.translatef(i + guiWidth + 5, j + 4, 0);
         GlStateManager.scalef(1, (1.0f / 2.0f) * guiHeight, 1);
         this.blit(0, 0, 5, 2, 3, 2);
         GlStateManager.popMatrix();
         
         GlStateManager.pushMatrix();
         GlStateManager.translatef(i + 4, j + guiHeight + 5, 0);
         GlStateManager.scalef((1.0f / 2.0f) * guiWidth, 1, 1);
         this.blit(0, 0, 2, 5, 2, 3);
         GlStateManager.popMatrix();
     }
	 
	 

}
