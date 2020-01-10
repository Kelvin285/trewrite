package kmerrill285.trewrite.items.terraria.tools;

import kmerrill285.trewrite.items.ItemT;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MagicMirror extends ItemT {
	public MagicMirror(String name) {
		super(new Properties().group(ItemGroup.TOOLS), name);
		this.setBuySell(10000);
		this.setTooltip("Gaze in the mirror to return home");
		this.useTime = 90;
		this.speed = 90;
		this.maxStack = 1;
	}
	
	 public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		 if (!worldIn.isRemote) {
		      worldIn.playSound((PlayerEntity)null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ITEM_TRIDENT_THUNDER, SoundCategory.PLAYERS, 1.0F, 0.5F);
		      {
		    	  BlockPos bedPos = playerIn.getBedLocation(playerIn.getSpawnDimension());
					 if (bedPos == null) {
						 bedPos = worldIn.getSpawnPoint();
						 System.out.println(bedPos);
					 }
				  worldIn.playSound((PlayerEntity)null, bedPos.getX(), bedPos.getY(), bedPos.getZ(), SoundEvents.ITEM_TRIDENT_THUNDER, SoundCategory.PLAYERS, 1.0F, 0.5F);

		      }
		      
		     for (int i = 0; i < 20; i++) {
		    	 worldIn.addParticle(ParticleTypes.END_ROD, playerIn.posX, playerIn.posY + i * (1.0 / 20.0), playerIn.posZ, worldIn.rand.nextDouble() - 0.5f, worldIn.rand.nextDouble() - 0.5f, worldIn.rand.nextDouble() - 0.5f);
		     }
			 new Thread() {
				 public void run() {
					 BlockPos bedPos = playerIn.getBedLocation(playerIn.getSpawnDimension());
					 if (bedPos == null) {
						 bedPos = worldIn.getSpawnPoint();
						 System.out.println(bedPos);
					 }
					 playerIn.addPotionEffect(new EffectInstance(Effects.NAUSEA, 5 * 20, 255, true, true));
					 playerIn.addPotionEffect(new EffectInstance(Effects.BLINDNESS, 5 * 20, 1, true, true));
					 
					 try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					 
					 playerIn.setPositionAndUpdate(bedPos.getX(), bedPos.getY(), bedPos.getZ());
					 playerIn.noClip = false;
					 
					 
					 if (playerIn.dimension != playerIn.getSpawnDimension())
						 playerIn.changeDimension(playerIn.getSpawnDimension());
				 }
			 }.start();
			
		 }
		 playerIn.getCooldownTracker().setCooldown(this, (int) ((this.useTime) * (30.0 / 60.0)));
		
		 return new ActionResult<>(ActionResultType.SUCCESS, new ItemStack(this));
	 }
}
