package kmerrill285.trewrite.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Random;

import net.minecraft.util.math.RayTraceResult;

public class Util {
	public static float terminalVelocity = 51.0f;
	
	public static int renderMana, renderMaxMana, renderCoins, renderPotionSickness, dayTime, renderManaSickness, renderManaSicknessEffect;
	
	public static String watchTime = "";
	
	public static boolean terrariaInventory = true, justClosedInventory = false;
	
	public static boolean resetRecipes = false;
	
	public static int projectileCooldown = 0;
	
	public static double entitySpawnRate = 1.0/100.0;
	public static double minSpawnDistance = 15.0;
	
	public static int surfaceLevel = 100; //anything above 128 is surface
	public static int skyLevel = 175;
	public static int caveLevel = 65;
	public static int underworldLevel = 0;
	
	public static double starChance = (3.0/1600.0) * 3.0;

	public static boolean refreshDimensionRenderer;

	public static RayTraceResult blockHit;

	public static boolean INVERSE_GRAVITY = false;
	public static long LAST_SPACETAP = 0;
	
	public static int renderBuild, renderCalming, renderIronskin, renderSwiftness,
	renderNightOwl, renderGills, renderRegeneration, renderMining, renderArchery, renderHunter,
	renderFeatherfall, renderFlipper, renderGravitation, renderHeartreach, renderInvisibility,
	renderThorns, renderWaterWalking, renderShine, renderBattle, renderObsidianSkin, renderMagicPower,
	renderManaRegeneration, renderTitan;

	public static int renderWeakDebuff, renderHorrified;

	
	public static boolean isChristmas() {
		return LocalDateTime.now().getMonth() == Month.DECEMBER && LocalDateTime.now().getDayOfMonth() >= 15 && LocalDateTime.now().getDayOfMonth() <= 31;
	}
	
	public static int randomValue(int min, int max, Random random) {
		return random.nextInt(max - min) + min;
	}
	
	public static int randomValue(int min, int max) {
		return new Random().nextInt(max - min) + min;
	}
	
	public static void makeFieldAccessible(Field field) throws Exception {
		Field modifiers = Field.class.getDeclaredField("modifiers");
		modifiers.setAccessible(true);
		try {
			modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);
			modifiers.setInt(field, field.getModifiers() & ~Modifier.PROTECTED);
			modifiers.setInt(field, field.getModifiers() | Modifier.PUBLIC);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public static String getTimerString(int seconds) {
		int mins = seconds / 60;
		int secs = seconds - (mins * 60);
		return mins + ":" + secs;
	}
	
	public static double getAngle(int x, int y)
	{
	    return 1.5 * Math.PI - Math.atan2(y,x); //note the atan2 call, the order of paramers is y then x
	}
	
	public static double lerp(double a, double b, double f) 
	{
	    return (a * (1.0 - f)) + (b * f);
	}
	
	public static String getSpeedString(int speed) {
		String sp = "Insanely Fast Speed";
		if (speed >= 0) sp = "Insanely Fast Speed";
		if (speed >= 9) sp = "Very Fast Speed";
		if (speed >= 21) sp = "Fast Speed";
		if (speed >= 26) sp = "Average Speed";
		if (speed >= 31) sp = "Slow Speed";
		if (speed >= 36) sp = "Very Slow Speed";
		if (speed >= 46) sp = "Extremely Slow Speed";
		if (speed >= 56) sp = "Snail Speed";
		return sp;
	}
	
	public static String getKnockbackString(float knockback) {
		String sp = "No Knockback";
		if (knockback > 11) sp = "Insane Knockback";
		if (knockback <= 11) sp = "Extremely Strong Knockback";
		if (knockback <= 9) sp = "Very Strong Knockback";
		if (knockback <= 7) sp = "Strong Knockback";
		if (knockback <= 6) sp = "Average Knockback";
		if (knockback <= 4) sp = "Weak Knockback";
		if (knockback <= 3) sp = "Very Weak Knockback";
		if (knockback <= 1.5f) sp = "Extremely Weak Knockback";
		if (knockback == 0) sp = "No Knockback";
		return sp;
	}
	
	
}
