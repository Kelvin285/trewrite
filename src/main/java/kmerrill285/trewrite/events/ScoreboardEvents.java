package kmerrill285.trewrite.events;

import kmerrill285.trewrite.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreCriteria;
import net.minecraft.scoreboard.ScoreCriteria.RenderType;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class ScoreboardEvents {	
	
	public static String POTION_SICKNESS = "PSN",
			MANA = "MANA", LIFE_CRYSTALS = "LCRYSTALS", MAX_MANA = "MAXMANA", MANA_TIMER = "MANAT",
			COINS = "COINS", DAYTIME = "DAYT", MANA_SICKNESS = "MS", MANA_SICKNESS_EFFECT = "ME";
	
	public static int tickTimer = 0;
	
	@SubscribeEvent
	public static void handleTickEvent(TickEvent event) {
		if (tickTimer < 20) {
			tickTimer++;
		} else {
			tickTimer = 0;
		}
		
		
	}
	
	public static int getMana(PlayerEntity player) {
		return Util.renderMana;
	}
	
	public static int getMaxMana(PlayerEntity player) {
		return Util.renderMaxMana;
	}
	
	public static Score getScore(Scoreboard scoreboard, PlayerEntity player, String name) {
		ScoreObjective OBJ = getObjective(name, scoreboard, player);
		return scoreboard.getOrCreateScore(player.getScoreboardName(), OBJ);
	}
	
	public static void handleScoreboard(PlayerEntity player, World world, Scoreboard scoreboard) {
//		if (Minecraft.getInstance() == null) return;
//		if (Minecraft.getInstance().getRenderViewEntity() == null) return;
		int i = 0;
		ScoreObjective COINS = getObjective(ScoreboardEvents.COINS, scoreboard, player, i++);
		i++; //skip over sidebar rendering
		ScoreObjective MANA = getObjective(ScoreboardEvents.MANA, scoreboard, player, i++);
		ScoreObjective MAX_MANA = getObjective(ScoreboardEvents.MAX_MANA, scoreboard, player, i++);
		ScoreObjective MANA_TIMER = getObjective(ScoreboardEvents.MANA_TIMER, scoreboard, player);
		ScoreObjective LIFE_CRYSTALS = getObjective(ScoreboardEvents.LIFE_CRYSTALS, scoreboard, player, i++);
		ScoreObjective POTION_SICKNESS = getObjective(ScoreboardEvents.POTION_SICKNESS, scoreboard, player);
		ScoreObjective DAYTIME = getObjective(ScoreboardEvents.DAYTIME, scoreboard, player, i++);
		ScoreObjective MANA_SICKNESS = getObjective(ScoreboardEvents.MANA_SICKNESS, scoreboard, player);
		ScoreObjective MANA_SICKNESS_EFFECT = getObjective(ScoreboardEvents.MANA_SICKNESS_EFFECT, scoreboard, player);
		Score manaSickness = scoreboard.getOrCreateScore(player.getScoreboardName(), MANA_SICKNESS);
		Score manaSicknessEffect = scoreboard.getOrCreateScore(player.getScoreboardName(), MANA_SICKNESS_EFFECT);
		
		
		Score mana = scoreboard.getOrCreateScore(player.getScoreboardName(), MANA);
		Score maxMana = scoreboard.getOrCreateScore(player.getScoreboardName(), MAX_MANA);
		Score manaTimer = scoreboard.getOrCreateScore(player.getScoreboardName(), MANA_TIMER);
		
		
		Score coins = scoreboard.getOrCreateScore(player.getScoreboardName(), COINS);
		Score potionSickness = scoreboard.getOrCreateScore(player.getScoreboardName(), POTION_SICKNESS);
		Score daytime = scoreboard.getOrCreateScore(player.getScoreboardName(), DAYTIME);

		daytime.setScorePoints((int)(world.getDayTime() % 24000));
		
		if (Util.projectileCooldown > 0) {
			Util.projectileCooldown--;
		}
		
		if (potionSickness.getScorePoints() > 0) {
			setDisplay(i++, scoreboard, ScoreboardEvents.POTION_SICKNESS);
			potionSickness.setScorePoints(potionSickness.getScorePoints() - 1);
		}
		
		if (manaSickness.getScorePoints() > 0) {
			setDisplay(i++, scoreboard, ScoreboardEvents.MANA_SICKNESS);
			setDisplay(i++, scoreboard, ScoreboardEvents.MANA_SICKNESS_EFFECT);
			manaSickness.setScorePoints(manaSickness.getScorePoints() - 1);
		} else {
			manaSicknessEffect.setScorePoints(0);
		}
		
		
		
		if (mana.getScorePoints() < 0) mana.setScorePoints(0);
		if (maxMana.getScorePoints() < 20) { 
			maxMana.setScorePoints(20); 
			mana.setScorePoints(20);
		}
		boolean playerMoving = false;
		if (Math.abs(player.getMotion().x + player.getMotion().y + player.getMotion().z) > 0.1f) playerMoving = true;
		if (manaTimer.getScorePoints() < (playerMoving ? 10 : 5)) {
			manaTimer.incrementScore();
		} else {
			manaTimer.setScorePoints(0);
			if (mana.getScorePoints() < maxMana.getScorePoints()) {
				mana.incrementScore();
			}
		}
		
		if (mana.getScorePoints() > maxMana.getScorePoints()) mana.setScorePoints(maxMana.getScorePoints());
		
		
	}
	
	public static boolean scoreboardHasObjective(Scoreboard scoreboard, String name) {
		return scoreboard.getObjective(name) != null;
	}
	
	public static void setDisplay(int scoreDisplay, Scoreboard scoreboard, String name) {
		if (scoreDisplay != -1)
			if (scoreboard.getObjectiveInDisplaySlot(scoreDisplay) != scoreboard.getObjective(name))
				scoreboard.setObjectiveInDisplaySlot(scoreDisplay, scoreboard.getObjective(name));
	}
	
	public static ScoreObjective getObjective (String name, Scoreboard scoreboard, PlayerEntity player) {
		if (scoreboardHasObjective(scoreboard, name)) {
			
			return scoreboard.getObjective(name);
		}
		return scoreboard.addObjective(name, ScoreCriteria.DUMMY, new StringTextComponent(name), RenderType.INTEGER);
	}
	
	public static ScoreObjective getObjective (String name, Scoreboard scoreboard, PlayerEntity player, int scoreDisplay) {
		if (scoreboardHasObjective(scoreboard, name)) {
			if (scoreDisplay != -1)
			if (scoreboard.getObjectiveInDisplaySlot(scoreDisplay) != scoreboard.getObjective(name)) {
				scoreboard.setObjectiveInDisplaySlot(scoreDisplay, scoreboard.getObjective(name));
				System.out.println(Scoreboard.getObjectiveDisplaySlot(scoreDisplay));
			}
			return scoreboard.getObjective(name);
		}
		return scoreboard.addObjective(name, ScoreCriteria.DUMMY, new StringTextComponent(name), RenderType.INTEGER);
	}
	@OnlyIn(value = Dist.CLIENT)
	public static void handleClientScoreboard(PlayerEntity player, World world, Scoreboard scoreboard) {
		ScoreObjective COINS = getObjectiveClient(ScoreboardEvents.COINS, scoreboard, player);
		ScoreObjective MANA = getObjectiveClient(ScoreboardEvents.MANA, scoreboard, player);
		ScoreObjective MAX_MANA = getObjectiveClient(ScoreboardEvents.MAX_MANA, scoreboard, player);
		ScoreObjective MANA_TIMER = getObjectiveClient(ScoreboardEvents.MANA_TIMER, scoreboard, player);
		ScoreObjective LIFE_CRYSTALS = getObjectiveClient(ScoreboardEvents.LIFE_CRYSTALS, scoreboard, player);
		ScoreObjective POTION_SICKNESS = getObjectiveClient(ScoreboardEvents.POTION_SICKNESS, scoreboard, player);
		ScoreObjective DAYTIME = getObjectiveClient(ScoreboardEvents.DAYTIME, scoreboard, player);
		ScoreObjective MANA_SICKNESS = getObjectiveClient(ScoreboardEvents.MANA_SICKNESS, scoreboard, player);
		ScoreObjective MANA_SICKNESS_EFFECT = getObjectiveClient(ScoreboardEvents.MANA_SICKNESS_EFFECT, scoreboard, player);
		Score manaSickness = scoreboard.getOrCreateScore(player.getScoreboardName(), MANA_SICKNESS);
		Score manaSicknessEffect = scoreboard.getOrCreateScore(player.getScoreboardName(), MANA_SICKNESS_EFFECT);
		
		
		Score mana = scoreboard.getOrCreateScore(player.getScoreboardName(), MANA);
		Score maxMana = scoreboard.getOrCreateScore(player.getScoreboardName(), MAX_MANA);
		Score manaTimer = scoreboard.getOrCreateScore(player.getScoreboardName(), MANA_TIMER);
		
		
		Score coins = scoreboard.getOrCreateScore(player.getScoreboardName(), COINS);
		Score potionSickness = scoreboard.getOrCreateScore(player.getScoreboardName(), POTION_SICKNESS);
		Score daytime = scoreboard.getOrCreateScore(player.getScoreboardName(), DAYTIME);

		
		
		if (Minecraft.getInstance() != null) {
			if (Minecraft.getInstance().getRenderViewEntity() instanceof PlayerEntity) {
				PlayerEntity p = (PlayerEntity)Minecraft.getInstance().getRenderViewEntity();
				if (p.getScoreboardName().contentEquals(player.getScoreboardName())) {
					Util.renderCoins = coins.getScorePoints();
					Util.renderMana = mana.getScorePoints();
					Util.renderMaxMana = maxMana.getScorePoints();
					Util.renderPotionSickness = potionSickness.getScorePoints() / 20;
					Util.dayTime = daytime.getScorePoints();
					Util.renderManaSickness = manaSickness.getScorePoints();
					Util.renderManaSicknessEffect = manaSicknessEffect.getScorePoints();
				}
			}
		}
		
	}
	@OnlyIn(value = Dist.CLIENT)
	public static ScoreObjective getObjectiveClient (String name, Scoreboard scoreboard, PlayerEntity player) {
		if (scoreboard.hasObjective(name)) {
			//scoreboard.broadcastScoreUpdate(player.getScoreboardName(), scoreboard.getObjective(name));
			return scoreboard.getObjective(name);
		}
		return scoreboard.addObjective(name, ScoreCriteria.DUMMY, new StringTextComponent(name), RenderType.INTEGER);
	}
	
}
