package kelvin285.trewrite.mixin.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.EggEntity;
import net.minecraft.item.EggItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EggItem.class)
public class EggItemMixin extends Item  {

    public EggItemMixin(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {


        ItemStack itemStack = user.getStackInHand(hand);
        world.playSound((PlayerEntity)null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_EGG_THROW, SoundCategory.PLAYERS, 0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
        if (!world.isClient) {

            Vec3d observed_pos = null;

            try {
                observed_pos = (Vec3d)PlayerEntity.class.getDeclaredField("observed_pos").get(user);
            }catch (Exception e) {
                e.printStackTrace();
            }

            if (observed_pos != null) {
                EggEntity eggEntity = new EggEntity(world, user);
                eggEntity.setItem(itemStack);

                Vec3d vec = new Vec3d(observed_pos.x - user.getX(), observed_pos.y - user.getY(), observed_pos.z - user.getZ());
                double h_magnitude = Math.sqrt(Math.pow(observed_pos.x - user.getX(), 2) + Math.pow(observed_pos.z - user.getZ(), 2));
                double pitch = -Math.toDegrees(Math.atan2(observed_pos.y - (user.getY() + user.getStandingEyeHeight()), h_magnitude)) - 5;
                eggEntity.setProperties(user, (float)pitch, (float)Math.toDegrees(Math.atan2(observed_pos.z - user.getZ(), observed_pos.x - user.getX())) - 90, 0.0F, 0.5F, 1.0F);

                world.spawnEntity(eggEntity);
            }
        }

        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!user.getAbilities().creativeMode) {
            itemStack.decrement(1);
        }

        return TypedActionResult.success(itemStack, world.isClient());
    }
}
