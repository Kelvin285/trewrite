package kmerrill285.trewrite.util;

import net.minecraft.client.Minecraft;

public class Conversions {
	public static float conversion = 25.0f;
	
	public static float convertToIngame(float num) {
		return num / conversion;
	}
	public static float convertToRealtime(float num) {
		return num * conversion;
	}
	
	public static int buyToSell(int buy) {
		return (int)(buy * buyToSell);
	}
	
	public static int sellToBuy(int sell) {
		return (int)(sell * sellToBuy);
	}
	
	public static int toMCTicks(int terrariaTicks) {
		return terrariaTicks / 3;
	}
	
	public static int toTerrariaTicks(int MCTicks) {
		return MCTicks * 3;
	}
	
	public static int toScreenX(float x) {
		if (Minecraft.getInstance() != null) {
			if (Minecraft.getInstance().mainWindow != null) {
				int scaledWidth, scaledHeight;
				scaledWidth = Minecraft.getInstance().mainWindow.getScaledWidth();
				return (int)(scaledWidth * (x / scaledWidth));
			}
		}
		return (int)x;
	}
	
	public static int toScreenY(float y) {
		if (Minecraft.getInstance() != null) {
			if (Minecraft.getInstance().mainWindow != null) {
				int scaledWidth, scaledHeight;
			    scaledHeight = Minecraft.getInstance().mainWindow.getScaledHeight();
				return (int)(scaledHeight * (y / scaledHeight));
			}
		}
		return (int)y;
	}
	
	public static float feetToMeters = 0.3048f;
	public static float metersToFeet = 3.28084f;
	
	public static float buyToSell = 0.2f;
	public static float sellToBuy = 1.0f / 0.2f;
	
}
