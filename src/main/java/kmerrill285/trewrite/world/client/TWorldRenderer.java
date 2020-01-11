package kmerrill285.trewrite.world.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
@OnlyIn(value=Dist.CLIENT)
public class TWorldRenderer extends WorldRenderer {

	public TWorldRenderer(Minecraft mcIn) {
		super(mcIn);
	}

}
