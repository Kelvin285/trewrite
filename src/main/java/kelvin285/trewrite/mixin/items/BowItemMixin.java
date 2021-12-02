package kelvin285.trewrite.mixin.items;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.thrown.EggEntity;
import net.minecraft.item.*;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BowItem.class)
public class BowItemMixin extends Item {
    public BowItemMixin(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (stack.getCooldown() < 1)
        if (user instanceof PlayerEntity) {
            stack.setCooldown(20);
            PlayerEntity playerEntity = (PlayerEntity)user;
            boolean bl = playerEntity.getAbilities().creativeMode || EnchantmentHelper.getLevel(Enchantments.INFINITY, stack) > 0;
            ItemStack itemStack = playerEntity.getArrowType(stack);
            if (!itemStack.isEmpty() || bl) {
                if (itemStack.isEmpty()) {
                    itemStack = new ItemStack(Items.ARROW);
                }

                boolean bl2 = bl && itemStack.isOf(Items.ARROW);
                if (!world.isClient) {
                    ArrowItem arrowItem = (ArrowItem)(itemStack.getItem() instanceof ArrowItem ? itemStack.getItem() : Items.ARROW);
                    PersistentProjectileEntity persistentProjectileEntity = arrowItem.createArrow(world, itemStack, playerEntity);
                    Vec3d observed_pos = null;

                    try {
                        observed_pos = (Vec3d)PlayerEntity.class.getDeclaredField("observed_pos").get(user);
                    }catch (Exception e) {
                        e.printStackTrace();
                    }

                    Vec3d vec = new Vec3d(observed_pos.x - user.getX(), observed_pos.y - user.getY(), observed_pos.z - user.getZ());
                    double h_magnitude = Math.sqrt(Math.pow(observed_pos.x - user.getX(), 2) + Math.pow(observed_pos.z - user.getZ(), 2));
                    double pitch = -Math.toDegrees(Math.atan2(observed_pos.y - (user.getY() + user.getStandingEyeHeight()), h_magnitude)) - 5;
                    persistentProjectileEntity.setProperties(user, (float)pitch, (float)Math.toDegrees(Math.atan2(observed_pos.z - user.getZ(), observed_pos.x - user.getX())) - 90, 0.0F, 1.5F, 1.0F);


                    //if (f == 1.0F) {
                        //persistentProjectileEntity.setCritical(true);
                    //}

                    int j = EnchantmentHelper.getLevel(Enchantments.POWER, stack);
                    if (j > 0) {
                        persistentProjectileEntity.setDamage(persistentProjectileEntity.getDamage() + (double)j * 0.5D + 0.5D);
                    }

                    int k = EnchantmentHelper.getLevel(Enchantments.PUNCH, stack);
                    if (k > 0) {
                        persistentProjectileEntity.setPunch(k);
                    }

                    if (EnchantmentHelper.getLevel(Enchantments.FLAME, stack) > 0) {
                        persistentProjectileEntity.setOnFireFor(100);
                    }

                    stack.damage(1, playerEntity, (p) -> {
                        p.sendToolBreakStatus(playerEntity.getActiveHand());
                    });
                    if (bl2 || playerEntity.getAbilities().creativeMode && (itemStack.isOf(Items.SPECTRAL_ARROW) || itemStack.isOf(Items.TIPPED_ARROW))) {
                        persistentProjectileEntity.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
                    }

                    world.spawnEntity(persistentProjectileEntity);
                }

                world.playSound((PlayerEntity)null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (world.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
                if (!bl2 && !playerEntity.getAbilities().creativeMode) {
                    itemStack.decrement(1);
                    if (itemStack.isEmpty()) {
                        playerEntity.getInventory().removeOne(itemStack);
                    }
                }

                playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
            }
        }

        return TypedActionResult.success(stack, world.isClient());
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {

    }
}
